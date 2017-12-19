package com.yzb.card.king.ui.appwidget.picks;

import android.view.View;

import com.jamgle.pickerviewlib.adapter.ArrayWheelAdapter;
import com.jamgle.pickerviewlib.adapter.NumericWheelAdapter;
import com.jamgle.pickerviewlib.lib.WheelView;
import com.jamgle.pickerviewlib.listener.OnItemSelectedListener;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/3/21
 * 描  述：
 */
public class NumberWheelView {


    private WheelView wvChengren;

    private WheelView wvErtong;

    private WheelView wvYinger;


    public NumberWheelView(View numberView){

        wvChengren = (WheelView) numberView.findViewById(R.id.wvChengren);

        wvErtong = (WheelView) numberView.findViewById(R.id.wvErtong);

        wvYinger = (WheelView) numberView.findViewById(R.id.wvYinger);

        wvChengren.setAdapter(new NumericWheelAdapter(1, 9));
        wvChengren.setCurrentItem(1);//设置第二位
        wvChengren.setCyclic(false);

        wvErtong.setAdapter(new NumericWheelAdapter(0, 18));
        wvErtong.setCurrentItem(0);//设置第二位
        wvErtong.setCyclic(false);


        wvYinger.setAdapter(new NumericWheelAdapter(0, 9));
        wvYinger.setCurrentItem(0);//设置第二位
        wvYinger.setCyclic(false);

        wvChengren.setOnItemSelectedListener(ChengrenListener);

        wvErtong.setOnItemSelectedListener(ErtongListener);

        wvYinger.setOnItemSelectedListener(YingerListener);

    }

    int chengren;

    /**
     * 信息监听
     */
    private OnItemSelectedListener ChengrenListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index)
        {
            chengren = index;

            LogUtil.e("BBBBB","----------"+index);
        }
    };


    int ertong;
    /**
     * 信息监听
     */
    private OnItemSelectedListener ErtongListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index)
        {
            ertong = index;
            LogUtil.e("BBBBB","----------"+index);
        }
    };
    int yinger;
    /**
     * 信息监听
     */
    private OnItemSelectedListener YingerListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index)
        {
            yinger = index;
            LogUtil.e("BBBBB","----------"+index);
        }
    };

    public List<Integer> getSelectedInfo()
    {
        List<Integer> list = new ArrayList<Integer>();

        list.add(chengren);

        list.add(ertong);

        list.add(yinger);

        return  list;
    }

    public void setCurrentIndex(int crAdult, int etChilDren, int yebaBy)
    {

        wvChengren.setCurrentItem(crAdult-1);

        wvErtong.setCurrentItem(etChilDren);

        wvYinger.setCurrentItem(yebaBy);
    }
}
