package com.yzb.card.king.http.hotel;

import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：查询酒店其他商品菜单
 * 作  者：Li Yubing
 * 日  期：2017/8/16
 * 描  述：
 */
public class QueryHotelGoodsMenuRequest extends BaseRequest {

    private String serverName = CardConstant.QUERYHOTELOTHERGOODSMENU;

    Map<String, Object> param = new HashMap<String, Object>();

    public QueryHotelGoodsMenuRequest(String  goodsType,String goodsId)
    {
        serverName = CardConstant.QUERYHOTELOTHERGOODSMENU;
        // 公共参数
        param.put("goodsType", goodsType);
        param.put("goodsId", goodsId);

    }

    public QueryHotelGoodsMenuRequest(String  goodsType,String policysId,boolean flag)
    {
        serverName = CardConstant.QUERYHOTELOTHERGOODSPOLICYSMENU;
        // 公共参数
        param.put("goodsType", goodsType);
        param.put("policysId", policysId);

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
