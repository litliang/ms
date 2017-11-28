package com.yzb.card.king.ui.gift.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.FavorPayee;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.gift.adapter.FavorPayeeAdapter;
import com.yzb.card.king.ui.gift.presenter.FavorPayeePresenter;
import com.yzb.card.king.ui.bonus.view.FavorPayeeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能：礼品卡常用收款人；
 *
 * @author:gengqiyun
 * @date: 2017/2/17
 */
@ContentView(R.layout.activity_favor_payee)
public class FavorPayeeActivity extends BaseActivity implements View.OnClickListener, LoadMoreListView.OnLoadMoreListener,
        FavorPayeeView
{
    @ViewInject(R.id.etKeyword)
    private EditText etKeyword;
    @ViewInject(R.id.listView)
    private LoadMoreListView listView;

    private FavorPayeeAdapter adapter;
    private String backDataFlag;
    private FavorPayeePresenter favorPayeePresenter;
    private View ivClear;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);

        backDataFlag = getIntent().getStringExtra("sourceActivity");
        favorPayeePresenter = new FavorPayeePresenter(this);
        assignViews();

        loadData(true);
    }

    private void assignViews()
    {
        findViewById(R.id.ivBack).setOnClickListener(this);
        ivClear = findViewById(R.id.ivClear);
        ivClear.setOnClickListener(this);

        etKeyword.addTextChangedListener(searchTxtWatcher);
        listView.setCanLoadMore(true);
        listView.setOnLoadMoreListener(this);
        adapter = new FavorPayeeAdapter(this);
        adapter.setDataHandler(handler);
        listView.setAdapter(adapter);
    }

    /**
     * 搜索关键字输入监听；
     */
    private MyTextWatcher searchTxtWatcher = new MyTextWatcher()
    {
        @Override
        public void afterTextChange(String text)
        {
            ivClear.setVisibility(isEmpty(text) ? View.INVISIBLE : View.VISIBLE);
            loadData(true);
        }
    };

    private Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case FavorPayeeAdapter.WHAT_ITEM:
                    FavorPayee favorPayee = adapter.getItem(msg.arg1);
                    if (!isEmpty(backDataFlag))
                    {
                        Intent intent = new Intent();
                        intent.putExtra("payeeData", favorPayee);
                        setResult(Activity.RESULT_OK, intent);
                    }
                    finish();
                    break;
            }
            return false;
        }
    });

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivClear:
                etKeyword.setText("");
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }

    @Override
    public void onLoadMore()
    {
        loadData(false);
    }

    public void loadData(boolean isRefresh)
    {
        Map<String, Object> args = new HashMap<>();
        args.put("sessionId", AppConstant.sessionId);
        args.put("type", "1"); //0全部；1平台账户；2银行卡
        args.put("pageSize", "20");
        args.put("pageStart", isRefresh ? "0" : adapter.getCount() + "");
        args.put("searchName", etKeyword.getText().toString().trim());
        favorPayeePresenter.loadData(isRefresh, args);
    }

    @Override
    public void onGetFavorPayeeSuc(boolean event_tag, List<FavorPayee> list)
    {
        closePDialog();
        listView.onLoadMoreComplete();

        if (event_tag)
        {
            adapter.clearAll();
        }
        adapter.appendALL(list);
    }

    @Override
    public void onGetFavorPayeeFail(String failMsg)
    {
        closePDialog();
        listView.onLoadMoreComplete();
        //toastCustom(failMsg);
    }

}
