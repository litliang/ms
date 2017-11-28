package com.yzb.card.king.ui.app.activity.ia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.UserAuthenticationBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.app.activity.ValidFinishActivity;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.ui.user.presenter.LoginPresenter;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import static com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity.USER_AUTHENTICATION_DATA;

/**
 * 类  名：验证结果
 * 作  者：Li Yubing
 * 日  期：2017/10/14
 * 描  述：
 */
@ContentView(R.layout.activity_verify_result)
public class VerifyResultActivity extends BaseActivity {

    public static final int RESULT_REQUEST_CODE = 50000;

    public static final int RESULT_RESULT_SUCCESS = 50011;

    public static final int RESULT_RESULT_REVERIFY = 50021;

    public static final String VERIFY_RESULT_TAG ="verify_result_tag";

    @ViewInject(R.id.llFailed)
    private LinearLayout llFailed;

    @ViewInject(R.id.llSucced)
    private LinearLayout llSucced;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData()
    {
        final int type = getIntent().getIntExtra(VERIFY_RESULT_TAG,1);

        String status = "3";

        if(type ==1){

            llFailed.setVisibility(View.VISIBLE);

            llSucced.setVisibility(View.GONE);

            UserManager.getInstance().getUserBean().setAuthenticationStatus("3");

            status = "3";

        }else if(type ==0){

            llFailed.setVisibility(View.GONE);

            llSucced.setVisibility(View.VISIBLE);

            UserManager.getInstance().getUserBean().setAuthenticationStatus("1");

            status = "2";
        }

        if (getIntent().hasExtra(USER_AUTHENTICATION_DATA)) {

            UserAuthenticationBean   userAuthenticationBean = (UserAuthenticationBean) getIntent().getSerializableExtra(USER_AUTHENTICATION_DATA);
            SimpleRequest request = new SimpleRequest("ApplyAuthentication");
            Map<String, Object> data = new HashMap<>();
            data.put("countryId", userAuthenticationBean.getCountryId());
            data.put("certType", userAuthenticationBean.getCertType());
            data.put("certNo", userAuthenticationBean.getCertNo());
            data.put("realName", userAuthenticationBean.getRealName());

            data.put("birthday", userAuthenticationBean.getBirthday());
            data.put("certInvalidDate", userAuthenticationBean.getCertInvalidDate());
            data.put("cardType", userAuthenticationBean.getCardType());
            data.put("bankId", userAuthenticationBean.getBankId());

            data.put("cardNo", userAuthenticationBean.getCardNo());
            data.put("validityPeriod",userAuthenticationBean.getValidityPeriod());
            data.put("mobile", userAuthenticationBean.getMobile());

            data.put("status", status);
            request.setParam(data);
            request.sendRequestNew(new BaseCallBack()
            {
                @Override
                public void onSuccess(Object data)
                {
                    if(type ==1){

                        UserManager.getInstance().getUserBean().setAuthenticationStatus("3");

                    }else if(type ==0){

                        UserManager.getInstance().getUserBean().setAuthenticationStatus("1");

                        new LoginPresenter(VerifyResultActivity.this).sendPersonInfoRequest();
                    }
                }

                @Override
                public void onFail(Error error)
                {
                    UiUtils.shortToast(error.getError());

                    if(type ==1){

                        UserManager.getInstance().getUserBean().setAuthenticationStatus("3");

                    }else if(type ==0){

                        UserManager.getInstance().getUserBean().setAuthenticationStatus("1");
                    }
                }
            });

        }
    }

    private void initView()
    {
        setTitleNmae("验证结果");
    }

    @Event(R.id.tvOpenHiLifeApp)
    private void tvOpenHiLifeApp(View view){

        setResult(RESULT_RESULT_SUCCESS);

        finish();

    }

    @Event(R.id.tvAddBank)
    private void tvAddBank(View view){

        setResult(RESULT_RESULT_SUCCESS);

        finish();

        //进入添加银行
        Intent intent = new Intent(this, AddCardAllActivity.class);
        intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.PART_BUSINESS_VALUE);
        startActivityForResult(intent , 0);

    }

    @Event(R.id.tvReVerify)
    private void tvReVerify(View view){

        setResult(RESULT_RESULT_REVERIFY);

        finish();
    }

    /**
     * 返回按钮
     * @param v
     */
    public void backAction(View v){

        setResult(RESULT_RESULT_SUCCESS);

        finish();
    }

}
