package com.yzb.card.king.ui.integral.model;

import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.bean.user.UserCollectBean;

/**
 * 类名： OnDataLoadFinish
 * 作者： Li JianQiang.
 * 日期： 2016-08-16
 * 描述：
 */
public interface OnDataLoadFinish {

    void onStart();

    void onSuccess(Object o);

    void onFailed(Object o);

    void onLoadMore(Object o);

    void onFinish();

    void onDelete(UserCollectBean rb);

    void onDeleteRemind(RemindBean remindBean);

    void onGoCardInfo(Object o);
}
