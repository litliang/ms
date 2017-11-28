package com.yzb.card.king.ui.travel.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.FromPlaceBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import java.util.List;

/**
 * 类  名：旅游详情设置出发地适配器
 * 作  者：Li JianQiang
 * 日  期：2016/11/17
 * 描  述：
 */
public class TravelBgnPopAdapter extends BaseListAdapter<FromPlaceBean>
{
    private FromPlaceBean fromPlaceBean;

    public TravelBgnPopAdapter(Activity activity, List<FromPlaceBean> list, FromPlaceBean fromPlaceBean)
    {
        super(activity, list);
        this.fromPlaceBean = fromPlaceBean;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        ViewHolder vh;
        if (view == null)
        {
            view = mInflater.inflate(R.layout.travel_pop_grid, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else
        {
            vh = (ViewHolder) view.getTag();
        }
        FromPlaceBean bean = getItem(i);
        vh.llInfo.setSelected(fromPlaceBean != null && !isEmpty(fromPlaceBean.getCityId()) && fromPlaceBean.getCityId().equals(bean.getCityId()));

        String price = bean.getAddFee() + "";
        vh.txtCityName.setText(bean.getCityName());
        vh.txtPrice.setText("¥" + price.substring(0, price.indexOf(".")));

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (onItemClickListener != null)
                {
                    onItemClickListener.onClickListener(i);
                }
            }
        });
        return view;
    }

    public class ViewHolder
    {
        public TextView txtCityName;
        public TextView txtPrice;
        public LinearLayout llInfo;

        public ViewHolder(View view)
        {
            llInfo = (LinearLayout) view.findViewById(R.id.llInfo);
            txtCityName = (TextView) view.findViewById(R.id.denomination);
            txtPrice = (TextView) view.findViewById(R.id.price_bgn);
        }
    }


    public onClickeListener onItemClickListener;

    public void setOnItemClickListener(onClickeListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onClickeListener
    {
        void onClickListener(int position);
    }
}
