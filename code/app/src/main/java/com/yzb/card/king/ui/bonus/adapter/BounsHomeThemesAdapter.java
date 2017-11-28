package com.yzb.card.king.ui.bonus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 红包首页；
 *
 * @author:gengqiyun
 * @date: 2016/12/21
 */
public class BounsHomeThemesAdapter extends BaseListAdapter<BounsThemeBean>
{
    public static final int WHAT_ITEM_CLICK = 0x002;
    private ImageOptions imageOptions;

    public BounsHomeThemesAdapter(Context context)
    {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(10), ImageView.ScaleType.FIT_XY);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_bouns_home, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        BounsThemeBean bean = getItem(position);

        x.image().bind(holder.iv, ServiceDispatcher.getImageUrl(bean.getCloseImageCode()), imageOptions);
        holder.tv.setText(bean.getThemeName());
        return holder.root;
    }

    public class ViewHolder
    {
        @ViewInject(R.id.iv)
        public ImageView iv;
        @ViewInject(R.id.tv)
        public TextView tv;
        public View root;

        public ViewHolder(View root)
        {
            this.root = root;
            x.view().inject(this, root);
        }
    }
}
