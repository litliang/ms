package com.yzb.card.king.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.ModifyOrderManager;
import com.yzb.card.king.bean.ticket.ReasonForChangeBean;
import com.yzb.card.king.bean.ticket.RescheCondition;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.order.adapter.ReschedueConditionAdapter;
import com.yzb.card.king.ui.order.adapter.ReschedueTicketAdapter;
import com.yzb.card.king.ui.ticket.activity.RescheduleListActivity;
import com.yzb.card.king.ui.ticket.presenter.TicketOrderDetailPresenter;
import com.yzb.card.king.ui.ticket.view.TicketOrderDetailView;
import com.yzb.card.king.util.DateUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机票改签页面
 */
@ContentView(value = R.layout.activity_modify_order)
public class ModifyOrderActivity extends BaseActivity implements TicketOrderDetailView
{
    private static final int REQ_GET_REFUND_REASON = 0x100; // 获取改签原因；
    @ViewInject(value = R.id.orderListView)
    private WholeListView orderListView;
    @ViewInject(value = R.id.conditionListView)
    private WholeListView conditionListView;
    @ViewInject(value = R.id.tv_reason)
    private TextView tv_reason;
    private String orderId; //订单号；
    private ReasonForChangeBean resultBeans; //改签原因；
    private ReschedueTicketAdapter adapter;
    private ReschedueConditionAdapter schedueCondiAdapter; //更改条件；
    private TicketOrderDetailPresenter ticketOrderDetPresenter;
    private TicketOrderDetBean orderDetailBean; //航班列表；
    private OrderBean orderBean; //订单信息bean；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        ticketOrderDetPresenter = new TicketOrderDetailPresenter(this);
        recvIntentData();
        initView();
        initData();
    }

    private void initView()
    {
        adapter = new ReschedueTicketAdapter(this);
        orderListView.setAdapter(adapter);
        schedueCondiAdapter = new ReschedueConditionAdapter(this);
        conditionListView.setAdapter(schedueCondiAdapter);
        schedueCondiAdapter.appendALL(getSchedueData());
    }

    private void initData()
    {
        showPDialog(R.string.loading);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        ticketOrderDetPresenter.loadData(true, params);
    }

    @Override
    public void onGetOrderDetailSucess(TicketOrderDetBean orderDetailBean)
    {
        closePDialog();
        this.orderDetailBean = orderDetailBean;
        if (orderDetailBean != null)
        {
            adapter.clearAll();
            adapter.setFlightType(orderDetailBean.getFlightType());
            adapter.appendALL(orderDetailBean.getOrderInfo());
        }
    }

    @Override
    public void onGetOrderDetailFail(String failMsg)
    {
        closePDialog();
    }

    /**
     * 更改条件列表；
     */
    private List<RescheCondition> getSchedueData()
    {
        List<RescheCondition> rescheConditions = new ArrayList<>();
        RescheCondition condition = new RescheCondition();

        condition.setTimeIntro("起飞前2小时外");
        condition.setPrice(58);
        rescheConditions.add(condition);

        RescheCondition condition2 = new RescheCondition();
        condition2.setTimeIntro("起飞前2小时内");
        condition2.setPrice(116);
        rescheConditions.add(condition2);

        return rescheConditions;
    }

    private void recvIntentData()
    {
        Serializable orderIdObj = getIntent().getSerializableExtra("orderBean");
        if (orderIdObj != null)
        {
            orderBean = (OrderBean) orderIdObj;
            orderId = orderBean.getOrderId();
        }
    }

    @Event(value = {R.id.rl_title, R.id.btn_ok, R.id.reason_for_change})
    private void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.reason_for_change: //改签条件；
                Intent reason = new Intent(this, ReasonForChangeActivity.class);
                reason.putExtra("reasonType", "2");
                startActivityForResult(reason, REQ_GET_REFUND_REASON);
                break;
            case R.id.rl_title:
                finish();
                break;
            case R.id.btn_ok:
                if (isInputRight())
                {
                    startModifyOrder();
                }
                break;
        }
    }

    /**
     * 改签之前的条件是否成立；
     */
    private boolean isInputRight()
    {
        //是否选择退票人；
        List<TicketOrderDetBean.TicketsListBean> tickets = adapter.getSelectPassengers();
        if (tickets == null || tickets.size() == 0)
        {
            toastCustom(R.string.ticket_chose_plane);
            return false;
        }
//        if (resultBeans == null)
//        {
//            toastCustom(R.string.ticke_chose_yuanyin);
//            return false;
//        }
        return true;
    }

    /**
     * 开始改签；
     */
    private void startModifyOrder()
    {
        //航班列表；
        Intent intent = new Intent(this, RescheduleListActivity.class);
//        intent.putExtra("flightList", getFlightList());
        ModifyOrderManager modifyOrderManager = new ModifyOrderManager();
        modifyOrderManager.setOrderBean(orderBean);
        modifyOrderManager.setCurrentLine(getFlightType());
        // 原航班列表；
        modifyOrderManager.setOrinalOrders(getOrignalFlights());
        modifyOrderManager.setFlightType(orderDetailBean.getFlightType());
        intent.putExtra("modifyOrderManager", modifyOrderManager);
        startActivity(intent);
    }

    /**
     * 获取原航班列表；有中转就是中转列表；无中转就是航线列表；
     *
     * @return
     */
    private List<TicketOrderDetBean.OrderInfoBean> getOrignalFlights()
    {
        List<TicketOrderDetBean.OrderInfoBean> oibs = adapter.getSelectTicket();
        if (oibs != null)
        {
            List<TicketOrderDetBean.OrderInfoBean> handleList = new ArrayList<>();
            TicketOrderDetBean.OrderInfoBean oib;
            try
            {
                for (int i = 0; i < oibs.size(); i++)
                {
                    oib = oibs.get(i);
                    if (oib.getFlightList() != null)
                    {
                        List<TicketOrderDetBean.MyFlight> myFlights = oib.getFlightList();
                        //里层；
                        for (int j = 0; j < myFlights.size(); j++)
                        {
                            TicketOrderDetBean.OrderInfoBean oibClone = oib.clone();
                            List<TicketOrderDetBean.MyFlight> myFlightList = new ArrayList<>();
                            myFlightList.add(myFlights.get(j));
                            oibClone.setFlightList(myFlightList);
                            handleList.add(oibClone);
                        }
                    }
                }
            } catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            return handleList;
        }
        return null;
    }

    /**
     * 获取要改签的航班列表；
     */
    private ArrayList<Map<String, Object>> getFlightList()
    {
        ArrayList<Map<String, Object>> mList = new ArrayList<>();
        List<TicketOrderDetBean.OrderInfoBean> orders = adapter.getSelectTicket();
        // 添加往返 组合套餐；  ????????
        if (adapter.isCombineProducts() && orders.size() == 2)
        {
            addRoundTicket(mList, orders.get(0));
        } else
        {
            addTicket(mList, orders);
        }
        return mList;
    }

    /**
     * 添加往返票；
     */
    private void addRoundTicket(List<Map<String, Object>> mList, TicketOrderDetBean.OrderInfoBean order)
    {
        if (order != null)
        {
            Map<String, Object> args = new HashMap<>();
            args.put("orderNo", order.getOrderNo()); //航空订单号
            args.put("departDate", DateUtil.string2SpecString(order.getStartTime(),
                    DateUtil.DATE_FORMAT_DATE)); //改签起飞时间（格式：yyyyMMdd） 用户可选择，此处默认和原定起飞时间 相同；
            args.put("oriDepartDate", DateUtil.string2SpecString(order.getStartTime(),
                    DateUtil.DATE_FORMAT_DATE)); //原定起飞时间  （格式：yyyyMMdd）
            /**
             *非组合套餐，设置为OW，否则正常处理；（依赖接口设计）；
             */
            args.put("tripType", adapter.isCombineProducts() ? getFlightType() : AppConstant.TYPE_SINGLE); //航班类型（航程类型OW,RT,MT）
            args.put("userList", getUserList(order.getTicketsList())); //选择改签人员
            mList.add(args);
        }
    }

    /**
     * 获取航班类型；
     *
     * @return
     */
    public String getFlightType()
    {
        if (orderDetailBean != null)
        {
            return orderDetailBean.getFlightType();
        }
        return "";
    }

    /**
     * 添加单程，往返非组合套餐，或多程票；
     */
    private void addTicket(List<Map<String, Object>> mList, List<TicketOrderDetBean.OrderInfoBean> orders)
    {
        if (mList != null && orders != null)
        {
            for (int i = 0; i < orders.size(); i++)
            {
                addRoundTicket(mList, orders.get(i));
            }
        }
    }

    /**
     * 获取证件信息
     *
     * @param ticketsList
     */
    private List<Map<String, String>> getUserList(List<TicketOrderDetBean.TicketsListBean> ticketsList)
    {
        List<Map<String, String>> args = new ArrayList<>();
        if (ticketsList != null)
        {
            for (int i = 0; i < ticketsList.size(); i++)
            {
                //添加选中的；
                if (ticketsList.get(i).isSelect())
                {
                    Map<String, String> map = new HashMap<>();
                    map.put("paxIdType", ticketsList.get(i).getIdType());
                    map.put("paxIdNo", ticketsList.get(i).getGuestIDCard());
                    args.add(map);
                }
            }
        }
        return args;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null || resultCode != Activity.RESULT_OK) return;

        switch (requestCode)
        {
            case REQ_GET_REFUND_REASON: //获取改签原因；
                Serializable ser = data.getSerializableExtra("reasonBean");
                if (ser != null)
                {
                    resultBeans = (ReasonForChangeBean) ser;
                    tv_reason.setText(resultBeans.getContent());
                }
                break;
        }
    }
}
