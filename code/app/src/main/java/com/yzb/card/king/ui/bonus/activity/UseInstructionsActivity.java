package com.yzb.card.king.ui.bonus.activity;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        setTitleNmae("使用说明须知");
    }
}
