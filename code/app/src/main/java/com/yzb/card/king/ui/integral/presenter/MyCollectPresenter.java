package com.yzb.card.king.ui.integral.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.bean.user.UserCollectBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.ui.integral.model.MyCollectDao;
import com.yzb.card.king.ui.integral.model.impl.MyCollectDaoImpl;
import com.yzb.card.king.ui.integral.model.OnDataLoadFinish;
import com.yzb.card.king.ui.integral.view.MyCollectView;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public class MyCollectPresenter implements OnDataLoadFinish {
    private MyCollectDao myCollectDao;

    private MyCollectView myCollectView;

    public MyCollectPresenter(MyCollectView myCollectView){
        this.myCollectView=myCollectView;
        this.myCollectDao=new MyCollectDaoImpl();
        this.myCollectDao.setOnDataLoadFinish(this);
    }
    public void getData(String type){
        myCollectDao.getMyCollectInfo(type);
    }

    public void delete(UserCollectBean ucBean,String type){
        myCollectDao.deleteMyCollect(ucBean,type);
    }

    @Override
    public void onStart() {
        this.myCollectView.onBegin();
    }

    @Override
    public void onSuccess(Object o) {
        this.myCollectView.onSuccess(JSON.parseArray(String.valueOf(o), UserCollectBean.class));
    }

    @Override
    public void onFailed(Object o) {
        if(o!=null && o instanceof Map){
            Map<String,String> map= (Map<String, String>) o;

            this.myCollectView.onFailed(map.get(HttpConstant.SERVER_ERROR));
        }
    }

    @Override
    public void onLoadMore(Object o) {

    }

    @Override
    public void onFinish() {
        this.myCollectView.onFinish();
    }

    @Override
    public void onDelete(UserCollectBean rb) {
        this.myCollectView.onDelete(rb);
    }

    @Override
    public void onDeleteRemind(RemindBean remindBean) {

    }

    @Override
    public void onGoCardInfo(Object o) {

    }

}
