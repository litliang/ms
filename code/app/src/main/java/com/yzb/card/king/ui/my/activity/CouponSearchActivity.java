package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.SearchResultBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.adapter.SearchListAdapter;
import com.yzb.card.king.ui.app.presenter.SearchListPresenter;
import com.yzb.card.king.ui.app.view.SearchInputView;
import com.yzb.card.king.ui.appwidget.HistoryRecordView;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.travel.activity.view.TravelHistoryRecordView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 优惠券、代金券搜索；
 *
 * @author gengqiyun
 * @date 2017/1/12
 */
@ContentView(R.layout.activity_coupon_search)
public class CouponSearchActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SearchInputView
{
    @ViewInject(R.id.etKeywordInput)
    private EditText etKeywordInput;

    @ViewInject(R.id.listView)
    private LoadMoreListView listView;

    @ViewInject(R.id.historyView)
    private TravelHistoryRecordView historyView;

    @ViewInject(R.id.ivClear)
    private View ivClear;

    private List<String> historyList; //历史记录；

    private SearchListAdapter searchAdapter;
    private SwipeRefreshLayout srl;
    private SearchListPresenter searchListPresenter;

    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        type = getIntent().getIntExtra("youhuiType",1);

        assignViews();
        searchListPresenter = new SearchListPresenter(this);
        readHistoryFromLocal();
    }

    private void assignViews()
    {
        findViewById(R.id.ivBack).setOnClickListener(this);
        etKeywordInput.addTextChangedListener(searchTxtWatcher);
        ivClear.setOnClickListener(this);
        ivClear.setVisibility(View.INVISIBLE);

        findViewById(R.id.tvSearch).setOnClickListener(this);

        historyView.setItemListener(new HistoryRecordView.HistoryItemListener()
        {
            @Override
            public void callBack(String keyWord)
            {
                etKeywordInput.setText(keyWord);
            }
        });
        srl = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, srl);
        srl.setOnRefreshListener(this);

        listView = (LoadMoreListView) findViewById(R.id.listView);
        listView.setCanLoadMore(true);
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                searchInput(false);
            }
        });
        searchAdapter = new SearchListAdapter(this);
        searchAdapter.setType(1);
        searchAdapter.setCallBack(searchAdapterCallBack);
        listView.setAdapter(searchAdapter);
    }

    /**
     * 搜索结果item回调；
     */
    private SearchListAdapter.ISearchCallBack searchAdapterCallBack = new SearchListAdapter.ISearchCallBack()
    {
        @Override
        public void callBack(SearchResultBean bean)
        {

            if(type == 1){

                Intent intent = new Intent(CouponSearchActivity.this, CouponMoreShopsActivity.class);
                intent.putExtra("paramData", bean);
                startActivity(intent);

            }else if(type == 2){

                Intent intent = new Intent(CouponSearchActivity.this, VoucherMoreShopsActivity.class);
                intent.putExtra("paramData", bean);
                startActivity(intent);
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

        params.put("cityId", isEmpty(selectedCity.cityId) ? positionedCity.cityId : selectedCity.cityId);

        params.put("searchName", etKeywordInput.getText().toString().trim());//搜索内容

        params.put("pageStart", event_tag ? "0" : searchAdapter.getCount() + "");//请求开始位置

        params.put("pageSize", "20");

        if(type == 1){

            params.put("serviceName", CardConstant.card_app_searchcoupon);

        }else if(type == 2){

            params.put("serviceName", CardConstant.card_app_cashcouponkeywordssearch);
        }



        searchListPresenter.loadData(event_tag, params);
    }

    /**
     * 初始化历史记录view数据；
     */
    private void readHistoryFromLocal()
    {
        String searchJson = SharePrefUtil.getValueFromSp(this, SharePrefUtil.SEARCH_COUPON, "[]");
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
            boolean isEmpty = isEmpty(text);
            ivClear.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
            if (!isEmpty)
            {
                searchAdapter.setKeyWord(text);
                searchInput(true);
                addHistory(etKeywordInput.getText().toString().trim());
            }
        }
    };

    @Override
    public void onRefresh()
    {
        searchInput(true);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivBack:
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
                searchInput(true);
                break;
            case R.id.ivClear:
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
        SharePrefUtil.saveToSp(this, SharePrefUtil.SEARCH_COUPON, historyList == null ? "[]" : JSON.toJSONString(historyList));
    }

    @Override
    public void onSearchInputSucess(boolean event_tag, Object data)
    {
        srl.setRefreshing(false);
        if (!event_tag)
        {
            listView.onLoadMoreComplete();
        }
        if (data != null)
        {
            List<SearchResultBean> resultBeans = (List<SearchResultBean>) data;
            //下拉刷新先清空；
            if (event_tag)
            {
                searchAdapter.clearAll();
            }
            searchAdapter.appendALL(resultBeans);
        }
    }

    @Override
    public void onSearchInputFail(String failMsg)
    {
        srl.setRefreshing(false);
        listView.onLoadMoreComplete();
        //toastCustom(failMsg);
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
}
