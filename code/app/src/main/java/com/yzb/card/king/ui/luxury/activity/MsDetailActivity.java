package com.yzb.card.king.ui.luxury.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserCollectBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.SmoothScrollView;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.activity.ShopHomeActivity;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.fragment.ClockMsgDialogFragment;
import com.yzb.card.king.ui.discount.fragment.LqhbDialogFragment;
import com.yzb.card.king.ui.discount.fragment.MsDetail_ShxxFragment;
import com.yzb.card.king.ui.discount.fragment.MsDetail_SjxxFragment;
import com.yzb.card.king.ui.discount.fragment.MsDetail_YhxqFragment;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.luxury.model.IMsDetail;
import com.yzb.card.king.ui.luxury.presenter.MsDetailPresenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.FullScreenImgActivity;
import com.yzb.card.king.ui.other.activity.PjActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 美食详情；
 * created by gqy on 2016.4.16
 */
public class MsDetailActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, BaseViewLayerInterface
{
    private float ivWhRate = 288 / 720.0f;
    private List<Fragment> fragments;
    private RelativeLayout rl0;

    private ImageView ivHeader;
    private TextView tvPicNumber;
    private TextView tvSjmc;

    private LinearLayout panelShare;

    private final static String STATUS_COLLECT = "1";   //收藏
    private final static String STATUS_NOTCOLLECT = "0";  //未收藏

    private TextView tvCity;
    private TextView tvXl;
    private TextView tvFs;
    private TextView tvPt;
    private TextView tv_sj;
    private LinearLayout panelYhq;
    private LinearLayout panelHb; //红包
    private LinearLayout panelTppmd;
    private LinearLayout panelYhxq;
    private TextView tvYhxq;
    private TextView tvTppmd;
    private LinearLayout panelShxx;
    private TextView tvShxx;
    private LinearLayout panelPjlist;
    private TextView tvPjnumber;
    private LinearLayout tv_sjxx;
    private TextView tvSjxx;
    private LinearLayout container;

    private View ll_back;
    private ImageView ivMsg;
    private ImageView ivClock;
    private ImageView ivMap;

    private LinearLayout panelDp;
    private ImageView ivDp;
    private TextView tvDp;
    private LinearLayout panelSc;
    private ImageView ivSc;
    private TextView tvSc;
    private LinearLayout panelThfk;
    private List<View> titleViews = new ArrayList<View>();
    private List<View> underlineViews = new ArrayList<View>();
    private String id;  //商户id
    private SwipeRefreshLayout swipeRefresh;
    private StarBar ratingBar;
    private List<BankBean> myBanks;
    private ImageView arrow_yhq, arrow_hb;
    public StoreBean storeBean;
    public UserCollectBean userCollectBean;
    private String sc_status = "-1"; // 状态（1已收藏、0未收藏）
    private ImageView imageView;

    private View panel_top;
    private int headerHeight = 0; // 顶部的高度；
    private SmoothScrollView scrollView;
    private String customerId = "";
    private View panel_jf_line; // 商家，积分行；
    private String typeParentId; // 大分类的id；
    private String typeGrandParentId;
    private String typeGrandName;
    private MsDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_detail);
        recvIntentData();
        assignViews();
        getData();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        recvIntentData();
        initFragmentList();
        update4ItemState(0);
        getData();
    }

    /**
     * 初始化顶部view内容；
     *
     * @param storeBean
     */
    public void initTopViewContent(StoreBean storeBean)
    {
        if (storeBean == null) return;
        sc_status = storeBean.status;

        this.typeParentId = storeBean.parentId;
        this.typeGrandParentId = storeBean.grandParentId;
        this.typeGrandName = storeBean.grandParentName;

        updateScPanelState(sc_status);

        //获取图片张数和第一张图片；
        int picCount = 0;
        String firstPic = "";
        if (!TextUtils.isEmpty(storeBean.picCodes))
        {
            String[] pics = storeBean.picCodes.split(",");
            for (int i = 0; i < pics.length; i++)
            {
                pics[i] = ServiceDispatcher.getImageUrl(pics[i]);
            }
            picCount = pics.length;
            firstPic = pics[0];
        }

        x.image().bind(ivHeader, firstPic);

        tvPicNumber.setText(picCount + getString(R.string.train_zhang));

        tvSjmc.setText(storeBean.storeName);
        tvCity.setText(storeBean.cityName);
        tvXl.setText(getString(R.string.food_yx) + storeBean.orderCount + getString(R.string.food_bi));

        ratingBar.setStarMarkAndSore(storeBean.vote / 2);
        tvFs.setText(storeBean.vote + getString(R.string.fen));
        TextView tvPjNum = (TextView) findViewById(R.id.tv_pjnumber);
        tvPjNum.setText(getString(R.string.pj) + storeBean.voteCount + ")");

        // 优惠券数量；
        int yhqCount = storeBean.countCoupon;
        if (yhqCount < 1)
        {
            panelYhq.setVisibility(View.GONE);
            findViewById(R.id.divider_yhq).setVisibility(View.GONE);
        } else
        {
            findViewById(R.id.divider_yhq).setVisibility(View.VISIBLE);
            panelYhq.setVisibility(View.VISIBLE);
        }

        // 红包数量；
        int hbCount = storeBean.countBouns;
        if (hbCount < 1)
        {
            findViewById(R.id.divider_hb).setVisibility(View.GONE);
            panelHb.setVisibility(View.GONE);
        } else
        {
            findViewById(R.id.divider_hb).setVisibility(View.VISIBLE);
            panelHb.setVisibility(View.VISIBLE);
        }

        int shopCount = storeBean.shopCount;
        if (shopCount <= 1)
        {
            panelTppmd.setVisibility(View.GONE);
        } else
        {
            panelTppmd.setVisibility(View.VISIBLE);
            tvTppmd.setText(getString(R.string.food_tppmd) + shopCount + ")");
        }

        //panel_jf_line显示隐藏？？？
        tvPt.setText(TextUtils.isEmpty(storeBean.platformPoint) ? "" :
                getString(R.string.ms_platform_jf,
                        String.valueOf(Double.parseDouble(storeBean.platformPoint) * 100))); //平台积分赠送比率

        tv_sj.setText(TextUtils.isEmpty(storeBean.shopPoint) ? "" :
                getString(R.string.ms_store_jf,
                        String.valueOf(Double.parseDouble(storeBean.shopPoint) * 100))); //商家积分赠送比率

        update4ItemState(0);
    }

    /**
     * 根据下标更新容器部分的内容；
     */
    private void update4ItemState(int index)
    {

        for (int i = 0; i < titleViews.size(); i++)
        {
            if (i == index)
            {
                titleViews.get(i).setSelected(true);
                underlineViews.get(i).setVisibility(View.VISIBLE);
                replaceFragment(i);
            } else
            {
                titleViews.get(i).setSelected(false);
                underlineViews.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Fragment替换，
     */
    private void replaceFragment(int index)
    {
        if (index < 0 || index >= fragments.size())
        {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragments.get(index);

        if (fragment instanceof MsDetail_SjxxFragment)
        { // 商家信息；
            ((MsDetail_SjxxFragment) fragment).setSjId(storeBean == null ? "" : storeBean.id);
        }
        transaction.replace(R.id.container, fragment, "" + index).commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (UserManager.getInstance().getUserBean() != null)
        {
            customerId = UserManager.getInstance().getUserBean().getAmountAccount();
        }
    }

    public void recvIntentData()
    {
        id = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE).getString("id", "");
        LogUtil.i("接收到的商户id==" + id);
    }

    private void assignViews()
    {
        assignTopViews();
        assignSjPartViews();
        assgin4ItemViews();
        assignBottomViews();
        update4ItemState(0);


        panel_top = findViewById(R.id.panel_top);
        panel_top.getBackground().mutate().setAlpha(0);

        panel_top.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
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

        scrollView = (SmoothScrollView) findViewById(R.id.scrollView);
        //滚动监听；
        scrollView.setOnScrollistener(new SmoothScrollView.OnScrollistener()
        {
            @Override
            public void onScroll(int scrollY)
            {
                //根据scrollview滑动更改标题栏透明度
                dynamicChangeAphla(scrollY);
            }
        });

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        presenter = new MsDetailPresenter(this);
        if (UserManager.getInstance().isLogin())
        {
            isCollect();
        }

    }

    /**
     * 根据内容窗体的移动改变标题栏背景透明度
     */
    private void dynamicChangeAphla(int scrollY)
    {
        if (scrollY > 30)
        {
            //参考：http://my.oschina.net/gef/blog/609289?fromerr=NHGBjnho
            panel_top.getBackground().mutate().setAlpha(255);
        } else
        {
            // 全透明；
            panel_top.getBackground().mutate().setAlpha(0);
        }
    }

    @Override
    public void onRefresh()
    {
        getData();
    }

    private void assignTopViews()
    {
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);

        ivMsg = (ImageView) findViewById(R.id.iv_msg);
        ivMsg.setOnClickListener(this);

        ivClock = (ImageView) findViewById(R.id.iv_clock);
        ivClock.setOnClickListener(this);

        ivMap = (ImageView) findViewById(R.id.iv_map);
        ivMap.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.iv_header);
        imageView.setOnClickListener(this);

        //要设置高度(即为图片的高度)；
        rl0 = (RelativeLayout) findViewById(R.id.rl_0);
        rl0.getLayoutParams().height = (int) (CommonUtil.getScreenWidth(this) * ivWhRate + 0.5);

        //显示图片；
        ivHeader = (ImageView) findViewById(R.id.iv_header);
        ivHeader.setScaleType(ImageView.ScaleType.FIT_CENTER);

        //图片的数量；
        tvPicNumber = (TextView) findViewById(R.id.tv_pic_number);
    }


    /**
     * 注册商家相关views；
     */
    private void assignSjPartViews()
    {
        tvSjmc = (TextView) findViewById(R.id.tv_sjmc);
        panelShare = (LinearLayout) findViewById(R.id.panel_share);
        panelShare.setOnClickListener(this);

        tvCity = (TextView) findViewById(R.id.tv_city);

        panel_jf_line = findViewById(R.id.panel_jf_line);

        tvCity = (TextView) findViewById(R.id.tv_city);
        tvXl = (TextView) findViewById(R.id.tv_xl);
        ratingBar = (StarBar) findViewById(R.id.starBar);

        arrow_yhq = (ImageView) findViewById(R.id.arrow_yhq);
        arrow_hb = (ImageView) findViewById(R.id.arrow_hb);

        tvFs = (TextView) findViewById(R.id.tv_fs);

        tvTppmd = (TextView) findViewById(R.id.tv_tppmd);

        tvPt = (TextView) findViewById(R.id.tv_pt);
        tvPt.setText("");
        tv_sj = (TextView) findViewById(R.id.tv_sj);
        tv_sj.setText("");

        panelYhq = (LinearLayout) findViewById(R.id.panel_yhq);
        panelYhq.setOnClickListener(this);

        panelHb = (LinearLayout) findViewById(R.id.panel_hb);
        panelHb.setOnClickListener(this);
        panelTppmd = (LinearLayout) findViewById(R.id.panel_tppmd);
        panelTppmd.setOnClickListener(this);
    }


    /**
     * 获取商家信息；
     */
    private void getData()
    {
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                starRequest();
            }
        }, 100);


    }

    private void starRequest()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("storeId", id);
        presenter.getData(param);
    }


    /**
     * 执行旋转动画；
     *
     * @param view
     * @param animationResId
     */
    public void startInverse(View view, int animationResId)
    {
        view.startAnimation(AnimationUtils.loadAnimation(this, animationResId));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_back:  //返回
                finish();
                break;
            case R.id.iv_map:// 地图模式；
                if (storeBean == null)
                {
                    ToastUtil.i(this, getString(R.string.xqjzsb));
                    return;
                }
                goMap();
                break;
            case R.id.iv_clock:  //设置闹钟提醒
                showClockDialog();
                break;
            case R.id.iv_msg:
                break;
            case R.id.panel_share: // 分享；
                ShareDialogFragment sdf2 = ShareDialogFragment.getInstance("", "");
                sdf2.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
            case R.id.panel_yhq:  //优惠券
//                YhqDialogFragment dialogFragment = YhqDialogFragment.getInstance("", "");
//                if (TextUtils.isEmpty(storeBean.id))
//                {
//                    return;
//                }
//                dialogFragment.setShopId(storeBean.id);
//                dialogFragment.show(getSupportFragmentManager(), "YhqDialogFragment");
                break;

            case R.id.panel_hb:   //红包
                LqhbDialogFragment hbFragment = LqhbDialogFragment.getInstance("", "");
                if (TextUtils.isEmpty(storeBean.id))
                {
                    return;
                }
                hbFragment.setShopId(storeBean.id);
                hbFragment.show(getSupportFragmentManager(), "LqhbDialogFragment");
                break;
            case R.id.panel_tppmd:  //同品牌店铺
                if (storeBean != null && !TextUtils.isEmpty(storeBean.shopId))
                {
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("shopId", storeBean.shopId);
//                    readyGoWithBundle(this, MsMoreActivity.class, bundle1);
                } else
                {
                    ToastUtil.i(this, getString(R.string.food_ymjzsb));
                }
                break;
            case R.id.panel_yhxq:  //优惠详情
                update4ItemState(0);
                break;
            case R.id.panel_shxx:  //企业宣传
                update4ItemState(1);
                break;
            case R.id.panel_pjlist: //评价；
                if (storeBean != null)
                {
                    Intent pjIntent = new Intent(this, PjActivity.class);
                    pjIntent.putExtra("detailId", storeBean.id);
                    pjIntent.putExtra("type", "");
                    startActivity(pjIntent);
                }
                break;
            case R.id.panel_sjxx:   //商家信息
                update4ItemState(2);
                break;
            case R.id.panel_dp:  //店铺
                if (!UserManager.getInstance().isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }
                Intent intentDp = new Intent(this, ShopHomeActivity.class);
                if (storeBean != null)
                {
                    intentDp.putExtra("storeId", storeBean.id);
                    intentDp.putExtra("storeName", storeBean.storeName);
                }
                startActivity(intentDp);
                break;
            case R.id.panel_sc: //收藏
                if (storeBean == null)
                {
                    ToastUtil.i(this, getString(R.string.jzsb));
                    return;
                }
                exeSc();
                break;
            case R.id.panel_thfk:  //特惠付款
                if (!UserManager.getInstance().isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }
                if (storeBean == null)
                {
                    ToastUtil.i(this, getString(R.string.food_sjjzsb));
                    return;
                }
                Intent intent = new Intent(this, DiscountPayActivity.class);
                // 美食，奢侈品需要手动输入金额；
                intent.putExtra("isInput", true);
//                intent.putExtra("amount", storeBean.storeName);
                intent.putExtra("storeName", storeBean.storeName);
                startActivity(intent);
                break;
            case R.id.iv_header: //查看图集
                if (storeBean == null)
                {
                    ToastUtil.i(this, getString(R.string.xqjzsb_wf));
                    return;
                }
                if (!TextUtils.isEmpty(storeBean.picCodes))
                {
                    final String[] pics = storeBean.picCodes.split(",");
                    Intent intent1 = new Intent(MsDetailActivity.this, FullScreenImgActivity.class);
                    intent1.putExtra("currentPage", 0);
                    intent1.putExtra("imgUrls", pics);
                    startActivity(intent1);
                }
                break;
        }

    }

    /**
     * 进入地图模式
     */
    private void goMap()
    {
//        Intent intentMap = new Intent(this, MapModelActivity.class);
//        intentMap.putExtra("typeParentId", typeParentId);
//        intentMap.putExtra("typeGrandParentId", typeGrandParentId);
//        intentMap.putExtra("typeGrandName", typeGrandName);
//
//        ArrayList<StoreBean> storeBeans = new ArrayList<>();
//        storeBeans.add(storeBean);
//        intentMap.putExtra("listData", storeBeans);
//        startActivity(intentMap);
    }

    /**
     * 显示闹钟提醒dialog
     */
    private void showClockDialog()
    {
        if (!UserManager.getInstance().isLogin())
        {
            readyGo(this, LoginActivity.class);
            return;
        }
        final ClockMsgDialogFragment sdf = ClockMsgDialogFragment.getInstance("", "");
        sdf.setCallBack(new IDialogCallBack()
        {
            @Override
            public void dialogCallBack(Object... obj)
            {
                sdf.dismiss();
                // 确认；
                if (obj != null && "1".equals(String.valueOf(obj[0])))
                {
                    exeTimedTask();
                }
            }
        });
        sdf.show(getSupportFragmentManager(), "ClockMsgDialogFragment");
    }

    /**
     * 执行定时任务；
     */
    private void exeTimedTask()
    {
    }


    /**
     * 是否收藏
     */
    private void isCollect()
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("category", AppConstant.collect_store_category); //类型
        param.put("detailsId", id);
        param.put("type", AppConstant.collect_shop_type); //类别 商家；
        param.put("status", "");
        presenter.isCollect(param);
    }

    /**
     * 收藏；
     */
    private void exeSc()
    {
        //未登录，进入登录页面，登陆成功返回该页面查询是否收藏该详情
        if (!UserManager.getInstance().isLogin())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.dialog_title));
            builder.setMessage(getResources().getString(R.string.dialog_not_login));
            builder.setPositiveButton(getResources().getString(R.string.go_login), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivityForResult(new Intent(MsDetailActivity.this, LoginActivity.class), 0);
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.not_login), null);
            builder.show();
            return;
        }
        if (TextUtils.isEmpty(id))
        {
            return;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("category", AppConstant.collect_store_category); //类型
        param.put("detailsId", id);
        param.put("type", AppConstant.collect_shop_type); //类别 商家；
        param.put("status", STATUS_COLLECT.equals(sc_status) ? STATUS_NOTCOLLECT : STATUS_COLLECT);
        presenter.collect(param);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == AppConstant.LOGIN_ACCESS && requestCode == 0)
        {
            isCollect();
        }
    }

    /**
     * 更新商家信息中的粉丝数量；
     *
     * @param flag 0：-1， 1：+1；
     */
    private void updateSjxxFsNumber(int flag)
    {

        if (fragments != null && fragments.size() == 3 && fragments.get(2) != null
                && fragments.get(2) instanceof MsDetail_SjxxFragment)
        {
            MsDetail_SjxxFragment sjxxFragment = (MsDetail_SjxxFragment) fragments.get(2);
            sjxxFragment.updalteFsNumber(flag);
        }
    }

    /**
     * 更新收藏view状态；
     *
     * @param flag 1:已收藏；0：未收藏；
     */
    private void updateScPanelState(String flag)
    {
        if (STATUS_COLLECT.equals(flag))
        {
            ivSc.setBackgroundResource(R.mipmap.icon_footer_sc_red);
            tvSc.setSelected(true);
        } else
        {
            ivSc.setBackgroundResource(R.mipmap.icon_footer_sc_gray);
            tvSc.setSelected(false);
        }
    }


    private void assgin4ItemViews()
    {
        panelYhxq = (LinearLayout) findViewById(R.id.panel_yhxq);
        panelYhxq.setOnClickListener(this);
        titleViews.add(panelYhxq.findViewById(R.id.tv_yhxq));
        underlineViews.add(panelYhxq.findViewById(R.id.underline0));

        panelShxx = (LinearLayout) findViewById(R.id.panel_shxx);
        panelShxx.setOnClickListener(this);
        titleViews.add(panelShxx.findViewById(R.id.tv_shxx));
        underlineViews.add(panelShxx.findViewById(R.id.underline1));

        panelPjlist = (LinearLayout) findViewById(R.id.panel_pjlist);
        panelPjlist.setOnClickListener(this);

//        titleViews.add(panelPjlist.findViewById(R.id.tv_pjnumber));
        panelPjlist.findViewById(R.id.underline2).setVisibility(View.INVISIBLE);
//        underlineViews.add(panelPjlist.findViewById(R.id.underline2));

        tv_sjxx = (LinearLayout) findViewById(R.id.panel_sjxx);
        tv_sjxx.setOnClickListener(this);
        titleViews.add(tv_sjxx.findViewById(R.id.tv_sjxx));
        underlineViews.add(tv_sjxx.findViewById(R.id.underline3));

        container = (LinearLayout) findViewById(R.id.container);
        initFragmentList();
    }

    public void initFragmentList()
    {
        if (fragments == null)
        {
            fragments = new ArrayList<>();
        } else
        {
            fragments.clear();
        }
        fragments.add(MsDetail_YhxqFragment.newInstance(id, ""));
        fragments.add(MsDetail_ShxxFragment.newInstance("", ""));
//        fragments.add(MsDetail_PjFragment.newInstance("", ""));
        MsDetail_SjxxFragment sjxxFragment = MsDetail_SjxxFragment.newInstance("", "");
        fragments.add(sjxxFragment);
    }

    /**
     * 注册底部相关views；
     */
    private void assignBottomViews()
    {
        panelDp = (LinearLayout) findViewById(R.id.panel_dp);
        panelDp.setOnClickListener(this);

        ivDp = (ImageView) findViewById(R.id.iv_dp);
        tvDp = (TextView) findViewById(R.id.tv_dp);
        tvDp.setSelected(true);

        panelSc = (LinearLayout) findViewById(R.id.panel_sc);
        panelSc.setOnClickListener(this);

        ivSc = (ImageView) findViewById(R.id.iv_sc);
        tvSc = (TextView) findViewById(R.id.tv_sc);

        panelThfk = (LinearLayout) findViewById(R.id.panel_thfk);
        panelThfk.setOnClickListener(this);
    }

    /**
     * 不使用状态栏一体化；
     *
     * @return
     */
    @Override
    protected boolean isApplyStatusBarTranslucency()
    {
        return false;
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == IMsDetail.GETDATA_CODE)
        {
            swipeRefresh.setRefreshing(false);
            if (o instanceof StoreBean)
            {
                storeBean = (StoreBean) o;
                initTopViewContent(storeBean);
                ((MsDetail_YhxqFragment) fragments.get(0)).refreshData();
            }

        } else if (type == IMsDetail.COLLECT_CODE)
        {
            // 原来状态；
            if (STATUS_COLLECT.equals(sc_status))
            {
                sc_status = STATUS_NOTCOLLECT;
                updateScPanelState(STATUS_NOTCOLLECT);
                ToastUtil.i(MsDetailActivity.this, getString(R.string.qxcoll));
                updateSjxxFsNumber(0);
            } else
            {
                sc_status = STATUS_COLLECT;
                updateScPanelState(STATUS_COLLECT);
                ToastUtil.i(MsDetailActivity.this, getString(R.string.collsucc));
                updateSjxxFsNumber(1);
            }
        } else if (type == IMsDetail.ISCOLLECT_CODE)
        {
            if (o instanceof Map)
            {
                Map map = (Map) o;
                if (map.get("status").equals(STATUS_COLLECT))
                {
                    updateScPanelState(STATUS_COLLECT);
                } else
                {
                    updateScPanelState(STATUS_NOTCOLLECT);
                }
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == IMsDetail.GETDATA_CODE)
        {
            swipeRefresh.setRefreshing(false);
        } else if (type == IMsDetail.COLLECT_CODE)
        {

        } else if (type == IMsDetail.ISCOLLECT_CODE)
        {

        }
    }
}
