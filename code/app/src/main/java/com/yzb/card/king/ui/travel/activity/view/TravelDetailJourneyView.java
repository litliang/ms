package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.app.activity.PdfActivity;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.DividerDecoration;
import com.yzb.card.king.ui.appwidget.MeasureHeightLinearLayout;
import com.yzb.card.king.ui.travel.adapter.TravelDateAdapter;
import com.yzb.card.king.ui.travel.view.ITravelDetailDataProvider;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.common.util.DensityUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 功能：旅游详情-->5日行程view；
 *
 * @author:gengqiyun
 * @date: 2016/11/12
 */
public class TravelDetailJourneyView extends BaseViewGroup implements View.OnClickListener
{
    private View panelLinesSelect; //线路选择，1个线路时，隐藏；
    private TextView tvStartPlace;
    private TextView tvStartPlaceIntro;
    private RecyclerView dateRecyclerView;
    private TravelDateAdapter dateAdapter;
    private TravelDetailLineRouteView lineRouteView; //线路介绍；非锦江且图文模式；
    private View panelLineRouteJj; //线路介绍(锦江模式)；
    private int titleMeasureHeight;//行程介绍标题块的高度；
    private TravelDetailLinesView detailLinesView;  //列表列表；
    private TravelLineBean lineBean;

    public TravelDetailJourneyView(Context context)
    {
        super(context);
    }

    public TravelDetailJourneyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        panelLinesSelect = rootView.findViewById(R.id.panelLinesSelect);
        initDateRecyclerView(rootView);

        rootView.findViewById(R.id.panelStartPalace).setOnClickListener(this);
        tvStartPlace = (TextView) rootView.findViewById(R.id.tvStartPlace);
        tvStartPlaceIntro = (TextView) rootView.findViewById(R.id.tvStartPlaceIntro);

        rootView.findViewById(R.id.panelMoreDate).setOnClickListener(this);

        //锦江模式；
        panelLineRouteJj = rootView.findViewById(R.id.panelLineRouteJj);
        panelLineRouteJj.setVisibility(GONE);
        rootView.findViewById(R.id.panelLineIntroPdf).setOnClickListener(this);
        //线路介绍；
        lineRouteView = (TravelDetailLineRouteView) rootView.findViewById(R.id.lineRouteView);
        lineRouteView.setVisibility(VISIBLE);

        MeasureHeightLinearLayout tvMeasureLayout = (MeasureHeightLinearLayout) rootView.findViewById(R.id.tvMeasureLayout);
        tvMeasureLayout.setCallBack(new MeasureHeightLinearLayout.ICallBack()
        {
            @Override
            public void heightChange(int newHeight)
            {
                titleMeasureHeight = newHeight;
            }
        });
        detailLinesView = (TravelDetailLinesView) rootView.findViewById(R.id.detailLinesView);
        detailLinesView.setNeedRefresh(false);
    }

    @Override
    public void setDataProvider(ITravelDetailDataProvider provider)
    {
        super.setDataProvider(provider);
        //此处必须要设置数据提供者；
        lineRouteView.setDataProvider(this.provider);
        detailLinesView.setDataProvider(this.provider);
    }

    public int getTitleMeasureHeight()
    {
        return titleMeasureHeight;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_journey;
    }

    private void initDateRecyclerView(View rootView)
    {
        dateRecyclerView = (RecyclerView) rootView.findViewById(R.id.dateRecyclerView);
        dateRecyclerView.setFocusable(false);
        //设置水平方向；
        LinearLayoutManager llm2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        dateRecyclerView.setLayoutManager(llm2);
        DividerDecoration divider2 = new DividerDecoration(mContext, DividerDecoration.HORIZONTAL_LIST,
                R.drawable.listview_divider_width_16dp);
        dateRecyclerView.addItemDecoration(divider2);

        LinearLayout.LayoutParams llLp = (LayoutParams) dateRecyclerView.getLayoutParams();

        int w = DensityUtil.getScreenWidth() - DensityUtil.dip2px(111);
        llLp.width = w;
        llLp.height = RecyclerView.LayoutParams.MATCH_PARENT;
        dateRecyclerView.setLayoutParams(llLp);

        dateAdapter = new TravelDateAdapter(mContext);
        dateAdapter.setAttachRecyView(dateRecyclerView);
        dateRecyclerView.setAdapter(dateAdapter);
    }

    /**
     * 模式切换；
     */
    public void switchMode()
    {
        lineRouteView.switchMode();
    }

    public boolean getCurMode()
    {
        return lineRouteView.getCurMode();
    }

    @Override
    public void fillViewTravelLines(List<TravelLineBean> lineBeans)
    {
        int lineLen = lineBeans.size();
        panelLinesSelect.setVisibility(lineLen > 1 ? VISIBLE : GONE);
        detailLinesView.notifyTravelLineChanged();
    }

    @Override
    protected void fillViewData(TravelProduDetailBean detailBean)
    {
        tvStartPlace.setText(detailBean.getFromPlace() + "出发");
    }

    /**
     * 出发地切换时，设置目的地；
     *
     * @param departure
     */
    public void setDeparture(String departure)
    {
        if (provider == null || provider.getProduDetailBean() == null || isEmpty(departure)) return;
        //真实出发地；
        String actuaDeparture = provider.getProduDetailBean().getFromPlace();

        boolean equals = departure.contains(actuaDeparture) || (!isEmpty(actuaDeparture) && actuaDeparture.contains(departure));
        //选择“出发地”和“真正出发地”不一样时，出发地后面加：“出发地到真正出发地的费用自理”
        tvStartPlace.setText(departure);

        tvStartPlaceIntro.setVisibility(equals ? INVISIBLE : VISIBLE);
        tvStartPlaceIntro.setText(mContext.getString(R.string.travel_city_diff_intro, departure, actuaDeparture));
    }

    /**
     * 通知线路更新；
     */
    public void notifyChangeTravelLine()
    {
        detailLinesView.notifyTravelLineChanged();
    }

    /**
     * 线路切换后刷新数据；
     */
    public void refreshData(TravelLineBean lineBean)
    {
//        if (dateAdapter != null)
//        {
//            dateAdapter.clearAll();
//        }

        this.lineBean = lineBean;
        if (lineBean != null)
        {
            //选中相应的线路；
            detailLinesView.selectItem(lineBean);
            LogUtil.i("该线路的lineUrl=" + lineBean.getLineUrl());

            boolean empty = isEmpty(lineBean.getLineUrl());
            //线路介绍(锦江模式)；
            panelLineRouteJj.setVisibility(empty ? GONE : VISIBLE);
            lineRouteView.setVisibility(empty ? VISIBLE : GONE);

            if (empty)
            {
                //刷新线路介绍；
                lineRouteView.refreshLineIntro(lineBean);
            }
        }
    }

    public DateBean getSelectDateBean()
    {
        if (dateAdapter != null)
        {
            return dateAdapter.getSelectItem();
        }
        return null;
    }

    /**
     * 获取当前选中的日期；
     */
    public Date getSelectDate()
    {
        DateBean selectDateBean = getSelectDateBean();
        if (selectDateBean != null)
        {
            Calendar calendar = Calendar.getInstance();
            String dateStr = selectDateBean.getDepDate();
            calendar.setTime(DateUtil.string2Date(dateStr, DateUtil.DATE_FORMAT_DATE));

            LogUtil.i("选中的日期==" + calendar.getTime());
            return calendar.getTime();
        }
        return null;
    }

    @Override
    public void fillViewDateList(List<DateBean> dateBeans)
    {
        dateAdapter.clearAll();
        dateAdapter.appendALL(dateBeans);

        dateAdapter.selectItem();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelStartPalace: //更多出发地；
                TravelLineBean travelLineBean = detailLinesView.getSelectLine();
                provider.selectDeparture(travelLineBean == null ? "" : travelLineBean.getLineId());
                break;
            case R.id.panelMoreDate: //更多日期；
                provider.selectMoreDate();
                break;
            case R.id.panelLineIntroPdf: //行程介绍(锦江模式时 可用, 打开pdf)；
                Intent intentDp = new Intent(mContext, PdfActivity.class);
                intentDp.putExtra(PdfActivity.ARG_URL, lineBean.getLineUrl());
                intentDp.putExtra(PdfActivity.ARG_TITLE, mContext.getString(R.string.travel_line_intro));
                mContext.startActivity(intentDp);
                break;
        }
    }

}
