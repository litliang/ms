package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.presenter.UpdateUserInfoPresenter;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.holder.BindEmailHolder;
import com.yzb.card.king.ui.my.holder.IHolder;
import com.yzb.card.king.ui.my.holder.UpdateEmailHolder;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

public class BindEmailActivity extends BaseActivity implements BaseViewLayerInterface
{

    private LinearLayout llContent;
    private String email;
    private UpdateUserInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        presenter = new UpdateUserInfoPresenter(this);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_bind_email);
        llContent = (LinearLayout) findViewById(R.id.llContent);
    }

    private void initListener()
    {

    }

    private void initData()
    {
        getIntentData();
        IHolder holder = getHolder();
        llContent.removeAllViews();
        llContent.addView(holder.getView());

    }

    private void getIntentData()
    {
        email = getIntent().getStringExtra("email");
    }

    private IHolder getHolder()
    {
        if (hasEmail())
        {
            setTitleNmae("修改邮箱");
            return new UpdateEmailHolder(this);

        } else
        {
            setTitleNmae("绑定邮箱");
            return new BindEmailHolder(this);
        }
    }

    private boolean hasEmail()
    {
        return !TextUtils.isEmpty(email);
    }

    public void updateEmail(String email)
    {

        Map<String, Object> param = new HashMap<>();
        param.put("email",email);
        presenter.update(param);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        UiUtils.shortToast(o+"");
    }
}
