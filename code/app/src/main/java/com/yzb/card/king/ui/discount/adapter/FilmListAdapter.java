package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.StarBar;

import org.xutils.x;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mInflater;
    private List<Map> mData;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public FilmListAdapter(Context context, List<Map> data, OnRecyclerViewItemClickListener mOnItemClickListener) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView fImg;
        TextView fName;
        StarBar fRating;
        TextView fVote;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.film_list_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.fImg = (ImageView) view.findViewById(R.id.film_image);
        viewHolder.fName = (TextView) view.findViewById(R.id.film_name);
        viewHolder.fRating = (StarBar) view.findViewById(R.id.starBar);
        viewHolder.fVote = (TextView) view.findViewById(R.id.film_vote);
        view.setOnClickListener(this);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        Map<String, String> map = mData.get(i);


        String imageUri = ServiceDispatcher.getImageUrl(String.valueOf(map.get("imageCode")));//ServiceDispatcher.url_image + "getImg/" + String.valueOf(map.get("imageCode")) + "/0";
        x.image().bind(viewHolder.fImg,imageUri, GlobalApp.getInstance().getImageOptionsLogo());
        viewHolder.fName.setText(String.valueOf(map.get("filmName")));
        String vote = String.valueOf(map.get("vote"));
        Float voteF = Float.parseFloat(vote) / 2;
        viewHolder.fRating.setStarMarkAndSore(voteF);
        viewHolder.fVote.setText(new BigDecimal(vote).setScale(1) + "分");

        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(String.valueOf(map.get("filmId")));
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

}