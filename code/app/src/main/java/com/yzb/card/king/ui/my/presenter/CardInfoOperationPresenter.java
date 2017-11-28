package com.yzb.card.king.ui.my.presenter;

import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.my.view.CardInfoOperationView;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：储蓄卡、信用卡操作观察器
 * 作  者：Li Yubing
 * 日  期：2017/6/20
 * 描  述：
 */
public class CardInfoOperationPresenter {

    public static final int CREDIT_CARD_CODE = CardConstant.CREATE_CREDIT_CARD.hashCode();

    public static final int DEBIT_CARD_CODE = CardConstant.CREATE_DEBIT_CARD.hashCode();

    private CardInfoOperationView view;

    public CardInfoOperationPresenter(CardInfoOperationView view){

        this.view = view;

    }

    /**
     *  给用户快速创建信用卡
     * @param cardBin 创建成功后此卡不可支出
     */
    public void fastCreateCrediteCard(CardBin cardBin){

        Map<String, Object> param = obtainPublicRequestParam(cardBin);

        SimpleRequest request = new SimpleRequest(CardConstant.CREATE_CREDIT_CARD);

        request.setParam(param);

        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {

                view.callSuccessViewLogic(data,CREDIT_CARD_CODE);

            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    /**
     *  给用户快速创建储蓄卡
     * @param cardBin 创建成功后此卡不可支出
     */
    public void fastCreateDebitCard(CardBin cardBin){

        Map<String, Object> param = obtainPublicRequestParam(cardBin);

        SimpleRequest request = new SimpleRequest(CardConstant.CREATE_DEBIT_CARD);

        request.setParam(param);

        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {

                view.callSuccessViewLogic(data,DEBIT_CARD_CODE);

            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    /**
     * 获取公共的请求参数
     * @param cardBin
     * @return
     */
    private  Map<String, Object> obtainPublicRequestParam(CardBin cardBin){

        String[] strA = view.getCardHolder();

        String bankNumber =  strA[1].trim().replace(" ", "");

        String cardholderP = strA[0];

        Map<String, Object> param = new HashMap<>();

        param.put("payType","0");//不可支付

        param.put("bin", cardBin.getBin());

        param.put("name",cardholderP);

        param.put("cardNo",bankNumber);

        param.put("bankId",cardBin.getBankId());

        param.put("bankName",cardBin.getBankName());

        return  param;

    }


}
