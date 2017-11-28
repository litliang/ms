package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.model.ActivatePhysCardModel;
import com.yzb.card.king.util.AppUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：实体卡充值；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class ActivatePhysCardPresenter implements BaseMultiLoadListener
{
    private ActivatePhysCardModel model;

    private BaseViewLayerInterface view;

    public ActivatePhysCardPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new ActivatePhysCardModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    public void setInterfaceParameters(String cardNo,String pswd){
        Map<String, Object> params = new HashMap<>();
        params.put("cardNo", cardNo);//卡号
        params.put("pswd", pswd);//密码
        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        model.loadData(true, params);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(null,-1);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,-1);
    }
}
