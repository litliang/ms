package com.yzb.card.king.ui.credit.activity;

import com.yzb.card.king.ui.credit.holder.AddCompleteHolder;
import com.yzb.card.king.ui.credit.holder.AddNoPayCardHolder;
import com.yzb.card.king.ui.manage.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：添加不可支付信用卡
 * 作者：殷曙光
 * 日期：2017/1/4 14:48
 */

public class AddNoPayCardActivity extends AddCanPayCardActivity
{
    @Override
    protected void initHolders()
    {
        holderList = new ArrayList<>();
        holderList.add(new AddNoPayCardHolder(this));
        holderList.add(new AddCompleteHolder(this));
    }

    public Map<String, Object> getCardInfo()
    {
        AddNoPayCardHolder addCardHolder = (AddNoPayCardHolder) holderList.get(0);
        Map<String, Object> param = new HashMap<>();
        param.put("certType",addCardHolder.getCertType());
        param.put("certNo",addCardHolder.getCertNo());
        param.put("payType", "0");//不可支付
        param.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        param.put("bin", addCardHolder.getBin());
        param.put("cardNo", addCardHolder.getCardNum());
        param.put("name", addCardHolder.getName());
        param.put("billDay", addCardHolder.getStatementDay());
        param.put("bankId", addCardHolder.getBankId());
        param.put("bankName", addCardHolder.getBankName());
        return param;
    }
}
