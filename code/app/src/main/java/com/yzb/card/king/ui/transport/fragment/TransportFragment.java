package com.yzb.card.king.ui.transport.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.other.constants.TypeConstants;
import com.yzb.card.king.ui.luxury.activity.ChannelActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.ui.appwidget.popup.presenter.ChannelPresenter;
import com.yzb.card.king.ui.appwidget.popup.presenter.impl.ChannelPresenterImpl;
import com.yzb.card.king.ui.appwidget.popup.view.ChannelView;
import com.yzb.card.king.ui.appwidget.MoreFunctionPublicTitleView;
import com.yzb.card.king.ui.base.BaseCityChangeFragment;
import com.yzb.card.king.ui.discount.DiscountFragmentCallBack;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.SlideShow8ItemView2;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 2016/5/25.
 * 第一次修改：gengqiyun  date:2016.9.6
 */
public class TransportFragment extends BaseCityChangeFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, ChannelView
{
    private static final int REQ_EDIT_CHANNEL = 100;
    private SlideShow1ItemView show1ItemView;
    private SlideShow8ItemView2 show8ItemView;
    private String typeParentId = AppConstant.transport_id;
    private float slide1_whrate = 432 / 1080.0f;
    private Map<String, Fragment> fragmentsMap = new HashMap<>();
    private SwipeRefreshLayout swipeRefresh;
    private DiscountFragmentCallBack callBack; //Activity和Fragment作用接口；
    private SparseArray<String> titleArray = new SparseArray<>();
    private ChannelPresenter channelPresenter; //获取个人频道列表

    public static TransportFragment newInstance(String param1, String param2)
    {
        Bundle args = new Bundle();
        TransportFragment fragment = new TransportFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof DiscountFragmentCallBack)
        {
            callBack = (DiscountFragmentCallBack) context;
        }
    }

    /**
     * 获取个人频道列表；
     */
    private void getUserChannel()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("parentId", AppConstant.transport_id);
        param.put("category", AppConstant.discount_channel_category);
        channelPresenter.loadData(param);
    }

    @Override
    public void onCityChange(Location city)
    {
        super.onCityChange(city);
        initData();
    }

    @Override
    public void onGetChannelSucess(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
    {
        swipeRefresh.setRefreshing(false);
        log("频道列表displayChannelList=" + JSON.toJSONString(displayChannelList
                + ",hideChannelList=" + JSON.toJSONString(hideChannelList)));
        if (displayChannelList != null)
        {
            displayChannelList.addAll(hideChannelList);
            String localJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_transport, "[]");
            List<ChildTypeBean> localLists = JSON.parseArray(localJson, ChildTypeBean.class);
            localLists = CommonUtil.filterList(localLists, displayChannelList);
            // 持久化到本地；
            SharePrefUtil.saveToSp(getActivity(), AppConstant.sp_childtypelist_transport,
                    JSON.toJSONString(localLists));
            if (localLists != null && localLists.size() > 0)
            {
                show8ItemView.setDataList(localLists, Integer.parseInt(typeParentId));
                resetFragmentMap(localLists);
                changeContent(titleArray.keyAt(0) + "");
            }
        }
    }

    @Override
    public void onGetChannelFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_home, container, false);
        callBack.getTitleView().setTitleName(TypeConstants.LushangjiaotongType.HUOCHEPIAO.getName());
        initView(view);
        initData();
        return view;
    }

    private void initView(View view)
    {
        channelPresenter = new ChannelPresenterImpl(this);

        show1ItemView = (SlideShow1ItemView) view.findViewById(R.id.slideShowView1);
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        ViewUtil.set(show1ItemView,
                screenWith,
                (int) (screenWith * slide1_whrate + 0.5));

        show8ItemView = (SlideShow8ItemView2) view.findViewById(R.id.slideShowView2);
        show8ItemView.setFragmentManager(getChildFragmentManager());
        show8ItemView.setOnItemClickListener(new SlideShow8ItemView2.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position, String typeId)
            {
                changeContent(typeId);
            }
        });
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        view.findViewById(R.id.ll_edit).setOnClickListener(this);

        LinearLayout panelDot = (LinearLayout) view.findViewById(R.id.panel_dots2);
        show8ItemView.setPanelDots(panelDot);
    }

    /**
     * 根据点击的子分类的typeId改变title和内容；
     *
     * @param typeId
     */
    private void changeContent(String typeId)
    {
        if (fragmentsMap == null || TextUtils.isEmpty(typeId))
        {
            return;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // commit; 会报 Can not perform this action after onSaveInstanceState
        transaction.replace(R.id.fl_container, fragmentsMap.get(typeId)).commitAllowingStateLoss();

        MoreFunctionPublicTitleView titleView = callBack.getTitleView();
        for (int i = 0; i < titleArray.size(); i++)
        {
            if (typeId.equals("" + titleArray.keyAt(i)))
            {
                titleView.setTitleName(titleArray.valueAt(i));
                break;
            }
        }
    }

    /**
     * 初始化子分类点击时相关数据配置；
     */
    private void initTitleNameArray()
    {
        String[] titles = getResources().getStringArray(R.array.transport_title_array);
        titleArray.clear();
        for (int i = 0; i < titles.length; i++)
        {
            titleArray.put(77 + i, titles[i]);
        }
    }

    private void initData()
    {
        initTitleNameArray();
        show1ItemView.setParam(AppConstant.transport_id, cityId,AppConstant.transport_id);

        String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_transport, "[]");

        List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);
        if (childTypeBeans != null && childTypeBeans.size() > 0)
        {
            show8ItemView.setDataList(childTypeBeans, Integer.parseInt(typeParentId));
            resetFragmentMap(childTypeBeans);
            changeContent(titleArray.keyAt(0) + "");
        }
        getUserChannel();
    }

    @Override
    public void onRefresh()
    {
        initData();
    }

    private void resetFragmentMap(List<ChildTypeBean> childTypeBeans)
    {
        fragmentsMap.clear();
        for (ChildTypeBean child : childTypeBeans)
        {
            fragmentsMap.put(child.id, AppFactory.createFragment(child.id));
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_edit: //编辑子菜单
                Intent intent = new Intent(getActivity(), ChannelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("typeParentId", typeParentId);
                bundle.putString(ChannelActivity.SP_KEY, AppConstant.sp_childtypelist_transport);
                bundle.putString("category", AppConstant.discount_channel_category);
                intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
                startActivityForResult(intent, REQ_EDIT_CHANNEL);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQ_EDIT_CHANNEL://编辑子菜单
                String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_transport, "[]");
                show8ItemView.setDataList(JSON.parseArray(childTypeJson, ChildTypeBean.class), 9);
                resetFragmentMap(JSON.parseArray(childTypeJson, ChildTypeBean.class));
                break;
        }
    }

}
