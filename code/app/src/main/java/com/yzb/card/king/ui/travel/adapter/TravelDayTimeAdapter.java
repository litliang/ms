package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.travel.view.ITextClickCall;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 功能：旅游日程(图文模式)；
 *
 * @author:gengqiyun
 * @date: 2016/11/19
 */
public class TravelDayTimeAdapter extends BaseListAdapter<TravelLineBean.MyPlan>
{
    private ImageOptions imageOptions;

    public TravelDayTimeAdapter(Context context)
    {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(3), ImageView.ScaleType.FIT_XY);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_travel_daytime_content, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TravelLineBean.MyPlan data = mList.get(position);

        String time = "";  //时间；
        CharSequence content = "";
        List<String> imageCodes = null;
        String intervalTime = "";
        String trafficLogo = "";
        String isClassic = null;
        if (data != null)
        {
            time = data.getPlanTime();
            trafficLogo = ServiceDispatcher.getImageUrl(data.getTrafficCode());

            content = getContentSummary(data);
            String spotPhotos = data.getSpotPhotosCodes();
            if (!isEmpty(spotPhotos))
            {
                imageCodes = Arrays.asList(spotPhotos.split(","));
                //最多4张；
                imageCodes = imageCodes.size() > 4 ? imageCodes.subList(0, 4) : imageCodes;
            }
            intervalTime = getIntervalTime(data);
            isClassic = data.getIsClassic();
        }
        //1用餐、2景点、3住宿、4交通
        //交通：网络加载；其它本地加载
        String planType = data.getPlanType();
        //是否经典；只有景点 存在经典；
        viewHolder.tvClassic.setVisibility("1".equals(isClassic) && "2".equals(planType) ? View.VISIBLE : View.GONE);

        if ("4".equals(planType))
        {
            x.image().bind(viewHolder.ivTrafficType, trafficLogo);
        } else if ("3".equals(planType))
        {
            viewHolder.ivTrafficType.setImageResource(R.mipmap.icon_pic_hotel);
        } else if ("2".equals(planType))
        {
            viewHolder.ivTrafficType.setImageResource(R.mipmap.icon_pic_spot);
        } else if ("1".equals(planType))
        {
            viewHolder.ivTrafficType.setImageResource(R.mipmap.icon_pic_meal);
        }
        viewHolder.tvTime.setText(time);

        viewHolder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.tvContent.setText(content);

        viewHolder.ivGridView.setAdapter(null);
        if (imageCodes != null && imageCodes.size() > 0)
        {
            viewHolder.ivGridView.setVisibility(View.VISIBLE);

            List<String> imageUrls = CommonUtil.convetCodesToUrls(imageCodes);

            ImageOnlyAdapter adapter = new ImageOnlyAdapter(mContext, imageUrls);
            adapter.setImageSize(DensityUtil.dip2px(133), DensityUtil.dip2px(90));
            adapter.setImageOptions(imageOptions);
            viewHolder.ivGridView.setAdapter(adapter);
        } else
        {
            viewHolder.ivGridView.setVisibility(View.GONE);
        }
        viewHolder.tvIntervalTime.setVisibility(isEmpty(intervalTime) ? View.GONE : View.VISIBLE);
        viewHolder.tvIntervalTime.setText(intervalTime);
        return viewHolder.root;
    }

    /**
     * 获取时间；
     *
     * @param data
     * @return
     */
    private String getIntervalTime(TravelLineBean.MyPlan data)
    {
        String timeSummary = CommonUtil.formatMmToHh(data.getPlaneDuration());
        //计划里程行驶时长
        String planMileageDuration = CommonUtil.formatMmToHh(data.getPlanMileageDuration());
        String result = "";
        if (data != null)
        {
            String planType = data.getPlanType();
            //1用餐、2景点、3住宿、4交通
            switch (planType)
            {
                case "1":
                    result = "用餐时间：约" + timeSummary;
                    break;
                case "3":
                    result = "";
                    break;
                case "2":
                    result = "行驶时间：约" + planMileageDuration + "　活动时间：约" + timeSummary;
                    break;
                case "4":
                    result = "行程\u3000约" + data.getPlanMileage() + "公里/" + planMileageDuration;
                    break;
            }
        }
        return result;
    }


    /**
     * 获取内容概要；
     * 1用餐、2景点、3住宿、4交通
     *
     * @param data
     * @return
     */
    private CharSequence getContentSummary(TravelLineBean.MyPlan data)
    {
        String result;
        CharSequence seqResult = "";
        if (data != null)
        {
            String planType = data.getPlanType();
            //1用餐、2景点、3住宿、4交通
            switch (planType)
            {
                case "1":
                    //用餐类型	Y	1早餐、2午餐、3晚餐
                    String mealType = getMealTypeText(data.getMealsType());
                    String mealMode = getMealModeText(data.getMealsMode());
                    seqResult = mealType + " " + mealMode;
                    break;
                case "2":
                    result = "前往景点: " + data.getPlanDetailName();
                    seqResult = result;
                    break;
                case "3":
                    List<TravelLineBean.MyHotel> myHotels = data.getHotelList();
                    String hotels = "";
                    //关键字；
                    List<TravelLineBean.MyHotel> keys = new ArrayList<>();

                    if (myHotels != null)
                    {
                        TravelLineBean.MyHotel myHotel;
                        for (int i = 0; i < myHotels.size(); i++)
                        {
                            myHotel = myHotels.get(i);
                            hotels += i != myHotels.size() - 1 ? " " + myHotel.getHotelName() + " 或 " : " " + myHotel.getHotelName();

                            //平台合作的酒店；
                            if (!isEmpty(myHotel.getHotelId()))
                            {
                                keys.add(myHotel);
                            }
                        }
                    }
                    result = "入住酒店:" + hotels;
                    seqResult = result;

                    // 标注特殊的酒店；
                    for (int i = 0; i < keys.size(); i++)
                    {
                        seqResult = CommonUtil.colourTxt(seqResult,
                                keys.get(i).getHotelName(), mContext.getResources().getColor(R.color.color_1f8a99),
                                keys.get(i).getHotelId(), new ITextClickCall()
                                {
                                    @Override
                                    public void callBack(Object... args)
                                    {
                                        LogUtil.i("点击的酒店的id=" + args);
                                        if (args != null && args.length == 1)
                                        {
                                            LogUtil.i("点击的酒店的id=" + args[0]);
//                                            Intent intent = new Intent(mContext, HotelDetailActivity.class);
//                                            intent.putExtra("id", String.valueOf(args[0]));
//                                            mContext.startActivity(intent);
                                        }
                                    }
                                });
                    }
                    seqResult = CommonUtil.colourTxt(seqResult, "或", mContext.getResources().getColor(R.color.color_999999), null, null);
                    break;
                case "4":
                    result = "交通: " + data.getPlanSummary();
                    seqResult = result;
                    break;
            }
        }
        return seqResult;
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
        public final View root;
        public ImageView ivTrafficType;
        public TextView tvTime;
        public TextView tvContent;
        public GridView ivGridView;
        public TextView tvIntervalTime;
        public TextView tvClassic; //经典；

        public ViewHolder(View root)
        {
            this.root = root;
            ivTrafficType = (ImageView) root.findViewById(R.id.ivTrafficType);
            tvTime = (TextView) root.findViewById(R.id.tvTime);
            tvContent = (TextView) root.findViewById(R.id.tvContent);
            ivGridView = (GridView) root.findViewById(R.id.ivGridView);
            tvIntervalTime = (TextView) root.findViewById(R.id.tvIntervalTime);
            tvClassic = (TextView) root.findViewById(R.id.tvClassic);
        }
    }
}
