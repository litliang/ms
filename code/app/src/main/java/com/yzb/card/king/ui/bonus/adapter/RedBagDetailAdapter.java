package com.yzb.card.king.ui.bonus.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.bonus.bean.RedBagRecOrSendBean;
import com.yzb.card.king.util.Utils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 类 名： 红包明细详情的Adapter
 * 作 者： gaolei
 * 日 期：2016年12月26日
 * 描 述: 红包明细详情的Adapter
 */

public class RedBagDetailAdapter extends RecyclerView.Adapter<RedBagDetailAdapter.RedBagSendViewHolder> implements View.OnClickListener {

    private Context context;
    private List<RedBagRecOrSendBean.ReceiveListBean> list;
    private ImageOptions imageOptions;
    /**
     * 红包是否已经领取完
     */
    private boolean statuFlag = false;

    private int theBestIndex = 0;

    public RedBagDetailAdapter(Context context, List<RedBagRecOrSendBean.ReceiveListBean> list)
    {

        this.context = context;

        this.list = list;

        imageOptions = new ImageOptions.Builder()
                //  .setSize(org.xutils.common.util.DensityUtil.dip2px(30), org.xutils.common.util.DensityUtil.dip2px(30))
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(360))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                .setUseMemCache(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.icon_nav_user)
                .setFailureDrawableId(R.mipmap.icon_nav_user)
                .setCircular(false)
                .build();

        theBestIndex = calBestObjectIndex();
    }

    /**
     * 计算出最佳领取人编号
     *
     * @return
     */
    private int calBestObjectIndex()
    {

        int tempIndex = 0;

        int size = list.size();//放在外部提高效率

        if (size > 0) {

            RedBagRecOrSendBean.ReceiveListBean bean = list.get(0);

            double beanAmount = bean.getReceiveAmount();

            String beanReceiveTime = bean.getReceiveTime();

            long beanReceiveTimeLong = Utils.toTimestamp(beanReceiveTime, 1);

            for (int i = 0; i < size; i++) {

                RedBagRecOrSendBean.ReceiveListBean tempBean = list.get(i);

                double temp = tempBean.getReceiveAmount();

                String tempReceiveTime = tempBean.getReceiveTime();

                long tempReceiveTimeLong = Utils.toTimestamp(tempReceiveTime, 1);

                if (temp > beanAmount) {//计算出最佳价格


                    tempIndex = i;

                    beanAmount = temp;

                    beanReceiveTimeLong = tempReceiveTimeLong;


                }
                if (temp == beanAmount) {

                    if (tempReceiveTimeLong < beanReceiveTimeLong) {

                        tempIndex = i;

                        beanAmount = temp;

                        beanReceiveTimeLong = tempReceiveTimeLong;


                    }
                }

            }
        }

        return tempIndex;
    }

    public void setStatuFlag(boolean statuFlag)
    {

        this.statuFlag = statuFlag;
    }

    @Override
    public RedBagDetailAdapter.RedBagSendViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_redbag_detail, parent, false);
        RedBagSendViewHolder holder = new RedBagSendViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RedBagSendViewHolder holder, int position)
    {
        //时间服务器得到的是long 后面会转化为String
        String tradeTime = list.get(position).getReceiveTime();

        String[] split = tradeTime.split(" ");

        String time = split[1];

        String[] split2 = time.split(":");

        holder.item_money.setText(list.get(position).getReceiveAmount() + "元");

        holder.item_name.setText(list.get(position).getReceivePerson());

        holder.item_date.setText(split[0] + "  " + split2[0] + ":" + split2[1] + "");//还有time服务器一直没有

        x.image().bind(holder.item_icon, ServiceDispatcher.getImageUrl(list.get(position).getReceivePicUrl()), imageOptions);

        if (position == theBestIndex) {

            if (statuFlag) {
                holder.item_zuijia_img.setVisibility(View.VISIBLE);
                holder.item_zuijia.setVisibility(View.VISIBLE);
            } else {
                holder.item_zuijia_img.setVisibility(View.INVISIBLE);
                holder.item_zuijia.setVisibility(View.INVISIBLE);
            }

        } else {
            holder.item_zuijia_img.setVisibility(View.INVISIBLE);
            holder.item_zuijia.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount()
    {

        return list.size();
    }

    RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v)
    {
        if (recyclerView != null && listener != null) {
            //取得位置
            int position = recyclerView.getChildAdapterPosition(v);
            listener.OnMyItemClick(recyclerView, v, position, list);
        }
    }

    public interface OnMyItemClickListener {
        //view 被点击的view
        void OnMyItemClick(RecyclerView parent, View view, int position, List<RedBagRecOrSendBean.ReceiveListBean> data);
    }


    public static class RedBagSendViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_icon, item_zuijia_img;
        private TextView item_time, item_money, item_zuijia, item_date, item_name;

        public RedBagSendViewHolder(View itemView)
        {
            super(itemView);
            item_icon = (ImageView) itemView.findViewById(R.id.item_icon);
            item_date = (TextView) itemView.findViewById(R.id.item_date);
            item_time = (TextView) itemView.findViewById(R.id.item_time);
            item_money = (TextView) itemView.findViewById(R.id.item_money);
            item_zuijia = (TextView) itemView.findViewById(R.id.item_zuijia);
            item_zuijia_img = (ImageView) itemView.findViewById(R.id.item_zuijia_img);
            item_name = (TextView) itemView.findViewById(R.id.item_name);

        }
    }

}
