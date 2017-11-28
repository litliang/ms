package com.yzb.card.king.ui.hotel.persenter;

import android.text.TextUtils;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.HotelProductOrderModel;
import com.yzb.card.king.ui.hotel.view.HotelRoomCreateOrderView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public class HotelProductOrderPresenter implements DataCallBack {

    public static final int QUERYROOMSPOLICYSPRICECODE = 9801;

    public static final int CREATEROOMORDERCODE = 9802;

    public static final int QUERYGOODSPLUSC0DE = 9803;

    public static final int SELECTROOMSPOLICYSQUANTITYC0DE = 9804;

    public static final int QUERYROOMSRETENTIONTIMECODE = 9805;

    public static final int RIGHTSORDERCREATECODE = 9806;

    public static final int FLASHSALEORDERCREATECODE = 9807;

    private HotelRoomCreateOrderView view;

    private BaseViewLayerInterface baseViewLayerInterface;

    private HotelProductOrderModel impl;

    public  HotelProductOrderPresenter(HotelRoomCreateOrderView baseViewLayerInterface){

        this.view = baseViewLayerInterface;

        impl = new HotelProductOrderModel(this);

    }

    public  HotelProductOrderPresenter(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        impl = new HotelProductOrderModel(this);

    }

    /**
     * 发送查询房间保留时间请求
     * @param policysId
     * @param arrDate
     */
    public void sendHotelRoomPolicyTimerRequest(String policysId, String arrDate){

        impl.sendHotelRoomPolicysTimeAction(policysId,arrDate,QUERYROOMSRETENTIONTIMECODE);
    }


    /**
     * 发送查询房间套餐剩余数量请求
     * @param policysId
     * @param arrDate
     */
    public void sendHotelRoomPolicyNumberRequest(String policysId, String arrDate){

        impl.sendHotelRoomPolicysNuberAction(policysId,arrDate,SELECTROOMSPOLICYSQUANTITYC0DE);
    }

    /**
     * 发送查询房间预订价格
     * @param policysId
     * @param arrDate
     * @param depDate
     */
    public void sendHotelRoomPolicyRequest(String policysId, String arrDate,String depDate,int goodsQuantity){

        impl.sendHotelRoomPolicysPriceAction(policysId,arrDate,depDate,goodsQuantity,QUERYROOMSPOLICYSPRICECODE);
    }

    /**
     * 发送创建房间订单请求
     */
    public  void sendCreateRoomOrderRequest(){

        impl.createRoomOrderAction(view.collectHotelRoomOrderParam(),CREATEROOMORDERCODE);
    }

    /**
     * 发送创建其它产品订单请求
     */
    public void sendCreateOtherGoodsOrderRequest()
    {

        impl.createOtherGoodsOrderAction(view.collectHotelRoomOrderParam(),CREATEROOMORDERCODE);
    }

    /**
     * 查询超值加购请求
     * @param industryId
     */
    public void queryGoodsPlusRequest(String industryId){

        impl.queryGoodsPlusAction(industryId,QUERYGOODSPLUSC0DE);

    }

    /**
     * 发送卡权益下单请求
     */
    public void sendRightsOrderCreateRequest(){

        impl.rightsOrderCreateAction(view.collectHotelRoomOrderParam(),RIGHTSORDERCREATECODE);
    }

    /**
     * 发送限时抢购下单请求
     */
    public void sendFlashSaleOrderCreateRequest(){

        impl.flashSaleCreateAction(view.collectHotelRoomOrderParam(),FLASHSALEORDERCREATECODE);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if(view != null){
            view.callSuccessViewLogic(o,type);
        }


        if(baseViewLayerInterface != null){

            baseViewLayerInterface.callSuccessViewLogic(o,type);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if(view!= null){
            view.callFailedViewLogic(o,type);
        }
        if(baseViewLayerInterface != null){

            baseViewLayerInterface.callFailedViewLogic(o,type);
        }
    }

    public List<PassengerInfoBean> getEtNoEmptyBeans()
    {
        List<PassengerInfoBean> nowList = view.getPassengerList();
        List<PassengerInfoBean> targetList = new ArrayList<>();
        for (int i = 0; i < nowList.size(); i++)
        {
            if (!TextUtils.isEmpty(nowList.get(i).getZhName()))
            {
                targetList.add(nowList.get(i));
            }
        }
        return targetList;
    }

    public boolean hasPassengerAdded(PassengerInfoBean passenger)
    {
        String targetId;
        List<PassengerInfoBean> passengerList = view.getPassengerList();
        for (int i = 0; i < passengerList.size(); i++)
        {
            targetId = passengerList.get(i).getId();
            if (!TextUtils.isEmpty(targetId) && targetId.equals(passenger.getId()))
            {
                return true;
            }
        }
        return false;
    }

    public List<PassengerInfoBean> addPassengerToList(PassengerInfoBean passenger)
    {
        List<PassengerInfoBean> noEmptyBeans = getEtNoEmptyBeans();
        noEmptyBeans.add(passenger);
        int newSize = noEmptyBeans.size();
        //补全剩余空白的；
        for (int i = 0; i < view.getRoomCount() - newSize; i++)
        {
            noEmptyBeans.add(new PassengerInfoBean());
        }
        return noEmptyBeans;
    }

    public List<PassengerInfoBean> updatePassengerList()
    {
        List<PassengerInfoBean> noEmptyBeans = getEtNoEmptyBeans();
        List<PassengerInfoBean> targetBeans = null;
        int noEmptySize = noEmptyBeans.size();//不为空的数量；
        int roomCount = view.getRoomCount();

        //大于房间数量要裁剪；
        if (noEmptySize >= roomCount)
        {
            targetBeans = noEmptyBeans.subList(0, roomCount);
        } else if (noEmptySize < roomCount) //小于房间数量要添加额外的房间数；
        {
            //补全剩余的；
            for (int i = 0; i < roomCount - noEmptySize; i++)
            {
                noEmptyBeans.add(new PassengerInfoBean());
            }
            targetBeans = noEmptyBeans;
        }
        return targetBeans;
    }

}
