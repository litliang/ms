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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.interfaces.IAddCardStep;
import com.yzb.card.king.ui.credit.popup.SimpleListPop;
import com.yzb.card.king.ui.credit.popup.SimpleTextPop;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/28
 */
public class BankMobileHolder extends IAddCardStep
{
    AddBankCardActivity addBankCardActivity;
    private View mRoot;
    private TextView tvCardType;
    private EditText etMobile;
    private ImageView ivHelp;
    private TextView tvProtocol;
    private SimpleListPop protocolPop;
    private ImageView ivClear;
    public CardBin bankInfo;

    public BankMobileHolder(AddBankCardActivity addBankCardActivity)
    {
        this.addBankCardActivity = addBankCardActivity;
        initView();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_bank_mobile);
        tvCardType = (TextView) mRoot.findViewById(R.id.tvCardType);
        etMobile = (EditText) mRoot.findViewById(R.id.etMobile);
        ivHelp = (ImageView) mRoot.findViewById(R.id.ivHelp);
        ivClear = (ImageView) mRoot.findViewById(R.id.ivClear);
        ivClear.setVisibility(View.GONE);
        tvProtocol = (TextView) mRoot.findViewById(R.id.tvProtocol);
        setProtocolText();
        initListener();
    }

    @Override
    public void onStart()
    {
        etMobile.setFocusable(true);
        etMobile.setFocusableInTouchMode(true);
        boolean focus = etMobile.requestFocus();
        LogUtil.e("focus:"+focus);
    }

    private void initListener()
    {
        etMobile.addTextChangedListener(new MyTextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() > 0)
                {
                    showClose();
                } else
                {
                    showHelp();
                }
            }
        });

        ivHelp.setOnClickListener(new View.OnClickListener()
        {
            public SimpleTextPop pop;

            @Override
            public void onClick(View v)
            {
                if (pop == null)
                {
                    pop = new SimpleTextPop(addBankCardActivity);
                    pop.setData(UiUtils.getString(R.string.card_phone_instruction_title)
                            , UiUtils.getString(R.string.card_phone_instruction_content));
                }
                pop.showAtLocation(mRoot, Gravity.CENTER_HORIZONTAL, 0, -UiUtils.dp2px(50));
            }
        });

        ivClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                etMobile.setText("");
            }
        });
    }

    private void showClose()
    {
        ivClear.setVisibility(View.VISIBLE);
        ivHelp.setVisibility(View.GONE);
    }

    private void showHelp()
    {
        ivClear.setVisibility(View.GONE);
        ivHelp.setVisibility(View.VISIBLE);
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
                ds.setColor(UiUtils.getColor(R.color.color_blue_5a7d96));
              //  ds.bgColor = Color.WHITE;
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
        protocolPop = new SimpleListPop(addBankCardActivity)
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(addBankCardActivity, WebViewClientActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(WebViewClientActivity.CATEGORY,getCategory(position));
                intent.putExtra(AppConstant.INTENT_BUNDLE,bundle);
                addBankCardActivity.startActivity(intent);
            }

            private String getCategory(int position)
            {
                if (position == 0)
                {
                    return "24";
                } else if (position == 1)
                {
                    return "25";
                }else {
                    return "";
                }
            }
        };
        protocolPop.setDataList(protocolList);
        protocolPop.setGravity(Gravity.CENTER_VERTICAL);
        protocolPop.setTitle("阅读协议");
    }

    public void initData()
    {
        SimpleRequest<CardBin> request = new SimpleRequest<CardBin>(CardConstant.QUERY_CARDBIN)
        {
            @Override
            protected CardBin parseData(String data)
            {
                return JSON.parseObject(data, CardBin.class);
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("cardNo", addBankCardActivity.getCardNum());
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<CardBin>()
        {

            @Override
            public void onSuccess(CardBin data)
            {
                bankInfo = data;
                tvCardType.setText(data.getArchivesName());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    @Override
    public boolean rightClick()
    {
        if (hasValid())
        {
            addBankCardActivity.nextStep();
        }
        return false;
    }

    private boolean hasValid()
    {
        return RegexUtil.validPhoneNum(getMobile()) && validCardType();
    }

    private boolean validCardType()
    {
        if (bankInfo == null || TextUtils.isEmpty(bankInfo.getArchivesName()))
        {
            UiUtils.shortToast("未检测到卡片类型");
            return false;
        } else
        {
            return true;
        }
    }

    public String getMobile()
    {
        return etMobile.getText().toString();
    }

    @Override
    public String getRightText()
    {
        return "下一步";
    }

    @Override
    public String getTitle()
    {
        return "填写手机号";
    }

    @Override
    public View getView()
    {
        return mRoot;
    }

    @Override
    public void onActivityResult(int requestCode, int requestCode1, Intent data)
    {

    }

    public String getBin()
    {
        return bankInfo.getBin();
    }

    public String getBankName()
    {
        return bankInfo.getBankName();
    }

    public String getBankId()
    {
        return bankInfo.getBankId()+"";

    }
}
