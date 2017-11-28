package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.gift.modle.BuyCardModel;
import com.yzb.card.king.ui.gift.modle.EcardOrderCreateModel;
import com.yzb.card.king.ui.gift.modle.EntitycardOrderCreateModel;
import com.yzb.card.king.ui.gift.view.BuyCardView;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：购买心意e卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class BuyCardPresenter implements BaseMultiLoadListener {
    private BuyCardModel model;

    private BuyCardView view;

    private EcardOrderCreateModel ecardOrderCreateModel;

    private EntitycardOrderCreateModel entitycardOrderCreateModel;

    public BuyCardPresenter(BuyCardView view)
    {
        this.view = view;
        model = new BuyCardModel(this);

    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    /**
     * 设置此接口参数信息
     *
     * @param productId     产品id  必填
     * @param quantitys     数量 必填
     * @param amounts       金额  必填
     * @param anonymousFlag 是否匿名 必填
     * @param typeFlag      金额类型 必填
     * @param blessWord     祝福语 必填
     * @param express       配送信息 非必填
     */
    public void setInterfaceParameters(String productId, String quantitys, String amounts, boolean anonymousFlag, boolean typeFlag, String blessWord, String express)
    {

        Map<String, Object> args = new HashMap<>();

        args.put("productId", productId);

        args.put("quantitys", quantitys); //数量

        args.put("amounts", amounts); //金额

        args.put("anonymous", anonymousFlag ? "1" : "0"); //是否匿名  1、是；0、否

        args.put("type", typeFlag ? "2" : "1"); //1、等额；2、随机

        args.put("blessWord", blessWord);

        if (express != null) {

            args.put("express", express);
        }

        model.loadData(true, args);
    }

    public void setEcardOrderInterfaceParameters(String productId, String amounts, boolean anonymousFlag, String blessWord)
    {

        if (ecardOrderCreateModel == null) {

            ecardOrderCreateModel = new EcardOrderCreateModel(this);
        }

        Map<String, Object> args = new HashMap<>();

        args.put("productId", productId);

        args.put("blessWord", blessWord);

        args.put("anonymous", anonymousFlag ? "1" : "0"); //是否匿名  1、是；0、否

        args.put("amount", amounts); //金额


        ecardOrderCreateModel.loadData(true, args);
    }


    /**
     * @param productId
     * @param quantitys
     * @param amounts
     * @param anonymousFlag
     * @param typeFlag
     * @param blessWord
     * @param express
     */
    public void setEntityCardInterfaceParameters(String productId, String quantitys, String amounts, boolean anonymousFlag, boolean typeFlag, String blessWord, String express, String orderAmount)
    {

        if (entitycardOrderCreateModel == null) {
            entitycardOrderCreateModel = new EntitycardOrderCreateModel(this);
        }


        Map<String, Object> args = new HashMap<>();

        args.put("productId", productId);

        args.put("quantitys", quantitys); //数量

        args.put("amounts", amounts); //金额

        args.put("orderAmount", orderAmount); //总金额

        args.put("anonymous", anonymousFlag ? "1" : "0"); //是否匿名  1、是；0、否

        args.put("type", typeFlag ? "2" : "1"); //1、等额；2、随机

        args.put("blessWord", blessWord);

        if (express != null) {

            args.put("deliveryAddress", express);
        }

        entitycardOrderCreateModel.loadData(true, args);
    }


    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onBuyMindECardSuc((OrderOutBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onBuyMindECardFail(msg);
    }
}
