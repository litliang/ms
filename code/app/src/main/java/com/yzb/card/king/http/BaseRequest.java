package com.yzb.card.king.http;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.encryption.RsaUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 类  名：app所有请求基类
 * 作  者：Li Yubing
 * 日  期：2016/8/5
 * 描  述：
 */
public abstract class BaseRequest {

    //上传图片的服务器地址
    public final String upload_image_url = ServiceDispatcher.url_image + "saveImage";

    //app开发服务器
    public final String url_api = ServiceDispatcher.url_api;
    //app测试服务器
    //public final static String url_api = "http://116.228.184.116/card/api/api/";
    //java开发服务器
    // public final static String url_api_1 = "http://192.168.100.97:80821/card/api/api/";
    private String serviceName = "";

    private String charName = "ISO-8859-1";

    private boolean ifSaveSessionId = true;
    /**
     * 是否是加密请求
     */
    private boolean ifEncryption = false;

    static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALE3/AxHcbI67afCbVJOwzCTEfZAU+Gxn7rBOhXrEVKbujuuTwTDPJ//s0UjitzlmgHfUd2TIqaZDvPANXZZmPJBUtXjh+unMoq8VlcH+dGl5dZyZgwxnmYTSgsEFgH4xQdKbGzUNPFxmzY9jxWN7xpfXUkP+TFIjq2dw2UroFhZAgMBAAECgYACMAe7exJLOfD/FiZVUsWUDuy01nxyl8e5/XKjHKC9HVECNZeiHmrcKsLiwpqPOYJaLZCMJKnT1qXZmnDOgjKiHanomA/seWFVvlbofxVgTI4Np4oBXxX1AnXK6tTovHhvoc6ynKYIG8rYn6tVloNk4K0cWWvyEhAk/eo/Lt8jVQJBAOGEsFA943dh24qnT8IrxU6VDDsZoHRn9X6j121wjIH2pyTqLz/WvH3DW6xjTpnX5CVbb0lBzleNga8Y8LbuShcCQQDJLAxE9vR4qCQQQFOJkDiqHILUXEVryJraAx2OeUtB5/mK+03sxH5sGs6yGBOUiA6mO8BK5R1SZmpAjFyvc6cPAkB+BPW5gTvw5EAYNPJ+4JK7HNLe260sH0Ox5sBlKXV60mgIWszYcZiW9mnt5PhxQ5D7xyJi32D8z9heDiPFNVc1AkA/tzDHowP5Nx/8+bK7ri8USeVyByuBoM7S4Au7dVVNsYBK8Z9Tr0RvTupKY+/HqnQhWGlmDz0DPSbH/OzaMSInAkAGiKvpz5Or1qx3JPIKSFERKXVKNgSnhiSgTVzpECAlw8Djrw0LrVYvSBrKIBvoL9Xl6x1KVcWVXGhhcJEb/p6m";

    static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxN/wMR3GyOu2nwm1STsMwkxH2QFPhsZ+6wToV6xFSm7o7rk8Ewzyf/7NFI4rc5ZoB31HdkyKmmQ7zwDV2WZjyQVLV44frpzKKvFZXB/nRpeXWcmYMMZ5mE0oLBBYB+MUHSmxs1DTxcZs2PY8Vje8aX11JD/kxSI6tncNlK6BYWQIDAQAB";


    /**
     * 设置app的统一入参
     *
     * @param sessionId      用户的sessionId
     * @param serviceName    请求接口名
     * @param identification app的UUID
     * @param dataMap        接口的请求参数
     * @return
     */
    public Map<String, String> setParams(String sessionId, String serviceName, String identification, Map<String, Object> dataMap) {
        this.serviceName = serviceName;
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("sessionId", sessionId);
        parameters.put("serviceName", serviceName);
        parameters.put("identification", identification);

        if (ifEncryption) {
            try {
                String adb = JSON.toJSONString(dataMap);

                LogUtil.e("AAAA", "--b--明文--" + adb);


                byte[] abb = RsaUtil.encryptByPublicKey(adb.getBytes(charName), publicKey);

                LogUtil.e("AAAA", "---b--密文--size=" + abb.length);

                StringBuffer sbb = new StringBuffer();

                for (byte bb : abb) {

                    sbb.append(bb).append(",");
                }
                System.out.println("---b---=" + sbb.toString());

                String data = new String(abb, charName);

                LogUtil.e("AAAA", "---b--密文--" + data);

                parameters.put("data", data);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            parameters.put("data", JSON.toJSONString(dataMap));
        }

        parameters.put("merchantNo", "123456");//商户号
        String sign = getMD5(JSON.toJSONString(dataMap).toString());//对请求参数进行签名
        parameters.put("sign", sign);

        return parameters;
    }

    public void isIfSaveSessionId(boolean ifSaveSessionId) {

        this.ifSaveSessionId = ifSaveSessionId;
    }

    public void setIfEncryption(boolean ifEncryption) {
        this.ifEncryption = ifEncryption;
    }

    protected Listener listener;


    public static abstract class Listener {


        public void onSuccess(String data) {

        }

        /**
         * 带结果码的回调；
         *
         * @param data
         * @param resultCode 结果码；
         */
        public void onSuccess(String data, String resultCode) {

        }

        public void onResult(String result) {

        }

        public void onFail(String failMsg) {

        }

        public void onFail(String failMsg, String resultCode) {

        }

        public void onToast(String success, Map<String, String> parameterMap, String data) {
        }
    }


    /**
     * 获取超时时间；
     */
    protected int getConnectTimeOut() {
        return 20 * 1000;
    }

    /**
     * 发送Post请求
     *
     * @param parameterMap
     * @param callBack
     */
    public void sendPostRequest(final Map<String, String> parameterMap, final HttpCallBackData callBack) {
        if (callBack != null) {
            callBack.onStart();
        }

        RequestParams params = new RequestParams(url_api);

        params.setConnectTimeout(getConnectTimeOut());

        setParamsHeader(params);

        try {
            String requestStr = JSON.toJSONString(parameterMap);
            LogUtil.e("------请求参数--------------" + requestStr);
            params.setBodyContent(requestStr);
        } catch (Exception e) {
            e.printStackTrace();

            if (callBack != null) {
                callBack.onFailed(null);
            }

            if (listener != null) {
                listener.onToast("参数错误:", parameterMap, "");
                listener.onFail(null);
                listener.onFail(null, null);
            }
        }
        final long time = System.currentTimeMillis();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                JSONObject jo = null;
                try {
                    jo = new JSONObject(s);
                    BaseModelImpl.toastcost(jo.getString("code").equals("0000") ? "成功:" : "失敗", parameterMap.get("serviceName"), parameterMap, s, time);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogUtil.e("------服务器数据-----" + serviceName + "---------" + s);

                Map<String, String> onSuccessData = JSON.parseObject(s, Map.class);

                if (onSuccessData != null && HttpConstant.SUCCESSCODE.equals(onSuccessData.get(HttpConstant.SERVER_CODE))) {

                    if (listener != null) {

                        listener.onResult(s);
                    }

                    LogUtil.e("CCCC", serviceName + "-------------" + ifSaveSessionId);
                    if (onSuccessData.containsKey(HttpConstant.SERVER_SESSIONID) && ifSaveSessionId) {
                        //对每次请求的session进行保存处理
                        AppConstant.handleSessionId(onSuccessData.get(HttpConstant.SERVER_SESSIONID) + "");
                    }

                    //服务器返回data值
                    String data = null;
                    if (onSuccessData.containsKey(HttpConstant.SERVER_DATA)) {

                        data = onSuccessData.get(HttpConstant.SERVER_DATA);
                        //检测请求的接口是否加密
                        if (ifEncryption) {

                            LogUtil.e("AAA", "---1--密文-" + data);

                            try {

                                byte[] bbbb = RsaUtil.decryptByPrivateKey(data.getBytes(charName), privateKey);

                                data = new String(bbbb, "UTF-8");

                                LogUtil.e("AAA", "---1--明文-" + data);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {


                        }
                    }

                    if (onSuccessData.containsKey(HttpConstant.SERVER_SIGN)) {
                        //服务器加密的值
                        String signValue = onSuccessData.get(HttpConstant.SERVER_SIGN) + "";
                        //对data数据进行排序
                        Map<String, String> sortData = sortData(data);

                        String json = JSON.toJSONString(sortData);

                        String encrptionStr = getMD5(json);//JSON.toJSONString(sortData));
                        LogUtil.e("AAA", "---3---" + encrptionStr.equals(signValue));
                        if (encrptionStr.equals(signValue)) {//校验成功

                            if (callBack != null) {
                                callBack.onSuccess(data);
                            }

                            if (listener != null) {

                                listener.onSuccess(data);
                                listener.onSuccess(data, onSuccessData.get(HttpConstant.SERVER_CODE) + "");
                                listener.onResult(s);
                            }
                        } else {//校验失败
                            if (callBack != null) {
                                callBack.onFailed(null);
                            }

                            if (listener != null) {
                                listener.onFail(onSuccessData.get(HttpConstant.SERVER_ERROR) + "");
                                listener.onFail(onSuccessData.get(HttpConstant.SERVER_ERROR) + "", onSuccessData.get(HttpConstant.SERVER_CODE) + "");
                            }
                        }

                    } else {
                        //检查当前请求是否是加密请求
                        if (ifEncryption) {
                            //对data数据进行解密处理

                        }

                        if (callBack != null) {
                            callBack.onSuccess(data);
                        }

                        if (listener != null) {
                            listener.onSuccess(data);
                            listener.onSuccess(data, onSuccessData.get(HttpConstant.SERVER_CODE) + "");
                            listener.onResult(s);
                        }

                    }

                } else {
                    if (callBack != null) {
                        callBack.onFailed(onSuccessData);
                    }

                    if (listener != null) {
                        listener.onFail(onSuccessData.get(HttpConstant.SERVER_ERROR) + "");

                        listener.onFail(onSuccessData.get(HttpConstant.SERVER_ERROR) + "", onSuccessData.get(HttpConstant.SERVER_CODE) + "");

                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                LogUtil.e("onError:" + throwable.getMessage());
                if (callBack != null) {
                    callBack.onFailed(null);
                }

                if (listener != null) {
                    listener.onFail("加载失败，请重试");
                    listener.onFail("加载失败，请重试", null);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                if (callBack != null) {
                    callBack.onCancelled(e);
                }
            }

            @Override
            public void onFinished() {
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });

    }


    /**
     * 上传图片
     *
     * @param parameterMap
     */
    public void sendUploadImageRequest(Map<String, String> parameterMap, final HttpCallBackData callBack) {

        RequestParams params = new RequestParams(upload_image_url);

        params.setConnectTimeout(getConnectTimeOut());

        setParamsHeader(params);

        try {
            String requestStr = JSON.toJSONString(parameterMap);
            LogUtil.e("------请求参数--------------" + requestStr);
            params.setBodyContent(requestStr);
        } catch (Exception e) {
            e.printStackTrace();

        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtil.e("UUUU", "-------onSuccess-------=" + s);
                if (callBack != null) {

                    callBack.onSuccess(JSON.parseObject(s, Map.class));
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (callBack != null) {

                    callBack.onFailed(null);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                if (callBack != null) {
                    callBack.onCancelled(null);
                }
            }

            @Override
            public void onFinished() {
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });

    }

    /**
     * 设置参数的头部信息
     *
     * @param params
     */
    private void setParamsHeader(RequestParams params) {
        params.setHeader("Accept", "application/json");
        params.setHeader("Content-type", "application/json;charset=UTF-8");
        params.setHeader("Accept-Encoding", "gzip, deflate");
    }

    /**
     * 对服务器map值进行升序
     *
     * @param serverData
     * @return
     */
    public TreeMap<String, String> sortData(String serverData) {

        Map<String, String> dataMap = JSON.parseObject(serverData, Map.class);

        TreeMap<String, String> containerMap = new TreeMap<String, String>();

        //遍历数据
        Iterator<Map.Entry<String, String>> it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();

            containerMap.put(entry.getKey(), entry.getValue());
        }

        return containerMap;

    }


    public String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 发送请求方法
     *
     * @param callBack 服务器返回值
     */
    public abstract void sendRequest(HttpCallBackData callBack);


}
