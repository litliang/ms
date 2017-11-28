package com.yzb.card.king.bean.travel;

import java.util.List;

/**
 * 功能：旅游线路；
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelLineBean
{
    private boolean isSelct; //是否选中；

    private String lineId; //线路id；
    private String sleepDesc;//String	住宿描述	Y
    private int spotQuantity;//int	景点个数	N
    private int classicSpotQuantity;//	经典景点个数int		N
    private String classicSpotDesc;//	String	经典景点描述	Y	多个使用英文逗号分割
    private String lineUrl;//	String	线路详情	Y
    private String hotelName;    //String	酒店名称	Y	order by星级降序,入住日期升序，取第一笔，若酒店id都为空，取第一条
    private List<Meal> mealsList;    //List<Map>		用餐列表Y
    private List<Traffic> trafficList;    //List<Map>	交通列表	Y
    private List<Schedule> scheduleList;    //List<Map>	日程安排	Y

    public boolean isSelct()
    {
        return isSelct;
    }

    public void setIsSelct(boolean isSelct)
    {
        this.isSelct = isSelct;
    }

    public String getSleepDesc()
    {
        return sleepDesc;
    }

    public void setSleepDesc(String sleepDesc)
    {
        this.sleepDesc = sleepDesc;
    }

    public String getLineId()
    {
        return lineId;
    }

    public void setLineId(String lineId)
    {
        this.lineId = lineId;
    }

    public int getSpotQuantity()
    {
        return spotQuantity;
    }

    public void setSpotQuantity(int spotQuantity)
    {
        this.spotQuantity = spotQuantity;
    }

    public int getClassicSpotQuantity()
    {
        return classicSpotQuantity;
    }

    public void setClassicSpotQuantity(int classicSpotQuantity)
    {
        this.classicSpotQuantity = classicSpotQuantity;
    }

    public String getClassicSpotDesc()
    {
        return classicSpotDesc;
    }

    public void setClassicSpotDesc(String classicSpotDesc)
    {
        this.classicSpotDesc = classicSpotDesc;
    }

    public String getLineUrl()
    {
        return lineUrl;
    }

    public void setLineUrl(String lineUrl)
    {
        this.lineUrl = lineUrl;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public List<Meal> getMealsList()
    {
        return mealsList;
    }

    public void setMealsList(List<Meal> mealsList)
    {
        this.mealsList = mealsList;
    }

    public List<Traffic> getTrafficList()
    {
        return trafficList;
    }

    public void setTrafficList(List<Traffic> trafficList)
    {
        this.trafficList = trafficList;
    }

    public List<Schedule> getScheduleList()
    {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList)
    {
        this.scheduleList = scheduleList;
    }

    /**
     * 用餐；
     */
    public static class Meal
    {
        private String mealsMode;    //String	用餐方式	N	1酒店内、2自理、3团餐
        private int mealsQuantity;    //int	用餐个数	N
        private String mealsDesc;//String	用餐描述	N

        public String getMealsMode()
        {
            return mealsMode;
        }

        public void setMealsMode(String mealsMode)
        {
            this.mealsMode = mealsMode;
        }

        public String getMealsDesc()
        {
            return mealsDesc;
        }

        public void setMealsDesc(String mealsDesc)
        {
            this.mealsDesc = mealsDesc;
        }

        public int getMealsQuantity()
        {
            return mealsQuantity;
        }

        public void setMealsQuantity(int mealsQuantity)
        {
            this.mealsQuantity = mealsQuantity;
        }
    }

    /**
     * 交通；
     */
    public static class Traffic
    {
        private String trafficName;//	String	交通工具名称	N
        private int trafficLength;//String	交通时长	N	分钟

        public String getTrafficName()
        {
            return trafficName;
        }

        public void setTrafficName(String trafficName)
        {
            this.trafficName = trafficName;
        }

        public int getTrafficLength()
        {
            return trafficLength;
        }

        public void setTrafficLength(int trafficLength)
        {
            this.trafficLength = trafficLength;
        }
    }

    /**
     * 日程安排；
     */
    public static class Schedule
    {
        private int scheduleDay;//int	第几日	N
        private String scheduleName;//String	日程名称	Y
        private List<MyPlan> planList;    //List<Map>	计划列表	Y

        public int getScheduleDay()
        {
            return scheduleDay;
        }

        public void setScheduleDay(int scheduleDay)
        {
            this.scheduleDay = scheduleDay;
        }

        public String getScheduleName()
        {
            return scheduleName;
        }

        public void setScheduleName(String scheduleName)
        {
            this.scheduleName = scheduleName;
        }

        public List<MyPlan> getPlanList()
        {
            return planList;
        }

        public void setPlanList(List<MyPlan> planList)
        {
            this.planList = planList;
        }
    }

    /**
     * 计划列表；
     */
    public static class MyPlan
    {
        private String planId;//Long		计划id  N
        private String planType;//String	计划类型	N	1用餐、2景点、3住宿、4交通
        private String planTime;//	String	计划开始时间	Y	HH:mm:ss
        private int planeDuration;//	String	计划时长	Y
        private String planMileage;//double	计划里程	Y  公里；
        private int planMileageDuration;//	String	计划里程行驶时长	Y  (分钟)
        private String planSummary;//	String	计划说明	Y
        private String planDetailId;//	Long	计划详情id	Y
        private String planDetailName;//String	计划详情名称	Y
        private String mealsType;//	String	用餐类型	Y	1早餐、2午餐、3晚餐
        private String mealsMode;//	String	用餐方式	Y	1酒店内、2自理、3团餐
        private String spotPhotosCodes;//String	景点图片	Y
        private String isClassic;  //String	景点是否经典	Y	1是；0否
        private String trafficType;//	String	交通工具类型	Y	0无、1直飞、2中转
        private String trafficCode;//	String	交通工具图标	Y
        private List<MyHotel> hotelList;//List<Map>	酒店列表	Y

        private CharSequence contactSeq; //酒店名称组合；只在客户端使用；


        public CharSequence getContactSeq()
        {
            return contactSeq;
        }

        public void setContactSeq(CharSequence contactSeq)
        {
            this.contactSeq = contactSeq;
        }

        public String getPlanId()
        {
            return planId;
        }

        public void setPlanId(String planId)
        {
            this.planId = planId;
        }

        public String getPlanType()
        {
            return planType;
        }

        public void setPlanType(String planType)
        {
            this.planType = planType;
        }

        public String getPlanTime()
        {
            return planTime;
        }

        public void setPlanTime(String planTime)
        {
            this.planTime = planTime;
        }

        public int getPlaneDuration()
        {
            return planeDuration;
        }

        public void setPlaneDuration(int planeDuration)
        {
            this.planeDuration = planeDuration;
        }

        public String getPlanMileage()
        {
            return planMileage;
        }

        public void setPlanMileage(String planMileage)
        {
            this.planMileage = planMileage;
        }

        public int getPlanMileageDuration()
        {
            return planMileageDuration;
        }

        public void setPlanMileageDuration(int planMileageDuration)
        {
            this.planMileageDuration = planMileageDuration;
        }

        public String getPlanSummary()
        {
            return planSummary;
        }

        public void setPlanSummary(String planSummary)
        {
            this.planSummary = planSummary;
        }

        public String getPlanDetailId()
        {
            return planDetailId;
        }

        public void setPlanDetailId(String planDetailId)
        {
            this.planDetailId = planDetailId;
        }

        public String getPlanDetailName()
        {
            return planDetailName;
        }

        public void setPlanDetailName(String planDetailName)
        {
            this.planDetailName = planDetailName;
        }

        public String getMealsType()
        {
            return mealsType;
        }

        public void setMealsType(String mealsType)
        {
            this.mealsType = mealsType;
        }

        public String getMealsMode()
        {
            return mealsMode;
        }

        public void setMealsMode(String mealsMode)
        {
            this.mealsMode = mealsMode;
        }

        public String getSpotPhotosCodes()
        {
            return spotPhotosCodes;
        }

        public void setSpotPhotosCodes(String spotPhotosCodes)
        {
            this.spotPhotosCodes = spotPhotosCodes;
        }

        public String getIsClassic()
        {
            return isClassic;
        }

        public void setIsClassic(String isClassic)
        {
            this.isClassic = isClassic;
        }

        public String getTrafficType()
        {
            return trafficType;
        }

        public void setTrafficType(String trafficType)
        {
            this.trafficType = trafficType;
        }

        public String getTrafficCode()
        {
            return trafficCode;
        }

        public void setTrafficCode(String trafficCode)
        {
            this.trafficCode = trafficCode;
        }

        public List<MyHotel> getHotelList()
        {
            return hotelList;
        }

        public void setHotelList(List<MyHotel> hotelList)
        {
            this.hotelList = hotelList;
        }
    }

    /**
     * 酒店；
     */
    public static class MyHotel
    {
        private String hotelId;//	Long	酒店id	Y
        private String hotelName;//	String	酒店名称	N

        public String getHotelId()
        {
            return hotelId;
        }

        public void setHotelId(String hotelId)
        {
            this.hotelId = hotelId;
        }

        public String getHotelName()
        {
            return hotelName;
        }

        public void setHotelName(String hotelName)
        {
            this.hotelName = hotelName;
        }
    }
}
