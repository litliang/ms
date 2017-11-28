package com.yzb.card.king.ui.order.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/13
 * 描  述：
 */
public class HotelReturenMoneySucceedDialog extends DialogFragment {


    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_fragment_dialog_hotel_order_return_money_succeed_dialog, container, false);
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle bundle = getArguments();

        String titleName = bundle.getString("titleName");

        String msg = bundle.getString("msg");

        initView(view,titleName,msg);

        return view;
    }

    private void initView(View view,String title,String msg)
    {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        TextView tvMsg = (TextView) view.findViewById(R.id.tvMsg);

        tvTitle.setText(title);

        tvMsg.setText(msg);
    }


}
