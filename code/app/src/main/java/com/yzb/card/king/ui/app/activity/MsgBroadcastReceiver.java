package com.yzb.card.king.ui.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yzb.card.king.bean.my.KeyInfoBean;
import com.yzb.card.king.service.MsgIntentService;
import com.yzb.card.king.ui.bonus.fragment.BonusRecvMsgDialog;
import com.yzb.card.king.ui.gift.fragment.GiftRecvMsgDialog;
import com.yzb.card.king.util.LogUtil;

/**
 * 功能：消息监听；
 *
 * @author:gengqiyun
 * @date: 2017/1/20
 */
public class MsgBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //消息类型；
        int msgType = intent.getIntExtra("msgType", -1);
        LogUtil.i("接受到消息了=" + msgType);
        switch (msgType)
        {
            case MsgIntentService.TYPE_GIFT: //礼品卡；
                if (intent.hasExtra("keyInfo"))
                {
                    KeyInfoBean keyInfoBean = (KeyInfoBean) intent.getSerializableExtra("keyInfo");
                    GiftRecvMsgDialog.getInstance(context).setOrderNo(keyInfoBean.getCodeNo()).setSender(keyInfoBean.getUserName()).show();
                }
                break;
            case MsgIntentService.TYPE_BOUNS: //红包；
                if (intent.hasExtra("keyInfo"))
                {
                    KeyInfoBean keyInfoBean = (KeyInfoBean) intent.getSerializableExtra("keyInfo");
                    BonusRecvMsgDialog.getInstance(context).setOrderNo(keyInfoBean.getOrderId()).show();
                }
                break;
        }
    }
}
