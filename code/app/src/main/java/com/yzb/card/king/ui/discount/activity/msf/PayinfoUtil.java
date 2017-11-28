package com.yzb.card.king.ui.discount.activity.msf;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.bean.common.QrPayBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：付款信息工具
 * 作  者：Li Yubing
 * 日  期：2017/2/10
 * 描  述：
 */
public class PayinfoUtil {
    /**
     *  生成支付需要的信息
     * @param payMethod
     * @param moneyStr
     * @param flag1
     * @param customerType1
     * @param userSessionId1
     * @return
     */
    public static String  createPayGetInfo( PayMethod payMethod,String moneyStr,String flag1,String customerType1,String userSessionId1){

        //不加密部分
        //钱
        String noAmount = moneyStr;
        //生成时间
        long currentTime = System.currentTimeMillis();

        String noCreateTime = Utils.toData(currentTime, 16);

        //收付方向
        String flag = flag1;

        //账户类型
        String customerType = customerType1;

       /*
          加密部分
        */

        String payType = "";


        if (payMethod != null) {

            payType = payMethod.getAccountType();

        }

        Map<String, String> map = new HashMap<String, String>();

        map.put("provideAccount", userSessionId1);

        map.put("payType", payType);

        Object jsonObject = JSONObject.toJSON(map);

        String provideParamsde = String.valueOf(jsonObject);

        //网络情况
        boolean netStatus = ServiceDispatcher.isNetworkConnected(GlobalApp.getInstance().getContext());

        String urlstr = ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER + "amount=" + noAmount + "&createTime=" + noCreateTime + "&flag=" + flag + "&customerType=" + customerType + "&provideParamsde=" + provideParamsde+ "&netStatus=" + netStatus;

        return urlstr;
    }

    /**
     * 解析出支付传递的信息
     * @param qrText
     */
    public static QrPayBean parsingQrBitmap(String qrText){


        if(TextUtils.isEmpty(qrText)){

            return null;
        }

        String url ;

        int index = qrText.indexOf(ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER);

        if(index == -1){

            int indexA = qrText.indexOf(ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER_BUT);

            if(indexA == -1){

                return null;

            }else{

                url = ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER_BUT;
            }

        }else{

            url = ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER;
        }

        QrPayBean bean = new QrPayBean();

        //解析二维码
        String newResultString = qrText.replace(url,"");

        String[]newResultStringArray =  newResultString.split("&");

        int length = newResultStringArray.length;

        if(length==6){

            for(int a = 0 ; a<length;a++){

                String b = newResultStringArray[a].split("=")[1];

                if(a==0){

                    bean.setAmount(b);

                }else if(a==1){
                    bean.setCreateTime(b);

                }else if(a==2){

                    bean.setFlag(b);

                }else if(a==3){

                    bean.setCustomerType(b);

                }else if(a==4){

                    JSONObject jsonObjectB = JSONObject.parseObject(b);

                    String provideAccount =  jsonObjectB.getString("provideAccount");

                    bean.setProvideAccount(provideAccount);

                    String payType =  jsonObjectB.getString("payType");

                    bean.setPayType(payType);

                }else  if( a== 5){

                    boolean netIf = Boolean.parseBoolean(b);

                    bean.setNetStatus(netIf);

                }

            }
        }
        return  bean;
    }



}
