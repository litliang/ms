package com.yzb.card.king.ui.luxury.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.discount.adapter.MstdAdapter;
import com.yzb.card.king.ui.discount.bean.MstdItemBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
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
 * 美食天地；
 * created by  gengqiyun  on 2016.4.18
 */
public class MstdActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener, View.OnClickListener {
    private LinearLayout panelCity;
    private TextView tvCity;
    private ImageView arrowDownCity;

    private TextView titlebarTitle;
    private ImageView ivShare;
    private ListView listview;
    private SwipeRefreshLayout swipeRefresh;
    private MstdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mstd);
        assignViews();
        onRefresh();
    }

    public void goBack(View view) {
        finish();
    }

    private void assignViews() {
        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivShare.setOnClickListener(this);

        panelCity = (LinearLayout) findViewById(R.id.panel_city);
        panelCity.setOnClickListener(this);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvCity.setText(selectedCity.cityName);

        arrowDownCity = (ImageView) findViewById(R.id.arrow_down_city);

        titlebarTitle = (TextView) findViewById(R.id.titlebar_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        listview = (ListView) findViewById(R.id.listview);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new MstdAdapter(this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(this);
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

    private void getData(final boolean loadDataRefresh) {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share:
                ShareDialogFragment sdf = ShareDialogFragment.getInstance("", "");
                sdf.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
            case R.id.panel_city: // 城市；
                readyGo(this, SelectPlaceActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MstdItemBean itemBean = adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mstdItemBean", itemBean);
        readyGoWithBundle(this, MstdDetailActivity.class, bundle);
    }


    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }
}
