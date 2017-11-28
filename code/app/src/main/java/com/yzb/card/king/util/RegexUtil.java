package com.yzb.card.king.util;

import android.app.Activity;
import android.text.TextUtils;

import com.yzb.card.king.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/21 16:33
 * 描述：
 */
public class RegexUtil
{
    /**
     * 将手机号码中间5位换成*号
     *
     * @author ysg
     * created at 2016/6/21 16:35
     */
    public static String hide5PhoneNum(String phoneNum)
    {
        if (TextUtils.isEmpty(phoneNum)) return "";
        return phoneNum.replaceAll("(\\d{3})\\d{5}(\\d{3})", "$1*****$2");
    }

    /**
     * 将手机号码中间4位换成*号
     * @param phoneNum
     * @return
     */
    public static String hide4PhoneNum(String phoneNum)
    {
        if (TextUtils.isEmpty(phoneNum)) return "";
        return phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 验证手机号码
     *
     * @author ysg
     * created at 2016/6/13 16:48
     */
    public static boolean validPhoneNum(String phone)
    {
        boolean flag = true;
        if (phone != null && phone.length() > 0)
        {
            Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(177)|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phone);
            if (!m.matches())
            {
                flag = false;
                UiUtils.shortToast("请输入正确的手机号码");
            }
        } else
        {
            flag = false;
            UiUtils.shortToast("手机号不能为空");
        }
        return flag;
    }

    /**
     * 金额验证，最多精确到小数点后2位；
     * 功能：判断输入框内容是否超出小数点后2位；
     *
     * @param s
     * @return true:已超出；false:未超出；
     */
    public static boolean verifyJeFormat(String s)
    {
        if (!TextUtils.isEmpty(s) && s.contains("."))
        {
            Pattern p = Pattern.compile("^[0-9]+.[0-9]{3,}$");
            boolean m = p.matcher(s).matches();
            return m;
        }
        return false;
    }

    /**
     * 功能：判断s为 不超出小数点后2位的合法字符；
     *
     * @param s
     * @return true:合法；false:不合法；
     */
    public static boolean verifyJeFormat2(String s)
    {
        if (!TextUtils.isEmpty(s))
        {
            Pattern p = Pattern.compile("^([0-9]+)|([0-9]+.[0-9]{0,2})$");
            boolean m = p.matcher(s).matches();
            return m;
        }
        return false;
    }

    /**
     * 验证身份证
     *
     * @author ysg
     * created at 2016/6/13 16:49
     */
    public static boolean validIdNum(String idNum)
    {
        boolean flag = true;
        if (idNum != null && idNum.length() > 0)
        {
            Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            Matcher idNumMatcher = idNumPattern.matcher(idNum);
            if (!idNumMatcher.matches())
            {
                flag = false;
                UiUtils.shortToast("身份证号码格式错误");
            }
        } else
        {
            flag = false;
            UiUtils.shortToast("请输入身份证号码");
        }
        return flag;
    }

    /**
     * 验证航班号格式；
     * 航班号格式:2位英文字母开头；后跟3或4位数字；
     *
     * @param flightNumber
     * @return
     */
    public static boolean validFlightNumber(String flightNumber)
    {
        if (!TextUtils.isEmpty(flightNumber))
        {
            Pattern pattern = Pattern.compile("^[a-zA-Z]{2}[0-9]{3,4}$");
            Matcher matcher = pattern.matcher(flightNumber);
            return matcher.matches();
        }

        return false;
    }

    /**
     * 验证身份证
     *
     * @author created at 2016/6/24
     */
    public static boolean validIdNumNoToast(String idNum)
    {
        boolean flag = true;
        if (idNum != null && idNum.length() > 0)
        {
            Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            Matcher idNumMatcher = idNumPattern.matcher(idNum);
            if (!idNumMatcher.matches())
            {
                flag = false;
            }
        } else
        {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱
     */
    public static boolean verifyEmail(Activity context, String email)
    {
        boolean result = false;
        if (!TextUtils.isEmpty(email))
        {
            Pattern pattern = Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
            Matcher matcher = pattern.matcher(email);
            result = matcher.matches();
        }

        if (!result)
        {
            toastCustom(context, R.string.email_format_error);
        }
        return result;
    }


    public static void toastCustom(Activity context, int resId)
    {
        ToastUtil.i(context, context.getString(resId));
    }

    /**
     * 将身份证号首位换成*号
     *
     * @author gqy
     */
    public static String hideIdNum(String idNum)
    {
        if (!TextUtils.isEmpty(idNum))
        {
            return idNum.replaceAll("(\\d{6})\\d+(\\d{2})", "$1**********$2");
        }
        return "";
    }

    /**
     * 将身份证号出生年月日换成*号
     */

    public static String hideIdMidNum(String idNum)
    {

        if (!TextUtils.isEmpty(idNum))
        {
            return idNum.replaceAll("(\\d{6})\\d{8}(\\d{4})", "$1*****$2");
        }
        return "";
    }

    /**
     * 汉字验证；
     * true为全部是汉字，否则是false
     *
     * @author gqy
     */
    public static boolean isChinaChacters(String input)
    {
        if (!TextUtils.isEmpty(input))
        {
            Pattern pa = Pattern.compile("^[\u4e00-\u9fa5]*$");
            Matcher matcher = pa.matcher(input);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 英文字母验证；
     * true为全部是英文否则是false
     *
     * @author gqy
     */
    public static boolean isEngChacters(String input)
    {
        if (!TextUtils.isEmpty(input))
        {
            Pattern pa = Pattern.compile("^[a-zA-Z]*$");
            Matcher matcher = pa.matcher(input);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 中文验证；
     * true为中文否则是false
     *
     * @author gqy
     */
    public static boolean validChinese(CharSequence name)
    {
        if (!TextUtils.isEmpty(name))
        {
            Pattern pa = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
            Matcher matcher = pa.matcher(name);
            return matcher.matches();
        }
        return false;
    }


    /**
     * 验证护照；
     * (P\d{7})|G\d{8})
     *
     * @param passPort
     * @return
     */
    public static boolean validPassPort(String passPort)
    {
        if (!TextUtils.isEmpty(passPort))
        {
            Pattern pa = Pattern.compile("^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$");

            Matcher matcher = pa.matcher(passPort);
            return matcher.matches(); //
        }
        return false;
    }

    /**
     * 验证港澳通行证；
     *
     * @param s
     * @return
     */
    public static boolean validGangAo(String s)
    {
        if (!TextUtils.isEmpty(s))
        {
            Pattern pa = Pattern.compile("^[HMhm]{1}([0-9]{10}|[0-9]{8})$");
            Matcher matcher = pa.matcher(s);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 验证台胞证；
     *
     * @param s
     * @return
     */
    public static boolean validTaiwanese(String s)
    {
        if (!TextUtils.isEmpty(s))
        {
            Pattern pa = Pattern.compile("^([0-9]{8})|([0-9]{10})$");
            Matcher matcher = pa.matcher(s);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 验证军官证；
     *
     * @param s
     * @return
     */
    public static boolean validOfficer(String s)
    {
        if (!TextUtils.isEmpty(s))
        {
            Pattern pa = Pattern.compile("^\\d{8}$");
            Matcher matcher = pa.matcher(s);
            return matcher.matches();
        }
        return false;
    }

    public static boolean validUserName(String nickName)
    {
        boolean flag = true;
        if (!TextUtils.isEmpty(nickName))
        {
            Pattern pa = Pattern.compile("^[a-zA-z\\u4e00-\\u9fa5][\\u4e00-\\u9fa5a-zA-Z0-9_]{2,9}$");
            Matcher matcher = pa.matcher(nickName);
            if (!matcher.matches())
            {
                UiUtils.longToast("昵称只能字母、中文、数字、下划线组成，只能以字母或中文开头，长度2~9");
                flag = false;
            }
        } else
        {
            UiUtils.shortToast("昵称不能为空");
            flag = false;
        }
        return flag;
    }

    public static boolean isNumber(String str)
    {
        if (!TextUtils.isEmpty(str))
        {
            Pattern pa = Pattern.compile("^\\d$");
            Matcher matcher = pa.matcher(str);
            return matcher.matches();
        }
        return false;
    }

    /**
     * email不是必填情况下，email为空时返回true
     *
     * @param s
     * @return
     */
    public static boolean validEmail(String s)
    {
        if (!TextUtils.isEmpty(s))
        {
            Pattern pa = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
            Matcher matcher = pa.matcher(s);
            if (!matcher.matches())
            {
                UiUtils.shortToast("邮箱格式错误");
                return false;
            }
        }
        return true;
    }

}
