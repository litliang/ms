package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.IFilterPopItem;
import com.yzb.card.king.bean.ticket.SortType;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.ticket.interfaces.OnItemSelectLis;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ViewUtil;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/11 15:57
 */
public class SimpleListPop extends PopupWindow {
    private View mRoot;
    List<IFilterPopItem> items;
    OnItemSelectLis listener;
    private ListView listView;
    private int selectedItem = -1;
    private AbsBaseListAdapter<IFilterPopItem> adapter;
    private Float textSize = 15f;
    private View tvTitle;

    public void setTextSize(Float textSize)
    {
        this.textSize = textSize;
    }

    public SimpleListPop(List<IFilterPopItem> items, OnItemSelectLis listener)
    {
        this.items = items;
        this.listener = listener;
        initView();
        initData();
        init();
    }


    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.popup_ticket_filter);
        tvTitle = mRoot.findViewById(R.id.tvTitle);
        listView = (ListView) mRoot.findViewById(R.id.listView);
        adapter = new AbsBaseListAdapter<IFilterPopItem>(items) {
            @Override
            protected Holder getHolder(int position)
            {
                return new PopHolder();
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                dismiss();
                IFilterPopItem item = items.get(position);
                if (listener != null) {
                    listener.onItemSelect(item);
                }
            }
        });
    }

    public void hideTitle()
    {
        tvTitle.setVisibility(View.GONE);
    }

    private void initData()
    {
    }

    private void init()
    {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        setOutsideTouchable(true);
    }



    public int getMeasuredHeight()
    {
        if (tvTitle != null) {

            tvTitle.setVisibility(View.VISIBLE);
        }
        return ViewUtil.getListViewHeight(listView, items.size()) + CommonUtil.dip2px(GlobalApp.getInstance(), 40);
    }

    public int getMeasuredHeight1()
    {

        return ViewUtil.getListViewHeight(listView, items.size());
    }

    public void setSelectedItem(int selectedItem)
    {
        this.selectedItem = selectedItem;

        adapter.notifyDataSetChanged();
    }

    class PopHolder extends Holder<IFilterPopItem> {

        private TextView textView;

        @Override
        public View initView()
        {
            View view = UiUtils.inflate(R.layout.pop_ticket_item);
            textView = (TextView) view.findViewById(R.id.textView);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, getTextSize());

            return view;
        }

        @Override
        public void refreshView(IFilterPopItem data)
        {

            if (selectedItem != -1) {

                SortType type = (SortType) data;

                int sort = type.getSortIndex();

                if (sort == selectedItem) {

                    textView.setTextColor(UiUtils.getColor(R.color.color_d29a4a));

                } else {
                    textView.setTextColor(UiUtils.getColor(R.color.black_sort_323232));
                }

            }
            textView.setText(data.getName());
        }
    }

    private Float getTextSize()
    {
        return textSize;
    }
}
