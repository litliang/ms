package com.yzb.card.king.sys;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yzb.card.king.util.ValidatorUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ServiceDispatcher {


    public final static String base_url_api = "http://116.228.184.115:8080/";
//   public final static String base_url_api = "http://116.228.184.115:8080/";
//    public final static String base_url_api = "http://10.0.100.91:8080/";
    public final static String url_api = base_url_api + "card/api/api/";

    public final static String url_refund_rule = base_url_api + "card/api/refundRule"; //机票退改签规则url；

    /**
     * 旅客信息-邀请微信好友填写；
     */
    public static String url_passenger_invite = ServiceDispatcher.base_url_api + "card/api/customerGuestCreateH5";
    /**
     * 支付和收款的二维码
     */
    public final static String URL_CREATE_FAST_PAYMENT_ORDER = url_api + "createFastPaymentOrder?";
    /**
     * 收款方二维码url
     */
    public final static String URL_CREATE_FAST_PAYMENT_ORDER_BUT = base_url_api + "card/shop/api/api/createFastPaymentOrder?";
    /**
     * 金融生活
     */
    public final static String URL_JINRONG_LIFE = ServiceDispatcher.base_url_api + "card/api/jrsh";
    /**
     * 动态航班机票详情分享链接
     */
    public final static String URL_DYNAMIC_FLIGHT_DETAILS = base_url_api + "card/api/ticketH5?ticket=";//机票id

    public final static String url_image = "http://116.228.184.116/card/image/";

    public final static String base_update_path = base_url_api + "mail/";

    /**
     * 检测当前设备网络状况
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context)
    {
        boolean result = false;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                result = mNetworkInfo.isAvailable();
            }
        }
        return result;
    }

    /**
     * @param imageCode
     * @return
     */
    public static String getImageUrl(String imageCode)
    {
        if (ValidatorUtil.isUrl(imageCode)) {
            return imageCode;
        } else {

            int index = imageCode.indexOf(url_image);

            if (index == -1) {
                return url_image + "getImg/" + imageCode + "/0";
            } else {
                return imageCode;
            }
        }
    }
}