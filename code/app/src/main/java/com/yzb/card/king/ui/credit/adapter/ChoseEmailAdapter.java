package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzb.card.king.R;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/12/1
 * 描  述：
 */
public class ChoseEmailAdapter extends RecyclerView.Adapter {

    private Context context;

    private LayoutInflater inflater;

    public ChoseEmailAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(inflater.inflate(R.layout.chose_email_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ViewHolder)
        {
            if (onItemClickListener != null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.setOnItemClickListener(position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(int postion);
    }
}
