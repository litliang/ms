package com.yzb.card.king.ui.base;

import android.os.Bundle;

import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.MoreFunctionPublicTitleView;
import com.yzb.card.king.util.StringUtils;

/**
 * @author ：gengqiyun
 * @date：2016/8/24 城市改变的基类；
 */
public class BaseCityChangeActivity extends BaseActivity implements GlobalApp.OnCityChangeListener {

    public MoreFunctionPublicTitleView moreFunctionPublicTitleView;

    public GlobalApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalApp.getInstance().setOnCityChangeListeners(this);
        app = (GlobalApp) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CitySelectManager manager = CitySelectManager.getInstance();
        if (!StringUtils.isEmpty(manager.getPlaceId())) {

            String cityId = manager.getPlaceId();

            String cityName = manager.getPlaceName();

            GlobalApp.getInstance().setSelectedCity(cityId, cityName);

            if (moreFunctionPublicTitleView != null) {

                moreFunctionPublicTitleView.setCityName(cityName);
            }

            manager.clearData();
        }
    }

    @Override
    public void onCityChange(Location city) {
        //do nothing;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (moreFunctionPublicTitleView != null) {
//            moreFunctionPublicTitleView.unRegistListener();
//        }

        //移除城市变化的监听；
        if (app != null) {
            app.removeListener(this);
        }
    }
}
