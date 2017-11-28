package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.gift.bean.GiftCardProductBean;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 类 名： 礼品卡商城适配器
 * 作 者： gaolei
 * 日 期：2016年12月6日
 * 描 述：礼品卡商城适配器
 */

public class ShopGiftCardAdapter extends RecyclerView.Adapter<ShopGiftCardAdapter.MyNotificViewHolder>
        implements View.OnClickListener
{
    private Context context;//上下文
    private List<GiftCardProductBean> data;//数据源
    private RecyclerView recyclerview;//绑定RecyclerView
    private OnNotItemClickListener listener;//自定义监听对象
    private ImageOptions imageOptions;
    private LayoutInflater inflater;

    private int viewType = 0;

    public ShopGiftCardAdapter(Context context, List<GiftCardProductBean> data)
    {
        this.context = context;
        this.data = data;
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(4), ImageView.ScaleType.FIT_XY);
        inflater = LayoutInflater.from(context);
    }

    public void setViewType(int viewType){

        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position)
    {

        return  viewType;
    }
    @Override
    public MyNotificViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        if(viewType== 1){

            view = inflater.inflate(R.layout.item_shop_giftcard_user, parent, false);

        }else{
             view = inflater.inflate(R.layout.item_shop_giftcard, parent, false);
        }

        view.setOnClickListener(this);

        return new MyNotificViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyNotificViewHolder holder, int position)
    {
        GiftCardProductBean bean = data.get(position);
        x.image().bind(holder.cardImg, ServiceDispatcher.getImageUrl(bean.getImageCode()), imageOptions);
        holder.cardTxt.setText(bean.getTypeName());
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    @Override
    public void onClick(View v)
    {
        if (recyclerview != null && listener != null)
        {
            int position = recyclerview.getChildAdapterPosition(v);
            listener.OnNotItemClick(recyclerview, v, position, data);
        }
    }


    public static class MyNotificViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView cardImg;
        private TextView cardTxt;

        public MyNotificViewHolder(View itemView)
        {
            super(itemView);
            cardImg = (ImageView) itemView.findViewById(R.id.giftcard_shop_img);
            cardTxt = (TextView) itemView.findViewById(R.id.giftcard_shop_txt);
        }
    }

    //绑定RecyclerView
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerview = recyclerView;
    }

    //解绑定RecyclerView
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerview = null;
    }

    //自定义监听事件
    public interface OnNotItemClickListener
    {
        void OnNotItemClick(RecyclerView parent, View view, int position, List<GiftCardProductBean> data);
    }

    public void setOnNotItemClickListener(OnNotItemClickListener listener)
    {
        this.listener = listener;
    }

}
