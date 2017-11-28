package com.yzb.card.king.ui.app.model;

import android.graphics.Bitmap;

import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.ui.app.task.UploadTask;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/6/19
 * 描  述：
 */
public class UploadImageImpl implements  UploadImage{

    public static final int RESPONSE_CODE = "uploadImage".hashCode();

    private BaseViewLayerInterface baseViewLayerInterface ;

    public UploadImageImpl(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;
    }

    @Override
    public void upLoadImage(Bitmap bitmap)
    {
        UploadTask task = new UploadTask(new com.yzb.card.king.ui.app.bean.UploadImage(bitmap));

        task.setCallBack(new BaseCallBack<String>()
        {
            @Override
            public void onSuccess(String data)
            {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("pic",data);

                baseViewLayerInterface.callSuccessViewLogic(data,RESPONSE_CODE);
            }

            @Override
            public void onFail(Error error)
            {
                String errorMsg ;

                if(error != null){

                    errorMsg = error.getError();
                }else{
                    errorMsg = "上传图片失败";
                }

                baseViewLayerInterface.callSuccessViewLogic(errorMsg,RESPONSE_CODE);
            }
        });
        task.execute();
    }
}
