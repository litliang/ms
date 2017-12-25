package com.yzb.card.king.ui.appwidget.picks;

import android.view.View;

import com.jamgle.pickerviewlib.lib.WheelView;
import com.jamgle.pickerviewlib.listener.OnItemSelectedListener;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/2
 * 描  述：
 */
public class CountryCityWheelView {

    public enum Type {
        ALL, COUNTRY, CHINA
    }// 全球、国家信息，中国


    private View timepickerview;


    private WheelView wvCountry, wvProvice, wvCity, wvQuYu;

    private NationalCountryPresenter presenter;

    private List<NationalCountryBean> countryList;

    private List<NationalCountryBean> provinceList;

    private List<NationalCountryBean> cityList;

    private List<NationalCountryBean> areanList;

    private int defaulIndex = 0;

    private Type type;

    private NationalCountryBean countryBean;

    private NationalCountryBean provinceBean;

    private NationalCountryBean cityBean;

    private NationalCountryBean areanBean;

    private boolean specialFlag = true;

    public CountryCityWheelView(View timepickerview, Type type)
    {

        this.type = type;

        this.timepickerview = timepickerview;

        presenter = new NationalCountryPresenter();

        wvCountry = (WheelView) timepickerview.findViewById(R.id.wvCountry);

        wvProvice = (WheelView) timepickerview.findViewById(R.id.wvProvice);

        wvCity = (WheelView) timepickerview.findViewById(R.id.wvCity);

        wvQuYu = (WheelView) timepickerview.findViewById(R.id.wvQuYu);

        init(type);

    }

    /**
     * @return
     */
    public List<NationalCountryBean> getSelectedData()
    {

        List<NationalCountryBean> temp = new ArrayList<NationalCountryBean>();

        if (countryBean != null) {

            temp.add(countryBean);
        }

        if (provinceBean != null) {

            temp.add(provinceBean);
        }

        if (cityBean != null) {

            temp.add(cityBean);
        }

        if (areanBean != null) {

            temp.add(areanBean);
        }

        return temp;
    }

    private void init(Type type)
    {
        if (type == Type.ALL) {

            wvCountry.setVisibility(View.VISIBLE);

            wvProvice.setVisibility(View.VISIBLE);

            wvCity.setVisibility(View.VISIBLE);

            wvQuYu.setVisibility(View.GONE);

            wvCountry.setCyclic(false);

            wvProvice.setCyclic(false);

            wvCity.setCyclic(false);

            wvCountry.setOnItemSelectedListener(countryListener);

            wvProvice.setOnItemSelectedListener(provinceListener);

            wvCity.setOnItemSelectedListener(cityListener);
            //设置国家信息
            CountryObjectWheelAdapter adapter = new CountryObjectWheelAdapter();

            countryList = presenter.selectCountryData();

            adapter.addNewDataList(countryList);

            wvCountry.setAdapter(adapter);

            wvCountry.setCurrentItem(defaulIndex);//设置第一位

            NationalCountryBean contryBean = countryList.get(defaulIndex);

            resetProviceData(contryBean);

        } else if (type == Type.COUNTRY) {

            wvCountry.setVisibility(View.VISIBLE);

            wvProvice.setVisibility(View.GONE);

            wvCity.setVisibility(View.GONE);

            wvQuYu.setVisibility(View.GONE);

            wvCountry.setCyclic(false);

            CountryObjectWheelAdapter adapter = new CountryObjectWheelAdapter();

            countryList = presenter.selectCountryData();

            countryBean = countryList.get(defaulIndex);

            adapter.addNewDataList(countryList);

            wvCountry.setAdapter(adapter);

            wvCountry.setCurrentItem(defaulIndex);

            wvCountry.setOnItemSelectedListener(countryListener);

        } else if (type == Type.CHINA) {

            specialFlag = false;

            wvCountry.setVisibility(View.GONE);

            wvProvice.setVisibility(View.VISIBLE);

            wvCity.setVisibility(View.VISIBLE);

            wvQuYu.setVisibility(View.VISIBLE);

            wvCountry.setCyclic(false);

            wvProvice.setCyclic(false);

            wvCity.setCyclic(false);

            wvQuYu.setCyclic(false);

            wvCountry.setOnItemSelectedListener(countryListener);

            wvProvice.setOnItemSelectedListener(provinceListener);

            wvCity.setOnItemSelectedListener(cityListener);

            wvQuYu.setOnItemSelectedListener(areanListener);

            NationalCountryBean ben = presenter.selectOneDataByNameFromDb("中国");

            if(ben != null){
                //设置省份数据
                resetProviceData(ben);
            }
        }

    }

    /**
     * 国家信息监听
     */
    private OnItemSelectedListener countryListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index)
        {
            if (countryList.size() > 0) {

                if (type == Type.ALL) {

                    NationalCountryBean contryBean = countryList.get(index);

                    resetProviceData(contryBean);
                }

                if (countryList != null) {

                    countryBean = countryList.get(index);
                }
            }
        }
    };

    /**
     * 省份信息监听
     */
    private OnItemSelectedListener provinceListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index)
        {

            if (provinceList.size() > 0) {


                if (type == Type.ALL || type == Type.CHINA) {
                    //设置城市信息
                    resetCityData(index);
                }

                if (provinceList != null)
                    provinceBean = provinceList.get(index);
            }
        }
    };

    /**
     * 城市信息监听
     */
    private OnItemSelectedListener cityListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index)
        {


            int size = cityList.size();

            if (size > 0) {
                cityBean = cityList.get(index);

                if (type == Type.CHINA) {//中国信息展示区域

                    resetAreanData(index);

                }
            }
        }
    };


    /**
     * 区域信息监听
     */
    private OnItemSelectedListener areanListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index)
        {

            if (areanList != null && areanList.size() > 0) {

                areanBean = areanList.get(index);
            }
        }
    };

    /**
     * 重新设置省份数据
     *
     * @param index
     */
    public void resetProviceData(NationalCountryBean index)
    {

        countryBean = index;

        int parentId = index.getCityId();

        provinceList = presenter.selectAllDataForParentIdFromDb(parentId + "");

        if (provinceList != null && provinceList.size() > 0) {

            //设置省份州信息
            CountryObjectWheelAdapter provinceAdapter = new CountryObjectWheelAdapter();

            provinceAdapter.addNewDataList(provinceList);

            wvProvice.setAdapter(provinceAdapter);

            wvProvice.setCurrentItem(defaulIndex);//设置第一位

            //设置城市信息
            resetCityData(defaulIndex);


        } else {

            CountryObjectWheelAdapter provinceAdapter = new CountryObjectWheelAdapter();

            provinceAdapter.clearDataList();

            wvProvice.setAdapter(provinceAdapter);

            CountryObjectWheelAdapter wvAreaAdapter1 = new CountryObjectWheelAdapter();

            wvAreaAdapter1.clearDataList();

            wvCity.setAdapter(wvAreaAdapter1);

            CountryObjectWheelAdapter wvAreaAdapter2 = new CountryObjectWheelAdapter();

            wvAreaAdapter2.clearDataList();

            wvQuYu.setAdapter(wvAreaAdapter2);
        }
    }

    /**
     * 设置城市数据
     *
     * @param index
     */
    private void resetCityData(int index)
    {
        NationalCountryBean provinceBean = provinceList.get(index);

        this.provinceBean = provinceBean;

        String cityName = provinceBean.getCityName();

        int provinceParentId = provinceBean.getCityId();
        /*
           检查省级信息是不是直辖市
         */
        if (AppUtils.ifSpecialCityInfo(cityName)&&specialFlag) {

            List<NationalCountryBean> list = presenter.selectAllDataForParentIdFromDb(provinceParentId + "");

            int size = list.size();

            if (list != null && size >= 1) {

                NationalCountryBean tempSpecialCity = list.get(0);

                int tempSpecialCityCityId = tempSpecialCity.getCityId();

                loadThirdInfo(tempSpecialCityCityId);

            }

        } else {

            loadThirdInfo(provinceParentId);

        }
    }

    /**
     * 设置城市下的区域信息
     *
     * @param index
     */
    private void resetAreanData(int index)
    {
        cityBean = cityList.get(index);

        int cityId = cityBean.getCityId();
        //加载城市下，区域信息
        areanList = presenter.selectAllDataForParentIdFromDb(cityId + "");


        if (areanList != null && areanList.size() > 0) {

            CountryObjectWheelAdapter wvAreaAdapter = new CountryObjectWheelAdapter();

            wvAreaAdapter.addNewDataList(areanList);

            wvQuYu.setAdapter(wvAreaAdapter);

            wvQuYu.setCurrentItem(defaulIndex);//设置第一位

            areanBean = areanList.get(defaulIndex);

        } else {

            CountryObjectWheelAdapter wvAreaAdapter = new CountryObjectWheelAdapter();

            wvAreaAdapter.clearDataList();

            wvQuYu.setAdapter(wvAreaAdapter);

        }

    }

    /**
     * 加载省或者直辖市下级数据
     *
     * @param parentId
     */
    private void loadThirdInfo(int parentId)
    {
        cityList = presenter.selectAllDataForParentIdFromDb(parentId + "");

        int size = cityList.size();

        if (cityList != null && size > 0) {

            CountryObjectWheelAdapter wvAreaAdapter = new CountryObjectWheelAdapter();

            wvAreaAdapter.addNewDataList(cityList);

            wvCity.setAdapter(wvAreaAdapter);

            wvCity.setCurrentItem(defaulIndex);//设置第一位

            cityBean = cityList.get(defaulIndex);

            resetAreanData(defaulIndex);

        } else {

            CountryObjectWheelAdapter wvAreaAdapter = new CountryObjectWheelAdapter();

            wvAreaAdapter.clearDataList();

            wvCity.setAdapter(wvAreaAdapter);

            CountryObjectWheelAdapter wvAreaAdapter1 = new CountryObjectWheelAdapter();

            wvAreaAdapter1.clearDataList();

            wvQuYu.setAdapter(wvAreaAdapter1);

        }
    }
}
