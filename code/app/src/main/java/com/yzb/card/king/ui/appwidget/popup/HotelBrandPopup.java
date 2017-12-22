package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.adapter.CatalogueTypeAdapter;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.persenter.FilterListPersenter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名： 酒店品牌
 * 作  者：Li Yubing
 * 日  期：2017/7/20
 * 描  述：目录类型 --- 经济邢、 舒适型、豪华型
 */
public class HotelBrandPopup implements View.OnClickListener, BaseViewLayerInterface {

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private List<CatalogueTypeBean> monthBeanList = new ArrayList<CatalogueTypeBean>();

    private RecyclerView wvLvData;

    private CatalogueTypeAdapter hotelBrandTypeAdapter;

    private HotelBrandPopup.ViewDataCallBack callBack;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     * @param industryFun  酒店（1酒店列表；2礼品套餐；）
     */
    public HotelBrandPopup(Activity activity, int defineHeight, String industryFun) {

        this.activity = activity;

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_TOP);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_hotel_brand_popup, null);


        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(activity, defineHeight));


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

//        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
//            @Override
//            public void onClickListenerDismiss()
//            {
//
//                if(callBack!=null){
//
//                    callBack.onConfirm(calSelectoredList());
//                }
//            }
//        });

        view.findViewById(R.id.tvClear).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        wvLvData.setLayoutManager(new GridLayoutManager(activity, 1));

        hotelBrandTypeAdapter = new CatalogueTypeAdapter(activity, monthBeanList);

        wvLvData.setAdapter(hotelBrandTypeAdapter);

        initRequest(industryFun);
    }

    /**
     * 初始观察者请求
     */
    private void initRequest(String industryFun) {

        FilterListPersenter persenter = new FilterListPersenter(this);

        persenter.sendHotelBrandRequest(industryFun, GlobalApp.getInstance().getPositionedCity().getCityId());

        ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", activity, true);
    }

    public void showPP(View rootView) {

        baseBottomFullPP.showBottomAsView(rootView);
    }

    /**
     * 计算出选择内容
     */
    public List<SubItemBean> calSelectoredList() {

        List<SubItemBean> selectorList = new ArrayList<SubItemBean>();

        for (CatalogueTypeBean brandTypeBean : monthBeanList) {

            List<SubItemBean> list = brandTypeBean.getChildList();

            for (SubItemBean brandBean : list) {

                boolean flag = brandBean.isDefault();

                if (flag) {

                    selectorList.add(brandBean);
                }
            }


        }

        return selectorList;
    }

    /**
     * 设置初始化数据
     */
    public void reInitializeData() {

        for (CatalogueTypeBean brandTypeBean : monthBeanList) {

            List<SubItemBean> list = brandTypeBean.getChildList();

            for (SubItemBean brandBean : list) {

                boolean flag = brandBean.isDefault();

                if (flag) {

                    brandBean.setDefault(false);
                }
            }

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvClear://清理

                reInitializeData();

                hotelBrandTypeAdapter.reSetView(false);

                break;
            case R.id.tvConfirm://确定

                if (callBack != null) {

                    selectedData = calSelectoredList();

                    callBack.onConfirm(selectedData);
                }
                baseBottomFullPP.dismiss();

                break;
            default:
                break;

        }
    }

    public void setCallBack(ViewDataCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == -1) {

            List<CatalogueTypeBean> catalogueTypeBeanList = JSONArray.parseArray(o + "", CatalogueTypeBean.class);

            this.monthBeanList = catalogueTypeBeanList;

            hotelBrandTypeAdapter.addNewData(monthBeanList);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {

        ProgressDialogUtil.getInstance().closeProgressDialog();

    }

    private List<SubItemBean> selectedData;

    public void showSelectedData() {

        if (selectedData != null && selectedData.size() > 0) {

            reInitializeData();

            for (SubItemBean bean : selectedData) {

                for (CatalogueTypeBean brandTypeBean : monthBeanList) {

                    List<SubItemBean> list = brandTypeBean.getChildList();

                    for (SubItemBean brandBean : list) {

                        if (bean.ifSame(brandBean)) {

                            brandBean.setDefault(true);

                            break;
                        }
                    }
                }
            }

            hotelBrandTypeAdapter.addNewData(monthBeanList);

        }
    }

    public interface ViewDataCallBack {

        void onConfirm(List<SubItemBean> selectedBean);
    }

}
