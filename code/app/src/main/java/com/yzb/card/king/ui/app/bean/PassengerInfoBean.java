package com.yzb.card.king.ui.app.bean;

import android.text.TextUtils;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能：旅客信息
 *
 * @author:gengqiyun
 * @date: 2016/6/20
 */
public class PassengerInfoBean implements Serializable
{
    public String id;//旅客ID
    public String name;//姓名；
    public String zhName;
    public String mobile;//手机号；
    public String certNo;//证件号（不限于身份证号）；
    public String certType;//证件类型  1身份证；2护照；3台胞证；4回乡证；5军人证；6港澳通行证；
    public String countryName;//国籍名称
    public String countryId; //国籍id
   // public boolean isDefault; //是否默认 0否，1是
    public long createTime;
    public String customerId; //当前登录用户id；
    public String status; //1可用；0删除；2默认
    public String sex; //1:男；2：女；
    public String engSurname; //英文姓；
    public String engName;//英文名；
    public String birthday;
    private String email;//邮箱
    private String certInvalidDate;//证件失效日期

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * 通过身份证判断是否成年
     *
     * @author ysg
     * created at 2016/7/12 14:42
     */
    public String getPersonType()
    {
        String type = "未识别";

        Date birth;
        String birthdayLocal = "";
        if ("1".equals(certType) && RegexUtil.validIdNum(certNo))
        {
            birthdayLocal = certNo.substring(6, 14);
            LogUtil.e("birthday" + birthdayLocal);
        } else if (!TextUtils.isEmpty(birthday))
        {
            birthdayLocal = birthday.replaceAll("-", "");
        }
        if (TextUtils.isEmpty(birthdayLocal))
        {
            return type;
        }

        birth = DateUtil.string2Date(birthdayLocal, "yyyyMMdd");
        Calendar nowTime = Calendar.getInstance();
        Calendar birthTime = Calendar.getInstance();
        birthTime.setTime(birth);

        if (nowTime.after(birthTime))
        {
            //计算年龄；
            int age = nowTime.get(Calendar.YEAR) - birthTime.get(Calendar.YEAR);
            if (nowTime.get(Calendar.DAY_OF_YEAR) < birthTime.get(Calendar.DAY_OF_YEAR))
            {
                age -= 1;
            }
            if (age < 2)
            {
                type = "婴儿";
            } else if (age <= 12)
            {
                type = "儿童";
            } else
            {
                type = "成人";
            }
        }

        return type;
    }

    /**
     * 通过身份证判断用户年龄层；
     */
    public String getPersonTypeEn()
    {
        String type = "未识别";
        Date birth;
        String birthdayLocal = "";
        if ("1".equals(certType) && RegexUtil.validIdNum(certNo))
        {
            birthdayLocal = certNo.substring(6, 14);
            LogUtil.e("birthday" + birthdayLocal);
        } else if (!TextUtils.isEmpty(birthday))
        {
            birthdayLocal = birthday.replaceAll("-", "");
        }
        if (TextUtils.isEmpty(birthdayLocal))
        {
            return type;
        }
        birth = DateUtil.string2Date(birthdayLocal, "yyyyMMdd");

        Calendar nowTime = Calendar.getInstance();
        Calendar birthTime = Calendar.getInstance();
        birthTime.setTime(birth);

        if (nowTime.after(birthTime))
        {
            //计算年龄；
            int age = nowTime.get(Calendar.YEAR) - birthTime.get(Calendar.YEAR);
            if (nowTime.get(Calendar.DAY_OF_YEAR) < birthTime.get(Calendar.DAY_OF_YEAR))
            {
                age -= 1;
            }
            if (age < 2)
            {
                type = AppConstant.TYPE_BABY;
            } else if (age <= 12)
            {
                type = AppConstant.TYPE_CHILD;
            } else
            {
                type = AppConstant.TYPE_ADULT;
            }
        }
        return type;
    }

    public String getZhName()
    {
        return zhName;
    }

    public void setZhName(String zhName)
    {
        this.zhName = zhName;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getCertNo()
    {
        return certNo;
    }

    public void setCertNo(String certNo)
    {
        this.certNo = certNo;
    }

    public String getCertType()
    {
        return certType;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getCertInvalidDate()
    {
        return certInvalidDate;
    }

    public void setCertInvalidDate(String certInvalidDate)
    {
        this.certInvalidDate = certInvalidDate;
    }

    public void setCertType(String certType)
    {
        this.certType = certType;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

//    public boolean getIsDefault()
//    {
//        return isDefault;
//    }
//
//    public void setIsDefault(boolean isDefault)
//    {
//        this.isDefault = isDefault;
//    }

    public long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getEngSurname()
    {
        return engSurname;
    }

    public void setEngSurname(String engSurname)
    {
        this.engSurname = engSurname;
    }

    public String getEngName()
    {
        return engName;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public void setEngName(String engName)
    {
        this.engName = engName;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }
}
