package com.yzb.card.king.ui.other.controller;

import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.ui.app.bean.UploadImage;
import com.yzb.card.king.ui.app.task.UploadTask;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/5 14:50
 */
public class UploadController
{
    private BaseCallBack<List<String>> callBack;
    private List<String> codes = new ArrayList<>();

    public void setCallBack(BaseCallBack<List<String>> callBack)
    {
        this.callBack = callBack;
    }

    public void uploadImage(final List<UploadImage> images)
    {
        if (images != null && images.size() > 0)
        {
            UploadTask task = new UploadTask(images.remove(0));
            task.setCallBack(new BaseCallBack<String>()
            {
                @Override
                public void onSuccess(String data)
                {
                    codes.add(data);
                    LogUtil.e("剩余" + images.size() + "张");
                    if (images.size() > 0)
                    {
                        uploadImage(images);
                    } else
                    {
                        if (callBack != null)
                            callBack.onSuccess(codes);
                    }
                }

                @Override
                public void onFail(Error error)
                {
                    if (callBack != null)
                        callBack.onFail(error);
                }
            });
            task.execute();
        }
    }


}
