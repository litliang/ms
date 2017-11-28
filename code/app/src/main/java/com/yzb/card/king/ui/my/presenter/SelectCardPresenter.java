package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.common.AppPayMethodRequest;
import com.yzb.card.king.http.DataCallBack;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/18
 * 描  述：
 */
public class SelectCardPresenter implements HttpCallBackData {

    private DataCallBack callBack;

    private int type = 1;

    public SelectCardPresenter(DataCallBack callBack)
    {

        this.callBack = callBack;
    }

    /**
     * @param flag
     */
    public void sendReqeust(boolean flag)
    {

        type = 1;

        if (flag) {//商户类型相同只有 储蓄卡、钱包

            new AppPayMethodRequest(null, "1", "0", "0", "0", "1", "1", null).sendRequest(this);

        } else {
            new AppPayMethodRequest(null, "1", "1", "1", "1", null, "1", null).sendRequest(this);
        }

    }

    /**
     * 查询出用户的钱包账户、储蓄卡账户、信用卡账户
     */
    public void sendMethodReqeust()
    {

        type = 1;

        new AppPayMethodRequest(null, "1", "0", "0", "0", null, "1", null).sendRequest(this);

    }


    /**
     * 发起查询指定用户的付款方式请求
     *
     * @param userSesionId
     */
    public void querySpecifiedUserSelectCardRequest(String userSesionId)
    {

        type = 1001;
        new AppPayMethodRequest(userSesionId).sendRequest(this);

    }

    /**
     * 发送使用者的付款方式请求
     *
     * @param userSesionId
     */
    public void sendUserPaymethod(String userSesionId)
    {

        type = 1;
        new AppPayMethodRequest(userSesionId).sendRequest(this);

    }

    /**
     *
     * @param cardType  1借记卡 2信用卡
     * @param payType   1可支付 0不可支付
     * @param limitDay  1是 0否
     * @param orderId
     * @param goldTicketParam
     * @param goodsAmount
     */
    public void sendUserPaymethodRequest(int cardType, int payType, long limitDay,long orderId, GoldTicketParam goldTicketParam, double goodsAmount){

        type = 1002;

        new AppPayMethodRequest(cardType,payType,limitDay,orderId,goldTicketParam,goodsAmount).sendRequest(this);
    }


    @Override
    public void onStart()
    {

    }

    @Override
    public void onSuccess(Object o)
    {

        callBack.requestSuccess(o, type);
    }

    @Override
    public void onFailed(Object o)
    {

        callBack.requestFailedDataCall(o,type);
    }

    @Override
    public void onCancelled(Object o)
    {

    }

    @Override
    public void onFinished()
    {

    }
}
