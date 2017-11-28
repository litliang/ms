package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.gift.modle.GiftCardProductDao;
import com.yzb.card.king.ui.gift.modle.GiftCardProductImpl;
import com.yzb.card.king.ui.gift.view.GiftCardProductView;

import java.util.HashMap;
import java.util.Map;

/**
 * 类 名： 礼品卡商城Presenter
 * 作 者： gaolei
 * 日 期：2017年02月07日
 * 描 述：Presenter
 */
public class GiftCardStoreProductPresenter implements DataCallBack {

    private GiftCardProductDao dao;
    private GiftCardProductView view;

    public GiftCardStoreProductPresenter(GiftCardProductView view) {
        this.view = view;
        dao = new GiftCardProductImpl(this);
    }


    public void getGiftCardProductData(Map<String, Object> map, String serviceName, int type){
        dao.setProductData(map,serviceName,type);
    }

    /**
     * 嗨生活首页的礼品卡推荐
     */
    public void sendAppShopGiftRequest(){

        Map<String, Object> param = new HashMap<>();
        param.put("recommendPage", "1");
        getGiftCardProductData(param, CardConstant.CARD_APP_QUERYGIFTCARDRECOMMEND, 1);
    }

    /**
     * 发送查询礼品卡首页推荐的心意卡请求
     */
    public void sendHomeGiftXinYiRecommandRequest(){

        Map<String, Object> param = new HashMap<>();
        param.put("recommendPage", "2");
        param.put("ecardType", "1");
        getGiftCardProductData(param, CardConstant.CARD_APP_QUERYGIFTCARDRECOMMEND, 888);

    }
    /**
     * 发送查询礼品卡首页推荐的E卡请求
     */
    public void sendHomeGiftECardRecommandRequest(){

        Map<String, Object> param = new HashMap<>();
        param.put("recommendPage", "2");
        param.put("ecardType", "2");
        getGiftCardProductData(param, CardConstant.CARD_APP_QUERYGIFTCARDRECOMMEND, 889);

    }
    /**
     * 发送用户页面的礼品卡商品信息请求
     */
    public void setUserGiftGoodsReqeust(){
        Map<String, Object> param = new HashMap<>();
        param.put("category", 2);
        param.put("sort", 3);
        param.put("pageStart", 0);
        param.put("pageSize", 6);
        getGiftCardProductData(param, CardConstant.card_querygiftcardproduct, 1);
    }

    /**
     * 发送商户优惠页面的礼品卡商品信息请求
     */
    public void setGiftGoodsReqeust(){
        Map<String, Object> param = new HashMap<>();
        param.put("category", 2);
        param.put("sort", 3);
        param.put("pageStart", 0);
        param.put("pageSize", 4);
        getGiftCardProductData(param, CardConstant.card_querygiftcardproduct, 1);
    }

    /**
     * 发送实体卡信息请求
     */
    public void setEntityCardReqeust(){
        Map<String, Object> param = new HashMap<>();
        param.put("category", "1");
       param.put("sort", "2");
       param.put("pageStart", "0");
       param.put("pageSize", "1");
        getGiftCardProductData(param, CardConstant.card_querygiftcardproduct, 2);
    }

    @Override
    public void requestSuccess(Object o, int type) {
            view.onLoadProductSuccess(o,type);
    }


    @Override
    public void requestFailedDataCall(Object o, int type) {
            view.onLoadProductFail(o,type);

    }
}
