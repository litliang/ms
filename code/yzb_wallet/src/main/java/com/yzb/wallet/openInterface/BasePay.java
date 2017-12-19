package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yzb.wallet.dialog.ConfirmDialog;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.Map;

/**
 *
 */
public class BasePay {

    public Activity aty;

    public Activity getAty() {
        return aty;
    }

    public void setAty(Activity aty) {
        this.aty = aty;
    }

    protected WalletBackListener wbListener;
    protected ForgetPwdBackListener forgetPwdListener;
    protected AddCardBackListener addCardBackListener;
    protected PayFailBackListener payFailBackListener;
    protected PayMethodListener payMethodListener;
    protected BackListener backListener;

    public void setCallBack(WalletBackListener listener) {
        this.wbListener = listener;
    }

    /**
     * 忘记密码；
     *
     * @param listener
     */
    public void setForgetPwdCallBack(ForgetPwdBackListener listener) {
        this.forgetPwdListener = listener;
    }
    /**
     * 添加银行卡；
     *
     * @param listener
     */
    public void setAddCardCallBack(AddCardBackListener listener) {
        this.addCardBackListener = listener;
    }

    /**
     * 支付失败；
     *
     * @param listener
     */
    public void payFailCallBack(PayFailBackListener listener) {
        this.payFailBackListener = listener;
    }

    /**
     * 选择支付方式；
     *
     * @param listener
     */
    public void payMethodCallBack(PayMethodListener listener) {
        this.payMethodListener = listener;
    }


    /**
     * 返回；
     *
     * @param listener
     */
    public void setBack(BackListener listener) {
        this.backListener = listener;
    }

    /**
     * 判断是否登录
     */
    protected String checkLoginMsg(Map<String, String> params) {

        if (StringUtil.isEmpty(params.get("sessionId")) || StringUtil.isEmpty(params.get("UUID"))) {
            return WalletConstant.MSG_UN_LOGIN;
        } else {
            WalletConstant.sessionId = params.get("sessionId");
            WalletConstant.UUID = params.get("UUID");
        }
        return "";
    }

    /**
     * 检查当前网络是否可用
     */
    protected boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    //System.out.println(i + "===状态===" + networkInfo[i].getState());
                    //System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 网络异常提示
     */
    protected void NetWorkUnavailable(Activity activity) {

        final ConfirmDialog.Builder builder = new ConfirmDialog.Builder(activity);
        builder.setTitle("当前网络异常，请确认网络可用后重试");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        builder.create().show();
    }
}
