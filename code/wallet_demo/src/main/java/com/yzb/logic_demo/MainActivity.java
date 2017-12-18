package com.yzb.logic_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yzb.R;
import com.yzb.card_wallet.OldMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        ((Button) findViewById(R.id.oldBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 旧版接口
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, OldMainActivity.class);
                startActivityForResult(intent, 0x1);
            }
        });

        ((Button) findViewById(R.id.balanceBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 账户余额接口
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AccountBalanceActivity.class);
                startActivityForResult(intent, 0x2);
            }
        });
        ((Button) findViewById(R.id.accountBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 账户列表接口
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AccountListActivity.class);
                startActivityForResult(intent, 0x3);
            }
        });

        ((Button) findViewById(R.id.addPayMethodBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 支付方式设置接口
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PayMethodAddActivity.class);
                startActivityForResult(intent, 0x4);
            }
        });
        ((Button) findViewById(R.id.defaultPayMethodBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 默认支付方式接口
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PayMethodDefaultActivity.class);
                startActivityForResult(intent, 0x5);
            }
        });
        ((Button) findViewById(R.id.seqPayMethodBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 排序后的支付方式接口
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PayMethodSeqActivity.class);
                startActivityForResult(intent, 0x6);
            }
        });

        ((Button) findViewById(R.id.addPaypswdBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 设置支付密码
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PaypswdAddActivity.class);
                startActivityForResult(intent, 0x7);
            }
        });
        ((Button) findViewById(R.id.validatePaypswdBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 校验支付密码
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PaypswdValidateActivity.class);
                startActivityForResult(intent, 0x8);
            }
        });

        ((Button) findViewById(R.id.payRequestBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 付款（现金、快捷）
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PayRequestActivity.class);
                startActivityForResult(intent, 0x9);
            }
        });

        ((Button) findViewById(R.id.rechargeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 充值
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RechargeActivity.class);
                startActivityForResult(intent, 0x10);
            }
        });

        ((Button) findViewById(R.id.settleBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 提现
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettleActivity.class);
                startActivityForResult(intent, 0x11);
            }
        });

        ((Button) findViewById(R.id.transferBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 转账
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TransferActivity.class);
                startActivityForResult(intent, 0x12);
            }
        });

        ((Button) findViewById(R.id.freePayBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 免密支付
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FreePayActivity.class);
                startActivityForResult(intent, 0x13);
            }
        });

        ((Button) findViewById(R.id.earnBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 收益
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EarnActivity.class);
                startActivityForResult(intent, 0x13);
            }
        });

        ((Button) findViewById(R.id.baseInfoBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 基本信息
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BaseInfoActivity.class);
                startActivityForResult(intent, 0x13);
            }
        });

        ((Button) findViewById(R.id.bankListBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 基本信息
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BankListActivity.class);
                startActivityForResult(intent, 0x13);
            }
        });
        ((Button) findViewById(R.id.bankListBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 基本信息
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BankListActivity.class);
                startActivityForResult(intent, 0x13);
            }
        });

        ((Button) findViewById(R.id.payMethodLimitBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 基本信息
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PayMethodLimitActivity.class);
                startActivityForResult(intent, 0x14);
            }
        });


    }
}
