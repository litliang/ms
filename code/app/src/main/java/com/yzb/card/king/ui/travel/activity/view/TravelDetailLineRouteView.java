package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.travel.adapter.MealAdapter;
import com.yzb.card.king.ui.travel.adapter.RouteScheduleAdapter;
import com.yzb.card.king.ui.travel.adapter.SimpleScheAdapter;
import com.yzb.card.king.ui.travel.adapter.TrafficAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.RecyclerViewUtil;

import java.util.List;

/**
 * 功能：旅游详情-->线路介绍view；
 *
 * @author:gengqiyun
 * @date: 2016/11/12
 */
public class TravelDetailLineRouteView extends BaseViewGroup
{
    private TextView tvHotelInfo; //住宿；
    private TextView tvAllScenerySpotNum; //景点总共；
    private TextView tvClassicsSpotNum;
    private TextView tvScenerySpotList;

    private TextView tvfeatureGroupDinner;

    private RecyclerView multTrafficRecyView;
    private TextView tvTrafficTimeType;

    private WholeListView photoTxtListView; //图文模式；
    private WholeListView simpleRouteListView; //简单行程；
    private RouteScheduleAdapter scheduleAdapter;
    private RecyclerView dinnerRecyclerView;
    private SimpleScheAdapter simpleScheAdapter; //简单行程adapter；

    public TravelDetailLineRouteView(Context context)
    {
        super(context);
    }

    public TravelDetailLineRouteView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TravelDetailLineRouteView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_line_route;
    }

    @Override
    protected void init()
    {
        super.init();
        //住宿；
        tvHotelInfo = (TextView) rootView.findViewById(R.id.tvHotelInfo);
        //景点；
        tvAllScenerySpotNum = (TextView) rootView.findViewById(R.id.tvAllScenerySpotNum);
        tvClassicsSpotNum = (TextView) rootView.findViewById(R.id.tvClassicsSpotNum);
        tvScenerySpotList = (TextView) rootView.findViewById(R.id.tvScenerySpotList);

        //用餐；
        tvfeatureGroupDinner = (TextView) rootView.findViewById(R.id.tvfeatureGroupDinner);

        dinnerRecyclerView = (RecyclerView) rootView.findViewById(R.id.dinnerRecyclerView);
        dinnerRecyclerView.setFocusable(false);

        RecyclerViewUtil.init(mContext, dinnerRecyclerView, LinearLayoutManager.HORIZONTAL, 0);

        //交通；
        tvTrafficTimeType = (TextView) rootView.findViewById(R.id.tvTrafficTimeType);
        multTrafficRecyView = (RecyclerView) rootView.findViewById(R.id.multTrafficRecyclerView);

        multTrafficRecyView.setFocusable(false);

        RecyclerViewUtil.init(mContext, multTrafficRecyView, LinearLayoutManager.HORIZONTAL, 0);

        photoTxtListView = (WholeListView) rootView.findViewById(R.id.photoTxtListView);

        photoTxtListView.setFocusable(false);

        scheduleAdapter = new RouteScheduleAdapter(mContext);
        photoTxtListView.setAdapter(scheduleAdapter);

        simpleRouteListView = (WholeListView) rootView.findViewById(R.id.simpleRouteListView);
        simpleRouteListView.setFocusable(false);

        simpleScheAdapter = new SimpleScheAdapter(mContext);
        simpleRouteListView.setAdapter(simpleScheAdapter);
        refreshLineIntro(null);
        simpleRouteListView.setVisibility(GONE);
    }

    /**
     * true:图文模式；
     * false：简单模式；
     */
    private boolean curMode = true; //默认图文模式；

    public boolean getCurMode()
    {
        return curMode;
    }

    /**
     * 模式切换；
     */
    public void switchMode()
    {
        curMode = !curMode;
        //简单行程；
        photoTxtListView.setVisibility(curMode ? VISIBLE : GONE);
        simpleRouteListView.setVisibility(curMode ? GONE : VISIBLE);
    }

    /**
     * 数据刷新；
     *
     * @param lineBean
     */
    public void refreshLineIntro(TravelLineBean lineBean)
    {
        String hotelInfo = mContext.getString(R.string.travel_nohotel_info);
        String allScenerySpotNum = "0";
        String classicsSpotNum = "0";
        String scenerySpot = "";
        String featureGroupDinner = "";

        if (lineBean != null)
        {
            hotelInfo = !isEmpty(lineBean.getHotelName()) ? lineBean.getHotelName() : hotelInfo;

            allScenerySpotNum = lineBean.getSpotQuantity() + "";
            classicsSpotNum = lineBean.getClassicSpotQuantity() + "";

            String clasicSceSpots = lineBean.getClassicSpotDesc();
            if (!isEmpty(clasicSceSpots))
            {
                scenerySpot = CommonUtil.getSubStr(clasicSceSpots.replace(',', ' '), 15);
            }
            List<TravelLineBean.Meal> meals = lineBean.getMealsList();
            dinnerRecyclerView.setAdapter(new MealAdapter(meals, mContext));

            featureGroupDinner = getFeatureDinner(meals);

            initTrafficViewData(lineBean.getTrafficList());

            //日程安排；
            scheduleAdapter.clearAll();
            scheduleAdapter.appendALL(lineBean.getScheduleList());
            photoTxtListView.setAdapter(scheduleAdapter);

            //简单行程；
            simpleScheAdapter.clearAll();
            simpleScheAdapter.appendALL(lineBean.getScheduleList());
            simpleRouteListView.setAdapter(simpleScheAdapter);
        }

        tvHotelInfo.setText(hotelInfo);
        //景点初始化；
        tvAllScenerySpotNum.setText(mContext.getString(R.string.travel_detail_per, allScenerySpotNum));
        tvClassicsSpotNum.setText(mContext.getString(R.string.travel_detail_per, classicsSpotNum));
        tvScenerySpotList.setText(scenerySpot);

        tvfeatureGroupDinner.setVisibility(isEmpty(featureGroupDinner) ? GONE : VISIBLE);
        //特色团餐；
        tvfeatureGroupDinner.setText(mContext.getString(R.string.travel_detail_feature_dinner, featureGroupDinner));
    }

    /**
     * 获取特色团餐；
     *
     * @param meals
     * @return
     */
    private String getFeatureDinner(List<TravelLineBean.Meal> meals)
    {
        String result = "";
        if (meals != null)
        {
            //特色团餐；
            for (int i = 0; i < meals.size(); i++)
            {
                if ("3".equals(meals.get(i).getMealsMode()))
                {
                    result = meals.get(i).getMealsDesc();
                    break;
                }
            }
        }
        return TextUtils.isEmpty(result) ? "" : CommonUtil.getSubStr(result.replace(',', ' '), 15);
    }

    /**
     * 初始化交通view；
     *
     * @param trafficList
     */
    public void initTrafficViewData(List<TravelLineBean.Traffic> trafficList)
    {
        if (trafficList == null || trafficList.size() == 0)
        {
            tvTrafficTimeType.setVisibility(GONE);
            multTrafficRecyView.setVisibility(GONE);
            return;
        }
        int size = trafficList.size();
        tvTrafficTimeType.setVisibility(size == 1 ? VISIBLE : GONE);
        multTrafficRecyView.setVisibility(size > 1 ? VISIBLE : GONE);
        if (size == 1)
        {
            TravelLineBean.Traffic bean = trafficList.get(0);
            tvTrafficTimeType.setText(CommonUtil.formatMmToHh(bean.getTrafficLength()) + "\u3000" + bean.getTrafficName());
        } else
        {
            multTrafficRecyView.setAdapter(new TrafficAdapter(trafficList, mContext));
        }
    }

}
