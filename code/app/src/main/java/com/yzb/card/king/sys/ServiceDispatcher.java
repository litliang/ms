package com.yzb.card.king.sys;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Toast;

import com.yzb.card.king.util.SharedPreferencesUtils;
import com.yzb.card.king.util.StorageUtil;
import com.yzb.card.king.util.ValidatorUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.tencent.open.utils.Global.getPackageName;

public class ServiceDispatcher {



    public final static String base_url_api_115 = "http://116.228.184.117:8082/";
    public final static String base_url_api_117 = "http://116.228.184.117:8082/";
    public static String base_url_api = "http://116.228.184.000:8082/";

    public static String ver;

    public static void change(Context ctx, String defaultver) {
        String target = "";
        String v;
        if (!TextUtils.isEmpty(defaultver)) {
            v = defaultver;
        } else {
            v = (String) new StorageUtil(StorageUtil.testpath).getKeyedV("ver");
        }
        if (TextUtils.isEmpty(v) || v.equals("115")) {
            target = "117";
        } else {
            target = "115";
        }
        new StorageUtil(StorageUtil.testpath).addKeyV("ver",target);
        reset(target);
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public static void init(Context ctx) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = ctx.getPackageManager()
                    .getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String v = new StorageUtil(StorageUtil.testpath).getKeyedV("ver");
        if (TextUtils.isEmpty(v)) {
            ver = appInfo.metaData.get("ver") + "";
            change(ctx, ver);
        } else {
            reset(v);
        }

    }

    public static void toastVer() {
        Toast.makeText(GlobalApp.getInstance().getPublicActivity(), base_url_api, Toast.LENGTH_SHORT).show();
    }

    public static void setBase_url_api(String base_url_api) {
        ServiceDispatcher.base_url_api = base_url_api;
    }

    public static void reset(String ver) {

        base_url_api = base_url_api.replaceAll("000", ver);

        com.yzb.wallet.sys.ServiceDispatcher.app_url_api  = "http://116.228.184."+ver+":8082/app/api/api/";
        url_api = base_url_api + "card/api/api/";
        url_refund_rule = base_url_api + "card/api/refundRule";
        url_passenger_invite = ServiceDispatcher.base_url_api + "card/api/customerGuestCreateH5";
        URL_CREATE_FAST_PAYMENT_ORDER = url_api + "createFastPaymentOrder?";
        URL_CREATE_FAST_PAYMENT_ORDER_BUT = base_url_api + "card/shop/api/api/createFastPaymentOrder?";
        URL_JINRONG_LIFE = ServiceDispatcher.base_url_api + "card/api/jrsh";
        URL_DYNAMIC_FLIGHT_DETAILS = base_url_api + "card/api/ticketH5?ticket=";//机票id
        url_image = "http://116.228.184.116/card/image/";
        base_update_path = base_url_api + "mail/";
    }

    //    public  static String base_url_api = "http://10.0.100.91:8082/";
    public static String url_api = base_url_api + "card/api/api/";

    public static String url_refund_rule = base_url_api + "card/api/refundRule"; //机票退改签规则url；

    /**
     * 旅客信息-邀请微信好友填写；
     */
    public static String url_passenger_invite = ServiceDispatcher.base_url_api + "card/api/customerGuestCreateH5";
    /**
     * 支付和收款的二维码
     */
    public static String URL_CREATE_FAST_PAYMENT_ORDER = url_api + "createFastPaymentOrder?";
    /**
     * 收款方二维码url
     */
    public static String URL_CREATE_FAST_PAYMENT_ORDER_BUT = base_url_api + "card/shop/api/api/createFastPaymentOrder?";
    /**
     * 金融生活
     */
    public static String URL_JINRONG_LIFE = ServiceDispatcher.base_url_api + "card/api/jrsh";
    /**
     * 动态航班机票详情分享链接
     */
    public static String URL_DYNAMIC_FLIGHT_DETAILS = base_url_api + "card/api/ticketH5?ticket=";//机票id

    public static String url_image = "http://116.228.184.116/card/image/";

    public static String base_update_path = base_url_api + "mail/";

    /**
     * 检测当前设备网络状况
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
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
    public static String getImageUrl(String imageCode) {
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