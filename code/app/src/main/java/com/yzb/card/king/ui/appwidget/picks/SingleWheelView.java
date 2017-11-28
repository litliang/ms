package com.yzb.card.king.ui.appwidget.picks;

import android.view.View;

import com.jamgle.pickerviewlib.adapter.ArrayWheelAdapter;
import com.jamgle.pickerviewlib.lib.WheelView;
import com.jamgle.pickerviewlib.listener.OnItemSelectedListener;
import com.yzb.card.king.R;

import java.util.ArrayList;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/23
 * 描  述：
 */
public class SingleWheelView {

    private View view;

    private WheelView singleWV;

    private int current = 0;

    public SingleWheelView(View view) {
        super();
        this.view = view;

    }


    public void setPicker(ArrayList<String> picker) {

        singleWV = (WheelView) view.findViewById(R.id.wvContent);

        ArrayWheelAdapter arryWadapter = new ArrayWheelAdapter(picker);

        singleWV.setAdapter(arryWadapter);

        singleWV.setCurrentItem(current);

        singleWV.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

                current = index;
            }
        });
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setCyclic(boolean cyclic) {

        singleWV.setCyclic(cyclic);
    }
}
