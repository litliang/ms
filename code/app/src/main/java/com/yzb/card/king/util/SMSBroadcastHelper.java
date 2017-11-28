package com.yzb.card.king.util;

import android.content.IntentFilter;
import android.widget.TextView;

import com.yzb.card.king.ui.other.broadcast.SMSReceiver;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/8/12 10:38
 * 描述：
 */
public class SMSBroadcastHelper
{
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static SMSReceiver receiver;

    public static void registerBroadcast(final TextView textView)
    {
        unRegisterBroadcast();
        receiver = new SMSReceiver()
        {

            @Override
            protected void onMessageReceived(String s)
            {
                textView.setText(s);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SMS_RECEIVED_ACTION);
        UiUtils.getContext().registerReceiver(receiver, intentFilter);
    }

    public static void unRegisterBroadcast()
    {
        if (receiver != null)
            try
            {
                UiUtils.getContext().unregisterReceiver(receiver);
            } catch (Exception e)
            {

            }
    }
}
