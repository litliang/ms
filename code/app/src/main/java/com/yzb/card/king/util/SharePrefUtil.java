package com.yzb.card.king.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gengqiyun on 2016/5/8.
 * 本地缓存工具类；
 */
public class SharePrefUtil
{
    private static String sp_name = "card_king";
    public static String SEARCH_HISTORY = "searchHistory";//搜索历史
    public static String SEARCH_TRAVEL_DESTINATION = "TRAVEL_DESTINATION_HISTORY";//旅游目的地搜索历史
    public static String SEARCH_COUPON = "SEARCH_COUPON";//优惠券搜索；
    public static String CURRENT_HISTORY ="current_history";//记录当前城市
    public static String USE_CITY_HISTORY_DATA = "use_city_history_data";//用户历史城市数据
    public static String HOTEL_SEARCH_RESULT_DATA ="hotel_search_result_data";//酒店搜索结果历史数据

    public static String HOTEL_ROOM_PASSENGERINFO_DATA = "hotel_room_passengerInfo_data";//酒店房间入住人标记

    public static String HOTEL_ROOM_LINKMAN_PHONE = "hotel_room_linkman_phone";//酒店房间联系人标记

    public static String HOTEL_OTHER_PRODUCT_LINKMAN_PHONE = "hotel_other_product_linkman_phone";//酒店其它产品联系人标记

    public static String TICKET_SINGLE_FLIGHT_INFO = "ticket_single_flight_info";//记录用户单程航班信息

    public static String TICKET_ROUND_LINE_INFO = "ticket_round_line_info";//记录用户往返程航班信息

    public static String TICKET_MULTI_LINE_INFO = "ticket_multi_line_info";//记录用户往返程航班信息

    /**
     * 保存值到本地；
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveToSp(Context context, String key, String value)
    {
        SharedPreferences sp = getSp(context);
        if (sp != null)
        {
            sp.edit().putString(key, value).commit();
        }
    }

    /**
     * 保存值到本地；
     *
     * @param context
     * @param key
     */
    public static void removeKeyFromSp(Context context, String key)
    {
        SharedPreferences sp = getSp(context);
        if (sp != null)
        {
            sp.edit().remove(key).commit();
        }
    }

    /**
     * 读取本地缓存工具类；
     *
     * @param context
     * @param key
     * @param defaultValue 子分类json格式
     *                     [{"id":11,"typeName":"徽菜","parentId":1,"typeImage":"2016042616243116040403","leafNote":"1"},
     *                     {"id":16,"typeName":"小吃快餐","parentId":1,"typeImage":"2016042616243116040403","leafNote":"1"}]}
     */
    public static String getValueFromSp(Context context, String key, String defaultValue)
    {
        SharedPreferences sp = getSp(context);
        if (sp != null)
        {
            return sp.getString(key, defaultValue);
        }
        return "";
    }

    private static SharedPreferences getSp(Context context)
    {
        if (context != null)
        {
            return context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        }
        return null;
    }


}
