package com.yzb.card.king.ui.bonus.presenter;

import com.yzb.card.king.ui.bonus.bean.BounsBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.bonus.model.BounsListModel;
import com.yzb.card.king.ui.bonus.view.BounsListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：红包列表
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public class BounsListPresenter implements BaseMultiLoadListener
{
    private BounsListModel model;

    private BounsListView view;

    public BounsListPresenter(BounsListView view)
    {
        this.view = view;
        model = new BounsListModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetBounsListSuc(event_tag, (List<BounsBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetBounsListFail(msg);
    }

    /**
     * 发送请求,获取未领取红包信息
     */
    public void sendRedEnvelopeRequest()
    {
        Map<String, Object> args = new HashMap<>();
        args.put("pageStart", 0);
        args.put("pageSize", Integer.MAX_VALUE);
        loadData(true, args);
    }
}
