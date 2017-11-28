package com.yzb.card.king.ui.integral.model;

import android.app.Activity;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 11:13
 */
public interface IAccountSchema
{
    void loadData();

    void sendUserGiftRequest(String id, Activity accountSchemaActivity);

    void sendUserRedPackRequest(String id, Activity accountSchemaActivity);

    void sendUserMoneyRequest(String id, Activity accountSchemaActivity);

    void sendUserIntegralRequest(String id, Activity accountSchemaActivity);
}
