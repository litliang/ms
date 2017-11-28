package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.ui.gift.bean.NoRecvCardBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.gift.modle.GiftcardNoRecvModel;
import com.yzb.card.king.ui.gift.view.GiftcardNoRecvView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：礼品卡首页；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class GiftcardNoRecvPresenter implements BaseMultiLoadListener
{
    private GiftcardNoRecvModel model;
    private GiftcardNoRecvView view;

    public GiftcardNoRecvPresenter(GiftcardNoRecvView view)
    {
        this.view = view;
        model = new GiftcardNoRecvModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    public void setInterfaceParameters(boolean event_tag,String pageStart){

        Map<String, Object> args = new HashMap<>();
        args.put("pageStart", pageStart);
        args.put("pageSize", AppConstant.MAX_PAGE_NUM);
        args.put("sessionId", AppConstant.sessionId);
        model.loadData(event_tag, args);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetECardListSuc(event_tag, (List<NoRecvCardBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetECardListFail(msg);
    }
}
