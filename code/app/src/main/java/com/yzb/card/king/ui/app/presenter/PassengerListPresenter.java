package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.app.model.PassengerListModel;
import com.yzb.card.king.ui.app.view.PassengerListView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅客信息列表
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class PassengerListPresenter implements BaseMultiLoadListener
{
    private PassengerListModel model;
    private PassengerListView view;

    public PassengerListPresenter(PassengerListView view)
    {
        this.view = view;
        model = new PassengerListModel(this);
    }

    public void loadData(boolean event_flag, Map<String, Object> paramMap)
    {
        model.loadData(event_flag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadPassengersSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadPassengersFail(msg);
    }


    /**
     * 获取默认旅客；
     *
     * @param list
     * @return
     */
    public PassengerInfoBean getDefaultPassenger(List<PassengerInfoBean> list)
    {
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                if ("2".equals(list.get(i).getStatus()))
                {
                    return list.get(i);
                }
            }
        }
        return null;
    }
}
