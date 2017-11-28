package com.yzb.card.king.ui.ticket.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.BaseGridLayoutNoSlideLayoutManager;
import com.yzb.card.king.ui.appwidget.TicketFilterView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.FilterManager;
import com.yzb.card.king.ui.ticket.adapter.AcftAdapter;
import com.yzb.card.king.ui.ticket.adapter.ArrAirPortAdapter;
import com.yzb.card.king.ui.ticket.adapter.ArrtimeAdapter;
import com.yzb.card.king.ui.ticket.adapter.BasecabincodeAdapter;
import com.yzb.card.king.ui.ticket.adapter.CarrierAdapter;
import com.yzb.card.king.ui.ticket.adapter.DepairportAdapter;
import com.yzb.card.king.ui.ticket.adapter.DeptimeAdapter;
import com.yzb.card.king.ui.ticket.adapter.FilterBaseOnClickListener;
import com.yzb.card.king.ui.ticket.interfaces.FilterTypes;
import com.yzb.card.king.ui.ticket.presenter.FilterPresenter;
import com.yzb.card.king.ui.ticket.view.FilterView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ContentView(value = R.layout.activity_filter)
public class FilterActivity extends BaseActivity implements FilterView
        , FilterBaseOnClickListener
{

    private FilterPresenter presenter;
    @ViewInject(value = R.id.btn_group)
    private LinearLayout btnGroup;

    // 功能视图 决定是否显示

    // 起抵时间
    @ViewInject(value = R.id.view_time)
    private LinearLayout viewTime;

    // 起抵机场
    @ViewInject(value = R.id.view_airport)
    private LinearLayout viewAirport;

    // 机型
    @ViewInject(value = R.id.view_acft)
    private LinearLayout viewAcft;

    // 仓位
    @ViewInject(value = R.id.view_basecabincode)
    private LinearLayout viewBasecabincode;

    // 航空公司
    @ViewInject(value = R.id.view_carrier)
    private LinearLayout viewCarrier;


    // 航空公司Recycler
    @ViewInject(value = R.id.selector_carrier)
    private RecyclerView selectorCarrier;

    // 起始时间
    @ViewInject(value = R.id.selector_deptime)
    private RecyclerView selectorDeptime;


    // 抵达时间
    @ViewInject(value = R.id.selector_arrtime)
    private RecyclerView selectorArrtime;


    // 起始机场
    @ViewInject(value = R.id.selector_depairport)
    private RecyclerView selectorDepairport;


    // 抵达机场
    @ViewInject(value = R.id.selector_arrairport)
    private RecyclerView selectorArrairport;


    // 机型
    @ViewInject(value = R.id.selector_acft)
    private RecyclerView selectorAcft;

    // 仓位
    @ViewInject(value = R.id.selector_basecabincode)
    private RecyclerView selectorBasecabincode;

    private AcftAdapter acftAdapter;
    private ArrAirPortAdapter arrAirPortAdapter;
    private ArrtimeAdapter arrtimeAdapter;
    private BasecabincodeAdapter basecabincodeAdapter;
    private CarrierAdapter carrierAdapter;
    private DepairportAdapter depairportAdapter;
    private DeptimeAdapter deptimeAdapter;

    // 起始时间
    private List<FilterType> depTime = new ArrayList();
    // 抵达时间
    private List<FilterType> arrTime = new ArrayList();
    // 起始机场
    private List<FilterType> depAirport = new ArrayList();
    // 抵达机场
    private List<FilterType> arrAirport = new ArrayList();
    // 仓位
    private List<FilterType> flightQabin = new ArrayList();
    // 航空公司
    private List<FilterType> carrier = new ArrayList();
    // 机型
    private List<FilterType> acft = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (presenter == null)
        {
            presenter = new FilterPresenter(this);
        }

        initView();

        initData();
    }

    private void initView()
    {
        acftAdapter = new AcftAdapter(acft, this);
        acftAdapter.setOnClickListener(this);

        arrAirPortAdapter = new ArrAirPortAdapter(arrAirport, this);
        arrAirPortAdapter.setOnClickListener(this);

        arrtimeAdapter = new ArrtimeAdapter(arrTime, this);
        arrtimeAdapter.setOnClickListener(this);

        basecabincodeAdapter = new BasecabincodeAdapter(flightQabin, this);
        basecabincodeAdapter.setOnClickListener(this);

        carrierAdapter = new CarrierAdapter(carrier, this);
        carrierAdapter.setOnClickListener(this);

        depairportAdapter = new DepairportAdapter(depAirport, this);
        depairportAdapter.setOnClickListener(this);

        deptimeAdapter = new DeptimeAdapter(depTime, this);
        deptimeAdapter.setOnClickListener(this);

        selectorAcft.setLayoutManager(new BaseGridLayoutNoSlideLayoutManager(this, 3));
        selectorAcft.setAdapter(acftAdapter);
        selectorArrairport.setLayoutManager(new BaseGridLayoutNoSlideLayoutManager(this, 3));
        selectorArrairport.setAdapter(arrAirPortAdapter);
        selectorArrtime.setLayoutManager(new BaseGridLayoutNoSlideLayoutManager(this, 3));
        selectorArrtime.setAdapter(arrtimeAdapter);
        selectorBasecabincode.setLayoutManager(new BaseGridLayoutNoSlideLayoutManager(this, 3));
        selectorBasecabincode.setAdapter(basecabincodeAdapter);
        selectorCarrier.setLayoutManager(new BaseGridLayoutNoSlideLayoutManager(this, 3));
        selectorCarrier.setAdapter(carrierAdapter);
        selectorDepairport.setLayoutManager(new BaseGridLayoutNoSlideLayoutManager(this, 3));
        selectorDepairport.setAdapter(depairportAdapter);
        selectorDeptime.setLayoutManager(new BaseGridLayoutNoSlideLayoutManager(this, 3));
        selectorDeptime.setAdapter(deptimeAdapter);
    }


    private void initData()
    {
        //起飞时间、抵达时间、起飞机场、抵达机场、机型、仓位和航空公司
        String url = AppConstant.deptime + ","
                + AppConstant.arrtime + "," + AppConstant.depAirPort + "," + AppConstant.arrAirPort
                + "," + AppConstant.acft + "," + AppConstant.flightQabin
                + "," + AppConstant.carrier;

        Flight flight = FilterManager.getInstance().getCurrentFlight();
        Map<String, Object> params = new HashMap<>();
        params.put("type", url);
        params.put("arrCityId", flight.getEndCity().getCityId());//目的城市id  3690
        params.put("depCityId", flight.getStartCity().getCityId());//出发城市id 52315
        presenter.sendFilterRequest(params, CardConstant.ticket_filter);
    }

    @Event(value = {R.id.btn_ok, R.id.btn_cancel, R.id.btn_reset, R.id.relativeLayout3})
    private void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.relativeLayout3:
                finish();
                break;
            case R.id.btn_ok:

                // 保存数据
                saveFilter();

                finish();

                break;
            case R.id.btn_reset:
                ToastUtil.i(this, getString(R.string.toast_has_been_set));
                //清理缓存数据
                FilterManager.getInstance().setSelectedConditionList(null);
                // 起飞时间重置
                deptimeAdapter.reSet();
                selectorDeptime.getAdapter().notifyDataSetChanged();

                arrtimeAdapter.reSet();
                selectorArrtime.getAdapter().notifyDataSetChanged();

                acftAdapter.reSet();
                selectorAcft.getAdapter().notifyDataSetChanged();

                basecabincodeAdapter.reSet();
                selectorBasecabincode.getAdapter().notifyDataSetChanged();

                carrierAdapter.reSet();
                selectorCarrier.getAdapter().notifyDataSetChanged();


                arrAirPortAdapter.reSet();
                selectorArrairport.getAdapter().notifyDataSetChanged();

                depairportAdapter.reSet();
                selectorDepairport.getAdapter().notifyDataSetChanged();

                break;
            case R.id.btn_cancel:
                finish();
                break;
        }

    }

    private void saveFilter()
    {

        List<List<FilterType>> conditionsList = new ArrayList<>();
        // 机型
        conditionsList.add(acftAdapter.getSelectedDatas());
        // 起始时间
        conditionsList.add(deptimeAdapter.getSelectedDatas());
        // 抵达时间
        conditionsList.add(arrtimeAdapter.getSelectedDatas());
        // 起始机场
        conditionsList.add(depairportAdapter.getSelectedDatas());
        // 抵达机场
        conditionsList.add(arrAirPortAdapter.getSelectedDatas());
        // 航空公司
        conditionsList.add(carrierAdapter.getSelectedDatas());
        // 仓位
        conditionsList.add(basecabincodeAdapter.getSelectedDatas());

        int size = conditionsList.size();

        List<com.yzb.card.king.bean.ticket.FilterType> list = new ArrayList<com.yzb.card.king.bean.ticket.FilterType>();

        for (int i = 0; i < size; i++)
        {

            List<FilterType> listMap = conditionsList.get(i);

            int size1 = listMap.size();
            for (int j = 0; j < size1; j++)
            {
                if(!"0".equals(listMap.get(j).getCode()))
                {
                    list.add(listMap.get(j));
                }
            }

        }
        TicketFilterView.filterData.setFilterTypes(list);

        FilterManager.getInstance().setSelectedConditionList(list);
    }


    @Override
    public void getFilter(List<Map> filterBeen)
    {

        // 显示底部按钮
        btnGroup.setVisibility(View.VISIBLE);

        LogUtil.LCi(JSON.toJSONString(filterBeen) + "    大小  " + filterBeen.size());
        // 是否有起飞时间
        depTime.clear();
        depTime.addAll(JSON.parseArray(JSON.toJSONString(filterBeen.get(0).get("deptime")), FilterType.class));
        LogUtil.LCi("  转换之后 depTime  " + JSON.toJSONString(depTime));
        // 是否有抵达时间
        arrTime.clear();
        arrTime.addAll(JSON.parseArray(JSON.toJSONString(filterBeen.get(1).get("arrtime")), FilterType.class));
        LogUtil.LCi("  转换之后 arrTime  " + JSON.toJSONString(arrTime));
        // 起始机场
        depAirport.clear();
        depAirport.addAll(JSON.parseArray(JSON.toJSONString(filterBeen.get(2).get("depAirPort")), FilterType.class));
        LogUtil.LCi("  转换之后 depAirport  " + JSON.toJSONString(depAirport));
        // 抵达机场
        arrAirport.clear();
        arrAirport.addAll(JSON.parseArray(JSON.toJSONString(filterBeen.get(3).get("arrAirPort")), FilterType.class));
        LogUtil.LCi("  转换之后  arrAirport " + JSON.toJSONString(arrAirport));
        // 机型
        acft.clear();
        acft.addAll(JSON.parseArray(JSON.toJSONString(filterBeen.get(4).get("acft")), FilterType.class));
        LogUtil.LCi("  转换之后 acft  " + JSON.toJSONString(acft));
        // 仓位
        flightQabin.clear();
        flightQabin.addAll(JSON.parseArray(JSON.toJSONString(filterBeen.get(5).get("flightQabin")), FilterType.class));
        LogUtil.LCi("  转换之后  flightQabin " + JSON.toJSONString(flightQabin));
        // 航空公司
        carrier.clear();
        carrier.addAll(JSON.parseArray(JSON.toJSONString(filterBeen.get(6).get("carrier")), FilterType.class));
        LogUtil.LCi("  转换之后 carrier  " + JSON.toJSONString(carrier));

        selectorAcft.getAdapter().notifyDataSetChanged();
        selectorArrairport.getAdapter().notifyDataSetChanged();
        selectorArrtime.getAdapter().notifyDataSetChanged();
        selectorBasecabincode.getAdapter().notifyDataSetChanged();
        selectorCarrier.getAdapter().notifyDataSetChanged();
        selectorDepairport.getAdapter().notifyDataSetChanged();
        selectorDeptime.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onClick(int postition, int type)
    {
        switch (type)
        {
            case FilterTypes.DEPTIME:
                selectorDeptime.getAdapter().notifyDataSetChanged();
                break;
            case FilterTypes.ARRTIME:
                selectorArrtime.getAdapter().notifyDataSetChanged();
                break;
            case FilterTypes.ACFT:
                selectorAcft.getAdapter().notifyDataSetChanged();
                break;
            case FilterTypes.ARRAIRPORT:
                selectorArrairport.getAdapter().notifyDataSetChanged();
                break;
            case FilterTypes.CARRIER:
                selectorCarrier.getAdapter().notifyDataSetChanged();
                break;
            case FilterTypes.DEPAIRPORT:
                selectorDepairport.getAdapter().notifyDataSetChanged();
                break;
            case FilterTypes.FLIGHTQABIN:
                selectorBasecabincode.getAdapter().notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        Toast.makeText(this, o+"", Toast.LENGTH_SHORT).show();
    }
}