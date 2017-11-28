package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.app.model.UpdateUserInfoModel;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.UserManager;

import java.util.Map;

/**
 * 功能：更新用户信息
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class UpdateUserInfoPresenter implements  UpdateUserInfoCallBack
{
    private BaseViewLayerInterface view;
    private UpdateUserInfoModel model;

    public UpdateUserInfoPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new UpdateUserInfoModel(this);
    }

    public void update(Map<String, Object> param)
    {

        model.loadData(param);
    }

    @Override
    public void onListenSuccess(UserBean userBean)
    {
        userBean.setMobileIsChanged(false);
        com.yzb.card.king.ui.manage.UserManager.getInstance().setUserBean(userBean);
        UserManager.getInstance().update();
        view.callSuccessViewLogic(null,3);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,3);
    }
}
