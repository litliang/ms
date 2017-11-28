package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.presenter.ExistPayPwdPresenter;
import com.yzb.card.king.ui.app.view.ExistPayPwdView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.pop.ResetPayPasswordDialog;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 密码设置首页；
 */
public class PwdSettingActivity extends BaseActivity implements View.OnClickListener, ExistPayPwdView
{
    private ExistPayPwdPresenter existPayPwdPresenter;//支付密码是否存在校验；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_setting);
        existPayPwdPresenter = new ExistPayPwdPresenter(this, this);
        assignViews();
    }

    private void assignViews()
    {

        setTitleNmae(getString(R.string.setting_pwd_set));

        findViewById(R.id.rl_reset_login_pwd).setOnClickListener(this);
        findViewById(R.id.rl_reset_pay_pwd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rl_reset_login_pwd:
                readyGo(this, VerifyOldLoginPwdActivity.class);
                break;
            case R.id.rl_reset_pay_pwd:
                checkExistPayPwd();
                break;
        }
    }

    /**
     * 检验是否已经设置了支付密码；
     */
    private void checkExistPayPwd()
    {
        showPDialog(R.string.setting_checking_pay_pwd);
        Map<String, String> params = new HashMap<>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAmountAccount());
        params.put("merchantNo", WalletConstant.MERCHANT_NO); //商户号
        params.put("sign", AppConstant.sign); //签名
        existPayPwdPresenter.loadData(params);
    }

    @Override
    public void onExistPayPwdSucess()
    {
        closePDialog();
        readyGo(this, ResetPayPwdActivity.class);
    }

    @Override
    public void onExistPayPwdFail(String code, String msg)
    {
        closePDialog();
        //不存在；
        if (AppConstant.CODE_PAY_PWD_NO_EXIST.equals(code))
        {
            ResetPayPasswordDialog.getInstance().setDataHandler(null).show(getSupportFragmentManager(), "");
        } else
        {
            toastCustom(msg);
        }
    }
}
