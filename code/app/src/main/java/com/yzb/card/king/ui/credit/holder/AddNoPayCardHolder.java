package com.yzb.card.king.ui.credit.holder;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/4 15:06
 */
public class AddNoPayCardHolder extends AddCardHolder
{
    private float amount = 0.01f;
    private String goodsName = "验证卡片";

    public AddNoPayCardHolder(AddCanPayCardActivity addCanPayCardActivity)
    {
        super(addCanPayCardActivity);
        hideCanPayContent();
    }

    @Override
    public boolean rightClick()
    {
        if (hasValid())
        {
            createBaseOrder();
        }
        return true;
    }
    private void createBaseOrder()
    {
        SimpleRequest<BaseOrder> request = new SimpleRequest<BaseOrder>(CardConstant.CREATE_BASE_ORDERS)
        {
            @Override
            protected BaseOrder parseData(String data)
            {
                return JSON.parseObject(data, BaseOrder.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("creditId", UserManager.getInstance().getUserBean().getAccount());//收款方钱包账户
        param.put("amount", amount);//订单金额
        param.put("intro", goodsName);//订单信息
        param.put("orderType", OrderType.VALID_CARD.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>()
        {
            @Override
            public void onSuccess(BaseOrder data)
            {
                addCreditCard(data.getOrderNo());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    @Override
    protected boolean hasValid()
    {
        return validCardNum() && validUserName() && validStatementDay();
    }

    private void addCreditCard(String orderNo)
    {
        Map<String, Object> param = addCanPayCardActivity.getCardInfo();
        param.put("orderNo",orderNo);
        SimpleRequest request = new SimpleRequest(CardConstant.CREATE_CREDIT_CARD);
        request.setParam(param);

        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
                addCanPayCardActivity.nextStep();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    @Override
    protected void setNoticeVisibility(CardBin data)
    {

    }
}
