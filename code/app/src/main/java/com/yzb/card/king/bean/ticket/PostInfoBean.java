package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：快递信息；
 *
 * @author:gengqiyun
 * @date: 2016/10/11
 */
public class PostInfoBean extends BaseBean
{
    String name; //姓名
    String ophone;//联系方式
    String distributionFee;//配送费
    String addRess;//配送地址

    public String getName()
    {
        return super.isStrEmpty(name);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOphone()
    {
        return super.isStrEmpty(ophone);
    }

    public void setOphone(String ophone)
    {
        this.ophone = ophone;
    }

    public String getDistributionFee()
    {
        return super.isStrEmpty(distributionFee);
    }

    public void setDistributionFee(String distributionFee)
    {
        this.distributionFee = distributionFee;
    }

    public String getAddRess()
    {
        return super.isStrEmpty(addRess);
    }

    public void setAddRess(String addRess)
    {
        this.addRess = addRess;
    }
}
