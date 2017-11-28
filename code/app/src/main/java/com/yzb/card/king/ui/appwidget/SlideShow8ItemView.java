package com.yzb.card.king.ui.appwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.luxury.activity.MsThirdClassicActivity;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ViewPager图片轮播View；
 * Created by gqy on 2016/5/10.
 */
public class SlideShow8ItemView extends RelativeLayout implements View.OnClickListener
{
    //轮播任务开始时的时间间隔；
    private final static int INITIAL_DELAY = 6;
    //自动轮播的时间间隔
    private final static int PERIOD = 4;
    private static final int PAGE_TOTAL_COUNT = 8; // 每页显示的总数量；
    private FragmentManager fragmentManager;

    //自动轮播启用开关
    private boolean isAutoPlay = false;

    private int pages = 0;

//    private List<Fragment> fragments = null;

    //轮播图列表的url
    private List<ChildTypeBean> childTypeBeanList;
    private CustomViewPager viewPager;
    private LinearLayout panel_dots2;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            viewPager.setCurrentItem(currentItem);
        }
    };
    private int flag = -1;

    public void setFragmentManager(FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
    }

    public SlideShow8ItemView(Context context)
    {
        super(context);
        initUI(context);
    }

    public SlideShow8ItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initUI(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SlideShow8ItemView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initUI(context);
    }

    /**
     * 开始轮播图
     */
    private void startPlay()
    {
        if (childTypeBeanList == null || childTypeBeanList.size() < 2)
        {
            return;
        }
        if (!isAutoPlay)
        {
            return;
        }
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),
                INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播图切换
     */
    public void stopPlay()
    {
        scheduledExecutorService.shutdown();
    }

    /**
     * 初始化UI;
     */
    private void initUI(Context context)
    {
        // 此处一定要为true；
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow8_items, this, true);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        viewPager = (CustomViewPager) findViewById(R.id.viewPager2);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        }
    }


    public List<ChildTypeBean> getChildTypeBeanList()
    {
        return childTypeBeanList;
    }

    /**
     * 注入数据列表；
     *
     * @param childTypeBeans
     * @param flag           页面类型；
     */
    public void setDataList(List<ChildTypeBean> childTypeBeans, int flag)
    {
        childTypeBeanList = childTypeBeans;
        this.flag = flag;
        initXmPagesList();
        initDotViewList();

        MyPagerAdapter pagerAdapter = new MyPagerAdapter();

        // 主要控制ViewPager的高度；
        viewPager.setLines(childTypeBeans.size() > 4 ? 2 : 1);
        viewPager.setAdapter(pagerAdapter);

        MyPageChangeListener pageChangeListener = new MyPageChangeListener();
        viewPager.addOnPageChangeListener(pageChangeListener);

        pageChangeListener.onPageSelected(0);

        if (isAutoPlay)
        {
            startPlay();
        }
    }


    /**
     * 初始化ViewPager页面列表；
     * 每页4个item；
     */
    private void initXmPagesList()
    {
        if (childTypeBeanList == null) return;

        int itemCount = childTypeBeanList.size();
        // 页面数；
        pages = itemCount / PAGE_TOTAL_COUNT + (itemCount % PAGE_TOTAL_COUNT == 0 ? 0 : 1);
        LogUtil.i("总的pages=" + pages);
    }

    /**
     * 初始化指示器列表；
     */
    private void initDotViewList()
    {
        if (panel_dots2 == null)
        {
            return;
        }

        panel_dots2.removeAllViews();
        LogUtil.i("itemCount==" + childTypeBeanList.size() + ",pages==" + pages);

        //指示器的宽和高；
        int width = CommonUtil.dip2px(getContext(), 8);
        View dotView = null;
        for (int i = 0; i < pages; i++)
        {

            dotView = LayoutInflater.from(getContext()).inflate(R.layout.dot_view, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
            if (i > 0)
            {
                lp.leftMargin = CommonUtil.dip2px(getContext(), 8);
            }
            final int finalI = i;
            dotView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (viewPager != null)
                    {
                        viewPager.setCurrentItem(finalI);
                    }
                }
            });

            dotView.setLayoutParams(lp);
            int paddingLeft = CommonUtil.dip2px(getContext(), 4);
            int paddingTop = CommonUtil.dip2px(getContext(), 2);
            dotView.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);

            LogUtil.i("dotdot===" + i);
            panel_dots2.addView(dotView, i);
        }
    }

    public void setPanelDots(LinearLayout panel_dots2)
    {
        this.panel_dots2 = panel_dots2;
    }


    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter
    {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            if (childTypeBeanList == null || childTypeBeanList.size() == 0)
            {
                return null;
            }

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.gv_layout, null);
            GridView gridView = (GridView) view.findViewById(R.id.gv);
            int itemCount = childTypeBeanList.size();

            int startIndex = position * PAGE_TOTAL_COUNT;

            // 数据的end position；
            int endIndex = (itemCount - startIndex) >= PAGE_TOTAL_COUNT ? (startIndex + 7) : (itemCount - 1);

            final List<ChildTypeBean> beanList = new ArrayList<>();
            for (int j = startIndex; j <= endIndex; j++)
            {
                beanList.add(childTypeBeanList.get(j));
            }
            LbtGvAdapter adapter = new LbtGvAdapter(beanList);
            gridView.setAdapter(adapter);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount()
        {
            if (childTypeBeanList == null)
            {
                return 0;
            }
            return childTypeBeanList.size() / PAGE_TOTAL_COUNT + (childTypeBeanList.size() % PAGE_TOTAL_COUNT == 0 ? 0 : 1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
    }


    /**
     * 轮播图Gridview适配器；
     */
    private class LbtGvAdapter extends BaseAdapter
    {
        private final List<ChildTypeBean> beanList;

        public LbtGvAdapter(List<ChildTypeBean> beanList)
        {
            this.beanList = beanList;
        }

        @Override
        public int getCount()
        {
            return beanList == null ? 0 : beanList.size();
        }

        @Override
        public ChildTypeBean getItem(int position)
        {
            return beanList == null ? null : beanList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            if (getContext() != null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gv_lbt, parent, false);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);

            final ChildTypeBean typeBean = getItem(position);
            x.image().bind(iv, typeBean.typeImage);
            tv.setText(typeBean.typeName);

            convertView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();

                    ChildTypeBean data = beanList.get(position);
                    bundle.putString("typeName", beanList.get(position).typeName);

                    // 区分美食和旅游的三级问题；
                    if (flag == AppConstant.ms_parent_id || flag == AppConstant.travel_parent_id)
                    {
                        bundle.putString("typeGrandParentId", data.parentId);
                        bundle.putString("typeParentId", data.id);

                        if (flag == AppConstant.ms_parent_id)
                        {
                            bundle.putString("typeGrandName", getContext().getResources().getString(R.string.category_ms));
                        } else if (flag == AppConstant.travel_parent_id)
                        {
                            bundle.putString("typeGrandName", getContext().getResources().getString(R.string.category_travel));
                        }
                    } else
                    {
                        bundle.putString("typeId", beanList.get(position).id);
                        bundle.putString("typeParentId", beanList.get(position).parentId);

                        //添加顶部左侧标题文字；
                        if (flag == AppConstant.scp_parent_id)
                        {
                            bundle.putString("typeGrandName", getContext().getResources().getString(R.string.category_scp));
                        } else if (flag == AppConstant.travel_parent_id)
                        {
                            bundle.putString("typeGrandName", getContext().getResources().getString(R.string.category_travel));
                        }
                    }

                    LogUtil.i("点击的子分类的参数typeId；==" + beanList.get(position).id
                            + ",typeParentId=" + flag + ",typeName=" + beanList.get(position).typeName);

                    intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);

                    if (flag == 8)
                    {
//                        intent.setClass(getContext(), HotelListActivity.class);
//                        getContext().startActivity(intent);
                    } else if (flag == 1)
                    {// 美食；跳转至三级页面；
                        intent.setClass(getContext(), MsThirdClassicActivity.class);
                        getContext().startActivity(intent);
                    } else if (flag == 5)
                    {// 奢侈品；
//                        intent.setClass(getContext(), MsMoreActivity.class);
//                        getContext().startActivity(intent);
                    } else if (flag == AppConstant.travel_parent_id)
                    {// 旅游；
//                        intent.setClass(getContext(), GtyActivity.class);
//                        getContext().startActivity(intent);
                    }
                }
            });
            return convertView;
        }
    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author caizhiming
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrollStateChanged(int arg0)
        {
            switch (arg0)
            {
                case ViewPager.SCROLL_STATE_DRAGGING:// 手势拖动中；
//                    isAutoPlay = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:// 界面切换中
//                    isAutoPlay = false;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:// 滑动结束，即切换完毕或者加载完毕
//                    isAutoPlay = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }

        @Override
        public void onPageSelected(int pos)
        {
            currentItem = pos;
//            isAutoPlay = true;

            if (panel_dots2 == null)
            {
                return;
            }
            for (int i = 0; i < pages; i++)
            {
                //选中页面的dot；
                if (i == pos)
                {
                    panel_dots2.getChildAt(i).setBackgroundResource(R.drawable.icon_round_red2);
                } else
                {
                    panel_dots2.getChildAt(i).setBackgroundResource(R.mipmap.icon_round_grey2);
                }
            }
        }
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable
    {

        @Override
        public void run()
        {

            if (isAutoPlay)
            {
                synchronized (viewPager)
                {
                    currentItem = (currentItem + 1) % pages;
                    handler.obtainMessage().sendToTarget();
                }
            }
        }
    }

}
