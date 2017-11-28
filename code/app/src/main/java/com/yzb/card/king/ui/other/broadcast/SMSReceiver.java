package com.yzb.card.king.ui.other.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/8/11 17:17
 * 描述：
 */
public abstract class SMSReceiver extends BroadcastReceiver {
    public static final String TAG = "SMSReceiver";
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
            SmsMessage[] messages = getMessagesFromIntent(intent);
            for (SmsMessage message : messages) {
                String smsContent = message.getDisplayMessageBody();
                Log.i(TAG, smsContent);
                if(smsContent.startsWith("【银证保】")){
                    Pattern pattern =  Pattern.compile("([0-9]{4})");
                    Matcher matcher = pattern.matcher(smsContent);
                    if(matcher.find()){
                        String s = matcher.group();
//                        SMSManager.getSMSManager().setCode(s);
                        onMessageReceived(s);
                    }else {
                        Log.i(TAG, "没有找到");
                    }
                }
            }
        }
    }

    protected abstract void onMessageReceived(String s);

    private SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];
        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }
}
