package com.yzb.card.king.ui.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.bean.user.AddrInfoBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity;
import com.yzb.card.king.ui.app.activity.ia.VerifyResultActivity;
import com.yzb.card.king.ui.app.manager.ValidManager;
import com.yzb.card.king.ui.app.popup.GetPicPopup;
import com.yzb.card.king.ui.app.presenter.UpdateUserInfoPresenter;
import com.yzb.card.king.ui.app.presenter.UserCenterPres;
import com.yzb.card.king.ui.appwidget.picks.CountryCityWheelView;
import com.yzb.card.king.ui.appwidget.picks.SelectCountryPick;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.WalletActivity;
import com.yzb.card.king.ui.my.diaolog.UpdateNameDialog;
import com.yzb.card.king.ui.my.pop.RealNameCertificationDialog;
import com.yzb.card.king.ui.my.pop.ResetPayPasswordDialog;
import com.yzb.card.king.ui.other.controller.SelectPicController;
import com.yzb.card.king.ui.other.widget.RoundImageView;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

@ContentView(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity implements
        View.OnClickListener, BaseViewLayerInterface {
    private static final int REQ_UPDATE_EMAIL = 1010;
    private static final int MODIFY_NICK_NAME = 3;
    private static final int MODIFY_BIND_MOBILE = 4;
    private static final int VALID_ID = 5;
    private GetPicPopup getPicPopup;
    private View contentView;
    private UserManager userManager;
    private UserBean userBean;
    private UserCenterPres presenter;
    private UpdateNameDialog updateNameDialog;
    private TimePickerView pvTime;
    private TextView tvBirthday;
    private boolean man;

    @ViewInject(R.id.tvMan)
    private TextView tvMan;

    @ViewInject(R.id.vMan)
    private View vMan;

    @ViewInject(R.id.tvWomen)
    private View tvWomen;

    @ViewInject(R.id.vWomen)
    private View vWomen;

    @ViewInject(R.id.tvName)
    private TextView tvName;

    @ViewInject(R.id.tvLabel)
    private View tvLabel;

    @ViewInject(R.id.tvNickName)
    private EditText tvNickName;

    @ViewInject(R.id.tvRegion)
    private TextView tvRegion;


    @ViewInject(R.id.tvEmail)
    private TextView tvEmail;

    @ViewInject(R.id.ivPhoto)
    private ImageView ivPhoto;

    @ViewInject(R.id.llEmail)
    private LinearLayout llEmail;

    private UpdateUserInfoPresenter userInfoPresenter;
    private SelectCountryPick countryPick;
    private NationalCountryBean country;
    private NationalCountryBean province;
    private NationalCountryBean city;
    private SelectPicController picController;

    private ImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                .setLoadingDrawableId(R.mipmap.user_head_red)
                .setFailureDrawableId(R.mipmap.user_head_red)
                .setCircular(false)
                .build();
        if (presenter == null) {
            presenter = new UserCenterPres();
        }
        userInfoPresenter = new UpdateUserInfoPresenter(this);
        initView();
        initListener();
        initData();
    }

    private void initListener() {

    }

    @SuppressLint("NewApi")
    private void initView() {
        contentView = findViewById(R.id.content);
        setTitleNmae("个人主页");
        tvBirthday = (TextView) findViewById(R.id.tvBirthday);
        tvNickName.setBackground(null);
    }


    private void initData() {
        userManager = UserManager.getInstance();
        userBean = userManager.getUserBean();
        initPicController();
        setName();
        if (userBean.getNickName().equals("未设置昵称")) {
            tvNickName.setHint(userBean.getNickName());
        } else
            tvNickName.setText(userBean.getNickName());
        initPhoto();
        initSex();
        initBirthday();
        setRegion();
        getDataList();
        getConsume();

        tvEmail.setText(TextUtils.isEmpty(userBean.getEmail()) ? "去绑定" : userBean.getEmail());

        llEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, BindEmailActivity.class);
                intent.putExtra("email", userBean.getEmail());
                startActivityForResult(intent, REQ_UPDATE_EMAIL);
            }
        });
    }

    private void initPhoto() {
        x.image().bind(ivPhoto, ServiceDispatcher.getImageUrl(userBean.getPic()), imageOptions);
    }

    private void initSex() {
        selectSex("1".equals(UserManager.getInstance().getUserBean().getSex()));
    }

    private void initBirthday() {
        tvBirthday.setText(DateUtil.date2String(userBean.getBirthday(), "yyyy年MM月dd日"));
    }

    private void initPicController() {
        picController = new SelectPicController(this);
        picController.setOnGetPicListener(new SelectPicController.OnGetPicListener() {
            @Override
            public void onGetPic(Bitmap bitmap) {
                getPicPopup.dismiss();
                uploadImages(bitmap);
            }
        });
    }

    private void setRegion() {
        AddrInfoBean info = userBean.getAddrInfo();
        if (info != null) {

            if (!TextUtils.isEmpty(info.getCountryName())) {

                StringBuilder sb = new StringBuilder();
                sb.append(info.getCountryName());
                sb.append(info.getProvinceName());
                if (info.getProvinceName() != null && !info.getProvinceName().equals(info.getCityName()))
                    sb.append(info.getCityName());
                sb.append(info.getRegionName());

                tvRegion.setText(sb.toString());
            } else {

                tvRegion.setText("请选择地区");
            }

        } else {

            tvRegion.setText("请选择地区");
        }
    }

    private void setName() {
        String name = userBean.getAuthenticationInfo().getRealName();
        if (TextUtils.isEmpty(name)) {
            tvLabel.setVisibility(View.VISIBLE);
        } else {

            tvLabel.setVisibility(View.GONE);
            tvName.setText(name);

        }
    }

    @Event(R.id.llName)
    private void llName(View view) {
        goNext(new BaseFragment.OnIDValid() {
            @Override
            public void onValid() {
            }
        });

    }

    private void goNext(BaseFragment.OnIDValid valid) {
        if (valid == null) return;
        if (UserBean.AuthenticationStatus_SUCCESS.equals(getValidStatus())) {
            valid.onValid();
        } else {
            //进入实名认证流程
            startActivityForResult(new Intent(this, ExamineIaActivity.class), VerifyResultActivity.RESULT_REQUEST_CODE);
        }
    }

    @Event(R.id.llMan)
    private void selectMan(View view) {
        if (hasValid()) return;
        selectSex(true);
    }

    @Event(R.id.llWomen)
    private void selectWomen(View view) {
        if (hasValid()) return;
        selectSex(false);
    }

    /**
     * true男
     *
     * @param man
     */
    private void selectSex(boolean man) {
        this.man = man;
        tvWomen.setVisibility(View.GONE);
        if (man) {
            tvMan.setEnabled(false);
            tvWomen.setEnabled(false);
        } else {
            tvMan.setText("女");
            tvMan.setEnabled(false);
            tvWomen.setEnabled(true);
        }
    }

    @Event(R.id.headerLeft)
    private void back(View view) {
        finish();
    }

    @Event(R.id.llPhoto)
    private void setPhoto(View v) {
        if (getPicPopup == null) {
            getPicPopup = new GetPicPopup(this);
        }
        getPicPopup.showAtLocation(contentView, Gravity.CENTER, 0, 0);

    }

    @Event(R.id.llNickName)
    private void setNickName(View view) {
//        if (updateNameDialog == null) {
//            updateNameDialog = new UpdateNameDialog();
//            updateNameDialog.setNickName(userBean.getNickName());
//            updateNameDialog.setListener(new OnItemClickListener<String>() {
//                @Override
//                public void onItemClick(String data) {
//                    tvNickName.setText(data);
//                }
//            });
//        }
//        updateNameDialog.show(getSupportFragmentManager(), "nickName");
    }

    @Event(R.id.llBirthday)
    private void setBirthday(View view) {
        if (hasValid()) return;
        if (pvTime == null) {
            initTimePicker();
        }
        pvTime.show();
    }

    private boolean hasValid() {
        return UserManager.HAS_VALID.equals(UserManager.getInstance().getStatus());
    }

    @Event(R.id.btSave)
    private void save(View v) {
        Map<String, Object> param = new HashMap<>();
        param.put("nickName", tvNickName.getText().toString());
        param.put("sex", man ? "1" : "2");
        param.put("birthday", getBirthday());

        if (country != null && province != null && city != null) {
            param.put("countryId", country.getCityId());
            param.put("provinceId", province.getCityId());
            if (isDirectCity(province.getCityName())) {
                param.put("cityId", province.getCityId());
                param.put("cityRegionId", city.getCityId());
            } else {
                param.put("cityId", city.getCityId());
            }
        }

        userInfoPresenter.update(param);
    }


    @Override
    public void finish() {
        save(null);
        super.finish();
    }

    @NonNull
    private String getBirthday() {
        return tvBirthday.getText().toString().replace("年", "-").replace("月", "-").replace("日", "");
    }

    private void initTimePicker() {
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvBirthday.setText(DateUtil.date2String(date, "yyyy年MM月dd日"));
            }

            @Override
            public void onCancel() {

            }
        });
    }


    private boolean isDirectCity(String name) {
        String[] city = UiUtils.getStringArray(R.array.direct_city);
        for (int i = 0; i < city.length; i++) {
            if (city[i].equals(name)) return true;
        }
        return false;
    }

    @Event(R.id.llAddress)
    private void setAddress(View view) {

        if (countryPick == null) {
            countryPick = new SelectCountryPick(this, CountryCityWheelView.Type.ALL);
            countryPick.setCancelable(true);
            countryPick.setListener(new SelectCountryPick.SelectedData() {
                @Override
                public void getSelectedData(List<NationalCountryBean> data) {
                    LogUtil.e(JSON.toJSONString(data));
                    if (data != null) {
                        country = data.get(0);
                        province = data.get(1);
                        city = data.get(2);
                        tvRegion.setText(country.getCityName() + province.getCityName() + city.getCityName());
                    }
                }
            });
        }
        countryPick.show();
    }


    private void getDataList() {
        presenter.getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCamera:
                picController.getPicFormCamera();
                break;
            case R.id.tvAlbum:
                picController.getPicFromGallery();
                break;
            case R.id.tvCancel:
                getPicPopup.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        picController.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_UPDATE_EMAIL:
                //
                userBean = UserManager.getInstance().getUserBean();
                tvEmail.setText(TextUtils.isEmpty(userBean.getEmail()) ? "去绑定" : userBean.getEmail());
                break;

            case MODIFY_NICK_NAME:
            case MODIFY_BIND_MOBILE:
            case VALID_ID:
                ValidManager validManager = ValidManager.getValidManager();
                validManager.finishAll();
                refresh();
                break;
        }

        if (requestCode == VerifyResultActivity.RESULT_REQUEST_CODE) {

            if (resultCode == VerifyResultActivity.RESULT_RESULT_SUCCESS) {

                initData();

            }

        }
    }

    private void refresh() {
        getDataList();
    }

    private void getConsume() {
        presenter.getConsume(this);
    }

    private void uploadImages(Bitmap bitmap) {
        ivPhoto.setImageDrawable(new BitmapDrawable(bitmap));
        presenter.uploadImage(bitmap);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        UiUtils.shortToast(o + "");
    }
}
