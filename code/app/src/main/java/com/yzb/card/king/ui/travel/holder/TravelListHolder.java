package com.yzb.card.king.ui.travel.holder;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.FilterCollection;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.ui.hotel.holder.EndHolder;
import com.yzb.card.king.ui.hotel.holder.ListGridHolder;
import com.yzb.card.king.ui.hotel.interfaces.OnTypeClickListener;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/22
 * 描  述：
 */
public class TravelListHolder extends Holder<FilterCollection> {
    private int maxLine;
    private int columnNum = 4;
    private GridView listGridView;
    private TextView textView;
    private AbsBaseListAdapter<FilterType> listGridAdapter;
    private List<FilterType> firstList = new ArrayList<>();
    private List<FilterType> secondList = new ArrayList<>();
    private FilterType end = new FilterType("展开 >>", "end", null);
    private ColorStateList colorList;
    private int background;
    private List<FilterType> allList = new ArrayList<>();


    public void setColorList(ColorStateList colorList)
    {
        this.colorList = colorList;
    }

    public void setBackground(int background)
    {
        this.background = background;
    }

    public AbsBaseListAdapter<FilterType> getListGridAdapter()
    {
        return listGridAdapter;
    }

    public List<FilterType> getFirstList()
    {
        return firstList;
    }

    public List<FilterType> getSecondList()
    {
        return secondList;
    }

    public FilterType getEnd()
    {
        return end;
    }

    public void setEnd(FilterType end)
    {
        this.end = end;
    }

    public void setMaxLine(int maxLine)
    {
        this.maxLine = maxLine;
    }


    public TravelListHolder(int resId)
    {
        super(resId);
    }

    @Override
    public View initView()
    {
//        View view = UiUtils.inflate(R.layout.hotel_filter_pop_list_item);
//        textView = (TextView) view.findViewById(R.id.textView);
//        listGridView = (GridView) view.findViewById(R.id.gridView);
//        tvExpand = (TextView) view.findViewById(R.id.tvExpand);
//        tvShrink = (TextView) view.findViewById(R.id.tvShrink);
//        initListener();
//        return view;
        return null;
    }

    @Override
    protected View initView(int resId)
    {
        View view = UiUtils.inflate(resId);
        textView = (TextView) view.findViewById(R.id.textView);
        listGridView = (GridView) view.findViewById(R.id.gridView);
        return view;
    }


    public void setGridParam(int columnNum, int horizontalSpace)
    {
        this.columnNum = columnNum;
        listGridView.setNumColumns(columnNum);
        listGridView.setHorizontalSpacing(horizontalSpace);
    }

    @Override
    public void refreshView(FilterCollection data)
    {
        if (data == null) return;
        textView.setText(data.getName());
        textView.setVisibility(TextUtils.isEmpty(data.getName()) ? View.GONE : View.VISIBLE);
        splitList(data);
        initListContent(this);
        setAdapter();
    }


    private void initListContent(TravelListHolder holder)
    {
        FilterType end = holder.getEnd();
        if (holder.getData().isExpand())
        {
            setExpandList(holder, end);
        } else
        {
            setNoExpandList(holder, end);
        }
    }

    private void setNoExpandList(TravelListHolder holder, FilterType end)
    {
        end.setName("更多");
        holder.getFirstList().removeAll(holder.getSecondList());
        if (!holder.getFirstList().contains(end))
        {
            holder.getFirstList().add(end);
        }
        if (holder.getData().getChildList().size() <= getMaxItemNum())
        {
            holder.getFirstList().remove(end);
        }
    }

    private void setExpandList(TravelListHolder holder, FilterType end)
    {
        end.setName("收缩");
        holder.getFirstList().remove(end);
        holder.getFirstList().addAll(holder.getSecondList());
        holder.getFirstList().add(end);
    }

    private void setListContent(TravelListHolder holder)
    {
        FilterType end = holder.getEnd();
        if (holder.getData().isExpand())
        {
            holder.getData().setExpand(false);
            setNoExpandList(holder, end);
        } else
        {
            holder.getData().setExpand(true);
            setExpandList(holder, end);
        }
    }

    public void setAdapter()
    {
        listGridAdapter = new AbsBaseListAdapter<FilterType>(firstList) {
            @Override
            public int getViewTypeCount()
            {
                return super.getViewTypeCount() + 1;
            }

            @Override
            public int getItemViewType(int position)
            {
                if ("end".equals(getList().get(position).getType()))
                {
                    return 1;
                } else
                {
                    return super.getItemViewType(position);
                }
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                if (1 == getItemViewType(position))
                {
                    TravelEndHolder endHolder = new TravelEndHolder();
                    endHolder.refreshView(getList().get(position));
                    endHolder.setListener(new EndItemClickListener(TravelListHolder.this));
                    return endHolder.getConvertView();
                } else
                {
                    return super.getView(position, convertView, parent);
                }
            }

            @Override
            protected Holder getHolder(int position)
            {
                TravelListGridHolder holder = new TravelListGridHolder();
                holder.setListener(new OnItemClickListener(allList));
                return holder;
            }
        };
        listGridView.setAdapter(listGridAdapter);
    }

    private void splitList(FilterCollection data)
    {
        allList.clear();
        allList.addAll(data.getChildList());
        firstList.clear();
        secondList.clear();
        if (allList.size() > getMaxItemNum())
        {
            for (int i = 0; i < getMaxItemNum() - 1; i++)
            {
                firstList.add(allList.remove(0));
            }
            secondList.addAll(allList);
        } else
        {
            firstList.addAll(allList);
        }
        allList.clear();
        allList.addAll(firstList);
        allList.addAll(secondList);
    }

    private int getMaxItemNum()
    {
        return maxLine * columnNum;
    }

    public void hideTextView()
    {
        textView.setVisibility(View.GONE);
    }

    public void showTextView()
    {
        textView.setVisibility(View.VISIBLE);
    }

    class EndItemClickListener implements View.OnClickListener {
        private TravelListHolder holder;

        public EndItemClickListener(TravelListHolder listHolder)
        {
            this.holder = listHolder;
        }

        @Override
        public void onClick(View v)
        {
            setListContent(holder);
            holder.getListGridAdapter().notifyDataSetChanged();
        }
    }

    class OnItemClickListener implements OnTypeClickListener {
        private List<FilterType> list;

        public OnItemClickListener(List<FilterType> list)
        {
            this.list = list;
        }

        @Override
        public void onTypeClick(FilterType filterType)
        {
            normalSetting(filterType);
            listGridAdapter.notifyDataSetChanged();
        }


        private void normalSetting(FilterType filterType)
        {
            int start = 0;
            int end = list.size();
            setting(filterType, start, end);
        }

        private void setting(FilterType filterType, int start, int end)
        {
            if (filterType == list.get(start))
            {
                for (int i = start; i < end; i++)
                {
                    FilterType item = list.get(i);
                    if (i == start)
                    {
                        item.setSelected(true);
                    } else
                    {
                        item.setSelected(false);
                    }
                }
            } else
            {
                for (int i = start; i < end; i++)
                {
                    FilterType item = list.get(i);
                    if (i == start)
                    {
                        item.setSelected(false);
                    }
                }


            }
        }
    }
}
