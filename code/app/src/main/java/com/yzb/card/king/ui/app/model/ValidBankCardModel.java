package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.bean.user.AuthenticationInfoBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseNoRefreshModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.manage.UserManager;

import java.util.Map;

/**
 * 功能：银行卡验证
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class ValidBankCardModel extends BaseModelImpl implements BaseNoRefreshModel
{
    public ValidBankCardModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_api_appbankcardlist;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null) {
            UserBean userBean = UserManager.getInstance().getUserBean();

            AuthenticationInfoBean bean = new AuthenticationInfoBean();

            bean.setCertType("1");

            bean.setRealName(String.valueOf(paramMap.get("name")));

            bean.setCertNo(String.valueOf(paramMap.get("certNo")));

//            userBean.setCertType("1");
//            userBean.setName(String.valueOf(paramMap.get("name")));
//            userBean.setCertNo(String.valueOf(paramMap.get("certNo")));
            userBean.setAuthenticationStatus("2");

            userBean.setAuthenticationInfo(bean);

            loadListener.onListenSuccess(true, data);
        }
    }
}
