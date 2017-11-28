package com.yzb.card.king.ui.app.task;

import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.common.UploadImageRequest;
import com.yzb.card.king.ui.app.bean.UploadImage;

import java.util.HashMap;
import java.util.Map;

public class UploadTask  implements HttpCallBackData
{
    UploadImage uploadImage;

    BaseCallBack<String> callBack;

    private UploadImageRequest request;

    public void setCallBack(BaseCallBack<String> callBack)
    {
        this.callBack = callBack;
    }

    public UploadTask(UploadImage uploadImage)
    {
        this.uploadImage = uploadImage;

        request = new UploadImageRequest();
    }



    public void execute(){

        if (uploadImage != null) {

        Map<String, String> param = new HashMap<>();
        param.put("fileName", uploadImage.getFileName());
        param.put("content", uploadImage.getBase64());
        param.put("source", "app");

        request.sendUploadImageRequest(param,this);

        }

    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onSuccess(Object o)
    {
        Map<String,String > result = (Map<String, String>) o;

        if (result != null && "0000".equals(result.get("code")))
        {
            if (callBack != null)
            {
                callBack.onSuccess(result.get("imageCode"));
            }
        } else
        {
            if (callBack != null)
            {
                callBack.onFail(new Error("", "图片上传失败"));
            }
        }
    }

    @Override
    public void onFailed(Object o)
    {
        if (callBack != null)
        {
            callBack.onFail(new Error("", "图片上传失败"));
        }
    }

    @Override
    public void onCancelled(Object o)
    {
        if (callBack != null)
        {
            callBack.onFail(new Error("", "图片上传失败"));
        }
    }

    @Override
    public void onFinished()
    {

    }
}