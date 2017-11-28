package com.yzb.card.king.ui.bonus.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.bonus.adapter.RedBagSendDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名： 红包send的领取详情
 * 作 者： gaolei
 * 日 期：2016年12月26日
 * 描 述：红包send的领取详情
 */
public class RedBagSendDetailActivity extends BaseActivity implements RedBagSendDetailAdapter.OnMyItemClickListener {
    private RecyclerView recycler;
    private RedBagSendDetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_bag_send_detail);
        initView();
        initData();
    }


    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            list.add("i" + i);
        }
        adapter = new RedBagSendDetailAdapter(this,list);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new DecorationUtil(2));
        recycler.setAdapter(adapter);
        adapter.setOnMyItemClickListener(this);
    }

    private void initView() {
        recycler = (RecyclerView)findViewById(R.id.red_bag_detail_recyclerview);
    }

    @Override
    public void OnMyItemClick(RecyclerView parent, View view, int position, List<String> data) {
        Toast.makeText(this, "没有了,够详细了哇", Toast.LENGTH_SHORT).show();
    }
}
