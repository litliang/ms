package com.yzb.card.king.ui.user.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.credit.bean.CreditCardBillBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.WalletRequestUtil;
import com.yzb.card.king.http.creditcard.QueryBindCreditListRequest;
import com.yzb.card.king.http.user.LoginRequest;
import com.yzb.card.king.http.user.LogoutRequest;
import com.yzb.card.king.http.user.PersonInfoRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 类  名：实现登录业务接口类
 * 作  者：Li Yubing
 * 日  期：2016/8/12
 * 描  述：
 */
public class LoginImpl implements Ilogin {

    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName(AppConstant.APP_DB_NAME)
            // 不设置dbDir时, 默认存储在app的私有目录.
            .setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
            .setDbVersion(AppConstant.APP_DB_VERSION)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    LogUtil.e("oldVersion="+oldVersion+" newVersion="+newVersion);
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                    // or
                    // db.dropDb();
                }
            });

    private DataCallBack dataCallBack;

    public LoginImpl(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;
    }


    @Override
    public void saveUserInfo(UserBean bean) {

        DbManager db = x.getDb(daoConfig);
        try {
            //检查是否存在用户数据
            List<UserBean> list =  db.selector(UserBean.class).findAll();

            if(list != null && list.size() >0){
                 //删除用户数据
                db.delete(list);
            }
            //保存用户信息
            db.saveBindingId(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public UserBean checkUserInfo() {

        DbManager db = x.getDb(daoConfig);

        try {
            List<UserBean> list =  db.selector(UserBean.class).findAll();

            int size = list.size();

            if(size > 0){

                return  list.get(0);

            }else {

                return  null;
            }


        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delUserInfo(UserBean bean) {

        DbManager db = x.getDb(daoConfig);

        try {
            db.delete(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendLoginRequest(String loginName, String loginKey,int type) {

        new LoginRequest(loginName, loginKey, type).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {
                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);
            }

            @Override
            public void onSuccess(Object o) {


                if(dataCallBack != null){

                    //发送用户当前绑定的信用卡信息
                   // sendQueryBindCreditListRequest();
                    //发送用户个人信息
                    sendSelfLoginRequest();
                }
            }

            @Override
            public void onFailed(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                }

            }

            @Override
            public void onCancelled(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void sendLogoutRequest() {

        new LogoutRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {


                if(dataCallBack != null){

                    dataCallBack.requestSuccess(null, Ilogin.LOGOUT_CODE);

                }
            }

            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailed(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
            }

            @Override
            public void onCancelled(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 发送已绑定信用卡列表,获取个人已绑定卡数；获取个人钱包信息
     */
    private void sendQueryBindCreditListRequest() {

        final UserBean userBean = UserManager.getInstance().getUserBean();
        //发送个人钱包信息
        new WalletRequestUtil(null).sendChechUserWalletRequest(userBean.getAccount(), GlobalApp.getInstance().getPublicActivity());
        //发送已绑定信用卡列表
        new QueryBindCreditListRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                String data = "" + o;

                List<CreditCardBillBean> busShopList = JSON.parseArray(data, CreditCardBillBean.class);

                int size = busShopList.size();

                if(dataCallBack != null){

                    dataCallBack.requestSuccess(size, Ilogin.BINDING_CARD_CODE);
                }
            }

            @Override
            public void onFailed(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    String code = onSuccessData.get(HttpConstant.SERVER_CODE);

                    if(code.equals(HttpConstant.NOINFO)){

                        if(dataCallBack != null){

                            dataCallBack.requestSuccess(0, Ilogin.BINDING_CARD_CODE);
                        }

                    }else{

                        ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    }

                }
            }

            @Override
            public void onCancelled(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void sendSelfLoginRequest() {

        //发送自动登录
        new PersonInfoRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                String data = o + "";

                //根据接口文档内容，编辑bean，解析data的value
                UserBean userBean = JSON.parseObject(data, UserBean.class);

                if(dataCallBack != null){

                    dataCallBack.requestSuccess(userBean, Ilogin.LOGIN_CODE);

                    //发送用户当前绑定的信用卡信息
                    sendQueryBindCreditListRequest();
                }

            }

            @Override
            public void onFailed(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
                if (o != null && o instanceof Map) {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
            }

            @Override
            public void onCancelled(Object o) {
                ProgressDialogUtil.getInstance().closeProgressDialog();
            }

            @Override
            public void onFinished() {

            }
        });

    }
}
