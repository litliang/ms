package com.yzb.card.king.ui.manage;

import com.yzb.card.king.bean.travel.TravelCategorySBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 类  名：旅游数据缓存器
 * 作  者：Li Yubing
 * 日  期：2016/11/28
 * 描  述：
 */
public class TravelDataManager {

    private static TravelDataManager instance = null;

    private Object o;

    private List<TravelCategorySBean> list;

    private TravelDataManager.TravleListRequestBean requestBean;

    /**
     * 银行优惠状态
     */
    private String bankStatus = "0";//默认0  ------  0全部、1我的银行、2更多银行
    /**
     * 排序
     */
    private String sort = "1";// 默认 为1
    /**
     * 出发城市id
     */
    private String depCityId = null;
    /**
     * 目的类型
     */
    private String arrType = null;
    /**
     * 目的详情id
     */
    private String arrDetailId = null;
    /**
     * 供应商id
     */
    private String agentIds = null;
    /**
     * 特色ids
     */
    private String labelIds = null;
    /**
     * 最小价格
     */
    private String minPrice = null;

    /**
     * 最大价格
     */
    private String maxPrice = null;

    /**
     * 出行最小天数
     */
    private String minTravelDay = null;

    /**
     * 出行最大天数
     */
    private String maxTravelDay = null;

    /**
     * 出行开始日期
     */
    private String startTravelDate = null;

    /**
     * 出行结束日期
     */
    private String endTravelDate = null;


    private TravelDataManager()
    {

    }

    public static TravelDataManager getInstance()
    {

        if (instance == null) {

            instance = new TravelDataManager();
        }

        return instance;
    }

    /**
     * 设置初始请求参数
     */
    public void setRequestParameter()
    {

        if (requestBean == null) {

            TravelDataManager.TravleListRequestBean requestBean = TravelDataManager.getInstance().new TravleListRequestBean();

            this.requestBean = requestBean;

        }
        //requestBean.setProductType(requestBean.ge);
        /*
         必须传递入参信息
         */
        requestBean.setBankStatus(bankStatus);

        requestBean.setSort(sort);

        requestBean.setDepCityId(depCityId);

        requestBean.setArrType(arrType);

        requestBean.setArrDetailId(arrDetailId);
        /*
        非必须传递入参信息
         */
        requestBean.setAgentIds(agentIds);

        requestBean.setLabelIds(labelIds);

        requestBean.setMinPrice(minPrice);

        requestBean.setMaxPrice(maxPrice);

        requestBean.setMinTravelDay(minTravelDay);

        requestBean.setMaxTravelDay(maxTravelDay);

        requestBean.setStartTravelDate(startTravelDate);

        requestBean.setEndTravelDate(endTravelDate);

    }

    public String getBankStatus()
    {
        return bankStatus;
    }

    public TravleListRequestBean getRequestBean()
    {
        return requestBean;
    }

    public void setRequestBean(TravleListRequestBean requestBean)
    {
        this.requestBean = requestBean;
    }

    public Object getO()
    {
        return o;
    }


    public void setO(Object o)
    {
        this.o = o;
    }

    public List<TravelCategorySBean> getList()
    {
        return list;
    }

    public void setList(List<TravelCategorySBean> list)
    {
        this.list = list;
    }

    public String getEndTravelDate()
    {
        return endTravelDate;
    }

    public String getStartTravelDate()
    {
        return startTravelDate;
    }

    public String getArrDetailId()
    {
        return arrDetailId;
    }

    public String getArrType()
    {
        return arrType;
    }

    /**
     * 清理缓存数据
     */
    public void clearData()
    {

        requestBean = null;

        list.clear();

        list = null;

        o = null;

        instance = null;

    }

    /**
     * 清理旅游列表请求入参信息
     */
    public void clearTravleListRequestBean()
    {
        requestBean = null;
        bankStatus = null;
        sort = null;
        depCityId = null;
        arrType = null;
        arrDetailId = null;
        agentIds = null;
        labelIds = null;
        minPrice = null;

        maxPrice = null;

        minTravelDay = null;

        maxTravelDay = null;

        startTravelDate = null;
        endTravelDate = null;
    }

    public void setBankStatus(String bankStatus)
    {
        this.bankStatus = bankStatus;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public void setDepCityId(String depCityId)
    {
        this.depCityId = depCityId;
    }

    public String getDepCityId()
    {
        return depCityId;
    }

    public void setArrType(String arrType)
    {
        this.arrType = arrType;
    }

    public void setArrDetailId(String arrDetailId)
    {
        this.arrDetailId = arrDetailId;
    }

    public void setAgentIds(String agentIds)
    {
        this.agentIds = agentIds;
    }

    public void setLabelIds(String labelIds)
    {
        this.labelIds = labelIds;
    }

    public void setMinPrice(String minPrice)
    {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(String maxPrice)
    {
        this.maxPrice = maxPrice;
    }

    public void setMinTravelDay(String minTravelDay)
    {
        this.minTravelDay = minTravelDay;
    }

    public void setMaxTravelDay(String maxTravelDay)
    {
        this.maxTravelDay = maxTravelDay;
    }

    public void setStartTravelDate(String startTravelDate)
    {
        this.startTravelDate = startTravelDate;
    }

    public void setEndTravelDate(String endTravelDate)
    {
        this.endTravelDate = endTravelDate;
    }

    /**
     * 旅游列表接口入参请求bean类
     */
    public class TravleListRequestBean {
        //出发城市id
        private String depCityId;
        //出发景点id
        private String depSpotId;
        //目的城市id
        private String arrCityId;
        //目的景点id
        private String arrSpotId;
        //目的类型
        private String arrType;//1景点、4城市
        //目的详情id
        private String arrDetailId;//类型对应id（景点ID、城市id）
        //代理商ids
        private String agentIds;//多个使用英文逗号分割
        //产品类型
        private String productType;
        //特色ids
        private String labelIds;//多个使用英文逗号分割
        //最小价格
        private String minPrice;
        //最大价格
        private String maxPrice;
        //出行最小天数
        private String minTravelDay;
        //出行最大天数
        private String maxTravelDay;
        //银行优惠状态
        private String bankStatus;
        //出行开始日期  yyyy-MM-dd
        private String startTravelDate;
        //出行结束日期  yyyy-MM-dd
        private String endTravelDate;
        //排序
        private String sort;
        //分页起始数
        private int pageStart = 0;
        //每页条目数
        private int pageSize = AppConstant.MAX_PAGE_NUM;

        public String getDepCityId()
        {
            return depCityId;
        }

        public void setDepCityId(String depCityId)
        {
            this.depCityId = depCityId;
        }

        public String getDepSpotId()
        {
            return depSpotId;
        }

        public void setDepSpotId(String depSpotId)
        {
            this.depSpotId = depSpotId;
        }

        public String getArrCityId()
        {
            return arrCityId;
        }

        public void setArrCityId(String arrCityId)
        {
            this.arrCityId = arrCityId;
        }

        public String getArrSpotId()
        {
            return arrSpotId;
        }

        public void setArrSpotId(String arrSpotId)
        {
            this.arrSpotId = arrSpotId;
        }

        public String getArrType()
        {
            return arrType;
        }

        public void setArrType(String arrType)
        {
            this.arrType = arrType;
        }

        public String getArrDetailId()
        {
            return arrDetailId;
        }

        public void setArrDetailId(String arrDetailId)
        {
            this.arrDetailId = arrDetailId;
        }

        public String getAgentIds()
        {
            return agentIds;
        }

        public void setAgentIds(String agentIds)
        {
            this.agentIds = agentIds;
        }

        public String getProductType()
        {
            return productType;
        }

        public void setProductType(String productType)
        {
            this.productType = productType;
        }

        public String getLabelIds()
        {
            return labelIds;
        }

        public void setLabelIds(String labelIds)
        {
            this.labelIds = labelIds;
        }

        public String getMinPrice()
        {
            return minPrice;
        }

        public void setMinPrice(String minPrice)
        {
            this.minPrice = minPrice;
        }

        public String getMaxPrice()
        {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice)
        {
            this.maxPrice = maxPrice;
        }

        public String getMinTravelDay()
        {
            return minTravelDay;
        }

        public void setMinTravelDay(String minTravelDay)
        {
            this.minTravelDay = minTravelDay;
        }

        public String getMaxTravelDay()
        {
            return maxTravelDay;
        }

        public void setMaxTravelDay(String maxTravelDay)
        {
            this.maxTravelDay = maxTravelDay;
        }

        public String getBankStatus()
        {
            return bankStatus;
        }

        public void setBankStatus(String bankStatus)
        {
            this.bankStatus = bankStatus;
        }

        public String getStartTravelDate()
        {
            return startTravelDate;
        }

        public void setStartTravelDate(String startTravelDate)
        {
            this.startTravelDate = startTravelDate;
        }

        public String getEndTravelDate()
        {
            return endTravelDate;
        }

        public void setEndTravelDate(String endTravelDate)
        {
            this.endTravelDate = endTravelDate;
        }

        public String getSort()
        {
            return sort;
        }

        public void setSort(String sort)
        {
            this.sort = sort;
        }

        public int getPageStart()
        {
            return pageStart;
        }

        public void setPageStart(int pageStart)
        {
            this.pageStart = pageStart;
        }

        public int getPageSize()
        {
            return pageSize;
        }

        public void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }
    }
}
