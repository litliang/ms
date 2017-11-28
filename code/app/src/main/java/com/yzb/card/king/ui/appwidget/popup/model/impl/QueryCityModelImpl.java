package com.yzb.card.king.ui.appwidget.popup.model.impl;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.popup.model.QueryCityModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.other.bean.IPlace;
import com.yzb.card.king.ui.other.bean.Place;
import com.yzb.card.king.ui.other.listeners.QueryPlaceListener;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：城市查询；
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class QueryCityModelImpl implements QueryCityModel
{
    private BaseMultiLoadListener loadListener;
    private DbManager dbManager;
    private QueryPlaceListener<IPlace> queryPlaceListener;
    public List<IPlace> hotList;
    private SimpleRequest<List<City>> request;
    private Map<String, Object> paramMap;
    private List<City> allCity = new ArrayList<>();

    public QueryCityModelImpl(BaseMultiLoadListener listener, QueryPlaceListener<IPlace> queryPlaceListener)
    {
        loadListener = listener;
        this.queryPlaceListener = queryPlaceListener;
    }

    /**
     * 新的统计
     * @param industryId
     * @param cityId
     */
    public void hotCityStatistics(int industryId, String cityId)
    {
        SimpleRequest simpleRequest = new SimpleRequest(CardConstant.HOTADDRSTATISTICS);
        Map<String, Object> map = new HashMap<>();
        map.put("industryId", industryId);
        map.put("addrId", cityId);
        simpleRequest.setParam(map);
        simpleRequest.sendRequest(null);
    }

    @Override
    public void getCities()
    {
        request = getListSimpleRequest(CardConstant.QUREY_BASE);
        paramMap = new HashMap<>();
        paramMap.put("regionId", "1");
        paramMap.put("type", "1");
        request.setParam(paramMap);
        request.sendRequestNew(new BaseCallBack<List<City>>()
        {
            @Override
            public void onSuccess(List<City> data)
            {
                LogUtil.e("國內城市信息查詢成功");
                allCity.clear();
                allCity.addAll(data);
                paramMap.put("type", "2");
                request.sendRequestNew(new BaseCallBack<List<City>>()
                {
                    @Override
                    public void onSuccess(List<City> data)
                    {
                        LogUtil.e("國外城市信息查詢成功");
                        allCity.addAll(data);
                        new SaveCityThread(allCity).start();
                    }

                    @Override
                    public void onFail(Error error)
                    {
                        LogUtil.e("國外城市信息查詢失敗");
                    }
                });

            }

            @Override
            public void onFail(Error error)
            {
                LogUtil.e("國內城市信息查詢失敗");
            }
        });
    }

    @NonNull
    private SimpleRequest<List<City>> getListSimpleRequest(String queryHotCity)
    {
        return new SimpleRequest<List<City>>(queryHotCity)
        {
            @Override
            protected List<City> parseData(String data)
            {
                return JSON.parseArray(data, City.class);
            }
        };
    }

    class SaveCityThread extends Thread
    {
        List<City> cities;

        public SaveCityThread(List<City> cities)
        {
            this.cities = cities;
        }

        @Override
        public void run()
        {
            saveCity(getIPlaces(cities));
        }
    }

    public List<IPlace> getIPlaces(List<City> list)
    {
        List<IPlace> iPlaces = new ArrayList<>();
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                iPlaces.add(list.get(i));
            }
        }
        return iPlaces;
    }

    private void saveCity(List<IPlace> IPlaces)
    {
        initDb();
        try
        {
            dbManager.dropTable(City.class);
            dbManager.save(IPlaces);
            LogUtil.e("城市信息保存成功");
        } catch (DbException e)
        {
            LogUtil.e("城市信息保存失敗" + e.getMessage());
        }
    }

    @Override
    public void queryCity(String type, String key)
    {
        initDb();
        try
        {
            final List<City> cities = new ArrayList<>();
            if (TextUtils.isEmpty(key))
            {
                Selector selector = dbManager.selector(City.class);
                List<City> list = selector.where("type", "=", type).and("cityLevel", "=", 4).findAll();

                if (list != null)
                {
                    cities.addAll(list);
                } else
                {
                    UiUtils.shortToast("没查到数据");
                }
            } else
            {
                key = "%" + key + "%";
                String sql = "select * from city where (cityRuby like \'" + key
                        + "\'  or firstSpell like \'" + key + "\') and type = "
                        + type + " and cityLevel = " + 4;
                LogUtil.e(sql);
                Cursor cursor = dbManager.execQuery(sql);
                while (cursor.moveToNext())
                {
                    City city = new City();
                    city.setCityId(cursor.getString(cursor.getColumnIndex("cityId")));
                    city.setType(cursor.getString(cursor.getColumnIndex("type")));
                    city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
                    city.setFirstSpell(cursor.getString(cursor.getColumnIndex("firstSpell")));
                    city.setCityRuby(cursor.getString(cursor.getColumnIndex("cityRuby")));
                    city.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
                    city.setLng(cursor.getDouble(cursor.getColumnIndex("lng")));
                    city.setCityLevel(cursor.getInt(cursor.getColumnIndex("cityLevel")));
                    cities.add(city);
                }
            }

            SimpleRequest<List<City>> request = getListSimpleRequest(CardConstant.QUERY_HOT_CITY);
            Map<String, Object> param = new HashMap<>();
            param.put("regionId", "1");
            param.put("type", type);
            param.put("industryId", GlobalVariable.industryId);
            request.setParam(param);
            request.sendRequestNew(new BaseCallBack<List<City>>()
            {
                @Override
                public void onSuccess(List<City> data)
                {
                    hotList = getIPlaces(data);
                    onFinish();
                }

                private void onFinish()
                {
                    Map<String, List<IPlace>> map = getCityMap(cities);
                    if (queryPlaceListener != null)
                    {
                        queryPlaceListener.onQuerySuccess(map);
                    }
                }

                @Override
                public void onFail(Error error)
                {
                    onFinish();
                }
            });

        } catch (DbException e)
        {
            LogUtil.e(e.getMessage());
            if (queryPlaceListener != null)
            {
                queryPlaceListener.onQueryFail();
            }
        }

    }

    /**
     * 将list 转为map
     *
     * @param cities
     * @return
     */
    private Map<String, List<IPlace>> getCityMap(List<City> cities)
    {
        Map<String, List<IPlace>> map = new HashMap<>();

        map.put("热门", hotList);

        if (cities != null)
        {
            for (int i = 0; i < cities.size(); i++)
            {
                Place city = cities.get(i);

                String cityRuby = city.getFirstSpell();

                if (cityRuby != null && cityRuby.length() > 0)
                {
                    List<IPlace> list = map.get(cityRuby.substring(0, 1));

                    if (list == null)
                    {
                        list = new ArrayList<>();

                        map.put(cityRuby.substring(0, 1), list);
                    }
                    list.add(city);
                }
            }
        }
        return map;
    }

    private void initDb()
    {
        if (dbManager == null)
        {
            dbManager = x.getDb(new DbManager.DaoConfig());
        }
    }
}
