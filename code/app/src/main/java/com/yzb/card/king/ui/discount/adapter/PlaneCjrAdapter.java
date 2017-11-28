package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;

import java.util.List;

/**
 * Created by dev on 2016/3/24.
 * 乘机人；
 */
public class PlaneCjrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<PassengerInfoBean> data;
    private int listviewItem;
    private LayoutInflater layoutInflater = null;
    private View.OnClickListener deleteCjrListener = null;
    private String[] typeArray;

    public PlaneCjrAdapter(Context context, List<PassengerInfoBean> data, int listviewItem, View.OnClickListener deleteCjrListener)
    {
        this.data = data;
        this.listviewItem = listviewItem;
        this.layoutInflater = LayoutInflater.from(context);
        this.deleteCjrListener = deleteCjrListener;
        typeArray = context.getResources().getStringArray(R.array.setting_certificate_type);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(layoutInflater.inflate(listviewItem, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            PassengerInfoBean bean = data.get(position);
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.cjrName.setText(bean.name);

            String personType = bean.getPersonType();
            if ("未识别".equals(personType))
            {
                viewHolder.cjrTicketType.setText("");
            } else
            {
                viewHolder.cjrTicketType.setText(bean.getPersonType() + "票");
            }

            if (TextUtils.isDigitsOnly(bean.getCertType()))
            {
                int typeInt = Integer.parseInt(bean.getCertType());
                if (typeInt >= 1 && typeInt <= typeArray.length)
                {
                    viewHolder.tvSfType.setText(typeArray[typeInt - 1]);
                }
            } else
            {
                viewHolder.tvSfType.setText("");
            }
            viewHolder.cjrIdNo.setText(bean.certNo);
            viewHolder.ivDelete.setTag(bean.id);

            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    deleteCjrListener.onClick(v);
                }
            });
        }
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView cjrName;
        TextView cjrTicketType;
        TextView cjrIdNo;

        View ivDelete;
        TextView details;
        TextView tvSfType;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            cjrName = (TextView) itemView.findViewById(R.id.cjrName);
            cjrTicketType = (TextView) itemView.findViewById(R.id.cjrTicketType);
            cjrIdNo = (TextView) itemView.findViewById(R.id.cjrIdNo);

            ivDelete = itemView.findViewById(R.id.ivDelete);
            details = (TextView) itemView.findViewById(R.id.details);
            tvSfType = (TextView) itemView.findViewById(R.id.tvSfType);
        }
    }

    public OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener
    {
        void setOnItemClickListener(int position);
    }
}
