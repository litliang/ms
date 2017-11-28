package com.yzb.card.king.ui.credit.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.interfaces.IAddCardStep;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/29
 */

public class ValidBankMobile extends IAddCardStep
{
    AddBankCardActivity addBankCardActivity;
    private View mRoot;
    private TextView tvAlert;
    private EditText etValidNum;
    private ImageView ivClear;
    private TextView tvGetValidCode;
    private ValidCodeController codeController;
    private float amount = 0.01f;
    private String goodsName = "验证卡片";
    private String orderNo;
    private String token;

    public ValidBankMobile(AddBankCardActivity addBankCardActivity)
    {
        this.addBankCardActivity = addBankCardActivity;
        initView();
        initListener();
        initData();
    }

    public void initData()
    {
        tvAlert.setText(UiUtils.getString(R.string.card_input_mobile_valid_code, addBankCardActivity.getEditMobile()));
        codeController = new ValidCodeController(ValidCodeController.BIND_CARD);
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.layout_holder_valid_bank_mobile);
        tvAlert = (TextView) mRoot.findViewById(R.id.tvAlert);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRoot.setLayoutParams(params);
        etValidNum = (EditText) mRoot.findViewById(R.id.etValidNum);
        ivClear = (ImageView) mRoot.findViewById(R.id.ivClear);
        tvGetValidCode = (TextView) mRoot.findViewById(R.id.tvGetValidCode);

    }

    @Override
    public void onStart()
    {
        etValidNum.setFocusable(true);
        etValidNum.setFocusableInTouchMode(true);
        boolean focus = etValidNum.requestFocus();
        LogUtil.e("focus:" + focus);
    }

    private void initListener()
    {
        ivClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                etValidNum.setText("");
            }
        });
        tvGetValidCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (RegexUtil.validPhoneNum(addBankCardActivity.getEditMobile()))
                {
                    getCode();
                    codeController.startTask(tvGetValidCode);
                }
            }
        });
    }

    private void getCode()
    {
        createBaseOrder();
    }

    private void createBaseOrder()
    {
        SimpleRequest<BaseOrder> request = new SimpleRequest<BaseOrder>(CardConstant.CREATE_BASE_ORDERS)
        {
            @Override
            protected BaseOrder parseData(String data)
            {
                return JSON.parseObject(data, BaseOrder.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("creditId", UserManager.getInstance().getUserBean().getAccount());//收款方id
        param.put("amount", amount);//订单金额
        param.put("intro", goodsName);//订单信息
        param.put("orderType", OrderType.VALID_CARD.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>()
        {
            @Override
            public void onSuccess(BaseOrder data)
            {
                orderNo = data.getOrderNo();
                getBankValidCode();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void getBankValidCode()
    {
        SimpleRequest<Map<String, String>> request = new SimpleRequest<Map<String, String>>(CardConstant.QUICK_PREAUTH)
        {
            @Override
            protected Map<String, String> parseData(String data)
            {
                return JSON.parseObject(data, Map.class);
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("mobile", addBankCardActivity.getEditMobile());
        param.put("name", addBankCardActivity.getUserName());
        param.put("bankId", addBankCardActivity.getBankId());
        param.put("certType", addBankCardActivity.getCertType());
        param.put("certNo", addBankCardActivity.getCertNo());
        param.put("cardType", "1");//储蓄卡
        param.put("cardNo", addBankCardActivity.getCardNum());

        param.put("orderNo", orderNo);
        param.put("amount", amount);
        param.put("goodsName", goodsName);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<Map<String, String>>()
        {
            @Override
            public void onSuccess(Map<String, String> data)
            {
                token = data.get("token");
                UiUtils.shortToast("验证码发送请求成功");
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    public String getValidCode()
    {
        return etValidNum.getText().toString();
    }

    @Override
    public boolean rightClick()
    {
        if (hasValid())
        {
            addCard();
        }
        return false;
    }

    private boolean hasValid()
    {
        if (TextUtils.isEmpty(getValidCode()))
        {
            UiUtils.shortToast("请输入验证码");
            return false;
        }

//        if (TextUtils.isEmpty(token))
//        {
//            UiUtils.shortToast("没有获取到token信息");
//            return false;
//        }
        return true;
    }

    private void addCard()
    {
        WaitingDialog.create(addBankCardActivity, "");
        SimpleRequest request = new SimpleRequest(CardConstant.CREATE_DEBIT_CARD);
        Map<String, Object> param = new HashMap<>();
        param.put("name", addBankCardActivity.getUserName());
        param.put("mobile", addBankCardActivity.getEditMobile());
        param.put("bin", addBankCardActivity.getBin());
        param.put("cardNo", addBankCardActivity.getCardNum());
        param.put("bankId", addBankCardActivity.getBankId());
        param.put("bankName", addBankCardActivity.getBankName());
        param.put("verifyCode", getValidCode());
        param.put("certType", addBankCardActivity.getCertType());
        param.put("certNo", addBankCardActivity.getCertNo());
        param.put("orderNo", orderNo);
        param.put("payType", "1");//可支付
        if (!"spdb".equals(addBankCardActivity.getAddCardHolder().getBankCode()))
        {//他行需通過支付來驗證
            param.put("token", token);
            param.put("amount", amount);
            param.put("goodsName", goodsName);
            param.put("transIp", AppUtils.getLocalIpAddress(addBankCardActivity));
        }
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
                WaitingDialog.close();
                addBankCardActivity.nextStep();
            }

            @Override
            public void onFail(Error error)
            {
                WaitingDialog.close();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    @Override
    public String getRightText()
    {
        return "下一步";
    }

    @Override
    public String getTitle()
    {
        return "手机验证";
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
}
