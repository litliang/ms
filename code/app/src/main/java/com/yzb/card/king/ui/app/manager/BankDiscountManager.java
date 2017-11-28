package com.yzb.card.king.ui.app.manager;


import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;

import java.util.List;

/**
 * 功能：银行折扣信息管理；
 *
 * @author:gengqiyun
 * @date: 2016/10/16
 */
public class BankDiscountManager
{
    private static BankDiscountManager instance;
    private List<ShopGoodsActivityBean> dataList;

    public static BankDiscountManager getInstance()
    {
        if (instance == null)
        {
            instance = new BankDiscountManager();
        }
        return instance;
    }


    public void setShopGoodsActivity(List<ShopGoodsActivityBean> dataList)
    {
        this.dataList = dataList;
    }


    /**
     * 根据bankId查询相应银行卡的折扣；
     *
     * @param bankId
     * @return 返回组合好的优惠字符串；
     */
    public String getConcatRateByBankId(Long bankId)
    {
        if (dataList == null || dataList.size() == 0 || bankId <= 0)
        {
            return "";
        }

        GoodActivityBean targetBean = getTargetBeanByBankId(bankId);
        if (targetBean != null)
        {
            //满减；
            float fullAmount = targetBean.getFullAmount();
            int rate = targetBean.getRate();
            // int category = targetBean.getCategory(); //优惠类型 1折扣；2元
            String result = "每满" + fullAmount + "享" + rate / 10.0f + "折";
//
            if (fullAmount <= 0 || rate <= 0)
            {
                return "";
            }
            return result;
        }
        return "";
    }

    private GoodActivityBean getTargetBeanByBankId(Long bankId)
    {
        // 暂时无法确定不同商家对相同的银行 都有折扣的情况；
        List<GoodActivityBean> goodDataList;
        GoodActivityBean targetBean = null;
        for (int i = 0; i < dataList.size(); i++)
        {
            goodDataList = dataList.get(i).getGoodActivityBeans();
            if (goodDataList != null)
            {
                for (int j = 0; j < goodDataList.size(); j++)
                {
                    if (bankId == Long.parseLong(goodDataList.get(j).getBankId() + ""))
                    {
                        targetBean = goodDataList.get(j);
                        break;
                    }
                }
            }
        }
        return targetBean;
    }

    /**
     * 根据bankId查询相应银行卡的折扣
     *
     * @param bankId
     * @return 折扣；例如：0.95;  1表示不打折；
     */
    public float getRateByBankId(Long bankId)
    {
        if (dataList == null || dataList.size() == 0 || bankId == null || bankId <= 0)
        {
            return 1;
        }
        //暂时无法确定不同商家对相同的银行 都有折扣的情况；
        GoodActivityBean targetBean = getTargetBeanByBankId(bankId);
        if (targetBean != null)
        {
            return targetBean.getRate() <= 0 ? 1 : targetBean.getRate() / 100.0f;
        }
        return 1;
    }

    /**
     * 获取银行满减；例如：每满X减Y;
     *
     * @param bankId
     * @return X;
     */
    public float getBankFullAmount(Long bankId)
    {
        if (dataList == null || dataList.size() == 0 || bankId == null || bankId <= 0L)
        {
            return 0;
        }
        GoodActivityBean targetBean = getTargetBeanByBankId(bankId);
        if (targetBean != null)
        {
            return targetBean.getFullAmount();
        }
        return 0;
    }
}
