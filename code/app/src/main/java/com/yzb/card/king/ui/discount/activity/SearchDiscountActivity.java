package com.yzb.card.king.ui.discount.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.adapter.HistoryAdapterAbs;
import com.yzb.card.king.ui.discount.adapter.search.GridPagerAdapter;
import com.yzb.card.king.ui.discount.bean.History;
import com.yzb.card.king.ui.discount.holder.OrderDetailHolder;
import com.yzb.card.king.ui.discount.holder.SearchKeyListHolder;
import com.yzb.card.king.ui.discount.holder.ShopSearchResultHolder;
import com.yzb.card.king.ui.appwidget.SlideShowView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索页；
 */
public class SearchDiscountActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener
{
    private LinearLayout ivBack;
    private TextView tvSearch;
    private SlideShowView slideShowView;
    private ListView lvHistory;
    private View llClear;
    private AbsBaseListAdapter adapter;
    private List<String> historyList = new ArrayList<>();
    private EditText etSearch;
    private static final int MAX_SIZE = 9;
    private GridPagerAdapter<String> pagerAdapter;
    private List<String> hotSearchList = new ArrayList<>();
    private String storeId;
    private FrameLayout flResult;
    private TextWatcher watcher;
    private String parentType;
    private SearchKeyListHolder searchKeyListHolder;
    private FrameLayout flKeyList;
    private boolean isInputChange = true;
    private LinearLayout llResult;
    private FrameLayout flOrder;
    private ShopSearchResultHolder shopSearchResultHolder;
    private OrderDetailHolder orderDetailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_search_discount);
        ivBack = (LinearLayout) findViewById(R.id.iv_back);
        slideShowView = (SlideShowView) findViewById(R.id.show9ItemView);
        tvSearch = (TextView) findViewById(R.id.tvSearch);
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        llClear = findViewById(R.id.llClear);
        etSearch = (EditText) findViewById(R.id.et_search);
        llResult = (LinearLayout) findViewById(R.id.llResult);
        flResult = (FrameLayout) findViewById(R.id.flResult);
        flOrder = (FrameLayout) findViewById(R.id.flOrder);
        flKeyList = (FrameLayout) findViewById(R.id.flKeyList);

        ivBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        llClear.setOnClickListener(this);
    }

    private void initData()
    {
        storeId = getIntent().getStringExtra("storeId");
        parentType = getIntent().getStringExtra("parentType");

        watcher = new SearchWatcher();
        etSearch.addTextChangedListener(watcher);
//
//        GetHotSearchesTask getHotSearchesTask = new GetHotSearchesTask();
//        getHotSearchesTask.execute();
        initHistoryView();
        setKeyListContent();
        pagerAdapter = new GridPagerAdapter<>(hotSearchList,
                new GridPagerAdapter.OnGetHolderListener<String>()
                {
                    @Override
                    public Holder<String> onGetHolder()
                    {
                        return new HotSearchHolder();
                    }
                });
        slideShowView.setDotSelector(R.drawable.selector_dot_white_red);
        slideShowView.setPagerAdapter(pagerAdapter);

        initResultView();
    }

    private void initResultView()
    {
        orderDetailHolder = new OrderDetailHolder(this);
        shopSearchResultHolder = new ShopSearchResultHolder(orderDetailHolder);

        flResult.removeAllViews();
        flOrder.removeAllViews();

        flResult.addView(shopSearchResultHolder.getConvertView());
        flOrder.addView(orderDetailHolder.getConvertView());
    }

    private void setKeyListContent()
    {
        View view = getContent();
        flKeyList.removeAllViews();
        flKeyList.addView(view);
    }

    private View getContent()
    {
        searchKeyListHolder = new SearchKeyListHolder();
        searchKeyListHolder.setListener(new SearchKeyListHolder.OnItemClickListener()
        {
            @Override
            public void onItemClick(History history)
            {
                doSearch(history.key);
            }
        });
        return searchKeyListHolder.getConvertView();
    }

    /**
     * 初始化历史
     *
     * @author ysg
     * created at 2016/8/2 18:09
     */
    private void initHistoryView()
    {
        adapter = new HistoryAdapterAbs(historyList);
        lvHistory.setAdapter(adapter);
        getHistory();
        lvHistory.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tvSearch://搜索
                String key = etSearch.getText().toString();
                doSearch(key);
                break;
            case R.id.llClear://清除历史
                clearHistory();
                break;
        }

    }

    /**
     * 获取历史记录
     *
     * @author ysg
     * created at 2016/8/2 18:16
     */
    private void getHistory()
    {
        String json = SharePrefUtil.getValueFromSp(this, SharePrefUtil.SEARCH_HISTORY, "[]");
        historyList.clear();
        historyList.addAll(JSON.parseArray(json, String.class));
        adapter.notifyDataSetChanged();
    }

    /**
     * 清除历史
     *
     * @author ysg
     * created at 2016/8/2 17:55
     */
    private void clearHistory()
    {
        historyList.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 添加历史
     *
     * @author ysg
     * created at 2016/8/2 18:11
     */
    private void addHistory(String str)
    {
        historyList.add(0, str);
        for (int i = 1; i < historyList.size(); i++)
        {
            if (str.equals(historyList.get(i)))
            {
                historyList.remove(i);
                break;
            }
        }
        keepListSize(historyList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 保证list的大小不超过Maxsize
     *
     * @author ysg
     * created at 2016/8/4 16:00
     */
    private void keepListSize(List list)
    {
        if (list.size() > MAX_SIZE)
        {
            list.remove(list.size() - 1);
            keepListSize(list);
        }
    }

    /**
     * 保存历史
     *
     * @author ysg
     * created at 2016/8/2 18:13
     */
    private void saveHistory()
    {
        SharePrefUtil.saveToSp(this, SharePrefUtil.SEARCH_HISTORY, JSON.toJSONString(historyList));
    }

    /**
     * 搜索
     *
     * @author ysg
     * created at 2016/8/2 17:50
     */
    public void doSearch(String key)
    {
        if (TextUtils.isEmpty(key))
        {
            UiUtils.shortToast("请输入词条");
        } else
        {
            isInputChange = false;
            etSearch.setText(key);
            isInputChange = true;
            addHistory(key);
            goResultPage(key);
        }
    }

    /**
     * 跳转搜索结果页面
     *
     * @author ysg
     * created at 2016/8/4 14:59
     */
    private void goResultPage(String key)
    {

        if (TextUtils.isEmpty(storeId))
        {
//            Intent intent = new Intent();
//            intent.setClass(this, SearchResultActivity.class);
//            intent.putExtra("parentType", parentType);
//            startActivity(intent);
//            finish();
        } else
        {
            llResult.setVisibility(View.VISIBLE);
            shopSearchResultHolder.refreshView(new History(key, storeId, ""));
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String history = historyList.get(position);
        doSearch(history);
    }

//    /**
//     * 获取热门搜索词条
//     *
//     * @author ysg
//     *         created at 2016/8/2 18:08
//     */
//    private class GetHotSearchesTask extends CommentTask
//    {
//        public GetHotSearchesTask()
//        {
//            super("");
//        }
//
//        @Override
//        protected void parseJson(String data)
//        {
//
//
//        }
//
//        @Override
//        protected void setParam(Map<String, String> param)
//        {
//
//        }
//
//        @Override
//        protected void onFail(String code, String error)
//        {
//            super.onFail(code, error);
//            for (int i = 0; i < 20; i++)
//            {
//                hotSearchList.add("测试试" + i);
//            }
//            pagerAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        saveHistory();
    }

    public class HotSearchHolder extends Holder<String>
    {
        private TextView textView;
        private String history;

        @Override
        public View initView()
        {
            View view = UiUtils.inflate(R.layout.holder_hot_search);
            textView = (TextView) view.findViewById(R.id.textView);
            textView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    doSearch(history);
                }
            });
            return view;
        }

        @Override
        public void refreshView(String data)
        {
            history = data;
            if (data != null && data.length() > 4)
            {
                data = data.substring(0, 4);
            }
            textView.setText(data);
        }
    }

    private class SearchWatcher implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (isInputChange)
            {
                if (s.length() > 0)
                {//显示搜索结果页面
                    flKeyList.setVisibility(View.VISIBLE);
                } else
                {//隐藏搜索结果页面
                    flKeyList.setVisibility(View.GONE);
                }
                llResult.setVisibility(View.GONE);
                searchKeyListHolder.refreshView(new History(s.toString(), storeId, parentType));
            }
        }
    }
}
