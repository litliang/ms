package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelBean;
import com.yzb.card.king.bean.hotel.HotelThemeBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseFragmentActivity;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.hotel.adapter.HotelRecommedeyAdapter;
import com.yzb.card.king.ui.hotel.model.HotelRecommedeyDao;
import com.yzb.card.king.ui.hotel.persenter.HotelRecommedeyPersenter;
import com.yzb.card.king.ui.travel.adapter.SpaceItemDecoration;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.wallet.gridpasswordview.Util;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类  名：酒店推荐
 * 作  者：Li Yubing
 * 日  期：2016/7/14
 * 描  述：
 */
@ContentView(R.layout.hotel_recommedey_hotel)
public class HotelRecommedeyHotelActivity extends BaseFragmentActivity implements SwipeRefreshLayout.OnRefreshListener, BaseViewLayerInterface {
    //控件

    @ViewInject(R.id.tvTitle)
    private TextView titleName;

    @ViewInject(R.id.ivRight)
    private ImageView ivRight;


    @ViewInject(R.id.listView_credit_index)
    private HeadFootRecyclerView lvRecommedeyHotel;


    @ViewInject(R.id.pull_to_load_recommend)
    private SwipeRefreshLayout sRL;

    private int startPageSize = 0;

    private HotelThemeBean data;

    private HotelRecommedeyAdapter adapter;

    private List<HotelBean> list = new ArrayList<>();

    private HotelRecommedeyPersenter hotelPersenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();

        //接收intent传递的值
        if (getIntent().hasExtra("data")) {

            data = (HotelThemeBean) getIntent().getSerializableExtra("data");


            sendRecommendThemeListRequest(data.getThemeId() + "", 0 + "");
        }
    }

    /**
     * 发送推荐主题请求
     *
     * @param themeId
     */
    private void sendRecommendThemeListRequest(String themeId, String startpage)
    {

        hotelPersenter.getHotelRecommedey(themeId, startpage + "");
    }

    /**
     * 初始化信息
     */
    private void initData()
    {
        //初始化酒店推荐观察者
        hotelPersenter = new HotelRecommedeyPersenter(HotelRecommedeyHotelActivity.this);
        lvRecommedeyHotel.setLayoutManager(new GridLayoutManager(this,2));
        lvRecommedeyHotel.addItemDecoration(new SpaceItemDecoration(Util.dp2px(this,14)) );
        adapter = new HotelRecommedeyAdapter(this, list);
        lvRecommedeyHotel.setAdapter(adapter);
        lvRecommedeyHotel.setIsEnale(true);
        lvRecommedeyHotel.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener()
            {
                loadMore();
                lvRecommedeyHotel.setLoadMoreEnable(true);
                lvRecommedeyHotel.calByNewNum(list.size());
            }
        });


        adapter.setOnItemClickListener(new HotelRecommedeyAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int position)
            {
                Intent intent = new Intent(HotelRecommedeyHotelActivity.this, HotelProductInfoActivity.class);

                HotelBean bean = list.get(position);
                intent.putExtra("hotelId", bean.getHotelId());
                startActivity(intent);


            }
        });
    }

    /**
     * 加载更多
     */
    private void loadMore()
    {
        hotelPersenter.getLoadMoreRecommedey(data.getThemeId() + "", (startPageSize += AppConstant.MAX_PAGE_NUM) + "");
    }

    private void initView()
    {

        titleName.setText(R.string.hotel_recommendey_hotel);

        ivRight.setImageResource(R.mipmap.icon_share_gray);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ShareDialogFragment.getInstance("", "").show(getSupportFragmentManager(), "");
            }
        });

        SwipeRefreshSettings.setAttrbutes(this, sRL);
        sRL.setOnRefreshListener(this);


    }

    /**
     * 分享
     *
     * @param v
     */
    public void rightAciton(View v)
    {

        ShareDialogFragment dialogFragment = ShareDialogFragment.getInstance("", "");
        dialogFragment.show(getSupportFragmentManager(), "ShareDialogFragment");
    }


    /**
     * 刷新
     */
    @Override
    public void onRefresh()
    {
        sRL.setRefreshing(true);
        sendRecommendThemeListRequest(data.getThemeId() + "", 0 + "");
        lvRecommedeyHotel.setLoadMoreEnable(true);
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == HotelRecommedeyDao.HOTEL_RECOMMEDEY) {
            if (o instanceof List) {

                List<HotelBean> beans = (List<HotelBean>) o;
                sRL.setRefreshing(false);
                list.clear();
                list.addAll(beans);
                adapter.notifyDataSetChanged();
                Intent intent = new Intent("com.boardcast");
                intent.putExtra("themId", data.getThemeId());
                sendBroadcast(intent);
            }
        } else if (type == HotelRecommedeyDao.HOTEL_LOADMORE) {

            if (o instanceof List) {
                List<HotelBean> beanMore = (List<HotelBean>) o;
                sRL.setRefreshing(false);
                list.addAll(beanMore);
                adapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == HotelRecommedeyDao.HOTEL_RECOMMEDEY) {
            sRL.setRefreshing(false);
            if (o != null && o instanceof Map) {

                Map<String, String> onSuccessData = (Map<String, String>) o;

                String code = onSuccessData.get(HttpConstant.SERVER_CODE);

                if (code.equals(HttpConstant.NOINFO)) {


                } else {
                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
            }
        } else if (type == HotelRecommedeyDao.HOTEL_LOADMORE) {
            sRL.setRefreshing(false);
            adapter.notifyDataSetChanged();
            lvRecommedeyHotel.setLoadMoreEnable(false);
            if (o != null && o instanceof Map) {

                Map<String, String> onSuccessData = (Map<String, String>) o;

                String code = onSuccessData.get(HttpConstant.SERVER_CODE);

                if (code.equals(HttpConstant.NOINFO)) {


                } else {
                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
            }
        }
    }
}
