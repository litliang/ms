package com.yzb.card.king.ui.manage;

import com.yzb.card.king.bean.integral.ExchangeIntegralListBean;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;

import java.util.List;

/**
 * 类  名：积分数据管理器
 * 作  者：Li Yubing
 * 日  期：2016/6/28
 * 描  述：
 */
public class IntegralDataManager {

    private static IntegralDataManager instance = null;

    private List<ExchangeIntegralListBean> travellerPlanBeanList ;

    private  List<IntegralShareLinkman> contactsBeenList;



    private IntegralDataManager(){

    }

    public static IntegralDataManager getInstance(){

        if( instance == null ){

            instance = new  IntegralDataManager();

        }

        return instance;

    }

    public List<ExchangeIntegralListBean> getTravellerPlanBeanList() {
        return travellerPlanBeanList;
    }

    public void setTravellerPlanBeanList(List<ExchangeIntegralListBean> travellerPlanBeanList) {
        this.travellerPlanBeanList = travellerPlanBeanList;
    }

    public List<IntegralShareLinkman> getContactsBeenList() {
        return contactsBeenList;
    }

    public void setContactsBeenList(List<IntegralShareLinkman> contactsBeenList) {
        this.contactsBeenList = contactsBeenList;
    }

    /**
     * 检查积分联系是否有相同的
     * @return
     */
    public boolean checkIntegralShareLinkmanSame(String mobile,String name){

        boolean flag = false;

        if(contactsBeenList != null){

            for(IntegralShareLinkman temp: contactsBeenList){

                if(temp.getMobile().equals(mobile) || temp.getNickName().equals(name)){

                    flag = true;

                    break;

                }


            }

        }

        return flag;
    }
}
