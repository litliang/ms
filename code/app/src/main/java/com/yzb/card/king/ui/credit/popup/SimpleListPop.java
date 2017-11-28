package com.yzb.card.king.ui.credit.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/25
 */

public abstract class SimpleListPop extends PopupWindow implements AdapterView.OnItemClickListener {

    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<String> dataList = new ArrayList<>();
    private Activity activity;
    private View mRoot;
    private TextView tvTitle;
    private View llTitle;
    private int gravity = Gravity.CENTER;

    public void setDataList(List<String> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    private void setBg(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public SimpleListPop(Activity activity) {
        this.activity = activity;
        initView();
        initData();
        init();
    }

    private void initView() {
        mRoot = UiUtils.inflate(R.layout.pop_simple_list);
        listView = (ListView) mRoot.findViewById(R.id.listView);
        llTitle = mRoot.findViewById(R.id.llTitle);
        tvTitle = (TextView) mRoot.findViewById(R.id.tvTitle);

    }

    public void setTitle(String title) {
        tvTitle.setText(title);
        llTitle.setVisibility(View.VISIBLE);
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    private void initData() {
        adapter = new AbsBaseListAdapter<String>(dataList) {
            @Override
            protected Holder getHolder(int position) {
                return new TextHolder();
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }


    private class TextHolder extends Holder<String> {

        private TextView textView;

        @Override
        public View initView() {

            View view = UiUtils.inflate(R.layout.holder_smiple_list);
            textView = (TextView) view.findViewById(R.id.textView);
            textView.setGravity(gravity);
            return view;
        }

        @Override
        public void refreshView(String data) {
            textView.setText(data);
            textView.setTextColor(getPostition() % 2 == 0 ? Color.parseColor("#b79739")
                    : Color.parseColor("#6b779a"));
        }
    }

    private void init() {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setTouchable(true);
        setOutsideTouchable(true);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setBg(0.7f);
        super.showAtLocation(parent, gravity, x, y);

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        setBg(0.7f);
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setBg(1f);
    }
}
