package com.yzb.card.king.service;

import android.app.IntentService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.yzb.card.king.bean.my.KeyInfoBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.presenter.KeyInfoPresenter;
import com.yzb.card.king.ui.my.view.KeyInfoView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * 消息监听服务；
 *
 * @author gengqiyun
 * @date 2016.8.9
 */
public class MsgIntentService extends IntentService implements KeyInfoView
{
    public static final int TYPE_GIFT = 0x001;
    public static final int TYPE_BOUNS = 0x002;
    private KeyInfoPresenter keyInfoPresenter;

    public MsgIntentService()
    {
        super("MsgIntentService");
        keyInfoPresenter = new KeyInfoPresenter(this);
    }

    /**
     * 停止服务；
     */
    public static void stop(Context context)
    {
        Intent intent = getServiceIntent(context);
        context.stopService(intent);
    }

    /**
     * 监听礼品卡和红包；
     */
    public static void listenGiftAndBouns(Context context)
    {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0)
        {
            if (clipData.getItemAt(0).getText() != null)
            {
                //口令内容；
                String command = clipData.getItemAt(0).getText().toString();

                if (!TextUtils.isEmpty(command))
                {
                    serviceStart(context, command);
                    clipboard.setPrimaryClip(ClipData.newPlainText("command", ""));
                }
            }
        }
    }

    private static void serviceStart(Context context, String commandContent)
    {
        stop(context);
        Intent intent = getServiceIntent(context);
        int type = 0;
        if (commandContent.contains("礼品卡"))
        {
            type = TYPE_GIFT;
        } else if (commandContent.contains("红包"))
        {
            type = TYPE_BOUNS;
        }

        //礼品卡和红包 登录时才检测，旅游和酒店的不检测登录；
        if ((type == TYPE_GIFT || type == TYPE_BOUNS))
        {
            if (UserManager.getInstance().isLogin())
            {
                intent.putExtra("key", CommonUtil.handleCommand(commandContent));
                intent.putExtra("type", type);
                context.startService(intent);
            }
        }
    }

    private static Intent getServiceIntent(Context context)
    {
        Intent intent = new Intent(context, MsgIntentService.class);
        intent.setAction(AppConstant.MSG_SERVICE_ACTION);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            String action = intent.getAction();
            if (AppConstant.MSG_SERVICE_ACTION.equals(action))
            {
                getKetInfo(intent.getStringExtra("key"), intent.getIntExtra("type", 0));
            }
        }
    }

    private void getKetInfo(String key, int type)
    {

        keyInfoPresenter.setInterfaceParameter(key,type,type==TYPE_BOUNS);
    }

    @Override
    public void onGetKeyInfoSuc(KeyInfoBean keyInfoBean)
    {
        Intent intent = new Intent(AppConstant.MSG_BROASTCAST_ACTION);
        intent.putExtra("msgType", keyInfoBean.getKeyType());
        intent.putExtra("keyInfo", keyInfoBean);
        sendBroadcast(intent);
    }

    @Override
    public void onGetKeyInfoFail(String failMsg)
    {
        LogUtil.i("failMsg==" + failMsg);
    }
}
