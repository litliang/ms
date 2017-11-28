package com.yzb.card.king.bean.travel;

import com.yzb.card.king.ui.discount.bean.BankBean;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/11/24
 */
public class TravelNoticeBean extends BankBean
{
    private String feeId; //费用id；
    private String feeIntro; //费用内容；

    public String getFeeId()
    {
        return feeId;
    }

    public void setFeeId(String feeId)
    {
        this.feeId = feeId;
    }

    public String getFeeIntro()
    {
        return feeIntro;
    }

    public void setFeeIntro(String feeIntro)
    {
        this.feeIntro = feeIntro;
    }
}
