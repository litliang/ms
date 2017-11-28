package com.yzb.card.king.ui.manage;

import android.text.TextUtils;

import com.yzb.card.king.bean.ticket.OrderIdBean;
import com.yzb.card.king.bean.user.AccountInfo;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.bonus.bean.BounsBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 玉兵 on 2016/6/18.
 */
public class UserManager
{
    public static final String HAS_VALID = "已认证";
    public static final String HAS_NOT_VALID = "未认证";
    public static final String VALID_ING = "审核中";
    public static final String VALID_FAIL = "审核未通过";

    private static UserManager instance = null;

    private UserBean userBean = null;

    private long integrationNumeber = 1000;//用户平台积分积分
    //礼品卡信息
    private AccountInfo giftAccountInfo = null;
    //红包余额
    private AccountInfo redMoneyAccountInfo = null;
    //钱包
    private AccountInfo moneyAccountInfo = null;
    //积分信息
    private AccountInfo accountInfo = null;
    //未领取的红包数据
    private List<BounsBean> redEnvelepoList;

    //机票的当前航线类型
    private String flightType = null;

    private ArrayList<OrderIdBean.OrderIds> orderIdBeans;

    private UserManager()
    {

    }

    public static UserManager getInstance()
    {

        if (instance == null)
        {

            instance = new UserManager();

        }

        return instance;
    }

    public AccountInfo getAccountInfo()
    {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo)
    {
        this.accountInfo = accountInfo;
    }

    public long getIntegrationNumeber()
    {
        return integrationNumeber;
    }

    public void setIntegrationNumeber(long integrationNumeber)
    {
        this.integrationNumeber = integrationNumeber;
    }

    public void subtractionShareNumber(long number)
    {

        integrationNumeber = integrationNumeber - number;
    }

    public UserBean getUserBean()
    {
        return userBean;
    }

    public void setUserBean(UserBean userBean)
    {
        this.userBean = userBean;

    }

    public String getStatus()
    {
        String status;
        switch (userBean.getAuthenticationStatus())
        {
            case "2":
                status = HAS_NOT_VALID;
                break;
            case "1":
                status = HAS_VALID;
                break;
            case "3":
                status = VALID_ING;
                break;
            case "4":
                status = VALID_FAIL;
                break;
            default:
                status = HAS_NOT_VALID;
                break;
        }
        return status;
    }

    public AccountInfo getMoneyAccountInfo()
    {
        return moneyAccountInfo;
    }

    public void setMoneyAccountInfo(AccountInfo moneyAccountInfo)
    {
        this.moneyAccountInfo = moneyAccountInfo;
    }

    public AccountInfo getGiftAccountInfo()
    {
        return giftAccountInfo;
    }

    public void setGiftAccountInfo(AccountInfo giftAccountInfo)
    {
        this.giftAccountInfo = giftAccountInfo;
    }

    public AccountInfo getRedMoneyAccountInfo()
    {
        return redMoneyAccountInfo;
    }

    public void setRedMoneyAccountInfo(AccountInfo redMoneyAccountInfo)
    {
        this.redMoneyAccountInfo = redMoneyAccountInfo;
    }

    public String getFlightType()
    {
        return flightType;
    }

    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public ArrayList<OrderIdBean.OrderIds> getOrderIdBeans()
    {
        return orderIdBeans;
    }

    public void setOrderIdBeans(ArrayList<OrderIdBean.OrderIds> orderIdBeans)
    {
        this.orderIdBeans = orderIdBeans;
    }

    /**
     * 登录时通知改变ui
     *
     * @author ysg
     * created at 2016/7/26 15:33
     */
    public void login()
    {
        if (listeners.size() > 0)
        {
            for (OnLoginListener listener : loginListeners)
            {
                listener.onLogin();
            }
        }
    }

    private LinkedList<OnLoginListener> loginListeners = new LinkedList<>();

    public void setOnLoginListener(OnLoginListener listener)
    {
        loginListeners.add(listener);
    }

    public void unRegisterListener(OnLoginListener listener)
    {
        loginListeners.remove(listener);
    }

    public interface OnLoginListener
    {
        void onLogin();
    }

    /**
     * 判断用户的登录状态；
     *
     * @return
     */
    public boolean isLogin()
    {

        if (userBean == null || TextUtils.isEmpty(userBean.getAccount()))
        {
            return false;
        } else
        {
            return true;
        }

    }

    public void update()
    {
        if (listeners.size() > 0)
        {
            for (OnUpdateListener listener : listeners)
            {
                listener.onUpdate();
            }
        }
    }

    private LinkedList<OnUpdateListener> listeners = new LinkedList<>();

    public void unRegistListener(OnUpdateListener listener)
    {
        listeners.remove(listener);
    }

    public void setOnUpdateListener(OnUpdateListener listener)
    {
        listeners.add(listener);
    }

    public interface OnUpdateListener
    {
        void onUpdate();
    }

    public List<BounsBean> getRedEnvelepoList()
    {
        return redEnvelepoList;
    }

    public void setRedEnvelepoList(List<BounsBean> redEnvelepoList)
    {
        this.redEnvelepoList = redEnvelepoList;
    }
}
