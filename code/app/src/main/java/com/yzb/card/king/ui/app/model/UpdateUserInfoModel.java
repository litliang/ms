package com.yzb.card.king.ui.app.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.base.BaseNoRefreshModel;
import com.yzb.card.king.ui.app.presenter.UpdateUserInfoCallBack;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 功能：更新用户信息
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class UpdateUserInfoModel implements BaseNoRefreshModel
{
    private UpdateUserInfoCallBack loadListener;

    public UpdateUserInfoModel(UpdateUserInfoCallBack loadListener)
    {
        this.loadListener = loadListener;
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        String sysCode = null;

        if( GlobalApp.regIdList.size()>1){

             sysCode = GlobalApp.regIdList.get(0).get("regId");
        }

        if (TextUtils.isEmpty(sysCode)) {

            sysCode = JPushInterface.getRegistrationID(GlobalApp.getInstance().getContext());
        }
        paramMap.put("pushCode", sysCode);

        new SimpleRequest(CardConstant.card_app_updatePerson, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                UserBean userBean = JSON.parseObject(data, UserBean.class);
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(userBean);
                }
            }

            @Override
            public void onFail(String failMsg)
            {
                if (loadListener != null)
                {
                    loadListener.onListenError(failMsg);
                }
            }
        }).sendPostRequest();
    }
}
