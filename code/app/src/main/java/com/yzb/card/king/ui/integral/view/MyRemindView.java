package com.yzb.card.king.ui.integral.view;

import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.ui.base.LBaseView;
import com.yzb.card.king.ui.credit.bean.CreditCardBillBean;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public interface MyRemindView extends LBaseView {


    void onSuccess(List<RemindBean> remindBeanList);

    void onFailed(String errorResult);

    void onGoCardInfo(CreditCardBillBean creditCardBillBean);

    void onDelete(RemindBean remindBean);
}
