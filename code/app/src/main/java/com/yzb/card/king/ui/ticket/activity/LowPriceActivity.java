package com.yzb.card.king.ui.ticket.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.FlightManager;
import com.yzb.card.king.bean.ticket.LowPriceBean;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.ChannelPopWindow;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.ticket.adapter.FindLowAdapter;
import com.yzb.card.king.ui.ticket.presenter.FindLowPresenter;
import com.yzb.card.king.ui.ticket.view.FindLowView;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@ContentView(value = R.layout.content_low_price)
public class LowPriceActivity extends BaseActivity implements FindLowView
        , SwipeRefreshLayout.OnRefreshListener, FindLowAdapter.OnItemClick {

    private View popupTime;

    private View popupView;

    private View popupPrice;

    private List<LowPriceBean> datas;

    private PopupWindow sortWindow;

    private PopupWindow sortTimeWindow;

    private PopupWindow sortPrice;

    private TextView txtBottomL;

    private TextView txtBottom;

    private Button btnSure;

    @ViewInject(value = R.id.srl_low)
    private SwipeRefreshLayout srl;

    private TextView txtTopL;

    private TextView txtTop;

    private FindLowAdapter adapter;

    @ViewInject(value = R.id.tv_start_city)
    private TextView tvStartCity;

    @ViewInject(value = R.id.parent)
    private LinearLayout parentLocation;

    @ViewInject(value = R.id.tv_end_city)
    private TextView tvEndCity;

    @ViewInject(value = R.id.data_list)
    private HeadFootRecyclerView recyclerView;

    @ViewInject(value = R.id.btn_line_type)
    private Button lineType;

    @ViewInject(value = R.id.btn_price)
    private Button btnPrice;

    @ViewInject(value = R.id.btn_time)
    private Button btn_time;

    @ViewInject(value = R.id.btn_price)
    private Button price;

    @ViewInject(value = R.id.img_title)
    private ImageView imgTitle;
    private ChannelPopWindow channelPopWindow;
    private Handler uiHandler;
    private Button any;
    private Button month1;
    private Button month2;
    private Button month3;
    private Flight currentFlight;
    private FlightManager flightManager;
    private FindLowPresenter findLowPresenter;
    private String sort = "";
    private String time = "";
    private int type;
    private int timeTag = 0;
    public static final int bxTag = 4; //不限
    public static final int oneTag = 1;//当月
    public static final int twoTag = 2;//第二个月
    public static final int threeTag = 3;//第三个月

    private List<String> month = new ArrayList<>();

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        recvIntentData();

        initThisView();

        initData();

    }

    private void initThisView()
    {

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LowPriceActivity.this.finish();
            }
        });

        //设置当前句柄控制
        setUiHandler(myHandler);

        SwipeRefreshSettings.setAttrbutes(this, srl);
        srl.setOnRefreshListener(this);
        datas = new ArrayList<>();
        adapter = new FindLowAdapter(datas, this, type, currentFlight.getStartCity().getCityName(), currentFlight.getEndCity().getCityName());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setIsEnale(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener()
        {
            @Override
            public void loadMoreListener()
            {

                sendRequest();
            }
        });

    }

    /**
     * 獲取最低票價
     */
    private void initData()
    {

        findLowPresenter = new FindLowPresenter(this);
        if (flightManager.getCurrentLine() == BaseTicketActivity.TYPE_SINGLE)//首页进入低价页面单程
        {
            lineType.setText(getString(R.string.ticket_single_route));
        } else if (flightManager.getCurrentLine() == BaseTicketActivity.TYPE_ROUND) //首页进入低价页面往返
        {
            lineType.setText(getString(R.string.ticket_wf));
        } else //首页进入低价页面多程拿第一程
        {
            lineType.setText(getString(R.string.ticket_single_route));
        }

        selfRequest();

    }

    private void selfRequest(){
        srl.post(new Runnable() {
            @Override
            public void run()
            {
                srl.setRefreshing(true);

            }
        });

        this.onRefresh();
    }

    /**
     * 单程发现低价
     */
    private void sendSingRequest()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("arrCityId", currentFlight.getEndCity().getCityId());
        params.put("depCityId", currentFlight.getStartCity().getCityId());
        params.put("price", sort);
        params.put("timesers", time);
        params.put("page",page);
        params.put("pageSize", AppConstant.MAX_PAGE_NUM);
        findLowPresenter.sendFindLowRequest(params, CardConstant.card_app_find_low_price);
    }

    /**
     * 多程发现低价
     */
    private void sendRoundRequest()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("arrCityId", currentFlight.getEndCity().getCityId());
        params.put("depCityId", currentFlight.getStartCity().getCityId());
        params.put("price", sort);
        params.put("timesers", time);
        params.put("page",page);
        params.put("pageSize", AppConstant.MAX_PAGE_NUM);
        findLowPresenter.sendFindLowRoundRequest(params, CardConstant.card_app_find_low_round_price);
    }

    /**
     * 接口句柄
     */
    private Handler myHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            finish();
            return false;
        }
    });

    /**
     * 设置句柄
     *
     * @param uiHandler
     */
    public void setUiHandler(Handler uiHandler)
    {
        this.uiHandler = uiHandler;

    }

    private void initView()
    {
        if (popupPrice == null)
        {

            popupPrice = LayoutInflater.from(this).inflate(R.layout.low_price_sort, null);
            txtTop = (TextView) popupPrice.findViewById(R.id.txt_top);
            txtBottom = (TextView) popupPrice.findViewById(R.id.txt_bottom);
            //升序
            txtTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    sort = "0";
                    btnPrice.setText(txtTop.getText().toString());
                    sortPrice.dismiss();
                    txtTop.setTextColor(getResources().getColor(R.color.color_fb800b));

                    txtBottom.setTextColor(getResources().getColor(R.color.color_333333));

                    price.setTextColor(getResources().getColor(R.color.color_fb800b));
                    selfRequest();
                }
            });
            //降序
            txtBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    sort = "1";
                    btnPrice.setText(txtBottom.getText().toString());
                    sortPrice.dismiss();

                    txtBottom.setTextColor(getResources().getColor(R.color.color_fb800b));

                    txtTop.setTextColor(getResources().getColor(R.color.color_333333));
                    price.setTextColor(getResources().getColor(R.color.color_fb800b));
                    selfRequest();
                }
            });
            sortPrice = new PopupWindow(popupPrice, LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            sortPrice.setFocusable(true);
            sortPrice.setTouchable(true);
            sortPrice.setOutsideTouchable(true);
            sortPrice.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

            txtTop.setTextColor(getResources().getColor(R.color.color_fb800b));

            txtBottom.setTextColor(getResources().getColor(R.color.color_333333));
        }

    }

    private void initSort()
    {
        if (popupView == null)
        {

            popupView = LayoutInflater.from(this).inflate(R.layout.low_line_pop, null);
            //单程
            txtTopL = (TextView) popupView.findViewById(R.id.txt_top_s);
            txtBottomL = (TextView) popupView.findViewById(R.id.txt_bottom_s);
            txtTopL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    lineType.setText(txtTopL.getText().toString());
                    flightManager.setCurrentLine(BaseTicketActivity.TYPE_SINGLE);
                    adapter.setType(BaseTicketActivity.TYPE_SINGLE);
                    type = BaseTicketActivity.TYPE_SINGLE;
                    imgTitle.setImageResource(R.mipmap.jipiao_icon_toaddr);
                    selfRequest();
                    sortWindow.dismiss();
                    txtTopL.setTextColor(getResources().getColor(R.color.color_fb800b));
                    txtBottomL.setTextColor(getResources().getColor(R.color.color_333333));
                    lineType.setTextColor(getResources().getColor(R.color.color_fb800b));
                }
            });
            //往返
            txtBottomL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    lineType.setText(txtBottomL.getText().toString());
                    flightManager.setCurrentLine(BaseTicketActivity.TYPE_ROUND);
                    adapter.setType(BaseTicketActivity.TYPE_ROUND);
                    type = BaseTicketActivity.TYPE_ROUND;
                    imgTitle.setImageResource(R.mipmap.icon_arrow_wf);
                    selfRequest();
                    sortWindow.dismiss();
                    lineType.setTextColor(getResources().getColor(R.color.color_fb800b));
                    txtTopL.setTextColor(getResources().getColor(R.color.color_333333));
                    txtBottomL.setTextColor(getResources().getColor(R.color.color_fb800b));
                }
            });
            sortWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            sortWindow.setFocusable(true);
            sortWindow.setTouchable(true);
            sortWindow.setOutsideTouchable(true);
            sortWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));



            if (flightManager.getCurrentLine() == BaseTicketActivity.TYPE_SINGLE)//首页进入低价页面单程
            {

                txtTopL.setTextColor(getResources().getColor(R.color.color_fb800b));

                txtBottomL.setTextColor(getResources().getColor(R.color.color_333333));
            } else if (flightManager.getCurrentLine() == BaseTicketActivity.TYPE_ROUND) //首页进入低价页面往返
            {
                txtTopL.setTextColor(getResources().getColor(R.color.color_333333));
                txtBottomL.setTextColor(getResources().getColor(R.color.color_fb800b));

            } else //首页进入低价页面多程拿第一程
            {

                txtTopL.setTextColor(getResources().getColor(R.color.color_fb800b));

                txtBottomL.setTextColor(getResources().getColor(R.color.color_333333));
            }
        }
    }


    private void recvIntentData()
    {
        /**
         * 接收Intent数据；
         */
        flightManager = (FlightManager) getIntent().getSerializableExtra("flightManager");

        if (flightManager != null)
        {
            currentFlight = flightManager.getCurrentFlight();
        }

        if (flightManager.getCurrentLine() == BaseTicketActivity.TYPE_ROUND)
        {
            imgTitle.setImageResource(R.mipmap.icon_arrow_wf);
        }

        type = flightManager.getCurrentLine();
        tvStartCity.setText(currentFlight.getStartCity().getCityName());
        tvEndCity.setText(currentFlight.getEndCity().getCityName());


    }


    @Event(value = {R.id.btn_line_type, R.id.btn_price, R.id.btn_time})
    private void onClick(View view)
    {
        switch (view.getId())
        {

            // 直飞类型
            case R.id.btn_line_type:
                initSort();

                int[] location1 = new int[2];
                parentLocation.getLocationInWindow(location1);
                int height1 = UiUtils.dp2px(80);
                sortWindow.showAtLocation(parentLocation, Gravity.NO_GRAVITY, 0, location1[1] - height1);
                break;
            // 价格
            case R.id.btn_price:
                initView();
                int[] location = new int[2];
                parentLocation.getLocationInWindow(location);
                int height = UiUtils.dp2px(80);
                sortPrice.showAtLocation(parentLocation, Gravity.NO_GRAVITY, 0, location[1] - height);
                break;
            // 时间
            case R.id.btn_time:
                createSortTimePopup();
                break;

        }
    }

    private void createSortTimePopup()
    {
        if (popupTime == null)
        {
            popupTime = LayoutInflater.from(this).inflate(R.layout.find_low_sort_time, null);
            sortTimeWindow = new PopupWindow(popupTime, LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            any = (Button) popupTime.findViewById(R.id.any);
            btnSure = (Button) popupTime.findViewById(R.id.sure);
            month1 = (Button) popupTime.findViewById(R.id.next1);
            month1.setTag(1);
            month2 = (Button) popupTime.findViewById(R.id.next2);
            month2.setTag(1);
            month3 = (Button) popupTime.findViewById(R.id.next3);
            month3.setTag(1);
            any.setOnClickListener(myOnClickListener);
            month1.setOnClickListener(myOnClickListener);
            month2.setOnClickListener(myOnClickListener);
            month3.setOnClickListener(myOnClickListener);
            final Calendar date = Calendar.getInstance(Locale.CHINA);
            date.setTime(new Date());
            month1.setText((date.get(Calendar.MONTH) + 1) + "月");
            date.add(Calendar.MONTH, 1);
            month2.setText((date.get(Calendar.MONTH) + 1) + "月");
            date.add(Calendar.MONTH, 1);
            month3.setText((date.get(Calendar.MONTH) + 1) + "月");
            sortTimeWindow.setFocusable(true);
            sortTimeWindow.setTouchable(true);
            sortTimeWindow.setOutsideTouchable(true);
            sortTimeWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
            btnSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (timeTag == bxTag)//不限
                    {
                        time = "";
                        btn_time.setText(any.getText().toString());


                        selfRequest();
                    } else
                    {
                        String mon = "";
                        for (int i = 0; i < month.size(); i++)
                        {
                            mon += month.get(i) + ",";
                        }
                        time = mon.substring(0, mon.lastIndexOf(","));
                        selfRequest();
                    }
                    sortTimeWindow.dismiss();
                }
            });
        }
        // 获取当前月份

        int[] location = new int[2];
        parentLocation.getLocationInWindow(location);
        int height = UiUtils.dp2px(138);
        sortTimeWindow.showAtLocation(parentLocation, Gravity.NO_GRAVITY, 0, location[1] - height);
    }


    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            final Calendar date = Calendar.getInstance(Locale.CHINA);
            date.setTime(new Date());
            int m1Tag = (int) month1.getTag();
            int m2Tag = (int) month2.getTag();
            int m3Tag = (int) month3.getTag();
            switch (v.getId())
            {
                case R.id.any:  //日期选择不限
                    timeTag = bxTag;
                    any.setBackgroundResource(R.drawable.shape_btn_yellow_2);
                    any.setTextColor(getResources().getColor(R.color.color_fb800b));
                    month1.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                    month1.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                    month2.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                    month2.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                    month3.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                    month3.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                    month1.setTag(1);
                    month2.setTag(1);
                    month3.setTag(1);
                    month.clear();
                    break;
                case R.id.next1:  //日期选择当前月
                    timeTag = oneTag;
                    time = DateUtil.date2String(new Date(), DateUtil.DATE_FORMAT_DATE);
                    any.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                    any.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                    if (m1Tag == 1)
                    {
                        month1.setTag(0);
                        month1.setBackgroundResource(R.drawable.shape_btn_yellow_2);
                        month1.setTextColor(getResources().getColor(R.color.color_fb800b));
                        month.add(time);
                    } else
                    {
                        month1.setTag(1);
                        month1.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                        month1.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                        month.remove(time);
                    }

                    break;
                case R.id.next2:  //日期选择下一个月
                    timeTag = twoTag;
                    any.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                    any.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                    date.add(date.MONTH, 1);
                    time = DateUtil.date2String(date.getTime(), DateUtil.DATE_FORMAT_DATE);
                    if (m2Tag == 1)
                    {
                        month2.setTag(0);
                        month2.setBackgroundResource(R.drawable.shape_btn_yellow_2);
                        month2.setTextColor(getResources().getColor(R.color.color_fb800b));
                        month.add(time);
                    } else
                    {
                        month2.setTag(1);
                        month2.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                        month2.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                        month.remove(time);
                    }

                    break;
                case R.id.next3: //日期选择当前月的第二个月.
                    timeTag = threeTag;
                    any.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                    any.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                    date.add(date.MONTH, 2);
                    time = DateUtil.date2String(date.getTime(), DateUtil.DATE_FORMAT_DATE);
                    if (m3Tag == 1)
                    {
                        month3.setTag(0);
                        month3.setBackgroundResource(R.drawable.shape_btn_yellow_2);
                        month3.setTextColor(getResources().getColor(R.color.color_fb800b));
                        month.add(time);
                    } else
                    {
                        month3.setTag(1);
                        month3.setBackgroundResource(R.drawable.bg_white_solid_gray_stroke);
                        month3.setTextColor(getResources().getColor(R.color.selfedrive_gray_4C4C4C));
                        month.remove(time);
                    }

                    break;
            }
        }
    };


    @Override
    public void getLowPrice(List<LowPriceBean> data)
    {
        handlerData(data);
        if(page>=0 && page<=20){
            refreshUIhandler.sendEmptyMessageDelayed(0,1);
        }

    }

    private Handler refreshUIhandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            srl.setRefreshing(false);
        }
    };




    @Override
    public void onRefresh()
    {
        page = 0;

        sendRequest();
    }

    private void sendRequest(){
        if (flightManager.getCurrentLine() == BaseTicketActivity.TYPE_SINGLE)
        {
            sendSingRequest();
        } else if (flightManager.getCurrentLine() == BaseTicketActivity.TYPE_ROUND)
        {
            sendRoundRequest();
        } else
        {
            sendSingRequest();
        }
    }

    @Override
    public void onItemClickListener(LowPriceBean lowPriceBean)
    {
        FlightManager f = new FlightManager();
        Flight flight = new Flight(false);
        ShippingSpace shippingSpace = new ShippingSpace();
        shippingSpace.setQabinCode(lowPriceBean.getBaseCabinCode());
        shippingSpace.setQabinName(lowPriceBean.getProduct());
        flight.setShippingSpace(shippingSpace);
        flight.setStartCity(flightManager.getCurrentFlight().getStartCity());
        flight.setEndCity(flightManager.getCurrentFlight().getEndCity());
        flight.setStartDate(DateUtil.string2Date(lowPriceBean.getTimeSeres()));
        f.addFlight(flight);
        if (type == BaseTicketActivity.TYPE_ROUND) //往返程多加一个Flight
        {
            Flight flightBack = new Flight(false);
            ShippingSpace shippingSpaceBack = new ShippingSpace();
            shippingSpaceBack.setQabinCode(lowPriceBean.getReturnCabinCode());
            shippingSpaceBack.setQabinName(lowPriceBean.getReturnProduct());
            flightBack.setShippingSpace(shippingSpaceBack);
            flightBack.setStartCity(flightManager.getCurrentFlight().getEndCity());
            flightBack.setEndCity(flightManager.getCurrentFlight().getStartCity());
            flightBack.setStartDate(DateUtil.string2Date(lowPriceBean.getReturnTime()));
            f.addFlight(flightBack);
        }
        f.setAdultNum(flightManager.getAdultNum());
        f.setChildrenNum(flightManager.getChildrenNum());
        f.setBabyNum(flightManager.getBabyNum());
        f.setCurrentLine(type);
        Intent intent = new Intent();
        intent.setClass(this, SingleListActivity.class);
        intent.putExtra("flightManager", f);
        startActivity(intent);
    }

    @Override
    public void getRound(List<LowPriceBean> data)
    {
        if(page==0){
            this.srl.setRefreshing(false);
        }
        handlerData(data);
        if(page>=0 && page<=20){
            refreshUIhandler.sendEmptyMessageDelayed(0,1);
        }

    }


    private void handlerData(List<LowPriceBean> data){
        int size = data.size();

        //首页
        if(page == 0 ){

            datas.clear();

            datas.addAll(data);

            adapter.notifyDataSetChanged();

            if (size == AppConstant.MAX_PAGE_NUM)
            {
                page = size;

            } else
            {
                recyclerView.calByNewNum(size);
            }

        }else{//加载数据

            datas.addAll(data);

            adapter.notifyDataSetChanged();

            if (size == AppConstant.MAX_PAGE_NUM)
            {

                page = datas.size();

                recyclerView.notifyData();

            } else
            {

                recyclerView.calByNewNum(size);

            }
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        String error = o +"";
        if(page == 0 && (HttpConstant.NOINFO1.equals(error)||HttpConstant.NOINFO.equals(error))){
            datas.clear();
            adapter.notifyDataSetChanged();
        }

    }
}
