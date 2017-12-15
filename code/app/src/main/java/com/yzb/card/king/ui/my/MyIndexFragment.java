package com.yzb.card.king.ui.my;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.AuthenticationInfoBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.WalletRequestUtil;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.activity.AppSettingActivity;
import com.yzb.card.king.ui.app.activity.UserCenterActivity;
import com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity;
import com.yzb.card.king.ui.app.activity.ia.VerifyResultActivity;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.bonus.activity.BoundCenterActivty;
import com.yzb.card.king.ui.bonus.activity.BounsHomeActivity;
import com.yzb.card.king.ui.bonus.activity.VoucherCenterActivity;
import com.yzb.card.king.ui.credit.activity.RepaymentActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardHomeActivity;
import com.yzb.card.king.ui.integral.IntegralHomeActivity;
import com.yzb.card.king.ui.integral.MyCollectActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.CouponsHomeActivity;
import com.yzb.card.king.ui.my.activity.CouponsMySelfActivity;
import com.yzb.card.king.ui.my.activity.MessageActivity;
import com.yzb.card.king.ui.my.activity.MyBillActivity;
import com.yzb.card.king.ui.my.activity.WalletActivity;
import com.yzb.card.king.ui.my.pop.RealNameCertificationDialog;
import com.yzb.card.king.ui.my.pop.ResetPayPasswordDialog;
import com.yzb.card.king.ui.order.AppOrderServeActivity;
import com.yzb.card.king.ui.order.OrderManageActivity;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 类  名：首页 --- “我的”碎片
 * 作  者：Li Yubing
 * 日  期：2016/12/8
 * 描  述：
 */
@ContentView(R.layout.my_index_home_fragment)
public class MyIndexFragment extends BaseFragment {

    @ViewInject(R.id.ivUserHead)
    private ImageView ivUserHead;

    @ViewInject(R.id.ivUserHeadTemp)
    private TextView ivUserHeadTemp;

    @ViewInject(R.id.tvName)
    private TextView tvName;

    @ViewInject(R.id.tvPhone)
    private TextView tvPhone;

    private ImageOptions imageOptions;

    @ViewInject(R.id.llShimingRenzheng)
    private LinearLayout llShimingRenzheng;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        imageOptions = new ImageOptions.Builder()
                //  .setSize(org.xutils.common.util.DensityUtil.dip2px(30), org.xutils.common.util.DensityUtil.dip2px(30))
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                .setUseMemCache(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.bg_my_default)
                .setFailureDrawableId(R.mipmap.bg_my_default)
                .setCircular(false)
                .build();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        initData();

    }

    public void initData()
    {

        UserBean userBean = UserManager.getInstance().getUserBean();

        if (userBean == null) return;

        if (!TextUtils.isEmpty(userBean.getPic())) {

            x.image().bind(ivUserHead, ServiceDispatcher.getImageUrl(userBean.getPic()),
                    imageOptions);

         //   Glide.with(this).load(ServiceDispatcher.getImageUrl(userBean.getPic())).transform(new GlideRoundTransform(getContext(),10)).into(ivUserHead);

            if("2016122218433216121167".equals(userBean.getUserImage())){
                ivUserHeadTemp.setVisibility(View.VISIBLE);
            }else {
                ivUserHeadTemp.setVisibility(View.GONE);
            }


        }else {

            ivUserHeadTemp.setVisibility(View.VISIBLE);
        }

        AuthenticationInfoBean info = userBean.getAuthenticationInfo();

        if(info== null ){
            tvName.setText("未设置姓名");
            tvName.setTextColor(Color.parseColor("#979797"));

        }else {
            tvName.setText(info.getRealName());
            tvName.setTextColor(Color.parseColor("#333333"));
        }

        SpannableString ss = new SpannableString(RegexUtil.hide5PhoneNum(userBean.getAccount()));
        ss.setSpan(new AbsoluteSizeSpan(12,true),  3,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPhone.setText(ss);


        WalletRequestUtil util = new WalletRequestUtil(null);

        //检查用户是否存在支付密码
        util.sendCheckUsePayPassword(userBean.getAmountAccount(), getActivity(), uiHandler);

        if (UserBean.AuthenticationStatus_SUCCESS.equals(getValidStatus())){

            llShimingRenzheng.setVisibility(View.GONE);

        }else {

           llShimingRenzheng.setVisibility(View.VISIBLE);
       }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {//订单
            //查询信息

        }else if(requestCode == VerifyResultActivity.RESULT_REQUEST_CODE){

            if(resultCode == VerifyResultActivity.RESULT_RESULT_SUCCESS){

                initData();

            }
        }

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case REQ_CONFIRM:
                    initData();
                    break;
            }
        }

    }


    @Event(R.id.llJifenBao)
    private void llJifenBao(View view){

        startActivity(new Intent(getActivity(), IntegralHomeActivity.class));

    }

    @Event(R.id.llSetting)
    private void llSetting(View view)
    {
        startActivity(new Intent(getContext(), AppSettingActivity.class));

    }

    @Event({R.id.llUserInfo, R.id.ivUserHead})
    private void userCenter(View view)
    {
        startActivity(new Intent(getContext(), UserCenterActivity.class));
    }

    @Event(R.id.tvMessage)
    private void tvMessage(View view)
    {
        startActivity(new Intent(getActivity(), MessageActivity.class));
    }

    @Event(R.id.tvWallet)
    private void tvWallet(View view)
    {

        goNext(new OnIDValid() {
            @Override
            public void onValid()
            {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            }
        });

    }
    private static final int REQ_CONFIRM = 5;
    @Event(R.id.llShimingRenzheng)
    private void llShimingRenzheng(View view){

        startActivityForResult(new Intent(getActivity(),ExamineIaActivity.class),VerifyResultActivity.RESULT_REQUEST_CODE);
    }


    //礼品卡；
    @Event(R.id.tvGiftCard)
    private void tvGiftCard(View v)
    {
        goNext(new OnIDValid() {
            @Override
            public void onValid()
            {
                startActivity(new Intent(getActivity(), GiftCardHomeActivity.class));//
            }
        });
    }

    //红包；
    @Event(R.id.tvRedPacket)
    private void tvRedPacket(View v)
    {
        goNext(new OnIDValid() {
            @Override
            public void onValid()
            {
                startActivity(new Intent(getActivity(), BounsHomeActivity.class));
            }
        });
    }

    //代金券；
    @Event(R.id.tvCoupon)
    private void tvCoupon(View v)
    {
        Intent moreIntent = new Intent(getContext(), VoucherCenterActivity.class);

        startActivity(moreIntent);
    }

    //优惠券；
    @Event(R.id.tvYouhuiquanMy)
    private void tvYouhuiquanMy(View v)
    {
        Intent moreIntent = new Intent(getContext(), BoundCenterActivty.class);

        startActivity(moreIntent);
    }

    //账户明细
    @Event(R.id.tv_order_detail)
    private void tvDetail(View v)
    {
        goNext(new OnIDValid() {
            @Override
            public void onValid()
            {
                startActivity(new Intent(getActivity(), MyBillActivity.class));
            }
        });
    }

    //扫一扫
    @Event(R.id.tvScanning)
    private void tvScanning(View v)
    {
        Intent intent = new Intent(getActivity(), MipcaActivityCapture.class);
        intent.putExtra("isSaoYiSao", true);
        intent.putExtra(AppConstant.PAYTYPE, AppConstant.NONE);
        startActivity(intent);

    }

    //信用卡还款
    @Event(R.id.llCallback)
    private void llCallback(View view)
    {
        goNext(new OnIDValid() {
            @Override
            public void onValid()
            {
                startActivity(new Intent(getActivity(), RepaymentActivity.class));
            }
        });
    }


    //帮助
    @Event(R.id.llHelp)
    private void llHelp(View v)
    {
        Bundle bundle = new Bundle();
        bundle.putString("category", AppConstant.h5_category_help_list); //
        bundle.putString("titleName", getString(R.string.user_help));
        Intent intent = new Intent(getActivity(), WebViewClientActivity.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivity(intent);
    }


    //收藏
    @Event(R.id.llFav)
    private void llFav(View v)
    {
        readyGo(getActivity(), MyCollectActivity.class);

    }

    @Event(R.id.llBaoxianFuwu)
    private void llBaoxianFuwu(View v)
    {
        ToastUtil.i(getContext(), "敬请期待");
    }

    //全部订单
    @Event(R.id.tvAllOrder)
    private void tvAllOrder(View v)
    {

        //startActivityForResult(new Intent(getActivity(), OrderManageActivity.class), 1000);
        startActivityForResult(new Intent(getActivity(), AppOrderServeActivity.class), 1000);

    }

    //未支付
    @Event(R.id.tvNopay)
    private void tvNopay(View v)
    {
        Intent it = new Intent(getActivity(), OrderManageActivity.class);
        it.putExtra("orderStatus", "1");
        startActivityForResult(it, 1000);
    }

    //已支付
    @Event(R.id.tvPayed)
    private void tvPayed(View v)
    {
        Intent it = new Intent(getActivity(), OrderManageActivity.class);
        it.putExtra("orderStatus", "2");
        startActivityForResult(it, 1000);
    }

    //已退款
    @Event(R.id.tvTuikuan)
    private void tvTuikuan(View v)
    {

        Intent it = new Intent(getActivity(), OrderManageActivity.class);
        it.putExtra("orderStatus", "4");
        startActivityForResult(it, 1000);

        // startActivityForResult(new Intent(getActivity(), TestActivity.class), 1000);
    }

    @Event(R.id.llFinanceLife)
    private void finaceLife(View v)
    {
        Bundle bundle = new Bundle();
        bundle.putString("url", ServiceDispatcher.URL_JINRONG_LIFE);
        bundle.putString(WebViewClientActivity.TITLE_NAME, "金融生活");
        readyGoWithBundle(getActivity(), WebViewClientActivity.class, bundle);
    }


    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (RealNameCertificationDialog.WHAT_LOOK == msg.what) {
                //进入实名认证流程
                startActivityForResult(new Intent(getActivity(),ExamineIaActivity.class),VerifyResultActivity.RESULT_REQUEST_CODE);
            } else if (0 == msg.what) {

                ResetPayPasswordDialog.getInstance().setDataHandler(null).show(getFragmentManager(), "");
            }
        }
    };

    private void goNext(OnIDValid valid)
    {
        if (valid == null) return;
        if (UserBean.AuthenticationStatus_SUCCESS.equals(getValidStatus())) {
            valid.onValid();
        } else {
            RealNameCertificationDialog.getInstance().setDataHandler(uiHandler).show(getFragmentManager(), "");
        }
    }


}
