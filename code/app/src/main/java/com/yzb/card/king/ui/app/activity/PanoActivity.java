package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.mapapi.model.LatLng;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.LogUtil;


/**
 * 类  名：全景导航；
 * 作  者：gengqiyun
 * 日  期：2017/1/5
 * 描  述：
 */
public class PanoActivity extends BaseActivity implements View.OnClickListener
{
    private PanoramaView panoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        //先初始化BMapManager
        initBMapManager();
        setContentView(R.layout.activity_pano);
        initView();
    }

    /**
     * 初始化BMapManager
     */
    private void initBMapManager()
    {
        GlobalApp app = (GlobalApp) getApplication();
        if (app.mBMapManager == null)
        {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new GlobalApp.MyGeneralListener());
        }
    }

    private void initView()
    {
        setHeader(R.mipmap.icon_back_white, getString(R.string.map_pano));
        findViewById(R.id.headerLeft).setOnClickListener(this);

        panoramaView = (PanoramaView) findViewById(R.id.panoramaView);

        Parcelable latLng = getIntent().getParcelableExtra("latlng");

        if (latLng != null)
        {
            LatLng localLatLng = (LatLng) latLng;
            panoramaView.setPanorama(localLatLng.longitude, localLatLng.latitude);
        }
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
    protected void onPause()
    {
        super.onPause();
        panoramaView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        panoramaView.onResume();
    }

    @Override
    protected void onDestroy()
    {
        panoramaView.destroy();
        super.onDestroy();
    }
}
