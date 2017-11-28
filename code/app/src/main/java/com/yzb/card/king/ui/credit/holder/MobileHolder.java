package com.yzb.card.king.ui.credit.holder;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.interfaces.IAddCardStep;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/23
 */
public class MobileHolder extends IAddCardStep
{
    private View mRoot;
    private EditText etMobile;
    private TextView tvGetValidCode;
    private ImageView ivClear;
    private EditText etValidNum;
    AddCanPayCardActivity addCanPayCardActivity;
    private ValidCodeController codeController;
    private String token;
    private float amount = 0.01f;
    private String goodsName = "验证银行卡";
    private String orderNo;

    public MobileHolder(AddCanPayCardActivity addCanPayCardActivity)
    {
        this.addCanPayCardActivity = addCanPayCardActivity;
        initView();
        initListener();
        initData();
    }

    private void initData()
    {
        codeController = new ValidCodeController(ValidCodeController.BIND_CARD);
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
                if (RegexUtil.validPhoneNum(getEditMobile()))
                {
                    getCode();
                    codeController.startTask(tvGetValidCode);
                }
            }
        });
    }

    @Override
    public void onStart()
    {
        etValidNum.setFocusable(true);
        etValidNum.setFocusableInTouchMode(true);
        etValidNum.requestFocus();
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
        SimpleRequest<Map<String,String>> request = new SimpleRequest<Map<String,String>>(CardConstant.QUICK_PREAUTH){
            @Override
            protected Map<String,String> parseData(String data)
            {
                return JSON.parseObject(data,Map.class);
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("mobile", getEditMobile());
        param.put("name", addCanPayCardActivity.getCardHolder().getName());
        param.put("bankId", addCanPayCardActivity.getCardHolder().getBankId());
        param.put("certType", addCanPayCardActivity.getCardHolder().getCertType());
        param.put("certNo", addCanPayCardActivity.getCardHolder().getCertNo());
        param.put("cardType", WalletConstant.CREDIT_CARD);
        param.put("cardNo", addCanPayCardActivity.getCardHolder().getCardNum());
        param.put("cvv2", addCanPayCardActivity.getCardHolder().getSafeCode());
        param.put("validThru", getValidThru(addCanPayCardActivity.getCardHolder().getValidityDay()));

        param.put("orderNo",orderNo);
        param.put("amount",amount);
        param.put("goodsName", goodsName);

        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<Map<String,String>>()
        {
            @Override
            public void onSuccess(Map<String,String> data)
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

    private String getValidThru(String validityDay)
    {
        if(!TextUtils.isEmpty(validityDay)&&validityDay.length()>=6){
            return validityDay.replace("/","").substring(2);
        }
        return "";
    }

    private String getEditMobile()
    {
        return etMobile.getText().toString();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_mobile);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRoot.setLayoutParams(params);
        etMobile = (EditText) mRoot.findViewById(R.id.etMobile);
        etValidNum = (EditText) mRoot.findViewById(R.id.etValidNum);
        ivClear = (ImageView) mRoot.findViewById(R.id.ivClear);
        tvGetValidCode = (TextView) mRoot.findViewById(R.id.tvGetValidCode);

    }

    @Override
    public boolean rightClick()
    {
        validData();
        return true;
    }

    private void validData()
    {
        if (!TextUtils.isEmpty(getValidCode()))
        {
            addCreditCard();
        } else
        {
            UiUtils.shortToast("请输入验证码");
        }
    }

    private void addCreditCard()
    {
        WaitingDialog.create(addCanPayCardActivity,"");
        Map<String, Object> param = addCanPayCardActivity.getCardInfo();
        param.put("mobile", etMobile.getText().toString());
        param.put("verifyCode", getValidCode());
        param.put("orderNo",orderNo);

        if (!"spdb".equals(addCanPayCardActivity.getCardHolder().getBankCode()))
        {//他行需通過支付來驗證
            param.put("token",token);
            param.put("amount",amount);
            param.put("goodsName",goodsName);
            param.put("transIp", AppUtils.getLocalIpAddress(addCanPayCardActivity));
        }

        SimpleRequest request = new SimpleRequest(CardConstant.CREATE_CREDIT_CARD);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
                WaitingDialog.close();
                addCanPayCardActivity.nextStep();
            }

            @Override
            public void onFail(Error error)
            {
                WaitingDialog.close();
                UiUtils.shortToast("添加银行卡失败，错误信息：" + error.getError());
            }
        });
    }

    @NonNull
    private String getValidCode()
    {
        return etValidNum.getText().toString();
    }

    @Override
    public String getRightText()
    {
        return "下一步";
    }

    @Override
    public String getTitle()
    {
        return "卡片验证";
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
