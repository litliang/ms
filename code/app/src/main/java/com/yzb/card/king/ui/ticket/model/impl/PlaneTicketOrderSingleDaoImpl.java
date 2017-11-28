package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.discount.bean.Insurance;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.ticket.BxListRequest;
import com.yzb.card.king.http.ticket.GetDefaultAddressRequest;
import com.yzb.card.king.http.ticket.LoadDebitRiseListRequest;
import com.yzb.card.king.http.ticket.PlaneTicketRoleRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.ticket.model.IPlaneTicketOrderSingle;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/5
 * 描  述：
 */
public class PlaneTicketOrderSingleDaoImpl implements IPlaneTicketOrderSingle {

    private DataCallBack dataCallBack;

    public PlaneTicketOrderSingleDaoImpl(DataCallBack dataCallBack){
        this.dataCallBack=dataCallBack;
    }

    /**
     * 保险列表
     * @param params
     */
    @Override
    public void getBxList(Map<String, Object> params)
    {
        new BxListRequest(params).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<Insurance> list = JSON.parseArray(String.valueOf(o), Insurance.class);
                if (null == list || list.isEmpty())
                    return;
                dataCallBack.requestSuccess(list,IPlaneTicketOrderSingle.BXLIST_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                   // ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
                dataCallBack.requestFailedDataCall(null,IPlaneTicketOrderSingle.BXLIST_CODE);
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
    }

    /**
     * 退改签规则
     */
    @Override
    public void getRoleTicket()
    {
        new PlaneTicketRoleRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o,IPlaneTicketOrderSingle.ROLETICKET_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(),
                            onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
                dataCallBack.requestFailedDataCall(null,IPlaneTicketOrderSingle.ROLETICKET_CODE);
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
    }

    /**
     * 默认地址
     */
    @Override
    public void getDefaultAddress()
    {
        new GetDefaultAddressRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<GoodsAddressBean> list = JSON.parseArray(String.valueOf(o), GoodsAddressBean.class);
               dataCallBack.requestSuccess(list,IPlaneTicketOrderSingle.DEFAULTADDRESS_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

//                    ToastUtil.i(GlobalApp.getInstance().getContext(),
//                            onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
                dataCallBack.requestFailedDataCall(null,IPlaneTicketOrderSingle.DEFAULTADDRESS_CODE);
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
    }

    @Override
    public void getDefaultDebit()
    {
        new LoadDebitRiseListRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<DebitRiseBean> data=JSON.parseArray(String.valueOf(o), DebitRiseBean.class);
                dataCallBack.requestSuccess(data,IPlaneTicketOrderSingle.DEFAULTDEBIT_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(null,IPlaneTicketOrderSingle.DEFAULTDEBIT_CODE);
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
    }
}
