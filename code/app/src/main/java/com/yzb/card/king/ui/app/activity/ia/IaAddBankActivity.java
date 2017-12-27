package com.yzb.card.king.ui.app.activity.ia;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.UserAuthenticationBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.watcher.CardTextWatcher;
import com.yzb.card.king.ui.other.controller.ScanCardController;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity.USER_AUTHENTICATION_DATA;
import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_CREDIT;
import static com.yzb.card.king.ui.credit.bean.CardBin.CARD_DEBIT;

/**
 * 类  名：添加银行
 * 作  者：Li Yubing
 * 日  期：2017/10/14
 * 描  述：
 */
@ContentView(R.layout.activity_ia_add_bank)
public class IaAddBankActivity extends BaseActivity {

    @ViewInject(R.id.et_bank_id)
    private EditText etBankNo;

    private ScanCardController cardController;

    public CardBin cardBin;

    private UserAuthenticationBean userAuthenticationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();

        initScanController();

        initListener();
    }

    private void initData()
    {
        if(getIntent().hasExtra(USER_AUTHENTICATION_DATA)){

            userAuthenticationBean = (UserAuthenticationBean) getIntent().getSerializableExtra(USER_AUTHENTICATION_DATA);
        }

    }

    private void initListener()
    {
        CardTextWatcher watcher = new CardTextWatcher(etBankNo);
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
        etBankNo.addTextChangedListener(watcher);
    }

    private void initScanController()
    {
        cardController = new ScanCardController(this);

        cardController.setCallBack(new BaseCallBack<CardInfo>()
        {
            @Override
            public void onSuccess(CardInfo data)
            {
                etBankNo.setText(data.getFieldString(TFieldID.TBANK_NUM));
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }


    @Event(R.id.tvNextStep)
    private void tvNextStep( View view){

        if (TextUtils.isEmpty(etBankNo.getText().toString()))
        {
            UiUtils.shortToast("请填写银行卡号");
            return;
        }

        LogUtil.e("AAAAA","--------------0------------"+(cardBin != null));

        if (cardBin != null && userAuthenticationBean != null)
        {
            LogUtil.e("AAAAA","--------------1------------"+(cardBin != null));

            userAuthenticationBean.setBankId(cardBin.getBankId());

            userAuthenticationBean.setCardType(Integer.parseInt(cardBin.getType()));

            userAuthenticationBean.setBin(cardBin.getBin());

            userAuthenticationBean.setCardNo(etBankNo.getText().toString().trim());

            userAuthenticationBean.setBankCode(cardBin.getBankCode());

            userAuthenticationBean.setBankName(cardBin.getBankName());

            if (!TextUtils.isEmpty(cardBin.getType()))//信用卡
            {

                Intent intent = new Intent(this,IaBankInfoActivity.class);

                intent.putExtra(USER_AUTHENTICATION_DATA,userAuthenticationBean);

                startActivityForResult(intent,VerifyResultActivity.RESULT_REQUEST_CODE);

            } else
            {
                UiUtils.shortToast("未知的卡类型");
            }
        } else
        {
            UiUtils.shortToast("未检测出银行卡信息");
        }
    }

    @Event(R.id.iv_capture)
    private void captureAction(View view){
        cardController.scan();
    }

    private void initView()
    {

        setTitleNmae("添加银行卡");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       // super.onActivityResult(requestCode, resultCode, data);

        cardController.onActivityResult(requestCode, resultCode, data);

        if(requestCode == VerifyResultActivity.RESULT_REQUEST_CODE){

            if(resultCode == VerifyResultActivity.RESULT_RESULT_SUCCESS){

                setResult(resultCode);

                finish();
            }else    if(resultCode == VerifyResultActivity.RESULT_RESULT_REVERIFY){
                setResult(resultCode);
                finish();
            }

        }

    }
}
