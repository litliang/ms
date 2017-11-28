package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.other.task.Init1ItemViewTask;
import com.yzb.card.king.ui.film.activity.FilmCinemaDetailActivity;
import com.yzb.card.king.ui.luxury.activity.ImgClickCallBack;
import com.yzb.card.king.ui.luxury.activity.MsDetailActivity;
import com.yzb.card.king.ui.discount.bean.LbtBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ViewPager图片轮播View；
 * Created by gqy on 2016/2/24.
 * 用法：设置，setImageDataList();
 */
public class SlideShow1ItemView extends FrameLayout implements Init1ItemViewTask.OnParseFinishListener
{
    //自动轮播的时间间隔
    private static int DURATION = 3000;

    //消息类型：下一页
    private final static int MESSAGE_NEXT_PAGE = 1;

    //轮播图列表的url
    private List<LbtBean> imagesUrls;
    private SlideUnConflictViewPager viewPager;
    private LinearLayout panel_dots;


    public void setDuration(int duration)
    {
        DURATION = duration;
    }

    //定时任务
    private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem + 1);
        }
    };
    private ImgClickCallBack callBack;
    private String typeParentId;
    private ViewGroup.LayoutParams IvLayoutParams;
    private ImageView.ScaleType scaleType;

    public SlideShow1ItemView(Context context)
    {
        this(context, null);
    }

    public SlideShow1ItemView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public SlideShow1ItemView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initUI(context);
    }

    /**
     * 图片点击 的回调；
     */
    public void setImgClickCallBack(ImgClickCallBack callBack)
    {
        this.callBack = callBack;
    }

    /**
     * 开始轮播图
     */
    private void startPlay()
    {
        if (imagesUrls == null || imagesUrls.size() < 2)
        {
            return;
        }


        handler.sendEmptyMessageDelayed(MESSAGE_NEXT_PAGE, DURATION);
    }

    /**
     * 停止轮播图切换
     */
    public void stopPlay()
    {
        handler.removeMessages(MESSAGE_NEXT_PAGE);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        viewPager = (SlideUnConflictViewPager) findViewById(R.id.viewPager);
//        viewPager.getLayoutParams().height = (int) (CommonUtil.getScreenWidth(getContext()) * imgWhRate + 0.5f);

        panel_dots = (LinearLayout) findViewById(R.id.panel_dots);
        panel_dots.removeAllViews();
    }

    /**
     * 初始化UI;
     */
    private void initUI(Context context)
    {
        // 此处一定要为false；
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
        IvLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setParam(String typeParentId, String cityId, String parentId)
    {
        this.typeParentId = parentId;
        if (TextUtils.isEmpty(typeParentId) || TextUtils.isEmpty(cityId)) return;
        Init1ItemViewTask task = new Init1ItemViewTask(typeParentId, cityId);
        task.setOnParseFinishListener(this);
        task.execute();
    }

    /**
     * 注入图片数据；
     *
     * @param imagesUrlsArg
     */
    public void setImageDataList(List<LbtBean> imagesUrlsArg)
    {
        imagesUrls = imagesUrlsArg;
        stopPlay();
        initDotViewList();
        if (imagesUrls == null || imagesUrls.size() == 0)
        {
            viewPager.setAdapter(null);
            return;
        }

        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        MyPageChangeListener pageChangeListener = new MyPageChangeListener();
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setCurrentItem(getStartPosition());
        startPlay();
    }

    /**
     * 设置初始位置，使一开始就可以向左滑动
     *
     * @return
     */
    private int getStartPosition()
    {
        int i = 10000;
        i -= i % imagesUrls.size();
        return i;
    }

    /**
     * 初始化指示器列表；
     */
    private void initDotViewList()
    {
        panel_dots.removeAllViews();
        if (imagesUrls == null)
        {
            return;
        }
        //指示器的宽和高；
        int width = CommonUtil.dip2px(getContext(), 8);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
        lp.leftMargin = CommonUtil.dip2px(getContext(), 5);

        for (int i = 0; i < imagesUrls.size(); i++)
        {
            View dotView = LayoutInflater.from(getContext()).inflate(R.layout.dot_view, null);
            final int finalI = i;
            dotView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (viewPager != null)
                    {
                        int currentItem = viewPager.getCurrentItem();
                        currentItem -= currentItem % imagesUrls.size();
                        viewPager.setCurrentItem(currentItem + finalI);
                    }
                }
            });
            dotView.setLayoutParams(lp);
            panel_dots.addView(dotView, i);
        }
    }

    @Override
    public void onParseFinish(List<LbtBean> lbtBeans)
    {
        setImageDataList(lbtBeans);
        if (listener != null)
        {
            listener.onDataLoadFinish();
        }
    }

    private void AdvertisPrice(Map<String, Object> param)
    {
        new SimpleRequest(CardConstant.card_add_addprice, param).sendRequest(new HttpCallBackData()
        {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                LogUtil.i("qqq " + JSON.toJSONString(o));
            }

            @Override
            public void onFailed(Object o)
            {
                LogUtil.i("qqq1 " + JSON.toJSONString(o));
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    /**
     * 填充ViewPager的页面适配器
     *
     * @author caizhiming
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
            position = position % imagesUrls.size();
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(getScaleType());
            imageView.setLayoutParams(new ViewPager.LayoutParams());


            final LbtBean lbtBean = imagesUrls.get(position);
            if (CommonUtil.isNotEmpty(lbtBean.picCode))
            {

                x.image().bind(imageView, ServiceDispatcher
                        .getImageUrl(lbtBean.picCode), GlobalApp.getInstance().getImageOptionsFitXY());
            }
            imageView.setOnClickListener(new ImageClickListener(lbtBean));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount()
        {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
    }

    public void setImageScaleType(ImageView.ScaleType scaleType)
    {
        this.scaleType = scaleType;
    }

    public ImageView.ScaleType getScaleType()
    {
        return scaleType == null ? ImageView.ScaleType.FIT_CENTER : scaleType;
    }

    private class ImageClickListener implements View.OnClickListener
    {

        private LbtBean lbtBean;

        public ImageClickListener(LbtBean lbtBean)
        {
            this.lbtBean = lbtBean;
        }

        @Override
        public void onClick(View v)
        {
            Map<String, Object> param = new HashMap<>();
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap();
            map.put("adId", lbtBean.adId);
            map.put("pageCode", lbtBean.pageCode);
            list.add(map);
            param.put("applicationType", "1");
            param.put("adList", JSON.toJSONString(list));
            AdvertisPrice(param);

            Intent intent;
            Bundle bundle = new Bundle();
            LogUtil.i("lbtBean " + JSON.toJSONString(lbtBean));
            //如果h5Detail不为空就直接加载页面否则跳门店详情页面
            if (TextUtils.isEmpty(lbtBean.h5Detail))
            {//门店详情
                //actionsId有多个则不跳转
                if (!TextUtils.isEmpty(lbtBean.actionIds) && !lbtBean.actionIds.contains(","))
                {
                    intent = getIntent();
                    if (intent != null)
                    {
                        intent.putExtra("id", lbtBean.actionIds);
                        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
                    }
                } else
                {
                    intent = null;
                }
            } else
            {//直接加载页面
                intent = new Intent();
                intent.setClass(getContext(), WebViewClientActivity.class);
                bundle.putString("url", lbtBean.h5Detail);
                bundle.putString("title", "");
                intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
            }
            if (intent != null)
            {
                getContext().startActivity(intent);
            }
        }
    }

    private Intent getIntent()
    {
        Intent intent = new Intent();
        switch (typeParentId)
        {
            case AppConstant.meishi_id:
                intent.setClass(getContext(), MsDetailActivity.class);
                break;
            case AppConstant.travel_id:
                intent.setClass(getContext(), TravelProductDetailActivity.class);
                break;
            case AppConstant.shechipin_id:
                intent.setClass(getContext(), MsDetailActivity.class);
                break;
            case AppConstant.film_id:
                intent.setClass(getContext(), FilmCinemaDetailActivity.class);
                break;
            case AppConstant.hotel_id:
               // intent.setClass(getContext(), HotelDetailActivity.class);
                break;
            case AppConstant.gift_card_id:
                //intent.setClass(getContext(), HotelDetailActivity.class);
                break;
            default:
                intent = null;
                break;
        }
        return intent;
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
                case ViewPager.SCROLL_STATE_IDLE:// 滑动结束，即切换完毕或者加载完毕
                    startPlay();
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:// 手势拖动中；
                    stopPlay();
                    break;
                default:
                    stopPlay();
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
            for (int i = 0; i < imagesUrls.size(); i++)
            {
                //选中页面的dot；
                if (i == (pos % imagesUrls.size()))
                {
                    panel_dots.getChildAt(i).setBackgroundResource(R.drawable.bg_rule_entiy_white);
                } else
                {
                    panel_dots.getChildAt(i).setBackgroundResource(R.drawable.bg_white_touming);
                }
            }
        }
    }

    private OnDataLoadFinishListener listener;

    public void setOnDataLoadFinishListener(OnDataLoadFinishListener listener)
    {
        this.listener = listener;
    }

    public interface OnDataLoadFinishListener
    {
        void onDataLoadFinish();
    }
}
