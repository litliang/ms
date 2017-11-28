package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.OptionView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.adapter.DiscountInfoAdapter;
import com.yzb.card.king.ui.credit.bean.DiscountInfo;
import com.yzb.card.king.ui.credit.bean.LeftPopItem;
import com.yzb.card.king.ui.credit.presenter.DiscountInfoPres;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountInfoActivity extends BaseActivity
        implements View.OnClickListener, OptionView.OnBankChangeListener,
        OptionView.OnTypeChangeListener, AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, GlobalApp.OnCityChangeListener, BaseViewLayerInterface
{

    private LinearLayout headerLeft;
    private OptionView optionView;
    private ListView listView;
    private List<DiscountInfo> dataList = new ArrayList<>();
    private DiscountInfoAdapter adapter;
    private String bankId = "";
    private LeftPopItem leftPopItem;
    private String bankName = "我的银行";
    private String typeId;
    private SwipeRefreshLayout sRL;
    private String cityId;
    private String cityName;
    private DiscountInfoPres presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        if (presenter == null)
        {
            presenter = new DiscountInfoPres(this);
        }
        initView();
        initData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        GlobalApp.getInstance().removeListener(this);
    }

    private void initData()
    {
        cityId = selectedCity.cityId;
        cityName = selectedCity.cityName;
        GlobalApp.getInstance().setOnCityChangeListeners(this);
        leftPopItem = (LeftPopItem) getIntent().getSerializableExtra("data");

        if (leftPopItem != null)
        {
            typeId = leftPopItem.id;
            optionView.setType(leftPopItem.typeName);
        }
        optionView.setCity(cityName);
        optionView.setBankName(bankName);
        optionView.setOnTypeChangeListener(this);
        optionView.setOnBankChangeListener(this);
        getDataList();
    }

    private void getDataList()
    {

        Map<String,Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("bankId", bankId);
        param.put("cityId", cityId);
        presenter.loadData(param);
    }

    private void initView()
    {
        setContentView(R.layout.activity_discount_info);
        if (getIntent().hasExtra("title"))
        {
            setHeader(R.mipmap.icon_back_white, getIntent().getStringExtra("title"));
        } else
        {
            setHeader(R.mipmap.icon_back_white, "优惠资讯");
        }

        headerLeft = (LinearLayout) findViewById(R.id.headerLeft);
        optionView = (OptionView) findViewById(R.id.optionView);

        sRL = (SwipeRefreshLayout) findViewById(R.id.sRL);

        SwipeRefreshSettings.setAttrbutes(this, sRL);

        sRL.setOnRefreshListener(this);

        adapter = new DiscountInfoAdapter(this, dataList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        headerLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:
                finish();
                break;
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        if (!StringUtils.isEmpty(CitySelectManager.getInstance().getPlaceId()))
        {
            String cityId = CitySelectManager.getInstance().getPlaceId();
            if (!this.cityId.equals(cityId))
            {
                this.cityId = cityId;
                cityName = CitySelectManager.getInstance().getPlaceName();
                optionView.setCity(cityName);
                refresh();
            }
            CitySelectManager.getInstance().clearData();
        }
    }

    @Override
    public void onBankChange(String bankId)
    {
        this.bankId = bankId;
        refresh();
    }

    private void refresh()
    {
        getDataList();
    }

    @Override
    public void onTypeChange(String typeId)
    {
        this.typeId = typeId;
        refresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this, DiscountDetailActivity.class);
        intent.putExtra("data", dataList.get(position));
        startActivity(intent);
    }

    @Override
    public void onRefresh()
    {
        refresh();
    }

    @Override
    public void onCityChange(Location city)
    {
        optionView.setCity(cityName);
    }



    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        sRL.setRefreshing(false);
        ProgressDialogUtil.getInstance().closeProgressDialog();
        dataList.clear();
        dataList.addAll((List<DiscountInfo>)o);
        adapter.notifyDataSetChanged();
        if (dataList.size() <= 0)
        {
            UiUtils.shortToast("没数据");
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        sRL.setRefreshing(false);
        ProgressDialogUtil.getInstance().closeProgressDialog();
        dataList.clear();
        adapter.notifyDataSetChanged();
        UiUtils.shortToast("请求失败");
    }
}
