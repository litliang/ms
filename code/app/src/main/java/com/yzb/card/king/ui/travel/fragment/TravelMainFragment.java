package com.yzb.card.king.ui.travel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.CycleTextView;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.SlideShow2ItemView;
import com.yzb.card.king.ui.appwidget.SlideShow8ItemView;
import com.yzb.card.king.ui.base.BaseCityChangeFragment;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.bean.TextCycleItem;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.ui.luxury.activity.ChannelActivity;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.travel.activity.DestinationSearchActivity;
import com.yzb.card.king.ui.travel.activity.TravelHeightSeachActivity;
import com.yzb.card.king.ui.travel.activity.TravelLineListActivity;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.ui.travel.activity.TravelSaleActivity;
import com.yzb.card.king.ui.travel.adapter.TravelMainAdapter;
import com.yzb.card.king.ui.travel.bean.TravelFxbBean;
import com.yzb.card.king.ui.travel.bean.TravelSearchCriteriaBean;
import com.yzb.card.king.ui.travel.model.TravelMainDao;
import com.yzb.card.king.ui.travel.presenter.TravelMainPresenter;
import com.yzb.card.king.ui.travel.view.TravelMainView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ViewUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游主页Fragment；
 *
 * @author gengqiyun
 */

@ContentView(R.layout.fragment_travel_main)
public class TravelMainFragment extends BaseCityChangeFragment implements View.OnClickListener,
		SwipeRefreshLayout.OnRefreshListener, AdapterItemClickCallBack, TravelMainView {

	private TravelMainAdapter adapter;

	@ViewInject(R.id.swipeRefresh)
	private SwipeRefreshLayout swipeRefresh;

	@ViewInject(R.id.scrollView)
	private ScrollView scrollView;
	@ViewInject(R.id.gridView)
	private HeadFootRecyclerView listView;
	@ViewInject(R.id.slideShowView1)
	private SlideShow1ItemView slideShowView1;
	@ViewInject(R.id.slideShowView2)
	private SlideShow8ItemView slideShowView2;
	@ViewInject(R.id.slideShowView3)
	private SlideShow2ItemView slideShowView3;

	@ViewInject(R.id.cycleTextView)
	private CycleTextView cycleTextView;

	@ViewInject(R.id.dingwei_img)
	private LinearLayout llDingWeiImg;

	@ViewInject(R.id.rl_dep)
	private RelativeLayout rlDep;

	@ViewInject(R.id.rl_arr)
	private RelativeLayout rlArr;

	@ViewInject(R.id.tv_dep)
	private TextView tvDep;

	@ViewInject(R.id.tv_arr)
	private TextView tvArr;

	@ViewInject(R.id.rl_hig)
	private RelativeLayout rlHig;

	@ViewInject(R.id.searchTravel)
	private Button btnSeachTravel;

	@ViewInject(R.id.llDelAddress)
	private LinearLayout llDelAddress;

	private ChannelMainActivity activity;

	private int pageStart = 0;

	private FilterTwoBean filterTwoBean;

	private TravelSearchCriteriaBean bean; //旅游首页传入列表的参数
	//常量
	private float slide1_whrate = 432 / 1080.0f;

	private String typeGrandParentId = AppConstant.travel_id;

	private TravelMainPresenter travelMainPresenter;

	public TravelMainFragment() {
	}

	public static TravelMainFragment newInstance(String param1, String param2) {
		TravelMainFragment fragment = new TravelMainFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		travelMainPresenter = new TravelMainPresenter(this);
		init(view);
		getData(true, "onViewCreated");
	}


	private void init(View view) {
		if (view == null) return;
		activity = (ChannelMainActivity) getActivity();

		scrollView.smoothScrollTo(0, 0);

		SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);
		swipeRefresh.setOnRefreshListener(this);

		listView.setFocusable(false);
		findViewFromHeader(view);

		adapter = new TravelMainAdapter(getActivity());

		adapter.setAdapterItemClickCallBack(this);

		listView.setAdapter(adapter);

		GridLayoutManager manager = new GridLayoutManager(getContext(), 2);

		listView.setLayoutManager(manager);

		listView.setIsEnale(true);

		listView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
			@Override
			public void loadMoreListener() {
				loadMore();
			}
		});

		rlDep.setOnClickListener(this);

		rlArr.setOnClickListener(this);

		rlHig.setOnClickListener(this);

		cycleTextView.setOnClickListener(this);

		rlHig.setOnClickListener(this);

		btnSeachTravel.setOnClickListener(this);

		llDingWeiImg.setOnClickListener(this);

		bean = new TravelSearchCriteriaBean();

		cycleTextView.setTitle(getString(R.string.travel_sell_privilege));

		cycleTextView.setOnArrowClickListener(new CycleTextView.OnArrowClickListener() {
			@Override
			public void onClick(TextCycleItem item) {
				Intent intent = new Intent(getActivity(), TravelSaleActivity.class);
				intent.putExtra("content", item.getThemeName());
				intent.putExtra("saleId", item.getThemeId());
				startActivity(intent);
			}
		});


		String cityId = activity.returnCityId();
		if (TextUtils.isEmpty(cityId)) {
			tvDep.setText(getResources().getString(R.string.setting_locate_fail));
		} else {
			String cityNa = activity.returnCityName();
			tvDep.setText(cityNa);
			bean.setStarCity(cityId);
		}

		llDelAddress.setOnClickListener(this);

	}

	/**
	 * 加载更多风向标
	 */
	private void loadMore() {
		Map<String, Object> param = new HashMap<>();
		param.put("pageStart", pageStart);
		param.put("pageSize", AppConstant.MAX_PAGE_NUM);
		travelMainPresenter.sendTravelFxbRequest(param, CardConstant.TRAVEL_FXB_LIST, TravelMainDao.TRAVELFXB_MORE_CODE);
	}

	private void findViewFromHeader(View view) {
		slideShowView1 = (SlideShow1ItemView) view.findViewById(R.id.slideShowView1);
		slideShow1ItemViewSetings();

		slideShowView2 = (SlideShow8ItemView) view.findViewById(R.id.slideShowView2);
		SlideShow8ItemViewSetings();

		slideShowView3 = (SlideShow2ItemView) view.findViewById(R.id.slideShowView3);
		slideShow2ItemViewSetings();

	}

	@Override
	public void onCityChange(Location city) {
		super.onCityChange(city);
		cityId = city.cityId;
		getData(true, "onCityChange");
	}

	private void getData( boolean loadDataRefresh, final String str) {

		slideShowView1.setParam(AppConstant.TRAVEL_HOMEPAGE, cityId, typeGrandParentId);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!"onCityChange".equals(str)) {
					swipeRefresh.setRefreshing(true);
				}
				// 本地json格式的子分类；
				String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(),AppConstant.sp_childtypelist_travel, "[]");

				List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);

//                travelMainPresenter.sendUserChannelRequest();
				//特卖会主题
				travelMainPresenter.sendSpecialSaleRequest();
				//旅游风向标
				pageStart = 0;
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("pageStart", pageStart);
				param.put("pageSize", AppConstant.MAX_PAGE_NUM);
				travelMainPresenter.sendTravelFxbRequest(param, CardConstant.TRAVEL_FXB_LIST, TravelMainDao.TRAVELFXB_CODE);

			}
		}, 200);
	}


	/**
	 * 获取当前位置信息
	 */
	private void currentLocation() {
		GlobalApp.getInstance().startLocate();
		GlobalApp.getInstance().setOnCityChangeListeners(new GlobalApp.OnCityChangeListener() {
			@Override
			public void onCityChange(Location city) {
				if (TextUtils.isEmpty(city.address)) {
					UiUtils.shortToast(R.string.can_not_get_location);
				} else {
					cityName = city.cityName;
					tvDep.setText(cityName);
					bean.setStarCity(activity.returnCityId());
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		getData(true, "onRefresh");
	}

	/**
	 * 顶部轮播设置；
	 */
	private void slideShow1ItemViewSetings() {
		if (slideShowView1 == null) return;
		int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        ViewUtil.set(slideShowView1,
				screenWith,
				(int) (screenWith * slide1_whrate + 0.5));
	}

	private void SlideShow8ItemViewSetings() {
		if (slideShowView2 == null) return;
		slideShowView2.setFragmentManager(getChildFragmentManager());
	}

	private void slideShow2ItemViewSetings() {
		if (slideShowView3 == null) return;
		slideShowView3.setFragmentManager(getChildFragmentManager());
		slideShowView3.initDataList();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_edit: // 编辑按钮；

				Intent intent = new Intent(getActivity(), ChannelActivity.class);

				Bundle bundle = new Bundle();

				bundle.putString("typeParentId", typeGrandParentId);

				bundle.putString(ChannelActivity.SP_KEY, AppConstant.sp_childtypelist_travel);

				bundle.putString("category", AppConstant.discount_channel_category);

				intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);

				startActivityForResult(intent, 100);

				break;
			case R.id.rl_hig: //高级搜索

				Intent hight = new Intent(getActivity(), TravelHeightSeachActivity.class);

				hight.putExtra("searchBean", bean);

				startActivity(hight);

				break;
			case R.id.rl_dep:  //出发地
				goPlace();
				break;
			case R.id.rl_arr:   //目的地
				Intent intent1 = new Intent(getActivity(), DestinationSearchActivity.class);
				intent1.putExtra("category", filterTwoBean);
				intent1.putExtra("industryId", AppConstant.travel_id);
				startActivityForResult(intent1, 0);
				break;
			case R.id.searchTravel:   //搜索
				if (tvDep.getText().toString().equals(getResources().getString(R.string.setting_locate_fail))) {
					ToastUtil.i(getContext(), getResources().getString(R.string.setting_locate_not));
					return;
				}
				Intent intentS = new Intent(getActivity(), TravelLineListActivity.class);
				intentS.putExtra("searchBean", bean);
				startActivity(intentS);
				break;
			case R.id.dingwei_img: //定位
				currentLocation();
				break;
			case R.id.llDelAddress:
				llDelAddress.setVisibility(View.INVISIBLE);
				tvArr.setText("");
				bean.setEndCity( "");
				bean.setEndCityType("");
				filterTwoBean = null;
				break;
		}
	}

	/**
	 * 选择出发地
	 */
	private void goPlace() {
		Intent i = new Intent();
		i.setClass(getActivity(), SelectPlaceActivity.class);
		startActivityForResult(i,9999);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {


		switch (resultCode) {
			case ChannelActivity.SAVE_CHNNEL_OK:
				String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(), AppConstant.sp_childtypelist_travel, "[]");
				slideShowView2.setDataList(JSON.parseArray(childTypeJson, ChildTypeBean.class), 1);
				break;
		}
		if (requestCode == 0 && resultCode == Activity.RESULT_OK)//目的地选择返回结果
		{
			Serializable serialObj = data.getSerializableExtra("backData");
			if (serialObj != null && serialObj instanceof FilterTwoBean) {
				filterTwoBean = (FilterTwoBean) serialObj;
				FilterBean filterBean = filterTwoBean.getSelectedFilterEntity();
				if (filterBean != null) {
					LogUtil.i("JsonCity===" + JSON.toJSONString(filterBean));
					tvArr.setText(filterBean.getObjName());
					bean.setEndCity(filterBean.getObjId() + "");
					bean.setEndCityType(filterBean.getType());
					llDelAddress.setVisibility(View.VISIBLE);
				}
			}
		}


		if(requestCode== 9999){
			if (CitySelectManager.getInstance().isLoadPlaceFlag()) {
				activity.moreFunctionPublicTitleView.updateAppCity();
				cityName = CitySelectManager.getInstance().getPlaceName();
				City city = (City) CitySelectManager.getInstance().getPlace();
				if (city != null) {
					tvDep.setText(city.getCityName());
					cityId = CitySelectManager.getInstance().getPlaceId();
					bean.setStarCity(city.getCityId());
//                getData(true);
					CitySelectManager.getInstance().clearData();
				}
			}
		}
	}

	/**
	 * 风向标点击
	 *
	 * @param args
	 */
	@Override
	public void itemClickCallBack(Object... args) {
		if (args == null || args.length == 0 || (int) args[0] < 0) {
			return;
		}
		int position = (int) args[0];
		Intent intent = new Intent(getActivity(), TravelProductDetailActivity.class);
		intent.putExtra("id", adapter.getItem(position).getProductId() + "");
		startActivity(intent);
	}


	@Override
	public void transmitChannelData(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList) {
		swipeRefresh.setRefreshing(false);
		if (displayChannelList != null) {
			displayChannelList.addAll(hideChannelList);
			String localJson = SharePrefUtil.getValueFromSp(GlobalApp.getInstance(),
					AppConstant.sp_childtypelist_travel, "[]");
			List<ChildTypeBean> localLists = JSON.parseArray(localJson, ChildTypeBean.class);
			localLists = CommonUtil.filterList(localLists, displayChannelList);
			// 持久化到本地
			SharePrefUtil.saveToSp(getActivity(), AppConstant.sp_childtypelist_travel, JSON.toJSONString(localLists));
			slideShowView2.setDataList(localLists, AppConstant.travel_parent_id);
		}
	}

	@Override
	public void callSuccessViewLogic(Object o, int type)
	{
		swipeRefresh.setRefreshing(false);

		if (type == TravelMainDao.SPECIALSALE_CODE) //特卖会主题
		{

			List<TextCycleItem> cycleTextList = (List<TextCycleItem>) o;
			cycleTextView.setList(cycleTextList);

		} else if (type == TravelMainDao.TRAVELFXB_CODE) //风向标
		{
			scrollView.smoothScrollTo(0, 0);
			List<TravelFxbBean> travelBeans = JSON.parseArray(String.valueOf(o), TravelFxbBean.class);
			if (travelBeans.size() == AppConstant.MAX_PAGE_NUM) {
				pageStart = adapter.getItemCount();
				listView.notifyData();
			} else {
				listView.calByNewNum(travelBeans.size());
			}

			adapter.appendDataList(travelBeans);
			swipeRefresh.setRefreshing(false);
		} else if (type == TravelMainDao.TRAVELFXB_MORE_CODE) //加载更多风向标
		{
			List<TravelFxbBean> saleListBeen = JSON.parseArray(String.valueOf(o), TravelFxbBean.class);
			adapter.newDataList(saleListBeen);
			if (saleListBeen.size() == AppConstant.MAX_PAGE_NUM) {
				pageStart = adapter.getItemCount();
				listView.notifyData();
			} else {
				listView.calByNewNum(saleListBeen.size());
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

				listView.calByNewNum(0);
			}
		}
	}
}
