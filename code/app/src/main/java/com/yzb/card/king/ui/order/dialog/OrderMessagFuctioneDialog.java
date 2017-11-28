package com.yzb.card.king.ui.order.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.ProductBaseOrderBean;

/**
 * 类  名：酒店订单
 * 作  者：Li Yubing
 * 日  期：2017/9/13
 * 描  述：
 */
public class OrderMessagFuctioneDialog extends DialogFragment implements View.OnClickListener {

    public final static int FUCTION_ORDER_REFUND_CODE = 8000;

    public final static int FUCTION_ORDER_DEL_CODE = 8001;

    public final static int FUCTION_SUPPLEMENTORDERINVOICE_CODE = 8002;

    private String dialogMsg;

    private Handler dialogHandler;

    private int code;

    public void setDialogHandler(Handler dialogHandler)
    {
        this.dialogHandler = dialogHandler;
    }

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
        View view = inflater.inflate(R.layout.view_fragment_dialog_hotel_order_del_dialog, container, false);
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle bundle = getArguments();

        dialogMsg = bundle.getString("dialogMsg");

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        view.findViewById(R.id.tvCancel).setOnClickListener(this);

        tvTitle.setText(dialogMsg);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvConfirm:
                //检查是否登录
                if(dialogHandler != null){

                    dialogHandler.sendEmptyMessage(code);
                }
                dismiss();
                break;
            case R.id.tvCancel:

                dismiss();

                break;

            default:
                break;
        }
    }

    public void setCode(int code)
    {
        this.code = code;
    }
}
