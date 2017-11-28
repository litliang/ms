package com.yzb.card.king.ui.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.NoRollGridView;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.travel.SearchBarInfo;
import com.yzb.card.king.ui.travel.adapter.TravelPicesAdapter;
import com.yzb.card.king.ui.travel.bean.TravelSearchCriteriaBean;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 旅游 --- 高级搜索
 */
public class TravelHeightSeachActivity extends BaseActivity implements View.OnClickListener {

    //出发日期最早日期标志
    private static final int REQ_CHECK_IN_DATE = 1;
    //出发日期最晚标志
    private static final int REQ_CHECK_OUT_DATE = 2;

    private RelativeLayout rlReset, rlSeach;

    private LinearLayout rlEarliest, rlEnd;

    private TextView txBgn, txEnd, singleBudget, travelDays;

    private LinearLayout panel_back;

    private SeekBar seekBar;

    private int currentDate = REQ_CHECK_IN_DATE;

    private SeekBar seekBarPrice;

    private Date bgn;

    private Date end;

    private NoRollGridView priceView;

    private GridView dayView;
    /**
     * 预算价格适配器
     */
    private TravelPicesAdapter priceAdapter;
    /**
     * 出行天数适配器
     */
    private TravelPicesAdapter dayAdapter;

    private TravelSearchCriteriaBean searchBean;

    private CalendarPop popDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_travel_height_seach);

        getIntentInfo();

        initView();

        initPop();
    }

    private void initPop()
    {
        popDate = new CalendarPop();
        popDate.setMonthCount(6);
        popDate.setListener(new OnItemClickListener<CalendarDay>() {
            @Override
            public void onItemClick(CalendarDay data)
            {

                getDate(currentDate, data.getDay());

                popDate.dismiss();

            }
        });
    }

    private long tempStartDataSys = 0L;

    private long tempEndDatSys = 0L;

    private void getDate(int index, Date data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");

        if (index == REQ_CHECK_IN_DATE) {

            long startDateLong = data.getTime();
            if (startDateLong <= tempEndDatSys || tempEndDatSys == 0) {
                txBgn.setText(sdf.format(data));
                bgn = data;
                tempStartDataSys = startDateLong;
            } else {

                txBgn.setText("");
                bgn = null;
                tempStartDataSys = 0L;
                ToastUtil.i(GlobalApp.getInstance(), "最晚出发时间不能比最早出发时间早");
            }

        } else {
            long endDateLong = data.getTime();
            if (endDateLong >= tempStartDataSys) {
                txEnd.setText(sdf.format(data));
                end = data;
                tempEndDatSys = endDateLong;
            } else {

                end = null;
                txEnd.setText("");
                tempEndDatSys = 0L;
                ToastUtil.i(GlobalApp.getInstance(), "最晚出发时间不能比最早出发时间早");
            }
        }
    }

    private void getIntentInfo()
    {
        Intent i = getIntent();
        if (i != null) {
            searchBean = (TravelSearchCriteriaBean) i.getSerializableExtra("searchBean");
        }
    }



    private void initView()
    {

        rlEarliest = (LinearLayout) findViewById(R.id.earliest);
        rlEnd = (LinearLayout) findViewById(R.id.rl_latest);
        rlReset = (RelativeLayout) findViewById(R.id.rl_reset);
        rlSeach = (RelativeLayout) findViewById(R.id.rl_seach);
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        txBgn = (TextView) findViewById(R.id.tx_bgn);
        txEnd = (TextView) findViewById(R.id.tx_end);
        singleBudget = (TextView) findViewById(R.id.personbudget_unlimited);
        travelDays = (TextView) findViewById(R.id.traveldays_unlimited);
        seekBarPrice = (SeekBar) findViewById(R.id.personbudget_seekbar);
        seekBar = (SeekBar) findViewById(R.id.personbudget_seekbar_two);
        priceView = (NoRollGridView) findViewById(R.id.gridView_price);
        dayView = (GridView) findViewById(R.id.gridView_days);
        priceAdapter = new TravelPicesAdapter(this, SearchBarInfo.priceName);
        dayAdapter = new TravelPicesAdapter(this, SearchBarInfo.daysName);
        priceView.setAdapter(priceAdapter);
        dayView.setAdapter(dayAdapter);

        //单人预算
        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {

                SearchBarInfo.calBudgetPrice(i,seekBar,searchBean,singleBudget,priceAdapter);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        priceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                //根据position重新绘制 信息
                SearchBarInfo.clickCalBudgetPrice(position,seekBarPrice,searchBean,singleBudget,priceAdapter);
            }
        });

        //出行天数
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                SearchBarInfo.calTravelDays(i, seekBar,searchBean,travelDays,dayAdapter);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        dayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                SearchBarInfo.clickCalTravelDays(position, seekBar,searchBean,travelDays,dayAdapter);
            }
        });

        rlEarliest.setOnClickListener(this);

        rlEnd.setOnClickListener(this);

        rlReset.setOnClickListener(this);

        rlSeach.setOnClickListener(this);

        panel_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.panel_back:
                finish();
                break;
            case R.id.earliest: //最早时间
                currentDate = REQ_CHECK_IN_DATE;
                goDateInfo(view);
                break;
            case R.id.rl_latest:  //最晚时间
                currentDate = REQ_CHECK_OUT_DATE;
                goDateInfo(view);
                break;
            case R.id.rl_reset:  //重置
                reSet();
                break;
            case R.id.rl_seach:  //搜索
                setTime();
                if (bgn != null && end != null) {
                    if (DateUtil.naturalDaysBetween(bgn, end) < 0) {
                        ToastUtil.i(this, "出发最晚日期不能小于最早日期");
                        return;
                    }
                }
                Intent intent = new Intent(this, TravelLineListActivity.class);
                intent.putExtra("searchBean", searchBean);
                startActivityForResult(intent, 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            if (resultCode == 1001) {

                if(data != null){

                    TravelSearchCriteriaBean searchBean = (TravelSearchCriteriaBean) data.getSerializableExtra("dataBean");

                    seekBarPrice.setProgress(searchBean.getBudgetPriceProValue());

                    seekBar.setProgress(searchBean.getTravelDaysValue());

                    String startStr = searchBean.getStartStr();

                    if(TextUtils.isEmpty(startStr)){

                        txBgn.setText("");

                        bgn = null;
                    }else{

                        long startLong = Utils.toTimestamp(startStr,7);

                        bgn =  new Date(startLong);

                        txBgn.setText(Utils.toData(startLong,14));
                    }

                    String endStr = searchBean.getEndStr();

                    if(TextUtils.isEmpty(endStr)){

                        txEnd.setText("");

                        end = null;
                    }else{

                        long endLong = Utils.toTimestamp(endStr,7);

                        end =  new Date(endLong);

                        txEnd.setText(Utils.toData(endLong,14));
                    }

                }

            }

        }
    }


    /**
     * 重置
     */
    private void reSet()
    {
        seekBar.setProgress(0);
        seekBarPrice.setProgress(0);
        txBgn.setText("");
        txEnd.setText("");
        bgn = null;
        end = null;
        searchBean.setTheEarliest("");
        searchBean.setTheEndTime("");
        searchBean.setSinglBudget(0 + "");
        searchBean.setSinglBudgetMax(Integer.MAX_VALUE + "");
        searchBean.setDaysCount(0 + "");
        searchBean.setDaysCountMax(Integer.MAX_VALUE + "");
    }

    /**
     * 选择时间
     */
    private void goDateInfo(View view)
    {

        popDate.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
//        Intent it = new Intent(this, CalendarActivity.class);
//        it.putExtra(CalendarActivity.TYPE, CalendarActivity.TYPE_NO_PRICE);
//        startActivity(it);
    }


    /**
     * 设置出发时间
     */
    private void setTime()
    {
        String txStar = txBgn.getText().toString();
        String txlast = txEnd.getText().toString();


        if ((txStar != null && txlast == null) || (!txStar.equals("") && txlast.equals(""))) {
            end = DateUtil.addDay(bgn, 7);
            searchBean.setTheEndTime(DateUtil.date2String(end, DateUtil.DATE_FORMAT_DATE));
            searchBean.setTheEarliest(DateUtil.date2String(bgn, DateUtil.DATE_FORMAT_DATE));
        } else if ((txStar == null && txlast != null) || (txStar.equals("") && !txlast.equals(""))) {
            bgn = new Date();
            Date bgnTime = DateUtil.addDay(end, -7);
            if (DateUtil.naturalDaysBetween(bgn, bgnTime) < 0) {
                searchBean.setTheEarliest(DateUtil.date2String(bgn, DateUtil.DATE_FORMAT_DATE));
            } else {
                bgn = bgnTime;
                searchBean.setTheEarliest(DateUtil.date2String(bgnTime, DateUtil.DATE_FORMAT_DATE));
            }
            searchBean.setTheEndTime(DateUtil.date2String(end, DateUtil.DATE_FORMAT_DATE));
        } else if ((txStar == null && txlast == null) || (txStar.equals("") && txlast.equals(""))) {
            searchBean.setTheEarliest("");
            searchBean.setTheEndTime("");
        } else if ((txStar != null && txlast != null) || (!txStar.equals("") && !txlast.equals(""))) {
            searchBean.setTheEarliest(DateUtil.date2String(bgn, DateUtil.DATE_FORMAT_DATE));
            searchBean.setTheEndTime(DateUtil.date2String(end, DateUtil.DATE_FORMAT_DATE));
        }

        /*
           对最早和最晚出发日期特殊字段处理
         */
        if (!TextUtils.isEmpty(txStar)) {

            searchBean.setStartStr(searchBean.getTheEarliest());
        }else{
            searchBean.setStartStr(null);
        }

        if (!TextUtils.isEmpty(txlast)) {
            searchBean.setEndStr(searchBean.getTheEndTime());
        }else{
            searchBean.setEndStr(null);
        }

    }
}
