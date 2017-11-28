package com.yzb.card.king.ui.appwidget;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.UserAuthenticationBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.user.IdentifyingCodeRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ValidatorUtil;

import java.util.HashMap;
import java.util.Map;

import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_CREDIT;
import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_DEBIT;

/**
 * 类  名：验证码
 * 作  者：Li Yubing
 * 日  期：2016/8/5
 * 描  述：
 */
public class AuthCodeView {

    private String phoneNum;

    private TextView tvGetCode;

    private int time = 60;

    private Handler dataHandler;
    //是否正在倒计时
    private boolean ifCountDownIng = false;


    public AuthCodeView(TextView tvGetCode)
    {

        this.tvGetCode = tvGetCode;

        time = 60;

    }

    public void setDataHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }

    /**
     * 发送验证码请求
     *
     * @param phoneNum
     * @param custType 1手机号；2:sessionId
     * @param type     类别（1注册；2重置登录密码；3发送新密码；4添加信用卡；5验证手机；6转账验证码）
     */
    public void sendCodeRequest(String phoneNum, String custType, String type)
    {

        this.phoneNum = phoneNum;

        if ("2".equals(custType) || checkPhoneNum()) {

            if (time == 60 && !ifCountDownIng) {

                ifCountDownIng = true;

                IdentifyingCodeRequest request = new IdentifyingCodeRequest(phoneNum, custType, type);
                //当发送转账验证码时，设置为验证码长度为6
                if ("6".equals(type)) {
                    request.setCodeLength("6");
                }
                request.sendRequest(new HttpCallBackData() {
                    @Override
                    public void onStart()
                    {

                    }

                    @Override
                    public void onSuccess(Object o)
                    {

                        String data = o + "";

                        ToastUtil.i(GlobalApp.getInstance().getContext(), data);

                        time = 60;

                        tvGetCode.setBackgroundResource(R.drawable.shape_phone_code_gray);

                        tvGetCode.setTextColor(GlobalApp.getInstance().getResources().getColor(R.color.color_4D4D4D));

                        tvGetCode.setText("已发送 " + time + "s");

                        timeHandler.sendEmptyMessageDelayed(0, 1000);
                    }

                    @Override
                    public void onFailed(Object o)
                    {

                        if (timeHandler != null) {
                            Message message = timeHandler.obtainMessage();

                            message.what = -1;
                            message.obj = o;

                            timeHandler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onCancelled(Object o)
                    {

                    }

                    @Override
                    public void onFinished()
                    {

                    }
                });


            } else {

                ToastUtil.i(GlobalApp.getInstance().getContext(), "已发送短信验证码");
            }
        }

    }

    /**
     * 发送验证码验证请求
     *
     * @param smsCode
     * @param mobile
     * @param custType
     * @param type
     */
    public void sendValidationRequest(String smsCode, String mobile, String custType, String type)
    {
        new IdentifyingCodeRequest(smsCode, mobile, custType, type).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o + "";
                ToastUtil.i(GlobalApp.getInstance().getContext(), data);
                dataHandler.sendEmptyMessage(0);
            }

            @Override
            public void onFailed(Object o)
            {
                if (timeHandler != null) {
                    Message message = timeHandler.obtainMessage();

                    message.what = -1;
                    message.obj = o;

                    timeHandler.sendMessage(message);
                }
            }

            @Override
            public void onCancelled(Object o)
            {
                if (timeHandler != null) {
                    Message message = timeHandler.obtainMessage();

                    message.what = -1;
                    message.obj = o;

                    timeHandler.sendMessage(message);
                }
            }

            @Override
            public void onFinished()
            {

            }
        });

    }

    /**
     * 发送他行银行校验码请求
     *
     * @param sessionId
     * @param sortCode
     * @param amount
     * @param orderNo
     */
    public void sendAppQuickPreConsume(String sessionId, String sortCode, String amount, String orderNo)
    {

        if (time == 60 && !ifCountDownIng) {

            ifCountDownIng = true;

            IdentifyingCodeRequest request = new IdentifyingCodeRequest();

            request.setRequestData(sortCode, amount, orderNo);

            request.sendRequest(new HttpCallBackData() {
                @Override
                public void onStart()
                {

                }

                @Override
                public void onSuccess(Object o)
                {

                    String data = o + "";
                    Message message = dataHandler.obtainMessage();

                    message.what = 2;

                    message.obj = data;

                    dataHandler.sendMessage(message);

                    time = 60;
                    tvGetCode.setBackgroundResource(R.drawable.shape_phone_code_gray);

                    tvGetCode.setTextColor(GlobalApp.getInstance().getResources().getColor(R.color.color_4D4D4D));

                    tvGetCode.setText("已发送 " + time + "s");

                    timeHandler.sendEmptyMessageDelayed(0, 1000);

                }

                @Override
                public void onFailed(Object o)
                {
                    if (timeHandler != null) {
                        Message message = timeHandler.obtainMessage();
                        message.what = -1;
                        message.obj = o;
                        timeHandler.sendMessage(message);
                    }
                }

                @Override
                public void onCancelled(Object o)
                {
                    if (timeHandler != null) {
                        Message message = timeHandler.obtainMessage();

                        message.what = -1;
                        message.obj = o;
                        timeHandler.sendMessage(message);
                    }
                }

                @Override
                public void onFinished()
                {

                }
            });

        } else {

            ToastUtil.i(GlobalApp.getInstance().getContext(), "已发送短信验证码");
        }


    }


    /**
     * 时间句柄
     */
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (time > 0) {

                --time;

                if (time == 0) {
                    tvGetCode.setText(R.string.txt_get_yanzhenma);
                    tvGetCode.setBackgroundResource(R.drawable.shape_rounded_rectangle_red_ea3336);

                    tvGetCode.setTextColor(GlobalApp.getInstance().getResources().getColor(R.color.titleRed));

                    time = 60;

                    ifCountDownIng = false;
                } else {

                    String string = time + "";

                    if (time <= 9) {

                        string = "0" + string;
                    }
                    tvGetCode.setText("已发送 " + string + "s");


                    timeHandler.sendEmptyMessageDelayed(0, 1000);
                }


            } else {

                time = 60;
            }
        }
    };


    /**
     * 检查手机号
     *
     * @return
     */
    private boolean checkPhoneNum()
    {
        boolean flag = true;

        int str = 0;


        String str1 = phoneNum;

        if (TextUtils.isEmpty(str1)) {

            flag = false;

            str = R.string.hint_phone_number;

        } else if (!ValidatorUtil.isMobile(str1)) {

            flag = false;

            str = R.string.toast_chech_your_phone_number;

        }

        if (!flag) {

            ToastUtil.i(GlobalApp.getInstance().getContext(), str);
        }


        return flag;
    }

    private long bankId;
    private String bankCardNo;
    private String orderNo;
    private String certNo;
    private int certType;
    private int cardType;
    private String realName;
    private String validThru;

    private String token;

    private String amount;

    private String cvv2;

    private String bankCode;

    /**
     * 身份绑定 通过绑定银行卡发送验证码,第一步创建一个订单，完成此订单交易，第二步验证验证码
     *
     * @param phoneNumber
     */
    public void sendOtherBankValidCodeRequest(String phoneNumber, long bankId, String bankCardNo, int cardType, int certType, String realName, String validThru, String cvv2, String amount, String bankCodeP, String certNo)
    {

        this.phoneNum = phoneNumber;

        this.bankId = bankId;

        this.bankCardNo = bankCardNo;

        this.cardType = cardType;

        this.certType = certType;

        this.realName = realName;

        this.validThru = validThru;

        this.cvv2 = cvv2;

        this.amount = amount;

        this.certNo = certNo;

        this.bankCode =bankCodeP;

        if (time == 60 && !ifCountDownIng) {

            ifCountDownIng = true;


                SimpleRequest<BaseOrder> request = new SimpleRequest<BaseOrder>(CardConstant.CREATE_BASE_ORDERS) {
                    @Override
                    protected BaseOrder parseData(String data)
                    {
                        return JSON.parseObject(data, BaseOrder.class);
                    }
                };

                Map<String, Object> param = new HashMap<>();
                param.put("creditId", UserManager.getInstance().getUserBean().getAccount());//收款方id
                param.put("amount", amount);//订单金额
                param.put("intro", "验证银行卡");//订单信息
                param.put("orderType", OrderType.VALID_CARD.getValue());//订单类型
                request.setParam(param);
                request.sendRequestNew(new BaseCallBack<BaseOrder>() {
                    @Override
                    public void onSuccess(BaseOrder data)
                    {
                        orderNo = data.getOrderNo();

                        if ("spdb".equals(bankCode)) {//浦发快速授权

                            getBankValidCode();

                        } else {

                            getBankValidCode();

                        }
                    }

                    @Override
                    public void onFail(Error error)
                    {
                        UiUtils.shortToast(error.getError());
                        if (timeHandler != null) {
                            Message message = timeHandler.obtainMessage();

                            message.what = -1;

                            timeHandler.sendMessage(message);
                        }
                    }
                });


        } else {

            ToastUtil.i(GlobalApp.getInstance().getContext(), "已发送短信验证码");
        }
    }

    private void getBankValidCode()
    {
        SimpleRequest<Map<String, String>> request = new SimpleRequest<Map<String, String>>(CardConstant.QUICK_PREAUTH) {
            @Override
            protected Map<String, String> parseData(String data)
            {
                return JSON.parseObject(data, Map.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("mobile", phoneNum);
        param.put("name", realName);
        param.put("certNo", certNo);
        param.put("bankId", bankId);
        param.put("certType", certType);
        param.put("cardNo", bankCardNo.replace(" ", ""));
        param.put("cardType", cardType);

        if (cardType == 2) {
            param.put("validThru", validThru.substring(2).replace("/", ""));//cvv2
            param.put("cvv2", cvv2);
        }
        if (!TextUtils.isEmpty(orderNo)) {
            param.put("orderNo", orderNo);
        }

        param.put("amount", amount);
        param.put("goodsName", "验证银行卡");
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> data)
            {
                if (data.containsKey("token")) {

                    token = data.get("token");
                    UiUtils.shortToast("验证码发送请求成功");

                    time = 60;

                    tvGetCode.setBackgroundResource(R.drawable.shape_phone_code_gray);

                    tvGetCode.setTextColor(GlobalApp.getInstance().getResources().getColor(R.color.color_4D4D4D));

                    tvGetCode.setText("已发送 " + time + "s");

                    timeHandler.sendEmptyMessageDelayed(0, 1000);

                }
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
                if (timeHandler != null) {
                    Message message = timeHandler.obtainMessage();

                    message.what = -1;

                    timeHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 检测手机验证码
     *
     * @param verifyCode
     * @param certNo
     * @param bin
     * @param bankName
     * @param bankCode
     */
    public void checkPhoneCodeRequest(String verifyCode, int cardType, String certNo, String bin, String bankName, String bankCode)
    {

        Map<String, Object> param = new HashMap<>();

        String serverName = "";

        if (cardType == CARD_CREDIT) {//信用卡

            serverName = CardConstant.CREATE_CREDIT_CARD;
            param.put("validityPeriod", validThru.substring(2).replace("/", ""));
            param.put("cvv2", cvv2);

        } else if (cardType == CARD_DEBIT) {//储蓄卡
            serverName = CardConstant.CREATE_DEBIT_CARD;

        }

        param.put("name", realName);
        param.put("mobile", phoneNum);
        param.put("bin", bin);
        param.put("cardNo", bankCardNo.replace(" ", ""));
        param.put("bankId", bankId);
        param.put("bankName", bankName);
        param.put("verifyCode", verifyCode);
        param.put("certType", certType);
        param.put("certNo", certNo);
        param.put("orderNo", orderNo);
        param.put("payType", "1");//可支付

        if (!"spdb".equals(bankCode)) {//他行需通過支付來驗證
            param.put("token", token);
            param.put("amount", "0.01");
            param.put("goodsName", "验证银行卡");
            param.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));
        }

        SimpleRequest request = new SimpleRequest(serverName);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack() {
            @Override
            public void onSuccess(Object data)
            {

                if (dataHandler != null) {

                    dataHandler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onFail(Error error)
            {

                if (dataHandler != null) {

                    dataHandler.sendEmptyMessage(1);
                }
            }
        });
    }


    /**
     * 信用卡快捷支付（一次性使用此卡完成支付）
     * @param userAuthenticationBean
     * @param amount
     * @param fee
     * @param verifyCode
     */
    public void quickPayRequest(UserAuthenticationBean userAuthenticationBean, String amount, String fee, String verifyCode)
    {

        Map<String, Object> param = new HashMap<>();

        String serverName = CardConstant.QUICK_PAY_CARD;
        param.put("mobile", userAuthenticationBean.getMobile());
        param.put("name", userAuthenticationBean.getRealName());
        param.put("certType", userAuthenticationBean.getCertType());
        param.put("certNo", userAuthenticationBean.getCardNo());
        param.put("bankId", userAuthenticationBean.getBankId());
        param.put("cardType", userAuthenticationBean.getCardType());
        param.put("cardNo", userAuthenticationBean.getCardNo());
        param.put("cvv2", userAuthenticationBean.getCvv2());
        param.put("validThru", userAuthenticationBean.getValidityPeriod().substring(2).replace("/", ""));
        param.put("amount", amount);
        param.put("fee", fee);
        param.put("goodsName", "他人代还");

        CreditCard data = userAuthenticationBean.getData();

        param.put("creditCardName", data.getBankName());
        param.put("creditCardNo", data.getCardNo());
        param.put("bankMark", data.getBankMark());

        param.put("verifyCode", verifyCode);

        SimpleRequest request = new SimpleRequest(serverName);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack() {
            @Override
            public void onSuccess(Object data)
            {

                if (dataHandler != null) {

                    dataHandler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onFail(Error error)
            {

                if (dataHandler != null) {

                    dataHandler.sendEmptyMessage(1);
                }
            }
        });
    }


}
