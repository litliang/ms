package com.yzb.card.king.ui.discount.bean;

/**
 * Created by gengqiyun on 2016/5/2.
 * 有关图片的信息实体；
 * 评论页面使用；
 */
public class ImageInfoBean {
    //图片的id；作为唯一标识；
    private int imageId;
    //图片源文件路径；
    private String imagePath;
    //图片区域是否有图片；
    private boolean hasImage = false;
    //图片Base64；
    private String base64Str = "";

    public String getImgStr() {
        return base64Str;
    }

    public ImageInfoBean(int imageId, String base64Str, boolean hasImage) {
        this.imageId = imageId;
        this.base64Str = base64Str;
        this.hasImage = hasImage;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public ImageInfoBean setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageInfoBean setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
