package com.yzb.card.king.ui.other.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.idcard.CardInfo;
import com.idcard.TRECAPIImpl;
import com.idcard.TStatus;
import com.idcard.TengineID;
import com.turui.bank.ocr.CaptureActivity;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/5 15:48
 */
public class ScanCardController
{
    private static final int RESULT_GET_OK = 1;
    private TRECAPIImpl engineDemo = new TRECAPIImpl();
    private Activity activity;
    private BaseCallBack<CardInfo> callBack;

    public void setCallBack(BaseCallBack<CardInfo> callBack)
    {
        this.callBack = callBack;
    }

    public ScanCardController(Activity activity)
    {
        this.activity = activity;
    }

    public void scan()
    {
        initEngine();
        CaptureActivity.tengineID = TengineID.TIDBANK;
        Intent intent = new Intent(activity, CaptureActivity.class);
        intent.putExtra("engine", engineDemo);
        activity.startActivityForResult(intent, RESULT_GET_OK);
    }

    private void stopEngine()
    {
        engineDemo.TR_ClearUP();
    }

    private void initEngine()
    {
        TStatus tStatus = engineDemo.TR_StartUP();
        if (tStatus == TStatus.TR_TIME_OUT)
        {
            UiUtils.shortToast("引擎过期");
        } else if (tStatus == TStatus.TR_FAIL)
        {
            UiUtils.shortToast("引擎初始化失败");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RESULT_GET_OK)
        {
            Bundle bundle = data.getExtras();
            CardInfo cardInfo = (CardInfo) bundle.getSerializable("cardinfo");
            if (cardInfo != null)
            {
//                String cardNum = cardInfo.getFieldString(TFieldID.TBANK_NUM);
//                String cardType = cardInfo.getFieldString(TFieldID.TBANK_CLASS);
                if (callBack != null)
                {
                    callBack.onSuccess(cardInfo);
                }
                return;
            }else {
                UiUtils.shortToast("扫描失败");
            }
        }
        stopEngine();
    }
}
