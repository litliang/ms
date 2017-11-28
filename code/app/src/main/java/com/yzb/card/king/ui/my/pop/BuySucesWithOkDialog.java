package com.yzb.card.king.ui.my.pop;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;

import org.xutils.common.util.DensityUtil;

/**
 * 功能：购买成功附带确认按钮的dialog；
 *
 * @author:gengqiyun
 * @date: 2016/12/15
 */
public class BuySucesWithOkDialog extends BaseDialogFragment implements View.OnClickListener
{
    public static final int WHAT_OK = 0x001;
    private Handler dataHandler;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_buy_suces_with_ok;
    }

    public static BuySucesWithOkDialog getInstance()
    {
        return new BuySucesWithOkDialog();
    }

    @Override
    protected int getGravity()
    {
        return Gravity.TOP;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.y = DensityUtil.dip2px(193); //距离顶部的距离；
        window.setAttributes(wl);
        return dialog;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (getDialog() != null)
        {
            getDialog().setOnDismissListener(new DialogInterface.OnDismissListener()
            {
                @Override
                public void onDismiss(DialogInterface dialog)
                {
                    sendMsg(WHAT_OK);
                }
            });
        }
    }

    @Override
    protected int getDialogWidth()
    {
        return DensityUtil.getScreenWidth() - DensityUtil.dip2px(100);
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    @Override
    protected void initView(View view)
    {
        view.findViewById(R.id.tvSure).setOnClickListener(this);
    }

    @Override
    protected int getDialogStyle()
    {
        return 0;
    }

    public BuySucesWithOkDialog setDataHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
        return this;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvSure: //确认；
                dismiss();
                break;
        }
    }

    public void sendMsg(int what)
    {
        if (dataHandler != null)
        {
            Message msg = dataHandler.obtainMessage(what);
            dataHandler.sendMessage(msg);
        }
    }
}
