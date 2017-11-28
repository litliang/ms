package com.yzb.card.king.ui.discount.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.common.CustomerChannelListRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.ShopBean;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/20
 * 描  述：
 */
public class ScpMainDaoImpl implements IScpMain {

    private DataCallBack dataCallBack;

    public ScpMainDaoImpl(DataCallBack dataCallBack){
        this.dataCallBack=dataCallBack;
    }

    @Override
    public void getTjsh(Map<String, Object> param)
    {
        SimpleRequest s=new SimpleRequest( CardConstant.card_app_shoplist,param);
        s.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<ShopBean> storeBeans = JSON.parseArray(String.valueOf(o), ShopBean.class);
                dataCallBack.requestSuccess(storeBeans,IScpMain.SHANGHULIST_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    if(dataCallBack != null) {
                        dataCallBack.requestFailedDataCall(null, IScpMain.SHANGHULIST_CODE);
                    }
                }
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void getUserChannel(String parentId, String category)
    {
        new CustomerChannelListRequest(parentId,category).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                String data = o + "";

                //根据接口文档内容，编辑bean，解析data的value
                List<ChildTypeBean> beans = JSON.parseArray(data, ChildTypeBean.class);

                if(dataCallBack != null){

                    dataCallBack.requestSuccess(beans, IScpMain.GERENPD_CODE);

                }
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    if(dataCallBack != null) {
                        dataCallBack.requestFailedDataCall(null, IScpMain.GERENPD_CODE);
                    }
                }
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }
}
