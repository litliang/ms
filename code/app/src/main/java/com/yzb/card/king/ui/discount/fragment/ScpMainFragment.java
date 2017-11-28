package com.yzb.card.king.ui.discount.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.discount.model.IScpMain;
import com.yzb.card.king.ui.discount.presenter.ScpMainPresenter;
import com.yzb.card.king.ui.luxury.activity.ChannelActivity;
import com.yzb.card.king.ui.luxury.activity.ImgClickCallBack;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.base.BaseCityChangeFragment;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.discount.adapter.ScpMainAdapter;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.LbtBean;
import com.yzb.card.king.ui.discount.bean.ShopBean;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.SlideShow8ItemView;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.travel.view.TravelMainView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奢侈品主页Fragment；
 */
public class ScpMainFragment extends BaseCityChangeFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener, TravelMainView {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View headerView;
    private ScpMainAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    private ListView listview;
    private SlideShow1ItemView slideShowView1;
    private SlideShow8ItemView slideShowView2;
    private float slide1_whrate = 432 / 1080.0f;
    private String typeParentId = AppConstant.shechipin_id;
    private ChannelMainActivity activity;
    private ScpMainPresenter presenter;

    public ScpMainFragment()
    {
    }

    public static ScpMainFragment newInstance(String param1, String param2)
    {
        ScpMainFragment fragment = new ScpMainFragment();
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
        presenter = new ScpMainPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_scp_main, container, false);
        init(view);
        activity = (ChannelMainActivity) getActivity();
        getData(true);
        return view;
    }

    private void init(View view)
    {
        if (view == null) return;

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        listview = (ListView) view.findViewById(R.id.listview);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.scp_main_list_header, null);
        findViewFromHeader();
        listview.addHeaderView(headerView);
        listview.setHeaderDividersEnabled(false);
        listview.setFooterDividersEnabled(false);
        adapter = new ScpMainAdapter(getActivity());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    private void getData(final boolean loadDataRefresh)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                slideShowView1.setParam(AppConstant.shechipin_id, cityId,"5");
                // 本地json格式的子分类；
                String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_scp, "[]");

                LogUtil.i("本地json子分类==" + childTypeJson);
                List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);
                if (childTypeBeans != null && childTypeBeans.size() > 0)
                {
                    slideShowView2.setDataList(childTypeBeans, 5);
                }
                getUserChannel();
                getTjsh(loadDataRefresh);
            }
        }, 200);
    }

    @Override
    public void onCityChange(Location city)
    {
        super.onCityChange(city);
        getData(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (resultCode)
        {
            case ChannelActivity.SAVE_CHNNEL_OK:
                String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_scp, "[]");
                slideShowView2.setDataList(JSON.parseArray(childTypeJson, ChildTypeBean.class), 5);
                break;
        }
    }

    @Override
    public void onRefresh()
    {
        getData(true);
    }

    /**
     * 获取商户列表； 待调试；
     *
     * @param loadDataRefresh
     */
    private void getTjsh(final boolean loadDataRefresh)
    {
        swipeRefresh.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("cityId", getCityId(getActivity()));
        param.put("typeId", typeParentId);
        presenter.getTjsh(param);
    }

    /**
     * 获取个人频道列表；
     */
    private void getUserChannel()
    {
        presenter.getUserChannel(typeParentId, AppConstant.discount_channel_category);

    }

    /**
     * init header中的view；
     */
    private void findViewFromHeader()
    {
        slideShowView1 = (SlideShow1ItemView) headerView.findViewById(R.id.slideShowView1);
        slideShow1ItemViewSetings();
        slideShowView2 = (SlideShow8ItemView) headerView.findViewById(R.id.slideShowView2);
        slideShow4ItemViewSetings();
        LinearLayout panel_dots2 = (LinearLayout) headerView.findViewById(R.id.panel_dots2);
        slideShowView2.setPanelDots(panel_dots2);

        headerView.findViewById(R.id.ll_edit).setOnClickListener(this);
    }

    /**
     * 顶部轮播设置；
     */
    private void slideShow1ItemViewSetings()
    {
        if (slideShowView1 == null) return;
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        ViewUtil.set(slideShowView1,
                screenWith,
                (int) (screenWith * slide1_whrate + 0.5));

        slideShowView1.setImgClickCallBack(new ImgClickCallBack() {

            @Override
            public void imgClickcallBack(Object... objects)
            {
                if (objects != null && objects.length == 1 && objects[0] instanceof LbtBean)
                {
                    LbtBean lbtBean = (LbtBean) objects[0];
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataBean", lbtBean);
                    readyGoWithBundle(getActivity(), WebViewClientActivity.class, bundle);
                }
            }
        });
    }

    /**
     * 4个item的轮播；
     */
    private void slideShow4ItemViewSetings()
    {
        if (slideShowView2 == null) return;
        slideShowView2.setFragmentManager(getChildFragmentManager());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_edit: // 编辑按钮；
                Intent intent = new Intent(getActivity(), ChannelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ChannelActivity.SP_KEY, AppConstant.sp_childtypelist_scp);
                bundle.putString("typeParentId", typeParentId);
                bundle.putString("category", AppConstant.discount_channel_category);

                intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
//        Bundle bundle = new Bundle();
//        bundle.putString("typeParentId", typeParentId);
//        //是否还需要提交品牌的id；
//        bundle.putString("typeGrandName", getString(R.string.category_scp));
//        readyGoWithBundle(getActivity(), MsMoreActivity.class, bundle);
    }

    @Override
    public void transmitChannelData(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
    {
        swipeRefresh.setRefreshing(false);
        if (displayChannelList != null)
        {
            displayChannelList.addAll(hideChannelList);

            String localJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_scp, "[]");
            List<ChildTypeBean> localLists = JSON.parseArray(localJson, ChildTypeBean.class);
            localLists = CommonUtil.filterList(localLists, displayChannelList);

            // 持久化到本地；
            SharePrefUtil.saveToSp(getActivity(), AppConstant.sp_childtypelist_scp,
                    JSON.toJSONString(localLists));
            slideShowView2.setDataList(localLists, 5);
        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == IScpMain.SHANGHULIST_CODE)
        {
            if (o instanceof ShopBean)
            {
                List<ShopBean> storeBeans = (List<ShopBean>) o;

                LogUtil.i("奢侈品推荐门店storeBeans:" + storeBeans);
                if (storeBeans != null && storeBeans.size() > 0)
                {
                    for (ShopBean item : storeBeans)
                    {
                        if (!TextUtils.isEmpty(item.photo))
                        {
                            item.photo = ServiceDispatcher.getImageUrl(item.photo);
                        }
                    }
                    adapter.clear();
                    adapter.appendDataList(storeBeans);
                }
            }

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
