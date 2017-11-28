package com.yzb.card.king.ui.appwidget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.manage.DiscountManager;
import com.yzb.card.king.ui.credit.bean.LeftPopItem;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 作者：殷曙光
 * 日期：2016/6/8 10:18
 * 描述：
 */
public class ListPopup extends PopupWindow {
    private Context context;
    private View mRoot;
    private ListView listView;
    private BaseAdapter adapter;
    private OnItemClick listener;
    private View.OnClickListener textViewListener;
    public String currentName;
    public String currentId;

    private List<LeftPopItem> itemList = new ArrayList<>();

    public ListPopup(Context context) {
        super(context);
        this.context = context;
        initView();
        initData();
        init();
    }

    private void initView() {
        mRoot = View.inflate(context, R.layout.popwindow_list_view, null);
        listView = (ListView) mRoot.findViewById(R.id.listView);
    }

    private void initData() {

        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        textViewListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                currentName = itemList.get(position).typeName;
                currentId = itemList.get(position).id;
                if (listener != null) listener.onItemClick(currentName, currentId);
            }
        };
        getDataList();
    }

    private void getDataList() {
        if (DiscountManager.getInstance().getDataList() != null) {

            itemList.clear();
            itemList.addAll(DiscountManager.getInstance().getDataList());
            adapter.notifyDataSetChanged();

        }
    }

    private void init() {
        this.setContentView(mRoot);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#ffffff"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        setWidth(CommonUtil.dip2px(context, 120));
        setHeight(CommonUtil.dip2px(context, 250));
        setOutsideTouchable(true);

    }


    class MyAdapter extends BaseAdapter {
        int padding;

        public MyAdapter() {
            this.padding = CommonUtil.dip2px(context, 10);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(context);
            LeftPopItem leftPopItem = itemList.get(position);
            textView.setPadding(0, padding, 0, padding);
            textView.setText(leftPopItem.typeName);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(textViewListener);
            textView.setTag(position);
            return textView;
        }
    }

    public void setOnItemClick(OnItemClick listener) {
        this.listener = listener;
    }

    public interface OnItemClick {
        void onItemClick(String name, String currentId);
    }


}
