package com.yzb.card.king.ui.other.task;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.luxury.model.VoteDelModel;

import java.util.Map;

/**
 * 功能：删除评论；
 *
 * @author:gengqiyun
 * @date: 2017/3/29
 */
public class VoteDelPresenter implements BaseMultiLoadListener
{
    private VoteDelModel model;
    private BaseViewLayerInterface view;

    public VoteDelPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new VoteDelModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,1);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,1);
    }
}
