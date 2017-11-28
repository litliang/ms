package com.yzb.card.king.ui.integral.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.bean.user.UserCollectBean;
import com.yzb.card.king.ui.credit.bean.CreditCardBillBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.ui.integral.model.MyRemindDao;
import com.yzb.card.king.ui.integral.model.impl.MyRemindDaoImpl;
import com.yzb.card.king.ui.integral.model.OnDataLoadFinish;
import com.yzb.card.king.ui.integral.view.MyRemindView;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public class MyRemindPresenter implements OnDataLoadFinish {
    private MyRemindDao myRemindDao;

    private MyRemindView myRemindView;

    public MyRemindPresenter(MyRemindView myRemindView){
        this.myRemindView=myRemindView;
        this.myRemindDao=new MyRemindDaoImpl();
        this.myRemindDao.setOnDataLoadFinish(this);
    }

    public void getData(String type){
        this.myRemindDao.getData(type);
    }

    public void getCardInfo(int id){
        this.myRemindDao.getCardInfo(id);
    }

    public void deleteRemind(RemindBean remindBean){
        this.myRemindDao.deleteRemind(remindBean);
    }

    @Override
    public void onStart() {
        this.myRemindView.onBegin();
    }

    @Override
    public void onSuccess(Object o) {
        this.myRemindView.onSuccess(JSON.parseArray(String.valueOf(o),RemindBean.class));
    }

    @Override
    public void onFailed(Object o) {
        if(o!=null && o instanceof Map){
            Map<String,String> map= (Map<String, String>) o;
            this.myRemindView.onFailed(map.get(HttpConstant.SERVER_ERROR));
        }
    }

    @Override
    public void onLoadMore(Object o) {

    }

    @Override
    public void onFinish() {
        this.myRemindView.onFinish();
    }

    @Override
    public void onDelete(UserCollectBean rb) {

    }

    @Override
    public void onDeleteRemind(RemindBean remindBean) {
        this.myRemindView.onDelete(remindBean);
    }

    @Override
    public void onGoCardInfo(Object o) {
        this.myRemindView.onGoCardInfo(JSON.parseObject(String.valueOf(o), CreditCardBillBean.class));
    }
}
