package com.yzb.card.king.ui.discount.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;

/**
 * Created by gengqiyun on 2016/4/20.
 *
 * @author gengqiyun
 * @date 2016.8.29
 * 闹钟提醒对话框；
 */
public class ClockMsgDialogFragment extends DialogFragment implements View.OnClickListener {
    private static ClockMsgDialogFragment dialogFragment;
    private String storeId = "";
    private TextView tvCancel;
    private TextView tvYes;
    private IDialogCallBack callBack;

    public ClockMsgDialogFragment setCallBack(IDialogCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public static ClockMsgDialogFragment getInstance(String arg1, String arg2) {

        synchronized (ClockMsgDialogFragment.class) {
            if (dialogFragment == null) {
                dialogFragment = new ClockMsgDialogFragment();
            }
        }
        return dialogFragment;
    }


    public void setShopId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_clock_msg, null);
        dialog.setContentView(view);
        initView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        return dialog;
    }

    private void initView(View view) {
        if (view == null) {
            return;
        }
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        tvYes = (TextView) view.findViewById(R.id.tv_yes);
        tvYes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_yes:
                if (callBack != null) {
                    dismiss();
                    callBack.dialogCallBack("1");
                }
                break;
        }
    }

}
