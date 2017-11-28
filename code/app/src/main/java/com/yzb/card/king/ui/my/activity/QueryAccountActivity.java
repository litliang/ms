package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.pop.AccountListPop;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 描述：查找账户
 * 作者：殷曙光
 * 日期：2016/12/27 17:33
 */
@ContentView(R.layout.activity_query_account)
public class QueryAccountActivity extends BaseActivity {
    private static final int REQ_TRANSFER = 2;
    private static final int REQ_HISTORY_PEOPLE = 3;

    @ViewInject(R.id.etAccount)
    private EditText etAccount;

    @ViewInject(R.id.btNext)
    private Button btNext;

    @ViewInject(R.id.llAccount)
    private LinearLayout llAccount;

    private AccountListPop pop;
    private Payee payee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        btNext.setEnabled(false);
    }

    private void initListener() {

        etAccount.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (before == 0) {
                    payee = null;
                }

                if (s.toString().length() != 11) {
                    btNext.setEnabled(false);
                } else {
                    btNext.setEnabled(true);
                }
                if (s.length() >= 7) {
                    if (pop == null) {
                        pop = new AccountListPop();
                        pop.setListener(new AccountListPop.OnOneResultListener() {

                            @Override
                            public void onSelected(Payee payee) {
                                    setPayee(payee);
                            }

                            @Override
                            public void onOneResult(Payee payee) {
                                    QueryAccountActivity.this.payee = payee;
                            }

                            @Override
                            public void onClear() {
                                QueryAccountActivity.this.payee = null;
                            }
                        });
                    }
                    pop.setData(s.toString());
                    if (!pop.isShowing()) {
                        pop.showAsDropDown(llAccount, 0, 0);
                    }
                } else {
                    if (pop != null && pop.isShowing()) pop.dismiss();
                }
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payee != null) {
                    Intent intent = new Intent(QueryAccountActivity.this, HiLifeTransferActivity.class);
                    intent.putExtra("payee", payee);
                    startActivityForResult(intent, REQ_TRANSFER);
                } else {
                    UiUtils.shortToast("账户不存在！");
                }
            }
        });
    }

    private void initData() {
        setTitleNmae("转到嗨生活账户");
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
        etAccount.setText(payee == null ? "" : payee.getTradeAccount());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_TRANSFER:
                    setResult(RESULT_OK);
                    finish();
                    break;
                case REQ_HISTORY_PEOPLE:
                    setPayee((Payee) data.getSerializableExtra("payee"));
                    break;
            }
        }
    }

    @Event(R.id.ivPeople)
    private void selectAccount(View view) {
        Intent intent = new Intent(this, PayeeListActivity.class);
        intent.putExtra("type", "1");
        startActivityForResult(intent, REQ_HISTORY_PEOPLE);
    }
}
