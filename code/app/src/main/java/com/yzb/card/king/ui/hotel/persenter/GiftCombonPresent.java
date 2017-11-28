package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.GiftCombonImpl;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
public class GiftCombonPresent implements DataCallBack {

    public static final  int SELECTCARDRIGHTSINFO_CODE = 9900;

    public static final int QUERYGIFTSINCREMENTITEMS_CODE = 9901;

    public static final int QUERYGOODSAPPLYSTOR_CODE = 9002;

    public static final int SELECTFLASHSALEINFO_CODE = 9003;

    private BaseViewLayerInterface baseViewLayerInterface;

    private GiftCombonImpl giftCombon;

    public GiftCombonPresent(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        giftCombon = new GiftCombonImpl(this);

    }

    /**
     * 发送查询卡权益详情请求
     * @param actId
     */
    public void sendSelectCardRightsInfoRequest( long actId){

        giftCombon.selectCardRightsInfoAction(actId,SELECTCARDRIGHTSINFO_CODE);

    }
    /**
     * 发送查询限时抢购详情请求
     * @param actId
     */
    public void sendSelectFlashsaleInfoRequest( long actId){

        giftCombon.selectFlashsaleInfoAction(actId,SELECTFLASHSALEINFO_CODE);

    }
    /**
     * 发送查询礼品套餐增值项
     * @param actId
     */
    public  void sendQueryGiftsIncrementItemsRequest(long actId,int goodsType){

        giftCombon.queryGiftsIncrementItemsAction(actId,goodsType,QUERYGIFTSINCREMENTITEMS_CODE);

    }

    /**
     * 发送查询商品适用门店列表请求
     * @param actId
     * @param goodsType
     */
    public void sendQueryGoodsApplyStoreRequest(long actId, int goodsType){

        giftCombon.queryGoodsApplyStoreAction(actId,goodsType,QUERYGOODSAPPLYSTOR_CODE);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {

        baseViewLayerInterface.callSuccessViewLogic(o,type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        baseViewLayerInterface.callFailedViewLogic(o,type);
    }
}
