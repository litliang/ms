package com.yzb.card.king.ui.luxury.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.discount.activity.SearchDiscountActivity;
import com.yzb.card.king.ui.luxury.fragment.MsThirdClassicFragment;
import com.yzb.card.king.ui.base.BaseCityChangeActivity;
import com.yzb.card.king.ui.home.AppHomeActivity;

/**
 * @author gengqiyun
 * @date 2016.6.16
 * 美食三级页面；
 */
public class MsThirdClassicActivity extends BaseCityChangeActivity implements View.OnClickListener
{
    private String typeParentId;

    private TextView titlebarTitle;
    private View llParent;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_third_classic_main);
        recvIntentData();
        assignViews();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                MsThirdClassicFragment.newInstance(typeParentId, "")).commit();
    }

    private void recvIntentData()
    {
        // 美食首页点击的子分类item的id；
        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        typeParentId = bundle == null ? "" : bundle.getString("typeParentId", "");
        title = bundle == null ? getString(R.string.category_ms) :
                bundle.getString("typeName", getString(R.string.category_ms));
    }

    private void assignViews()
    {
        titlebarTitle = (TextView) findViewById(R.id.titlebar_title);
        llParent = findViewById(R.id.llParent);

        findViewById(R.id.iv_top_right).setOnClickListener(this);

        findViewById(R.id.iv_search).setOnClickListener(this);
        findViewById(R.id.panel_back).setOnClickListener(this);
        titlebarTitle.setText(title);
        findViewById(R.id.rl_xyk).setOnClickListener(this);
        findViewById(R.id.rl_jfb).setOnClickListener(this);
        findViewById(R.id.rl_shyh).setOnClickListener(this);
        findViewById(R.id.rl_shfw).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panel_back:
                finish();
                break;
            case R.id.iv_top_right:
                break;
            case R.id.iv_search:
                readyGo(this, SearchDiscountActivity.class);
                break;

            case R.id.rl_xyk:
                Intent intent0 = new Intent(this, AppHomeActivity.class);
                intent0.putExtra("pagetype_from_msmain", 0);
                startActivity(intent0);
                break;
            case R.id.rl_jfb:
                Intent intent1 = new Intent(this, AppHomeActivity.class);
                intent1.putExtra("pagetype_from_msmain", 1);
                startActivity(intent1);
                finish();
                break;
            case R.id.rl_shyh:
                Intent intent2 = new Intent(this, AppHomeActivity.class);
                intent2.putExtra("pagetype_from_msmain", 2);
                startActivity(intent2);
                finish();
                break;
            case R.id.rl_shfw:
                Intent intent3 = new Intent(this, AppHomeActivity.class);
                intent3.putExtra("pagetype_from_msmain", 3);
                startActivity(intent3);
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
