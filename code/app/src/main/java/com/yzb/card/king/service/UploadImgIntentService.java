package com.yzb.card.king.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.yzb.card.king.http.common.UploadImageRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传图片服务；支持队列任务列表；
 * 使用方法：直接调用downloadFile方法即可；
 *
 * @author gengqiyun
 * @date 2016.8.9
 */
public class UploadImgIntentService extends IntentService
{
    private static List<String> imgArray;//图片的base64位编码；
    private static List<String> codeArray;//图片的code码

    public UploadImgIntentService()
    {
        super("UploadImgIntentService");
    }

    /**
     * 停止服务；
     *
     * @param context
     */
    public static void stop(Context context)
    {
        imgArray = null;
        codeArray = null;
        Intent intent = getServiceIntent(context);
        context.stopService(intent);
    }

    /**
     * 上传图片；
     *
     * @param imgArrayArg  图片的base64位编码；
     * @param codeArrayArg 图片的code码；
     */
    public static void uploadFile(Context context, List<String> imgArrayArg, List<String> codeArrayArg)
    {
        if (imgArrayArg == null || codeArrayArg == null)
        {
            return;
        }
        stop(context);

        imgArray = imgArrayArg;
        codeArray = codeArrayArg;
        Intent intent = getServiceIntent(context);
        intent.setAction(AppConstant.UPLOAD_SERVICE_ACTION);
        context.startService(intent);
    }

    @Override
    public void onDestroy()
    {
        imgArray = null;
        codeArray = null;
        super.onDestroy();
    }

    private static Intent getServiceIntent(Context context)
    {
        Intent intent = new Intent(context, UploadImgIntentService.class);
        intent.setAction(AppConstant.UPLOAD_SERVICE_ACTION);
        return intent;
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            String action = intent.getAction();
            if (AppConstant.UPLOAD_SERVICE_ACTION.equals(action))
            {
                startUpload();
            }
        }
    }

    /**
     * 开始上传；
     */
    private void startUpload()
    {
        LogUtil.e("服务启动，开始上传......");
        for (int i = 0; i < imgArray.size(); i++)
        {

            LogUtil.e("UUUU",""+ codeArray.get(i)  +"    "+codeArray.get(i)  +"   "+imgArray.get(i)    );

            Map<String, String> params = new HashMap<>();
            params.put("code", codeArray.get(i));
            params.put("fileName", codeArray.get(i) + ".jpg");
            params.put("content", imgArray.get(i));
            params.put("source", "app");
            LogUtil.e("上传第" + (i + 1) + "张图片");

            new UploadImageRequest(params).sendRequest(null);
        }
    }
}
