package com.yzb.card.king.bean.user;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.user.LoginActivity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
@Table(name = "user")
public class UserBean implements Serializable
{

    public static final String AuthenticationStatus_SUCCESS = "1";

    public static final String AuthenticationStatus_FAILED = "4";

    public  static String MOD = "4";

    public static final String TYPE = "P";

    public  static  final  String P ="P";//普通用户

    public  static  final  String C ="C";//商户

    private int consume;//平台消费

    private boolean mobileIsChanged;
    /**
     * 客户id
     */
    private long custId;
    /**
     * 用户名
     */
    private String account;

    /**
     * 钱包账户
     */
    private String amountAccount;
    /**
     * 昵称
     */
    private String nickName = "未设置昵称";//

    /**
     * 头像图片
     */
    @Column(name = "pic")
    private String pic;//头像图片
    /**
     * 创建时间
     */
    private String createTime;//
    /**
     * 邮箱
     */
    private String email;
    /**
     *  支付顺序设置（0：自动；1：手动）；
     */
    private String paymentStatus; // 支付顺序设置（0：自动；1：手动）；

    /**
     * 消息推送设置  1开；0关
     */
    private String messageStatus;

    /**
     *  提醒设置  1开；0关
     */
    private String remindStatus;
    /**
     * 性别 1男2女
     */
    private String sex; //1男2女

    /**
     * 出生日期 yyyy-MM-dd
     */
    private Date birthday;
    /**
     * 认证标志  1：已认证；2：未认证；3已提交审核；4认证失败；
     */
    private String authenticationStatus;
    /**
     * 客户类别  P(APP)、C(商户)
     */
    private String customerType;
    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 用户地址信息
     */
    private AddrInfoBean addrInfo;
    /**
     * 会员等级信息
     */
    private LevelInfoBean levelInfo;
    /**
     * 认证信息(已认证时此信息有效)
     */
    private AuthenticationInfoBean authenticationInfo;

    private  String passwordStr = "18298019023aaaaaaaaaaaaaa";


    private int integralNumber = -1;//积分数

    private int creditCardNum = 0;

    public int getIntegralNumber()
    {
        return integralNumber;
    }

    public void setIntegralNumber(int integralNumber)
    {
        this.integralNumber = integralNumber;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }


    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }


    public String getAmountAccount()
    {
        return amountAccount==null?"":amountAccount;
    }

    public void setAmountAccount(String amountAccount)
    {
        this.amountAccount = amountAccount;
    }


    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        if(!TextUtils.isEmpty(nickName)){
            this.nickName = nickName;
        }

    }

    public long getCustId()
    {
        return custId;
    }

    public void setCustId(long custId)
    {
        this.custId = custId;
    }

    public String getPic()
    {
        return ServiceDispatcher.getImageUrl(pic);
    }

    public String getUserImage(){

        return  pic;
    }


    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public int getCreditCardNum()
    {
        return creditCardNum;
    }

    public void setCreditCardNum(int creditCardNum)
    {
        this.creditCardNum = creditCardNum;
    }

    public int getConsume()
    {
        return consume;
    }

    public void setConsume(int consume)
    {
        this.consume = consume;
    }

    public boolean isMobileIsChanged()
    {
        return mobileIsChanged;
    }

    public void setMobileIsChanged(boolean mobileIsChanged)
    {
        this.mobileIsChanged = mobileIsChanged;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getCustomerType()
    {
        return customerType;
    }

    public void setCustomerType(String customerType)
    {
        this.customerType = customerType;
    }

    public String getPublicKey()
    {
        return publicKey;
    }

    public void setPublicKey(String publicKey)
    {
        this.publicKey = publicKey;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getMessageStatus()
    {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus)
    {
        this.messageStatus = messageStatus;
    }

    public String getRemindStatus()
    {
        return remindStatus;
    }

    public void setRemindStatus(String remindStatus)
    {
        this.remindStatus = remindStatus;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getAuthenticationStatus()
    {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus)
    {
        this.authenticationStatus = authenticationStatus;
    }

    public AddrInfoBean getAddrInfo()
    {
        return addrInfo;
    }

    public void setAddrInfo(AddrInfoBean addrInfo)
    {
        this.addrInfo = addrInfo;
    }

    public LevelInfoBean getLevelInfo()
    {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfoBean levelInfo)
    {
        this.levelInfo = levelInfo;
    }

    public AuthenticationInfoBean getAuthenticationInfo()
    {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfoBean authenticationInfo)
    {
        this.authenticationInfo = authenticationInfo;
    }

    public String getPasswordStr()
    {
        return passwordStr;
    }

    public void setPasswordStr(String passwordStr)
    {
        this.passwordStr = passwordStr;
    }

    public int getLevel()
    {
        int level = 0;
        if (consume >= 4999)
        {
            level = 4;
        } else if (consume >= 2999)
        {
            level = 3;
        } else if (consume >= 999)
        {
            level = 2;
        } else if (consume >= 0)
        {
            level = 1;
        }
        return level;
    }
}
