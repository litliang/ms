package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.food.FoodThemeBean;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.sys.ServiceDispatcher;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：首页美食今日推荐适配器
 * 作  者：Li Yubing
 * 日  期：2016/7/18
 * 描  述：
 */
public class MsMainTodayRecommendAdapter extends BaseAdapter
{

    private AdapterItemClickCallBack callBack;


    private Context context;

    private LayoutInflater layoutInflater;

    private List<FoodThemeBean> list = new ArrayList<FoodThemeBean>();

    private int imageHeight;

    private String browesCountStr;

    public MsMainTodayRecommendAdapter(Context context)
    {

        this.context = context;

        layoutInflater = LayoutInflater.from(context);
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;

        imageHeight = heightPixels * 220 / 1080;

        browesCountStr = context.getString(R.string.hotel_browe_count);

    }

    /**
     * 清空数据；
     */
    public void clear()
    {
        list.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加新数据；
     */
    public void addNewList(List<FoodThemeBean> list)
    {
        if (list != null)
        {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setAdapterItemClickCallBack(AdapterItemClickCallBack callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        ViewHold viewHold;

        if (convertView == null)
        {

            convertView = layoutInflater.inflate(R.layout.food_main_today_recommend, null);

            viewHold = new ViewHold(convertView);

            convertView.setTag(viewHold);
        } else
        {

            viewHold = (ViewHold) convertView.getTag();
        }


        FoodThemeBean bean = (FoodThemeBean) getItem(position);

        if (!TextUtils.isEmpty(bean.getBgImage()))
        {

            x.image().bind(viewHold.ivThemeImage, ServiceDispatcher
                    .getImageUrl(bean.getBgImage()));

        }

        if (!TextUtils.isEmpty(bean.getThemeName()))
        {

            viewHold.tvThemeName.setText(bean.getThemeName());

        }

        if (!TextUtils.isEmpty(bean.getBrowseCount()))
        {

            String temp = browesCountStr.replace("##", bean.getBrowseCount());

            viewHold.tvBrownsCount.setText(temp);

        }

        if (position % 2 == 0)
        {

            viewHold.tvThemeName.setTextColor(context.getResources().getColor(R.color.food_blue_96cee0));

        } else
        {

            viewHold.tvThemeName.setTextColor(context.getResources().getColor(R.color.white_ffffff));
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (callBack != null)
                {
                    callBack.itemClickCallBack(position);
                }
            }
        });

        return convertView;
    }

    class ViewHold
    {

        RelativeLayout rlTodayHotelRecommend;

        ImageView ivThemeImage;

        TextView tvThemeName;

        TextView tvBrownsCount;

        public ViewHold(View v)
        {

            rlTodayHotelRecommend = (RelativeLayout) v.findViewById(R.id.rlTodayHotelRecommend);

            ivThemeImage = (ImageView) v.findViewById(R.id.ivThemeImage);

            tvThemeName = (TextView) v.findViewById(R.id.tvThemeName);

            tvBrownsCount = (TextView) v.findViewById(R.id.tvBrownsCount);

        }


    }
}
