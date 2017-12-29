package com.yzb.card.king.ui.manage;

import com.yzb.card.king.bean.hotel.HotelLevelBean;

import java.util.List;

/**
 * Created by 玉兵 on 2017/12/28.
 */

public class HotelPopupRecordDataInstance {

    private static HotelPopupRecordDataInstance instance;

    /**
     * 记录用户操作酒店的星级和价格popup的数据信息
     */
    private List<HotelLevelBean> selectedList;

    private  int selectedCurrentBarMix = 0;

    private int selectedCurrentBarMax =1001;

    /**
     *
     */

    private HotelPopupRecordDataInstance(){

    }

    public static HotelPopupRecordDataInstance getInstance(){

        if(instance == null){
            instance = new HotelPopupRecordDataInstance();
        }

        return instance;
    }

    /**
     * 存储酒店的星级和价格数据
     * @param selectedList
     * @param selectedCurrentBarMix
     * @param selectedCurrentBarMax
     */
    public void saveHotelStartPriceData(List<HotelLevelBean> selectedList,int selectedCurrentBarMix,int selectedCurrentBarMax){

        this.selectedList = selectedList;

        this.selectedCurrentBarMix = selectedCurrentBarMix;

        this.selectedCurrentBarMax = selectedCurrentBarMax;
    }

    /**
     * 初始化酒店星级价格操作数据
     */
    public  void initHotelStartPriceRecordData(){

        selectedList = null;

        selectedCurrentBarMix = 0;

        selectedCurrentBarMax =1001;
    }

    public boolean ifSelectedData(){

        boolean selecteFlag =false;

        if(selectedList != null && selectedList.size() >0){

            selecteFlag = true;

        }else if(selectedCurrentBarMix != 0 || selectedCurrentBarMax != 1001){

            selecteFlag = true;
        }


        return selecteFlag;
    }

    public List<HotelLevelBean> getSelectedList() {
        return selectedList;
    }

    public int getSelectedCurrentBarMix() {
        return selectedCurrentBarMix;
    }

    public int getSelectedCurrentBarMax() {
        return selectedCurrentBarMax;
    }
}
