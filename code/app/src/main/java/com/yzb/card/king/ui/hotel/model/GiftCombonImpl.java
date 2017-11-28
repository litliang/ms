package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.giftcard.GiftCombonRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
public class GiftCombonImpl {

    private DataCallBack dataCallBack;

    public GiftCombonImpl(DataCallBack dataCallBack){
        this.dataCallBack = dataCallBack;
    }

    /**
     * 查询限时抢购详情事件
     * @param actityId
     * @param type
     */
    public void selectFlashsaleInfoAction(long actityId, final int type)
    {

        GiftCombonRequest request =   new GiftCombonRequest(actityId);

        request.setFlashSalseServiceName();

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 查询卡权益详情事件
     */
    public void selectCardRightsInfoAction(long actityId,final  int type ){

        GiftCombonRequest request =   new GiftCombonRequest(actityId);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 查询礼品套餐增值项事件
     * @param actityId
     * @param type
     */
    public void queryGiftsIncrementItemsAction(long actityId,int goodsType,final  int type ){

        GiftCombonRequest request =   new GiftCombonRequest(actityId,goodsType,false);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 查询商品适用门店列表事件
     * @param actityId
     * @param type
     */
    public void queryGoodsApplyStoreAction(long actityId,int goodsType,final  int type ){

        GiftCombonRequest request =   new GiftCombonRequest(actityId,goodsType);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


}
