package com.yzb.card.king.ui.luxury.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.adapter.MstdDetailAdapter;
import com.yzb.card.king.ui.discount.bean.MstdItemBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ViewUtil;

import org.xutils.x;

/**
 * 美食天地-详情；
 * created by  gengqiyun  on 2016.4.19
 */
public class MstdDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private ListView listview;
    private TextView titlebarTitle;
    private ImageView ivShare;

    private SwipeRefreshLayout swipeRefresh;
    private MstdDetailAdapter adapter;
    private float imgWhRate = 272 / 676.0f;
    private View headerView;
    private MstdItemBean recvMstdItemBean;
    private ExpandableTextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mstd_detail);
        assignViews();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(AppConstant.LOAD_DATA_REFRESH);
            }
        }, 200);
    }

    public void goBack(View view) {
        finish();
    }

    private void assignViews() {

        Bundle data = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        if (data != null) {
            recvMstdItemBean = (MstdItemBean) data.getSerializable("mstdItemBean");
        }

        titlebarTitle = (TextView) findViewById(R.id.titlebar_title);

        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivShare.setOnClickListener(this);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        listview = (ListView) findViewById(R.id.listview);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));

//        listview.setDivider(getResources().getDrawable(R.drawable.rect_divider));

        headerView = LayoutInflater.from(this).inflate(R.layout.listview_header_mstd_detail, null);
        initHeaderView();
        listview.addHeaderView(headerView);
        adapter = new MstdDetailAdapter(this);
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        adapter.setMeasure(screenWith, (int) (screenWith * imgWhRate + 0.5f));

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }

    private void initHeaderView() {
        ImageView iv = (ImageView) headerView.findViewById(R.id.iv);
        int imgWidth = CommonUtil.getScreenWidth(this) - CommonUtil.dip2px(this, 20);
        ViewUtil.set(iv, imgWidth, (int) (imgWidth * imgWhRate));
        tvDescription = (ExpandableTextView) headerView.findViewById(R.id.tv_description);
        tvDescription.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
            }
        });
        if (recvMstdItemBean != null) {
            titlebarTitle.setText(recvMstdItemBean.title);
            x.image().bind(iv,recvMstdItemBean.image);
            tvDescription.setText(recvMstdItemBean == null ? "" : "\u3000\u3000" + recvMstdItemBean.content);
        }
    }

    @Override
    public void onRefresh() {
        getData(AppConstant.LOAD_DATA_REFRESH);
    }

    private void getData(final int loadDataRefresh) {
        if (recvMstdItemBean == null || TextUtils.isEmpty(recvMstdItemBean.id)) return;
//
//        new AsyncTask<String, Void, Map<String, String>>() {
//
//            protected Map<String, String> doInBackground(String... params) {
//                Map<String, Object> param = new HashMap<String, Object>();
//                Map<String, String> map = new HashMap<String, String>();
//                param.put("topicId", recvMstdItemBean.id);
//                map.put("serviceName", CardConstant.card_app_foodtopicstorequery);
//                map.put("data", JSON.toJSONString(param));
//                LogUtil.i("美食天地提交的参数:" + map);
//
//                return ServiceDispatcher.call(MstdDetailActivity.this, map);
//            }
//
//            @Override
//            protected void onPostExecute(Map<String, String> result) {
//                swipeRefresh.setRefreshing(false);
//                LogUtil.i("美食天地result:" + result);
//                // 成功；
//                if (null != result && "0000".equals(result.get("code"))) {
//                    String data = result.get("data");
//
//                    if (!TextUtils.isEmpty(data)) {
//                        List<MstdDetailBean> beans = JSON.parseArray(data, MstdDetailBean.class);
//                        if (beans != null && beans.size() > 0) {
//                            for (MstdDetailBean item : beans) {
//                                item.storePhoto = ServiceDispatcher.url_image + "getImg/" + item.storePhoto + "/0";
//                            }
//                            // 下拉刷新，需要提前清空数据；
//                            if (loadDataRefresh == 0) {
//                                adapter.clear();
//                            }
//                            adapter.appendDataList(beans);
//                        }
//                    }
//                }
//            }
//        }.executeOnExecutor(Executors.newCachedThreadPool());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share:
                //分享；
                ShareDialogFragment sdf = ShareDialogFragment.getInstance("", "");
                sdf.show(getSupportFragmentManager(), "ShareDialogFragment");

//                UmsUtil.openShareBoad(this, "分享的title", "分享的content", "", "");
                break;
        }
    }

}
