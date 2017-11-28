package com.yzb.card.king.ui.ticket.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FlightDynamicsBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.ui.appwidget.menulistview.MenuListView;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.ticket.activity.PlaneDtDetailActivity;
import com.yzb.card.king.ui.ticket.adapter.MyCollectListAdapter;
import com.yzb.card.king.ui.ticket.model.IPlaneMyConcern;
import com.yzb.card.king.ui.ticket.presenter.PlaneMyConcernPresenter;
import com.yzb.card.king.util.SharedPreferencesUtils;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/28
 * 描  述：
 */
public class PlaneMyConcernFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, BaseViewLayerInterface {

    private View view;

    private MenuListView list;


    private List<FlightDynamicsBean> listfb = new ArrayList<>();

    private List<FlightDynamicsBean> myFocusOnBeen = new ArrayList<>();

    private MyCollectListAdapter adapter;
    private int pageStart = 0;

    private SwipeRefreshLayout sRl;
    private View.OnClickListener onClickListener;

    private RemoveListInfo removeListInfo;

    private PlaneMyConcernPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_my_concern, container, false);
        getData();
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(View view)
    {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int index = (int) view.getTag();

                FlightDynamicsBean flightDynamicsBean = (FlightDynamicsBean) adapter.getItem(index);
                switch (view.getId())
                {
                    case R.id.rl_item:
                        Intent intent = new Intent(PlaneMyConcernFragment.this.getActivity(), PlaneDtDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("hbInfo", flightDynamicsBean.getFlight_number());
                        bundle.putString("hbTime", flightDynamicsBean.getTimeseres());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }
        };
        sRl = (SwipeRefreshLayout) view.findViewById(R.id.plane_sRL);
        SwipeRefreshSettings.setAttrbutes(PlaneMyConcernFragment.this.getActivity(), sRl);
        sRl.setOnRefreshListener(this);
        list = (MenuListView) view.findViewById(R.id.listView_mycoll);
        adapter = new MyCollectListAdapter(PlaneMyConcernFragment.this.getActivity(), myFocusOnBeen, onClickListener);
        list.setAdapter(adapter);

        presenter = new PlaneMyConcernPresenter(this);
        getListData();
    }

    private void getListData()
    {
        String flightName = "";
        String date = "";
        if (listfb.size() > 0)
        {
            Map<String, Object> param = new HashMap<>();
            for (int i = 0; i < listfb.size(); i++)
            {
                flightName += listfb.get(i).getFlight_number() + ",";
                date += listfb.get(i).getTimeseres() + ",";

            }
            param.put("date", date.substring(0, date.lastIndexOf(",")));
            param.put("name", flightName.substring(0, flightName.lastIndexOf(",")));
            presenter.getList(param);

        } else if (listfb.size() == 0)
        {
            Map<String, Object> param = new HashMap<>();
            param.put("date", date);
            param.put("name", "");
            presenter.getList(param);
        }

    }

    /**
     * 获取保存在本地的数据
     */
    private void getData()
    {
        String info = (String) SharedPreferencesUtils.getParam(getActivity(), "list", "[]", "collect");
        listfb.clear();
        listfb.addAll(JSON.parseArray(info, FlightDynamicsBean.class));
    }

    @Override
    public void onRefresh()
    {
        getListData();
    }

    @Override
    public void onClick(View view)
    {
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == IPlaneMyConcern.QUERT_CODE)
        {
            sRl.setRefreshing(false);
            if (o instanceof List)
            {
                List<FlightDynamicsBean> list = (List<FlightDynamicsBean>) o;
                myFocusOnBeen.clear();
                myFocusOnBeen.addAll(list);
                adapter.notifyDataSetChanged();
            }
            if (o == null)
            {
                myFocusOnBeen.clear();
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        sRl.setRefreshing(false);
        if (o != null && o instanceof Map)
        {
            Map<String, String> onSuccessData = (Map<String, String>) o;

            if (onSuccessData.get(HttpConstant.SERVER_CODE).equals("9999"))
            {
                ToastUtil.i(PlaneMyConcernFragment.this.getContext(), "请求超时");
            }
        }
        myFocusOnBeen.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 注册广播
     */
    private class RemoveListInfo extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals("com.planeMyConcernFragmet"))
            {
                if (intent.getExtras().getInt("notice") == 1)
                {
                    int i = intent.getExtras().getInt("listId");
                    listfb.remove(i);
                    getListData();

                } else if (intent.getExtras().getInt("notice") == 2)
                {
                    ArrayList<FlightDynamicsBean> fdb = (ArrayList<FlightDynamicsBean>) intent.getBundleExtra("bundle").getSerializable("list");
                    listfb.clear();
                    listfb.addAll(fdb);
                    getListData();
                }
            }
        }
    }

    @Override
    public void onAttach(Context context)
    {
        removeListInfo = new RemoveListInfo();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.planeMyConcernFragmet");
        getActivity().registerReceiver(removeListInfo, filter);
        super.onAttach(context);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getActivity().unregisterReceiver(removeListInfo);
    }
}
