package com.yzb.card.king.ui.bonus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.bonus.bean.RedBagReceiveBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 类 名： 红包发明细的Adapter
 * 作 者： gaolei
 * 日 期：2016年12月26日 ni
 * 描 述
 */

public class RedBagReceiverAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<RedBagReceiveBean> list;
    private ImageOptions imageOptions;


    public RedBagReceiverAdapter(Context context, List<RedBagReceiveBean> list) {
        this.context = context;
        this.list = list;
        imageOptions = new ImageOptions.Builder()
                //  .setSize(org.xutils.common.util.DensityUtil.dip2px(30), org.xutils.common.util.DensityUtil.dip2px(30))
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(5))
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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);

        if (viewType == R.layout.load_more_layout) {
            return new RedBagReceiverAdapter.LoadMoreVH(view);
        } else {
            RedBagSendViewHolder holder = new RedBagSendViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if ( position == list.size() ) {
            return R.layout.load_more_layout;
        } else {
            return R.layout.item_redbag_receiver;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RedBagSendViewHolder) {
            ((RedBagSendViewHolder)holder).red_type.setText(list.get(position).getThemeName() + "");
            ((RedBagSendViewHolder)holder).from_who.setText(list.get(position).getIssuePerson() + "");
            ((RedBagSendViewHolder)holder).red_date.setText(list.get(position).getReceiveTime() + "");
            ((RedBagSendViewHolder)holder).money_num.setText(list.get(position).getReceiveAmount()+"");
            if ((list.get(position).getEndStatus()).equals("1")){
                ((RedBagSendViewHolder)holder).red_status.setText("已领完");
            } else {
                ((RedBagSendViewHolder)holder).red_status.setText("未领完");
            }
            x.image().bind(((RedBagSendViewHolder)holder).sendWho_Img, ServiceDispatcher.getImageUrl(list.get(position).getIssuePersonPhoto()), imageOptions);//获取背景图片
//            Picasso.with(context).load(ServiceDispatcher.getImageUrl(list.get(position).getIssuePersonPhoto())+".jpg").
//                    into(((RedBagSendViewHolder)holder).sendWho_Img);
        } else if (holder instanceof LoadMoreVH ) {
            listener.onLoad();
        }
    }

    public void addData(List<RedBagReceiveBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (recyclerView != null && listener != null) {
            //取得位置
            int position = recyclerView.getChildAdapterPosition(v);
            listener.OnMyItemClick(recyclerView, v, position, list );
        }
    }

    public interface OnMyItemClickListener {
        //view 被点击的view
        void OnMyItemClick(RecyclerView parent, View view, int position, List<RedBagReceiveBean> list);
        void onLoad();
    }


    public static class RedBagSendViewHolder extends RecyclerView.ViewHolder {

        private ImageView sendWho_Img;
        private TextView red_type, red_date, money_num, red_status,from_who;

        public RedBagSendViewHolder(View itemView) {
            super(itemView);
            sendWho_Img = (ImageView) itemView.findViewById(R.id.red_bag_type1_img);
            red_type = (TextView) itemView.findViewById(R.id.red_bag_type_txt);
            red_date = (TextView) itemView.findViewById(R.id.red_bag_date_txt);
            money_num = (TextView) itemView.findViewById(R.id.red_bag_moneynum_txt);
            red_status = (TextView) itemView.findViewById(R.id.red_bag_status_txt);
            from_who = (TextView) itemView.findViewById(R.id.red_bag_fromwho_txt);
        }
    }

    //底部加载更多item的viewholder
    static class LoadMoreVH extends RecyclerView.ViewHolder {

        public LoadMoreVH(View itemView) {
            super(itemView);
        }
    }

}
