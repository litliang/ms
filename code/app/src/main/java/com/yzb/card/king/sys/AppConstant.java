package com.yzb.card.king.sys;


import android.text.TextUtils;

import com.yzb.card.king.util.PreferencesService;

public class AppConstant
{
    // 每页最大数量
    public static final int MAX_PAGE_NUM = 20;
    // 图片上传的最大张数；
    public static final int MAX_IMAGE_NUMBER = 9;

    public static final int LOGIN_ACCESS = 000000001;

    public static final int LOAD_DATA_REFRESH = 0;

    /****************************
     * 跳转请求参数requestCode
     *********************************/
    public static int REQ_TRANSFER_MAIL = 0x104;//转发邮件
    public static int REQ_TRANSFER_RULE = 0x105;//转发规则
    public static int REQ_ADJUST_LIMIT = 0x106;//额度提升
    public static int REQ_ADD_CREDIT_CARD = 0x110;//添加信用卡
    public static int REQ_ADD_CREDIT_INTEGER = 0x1101;//添加信用卡
    public static int REQ_BANK_CARD = 0x111;//我的银行卡
    public static int REQ_ORDER_DETAIL = 0x112;//账单明细
    public static int REQ_REPAYMENT = 0x113;//还款

    public static int REQ_MSF_GET = 0x201;//码尚付输入金额(收)
    public static int REQ_MSF_GET_QR = 0x202;//码尚付二维码页面(收)
    public static int REQ_MSF_GET_PSD = 0x203;//码尚付密码验证(收)
    public static int REQ_MSF_PAY = 0x204;//码尚付输入金额(付)
    public static int REQ_MSF_PAY_QR = 0x205;//码尚付二维码页面(付)
    public static int REQ_MSF_PAY_PSD = 0x206;//码尚付密码验证(付)

    public static int REQ_PHONE = 0x990;//拨号键盘
    public static int REQ_SHARE = 0x992;//分享
    public static int REQ_CODE = 0x0;

    /****************************
     * 跳转返回参数resultCode
     *********************************/
    public static int RES_HOME_PAGE = 0x0;//首页
    public static int RES_CONTACT_BANK = 0x102;//联络银行
    public static int RES_IMPORT_BILL = 0x103;//导入账单
    public static int RES_TRANSFER_MAIL = 0x104;//转发邮件

    public static int RES_MSF_GET = 0x201;//码尚付输入金额(收)
    public static int RES_MSF_PAY = 0x202;//码尚付输入金额(付)
    public static int RES_BACK = 0x990;//返回

    // 用户
    public static final String CODE_OK = "0000";
    public static final String CODE_PAY_PWD_NO_EXIST = "2002"; // 支付密码不存在；
    public static final String CODE_PAY_PWD_VERIFY_FAIL = "2001"; // 支付密码验证失败；
    public static final String CODE_NULL = "9993";
    public static final String CODE_ALL = "999";
    public static final String CODE_PWD_SAME = "1006"; //新密码不能和原密码相同
    public static final String CODE_CARD_BD = "2013"; //此卡已绑定

    public static final String APP_DIR = "com.yzb.card.app";
    public static final String APP_CF = "carouselFigure";
    public static final String APP_TEMP = "temp";
    public static final String ADD_POINT_CONTACT = "add_point_contact";


    public static final int PAY_PWD_LENGTH = 6;// 支付密码长度

    public static final String sign = "18856066230";//签名；
    public static final String leftTime = "20";//超时时间  1-2两位的整数；
    public static final String charset = "UTF-8";

    /**
     * 账户类型
     */
    public static final String ACCOUNT_TYPE_BANK = "0";// 银行卡
    public static final String ACCOUNT_TYPE_BALANCE = "1";// 账户余额
    public static final String ACCOUNT_TYPE_BONUS = "2";// 红包账户
    public static final String ACCOUNT_TYPE_GIFT_CARD = "3";// 礼品卡账户
    public static final String ACCOUNT_NO_INPUT = "0";// //特惠付款页无需手动输入 不参与优惠金额；
    public static final String ACCOUNT_NEEN_INPUT = "1";// 特惠付款页需手动输入 不参与优惠金额；

    /**
     * 特惠付款退款方式
     */
    public static final String refund_type_platform_wallet = "1";//平台钱包
    public static final String refund_type_way_return = "2";//原路退回
    /**
     * 机票订单退款方式；
     */
    public static final String refund_type_card0 = "3";  //储蓄卡
    public static final String refund_type_card1 = "2";  //信用卡
    public static final String refund_type_wallet = "1";     //钱包
    /**
     * 人种的标识符；
     * ADT：成人；CHD：儿童；INF：婴儿
     */
    public static final String TYPE_ADULT = "ADT";//成人；
    public static final String TYPE_CHILD = "CHD";//儿童
    public static final String TYPE_BABY = "INF";//婴儿
    /**
     * 航班类型（单程：OW；往返：RT；多段：MT）
     */
    public static final String TYPE_SINGLE = "OW";//单程；
    public static final String TYPE_ROUND = "RT";//往返
    public static final String TYPE_MULT = "MT";//多段

    //特惠付款-->积分列表出参 账户类型；
    public static final String product_id_outcountry = "332";//国际：332；
    public static final String product_id_incountry = "331";//国内 : 331；

    //特惠付款-->积分列表出参 账户类型；
    public static final String INTEGRAL_ACCOUNT_PLATFORM = "4";//平台积分；
    public static final String INTEGRAL_ACCOUNT_SHOP = "5";//商家积分；

    /**
     * 酒店订单页支付类型
     */
    public static final String PAY_ONLINE = "1";// 在线付；
    public static final String PAY_ARRIVE = "2";// 到店付；

    /**
     * 到店时间类型
     */
    public static final String ARRIVE_18 = "1";// 18点无担保；
    public static final String ARRIVE_23 = "2";// 23:59点担保；
    public static final String ARRIVE_06 = "3";// 6点担保；

    /**
     * 满减类型；
     */
    public static final int TYPE_FULL_CUT = 1;// 满减
    public static final int TYPE_EVERY_FULL_CUT = 2;// 每满减
    public static final int TYPE_STAIR_CUT = 3;// 阶梯减

    /**
     * 支付类型
     */
    public static final String PAYTYPE = "0";
    public static final String DEBIT = "-1";// 付款
    public static final String CREDIT = "1";// 收款
    public static final String NONE = "3";

    /**
     * 账单状态
     */
    public static String functionCode = "functionCode";

    public static String UUID = "1";

    public static String sessionId = "";
    /**
     * 特殊sessionId
     */
    public static String qrCodeSessionId = "";

    /**
     * 比较sessionId
     *
     * @return
     */
    public static boolean handleSessionId(String temp)
    {
        boolean flag = false;

        if (temp != null)
        {
            if (TextUtils.isEmpty(temp))
            {
                //存储
                PreferencesService pre = new PreferencesService(GlobalApp.getInstance().getContext());
                pre.updatePreferences("sessionId", sessionId);

                String sessionIdTemp = pre.getValue("qrCodeSessionId");

                if (TextUtils.isEmpty(sessionIdTemp))
                {

                    pre.updatePreferences("qrCodeSessionId", temp);
                }


            } else
            {
                temp = temp.replaceAll("\"", "").replaceAll("\'", "");

                if (sessionId.equals(temp))
                {

                    flag = false;
                } else
                {
                    sessionId = temp;
                    flag = true;

                    //存储
                    PreferencesService pre = new PreferencesService(GlobalApp.getInstance().getContext());
                    pre.updatePreferences("sessionId", sessionId);

                    pre.updatePreferences("qrCodeSessionId", sessionId);
                }
            }

        }
        return flag;
    }

    /**
     * 邮件属性
     */
    public static String mail_user = "alerts@payvel.com";
    public static String mail_password = "Abc.12";
    public static String mail_smtp_host = "smtp.exmail.qq.com";


    public static String MAIL_TITLE_RULE = "【卡王】转发邮件设定步骤";


    /**
     * 机票
     */
    public static final String select_domestic_region_id = "1";//国内
    public static final String select_abroad_region_id = "2";//国外
    public static final String TICKET_LOCATION = "定位";//定位
    public static final String HOT = "热门";//热门
    public static final String TICKET_CANGWEI = "1"; //飞机


    public static final String HOTEL_HOMEPAGER = "HOTEL_HOMEPAGE";//酒店轮播图code
    public static final String DISCOUNT_HOMEPAGER = "DISCOUNT_HOMEPAGE"; //商户优惠首页轮播图code
    public static final String PLANE_HOMEPAGE = "PLANE_HOMEPAGE"; //机票首页轮播图code
    public static final String COUPON_SHOPS = "COUPON_SHOP"; //优惠券商城轮播图code

    public static final String GIFTCARD_SHOP_HOMEPAGE = "GIFTCARD_HOME"; //礼品卡商城首页轮播图code
    public static final String TRAVEL_HOMEPAGE = "TRAVEL_HOMEPAGE"; //旅游首页轮播图code
    public static final String POINT_HOMEPAGE = "POINT_HOMEPAGE"; //积分宝首页轮播图code
    public static final String ONLINE_CARD_HOMEPAGE = "CREDIT_ONLINE"; //在线办卡首页轮播图code


    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_PLACE_NAME = "card-king-place";
    public static final String FILE_HOTEL_NAME = "card-king-hotel";
    public static final String FILE_TRAIN_NAME = "card-king-train";

    public static final String home_type_id = "0";
    /**
     * 美食
     */
    public static final String meishi_id = "1";
    /**
     * 旅游
     */
    public static final String travel_id = "3";
    /**
     * 奢侈品
     */
    public static final String shechipin_id = "5";
    /**
     * 机票
     */
    public static final String ticket_id = "6";
    /**
     * 电影
     */
    public static final String film_id = "7";
    /**
     * 酒店
     */
    public static final String hotel_id = "8";
    /**
     * 陆上交通
     */
    public static final String transport_id = "9";



    /**
     * 礼品卡
     */
    public static final String gift_card_id = "130";
    /**
     * 优惠券产品列表 轮播图id；
     */
    public static final int coupons_id = 131;

    /**
     * 积分兑换内容标识信息
     */
    public static final String integral_type_parentid = "1002";//积分宝首页频道parentId；
    public static final String discount_type_parentid = "1003";//商户优惠首页频道parentId；
    public static final String exchange_type_parentid = "1005";//积分兑换首页频道parentId；
    public static final String mileage_type_parentid = "1006";//兑里程parentId；
    public static final String youka_type_parentid = "1007";//兑油卡parentId；
    public static final String phone_type_parentid = "1008";//兑话费parentId；
    public static final String flow_type_parentid = "1009";//兑流量parentId；
    public static final String money_type_parentid = "1010";//兑现金parentId；


    public static final String discount_channel_category = "3";//商户优惠；
    public static final String collect_shop_type = "1";//商家 类别
    public static final String collect_product_type = "2";//商品 类别

    public static final String collect_store_category = "1";//门店；类型；
    public static final String collect_film_category = "2";//影片；类型；
    public static final String collect_hotel_category = "5";//酒店；类型；



    /**
     * h5页面接口信息参数
     */
    public static String h5_category_help = "2"; // 帮助详情；
    public static String h5_category_temaihui = "3"; // 特卖惠的h5页面参数；
    public static String h5_category_paysetting = "4"; // 支付设置的h5页面参数；
    public static String h5_category_passenger_info = "5"; // 旅客信息的h5页面参数；
    public static String h5_category_integral_help = "10"; // 积分帮助；
    public static String h5_category_ticket_back_detail = "11"; // 机票退改详情；
    public static String h5_category_help_list = "13"; // 帮助列表；
    public static String h5_category_contacts_us = "14"; // 联系我们；
    public static String h5_category_integral_exchange_norm = "15";//积分兑换准则
    public static String h5_category_pay_service_agreement = "16";//支付服务协议
    public static String h5_category_integral_exchange_service_agreement = "17";//积分兑换服务协议
    public static String h5_category_hotel_cancellation_insurance = "18";//酒店取消险
    public static String h5_category_hotel_travel_insurance = "19";//酒店出行意外险
    public static String h5_category_plane_ticket_refund_rule = "23"; //机票退票政策详情
    public static String h5_category_plane_adlut_che_baby = "20"; //婴儿儿童预定须知
    public static String h5_category_ticket_order_h5_1 = "21"; //锂电池及危险品须知
    public static String h5_category_ticket_order_h5_2 = "22"; //旅行套餐产品服务条款
    public static String h5_giftcard_active = "27"; // 激活实体卡服务协议
    public static String h5_bouns_create_service = "28"; // 发红包服务协议
    public static String h5_buy_giftcard_service = "29"; // 购买心意卡页面的服务协议


    //查询商品活动  商品code码；
    //多个使用英文逗号分割（1：旅游产品，2：酒店房间套餐，3：酒店餐厅套餐4：酒店酒吧套餐，5：酒店会场套餐，6：酒店运动套餐，7：机票套餐,8茶吧，9健身，10大礼包）
    public static String discount_code_travel = "1";
    public static String discount_code_room = "2";
    public static String discount_code_dinner = "3";
    public static String discount_code_bar = "4";
    public static String discount_code_meet = "5";
    public static String discount_code_sport = "6";
    public static String discount_code_ticket = "7";
    public static String discount_code_tea_bar = "8";
    public static String discount_code_gym = "9";
    public static String discount_code_gift = "10";

    ////口令类型；1:商家，商品；2，第三方送红包；3，第三方送礼品卡；4：订单信息推送；5，平台推送信息；6,优惠券；
    public static final String command_type_shop = "1";
    public static final String command_type_bouns = "2";
    public static final String command_type_giftcard = "3";
    public static final String command_type_order = "4";
    public static final String command_type_platform = "5";
    public static final String command_type_coupon = "6";


    /**
     * 第三方平台申请的appKey以及其它信息
     */
    // 微信平台申请的key和secret；
    public static final   String weixin_id = "wxc1b35d7cf0e594b1";
    public static final  String weixin_secret = "fdcbd701826b18a995f3c2db050ce25f";
    // qq平台申请的key和secret；
    public static final  String qq_id = "1106151462";
    public static final   String qq_secret = "ransXSB3hCIph40M";

    // 新浪微博申请的key和secret；
    public static final  String sina_id = "2273870150";
    public static final  String sina_secret = "8a6830a4be43d635caa2be210871cc92";

    public static final   String INTENT_BUNDLE = "intent_bundle";
    public static final   String sp_childtypelist_scp = "childtypelist_scp";
    public static final   String sp_childtypelist_ms = "childtypelist_ms";
    public static final  String sp_childtypelist_travel = "childtypelist_travel";
    public static final  String sp_childtypelist_hotel = "childtypelist_hotel";
    public static final  String sp_childtypelist_transport = "childtypelist_transport";
    public static final  String sp_childtypelist_home = "childtypelist_home";

    public static final  int travel_parent_id = 3;
    public static final int ms_parent_id = 1;
    public static final    int scp_parent_id = 5;

    //验证码类型
    public static final   String DOWNLOAD_SERVICE_ACTION = "com.yzb.card.king.download_service_aciton";
    public static final  String UPLOAD_SERVICE_ACTION = "com.yzb.card.king.upload_service_aciton";
    public static final  String MSG_SERVICE_ACTION = "com.yzb.card.king.msg_service_aciton";
    public static final  String MSG_BROASTCAST_ACTION = "com.yzb.card.king.msg_broadcast_aciton";


    ///////////////////////////////////////////////////
    ////////////////////App 数据库//////////////////
    //////////////////////////////////////////////////
    //数据库名
    public static final String APP_DB_NAME = "hilife.db";
    //数据库版本号
    public static final int APP_DB_VERSION = 1;

    /****************
     * 收藏相关常量
     **************/
    public static final   String COLLECTION_TYPE_STORE = "1";//商家
    public static final   String COLLECTION_CATEGORY_STORE = "1";//门店


//    deptime出发时间  arrtime抵达时间    depAirPort 出发机场   arrAirPort抵达机场     机型acft     舱位 flightQabin    航空公司 carrier

    // 出发时间
    public static final  String deptime = "deptime";
    // 抵达时间
    public static final  String arrtime = "arrtime";
    // 出发机场
    public static final String depAirPort = "depAirPort";
    // 抵达机场
    public static final  String arrAirPort = "arrAirPort";
    // 机型
    public static final  String acft = "acft";
    // 仓位
    public static final String flightQabin = "flightQabin";
    // 航空公司
    public static final  String carrier = "carrier";

    public static final int TYPE_NULL = 0;
    public static final int TYPE_NORMLL = 300;//特殊标记


    // 往返程
    //OW：单程；RT：往返；MT：多段
    public static final  String OW = "OW";
    public static final  String RT = "RT";
    public static final  String MT = "MT";
    //商户联系
    public static final  String APP_TEL = "021-58606003";

}
