package com.yzb.card.king.ui.order.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.order.model.OrderManagerListsImpl;
import com.yzb.card.king.ui.order.view.OrderManagerListsView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： OrderListsPresenter
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述：
 */
public class OrderListsPresenter implements DataCallBack
{

    private OrderManagerListsView view;
    private OrderManagerListsImpl iOrderManagerLists;

    /**
     * 请求页数编号
     */
    private int requestPageIndex = 0;


    public OrderListsPresenter(OrderManagerListsView view)
    {
        this.view = view;

        this.iOrderManagerLists = new OrderManagerListsImpl();

        this.iOrderManagerLists.setOnDataLoadFinish(this);
    }


    /**
     * 根据行业和订单状态发送订单请求
     *
     * @param pageStart
     * @param orderType  0全部；1火车票；2机票；3船票；4汽车票；5叫车；6旅游；7酒店;8礼品卡；9红包
     * @param orderStatus
     */
    public void sendOrderInforRequestByInfo(int pageStart, String orderType, String orderStatus)
    {
        requestPageIndex = pageStart;

        Map<String, Object> args = new HashMap<String, Object>();

        args.put("orderType", orderType);

        if (orderStatus != null)
        {
            args.put("orderStatus", orderStatus);
        }
        args.put("pageStart", pageStart + "");
        args.put("pageSize", AppConstant.MAX_PAGE_NUM + "");
        iOrderManagerLists.sendOrderListsRequest(args, CardConstant.ORDER_LIST);
    }

    /**
     * 发送待使用订单列表请求
     */
    public void sendSoonOrdersManageListRequest(){

        requestPageIndex = 0;

        Map<String, Object> args = new HashMap<String, Object>();

        iOrderManagerLists.sendOrderListsRequest(args, CardConstant.ORDER_SOONORDERSMANAGELIST);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        List<OrderBean> list = JSON.parseArray(String.valueOf(o), OrderBean.class);

        if (requestPageIndex == 0)
        {//刷新加载数据

            view.getOrderLists(list);

        } else
        {//加载更多数据

            view.getMoreDataList(list);

        }

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        view.onError(o);
    }


    /**
     * 获取订单总金额；
     *
     * @param bean
     * @return
     */
    public String getOrderAmount(OrderBean bean)
    {
        //格式：1760.0,2560.0
        String fareInforses = bean.getFareInforses();
        if (!TextUtils.isEmpty(fareInforses))
        {
            String[] dataArray = fareInforses.split(",");
            if (dataArray != null)
            {
                float sum = 0f;
                for (int i = 0; i < dataArray.length; i++)
                {
                    if (!TextUtils.isEmpty(dataArray[i]))
                    {
                        try
                        {
                            sum += Float.parseFloat(dataArray[i]);
                        } catch (Exception e)
                        {
                            continue;
                        }
                    }
                }
                return sum + "";
            }
        }

        return "";
    }

    /**
     * 获取行业id，特惠付款时用到；
     * 主要解决设计时，bean中的orderType字段值和大分类id不一致问题；
     *
     * @param bean 订单类型(1火车票；2机票；3船票；4汽车票；5叫车；6旅游；7酒店)
     * @return 处理过的行业id；
     */
    public String getIndustryIds(OrderBean bean)
    {
        String industryIds = "";
        int orderType = bean.getOrderType();
        if (orderType == 1 || orderType == 3 || orderType == 4 || orderType == 5)
        {
            industryIds = AppConstant.transport_id;
        } else if (orderType == 2)
        {
            industryIds = AppConstant.ticket_id;
        } else if (orderType == 6)
        {
            industryIds = AppConstant.travel_id;
        } else if (orderType == 7)
        {
            industryIds = AppConstant.hotel_id;
        }
        return industryIds;
    }

    /**
     * 获取商品code码，特惠付款时用到；
     */
    public String getGoodsIds(OrderBean bean)
    {
        String goodsIds = "";
        int orderType = bean.getOrderType();
        if (orderType == 1 || orderType == 3 || orderType == 4 || orderType == 5)
        {
            goodsIds = "";

        } else if (orderType == OrderBean.ORDER_TYPE_AIRCRAFT)
        {
            goodsIds = AppConstant.discount_code_ticket;

        } else if (orderType == OrderBean.ORDER_TYPE_TOUR)
        {
            goodsIds = AppConstant.discount_code_travel;

        } else if (orderType == OrderBean.ORDER_TYPE_HOTELS)
        {
            goodsIds = bean.getHotelInfo().getGoodsId()+"";
        }
        return goodsIds;
    }
    /**
     *
     */
    public String getGoodIds(OrderBean bean)
    {
        String goodsIds = "";
        int orderType = bean.getOrderType();
        if (orderType == 1 || orderType == 3 || orderType == 4 || orderType == 5)
        {
            goodsIds = "";

        } else if (orderType == OrderBean.ORDER_TYPE_AIRCRAFT)
        {
            goodsIds = bean.getGoodIds();

        } else if (orderType == OrderBean.ORDER_TYPE_TOUR)
        {
            goodsIds =bean.getTravelInfo().getGoodsIds();

        } else if (orderType == OrderBean.ORDER_TYPE_HOTELS)
        {
            goodsIds = bean.getHotelInfo().getGoodsId()+"";
        }
        return goodsIds;
    }
}