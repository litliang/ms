package com.yzb.card.king.http.integral;

import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/8
 * 描  述：
 */
public class RemoveRemindRequest extends BaseRequest {

    private String serverName= CardConstant.INTEGRAL_DELETEREMIND;

    Map<String,Object> param=new HashMap<String,Object>();

    public RemoveRemindRequest(RemindBean rb){
        param.put("remindId",rb.getRemindId());
    }
    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,param),callBack);
    }
}
