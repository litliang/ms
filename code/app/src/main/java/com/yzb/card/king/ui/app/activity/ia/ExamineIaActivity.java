package com.yzb.card.king.ui.app.activity.ia;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.platform.comapi.map.E;
import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.UserAuthenticationBean;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.ui.other.activity.CountryInternationActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

import static com.yzb.card.king.ui.other.activity.CountryInternationActivity.COUNTRYINTERNATION_RESULTCODE;

/**
 * 类  名：校验本人身份认证
 * 作  者：Li Yubing
 * 日  期：2017/10/14
 * 描  述：
 */
@ContentView(R.layout.activity_examine_ia)
public class ExamineIaActivity extends BaseActivity {

    public static final String USER_AUTHENTICATION_DATA ="user_authentication_data";

    @ViewInject(R.id.tvCountryName)
    private TextView tvCountryName;

    @ViewInject(R.id.tvCerType)
    private TextView tvCerType;

    @ViewInject(R.id.etUseName)
    private EditText etUseName;

    @ViewInject(R.id.etCertNo)
    private EditText etCertNo;

    @ViewInject(R.id.llBirthday)
    private LinearLayout llBirthday;

    @ViewInject(R.id.llYouxiaoDate)
    private LinearLayout llYouxiaoDate;

    @ViewInject(R.id.tvBirthday)
    private  TextView tvBirthday;

    @ViewInject(R.id.tvYouxiaoDate)
    private TextView tvYouxiaoDate;

    private UserAuthenticationBean userAuthenticationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        NationalCountryBean bean =  new NationalCountryPresenter().selectOneDataByNameFromDb("中国大陆");

        initData(bean);
    }


    private void initView()
    {

        setTitleNmae("校验本人身份证件");

    }

    @Event(R.id.tvNextStep)
    private void tvNextStep(View view){

        if(userAuthenticationBean == null){

            ToastUtil.i(this,"请选择国籍（地区）");

            return;
        }

        String etUseNameStr = etUseName.getText().toString().trim();

        if(TextUtils.isEmpty(etUseNameStr)){
            ToastUtil.i(this,"请输入您的真实姓名");
            return;
        }

        userAuthenticationBean.setRealName(etUseNameStr);

        String etCertNoStr = etCertNo.getText().toString().trim();

        if(TextUtils.isEmpty(etCertNoStr)){
            ToastUtil.i(this,"请输入证件号码");
            return;
        }

        userAuthenticationBean.setCertNo(etCertNoStr);

        int certType =  userAuthenticationBean.getCertType();

        if(certType == 3 || certType == 6){

            String tvBirthdayStr = tvBirthday.getText().toString().trim();

            if(TextUtils.isEmpty(tvBirthdayStr)){
                ToastUtil.i(this,"请设置出生日期");
                return;
            }

            userAuthenticationBean.setBirthday(tvBirthdayStr);

            String tvYouxiaoDateStr = tvYouxiaoDate.getText().toString().trim();

            if(TextUtils.isEmpty(tvYouxiaoDateStr)){
                ToastUtil.i(this,"请设置有效期");
                return;
            }

            userAuthenticationBean.setCertInvalidDate(tvYouxiaoDateStr);
        }

        Intent intent = new Intent(this,IaAddBankActivity.class);

        intent.putExtra(USER_AUTHENTICATION_DATA,userAuthenticationBean);

        startActivityForResult(intent,VerifyResultActivity.RESULT_REQUEST_CODE);
    }


    @Event(R.id.llContryData)
    private void llContryData(View view){

        Intent intentCity = new Intent(this, CountryInternationActivity.class);

        startActivityForResult(intentCity,1000);

    }
    private TimePickerView pvTime;

    private int currentType;

    @Event(R.id.llBirthday)
    private void llBirthday(View view){

        currentType = 1;
        if (pvTime == null)
        {
            initTimePicker();
        }
        pvTime.show();
    }

    @Event(R.id.llYouxiaoDate)
    private void llYouxiaoDate(View view){
        currentType = 2;
        if (pvTime == null)
        {
            initTimePicker();
        }
        pvTime.show();
    }

    private void initTimePicker()
    {
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(Date date)
            {
                if(currentType == 1){
                    tvBirthday.setText(DateUtil.date2String(date, "yyyy-MM-dd"));
                }else {
                    tvYouxiaoDate.setText(DateUtil.date2String(date, "yyyy-MM-dd"));
                }

            }

            @Override
            public void onCancel()
            {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

      if(requestCode == VerifyResultActivity.RESULT_REQUEST_CODE){

            if(resultCode == VerifyResultActivity.RESULT_RESULT_SUCCESS){

                setResult(resultCode);

                finish();
            }

        }else if(requestCode == 1000) {

          if (resultCode == COUNTRYINTERNATION_RESULTCODE){

              if(data != null){

                  NationalCountryBean  bean = (NationalCountryBean) data.getSerializableExtra("selectCountryData");

                  initData(bean);

              }

          }

      }

    }

    private void initData(NationalCountryBean bean)
    {
        if(bean == null){

            return;
        }

        userAuthenticationBean = new UserAuthenticationBean();

        userAuthenticationBean.setCountryId(bean.getCityId());

        tvBirthday.setText("");

        tvYouxiaoDate.setText("");

        tvCountryName.setText(bean.getCityName());

        if(bean.isIfForeign()){//国外

            tvCerType.setText("护照");

            etUseName.setHint("请输入真实姓名");

            llBirthday.setVisibility(View.GONE);

            llYouxiaoDate.setVisibility(View.GONE);

            userAuthenticationBean.setCertType(2);

            userAuthenticationBean.setCertTypeName("护照");

        }else {//国内

            int cityId =  bean.getCityId();

            if( 8 == cityId){//大陆
                tvCerType.setText("身份证");

                etUseName.setHint("请输入真实姓名");

                llBirthday.setVisibility(View.GONE);

                llYouxiaoDate.setVisibility(View.GONE);

                userAuthenticationBean.setCertType(1);

                userAuthenticationBean.setCertTypeName("身份证");

            }else if( 255 == cityId || 256 == cityId){//大陆香港 大陆澳门

                tvCerType.setText("港澳居民来往大陆通行证");

                etUseName.setHint("请输入简体中文姓名");

                llBirthday.setVisibility(View.VISIBLE);

                llYouxiaoDate.setVisibility(View.VISIBLE);

                userAuthenticationBean.setCertType(6);

                userAuthenticationBean.setCertTypeName("港澳居民来往大陆通行证");

            }else if( 257 == cityId){//大陆台湾
                tvCerType.setText("台湾居民来往大陆通行证");

                etUseName.setHint("请输入简体中文姓名");

                llBirthday.setVisibility(View.VISIBLE);

                llYouxiaoDate.setVisibility(View.VISIBLE);

                userAuthenticationBean.setCertTypeName("台湾居民来往大陆通行证");

                userAuthenticationBean.setCertType(3);
            }

        }

    }
}
