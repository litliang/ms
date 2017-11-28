package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 功能：评价；
 *
 * @author:gengqiyun
 * @date: 2016/11/21
 */
public class EvaluateListAdapter extends BaseListAdapter<PjBean>
{
    private ImageOptions imageOptions;

    public EvaluateListAdapter(Context context)
    {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(27), ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.travel_detail_evaluate_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        PjBean pjBean = getItem(position);
        x.image().bind(holder.ivHeadPic, ServiceDispatcher.getImageUrl(pjBean.getCustPicUrl()), imageOptions);

        String uName =  pjBean.getNickName();
        holder.tvUname.setText(uName);
        holder.tvUname.setVisibility(TextUtils.isEmpty(uName) ? View.GONE : View.VISIBLE);

        holder.tvScore.setText(pjBean.getVote() + "分");
        holder.ratingBar.setStarMarkAndSore(pjBean.getVote() / 2f);
      ///  holder.tvDate.setText(DateUtil.long2String(pjBean.getCreateTime(), DateUtil.DATE_FORMAT_DATE));
        try
        {
            holder.tvContent.setText(URLDecoder.decode(pjBean.getComment(), "utf-8"));
        } catch (UnsupportedEncodingException e)
        {
            holder.tvContent.setText("");
            e.printStackTrace();
        }
        return holder.root;
    }

    public class ViewHolder
    {
        public final ImageView ivHeadPic;
        public final TextView tvUname;
        public final TextView tvScore;
        public final TextView tvDate;
        public final TextView tvContent;
        public final View root;
        public StarBar ratingBar;

        public ViewHolder(View root)
        {
            ivHeadPic = (ImageView) root.findViewById(R.id.ivHeadPic);
            tvUname = (TextView) root.findViewById(R.id.tvUname);
            tvScore = (TextView) root.findViewById(R.id.tvScore);
            ratingBar = (StarBar) root.findViewById(R.id.starBar);
            tvDate = (TextView) root.findViewById(R.id.tvDate);
            tvContent = (TextView) root.findViewById(R.id.tvContent);
            this.root = root;
        }
    }
}
