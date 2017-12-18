package com.yzb.wallet.sys;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ServiceDispatcher {

    //public final static String url_api = "http://116.228.184.116/card/api/api/";
    //public final static String url_api = "http://116.228.184.117/card/api/api/";
    //public final static String url_api = "http://116.228.184.115:8082/card/api/api/";
    public final static String url_api = "http://116.228.184.115/card/api/api/";
    //public final static String url_api = "http://192.168.1.106:8082/card/api/api/";

    //public final static String app_url_api = "http://116.228.184.117:8082/app/api/api/";
    public static String app_url_api = "http://116.228.184.117:8082/app/api/api/";
    //public final static String app_url_api = "http://116.228.184.117/app/api/api/";
    //public final static String app_url_api = "http://116.228.184.115:8082/app/api/api/";
    //public final static String app_url_api = "http://116.228.184.115/app/api/api/";
    //public final static String app_url_api = "http://192.168.31.246:8082/app/api/api/";

    public final static String url_image = "http://116.228.184.116/card/image/";
    //public final static String url_image = "http://116.228.184.117:8082/card/image/";
    //public final static String url_image = "http://192.168.1.99:8082/card/image/";

    public static Map<String, String> call(Map<String, String> parameters) {
        try {
            return HttpClientUtil.postJsonBody(url_api, parameters, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> call(Context context, Map<String, String> parameters) {
        Map<String, String> result = new HashMap<String, String>();
        if (!isNetworkConnected(context)) {
            result.put("code", "9999");
            result.put("error", "网络异常");
            return result;
        }

        try {
            result = HttpClientUtil.requestByPost(url_api, parameters, Map.class);
            if (null == result || result.isEmpty()) {
                result = new HashMap<String, String>();
                result.put("code", "9999");
                result.put("error", "请求失败，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> callApp(Context context, Map<String, String> parameters) {
        Map<String, String> result = new HashMap<String, String>();
        if (!isNetworkConnected(context)) {
            result.put("code", "9999");
            result.put("error", "网络异常");
            return result;
        }

        try {
            result = HttpClientUtil.requestByPost(app_url_api, parameters, Map.class);
            if (null == result || result.isEmpty()) {
                result = new HashMap<String, String>();
                result.put("code", "9999");
                result.put("error", "请求失败，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> setParams(String sessionId, String serviceName, String identification, String data) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("sessionId", sessionId);
        parameters.put("serviceName", serviceName);
        parameters.put("identification", identification);
        parameters.put("data", data);

        return parameters;
    }

    public static Map<String, String> setParams(String serviceName, String data) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("serviceName", serviceName);
        parameters.put("data", data);

        return parameters;
    }

    public static Map<String, String> callImage(Context context, Map<String, String> parameters) {
        Map<String, String> result = new HashMap<String, String>();
        if (!isNetworkConnected(context)) {
            result.put("code", "9999");
            result.put("error", "网络异常");
            return result;
        }
        try {
            result = HttpClientUtil.requestByPost(url_image + "getImage", parameters, Map.class);
            if (null == result || result.isEmpty()) {
                result = new HashMap<String, String>();
                result.put("code", "9999");
                result.put("error", "请求失败，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static InputStream callImage(String code) {
        try {
            return HttpClientUtil.invokeGet(url_image + "getImg/" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> uploadImage(Map<String, String> parameters) {
        try {
            return HttpClientUtil.postJsonBody(url_image + "saveImage", parameters, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前的网络状态  -1：没有网络  1：WIFI网络 2：wap网络 3：net网络
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }
}