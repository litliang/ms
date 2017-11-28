package com.yzb.card.king.ui.transport.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.BankPop;
import com.yzb.card.king.ui.appwidget.popup.CategoryPop;
import com.yzb.card.king.ui.appwidget.popup.SortPop;
import com.yzb.card.king.ui.appwidget.popup.presenter.CategoryPresenter;
import com.yzb.card.king.ui.appwidget.popup.presenter.impl.CategoryPresenterImpl;
import com.yzb.card.king.ui.appwidget.popup.view.CategoryView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.transport.presenter.CommonPresenter;
import com.yzb.card.king.ui.transport.presenter.impl.CommonPresenterImpl;
import com.yzb.card.king.ui.transport.view.CommonView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/29 10:41
 */
public abstract class BaseTransportActivity  extends BaseActivity implements CommonView, CategoryView,
        SwipeRefreshLayout.OnRefreshListener
{
    protected HeadFootRecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected TextView tv_title_1, tv_title_2;
    protected RelativeLayout rl_title_right;
    protected TextView tv_type; //顶部的类型；比如：单程，往返，；
    protected LinearLayout fl_second_title, ll_option; //第二标题
    protected View ll_ticket;
    protected ImageView img_title;
    protected Activity mActivity;
    public static final String TEXT_ZX = "自选";
    public static final String TEXT_ZH = "组合";
    public static final String TEXT_PJ = "票价";
    public static final String TEXT_HSZJ = "含税总价";
    public static final int TYPE_SINGLE = 1; //单程；
    public static final int TYPE_ROUND = 2;  //往返；
    public static final int TYPE_MULTI = 3;  //多程；
    public static final int TYPE_PJ = 4;     //票价
    public static final int TYPE_HSZJ = 5; //含税总价；

    protected List<CategoryBean> categoryBeans; // 分类列表；
    protected String cityId; //城市id；
    protected GlobalApp app;
    protected String lat; //经度；
    protected String lng; // 纬度；
    protected String typeId; //分类id；
    protected String typeParentId = AppConstant.ticket_id; //父分类id；
    protected String sort = ""; //排序(默认)；
    protected String filter = "1"; //筛选(默认)；
    protected TextView tvBank, tvFl, tvPx, tvSx, tvBankArrow, tvFlArrow, tvPxArrow, tvSxArrow;
    protected String bankId = "";//银行id；
    public int pjType = TYPE_HSZJ;
    protected Map<String, Object> commonparam = new HashMap<>();
    protected String serviceName = "";
    protected String typeChildId;  //等效于typeId；

    protected CommonPresenter commonPresenter;
    private CategoryPresenter categoryPresenter;

    protected int pageStart = 0; //分页的下标；
    protected int pageSize = 15; //每页的大小；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        commonPresenter = new CommonPresenterImpl(this);
        categoryPresenter = new CategoryPresenterImpl(this);
    }

    public void initView(Activity activity)
    {
        mActivity = activity;
        setContentView(R.layout.activity_base_transport);
        lat = selectedCity.latitude + "";
        lng = selectedCity.longitude + "";
        cityId = selectedCity.cityId;

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (HeadFootRecyclerView) findViewById(R.id.listview);
        ViewUtil.recyclerViewSetting1(this, recyclerView);
        //加载更多
        recyclerView.setOnLoadMoreListener(loadMoreListener);

        tv_title_1 = (TextView) findViewById(R.id.tv_title_1);
        tv_title_2 = (TextView) findViewById(R.id.tv_title_2);
        img_title = (ImageView) findViewById(R.id.img_title);
        rl_title_right = (RelativeLayout) findViewById(R.id.rl_title_right);
        fl_second_title = (LinearLayout) findViewById(R.id.fl_second_title);
        ll_option = (LinearLayout) findViewById(R.id.ll_option);

        tv_type = (TextView) findViewById(R.id.tv_type);

        ll_ticket = findViewById(R.id.ll_ticket);

        tvBank = (TextView) findViewById(R.id.tv_bank);
        tvFl = (TextView) findViewById(R.id.tv_fl);
        tvPx = (TextView) findViewById(R.id.tv_px);
        tvSx = (TextView) findViewById(R.id.tv_sx);

        tvBankArrow = (TextView) findViewById(R.id.tv_bank_arrow);
        tvFlArrow = (TextView) findViewById(R.id.tv_ticket_arrow);
        tvPxArrow = (TextView) findViewById(R.id.tv_sort_arrow);
        tvSxArrow = (TextView) findViewById(R.id.tv_filter_arrow);
    }


    private Handler mainHandler = new Handler();


    private HeadFootRecyclerView.OnLoadMoreListener loadMoreListener = new HeadFootRecyclerView.OnLoadMoreListener()
    {
        @Override
        public void loadMoreListener()
        {
            commonparam.put("pageStart", pageStart);
            commonPresenter.loadData(false, commonparam);
        }
    };

    public void setSecondTitle(View v)
    {
        fl_second_title.addView(v);
    }

    public void setOptionVisibility(int visibility)
    {
        ll_option.setVisibility(visibility);
    }

    /**
     * 设置标题栏下的单程块的隐藏和显示；
     *
     * @param visibility
     */
    public void setTripTypeVisibility(int visibility)
    {
        tv_type.setVisibility(visibility);
    }

    public void setTitle(String text1, int imgId, String text2)
    {
        tv_title_1.setText(text1);
        tv_title_2.setText(text2);
        if (imgId != 0)
            img_title.setImageResource(imgId);
        else
            img_title.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh()
    {
        pageStart = 0;
        refreshData();
    }

    public void setTitleRight(View v)
    {
        rl_title_right.addView(v);
    }

    public void back(View v)
    {
        finish();
    }

    /**
     * 我的银行；
     */
    public void myBank(View v)
    {
        grayViewClick(null);
        // 有数据直接显示；
        if (BankPop.getAllBanks() != null && BankPop.getAllBanks().size() > 0)
        {
            startInverse(tvBankArrow, R.anim.rotate_animation_start);
            BankPop.getInstance(this).setIBankCallBack(iBankPop).
                    showYhkPopWindow(this, ll_option);
        } else
        {
            BankPop.getInstance(this).setIBankCallBack(iBankPop).getYhkData();
        }
    }

    //银行卡回调；
    BankPop.IBankPop iBankPop = new BankPop.IBankPop()
    {
        @Override
        public void popDismiss()
        {
            startInverse(tvBankArrow, R.anim.rotate_animation_end);
        }

        @Override
        public void bankItemClick(BankBean bankBean)
        {
            grayViewClick(null);
            LogUtil.i("点击的bank==" + JSON.toJSONString(bankBean));
            if (bankBean != null)
            {
                bankId = bankBean.id + "";
                tvBank.setText(bankBean.bankName);
                refresh();
            }
        }

        @Override
        public void onAllBankLoadFinished()
        {
            if (BankPop.getAllBanks() != null)
            {
                startInverse(tvBankArrow, R.anim.rotate_animation_start);
                BankPop.getInstance(BaseTransportActivity.this).showYhkPopWindow(BaseTransportActivity.this, ll_option);
            } else
            {
                ToastUtil.i(BaseTransportActivity.this, "获取失败，请重试");
            }
        }
    };

    public void ticket(View v)
    {
        grayViewClick(null);
        getFl();
    }

    public void sort(View v)
    {
        grayViewClick(null);

        startInverse(tvPxArrow, R.anim.rotate_animation_start);
        SortPop.getInstance(this).setSortPopCallBack(iSortPop).
                showSortPopWindow(BaseTransportActivity.this, ll_option);
    }

    public void filter(View v)
    {
        grayViewClick(null);


    }


    // 智能排序接口回调；
    private SortPop.ISortPop iSortPop = new SortPop.ISortPop()
    {

        @Override
        public void popDismiss()
        {
            startInverse(tvPxArrow, R.anim.rotate_animation_end);
        }

        @Override
        public void onItemClick(String[] args)
        {
            grayViewClick(null);
            sort = args[0];
            String sortText = args[1];

            commonparam.put("sort", sort);
            tvPx.setText(sortText);
            refreshData();
        }
    };

    public void startInverse(View view, int animationResId)
    {
        view.startAnimation(AnimationUtils.loadAnimation(this, animationResId));
    }

    /**
     * popwindow隐藏；
     */
    public void grayViewClick(View v)
    {
        BankPop.getInstance(this).dismissPopWindow();
        CategoryPop.getInstance(this).dismissPopWindow();
        SortPop.getInstance(this).dismissPopWindow();
    }

    public void dismissPopWindow(PopupWindow window)
    {
        if (window != null && window.isShowing())
        {
            window.dismiss();
        }
    }

    @Override
    public void onGetCategorySucess(Object data)
    {
        if (data != null)
        {
            categoryBeans = (List<CategoryBean>) data;
            LogUtil.i("categoryBeans=" + JSON.toJSONString(categoryBeans));
            showFlPopWindow(ll_ticket);
        }
    }

    @Override
    public void onGetCategoryFail(String failMsg)
    {
        toastCustom(getString(R.string.transport_get_category_error));
    }


    /**
     * 获取分类；
     */
    private void getFl()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", cityId);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("bankId", bankId);
        params.put("typeId", typeId);
        params.put("typeParentId", typeParentId);

        LogUtil.i("分类条件提交的参数；==" + params);
        categoryPresenter.loadData(params);
    }

    /**
     * 分类popupwindow；
     */
    private void showFlPopWindow(View anchor)
    {
        grayViewClick(null);
        if (anchor == null)
        {
            return;
        }
        // 有数据直接显示；
        if (categoryBeans != null && categoryBeans.size() > 0)
        {
            startInverse(tvFlArrow, R.anim.rotate_animation_start);

            CategoryPop.getInstance(this).
                    setTypeId(typeChildId).
                    setTypeParentId(typeParentId).
                    setCategoryPopCallBack(iCategoryPop).
                    setCategoryBeans(categoryBeans).
                    showCategoryPopWindow(this, anchor);
        }
    }


    public void addMsg(Context context, Map map, String dotText)
    {
        View v = View.inflate(context, R.layout.ticket_msg_view, null);
        TextView dot = (TextView) v.findViewById(R.id.tv_dot);
        TextView date = (TextView) v.findViewById(R.id.tv_date);
        TextView startTime = (TextView) v.findViewById(R.id.tv_startTime);
        TextView endTime = (TextView) v.findViewById(R.id.tv_endTime);
        TextView startAirport = (TextView) v.findViewById(R.id.tv_startAirport);
        TextView endAirport = (TextView) v.findViewById(R.id.tv_endAirport);
        TextView company = (TextView) v.findViewById(R.id.tv_company);

        dot.setText(dotText);
        date.setText(getDate(map.get("startTime").toString()));
        startTime.setText(getTime(map.get("startTime").toString()));
        endTime.setText(getTime(map.get("endTime").toString()));
        startAirport.setText(map.get("startCity").toString());
        endAirport.setText(map.get("endCity").toString());
        company.setText(map.get("aviationType").toString());
        fl_second_title.addView(v);
    }

    public String getDate(String str)
    {
        if (str != null && str.length() > 0)
            return str.substring(str.indexOf("-") + 1, str.indexOf(" "));
        else
            return "";
    }

    public String getTime(String str)
    {
        if (str != null && str.length() > 0)
            return str.substring(str.indexOf(" "), str.length());
        else
            return "";
    }

    /**
     * 数据初始化调用，相当于下拉刷新； 此时
     * pageStart=0;
     */
    protected void refreshData()
    {
        pageStart = 0;
        commonparam.put("pageStart", pageStart);
        if (this.commonparam != null)
        {
            this.commonparam.put("serviceName", this.serviceName);
        }
        mainHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefreshLayout.setRefreshing(true);
                commonPresenter.loadData(true, commonparam);
            }
        }, 100);
    }

    private CategoryPop.ICategoryPop iCategoryPop = new CategoryPop.ICategoryPop()
    {
        @Override
        public void popDismiss()
        {
            startInverse(tvFlArrow, R.anim.rotate_animation_end);
        }

        @Override
        public void onItemClick(String[] args)
        {
            grayViewClick(null);
            if (args == null)
            {
                return;
            }
            String parentIdEmp = args[0];
            String typeIdEmp = args[1];
            String typeGrandParentIdEmp = args[4];

            LogUtil.i("arg0=" + parentIdEmp + ",arg1=" + typeIdEmp + ",typeGrandParentId=" + typeGrandParentIdEmp);

            //美食或旅游的三级；
            if (!TextUtils.isEmpty(typeGrandParentIdEmp))
            {
                typeParentId = parentIdEmp;
                typeId = null;
            } else
            {
                typeParentId = parentIdEmp;
                typeId = typeIdEmp;
            }

            //机票；
            if (AppConstant.ticket_id.equals(typeParentId))
            {
                typeParentId = parentIdEmp;
                typeId = typeIdEmp;
                typeChildId = typeId;
                commonparam.put("typeId", typeId);
                refreshData();
                return;
            }

            //美食或奢侈品；
            if (AppConstant.meishi_id.equals(typeGrandParentIdEmp) || AppConstant.shechipin_id.equals(typeParentId))
            {
//                Bundle bundle = new Bundle();
//                bundle.putString("typeParentId", parentIdEmp); // 跳转美食二级下的所有门店列表；
//                bundle.putString("typeName", args[3]);
//                readyGoWithBundle(BaseTransportActivity.this, MsMoreActivity.class, bundle);
//                finish();
                return;
            }

            //影片；或影院
            if (AppConstant.film_id.equals(typeParentId))
            {
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("typeParentId", typeParentId);
//                bundle2.putString("typeId", typeId);
//                bundle2.putString("typeName", args[3]);
//                readyGoWithBundle(BaseTransportActivity.this, FilmMoreActivity.class, bundle2);
//                finish();
                return;
            }
            // 旅游；
            if (AppConstant.travel_id.equals(typeGrandParentIdEmp))
            {
                Bundle bundle = new Bundle();
                bundle.putString("typeParentId", typeParentId);
                bundle.putString("typeName", args[3]);
                finish();
                return;
            }

            //酒店；
            if (AppConstant.hotel_id.equals(typeParentId))
            {
//                Bundle bundle = new Bundle();
//                bundle.putString("typeParentId", typeParentId);
//                bundle.putString("typeId", typeId);
//                readyGoWithBundle(BaseTransportActivity.this, HotelListActivity.class, bundle);
//                finish();
                return;
            }
        }
    };

    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    /**
     * 数据加载成功；
     *
     * @param event_tag 下拉或上拉；
     * @param data
     */
    @Override
    public void onLoadSucess(boolean event_tag, Object data)
    {
        swipeRefreshLayout.setRefreshing(false);
        if (data != null)
        {
            pageStart++;

            String dataStr = String.valueOf(data);
            if (!isEmpty(dataStr))
            {
                onSucess(event_tag, dataStr);
            }
        } else
        {
            toastCustom(getString(R.string.app_no_data));
        }
    }

    /**
     * 加载失败；
     */
    @Override
    public void onLoadFail(String failMsg)
    {
        swipeRefreshLayout.setRefreshing(false);
        toastCustom(getString(R.string.app_no_data));
    }

    /**
     * 数据加载结果回调方法；
     * 子类需要重写；
     *
     * @param data 数据部分
     */
    protected void onSucess(boolean event_tag, String data)
    {

    }

    protected abstract void refresh();

    @Override
    protected void onDestroy()
    {
        BankPop.reset();
        CategoryPop.reset();
        SortPop.reset();
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        BankPop.reset();
        CategoryPop.reset();
        SortPop.reset();
        super.onBackPressed();
    }
}
