package com.yzb.card.king.ui.luxury.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.popup.presenter.ChannelPresenter;
import com.yzb.card.king.ui.appwidget.popup.presenter.ChannelUpdatePresenter;
import com.yzb.card.king.ui.appwidget.popup.presenter.impl.ChannelPresenterImpl;
import com.yzb.card.king.ui.appwidget.popup.presenter.impl.ChannelUpdatePresenterImpl;
import com.yzb.card.king.ui.appwidget.popup.view.ChannelUpdateView;
import com.yzb.card.king.ui.appwidget.popup.view.ChannelView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.adapter.ChannelAdapter;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 频道编辑
 *
 * @author gengqiyun
 * @date 2016/4/23
 */
public class ChannelActivity extends BaseActivity implements ChannelView, ChannelUpdateView, View.OnClickListener
{
    public static final int SAVE_CHNNEL_OK = 100;
    public static final String SP_KEY = "SP_KEY";
    private static final int COLUM_NUM = 4;//列数；
    private RecyclerView recyclerView;
    private List<ChildTypeBean> myItems;
    private List<ChildTypeBean> allItems;
    private String typeParentId;
    private ChannelAdapter adapter;
    private String category; //1:信用卡，2：积分宝；3：商户优惠；
    private String spKey; //SharePreferance中的key；

    private ChannelPresenter channelPresenter;
    private ChannelUpdatePresenter channelUpdatePresenter;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        channelPresenter = new ChannelPresenterImpl(this);
        channelUpdatePresenter = new ChannelUpdatePresenterImpl(this);
        init();
        getAllChildList();
    }

    private void init()
    {
        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        if (bundle != null)
        {
            typeParentId = bundle.getString("typeParentId");
            category = bundle.getString("category");
            spKey = bundle.getString(SP_KEY, "");
        }
        LogUtil.i("ChannelActivity typeParentId==" + typeParentId + ",category=" + category);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //取本地数据；
        if (!isEmpty(spKey))
        {
            String spValue = SharePrefUtil.getValueFromSp(this, spKey, "[]");
            myItems = JSON.parseArray(spValue, ChildTypeBean.class);
        }

        findViewById(R.id.panel_back).setOnClickListener(this);

        // 最多4列；
        GridLayoutManager manager = new GridLayoutManager(this, COLUM_NUM);
        recyclerView.setLayoutManager(manager);
        //===============
        recyclerView.setHasFixedSize(true);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        //设置每个item占用的跨度；
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                int viewType = adapter.getItemViewType(position);
                //如果是显示选单或隐藏选单，跨度为4，占一整行，其他情况跨度为1；
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        initViewData();
    }

    public void initViewData()
    {
        if (myItems == null)
        {
            myItems = new ArrayList<>();
        }
        if (allItems == null)
        {
            allItems = new ArrayList<>();
        }
        LogUtil.i("myItems==" + JSON.toJSONString(myItems));
        LogUtil.i("allItems==" + JSON.toJSONString(allItems));

        adapter = new ChannelAdapter(this, helper, myItems, allItems);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetChannelSucess(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
    {
        if (displayChannelList != null)
        {
            displayChannelList.addAll(hideChannelList);
            //剔除旧的；
            myItems = CommonUtil.filterList(myItems, displayChannelList);
            //筛选出未选择项；
            allItems = CommonUtil.filterList2(myItems, displayChannelList);

            if (allItems != null)
            {
                // adapter.notifyItemInserted(myItems.size());
//                adapter.notifyItemRangeChanged(0, myItems.size() + allItems.size());
            }
            initViewData();
        }
    }

    @Override
    public void onGetChannelFail(String failMsg)
    {
        //获取所有频道失败；
    }

    private void getAllChildList()
    {
        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        if (bundle == null)
        {
            return;
        }
        String typeIdEmp = bundle.getString("typeId");
        //来源于跟团游页面；
        String targetTypeId = TextUtils.isEmpty(typeIdEmp) ? typeParentId : typeIdEmp;
        final String finalTempTypeId = targetTypeId;

        Map<String, Object> param = new HashMap<>();
        param.put("parentId", finalTempTypeId);
        param.put("category", category);
        LogUtil.i("个人频道-提交参数:" + param);
        channelPresenter.loadData(param);
    }

    @Override
    public void onClick(View v)
    {
        save();
        go();
    }

    private Handler handler = new Handler();

    public void go()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                setResult(SAVE_CHNNEL_OK);
                finish();
            }
        }, 100);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            save();
            go();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void save()
    {
        //为空，不保存；
        if (myItems == null || myItems.size() == 0)
        {
            return;
        }
        saveToServer();
        SharePrefUtil.saveToSp(getApplicationContext(), spKey, JSON.toJSONString(myItems));
    }

    @Override
    public void onChannelUpdateSucess()
    {

    }

    @Override
    public void onChannelUpdateFail()
    {

    }

    /**
     * 保存到服务器；
     */
    private void saveToServer()
    {
        if (myItems == null || myItems.size() == 0)
        {
            return;
        }
        for (int i = 0; i < myItems.size(); i++)
        {
            myItems.get(i).status = "1";
        }
        //保存的json对象；
        final String saveJson = JSON.toJSONString(myItems);

        Map<String, Object> param = new HashMap<>();
        param.put("channelList", saveJson);
        LogUtil.i("更新提交参数==" + saveJson);
        channelUpdatePresenter.loadData(param);
    }

}
