package com.yzb.wallet.sys;


public class CardConstant {

    /** app-api接口 new */
    public static String app_account_balance = "AppAccountBalance";
    public static String app_account_balance_pre = "AppAccountBalancePre";
    public static String app_account_list = "AppAccountList";
    public static String app_card_list = "AppCardList";
    public static String app_query_payment = "AppQueryPayment";
    public static String app_add_payment = "AppAddPayment";
    public static String app_add_pay_pswd = "AppPayPswd";
    public static String app_validate_pay_pswd = "AppValidatePayPswd";
    public static String app_pay_request = "AppPayRequest";
    public static String app_recharge = "AppRecharge";
    public static String app_settle = "AppSettle";
    public static String app_transfer = "AppTransfer";
    public static String app_detail_customer = "CustomerDetail";
    public static String app_update_customer = "UpdateCustomer";
    public static String app_query_earn = "QueryEarn";
    public static String app_base_info = "AppBaseInfo";
    public static String app_card_sort_code = "AppCardBySortCode";
    public static String app_quick_pre_consume = "QuickPreConsume";

    /** card-api old */

    public static String app_api_payment_pswd = "AppPaymentPswd";

    public static String app_api_cust_info = "AppCustInfo";

    public static String app_api_transfer = "AppTransfer";

    public static String app_api_bankCardList = "AppBankCardList";

    public static String app_api_appCustomerAccountList = "AppCustomerAccountList";

    /** app-api old */

    /**付、收款*/
    public static String app_api_recharge = "OrderRecharge";// 充值
    public static String app_api_account_transfer = "AccountTransfer";// 账户转账

    /**账户*/
    public static String app_api_account_balance = "AccountBalance";// 账户余额
    public static String app_api_account_list = "CustomerAccountList";// 账户列表
    public static String app_api_account_create = "AccountCreate";// 创建账户

    /**支付密码*/
    public static String app_api_validate_paypswd = "ValidatePayPswd";// 支付密码校验
    public static String app_api_set_pswd = "PaymentPswdById";// 设置支付密码

    /**订单*/
    public static String app_api_order_stat = "OrderStat";// 订单汇总

    /**用户*/
    public static String app_api_cust_create = "AppCustCreate";// 注册yzb用户

    /**秘钥*/
    public static String app_api_valid_key = "QueryValidKey";// 用户秘钥-获取有效秘钥
    public static String app_api_add_valid_key = "AddValidKey";// 用户秘钥-添加有效秘钥

    /**付款方式*/
    public static String app_api_query_customer_payment = "QueryCustomerPayment";// 用户付款方式-查询
    public static String app_api_add_customer_payment = "AddCustomerPayment";// 用户付款方式-添加

    /**银行卡*/
    public static String app_api_bind_card = "BindCard";// 绑定银行卡
    public static String app_api_relieve_card = "RelieveCard";// 解绑银行卡

}
