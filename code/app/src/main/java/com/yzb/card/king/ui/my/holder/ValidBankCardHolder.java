package com.yzb.card.king.ui.my.holder;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.activity.ValidBankCardActivity;
import com.yzb.card.king.ui.app.activity.ValidFinishActivity;
import com.yzb.card.king.ui.app.manager.ValidManager;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.watcher.CardTextWatcher;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.ui.other.controller.ScanCardController;
import com.yzb.card.king.ui.other.controller.ValidCodeController;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/20 19:21
 */
public class ValidBankCardHolder
{
    private View mRoot;
    private Activity activity;
    private View btnSubmit;
    private Map<String, String> data;
    private EditText etCardNum;
    private TextView tvBankName;
    private TextView tvGetValidCode;
    private EditText etValidCode;
    private EditText etMobile;
    private ScanCardController cardController;
    private ValidCodeController validCodeController;
    private CardBin cardBin;
    private View ivCamera;
    private List<String> codeArray;
    private String orderNo;
    private String token;

    public ValidBankCardHolder(ValidBankCardActivity activity)
    {
        this.activity = activity;
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_valid_bank_card);
        btnSubmit = mRoot.findViewById(R.id.btnSubmit);
        etCardNum = (EditText) mRoot.findViewById(R.id.etCardNum);
        tvBankName = (TextView) mRoot.findViewById(R.id.tvBankName);
        etMobile = (EditText) mRoot.findViewById(R.id.etMobile);
        etValidCode = (EditText) mRoot.findViewById(R.id.etValidCode);
        tvGetValidCode = (TextView) mRoot.findViewById(R.id.tvGetValidCode);
        tvBankName = (TextView) mRoot.findViewById(R.id.tvBankName);
        ivCamera = mRoot.findViewById(R.id.ivCamera);
    }

    private void initListener()
    {
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (hasValid())
                {
                    uploadImage();
                }
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

        tvGetValidCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (RegexUtil.validPhoneNum(getMobile()))
                {
                    validCodeController.startTask(tvGetValidCode);
                    createBaseOrder();
                }
            }
        });

        CardTextWatcher watcher = new CardTextWatcher(etCardNum);
        BaseCallBack<CardBin> listener = new BaseCallBack<CardBin>()
        {
            @Override
            public void onSuccess(CardBin data)
            {
                initBankInfo(data);
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());

            }
        };
        watcher.setListener(listener);
        etCardNum.addTextChangedListener(watcher);
    }

    private void initBankInfo(CardBin data)
    {
        this.cardBin = data;
        tvBankName.setText(data.getBankName());
    }

    private String getMobile()
    {
        return etMobile.getText().toString();
    }

    private void uploadImage()
    {
            WaitingDialog.create(activity, "正在上传用户信息");
            getValidManager().setCallBack(new BaseCallBack<List<String>>()
            {
                @Override
                public void onSuccess(List<String> data)
                {
                    codeArray = data;
                    sendRequest();
                }

                @Override
                public void onFail(Error error)
                {
                    WaitingDialog.close();
                    LogUtil.e(error.getError());
                }
            });
            getValidManager().startUpload();
    }


    private ValidManager getValidManager()
    {
        return ValidManager.getValidManager();
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
        param.put("amount", 0.01);//订单金额
        param.put("intro", "验证银行卡");//订单信息
        param.put("orderType", OrderType.VALID_CARD.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>()
        {
            @Override
            public void onSuccess(BaseOrder data)
            {
                orderNo = data.getOrderNo();
                if(validBank())
                {
                    getBankValidCode();
                }
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
        param.putAll(data);
        param.put("mobile", etMobile.getText().toString());
        param.put("bankId", cardBin.getBankId());
        param.put("cardType", "1");//储蓄卡
        param.put("cardNo", etCardNum.getText().toString().replace(" ",""));

        param.put("orderNo", orderNo);
        param.put("amount", 0.01);
        param.put("goodsName", "验证银行卡");
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

    private void sendRequest()
    {
        SimpleRequest request = new SimpleRequest("ApplyAuthentication");
        data.put("idcardCodes", codeArray.get(0) + "," + codeArray.get(1));
        data.put("bankNo", etCardNum.getText().toString());
        data.put("bankName", cardBin.getBankName());
//        data.put("bankCodes", codeArray.get(3));
        data.put("provingMobile", getMobile());
        data.put("identifyingCode", getValidCode());
        request.setParam(data);
        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
                WaitingDialog.close();
                UserManager.getInstance().getUserBean().setAuthenticationStatus("3");
                activity.startActivity(new Intent(activity, ValidFinishActivity.class));
            }

            @Override
            public void onFail(Error error)
            {
                WaitingDialog.close();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private boolean hasValid()
    {
        return validBank() && validCode();
    }

    private boolean validCode()
    {
        if (TextUtils.isEmpty(getValidCode()))
        {
            UiUtils.shortToast("请输入验证码");
            return false;
        } else
        {
            return true;
        }
    }

    private String getValidCode()
    {
        return etValidCode.getText().toString();
    }

    private boolean validBank()
    {
        if (TextUtils.isEmpty(etCardNum.getText().toString()))
        {
            UiUtils.shortToast("请输入卡号");
            return false;
        }

        if (cardBin == null)
        {
            UiUtils.shortToast("没检查到银行卡信息");
            return false;
        } else
        {
            return true;
        }
    }

    private void initData()
    {
        cardController = new ScanCardController(activity);
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
        validCodeController = new ValidCodeController(ValidCodeController.VALID_MOBILE);
    }

    public View getView()
    {
        return mRoot;
    }

    public void setData(Map<String, String> data)
    {
        this.data = data;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        cardController.onActivityResult(requestCode, resultCode, data);
    }
}
