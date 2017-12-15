package com.yzb.card.king.sys;

/**
 * app业务接口
 */
public class CardConstant {

    public static final String UpdatePayDetail = "UpdatePayDetail";

    /******************************************************************************
     ******************************项目首页模块*****************************************
     *******************************************************************************/
    //轮播图；
    public static String card_app_carouselfigure = "CarouselFigure";
    //广告计费
    public static String card_add_addprice = "AdvertFeeStatistics";
    //个人频道查询
    public static String card_app_customerchannellist = "CustomerChannelList";
    //个人频道更新
    public static String card_app_customerchannelupdate = "CustomerChannelUpdate";
    //版本更新
    public static String select_app_version = "SelectAppVersion";
    /**
     * 查询卡权益详情
     */
    public static  String SELECTCARDRIGHTSINFO_IT = "SelectCardRightsInfo";
    /**
     * 查询限时抢购详情
     */
    public static  String SELECTFLASHSALEINFO_IT = "SelectFlashsaleInfo";
    /**
     * 查询礼品套餐增值项
     */
    public static String QUERYGIFTSINCREMENTITEMS_IT = "QueryGiftsIncrementItems";
    /**
     * 查询商品适用门店列表
     */
    public static String QUERYGOODSAPPLYSTORE_IT = "QueryGoodsApplyStore";
    /**
     * 查询限时抢购详情
     */
    public static String QUERYFLASHSALEINFO_IT = "QueryFlashsaleInfo";


    /******************************************************************************
     * *****************************信用卡模块*****************************************
     *******************************************************************************/

    public static String CREDIT_HISTORY = "PaymentCreditHistory";//信用卡还款记录

    public static String CREDIT_UPDATE = "RevampBankCredit"; //信用卡修改

    public static String CREDIT_UNBUND = "SolveBankCredit";//信用卡解绑

    public static String card_app_credit_list = "QueryQuotaManagement";

    public static String RELIEVE_CARD = "RelieveCard";

    /******************************************************************************
     ******************************商户优惠模块*****************************************
     *******************************************************************************/
    /**
     * 常用联系人列表；
     */
    public static String DISCOUNT_CONTACTSLIST = "ContactsList";
    public static String ADD_CONNECTOR = "ContactsCreate";
    public static String UPDATE_CONNECTOR = "ContactsUpdate";
    public static String DELETE_CONNECTOR = "ContactsUpdate";
    public static String SET_DEFAULT_CONNECTOR = "ContactsDefaultUpdate";
    /******************************************************************************
     ******************************美食模块*****************************************
     *******************************************************************************/
    /**
     * 美食推荐主题
     */
    public static String RECOMMEND_FOOD_THEME_LIST_URL = "FoodThemeList";
    /**
     * 主题美食列表
     */
    public static String THEME_FOOD_LIST_URL = "ThemeFoodList";

    /******************************************************************************
     ******************************酒店模块*****************************************
     *******************************************************************************/
    /**
     * 银行优惠活动查询接口
     */
    public static String QUERY_BANK_ACTIVITY_LIST = "QueryBankActivityList";
    /**
     * 银行优惠活动详情接口
     */
    public static String QUERY_GOODS_BANK_ACTIVITY_HTTP = "QueryGoodsBankActivity";
    /**
     * 银行分期活动查询接口
     */
    public static String QUERY_BANK_STAGE_LIST = "QueryBankStageList";
    /**
     * 查询产品的生活分期数据
     */
    public static String QUERY_BANKSTAGEINFO_HTTP = "QueryBankStageInfo";
    /**
     * 可领取优惠券列表
     */
    public static String APP_CANRECEIVECOUPON_LIST = "CanReceiveCouponList";
    /**
     * 可使用代金券列表
     */
    public static String APP_CANUSECASHCOUPONLIST_LIST = "CanUseCashCouponList";
    /**
     * 创建代金券订单
     */
    public static String APP_CREATECOUPONORDER_IF ="CreateCashCouponOrder";
    /**
     * 活动抵扣查询
     */
    public static String APP_ACTIVITYDEDUCTIONINFO_IF ="ActivityDeductionInfo";

    /**
     * 更新代金券付款详情
     */
    public static String APP_UPDATECOUPONPAYDETAIL_IF ="UpdateCashCouponPayDetail";
    /**
     * 删除代金券订单
     */
    public static String APP_DELETECOUONORDER_IF = "DeleteCashCouponOrder";

    /**
     * 活动抵扣检测接口
     */
    public static String APP_ACTIVITYDEDUCTIONCHECK_HTTP = "ActivityDeductionCheck";
    /**
     * 活动抵扣金额
     */
    public static String APP_ACTIVITYDEDUCTIONAMOUNT_HTTP="ActivityDeductionAmount";
    /**
     * 过滤项列表
     */
    public static String  HOTEL_FILTER_LIST = "FilterList";
    /**
     * 酒店关键字搜索
     */
    public static String  HOTEL_KEYWORD_SEARCH = "HotelKeywordSearch";
    /**
     * 合作银行列表接口
     */
    public static String QUERYCOOPERATIVEBANK_LIST = "QueryCooperativeBank";

    public  static String QUERY_HOTEL_TEABAR_LIST = "QueryHotelTeabarList";

    public static String QUERY_HOTEL_PACKAGE = "QueryHotelPackage";

    public static String QUERY_HOTEL_GYM_LIST = "QueryHotelGymList";

    public  static String QUERY_HOT_CITY = "QueryHotCity";

    public static String HOTADDRSTATISTICS ="HotAddrStatistics";
    /**
     * 查询超值加购列表
     */
    public static String APP_QUERYGOODSPLUS = "QueryGoodsPlus";
    /**
     * 查询房间套餐剩余数量
     */
    public static String APP_SELECTROOMSPOLICYSQUANTITY ="SelectRoomsPolicysQuantity";
    /**
     * 查询房间保留时间
     */
    public static String APP_QUERYROOMSRETENTIONTIME ="QueryRoomsRetentionTime";
    /**
     * 酒店推荐主题接口
     */
    public static String RECOMMEND_THEME_LIST_URL = "QueryHotelTheme";
    /**
     * 主题酒店列表接口
     */
    public static String THEME_HOTEL_LIST_URL = "QueryThemeHotel";

    /**
     * 获取酒店详情图片接口
     */
    public static String HOTEL_IMAGES_LIST_URL = "QueryHotelPhoto";
    /**
     * 查询房间套餐价格列表
     */
    public static String HOTEL_QUERYROOMSPOLICYSPRICE ="QueryRoomsPolicysPrice";
    /**
     * 酒店下单
     */
    public static String hotel_hotelordercreate = "HotelOrderCreate";
    /**
     * 酒店其他商品下单
     */
    public static  String hotel_HotelOrderCreateOtherGoods = "HotelOrderCreateOtherGoods";
    /**
     * 查询酒店地图列表（）
     */
    public static String hotel_queryhotelmaplist = "QueryHotelMapList";
    //可领取红包列表；
    public static String card_canreceivebonuslist = "CanReceiveBonusList";
    /**
     * /**
     * 查询酒店餐厅列表
     */
    public static String QUERY_HOTEL_RESTAURANT_URL = "QueryHotelRestaurant";
    /**
     * 查询餐厅套餐
     */
    public static String QUERY_RESTAURANT_POLICYS_URL = "QueryRestaurantPolicys";
    /**
     * 查询套餐菜单
     */
    public static String QUERY_POLICYS_MENU_URL = "QueryPolicysMenu";
    /**
     * 查询酒店酒吧列表
     */
    public static String QUERY_HOTEL_BAR_URL = "QueryHotelBar";
    /**
     * 查询酒吧套餐
     */
    public static String QUERY_BAR_POLICYS_URL = "QueryBarPolicys";
    /**
     * 查询酒吧菜单
     */
    public static String QUERYBAR_MENU_URL = "QueryBarMenu";
    /**
     * 查询酒店会场列表
     */
    public static String QUERY_HOTEL_HALL_URL = "QueryHotelHall";
    /**
     * 查询酒店运动列表
     */
    public static String QUERY_HOTEL_SPORTS_URL = "QueryHotelSports";
    /**
     * 查询运动套餐
     */
    public static String QUERY_SPORTS_POLICYS_URL = "QuerySportsPolicys";
    /**
     * 住店须知
     */
    public static String SELECT_CHECK_NOTICE = "SelectCheckInNotice";
    /**
     * 卡权益下单
     */
    public static String RIGHTSORDERCREATE_URL = "RightsOrderCreate";
    /**
     * 限时抢购下单
     */
    public static String FLASHSALEORDERCREATE_URL ="FlashsaleOrderCreate";


    /******************************************************************************
     ******************************积分模块*****************************************
     *******************************************************************************/
    /**
     * 账户概要接口
     */
    public static String INTEGRAL_ACCOUNTSUMMARY = "AccountSummary";

    public static String INTEGRAL_FREQUENTPASSENGERPLAN = "FrequentPassengerPlan";

    public static String INTEGRAL_POINTSHARE = "PointShare";

    public static String INTEGRAL_USERCOUPONLIST = "UserCouponList";

    public static String INTEGRAL_COLLECTIONSLIST = "CollectionsList";

    public static String INTEGRAL_POINTEXCHANGEPLAN = "PointExchangePlan";

    public static String INTEGRAL_POINTEXCHANGE = "PointExchange";

    public static String INTEGRAL_AIRLINECOMPANYLIST = "AirlineCompanyList";

    public static String INTEGRAL_CONTACTSLIST = "ContactsList";

    public static String INTEGRAL_CONTACTSCREATE = "ContactsCreate";

    public static String INTEGRAL_REMINDLIST = "RemindList";


    public static String INTEGRAL_DELETEREMIND = "RemindDelete";


    /******************************************************************************
     ******************************生活模块*****************************************
     *******************************************************************************/


    /******************************************************************************
     ******************************用户模块*****************************************
     *******************************************************************************/

    /****************************用户************************************************/
    /**
     * 登录接口
     */
    public static String USER_LOGIN = "Login";
    /**
     * 注册接口
     */
    public static String USER_REGISTER = "Regist";
    /**
     * 登出接口
     */
    public static String USER_LOGOUT = "Logout";

    /**
     * 查询平台账户
     */
    public static String USER_QUERYPLATFORMACCOUNT = "QueryPlatformAccount";

    /******************************************************************************
     ******************************旅游模块*****************************************
     *******************************************************************************/
    /**
     * 旅游提交订单
     */
    public static String card_app_travelordercreate = "TravelOrderCreate";
    /**
     * 旅游产品详情
     */
    public static String card_app_selecttravelproductinfo = "SelectTravelProductInfo";
    /**
     * 旅游目的地列表和搜索；
     */
    public static String card_app_queryregioncity = "QueryRegionCity";
    /**
     * 旅游线路价格列表
     */
    public static String card_app_querytravellineamount = "QueryTravelLineAmount";
    /**
     * 旅游线路价格列表
     */
    public static String card_app_querytravelplanplace = "QueryTravelPlanPlace";
    /**
     * 查询旅游线路
     */
    public static String card_app_querytravelline = "QueryTravelLine";
    /**
     * 查询旅游线路出发日期；
     */
    public static String card_app_querylinedepdate = "QueryLineDepDate";
    /**
     * 旅游--查询节日列表
     */
    public static String TRAVELQUERYFESTIVAL2 = "QueryFestival2";

    public static String TRAVEL_SALE_TITLE = "QuerySalesTheme";  //特卖会主题

    public static String TRAVEL_SALE_LIST = "QuerySalesGoods"; //特卖会产品

    public static String TRAVEL_FXB_LIST = "QueryTravelDrogue";//旅游风向标

    public static String TRAVEL_XINGCHEN = "QueryTravelSchedule";//旅游行程概要

    public static String QUERY_TRAVEL_BASEINFO_URL = "QueryTravelBaseInfo";//旅游基本信息

    public static String QUERY_TRAVEL_PRODUCT_URL = "QueryTravelProduct";//查询旅游产品列表
    /******************************************************************************
     ******************************奢侈品模块****************************************
     *******************************************************************************/


    /******************************************************************************
     ******************************机票模块****************************************
     *******************************************************************************/
    /**
     * 机票详情查询（）
     */
    public static String transport_flightamount = "FlightAmount";
    /**
     * 机票详情查询，即机票代理商列表；
     */
    public static String transport_airfarequeryinfo = "AirfareQueryInfo";
    /**
     * (机票购票)
     */
    public static String transport_flightbooking = "FlightBooking";
    //航班列表未登录时；
    public static String card_app_airfarequerylistnotlogin = "AirfareQueryListNotLogin";
    // 退改签原因
    public static String card_app_queryOperateReason = "QueryOperateReason";
    //邮费列表；
    public static String card_app_querypostage = "QueryPostage";
    //退改签规则
    public static String card_app_queryexitmodificationinfo = "QueryExitModificationInfo";
    //保险列表（new）
    public static String card_app_queryinsurancetype = "QueryInsuranceType";
    //查询当前日期(前一天,后一天)票价最低金额
    public static String card_app_query_air_leastamount = "MonthlyFareInfor";
    //机票订单生成
    public static String card_app_flightorder_create = "FlightOrderCreate";
    //查询客户优惠券列表
    public static String card_app_querycustcoupon = "QueryCustCoupon";


    //退票查询（）
    public static String card_app_bouncquery = "BouncQuery";
    //改签确认
    public static String card_app_rebook = "ReBook";
    //查询可改签的订单详细接口（）
    public static String card_app_upgradeorderreq = "UpgradeOrderReq";
    //改签后需要支付接口（）
    public static String card_app_repay = "RePay";
    //确认退票（）
    public static String card_app_bouncrefund = "BouncRefund";
    //修改关注
    public static String card_app_followModify = "FollowModify";
    // 发现单程低价
    public static String card_app_find_low_price = "FloorPrice";

    // 发现往返程低价
    public static String card_app_find_low_round_price = "ReturnfloorPrice";
    //查询舱位
    public static String card_app_select_cangwei = "FlightQabin";

    //单程机票查询；
    public static String card_app_queryonwayticket = "AirfareQueryList";

    public static final String card_selecth5page = "SelectH5Page";

    public static final String QUERY_CREDIT_ACTIVITY_LIST = "QueryCreditActivityList";

    /**
     * 国际城市查询
     */
    public static String QUREY_BASE = "QueryBaseCity";

    /******************************************************************************
     ******************************电影模块****************************************
     *******************************************************************************/


    /**************************自驾租车*******************************************/

    /*************************陆上交通**********************************************/
    /**
     * 火车票列表；
     */
    public static String transport_querytrainticket = "QueryTrainTicket";
    /**
     *
     */
    public static String transport_querybusmerchant = "QueryBusMerchant";

    //查询商品活动
    public static String transport_querygoodsactivity = "QueryGoodsActivity";

    public static String ticket_filter = "ScreenQuery";

    public static String ticket_follow_query_star_end = "FollowQuerydeparrCity";

    /**
     *
     */
    public static String transport_queryshiplist = "QueryShipList";

    /**
     *
     */
    public static String transport_buslinelist = "BusLineList";
    /**
     * 火车票座位列表
     */
    public static String transport_queryseatlist = "QuerySeatList";
    /**
     * 船票座位列表
     */
    public static String transport_queryshipseatlist = "QueryShipSeatList";
    /**
     * 航线列表
     */
    public static String transport_queryline = "QueryLine";
/***********************************************************************/

    /**
     * 附近租车门店列表
     */
    public static String RENTAL_CAR_STORE_LIST_URL = "RentalCarStoreList";
    /**
     * 车型列表接口
     */
    public static String CAR_TYPE_LIST_URL = "CarTypeList";
    /*************************专车出行**********************************************/
    /**
     * 车型列表接口
     */
    public static String SPECIAL_CAR_TYPE_URL = "CarTypeList";

    /******************************************************************************
     * *****************************我的*******************************************
     *******************************************************************************/

    // 优惠推荐接口
    public static String card_app_discount_recommend = "CouponRecommend";
    /**
     * 礼品卡推荐列表
     */
    public static String CARD_APP_QUERYGIFTCARDRECOMMEND ="QueryGiftcardRecommend";

    /**
     * 我的优惠券
     */
    public static  String CARD_APP_QUERYCUSTCOUPON ="QueryCustCoupon";
    /**
     * 我的代金券
     */
    public static  String CARD_APP_QUERYCUSTCASHCOUPON ="QueryCustCashCoupon";
    //
    // 商户优惠首页优惠券接口
    public static String card_app_queryCoupon = "CouponBatchList";

    public static String card_api_identifyingCode = "IdentifyingCode";

    public static String card_api_provingMobile = "ProvingMobile";

    public static String card_api_provingIdCode = "ProvingIdCode";

    //筛选（）
    public static String card_app_shopfilterquery = "ShopFilterQuery";

    public static String card_app_personInfo = "PersonInfo";

    public static String card_app_updatePerson = "UpdatePerson";

    public static String card_app_queryCreditBill = "QueryCreditCardList";

    public static String CARD_APP_CREDIT_BILL_DETAIL = "CreditBillDetail";

    public static String card_app_plane_followQuery = "FollowQuery"; //查询关注详情

    public static String card_app_plane_followQyeryList = "FollowQueryList";//查询关注列表
    /**
     * 创建信用卡
     */
    public static String CREATE_CREDIT_CARD = "CreateCreditCard";
    /**
     * 创建储蓄卡
     */
    public static String CREATE_DEBIT_CARD = "CreateDebitCard";//
    /**
     * 快捷支付接口
     */
    public static String QUICK_PAY_CARD = "QuickPay";

    /**
     * 创建基础订单细心你
     */
    public static String CREATE_BASE_ORDERS = "CreateBaseOrders";
    /**
     * 快速授权
     */
    public static String QUICK_PREAUTH = "QuickPreAuth";
    /**
     * 发卡方识别接口
     */
    public static String QUERY_CARDBIN = "QueryCardBin";
    //附近条件；
    public static String card_app_nearcondition = "NearCondition";
    //我的银行；
    public static String card_app_mybanks = "MyBanks";
    //我的银行+全部银行（）
    public static String card_app_banklist = "BankList";
    //银行详情；
    public static String card_app_bank_detail = "QueryBankContactMethod";
    // 门店活动银行列表）；
    public static String card_app_bankofactivity = "BankOfActivity";
    /**
     * 全部银行
     */
    public static String card_app_allbanks = "AllBanks";
    /**
     * 查询账户信息
     */
    public static String card_selectaccountinfo = "SelectAccountInfo";
    /**
     * 删除评论
     */
    public static String card_delete_vote = "DeleteVote";
    /**
     * 获取口令；
     */
    public static String card_getpassword = "GetPassword";
    /**
     * 通过key礼品卡信息；
     */
    public static String card_getgiftkeyinfo = "GetGiftkeyInfo";
    /**
     * 查询优惠筛选列表
     */
    public static String card_queryactivityscreen = "QueryActivityScreen";
    /**
     * 查询礼品卡分类列表
     */
    public static String card_querygiftcardtype = "QueryGiftcardType";
    /**
     * 查询礼品卡产品列表
     */
    public static String card_querygiftcardproduct = "QueryGiftcardProduct";
    /**
     * 礼品卡订单创建
     */
    public static String card_giftcardordercreate = "GiftcardOrderCreate";
    /**
     * 查询未领取礼品卡
     */
    public static String card_queryreceivegiftcard = "QueryUnReceiveGiftcard";
    /**
     * 实体卡订单创建
     */
    public static String card_entitycardordercreate = "EntitycardOrderCreate";
    /**
     * 电子卡订单创建
     */
    public static String card_ecardordercreate = "EcardOrderCreate";

    /**
     * 领取心意卡
     */
    public static String card_receivemindcard = "ReceiveMindcard";
    /**
     * 查询礼品卡领取记录
     */
    public static String GIFTCARD_RECORSEND = "QueryGiftcardReceiveRecord";
    /**
     * 实体卡充值
     */
    public static String card_giftcardrecharge = "GiftcardRecharge";
    /**
     * 发送心意卡
     */
    public static String card_sendmindcard = "SendMindcard";
    /**
     * 查询红包主题列表
     */
    public static String card_bonusthemelist = "BonusThemeList";
    /**
     * 查询交易对象
     */
    public static String card_querytradeobject = "QueryTradeObject";
    /**
     * 发送红包
     */
    public static String card_sendbonus = "SendBonus";
    /**
     * 查询未领取红包列表
     */
    public static String card_queryreceivebonus = "QueryUnReceiveBonus";
    /**
     * 领取优惠券
     */
    public static String card_app_receivecoupon = "ReceiveCoupon";
    /**
     * 可使用优惠券列表
     */
    public static String card_app_canusecouponlist = "CanUseCouponList";
    /**
     * 领取红包
     */
    public static String card_app_receivebonus = "ReceiveBonus";
    /**
     * 创建红包订单
     */
    public static String card_createbonusorder = "CreateBonusOrder";
    /**
     * 修改酒店关键字搜索次数
     */
    public static String card_updatehotnamesize = "UpdateHotNameSize";
    /**
     * 查询收到红包记录
     */
    public static String RECEIVE_REDBAG = "QueryReceivedBonus";
    /**
     * 查询红包领取记录
     */
    public static String REC_OR_SEND_DETAIL = "QueryBonusRecord";
    /**
     * 我的发送红包
     */
    public static String QueryBonusOrder = "QueryBonusOrder";
    /**
     * 验证用户名
     */
    public static String ProvingAccount = "ProvingAccount";
    /**
     * 创建交易订单
     */
    public static String CreateTradeOrder = "CreateTradeOrder";
    /**
     * 商家积分查询
     */
    public static String QueryShopPointsList = "QueryShopPointsList";
    /**
     * 所有明细接口
     */
    public static String QueryPaymentsDetailed = "QueryPaymentsDetailed";
    /**
     * 所有优惠券
     */
    public static String query_all_coupon = "QueryAllCoupon";
    /**
     * 查询代金券列表
     */
    public static String QUERY_ALL_CASHCOUPON = "QueryAllCashCoupon";
    /**
     * 查询优惠券推荐列表
     */
    public static String QUERY_RECOMMOND_URL ="QueryCouponRecommend";
    /**
     * 查询代金券推荐列表
     */
    public static String QUERY_CASHCOUPONRECOMMEND_URL ="QueryCashCouponRecommend";
    /**
     * 查询个人账单信息
     */
    public static String SelectAccountInfo = "SelectAccountInfo";
    /**
     * 消息列表(三大类型)
     */
    public static String QueryNewsList = "QueryNewsList";
    /**
     * 消息列表(详情)
     */
    public static String QuerySubNewsList = "QuerySubNewsList";
    /**
     * 消息列表(状态)
     */
    public static String UpdateSubNewsSturt = "UpdateSubNewsSturt";
    /**
     * Jpush推送串口列表
     */
    public static String GetActivityCommon = "GetActivityCommon";
    public static String get_bouns_keyinfo = "GetBounskeyInfo";

    /******************************************************************************
     ******************************酒店模块***************************************
     *******************************************************************************/
    //美食/奢侈品主页门店列表；
    public static String card_app_storelist = "ShopStoreQuery";

    //美食列表筛选条件中的 "分类条件"
    public static String card_app_TypeCondition = "TypeCondition";
    //商家详情；
    public static String card_app_storeinfo = "StoreInfo";
    //店铺详情；
    public static String card_app_storedetail = "StoreDetail";
    //店铺商品列表；
    public static String card_app_storegoodslist = "StoreGoodsList";
    //商户列表；
    public static String card_app_shoplist = "ShopList";
    //酒店套餐
    public static String CARD_APP_HOTELROOMPOLICYS = "QueryRoomsPolicys";
    //酒店详情
    public static String CARD_APP_HOTELINFODETAILS = "SelectHotelInfo";
    //查询酒店服务信息
    public static String CARD_APP_SELECTHOTELSERVICEINFO = "SelectHotelServiceInfo";
    //查询房间列表
    public static String CARD_APP_HOTELROOMLIST = "QueryHotelRooms";
    /**
     * 查询酒店其他商品列表
     */
    public static String  CARD_APP_QUERYHOTELOTHERGOODS = "QueryHotelOtherGoods";
    /**
     * 查询酒店其他商品菜单
     */
    public static String QUERYHOTELOTHERGOODSMENU = "QueryHotelOtherGoodsMenu";
    /**
     * 查询酒店其他商品套餐菜单
     */
    public static String QUERYHOTELOTHERGOODSPOLICYSMENU = "QueryHotelOtherGoodsPolicysMenu";
    //查询酒店卡权益列表
    public static String CARD_APP_QUERYHOTELCARDRIGHTS = "QueryHotelCardRights";
    //查询酒店限时抢购列表
    public static String CARD_QUERYHOTELFLASHSALE = "QueryHotelFlashsale";

    //查询酒店其他商品套餐
    public static String CARD_APP_QUERYHOTELOTHERGOODSPOLICYS="QueryHotelOtherGoodsPolicys";
    //查询搜索默认列表
    public static String card_app_querysearchdefault = "QuerySearchDefault";
    //查询搜索信息列表
    public static String card_app_searchlist = "SearchList";
    //查询搜索关键字；
    public static String card_app_queryhotnameinfo = "QueryHotNameInfo";
    //搜索优惠券
    public static String card_app_searchcoupon = "CouponKeywordsSearch";
    //搜索代金券
    public static String card_app_cashcouponkeywordssearch ="CashCouponKeywordsSearch";
    //获取酒店详情里的服务信息
    public static String HOTEL_QUERY_DETAIL = "QueryHotelDetail";
    //酒店列表；
    public static String card_app_hotel_list = "QueryHotelList";
    /**
     * 查询今日甩房列表
     */
    public static String card_app_QueryLeftHotelList = "QueryLeftHotelList";
    //查询卡权益列表
    public static String CARD_APP_QUERYRIGHTSLIST = "QueryRightsList";
    //查询限时抢购列表
    public static String CARD_APP_QUERYFLASHSALELIST = "QueryFlashsaleList";

    //添加收藏；
    public static String card_app_collections = "Collections";
    //优惠列表
    public static String card_app_activitybankquery = "ActivityBankQuery";
    //红包列表
    public static String card_app_bounsbatchquery = "BounsBatchQuery";
    //领取红包
    public static String card_app_addcustomerbouns = "AddCustomerBouns";
    /******************************************************************************
     ******************************评论模块***************************************
     *******************************************************************************/
    //评价列表；
    public static String CARD_APP_VOTELIST = "VoteList";
    //发布评论
    public static String CARD_APP_CREATEVOTE = "CreateVote";
    //关键字列表
    public static String CARD_APP_KEYWORDSLIST = "KeywordsList";
    //评价数量
    public static String CARD_APP_VOTECOUNT = "VoteCount";
    //添加点赞回复
    public static String CARD_APP_CREATELIKEREPLY = "CreateLikeReply";
    //添加评价回复
    public static String CARD_APP_CREATEVOTEREPLY = "CreateVoteReply";
    //评价回复列表
    public static String CARD_APP_VOTEREPLYLIST = "VoteReplyList";
    //评价详情
    public static String CARD_APP_VOTEINFO = "VoteInfo";
    /**
     * 电影
     */
    public static String card_app_query_film_list = "QueryFilmList";//榜单
    public static String card_app_query_film = "QueryFilm";//影片列表
    public static String card_app_query_film_events = "QueryFlimEvents";//影片场次列表
    //电影指南
    public static String card_app_querymovieguide = "QueryFilmGuide";
    //2、影片列表（）
    public static String card_app_queryfilm = "QueryFilm";
    //3、影片明细（）
    public static String card_app_queryfilminfo = "QueryFilmInfo";

    // 特惠付款-->积分列表；
    public static String card_app_card_integral_list = "CardIntegralList";
    //活动抵扣金额
    public static String card_app_activitydeductionamount = "ActivityDeductionAmount";
    //9、更新订单状态（）
    public static String card_app_updateorderstatus = "UpdateOrderStatus";

    /*=================设置 start=====================*/
    /**
     * 获取银行的bin码；
     */
    public static String card_api_selectcreditbin = "SelectCreditBin";
    /**
     * 服务器付款方式
     */
    public static String CARD_HILIFE_APPPAYMETHOD = "AppPayMethod";

    /**
     * 查询用户的付款方式，可查询到银行的分期信息、优惠信息
     */
    public static String CARD_HILIFE_APPCARDLIST = "AppCardList";

    /**
     * 付款方式排序
     */
    public static String CARD_HILIFE_APPQUERYPAYMENT = "AppQueryPayment";
    /**
     * 创建码尚付订单
     */
    public static String CARD_HILIFE_CREATEFASTPAYMENTORDER = "CreateFastPaymentOrder";
    /**
     * 扫码付转账接口
     */
    public static String CARD_HILIFE_APPTRANSFERFORFASTPAYMENT = "AppTransferForFastpayment";
    /**
     * 他行发送银行校验码
     */
    public static String CARD_HILIFE_APPQUICKPRECONSUME = "AppQuickPreConsume";
    /**
     * 银行卡验证；
     */
    public static String card_api_appbankcardlist = "AppBankCardList";
    //常用旅客列表（CustomerGuestList）
    public static final String setting_customerguestlist = "CustomerGuestList";
    //新增常用旅客（CustomerGuestCreate）
    public static final String setting_customerguestcreate = "CustomerGuestCreate";
    //修改常用旅客（CustomerGuestUpdate）
    public static final String setting_customerguestupdate = "CustomerGuestUpdate";
    // 收货地址列表（CustomerAddressList）
    public static final String setting_customeraddresslist = "CustomerAddressList";
    //收货地址创建（CustomerAddressCreate）
    public static final String setting_customeraddresscreate = "CustomerAddressCreate";
    //收货地址修改（CustomerAddressUpdate）
    public static final String setting_customeraddressupdate = "CustomerAddressUpdate";
    // 发票抬头列表（InvoiceList）
    public static final String setting_invoicelist = "InvoiceList";
    // 发票抬头创建（InvoiceCreate）
    public static final String setting_invoicecreate = "InvoiceCreate";
    //发票抬头修改（InvoiceUpdate）
    public static final String setting_invoiceupdate = "InvoiceUpdate";
    //验证身份信息（ProvingCustomerInfo）
    public static final String setting_provingcustomerinfo = "ProvingCustomerInfo";
    //验证身份证和姓名
    public static final String setting_authentication = "Authentication";
    //重置登录密码（ResetPswd）
    public static final String setting_resetpswd = "ResetPswd";
    /*=================设置 end=====================*/

    //订单管理
    public static final String ORDER_LIST = "OrdersManageList"; //订单列表
    public static final String ORDER_TRAIN_DETAIL = "TrainTicketsDetail";//火车票详情
    public static final String ORDER_PLAIN_DETAIL = "PlaneTicketsDetail";//机票详情
    public static final String ORDER_HOTELS_DETAIL = "SelectHotelOrderInfo";//酒店订单详情(房间)
    public static final String ORDER_SELECTHOTELOTHERORDERINFO_DETAIL = "SelectHotelOtherOrderInfo";//酒店订单详情(其它)
    public static final String ORDER_SELECTRIGHTSORDERINFO = "SelectRightsOrderInfo";//卡权益
    public static final String ORDER_SELECTFLASHSALEORDERINFO ="SelectFlashsaleOrderInfo";//限时抢购
    public static final String ORDER_APPLYCASHCACK = "ApplyCashcack";//申请发现
    public static final String ORDER_REFUNDORDERCHECK = "RefundOrderCheck";//退款检测
    public static final String ORDER_REFUNDORDERCONFIRM = "RefundOrderConfirm";//退款确认
    public static final String ORDER_SHIP_DETAIL = "ShipTicketsDetail";//船票订单详情bouncQuery
    public static final String ORDER_BUS_DETAIL = "BusTicketsDetail";//汽车票订单详情
    public static final String ORDER_TOURISM_DETAIL = "SelectTravelOrderInfo";//旅游订单
    public static final String ORDER_SOONORDERSMANAGELIST = "SoonOrdersManageList";//待使用订单列表
    //
    public static final String ORDER_SUPPLEMENTORDERINVOICE = "SupplementOrderInvoice";//补开发票

    public static final String ORDER_HOTELORDERSSERVICES ="HotelOrdersServices";//酒店订单服务列表

    public static final String ORDER_ORDERSPAYFINISHINFO ="OrdersPayFinishInfo";//订单支付完成信息


}
