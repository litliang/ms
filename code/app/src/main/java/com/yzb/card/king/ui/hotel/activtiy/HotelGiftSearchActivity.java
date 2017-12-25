package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.adapter.HotelGiftSearchResultAdapter;
import com.yzb.card.king.ui.hotel.persenter.FilterListPersenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 酒店礼品卡套餐搜索
 * Created by 玉兵 on 2017/12/23.
 */
@ContentView(R.layout.activity_hotel_gift_search)
public class HotelGiftSearchActivity extends BaseActivity implements View.OnClickListener,BaseViewLayerInterface {

    @ViewInject(R.id.llSelectedAddress)
    private LinearLayout llSelectedAddress;

    @ViewInject(R.id.llSearch)
    private EditText llSearch;

    @ViewInject(R.id.lvHomeHotelpage)
    private HeadFootRecyclerView lvHomeHotelpage;

    @ViewInject(R.id.llDelKeyWord)
    private LinearLayout llDelKeyWord;

    private FilterListPersenter persenter;

    private int giftsType ;

    private int  industryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        persenter = new FilterListPersenter(this);

        giftsType = getIntent().getIntExtra("giftsType",7);

        industryId = getIntent().getIntExtra("industryId",Integer.parseInt(AppConstant.hotel_id));

        initView();
    }


    private void initView() {

        llSearch.setFocusable(false);

        myHandler.sendEmptyMessageDelayed(2, 1000);

        llSelectedAddress.setVisibility(View.GONE);

        llSearch.setHint("请输入门店名称");

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

                }

            }
        });

        llDelKeyWord.setOnClickListener(this);
    }

    private String keyWord;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;

          if (what == 1) {

                keyWord = (String) msg.obj;

                searchResultData(keyWord);

            } else if (what == 2) {

                llSearch.setFocusable(true);

                llSearch.setFocusableInTouchMode(true);

                llSearch.requestFocus();

            }
        }
    };

    private HotelGiftSearchResultAdapter hotelSearchResultAdapter;

    private int pageStart = 0;

    /**
     * 查询搜索结果
     */
    private void searchResultData(String keyWordT) {

        pageStart = 0;

        if (hotelSearchResultAdapter == null) {

            hotelSearchResultAdapter = new HotelGiftSearchResultAdapter(this);

            lvHomeHotelpage.setLayoutManager(new GridLayoutManager(this, 1));

            hotelSearchResultAdapter.setSelectedItemObject(new HotelGiftSearchResultAdapter.SelectedItemObject() {
                @Override
                public void selectedCallDataBack(String bean) {

                    Intent selectResult = new Intent();

                    selectResult.putExtra("storeName", bean);

                    setResult(1041, selectResult);

                    finish();

                }
            });
            lvHomeHotelpage.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
                @Override
                public void loadMoreListener() {

                    persenter.sendHotelGiftKeywordSearchAction(cityId,giftsType,industryId,keyWord,pageStart);
                }
            });
        }

        lvHomeHotelpage.setAdapter(hotelSearchResultAdapter);

        persenter.sendHotelGiftKeywordSearchAction(cityId,giftsType,industryId,keyWordT,pageStart);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.llDelKeyWord:

                llSearch.setText("");

                break;

            default:
                break;

        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {

        if(type == -4443){

            List<String> list = JSONArray.parseArray(o + "", String.class);

            int size = list.size();

            if(pageStart == 0){


                hotelSearchResultAdapter.addNewListData(list);

            }else {

                hotelSearchResultAdapter.addMoreList(list);

                lvHomeHotelpage.calByNewNum(size);
            }

            if (size == AppConstant.MAX_PAGE_NUM) {

                pageStart = pageStart + AppConstant.MAX_PAGE_NUM;
            }
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        if(type == -4443){

            if(pageStart == 0) {

                hotelSearchResultAdapter.clearData();

            }else {

                if (pageStart > 0) {

                    pageStart = pageStart - AppConstant.MAX_PAGE_NUM;
                }

            }


        }

    }
}
