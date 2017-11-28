package com.yzb.card.king.ui.travel;

import android.widget.SeekBar;
import android.widget.TextView;

import com.yzb.card.king.ui.travel.adapter.TravelPicesAdapter;
import com.yzb.card.king.ui.travel.bean.TravelSearchCriteriaBean;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/5/5
 * 描  述：
 */
public class SearchBarInfo {
    /*
          出行天数的视图bar的标签位置
     */
    public static final int[] days = new int[]{0, 9, 19, 30, 40, 50, 60, 72, 80};
    /*
          出现天数的视图显示的文字集合
     */
    public static final String[] daysName = new String[]{"不限", "1", "2", "3", "4", "5-6", "7-8", "9-10", "10+"};
    /*
          单人预算的视图bar的标签位置
     */
    public static final int[] prices = new int[]{0, 11, 22, 33, 44, 55};
    /*
          单人预算的视图显示的文字集合
     */
    public static final String[] priceName = new String[]{"不限", "0-0.5k", "0.5-1k", "1-5k", "5-10k", "10k+"};

    /**
     * 滑动计算预算价格
     *
     * @param i
     * @param seekBar
     */
    public static  void calBudgetPrice(int i, SeekBar seekBar , TravelSearchCriteriaBean searchBean, TextView singleBudget,TravelPicesAdapter priceAdapter)
    {
        int selecedIndex = -1;
        if (i <= 8) {
            selecedIndex = 0;
            searchBean.setSinglBudget(0 + "");
            searchBean.setSinglBudgetMax(Integer.MAX_VALUE + "");
        } else if (i > 8 && i <= 18) {
            selecedIndex = 1;
            searchBean.setSinglBudget(0 + "");
            searchBean.setSinglBudgetMax(500 + "");


        } else if (i > 18 && i <= 28) {
            selecedIndex = 2;
            searchBean.setSinglBudget(500 + "");
            searchBean.setSinglBudgetMax(1000 + "");


        } else if (i > 28 && i <= 38) {
            selecedIndex = 3;
            searchBean.setSinglBudget(1000 + "");
            searchBean.setSinglBudgetMax(5000 + "");


        } else if (i > 38 && i <= 48) {
            selecedIndex = 4;
            searchBean.setSinglBudget(5000 + "");
            searchBean.setSinglBudgetMax(10000 + "");

        } else if (i > 48 && i <= 55) {
            selecedIndex = 5;
            searchBean.setSinglBudget(10000 + "");
            searchBean.setSinglBudgetMax(Integer.MAX_VALUE + "");
        }

        if(selecedIndex != -1){

            singleBudget.setText(priceName[selecedIndex]);

            seekBar.setProgress(prices[selecedIndex]);

            priceAdapter.setPos(selecedIndex);
        }

        int priceProValue = seekBar.getProgress();
        searchBean.setBudgetPriceProValue(priceProValue);

    }



    /**
     * 点击--预算单人价格
     * @param clickIndex
     */
    public static void clickCalBudgetPrice(int clickIndex,SeekBar seekBar , TravelSearchCriteriaBean searchBean, TextView singleBudget,TravelPicesAdapter priceAdapter){

        int selecedIndex = clickIndex;

        singleBudget.setText(priceName[selecedIndex]);
        seekBar.setProgress(prices[selecedIndex]);
        priceAdapter.setPos(selecedIndex);
        if (selecedIndex == 0) {
            searchBean.setSinglBudget(0 + "");
            searchBean.setSinglBudgetMax(Integer.MAX_VALUE + "");

        } else if (selecedIndex==1) {
            searchBean.setSinglBudget(0 + "");
            searchBean.setSinglBudgetMax(500 + "");

        } else if (selecedIndex==2) {
            searchBean.setSinglBudget(500 + "");
            searchBean.setSinglBudgetMax(1000 + "");

        } else if (selecedIndex==3) {
            searchBean.setSinglBudget(1000 + "");
            searchBean.setSinglBudgetMax(5000 + "");

        } else if (selecedIndex==4) {
            searchBean.setSinglBudget(5000 + "");
            searchBean.setSinglBudgetMax(10000 + "");

     ;
        } else if (selecedIndex==5) {
            searchBean.setSinglBudget(10000 + "");
            searchBean.setSinglBudgetMax(Integer.MAX_VALUE + "");
        }

        int priceProValue = seekBar.getProgress();
        searchBean.setBudgetPriceProValue(priceProValue);
    }

    /**
     * 计算出行天数
     *
     * @param i
     * @param seekBar
     */
    public static  void calTravelDays(int i, SeekBar seekBar,TravelSearchCriteriaBean searchBean,TextView travelDays,TravelPicesAdapter dayAdapter)
    {
        int selectedIndex = -1;
        if (i <= 5) {
            selectedIndex = 0;
            searchBean.setDaysCount(0 + "");
            searchBean.setDaysCountMax(Integer.MAX_VALUE + "");

        } else if (i > 5 && i <= 15) {
            selectedIndex = 1;
            searchBean.setDaysCount(1 + "");
            searchBean.setDaysCountMax(1 + "");

        } else if (i > 15 && i <= 25) {
            selectedIndex = 2;
            searchBean.setDaysCount(2 + "");
            searchBean.setDaysCountMax(2 + "");

        } else if (i > 25 && i <= 35) {
            selectedIndex = 3;
            searchBean.setDaysCount(3 + "");
            searchBean.setDaysCountMax(3 + "");


        } else if (i > 35 && i <= 45) {
            selectedIndex = 4;
            searchBean.setDaysCount(4 + "");
            searchBean.setDaysCountMax(4 + "");

        } else if (i > 45 && i <= 55) {
            selectedIndex = 5;
            searchBean.setDaysCount(5 + "");
            searchBean.setDaysCountMax(6 + "");


        } else if (i > 55 && i <= 65) {
            selectedIndex = 6;
            searchBean.setDaysCount(7 + "");
            searchBean.setDaysCountMax(8 + "");


        } else if (i > 65 && i <= 75) {
            selectedIndex = 7;
            searchBean.setDaysCount(9 + "");
            searchBean.setDaysCountMax(10 + "");

        } else if (i > 75) {
            selectedIndex = 8;
            searchBean.setDaysCount(10 + "");
            searchBean.setDaysCountMax(Integer.MAX_VALUE + "");
        }

        if(selectedIndex != -1){

            travelDays.setText(daysName[selectedIndex]);

            seekBar.setProgress(days[selectedIndex]);

            dayAdapter.setPos(selectedIndex);
        }

        int priceProValue = seekBar.getProgress();

        searchBean.setTravelDaysValue(priceProValue);

    }


    /**
     * 点击计算出行天数
     * @param position
     * @param seekBar
     */
    public static  void clickCalTravelDays(int position, SeekBar seekBar,TravelSearchCriteriaBean searchBean,TextView travelDays,TravelPicesAdapter dayAdapter)
    {

        int selectedIndex = position;

        travelDays.setText(daysName[selectedIndex]);

        seekBar.setProgress(days[selectedIndex]);

        dayAdapter.setPos(selectedIndex);

        int priceProValue = seekBar.getProgress();

        searchBean.setTravelDaysValue(priceProValue);

        if (selectedIndex==0) {
            searchBean.setDaysCount(0 + "");
            searchBean.setDaysCountMax(Integer.MAX_VALUE + "");

        } else if (selectedIndex == 1) {
            searchBean.setDaysCount(1 + "");
            searchBean.setDaysCountMax(1 + "");

        } else if (selectedIndex == 2) {
            searchBean.setDaysCount(2 + "");
            searchBean.setDaysCountMax(2 + "");

        } else if (selectedIndex == 3) {
            searchBean.setDaysCount(3 + "");
            searchBean.setDaysCountMax(3 + "");


        } else if (selectedIndex == 4) {
            searchBean.setDaysCount(4 + "");
            searchBean.setDaysCountMax(4 + "");

        } else if (selectedIndex == 5) {
            searchBean.setDaysCount(5 + "");
            searchBean.setDaysCountMax(6 + "");

        } else if (selectedIndex==6) {

            searchBean.setDaysCount(7 + "");
            searchBean.setDaysCountMax(8 + "");

        } else if (selectedIndex == 7) {
            searchBean.setDaysCount(9 + "");
            searchBean.setDaysCountMax(10 + "");


        } else if (selectedIndex == 8) {
            searchBean.setDaysCount(10 + "");
            searchBean.setDaysCountMax(Integer.MAX_VALUE + "");

        }


    }

}
