package com.yzb.card.king.ui.citylife.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.CommonUtil;

/**
 * 城市生活详情；
 * created by gegnqiyun ;
 * 2016.7.4  --
 */
public class CityLifeDetailActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_life_detail);
        initView();
    }

    private void initView() {

        ImageView iv = (ImageView) findViewById(R.id.iv);
        ViewGroup.LayoutParams vl = iv.getLayoutParams();
        vl.width = CommonUtil.getScreenWidth(this);
        vl.height = (int) (CommonUtil.getScreenWidth(this) * 801 / 420);
        iv.setLayoutParams(vl);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panel_back1:
                finish();
                break;
        }
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }
}
