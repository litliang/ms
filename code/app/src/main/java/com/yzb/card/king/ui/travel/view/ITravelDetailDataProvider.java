package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.PjBean;

import java.util.List;

/**
 * 功能：旅游详情数据提供者；
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public interface ITravelDetailDataProvider
{
    /**
     * 获取产品详情bean；
     *
     * @return
     */
    TravelProduDetailBean getProduDetailBean();

    /**
     * 获取优惠银行列表；
     *
     * @return
     */
    List<GoodActivityBean> getCouponBankBean();

    /**
     * 获取旅游线路列表；
     *
     * @return
     */
    List<TravelLineBean> getTravelLineBeans();

    /**
     * 获取评价列表；
     *
     * @return
     */
    List<PjBean> getPjBeanList();

    /**
     * 获取日期列表；
     *
     * @return
     */
    List<DateBean> getDateBeanList();

    /**
     * 选择出发地；
     *
     * @param lineId 线路id；
     */
    void selectDeparture(String lineId);

    /**
     * 获取更多日期；
     */
    void selectMoreDate();

    /**
     * 线路切换；
     *
     * @param position    切换的线路的下标；
     * @param needRefresh 是否需要重新获取线路详情；
     */
    void switchLines(int position, boolean needRefresh);
}
