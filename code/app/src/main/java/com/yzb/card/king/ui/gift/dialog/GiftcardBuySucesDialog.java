package com.yzb.card.king.ui.gift.dialog;

import android.app.Dialog;
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
 * 功能：礼品卡购买成功dialog；
 *
 * @author:gengqiyun
 * @date: 2016/12/15
 */
public class GiftcardBuySucesDialog extends BaseDialogFragment implements View.OnClickListener
{
    public static final int WHAT_SEND = 0x001;
    public static final int WHAT_LOOK = 0x004;
    private Handler dataHandler;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_buy_giftcard_suces;
    }

    public static GiftcardBuySucesDialog getInstance()
    {
        return new GiftcardBuySucesDialog();
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
        view.findViewById(R.id.tvNowSend).setOnClickListener(this);
        view.findViewById(R.id.tvNowLook).setOnClickListener(this);
    }

    public GiftcardBuySucesDialog setDataHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
        return this;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvNowSend: //发送；
                sendMsg(WHAT_SEND);
                dismiss();
                break;
            case R.id.tvNowLook://查看；
                sendMsg(WHAT_LOOK);
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
