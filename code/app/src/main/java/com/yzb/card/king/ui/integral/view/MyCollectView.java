package com.yzb.card.king.ui.integral.view;

import com.yzb.card.king.bean.user.UserCollectBean;
import com.yzb.card.king.ui.base.LBaseView;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public interface MyCollectView extends LBaseView {

    void onSuccess(List<UserCollectBean> userCollectBeen);

    void onDelete(UserCollectBean rb);

    void onFailed(String errorResult);
}
