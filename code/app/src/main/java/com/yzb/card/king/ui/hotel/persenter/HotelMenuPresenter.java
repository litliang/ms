package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.HotelMenuIml;
import com.yzb.card.king.util.LogUtil;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/16
 * 描  述：
 */
public class HotelMenuPresenter implements DataCallBack {

    public static final  int  HOTEL_GOODS_MENU_CODE = 8701;

    public static final  int  HOTEL_GOODS_MENU_POLICY_CODE = 8702;

    private BaseViewLayerInterface baseViewLayerInterface;

    private HotelMenuIml iml;

    public HotelMenuPresenter( BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        iml = new HotelMenuIml(this);
    }

    /**
     * 发送酒店商品菜单请求
     * @param goodsType
     * @param goodsId
     */
    public void sendHotelGoodsMenuRequest(String  goodsType,String goodsId){

        iml.hotelGoodsMenuAction(goodsType,goodsId,HOTEL_GOODS_MENU_CODE);
    }

    /**
     * 发送酒店商品套餐菜单请求
     * @param goodsType
     * @param policysId
     */
    public void sendHotelGoodsPolicysMenuRequest(String  goodsType,String policysId){

        iml.hotelGoodsPolicyMenuAction(goodsType,policysId,HOTEL_GOODS_MENU_POLICY_CODE);
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
