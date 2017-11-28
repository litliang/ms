package com.yzb.card.king.ui.film.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.adapter.VPAdapter;
import com.yzb.card.king.ui.discount.bean.YpdetailBean;
import com.yzb.card.king.ui.discount.fragment.ClockMsgDialogFragment;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.discount.fragment.YpDetailTabListFragment;
import com.yzb.card.king.ui.film.presenter.MovieDetailsPersenter;
import com.yzb.card.king.ui.film.view.MovieDetailView;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.PjActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 影片详情；
 * created by gengqiyun  2016.5.18
 */
public class YpDetailActivity extends BaseActivity implements View.OnClickListener,MovieDetailView

{
    private ImageView filmImage;
    private TextView filmName;
    private TextView filmNameEn;
    private TextView filmEffect;
    private StarBar ratingBar;
    private TextView filmVote;
    private TextView filmType;
    private TextView filmProduction;
    private TextView splitLine;
    private TextView filmDuration;
    private TextView filmTime;
    private TextView filmPlace;
    private ImageView filmCollection;
    private TextView fileVoteCount;
    private ImageView ivClock;
    private ImageView ivMsg;
    private ImageView ivShare;
    private String filmId;// 影片的id；
    private LinearLayout panel_sc, panel_pj;
    private TextView tv_content;
    private String sc_status = "0"; // 收藏状态（1已收藏、0未收藏）
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabTitles;
    private int headerHeight = 0; // 顶部的高度；
    private List<Fragment> fragmentList;
    private static final int TAB_LEN = 7;
    private View panel_top;

    private String customerId = "";

    private MovieDetailsPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        assignViews();
        persenter = new MovieDetailsPersenter(this, null);
        /**
         * 获取详情
         */
        getData();
    }

    private void assignViews()
    {
        filmImage = (ImageView) findViewById(R.id.film_image);
        filmName = (TextView) findViewById(R.id.film_name);
        filmNameEn = (TextView) findViewById(R.id.film_name_en);
        filmEffect = (TextView) findViewById(R.id.film_effect);
        ratingBar = (StarBar) findViewById(R.id.starBar);
        filmVote = (TextView) findViewById(R.id.film_vote);
        filmType = (TextView) findViewById(R.id.film_type);
        filmProduction = (TextView) findViewById(R.id.film_production);
        splitLine = (TextView) findViewById(R.id.split_line);
        filmDuration = (TextView) findViewById(R.id.film_duration);
        filmTime = (TextView) findViewById(R.id.film_time);
        filmPlace = (TextView) findViewById(R.id.film_place);
        filmCollection = (ImageView) findViewById(R.id.film_collection);
        fileVoteCount = (TextView) findViewById(R.id.file_vote_count);
        findViewById(R.id.iv_back).setOnClickListener(this);

        panel_sc = (LinearLayout) findViewById(R.id.panel_sc);
        panel_sc.setOnClickListener(this);

        panel_pj = (LinearLayout) findViewById(R.id.panel_pj);
        panel_pj.setOnClickListener(this);

        panel_top = findViewById(R.id.panel_top);

        panel_top.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {

                if (panel_top == null)
                {
                    return;
                }
                headerHeight = panel_top.getMeasuredHeight();
                if (Build.VERSION.SDK_INT >= 16)
                {
                    panel_top.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else
                {
                    panel_top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        tv_content = (TextView) findViewById(R.id.tv_content);
        ivClock = (ImageView) findViewById(R.id.iv_clock);
        ivClock.setOnClickListener(this);
        ivMsg = (ImageView) findViewById(R.id.iv_msg);
        ivMsg.setOnClickListener(this);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivShare.setOnClickListener(this);

        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        if (bundle != null)
        {
            filmId = bundle.getString("id", "");
        }

        if (UserManager.getInstance().getUserBean() != null)
        {
            customerId = UserManager.getInstance().getUserBean().getAmountAccount();
        }

        tabLayout = (TabLayout) findViewById(R.id.id_stickynavlayout_indicator);
        viewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);

        if (tabTitles == null)
        {
            tabTitles = new String[TAB_LEN];
        }

        Date today = new Date();
        // 0；
        String todayStr = DateUtil.date2String(today, DateUtil.DATE_FORMAT_DATE_DAY2);
        // 1
        Date secondDay = DateUtil.addDay(today, 1);
        String secondDayStr = DateUtil.date2String(secondDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        //2
        Date thirdDay = DateUtil.addDay(today, 2);
        String thirdDayStr = DateUtil.date2String(thirdDay, DateUtil.DATE_FORMAT_DATE_DAY2);

        //3
        Date forthDay = DateUtil.addDay(today, 3);
        String ymd3 = DateUtil.date2String(forthDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        String week3 = DateUtil.getWeek(DateUtil.date2String(forthDay, DateUtil.DATE_FORMAT_DATE));
        //4
        Date fiveDay = DateUtil.addDay(today, 4);
        String ymd4 = DateUtil.date2String(fiveDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        String week4 = DateUtil.getWeek(DateUtil.date2String(fiveDay, DateUtil.DATE_FORMAT_DATE));
        //5
        Date thursDay = DateUtil.addDay(today, 5);
        String ymd5 = DateUtil.date2String(thursDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        String week5 = DateUtil.getWeek(DateUtil.date2String(thursDay, DateUtil.DATE_FORMAT_DATE));

        Date sunDay = DateUtil.addDay(today, 6);
        String ymd6 = DateUtil.date2String(sunDay, DateUtil.DATE_FORMAT_DATE_DAY2);
        String week6 = DateUtil.getWeek(DateUtil.date2String(sunDay, DateUtil.DATE_FORMAT_DATE));

        tabTitles[0] = "今天" + todayStr;
        tabTitles[1] = "明天" + secondDayStr;
        tabTitles[2] = "后天" + thirdDayStr;
        tabTitles[3] = "周" + week3 + " " + ymd3;
        tabTitles[4] = "周" + week4 + " " + ymd4;
        tabTitles[5] = "周" + week5 + " " + ymd5;
        tabTitles[6] = "周" + week6 + " " + ymd6;

        if (fragmentList == null)
        {
            fragmentList = new ArrayList<>();
        }
        fragmentList.clear();

        for (int i = 0; i < TAB_LEN; i++)
        {
            YpDetailTabListFragment fragment = YpDetailTabListFragment.newInstance(i, filmId);
            fragment.setChangeHeightCallBack(new YpDetailTabListFragment.IHeightChange()
            {
                @Override
                public void heightChange(int height)
                {
                    if (viewPager == null)
                    {
                        return;
                    }
                    LogUtil.i("viewPager新的高度==" + height);
                    ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                    params.height = height;
                    viewPager.setLayoutParams(params);
                }
            });
            fragmentList.add(fragment);
        }
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), fragmentList);
        vpAdapter.setTitles(tabTitles);
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * 子FragmentPopupWindow灰色区域单击；
     * 关闭所有的PopupWindow
     */
    public void grayViewClick(View v)
    {
        for (int i = 0; i < fragmentList.size(); i++)
        {
            YpDetailTabListFragment fragment = (YpDetailTabListFragment) fragmentList.get(i);
            fragment.grayViewClick(null);
        }
    }

    private void initViewContent(YpdetailBean ypdetailBean)
    {
        if (ypdetailBean == null)
        {
            return;
        }

        sc_status = ypdetailBean.collectionStatus;

        LogUtil.LCi("   收藏状态 " + sc_status);

        updateScPanelState(sc_status);

        x.image().bind(filmImage, ServiceDispatcher.getImageUrl(ypdetailBean.imageCode), GlobalApp.getInstance().getImageOptionsLogo());
        filmName.setText(ypdetailBean.filmName);
        filmNameEn.setText(ypdetailBean.enfilmName);

        filmEffect.setText(ypdetailBean.filmEffect);

        ratingBar.setStarMarkAndSore(ypdetailBean.vote / 2);
        filmVote.setText(ypdetailBean.vote + "分");

        if (TextUtils.isEmpty(ypdetailBean.filmType))
        {
            filmType.setVisibility(View.GONE);
        } else
        {
            filmType.setVisibility(View.VISIBLE);
            filmType.setText(ypdetailBean.filmType.replace(",", "\u3000"));
        }

        filmProduction.setText(ypdetailBean.filmProduction);
        filmDuration.setText(ypdetailBean.duration + "分钟");
        filmTime.setText(ypdetailBean.showTime);

        filmPlace.setText("在" + ypdetailBean.showPlace + "上映");

        fileVoteCount.setText("评价(" + (TextUtils.isEmpty(ypdetailBean.evaluateCount) ? "0" : ypdetailBean.evaluateCount) + "人)");

        tv_content.setText("\u3000\u3000" + ypdetailBean.summary);

        updateTvHeightByLineNum(tv_content);
    }

    private void updateTvHeightByLineNum(final TextView tv_content)
    {
        if (tv_content.getLineCount() <= 2)
        {
            findViewById(R.id.expand_collapse).setVisibility(View.GONE);
        } else
        {
            findViewById(R.id.expand_collapse).setVisibility(View.VISIBLE);
            tv_content.setMaxLines(2);
            ViewGroup.LayoutParams vl = tv_content.getLayoutParams();
            int textHeight = tv_content.getLayout().getLineTop(2);
            int padding = tv_content.getCompoundPaddingTop() + tv_content.getCompoundPaddingBottom();
            vl.height = textHeight + padding;
            tv_content.setLayoutParams(vl);
            tv_content.setTag("fold");
            tv_content.requestLayout();

            findViewById(R.id.expand_collapse).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    //如果未展开；
                    if ("fold".equals(String.valueOf(tv_content.getTag())))
                    {
                        tv_content.setMaxLines(Integer.MAX_VALUE);
                        ViewGroup.LayoutParams vl = tv_content.getLayoutParams();
                        int textHeight = tv_content.getLayout().getLineTop(tv_content.getLineCount());
                        int padding = tv_content.getCompoundPaddingTop() + tv_content.getCompoundPaddingBottom();
                        vl.height = textHeight + padding;
                        tv_content.setLayoutParams(vl);
                        tv_content.setTag("unfold");
                        tv_content.requestLayout();
                        findViewById(R.id.stickyNavLayout).requestLayout();
                    } else
                    {
                        tv_content.setMaxLines(2);
                        ViewGroup.LayoutParams vl = tv_content.getLayoutParams();
                        int textHeight = tv_content.getLayout().getLineTop(2);
                        int padding = tv_content.getCompoundPaddingTop() + tv_content.getCompoundPaddingBottom();
                        vl.height = textHeight + padding;
                        tv_content.setLayoutParams(vl);
                        tv_content.setTag("fold");
                        tv_content.requestLayout();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clock:
                if (!isLogin())
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
            case R.id.iv_msg:
                break;
            case R.id.panel_sc:
                if (!isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }

                exeSc();
                break;
            case R.id.panel_pj:
                if (!isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }
                Intent pjIntent = new Intent(this, PjActivity.class);
                pjIntent.putExtra("detailId", "10");
                pjIntent.putExtra("type", "2");
                startActivity(pjIntent);
                break;
            case R.id.iv_share:
                ShareDialogFragment sdf = ShareDialogFragment.getInstance("", "");
                sdf.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
        }
    }

    /**
     * 执行定时任务；
     */
    private void exeTimedTask()
    {
    }

    private void exeSc()
    {
        if (TextUtils.isEmpty(filmId))
        {
        }

        if (!"1".equals(sc_status) && !"0".equals(sc_status))
        {
            return;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", AppConstant.collect_product_type);
        param.put("category", AppConstant.collect_film_category); //类型
        param.put("detailsId", filmId);

        param.put("customerId", customerId);

        param.put("status", "1".equals(sc_status) ? "0" : "1");

        persenter.sendCollectRequest(param, CardConstant.card_app_collections);

    }

    /**
     * 更新收藏view状态；
     *
     * @param flag 1:已收藏；0：未收藏；
     */
    private void updateScPanelState(String flag)
    {
        if ("1".equals(flag))
        {
            filmCollection.setBackgroundResource(R.mipmap.icon_footer_sc_red);
//            tvSc.setSelected(true);
        } else
        {
            filmCollection.setBackgroundResource(R.mipmap.icon_detail_xk);
//            tvSc.setSelected(false);
        }
    }

    /**
     * 获取详情；
     */
    private void getData()
    {
        if (TextUtils.isEmpty(filmId))
        {
            return;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("filmId", filmId);
        param.put("customerId", customerId);
        persenter.sendDetailsRequest(param, CardConstant.card_app_queryfilminfo);

    }

    protected boolean isApplyStatusBarTranslucency()
    {
        return true;
    }



    @Override
    public void getCollectStatus()
    {

        // 原来状态
        if ("1".equals(sc_status))
        {
            sc_status = "0";
            updateScPanelState("0");
            ToastUtil.i(YpDetailActivity.this, "取消收藏成功");
        } else
        {
            sc_status = "1";
            updateScPanelState("1");
            ToastUtil.i(YpDetailActivity.this, "收藏成功");
        }

    }



    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        YpdetailBean ypdetailBean = (YpdetailBean) o;
        initViewContent(ypdetailBean);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
