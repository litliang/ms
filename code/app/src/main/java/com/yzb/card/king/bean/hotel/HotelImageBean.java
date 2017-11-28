package com.yzb.card.king.bean.hotel;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/24
 * 描  述：
 */
public class HotelImageBean implements Serializable{
    /**
     * 类别名称
     */
    private String typeName ;

    private List<ImageBean> photosList;


    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public List<ImageBean> getPhotosList()
    {
        return photosList;
    }

    public void setPhotosList(List<ImageBean> photosList)
    {
        this.photosList = photosList;
    }

    public static class  ImageBean implements Serializable{
        /**
         * 图片id
         */
        private long photoId;
        /**
         * 图片url
         */
        private String photoUrl;

        public long getPhotoId()
        {
            return photoId;
        }

        public void setPhotoId(long photoId)
        {
            this.photoId = photoId;
        }

        public String getPhotoUrl()
        {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl)
        {
            this.photoUrl = photoUrl;
        }
    }


}
