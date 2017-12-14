package com.yzb.card.king.ui.integral.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.TestActivity;
import com.yzb.card.king.ui.app.activity.HotelBillActivity;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductInfoIntroFragmentDialog;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductIntroFragmentDialog;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.appwidget.popup.LeftPopWindow;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.integral.EarnPointsActivity;
import com.yzb.card.king.ui.integral.IntegralExchangeActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.ui.other.activity.JinrongLifeWebviewActivity;
import com.yzb.card.king.ui.other.activity.VideoWebviewActivity;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.ticket.activity.RescheduleListActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.LogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 金融生活首页Fragment；
 *
 * @author gengqiyun
 * @date 2016.6.6
 */
@ContentView(R.layout.fragment_integral_main)
public class IntegralMainFragment extends BaseFragment {

    @Event(R.id.llOne)
    private void llOne(View view)
    {//房抵快贷

        UserManager userManager = UserManager.getInstance();


        String sessionId = "";

        if (userManager.isLogin()) {

            sessionId = AppConstant
                    .sessionId;

        }

        String oneUrl = "http://116.228.184.115/index/index/index?sessionId=" + sessionId + "&identification=" +  AppConstant.UUID+"&cityId="+cityId;
        Bundle bundle = new Bundle();
        bundle.putString("url", oneUrl); //
        bundle.putString("titleName", "房抵快贷");
        Intent intent = new Intent(getContext(), JinrongLifeWebviewActivity.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivity(intent);
    }

    @Event(R.id.llTwo)
    private void llTwo(View view)
    {//超级信用贷
        UserManager userManager = UserManager.getInstance();


        String sessionId = "";

        if (userManager.isLogin()) {

            sessionId = AppConstant
                    .sessionId;

        }

        String oneUrl = "http://116.228.184.115/index/credit_loan/index?sessionId=" + sessionId+ "&identification=" + AppConstant.UUID+"&cityId="+cityId;
        Bundle bundle = new Bundle();
        bundle.putString("url", oneUrl); //
        bundle.putString("titleName", "超级信用贷");
        Intent intent = new Intent(getContext(), JinrongLifeWebviewActivity.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivity(intent);

    }

    @Event(R.id.llThree)
    private void llThree(View view){
      //  startActivityForResult(new Intent(getActivity(), TestActivity.class), 1000);

//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");//相片类型
//        startActivityForResult(intent, 10001);

        //String oneUrl = "http://v.youku.com/v_show/id_XMjY1MDYzOTQ1Mg==.html?f=49258329&spm=a2hww.20023042.m_223465.5~5~5~5!2~5~5~A&from=y1.3-idx-beta-1519-23042.223465.4-1";
        String oneUrl ="https://v.qq.com/iframe/player.html?vid=f0549u31gbu&tiny=0&auto=0";
        Bundle bundle = new Bundle();
        bundle.putString("url", oneUrl); //
        bundle.putString("titleName", "房抵快贷");
        Intent intent = new Intent(getContext(), VideoWebviewActivity.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivity(intent);
    }

}
