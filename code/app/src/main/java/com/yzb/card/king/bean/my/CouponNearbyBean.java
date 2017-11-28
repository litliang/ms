package com.yzb.card.king.bean.my;

import java.util.List;

/**
 * 功能：查询优惠筛选列表
 *
 * @author:gengqiyun
 * @date: 2017/1/17
 */
public class CouponNearbyBean
{
    private List<String> distanceList;  //距离列表
    private List<Region> cantonList;  //行政区列表
    private List<Circle> circleList;  //商圈列表

    public List<String> getDistanceList()
    {
        return distanceList;
    }

    public void setDistanceList(List<String> distanceList)
    {
        this.distanceList = distanceList;
    }

    public List<Region> getCantonList()
    {
        return cantonList;
    }

    public void setCantonList(List<Region> cantonList)
    {
        this.cantonList = cantonList;
    }

    public List<Circle> getCircleList()
    {
        return circleList;
    }

    public void setCircleList(List<Circle> circleList)
    {
        this.circleList = circleList;
    }


    public static class Region
    {
        private long cantonId; //行政区id
        private String cantonName; //行政区名称

        public Region()
        {
        }

        public Region(long cantonId, String cantonName)
        {
            this.cantonId = cantonId;
            this.cantonName = cantonName;
        }

        public long getCantonId()
        {
            return cantonId;
        }

        public void setCantonId(long cantonId)
        {
            this.cantonId = cantonId;
        }

        public String getCantonName()
        {
            return cantonName;
        }

        public void setCantonName(String cantonName)
        {
            this.cantonName = cantonName;
        }
    }

    public static class Circle
    {
        private long circleId; //商圈id
        private String circleName; //商圈名称

        public Circle()
        {
        }

        public Circle(long circleId, String circleName)
        {
            this.circleId = circleId;
            this.circleName = circleName;
        }

        public long getCircleId()
        {
            return circleId;
        }

        public void setCircleId(long circleId)
        {
            this.circleId = circleId;
        }

        public String getCircleName()
        {
            return circleName;
        }

        public void setCircleName(String circleName)
        {
            this.circleName = circleName;
        }
    }

    /**
     * 在首部追加不限；
     */
    public void appendOne()
    {
        if (distanceList != null)
        {
            distanceList.add(0, "不限");
        }
        if (cantonList != null)
        {
            cantonList.add(0, new Region(Integer.MAX_VALUE, "不限"));
        }
        if (circleList != null)
        {
            circleList.add(0, new Circle(Integer.MAX_VALUE, "不限"));
        }
    }
}
