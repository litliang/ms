package com.yzb.card.king.ui.ticket.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.DividerDecoration;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.TicketFilterView;
import com.yzb.card.king.ui.appwidget.popup.ChannelPopWindow;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.transport.presenter.CommonPresenter;
import com.yzb.card.king.ui.transport.presenter.impl.CommonPresenterImpl;
import com.yzb.card.king.ui.transport.view.CommonView;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ViewUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 机票基类；
 *
 * @author yinshuguang;
 *         第一次修改：gengqiyun 2016.8.5; 代码优化；
 */
public abstract class BaseTicketActivity extends BaseActivity implements CommonView,
        SwipeRefreshLayout.OnRefreshListener
{
    protected HeadFootRecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected TextView tv_title_start, tv_title_end; //标题栏中间的出发地和目的地；
    protected TextView tv_type; //顶部的类型；比如：单程，往返，；
    protected LinearLayout fl_second_title; //第二标题

    protected ImageView img_title;
    protected Activity mActivity;
    public static final int TYPE_SINGLE = 1; //单程；
    public static final int TYPE_ROUND = 2;  //往返；
    public static final int TYPE_MULTI = 3;  //多程；
    protected String cityId; //城市id；
    protected GlobalApp app;
    protected String lat; //经度；
    protected String lng; // 纬度；
    protected String typeId; //分类id；
    protected String typeParentId = AppConstant.ticket_id; //父分类id；
    protected String sort = ""; //排序(默认)；
    protected String filter = "1"; //筛选(默认)；
    protected String bankId = "";//银行id；
    protected Map<String, Object> commonparam = new HashMap<>();
    protected String serviceName = "";

    protected String typeChildId;  //等效于typeId；

    protected CommonPresenter commonPresenter;
    public int pageStart = 0; //分页的下标；
    protected int pageSize = 15; //每页的大小；
    private ChannelPopWindow channelPopWindow;
    private TicketFilterView ticketFilterView;
    protected View rl_title_right;

    private Handler uiHandler;
    private LinearLayout flMsgView;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        commonPresenter = new CommonPresenterImpl(this);
    }

    /**
     * 设置句柄
     *
     * @param uiHandler
     */
    public void setUiHandler(Handler uiHandler)
    {
        this.uiHandler = uiHandler;

    }

    public void initView(Activity activity)
    {
        mActivity = activity;
        setContentView(getLayoutResId());
        lat = selectedCity.latitude + "";
        lng = selectedCity.longitude + "";
        cityId = selectedCity.cityId;

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (HeadFootRecyclerView) findViewById(R.id.listview);
        recyclerView.addItemDecoration(getItemDecration());
        ViewUtil.recyclerViewSetting1(this, recyclerView);
        //加载更多
        recyclerView.setOnLoadMoreListener(loadMoreListener);

        rl_title_right = findViewById(R.id.rl_title_right);

        tv_title_start = (TextView) findViewById(R.id.tv_title_1);
        tv_title_end = (TextView) findViewById(R.id.tv_title_2);
        img_title = (ImageView) findViewById(R.id.img_title);
        fl_second_title = (LinearLayout) findViewById(R.id.fl_second_title);
        flMsgView = (LinearLayout) findViewById(R.id.flMsgView);

        tv_type = (TextView) findViewById(R.id.tv_type);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        initListener();
        initBottomView();
    }

    private void initListener()
    {

    }

    protected RecyclerView.ItemDecoration getItemDecration()
    {
        return new DividerDecoration(this, DividerDecoration.VERTICAL_LIST, R.drawable.ticket_divider);
    }

    protected void setChangePlaceEnable(boolean enable)
    {
        tv_title_start.setEnabled(enable);
        tv_title_end.setEnabled(enable);
    }

    /**
     * 获取布局资源文件；
     */
    protected int getLayoutResId()
    {
        return R.layout.activity_base_ticket;
    }

    private void initBottomView()
    {
        RelativeLayout rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);
        ticketFilterView = new TicketFilterView(this);
        ticketFilterView.setInternational(isInternational());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ticketFilterView.setLayoutParams(layoutParams);
        rlBottom.addView(ticketFilterView);
    }

    /**
     * 设置底部筛选器的显示隐藏；
     *
     * @param visibility
     */
    protected void setBottomVisibility(int visibility)
    {
        RelativeLayout rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);
        if (rlBottom != null)
        {
            rlBottom.setVisibility(visibility);
        }
    }

    protected void setMsg(String msg)
    {
        if (tvMsg != null)
        {
            if (TextUtils.isEmpty(msg))
            {
                tvMsg.setVisibility(View.GONE);
            } else
            {
                tvMsg.setText(msg);
                tvMsg.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 判断是否是国际航班。是，返回true
     *
     * @return
     */
    protected boolean isInternational()
    {
        return false;
    }


    private Handler mainHandler = new Handler();


    private HeadFootRecyclerView.OnLoadMoreListener loadMoreListener = new HeadFootRecyclerView.OnLoadMoreListener()
    {
        @Override
        public void loadMoreListener()
        {
            commonparam.put("page", pageSize * pageStart);
            commonparam.put("pageSize", pageSize);
            commonPresenter.loadData(false, commonparam);
        }
    };

    public void setSecondTitle(View v)
    {
        fl_second_title.addView(v);
    }

    /**
     * 设置标题栏下的单程块的隐藏和显示；
     *
     * @param visibility
     */
    public void setTripTypeVisibility(int visibility)
    {
        tv_type.setVisibility(visibility);
    }

    public void setTitle(String text1, int imgId, String text2)
    {
        tv_title_start.setText(text1);
        tv_title_end.setText(text2);
        if (imgId != 0)
            img_title.setImageResource(imgId);
        else
            img_title.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh()
    {
        pageStart = 0;
        refreshData();
    }

    public void back(View v)
    {
        finish();
    }

    /**
     * 右侧弹窗
     *
     * @param v
     */
    public void popRight(View v)
    {
        if (channelPopWindow == null)
        {
            channelPopWindow = new ChannelPopWindow(uiHandler, this);
        }
        channelPopWindow.showAsDropDown(v);
    }

    public void addMsg(Context context, Object map, String dotText)
    {
        if (map == null)
        {
            return;
        }
        View v = View.inflate(context, R.layout.ticket_msg_view, null);
        TextView dot = (TextView) v.findViewById(R.id.tv_dot);
        TextView date = (TextView) v.findViewById(R.id.tv_date);
        TextView startTime = (TextView) v.findViewById(R.id.tv_startTime);
        TextView endTime = (TextView) v.findViewById(R.id.tv_endTime);
        TextView startAirport = (TextView) v.findViewById(R.id.tv_startAirport);
        TextView endAirport = (TextView) v.findViewById(R.id.tv_endAirport);
        TextView company = (TextView) v.findViewById(R.id.tv_company);

        if (map instanceof PlaneTicket)
        {
            PlaneTicket planeTicket = (PlaneTicket) map;
            dot.setText(dotText);
            date.setText(planeTicket.getTimeSeres());
            startTime.setText(planeTicket.getDepTime());
            endTime.setText(planeTicket.getArrTime());
            startAirport.setText(planeTicket.getStartCity());
            endAirport.setText(planeTicket.getEndCity());
            company.setText(planeTicket.getStoreName());
        } else if (map instanceof TicketOrderDetBean.OrderInfoBean) //添加改签消息；
        {
            TicketOrderDetBean.OrderInfoBean orderInfoBean = (TicketOrderDetBean.OrderInfoBean) map;
            dot.setText(dotText);
            dot.setVisibility(TextUtils.isEmpty(dotText) ? View.GONE : View.VISIBLE);

            if (orderInfoBean.getFlightList() != null && orderInfoBean.getFlightList().size() > 0)
            {
                //航班；
                TicketOrderDetBean.MyFlight myFlight = orderInfoBean.getFlightList().get(0);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(myFlight.getTimeSeres());
                date.setText("原票  " + DateUtil.date2String(calendar.getTime(), DateUtil.DATE_FORMAT_DATE_DAY2));

                startTime.setText(myFlight.getDepTime());
                endTime.setText(myFlight.getArrTime());

                startAirport.setText(myFlight.getDepAirPort());
                endAirport.setText(myFlight.getArrAirPort());
                company.setText(myFlight.getSccarrName()); //市场方航空公司名称
            }
        }
        flMsgView.addView(v);
    }

    public void clearMsg()
    {
        flMsgView.removeAllViews();
    }


    /**
     * 数据初始化调用，相当于下拉刷新； 此时
     * pageStart=0;
     */
    public void refreshData()
    {
        if (this.commonparam != null)
        {
            this.commonparam.put("serviceName", this.serviceName);
            commonparam.put("page", pageStart * pageSize);
        }
        mainHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefreshLayout.setRefreshing(true);
                commonPresenter.loadData(true, commonparam);
            }
        }, 100);

    }


    /**
     * 数据加载成功；
     *
     * @param refresh 下拉或上拉；
     * @param data
     */
    @Override
    public void onLoadSucess(boolean refresh, Object data)
    {
        swipeRefreshLayout.setRefreshing(false);
        if (data != null)
        {
            String dataStr = String.valueOf(data);
            if (!isEmpty(dataStr))
            {
                onSucess(refresh, dataStr);
            } else
            {
                noData();
            }
        } else
        {
            noData();
        }
    }

    /**
     * 没数据时调用
     */
    private void noData()
    {
        setMsg(getString(R.string.app_no_data));
        clearData();
    }


    /**
     * 加载失败；
     */
    @Override
    public void onLoadFail(String failMsg)
    {
        ToastUtil.i(GlobalApp.getInstance(),failMsg);
        swipeRefreshLayout.setRefreshing(false);
        clearData();
        setMsg(getString(R.string.server_error));
    }

    /**
     * 没数据时将原来的数据清理掉
     */
    protected void clearData()
    {

    }

    /**
     * 数据加载结果回调方法；
     * 子类需要重写；
     *
     * @param data 数据部分
     */
    protected void onSucess(boolean event_tag, String data)
    {

    }

    protected abstract void refresh();

}