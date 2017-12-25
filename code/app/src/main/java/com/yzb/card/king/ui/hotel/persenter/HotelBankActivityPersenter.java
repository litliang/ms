package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.hotel.BankActivityParam;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.model.HotelBankActivityModel;
import com.yzb.card.king.ui.hotel.view.BankActivityView;

/**
 * 类  名：酒店银行活动观察者
 * * 作  者：Li Yubing
 * 日  期：2017/8/28
 * 描  述：
 */
public class HotelBankActivityPersenter implements DataCallBack {

    public static final int BANK_PRE_ACT_LIST_CODE = 88880;

    public static final int BANK_LIFE_STAGE_LIST_CODE = 88881;

    public static final int QUERY_BANK_STAGE_INFO_CODE = 88882;

    public static final int QUERY_BANK_PRE_DETAIL_CODE = 88883;

    private BankActivityView bankActivityView;

    private BaseViewLayerInterface baseViewLayerInterface;

    private HotelBankActivityModel model;

    public HotelBankActivityPersenter(BankActivityView baseViewLayerInterface)
    {

        this.bankActivityView = baseViewLayerInterface;

        model = new HotelBankActivityModel(this);

    }

    public HotelBankActivityPersenter(BaseViewLayerInterface baseViewLayerInterface)
    {

        this.baseViewLayerInterface = baseViewLayerInterface;

        model = new HotelBankActivityModel(this);

    }

    /**
     * 发送银行优惠活动请求
     */
    public void sendBankPreActivityRequest()
    {

        if(bankActivityView == null){

            return;
        }

        BankActivityParam param = bankActivityView.getQueryParam();

        if (param != null) {
            model.quereyBankPreAcitivityAction(param.getCityId(), param.getBankIds(), param.getIndustryId(), param.getActType(), param.getEffMonth(), param.getPageStart(), BANK_PRE_ACT_LIST_CODE);
        }


    }

    /**
     * 查询银行优惠活动的详情
     */
    public void sendBankPreActivityDetailRequest(GoldTicketParam goldTicketParam ,int goodsType,String startDate){

        if(baseViewLayerInterface == null){

            return;
        }

        model.quereyBankPreAcitivityDetailAction(goldTicketParam.getIndustryId(), goldTicketParam.getShopId(), goldTicketParam.getStoreId(), goodsType, goldTicketParam.getGoodsId(), startDate, QUERY_BANK_PRE_DETAIL_CODE);
    }

    /**
     * 发送银行生活分期活动请求
     */
    public void sendBankLifeStageActivityRequest()
    {

        if(bankActivityView == null){

            return;
        }


        BankActivityParam param = bankActivityView.getQueryParam();

        if (param != null) {

            model.sendQueryBankLifeStageAction(param.getCityId(), param.getBankIds(), param.getIndustryId(), param.getStage(), param.getPageStart(), BANK_LIFE_STAGE_LIST_CODE);
        }
    }

    /**
     * 发送产品的生活分期详情请求
     */
    public void sendProductLifeStageActivityInfoRequest( GoldTicketParam goldTicketParam ,int goodsType,double productMoney)
    {

        if(baseViewLayerInterface == null){

            return;
        }

        model.sendProductBankLifeStageAction(goldTicketParam.getIndustryId(), goldTicketParam.getShopId(), goldTicketParam.getStoreId(), goodsType, goldTicketParam.getGoodsId(), productMoney, QUERY_BANK_STAGE_INFO_CODE);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (bankActivityView != null) {


            bankActivityView.callSuccessViewLogic(o, type);

        }

        if (baseViewLayerInterface != null) {


            baseViewLayerInterface.callSuccessViewLogic(o, type);

        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (bankActivityView != null) {


            bankActivityView.callFailedViewLogic(o, type);

        }

        if (baseViewLayerInterface != null) {


            baseViewLayerInterface.callFailedViewLogic(o, type);

        }
    }
}
