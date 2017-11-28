package com.yzb.card.king.ui.credit.activity;

import com.yzb.card.king.ui.credit.holder.AddBankCardHolder;
import com.yzb.card.king.ui.credit.holder.BankMobileHolder;
import com.yzb.card.king.ui.credit.holder.CompleteHolder;
import com.yzb.card.king.ui.credit.holder.ValidBankMobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加银行卡；
 */
public class AddBankCardActivity extends AddCanPayCardActivity {
    @Override
    protected void initHolders() {
        holderList = new ArrayList<>();
        holderList.add(new AddBankCardHolder(this));
        holderList.add(new BankMobileHolder(this));
        holderList.add(new ValidBankMobile(this));
        holderList.add(new CompleteHolder(this));
    }

    @Override
    public Map<String, Object> getCardInfo() {
        Map<String, Object> param = new HashMap<>();
        return param;
    }

    public AddBankCardHolder getAddCardHolder() {
        return (AddBankCardHolder) holderList.get(0);
    }

    public BankMobileHolder getMobileHolder() {
        return (BankMobileHolder) holderList.get(1);
    }

    public String getCardNum() {
        return getAddCardHolder().getCardNum();
    }

    public String getEditMobile() {
        return getMobileHolder().getMobile();
    }

    public String getBin() {
        return getMobileHolder().getBin();
    }

    public String getBankName() {
        return getMobileHolder().getBankName();
    }

    public String getBankId() {
        return getMobileHolder().getBankId();
    }

    public String getCertType() {
        return getAddCardHolder().getCertType();
    }

    public String getCertNo() {
        return getAddCardHolder().getCertNo();
    }

    public String getUserName() {

        return getAddCardHolder().getName();
    }
}
