package com.yzb.card.king.ui.integral.model.impl;

import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.ui.credit.bean.CreditCardBillBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.integral.RemindRequest;
import com.yzb.card.king.http.integral.RemoveRemindRequest;
import com.yzb.card.king.http.integral.SelectCardRequest;
import com.yzb.card.king.ui.integral.model.MyRemindDao;
import com.yzb.card.king.ui.integral.model.OnDataLoadFinish;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public class MyRemindDaoImpl implements MyRemindDao
{
    private OnDataLoadFinish onDataLoadFinish;

    @Override
    public void getData(String type) {
        sendGetRequest(type);
    }

    private void sendGetRequest(String type) {
        new RemindRequest(type).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {
                onDataLoadFinish.onStart();
            }

            @Override
            public void onSuccess(Object o) {
                onDataLoadFinish.onSuccess(o);
            }

            @Override
            public void onFailed(Object o) {
                onDataLoadFinish.onFailed(o);
            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {
                onDataLoadFinish.onFinish();
            }
        });
    }

    @Override
    public void getCardInfo(int id) {
        sendGetCardInfoRequest(id);
    }

    private void sendGetCardInfoRequest(int id) {
        new SelectCardRequest(id).sendRequest(new HttpCallBackData() {
            CreditCardBillBean creditCardBillBean;
            @Override
            public void onStart() {
                onDataLoadFinish.onStart();
            }

            @Override
            public void onSuccess(Object o) {
                onDataLoadFinish.onGoCardInfo(o);
            }

            @Override
            public void onFailed(Object o) {
                onDataLoadFinish.onFailed(o);
            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {
                onDataLoadFinish.onFinish();
            }
        });
    }

    @Override
    public void deleteRemind(RemindBean remindBean) {
        sendDeleteRemindRequest(remindBean);
    }

    private void sendDeleteRemindRequest(final RemindBean remindBean) {
        new RemoveRemindRequest(remindBean).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {
                onDataLoadFinish.onStart();
            }

            @Override
            public void onSuccess(Object o) {
                onDataLoadFinish.onDeleteRemind(remindBean);
            }

            @Override
            public void onFailed(Object o) {
                onDataLoadFinish.onFailed(o);
            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {
                onDataLoadFinish.onFinish();
            }
        });
    }

    @Override
    public void setOnDataLoadFinish(OnDataLoadFinish onDataLoadFinish) {
        this.onDataLoadFinish=onDataLoadFinish;
    }
}
