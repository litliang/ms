package com.yzb.card.king.ui.my.fragment;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.gift.activity.GiftCardGiveMindActivity;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.DensityUtil;

/**
 * 功能： 生成口令
 *
 * @author:gengqiyun
 * @date: 2016/12/19
 */
public class CommandDialog extends BaseDialogFragment implements View.OnClickListener
{
    private String shareContent;
    private String sharePlatform; //分享平台；
    private Handler dataHandler;
    public static final int WHAT_SHARE_OTHER = 0x003;


    public CommandDialog setShareContent(String shareContent)
    {
        this.shareContent = shareContent;
        return this;
    }

    public CommandDialog setSharePlatform(String sharePlatform)
    {
        this.sharePlatform = sharePlatform;
        return this;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_create_command;
    }

    public static CommandDialog getInstance()
    {
        return new CommandDialog();
    }

    @Override
    protected int getGravity()
    {
        return Gravity.CENTER;
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
    protected int getDialogWidth()
    {
        return DensityUtil.getScreenWidth() - DensityUtil.dip2px(39 * 2);
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    @Override
    protected void initView(View view)
    {
        view.findViewById(R.id.tvNoShare).setOnClickListener(this);
        view.findViewById(R.id.tvPast).setOnClickListener(this);
        TextView tvCommandContent = (TextView) view.findViewById(R.id.tvCommandContent);
        tvCommandContent.setText(shareContent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvNoShare: //不分享了；
                dismiss();
                break;
            case R.id.tvPast://去粘贴；
                dismiss();
                past();
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


    /**
     * 粘贴到剪贴板；
     */
    private void past()
    {
        if (getActivity() != null)
        {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            
            clipboard.setPrimaryClip(ClipData.newPlainText("command", shareContent));

            if(!"更多".equals(sharePlatform)){
                openApp();
            }else {
                toastCustom("粘贴成功");
            }

        }
    }

    /**
     * 打开三方app；
     */
    private void openApp()
    {
        try
        {
            if (getActivity() != null)
            {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(sharePlatform);
                startActivity(intent);

                EventBus.getDefault().post(dataHandler.obtainMessage(GiftCardGiveMindActivity.WHAT_CLOSE));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            toastCustom("未安装此应用");
        }
    }

    public CommandDialog setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
        return this;
    }
}
