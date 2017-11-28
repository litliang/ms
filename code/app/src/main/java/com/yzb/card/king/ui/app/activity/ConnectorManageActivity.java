package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.holder.ConnectorListHeaderHolder;
import com.yzb.card.king.ui.app.holder.ConnectorListHolder;
import com.yzb.card.king.ui.app.interfaces.ConnectorListener;
import com.yzb.card.king.ui.app.presenter.ConnectorManaPresenter;
import com.yzb.card.king.ui.app.view.ConnectorManaView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用联系人；
 */
public class ConnectorManageActivity extends BaseActivity implements ConnectorManaView,
        SwipeRefreshLayout.OnRefreshListener
{
    public static final String KEY = "sourceActivity";

    private static final int PICK_CONTACT = 1;
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<Connector> dataList = new ArrayList<>();
    private String sourceActivity;
    private ConnectorManaPresenter presenter;
    private View addImage;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        presenter = new ConnectorManaPresenter(this);
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_contactor_manage);
        setHeader(R.mipmap.icon_back_white, "联络人管理");
        switchContentLeft(0);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ConnectorAdapterAbs(dataList);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        ConnectorListHeaderHolder headerHolder = new ConnectorListHeaderHolder(ConnectorManageActivity.this);
        headerHolder.setAddConnector(new ConnectorListHeaderHolder.AddConnector()
        {
            @Override
            public void importConnector()
            {
                ContactUtil.startContactsActivity(ConnectorManageActivity.this, PICK_CONTACT);
            }
        });
        headerHolder.setListener(new MyConnectorListener());
        listView.addHeaderView(headerHolder.getConvertView());
        listView.setAdapter(adapter);
        addImage = findViewById(R.id.setting_touradd_img);
    }

    private void initData()
    {
        sourceActivity = getIntent().getStringExtra(KEY);
        getDataList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        onRefresh();
    }

    private void getDataList()
    {
        presenter.loadConn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case PICK_CONTACT:
                Connector connector = new Connector();
                connector.isDefault = false;
                ContactUtil.getConnector(this, data, connector);
                if (connector.mobile != null && connector.nickName != null && !hasExist(connector.mobile))
                {
                    dataList.add(connector);
                    connector.type = "2";
                    connector.relationship = "5";//朋友
                    presenter.addConnector(connector);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 是否存在某个联系人；
     *
     * @param mobile
     * @return
     */
    private boolean hasExist(String mobile)
    {
        if (dataList != null && !isEmpty(mobile))
        {
            for (int i = 0; i < dataList.size(); i++)
            {
                if (mobile.equals(dataList.get(i).getMobile()))
                {
                    toastCustom("不能导入相同的联系人");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onLoadSuccess(List<Connector> connectors)
    {
        swipeRefresh.setRefreshing(false);
        dataList.clear();
        dataList.addAll(connectors);
        adapter = new ConnectorAdapterAbs(dataList);
        listView.setAdapter(adapter);
        addImage.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoadFailed(String s)
    {
        swipeRefresh.setRefreshing(false);
        LogUtil.i(s);
        addImage.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRefresh()
    {
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                initData();
            }
        }, 100);
    }

    class ConnectorAdapterAbs extends AbsBaseListAdapter<Connector>
    {
        ConnectorListHolder connectorListHolder;

        public ConnectorAdapterAbs(List<Connector> list)
        {
            super(list);
        }

        @Override
        protected Holder getHolder(int position)
        {
            connectorListHolder = new ConnectorListHolder(ConnectorManageActivity.this);
            connectorListHolder.setOnCheckChangeListener(new MyConnectorListener());
            return connectorListHolder;
        }
    }

    class MyConnectorListener implements ConnectorListener
    {

        @Override
        public void onCheckChange(Connector connector)
        {
            for (Connector conn : dataList)
            {
                conn.isDefault = false;
            }
            connector.isDefault = true;
            adapter.notifyDataSetChanged();
            presenter.updateConn(connector);
        }

        @Override
        public void onDelete(Connector connector)
        {
            presenter.deleteConn(connector);
        }

        @Override
        public void onUpdate(Connector connector)
        {
            if (connector.contactsId == null)
            {
                connector.relationship = "5";//朋友
                presenter.addConnector(connector);
            } else
            {
                presenter.updateConn(connector);
            }
        }

        @Override
        public void onSelect(Connector connector)
        {
            if (sourceActivity != null && sourceActivity.length() > 0)
            {
                setResult(RESULT_OK, getIntent().putExtra("connector", connector));
                finish();
            }
        }

    }
}
