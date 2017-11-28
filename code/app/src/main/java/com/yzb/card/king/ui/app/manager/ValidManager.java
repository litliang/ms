package com.yzb.card.king.ui.app.manager;

import android.app.Activity;

import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.ui.app.bean.UploadImage;
import com.yzb.card.king.ui.other.controller.UploadController;
import com.yzb.card.king.util.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：殷曙光
 * 日期：2016/6/20 18:54
 * 描述：管理验证第一步到结束的activity，最后一步完成时将结束前面所有步骤的activity
 */
public class ValidManager
{
    private static ValidManager validManager;

    private ValidManager()
    {
    }

    public static ValidManager getValidManager()
    {
        if (validManager == null)
        {
            validManager = new ValidManager();
        }
        return validManager;
    }

    private List<Activity> activities = new LinkedList<>();
    private List<UploadImage> images;
    private UploadImage bankImage;
    private BaseCallBack<List<String>> callBack;

    public List<UploadImage> getImages()
    {
        return images;
    }

    public void setImages(List<UploadImage> images)
    {
        this.images = images;
    }

    public UploadImage getBankImage()
    {
        return bankImage;
    }

    public void setBankImage(UploadImage bankImage)
    {
        this.bankImage = bankImage;
    }

    public void setCallBack(BaseCallBack<List<String>> callBack)
    {
        this.callBack = callBack;
    }

    public void startUpload()
    {
        if (images != null && bankImage != null)
        {
            images.add(bankImage);
        }
        LogUtil.e("共" + images.size() + "张");
        UploadController controller = new UploadController();
        controller.setCallBack(callBack);
        controller.uploadImage(images);

    }


    public void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    public void finishAll()
    {
        List<Activity> activitiesCopy = new LinkedList<>();
        activitiesCopy.addAll(activities);
        for (int i = 0; i < activitiesCopy.size(); i++)
        {
            Activity activity = activities.remove(0);
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
        clear();
    }

    private void clear()
    {
        setCallBack(null);
    }
}
