package com.yzb.card.king.ui.discount.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.food.FoodThemeBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.SlideShow8ItemView;
import com.yzb.card.king.ui.appwidget.popup.presenter.ChannelPresenter;
import com.yzb.card.king.ui.appwidget.popup.presenter.impl.ChannelPresenterImpl;
import com.yzb.card.king.ui.appwidget.popup.view.ChannelView;
import com.yzb.card.king.ui.base.BaseCityChangeFragment;
import com.yzb.card.king.ui.discount.adapter.MsMainTodayRecommendAdapter;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.presenter.impl.FoodThemeListPresenterImpl;
import com.yzb.card.king.ui.discount.view.FoodThemeListView;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.ui.luxury.activity.ChannelActivity;
import com.yzb.card.king.ui.luxury.activity.RecommendDiningRoomActivtiy;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 美食主页Fragment；
 *
 * @author gengqiyun
 */
public class MsMainFragment extends BaseCityChangeFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, ChannelView, FoodThemeListView
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View headerView;
    private MsMainTodayRecommendAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    private LoadMoreListView listview;
    private SlideShow1ItemView slideShowView1;
    private SlideShow8ItemView slideShowView2;
    private float slide1_whrate = 432 / 1080.0f;
    private String typeGrandParentId = AppConstant.meishi_id;
    private int startIndex = 0;
    private Handler mainHandler = new Handler();
    private ChannelPresenter channelPresenter;   //频道presenter；
    private FoodThemeListPresenterImpl foodThemeListPresenter;

    public static MsMainFragment newInstance(String param1, String param2)
    {
        MsMainFragment fragment = new MsMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ms_main, container, false);
        init(view);
        getData();
        return view;
    }

    private void init(View view)
    {
        channelPresenter = new ChannelPresenterImpl(this);
        foodThemeListPresenter = new FoodThemeListPresenterImpl(this);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        listview = (LoadMoreListView) view.findViewById(R.id.listview);
        listview.setOnLoadMoreListener(loadMoreListener);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.meishi_main_list_header, null);
        findViewFromHeader();

        listview.addHeaderView(headerView);
        listview.setDividerHeight(0);

        adapter = new MsMainTodayRecommendAdapter(getContext());
        adapter.setAdapterItemClickCallBack(itemClickCallBack);
        listview.setAdapter(adapter);
    }


    /**
     * adapter item点击；
     */
    private AdapterItemClickCallBack itemClickCallBack = new AdapterItemClickCallBack()
    {

        @Override
        public void itemClickCallBack(Object... args)
        {
            int position = (int) args[0];
            FoodThemeBean foodThemeBean = (FoodThemeBean) adapter.getItem(position);
            Intent it = new Intent(getContext(), RecommendDiningRoomActivtiy.class);
            it.putExtra("data", foodThemeBean);
            startActivity(it);
        }
    };

    /**
     * 加载更多监听；
     */
    private LoadMoreListView.OnLoadMoreListener loadMoreListener = new LoadMoreListView.OnLoadMoreListener()
    {

        @Override
        public void onLoadMore()
        {
            getThemeList(false);
        }
    };

    @Override
    public void onCityChange(Location city)
    {
        super.onCityChange(city);
        LogUtil.i("新的city==" + city.cityId);
        getData();
    }

    private void getData()
    {
        mainHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                slideShowView1.setParam(AppConstant.meishi_id, cityId,"1");
                // 本地json格式的子分类；
                String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_ms, "[]");

                LogUtil.i("美食本地json子分类==" + childTypeJson);
                List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);
                if (childTypeBeans != null && childTypeBeans.size() > 0)
                {
                    slideShowView2.setDataList(childTypeBeans, AppConstant.ms_parent_id);
                }
                getUserChannel();
                getThemeList(true);
            }
        }, 100);
    }

    @Override
    public void onGetChannelSucess(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
    {
        if (displayChannelList != null)
        {
            displayChannelList.addAll(hideChannelList);

            String localJson = SharePrefUtil.getValueFromSp(GlobalApp.getInstance(), AppConstant.sp_childtypelist_ms, "[]");
            List<ChildTypeBean> localLists = JSON.parseArray(localJson, ChildTypeBean.class);
            localLists = CommonUtil.filterList(localLists, displayChannelList);
            // 持久化到本地；
            SharePrefUtil.saveToSp(getActivity(), AppConstant.sp_childtypelist_ms, JSON.toJSONString(localLists));
            slideShowView2.setDataList(localLists, 1);
        }
    }

    @Override
    public void onGetChannelFail(String failMsg)
    {
        LogUtil.i("频道列表加载失败=" + failMsg);
    }

    /**
     * 获取频道列表；
     */
    private void getUserChannel()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("parentId", AppConstant.meishi_id);
        param.put("category", AppConstant.discount_channel_category);
        LogUtil.i("频道列表提交参数:" + param);
        channelPresenter.loadData(param);
    }

    @Override
    public void onRefresh()
    {
        startIndex = 0;
        getData();
    }

    @Override
    public void onLoadFoodThemeListSucess(boolean event_tag, Object data)
    {
        swipeRefresh.setRefreshing(false);
        listview.onLoadMoreComplete();
        if (data == null)
        {
            return;
        }
        List<FoodThemeBean> dataList = (List<FoodThemeBean>) data;
        //下拉刷新先清空数据；
        if (event_tag)
        {
            adapter.clear();
        }
        adapter.addNewList(dataList);
        startIndex++;
    }

    @Override
    public void onLoadFoodThemeListFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        listview.onLoadMoreComplete();
        // toastCustom(R.string.app_no_data);
    }

    /**
     * 发送美食主题请求
     */
    private void getThemeList(boolean event_tag)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", GlobalApp.getSelectedCity().cityId);
        params.put("channelId", AppConstant.meishi_id);
        params.put("pageStart", startIndex + "");
        params.put("pageSize", AppConstant.MAX_PAGE_NUM + "");
        foodThemeListPresenter.loadData(event_tag, params);
    }

    /**
     * init header中的view；
     */
    private void findViewFromHeader()
    {
        slideShowView1 = (SlideShow1ItemView) headerView.findViewById(R.id.slideShowView1);
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        ViewUtil.set(slideShowView1,
                screenWith,
                (int) (screenWith * slide1_whrate + 0.5));

        slideShowView2 = (SlideShow8ItemView) headerView.findViewById(R.id.slideShowView2);
        slideShowView2.setFragmentManager(getChildFragmentManager());

        LinearLayout panel_dots2 = (LinearLayout) headerView.findViewById(R.id.panel_dots2);
        slideShowView2.setPanelDots(panel_dots2);
        headerView.findViewById(R.id.ll_edit).setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_edit: // 编辑按钮；
                Intent intent = new Intent(getActivity(), ChannelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ChannelActivity.SP_KEY, AppConstant.sp_childtypelist_ms);
                bundle.putString("typeParentId", typeGrandParentId);
                bundle.putString("category", AppConstant.discount_channel_category);

                intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
                startActivityForResult(intent, 100);
                break;
            case R.id.panel_more://更多；
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("typeGrandParentId", typeGrandParentId);
//                bundle2.putString("typeGrandName", getString(R.string.category_ms));
//                readyGoWithBundle(getActivity(), MsMoreActivity.class, bundle2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (resultCode)
        {
            case ChannelActivity.SAVE_CHNNEL_OK:
                String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_ms, "[]");
                slideShowView2.setDataList(JSON.parseArray(childTypeJson, ChildTypeBean.class), 1);
                break;
        }
    }

}
