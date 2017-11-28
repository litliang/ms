package com.yzb.card.king.ui.credit.holder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.popup.IdentificationsPopup;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.interfaces.IAddCardStep;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.credit.interfaces.OnSelectedListener;
import com.yzb.card.king.ui.credit.popup.SimpleListPop;
import com.yzb.card.king.ui.credit.popup.StatementDayDailog;
import com.yzb.card.king.ui.credit.popup.ValidityDayDialog;
import com.yzb.card.king.ui.credit.watcher.CardTextWatcher;
import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.other.controller.ScanCardController;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/23
 */
public class AddCardHolder extends IAddCardStep implements OnItemClickListener<CertType>
{
    private static final int REQUEST_PROTOCOL = 2;
    private View mRoot;
    private EditText etCardNum;
    private LinearLayout llBank;
    private ImageView ivBankLogo;
    private TextView tvValidityDate;
    private TextView tvStatementDate;
    private EditText etName;
    private TextView tvBankName;
    private ImageView ivCamera;
    private BaseCallBack<CardBin> listener;
    private CardBin bankInfo;
    private TextView tvProtocol;
    private SimpleListPop protocolPop;
    private EditText etSafeCode;
    private View llCanPayContent;

    protected AddCanPayCardActivity addCanPayCardActivity;
    private LinearLayout llIdentification;
    private EditText etCertNo;
    private IdentificationsPopup identificationsPopup;
    private CertType certType;
    private TextView tvCertType;
    private ScanCardController scanCardController;
    private LinearLayout llNotice;

    public AddCardHolder(AddCanPayCardActivity addCanPayCardActivity)
    {
        this.addCanPayCardActivity = addCanPayCardActivity;
        initView();
        initListener();
        initData();
    }

    private void initData()
    {
        etCardNum.setText(addCanPayCardActivity.getCardNo());
        etName.setText(addCanPayCardActivity.getName());
        identificationsPopup = new IdentificationsPopup(this);
        identificationsPopup.setWidth(UiUtils.dp2px(93));
        setCertType(identificationsPopup.getDefaultValue());

        scanCardController = new ScanCardController(addCanPayCardActivity);
        scanCardController.setCallBack(new BaseCallBack<CardInfo>()
        {
            @Override
            public void onSuccess(CardInfo data)
            {
                String cardNum = data.getFieldString(TFieldID.TBANK_NUM);
                etCardNum.setText(cardNum);
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }


    private void initListener()
    {
        ivCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                scanCardController.scan();
            }
        });

        tvStatementDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StatementDayDailog dayDailog = new StatementDayDailog();
                dayDailog.setListener(new OnSelectedListener()
                {
                    @Override
                    public void onSelected(String year, String day)
                    {
                        tvStatementDate.setText(day);
                    }
                });
                dayDailog.show(addCanPayCardActivity.getSupportFragmentManager(), "StatementDialog");
            }
        });

        tvValidityDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidityDayDialog dialog = new ValidityDayDialog();
                dialog.setListener(new OnSelectedListener()
                {
                    @Override
                    public void onSelected(String year, String day)
                    {
                        tvValidityDate.setText(year + "/" + (day.length() == 1 ? "0" + day : day));
                    }
                });
                dialog.show(addCanPayCardActivity.getSupportFragmentManager(), "ValidityDialog");
            }
        });

        llIdentification.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                identificationsPopup.showAsDropDown(v, -UiUtils.dp2px(16), 5);
            }
        });

        CardTextWatcher watcher = new CardTextWatcher(etCardNum)
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                initBankInfo(null);
                super.beforeTextChanged(s, start, count, after);
            }
        };
        listener = new BaseCallBack<CardBin>()
        {
            @Override
            public void onSuccess(CardBin data)
            {
                initBankInfo(data);
                setNoticeVisibility(data);
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
                llBank.setVisibility(View.GONE);
                setNoticeVisibility(null);
            }
        };
        watcher.setListener(listener);
        etCardNum.addTextChangedListener(watcher);
    }

    protected void setNoticeVisibility(CardBin data)
    {
        if (data == null || data.getBankId() == null)
        {
            llNotice.setVisibility(View.GONE);
            return;
        }
        if ("spdb".equals(data.getBankCode()))
        {
            llNotice.setVisibility(View.GONE);
        } else
        {
            llNotice.setVisibility(View.VISIBLE);
        }
    }

    protected void hideCanPayContent()
    {
        llCanPayContent.setVisibility(View.GONE);
        tvProtocol.setVisibility(View.GONE);
        mRoot.findViewById(R.id.vCertLine).setVisibility(View.GONE);
        mRoot.findViewById(R.id.llCertInfo).setVisibility(View.GONE);
        mRoot.findViewById(R.id.llStatementDate).setVisibility(View.GONE);
    }

    private void initBankInfo(CardBin data)
    {
        this.bankInfo = data;
        if (data != null && data.getBankId() != null)
        {
            llBank.setVisibility(View.VISIBLE);
            x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(data.getBankLogo()));
            tvBankName.setText(data.getBankName());
        } else
        {
            llBank.setVisibility(View.GONE);
            ivBankLogo.setImageResource(R.drawable.bg_transparent);
            tvBankName.setText(null);
        }

    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_add_card);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRoot.setLayoutParams(params);
        etCardNum = (EditText) mRoot.findViewById(R.id.etCardNum);
        llBank = (LinearLayout) mRoot.findViewById(R.id.llBank);
        ivBankLogo = (ImageView) mRoot.findViewById(R.id.ivBankLogo);
        tvBankName = (TextView) mRoot.findViewById(R.id.tvBankName);
        etName = (EditText) mRoot.findViewById(R.id.etName);
        tvStatementDate = (TextView) mRoot.findViewById(R.id.tvStatementDate);
        tvValidityDate = (TextView) mRoot.findViewById(R.id.tvValidityDate);
        ivCamera = (ImageView) mRoot.findViewById(R.id.ivCamera);
        llIdentification = (LinearLayout) mRoot.findViewById(R.id.llIdentification);
        etCertNo = (EditText) mRoot.findViewById(R.id.etCertNo);
        tvCertType = (TextView) mRoot.findViewById(R.id.tvCertType);

        etSafeCode = (EditText) mRoot.findViewById(R.id.etSafeCode);
        tvProtocol = (TextView) mRoot.findViewById(R.id.tvProtocol);
        llCanPayContent = mRoot.findViewById(R.id.llCanPayContent);

        llNotice = (LinearLayout) mRoot.findViewById(R.id.llNotice);
        setProtocolText();
    }

    private void setProtocolText()
    {
        tvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        String str = "《服务协议》";
        SpannableString spannableString = new SpannableString(str);
        ClickableSpan clickableSpan = new ClickableSpan()
        {

            @Override
            public void updateDrawState(TextPaint ds)
            {
                ds.setColor(UiUtils.getColor(R.color.credit_dark_brown));
                ds.bgColor = Color.WHITE;
            }

            @Override
            public void onClick(View widget)
            {
                if (protocolPop == null)
                {
                    initPop();
                }
                protocolPop.showAtLocation(mRoot, Gravity.CENTER_HORIZONTAL, 0, -UiUtils.dp2px(50));
            }

        };
        spannableString.setSpan(clickableSpan, 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvProtocol.append("同意 ");
        tvProtocol.append(spannableString);
    }

    private void initPop()
    {
        List<String> protocolList = new ArrayList<>();
        protocolList.add("快捷支付协议");
        protocolList.add("银行协议");
        protocolPop = new SimpleListPop(addCanPayCardActivity)
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(addCanPayCardActivity, WebViewClientActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(WebViewClientActivity.CATEGORY, getCategory(position));
                intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
                addCanPayCardActivity.startActivity(intent);

            }

            private String getCategory(int position)
            {
                if (position == 0)
                {
                    return "24";
                } else if (position == 1)
                {
                    return "25";
                } else
                {
                    return "";
                }
            }
        };
        protocolPop.setDataList(protocolList);
        protocolPop.setGravity(Gravity.CENTER_VERTICAL);
        protocolPop.setTitle("阅读协议");
    }


    @Override
    public boolean rightClick()
    {
        if (hasValid())
        {
            addCanPayCardActivity.nextStep();
        }
        return true;
    }

    protected boolean hasValid()
    {
        return validCardNum() && validUserName() && validStatementDay() && validValidityDay()
                && validSafeCode() && validCertNo();
    }

    private boolean validCertNo()
    {
        if (TextUtils.isEmpty(getCertNo()))
        {
            UiUtils.shortToast("请输入证件号码");
            return false;
        }
        return true;
    }

    public String getCertNo()
    {
        return etCertNo.getText().toString();
    }

    private boolean validSafeCode()
    {
        if (TextUtils.isEmpty(getSafeCode()))
        {
            UiUtils.shortToast("安全码不能为空");
            return false;
        }
        if (getSafeCode().length() == 3)
        {
            return true;
        } else
        {
            UiUtils.shortToast("安全码为3位数字");
            return false;
        }
    }

    public String getSafeCode()
    {
        return etSafeCode.getText().toString();
    }

    private boolean validValidityDay()
    {
        if (TextUtils.isEmpty(tvValidityDate.getText().toString()))
        {
            UiUtils.shortToast("有效期不能为空");
            return false;
        } else
        {
            return true;
        }
    }

    protected boolean validStatementDay()
    {
//        if (TextUtils.isEmpty(tvStatementDate.getText().toString()))
//        {
//            UiUtils.shortToast("账单日不能为空");
//            return false;
//        } else
//        {
//            return true;
//        }
        return true ;
    }

    protected boolean validUserName()
    {
        if (TextUtils.isEmpty(etName.getText().toString()))
        {
            UiUtils.shortToast("用户名不能为空");
            return false;
        } else
        {
            return true;
        }
    }

    protected boolean validCardNum()
    {
        if (TextUtils.isEmpty(getCardNum()))
        {
            UiUtils.shortToast("卡号不能为空");
            return false;
        }

        if (bankInfo == null || TextUtils.isEmpty(bankInfo.getBankName()))
        {
            UiUtils.shortToast("未检测到银行卡信息");
            return false;
        }

        if (!"2".equals(bankInfo.getType()))
        {
            UiUtils.shortToast("此卡不是信用卡，请输入信用卡卡号！");
            return false;
        }
        return true;
    }

    @Override
    public String getRightText()
    {
        return "下一步";
    }

    @Override
    public String getTitle()
    {
        return "添加信用卡";
    }

    @Override
    public View getView()
    {
        return mRoot;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        scanCardController.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PROTOCOL && resultCode == addCanPayCardActivity.RESULT_OK)
        {
            addCanPayCardActivity.nextStep();
        }
    }

    public String getCardNum()
    {
        return etCardNum.getText().toString().replace(" ", "");
    }

    public String getName()
    {
        return etName.getText().toString().trim();
    }

    public String getStatementDay()
    {
        return tvStatementDate.getText().toString();
    }

    public String getValidityDay()
    {
        return tvValidityDate.getText().toString();
    }

    public String getBankId()
    {
        return bankInfo.getBankId() + "";
    }

    public String getBin()
    {
        return bankInfo.getBin();
    }

    public String getBankName()
    {
        return bankInfo.getBankName();

    }

    @Override
    public void onItemClick(CertType data)
    {
        setCertType(data);
    }

    public void setCertType(CertType certType)
    {
        this.certType = certType;
        if (certType != null)
            tvCertType.setText(certType.getName());
    }

    public String getCertType()
    {
        return certType.getType();
    }

    public String getBankCode()
    {
        return bankInfo.getBankCode();
    }
}
