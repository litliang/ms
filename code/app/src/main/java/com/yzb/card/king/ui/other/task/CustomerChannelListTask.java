package com.yzb.card.king.ui.other.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：频道列表获取；
 *
 * @author:gengqiyun
 * @date: 2016/8/4
 */
public  class CustomerChannelListTask extends BaseRequest  implements HttpCallBackData {
    private IChannelListCallBack callBack;

    Map<String, Object> param = new HashMap<String, Object>();
    /**
     * 接口名
     */
    private String serverName = CardConstant.card_app_customerchannellist;

    public CustomerChannelListTask(IChannelListCallBack callBack) {
        this.callBack = callBack;
    }

    public void setParamData( Map<String, Object> param){

        this.param.putAll(param);
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
        String data = o+"";
        List<ChildTypeBean> displayBeans = new ArrayList<>();

        List<ChildTypeBean> hideBeans = new ArrayList<>();

        if (!TextUtils.isEmpty(data)) {
            List<ChildTypeBean> beans = JSON.parseArray(data, ChildTypeBean.class);
            if (beans != null && beans.size() > 0) {
                for (ChildTypeBean item : beans) {
                    item.typeImage = ServiceDispatcher.getImageUrl(item.typeImage);
                    //显示；
                    if ("1".equals(item.status)) {
                        displayBeans.add(item);
                    } else {
                        hideBeans.add(item);
                    }
                }
            }
        }
        if (callBack != null) {
            callBack.callBack(displayBeans, hideBeans);
        }
    }

    @Override
    public void onFailed(Object o)
    {
        if (callBack != null) {
            callBack.callBack(null, null);
        }
    }

    @Override
    public void onCancelled(Object o)
    {
        if (callBack != null) {
            callBack.callBack(null, null);
        }
    }

    @Override
    public void onFinished()
    {

    }



    public interface IChannelListCallBack {
        /**
         * @param displayChannelList 显示部分；
         * @param hideChannelList    隐藏部分；
         */
        void callBack(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList);
    }
}
