package com.yzb.card.king.ui.appwidget;

import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.IFilterPopItem;
import com.yzb.card.king.bean.ticket.SortType;
import com.yzb.card.king.ui.appwidget.popup.BaseMarginTopPopupWindow;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.ticket.interfaces.OnItemSelectLis;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/3/27
 * 描  述：
 */
public class SortTopListPop extends BaseMarginTopPopupWindow{


    private View mRoot;

    List<IFilterPopItem> items = new ArrayList<IFilterPopItem>();

    private  OnItemSelectLis listener;

    private ListView listView;

    private int selectedItem = -1;

    private AbsBaseListAdapter<IFilterPopItem> adapter;
    private Float textSize = 15f;

    public SortTopListPop(View ppView, int w, int h)
    {
        super(ppView, w, h);
        this.mRoot = ppView;
        initViewC();
    }


    private void initViewC()
    {

        listView = (ListView) mRoot.findViewById(R.id.listView);

        adapter = new AbsBaseListAdapter<IFilterPopItem>(items) {
            @Override
            protected Holder getHolder(int position)
            {
                return new SortTopListPop.PopHolder();
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

    public void setOnItemSelectLis( OnItemSelectLis listener){

        this.listener = listener;

    }

    public  void addDataList( List<IFilterPopItem>  dataList){

        items.clear();

        items.addAll(dataList);

        adapter.notifyDataSetChanged();
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
