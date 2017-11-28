package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SearchReusultBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.adapter.CatalogueTypeAdapter;
import com.yzb.card.king.ui.appwidget.popup.adapter.SubItemadapter;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.adapter.HotelSearchResultAdapter;
import com.yzb.card.king.ui.hotel.persenter.FilterListPersenter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/21
 * 描  述：酒店名、地名、地标搜索
 */
@ContentView(R.layout.activity_hotel_search)
public class HotelSearchActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    @ViewInject(R.id.llSelectedAddress)
    private LinearLayout llSelectedAddress;

    @ViewInject(R.id.llSearch)
    private EditText llSearch;

    @ViewInject(R.id.lvHomeHotelpage)
    private HeadFootRecyclerView lvHomeHotelpage;

    @ViewInject(R.id.llDelKeyWord)
    private LinearLayout llDelKeyWord;

    private CatalogueTypeAdapter hotelBrandTypeAdapter;

    private SubItemadapter subItemAdapter;

    private SubItemBean subItemBean;

    private FilterListPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("transmitData")) {

            subItemBean = (SubItemBean) getIntent().getSerializableExtra("transmitData");
        }

        initView();

        initRequest();

    }

    /**
     * 初始观察者请求
     */
    private void initRequest() {

        persenter = new FilterListPersenter(this);

        persenter.sendHotelSearchDefaultFilter(cityId);

        showPDialog("正在请求数据……");
    }

    private void initView() {

        myHandler.sendEmptyMessageDelayed(2, 1000);

        llSelectedAddress.setVisibility(View.GONE);

        llSearch.setHint("搜索酒店名/地名");

        if (subItemBean != null) {

            llSearch.setText(subItemBean.getFilterName());
        }

        llSearch.setFocusable(false);

        lvHomeHotelpage.setLayoutManager(new GridLayoutManager(this, 1));

        initData();

        llSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String keyStr = llSearch.getText().toString();

                if (!TextUtils.isEmpty(keyStr)) {

                    Message message = myHandler.obtainMessage();

                    message.what = 1;

                    message.obj = keyStr;

                    myHandler.sendMessage(message);


                    llDelKeyWord.setVisibility(View.VISIBLE);

                } else {

                    llDelKeyWord.setVisibility(View.GONE);

                    myHandler.sendEmptyMessage(3);
                }

            }
        });

        llDelKeyWord.setOnClickListener(this);
    }

    private void initData() {

        List<CatalogueTypeBean> monthBeanList = new ArrayList<CatalogueTypeBean>();

        hotelBrandTypeAdapter = new CatalogueTypeAdapter(this, monthBeanList);

        hotelBrandTypeAdapter.setItemDataCallBack(itemDataCallBack);

        lvHomeHotelpage.setAdapter(hotelBrandTypeAdapter);

        View headerView = LayoutInflater.from(this).inflate(R.layout.view_type_brand_item, null);//

        lvHomeHotelpage.setHeadView(headerView);

        initHeadView(headerView);

        initHeadData();

    }

    private HotelSearchResultAdapter hotelSearchResultAdapter;

    /**
     * 查询搜索结果
     */
    private void searchResultData(String keyWord) {

        if (hotelSearchResultAdapter == null) {

            hotelSearchResultAdapter = new HotelSearchResultAdapter(this);

            hotelSearchResultAdapter.setSelectedItemObject(new HotelSearchResultAdapter.SelectedItemObject() {
                @Override
                public void selectedCallDataBack(SearchReusultBean bean) {

                    Intent selectResult = new Intent();

                    selectResult.putExtra("selectoSubItemBean", bean);

                    setResult(1041, selectResult);

                    finish();

                }
            });

        }

        lvHomeHotelpage.setAdapter(hotelSearchResultAdapter);

        persenter.sendHotelKeywordSearchAction(cityName, keyWord);

    }

    private int maxNumber = 8;

    private void saveToPre(String keyWord) {

        //存储历史城市
        String histCityData = SharePrefUtil.getValueFromSp(this, SharePrefUtil.HOTEL_SEARCH_RESULT_DATA, null);

        if (histCityData != null) {

            List<String> listData = JSONArray.parseArray(histCityData, String.class);

            int size = listData.size();

            if (size > 0) {

                //检查是否已经存在该城市
                boolean ifExit = true;

                for (String beanTemp : listData) {

                    if (keyWord.equals(beanTemp)) {

                        ifExit = false;

                        break;
                    }

                }

                if (ifExit) {

                    if (size == maxNumber) {//移除最后一笔数据

                        listData.remove(maxNumber - 1);

                    }

                    listData.add(0, keyWord);

                    String totalStr = JSON.toJSONString(listData);

                    SharePrefUtil.saveToSp(this, SharePrefUtil.USE_CITY_HISTORY_DATA, totalStr);

                }


            } else {

                listData.add(keyWord);
            }

        } else {

            List<String> listData = new ArrayList<String>();

            listData.add(keyWord);

            String totalStr = JSON.toJSONString(listData);

            SharePrefUtil.saveToSp(this, SharePrefUtil.HOTEL_SEARCH_RESULT_DATA, totalStr);
        }

    }

    private String keyWord;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;

            if (what == 0) {

                // hotelBrandTypeAdapter.addNewData(getData());

            } else if (what == 1) {

                keyWord = (String) msg.obj;

                searchResultData(keyWord);

            } else if (what == 2) {

                llSearch.setFocusable(true);

                llSearch.setFocusableInTouchMode(true);

                llSearch.requestFocus();

            } else if (what == 3) {

                initData();

                persenter.sendHotelSearchDefaultFilter(cityId);
            }
        }
    };


    private void initHeadData() {
    }

    private void initHeadView(View headerView) {

        TextView tvBrandTypeName = (TextView) headerView.findViewById(R.id.tvBrandTypeName);

        tvBrandTypeName.setText(R.string.hotel_search_history);

        TextView tvClearData = (TextView) headerView.findViewById(R.id.tvClearData);

        tvClearData.setVisibility(View.VISIBLE);

        tvClearData.setOnClickListener(this);

        ImageButton tvSpread = (ImageButton) headerView.findViewById(R.id.tvSpread);

        tvSpread.setVisibility(View.GONE);

        LinearLayout llArrowGray = (LinearLayout) headerView.findViewById(R.id.llArrowGray);

        llArrowGray.setTag(true);


        List<SubItemBean> brandBeanList = new ArrayList<>();

        WholeRecyclerView wvBrand = (WholeRecyclerView) headerView.findViewById(R.id.wvBrand);

        String histCityData = SharePrefUtil.getValueFromSp(this, SharePrefUtil.HOTEL_SEARCH_RESULT_DATA, null);

        if (histCityData != null && histCityData.length()>0) {

            List<String> listData = JSONArray.parseArray(histCityData, String.class);

            for (int j = 0; j < listData.size(); j++) {

                SubItemBean brandBean = new SubItemBean();

                brandBean.setFilterName(listData.get(j));

                brandBeanList.add(brandBean);
            }

        }
        subItemAdapter = new SubItemadapter(this, brandBeanList, true);

        subItemAdapter.setItemDataCallBack(itemHistroyDataCallBack);

        wvBrand.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(this, 7)));

        wvBrand.setLayoutManager(new GridLayoutManager(this, 4));

        wvBrand.setAdapter(subItemAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvClearData://清空数据

                SharePrefUtil.saveToSp(this, SharePrefUtil.USE_CITY_HISTORY_DATA, "");

                subItemAdapter.clearData();

                break;
            case R.id.llDelKeyWord:

                llSearch.setText("");

                break;

            default:
                break;

        }
    }

    private SubItemadapter.ItemDataCallBack itemHistroyDataCallBack = new SubItemadapter.ItemDataCallBack() {
        @Override
        public void onSelectorItem(SubItemBean subItemBean) {

            llSearch.setText(subItemBean.getFilterName());

        }
    };

    private SubItemadapter.ItemDataCallBack itemDataCallBack = new SubItemadapter.ItemDataCallBack() {
        @Override
        public void onSelectorItem(SubItemBean subItemBean) {

            Intent selectResult = new Intent();

            selectResult.putExtra("selectoSubItemBean", subItemBean);

            setResult(1040, selectResult);

            finish();

        }
    };

    @Override
    public void callSuccessViewLogic(Object o, int type) {

        dimissPdialog();

        if (type == -1) {

            List<CatalogueTypeBean> catalogueTypeBeanList = JSONArray.parseArray(o + "", CatalogueTypeBean.class);


            hotelBrandTypeAdapter.addNewData(catalogueTypeBeanList);

        } else if (type == -4444) {

            List<SearchReusultBean> resultList = JSONArray.parseArray(o + "", SearchReusultBean.class);

            hotelSearchResultAdapter.addNewListData(resultList);


            //记录历史数据
            if (keyWord != null) {

                saveToPre(keyWord);

                SubItemBean brandBean = new SubItemBean();

                brandBean.setFilterName(keyWord);

                subItemAdapter.addOneData(brandBean);
            }

        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        dimissPdialog();

        if (type == -4444) {

            hotelSearchResultAdapter.clearData();

        }
    }
}
