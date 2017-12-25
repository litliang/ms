package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.ui.appwidget.popup.adapter.CatalogueTypeAdapter;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.persenter.FilterListPersenter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类  名：筛选
 * 作  者：Li Yubing
 * 日  期：2017/7/22
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_screen)
public class HotelScreenActivity extends BaseActivity implements View.OnClickListener,BaseViewLayerInterface {

    @ViewInject(R.id.wvLvData)
    private  RecyclerView wvLvData;

    private    CatalogueTypeAdapter    hotelBrandTypeAdapter;

    private    List<CatalogueTypeBean> monthBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitleNmae(R.string.hotel_screen);

        findViewById(R.id.tvClear).setOnClickListener(this);

        findViewById(R.id.tvConfirm).setOnClickListener(this);

        wvLvData.setLayoutManager(new GridLayoutManager(this, 1));

        List<CatalogueTypeBean> monthBeanList = new ArrayList<CatalogueTypeBean>();

        hotelBrandTypeAdapter = new CatalogueTypeAdapter(this, monthBeanList);

        wvLvData.setAdapter(hotelBrandTypeAdapter);


        initRequest();
    }

    /**
     * 初始观察者请求
     */
    private void initRequest() {

        FilterListPersenter  persenter = new FilterListPersenter(this);

        persenter.sendHotelScreenFilterRequest("1",cityId);

        showPDialog("正在请求数据……");
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.tvClear://清空数据

                    reInitializeData();

                   hotelBrandTypeAdapter.addNewData(monthBeanList);

                break;
            case R.id.tvConfirm://确定

                List<SubItemBean>  selectorList =  calSelectoredList();

                CatalogueTypeBean bean = new CatalogueTypeBean();

                bean.setChildList(selectorList);

                Intent intent = new Intent();

                intent.putExtra("transmitData",bean);

                setResult(1001,intent);

                finish();

                break;
            default:
                break;


        }
    }

    /**
     * 设置初始化数据
     */
    public  void reInitializeData(){

        for (CatalogueTypeBean brandTypeBean :monthBeanList){

            List<SubItemBean>  list =   brandTypeBean.getChildList();

            for(SubItemBean brandBean :list){

                boolean flag = brandBean.isDefault();

                if(flag){

                    brandBean.setDefault(false);
                }
            }

        }
    }

    /**
     * 计算出选择内容
     */
    public  List<SubItemBean> calSelectoredList(){

        List<SubItemBean> selectorList = new ArrayList<SubItemBean>();

        for (CatalogueTypeBean brandTypeBean :monthBeanList){

            List<SubItemBean>  list =   brandTypeBean.getChildList();

            for(SubItemBean brandBean :list){

                boolean flag = brandBean.isDefault();

                if(flag){

                    selectorList.add(brandBean);
                }
            }

        }

        return selectorList ;
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        dimissPdialog();

        if(type ==-1){

            List<CatalogueTypeBean>  catalogueTypeBeanList = JSONArray.parseArray(o+"",CatalogueTypeBean.class);

            loadUiData(catalogueTypeBeanList);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {

        dimissPdialog();

        if(type ==-1){

            loadUiData(null);
        }
    }

    private void loadUiData(List<CatalogueTypeBean> catalogueTypeBeanList){

        if(catalogueTypeBeanList == null){

            catalogueTypeBeanList = new ArrayList<CatalogueTypeBean>();

        }else {

        }

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        //检测是否选择
        List<SubItemBean> hotelBaseFilterList =  productListParam.getHotelBaseFilterList();

        if(hotelBaseFilterList.size() > 0){

            for(CatalogueTypeBean catalogueTypeBean : catalogueTypeBeanList){

                List<SubItemBean> childList =  catalogueTypeBean.getChildList();

                for(SubItemBean targetBean : childList ){

                    for(SubItemBean bean : hotelBaseFilterList){

                        if(targetBean.getFilterId().equals(bean.getFilterId()) &&
                                targetBean.getChildTypeCode().equals(bean.getChildTypeCode())&&
                                targetBean.getFilterName().equals(bean.getFilterName())&&
                                targetBean.getFilterLng()== bean.getFilterLng() &&
                                targetBean.getFilterLat() == bean.getFilterLng()){

                            targetBean.setDefault(true);

                            break;
                        }else {

                            targetBean.setDefault(false);

                        }

                    }

                }

            }


        }


        String[] strArray = new String[]{"评分", "促销优惠"};

        for (int i = 0; i < strArray.length; i++) {

            CatalogueTypeBean bean = new CatalogueTypeBean();

            bean.setTypeName(strArray[i]);

            List<SubItemBean> brandBeanList = new ArrayList<>();

            if (i == 0) {//評分

                String[]  gradeArray =  getResources().getStringArray(R.array.hotel_grade_name_array);

                String[] gradeValueArray = getResources().getStringArray(R.array.hotel_grade_value_array);

                bean.setMutualList(true);

                String minVoteStr = productListParam.getMinVote();

                for (int j = 0; j < gradeArray.length; j++) {

                    SubItemBean brandBean = new SubItemBean();

                    brandBean.setFilterName(gradeArray[j]);

                    String grayValueStr = gradeValueArray[j];

                    if(TextUtils.isEmpty(minVoteStr) || !minVoteStr.equals(grayValueStr)){

                        brandBean.setDefault(false);

                    }else {

                        brandBean.setDefault(true);
                    }

                    brandBean.setFilterId(grayValueStr);

                    brandBean.setLocalDataCode(1);

                    brandBeanList.add(brandBean);
                }

                bean.setChildList(brandBeanList);

                catalogueTypeBeanList.add(0,bean);

            } else if (i == 1) {//促销优惠

                String[]  promotionArray =  getResources().getStringArray(R.array.promotion_type_name_array);

                int[] promotionValueArray = getResources().getIntArray(R.array.promotion_type_value_array);

                Date nowDate = new Date();

                Date startDate = DateUtil.string2Date(productListParam.getArrDate(), DateUtil.DATE_FORMAT_DATE);

                String startDateStr = DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE3);

                String nowDateStr = DateUtil.date2String(nowDate, DateUtil.DATE_FORMAT_DATE3);

                int j = 1;

                if(startDateStr.equals(nowDateStr)){//如果入住日期是当前日期，则需要今日甩房

                    j = 0;
                }

                String disTypesStr = productListParam.getDisTypes();

                String[] array = null ;

                if(!TextUtils.isEmpty(disTypesStr)){

                    array = disTypesStr.split(",");
                }

                for ( ; j < promotionArray.length; j++) {

                    SubItemBean brandBean = new SubItemBean();

                    brandBean.setFilterName(promotionArray[j]);

                    String promotionValueArrayStr = promotionValueArray[j]+"";

                    brandBean.setFilterId(promotionValueArrayStr);

                    if(array != null){

                        for(String str : array){

                            if(promotionValueArrayStr.equals(str)){

                                brandBean.setDefault(true);

                                break;

                            }else {

                                brandBean.setDefault(false);
                            }
                        }

                    }else {


                        brandBean.setDefault(false);
                    }



                    brandBean.setLocalDataCode(2);

                    brandBeanList.add(brandBean);
                }
                bean.setChildList(brandBeanList);

                catalogueTypeBeanList.add(1,bean);
            }
        }


        monthBeanList = catalogueTypeBeanList;

        hotelBrandTypeAdapter.addNewData(monthBeanList);
    }
}
