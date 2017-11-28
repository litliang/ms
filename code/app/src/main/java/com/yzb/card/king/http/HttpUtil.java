package com.yzb.card.king.http;

import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.ToastUtil;

/**
 * 类  名：服务请求的通用嘛
 * 作  者：Li Yubing
 * 日  期：2016/7/20
 * 描  述：
 */
public class HttpUtil {


    /**
     *
     * @param code
     */
    public static void responseCallBackMessage(String code){

        String messageError = null;

        if("9999".equals(code)){

            messageError = "失败";

        }else if("9998".equals(code)){

            messageError = "该账号已注销，请重新登录";

        }else if("9997".equals(code)){

            messageError = "缺少必要的属性";

        }else if("9996".equals(code)){

            messageError = "sessionId错误";

        }else if("9995".equals(code)){

            messageError = "终端错误";

        }else if("9994".equals(code)){

            messageError = "账户冻结";

        }else if("9993".equals(code)) {

            messageError = "无信息";

        } else {

            messageError = "失败";
        }


        ToastUtil.i(GlobalApp.getInstance().getContext(),messageError);
    }

}
