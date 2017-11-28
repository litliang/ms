package com.yzb.card.king.ui.my.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/18 12:49
 */
public class LogoutConfirmDialog extends DialogFragment
{
    private View llMsg;
    private View llButton;
    private View tvNegative;
    private View tvPositive;
    private OnPositiveListener listener;

    public void setListener(OnPositiveListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return initDialog();
    }

    private Dialog initDialog()
    {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);
        dialog.setContentView(initView());
        initListener();
        initData();
        setWindow(dialog);
        return dialog;
    }

    private void initData()
    {
//        llButton.setLayoutParams(new LinearLayout.LayoutParams(llMsg.getMeasuredWidth(),
//                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void initListener()
    {
        tvNegative.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        tvPositive.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if (listener != null)
                {
                    listener.onPositive();
                }
            }
        });
    }

    private View initView()
    {
        View root = UiUtils.inflate(R.layout.dialog_logout);
        llMsg = root.findViewById(R.id.llMsg);
        llButton = root.findViewById(R.id.llButton);
        tvPositive = root.findViewById(R.id.tvPositive);
        tvNegative = root.findViewById(R.id.tvNegative);
        llMsg = root.findViewById(R.id.llMsg);
        return root;
    }

    private void setWindow(Dialog dialog)
    {
        Window window = dialog.getWindow();
        //底部弹出动画;
        window.setWindowAnimations(R.style.dialog_bottom_animation_style);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.drawable.bg_white_corner_3);
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        layoutParams.width =screenWith * 800 / 1080;
        layoutParams.height = CommonUtil.dip2px(getContext(),120);
        window.setAttributes(layoutParams);
    }

    public interface OnPositiveListener
    {
        void onPositive();
    }
}
