package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.credit.bean.CardBin;
import com.yzb.card.king.ui.credit.watcher.CardTextWatcher;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.other.controller.ScanCardController;
import com.yzb.card.king.ui.other.listeners.MoneyFormatWatcher;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 描述：转到银行卡
 * 作者：殷曙光
 * 日期：2016/12/27 9:57
 */
@ContentView(R.layout.activity_bank_card_trans)
public class BankCardTransActivity extends BaseActivity
{

    private static final int REQ_HISTORY_PEOPLE = 2;
    private static final int REQ_SELECT_BANK = 3;
    private static final int REQ_CONFIRM_INFO = 4;

    @ViewInject(R.id.etName)
    private EditText etName;

    @ViewInject(R.id.etCardNum)
    private EditText etCardNum;

    @ViewInject(R.id.etAmount)
    private EditText etAmount;

    @ViewInject(R.id.ivCamera)
    private ImageView ivCamera;

    @ViewInject(R.id.btNext)
    private Button btNext;

    @ViewInject(R.id.llBankInfo)
    private View llBankInfo;

    @ViewInject(R.id.llSelectedBank)
    private View llSelectedBank;

    @ViewInject(R.id.ivBankLogo)
    private ImageView ivBankLogo;

    @ViewInject(R.id.tvBankName)
    private TextView tvBankName;

    @ViewInject(R.id.tvTime)
    private TextView tvTime;

    private CardBin cardBin;
    //    private Bank bank;
    private ScanCardController scanController;
    private Payee payee;
    private boolean amountRight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        btNext.setEnabled(false);
        llBankInfo.setVisibility(View.GONE);
        llSelectedBank.setVisibility(View.GONE);
    }

    private TextWatcher textWatcher = new MyTextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            btNext.setEnabled(amountRight && !TextUtils.isEmpty(getName())
                    && !TextUtils.isEmpty(getCardNum()));
        }
    };

    private String getCardNum()
    {
        return etCardNum.getText().toString();
    }

    private void initListener()
    {
        etName.addTextChangedListener(textWatcher);

        CardTextWatcher watcher = new CardTextWatcher(etCardNum)
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                super.onTextChanged(s, start, before, count);
                if (s.length() <= 14)
                {
                    llBankInfo.setVisibility(View.GONE);
                } else
                {
                    llBankInfo.setVisibility(View.VISIBLE);
                }
            }
        };
        watcher.setListener(new BaseCallBack<CardBin>()
        {
            @Override
            public void onSuccess(CardBin data)
            {
                if (payee == null)
                    setCardBin(data);
            }

            @Override
            public void onFail(Error error)
            {
                setCardBin(new CardBin());
            }
        });
        etCardNum.addTextChangedListener(watcher);
        etCardNum.addTextChangedListener(textWatcher);

        etAmount.addTextChangedListener(new MoneyFormatWatcher()
        {
            @Override
            public void submitAble(boolean submitAble)
            {
                amountRight = submitAble;
                textWatcher.onTextChanged(null, 0, 0, 0);
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                scanController.scan();
            }
        });
    }

    private void initData()
    {
        setTitleNmae("转到银行卡");
        scanController = new ScanCardController(this);
        scanController.setCallBack(new BaseCallBack<CardInfo>()
        {
            @Override
            public void onSuccess(CardInfo data)
            {
                String cardNum = data.getFieldString(TFieldID.TBANK_NUM);
                etCardNum.setText(cardNum);
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast("扫描失败");
            }
        });

        getIntentData();
    }

    private void getIntentData()
    {
        setPayee((Payee) getIntent().getSerializableExtra("payee"));
    }

    @Event(R.id.tvNotice)
    private void amountLimit(View v)
    {
        Intent intent = new Intent(this, LimitInstructionActivity.class);
        startActivity(intent);
    }

    @Event(R.id.ivPeople)
    private void histroyPeople(View view)
    {
        Intent intent = new Intent(this, PayeeListActivity.class);
        intent.putExtra("type", "2");
        startActivityForResult(intent, REQ_HISTORY_PEOPLE);
    }

    @Event(R.id.btNext)
    private void next(View view)
    {
        if (hasValid())
        {
            Intent intent = new Intent(this, ConfirmInfoActivity.class);
            intent.putExtra("name", getName());
            intent.putExtra("bank", getBankInfo());
            intent.putExtra("amount", etAmount.getText().toString());
            intent.putExtra("time", tvTime.getText().toString());
            intent.putExtra("creditNo", etCardNum.getText().toString());
            intent.putExtra("payee", payee);
            startActivityForResult(intent, REQ_CONFIRM_INFO);
        }
    }

    private Bank getBankInfo()
    {
        if (cardBin != null)
        {
            Bank bank = new Bank();
            bank.setBankId(cardBin.getBankId());
            bank.setBankName(cardBin.getBankName());
            bank.setBankLogo(cardBin.getBankLogo());
            bank.setBankMark(cardBin.getBankMark());
            return bank;
        } else
        {
            return null;
        }
    }

    private boolean hasValid()
    {
        return validBank() && validPayee();
    }

    private boolean validBank()
    {
        if (cardBin == null)
        {
            UiUtils.shortToast("请选择银行");
            return false;
        }

        return true;
    }

    private boolean validPayee()
    {
        if (TextUtils.isEmpty(getName()))
        {
            UiUtils.shortToast("请选输入收款人姓名");
            return false;
        }

        if (payee == null)
        {
            payee = new Payee();
            payee.setBankName(cardBin.getBankName());
            payee.setCreditName(getName());
            payee.setCreditId(cardBin.getBankId());
            payee.setPhotoUrl(cardBin.getBankLogo());
            payee.setTradeType("2");
            payee.setTradeAccount(etCardNum.getText().toString());
            payee.setBankMark(cardBin.getBankMark());
        }
        return true;
    }

    @NonNull
    private String getName()
    {
        return etName.getText().toString();
    }

    @Event(R.id.llSelectedBank)
    private void selectBank(View view)
    {
        Intent intent = new Intent(this, SelectBankActivity.class);
        startActivityForResult(intent, REQ_SELECT_BANK);
    }

    @Event(R.id.tvTimeTable)
    private void timeTable(View view)
    {
        startActivity(new Intent(this, TimeTableActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        scanController.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_HISTORY_PEOPLE:
                    Payee payee = (Payee) data.getSerializableExtra("payee");
                    setPayee(payee);
                    break;
                case REQ_SELECT_BANK:
                    Bank bank = (Bank) data.getSerializableExtra("bank");
                    setCardBin(bank);
                    break;
                case REQ_CONFIRM_INFO:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }

    private void setCardBin(Bank bank)
    {
        CardBin cardBin = null;
        if (bank != null)
        {
            cardBin = new CardBin();
            cardBin.setBankId(bank.getBankId());
            cardBin.setBankName(bank.getBankName());
            cardBin.setBankLogo(bank.getBankLogo());
            cardBin.setBankMark(bank.getBankMark());
        }
        setCardBin(cardBin);
    }

    public void setCardBin(CardBin cardBin)
    {
        if (cardBin != null && cardBin.getBankId() == null) cardBin = null;
        this.cardBin = cardBin;
        setBankInfo(cardBin);
    }

    private void setBankInfo(CardBin cardBin)
    {
        llBankInfo.setVisibility(View.VISIBLE);
        if (cardBin != null && cardBin.getBankId() != 0)
        {
            x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(cardBin.getBankLogo()));
            tvBankName.setText(cardBin.getBankName());
            llSelectedBank.setVisibility(View.GONE);
        } else
        {
            ivBankLogo.setImageResource(R.drawable.bg_transparent);
            tvBankName.setText("");
            llSelectedBank.setVisibility(View.VISIBLE);
        }
    }

    public void setPayee(Payee payee)
    {
        this.payee = payee;
        if (payee == null) return;
        etName.setText(payee.getCreditName());
        etCardNum.setText(TextUtils.isEmpty(payee.getTradeAccount())?payee.getBankNo():payee.getTradeAccount());
        CardBin bin = new CardBin();
        bin.setBankLogo(payee.getPhotoUrl());
        bin.setBankName(payee.getBankName());
        bin.setBankId(payee.getCreditId());
        bin.setBankMark(payee.getBankMark());
        setCardBin(bin);
//        llBankInfo.setVisibility(View.VISIBLE);
//        x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(payee.getPhotoUrl()));
//        tvBankName.setText(payee.getBankName());
//        llSelectedBank.setVisibility(View.GONE);
    }
}
