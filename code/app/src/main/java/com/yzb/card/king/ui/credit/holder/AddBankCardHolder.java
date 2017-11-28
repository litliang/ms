package com.yzb.card.king.ui.credit.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.ui.app.popup.IdentificationsPopup;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.interfaces.IAddCardStep;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.credit.popup.SimpleTextPop;
import com.yzb.card.king.ui.credit.watcher.CardTextWatcher;
import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.ui.other.controller.ScanCardController;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/28
 */
public class AddBankCardHolder extends  IAddCardStep implements   OnItemClickListener<CertType>
{
    private static final int REQUEST_PROTOCOL = 2;
    AddBankCardActivity addBankCardActivity;
    private View mRoot;
    private TextView tvName;
    private EditText etCardNum;
    private View ivHelp;
    private View ivCamera;
    private ScanCardController cardController;
    private CardBin cardBin;
    private TextView tvCertType;
    private EditText etCertNo;
    private LinearLayout llIdentification;
    private IdentificationsPopup identificationsPopup;
    private CertType certType;
    private View llNotice;

    public AddBankCardHolder(AddBankCardActivity addBankCardActivity)
    {
        this.addBankCardActivity = addBankCardActivity;
        initView();
        initListener();
        initData();
    }

    private void initListener()
    {
        ivHelp.setOnClickListener(new View.OnClickListener()
        {

            private SimpleTextPop pop;

            @Override
            public void onClick(View v)
            {
                if (pop == null)
                {
                    pop = new SimpleTextPop(addBankCardActivity);
                    pop.setData(UiUtils.getString(R.string.card_user_instruction_title)
                            , UiUtils.getString(R.string.card_user_instruction_content));
                }
                pop.showAtLocation(mRoot, Gravity.CENTER_HORIZONTAL, 0, -UiUtils.dp2px(50));
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cardController.scan();
            }
        });

        CardTextWatcher watcher = new CardTextWatcher(etCardNum);
        watcher.setListener(new BaseCallBack<CardBin>()
        {
            @Override
            public void onSuccess(CardBin data)
            {
                cardBin = data;
                setNoticeVisibility(data);
            }

            @Override
            public void onFail(Error error)
            {
                setNoticeVisibility(null);

            }
        });
        etCardNum.addTextChangedListener(watcher);

        llIdentification.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                identificationsPopup.showAsDropDown(v, -UiUtils.dp2px(16), 5);
            }
        });
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_add_bank_card);
        tvName = (TextView) mRoot.findViewById(R.id.tvName);
        etCardNum = (EditText) mRoot.findViewById(R.id.etCardNum);
        ivHelp = mRoot.findViewById(R.id.ivHelp);
        ivCamera = mRoot.findViewById(R.id.ivCamera);
        llIdentification = (LinearLayout) mRoot.findViewById(R.id.llIdentification);
        etCertNo = (EditText) mRoot.findViewById(R.id.etCertNo);
        tvCertType = (TextView) mRoot.findViewById(R.id.tvCertType);
        llNotice = mRoot.findViewById(R.id.llNotice);
        llNotice.setVisibility(View.GONE);
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

    private void initData()
    {
        etCardNum.setText(addBankCardActivity.getCardNo());
        tvName.setText(addBankCardActivity.getName());
        cardController = new ScanCardController(addBankCardActivity);
        cardController.setCallBack(new BaseCallBack<CardInfo>()
        {
            @Override
            public void onSuccess(CardInfo data)
            {
                etCardNum.setText(data.getFieldString(TFieldID.TBANK_NUM));
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });

        identificationsPopup = new IdentificationsPopup(this);
        identificationsPopup.setWidth(UiUtils.dp2px(93));
        setCertType(identificationsPopup.getDefaultValue());
    }


    @Override
    public boolean rightClick()
    {
        if (valid())
        {
            addBankCardActivity.nextStep();
            addBankCardActivity.getMobileHolder().initData();
        }
        return false;
    }

    private boolean valid()
    {
        return validName() && validCardNum() && validCertNo();
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

    private boolean validCardNum()
    {
        if (TextUtils.isEmpty(getCardNum()))
        {
            UiUtils.shortToast("卡号不能为空！");
            return false;
        }

        if (cardBin == null || TextUtils.isEmpty(cardBin.getBankName()))
        {
            UiUtils.shortToast("未检测到银行卡信息！");
            return false;
        }

        if (!"1".equals(cardBin.getType()))
        {
            UiUtils.shortToast("此卡不是储蓄卡，请输入储蓄卡卡号！");
            return false;
        }
        return true;
    }

    private boolean validName()
    {
        if (TextUtils.isEmpty(getName()))
        {
            UiUtils.shortToast("用户名不能为空");
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
        return "填写卡号";
    }

    @Override
    public View getView()
    {
        return mRoot;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        cardController.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PROTOCOL && resultCode == addBankCardActivity.RESULT_OK)
        {
            addBankCardActivity.nextStep();
        }
    }


    public String getName()
    {
        return tvName.getText().toString();
    }

    public String getCardNum()
    {
        return etCardNum.getText().toString().trim().replace(" ", "");
    }

    public CardBin getCardBin()
    {
        return cardBin;
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
        return cardBin.getBankCode();
    }

    public Long getBankId()
    {
        return cardBin.getBankId();
    }
}
