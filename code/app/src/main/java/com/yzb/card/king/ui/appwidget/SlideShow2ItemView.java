package com.yzb.card.king.ui.appwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.JdmsActivity;
import com.yzb.card.king.ui.luxury.activity.MsglActivity;
import com.yzb.card.king.ui.luxury.activity.MstdActivity;
import com.yzb.card.king.ui.luxury.activity.TscActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ViewPager图片轮播View；
 * Created by gqy on 2016/4/15.
 */
public class SlideShow2ItemView extends LinearLayout {
    //轮播任务开始时的时间间隔；
    private final static int INITIAL_DELAY = 7;
    //自动轮播的时间间隔
    private final static int PERIOD = 4;
    private FragmentManager fragmentManager;

    //自动轮播启用开关
    private boolean isAutoPlay = false;
    // 总页数；
    private int pages = 2;

//    private List<Fragment> fragments = null;

    private SlideUnConflictViewPager viewPager;
    private LinearLayout panel_dots3;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currentItem);
        }
    };

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public SlideShow2ItemView(Context context) {
        super(context);
        initUI(context);
    }

    public SlideShow2ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SlideShow2ItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(context);
    }

    /**
     * 开始轮播图
     */
    private void startPlay() {
//        if (fragments == null || fragments.size() < 2) {
//            return;
//        }
        if (!isAutoPlay) {
            return;
        }

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),
                INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }

    private float imgWhRate = 266 / 426.0f;

    /**
     * 停止轮播图
     */
    public void stopPlay() {
        scheduledExecutorService.shutdown();
    }


    /**
     * 布局填充完毕时调用；
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewPager = (SlideUnConflictViewPager) findViewById(R.id.viewPager3);
//
//        int imgWidth = (int) ((DensityUtil.getScreenWidth(getContext()) - DensityUtil.dip2px(getContext(), 70)) / 2.0f + 0.5f);
//        int viewPagerHeight = (int) (imgWidth * imgWhRate + 0.5f + DensityUtil.dip2px(getContext(), 30));
//        viewPager.getLayoutParams().height = viewPagerHeight;

        panel_dots3 = (LinearLayout) findViewById(R.id.panel_dots3);
    }

    /**
     * 初始化UI;
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow2_items, this, true);
        setOrientation(VERTICAL);
    }

    /**
     * 注入数据列表；
     */
    public void initDataList() {
        initXmPagesList();
        initDotViewList();

//        VPStateAdapter pagerAdapter = new VPStateAdapter(fragmentManager, fragments);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        MyPageChangeListener pageChangeListener = new MyPageChangeListener();
        viewPager.addOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(0);

        if (isAutoPlay) {
            startPlay();
        }
    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //MsSlideViewFragment02
            if (position < 0 || position >= pages) {
                return null;
            }
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.ms_slide_view_fragment02, null);
            ImageView iv0 = (ImageView) view.findViewById(R.id.iv0);
            ImageView iv1 = (ImageView) view.findViewById(R.id.iv1);

            // 第一页；
            if (position == 0) {
                iv0.setBackgroundResource(R.mipmap.food_pic_msgl);
                iv1.setBackgroundResource(R.mipmap.food_pic_tsc);
                iv0.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readyGo(MsglActivity.class);
                    }
                });
                iv1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readyGo(TscActivity.class);
                    }
                });
            } else if (position == 1) {   // 第二页；
                iv0.setBackgroundResource(R.mipmap.food_pic_tsc);
                iv1.setBackgroundResource(R.mipmap.food_pic_tsc);

                iv0.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readyGo(MstdActivity.class);
                    }
                });
                iv1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readyGo(JdmsActivity.class);
                    }
                });
            }
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return pages;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public void readyGo(Class claz) {
        Intent intent = new Intent(getContext(), claz);
        getContext().startActivity(intent);
    }

    /**
     * 初始化ViewPager页面列表；
     * 每页4个item；
     */

    private void initXmPagesList() {
        int itemCount = 4;
        // 页面数；
        pages = itemCount / 2 + (itemCount % 2 == 0 ? 0 : 1);
        LogUtil.i("总的pages=" + pages);
//
//        if (fragments == null) {
//            fragments = new ArrayList<>();
//        }
//        fragments.clear();

//        MsSlideViewFragment02 fragmentItem;
//
//        // 初始化Fragments；
//        for (int i = 0; i < pages; i++) {
//            // 注入项目列表；
//            fragmentItem = MsSlideViewFragment02.newInstance(i, "");
//            fragments.add(fragmentItem);
//        }
    }

    /**
     * 初始化指示器列表；
     */
    private void initDotViewList() {
        panel_dots3.removeAllViews();
        //指示器的宽和高；
        int width = CommonUtil.dip2px(getContext(), 8);
        View dotView;
        for (int i = 0; i < pages; i++) {

            dotView = LayoutInflater.from(getContext()).inflate(R.layout.dot_view, null);
            LayoutParams lp = new LayoutParams(width, width);
            if (i > 0) {
                lp.leftMargin = CommonUtil.dip2px(getContext(), 5);
            }
            final int finalI = i;
            dotView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewPager != null) {
                        viewPager.setCurrentItem(finalI);
                    }
                }
            });

            dotView.setLayoutParams(lp);
            panel_dots3.addView(dotView, i);
        }

//        if (pages < 2) {
//            panel_dots3.setVisibility(INVISIBLE);
//            return;
//        } else {
//            panel_dots3.setVisibility(VISIBLE);
//        }
    }


    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author caizhiming
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
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
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
//            isAutoPlay = true;

            for (int i = 0; i < pages; i++) {
                //选中页面的dot；
                if (i == pos) {
                    panel_dots3.getChildAt(i).setBackgroundResource(R.drawable.icon_round_red2);
                } else {
                    panel_dots3.getChildAt(i).setBackgroundResource(R.mipmap.icon_round_grey2);
                }
            }
        }
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {

            if (isAutoPlay) {
                synchronized (viewPager) {
                    currentItem = (currentItem + 1) % pages;
                    handler.obtainMessage().sendToTarget();
                }
            }
        }
    }

}
