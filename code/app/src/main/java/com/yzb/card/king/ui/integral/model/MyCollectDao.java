package com.yzb.card.king.ui.integral.model;


import com.yzb.card.king.bean.user.UserCollectBean;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public interface MyCollectDao {

    /**
     * 获取我的收藏信息
     * @param type
     * type=1 商铺
     * type=0 商品
     */
    public void getMyCollectInfo(String type);


    /**
     * 删除我的收藏
     * @param ucBean
     */
    public void deleteMyCollect(UserCollectBean ucBean,String type);

    void setOnDataLoadFinish(OnDataLoadFinish onDataLoadFinish);
}
