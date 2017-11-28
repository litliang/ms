package com.yzb.card.king.ui.credit.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.creditcard.QueryCreditCardBillRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.presenter.CreditIndexCallBack;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 类  名：首页信用卡接口
 * 作  者：Li Yubing
 * 日  期：2016/8/16
 * 描  述：
 */
public class CreditIndexDao  implements  CreditIndexImpl{

    private CreditIndexCallBack callBack;

    public CreditIndexDao(CreditIndexCallBack callBack){

        this.callBack = callBack;

    }

    @Override
    public void sendCreditBillRequest() {

        new QueryCreditCardBillRequest().sendRequest(new HttpCallBackData(){
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                String data = o + "";
                //处理返回数据
                List<CreditCard> busShopList = JSON.parseArray(data, CreditCard.class);

                callBack.requestSuccess(busShopList,1);


            }

            @Override
            public void onFailed(Object o) {

                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));

                }

                callBack.requestFailedDataCall(null,1);
            }

            @Override
            public void onCancelled(Object o) {

                callBack.requestFailedDataCall(null,1);

            }

            @Override
            public void onFinished() {


            }
        });
    }

}
