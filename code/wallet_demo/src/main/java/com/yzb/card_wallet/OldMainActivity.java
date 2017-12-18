package com.yzb.card_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yzb.R;
import com.yzb.logic_demo.RechargeActivity;
import com.yzb.logic_demo.SettleActivity;

public class OldMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_main);

        ((Button) findViewById(R.id.rechargeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 跳转充值
                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, RechargeActivity.class);
                startActivityForResult(intent, 0x1);
            }
        });
        ((Button) findViewById(R.id.settleBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 跳转提现
                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, SettleActivity.class);
                startActivityForResult(intent, 0x2);
            }
        });
        ((Button) findViewById(R.id.authorizePay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 授权支付
                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, AuthorizeActivity.class);
                startActivityForResult(intent, 0x3);
            }
        });
        ((Button) findViewById(R.id.ordinaryPayWithoutMethod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 普通支付(无支付方式)
                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, OrdinaryNoMethodActivity.class);
                startActivityForResult(intent, 0x4);
            }
        });
        ((Button) findViewById(R.id.ordinaryPayWithMethod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 普通支付(有支付方式)
                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, OrdinaryActivity.class);
                startActivityForResult(intent, 0x5);
            }
        });
        ((Button) findViewById(R.id.creditBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 银行还款
                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, CreditActivity.class);
                startActivityForResult(intent, 0x7);
            }
        });
        ((Button) findViewById(R.id.chosePayMethod)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, PayMethodActivity.class);
                startActivityForResult(intent, 0x6);
            }
        });
        ((Button) findViewById(R.id.accountBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, AccountActivity.class);
                startActivityForResult(intent, 0x7);
            }
        });
        ((Button) findViewById(R.id.payPwdBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, PayPwdActivity.class);
                startActivityForResult(intent, 0x8);
            }
        });
        ((Button) findViewById(R.id.pointBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, PointActivity.class);
                startActivityForResult(intent, 0x9);
            }
        });
        ((Button) findViewById(R.id.orderBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, OrderActivity.class);
                startActivityForResult(intent, 0x10);
            }
        });
        ((Button) findViewById(R.id.codeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, CodeActivity.class);
                startActivityForResult(intent, 0x11);
            }
        });
        ((Button) findViewById(R.id.chargeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, AccountChargeActivity.class);
                startActivityForResult(intent, 0x12);
            }
        });
        ((Button) findViewById(R.id.customerBankCard)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, CustomerBankActivity.class);
                startActivityForResult(intent, 0x13);
            }
        });
        ((Button) findViewById(R.id.payBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(OldMainActivity.this, AccountPayActivity.class);
                startActivityForResult(intent, 0x14);
            }
        });
    }
}
