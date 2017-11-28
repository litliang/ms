package com.yzb.card.king.ui.integral.presenter;

import com.yzb.card.king.bean.integral.IntegralShareLinkman;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.ui.integral.model.IIntegralShare;
import com.yzb.card.king.ui.integral.model.impl.IntegralShareImpl;
import com.yzb.card.king.ui.integral.view.IntegralShareView;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 15:48
 */
public class IntegralSharePresenter
{
    private IntegralShareView view;
    private IIntegralShare model;

    public IntegralSharePresenter(IntegralShareView view)
    {
        this.view = view;
        model = new IntegralShareImpl(new ContactCallBack());
    }

    public void loadContacts(String contactType)
    {
        model.loadContacts(contactType);
    }

    private class ContactCallBack extends HttpCallBackImpl{
        @Override
        public void onFailed(Object o)
        {
            view.onContactLoadFailed(o);
        }

        @Override
        public void onSuccess(Object o)
        {
            view.onContactsLoadSuc( (List< IntegralShareLinkman >) o);
        }
    }
}
