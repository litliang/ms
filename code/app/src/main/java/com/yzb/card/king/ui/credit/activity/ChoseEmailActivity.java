package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.adapter.ChoseEmailAdapter;

/**
 * 邮箱选择
 */
public class ChoseEmailActivity extends BaseActivity
{

    private RecyclerView recyclerView;

    private ChoseEmailAdapter adapter;

    private LinearLayout panel_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_email);

        initView();
    }

    private void initView()
    {
        adapter = new ChoseEmailAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_email);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        panel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        adapter.setOnItemClickListener(new ChoseEmailAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int postion)
            {
                startActivity(new Intent(ChoseEmailActivity.this, ForwardMailActivity.class));
            }
        });
    }
}
