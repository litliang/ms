package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.popup.SimpleTextPop;
import com.yzb.card.king.ui.credit.watcher.CardTextWatcher;
import com.yzb.card.king.ui.my.presenter.CardInfoOperationPresenter;
import com.yzb.card.king.ui.my.view.CardInfoOperationView;
import com.yzb.card.king.ui.other.controller.ScanCardController;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 *
 */
@ContentView(R.layout.activity_add_card_my)
public class AddCardAllActivity extends BaseActivity implements CardInfoOperationView
{
    /**
     * 新增卡业务流程标记
     */
    public static final String BUSINESS_ADD_CARD = "business_add_card";
    /**
     * 可完成新增卡的全部业务流程
     */
    public static final int ALL_BUSINESS_VALUE = 1000;
    /**
     * 可快速添加信用卡和储蓄卡
     */
    public static final int PART_BUSINESS_VALUE = 1001;

    private static final int REQ_CAN_PAY_CARD = 2;

    private static final int REQ_BANK_CARD = 3;

    private final String CARD_CREDIT = "2";

    private final String CARD_DEBIT = "1";

    private SimpleTextPop pop;

    private ScanCardController cardController;

    public CardBin cardBin;

    @ViewInject(R.id.etCardNum)
    private EditText etCardNum;

    @ViewInject(R.id.tvName)
    private TextView tvName;
    /**
     * 当前业务流程标记
     */
    private int currentBusinessProcessFlag = 0;

    private CardInfoOperationPresenter cardInfoOperationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);

        cardInfoOperationPresenter = new CardInfoOperationPresenter(this);

        initListener();

        initData();

    }

    private void initListener()
    {
        CardTextWatcher watcher = new CardTextWatcher(etCardNum);
        watcher.setListener(new BaseCallBack<CardBin>()
        {

            @Override
            public void onSuccess(CardBin data)
            {
                cardBin = data;
            }

            @Override
            public void onFail(Error error)
            {

            }
        });
        etCardNum.addTextChangedListener(watcher);
    }

    private void initData()
    {
        setTitleNmae(getString(R.string.credit_add_credit_card));

        if(getIntent().hasExtra(BUSINESS_ADD_CARD)){

            currentBusinessProcessFlag = getIntent().getIntExtra(BUSINESS_ADD_CARD,0);

        }

        initScanController();
    }


    private void initScanController()
    {
        cardController = new ScanCardController(this);

        cardController.setCallBack(new BaseCallBack<CardInfo>()
        {
            @Override
            public void onSuccess(CardInfo data)
            {
                etCardNum.setText(data.getFieldString(TFieldID.TBANK_NUM));
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }



    @Event(R.id.ivHelp)
    private void help(View view)
    {
        if (pop == null)
        {
            pop = new SimpleTextPop(this);
            pop.setData(UiUtils.getString(R.string.card_user_instruction_title)
                    , UiUtils.getString(R.string.card_user_instruction_content));
        }
        pop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER_HORIZONTAL, 0, -UiUtils.dp2px(50));
    }

    @Event(R.id.ivCamera)
    private void scan(View view)
    {
        cardController.scan();
    }

    @Event(R.id.btNext)
    private void next(View view)
    {
        if (TextUtils.isEmpty(getName()))
        {
            UiUtils.shortToast("请输入持卡人姓名");
            return;
        }

        if (cardBin != null)
        {

            LogUtil.e("ABCE","currentBusinessProcessFlag="+currentBusinessProcessFlag+"  ALL_BUSINESS_VALUE= "+ALL_BUSINESS_VALUE);
            if (CARD_CREDIT.equals(cardBin.getType()))//信用卡
            {

                if(currentBusinessProcessFlag == ALL_BUSINESS_VALUE){

                    goNext(AddCanPayCardActivity.class, REQ_CAN_PAY_CARD);
                }else{
                    //快速添加信用卡

                    cardInfoOperationPresenter.fastCreateCrediteCard(cardBin);

                }

            } else if (CARD_DEBIT.equals(cardBin.getType()))//储蓄卡
            {

                if(currentBusinessProcessFlag == ALL_BUSINESS_VALUE){

                    goNext(AddBankCardActivity.class, REQ_BANK_CARD);

                }else {
                    //快速添加储蓄卡
                    cardInfoOperationPresenter.fastCreateDebitCard(cardBin);
                }

            } else
            {
                UiUtils.shortToast("未知的卡类型");
            }
        } else
        {
            UiUtils.shortToast("未检测出银行卡信息");
        }

    }

    @NonNull
    private String getName()
    {
        return tvName.getText().toString();
    }

    private void goNext(Class claz, int code)
    {
        Intent intent = new Intent(this, claz);
        intent.putExtra("cardNo", etCardNum.getText().toString());
        intent.putExtra("name", getName());
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        cardController.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_BANK_CARD:
                case REQ_CAN_PAY_CARD:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

        if(type == CardInfoOperationPresenter.CREDIT_CARD_CODE){

            ToastUtil.i(AddCardAllActivity.this,R.string.toast_credit_card);
            setResult(RESULT_OK);
            finish();

        }else  if(type == CardInfoOperationPresenter.DEBIT_CARD_CODE){

            ToastUtil.i(AddCardAllActivity.this,R.string.toast_debit_card);
            setResult(RESULT_OK);
            finish();
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }

    @Override
    public String[] getCardHolder()
    {
        String[] str = new String[2];

        str[0]  = getName();

        str[1] = etCardNum.getText().toString();

        return str ;
    }
}
