package com.yzb.card.king.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载服务；支持队列任务列表；
 * 使用方法：直接调用downloadFile方法即可；
 *
 * @author gengqiyun
 * @date 2016.7.27
 */
public class DownloadIntentService extends IntentService
{

    public DownloadIntentService()
    {
        super("DownloadIntentService");
    }

    /**
     * 停止服务；
     *
     * @param context
     */
    public static void stop(Context context)
    {
        Intent intent = getServiceIntent(context);
        context.stopService(intent);
    }

    /**
     * @param context
     * @param downloadUrl
     * @param targetFilePath 文件保存路径（带扩展名）；
     */
    public static void downloadFile(Context context, String downloadUrl, String targetFilePath)
    {
        if (context == null || TextUtils.isEmpty(downloadUrl) || TextUtils.isEmpty(targetFilePath))
        {
            return;
        }
        Intent intent = getServiceIntent(context);
        intent.setAction(AppConstant.DOWNLOAD_SERVICE_ACTION);
        intent.putExtra("downloadUrl", downloadUrl);
        intent.putExtra("targetFilePath", targetFilePath);
        context.startService(intent);
    }

    private static Intent getServiceIntent(Context context)
    {
        Intent intent = new Intent(context, DownloadIntentService.class);
        intent.setAction(AppConstant.DOWNLOAD_SERVICE_ACTION);
        return intent;
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            String action = intent.getAction();
            if (AppConstant.DOWNLOAD_SERVICE_ACTION.equals(action))
            {
                String url = intent.getStringExtra("downloadUrl");
                String targetFilePath = intent.getStringExtra("targetFilePath");
                startDownload(url, targetFilePath);
            }
        }
    }

    /**
     * 通过HttpUrlConnection下载；
     *
     * @param downloadUrl
     * @param targetFilePath
     */
    public void startDownload(String downloadUrl, String targetFilePath)
    {
        LogUtil.i("开始下载...");

        FileOutputStream fos = null;
        InputStream is = null;
        HttpURLConnection urlConnection = null;
        LogUtil.i("文件下载地址==" + targetFilePath);
        if (TextUtils.isEmpty(targetFilePath))
        {
            return;
        }

        try
        {
            URL url = new URL(downloadUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(20000);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(20000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                is = urlConnection.getInputStream();
                fos = new FileOutputStream(new File(targetFilePath));
                // 4KB缓存；
                byte[] buffer = new byte[4 * 1024];
//                int total = urlConnection.getContentLength();
                int count;
//                int current = 0;
                while ((count = is.read(buffer)) != -1)
                {
                    fos.write(buffer, 0, count);
//                    current += count;
                    LogUtil.i("count==" + count);
                }

                // 下载完成；
                broastcastResult(true);
            } else
            {
                // 下载失败；
                broastcastResult(false);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            broastcastResult(false);
        } finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
                if (urlConnection != null)
                {
                    // 断开连接；
                    urlConnection.disconnect();
                }
                if (fos != null)
                {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 广播提醒；
     *
     * @param flag true:下载成功；false：下载失败；
     */
    public void broastcastResult(boolean flag)
    {
        Intent intent = new Intent(AppConstant.DOWNLOAD_SERVICE_ACTION);
        intent.putExtra("isSuccess", flag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
