package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.discount.bean.GiftCardBean;

import java.util.List;

/**
 * 类名： DiscountGitfCardAdapter
 * 作者： Lei Chao.
 * 日期： 2016-08-08
 * 描述：
 */
public class DiscountGitfCardAdapter extends RecyclerView.Adapter<DiscountGitfCardAdapter.MyVH> {

    // 表示listview没有数据
    private static final int VIEW_TYPE = -1;

    public interface ImageOnClickListener {
        void setOnImageClick(int posistion, List<GiftCardBean> giftCardBeanLists);
    }

    private ImageOnClickListener imageOnClickListener;

    public void setImageOnClickListener(ImageOnClickListener imageOnClickListenerP) {
        this.imageOnClickListener = imageOnClickListenerP;
    }

    private List<GiftCardBean> giftCardBeanLists;

    private LayoutInflater inflater;

    private Context context;


    public DiscountGitfCardAdapter(List<GiftCardBean> giftCardBeanList, Context context) {
        this.giftCardBeanLists = giftCardBeanList;
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    public void addData(List<GiftCardBean> cardBeen) {
        this.giftCardBeanLists.clear();
        this.giftCardBeanLists.addAll(cardBeen);
        notifyDataSetChanged();


    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = inflater.inflate(R.layout.giftcard_item, parent, false);

        final MyVH myVH = new MyVH(view);
        return myVH;
    }

    @Override
    public int getItemViewType(int position) {

        if (this.giftCardBeanLists == null || this.giftCardBeanLists.size() <= 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyVH holder, final int position) {

        GiftCardBean bean =  giftCardBeanLists.get(position);
       // x.image().bind(holder.imageView, ServiceDispatcher.getImageUrl(bean.getImageCode()));
        Glide.with(context).load(ServiceDispatcher.getImageUrl(bean.getImageCode())).thumbnail(0.5f).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOnClickListener.setOnImageClick(position, giftCardBeanLists);
            }
        });

        holder.tvTitleName.setText(bean.getTypeName());


    }

    @Override
    public int getItemCount() {
        return giftCardBeanLists == null ? 0 : giftCardBeanLists.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView tvTitleName;


        public MyVH(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_giftcard);

            tvTitleName = (TextView) itemView.findViewById(R.id.tvTitleName);

        }
    }


}