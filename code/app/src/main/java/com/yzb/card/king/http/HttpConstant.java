package com.yzb.card.king.http;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 类  名：服务器接口字段
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
public class HttpConstant {

    //服务器出参接口参数
    public final static String  SERVER_DATA ="data";

    public final static String  SERVER_ERROR = "error";

    public final static String SERVER_CODE = "code";

    public final static String SERVER_SESSIONID = "sessionId";

    public final static String SERVER_SIGN = "sign";

    /**
     * MD5 key
     */
    public final static String MD5_KEY = "!qazxsw@";


    /**
     * 成功
     */
    public static String  SUCCESSCODE = "0000";
    /**
     * 无信息
     */
    public static String NOINFO = "9993";

    public static String NOINFO1 = "9999";
    /**
     * 免密支付code
     */
    public static String  AVOID_CLOSE_CODE = "1111";

    /**
     * 检查无数据
     * @param data
     * @return
     */
    public static boolean chechNoInfo(String data){

        //检查是否有9993
        int index =  data.indexOf(HttpConstant.NOINFO);

        if(index== -1 ){

            return  false;

        }else{
            return  true;
        }
    }

    /**
     * 解析出服务器返回的error字段信息
     * @param data
     * @return
     */
    public static String getData(Object data){
        Map<String, String> onSuccessData = (Map<String, String>) data;
        if(onSuccessData.containsKey(SERVER_ERROR)){

            return  onSuccessData.get(SERVER_ERROR);

        }else{
            return null;
        }
    }

}
