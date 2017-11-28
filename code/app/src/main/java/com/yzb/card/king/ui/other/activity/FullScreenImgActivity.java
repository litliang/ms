package com.yzb.card.king.ui.other.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.PinchImageView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.LinkedList;

/**
 * 功能：图片放大页(ViewPager左右滑动)；
 *
 * @author:gengqiyun
 * @date: 2016/6/1
 */
public class FullScreenImgActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private int currentPage = 0;
    private String[] imgUrls;
    private TextView allCount;
    private int dqPosition;
    private TextView dqCount;
    private LinearLayout down_load;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_full_screen);
        assignViews();
        recvIntentData();
        initViewData();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.panel_back:
                finish();
                break;
        }
    }

    private void assignViews()
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        allCount = (TextView) findViewById(R.id.total_sum);
        dqCount = (TextView) findViewById(R.id.sum_dq);
        down_load = (LinearLayout) findViewById(R.id.down_load);
        findViewById(R.id.panel_back).setOnClickListener(this);
    }


    /**
     * 接收intent数据；
     */
    private void recvIntentData()
    {
        currentPage = getIntent().getIntExtra("currentPage", 0);
        imgUrls = getIntent().getStringArrayExtra("imgUrls");
        if (imgUrls != null) {
            allCount.setText("/" + imgUrls.length);
        }

        allCount.setText("/" + imgUrls.length);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                dqPosition = position;
                dqCount.setText((dqPosition + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });


    }

    private void initViewData()
    {
        down_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

               x.image().bind(new ImageView(FullScreenImgActivity.this),imgUrls[viewPager.getCurrentItem()],new  CustomBitmapLoadCallBack());

            }
        });

        final LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount()
            {
                return imgUrls == null ? 0 : imgUrls.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object)
            {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position)
            {
                PinchImageView iv ;

                if (viewCache.size() > 0) {
                    iv = viewCache.remove();
                    iv.reset();
                } else {
                    iv = new PinchImageView(FullScreenImgActivity.this);
                }
                //大图单击关闭
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        finish();
                    }
                });

                Glide.with(FullScreenImgActivity.this).load(ServiceDispatcher.getImageUrl(imgUrls[position])).into(iv);

                container.addView(iv);

                return iv;
            }
        });

        viewPager.setCurrentItem(currentPage);
    }


    private Handler uiHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if(msg.what == 1){
                ToastUtil.i(FullScreenImgActivity.this, R.string.credit_save_success);
            }else{
                ToastUtil.i(FullScreenImgActivity.this, R.string.credit_save_failed);
            }
        }
    };


    /**
     * 图片加载进度
     */
    public class CustomBitmapLoadCallBack implements Callback.ProgressCallback<Drawable>
    {

        public CustomBitmapLoadCallBack()
        {
            //  this.holder = holder;
        }

        @Override
        public void onWaiting()
        {

            //this.holder.imgPb.setProgress(0);
        }

        @Override
        public void onStarted()
        {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading)
        {

            LogUtil.e("onLoading-->total=" + total + "  current=" + current + "  isDownloading=" + isDownloading);
        }

        @Override
        public void onSuccess(Drawable result)
        {

            Bitmap bitmap = BitmapUtil.drawableToBitmap(result);

            if (bitmap != null) {

                BitmapUtil.saveImageToGallery(FullScreenImgActivity.this, bitmap);

                uiHanlder.sendEmptyMessage(1);
            } else {

                uiHanlder.sendEmptyMessage(0);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback)
        {
            LogUtil.e("-------onError-------");
        }

        @Override
        public void onCancelled(CancelledException cex)
        {
            LogUtil.e("------onCancelled--------");
        }

        @Override
        public void onFinished()
        {
            LogUtil.e("--------onFinished------");
        }
    }

}
