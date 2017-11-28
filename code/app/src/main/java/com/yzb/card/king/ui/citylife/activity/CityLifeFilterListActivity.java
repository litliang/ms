package com.yzb.card.king.ui.citylife.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.CommonUtil;

/**
 * 城市生活分类；
 * created by gegnqiyun ;
 * 2016.7.4
 */
public class CityLifeFilterListActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_life_filter_list);
        initView();
    }

    private void initView() {
        findViewById(R.id.panel_back1).setOnClickListener(this);
        ImageView iv = (ImageView) findViewById(R.id.iv);

        ViewGroup.LayoutParams vl = iv.getLayoutParams();
        vl.width = CommonUtil.getScreenWidth(this);
        vl.height = (int) (CommonUtil.getScreenWidth(this) * 723 / 450);
        iv.setLayoutParams(vl);

        iv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panel_back1:
                finish();
                break;
            case R.id.iv:
                readyGo(this, CityLifeDetailActivity.class);
                break;
        }
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }
}
