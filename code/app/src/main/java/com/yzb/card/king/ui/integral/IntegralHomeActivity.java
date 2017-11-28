package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.popup.LeftPopWindow;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 类  名：积分宝首页
 * 作  者：Li Yubing
 * 日  期：2017/10/12
 * 描  述：
 */
@ContentView(R.layout.activity_integral_home)
public class IntegralHomeActivity extends BaseActivity{

    @ViewInject(R.id.slideShowView1)
    private SlideShow1ItemView slideShowView1;

    @ViewInject(R.id.llTitle)
    private View llTitle;

    private   LeftPopWindow letfWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initSlidingViewData();
    }

    private void initSlidingViewData() {

        slideShowView1.setParam(AppConstant.POINT_HOMEPAGE,cityId,AppConstant.integral_type_parentid);

    }

    @Event(R.id.rl_share_app)
    private void rlShareAppAction(View v){
//        ShareDialogFragment dialogFragment = ShareDialogFragment.getInstance("", "");
//        dialogFragment.show(getFragmentManager(), "ShareDialogFragment");

//        HotelOtherProductIntroFragmentDialog hotelOtherProductIntroFragmentDialog = new HotelOtherProductIntroFragmentDialog();
//
//        hotelOtherProductIntroFragmentDialog.show(getFragmentManager().beginTransaction(), HotelOtherProductIntroFragmentDialog.HOTEL_PRODUCT_MENU);
    }

    @Event(R.id.rl_call_us)
    private void rlCallUsAction(View v){

        Bundle bundle = new Bundle();
        bundle.putString("category", AppConstant.h5_category_contacts_us); //
        bundle.putString("titleName",getString(R.string.integral_contact_us));
        Intent intent = new Intent(this, WebViewClientActivity.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivity(intent);

//        BaseDialog delDialog = new BaseDialog(getContext());
//        delDialog.show();

//        HotelProductRoomInfoFragmentDialog hotelProductFragmentDialog = new HotelProductRoomInfoFragmentDialog();
//
//        hotelProductFragmentDialog.show(getFragmentManager().beginTransaction(), "roomFt");

    }

    @Event(R.id.rl_jfdh)
    private void rlJfdhAction(View v){// 积分兑换；
        Intent itEarnPointsActivity1 = new Intent(this, IntegralExchangeActivity.class);
        startActivity(itEarnPointsActivity1);

//        HotelOtherProductInfoIntroFragmentDialog hotelProductFragmentDialog = new HotelOtherProductInfoIntroFragmentDialog();
//
//        hotelProductFragmentDialog.show(getFragmentManager().beginTransaction(), "roomFt");
    }

    @Event(R.id.tvZhuanJiFen)
    private void tvZhuanJiFenAction(View v){//赚积分
        Intent itEarnPointsActivity = new Intent(this, EarnPointsActivity.class);
        startActivity(itEarnPointsActivity);
    }

    @Event(R.id.tvKaWangHui)
    private void tvKaWangHuiAction(View v){//卡王汇

    }

    @Event(R.id.tvJiFenYouHui)
    private void tvJiFenYouHuiAction(View v){//优惠积分
        if(letfWindow == null){

            letfWindow = new LeftPopWindow(this);
        }

        letfWindow.showAsDropDown(llTitle);
    }

}
