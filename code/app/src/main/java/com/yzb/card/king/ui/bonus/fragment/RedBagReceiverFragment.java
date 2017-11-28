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
import com.yzb.card.king.ui.bonus.activity.RedBagDetailActivity;
import com.yzb.card.king.ui.bonus.adapter.RedBagReceiverAdapter;
import com.yzb.card.king.ui.bonus.bean.RedBagReceiveBean;
import com.yzb.card.king.ui.bonus.presenter.ReceiveRedBagPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名： 红包明细收fragment
 * 作 者： gaolei
 * 日 期：2016年12月26日
 * 描 述：红包收明细
 */
public class RedBagReceiverFragment extends Fragment implements RedBagReceiverAdapter.OnMyItemClickListener, BaseViewLayerInterface {

    private View view;

    private RecyclerView recyclerview;

    private RedBagReceiverAdapter adapter;

    private ReceiveRedBagPresenter presenter;

    private List<RedBagReceiveBean> list = new ArrayList<>();

    private static int flag = 1;

    private int lastvisiableItem;

    private LinearLayoutManager linearLayoutManager;

    private Handler handler = new Handler();

    public RedBagReceiverFragment() {

    }

    //传值的要传实现序列化的Bean
    public static RedBagReceiverFragment getInstance(String data) {

        RedBagReceiverFragment fragment = new RedBagReceiverFragment();

        Bundle bundle = new Bundle();

        bundle.putString("data", data);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_red_bag_send, container, false);

        presenter = new ReceiveRedBagPresenter(this);
        initView();
        initSwipe();
        initData(0);
        return view;
    }



    private void initData(int page) {

        presenter.getReceiveRedBagData(page,  1);

    }



    private void initAdapter() {
        adapter = new RedBagReceiverAdapter(getActivity(), list);
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


    /**
     * item点击跳转到红包详情
     */
    @Override
    public void OnMyItemClick(RecyclerView parent, View view, int position, List<RedBagReceiveBean> list) {
        Log.d("gl","themeName" + list.get(position).getThemeName());
        RedBagReceiveBean temp = list.get(position);
        Intent intent = new Intent(getActivity(),RedBagDetailActivity.class);
        intent.putExtra("RecordId",temp.getOrderId());
        intent.putExtra("themeName",temp.getThemeName());
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
                        final List<RedBagReceiveBean> demos = new ArrayList<RedBagReceiveBean>();
                        initData(flag);
                        int len = list.size();
                        for (int i = len; i < len + len; ) {//最后服务器会有20条数据
                            demos.add(list.get(i - len));
                            i++;
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addData(demos);
                            }
                        }, 2000);
                        flag++;
                    } else {
                        Toast.makeText(getActivity(), "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                    super.onScrolled(recyclerView, dx, dy);
                    lastvisiableItem = linearLayoutManager.findLastVisibleItemPosition();
                    itemCount = linearLayoutManager.getItemCount();//adapter.getItemCount() 效果一样
                }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        flag = 1;
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1) {
            list = JSON.parseArray(String.valueOf(o), RedBagReceiveBean.class);
            initAdapter();
            initSwipe();
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
