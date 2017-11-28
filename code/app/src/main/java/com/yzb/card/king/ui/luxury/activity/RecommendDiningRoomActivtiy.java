package com.yzb.card.king.ui.luxury.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.baidu.mapapi.model.LatLng;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.food.FoodThemeBean;
import com.yzb.card.king.bean.food.ThemeDiningRoomBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseFragmentActivity;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.AppUtils;
import com.yzb.wallet.gridpasswordview.Util;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：推荐餐厅
 * 作  者：Li Yubing
 * 日  期：2016/7/18
 * 描  述：
 */
@ContentView(R.layout.activity_recommend_dining_room)
public class RecommendDiningRoomActivtiy extends BaseFragmentActivity implements AbPullToRefreshView.OnFooterLoadListener, AbPullToRefreshView.OnHeaderRefreshListener {


    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.ivRight)
    private ImageView ivRight;

    @ViewInject(R.id.tvRight)
    private TextView tvRight;

    @ViewInject(R.id.listView_credit_index)
    private ListView lvRecommedeyHotel;


    @ViewInject(R.id.pull_to_load_recommend)
    private AbPullToRefreshView pull_to_load_recommend;

    private int startPageSize;

    private FoodThemeBean data;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initData();

        if (getIntent().hasExtra("data")) {

            data = (FoodThemeBean) getIntent().getSerializableExtra("data");

            ProgressDialogUtil.getInstance().showProgressDialog(this, false);

            startPageSize = 0;

            sendRecommendThemeListRequest(data.getThemeId(), startPageSize);
        }
    }

    /**
     * 发送推荐主题请求
     *
     * @param themeId
     * @param startSize1
     */
    private void sendRecommendThemeListRequest(String themeId, int startSize1) {



        SimpleRequest<List<ThemeDiningRoomBean>> request = new SimpleRequest<List<ThemeDiningRoomBean>>(CardConstant.THEME_FOOD_LIST_URL)
        {
            @Override
            protected List<ThemeDiningRoomBean> parseData(String data)
            {
                return JSON.parseArray(data, ThemeDiningRoomBean.class);
            }
        };
        final Map<String, Object> params = new HashMap<>();
        params.put("themeId", themeId);

        params.put("pageStart",startSize1);

        params.put("pageSize", AppConstant.MAX_PAGE_NUM+"");
        request.setParam(params);
        request.sendRequestNew(new BaseCallBack<List<ThemeDiningRoomBean>>()
        {
            @Override
            public void onSuccess(List<ThemeDiningRoomBean> list)
            {

                pull_to_load_recommend.onFooterLoadFinish();
                pull_to_load_recommend.onHeaderRefreshFinish();

                // pull_to_load_recommend.setLoadMoreEnable(false);

                View foodView =  pull_to_load_recommend.getFooterView();

                if(list.size()==AppConstant.MAX_PAGE_NUM){

                    foodView .setVisibility(View.VISIBLE);
                }else {

                    if(foodView.getVisibility() == View.VISIBLE){

                        foodView .setVisibility(View.GONE);
                    }

                }


            }

            @Override
            public void onFail(Error error)
            {
                ProgressDialogUtil.getInstance().closeProgressDialog();
                pull_to_load_recommend.onHeaderRefreshFinish();
                pull_to_load_recommend.onFooterLoadFinish();
                if (startPageSize == 0) {


                } else {

                    startPageSize = startPageSize - AppConstant.MAX_PAGE_NUM;


                }
            }
        });




    }


    private void initData() {

        adapter = new MyAdapter();

        lvRecommedeyHotel.setAdapter(adapter);
    }

    private void initView() {

        titleName.setText(R.string.food_recommend_dining_room);

        ivRight.setImageResource(R.mipmap.icon_share_white);

        tvRight.setVisibility(View.GONE);

        pull_to_load_recommend.setOnHeaderRefreshListener(this);
        pull_to_load_recommend.setOnFooterLoadListener(this);

        lvRecommedeyHotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                //门店的id；
                bundle.putString("id", ((ThemeDiningRoomBean) adapter.getItem(position)).getStoreId());

                Intent it = new Intent(RecommendDiningRoomActivtiy.this,MsDetailActivity.class);

                it.putExtra(AppConstant.INTENT_BUNDLE, bundle);

                startActivity(it);


            }
        });
    }


    public void rightAciton(View v) {


        ShareDialogFragment dialogFragment = ShareDialogFragment.getInstance("", "");
        dialogFragment.show(getSupportFragmentManager(), "ShareDialogFragment");
    }


    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {

        if (data != null) {


            startPageSize  = startPageSize +AppConstant.MAX_PAGE_NUM;

            sendRecommendThemeListRequest(data.getThemeId(), startPageSize);
        } else {

            pull_to_load_recommend.onFooterLoadFinish();
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {

        if (data != null) {

            startPageSize = 0;

            sendRecommendThemeListRequest(data.getThemeId(), startPageSize);

        } else {
            pull_to_load_recommend.onHeaderRefreshFinish();

        }

    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        private int llHeight;

        private List<ThemeDiningRoomBean> list = new ArrayList<ThemeDiningRoomBean>();

        private String adapterStr;

        public MyAdapter() {

            layoutInflater = LayoutInflater.from(RecommendDiningRoomActivtiy.this);

            int screenWidth = getResources().getDisplayMetrics().widthPixels;

            llHeight = (screenWidth - Util.dp2px(RecommendDiningRoomActivtiy.this, 15)) / 2;

            adapterStr = getString(R.string.food_people_kilometre);
        }

        public void addNewData(List<ThemeDiningRoomBean> list) {

            this.list.clear();

            this.list.addAll(list);

            notifyDataSetChanged();

        }

        public void addMoreData(List<ThemeDiningRoomBean> list) {


            this.list.addAll(list);

            notifyDataSetChanged();

        }

        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHold viewHold;

            if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.food_adapter_recommendey_hotel, null);

                viewHold = new ViewHold(convertView);

                convertView.setTag(viewHold);
            } else {

                viewHold = (ViewHold) convertView.getTag();
            }

            ThemeDiningRoomBean bean = (ThemeDiningRoomBean) getItem(position);

            if (!TextUtils.isEmpty(bean.getStoreRemark())) {

                viewHold.tvHotelDesc.setText(bean.getStoreRemark());
            }

            if( !TextUtils.isEmpty(bean.getChannelName())){

                viewHold.tvCuisine.setText(bean.getChannelName());

            }

            if (!TextUtils.isEmpty(bean.getHotelName())) {

                viewHold.tvHotelName.setText(bean.getHotelName());
            }

            if (!TextUtils.isEmpty(bean.getExteriorImage())) {

                x.image().bind(viewHold.ivOne,bean.getExteriorImage());
            }

            if (!TextUtils.isEmpty(bean.getHallImage())) {

                x.image().bind(viewHold.ivTwo,bean.getHallImage());
            }

            if (!TextUtils.isEmpty(bean.getInteriorImage())) {

                x.image().bind(viewHold.ivThree,bean.getInteriorImage());
            }

            if (!TextUtils.isEmpty(bean.getAvgPrice())) {

                String temp = adapterStr.replace("#", bean.getAvgPrice() + "");

                LatLng llB = new LatLng(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng()));

                double distanceD = AppUtils.calculateDistance(GlobalApp.getCurLocation(),llB);

                BigDecimal b = new BigDecimal(distanceD/1000 );

                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                temp = temp.replace("$", f1+ "");

                viewHold.tvDrDistanceMoney.setText(temp);
            }
            return convertView;
        }

        class ViewHold {

            LinearLayout llImageAtlas;

            TextView tvHotelDesc;

            TextView tvHotelName;

            ImageView ivOne, ivTwo, ivThree;

            TextView tvDrDistanceMoney;//

            TextView tvCuisine;

            public ViewHold(View convertView) {


                llImageAtlas = (LinearLayout) convertView.findViewById(R.id.llImageAtlas);

                tvHotelDesc = (TextView) convertView.findViewById(R.id.tvHotelDesc);

                tvHotelName = (TextView) convertView.findViewById(R.id.tvHotelName);

                ivOne = (ImageView) convertView.findViewById(R.id.ivOne);

                ivTwo = (ImageView) convertView.findViewById(R.id.ivTwo);

                ivThree = (ImageView) convertView.findViewById(R.id.ivThree);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llImageAtlas.getLayoutParams();

                lp.height = llHeight;

                llImageAtlas.setLayoutParams(lp);


                LinearLayout.LayoutParams lpivOne = (LinearLayout.LayoutParams) ivOne.getLayoutParams();

                lpivOne.height = llHeight;

                ivOne.setLayoutParams(lpivOne);

                LinearLayout.LayoutParams lpivTwo = (LinearLayout.LayoutParams) ivTwo.getLayoutParams();

                lpivTwo.height = llHeight / 2;

                ivTwo.setLayoutParams(lpivTwo);

                LinearLayout.LayoutParams lpivThree = (LinearLayout.LayoutParams) ivThree.getLayoutParams();

                lpivThree.height = llHeight / 2;

                ivThree.setLayoutParams(lpivThree);

                tvDrDistanceMoney = (TextView) convertView.findViewById(R.id.tvDrDistanceMoney);

                tvCuisine = (TextView) convertView.findViewById(R.id.tvCuisine);
            }
        }
    }
}
