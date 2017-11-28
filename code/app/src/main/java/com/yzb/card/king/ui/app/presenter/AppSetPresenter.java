package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.ui.app.bean.AppSettingItem;
import com.yzb.card.king.ui.app.bean.NoticeMenu;
import com.yzb.card.king.ui.app.model.IAppSetting;
import com.yzb.card.king.ui.app.model.AppSettingImpl;
import com.yzb.card.king.ui.app.view.AppSetView;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 17:25
 */
public class AppSetPresenter
{
    private AppSetView view;
    private IAppSetting model;
    public AppSetPresenter(AppSetView view)
    {
        this.view =view;
        model = new AppSettingImpl(new NoticeListener());
    }

    public void loadNotice()
    {
        model.loadNotice();
    }

    public void save(String status, List<AppSettingItem> dataList)
    {
        model.save(status,dataList);
    }

    private class NoticeListener extends HttpCallBackImpl{
        @Override
        public void onSuccess(Object o)
        {
            view.noticeLoadSuccess((NoticeMenu)o);
        }

        @Override
        public void onFailed(Object o)
        {
            view.noticeLoadFail(o);
        }
    }
}
