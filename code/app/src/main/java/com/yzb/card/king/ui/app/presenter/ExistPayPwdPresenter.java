package com.yzb.card.king.ui.app.presenter;

import android.app.Activity;

import com.yzb.card.king.ui.app.model.ExistPayPwdModel;
import com.yzb.card.king.ui.app.view.ExistPayPwdView;
import com.yzb.card.king.ui.base.ExistPayPwdListener;

import java.util.Map;

/**
 * 功能：支付密码是否存在
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class ExistPayPwdPresenter implements ExistPayPwdListener
{
    private ExistPayPwdModel model;
    private ExistPayPwdView view;

    public ExistPayPwdPresenter(Activity context, ExistPayPwdView view)
    {
        this.view = view;
        model = new ExistPayPwdModel(context, this);
    }

    public void loadData(Map<String, String> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess()
    {
        view.onExistPayPwdSucess();
    }

    @Override
    public void onListenError(String code, String msg)
    {
        view.onExistPayPwdFail(code, msg);
    }
}
