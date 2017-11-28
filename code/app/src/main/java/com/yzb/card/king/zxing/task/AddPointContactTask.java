package com.yzb.card.king.zxing.task;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/27 16:51
 * 描述：
 */
public class AddPointContactTask extends BaseRequest implements HttpCallBackData {

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName ="ContactsCreate";

    public AddPointContactTask(String nickName,String mobile) {

        param.put("nickName", nickName);
        param.put("mobile",mobile);
        param.put("type","1");//积分联系人
        param.put("relationship","5");//朋友
    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), this);
    }
    @Override
    public void onStart()
    {

    }

    @Override
    public void onSuccess(Object o)
    {
        ToastUtil.i(GlobalApp.getInstance().getContext(),"添加成功");
    }

    @Override
    public void onFailed(Object o)
    {
        ToastUtil.i(GlobalApp.getInstance().getContext(),"添加失败");
    }

    @Override
    public void onCancelled(Object o)
    {

    }

    @Override
    public void onFinished()
    {

    }


}
