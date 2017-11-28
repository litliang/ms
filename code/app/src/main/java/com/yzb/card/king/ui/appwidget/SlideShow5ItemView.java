package com.yzb.card.king.ui.appwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.film.activity.YpDetailActivity;
import com.yzb.card.king.ui.discount.bean.FilmBean;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager图片轮播View；
 * Created by gqy on 2016/5/10.
 */
public class SlideShow5ItemView extends RelativeLayout {
    private static final int PAGE_TOTAL_COUNT = 5;
    private int pages = 0;
    //轮播图列表的url
    private List<FilmBean> filmBeanList;
    private CustomViewPager2 viewPager;
    private LinearLayout panel_dots2;
    //当前轮播页
    private int currentItem = 0;
    private int flag = -1;

    public SlideShow5ItemView(Context context) {
        super(context);
        initUI(context);
    }

    public SlideShow5ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SlideShow5ItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(context);
    }

    /**
     * 初始化UI;
     */
    private void initUI(Context context) {
        // 此处一定要为true；
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow5_items, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewPager = (CustomViewPager2) findViewById(R.id.viewPager);
    }

    /**
     * 注入数据列表；
     *
     * @param FilmBeans
     */
    public void setDataList(List<FilmBean> FilmBeans) {
        filmBeanList = FilmBeans;
        initXmPagesList();
        initDotViewList();

        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        MyPageChangeListener pageChangeListener = new MyPageChangeListener();
        viewPager.addOnPageChangeListener(pageChangeListener);

        pageChangeListener.onPageSelected(0);
    }


    /**
     * 初始化ViewPager页面列表；
     * 每页4个item；
     */
    private void initXmPagesList() {
        if (filmBeanList == null) return;

        int itemCount = filmBeanList.size();
        // 页面数；
        pages = itemCount / PAGE_TOTAL_COUNT + (itemCount % PAGE_TOTAL_COUNT == 0 ? 0 : 1);
        LogUtil.i("总的pages=" + pages);
    }

    /**
     * 初始化指示器列表；
     */
    private void initDotViewList() {
        if (panel_dots2 == null) {
            return;
        }

        panel_dots2.removeAllViews();
        LogUtil.i("itemCount==" + filmBeanList.size() + ",pages==" + pages);

        //指示器的宽和高；
        int width = CommonUtil.dip2px(getContext(), 8);
        View dotView = null;
        for (int i = 0; i < pages; i++) {

            dotView = LayoutInflater.from(getContext()).inflate(R.layout.dot_view, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
            if (i > 0) {
                lp.leftMargin = CommonUtil.dip2px(getContext(), 8);
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
            int paddingLeft = CommonUtil.dip2px(getContext(), 4);
            int paddingTop = CommonUtil.dip2px(getContext(), 2);
            dotView.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);

            LogUtil.i("dotdot===" + i);
            panel_dots2.addView(dotView, i);
        }
//        if (pages < 2) {
//            panel_dots2.setVisibility(INVISIBLE);
//            return;
//        } else {
//            panel_dots2.setVisibility(VISIBLE);
//        }
    }

    public void setPanelDots(LinearLayout panel_dots2) {
        this.panel_dots2 = panel_dots2;
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
            if (filmBeanList == null || filmBeanList.size() == 0) {
                return null;
            }

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.slideview_with_5item, null);

            int itemCount = filmBeanList.size();

            int startIndex = position * PAGE_TOTAL_COUNT;
            // 数据的end position；
            int endIndex = (itemCount - startIndex) >= PAGE_TOTAL_COUNT ? (startIndex + 4) : (itemCount - 1);

            // 过滤第一页数据；
            List<FilmBean> beanList = new ArrayList<>();
            for (int j = startIndex; j <= endIndex; j++) {
                beanList.add(filmBeanList.get(j));
            }

            List<ImageView> images = new ArrayList<>();
            List<TextView> titles = new ArrayList<>();
            List<View> panels = new ArrayList<>();

            images.add((ImageView) view.findViewById(R.id.iv_left));
            images.add((ImageView) view.findViewById(R.id.iv0));
            images.add((ImageView) view.findViewById(R.id.iv1));
            images.add((ImageView) view.findViewById(R.id.iv2));
            images.add((ImageView) view.findViewById(R.id.iv3));

            titles.add((TextView) view.findViewById(R.id.tv_bottom_title));
            titles.add((TextView) view.findViewById(R.id.tv0));
            titles.add((TextView) view.findViewById(R.id.tv1));
            titles.add((TextView) view.findViewById(R.id.tv2));
            titles.add((TextView) view.findViewById(R.id.tv3));

            panels.add(view.findViewById(R.id.panel_left));
            panels.add(view.findViewById(R.id.panel0));
            panels.add(view.findViewById(R.id.panel1));
            panels.add(view.findViewById(R.id.panel2));
            panels.add(view.findViewById(R.id.panel3));


            // 最多5个；
            if (beanList.size() > 5) {
                beanList = beanList.subList(0, 5);
            }

            //左侧描述；
            TextView tvBottomDesc = (TextView) view.findViewById(R.id.tv_bottom_desc);

            FilmBean filmBean;
            for (int i = 0; i < beanList.size(); i++) {
                panels.get(i).setVisibility(VISIBLE);
                filmBean = beanList.get(i);
                x.image().bind(images.get(i),filmBean.imageCode);
                titles.get(i).setText(filmBean.filmName);
                final FilmBean finalFilmBean = filmBean;

                panels.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), YpDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", finalFilmBean.filmId);
                        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
                        getContext().startActivity(intent);
                    }
                });
                if (i == 0) {
                    tvBottomDesc.setText(filmBean.summary);
                }
            }

            if (beanList.size() < 5) {
                for (int i = beanList.size() - 1; i < 5; i++) {
                    panels.get(i).setVisibility(INVISIBLE);
                }
            }

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if (filmBeanList == null) {
                return 0;
            }
            return filmBeanList.size() / PAGE_TOTAL_COUNT + (filmBeanList.size() % PAGE_TOTAL_COUNT == 0 ? 0 : 1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }


    /**
     * 轮播图Gridview适配器；
     */
    private class LbtGvAdapter extends BaseAdapter {
        private final List<FilmBean> beanList;

        public LbtGvAdapter(List<FilmBean> beanList) {
            this.beanList = beanList;
        }

        @Override
        public int getCount() {
            return beanList == null ? 0 : beanList.size();
        }

        @Override
        public FilmBean getItem(int position) {
            return beanList == null ? null : beanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (getContext() != null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gv_lbt_film, parent, false);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);

            final FilmBean typeBean = getItem(position);
            x.image().bind(iv,typeBean.imageCode);
            tv.setText(typeBean.filmName);

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), MsMoreActivity.class);
//                    Bundle bundle = new Bundle();
//
//                    intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
//                    getContext().startActivity(intent);
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
            if (panel_dots2 == null) {
                return;
            }
            for (int i = 0; i < pages; i++) {
                //选中页面的dot；
                if (i == pos) {
                    panel_dots2.getChildAt(i).setBackgroundResource(R.drawable.icon_round_red2);
                } else {
                    panel_dots2.getChildAt(i).setBackgroundResource(R.mipmap.icon_round_grey2);
                }
            }
        }
    }
}
