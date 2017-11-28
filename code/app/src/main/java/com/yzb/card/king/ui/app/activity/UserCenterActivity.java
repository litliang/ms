package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.LevelInfoBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.user.LogoutRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.appdialog.WeiXinBarcodeDialog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.UpdateMobileActivity;
import com.yzb.card.king.ui.my.fragment.LogoutConfirmDialog;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_user_center)
public class UserCenterActivity extends BaseActivity {

    private static final int REQ_UPDATE_EMAIL = 2;
    private static final int REQ_UPDATE_USER_INFO = 3;
    private static final int REQ_UPDATE_MOBILE = 4;
    private static final int REQ_CONFIRM = 5;

    private LinearLayout.LayoutParams lp;
    private UserBean userBean;


    @ViewInject(R.id.btExist)
    private View btExist;

    @ViewInject(R.id.tvNickName)
    private TextView tvNickName;

    @ViewInject(R.id.ivPhoto)
    private ImageView ivPhoto;

    @ViewInject(R.id.tvEmail)
    private TextView tvEmail;

    @ViewInject(R.id.tvMobile)
    private TextView tvMobile;

    @ViewInject(R.id.tvDengji)
    private TextView tvDengji;

    @ViewInject(R.id.tvConfirm)
    private TextView tvConfirm;

    @ViewInject(R.id.ivDengji)
    private ImageView ivDengji;

    private UserManager.OnUpdateListener onUpdateListener;

    private ImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);

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

        initView();
        initListener();
        initData();
    }

    private void initListener()
    {
        onUpdateListener = new UserManager.OnUpdateListener() {
            @Override
            public void onUpdate()
            {
                x.image().bind(ivPhoto, ServiceDispatcher.getImageUrl(
                        UserManager.getInstance().getUserBean().getPic()), imageOptions);
            }
        };
        UserManager.getInstance().setOnUpdateListener(onUpdateListener);
    }

    private void initView()
    {
        setTitleNmae("个人信息");
        lp = (LinearLayout.LayoutParams) btExist.getLayoutParams();
    }

    @Event(R.id.llWeixin)
    private void llWeixin(View v)
    {

        WeiXinBarcodeDialog dialog = new WeiXinBarcodeDialog(this);
        dialog.show();
    }

    @Event(R.id.llUserInfo)
    private void userInfo(View v)
    {
        startActivityForResult(new Intent(this, UserInfoActivity.class), REQ_UPDATE_USER_INFO);
    }

    @Event(R.id.llLevel)
    private void level(View view)
    {
        startActivity(new Intent(this, MemberLevelActivity.class));
    }


    @Event(R.id.llEmail)
    private void email(View v)
    {
        Intent intent = new Intent(this, BindEmailActivity.class);
        intent.putExtra("email", userBean.getEmail());
        startActivityForResult(intent, REQ_UPDATE_EMAIL);
    }

    @Event(R.id.llMobile)
    private void mobile(View v)
    {
        startActivityForResult(new Intent(this, UpdateMobileActivity.class), REQ_UPDATE_MOBILE);
    }

    @Event(R.id.llConfirm)
    private void confirm(View v)
    {
        startActivityForResult(getNextIntent(), REQ_CONFIRM);
    }

    @NonNull
    private Intent getNextIntent()
    {
        if ("3".equals(UserManager.getInstance().getUserBean().getAuthenticationStatus()))//审核中
        {
            return new Intent(this, ValidFinishActivity.class);
        } else {
            return new Intent(this, IDAuthenticationActivity.class);
        }
    }


    @Event(R.id.llTravellerInfo)
    private void travellerInfo(View v)
    {
        startActivity(new Intent(this, PassengerManageActivity.class));
    }

    @Event(R.id.llBillTitle)
    private void billTitle(View v)
    {
        startActivity(new Intent(this, DebitRiseManageActivity.class));
    }

    @Event(R.id.llAddress)
    private void address(View v)
    {
        startActivity(new Intent(this, AddressManageActivity.class));
    }

    @Event(R.id.llConnector)
    private void connector(View v)
    {
        startActivity(new Intent(this, ConnectorManageActivity.class));
    }

    @Event(R.id.btExist)
    private void exist(View view)
    {

        LogoutConfirmDialog dialog = new LogoutConfirmDialog();
        dialog.setListener(new LogoutConfirmDialog.OnPositiveListener() {
            @Override
            public void onPositive()
            {
                logout();
            }
        });
        dialog.show(getSupportFragmentManager(), "Logout");
    }

    private void logout()
    {
        LogoutRequest request = new LogoutRequest();
        request.sendRequest(new HttpCallBackImpl() {
            @Override
            public void onSuccess(Object o)
            {
                UserManager.getInstance().setUserBean(null);
                startActivity(new Intent(UserCenterActivity.this, LoginActivity.class));
                finish();

                GlobalApp.backFlag = true;
                UserManager.getInstance().setUserBean(null);
                AppConstant.sessionId = "";
                AppConstant.handleSessionId(AppConstant.sessionId);

            }

            @Override
            public void onFailed(Object o)
            {
                UiUtils.shortToast("退出失败");
            }
        });
    }


    private void initData()
    {
        userBean = UserManager.getInstance().getUserBean();
        tvNickName.setText(userBean.getNickName());
        tvEmail.setText(TextUtils.isEmpty(userBean.getEmail()) ? "去绑定" : userBean.getEmail());
        SpannableString ss = new SpannableString(RegexUtil.hide5PhoneNum(userBean.getAccount()));
        ss.setSpan(new AbsoluteSizeSpan(12,true),  3,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMobile.setText(ss);
        tvConfirm.setText(UserManager.getInstance().getStatus());

        if(!TextUtils.isEmpty(userBean.getPic())){
            x.image().bind(ivPhoto, ServiceDispatcher.getImageUrl(userBean.getPic()), imageOptions);
        }

        LevelInfoBean lBean = userBean.getLevelInfo();

        tvDengji.setText(lBean.getLevelName());

        if (!TextUtils.isEmpty(lBean.getLevelLogo())) {
            x.image().bind(ivDengji, ServiceDispatcher.getImageUrl(lBean.getLevelLogo()));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_UPDATE_EMAIL:
                case REQ_UPDATE_USER_INFO:
                case REQ_UPDATE_MOBILE:
                case REQ_CONFIRM:
                    initData();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        UserManager.getInstance().unRegistListener(onUpdateListener);
    }
}
