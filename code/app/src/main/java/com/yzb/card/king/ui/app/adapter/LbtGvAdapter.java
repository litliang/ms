package com.yzb.card.king.ui.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.List;

/**
 * 轮播图Gridview适配器；
 */
public class LbtGvAdapter extends BaseAdapter
{
    private final List<ChildTypeBean> beanList;

    public LbtGvAdapter(List<ChildTypeBean> beanList)
    {
        this.beanList = beanList;
    }

    @Override
    public int getCount()
    {
        return beanList == null ? 0 : beanList.size();
    }

    @Override
    public ChildTypeBean getItem(int position)
    {
        return beanList == null ? null : beanList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = UiUtils.inflate(R.layout.item_gv_lbt);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);

        final ChildTypeBean typeBean = getItem(position);

        if ("0000".equals(typeBean.id))
        {
            iv.setImageResource(R.mipmap.icon_coupon_all);
        } else
        {
            x.image().bind(iv, ServiceDispatcher.getImageUrl(typeBean.typeImage));
        }
        tv.setText(typeBean.typeName);

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (callBack != null)
                {
                    callBack.callBack(typeBean);
                }
            }
        });
        return convertView;
    }

    private IGvItemClickCallBack callBack;

    public void setCallBack(IGvItemClickCallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface IGvItemClickCallBack
    {
        void callBack(ChildTypeBean typeBean);
    }
}

