package com.yzb.card.king.ui.appwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.adapter.search.GridPagerAdapter;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：SlideShowView
 * 作者：殷曙光
 * 日期：2016/8/4 18:11
 * 描述：
 */
public class SlideShowView extends RelativeLayout {
    private CustomViewPager viewPager;
    private LinearLayout dotLayout;
    private View[] dotViews;
    private int dotSelector;

    public SlideShowView(Context context) {
        super(context);
        initUI(context);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(context);
    }

    /**
     * 初始化UI;
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow9_items, this, true);
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void refreshDot(int position) {
        for (int i = 0; i < dotViews.length; i++) {
            dotViews[i].setEnabled(i == position);
        }
    }

    /**
     *
     *
     * @author ysg
     * created at 2016/8/5 12:55
     */
    public void setPagerAdapter(final GridPagerAdapter pagerAdapter) {
        viewPager.setAdapter(pagerAdapter);
        viewPager.setLines(pagerAdapter.getPageSize()/pagerAdapter.getNumColumns());
        pagerAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int pageCount = pagerAdapter.getCount();
                initDot(pageCount);
            }
        });

    }

    /**
     * 初始化指示器
     *
     * @author ysg
     * created at 2016/8/5 14:17
     */
    private void initDot(int pageCount) {
        dotViews = new View[pageCount];
        dotLayout.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            View dotView = UiUtils.inflate(R.layout.dot_view);
            TextView textView = (TextView) dotView.findViewById(R.id.textView);
            textView.setBackgroundResource(dotSelector);
            dotLayout.addView(dotView);
            dotViews[i] = textView;
        }
        refreshDot(0);
    }

    public void setDotSelector(int dotSelector) {
        this.dotSelector = dotSelector;
    }
}
