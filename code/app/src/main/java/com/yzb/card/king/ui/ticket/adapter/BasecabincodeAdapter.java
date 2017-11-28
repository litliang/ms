package com.yzb.card.king.ui.ticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.ui.ticket.interfaces.FilterTypes;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名： BasecabincodeAdapter
 * 作者： Lei Chao.
 * 日期： 2016-10-15
 * 描述：
 */
public class BasecabincodeAdapter extends RecyclerView.Adapter
{

    private SparseBooleanArray sparseBooleanArray;
    private List<FilterType> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public List<FilterType> getSelectedDatas()
    {
        List<FilterType> selected = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++)
        {
            if (isSelected(i))
            {
                selected.add(mDatas.get(i));
            }
        }
        return selected;
    }

    public int getSelecteCount()
    {
        int count = 0;
        for (int i = 0; i < mDatas.size(); i++)
        {
            if (isSelected(i))
            {
                count++;
            }

            LogUtil.LCi("  仓位是否选中 -------------" + isSelected(i));

        }
        return count;
    }

    // 重置
    public void reSet()
    {
        sparseBooleanArray.clear();
        sparseBooleanArray.put(0, true);
    }

    private void setItemSelected(int position, boolean isSelected)
    {
        sparseBooleanArray.put(0, false);
        sparseBooleanArray.put(position, isSelected);
    }

    private boolean isSelected(int position)
    {
        return sparseBooleanArray.get(position);
    }

    private FilterBaseOnClickListener onClickListener;

    public void setOnClickListener(FilterBaseOnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    public BasecabincodeAdapter(List<FilterType> mDatas, Context context)
    {
        this.mDatas = mDatas;
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
        sparseBooleanArray = new SparseBooleanArray();
        // 初始化默认选中0
        sparseBooleanArray.put(0, true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new FilterBaseHolder(inflater.inflate(R.layout.filter_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof FilterBaseHolder)
        {
            FilterBaseHolder vh = (FilterBaseHolder) holder;
            if (position == 0)
            {
                vh.checkBox.setText(R.string.any);
            } else
            {
                vh.checkBox.setText(String.valueOf(mDatas.get(position).getCode()));
            }
            // 判断是否选中
            if (isSelected(position))
            {
                vh.checkBox.setBackgroundResource(R.drawable.shape_btn_red);
            } else
            {
                vh.checkBox.setBackgroundResource(R.drawable.btn_bg_gray);
            }

            vh.checkBox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if (onClickListener != null)
                    {
                        onClickListener.onClick(position, FilterTypes.FLIGHTQABIN);
                    }

                    // 判断是否都没选中
                    if (getSelecteCount() == 0)
                    {
                        reSet();
                        return;
                    }

                    // 不限按钮
                    if (position == 0)
                    {
                        reSet();
                        return;
                    }

                    if (isSelected(position))
                    {
                        setItemSelected(position, false);
                    } else
                    {
                        setItemSelected(position, true);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }
}