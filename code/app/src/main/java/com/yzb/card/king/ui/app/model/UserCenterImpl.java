package com.yzb.card.king.ui.app.model;

import android.app.Activity;
import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.app.bean.UploadImage;
import com.yzb.card.king.ui.app.presenter.UpdateUserInfoPresenter;
import com.yzb.card.king.ui.app.task.UploadTask;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.wallet.openInterface.OrderHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：用户中心数据层
 * 作者：殷曙光
 * 日期：2016/9/14 9:58
 */
public class UserCenterImpl implements IUserCenter, BaseViewLayerInterface
{
    private UserBean userBean;
    private CallBack callBack;
    private UpdateUserInfoPresenter infoPresenter;

    public UserCenterImpl(CallBack callBack)
    {
        this.callBack = callBack;
        this.userBean = UserManager.getInstance().getUserBean();
    }

    @Override
    public void upLoadImage(Bitmap bitmap)
    {
        infoPresenter = new UpdateUserInfoPresenter(this);

        UploadTask task = new UploadTask(new UploadImage(bitmap));

        task.setCallBack(new BaseCallBack<String>()
        {
            @Override
            public void onSuccess(String data)
            {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("pic",data);
                infoPresenter.update(param);
            }

            @Override
            public void onFail(Error error)
            {

            }
        });
        task.execute();

    }

    @Override
    public void getConsume(Activity activity)
    {
        OrderHandle payHandle = new OrderHandle(activity);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

            }
            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                if ("0000".equals(RESULT_CODE) && resultMap.get("data") != null)
                {
                    Map<String, Object> map = JSON.parseObject(resultMap.get("data"), Map.class);
                    if (map != null)
                    {
                        userBean.setConsume((int) Float.parseFloat(String.valueOf(map.get("transferAmount"))));

                    } else
                    {
                        userBean.setConsume(0);
                    }

                } else
                {
                    userBean.setConsume(0);

                }
                callBack.notifyDataChanged();
            }

            @Override
            public void setError(String RESULT_CODE, String RESULT_MSG)
            {
                System.out.println("======订单汇总 错误=======>" + RESULT_CODE);
                userBean.setConsume(0);
            }
        });
        payHandle.stat(userBean.getAmountAccount());
    }

    @Override
    public void getUserInfo()
    {

        SimpleRequest request = new SimpleRequest("PersonInfo");
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                userBean = JSON.parseObject((String) o, UserBean.class);
                callBack.notifyDataChanged();
            }

            @Override
            public void onFailed(Object o)
            {

            }
        });
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }

    public interface CallBack
    {
        void notifyDataChanged();



    }
}
