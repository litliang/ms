package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;

import org.xutils.x;


/**
 * 功能：旅游日程(jian模式)；
 *
 * @author:gengqiyun
 * @date: 2016/11/19
 */
public class TravelDayTimeSimpleAdapter extends BaseListAdapter<TravelLineBean.MyPlan>
{
    public TravelDayTimeSimpleAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_travel_daytime_simple_con, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TravelLineBean.MyPlan data = mList.get(position);

        CharSequence content = "";
        String trafficLogo = "";
        if (data != null)
        {
            trafficLogo = ServiceDispatcher.getImageUrl(data.getTrafficCode());
            content = data.getContactSeq();
        }

        viewHolder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.tvContent.setText(content);

        //1用餐、2景点、3住宿、4交通  交通时，网络加载；
        String planType = data.getPlanType();

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
        return viewHolder.root;
    }

    public class ViewHolder
    {
        public final View root;
        public ImageView ivTrafficType;
        public TextView tvContent;

        public ViewHolder(View root)
        {
            this.root = root;
            ivTrafficType = (ImageView) root.findViewById(R.id.ivTrafficType);
            tvContent = (TextView) root.findViewById(R.id.tvContent);
        }
    }
}
