package com.yzb.wallet.util;


public class WalletConstant {

    /**请求参数*/
    public static final String SPDB_CODE = "spdb";

    /**请求参数*/
    public static final int REQ_PAYPWD_FORGET = 0x1;

    /**返回参数*/
    public static final int RES_BACK = 0x0;

    /**系统参数*/
    public static final String CODE_OK = "0000";
    public static final String CODE_FAIL = "9999";
    public static final String CODE_NULL = "9993";
    public static final String CODE_INVALID = "9997";// 缺少必要参数
    public static final String CODE_EXIST = "4005";// 用户存在
    public static final String CODE_ALL = "999";
    public static final String CODE_NO_PWD = "2002";// 未设置支付密码
    public static final String PAY_TYPE_OFF = "10001";// 不可支付卡
    public static final String CODE_BANK = "0001";// 返回操作

    public static final int INIT_PAGE_SIZE = 10;
    public static final int NOT_LOGIN_DAYS = 180;
    public static final int PAY_PWD_LENGTH = 6;// 支付密码长度

    /**提示信息*/
    public static final String OPEN_WALLET = "银证保";
    public static final String MSG_UN_LOGIN = "请登录后重试";
    public static final String VERIFY = "正在验证";
    public static final String MSG_WITHOUT_PAY = "未开通支付账户";
    public static final String MSG_QUERY_FAIL = "获取数据失败";
    public static final String MSG_QUERY_NULL = "无数据";
    public static final String CREATE_SUCCESS = "创建成功";
    public static final String MSG_ERROR = "操作失败";
    public static final String ACCOUNT_NULL = "账户不存在";
    public static final String PAY_TYPE_OFF_TXT = "该卡不可支付，请完善信息";
    public static final String TEXT_BANK = "取消返回";

    /**付款方式 数据库中*/
    public static final String PAY_BANK = "0";
    public static final String PAY_BALANCE = "1";
    public static final String PAY_RED_ENVELOP = "2";
    public static final String PAY_GIFT_CARD = "3";
    public static final String PAY_INTEGRAL_PT = "4";// 平台积分
    public static final String PAY_INTEGRAL_SJ = "5";// 商家积分
    public static final String PAY_CREDIT = "7";// 信用余额
    public static final String PAY_BANK_TEXT = "银行卡";
    public static final String PAY_BALANCE_TEXT = "现金余额";
    public static final String PAY_RED_ENVELOP_TEXT = "红包余额";
    public static final String PAY_GIFT_CARD_TEXT = "礼品卡余额";

    /**付款方式 传入参数中*/
    public static final String PAY_TRANS_BALANCE = "1";// 钱包
    public static final String PAY_TRANS_CREDIT = "2";// 信用卡
    public static final String PAY_TRANS_DEBIT = "3";// 储蓄卡
    public static final String PAY_TRANS_GIFT = "4";//  礼品卡
    public static final String PAY_TRANS_RED = "5";// 红包

    /**付款方式 传入参数转换为数据库中含义 */
    public static final String TRANS_ACCOUNT(String accountType){
        if (WalletConstant.PAY_TRANS_BALANCE.equals(accountType)) {
            return WalletConstant.PAY_BALANCE;// 钱包

        } else if (WalletConstant.PAY_TRANS_CREDIT.equals(accountType)) {
            return WalletConstant.PAY_BANK;// 信用卡

        } else if (WalletConstant.PAY_TRANS_DEBIT.equals(accountType)) {
            return WalletConstant.PAY_BANK;// 储蓄卡

        } else if (WalletConstant.PAY_TRANS_GIFT.equals(accountType)) {
            return WalletConstant.PAY_GIFT_CARD;//  礼品卡

        } else if (WalletConstant.PAY_TRANS_RED.equals(accountType)) {
            return WalletConstant.PAY_RED_ENVELOP;// 红包
        }
        return "";
    }

    /** 付款方式名称*/
    public static final String PAY_TEXT(String orderType) {
        if (WalletConstant.PAY_BALANCE.equals(orderType)) {
            return "Hi钱包";
        } else if (WalletConstant.PAY_RED_ENVELOP.equals(orderType)) {
            return "红包账号";
        } else if (WalletConstant.PAY_GIFT_CARD.equals(orderType)) {
            return "礼品卡账号";
        }
        return "处理";
    }

    public static final String ACCOUNT_TEXT(String orderType) {
        if (WalletConstant.PAY_BALANCE.equals(orderType)) {
            return "Hi钱包";
        } else if (WalletConstant.PAY_RED_ENVELOP.equals(orderType)) {
            return "红包账号";
        } else if (WalletConstant.PAY_GIFT_CARD.equals(orderType)) {
            return "礼品卡账号";
        } else if (WalletConstant.PAY_INTEGRAL_PT.equals(orderType)) {
            return "平台积分账号";
        } else if (WalletConstant.PAY_INTEGRAL_SJ.equals(orderType)) {
            return "商家积分账号";
        } else if (WalletConstant.PAY_CREDIT.equals(orderType)) {
            return "信用账号";
        }
        return "";
    }

    public static final String SYS_ACCOUNT(String accountType){
        if (WalletConstant.PAY_BALANCE.equals(accountType)) {
            return "";
        } else if (WalletConstant.PAY_RED_ENVELOP.equals(accountType)) {
            return WalletConstant.SYS_BONUS;// 红包账户
        } else if (WalletConstant.PAY_GIFT_CARD.equals(accountType)) {
            return "";
        } else if (WalletConstant.PAY_INTEGRAL_PT.equals(accountType)) {
            return WalletConstant.SYS_POINT;// 积分账户
        } else if (WalletConstant.PAY_INTEGRAL_SJ.equals(accountType)) {
            return WalletConstant.SYS_POINT;// 积分账户
        }
        return "";
    }

    /**系统账户*/
    public static final String SYS_ACCOUNT_FEE = "3";// 收费账户
    public static final String SYS_POINT = "6";// 积分账户
    public static final String SYS_BONUS = "7";// 红包账户

    /**银行卡类型*/
    public static final String DEBIT_CARD = "1";// 借记卡
    public static final String CREDIT_CARD = "2";// 信用卡

    public static final String CARD_TYPE_TEXT(String orderType) {
        if (WalletConstant.DEBIT_CARD.equals(orderType)) {
            return "借记卡";
        } else if (WalletConstant.CREDIT_CARD.equals(orderType)) {
            return "信用卡";
        }
        return "";
    }

    /**收付款*/
    public static final String DEBIT = "01";// 付款
    public static final String CREDIT = "02";// 收款

    /**证件类型*/
    public static final String USER_CERT_TYPE_IDENTIFY = "1";//身份证

    /**实名认证*/
    public static final String PERSON_TYPE_AUTHENTICATION_NOT = "1";// 未认证
    public static final String PERSON_TYPE_AUTHENTICATION = "2";// 已认证
    public static final String PERSON_TYPE_EXAMINE_WAITING = "3";// 待审核
    public static final String PERSON_TYPE_EXAMINE_NOT = "4";// 未通过审核
    public static final String PERSON_TYPE_EXAMINE_FINISH = "5";// 已通过审核

    /**订单类型*/
    public static final String ORDER_TYPE_SETTLE = "2"; // 结算订单(提现)
    public static final String ORDER_TYPE_RECHARGE = "3";// 充值订单
    public static final String ORDER_TYPE_TRANSFER = "4";// 转账订单

    public static final String ORDER_TYPE_TEXT(String orderType) {
        if (WalletConstant.ORDER_TYPE_SETTLE.equals(orderType)) {
            return "提现";
        } else if (WalletConstant.ORDER_TYPE_RECHARGE.equals(orderType)) {
            return "充值";
        } else if (WalletConstant.ORDER_TYPE_TRANSFER.equals(orderType)) {
            return "支付";
        }
        return "处理";
    }

    /**订单状态*/
    public static final String ORDER_STATUS_CREATE = "0";//创建
    public static final String ORDER_STATUS_FINISH = "1";//完成
    public static final String ORDER_STATUS_REFUSE = "2";//拒绝
    public static final String ORDER_STATUS_CANCEL = "3";//撤销

    /**默认金额*/
    public static final String AMOUNT_ZERO = "0";

    /**用户类型*/
    public static final String USER_TYPE_PERSON = "P";//个人


    /**是否可用*/
    public static final String STATUS_ON = "1";//可用
    public static final String STATUS_OFF = "0";//不可用

    // 登录参数
    public static String sessionId = "";
    public static String UUID = "";

}
