package com.yzb.card.king.ui.app.interfaces;

/**
 * 功能：特惠付款listener；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public interface DiscountListener
{
    /**
     * 活动平台
     */
    int platform_type_platform_shop = 0; //1平台商家活动；
    int platform_type_platform = 1; //2平台活动；
    int platform_type_shop = 2; //3商家活动；
    int platform_type_bank = 4; //4银行活动；

    /**
     * 活动项（当活动平台为123时，0所有；1优惠券；2红包；3折扣；4满减；5积分；6促销；
     * 当活动平台为银行时，0所有；1银行积分；2银行折扣；）
     */
    int type_all = 0; //所有；
    int type_yhq = 1; //优惠券类型；
    int type_hb = 2;  //红包类型；
    int type_discount = 3;  //折扣；
    int type_full_reduction = 4; //满减；
    int type_full_integral = 5; //5积分；
    int type_full_sale = 6; //6促销；

    int type_bank_all = 0; //0所有
    int type_bank_integral = 1; //银行积分
    int type_bank_discount = 2; //银行折扣

    /**
     * @param req_flag 下拉或上拉的标志位；
     * @param data
     */
    void onListenSuccess(String req_flag, Object data);

    void onListenError(String msg);


    /**
     * 活动平台（1平台商家活动；2平台活动；3商家活动；4银行活动）
     */
    String req_platform_type_platform_shop = "1"; //1平台商家活动；
    String req_platform_type_platform = "2"; //2平台活动；
    String req_platform_type_shop = "3"; //3商家活动；
    String req_platform_type_bank = "4"; //4银行活动；
    /**
     * 请求标志；
     * <p/>
     * 活动项（当活动平台为123时，0所有；1优惠券；2红包；3折扣；4满减；5积分；6促销；
     * 当活动平台为银行时，0所有；1银行积分；2银行折扣；）
     */
    String req_type_all = "10"; //所有；
    String req_type_yhq = "11"; //优惠券类型；
    String req_type_hb = "12";  //红包类型；
    String req_type_discount = "13";  //折扣；
    String req_type_full_reduction = "14"; //满减；
    String req_type_full_integral = "15"; //5积分；
    String req_type_full_sale = "16"; //6促销；

    String req_type_bank_all = "20"; //0所有
    String req_type_bank_integral = "21"; //银行积分
    String req_type_bank_discount = "22"; //银行折扣
}
