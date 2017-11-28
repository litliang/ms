package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.credit.activity.DiscountInfoActivity;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.activity.CreditOnlineCardActivity;
import com.yzb.card.king.ui.credit.bean.LeftPopItem;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.manage.DiscountManager;

import java.util.List;

/**
 * 赚积分；
 *
 * @author gengqiyun
 * @date 2016.6.6
 */
public class EarnPointsActivity extends BaseActivity implements View.OnClickListener {
    private SlideShow1ItemView slideShowView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_points);
        assignViews();

        initData();
    }

    private void initData() {
        getDataList();

    }

    private void getDataList() {
//        new CommentTask("ChildTypeList") {
//
//            @Override
//            protected void parseJson(String data) {
//                List<LeftPopItem> list = JSON.parseArray(data, LeftPopItem.class);
//                DiscountManager.getInstance().setDataList(list);
//            }
//
//            @Override
//            protected void setParam(Map<String, String> param) {
//                param.put("typeId", "85");
//            }
//        }.execute();
    }

    private void assignViews() {
        slideShowView1 = (SlideShow1ItemView) findViewById(R.id.slideShowView1);
        initSlidingViewData();
        findViewById(R.id.rl_bank_jf).setOnClickListener(this);
        findViewById(R.id.rl_store_jf).setOnClickListener(this);
        findViewById(R.id.rl_sbxyk).setOnClickListener(this);
        findViewById(R.id.rl_share_app).setOnClickListener(this);
    }

    private void initSlidingViewData() {

        slideShowView1.setParam(AppConstant.flow_type_parentid,selectedCity.cityId,"1009");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bank_jf: // 银行积分；
                //ToastUtil.i(this, "进入商家积分优惠筛选页面,该商家优惠活动针对银行积分的优惠");
                List<LeftPopItem> list = DiscountManager.getInstance().getDataList();

                if (list != null) {

                    Intent intent = new Intent(EarnPointsActivity.this, DiscountInfoActivity.class);
                    LeftPopItem item = list.get(0);
                    intent.putExtra("data", item);
                    intent.putExtra("title","银行积分");
                    startActivity(intent);

                } else {

                    finish();
                }

                break;
            case R.id.rl_store_jf: // 商家积分；
                // ToastUtil.i(this, "进入商家积分优惠筛选页面,该商家优惠活动针对商家积分的优惠");

                List<LeftPopItem> list1 = DiscountManager.getInstance().getDataList();

                if (list1 != null) {

                    Intent intent = new Intent(EarnPointsActivity.this, DiscountInfoActivity.class);
                    LeftPopItem item = list1.get(0);
                    intent.putExtra("data", item);
                    intent.putExtra("title","商家积分");
                    startActivity(intent);

                } else {

                    finish();
                }
                break;
            case R.id.rl_sbxyk:
//                readyGo(this, AddCreditActivity.class);
                Intent intent = new Intent();
                intent.setClass(EarnPointsActivity.this, CreditOnlineCardActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_share_app:
                //分享APP可赚取积分。
                ShareDialogFragment sdf = ShareDialogFragment.getInstance("", "");
                sdf.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
        }
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    public void goBack(View view) {
        finish();
    }

}
