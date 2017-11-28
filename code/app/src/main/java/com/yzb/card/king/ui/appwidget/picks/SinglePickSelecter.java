package com.yzb.card.king.ui.appwidget.picks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.BasePickerView;
import com.yzb.card.king.R;

import java.util.ArrayList;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/23
 * 描  述：
 */
public class SinglePickSelecter extends BasePickerView implements View.OnClickListener {

    private TextView tvSubmit, tvCancel;//確定、取消
    private TextView tvTitle;

    private OnSelectIndexListener timeSelectListener;

    private SingleWheelView swv;

    public SinglePickSelecter(Context context, ArrayList<String> strAL) {
        super(context);

       LayoutInflater.from(context).inflate(R.layout.view_single_picktime_ios, contentContainer);

        tvCancel = (TextView) findViewById(R.id.tvCancel);


        tvSubmit = (TextView) findViewById(R.id.tvSubmit);


        tvCancel.setOnClickListener(this);

        tvSubmit.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        // ----时间转轮
        final View timepickerview = findViewById(com.jamgle.pickerviewlib.R.id.timepicker);

        swv = new SingleWheelView(timepickerview);

        swv.setPicker(strAL);


    }

    public void setTitle(String name){

        tvTitle.setText(name);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        swv.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.tvSubmit){

            if(timeSelectListener!=null){

                int  currentIndex =  swv.getCurrent();

                timeSelectListener.onSelectIndex(currentIndex);
            }

        }

        dismiss();
    }


    public interface OnSelectIndexListener {
        void onSelectIndex(int index);

        public void onCancel();
    }

    public void setOnSelectIndexListener(OnSelectIndexListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

}
