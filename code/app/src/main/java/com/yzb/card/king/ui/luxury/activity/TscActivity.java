package com.yzb.card.king.ui.luxury.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.adapter.TscAdapter;
import com.yzb.card.king.ui.discount.bean.TscBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;


/**
 * 特色菜；
 */
public class TscActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private ListView listview;
    private TextView titlebarTitle;
    private ImageView ivShare;
    private SwipeRefreshLayout swipeRefresh;
    private TscAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsc);
        assignViews();
        getData(AppConstant.LOAD_DATA_REFRESH);
    }

    public void goBack(View view) {
        finish();
    }

    private void assignViews() {
        titlebarTitle = (TextView) findViewById(R.id.titlebar_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivShare.setOnClickListener(this);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        listview = (ListView) findViewById(R.id.listview);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new TscAdapter(this);

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share:
                ShareDialogFragment sdf = ShareDialogFragment.getInstance("", "");
                sdf.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
        }
    }

    private void getData(final int loadDataRefresh) {
//        new AsyncTask<String, Void, Map<String, String>>() {
//
//            protected Map<String, String> doInBackground(String... params) {
//                Map<String, Object> param = new HashMap<String, Object>();
//                Map<String, String> map = new HashMap<String, String>();
//                param.put("cityId", selectedCity.cityId);
//                map.put("serviceName", CardConstant.card_app_foodsignlist);
//                map.put("data", JSON.toJSONString(param));
//                return ServiceDispatcher.call(TscActivity.this, map);
//            }
//
//            @Override
//            protected void onPostExecute(Map<String, String> result) {
//                swipeRefresh.setRefreshing(false);
//                LogUtil.i("特色菜result:" + result);
//                // 成功；
//                if (null != result && "0000".equals(result.get("code"))) {
//                    String data = result.get("data");
//
//                    if (!TextUtils.isEmpty(data)) {
//                        List<TscBean> beans = JSON.parseArray(data, TscBean.class);
//                        if (beans != null && beans.size() > 0) {
//                            for (TscBean item : beans) {
//                                item.dishImage = ServiceDispatcher.getImageUrl(item.dishImage);
//                            }
//                            // 下拉刷新，需要提前清空数据；
//                            if (loadDataRefresh == 0) {
//                                adapter.clear();
//                            }
//                            refreshDataList(beans);
//                        }
//                    }
//                }
//            }
//        }.executeOnExecutor(Executors.newCachedThreadPool());
    }

    /**
     * 刷新数据；
     *
     * @param beans
     */
    private void refreshDataList(List<TscBean> beans) {
        if (beans == null || beans.size() == 0) return;
        adapter.appendDataList(beans);
    }

    @Override
    public void onRefresh() {
        getData(AppConstant.LOAD_DATA_REFRESH);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }
}
