package com.yzb.card.king.ui.appwidget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/1 10:46
 */
public abstract class ThreeListPopup extends PopupWindow implements View.OnClickListener
{
    private View mRoot;
    private ListView lvFirst;
    private ListView lvSecond;
    private ListView lvThird;
    private View bottomView;
    private Context context;

    public ThreeListPopup(Context context)
    {
        this.context = context;
        initView();
        initData();
        init();
    }

    protected void initView()
    {
        mRoot = UiUtils.inflate(R.layout.popwindow_nearby);
        lvFirst = (ListView) mRoot.findViewById(R.id.lvFirst);
        lvSecond = (ListView) mRoot.findViewById(R.id.lvSecond);
        lvThird = (ListView) mRoot.findViewById(R.id.lvThird);
        FrameLayout flBottom = (FrameLayout) mRoot.findViewById(R.id.flBottom);
        initBottomView(flBottom);
        bottomView = mRoot.findViewById(R.id.bottomView);
        initListener();
        getPresenter();
    }

    /**
     * 初始化flBottom,有需要的子类可以覆盖此类，默认不添加任何view
     * @param flBottom
     */
    protected void initBottomView(FrameLayout flBottom){

    }

    protected abstract void getPresenter();

    protected void initListener()
    {
        bottomView.setOnClickListener(this);
    }

    protected void initData()
    {
        ListView[] listViews = {lvFirst, lvSecond, lvThird};
        initListView(listViews);
    }


    private void init()
    {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bottomView://灰色背景点击事件
                dismiss();
                break;
        }
    }


    protected void hideFirstListView()
    {
        lvFirst.setVisibility(View.GONE);
    }


    protected Context getContext()
    {
        return context;
    }

    protected void hideThirdListView()
    {
        lvThird.setVisibility(View.GONE);
    }

    public View getmRoot()
    {
        return mRoot;
    }

    protected abstract void initListView(ListView[] listViews);


}
