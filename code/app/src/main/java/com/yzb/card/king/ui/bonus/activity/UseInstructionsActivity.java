package com.yzb.card.king.ui.bonus.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 代金券使用说明
 * Created by 玉兵 on 2017/12/3.
 */

@ContentView(R.layout.activity_use_instructions)
public class UseInstructionsActivity extends BaseActivity {

    @ViewInject(R.id.tvRule)
    private TextView tvRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        setTitleNmae("使用说明须知");

        if(!TextUtils.isEmpty(getIntent().getStringExtra("useRule"))){

            tvRule.setText(getIntent().getStringExtra("useRule"));
        }

    }
}
