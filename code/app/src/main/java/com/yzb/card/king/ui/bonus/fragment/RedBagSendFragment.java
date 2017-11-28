package com.yzb.card.king.ui.bonus.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yzb.card.king.R;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.bonus.activity.RedBagDetailSendActivity;
import com.yzb.card.king.ui.bonus.adapter.RedBagSendAdapter;
import com.yzb.card.king.ui.bonus.bean.RedBagSendBean;
import com.yzb.card.king.ui.bonus.presenter.SendRedBagPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名： 红包明细发fragment
 * 作 者： gaolei
 * 日 期：2016年12月26日
 * 描 述：红包发明细
 */
public class RedBagSendFragment extends Fragment implements RedBagSendAdapter.OnMyItemClickListener, BaseViewLayerInterface {

    private View view;
    private RecyclerView recyclerview;
    private RedBagSendAdapter adapter;
    private SendRedBagPresenter presenter;
    private List<RedBagSendBean> list;
    private static int flag = 1;
    private LinearLayoutManager linearLayoutManager;
    private int lastvisiableItem;
    private Handler handler = new Handler();

    public RedBagSendFragment() {
        // Required empty public constructor
    }

    //传值的要传实现序列化的Bean
    public static RedBagSendFragment getInstance(String data) {
        RedBagSendFragment fragment = new RedBagSendFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_red_bag_send, container, false);
        presenter = new SendRedBagPresenter(this);
        getData(0);
        initView();
        return view;
    }

    private void getData(int page) {

        presenter.getSendRedBagData(page, 1);
    }

    private void initData() {

        adapter = new RedBagSendAdapter(getActivity(), list);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DecorationUtil(2));
        recyclerview.setAdapter(adapter);
        adapter.setOnMyItemClickListener(this);
    }

    private void initView() {
        recyclerview = (RecyclerView) view.findViewById(R.id.red_bag_send_recyclerview);
    }


    protected String cityId;
    protected String cityName;
    protected GlobalApp app;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cityId = GlobalApp.getSelectedCity().cityId;
        cityName = GlobalApp.getSelectedCity().cityName;
        app = (GlobalApp) context.getApplicationContext();
    }

    protected void toastCustom(String text) {
        ToastUtil.i(getActivity(),text);
    }

    protected void toastCustom(int resId) {
        ToastUtil.i(getActivity(), getString(resId));
    }

    protected String getCityId(Context context) {
        return GlobalApp.getSelectedCity().cityId;
    }


    private boolean injected = false;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    public void log(String msg) {
        LogUtil.i(msg);
    }


    //系统自动销毁Fragment前保存必要的数据
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void OnMyItemClick(RecyclerView parent, View view, int position, List<RedBagSendBean> data) {
        Intent intent = new Intent(getActivity(), RedBagDetailSendActivity.class);
        intent.putExtra("orderId", data.get(position).getOrderId()+"");
        intent.putExtra("themeName", data.get(position).getThemeName()+"");
        Log.d("gl", "orderId=====" + data.get(position).getOrderId());
        startActivity(intent);
    }

    @Override
    public void onLoad() {
    }


    private void initSwipe() {

       recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int itemCount;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastvisiableItem == (adapter.getItemCount() - 1)) {
                    if (list.size() >= 20) {
                        final List<RedBagSendBean> demos = new ArrayList<RedBagSendBean>();
                        getData(flag);
                        int len = list.size();
                        for (int i = len; i < len + len;) {//最后服务器会有20条数据
                            demos.add(list.get(i-len));
                            i++;
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addData(demos);
                            }
                        },2000);
                        flag++;
                    }else {
                        Toast.makeText(getActivity(), "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastvisiableItem = linearLayoutManager.findLastVisibleItemPosition();
                itemCount = linearLayoutManager.getItemCount();//adapter.getItemCount() 效果一样
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //getData(0);
        initSwipe();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = 1;
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1) {
            list = JSON.parseArray(String.valueOf(o), RedBagSendBean.class);

            for (int i = 0; i < list.size(); i++) {//可以用来判断是否领用
            }
            initData();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == 1) {
            Toast.makeText(getActivity(), "无信息", Toast.LENGTH_SHORT).show();
        }
    }
}
