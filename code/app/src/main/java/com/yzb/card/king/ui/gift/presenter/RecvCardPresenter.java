package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.gift.modle.RecvCardModel;
import com.yzb.card.king.ui.gift.view.RecvCardView;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：领取礼品卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/29
 */
public class RecvCardPresenter implements BaseMultiLoadListener
{
    private RecvCardModel model;
    private RecvCardView view;

    public RecvCardPresenter(RecvCardView view)
    {
        this.view = view;
        model = new RecvCardModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    public void setInterfaceParameters(String account,String orderId){

        Map<String, Object> args = new HashMap<String,Object>();
        args.put("account", account); //用户名
        args.put("customerType", UserBean.TYPE); //客户类别 App暂传“P”
        args.put("customerMod",UserBean.MOD); //服务类型  App暂传“2”
        args.put("orderId", orderId);
        model.loadData(true, args);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onRecvCardSuc(String.valueOf(data));
    }

    @Override
    public void onListenError(String msg)
    {
        view.onRecvCardFail(msg);
    }
}
