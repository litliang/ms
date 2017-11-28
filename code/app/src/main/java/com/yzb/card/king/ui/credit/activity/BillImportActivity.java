package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

/**
 * 账单导入
 */
public class BillImportActivity extends BaseActivity
{

    private RelativeLayout email_list;

    private LinearLayout panel_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_import);
        initView();
    }

    private void initView()
    {
        email_list = (RelativeLayout) findViewById(R.id.email_list);
        email_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(BillImportActivity.this, ChoseEmailActivity.class));
            }
        });
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        panel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
