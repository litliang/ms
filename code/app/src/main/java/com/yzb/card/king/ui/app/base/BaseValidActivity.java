package com.yzb.card.king.ui.app.base;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.manager.ValidManager;
import com.yzb.card.king.ui.base.BaseActivity;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/21 10:20
 * 描述：
 */
public abstract class BaseValidActivity extends BaseActivity implements View.OnClickListener
{
    private View view;
    private ImageView ivFirstStep, ivFirstLine, ivSecondStep, ivSecondLine, ivThirdStep;
    private TextView tvButton, tvStep1, tvStep2, tvStep3;
    protected int currentStep = 0;
    protected LinearLayout llForget;
    protected View llStep;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        ValidManager.getValidManager().addActivity(this);
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_idauthentication);
        setTitleNmae( setMidTitle());
//        switchContentLeft(0);
        llStep = findViewById(R.id.llStep);

        ivFirstStep = (ImageView) findViewById(R.id.ivFirstStep);
        ivFirstLine = (ImageView) findViewById(R.id.ivFirstLine);
        ivSecondStep = (ImageView) findViewById(R.id.ivSecondStep);
        ivSecondLine = (ImageView) findViewById(R.id.ivSecondLine);
        ivThirdStep = (ImageView) findViewById(R.id.ivThirdStep);

        tvButton = (TextView) findViewById(R.id.tvButton);
        tvStep1 = (TextView) findViewById(R.id.tvStep1);
        tvStep2 = (TextView) findViewById(R.id.tvStep2);
        tvStep3 = (TextView) findViewById(R.id.tvStep3);

        setStepText(tvStep1, tvStep2, tvStep3);

        llForget = (LinearLayout) findViewById(R.id.llForget);
        tvButton.setText(getButtonText());
        tvButton.setOnClickListener(this);
        FrameLayout flContent = (FrameLayout) findViewById(R.id.flContent);
        view = getContent();
        flContent.addView(view);
    }

    protected void setStep(int step)
    {
        currentStep = step;
        ivFirstStep.setEnabled(step >= 1);
        ivFirstLine.setEnabled(step >= 2);
        ivSecondStep.setEnabled(step >= 2);
        ivSecondLine.setEnabled(step >= 3);
        ivThirdStep.setEnabled(step >= 3);

        tvStep1.setEnabled(step >= 1);
        tvStep2.setEnabled(step >= 2);
        tvStep3.setEnabled(step >= 3);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvButton:
                onTvButtonClick();
                break;

        }
    }

    public void setTvButton(String text)
    {
        tvButton.setText(text);
    }

    public void hideStep()
    {
        llStep.setVisibility(View.GONE);
    }

    /**
     * 初始化中间的View
     *
     * @author ysg
     * created at 2016/6/21 10:22
     */
    protected abstract View getContent();

    /**
     * 初始数据，设置当前步骤
     *
     * @author ysg
     * created at 2016/6/21 10:23
     */
    protected abstract void initData();

    /**
     * 设置标题
     *
     * @author ysg
     * created at 2016/6/21 10:24
     */
    protected abstract String setMidTitle();

    /**
     * 设置按钮的文本
     *
     * @author ysg
     * created at 2016/6/21 10:24
     */
    protected abstract String getButtonText();

    /**
     * 设置按钮点击事件
     *
     * @author ysg
     * created at 2016/6/21 10:24
     */
    protected abstract void onTvButtonClick();

    /**
     * 设置步骤名
     *
     * @author ysg
     * created at 2016/6/21 10:40
     */
    protected abstract void setStepText(TextView tvStep1, TextView tvStep2, TextView tvStep3);
}
