package com.yzb.card.king.ui.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.sys.GlobalApp;

/**
 * @author ：gengqiyun
 * @date：2016/8/24 城市改变的基类；
 */
public class BaseCityChangeFragment extends BaseFragment implements GlobalApp.OnCityChangeListener
{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        GlobalApp.getInstance().setOnCityChangeListeners(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        CitySelectManager sm = CitySelectManager.getInstance();

        //如果该Fragment  attach的Activity继承了BaseCityChangeActivity,
        // 此处会为空；Activity的onResume先于Fragment的onResume方法调用；
        if (!TextUtils.isEmpty(sm.getPlaceId()))
        {
            cityId = sm.getPlaceId();
            cityName = sm.getPlaceName();
            //触发onCityChange方法；
            GlobalApp.getInstance().setSelectedCity(cityId, cityName);

            onCityChange(GlobalApp.getSelectedCity());

            CitySelectManager.getInstance().clearData();
        }
    }

    /**
     * 子类需要重写，city改变时，刷新数据；
     *
     * @param city
     */
    @Override
    public void onCityChange(Location city)
    {
        //此处必须赋值；否则cityId可能还是旧的；
        cityId = city.cityId;
        cityName = city.cityName;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        //移除城市变化的监听；
        if (app != null)
        {
            app.removeListener(this);
        }
    }
}
