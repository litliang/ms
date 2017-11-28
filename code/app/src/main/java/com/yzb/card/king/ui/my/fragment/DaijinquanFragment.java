package com.yzb.card.king.ui.my.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.adapter.MyDaijinquanCouponeAdapter;
import com.yzb.card.king.ui.my.presenter.MyCouponPresenter;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.List;

/**
 * Created by 玉兵 on 2017/10/29.
 */

public class DaijinquanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseViewLayerInterface {

    private View view;

    private SwipeRefreshLayout swipeRefresh;

    private MyDaijinquanCouponeAdapter adapter;

    private MyCouponPresenter myConuponePresenter;

    private ListView listView;

    //传值的要传实现序列化的Bean
    public static DaijinquanFragment getInstance(String data) {

        DaijinquanFragment fragment = new DaijinquanFragment();

        Bundle bundle = new Bundle();

        bundle.putString("data", data);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_youhui_quan, container, false);

        initView();

        initData();

        return view;
    }

    private void initView() {

        myConuponePresenter = new MyCouponPresenter(this);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        listView = (ListView) view.findViewById(R.id.listView);

        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        adapter = new MyDaijinquanCouponeAdapter(getActivity());

        listView.setAdapter(adapter);
    }

    private Handler handler = new Handler();

    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void onRefresh() {

        loadCouponList(true);
    }

    private void loadCouponList(boolean b) {

        myConuponePresenter.sendMyCouponAction(false);

    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {

        swipeRefresh.setRefreshing(false);

        List<CouponInfoBean> beans = JSON.parseArray(o + "", CouponInfoBean.class);

        adapter.clearAll();

        adapter.appendALL(beans);
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {

        swipeRefresh.setRefreshing(false);
    }
}
