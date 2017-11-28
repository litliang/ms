package com.yzb.card.king.ui.integral.presenter;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.yzb.card.king.bean.integral.AccountSchemaBean;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.ui.integral.model.impl.AccountSchemaImpl;
import com.yzb.card.king.ui.integral.model.IAccountSchema;
import com.yzb.card.king.ui.integral.view.AccountSchemaView;
import com.yzb.card.king.util.LogUtil;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 描述：账户概要提供者类
 * 作者：殷曙光
 * 日期：2016/9/12 11:13
 */
public class AccountSchemaPresenter
{
    private AccountSchemaView view;
    private IAccountSchema model;

    public AccountSchemaPresenter(){
        model = new AccountSchemaImpl(new LoadDataCallBack(),new WalletCallBack());
    }

    public AccountSchemaPresenter(AccountSchemaView view)
    {
        this.view = view;
        model = new AccountSchemaImpl(new LoadDataCallBack(),new WalletCallBack());
    }

    /**
     *
     */
    public void loadData()
    {
        model.loadData();
    }


    /**
     *
     * @param id
     * @param accountSchemaActivity
     */
    public void sendUserIntegralRequest(String id, Activity accountSchemaActivity)
    {
        model.sendUserIntegralRequest(id,accountSchemaActivity);

    }

    /**
     *
     * @param imageUrl
     * @param imageOptionsLogo
     */
    public void loadImage(String imageUrl, ImageOptions imageOptionsLogo)
    {
        x.image().loadDrawable(imageUrl,imageOptionsLogo,new CustomBitmapLoadCallBack());
    }

    private class LoadDataCallBack extends HttpCallBackImpl{
        @Override
        public void onFailed(Object o)
        {
        }

        @Override
        public void onSuccess(Object o)
        {
            if( view != null) {

                AccountSchemaBean bean = (AccountSchemaBean) o;
                view.updateUi(bean);

            }
        }
    }

    private class WalletCallBack implements AccountSchemaImpl.WalletRequest
    {
        @Override
        public void userIntegralCallBack(Object o)
        {
            if( view != null) {

                view.userIntegralCallBack(null);
            }
        }

        @Override
        public void userGiftCallBack(Object o)
        {
            if( view != null) {

                view.userGiftCallBack(null);
            }
        }

        @Override
        public void userMoneyCallBack(Object o)
        {
            if( view != null) {

                view.userMoneyCallBack(null);
            }
        }

        @Override
        public void userRedPackCallBack(Object o)
        {
            if( view != null) {

                view.userRedPackCallBack(null);
            }
        }
    }

    /**
     * 图片加载进度
     */
    public class CustomBitmapLoadCallBack implements Callback.ProgressCallback<Drawable>
    {
        public CustomBitmapLoadCallBack()
        {
            //  this.holder = holder;
        }

        @Override
        public void onWaiting()
        {
            //this.holder.imgPb.setProgress(0);
            LogUtil.e("-------onWaiting-------");
        }

        @Override
        public void onStarted()
        {
            LogUtil.e("----onStarted----------");
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading)
        {
            //this.holder.imgPb.setProgress((int) (current * 100 / total));

            LogUtil.e("onLoading-->total=" + total + "  current=" + current + "  isDownloading=" + isDownloading);
        }

        @Override
        public void onSuccess(Drawable result)
        {
            if( view != null) {

                view.onImageLoadUp(result);
            }
            //this.holder.imgPb.setProgress(100);
            LogUtil.e("-----onSuccess---------");
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback)
        {
            LogUtil.e("-------onError-------");
        }

        @Override
        public void onCancelled(CancelledException cex)
        {
            LogUtil.e("------onCancelled--------");
        }

        @Override
        public void onFinished()
        {
            LogUtil.e("--------onFinished------");
        }
    }
}
