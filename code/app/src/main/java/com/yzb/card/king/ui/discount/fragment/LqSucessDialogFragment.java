package com.yzb.card.king.ui.discount.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.util.Utils;

/**
 * Created by gengqiyun on 2016/4/20.
 * 领取优惠券/红包成功的对话框；
 */
public class LqSucessDialogFragment extends BaseDialogFragment implements View.OnClickListener
{
    private static LqSucessDialogFragment dialogFragment;
    private String je = "";
    private String flag = ""; // 0:优惠券；1：红包；

    public final static String TYPE_COUPON = "0";
    public final static String TYPE_BOUNS = "1";

    public static LqSucessDialogFragment getInstance(String je, String flag)
    {

        dialogFragment = new LqSucessDialogFragment();
        Bundle args = new Bundle();
        args.putString("je", je);
        args.putString("flag", flag);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            je = getArguments().getString("je");
            flag = getArguments().getString("flag");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    protected int getGravity()
    {
        return Gravity.CENTER;
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_lqsuccess_dialog;
    }

    @Override
    protected void initView(View view)
    {
        if (view != null)
        {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("恭喜您获得" + Utils.subZeroAndDot(je) + "元" + ("0".equals(flag) ? "优惠券" : "红包"));
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v)
    {
        this.dismiss();
    }
}
