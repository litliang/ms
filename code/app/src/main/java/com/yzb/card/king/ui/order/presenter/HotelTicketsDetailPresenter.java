package com.yzb.card.king.ui.order.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.order.model.HotelTicketDetailDAO;
import com.yzb.card.king.ui.order.model.HotelTicketDetailImpl;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * Created by sushuiku on 2016/11/5.
 */

public class HotelTicketsDetailPresenter implements DataCallBack {

    private BaseViewLayerInterface view;

    private HotelTicketDetailImpl hotelTicketDetailDAO;

    public HotelTicketsDetailPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        hotelTicketDetailDAO = new HotelTicketDetailImpl(this);

    }

    public void sendsRequest(String serviceName, Map<String, Object> paramMap)
    {

        hotelTicketDetailDAO.setType(0);

        hotelTicketDetailDAO.sendRequst(serviceName, paramMap);
    }

    /**
     * 申请返现
     * @param serviceName
     * @param paramMap
     */
    public void sendsRequestOne(String serviceName, Map<String, Object> paramMap)
    {

        hotelTicketDetailDAO.setType(1);

        hotelTicketDetailDAO.sendRequst(serviceName, paramMap);
    }

    /**
     * 退款检测
     * @param serviceName
     * @param paramMap
     */
    public void refundMoneyCheck(String serviceName, Map<String, Object> paramMap){
        hotelTicketDetailDAO.setType(2);

        hotelTicketDetailDAO.sendRequst(serviceName, paramMap);
    }

    /**
     * 确认退款
     * @param serviceName
     * @param paramMap
     */
    public void confirmRefundMoney(String serviceName, Map<String, Object> paramMap){

        hotelTicketDetailDAO.setType(3);

        hotelTicketDetailDAO.sendRequst(serviceName, paramMap);
    }

    /**
     * 删除订单
     * @param serviceName
     * @param paramMap
     */
    public void delOrderRequest(String serviceName, Map<String, Object> paramMap){

        hotelTicketDetailDAO.setType(400);

        hotelTicketDetailDAO.sendRequst(serviceName, paramMap);
    }

    /**
     * 发送补开发票请求
     * @param serviceName
     * @param paramMap
     */
    public void sendSupplementOrderInvoiceRequest(String serviceName, Map<String, Object> paramMap){

        hotelTicketDetailDAO.setType(500);

        hotelTicketDetailDAO.sendRequst(serviceName, paramMap);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        view.callSuccessViewLogic(o, type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        view.callFailedViewLogic(o,type);
    }
}
