package com.yzb.card.king.ui.bonus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.ui.bonus.bean.BounsThemeParam;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.bonus.adapter.BounsThemeAdapter;
import com.yzb.card.king.ui.bonus.presenter.BounsThemePresenter;
import com.yzb.card.king.ui.bonus.view.BounsThemeView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.List;


/**
 * 我的-->红包主题；
 *
 * @author gengqiyun
 * @date 2016.12.21
 */
@ContentView(R.layout.activity_bouns_theme)
public class BounsThemeActivity extends BaseActivity implements View.OnClickListener, BounsThemeView, SwipeRefreshLayout.OnRefreshListener
{
    public static final String INTENT_FLAG = "intentFlag";

    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;

    @ViewInject(R.id.gvThemes)
    private SpecHeightGridView gvThemes;

    private BounsThemePresenter themePresenter;

    private BounsThemeAdapter adapter;

    private BounsThemeParam themeParam;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        themePresenter = new BounsThemePresenter(this);

        Serializable ser = getIntent().getSerializableExtra("bounsParam");
        if (ser != null)
        {
            themeParam = (BounsThemeParam) ser;
        }

        initView();
        initData(true);
    }

    private void initView()
    {
        setHeader(R.mipmap.icon_back_white, getString(R.string.bouns_theme));

        findViewById(R.id.headerLeft).setOnClickListener(this);

        gvThemes.setFocusable(false);

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        adapter = new BounsThemeAdapter(this,bounsThemeHandler);

        gvThemes.setAdapter(adapter);

        findViewById(R.id.tvPreScan).setOnClickListener(this);

        findViewById(R.id.tvOk).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:  //左侧点击；

                finish();

                break;
            case R.id.tvPreScan:  //预览；

                BounsThemeBean scanThemeBean = adapter.getSelectItem();

                if (scanThemeBean != null)
                {
                    Intent detailIntent = new Intent(this, RedBagDetailSendActivity.class);

                    themeParam.setBlessWord(scanThemeBean.getBlessWord());

                    themeParam.setReceiveImageCode(scanThemeBean.getCloseImageCode());

                    themeParam.setThemeName(scanThemeBean.getThemeName());

                    themeParam.setOpenImageCode(scanThemeBean.getOpenImageCode());

                    detailIntent.putExtra("themeParam", themeParam);

                    startActivity(detailIntent);

                } else
                {
                    toastCustom(R.string.select_theme);
                }
                break;
            case R.id.tvOk:  //确定；

                BounsThemeBean themeBean = adapter.getSelectItem();

                if (themeBean != null)
                {
                    Intent intent = new Intent();

                    intent.putExtra("backThemeData", themeBean);

                    setResult(Activity.RESULT_OK, intent);

                    finish();

                } else
                {
                    toastCustom(R.string.select_theme);
                }
                break;
        }
    }

    private void initData(final boolean isRefresh)
    {
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                loadData(isRefresh);
            }
        }, 150);
    }

    private void loadData(boolean isRefresh)
    {

        String  pageStart = isRefresh ? "0" : adapter.getCount()+"";
        themePresenter.setInterfaceParameters(pageStart);
    }

    @Override
    public void onGetBounsThemeSuc(boolean event_tag, List<BounsThemeBean> list)
    {
        swipeRefresh.setRefreshing(false);
        if (event_tag)
        {
            adapter.clearAll();
        }

        //添加一个自定义红包主题
        list.add(null);
        adapter.appendALL(list);

        adapter.selectItem(themeParam.getThemeId());
    }

    @Override
    public void onGetBounsThemeFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(failMsg);
    }

    @Override
    public void onRefresh()
    {
        initData(true);
    }

    private Handler bounsThemeHandler = new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            Intent intent = new Intent(BounsThemeActivity.this,DefinethemeBounsActivity.class);

            intent.putExtra(DefinethemeBounsActivity.ActivityData,themeParam);

            startActivityForResult(intent,1000);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            //获取自定义主题红包信息
            if(resultCode == 1001 && data != null){

                    BounsThemeBean themeBean = (BounsThemeBean) data.getSerializableExtra("data");

                    Intent intent = new Intent();

                    intent.putExtra("backThemeData", themeBean);

                    setResult(Activity.RESULT_OK, intent);

                    finish();
            }

        }

    }
}
