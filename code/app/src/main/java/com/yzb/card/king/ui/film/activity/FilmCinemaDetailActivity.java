package com.yzb.card.king.ui.film.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.film.presenter.CinemaDetailsPersenter;
import com.yzb.card.king.ui.film.view.CinemaDetailsView;
import com.yzb.card.king.ui.other.activity.FullScreenImgActivity;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.ui.other.activity.PjActivity;
import com.yzb.card.king.ui.discount.adapter.FilmGalleryAdapter;
import com.yzb.card.king.ui.discount.adapter.VPAdapter;
import com.yzb.card.king.ui.discount.fragment.ClockMsgDialogFragment;
import com.yzb.card.king.ui.discount.fragment.FilmEventsFragment;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.appwidget.CustomGallery;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.photoutils.BlurUtil;

import org.xutils.common.Callback;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 影院详情
 */
public class FilmCinemaDetailActivity extends BaseActivity implements
        OnClickListener, AdapterView.OnItemSelectedListener
        , CinemaDetailsView
{

    private ImageView storePhoto;
    private TextView storeName;
    private TextView picCodes;
    private StarBar storeRating;
    private TextView storeVote;
    private TextView storeAddr;
    private TextView storeTel;
    private TextView storeVoteCount;
    private ImageView collectStatus;

    private String[] imageUrls;

    private TextView filmName;
    private TextView collectCount;
    private StarBar filmRating;
    private TextView filmVote;
    private TextView duration;
    private String[] titles;
    private String[] dates;

    private String storeId;
    private boolean isChecked;

    private LinearLayout iv_back;
    private ImageView iv_clock;
    private ImageView iv_share;

    private RelativeLayout btnCollect;

    private CustomGallery mGallery;
    private List<Map> mFilmLists;
    private String customerId;

    private String mShopIntro;

    /**
     * 用来保存制作过的毛玻璃
     */
    private Map<String, Drawable> cacheDrawable = new HashMap<>();
    private CinemaDetailsPersenter persenter;
    private int r;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_cinema_detail);
        init();
        persenter = new CinemaDetailsPersenter(this);
        queryCinemaDetail();
        queryFilmList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            queryCinemaDetail();
        }
    }

    public void init()
    {
        UserBean userBean = UserManager.getInstance().getUserBean();
        customerId = userBean == null ? "" : userBean.getAmountAccount();

        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        if (bundle != null)
        {
            storeId = bundle.getString("id", "");
        }
        LogUtil.i("影院明细的id==" + storeId);

        iv_back = (LinearLayout) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_clock = (ImageView) findViewById(R.id.iv_clock);
        iv_clock.setOnClickListener(this);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);

        storePhoto = (ImageView) findViewById(R.id.store_photo);
        storePhoto.setOnClickListener(this);
        storeName = (TextView) findViewById(R.id.store_name);
        storeName.setOnClickListener(this);
        picCodes = (TextView) findViewById(R.id.pic_codes);
        storeRating = (StarBar) findViewById(R.id.starBar);
        storeVote = (TextView) findViewById(R.id.store_vote);
        storeAddr = (TextView) findViewById(R.id.store_addr);
        storeAddr.setOnClickListener(this);
        storeTel = (TextView) findViewById(R.id.store_tel);
        storeTel.setOnClickListener(this);
        storeVoteCount = (TextView) findViewById(R.id.store_vote_count);
        storeVoteCount.setOnClickListener(this);
        collectStatus = (ImageView) findViewById(R.id.collect_status);

        btnCollect = (RelativeLayout) findViewById(R.id.btn_collect);
        btnCollect.setOnClickListener(this);
        filmName = (TextView) findViewById(R.id.filmName);
        collectCount = (TextView) findViewById(R.id.collectCount);
        filmRating = (StarBar) findViewById(R.id.filmRating);
        filmVote = (TextView) findViewById(R.id.filmVote);
        duration = (TextView) findViewById(R.id.duration);


        titles = marshallTitles();
        dates = marshallDates();

        mGallery = (CustomGallery) findViewById(R.id.gallery);
        mGallery.setCameraDistance(1);
        mGallery.setSpacing(1);
        mGallery.setClipChildren(true);
        mGallery.setUnselectedAlpha(new Float(0.5));
        mGallery.setOnItemSelectedListener(this);
    }

    /**
     * 组织tab切换标题
     *
     * @return
     */
    public String[] marshallTitles()
    {

        Date today = new Date();
        // 今天
        String todayStr = DateUtil.date2String(today, DateUtil.DATE_FORMAT_DATE_DAY2);
        todayStr = "今天 " + todayStr;
        // 明天
        Date secondDay = DateUtil.addDay(today, 1);
        String secondDayStr = DateUtil.date2String(secondDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        secondDayStr = "明天 " + secondDayStr;
        // 后天
        Date thirdDay = DateUtil.addDay(today, 2);
        String thirdDayStr = DateUtil.date2String(thirdDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        thirdDayStr = "后天 " + thirdDayStr;
        // 大后天
        Date forthDay = DateUtil.addDay(today, 3);
        String forthDayStr = DateUtil.date2String(forthDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        String forthDayStr2 = DateUtil.date2String(forthDay, DateUtil.DATE_FORMAT_DATE);
        String week = DateUtil.getWeek(forthDayStr2);
        forthDayStr = "周" + week + " " + forthDayStr;

        String[] titles = {todayStr, secondDayStr, thirdDayStr, forthDayStr};

        return titles;
    }

    /**
     * 组织时间
     *
     * @return
     */
    public String[] marshallDates()
    {

        Date today = new Date();
        // 今天
        String todayStr = DateUtil.date2String(today, DateUtil.DATE_FORMAT_DATE);
        // 明天
        Date secondDay = DateUtil.addDay(today, 1);
        String secondDayStr = DateUtil.date2String(secondDay, DateUtil.DATE_FORMAT_DATE);
        // 后天
        Date thirdDay = DateUtil.addDay(today, 2);
        String thirdDayStr = DateUtil.date2String(thirdDay, DateUtil.DATE_FORMAT_DATE);
        // 大后天
        Date forthDay = DateUtil.addDay(today, 3);
        String forthDayStr = DateUtil.date2String(forthDay, DateUtil.DATE_FORMAT_DATE);

        String[] dates = {todayStr, secondDayStr, thirdDayStr, forthDayStr};

        return dates;
    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.store_photo:
                if (imageUrls != null)
                {
                    Intent intent1 = new Intent(FilmCinemaDetailActivity.this, FullScreenImgActivity.class);
                    intent1.putExtra("currentPage", 0);
                    intent1.putExtra("imgUrls", imageUrls);
                    startActivity(intent1);
                    break;
                }
                break;

            case R.id.btn_collect:
                collections();
                break;
            case R.id.store_name://显示活动内容
                Bundle bundle = new Bundle();
                bundle.putString("titleName", getString(R.string.huodongneirong));
                bundle.putString("content", mShopIntro);
                readyGoWithBundle(this, WebViewClientActivity.class, bundle);
                break;
            case R.id.store_tel://拨打电话
                String tel = storeTel.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (FilmCinemaDetailActivity.this.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + tel));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(callIntent, AppConstant.REQ_PHONE);
                    }
                } else
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + tel));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(callIntent, AppConstant.REQ_PHONE);
                }
                break;
            case R.id.store_addr://地图
                ToastUtil.i(FilmCinemaDetailActivity.this, "敬请期待");
                break;
            case R.id.iv_clock://提醒
                if (!UserManager.getInstance().isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }

                final ClockMsgDialogFragment cdf = ClockMsgDialogFragment.getInstance("", "");
                cdf.setCallBack(new IDialogCallBack()
                {
                    @Override
                    public void dialogCallBack(Object... obj)
                    {
                        cdf.dismiss();
                        // 确认；
                        if (obj != null && "1".equals(String.valueOf(obj[0])))
                        {
                            exeTimedTask();
                        }
                    }

                });
                cdf.show(getSupportFragmentManager(), "ClockMsgDialogFragment");
                break;
            case R.id.iv_share://分享
                ShareDialogFragment sdf = ShareDialogFragment.getInstance("", "");
                sdf.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
            case R.id.iv_back://返回
                setResult(AppConstant.RES_BACK);
                finish();
                break;
            case R.id.store_vote_count://评论
                if (!UserManager.getInstance().isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }

                Intent intent = new Intent(this, PjActivity.class);
                intent.putExtra("detailId", storeId);
                intent.putExtra("type", "");
                startActivityForResult(intent, 1);
                break;
        }
    }

    /**
     * 执行定时任务；
     */
    private void exeTimedTask()
    {
    }

    /**
     * 查询影院详情
     */
    public void queryCinemaDetail()
    {

        Map<String, Object> param = new HashMap<>();
        param.put("storeId", storeId);
        param.put("customerId", customerId);

        persenter.sendCinemaDetailsRequest(param, CardConstant.card_app_storeinfo);
    }

    /**
     * 查询电影列表
     */
    public void queryFilmList()
    {

        Map<String, Object> param = new HashMap<>();
        param.put("storeId", storeId);
        persenter.sendMovieListRequest(param, CardConstant.card_app_query_film);

    }

    /**
     * 给电影属性赋值
     */
    public void setFilmValue(Map<String, Object> film)
    {

        filmName.setText(String.valueOf(film.get("filmName")));
        collectCount.setText(String.valueOf(film.get("collectionCount")));
        String vote = String.valueOf(film.get("vote"));
        Float voteF = Float.parseFloat(vote) / 2;
        filmRating.setStarMarkAndSore(voteF);
        filmVote.setText(new BigDecimal(vote).setScale(1) + "分");
        duration.setText(String.valueOf(film.get("duration")));

        String filmId = String.valueOf(film.get("filmId"));
        String filmName = String.valueOf(film.get("filmName"));
        String storeN = storeName.getText().toString();

        // 场次列表
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            FilmEventsFragment fragment = FilmEventsFragment.newInstance(storeId, filmId, dates[i], titles[i], storeN, filmName);
            fragments.add(fragment);
        }
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), fragments);

        vpAdapter.setTitles(titles);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     * 收藏操作
     */
    public void collections()
    {

        Map<String, Object> param = new HashMap<>();
        param.put("category", AppConstant.collect_film_category); //类型；
        param.put("detailsId", storeId);
        param.put("type", AppConstant.collect_shop_type);
        if (isChecked)
        {
            param.put("status", "0");// 状态为true,已收藏,操作需取消收藏收藏
        } else
        {
            param.put("status", "1");// 状态为true,未收藏或取消收藏,操作需进行收藏
        }
        persenter.sendCollectRequest(param, CardConstant.card_app_collections);
    }

    /**
     * create by timmy
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (mFilmLists == null || mFilmLists.size() == 0)
        {
            return;
        }
        ImageView iv = (ImageView) view.findViewById(R.id.img);
        int w = iv.getLayoutParams().width;
        int h = iv.getLayoutParams().height;
        RelativeLayout.LayoutParams params = getLayoutParams(w, h * 2 + 50);
        iv.setLayoutParams(params);
        for (int i = 0; i < parent.getChildCount(); i++)
        {
            View v = parent.getChildAt(i);
            if (v != view)
            {
                RelativeLayout.LayoutParams chilLyParams = getLayoutParams(w, h);
                ImageView iv2 = (ImageView) v.findViewById(R.id.img);
                iv2.setLayoutParams(chilLyParams);
            }
        }

        setFilmValue(mFilmLists.get(position));
        r = position % mFilmLists.size();
        Map<String, Object> map = mFilmLists.get(r);
        if (cacheDrawable.containsKey(map.get("imageCode")))
        {
            LogUtil.LCi("  图片已经存在 毛玻璃 ");
            mGallery.setBlurBackgroundDrawable(cacheDrawable.get(map.get("imageCode")), R.mipmap.icon_footer_sc_red);
        } else
        {
            LogUtil.LCi("  图片没有存在 制作毛玻璃");
            String imageUri = ServiceDispatcher.getImageUrl(String.valueOf(map.get("imageCode")));//ServiceDispatcher.url_image + "getImg/" + String.valueOf(map.get("imageCode")) + "/0";
            x.image().loadDrawable(imageUri, GlobalApp.getInstance().getImageOptionsLogo(), new CustomBitmapLoadCallBack());
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    private RelativeLayout.LayoutParams getLayoutParams(int width, int height)
    {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(width, height);
        marginLayoutParams.setMargins(0, 0, 0, 0);
        return new RelativeLayout.LayoutParams(marginLayoutParams);
    }

    @Override
    public void getCinemaDetails(Map<String, Object> data)
    {
        String imageUri = ServiceDispatcher.getImageUrl(String.valueOf(data.get("storePhoto")));//url_image + "getImg/" + String.valueOf(data.get("storePhoto")) + "/0";
        LogUtil.LCi(" 影院详情图片路径  " + imageUri);
        x.image().bind(storePhoto, imageUri, GlobalApp.getInstance().getImageOptionsLogo());
        String pic = String.valueOf(data.get("picCodes"));
        if (StringUtils.isEmpty(pic))
        {
            picCodes.setText("0张");
        } else
        {
            imageUrls = pic.split(",");
            picCodes.setText(imageUrls.length + "张");
        }
        storeName.setText(String.valueOf(data.get("storeName")));
        String vote = String.valueOf(data.get("vote"));
        Float voteF = Float.parseFloat(vote) / 2;
        storeRating.setStarMarkAndSore(voteF);
        storeVote.setText(new BigDecimal(vote).setScale(1) + "分");
        storeAddr.setText(String.valueOf(data.get("storeAddr")));
        storeTel.setText(String.valueOf(data.get("storeTel")));
        String voteCount = String.valueOf(data.get("voteCount"));
        LogUtil.LCi("   评价的人数  " + voteCount + "  长度  " + voteCount.length());
        if (!StringUtils.isEmpty(voteCount))
        {
            storeVoteCount.setText("评价 (" + String.valueOf(data.get("voteCount")) + ")");
        }
        String isColletcion = String.valueOf(data.get("isColletcion"));
        if ("1".equals(isColletcion))
        {
            isChecked = true;
            collectStatus.setImageResource(R.mipmap.icon_footer_sc_red);
        } else
        {
            isChecked = false;
            collectStatus.setImageResource(R.mipmap.icon_footer_sc_gray);
        }
        mShopIntro = String.valueOf(data.get("shopIntro"));
    }

    @Override
    public void getMovieList(List<Map> mFilmLists1)
    {

        this.mFilmLists = mFilmLists1;
        FilmGalleryAdapter adapter = new FilmGalleryAdapter(FilmCinemaDetailActivity.this, this.mFilmLists);
        mGallery.setAdapter(adapter);
        if (mFilmLists.size() > 0)
            mGallery.setSelection(1);

    }

    @Override
    public void getCollectStatus()
    {
        if (isChecked)
        {//已收藏改为取消收藏,并修改标志位为false取消收藏
            collectStatus.setImageResource(R.mipmap.icon_footer_sc_gray);
            ToastUtil.i(FilmCinemaDetailActivity.this, "取消收藏");
            isChecked = false;
        } else
        {//取消收藏改为已收藏,并修改标志位为true已收藏
            collectStatus.setImageResource(R.mipmap.icon_footer_sc_red);
            ToastUtil.i(FilmCinemaDetailActivity.this, "收藏成功");
            isChecked = true;
        }
    }



    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }

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
            LogUtil.e("-------onWaiting-------");
        }

        @Override
        public void onStarted()
        {

            LogUtil.e("----onStarted----------");
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading)
        {
            //this.holder.imgPb.setProgress((int) (current * 100 / total));

            LogUtil.e("onLoading-->total=" + total + "  current=" + current + "  isDownloading=" + isDownloading);
        }

        @Override
        public void onSuccess(Drawable result)
        {

            //this.holder.imgPb.setProgress(100);
            LogUtil.e("-----onSuccess---------");
            BitmapDrawable bd = (BitmapDrawable) result;
            Bitmap bitmap = bd.getBitmap();
            Bitmap bit2 = BlurUtil.fastblur(bitmap, 20);
            final Drawable newBitmapDrawable = new BitmapDrawable(bit2);
            mGallery.setBlurBackgroundDrawable(newBitmapDrawable, R.mipmap.slider_bg);
            cacheDrawable.put(((Map<String, String>) mFilmLists.get(r)).get("imageCode"), newBitmapDrawable);
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