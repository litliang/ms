package com.yzb.card.king.ui.travel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.SearchResultBean;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.adapter.SearchListAdapter;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.app.presenter.SearchListPresenter;
import com.yzb.card.king.ui.app.view.SearchInputView;
import com.yzb.card.king.ui.appwidget.HistoryRecordView;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.travel.activity.view.TravelHistoryRecordView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.travel.adapter.FilterLeftAdapter;
import com.yzb.card.king.ui.travel.adapter.FilterRightAdapter;
import com.yzb.card.king.ui.travel.presenter.impl.TravelGetDesListPImpl;
import com.yzb.card.king.ui.travel.view.TravelGetDesListView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游目的地搜索；
 *
 * @author gengqiyun
 * @date 2016.11.21
 */
public class DestinationSearchActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TravelGetDesListView, SearchInputView
{
    private EditText etKeywordInput;
    private ListView lvLeft;
    private GridView gvRight;
    private List<FilterTwoBean> leftBeans;
    private FilterTwoBean selectCategory; // 被选择的分类项
    private FilterLeftAdapter leftAdapter;
    private FilterRightAdapter rightAdapter;

    private TravelHistoryRecordView historyView;
    private View ivClear;
    private View panelSearch;
    private List<String> historyList; //历史记录；

    private SearchListAdapter searchAdapter;
    private SwipeRefreshLayout srl;
    private LoadMoreListView searchListView;
    private BasePresenter getPlaceListPesenter; //获取地址列表；
    private List<FilterBean> rightBeans;
    private SearchListPresenter searchListPresenter;
    private String industryId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_search);

        getPlaceListPesenter = new TravelGetDesListPImpl(this);
        searchListPresenter = new SearchListPresenter(this);
        assignViews();
        recvIntentData();
        readHistoryFromLocal();
        loadCategoryList(true);
    }

    private void recvIntentData()
    {
        industryId = getIntent().getStringExtra("industryId");
        Serializable serObj = getIntent().getSerializableExtra("category");
        if (serObj != null && serObj instanceof FilterTwoBean)
        {
            selectCategory = (FilterTwoBean) serObj;
        }
    }

    private void assignViews()
    {
        etKeywordInput = (EditText) findViewById(R.id.etKeywordInput);
        etKeywordInput.addTextChangedListener(searchTxtWatcher);

        findViewById(R.id.panelBack).setOnClickListener(this);
        ivClear = findViewById(R.id.iv_clear);
        ivClear.setOnClickListener(this);
        ivClear.setVisibility(View.INVISIBLE);

        findViewById(R.id.tvSearch).setOnClickListener(this);
        lvLeft = (ListView) findViewById(R.id.lv_left);
        //左边列表视图
        leftAdapter = new FilterLeftAdapter(this);
        lvLeft.setAdapter(leftAdapter);
        lvLeft.setOnItemClickListener(lvLeftItemListener);

        gvRight = (GridView) findViewById(R.id.gv_right);
        rightAdapter = new FilterRightAdapter(this);
        gvRight.setOnItemClickListener(gvRightItemListener);
        gvRight.setAdapter(rightAdapter);

        historyView = (TravelHistoryRecordView) findViewById(R.id.historyView);
        historyView.setItemListener(new HistoryRecordView.HistoryItemListener()
        {
            @Override
            public void callBack(String keyWord)
            {
                etKeywordInput.setText(keyWord);
            }
        });
        panelSearch = findViewById(R.id.panelSearch);
        panelSearch.setVisibility(View.GONE);

        srl = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, srl);
        srl.setOnRefreshListener(this);

        searchListView = (LoadMoreListView) findViewById(R.id.searchListView);
        searchListView.setCanLoadMore(true);
        searchListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                searchInput(false);
            }
        });
        searchAdapter = new SearchListAdapter(this);
        searchAdapter.setCallBack(searchAdapterCallBack);
        searchListView.setAdapter(searchAdapter);
    }

    /**
     * 加载地址列表；
     *
     * @param event_tag true:获取默认地址列表；false：搜索；
     */
    public void loadCategoryList(boolean event_tag)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("industryId", AppConstant.travel_id);
        param.put("searchName", etKeywordInput.getText().toString().trim()); //搜索关键字；
        getPlaceListPesenter.loadData(event_tag, param);
    }

    @Override
    public void onGetDesListSucess(boolean event_tag, List<FilterTwoBean> filterBeans)
    {
        srl.setRefreshing(false);
        //获取默认地址列表；
        if (event_tag)
        {
            this.leftBeans = filterBeans;
            setCategoryData();
        }
    }

    @Override
    public void onGetDesListFail(String failMsg)
    {
        srl.setRefreshing(false);
        toastCustom(failMsg);
    }

    /**
     * 搜索结果item回调；
     */
    private SearchListAdapter.ISearchCallBack searchAdapterCallBack = new SearchListAdapter.ISearchCallBack()
    {
        @Override
        public void callBack(SearchResultBean bean)
        {
            LogUtil.i("点击的搜索bean=" + JSON.toJSONString(bean));
            Intent intent = new Intent(DestinationSearchActivity.this, TravelProductDetailActivity.class);
            intent.putExtra("id", bean.getObjId());
            startActivity(intent);
        }
    };

    /**
     * 初始化历史记录view数据；
     */
    private void readHistoryFromLocal()
    {
        String searchJson = SharePrefUtil.getValueFromSp(this, SharePrefUtil.SEARCH_TRAVEL_DESTINATION, "[]");
        LogUtil.i("本地历史记录searchJson=" + searchJson);
        historyList = JSON.parseArray(searchJson, String.class);
        historyView.setData(historyList);
        historyView.setVisibility(historyList == null || historyList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 搜索关键字输入监听；
     */
    private MyTextWatcher searchTxtWatcher = new MyTextWatcher()
    {
        @Override
        public void afterTextChange(String text)
        {
            etKeywordInput.setSelection(text.length());

            boolean isEmpty = isEmpty(text);
            ivClear.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
            panelSearch.setVisibility(!isEmpty ? View.VISIBLE : View.GONE);
            if (!isEmpty)
            {
                searchAdapter.setKeyWord(text);
                searchInput(true);
            }
        }
    };

    /**
     * 根据关键字搜索；
     *
     * @event_tag true:下拉刷新；false：加载更多；
     */
    private void searchInput(boolean event_tag)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("industryId", industryId);
        params.put("searchName", etKeywordInput.getText().toString().trim());//搜索内容
        params.put("pageStart", event_tag ? "0" : searchAdapter.getCount() + "");//请求开始位置
        params.put("pageSize", "20");//每次请求结果数量
        params.put("serviceName", CardConstant.card_app_searchlist);
        searchListPresenter.loadData(event_tag, params);
    }

    /**
     * 添加历史记录；
     *
     * @param keyword
     */
    public void addHistory(String keyword)
    {
        //相同则不添加；
        if (historyList != null)
        {
            for (int i = 0; i < historyList.size(); i++)
            {
                if (historyList.get(i).trim().equals(keyword.trim()))
                {
                    return;
                }
            }
            historyList.add(0, keyword);
            if (historyList.size() > 10)
            {
                //最多10条记录；
                historyList = historyList.subList(0, 10);
            }
        }
    }

    @Override
    public void onRefresh()
    {
        searchAdapter.clearAll();
        searchInput(true);
    }

    /**
     * 设置数据；
     */
    private void setCategoryData()
    {
        if (leftBeans == null || leftBeans.size() == 0) return;
        if (selectCategory == null)
        {
            selectCategory = leftBeans.get(0);
        }
        leftAdapter.clearAll();
        leftAdapter.appendALL(leftBeans);
        leftAdapter.setSelectedEntity(selectCategory);

        //判断来自上个页面的数据；
        List<FilterBean> filterBeans = selectCategory.getChildList();

        //搜索选中的FilterTwoBean中无childList时，选中加载的右侧数据；
        rightBeans = filterBeans == null || filterBeans.size() == 0 ? leftAdapter.getRightDataByLeftId(selectCategory) : filterBeans;

        rightAdapter.clearAll();
        rightAdapter.appendALL(rightBeans);

        if (selectCategory.getSelectedFilterEntity() != null)
        {
            rightAdapter.setSelectedEntity(selectCategory.getSelectedFilterEntity());
        }
    }

    /**
     * 左侧ListView点击；
     */
    private AdapterView.OnItemClickListener lvLeftItemListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            LogUtil.i("左侧ListView点击了====");
            selectCategory = leftBeans.get(position);
            leftAdapter.setSelectedEntity(selectCategory);
            // 右边列表视图
            rightAdapter.setList(selectCategory.getChildList());
        }
    };

    /**
     * 右侧GridView点击；
     */
    private AdapterView.OnItemClickListener gvRightItemListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent();

            //设置子类型；当搜索结果进入时，childList字段会为空；
            selectCategory.setChildList(leftAdapter.getRightDataByLeftId(selectCategory));

            if (selectCategory != null && selectCategory.getChildList() != null && selectCategory.getChildList().size() > 0)
            {
                intent.putExtra("backData", selectCategory);
                //左侧保存右侧选中的部分；
                selectCategory.setSelectedFilterEntity(selectCategory.getChildList().get(position));
            }
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelBack:
                finish();
                break;
            case R.id.tvSearch:
                String inputKeyword = etKeywordInput.getText().toString().trim();
                if (isEmpty(inputKeyword))
                {
                    toastCustom("请输入关键字");
                    return;
                }
                searchAdapter.clearAll();
                searchAdapter.setKeyWord(inputKeyword);
                if (panelSearch.getVisibility() != View.VISIBLE)
                {
                    panelSearch.setVisibility(View.VISIBLE);
                }
                searchInput(true);
                break;
            case R.id.iv_clear:
                etKeywordInput.setText("");
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        saveHistory();
    }

    /**
     * 保存历史记录；
     */
    private void saveHistory()
    {
        SharePrefUtil.saveToSp(this, SharePrefUtil.SEARCH_TRAVEL_DESTINATION, historyList == null ? "[]" : JSON.toJSONString(historyList));
    }

    @Override
    public void onSearchInputSucess(boolean event_tag, Object data)
    {
        if (srl.isRefreshing())
        {
            srl.setRefreshing(false);
        }
        if (searchListView.isLoading())
        {
            searchListView.onLoadMoreComplete();
        }
        List<SearchResultBean> resultBeans = (List<SearchResultBean>) data;
        if (event_tag)
        {
            searchAdapter.clearAll();
        }
        searchAdapter.appendALL(resultBeans);
        addHistory(etKeywordInput.getText().toString().trim());
    }

    @Override
    public void onSearchInputFail(String failMsg)
    {
        if (srl.isRefreshing())
        {
            srl.setRefreshing(false);
        }
        if (searchListView.isLoading())
        {
            searchListView.onLoadMoreComplete();
        }
        // toastCustom(failMsg);
    }
}
