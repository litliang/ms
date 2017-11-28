package com.yzb.card.king.ui.manage;

import com.yzb.card.king.ui.discount.bean.BusTypeBean;
import com.yzb.card.king.ui.discount.bean.BusTypeGradeBean;

/**
 * 租车业务管理器
 * Created by tarena on 2016/5/28.
 */
public class RentCarLogicManager
{

    private boolean isModfyFlag = false;

    private static RentCarLogicManager instance = null;

    /**
     * 自驾租车开始日期时间戳
     */
    private long startCurrentTime;

    /**
     * 自驾租车结束日期时间戳
     */
    private long endCurrentTime;

    /**
     * 选中的附近门店对象
     */
    private Object o = null;

    private int placeId;//城市Id

    private String placeName;//城市名

    private BusTypeBean busTypeBean;//选择的车型

    private BusTypeGradeBean busTypeGradeBeanTemp;

    /**
     * 日租包车
     */
    private long dailyRentStartTime;//dailyrent
    /**
     * 用车时长
     */
    private float useBusDuration;

    private boolean flagBack = true;

    public boolean isFlagBack()
    {
        return flagBack;
    }

    public void setFlagBack(boolean flagBack)
    {
        this.flagBack = flagBack;
    }

    private RentCarLogicManager()
    {

    }

    public static RentCarLogicManager getInstance()
    {

        if (instance == null)
        {
            instance = new RentCarLogicManager();
        }
        return instance;

    }

    public void setPlaceName(String placeName)
    {

        this.placeName = placeName;
    }

    public String getPlaceName()
    {

        return placeName;
    }

    public boolean isModfyFlag()
    {
        return isModfyFlag;
    }

    public void setModfyFlag(boolean modfyFlag)
    {
        isModfyFlag = modfyFlag;
    }

    public long getStartCurrentTime()
    {
        return startCurrentTime;
    }

    public void setStartCurrentTime(long startCurrentTime)
    {
        this.startCurrentTime = startCurrentTime;
    }

    public long getEndCurrentTime()
    {
        return endCurrentTime;
    }

    public void setEndCurrentTime(long endCurrentTime)
    {
        this.endCurrentTime = endCurrentTime;
    }


    public void setPlaceId(int placeId)
    {

        this.placeId = placeId;
    }

    public int getPlaceId()
    {

        return placeId;
    }

    public float getUseBusDuration()
    {
        return useBusDuration;
    }

    public void setUseBusDuration(float useBusDuration)
    {
        this.useBusDuration = useBusDuration;
    }

    public long getDailyRentStartTime()
    {
        return dailyRentStartTime;
    }

    public void setDailyRentStartTime(long dailyRentStartTime)
    {
        this.dailyRentStartTime = dailyRentStartTime;
    }

    public Object getO()
    {
        return o;
    }

    public void setO(Object o)
    {
        this.o = o;
    }

    /**
     * 清理自驾租车缓存数据
     */
    public void clearAllData()
    {

        o = null;

        startCurrentTime = 0L;

        endCurrentTime = 0L;

        isModfyFlag = false;

        busTypeBean = null;

        busTypeGradeBeanTemp = null;

        useBusDuration = 0;

        dailyRentStartTime = 0;
    }


    public void setBusTypeBeanObject(Object busTypeBean1)
    {
        this.busTypeBean = (BusTypeBean) busTypeBean1;
    }

    public void setBusTypeBean(BusTypeBean busTypeBean)
    {

        this.busTypeBean = busTypeBean;
    }

    public BusTypeBean getBusTypeBean()
    {

        return busTypeBean;
    }

    public void setBusTypeGradeBean(BusTypeGradeBean busTypeGradeBeanTemp)
    {

        this.busTypeGradeBeanTemp = busTypeGradeBeanTemp;
    }

    public BusTypeGradeBean getBusTypeGradeBeanTemp()
    {

        return busTypeGradeBeanTemp;
    }
}
