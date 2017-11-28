package com.yzb.card.king.ui.appwidget.picks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.BasePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/2
 * 描  述：
 */
public class SelectCountryPick extends BasePickerView implements View.OnClickListener{


    private CountryCityWheelView countryCityWheelView;

    private TextView tvSubmit;

    private SelectedData listener;

    public SelectCountryPick(Context context,CountryCityWheelView.Type type)
    {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_select_country_pick_ios, contentContainer);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.llContainer);

        countryCityWheelView = new CountryCityWheelView(timepickerview,type);

        tvSubmit = (TextView) findViewById(R.id.tvSubmit);

        tvSubmit.setOnClickListener(this);
    }

    public void setListener(SelectedData listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()){
            case R.id.tvSubmit:

                if(listener != null){

                    listener.getSelectedData(countryCityWheelView.getSelectedData());
                }
                dismiss();
                break;
            default:
                break;
        }


    }

    public interface SelectedData{

        /**
         * 集合数据包裹，国家、省份、城市和区域数据
         * @param data
         */
        void getSelectedData(List<NationalCountryBean> data);

    }
}
