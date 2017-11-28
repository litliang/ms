package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.my.model.CommandModel;
import com.yzb.card.king.ui.my.view.CommandView;

import java.util.Map;

/**
 * 功能：口令生成；
 *
 * @author:gengqiyun
 * @date: 2017/1/19
 */
public class CommandPresenter implements BaseMultiLoadListener
{
    private CommandModel model;
    private CommandView view;

    public CommandPresenter(CommandView view)
    {
        this.view = view;
        model = new CommandModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCommandSuc(String.valueOf(data));
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCommandFail(msg);
    }
}
