package com.yzb.card.king.ui.transport.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.TouchableTabView;
import com.yzb.card.king.ui.base.BaseActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_price_evaluate)
public class PriceEvaluateActivity extends BaseActivity implements TouchableTabView.OnTabChangeListener
{

    private String[] tabs = null;
    private TouchableTabView llTab;
    private ViewPager viewPager;
    private PriceEvaluateAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        llTab = (TouchableTabView) findViewById(R.id.llTab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        llTab.setOnTabChangeListener(this);
    }

    private void initData()
    {
        this.tabs = this.getResources().getStringArray(R.array.estimated_fare);
        adapter = new PriceEvaluateAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    /**
     * tab改变事件
     *
     * @author ysg
     * created at 2016/6/3 9:53
     */
    @Override
    public void onTabChange(int position)
    {
        viewPager.setCurrentItem(position);
    }

    private class PriceEvaluateAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            return tabs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view = View.inflate(PriceEvaluateActivity.this, R.layout.transport_special_pager_item, null);
            TextView textView = (TextView) view.findViewById(R.id.tvPrice);
            textView.setText(position * 10 + 10 + "");
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}
