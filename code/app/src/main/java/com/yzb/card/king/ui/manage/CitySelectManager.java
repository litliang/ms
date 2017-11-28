package com.yzb.card.king.ui.manage;

import com.yzb.card.king.ui.other.bean.IPlace;

/**
 * 类  名：城市选择管理
 * 作  者：Jin jiayu
 * 日  期：2016/6/3
 * 描  述：
 */
public class CitySelectManager
{

    private static CitySelectManager instance = null;

    private IPlace place;

    private boolean loadPlaceFlag = true;



    private CitySelectManager()
    {

    }

    public static CitySelectManager getInstance()
    {

        if (instance == null)
        {

            instance = new CitySelectManager();
        }

        return instance;
    }


    public String getPlaceName()
    {
        return place == null ? "" : place.getPlaceName();
    }

    public String getPlaceId()
    {
        return place == null ? "" : place.getPlaceId();
    }

    public IPlace getPlace()
    {
        return place;
    }

    public void setPlace(IPlace iPlace)
    {
        this.place = iPlace;
    }



    /**
     * 清理缓存数据
     */
    public void clearData()
    {
        place = null;
        loadPlaceFlag = true;
    }

    public boolean isLoadPlaceFlag()
    {
        return loadPlaceFlag;
    }

    public void setLoadPlaceFlag(boolean loadPlaceFlag)
    {
        this.loadPlaceFlag = loadPlaceFlag;
    }
}
