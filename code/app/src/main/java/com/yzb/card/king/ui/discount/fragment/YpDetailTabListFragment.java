package com.yzb.card.king.ui.discount.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.discount.adapter.FilmMainAdapter2;
import com.yzb.card.king.ui.discount.adapter.VpBaseAdapter;
import com.yzb.card.king.ui.discount.adapter.YhkGvAdapter;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.discount.bean.FilterBean;
import com.yzb.card.king.ui.discount.bean.FjTjOutBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.bean.YpBean;
import com.yzb.card.king.ui.film.activity.FilmCinemaDetailActivity;
import com.yzb.card.king.ui.film.presenter.MovieDetailsPersenter;
import com.yzb.card.king.ui.film.view.MovieFiltersView;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.ui.luxury.activity.IMenuDataCallBack;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.ticket.activity.SingleListActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 影片详情tab_item  Fragment；
 */
public class YpDetailTabListFragment extends BaseFragment implements AdapterItemClickCallBack,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, MovieFiltersView

{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VpBaseAdapter vpBaseAdapter;
    private int pageType;
    private ListView listView;
    private FilmMainAdapter2 adapter;
    private LinearLayout panelWdyh, panelFj, panelFl, panelZnpx, panelSx;
    private PopupWindow yhkPopWindow, fjPopWindow, flPopWindow, znpxPopWindow, sxPopWindow;
    private String bankId = "";//银行id；
    private String lng = ""; // 经度；
    private String lat = ""; //纬度；
    private String regionId = ""; //区id；
    private String circleId = ""; //商圈id；
    private String spotId = ""; //热门地标id；
    private SwipeRefreshLayout swipeRefresh;
    private String typeId = "7"; //分类id；
    private String sort = "0"; //排序(默认)；
    private String shopId = ""; //
    private GlobalApp app;
    private String fjTitle = ""; // 附近的title；
    private String distance = ""; //距离；
    private List<StoreBean> savedStoreBeans;
    private TextView tvBank, tvFj, tvFl, tvPx, tvSx;
    private ImageView ivBank, ivFj, ivFl, ivPx, ivSx;
    private String typeParentId = "7";
    private String filmId = "";
    private String customerId;


    private MovieDetailsPersenter persenter;

    public YpDetailTabListFragment()
    {
    }

    public static YpDetailTabListFragment newInstance(int pageType, String filmId)
    {
        YpDetailTabListFragment fragment = new YpDetailTabListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, pageType);
        args.putString(ARG_PARAM2, filmId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            pageType = getArguments().getInt(ARG_PARAM1, -1);
            filmId = getArguments().getString(ARG_PARAM2, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ypxq_tab_item, container, false);
        persenter = new MovieDetailsPersenter(null, this);
        init(view);
        return view;
    }

    /**
     * 初始化；
     *
     * @param view
     */
    private void init(View view)
    {
        UserBean userBean = UserManager.getInstance().getUserBean();
        customerId = userBean == null ? "" : userBean.getAmountAccount();

        if (view == null) return;
        lng = GlobalApp.getSelectedCity().longitude + "";
        lat = GlobalApp.getSelectedCity().latitude + "";

        panelWdyh = (LinearLayout) view.findViewById(R.id.panel_wdyh);
        panelWdyh.setOnClickListener(this);
        panelFj = (LinearLayout) view.findViewById(R.id.panel_fj);
        panelFj.setOnClickListener(this);
        panelFl = (LinearLayout) view.findViewById(R.id.panel_fl);
        panelFl.setOnClickListener(this);

        panelZnpx = (LinearLayout) view.findViewById(R.id.panel_znpx);
        panelZnpx.setOnClickListener(this);
        panelSx = (LinearLayout) view.findViewById(R.id.panel_sx);
        panelSx.setOnClickListener(this);

        tvBank = (TextView) view.findViewById(R.id.tv_bank);
        tvFj = (TextView) view.findViewById(R.id.tv_fj);
        tvFl = (TextView) view.findViewById(R.id.tv_fl);
        tvPx = (TextView) view.findViewById(R.id.tv_px);
        tvSx = (TextView) view.findViewById(R.id.tv_sx);

        ivBank = (ImageView) view.findViewById(R.id.iv_bank);
        ivFj = (ImageView) view.findViewById(R.id.iv_fj);
        ivFl = (ImageView) view.findViewById(R.id.iv_fl);
        ivPx = (ImageView) view.findViewById(R.id.iv_px);
        ivSx = (ImageView) view.findViewById(R.id.iv_sx);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);

        listView = (ListView) view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adapter = new FilmMainAdapter2(getActivity());
        adapter.setAdapterItemClickCallBack(this);
        listView.setAdapter(adapter);

        if (savedStoreBeans != null)
        {
            adapter.appendDataList(savedStoreBeans);
        } else
        {
            getListData(true);
        }
    }

    private List<BankBean> myBanks; //我的银行；
    private List<BankBean> allBanks;//所有银行；
    private FjTjOutBean fjTjOutBean; // 附近数据；
    private List<CategoryBean> categoryBeans; // 分类列表；
    private List<FilterBean> sxBeans;

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panel_wdyh:

                if (yhkPopWindow != null && yhkPopWindow.isShowing())
                {
                    dismissPopWindow(yhkPopWindow);
                    return;
                }

                if (allBanks != null && allBanks.size() > 0)
                {
                    showYhkPopWindow(panelWdyh);
                } else
                {
//                    ToastUtil.i(getActivity(), "开始获取银行卡数据...");
                    getYhkData();
                }
                break;
            case R.id.panel_fj:
                if (fjPopWindow != null && fjPopWindow.isShowing())
                {
                    dismissPopWindow(fjPopWindow);
                    return;
                }

                if (fjTjOutBean != null)
                {
                    showFjPopWindow(panelFj);
                } else
                {
//                    ToastUtil.i(getActivity(), "开始获取附近数据...");
                    getFjtj();
                }
                break;
            case R.id.panel_fl:
                if (flPopWindow != null && flPopWindow.isShowing())
                {
                    dismissPopWindow(flPopWindow);
                    return;
                }
                getFl();
//                }
                break;
            case R.id.panel_znpx:
                if (znpxPopWindow != null && znpxPopWindow.isShowing())
                {
                    dismissPopWindow(znpxPopWindow);
                    return;
                }
                showZnpxPopWindow(panelZnpx);
                break;
            case R.id.panel_sx:
                if (sxPopWindow != null && sxPopWindow.isShowing())
                {
                    dismissPopWindow(sxPopWindow);
                    return;
                }
                if (sxBeans == null)
                {
                    getSxtj();
                } else
                {
                    showSxPopWindow(panelSx);
                }
                break;
        }
    }

    @Override
    public void getFilters(List<FilterBean> sxBeans)
    {

        this.sxBeans = sxBeans;

        if (this.sxBeans != null)
        {
            showSxPopWindow(panelSx);
        } else
        {
            ToastUtil.i(getActivity(), "获取筛选条件失败");
        }
    }

    @Override
    public void getCategory(List<CategoryBean> categoryBeans)
    {
        this.categoryBeans = categoryBeans;
        showFlPopWindow(panelFl);
    }

    @Override
    public void getNearby(FjTjOutBean fjTjOutBean)
    {
        this.fjTjOutBean = fjTjOutBean;
        showFjPopWindow(panelFj);
    }

    @Override
    public void getBank(List<BankBean> myBanks)
    {

        this.myBanks = myBanks;
        // 我的银行加载成功时，菜显示银行对话框；
        if (this.allBanks != null && allBanks.size() > 0)
        {
            showYhkPopWindow(panelWdyh);
        } else
        {
            ToastUtil.i(getActivity(), "您还没有绑定银行卡");
        }
    }

    @Override
    public void getAllBank(List<BankBean> allBanks)
    {
        this.allBanks = allBanks;
    }

    @Override
    public void getMovies(List<YpBean> ypBeanses)
    {
        List<YpBean> ypBeans = ypBeanses;
        LogUtil.i("影片列表storeBeans:" + ypBeans);
        if (ypBeans != null && ypBeans.size() > 0)
        {
            for (YpBean item : ypBeans)
            {
                if (!TextUtils.isEmpty(item.imageCode))
                {
                    item.imageCode = ServiceDispatcher.url_image + "getImg/" + item.imageCode + "/0";
                }
            }
//                        refeshData(ypBeans, null, isRefresh);
        } else
        {
//                        refeshData(null, null, isRefresh);
            ToastUtil.i(getActivity(), "没有数据");
        }
    }

    @Override
    public void getNearbyList(List<StoreBean> storeBeans1)
    {
        List<StoreBean> storeBeans = storeBeans1;
        LogUtil.i("影院列表storeBeans:" + storeBeans);
        if (storeBeans != null && storeBeans.size() > 0)
        {
            for (StoreBean item : storeBeans)
            {
                if (!TextUtils.isEmpty(item.storePhoto))
                {
                    item.storePhoto = ServiceDispatcher.url_image + "getImg/" + item.storePhoto + "/0";
                }
            }
        } else
        {
            ToastUtil.i(getActivity(), "没有数据");
        }

        adapter.clear();
        adapter.appendDataList(storeBeans);
    }

    @Override
    public void callDataFailedMsg(Object o)
    {

    }


    public interface IHeightChange
    {
        void heightChange(int height);
    }

    private IHeightChange heightCallBack;

    public void setChangeHeightCallBack(IHeightChange heightCallBack)
    {
        this.heightCallBack = heightCallBack;
    }

    /**
     * 获取筛选条件列表；
     */
    private void getSxtj()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("typeId", typeParentId);
        persenter.sendFiltersRequest(param, CardConstant.card_app_shopfilterquery);
    }

    /**
     * 获取分类；
     */
    private void getFl()
    {

        Map<String, Object> params_ = new HashMap<>();
        params_.put("cityId", cityId);
        params_.put("lat", lat);
        params_.put("lng", lng);
        params_.put("distance", distance);
        params_.put("bankId", bankId);
        params_.put("regionId", regionId);
        params_.put("circleId", circleId);
        params_.put("spotId", spotId);
        persenter.sendCategoryRequest(params_, CardConstant.card_app_TypeCondition);
    }

    /**
     * 获取附近条件；
     */
    private void getFjtj()
    {


        Map<String, Object> params_ = new HashMap<>();
        params_.put("cityId", cityId);

        persenter.sendNearbyRequest(params_, CardConstant.card_app_nearcondition);

    }

    /**
     * 获取我的银行卡，所有银行卡数据；
     */
    private void getYhkData()
    {
        if (myBanks == null || myBanks.size() == 0)
        {
            getMyBanks();
        }
        if (allBanks == null || allBanks.size() == 0)
        {
            getAllBanks();
        }
    }

    /**
     * 我的银行卡popupwindow；
     *
     * @param anchor 锚点
     */
    private void showYhkPopWindow(View anchor)
    {
        grayViewClick(null);
//        if (myBanks == null || myBanks.size() == 0) {
//            ToastUtil.i(this, "您还没有绑定银行卡");
//            return;
//        }
        if (anchor == null)
        {
            return;
        }
        if (yhkPopWindow == null)
        {
            yhkPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(yhkPopWindow, R.layout.popwindow_content_yhk);
        }
        initYhkViewData();
        yhkPopWindow.showAsDropDown(anchor, 0, CommonUtil.dip2px(getActivity(), 4));
        startInverse(ivBank, R.anim.rotate_animation_start);
        yhkPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                startInverse(ivBank, R.anim.rotate_animation_end);
            }
        });
    }


    /**
     * 初始化银行卡数据；
     * ViewPager+PageAdapter不能再PopupWindow中使用；
     */
    private void initYhkViewData()
    {
        View yhkContentView = yhkPopWindow.getContentView();
        if (yhkContentView == null) return;
        TabLayout tabLayout = (TabLayout) yhkContentView.findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) yhkContentView.findViewById(R.id.viewPager);
        vpBaseAdapter = new VpBaseAdapter();

        List<View> viewList = new ArrayList<>();
        viewList.add(getVpItemView(0));
        viewList.add(getVpItemView(1));
        vpBaseAdapter.setViewList(viewList);
        viewPager.setAdapter(vpBaseAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // 没有绑定银行卡，显示全部银行；
        if (myBanks == null || myBanks.size() == 0)
        {
            viewPager.setCurrentItem(1);
        } else
        {
            viewPager.setCurrentItem(0);
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        grayViewClick(null);
    }


    /**
     * 根据Frament显示情况隐藏PopupWindow；
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isVisibleToUser)
        {
            grayViewClick(null);
        }
    }

    /**
     * 获取ViewPager的每项布局；
     *
     * @param pageIndex
     * @return
     */
    public View getVpItemView(final int pageIndex)
    {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_popup_yhk, null);
        final GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setBackgroundColor(getResources().getColor(R.color.white));

        final YhkGvAdapter yhkGvAdapter = new YhkGvAdapter(getActivity());
        if (pageIndex == 0)
        {
            yhkGvAdapter.appendDataList(myBanks);
        } else if (pageIndex == 1)
        {
            yhkGvAdapter.appendDataList(allBanks);
        }
        yhkGvAdapter.setCurBankId(bankId);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                BankBean bankBean = yhkGvAdapter.getItem(position);
                tvBank.setText(bankBean.bankName);
                bankId = bankBean.id + "";
                grayViewClick(null);
                getListData(true);
            }
        });
        gridView.setAdapter(yhkGvAdapter);
        return view;
    }

    /**
     * 根据条件筛选门店列表；
     */
    private void getListData(final boolean isRefresh)
    {
        if (isRefresh)
        {
            swipeRefresh.setRefreshing(true);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("bankId", bankId);
        param.put("cityId", cityId);
        param.put("lng", lng); // 经度；
        param.put("lat", lat);

        param.put("shopId", shopId);
        param.put("filmId", filmId);

        param.put("regionId", regionId);//区id
        param.put("circleId", circleId);//商圈id
        param.put("typeId", typeId); //分类
        param.put("spotId", spotId); //热门地标id；
        param.put("sort", sort); //排序
        param.put("distance", distance); //排序
        param.put("customerId", customerId);

        persenter.sendNeaybyListRequest(param, CardConstant.card_app_storelist);


    }

    private void setListViewHeight(ListView listView)
    {
        int listViewHeight = ViewUtil.setListViewHeightBasedOnChildren(listView);
        if (heightCallBack != null)
        {
            heightCallBack.heightChange(listViewHeight);
        }
    }

    /**
     * 执行旋转动画；
     *
     * @param view
     * @param animationResId
     */
    public void startInverse(View view, int animationResId)
    {
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), animationResId));
    }

    @Override
    public void onDestroyView()
    {
        if (adapter != null && adapter.getCount() > 0)
        {
            savedStoreBeans = adapter.getDataList();
        }
        grayViewClick(null);
        super.onDestroyView();
    }

    /**
     * adapter item点击；
     *
     * @param args
     */
    @Override
    public void itemClickCallBack(Object... args)
    {
        if (args.length == 1)
        {
            int position = (int) args[0];
            if (position >= 0)
            {
                Bundle bundle = new Bundle();
                bundle.putString("id", adapter.getItem(position).id);
                readyGoWithBundle(getActivity(), FilmCinemaDetailActivity.class, bundle);
            }
        }
    }

    /**
     * 获取我的银行；
     */
    private void getMyBanks()
    {

        persenter.sendBankRequest(null, CardConstant.card_app_mybanks);

    }


    /**
     * 获所有银行；
     */
    private void getAllBanks()
    {
        persenter.sendAllBankRequest(null, CardConstant.card_app_allbanks);
    }

    private void showSxPopWindow(View anchor)
    {
        grayViewClick(null);
        if (anchor == null)
        {
            return;
        }
        if (sxPopWindow == null)
        {
            sxPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(sxPopWindow, R.layout.popwindow_content_sx);
        }
        initSxViewData();
        sxPopWindow.showAsDropDown(anchor, 0, CommonUtil.dip2px(getActivity(), 1.5f));

        startInverse(ivSx, R.anim.rotate_animation_start);
        sxPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                startInverse(ivSx, R.anim.rotate_animation_end);
            }
        });
    }

    private void initSxViewData()
    {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.popSxInnerFragment);
        if (fragment instanceof PopSxInnerFragment)
        {
            PopSxInnerFragment popInnerFragment = (PopSxInnerFragment) fragment;
            popInnerFragment.setMenuDataCallBack(new IMenuDataCallBack()
            {
                @Override
                public void menuDataCallBack(Object... objects)
                {
                    grayViewClick(null);
                }
            });
            popInnerFragment.setDataList(sxBeans);
        }
    }

    private void showZnpxPopWindow(View anchor)
    {
        grayViewClick(null);
        if (anchor == null)
        {
            return;
        }
        if (znpxPopWindow == null)
        {
            znpxPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(znpxPopWindow, R.layout.popwindow_content_znpx);
        }
        initZnpxViewData();
        znpxPopWindow.showAsDropDown(anchor, 0, CommonUtil.dip2px(getActivity(), 1.5f));

        startInverse(ivPx, R.anim.rotate_animation_start);
        znpxPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                startInverse(ivPx, R.anim.rotate_animation_end);
            }
        });
    }

    /**
     * 智能排序data初始化；
     */
    private void initZnpxViewData()
    {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.popZnpxInnerFragment);
        if (fragment instanceof PopZnpxInnerFragment)
        {
            PopZnpxInnerFragment popInnerFragment = (PopZnpxInnerFragment) fragment;
            popInnerFragment.setMenuDataCallBack(new IMenuDataCallBack()
            {
                @Override
                public void menuDataCallBack(Object... objects)
                {
                    grayViewClick(null);

                    if (objects.length == 2)
                    {
                        //分类的id；
                        sort = (String) objects[1];
                        tvPx.setText("智能排序");
                    } else if (objects.length == 3)
                    {
                        //分类的id；
                        sort = (String) objects[1];
                        tvPx.setText(String.valueOf(objects[2]));
                        LogUtil.i("接收到的sort===" + sort);
                        getListData(true);
                    }
                }
            });
            popInnerFragment.setCurSort(sort);
            // 此处数据固定；
            popInnerFragment.setDataList();
        }
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
        if (flPopWindow == null)
        {
            flPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(flPopWindow, R.layout.popwindow_content_fl);
        }
        initFlViewData();
        flPopWindow.showAsDropDown(anchor, 0, CommonUtil.dip2px(getActivity(), 1.5f));

        startInverse(ivFl, R.anim.rotate_animation_start);
        flPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                startInverse(ivFl, R.anim.rotate_animation_end);
            }
        });
    }

    private void initFlViewData()
    {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.popFlInnerFragment);
        if (fragment instanceof PopFlInnerFragment)
        {
            PopFlInnerFragment popInnerFragment = (PopFlInnerFragment) fragment;
            //设置当前选中的大/小分类的id，一边标红；
            popInnerFragment.setParentId(typeParentId);
            popInnerFragment.setCurentFlId(typeId);

            popInnerFragment.setMenuDataCallBack(new IMenuDataCallBack()
            {
                @Override
                public void menuDataCallBack(Object... objects)
                {
                    grayViewClick(null);

                    if (objects.length == 2 && objects[1] instanceof String[])
                    {
                        String[] args = (String[]) objects[1];
                        if (args == null)
                        {
                            return;
                        }
                        String parentIdEmp = args[0];
                        String typeIdEmp = args[1];
                        String typeGrandParentIdEmp = args[4];

                        LogUtil.i("typeGrandParentIdEmp=" + typeGrandParentIdEmp + ",parentIdEmp=" + parentIdEmp + ",typeIdEmp=" + typeIdEmp);

                        //影片；或影院
                        if (typeParentId.equals(parentIdEmp))
                        {
                            typeId = typeIdEmp;
                            String typeParentName = args[2]; // 大分类名称；
                            String typeName = args[3];       // 小分类名称；
                            tvFl.setText(TextUtils.isEmpty(typeName) ? typeParentName : typeName);//小分类
                            getListData(true);
                            return;
                        }

                        //美食；三阶
                        if (!TextUtils.isEmpty(typeGrandParentIdEmp) && AppConstant.meishi_id.equals(typeGrandParentIdEmp))
                        {
//                            Intent intent = new Intent(getActivity(), MsMoreActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("typeParentId", parentIdEmp); // 跳转如二级下的所有门店列表；
//                            bundle.putString("typeName", args[3]);
//                            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
//                            startActivity(intent);
                            return;
                        }
                        // 旅游；三阶；
                        if (!TextUtils.isEmpty(typeGrandParentIdEmp) && AppConstant.travel_id.equals(typeGrandParentIdEmp))
                        {
//                            Intent intent = new Intent(getActivity(), GtyActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("typeId", parentIdEmp);
//                            bundle.putString("typeName", args[3]);
//                            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
//                            startActivity(intent);
                            return;
                        }

                        //奢侈品；
                        if (AppConstant.shechipin_id.equals(typeParentId))
                        {
//                            Intent intent = new Intent(getActivity(), MsMoreActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("typeParentId", parentIdEmp);
//                            bundle.putString("typeId", typeIdEmp);
//                            bundle.putString("typeName", args[3]);
//                            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
//                            startActivity(intent);
                            return;
                        }

                        // 机票；
                        if ("6".equals(parentIdEmp))
                        {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("typeChildId", typeIdEmp);
                            intent.setClass(getActivity(), SingleListActivity.class);
                            intent.putExtra("jp", bundle);
                            startActivity(intent);
                            return;
                        }
                    }
                }
            });
            popInnerFragment.setDataList(categoryBeans);
        }
    }

    /**
     * 影片；
     *
     * @param isRefresh
     */
    private void getYpData(final boolean isRefresh)
    {


        Map<String, Object> param = new HashMap<>();
        param.put("bankId", bankId);
        param.put("cityId", cityId);
        param.put("lng", lng); // 经度；
        param.put("lat", lat);

        param.put("shopId", shopId);
        param.put("filmId", filmId);

        param.put("regionId", regionId);//区id
        param.put("circleId", circleId);//商圈id

        param.put("typeId", typeId); //分类
        param.put("spotId", spotId); //热门地标id；
        param.put("sort", sort); //排序
        param.put("distance", distance); // 距离；

        persenter.sendMoviesRequest(param, CardConstant.card_app_queryfilm);

    }

    /**
     * 根据大分类的id设置tvFl 小分类的内容；
     */
    public void updateTvFlByParentId()
    {
        //美食； 大分类显示；
        if ("1".equals(typeParentId))
        {
            tvFl.setText("美食");
        } else if ("5".equals(typeParentId))
        {
            tvFl.setText("奢侈品");
        } else if ("5".equals(typeParentId))
        {
            tvFl.setText("电影");
        }
    }

    /**
     * 直接设置tvType的内容；
     * 用于分类中的大分类切换时
     */
    public void updateTvFlDirect(String content)
    {
        if (TextUtils.isEmpty(content)) return;
        tvFl.setText(content);
    }

    /**
     * 附近popupwindow；
     *
     * @param anchor 锚点
     */
    private void showFjPopWindow(View anchor)
    {
        grayViewClick(null);
        if (anchor == null)
        {
            return;
        }
        if (fjPopWindow == null)
        {
            fjPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(fjPopWindow, R.layout.popwindow_content_fj);
        }
        initFjViewData(fjTjOutBean);
        fjPopWindow.showAsDropDown(anchor, 0, CommonUtil.dip2px(getActivity(), 1.5f));

        startInverse(ivFj, R.anim.rotate_animation_start);
        fjPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                startInverse(ivFj, R.anim.rotate_animation_end);
            }
        });
    }

    /**
     * 统一处理5个灰色区域单击；
     */
    public void grayViewClick(View v)
    {
        dismissPopWindow(yhkPopWindow);
        dismissPopWindow(fjPopWindow);
        dismissPopWindow(flPopWindow);
        dismissPopWindow(znpxPopWindow);
        dismissPopWindow(sxPopWindow);
    }

    public void dismissPopWindow(PopupWindow window)
    {
        if (window != null && window.isShowing())
        {
            window.dismiss();
        }
    }

    private void initPopWindow(PopupWindow window, int contentViewId)
    {
        if (window != null)
        {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.popupwindow_animation_style);
        }

        if (window != null && contentViewId != 0)
        {
            window.setContentView(LayoutInflater.from(getActivity()).inflate(contentViewId, null));
        }
    }

    /**
     * Fragment中嵌套Fragment时，用getChildFragmentManager从布局中findFragmentById时会为空；
     * 建议用getActivity().getSupportFragmentManager()；
     *
     * @param fjTjOutBean
     */
    private void initFjViewData(FjTjOutBean fjTjOutBean)
    {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.popFjInnerFragment);

        LogUtil.i("popFjInnerFragment==" + fragment);

        if (fragment != null && fragment instanceof PopFjInnerFragment)
        {
            PopFjInnerFragment popInnerFragment = (PopFjInnerFragment) fragment;
            popInnerFragment.setMenuDataCallBack(new IMenuDataCallBack()
            {

                @Override
                public void menuDataCallBack(Object... objects)
                {
                    grayViewClick(null);
                    if (objects != null)
                    {
                        // 选中的第一级；
                        int firstSelectLevel = (int) objects[2];

                        switch (firstSelectLevel)
                        {
                            case 10:
                                distance = (String) objects[1];
                                String label = (String) objects[3];
                                tvFj.setText(label);
                                break;
                            case 11:
                                circleId = (String) objects[1];
                                String label2 = (String) objects[3];
                                tvFj.setText(label2);
                                break;
                            case 12:
                                circleId = (String) objects[1];
                                String label3 = (String) objects[3];
                                tvFj.setText(label3);

                                break;
                            case 13: // 附近-行政区第三级回传；
                                circleId = (String) objects[1];
                                String label4 = (String) objects[3];
                                tvFj.setText(label4);
                                break;

                            case 14:
                                circleId = (String) objects[1];
                                String label5 = (String) objects[3];
                                tvFj.setText(label5);
                                break;
                            case 15:
                                String[] args = (String[]) objects[1];
                                if (args != null && args.length == 4)
                                {
                                    spotId = args[0];
                                    lng = args[1];
                                    lat = args[2];
                                    // 第4个参数 回传选中的名称；
                                    fjTitle = String.valueOf(args[3]);
                                    tvFj.setText(fjTitle);
                                }
                                break;
                        }
                        getListData(true);
                    }
                }
            });
            popInnerFragment.setDataList(fjTjOutBean);
        }
    }


    @Override
    public void onRefresh()
    {
    }

}
