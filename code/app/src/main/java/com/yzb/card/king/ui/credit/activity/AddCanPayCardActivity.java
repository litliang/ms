package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.holder.AddCardHolder;
import com.yzb.card.king.ui.credit.holder.CompleteHolder;
import com.yzb.card.king.ui.credit.holder.MobileHolder;
import com.yzb.card.king.ui.credit.interfaces.IAddCardStep;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 描述：添加不可支付信用卡
 * 作者：殷曙光
 * 日期：2017/1/4 14:48
 *
 */
public class AddCanPayCardActivity extends BaseActivity
{

    private RelativeLayout rlContent;
    protected List<IAddCardStep> holderList;

    private int currentStep = 0;
    private String cardNo;
    private String name;
    private Button btNext;
    private View rootView;

    private LinearLayout llImageLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initData()
    {
        getIntentData();
        initHolders();
        initContent(holderList.get(0));
    }

    private void getIntentData()
    {
        cardNo = getIntent().getStringExtra("cardNo");
        name = getIntent().getStringExtra("name");
    }

    private void initContent(IAddCardStep iAddCardStep)
    {
        initTitle(iAddCardStep);
        initRlContent(iAddCardStep);
    }

    private void initRlContent(IAddCardStep iAddCardStep)
    {
        rlContent.removeAllViews();
        rlContent.addView(iAddCardStep.getView());
        rootView.setBackgroundColor(iAddCardStep.getBackgroundColor());
        iAddCardStep.onStart();
    }

    private void initTitle(IAddCardStep iAddCardStep)
    {
        setTitleNmae(iAddCardStep.getTitle());
        btNext.setText(iAddCardStep.getRightText());
//        headerRightText.setText(iAddCardStep.getRightText());
    }

    protected void initHolders()
    {
        holderList = new ArrayList<>();
        holderList.add(new AddCardHolder(this));
        holderList.add(new MobileHolder(this));
        holderList.add(new CompleteHolder(this));
    }

    private void initView()
    {
        rootView = UiUtils.inflate(R.layout.activity_add_card);
        setContentView(rootView);
        rlContent = (RelativeLayout) findViewById(R.id.rlContent);
        btNext = (Button) findViewById(R.id.btNext);
        llImageLeft = (LinearLayout) findViewById(R.id.llImageLeft);
    }

    private void initListener()
    {

        btNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holderList.get(currentStep).rightClick();
            }
        });

        llImageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (currentStep == 0)
                {
                    setResult(RESULT_CANCELED);
                    finish();
                } else
                {
                    currentStep--;
                    initContent(holderList.get(currentStep));
                }
            }
        });
    }

    public void nextStep()
    {
        if (currentStep == holderList.size()-1)
        {
            setResult(RESULT_OK);
            finish();
        } else
        {
            currentStep++;
            initContent(holderList.get(currentStep));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        holderList.get(currentStep).onActivityResult(requestCode, resultCode, data);
    }

    public Map<String, Object> getCardInfo()
    {
        AddCardHolder addCardHolder = getCardHolder();
        Map<String, Object> param = new HashMap<>();
        param.put("certType",addCardHolder.getCertType());
        param.put("certNo",addCardHolder.getCertNo());

        param.put("payType","1");//可支付
        param.put("bin", addCardHolder.getBin());
        param.put("cardNo",addCardHolder.getCardNum());
        param.put("name",addCardHolder.getName());
        param.put("billDay",addCardHolder.getStatementDay());
        param.put("validityPeriod",addCardHolder.getValidityDay().substring(2).replace("/",""));
        param.put("bankId",addCardHolder.getBankId());
        param.put("bankName",addCardHolder.getBankName());
        param.put("cvv2",addCardHolder.getSafeCode());//卡片背面三位数字
        return param;
    }

    public AddCardHolder getCardHolder()
    {
        return (AddCardHolder) holderList.get(0);
    }

    @Override
    public void onBackPressed()
    {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }


    public String getCardNo()
    {
        return cardNo;
    }

    public String getName()
    {
        return name;
    }
}
