package com.yzb.card.king.ui.manage;

import com.yzb.card.king.ui.credit.bean.CardHolderBean;
import com.yzb.card.king.ui.credit.bean.CreditCardBillBean;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/11
 * 描  述：信用卡数据管理器
 */
public class CreditCardManager {
    /**
     * 用户所有的信用卡数据
     */
    private List<CreditCardBillBean> allCreditCardData;

    private   List<CardHolderBean> cardHoldBeanList;

    private static CreditCardManager instance = null;

    private boolean ifRefresh = false;

    private CreditCardManager(){

    }

    public static CreditCardManager getInstance(){

        if(instance == null){

            instance = new CreditCardManager();
        }

        return  instance;
    }

    public List<CreditCardBillBean> getAllCreditCardData() {
        return allCreditCardData;
    }

    public void setAllCreditCardData(List<CreditCardBillBean> allCreditCardData) {
        this.allCreditCardData = allCreditCardData;
    }

    public List<CardHolderBean> getCardHoldBeanList() {
        return cardHoldBeanList;
    }

    public void setCardHoldBeanList(List<CardHolderBean> cardHoldBeanList) {
        this.cardHoldBeanList = cardHoldBeanList;
    }

    public boolean isIfRefresh() {
        return ifRefresh;
    }

    public void setIfRefresh(boolean ifRefresh) {
        this.ifRefresh = ifRefresh;
    }

    /**
     * 清理缓存数据
     */
    public void clearAllData(){

        if(allCreditCardData!=null){

            allCreditCardData.clear();

            allCreditCardData = null;
        }


    }
}
