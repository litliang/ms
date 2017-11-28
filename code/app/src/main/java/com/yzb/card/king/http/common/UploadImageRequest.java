package com.yzb.card.king.http.common;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;

import java.util.Map;

/**
 * 类  名：上传图片请求类
 * 作  者：Li Yubing
 * 日  期：2017/5/11
 * 描  述：
 */
public class UploadImageRequest extends BaseRequest{

    private Map<String, String> parameters ;

    public UploadImageRequest(Map<String, String> parameters){

        this.parameters = parameters;

    }

    public UploadImageRequest(){


    }



    @Override
    public void sendRequest(HttpCallBackData callBack)
    {

        if(parameters != null){
            sendUploadImageRequest(parameters, callBack);
        }

    }
}
