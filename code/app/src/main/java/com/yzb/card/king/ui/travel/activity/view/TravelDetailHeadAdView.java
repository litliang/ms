package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;


import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.travel.adapter.HeaderAdAdapter;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：旅游详情-->顶部广告view；
 *
 * @author:gengqiyun
 * @date: 2016/11/12
 */
public class TravelDetailHeadAdView extends BaseViewGroup
{
    private static final int TYPE_CHANGE_AD = 0;
    private static final long INTERNAL_LEN = 2000; //间隔时间；
    private static final int IMAGE_MAX_LEN = 3; // 图片的最大张数；
    private Thread mThread;
    private ViewPager vpAd;
    private List<ImageView> ivList;
    private boolean isStopThread = false;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == TYPE_CHANGE_AD)
            {
                vpAd.setCurrentItem(vpAd.getCurrentItem() + 1);
            }
        }
    };

    public TravelDetailHeadAdView(Context context)
    {
        super(context);
    }

    public TravelDetailHeadAdView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        ivList = new ArrayList<>();
        vpAd = (ViewPager) rootView.findViewById(R.id.vp_ad);
    }

    public void setData(List<String> imageData)
    {
        ivList.clear();
        if (imageData != null && imageData.size() > 0)
        {
            int size = imageData.size();
            imageData = size > IMAGE_MAX_LEN ? imageData.subList(0, IMAGE_MAX_LEN) : imageData;

            for (int i = 0; i < imageData.size(); i++)
            {
                ivList.add(createImageView(imageData.get(i)));
            }
            HeaderAdAdapter photoAdapter = new HeaderAdAdapter(mContext, ivList);
            vpAd.setAdapter(photoAdapter);
            startADRotate();
        }
    }

    // 创建要显示的ImageView
    private ImageView createImageView(String url)
    {
        ImageView iv = new ImageView(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        iv.setLayoutParams(params);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        x.image().bind(iv, url);
        return iv;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_header_ad;
    }

    // 启动循环广告的线程
    private void startADRotate()
    {
        // 一个广告的时候不用转
        if (ivList == null || ivList.size() <= 1)
        {
            return;
        }
        if (mThread == null)
        {
            mThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    // 当没离开该页面时一直转
                    while (!isStopThread)
                    {
                        // 每隔5秒转一次
                        SystemClock.sleep(INTERNAL_LEN);
                        // 在主线程更新界面
                        mHandler.sendEmptyMessage(TYPE_CHANGE_AD);
                    }
                }
            });
            mThread.start();
        }
    }

    // 停止循环广告的线程，清空消息队列
    public void stopADRotate()
    {
        isStopThread = true;
        if (mHandler != null && mHandler.hasMessages(TYPE_CHANGE_AD))
        {
            mHandler.removeMessages(TYPE_CHANGE_AD);
        }
    }
}
