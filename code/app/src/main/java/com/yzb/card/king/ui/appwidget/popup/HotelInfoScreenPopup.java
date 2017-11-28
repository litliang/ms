package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.adapter.CatalogueTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/2
 * 描  述：
 */
public class HotelInfoScreenPopup implements View.OnClickListener{

    private Activity activity;

    private  BaseFullPP baseBottomFullPP;

    private CatalogueTypeAdapter hotelBrandTypeAdapter;

    private RecyclerView wvLvData;

    private  List<CatalogueTypeBean> monthBeanList;

    private ViewDataCallBack callBack;

    public void setCallBack(ViewDataCallBack callBack)
    {
        this.callBack = callBack;
    }

    public HotelInfoScreenPopup(Activity activity, int defineHeight, BaseFullPP.ViewPostion postion){

        this.activity = activity;

        baseBottomFullPP = new BaseFullPP(activity, postion);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_hotel_info_screen, null);

        LinearLayout llParent = (LinearLayout) view.findViewById(R.id.llParent);

        if (defineHeight > 0) {

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, defineHeight);


            llParent.setLayoutParams(lp);

        }

        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        initData();

        baseBottomFullPP.addChildView(view);


        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
            @Override
            public void onClickListenerDismiss()
            {
//                if(callBack != null){
//
//
//                    callBack.onConfirm(ppAdapter.getSelectorDataList(),currentBarMix,currentBarMax);
//                }

            }
        });
        view.findViewById(R.id.tvClear).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);
    }

    private void initData()
    {

         monthBeanList = new ArrayList<CatalogueTypeBean>();

        String[] screenTypeArray = activity.getResources().getStringArray(R.array.hotel_all_screen_name_array);

        int length = screenTypeArray.length;

        for(int a = 0 ; a< length ; a++){

            CatalogueTypeBean tempBean = new CatalogueTypeBean();

            tempBean.setMutualList(true);

            tempBean.setTypeName(screenTypeArray[a]);

            if(a == 0){

                List<SubItemBean> oneList = new ArrayList<>();

                SubItemBean oneBean = new SubItemBean();

                oneBean.setFilterName("直销");

                oneBean.setLocalDataHotelRoomCode(a);

                oneBean.setFilterId("1");

                oneList.add(oneBean);

                tempBean.setChildList(oneList);

            }else  if(a == 1){

                String[] screenTypeArrayOne = activity.getResources().getStringArray(R.array.hotel_all_screen_pay_name_array);

                int[] screenTypeArrayOneInt = activity.getResources().getIntArray(R.array.hotel_all_screen_pay_value_array);

                List<SubItemBean> oneList = new ArrayList<>();

                int size = screenTypeArrayOne.length;

                for (int b = 0; b <size;b++){

                    SubItemBean oneBean = new SubItemBean();

                    oneBean.setLocalDataHotelRoomCode(a);

                    oneBean.setFilterName(screenTypeArrayOne[b]);

                    oneBean.setFilterId(screenTypeArrayOneInt[b]+"");

                    oneList.add(oneBean);
                }
                tempBean.setChildList(oneList);

            }else  if(a == 2){

                String[] screenTypeArrayOne = activity.getResources().getStringArray(R.array.hotel_all_screen_food_name_array);

                int[] screenTypeArrayOneInt = activity.getResources().getIntArray(R.array.hotel_all_screen_food_value_array);

                List<SubItemBean> oneList = new ArrayList<>();

                int size = screenTypeArrayOne.length;

                for (int b = 0; b <size;b++){

                    SubItemBean oneBean = new SubItemBean();

                    oneBean.setLocalDataHotelRoomCode(a);

                    oneBean.setFilterName(screenTypeArrayOne[b]);

                    oneBean.setFilterId(screenTypeArrayOneInt[b]+"");

                    oneList.add(oneBean);
                }

                tempBean.setChildList(oneList);

            }else  if(a == 3){

                String[] screenTypeArrayOne = activity.getResources().getStringArray(R.array.hotel_all_screen_bed_name_array);

                int[] screenTypeArrayOneInt = activity.getResources().getIntArray(R.array.hotel_all_screen_bed_value_array);

                List<SubItemBean> oneList = new ArrayList<>();

                int size = screenTypeArrayOne.length;

                for (int b = 0; b <size;b++){

                    SubItemBean oneBean = new SubItemBean();

                    oneBean.setLocalDataHotelRoomCode(a);

                    oneBean.setFilterName(screenTypeArrayOne[b]);

                    oneBean.setFilterId(screenTypeArrayOneInt[b]+"");

                    oneList.add(oneBean);
                }
                tempBean.setChildList(oneList);
            }

            monthBeanList.add(tempBean);

        }

        //
        wvLvData.setLayoutManager(new GridLayoutManager(activity, 1));

        hotelBrandTypeAdapter = new CatalogueTypeAdapter(activity, monthBeanList,true,4);


       // hotelBrandTypeAdapter.setItemDataCallBack(itemDataCallBack);

        wvLvData.setAdapter(hotelBrandTypeAdapter);
    }

    public  void show(View rootView){

        baseBottomFullPP.show(rootView);
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.tvClear://清理

                reInitializeData();

                hotelBrandTypeAdapter.reSetView(false);

                break;
            case R.id.tvConfirm://确定

                if(callBack!=null){

                    callBack.onConfirm(calSelectoredList());
                }
                baseBottomFullPP.dismiss();

                break;
            default:
                break;

        }

    }

    public interface ViewDataCallBack {

        void onConfirm(List<SubItemBean> selectedBean);
    }

}
