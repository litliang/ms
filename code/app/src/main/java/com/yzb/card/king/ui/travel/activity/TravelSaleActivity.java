package com.yzb.card.king.ui.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.travel.adapter.SaleAdapter;
import com.yzb.card.king.ui.travel.adapter.SpaceItemDecoration;
import com.yzb.card.king.ui.travel.bean.TravelSaleListBean;
import com.yzb.card.king.ui.travel.model.ITravelSale;
import com.yzb.card.king.ui.travel.presenter.TravelSalePresenter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游特卖会
 */
public class TravelSaleActivity extends BaseActivity implements View.OnClickListener,
		SwipeRefreshLayout.OnRefreshListener,BaseViewLayerInterface {

	private HeadFootRecyclerView travelSale;

	private SaleAdapter adapter;

	private LinearLayout iv_back;

	//设置标题
	private TextView tx_title;

	private SwipeRefreshLayout swipeRefresh;

	private TravelSalePresenter presenter;

	private String content;

	private int saleId;

	private int pageStart = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_sale);
		getIntentInfo();
		init();
		getData();
	}

	private void getIntentInfo() {
		Intent i = getIntent();
		if (i != null) {
			content = i.getStringExtra("content");
			saleId = i.getIntExtra("saleId", 0);
		}
	}

	private void init() {
		presenter = new TravelSalePresenter(this);
		swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
		SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
		swipeRefresh.setOnRefreshListener(this);
		travelSale = (HeadFootRecyclerView) findViewById(R.id.gridView_sale);
		iv_back = (LinearLayout) findViewById(R.id.iv_back);
		tx_title = (TextView) findViewById(R.id.tx_title);

		tx_title.setText(content);


		iv_back.setOnClickListener(this);
		adapter = new SaleAdapter(this);
		travelSale.setAdapter(adapter);
		travelSale.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));

		travelSale.addItemDecoration(new SpaceItemDecoration(CommonUtil.dip2px(this,20)));

		travelSale.setIsEnale(true);
		travelSale.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
			@Override
			public void loadMoreListener() {
				loadMore();
			}
		});
		adapter.setOnItemClickListener(new SaleAdapter.OnItemClickListener() {
			@Override
			public void setOnItemClickListener(int postion) {
				Intent intent = new Intent(TravelSaleActivity.this, TravelProductDetailActivity.class);
				intent.putExtra("id", adapter.saleListBeen.get(postion).getGoodsId() + "");
				startActivity(intent);
			}
		});
	}

	/**
	 * 上拉加载更多
	 */
	private void loadMore() {
		Map<String, Object> param = new HashMap<>();
		param.put("themeId", saleId);
		param.put("pageStart", pageStart);
		param.put("pageSize", AppConstant.MAX_PAGE_NUM);
		presenter.getSaleList(param, CardConstant.TRAVEL_SALE_LIST, ITravelSale.SALE_INFO_MORE);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_back:
				finish();
				break;
		}
	}

	@Override
	public void onRefresh() {
		getData();
	}

	/**
	 * 获取特卖会信息
	 */
	private void getData() {
		swipeRefresh.setRefreshing(true);
		pageStart = 0;
		Map<String, Object> param = new HashMap<>();
		param.put("themeId", saleId);
		param.put("pageStart", pageStart);
		param.put("pageSize", AppConstant.MAX_PAGE_NUM);
		presenter.getSaleList(param, CardConstant.TRAVEL_SALE_LIST, ITravelSale.SALE_INFO);

	}

	@Override
	public void callSuccessViewLogic(Object o, int type)
	{
		if (type == ITravelSale.SALE_INFO) //获取特卖会信息
		{
			List<TravelSaleListBean> saleListBeen = JSON.parseArray(String.valueOf(o), TravelSaleListBean.class);
			if (saleListBeen.size() == AppConstant.MAX_PAGE_NUM) {
				pageStart = adapter.getItemCount();
				travelSale.notifyData();
			} else {
				travelSale.calByNewNum(saleListBeen.size());
			}
			adapter.appendData(saleListBeen);
			swipeRefresh.setRefreshing(false);
		} else if (type == ITravelSale.SALE_INFO_MORE) // 上拉加载更多
		{
			List<TravelSaleListBean> saleListBeen = JSON.parseArray(String.valueOf(o), TravelSaleListBean.class);
			adapter.addNewData(saleListBeen);
			if (saleListBeen.size() == AppConstant.MAX_PAGE_NUM) {
				pageStart = adapter.getItemCount();
				travelSale.notifyData();
			} else {
				travelSale.calByNewNum(saleListBeen.size());

			}
		}
	}

	@Override
	public void callFailedViewLogic(Object o, int type)
	{
		if (!TextUtils.isEmpty(o + "")) {
			if (pageStart == 0) {

				swipeRefresh.setRefreshing(false);

			} else {

				travelSale.calByNewNum(0);
			}
		}
	}
}
