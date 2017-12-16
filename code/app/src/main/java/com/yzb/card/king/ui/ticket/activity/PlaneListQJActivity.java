package com.yzb.card.king.ui.ticket.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.PlaneStarEndBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.ticket.adapter.MyConcernAdapter;
import com.yzb.card.king.ui.ticket.fragment.PlaneStarEndFragment;
import com.yzb.card.king.ui.ticket.model.IPlaneListQj;
import com.yzb.card.king.ui.ticket.presenter.PlaneListQjPresenter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaneListQJActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, BaseViewLayerInterface {


    private MyConcernAdapter adapter;
    private String[] startDates = new String[3];

    private SwipeRefreshLayout sRl;
    private RecyclerView list;
    private Bundle bundle = null;
    private TextView tvTitleStar, tvTitleEnd, tvCalendar, tvLastDay;
    private String starCity;
    private String endCity;
    private LinearLayout ivBack;
    private RelativeLayout dateRel;
    private static final int SENDTONGZHI = 1;
    private PlaneListQjPresenter presenter;

    private List<PlaneStarEndBean> planeStarEndBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_list_qj);
        sRl = (SwipeRefreshLayout) findViewById(R.id.qj_srl);
        SwipeRefreshSettings.setAttrbutes(PlaneListQJActivity.this, sRl);
        sRl.setOnRefreshListener(this);
        presenter = new PlaneListQjPresenter(this);
        getIntentInfo();
        init();
        getData();
    }

    /**
     * 获取列表数据
     */
    private void getData()
    {
        String intDate = startDates[0];
        String today = DateUtil.date2String(new Date(), DateUtil.DATE_FORMAT_DATE);

        if (today.equals(intDate))
        {
            setPreLayoutEnable(false);
        } else
        {
            setPreLayoutEnable(true);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("start", starCity);
        param.put("end", endCity);
        param.put("date", startDates[0]);
        param.put("flightNumbers", "");
        presenter.getListInfo(param);
    }

    /**
     * 接收bundle值
     */
    private void getIntentInfo()
    {
        bundle = getIntent().getExtras();
        if (bundle != null)
        {
            starCity = bundle.getString("starCity");
            endCity = bundle.getString("endCity");
            startDates = bundle.getStringArray("dateTime");
        }
    }

    private void setPreLayoutEnable(boolean enable)
    {
        tvLastDay = (TextView) findViewById(R.id.qyt_txt);

        tvLastDay.setEnabled(enable);
    }

    /**
     * 初始化
     */
    private void init()
    {
        tvTitleStar = (TextView) findViewById(R.id.tv_title_star);
        tvTitleStar.setText(starCity);
        tvTitleEnd = (TextView) findViewById(R.id.tv_title_end);
        tvTitleEnd.setText(endCity);
        tvCalendar = (TextView) findViewById(R.id.tv_start_date);
       // String date = DateUtil.date2String(DateUtil.string2Date(startDates[0]), DateUtil.DATE_FORMAT_MONTH_DAY);


        Date date = DateUtil.string2Date(startDates[0]);

        String date1 = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE_DAY2)+"/"+DateUtil.getWeekInf(date);

        tvCalendar.setText(date1);

        ivBack = (LinearLayout) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        dateRel = (RelativeLayout) findViewById(R.id.date_rel);
        dateRel.setOnClickListener(this);

        list = (RecyclerView) findViewById(R.id.qj_plane);
        list.setLayoutManager(new LinearLayoutManager(PlaneListQJActivity.this));
        adapter = new MyConcernAdapter(PlaneListQJActivity.this, planeStarEndBeen);
        adapter.setOnitemClickListener(new MyConcernAdapter.OnitemClickListener() {
            @Override
            public void onItemClickListener(View view, int position)
            {
                Intent intent = new Intent(PlaneListQJActivity.this, PlaneDtDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("hbInfo", planeStarEndBeen.get(position).getFlightNum());
                bundle.putString("hbTime", planeStarEndBeen.get(position).getFlightDate());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);


    }


    /**
     * 刷新数据
     */
    @Override
    public void onRefresh()
    {
        getData();
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back: //返回
                finish();
                break;
            case R.id.date_rel: //选择日期
                CalendarManager.getInstance().setDateLoadFlag(false);
                Intent it = new Intent(this, CalendarActivity.class);
                it.putExtra(CalendarActivity.TYPE, CalendarActivity.TYPE_NO_PRICE);
                startActivity(it);
                PlaneStarEndFragment.flagFlag = false;
                break;
        }
    }

    /**
     * 前一天后一天选中刷新数据
     *
     * @param v
     */
    public void refresh(View v)
    {
        //前一天
        if (v.getId() == R.id.pre_layout_list)
        {
            Date preDate = (DateUtil.addDay(DateUtil.string2Date(startDates[0]), -1));

            if (preDate.getTime() <= getToday()) return;
            startDates[0] = DateUtil.date2String(preDate, DateUtil.DATE_FORMAT_DATE);
        }

        //后一天
        if (v.getId() == R.id.next_layout_list)
        {
            startDates[0] = DateUtil.date2String(DateUtil.addDay(DateUtil.string2Date(startDates[0]), 1), DateUtil.DATE_FORMAT_DATE);
        }

        Date date = DateUtil.string2Date(startDates[0]);

        String date1 = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE_DAY2)+"/"+DateUtil.getWeekInf(date);
        tvCalendar.setText(date1);
        getData();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    private long getToday()
    {
        Date date = new Date();
        String dateStr = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);
        Date today = DateUtil.string2Date(dateStr, DateUtil.DATE_FORMAT_DATE);

        return DateUtil.addDay(today, -1).getTime();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        PlaneStarEndFragment.flagFlag = true;
        if (null != CalendarManager.getInstance().getDate())
        {
            Date date = CalendarManager.getInstance().getDate();
            tvCalendar.setText(DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE_DAY2)+"/"+DateUtil.getWeekInf(date));
            startDates[0] = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);
            getData();
            CalendarManager.getInstance().clearData();
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
//        Aexpected = "09:42:00"
//        Airline = "奥地利航空"
//        AirlineCode = null
//        ArrCity = "北京首都"
//        ArrCode = "PEK"
//        ArrTerminal = "T3"
//        ArrTime = "10:10"
//        DepCity = "上海虹桥"
//        DepCode = "SHA"
//        DepTerminal = "T2"
//        DepTime = "07:45"
//        Dexpected = "07:57:00"
//        FlightDate = "2017-12-16"
//        FlightNum = "OS8002"
//        OnTimeRate = "90%"
        sRl.setRefreshing(false);
        if (type == IPlaneListQj.GET_LIST_INFO)
        {
            if (o instanceof List)
            {
                planeStarEndBeen.clear();
                List<PlaneStarEndBean> planeStarEndBeens = (List<PlaneStarEndBean>) o;
                planeStarEndBeen.addAll(planeStarEndBeens);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        sRl.setRefreshing(false);
        planeStarEndBeen.clear();
        adapter.notifyDataSetChanged();
        if (o == null)
        {
            ToastUtil.i(this, "网络请求超时！");
        }
        if (o != null && o instanceof Map)
        {

            Map<String, String> onSuccessData = (Map<String, String>) o;

            if (onSuccessData.get(HttpConstant.SERVER_ERROR).equals("Error Start or End!"))
            {
                ToastUtil.i(GlobalApp.getInstance().getContext(),
                        "无机票信息");
            } else if (onSuccessData.get(HttpConstant.SERVER_CODE).equals("9999"))
            {
                ToastUtil.i(this, "无机票信息");
            } else
            {
                ToastUtil.i(GlobalApp.getInstance().getContext(),
                        onSuccessData.get(HttpConstant.SERVER_ERROR));
            }


        }
    }
}
