package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseNoRefreshModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：重置登录密码
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class ResetLoginPwdModel implements BaseNoRefreshModel
{
    private BaseMultiLoadListener loadListener;

    public ResetLoginPwdModel(BaseMultiLoadListener loadListener)
    {
        this.loadListener = loadListener;
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        new SimpleRequest(CardConstant.setting_resetpswd, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data, String resultCode)
            {
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, data);
                }
            }

            @Override
            public void onFail(String failMsg, String resultCode)
            {
                if (AppConstant.CODE_PWD_SAME.equals(resultCode))
                {
                    failMsg = "新密码与旧密码不能相同";
                } else
                {
                    failMsg = "密码修改失败";
                }
                if (loadListener != null)
                {
                    loadListener.onListenError(failMsg);
                }
            }
        }).sendPostRequest();
    }
}
