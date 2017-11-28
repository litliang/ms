package com.yzb.card.king.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * 检查更新app
 * 作者：刘锐 on 2017/2/28 14:07
 * 邮箱：liurui@payvel.com
 */

public class UpdateAppManager
{

    // 文件分隔符
    private static final String FILE_SEPARATOR = "/";
    // 外存sdcard存放路径
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "Card_King" + FILE_SEPARATOR;
    // 下载应用存放全路径
    private static final String FILE_NAME = FILE_PATH + "card_king.apk";
    // 更新应用版本标记
    private static final int UPDARE_TOKEN = 0x29;
    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 0x31;

    private Context context;
    // 下载应用的对话框
    private Dialog dialog;
    // 下载应用的进度条
    private ProgressBar progressBar;
    // 进度条的当前刻度值
    private int curProgress;
    // 用户是否取消下载
    private boolean isCancel;
    private List<UpdateAppManager> bbUpdataList;
    private String str = "";
    private String android_url = "";

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case UPDARE_TOKEN:
                    progressBar.setProgress(curProgress);
                    break;

                case INSTALL_TOKEN:
                    installApp();
                    break;
            }
        }
    };

    public UpdateAppManager(Context context)
    {
        this.context = context;
    }


    /**
     * 显示提示更新对话框
     */
    public void showNoticeDialog(String str, final String filepath, final int limitVersionNoInt, final int versionInt)
    {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle("软件版本更新");
        mMaterialDialog.setMessage(str);
        mMaterialDialog.setPositiveButton("确定", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mMaterialDialog.dismiss();
                showDownloadDialog(filepath, limitVersionNoInt, versionInt);
            }
        });
        mMaterialDialog.setNegativeButton("取消", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GlobalApp.updateFlag = true;
                mMaterialDialog.dismiss();
                if (limitVersionNoInt > versionInt)
                {
                    System.exit(0);
                }
            }
        });
        mMaterialDialog.show();
    }

    /**
     * 显示下载进度对话框
     */
    public void showDownloadDialog(String filepath, final int limitVersionNoInt, final int versionInt)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.app_update_progress, null);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("软件版本更新");
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                isCancel = true;
                if (limitVersionNoInt > versionInt)
                {
                    System.exit(0);
                }
            }
        });
        dialog = builder.create();
        dialog.show();
        downloadApp(filepath);
    }

    /**
     * 下载新版本应用
     */
    private void downloadApp(final String filepath)
    {
        android_url = filepath;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                URL url = null;
                InputStream in = null;
                FileOutputStream out = null;
                HttpURLConnection conn = null;
                try
                {
                    url = new URL(android_url);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    long fileLength = conn.getContentLength();
                    in = conn.getInputStream();
                    File filePath = new File(FILE_PATH);
                    if (!filePath.exists())
                    {
                        filePath.mkdir();
                    }
                    out = new FileOutputStream(new File(FILE_NAME));
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    long readedLength = 0l;
                    while ((len = in.read(buffer)) != -1)
                    {
                        // 用户点击“取消”按钮，下载中断
                        if (isCancel)
                        {
                            break;
                        }
                        out.write(buffer, 0, len);
                        readedLength += len;
                        curProgress = (int) (((float) readedLength / fileLength) * 100);
                        handler.sendEmptyMessage(UPDARE_TOKEN);
                        if (readedLength >= fileLength)
                        {
                            dialog.dismiss();
                            // 下载完毕，通知安装
                            handler.sendEmptyMessage(INSTALL_TOKEN);
                            break;
                        }
                    }
                    out.flush();
                } catch (Exception e)
                {
                    e.printStackTrace();
                } finally
                {
                    if (out != null)
                    {
                        try
                        {
                            out.close();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    if (in != null)
                    {
                        try
                        {
                            in.close();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null)
                    {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 安装新版本应用
     */
    private void installApp()
    {
        File appFile = new File(FILE_NAME);
        if (!appFile.exists())
        {
            return;
        }
        // 跳转到新版本应用安装页面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
