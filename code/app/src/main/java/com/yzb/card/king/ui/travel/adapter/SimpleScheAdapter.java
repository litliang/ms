package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.travel.view.ITextClickCall;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 功能：旅游行程安排  简单行程；
 *
 * @author:gengqiyun
 * @date: 2016/11/26
 */
public class SimpleScheAdapter extends BaseListAdapter<TravelLineBean.Schedule>
{

    public SimpleScheAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_listview_route_schedue, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TravelLineBean.Schedule routeScheBean = getItem(position);

        viewHolder.tvDay.setText("第" + CommonUtil.numberToStr(routeScheBean.getScheduleDay()) + "天");
        viewHolder.tvTravelSummay.setText(routeScheBean.getScheduleName());

        TravelDayTimeSimpleAdapter dayTimeAdapter = new TravelDayTimeSimpleAdapter(mContext);
        //数据重组；planType;   //String	计划类型	N	1用餐、2景点、3住宿、4交通 抽取出4种类型；
        Map<String, ArrayList<TravelLineBean.MyPlan>> map = CommonUtil.sort(routeScheBean.getPlanList());

        List<TravelLineBean.MyPlan> plans = convertMapToList(map);
        dayTimeAdapter.setList(plans);

        LogUtil.i("第" + position + "天概要=" + JSON.toJSONString(plans));

        viewHolder.dayTimeListView.setAdapter(dayTimeAdapter);

        return viewHolder.root;
    }

    /**
     * Map转换为List；
     *
     * @param map
     * @return
     */
    private List<TravelLineBean.MyPlan> convertMapToList(Map<String, ArrayList<TravelLineBean.MyPlan>> map)
    {
        List<TravelLineBean.MyPlan> plans = new ArrayList<>();
        if (map != null)
        {
            TravelLineBean.MyPlan myPlan;
            for (Map.Entry<String, ArrayList<TravelLineBean.MyPlan>> entry : map.entrySet())
            {
                myPlan = new TravelLineBean.MyPlan();
                myPlan.setPlanType(entry.getKey());
                myPlan.setTrafficCode(entry.getValue().get(0).getTrafficCode());
                myPlan.setContactSeq(concatStr(entry.getKey(), entry.getValue()));
                plans.add(myPlan);
            }
        }
        return plans;
    }


    /**
     * 组合关键字；
     *
     * @param key
     * @param plans
     * @return
     */
    private CharSequence concatStr(String key, ArrayList<TravelLineBean.MyPlan> plans)
    {
        CharSequence sequence = null;
        if (plans != null)
        {
            //1用餐、2景点、3住宿、4交通
            if ("1".equals(key))
            {
                sequence = concatMeal(plans);
            } else if ("2".equals(key))
            {
                sequence = concatSpot(plans);
            } else if ("3".equals(key))
            {
                sequence = concatHotel(plans);
            } else if ("4".equals(key))
            {
                sequence = concatTraffic(plans);
            }
        }
        return sequence;
    }

    private String concatTraffic(ArrayList<TravelLineBean.MyPlan> plans)
    {
        String result = "交通：";
        for (TravelLineBean.MyPlan myPlan : plans)
        {
            result += myPlan.getPlanSummary() + "\n";
        }
        return result;
    }

    /**
     * 组合酒店名称；
     *
     * @param plans
     * @return
     */
    private CharSequence concatHotel(ArrayList<TravelLineBean.MyPlan> plans)
    {
        CharSequence seqResult = null;
        String result = "";
        List<TravelLineBean.MyHotel> keys = new ArrayList<>();

        for (TravelLineBean.MyPlan myPlan : plans)
        {
            if (myPlan.getHotelList() != null)
            {
                for (TravelLineBean.MyHotel myHotel : myPlan.getHotelList())
                {
                    //排除相同名称的酒店；
                    if (!result.contains(myHotel.getHotelName()))
                    {
                        result += (keys.size() == 0 ? myHotel.getHotelName() : " 或 " + myHotel.getHotelName()) + "\n";
                        keys.add(myHotel);
                    }
                }
            }
        }

        // 标注特殊的酒店；
        for (int i = 0; i < keys.size(); i++)
        {
            seqResult = CommonUtil.colourTxt(result,
                    keys.get(i).getHotelName(), mContext.getResources().getColor(R.color.blue3),
                    keys.get(i).getHotelId(), new ITextClickCall()
                    {
                        @Override
                        public void callBack(Object... args)
                        {
                            LogUtil.i("点击的酒店的id=" + args);
                            if (args != null && args.length == 1)
                            {
                                LogUtil.i("点击的酒店的id=" + args[0]);

//                                Intent intent = new Intent(mContext, HotelDetailActivity.class);
//                                intent.putExtra("id", String.valueOf(args[0]));
//                                mContext.startActivity(intent);
                            }
                        }
                    });
        }
        seqResult = CommonUtil.colourTxt(seqResult, "或", mContext.getResources().getColor(R.color.gray30), null, null);
        return seqResult;
    }

    private String concatSpot(ArrayList<TravelLineBean.MyPlan> plans)
    {
        String result = "前往景点：";
        for (TravelLineBean.MyPlan myPlan : plans)
        {
            result += myPlan.getPlanDetailName() + "\u3000";
        }
        return result;
    }

    private String concatMeal(ArrayList<TravelLineBean.MyPlan> plans)
    {
        String result = "";
        for (TravelLineBean.MyPlan myPlan : plans)
        {
            //用餐类型	Y	1早餐、2午餐、3晚餐
            result += getMealTypeText(myPlan.getMealsType()) + " " + getMealModeText(myPlan.getMealsMode()) + "\n";
        }
        return result;
    }

    /**
     * 用餐方式	Y	1酒店内、2自理、3团餐
     *
     * @param mealsMode
     * @return
     */
    private String getMealModeText(String mealsMode)
    {
        String result = "";
        if ("1".equals(mealsMode))
        {
            result = "酒店内";
        } else if ("2".equals(mealsMode))
        {
            result = "自理";
        } else if ("3".equals(mealsMode))
        {
            result = "团餐";
        }
        return result;
    }

    /**
     * 用餐类型code转文本；
     *
     * @param mealType
     * @return
     */
    private String getMealTypeText(String mealType)
    {
        String result = "";
        if ("1".equals(mealType))
        {
            result = "早餐";
        } else if ("2".equals(mealType))
        {
            result = "午餐";
        } else if ("3".equals(mealType))
        {
            result = "晚餐";
        }
        return result;
    }


    public class ViewHolder
    {
        public final TextView tvDay;
        public final TextView tvTravelSummay;
        public final WholeListView dayTimeListView;
        public final View root;

        public ViewHolder(View root)
        {
            tvDay = (TextView) root.findViewById(R.id.tvDay);
            tvTravelSummay = (TextView) root.findViewById(R.id.tvTravelSummay);
            dayTimeListView = (WholeListView) root.findViewById(R.id.dayTimeListView);
            this.root = root;
        }
    }
}
