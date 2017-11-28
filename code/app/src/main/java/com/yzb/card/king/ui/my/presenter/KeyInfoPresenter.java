package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.bean.my.KeyInfoBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.my.model.KeyInfoModel;
import com.yzb.card.king.ui.my.view.KeyInfoView;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：口令信息；
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class KeyInfoPresenter implements BaseMultiLoadListener
{
    private KeyInfoModel model;
    private KeyInfoView view;

    public KeyInfoPresenter(KeyInfoView view)
    {
        this.view = view;
        model = new KeyInfoModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    public void setInterfaceParameter(String key,int type,boolean flag){
        Map<String, Object> args = new HashMap<>();
        args.put("key", key);
        args.put("type", type);
        args.put("serviceName", flag ? CardConstant.get_bouns_keyinfo : CardConstant.card_getgiftkeyinfo);
        model.loadData(true, args);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetKeyInfoSuc((KeyInfoBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetKeyInfoFail(msg);
    }
}
