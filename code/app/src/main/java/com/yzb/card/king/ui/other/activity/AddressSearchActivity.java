package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.other.adapter.AddressAdapter;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址搜索
 */
public class AddressSearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher
{

    private EditText searchTxt = null;

    private HeadFootRecyclerView addressListView = null;
    private List<Map> data = null;
    private AddressAdapter addressAdapter = null;
    private SwipeRefreshLayout srl;

    private String sourceType = ""; //请求来源
    private String cityId = "";     //城市id
    private String city = "";       //城市名
    private LinearLayout searchBtn;
    String tempKeywords = "";
    private int pageIndex = 0;

    // 百度地图POISearch
    private PoiSearch poiSearch;
    private boolean isLoadMore = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search_main);

        initView();

        initData();

    }

    private void initData()
    {

        sourceType = getIntent().getStringExtra("source");

        //获取城市Id
        if (getIntent().hasExtra("cityId"))
        {
            cityId = getIntent().getStringExtra("cityId");
        }
        //获取城市名
        if (getIntent().hasExtra("city"))
        {
            city = getIntent().getStringExtra("city");
        }

    }


    public void initView()
    {

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

        findViewById(R.id.llBack).setOnClickListener(this);
        searchBtn = (LinearLayout) findViewById(R.id.llSearch);
        searchBtn.setOnClickListener(this);

        searchTxt = (EditText) findViewById(R.id.searchTxt);
        searchTxt.addTextChangedListener(this);
        ImageView deleteImg = (ImageView) findViewById(R.id.deleteImg);
        deleteImg.setOnClickListener(this);

        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        SwipeRefreshSettings.setAttrbutes(this, srl);
        srl.setOnRefreshListener(onRefreshListener);

        data = new ArrayList<Map>();
        addressListView = (HeadFootRecyclerView) this.findViewById(R.id.addressListView);
        addressAdapter = new AddressAdapter(data, this);
        addressListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL
                , false));
        addressListView.setIsEnale(true);
        addressListView.setOnLoadMoreListener(onLoadMoreListener);
        addressAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                //AddressSearchManager.getInstance().setAddrId(String.valueOf(map.get("id")));

                AddressSearchManager.getInstance().setAddrName(
                        String.valueOf(data.get(position).get("name")));

                AddressSearchManager.getInstance().setStreet(
                        String.valueOf(data.get(position).get("street")));


                Intent resultIntent = new Intent();
                resultIntent.putExtra("addressName", String.valueOf(data.get(position).get("street")));
                setResult(1, resultIntent);
                AddressSearchActivity.this.finish();
            }
        });
        addressListView.setAdapter(addressAdapter);
    }

    /**
     * 下拉刷新
     */
    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            isLoadMore = false;
            pageIndex = 0;
            AddressSearchActivity.this.onRefresh();
        }
    };

    /**
     * 加载更多监听事件
     */
    HeadFootRecyclerView.OnLoadMoreListener onLoadMoreListener = new HeadFootRecyclerView.OnLoadMoreListener()
    {
        @Override
        public void loadMoreListener()
        {
            pageIndex++;
            loadMore(pageIndex);
        }
    };

    /**
     * POI检索结果
     */
    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener()
    {
        @Override
        public void onGetPoiResult(PoiResult poiResult)
        {
            ProgressDialogUtil.getInstance().closeProgressDialog();
            if (poiResult.error != SearchResult.ERRORNO.NO_ERROR)
            {
                addressListView.notifyData();
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR)
            {

                List<Map> list = new ArrayList<Map>();
                Map<String, Object> map;
                for (PoiInfo p : poiResult.getAllPoi())
                {
                    map = new HashMap<>();
                    // poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路,
                    if (p.type.getInt() == 0)
                    {
//                        map.put("type", p.type.getInt());
                        map.put("name", p.name);
                        map.put("street", p.address);
                    }
                    list.add(map);
                }
                Map<String, String> result = new HashMap<>();
                result.put("code", "0000");
                result.put("data", JSON.toJSONString(list));
                List<Map> list1 = JSON.parseArray(result.get("data"), Map.class);
                if (!isLoadMore)
                {
                    data.clear();
                }
                data.addAll(list1);
                addressListView.notifyData();
            }
            srl.setRefreshing(false);

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult)
        {
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult)
        {

        }
    };


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.llBack:
                back();
                break;
            case R.id.deleteImg:
                searchTxt.setText("");
                break;
            case R.id.llSearch:
                search();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        back();
        super.onBackPressed();
    }

    public void back()
    {
        GlobalApp.getInstance().startLocate();
        AddressSearchManager.getInstance().clearData();
        AddressSearchManager.getInstance().setIfSelectAddress(false);
        finish();
    }

    private void search()
    {

        if (StringUtils.isEmpty(searchTxt.getText().toString().trim()))
        {
            return;
        }
        ProgressDialogUtil.getInstance().showProgressDialogMsg("正在加载", this, true);

        if (StringUtils.isEmpty(sourceType))
            return;


        addressAdapter.setSearchParam(searchTxt.getText().toString());

        if ("address".equals(sourceType) || "noGPS".equals(sourceType) || "terminal".equals(sourceType))
        {
            if (checkKeywordsAndSearch()) return;
        }

    }

    /**
     * 检查搜索关键字是否为空 为空返回 不为空继续搜索
     * 检查本次搜索关键字是否和上次一样 如果一样 返回
     *
     * @return
     */
    private boolean checkKeywordsAndSearch()
    {

        ProgressDialogUtil.getInstance().showProgressDialogMsg("正在加载...", this, true);

        // 判断搜索关键字是否和上次一样 如果一样 返回
        if (tempKeywords.equals(searchTxt.getText().toString().trim()))
        {
            ProgressDialogUtil.getInstance().closeProgressDialog();
            return true;
        }

        if (TextUtils.isEmpty(searchTxt.getText().toString().trim()))
        {
            data.clear();
            addressListView.notifyData();
            ProgressDialogUtil.getInstance().closeProgressDialog();
            return true;
        }
        this.data.clear();
        // 搜索结果关键字 高亮显示
        addressAdapter.setSearchParam(searchTxt.getText().toString());
        poiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(
                searchTxt.getText().toString())
                .pageCapacity(AppConstant.MAX_PAGE_NUM)
                .pageNum(pageIndex));
        tempKeywords = searchTxt.getText().toString().trim();
        return false;
    }

    /**
     * 刷新
     */
    private void onRefresh()
    {
        if (StringUtils.isEmpty(searchTxt.getText().toString().trim()))
        {
            srl.setRefreshing(false);
            return;
        }
        addressAdapter.setSearchParam(searchTxt.getText().toString());
        poiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(
                searchTxt.getText().toString())
                .pageCapacity(AppConstant.MAX_PAGE_NUM)
                .pageNum(pageIndex));
        tempKeywords = searchTxt.getText().toString().trim();
    }


    /**
     * 加载更多
     *
     * @param pageIndex(页数)
     */
    private void loadMore(int pageIndex)
    {
        isLoadMore = true;
        addressAdapter.setSearchParam(searchTxt.getText().toString());
        poiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(
                searchTxt.getText().toString())
                .pageCapacity(AppConstant.MAX_PAGE_NUM)
                .pageNum(pageIndex));
    }

    @Override
    protected void onDestroy()
    {
        poiSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    /**
     * 文本框输入字符时
     *
     * @param s      输入的字符
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        if (checkKeywordsAndSearch())
        {
            return;
        }
    }
}
