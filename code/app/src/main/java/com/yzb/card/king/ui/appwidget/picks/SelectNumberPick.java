package com.yzb.card.king.ui.appwidget.picks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jamgle.pickerviewlib.view.BasePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;

/**
 * 类  名：选择乘机人数量
 * 作  者：Li Yubing
 * 日  期：2017/3/21
 * 描  述：
 */
public class SelectNumberPick extends BasePickerView implements View.OnClickListener {


    private NumberWheelView numberWheelView;

    private Context context;

    private SelectedData dataLister;

    public SelectNumberPick(Context context)
    {

        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_select_number_pick_ios, contentContainer);

        final View timepickerview = findViewById(R.id.llContainer);

        numberWheelView = new NumberWheelView(timepickerview);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_ok).setOnClickListener(this);

    }

    public void setOnDataCallBackList(SelectedData dataLister)
    {
        this.dataLister = dataLister;
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId()) {

            case R.id.tv_ok:

                List<Integer> str = numberWheelView.getSelectedInfo();

                int a = str.get(0);
                int b = str.get(1);
                int c = str.get(2);

                if(a == 0 && b == 0 && c == 0){
                    ToastUtil.i(context, R.string.ticket_cr_et_chose);
                    return;
                }

                if ((a < b / 2 + c) ) {
                    ToastUtil.i(context, R.string.ticket_cr_et_chose);
                    return;
                }

                if (dataLister != null) {
                    dataLister.getSelectedData(str);
                }

                dismiss();
                break;

            case R.id.tv_cancel:

                dismiss();
                break;
            default:
                break;

        }
    }

    public void setCurrentIndex(int crAdult, int etChilDren, int yebaBy)
    {
        numberWheelView.setCurrentIndex(crAdult, etChilDren, yebaBy);
    }


    public interface SelectedData {

        /**
         * @param data
         */
        void getSelectedData(List<Integer> data);

    }
}
