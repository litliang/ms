package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelImageBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.adapter.HotelPicAdapter;
import com.yzb.card.king.ui.hotel.fragment.PicFragment;
import com.yzb.card.king.ui.hotel.model.IHotelSellingPointDetail;
import com.yzb.card.king.ui.hotel.persenter.HotelPicPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.WebViewSettings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店图片集合
 */
public class HotelPicActivity extends BaseActivity implements BaseViewLayerInterface {
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private FrameLayout frm;

    private WebView webView;

    private  ArrayList<Fragment>  pagerList = new ArrayList<>();

    public String hotelId;

    public List<HotelImageBean> listAll = new ArrayList<>();

    public List<String> tabNames = new ArrayList<>();

    private HotelPicPresenter picPresenter;


    private SlidingTabLayout tabLayout_8;

    private String[] mTitleTab = {"图片", "视频"};

    private String videoUrl = null;


    private LinearLayout llVideo,llImage;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_hotel_pic);

        frm = (FrameLayout) findViewById(R.id.frl);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        llImage = (LinearLayout) findViewById(R.id.llImage);

        llVideo = (LinearLayout) findViewById(R.id.llVideo);

        webView = (WebView) findViewById(R.id.webView);
        WebViewSettings.setting(webView, null);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8") ;//这句话去掉也没事。。只是设置了编码格式
        settings.setJavaScriptEnabled(true);  //这句话必须保留。。不解释
        settings.setDomStorageEnabled(true);//这句话必须保留。。否则无法播放优酷视频网页。。其他的可以
        webView.setWebChromeClient(new WebChromeClient());//重写一下。有的时候可能会出现问题
        webView.setWebViewClient(new WebViewClient(){//不写的话自动跳到默认浏览器了。。跳出APP了。。怎么能不写？
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//这个方法必须重写。否则会出现优酷视频周末无法播放。周一-周五可以播放的问题
                if(url.startsWith("intent")||url.startsWith("youku")||url.startsWith("qq")){
                    return true;
                }else{
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });

        picPresenter = new HotelPicPresenter(this);

        View decorView = getWindow().getDecorView();

        SegmentTabLayout tabLayout_1 = UiUtils.find(decorView, R.id.tl_1);

        tabLayout_1.setTabData(mTitleTab);

        tabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position)
            {

                if(position == 0){

                    llImage.setVisibility(View.VISIBLE);

                    tabLayout_8.setVisibility(View.VISIBLE);

                    llVideo.setVisibility(View.GONE);

                    try {
                        webView.getClass().getMethod("onPause").invoke(webView,(Object[])null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                }else if(position == 1){


                    llImage.setVisibility(View.GONE);

                    tabLayout_8.setVisibility(View.GONE);

                    llVideo.setVisibility(View.VISIBLE);

                    try {
                        webView.getClass().getMethod("onResume").invoke(webView,(Object[])null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onTabReselect(int position)
            {
            }
        });
    }


    private void initData()
    {
        hotelId = getIntent().getLongExtra("hotelId",-1)+"";

        videoUrl =  getIntent().getStringExtra("videoUrl");

        getListData();

        if(TextUtils.isEmpty(videoUrl)){

            webView.loadUrl("www.baidu.com");

        }else {

            webView.loadUrl(videoUrl);
        }

    }

    /**
     * 获取酒店图片列表
     */
    private void getListData()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("hotelId", hotelId);
        picPresenter.getHotelPic(param, CardConstant.HOTEL_IMAGES_LIST_URL);
    }

    private String[] mTitles;

    private void getPagerList()
    {

        pagerList.clear();
        //分类条目
        int size = tabNames.size();

        mTitles = new String[size];

        for (int i = 0; i < size; i++) {

            String bean = tabNames.get(i);

            pagerList.add(PicFragment.newInstance(i));

            mTitles[i] = bean;
        }

        View decorView = getWindow().getDecorView();

        if (viewPager == null) {
            UiUtils.find(decorView, R.id.viewPager);
        }

        adapter = new HotelPicAdapter(getSupportFragmentManager(), pagerList);
        viewPager.setAdapter(adapter);


        if (tabLayout_8 == null) {

            tabLayout_8 = UiUtils.find(decorView, R.id.tl_8);

        } else {
            //  tabLayout_8.removeAllViews();
        }

        tabLayout_8.setViewPager(viewPager, mTitles, this, pagerList);

    }


    public void back(View v)
    {
        finish();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        try {
            webView.getClass().getMethod("onPause").invoke(webView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        try {
            webView.getClass().getMethod("onResume").invoke(webView,(Object[])null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == IHotelSellingPointDetail.HOTEL_PIC)
        {
                List<HotelImageBean> listDataAll = JSON.parseArray(o+"",HotelImageBean.class);

                if (listDataAll.size() > 0)
                {
                    tabNames.clear();

                    tabNames.add("全部");

                    this.listAll.clear();

                    this.listAll.addAll( listDataAll);

                    for (int i = 0; i < listDataAll.size(); i++)
                    {
                        String name = listDataAll.get(i).getTypeName();
                        tabNames.add(name);

                    }
                    getPagerList();
                } else
                {
                    frm.setVisibility(View.VISIBLE);
                    View nullView = View.inflate(this, R.layout.pic_null_info, null);
                    frm.addView(nullView);
                }


        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        frm.setVisibility(View.VISIBLE);
        View nullView = View.inflate(this, R.layout.pic_null_info, null);
        frm.addView(nullView);
    }
}
