package com.yzb.card.king.ui.luxury.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.discount.adapter.MsglAdapter;
import com.yzb.card.king.ui.discount.bean.MsglBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.appwidget.ExpandableLayoutListView;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 美食概览；
 * created by  gengqiyun  on 2016.4.18
 */
public class MsglActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private LinearLayout panelCity;
    private TextView tvCity;
    private ImageView arrowDownCity;

    private ExpandableLayoutListView expandListView;
    private List<MsglBean> itemBeans;
    private MsglAdapter adapter;
    private PopupWindow cityPopWindow;

    private SwipeRefreshLayout swipeRefresh;
    private ImageView iv_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgl);
        assignViews();
        onRefresh();
    }

    public void goBack(View view) {
        finish();
    }

    private void assignViews() {
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        panelCity = (LinearLayout) findViewById(R.id.panel_city);
        panelCity.setOnClickListener(this);

        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);

        tvCity = (TextView) findViewById(R.id.tv_city);
        tvCity.setText(selectedCity.cityName);

        arrowDownCity = (ImageView) findViewById(R.id.arrow_down_city);

        expandListView = (ExpandableLayoutListView) findViewById(R.id.expandListView);
        expandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    adapter.msglBean.isSelected = true;
                    if (adapter.itemBeans != null) {
                        for (int i = 0; i < adapter.itemBeans.size(); i++) {
                            adapter.itemBeans.get(i).isSelected = false;
                        }
                    }
                } else {
                    if (adapter.msglBean != null)
                        adapter.msglBean.isSelected = false;

                    if (adapter.itemBeans != null) {
                        for (int i = 0; i < adapter.itemBeans.size(); i++) {

                            if (i == position - 1) {
                                adapter.itemBeans.get(i).isSelected = true;
                            } else {
                                adapter.itemBeans.get(i).isSelected = false;
                            }

                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
        adapter = new MsglAdapter(this);
        expandListView.setAdapter(adapter);
    }

    /**
     * 用于返回城市信息；
     */
    @Override
    protected void onResume() {
        super.onResume();

        String cityId = CitySelectManager.getInstance().getPlaceId();
        String cityName = CitySelectManager.getInstance().getPlaceName();

        if (TextUtils.isEmpty(cityId)) {
            return;
        }
        tvCity.setText(cityName);
        this.selectedCity.cityId = cityId;

        LogUtil.i("返回的cityId==" + cityId);
        onRefresh();
        CitySelectManager.getInstance().clearData();
    }

    @Override
    public void onRefresh() {
        loadDataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                getData(true);
            }
        }, 50);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panel_city: // 城市；
                readyGo(this, SelectPlaceActivity.class);
                break;
            case R.id.iv_share:
                ShareDialogFragment sdf = ShareDialogFragment.getInstance("", "");
                sdf.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
        }
    }

    private void getData(boolean isRefresh) {
        swipeRefresh.setRefreshing(true);

//        new AsyncTask<String, Void, Map<String, String>>() {
//
//            protected Map<String, String> doInBackground(String... params) {
//                Map<String, Object> param = new HashMap<String, Object>();
//                Map<String, String> map = new HashMap<String, String>();
//                param.put("cityId", selectedCity.cityId);
//                map.put("serviceName", CardConstant.card_app_foodoverview);
//                map.put("data", JSON.toJSONString(param));
//                return ServiceDispatcher.call(MsglActivity.this, map);
//            }
//
//            @Override
//            protected void onPostExecute(Map<String, String> result) {
//                swipeRefresh.setRefreshing(false);
//
//                LogUtil.i("美食概览result:" + result);
//                // 成功；
//                if (null != result && "0000".equals(result.get("code"))) {
//                    adapter.clear();
//                    String data = result.get("data");
//
//                    if (!TextUtils.isEmpty(data)) {
//                        List<MsglBean> msglBeans = JSON.parseArray(data, MsglBean.class);
//                        if (msglBeans != null && msglBeans.size() > 0) {
//                            /**
//                             * 数组只有一项,取第一项；
//                             */
//                            adapter.setDataList(msglBeans.get(0));
//                            adapter.notifyDataSetChanged();
//                        } else {
//                            // 清空数据；
//                            adapter.setDataList(null);
//                            adapter.notifyDataSetChanged();
//                            ToastUtil.i(MsglActivity.this, getString(R.string.app_no_data));
//                        }
//                    }
//                } else {
//                    ToastUtil.i(MsglActivity.this, result.get("msg"));
//                }
//            }
//        }.executeOnExecutor(Executors.newCachedThreadPool());
    }

    private void initDataList(MsglBean msglBean) {
        if (msglBean != null) {
            adapter.setDataList(msglBean);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }
}
