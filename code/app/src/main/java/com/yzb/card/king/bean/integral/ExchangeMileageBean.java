package com.yzb.card.king.bean.integral;

import com.yzb.card.king.ui.discount.bean.BankBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/25
 * 描  述：
 */
public class ExchangeMileageBean implements Serializable {

    private List<BankBean> bankList;

    private List<AirlinesBean> airlineList;

    private Map<Long,List<Long>> relationshipMap;



    public List<AirlinesBean> getAirlineList() {
        return airlineList;
    }

    public void setAirlineList(List<AirlinesBean> airlineList) {
        this.airlineList = airlineList;
    }

    public Map<Long,List<Long>> getRelationshipMap() {
        return relationshipMap;
    }

    public void setRelationshipMap(Map<Long,List<Long>> relationshipMap) {
        this.relationshipMap = relationshipMap;
    }

    public List<BankBean> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankBean> bankList) {
        this.bankList = bankList;
    }

    public boolean checkDataValue(){

        boolean flag = true;

        if(bankList != null && bankList.size() >0){

            flag = true;

        }else {

            return false;
        }


        if(airlineList != null && airlineList.size() >0){

            flag = true;

        }else {

            return false;
        }



        if(relationshipMap != null && relationshipMap.size() >0){

            flag = true;

        }else {

            return false;
        }

        return flag;
    }
}
