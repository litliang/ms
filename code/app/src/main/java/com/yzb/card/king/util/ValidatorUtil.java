package com.yzb.card.king.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 校验器：利用正则表达式校验邮箱、手机号等
 *
 * @author liujiduo
 */
public class ValidatorUtil {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    /**
     * 正则表达式：验证登录密码
     */
    public static final String REGEX_LOGIN_PASSWORD = "^[a-zA-Z0-9@#$%&*()_]{6,20}$";

    /**
     * 正则表达式：必须包含数字和字母，长度必须在8到16位之内 可以使用字母、数字、特殊符号(如：!, $, #, %，其中需禁止使用`）
     */
    public static final String REGEX_PASSWORD_REG = "^(?![^a-zA-Z]+$)(?!\\D+$)(?![/!,/#,/$,/%])[^\\!,\\#,\\$,\\%]{8,16}$";

    /**
     * 6-20位英文字母、数字或符号组成
     */
    public static final String REGEX_PASSWORD_ZC = "^[^\\u4e00-\\u9fa5^ ]{6,20}$";

    /**
     * 用户昵称规则最长16个字符，大小写英文字母、下划线、数字、中文
     */
    public static final String USER_NAME="^[a-zA-Z\\d\\_\\u2E80-\\u9FFF]{0,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(147)|(15[0-9])|(177)|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";


    /**
     * 正则表达式：不可连续三码出现字母或数字
     */
//    public static final String REGEX_PASSWOR_LX="\\d{3,}|[a-zA-Z]{3,}";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式，验证邮政编码
     */
    public static final String ZIP_CODE = "^[1-9][0-9]{5}$";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)[-\\.]){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证银行卡
     */
    public static final String DEBIT_CARD = "(^(\\d{16}|\\d{19})$)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username)
    {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password)
    {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验昵称
     * @param name
     * @return
     */
    public static boolean isName(String name){
        return Pattern.matches(USER_NAME,name);
    }

    /**
     * 注册密码
     * @param password
     * @return
     */
    public static boolean isZcPassword(String password)
    {
        return Pattern.matches(REGEX_PASSWORD_ZC, password);
    }

    /**
     * 校验邮政编码
     *
     * @param zipCode
     * @return
     */
    public static boolean isZipCode(String zipCode)
    {
        return Pattern.matches(ZIP_CODE, zipCode);
    }

    /**
     * 校验密码 必须包含字母、数字 字母不区分大小写
     *
     * @param password
     * @return
     */
    public static boolean isRegPassword(String password)
    {
        return Pattern.matches(REGEX_PASSWORD_REG, password);
    }

//    /**
//     * 密码验证连续3个码的字母或数字
//     * @param password
//     * @return
//     */
//    public static boolean isRegPasswordLx(String password){
//        return Pattern.matches(REGEX_PASSWOR_LX,password);
//    }

    /**
     * 校验登录密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLoginPassword(String password)
    {
        return Pattern.matches(REGEX_LOGIN_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile)
    {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email)
    {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese)
    {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard)
    {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            return Pattern.matches(REGEX_URL, url);
        }
        return false;
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr)
    {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 校验银行卡
     */
    public static boolean isBankCard(String bankCard)
    {
        return Pattern.matches(DEBIT_CARD, bankCard);
    }

}