package com.yzb.card.king.ui.discount.presenter;

import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.bean.common.QrPayBean;
import com.yzb.card.king.bean.order.FastPaymentOrderBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.common.AppTransferForFastpaymentRequest;
import com.yzb.card.king.http.common.CreateFastPaymentOrderRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.AppQuickPreConsumeBean;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.PreferencesService;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/18
 * 描  述：
 */
public class CreateFastPaymentOrderPresenter implements HttpCallBackData {

    private DataCallBack dataCallBack;

    private int type = 1;

    public CreateFastPaymentOrderPresenter(DataCallBack dataCallBack)
    {

        this.dataCallBack = dataCallBack;
    }

    /**
     * 发送创建码尚付订单请求
     * @param qrPayBean
     * @param currentPayMethod
     * @param scanPayMethod
     */
    public void sendOrderRequest(QrPayBean qrPayBean, PayMethod currentPayMethod, PayMethod scanPayMethod)
    {
        type = 2;

        String amount = qrPayBean.getAmount();

        //生成时间
        long currentTime = System.currentTimeMillis();

        String createTime = Utils.toData(currentTime, 16);

        String flag = qrPayBean.getFlag();

        String customerType = qrPayBean.getCustomerType();

        String provideAccount = qrPayBean.getProvideAccount();

       /*
          加密部分
        */
        String payType =  currentPayMethod.getAccountType();

        Map<String, String> map = new HashMap<String, String>();

        map.put("payType", payType);

        map.put("status", AppUtils.ifPlatorm(currentPayMethod)?"1":"0");

        map.put("orderType", GlobalApp.orderType);

        Object jsonObject = JSONObject.toJSON(map);

        String provideParamsdeP = String.valueOf(jsonObject);

        Map<String, String> map2 = new HashMap<String, String>();

        map2.put("flag",  AppConstant.DEBIT.equals(flag)? AppConstant.CREDIT: AppConstant.DEBIT);

        map2.put("consumeAccount", AppConstant.sessionId);

        PreferencesService pre = new PreferencesService(GlobalApp.getInstance().getContext());

        String  sCustomerType = pre.getValue("customerType");

        map2.put("customerType", sCustomerType);

        map2.put("amount", amount);

        map2.put("payType", scanPayMethod.getAccountType());

        map2.put("status", AppUtils.ifPlatorm(scanPayMethod)?"1":"0");

        map2.put("provideAccount", qrPayBean.getProvideAccount());

        Object jsonObject2 = JSONObject.toJSON(map2);

        String consumeParamsdeP = String.valueOf(jsonObject2);

        map2.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

        new CreateFastPaymentOrderRequest(amount, createTime, flag, customerType, provideParamsdeP, consumeParamsdeP,provideAccount).sendRequest(this);

    }

    /**
     * 发送转账请求
     * @param qrPayBean
     * @param appQuickPreConsumeBean
     * @param passwordStr
     * @param currentPayMethod
     * @param scanPayMethod
     * @param fastPaymentOrderBean
     */
    public void sendAppTransferFastpaymentRequest(QrPayBean qrPayBean, AppQuickPreConsumeBean appQuickPreConsumeBean, String passwordStr, PayMethod currentPayMethod, PayMethod scanPayMethod, FastPaymentOrderBean fastPaymentOrderBean)
    {

        type = 3;
        String flag =  qrPayBean.getFlag();
        String debitSessionId = "";
        String creditSessionId = "";
        String status = "";
        String debitType = "";
        String debitSortCode = "";
        LogUtil.e("AAAA","二维码方flag="+flag);
        String tokenStr = null;
        String customerIdStr = null;

        if(appQuickPreConsumeBean !=null){
            tokenStr = appQuickPreConsumeBean.getToken();
            customerIdStr = appQuickPreConsumeBean.getCustomerId();
            LogUtil.e("AAAA","token="+appQuickPreConsumeBean.getToken()+"-----"+appQuickPreConsumeBean.getCustomerId());
        }

        String flagApp = "";

        if( AppConstant.DEBIT.equals(flag)){

            debitSessionId = qrPayBean.getProvideAccount();

            creditSessionId = AppConstant.sessionId;

            status = AppUtils.ifPlatorm(currentPayMethod)?"1":"0";

            debitType = currentPayMethod.getAccountType();

            debitSortCode= currentPayMethod.getSortCode();

            flagApp = AppConstant.CREDIT;

        }else {

            debitSessionId = AppConstant.sessionId;

            creditSessionId =  qrPayBean.getProvideAccount();

            status = AppUtils.ifPlatorm(scanPayMethod)?"1":"0";

            debitType = scanPayMethod.getAccountType();

            debitSortCode= scanPayMethod.getSortCode();

            flagApp = AppConstant.DEBIT;
        }

        String orderNo = fastPaymentOrderBean.getOrderNo();
        String amount = fastPaymentOrderBean.getOrderAmount();
        String identifyingCode = passwordStr;

        new AppTransferForFastpaymentRequest(debitSessionId,creditSessionId,orderNo,amount,status,debitType,debitSortCode,tokenStr,customerIdStr,identifyingCode,flagApp,fastPaymentOrderBean.getNotifyUrl()){

            @Override
            protected int getConnectTimeOut()
            {
                return 50*1000;
            }
        }.sendRequest(this);

    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onSuccess(Object o)
    {

        dataCallBack.requestSuccess(o, type);
    }

    @Override
    public void onFailed(Object o)
    {

        dataCallBack.requestFailedDataCall(o, type);
    }

    @Override
    public void onCancelled(Object o)
    {
        dataCallBack.requestFailedDataCall(o, type);
    }

    @Override
    public void onFinished()
    {

    }
}
