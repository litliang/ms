package com.yzb.card.king.ui.ticket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.ActivityMessageEvent;
import com.yzb.card.king.bean.hotel.HbDialogParam;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.bean.ticket.FlightManager;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.bean.ticket.TicketAmountBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.app.interfaces.DiscountListener;
import com.yzb.card.king.ui.appwidget.DividerDecoration;
import com.yzb.card.king.ui.appwidget.FlightDetailTitleView;
import com.yzb.card.king.ui.ticket.fragment.ShopCouponDialogFragment;
import com.yzb.card.king.ui.ticket.fragment.ShopBounsDialogFragment;
import com.yzb.card.king.ui.ticket.fragment.TicketBankYhDialogFragment;
import com.yzb.card.king.ui.ticket.fragment.TicketDetailFragmentDialog;
import com.yzb.card.king.ui.ticket.presenter.FlightAmountPresenter;
import com.yzb.card.king.ui.ticket.view.FlightAmountView;
import com.yzb.card.king.ui.transport.adapter.TicketAgentListAdapter;
import com.yzb.card.king.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 2016/5/10.
 * 机票代理商列表页；
 */
public class TicketAgentActivity extends BaseTicketActivity implements FlightAmountView
{
    private static final int NEXT_STEP = 1;
    private static final int REQ_ORDER = 0; //退改签规则请求码；

    private int type; //当前航线类型；单程；往返，多程； 具体查看base；
    private TicketAgentListAdapter adapter;
    private FlightManager flightManager;
    private Flight currentFlight;
    private String flightId; // 航班id；
    private FlightDetailBean flightDetailBean; // 航班详情；
    private FlightAmountPresenter flightAmountPresenter; //机票详情查询；

    protected void onCreate(Bundle savedInstanceState)
    {
        colorStatusResId = R.color.color_436a8e;
        super.onCreate(savedInstanceState);
        initView();
        flightAmountPresenter = new FlightAmountPresenter(this);
        initData();

        //注册evenBus
        GlobalApp.activityStr = "ticketAgentActivity";
        EventBus.getDefault().register(this);
    }

    private void initView()
    {
        super.initView(this);
        rl_title_right.setVisibility(View.GONE);
        setBottomVisibility(View.GONE);

        recvIntentData();

        recyclerView.setNeedDivider(true);
        //禁止加载更多；
        recyclerView.setIsEnale(false);
        adapter = new TicketAgentListAdapter(this);
        int  childNum = flightManager.getChildrenNum();
        int  babyNum = flightManager.getBabyNum();
        adapter.setChildrenPrice(childNum,babyNum);

        adapter.setItemListener(itemClickListener);
        recyclerView.setAdapter(adapter);

        tv_title_start.setClickable(false);
        tv_title_end.setClickable(false);
        setTripTypeVisibility(View.VISIBLE);
        initTitle();
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecration()
    {
        return new DividerDecoration(this, DividerDecoration.VERTICAL_LIST, R.drawable.divider_transparent_3dp);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_ticket_agent;
    }

    /**
     * 初始化title；
     */
    private void initTitle()
    {
        if (type == TYPE_SINGLE)
        {
            setTitle(currentFlight.getStartCity().getCityName(), R.mipmap.icon_single,
                    currentFlight.getEndCity().getCityName());
            tv_type.setText(getString(R.string.ticket_single_route));
        } else if (type == BaseTicketActivity.TYPE_ROUND)
        {
            setTitle(currentFlight.getStartCity().getCityName(), R.mipmap.icon_arrow_wf,
                    currentFlight.getEndCity().getCityName());

            tv_type.setText(getString(R.string.ticket_wf));
        } else if (type == TYPE_MULTI)
        {
            setTitle(getString(R.string.ticket_multi_flight), 0, "");
            tv_type.setText(getString(R.string.ticket_dc));
        }

        //2017.1.23
        tv_type.setVisibility(View.GONE);
    }

    /**
     * 接受Intent参数；
     */
    private void recvIntentData()
    {
        Intent intent = getIntent();
        flightManager = (FlightManager) intent.getSerializableExtra("flightManager");
        currentFlight = flightManager.getCurrentFlight();

        type = flightManager.getCurrentLine();

        //获取flightId;
        if (flightManager.getTicket() != null)
        {
            flightId = String.valueOf(flightManager.getTicket().getFlightId());
        }
    }

    private int selectAgentIndex = -1;

    /**
     * adapter item点击
     */
    private View.OnClickListener itemClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Object opType = v.getTag(R.id.ticket_agent_tag_op_type);
            Object posObj = v.getTag(R.id.ticket_agent_tag_position);
            if (opType == null)
            {
                return;
            }
            int position = Integer.parseInt(String.valueOf(posObj));
            FlightDetailBean.TicketPriceInfoBean bean = adapter.getData().get(position);

            switch (Integer.parseInt(String.valueOf(opType)))
            {
                case TicketAgentListAdapter.OP_TYPE_ADVANCE: //预定；
                    order(position);
                    break;
                case TicketAgentListAdapter.OP_TYPE_DISCOUNT: //退改签规则；
                    selectAgentIndex = position;
//                    Intent intent = new Intent(TicketAgentActivity.this, RefundTicketRuleActivity.class);
//                    intent.putExtra("data", bean);
//                    startActivityForResult(intent, REQ_ORDER);
                    //showTicketBackChangeDialog(bean,position);

                    String priceId = (String) v.getTag();
                    Intent intent = new Intent(TicketAgentActivity.this, RefundTicketRuleActivity.class);
                    intent.putExtra("priceId", bean.getTicketPriceId());
                    startActivity(intent);
                    break;
                case TicketAgentListAdapter.OP_TYPE_COUPON: //优惠券；
                    showYhqDialog(bean);
                    break;
                case TicketAgentListAdapter.OP_TYPE_PACKET: //红包；
                    showCouponDialog(bean);
                    break;
                case TicketAgentListAdapter.OP_TYPE_CREDIT_COUPON: //银行优惠；
                    showBankCouponsDialog(bean);
                    break;
                case TicketAgentListAdapter.OP_TYPE_DETAIL: //详情；
                    showTicketDialog(bean,position);
                    break;
            }
        }
    };

    private void showTicketBackChangeDialog(FlightDetailBean.TicketPriceInfoBean bean ,int position)
    {

        TicketDetailFragmentDialog ticketDetailFragmentDialog = new TicketDetailFragmentDialog();

        ticketDetailFragmentDialog.setDialogHandler(dialogHandler);

        Bundle bundle = new Bundle();

        bundle.putSerializable("data", bean);

        bundle.putInt("position",position);

        bundle.putBoolean("ifShowTicketDetail",false);

        ticketDetailFragmentDialog.setArguments(bundle);

        ticketDetailFragmentDialog.show(getSupportFragmentManager(), "roomFtTwo");
    }

    /**
     * 机票代理信息
     * @param bean
     * @param position
     */
    private void showTicketDialog(FlightDetailBean.TicketPriceInfoBean bean ,int position)
    {
        String storeName = flightDetailBean.getStoreName();

        String flightNumber = flightDetailBean.getFlightNumber();

        //行业id
        int industryId =  GlobalVariable.industryId;

        long shopId = Long.parseLong(flightDetailBean.getShopId());

        long goodsId = flightDetailBean.getFlightId();

        LogUtil.e("AAAA",industryId+"-----------"+ flightDetailBean.getShopId()+"   -------------"+flightDetailBean.getFlightId()+"----"+flightDetailBean.getDepTime());

        TicketDetailFragmentDialog ticketDetailFragmentDialog = new TicketDetailFragmentDialog();

        ticketDetailFragmentDialog.setDialogHandler(dialogHandler);

        Bundle bundle = new Bundle();

        bundle.putSerializable("data", bean);

        bundle.putInt("position",position);

        bundle.putBoolean("ifShowTicketDetail",true);

        bundle.putSerializable("storeName", storeName);

        bundle.putSerializable("flightNumber", flightNumber);

        bundle.putInt("industryId",industryId);

        bundle.putLong("shopId",shopId);

        bundle.putLong("goodsId",goodsId);

        bundle.putString("startDate",flightDetailBean.getDepTime());

        ticketDetailFragmentDialog.setArguments(bundle);

        ticketDetailFragmentDialog.show(getSupportFragmentManager(), "roomFt");

    }

    private Handler dialogHandler = new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            order(msg.what);
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        GlobalApp.activityStr = null;
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void mainEventThread(ActivityMessageEvent event)
    {
        if ("ticketAgentActivity".equals(event.getActivityName()))
        {//接收转账成功信息

            finish();

        }
    }

    /**
     * 显示银行优惠对话框；
     */
    private void showBankCouponsDialog(FlightDetailBean.TicketPriceInfoBean bean)
    {
        TicketBankYhDialogFragment.getInstance("", "").
                setData(bean.getAgentId(), bean.getFlightId()).show(getSupportFragmentManager(), "");
    }

    /**
     * 显示红包dialog；
     *
     * @param bean
     */
    private void showCouponDialog(FlightDetailBean.TicketPriceInfoBean bean)
    {
        HbDialogParam param = new HbDialogParam(bean.getAgentId(), bean.getFlightId()
                , DiscountListener.type_all, DiscountListener.type_hb, AppConstant.discount_code_ticket);
        ShopBounsDialogFragment.getInstance("", "").
                setData(param).show(getSupportFragmentManager(), "");
    }

    /**
     * 显示优惠券dialog；
     *
     * @param bean
     */
    private void showYhqDialog(FlightDetailBean.TicketPriceInfoBean bean)
    {
        HbDialogParam param = new HbDialogParam(bean.getAgentId(), bean.getFlightId()
                , DiscountListener.platform_type_platform_shop, DiscountListener.type_yhq, AppConstant.discount_code_ticket);
        ShopCouponDialogFragment.getInstance("", "").setData(param).show(getSupportFragmentManager(), "");
    }

    /**
     * 下拉刷新；
     */
    @Override
    protected void refresh()
    {
        initData();
    }

    private void initData()
    {
        pageStart = 0;
        if (!isEmpty(flightId))
        {
            //获取用户选择的最后一个航班；
            PlaneTicket planeTicket = flightManager.getTicket();
            commonparam.clear();
            commonparam.put("flightId", flightId);
            commonparam.put("baseCabinCode", planeTicket.getBaseCabinCode()); //舱位等级
            commonparam.put("transferFlights", planeTicket.getFlightType()); //航班类型（1航线，0直飞）
            commonparam.put("page", pageSize * pageStart);
            commonparam.put("pageSize", pageSize);
            this.serviceName = CardConstant.transport_airfarequeryinfo;
            refreshData();
        }
    }

    @Override
    protected void onSucess(boolean event_tag, String data)
    {
        if (event_tag)
        {
            adapter.clearAll();
        }
        flightDetailBean = JSON.parseObject(data, FlightDetailBean.class);
        if (flightDetailBean != null)
        {
            initSecondTitle();
            adapter.appendALL(flightDetailBean.getTicketPriceInfo());
            recyclerView.notifyData();
        }
        pageStart++;
    }

    /**
     * 预定；
     *
     * @param position 点击的data的下标；
     */
    private void order(int position)
    {
        if (!checkLogin())
        {
            return;
        }
        FlightDetailBean.TicketPriceInfoBean data = adapter.getData().get(position);
        PlaneTicket lastPlaneTicket = flightManager.getTicket();

        setPlaneTicketParams(lastPlaneTicket, data);
        //订单页面用到；
        lastPlaneTicket.setIsFlightNumber(flightDetailBean.getIsFlightNumber());
        lastPlaneTicket.setSharedFlightName(flightDetailBean.getSharedFlightName());
        lastPlaneTicket.setSharedFlightLogo(flightDetailBean.getSharedFlightLogo());
        lastPlaneTicket.setArrartureTerminal(flightDetailBean.getArrartureTerminal());

        String acbIninfo = data.getAcbIninfo();
        if (!acbIninfo.contains("A"))
        {
            if (Integer.parseInt(acbIninfo) <= 0)
            {
                toastCustom(R.string.no_more_ticket);
                return;
            }
        }

        //预定机票
        flightManager.addTicket();
        Intent intent = new Intent();
        //检查机票是否定完了
        if (flightManager.getFlights().size() > flightManager.getTickets().size())
        {
            //还有机票没买
            intent.setClass(this, SingleListActivity.class);
            intent.putExtra("flightManager", flightManager);
            startActivityForResult(intent, NEXT_STEP);
        } else
        {
            //最后一程，查询航线列表最新的票价；
            getFlightAmount();

        }
    }

    @Override
    public void onGetFlightAmountSucess(boolean event_tag, List<TicketAmountBean> accountBeans)
    {
        closePDialog();
        if (accountBeans != null && accountBeans.size() > 0)
        {
            List<PlaneTicket> tickets = flightManager.getTickets();
            //单程，往返赋票价；accountBeans长度为1；
            if (type == TYPE_SINGLE || type == TYPE_ROUND)
            {
                TicketAmountBean amountBean = accountBeans.get(0);
                // 往返时2个票价是一样的（为总票价）；
                for (int i = 0; i < tickets.size(); i++)
                {
                    setTicketFare(tickets.get(i), amountBean);
                }
            } else
            {
                //多程赋值；
                for (int i = 0; i < tickets.size(); i++)
                {
                    //为每个票赋值；
                    setTicketFare(tickets.get(i), accountBeans.get(i));
                }
            }
            //查询票价集合（多程是size大于1）
            TicketAmountBean amountBean = accountBeans.get(0);
            //跳转订单页；
            Intent intent = new Intent(this, TicketOrderActivity.class);
            intent.putExtra("flightManager", flightManager);

            intent.putExtra("flightType", amountBean.getFlightType());
            startActivityForResult(intent, TicketOrderActivity.REQ_COMMIT_ORDER);
        } else
        {
            toastCustom("无航班票价");
        }
    }

    private void setTicketFare(PlaneTicket planeTicket, TicketAmountBean amountBean)
    {
        planeTicket.setFareAdult(amountBean.getForAmount()); //成人票价；
        planeTicket.setFareChd(amountBean.getChaAmount()); //儿童票价；
        planeTicket.setFareBab(amountBean.getInfAmount()); //婴儿票价；

        planeTicket.setFueltax(amountBean.getFueltax()); //婴儿税费；
        planeTicket.setFueltaxChd(amountBean.getFueltaxChd()); //儿童税费；
        planeTicket.setFueltaxBab(amountBean.getFueltaxBad()); //婴儿税费；
    }

    @Override
    public void onGetFlightAmountFail(String failMsg)
    {
        //失败时要删除之前点击的最后一个；
        flightManager.removeLastTicket();
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 机票详情查询
     */
    private void getFlightAmount()
    {
        showNoCancelPDialog(R.string.loading);
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("amountQuery", getAmountQueryList()); //查询票价集合（多程是size大于1） amountQuery<Map>
        flightAmountPresenter.loadData(true, argMap);
    }

    /**
     * 查询票价集合（多程是size大于1）
     *
     * @return
     */
    private List<Map> getAmountQueryList()
    {
        List<Map> flightAmountList = new ArrayList<>();
        //单程或往返；
        if (type == TYPE_SINGLE || type == TYPE_ROUND)
        {
            Map map = new HashMap();
            map.put("flightType", getFlightType());
            map.put("productId", getProductId()); //产品编号；国际：332；国内 : 331
            map.put("flightList", getFlightList()); //机票信息（往返时size大于1）
            flightAmountList.add(map);
        } else
        {
            //已经预定的航线列表；
            List<PlaneTicket> planeTickets = flightManager.getTickets();
            for (int i = 0; i < planeTickets.size(); i++)
            {
                Map map = new HashMap();
                map.put("flightType", getFlightType());
                map.put("productId", getProductId());
                map.put("flightList", getMultFlightList(planeTickets.get(i)));
                flightAmountList.add(map);
            }
        }
        return flightAmountList;
    }

    /**
     * 获取 产品编号；国际：332；国内 : 331
     *
     * @return
     */
    public String getProductId()
    {
        PlaneTicket planeTicket = flightManager.getTicket();
        if (planeTicket != null)
        {
            return planeTicket.isNationalFlight() ? AppConstant.product_id_outcountry : AppConstant.product_id_incountry;
        }
        return AppConstant.product_id_incountry;
    }

    /**
     * 多程；
     *
     * @param planeTicket
     * @return
     */
    private List<Map> getMultFlightList(PlaneTicket planeTicket)
    {
        List<Map> arrayMult = new ArrayList<>();
        if (planeTicket != null)
        {
            Map itemMap = new HashMap();
            itemMap.put("flightId", planeTicket.getFlightId()); //航班id
            itemMap.put("ticketPriceId", planeTicket.getTicketPriceId()); //票价id
            itemMap.put("odNumber", "0"); //多程传0；
            arrayMult.add(itemMap);
        }
        return arrayMult;
    }

    /**
     * 机票信息（往返时size大于1）
     * 适用于单程和往返；
     *
     * @return
     */
    private List<Map> getFlightList()
    {
        //已经预定的航线列表；
        List<PlaneTicket> planeTickets = flightManager.getTickets();
        List<Map> array = new ArrayList<>();
        //flightId	ticketPriceId	odNumber
        //航班id	    票价id	        0：去程，1：返程
        Map itemMap;
        for (int i = 0; i < planeTickets.size(); i++)
        {
            itemMap = new HashMap<>();
            itemMap.put("flightId", planeTickets.get(i).getFlightId());
            itemMap.put("ticketPriceId", planeTickets.get(i).getTicketPriceId());
            //单程，多程；0
            if (type == TYPE_SINGLE || type == TYPE_MULTI)
            {
                itemMap.put("odNumber", "0");
            } else
            {
                //去返；
                itemMap.put("odNumber", i == 0 ? "0" : "1");
            }
            array.add(itemMap);
        }
        return array;
    }

    /**
     * 航班类型（单程：OW；往返：RT；多段：MT）
     *
     * @return
     */
    private String getFlightType()
    {
        return type == TYPE_SINGLE ? AppConstant.TYPE_SINGLE : (type == TYPE_ROUND ? AppConstant.TYPE_ROUND : AppConstant.TYPE_MULT);
    }

    /**
     * 为最后选择的一个航班PlaneTicket初始化相关的票价和税费；以便订单页面用到,
     * 主要处理提交订单时，PlaneTicket无法取得税费等相关参数的问题；
     *
     * @param lastPlaneTicket
     * @param data
     */
    private void setPlaneTicketParams(PlaneTicket lastPlaneTicket, FlightDetailBean.TicketPriceInfoBean data)
    {
        //设置票价；
        lastPlaneTicket.setFareAdult(data.getFareAdult());
        lastPlaneTicket.setFareChd(data.getFareChd());
        lastPlaneTicket.setFareBab(data.getFareBab());

        //设置税费；
        lastPlaneTicket.setFueltax(data.getFueltaxAdult());
        lastPlaneTicket.setFueltaxChd(data.getFueltaxChd());
        lastPlaneTicket.setFueltaxBab(data.getFueltaxBab());

        //设置代理商id，和票价id；
        lastPlaneTicket.setAgentId(data.getAgentId());
        lastPlaneTicket.setTicketPriceId(data.getTicketPriceId());

       // lastPlaneTicket.setPackage(data.getPachage());

        lastPlaneTicket.setDisMap(data.getDisMap());

      //  LogUtil.e("XYYYY","aa==---"+data.getPachage().getDiscountStatus()+"-------"+data.getPachage().getDiscountName());

    }

    /**
     * 初始化二级title；
     */
    private void initSecondTitle()
    {
        if (flightDetailBean == null)
        {
            return;
        }
        fl_second_title.removeAllViews();
        FlightDetailTitleView titleView = new FlightDetailTitleView(this);
        titleView.setTripType(type);

        //选中的是票次；
        titleView.setIndex(flightManager.getTickets().size() + 1);
        titleView.setData(flightDetailBean);
        setSecondTitle(titleView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case NEXT_STEP:
                flightManager.removeLastTicket();
                break;
            case REQ_ORDER: //退改签规则页面返回；
                if (resultCode == Activity.RESULT_OK && selectAgentIndex >= 0)
                {
                    order(selectAgentIndex);
                }
                break;
            case TicketOrderActivity.REQ_COMMIT_ORDER:
                //未提交订单时,要删除之前点击的最后一个；
                flightManager.removeLastTicket();
                break;
        }
    }

}
