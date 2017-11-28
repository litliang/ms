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
import com.yzb.card.king.ui.discount.adapter.JdmsAdapter;
import com.yzb.card.king.ui.discount.bean.JdmsBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 景点美食；
 * created by gengqiyun  on 2016.4.19
 */
public class JdmsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, AdapterView.OnItemClickListener {
    private LinearLayout panelCity;
    private TextView tvCity;
    private ImageView arrowDownCity;
    private TextView titlebarTitle;

    private ListView listview;
    private ImageView ivShare;
    private SwipeRefreshLayout swipeRefresh;
    private JdmsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdms);
        assignViews();
        onRefresh();
    }

    public void goBack(View view) {
        finish();
    }

    private void assignViews() {

        panelCity = (LinearLayout) findViewById(R.id.panel_city);
        panelCity.setOnClickListener(this);
        tvCity = (TextView) findViewById(R.id.tv_city);

        tvCity.setText(selectedCity.cityName);

        arrowDownCity = (ImageView) findViewById(R.id.arrow_down_city);

        titlebarTitle = (TextView) findViewById(R.id.titlebar_title);

        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivShare.setOnClickListener(this);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        listview = (ListView) findViewById(R.id.listview);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        listview.setDivider(getResources().getDrawable(R.drawable.rect_divider_jdms));
        listview.setDividerHeight(CommonUtil.dip2px(this, 10));

        adapter = new JdmsAdapter(this);
        listview = (ListView) findViewById(R.id.listview);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        JdmsBean jdmsBean = adapter.getItem(position);
        if (jdmsBean == null) {
            return;
        }
//        bundle.putString("spotId", jdmsBean.id);
//        bundle.putString("lat", jdmsBean.lat + "");
//        bundle.putString("lng", jdmsBean.lng + "");
//        bundle.putString("typeParentId", "1");
//        bundle.putString("typeName", jdmsBean.spotName);
//        readyGoWithBundle(this, MsMoreActivity.class, bundle);
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
//                UmsUtil.openShareBoad(this, "分享的title", "分享的content", "", "");
                break;
        }
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

}
