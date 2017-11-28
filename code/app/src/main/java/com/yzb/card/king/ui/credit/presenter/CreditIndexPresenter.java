package com.yzb.card.king.ui.credit.presenter;

import android.support.v4.app.Fragment;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.model.CreditIndexDao;
import com.yzb.card.king.ui.credit.model.CreditIndexImpl;
import com.yzb.card.king.ui.manage.UserManager;

import java.util.List;

/**
 * 类  名：首页信用卡观察者
 * 作  者：Li Yubing
 * 日  期：2016/8/9
 * 描  述：
 */
public class CreditIndexPresenter implements CreditIndexCallBack {


    private Fragment fragment;

    private BaseViewLayerInterface creditIndexView;

    private CreditIndexImpl impl;


    public CreditIndexPresenter(BaseViewLayerInterface creditIndexView, Fragment fragment) {

        this.fragment = fragment;

        this.creditIndexView = creditIndexView;

        impl = new CreditIndexDao(this);

    }

    /**
     * 查询用户已绑定的信用卡信息
     */
    public void queryUserBindCard() {

        if (!UserManager.getInstance().isLogin()) {

            creditIndexView.callFailedViewLogic(null,-1);

            return;

        }
        impl.sendCreditBillRequest();

    }


    @Override
    public void requestFailedDataCall(Object o, int type) {

        if (type == 1) {

            creditIndexView.callFailedViewLogic(null,-1);
        }
    }

    @Override
    public void requestSuccess(Object o, int type) {


        if (type == 1) {
            List<CreditCard> busShopList = (List<CreditCard>) o;


            creditIndexView.callSuccessViewLogic(busShopList,1);
        }
    }
}
