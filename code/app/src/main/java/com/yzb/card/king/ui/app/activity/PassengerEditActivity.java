package com.yzb.card.king.ui.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.app.interfaces.CardDigitsKeyListener;
import com.yzb.card.king.ui.app.presenter.SettingUpdatePresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.appwidget.picks.CountryCityWheelView;
import com.yzb.card.king.ui.appwidget.picks.SelectCountryPick;
import com.yzb.card.king.ui.appwidget.popup.AirLineCompanyPP;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.other.activity.CountryInternationActivity;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzb.card.king.ui.other.activity.CountryInternationActivity.COUNTRYINTERNATION_RESULTCODE;

/**
 * @author gengqiyun
 * @date 2016.6.20
 * 新增或修改旅客信息；
 */
public class PassengerEditActivity extends BaseActivity implements View.OnClickListener, AppBaseView, BaseViewLayerInterface {

    public static final String ARG_DATA_BEAN = "ARG_DATA_BEAN";
    private static final int REQ_CODE_SELECT_COUNTRY = 0x002;
    public static final String MODIFIED_BEAN = "MODIFIED_BEAN";
    private EditText etEngNameValue; //英文姓；
    private EditText etEngNameValue2;//英文名；
    private TextView tv_passport_label;

    private EditText et_identification_id;//证件号吗；

    private TextView tv_birthday;
    private View ll_birthday;

    private View tvSexBoy; //男；
    private View tvSexGirl;//女；
    private TextView tv_country_id; //国籍；

    private EditText etMobilePhone; //手机号；

    private SettingUpdatePresenter settingUpdatePresenter;  //数据更新；


    private static final int REQ_CODE_GET_CONTACT = 0x001;//获取联系人信息；
    private PassengerInfoBean data;  //上个页面传递过来的bean；
    private int certType = 1; //证件类型；默认身份证；
    private String countryId;// 国籍id;

    private TextView tvSure;
    private TextView tvDelete;

    private Date selectDate; //选中的date；
    private EditText et_email_value;

    private View tvHidePart;

    private TableRow llValidityDate;
    private TextView tvValidityDate;
    private View lineValidityDate;

    private TextView tvCerTypeMsg;

    private TableRow trReming;

    private TableRow tbXing;
    private TableRow tbMing;

    private EditText etXingming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        colorStatusResId = R.color.color_436a8e;
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_edit);
        settingUpdatePresenter = new SettingUpdatePresenter(this);
        recvIntentData();
        assignViews();
        initViewContent();
    }

    /**
     * 接收Intent参数；
     */
    private void recvIntentData() {
        Serializable obj = getIntent().getSerializableExtra(ARG_DATA_BEAN);
        if (obj != null && obj instanceof PassengerInfoBean) {
            data = (PassengerInfoBean) obj;
            this.countryId = data.countryId;
            //初始化证件类型；
            this.certType = Integer.parseInt(data.getCertType());
        }
    }

    private void assignViews() {
        String title = data == null ? getString(R.string.setting_add_common_passenger) : getString(R.string.setting_modify_common_passenger);

        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);

        headerTitle.setText(title);

        findViewById(R.id.headerLeft).setOnClickListener(this);

        llValidityDate = (TableRow) findViewById(R.id.llValidityDate);

        llValidityDate.setOnClickListener(this);

        tvValidityDate = (TextView) findViewById(R.id.tvValidityDate);

        lineValidityDate = findViewById(R.id.lineValidityDate);

        trReming = (TableRow) findViewById(R.id.trReming);

        tvCerTypeMsg = (TextView) findViewById(R.id.tvCerTypeMsg);

        tbXing = (TableRow) findViewById(R.id.tbXing);

        tbMing = (TableRow) findViewById(R.id.tbMing);

        etXingming = (EditText) findViewById(R.id.etXingming);

        tvHidePart = findViewById(R.id.ll_may_hide_part);

        tvSure = (TextView) findViewById(R.id.tvSure);
        tvDelete = (TextView) findViewById(R.id.tvDelete);

        et_email_value = (EditText) findViewById(R.id.et_email_value);

        findViewById(R.id.headerRight).setOnClickListener(this);

        tvSure.setOnClickListener(this);
        tvDelete.setOnClickListener(this);

        //新增无删除；
        tvDelete.setVisibility(data == null ? View.GONE : View.VISIBLE);

        findViewById(R.id.panelHelp).setOnClickListener(this);

        etEngNameValue = (EditText) findViewById(R.id.et_eng_name_value);

        etEngNameValue2 = (EditText) findViewById(R.id.et_eng_name_value2);


        tv_passport_label = (TextView) findViewById(R.id.tv_passport_label);
        et_identification_id = (EditText) findViewById(R.id.et_identification_id);
        assignOtherInfoView();

        //默认身份证；
        setCertificate(getString(R.string.setting_sfz));


    }

    /**
     * 如果是修改的话，填充内容；
     */
    private void initViewContent() {
        if (data != null) {
            etEngNameValue.setText(isEmpty(data.engSurname) ? "" : data.engSurname);

            etEngNameValue2.setText(isEmpty(data.engName) ? "" : data.engName);

            tv_passport_label.setText(findRightCertType(data.certType));

            etXingming.setText(data.getName());

            if (TextUtils.isDigitsOnly(data.certType)) {
                this.certType = Integer.parseInt(data.certType);
            }
            //出生日期、国际、性别、证件有效期
            tvHidePart.setVisibility(certType == 1 || certType == 5 ? View.GONE : View.VISIBLE);

            if (certType == 1 || certType == 5) {//身份证、军人证


                etEngNameValue.setKeyListener(TextKeyListener.getInstance());

                etEngNameValue2.setKeyListener(TextKeyListener.getInstance());

                tvCerTypeMsg.setVisibility(View.GONE);

                trReming.setVisibility(View.VISIBLE);

                tbMing.setVisibility(View.GONE);

                tbXing.setVisibility(View.GONE);

            } else {


                etEngNameValue.setKeyListener(new CardDigitsKeyListener(this));

                etEngNameValue2.setKeyListener(new CardDigitsKeyListener(this));

                tvCerTypeMsg.setVisibility(View.VISIBLE);

                trReming.setVisibility(View.GONE);

                tbMing.setVisibility(View.VISIBLE);

                tbXing.setVisibility(View.VISIBLE);
            }


            if (certType == 2 || certType == 3 || certType == 4 || certType == 6) {

                llValidityDate.setVisibility(View.VISIBLE);
                tvValidityDate.setVisibility(View.VISIBLE);
                lineValidityDate.setVisibility(View.VISIBLE);
                if (!isEmpty(data.getCertInvalidDate())) {
                    tvValidityDate.setText(data.getCertInvalidDate());

                    validityDate = DateUtil.string2Date(data.getCertInvalidDate(), DateUtil.DATE_FORMAT_DATE);

                } else {
                    tvValidityDate.setText("");
                }


            } else {

                llValidityDate.setVisibility(View.GONE);
                tvValidityDate.setVisibility(View.GONE);
                lineValidityDate.setVisibility(View.GONE);

            }

            //证件号；
            et_identification_id.setText(isEmpty(data.certNo) ? "" : data.certNo);
            //生日；
            tv_birthday.setText(isEmpty(data.getBirthday()) ? "" : data.getBirthday());

            if (!isEmpty(data.getBirthday())) {
                tv_birthday.setText(data.getBirthday());

                selectDate = DateUtil.string2Date(data.getBirthday(), DateUtil.DATE_FORMAT_DATE);

            } else {
                tv_birthday.setText("");
            }

            changeSexSelection("1".equals(data.sex) ? true : false);
            //国籍；
            tv_country_id.setText(isEmpty(data.countryName) ? "" : data.countryName);
            //电话；
            etMobilePhone.setText(isEmpty(data.mobile) ? "" : data.mobile);

            et_email_value.setText(isEmpty(data.getEmail()) ? "" : data.getEmail());

        }
    }


    /**
     * 查找证件类型
     *
     * @param certTypeIndex 目标证件类型；
     * @return
     */
    private String findRightCertType(String certTypeIndex) {
        String[] passportArray = getResources().getStringArray(R.array.setting_certificate_type);
        int[] typeArray = getResources().getIntArray(R.array.setting_indexs_inte);
        for (int i = 0; i < typeArray.length; i++) {
            int a = Integer.parseInt(certTypeIndex);
            //证件类型；
            if (typeArray[i] == a) {
                return passportArray[i];
            }
        }
        return "";
    }

    /**
     * 其他信息；
     */
    private void assignOtherInfoView() {
        ll_birthday = findViewById(R.id.ll_birthday);
        ll_birthday.setOnClickListener(this);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);

        tv_country_id = (TextView) findViewById(R.id.tv_country_id);

        tvSexBoy = findViewById(R.id.tv_sex_boy);
        tvSexBoy.setSelected(true);
        tvSexBoy.setBackgroundResource(R.drawable.shap_corner_blue);
        tvSexBoy.setOnClickListener(this);

        tvSexGirl = findViewById(R.id.tv_sex_girl);
        tvSexGirl.setSelected(false);
        tvSexGirl.setBackgroundResource(R.drawable.shap_corner_gray);
        tvSexGirl.setOnClickListener(this);

        findViewById(R.id.ll_country).setOnClickListener(this);

        etMobilePhone = (EditText) findViewById(R.id.et_mobile_phone);
        findViewById(R.id.iv_contact).setOnClickListener(this);
        changeSexSelection(true);
    }

    private int curOperation = -1; //0：删除操作；1：保存操作；

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvDelete:
                curOperation = 0;
                exeDelete();
                break;
            case R.id.headerRight:
            case R.id.tvSure: //保存；
                if (isInputEmpty() && isIndentification()) {
                    curOperation = 1;
                    exeSave();
                }
                break;
            case R.id.panelHelp:  //帮助;
                Bundle bundle = new Bundle();
                bundle.putString("category", AppConstant.h5_category_passenger_info);
                bundle.putString("titleName", getString(R.string.input_intro));
                readyGoWithBundle(this, WebViewClientActivity.class, bundle);
                break;
            case R.id.ll_birthday: // 生日；
                showDateSelectDialog();
                break;
            case R.id.llValidityDate://有效日期
                showValidityDateDialog();
                break;
            case R.id.tv_sex_boy: // 男；
                changeSexSelection(true);
                break;
            case R.id.tv_sex_girl: // 女；
                changeSexSelection(false);
                break;
            case R.id.ll_country: // 国籍；
                showCountryPickDialog();
                break;
            case R.id.iv_contact: //获取联系人；
                ContactUtil.startContactsActivity(this, REQ_CODE_GET_CONTACT);
                break;
        }
    }

    /**
     * 判断是有实名认证；
     */
    private boolean isIndentification() {
        if (getUserBean() != null) {
            boolean flag = "1".equals(getUserBean().getAuthenticationStatus());
            if (!flag) {
                toastCustom(R.string.no_identification);
            }
            return flag;
        }
        return false;
    }

    /**
     * 国籍选择；
     */
    private void showCountryPickDialog() {
//        if (countryPick == null)
//        {
//            countryPick = new SelectCountryPick(PassengerEditActivity.this, CountryCityWheelView.Type.COUNTRY);
//            countryPick.setCancelable(true);
//            countryPick.setListener(new SelectCountryPick.SelectedData()
//            {
//                @Override
//                public void getSelectedData(List<NationalCountryBean> data)
//                {
//                    if (data != null && data.size() > 0)
//                    {
//                        NationalCountryBean countryBean = data.get(0);
//                        countryId = countryBean.getCityId() + "";
//                        tv_country_id.setText(countryBean.getCityName());
//                    }
//                }
//            });
//        }
//        countryPick.show();

        Intent intentCity = new Intent(this, CountryInternationActivity.class);

        startActivityForResult(intentCity, 1000);
    }

    /**
     * 执行删除；
     */
    private void exeDelete() {
        showPDialog(R.string.setting_committing);
        Map<String, Object> params = new HashMap<>();
        params.put("guestId", data.id);
        params.put("status", "0");
        params.put("serviceName", CardConstant.setting_customerguestupdate);
        settingUpdatePresenter.loadData(params);
    }

    private TimePickerView pvValidityDate;
    private Date validityDate; //选中的date；

    /**
     * 有效期
     */
    private void showValidityDateDialog() {

        if (pvValidityDate == null) {
            pvValidityDate = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            pvValidityDate.setCyclic(false);
            pvValidityDate.setCancelable(true);
            pvValidityDate.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date) {
                    if (date.getTime() >System.currentTimeMillis()) {
                        validityDate = date;
                        tvValidityDate.setText(DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE));
                    }else {
                        ToastUtil.i(PassengerEditActivity.this,"您的证件有效期已过，请核实日期");
                    }
                }

                @Override
                public void onCancel() {

                }
            });
        }
        pvValidityDate.setTime(validityDate);
        pvValidityDate.show();
    }

    private TimePickerView pvTime;

    /**
     * 生日日期选择dialog；
     */
    private void showDateSelectDialog() {
        if (pvTime == null) {
            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            pvTime.setCyclic(false);
            pvTime.setCancelable(true);
            pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date) {
                    if (date.getTime() < System.currentTimeMillis()){
                        selectDate = date;
                    tv_birthday.setText(DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE));

                    }else {

                        ToastUtil.i(PassengerEditActivity.this,"请选择正确生日日期");
                    }
            }

            @Override
            public void onCancel ()
            {

            }
        });
    }
        pvTime.setTime(selectDate);
        pvTime.show();
}
    private AirLineCompanyPP invoiceContentPp = null;

    /**
     * 打开证件类型menu；
     */
    public void openIdentifiPop(View v) {

        if (invoiceContentPp == null) {

            String[] nameArray = getResources().getStringArray(R.array.setting_certificate_type);

            int[] typeArray = getResources().getIntArray(R.array.setting_indexs_inte);

            invoiceContentPp = new AirLineCompanyPP(PassengerEditActivity.this, -1);

            invoiceContentPp.setLogicData(nameArray, typeArray);

            invoiceContentPp.setSelectIndex(selectedCInvoicIndex);

            invoiceContentPp.setCallBack(invoiceCCallBack);
        }

        invoiceContentPp.showPP(getWindow().getDecorView());
    }

    int selectedCInvoicIndex = 0;

    private AirLineCompanyPP.BottomDataListCallBack invoiceCCallBack = new AirLineCompanyPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {
            certType = nameValue;
            setCertificate(name);
        }
    };

    /**
     * 提交
     */
    private void exeSave() {
        showPDialog(R.string.setting_loading);
        Map<String, Object> inputParams = getInputParams();
        if (data != null) {
            inputParams.put("guestId", data.id);// 旅客id;
        }
        inputParams.put("serviceName", data != null ? CardConstant.setting_customerguestupdate : CardConstant.setting_customerguestcreate);
        settingUpdatePresenter.loadData(inputParams);
    }


    /**
     * 获取入参；
     */
    public Map<String, Object> getInputParams() {
        Map<String, Object> param = new HashMap<>();

        final String engName1 = etEngNameValue.getText().toString().trim();
        final String engName2 = etEngNameValue2.getText().toString().trim();
        final String certType = this.certType + "";
        final String certNo = et_identification_id.getText().toString().trim();

        final String birthday = tv_birthday.getText().toString().trim();
        final String sex = tvSexBoy.isSelected() ? "1" : "2"; // 性别；
        final String phone = etMobilePhone.getText().toString().trim();

        String etXingmingStr = etXingming.getText().toString();

        param.put("guestName", etXingmingStr);// en 姓；
        param.put("engSurname", engName1);// en 姓；
        param.put("engName", engName2);// en 名；
        param.put("certType", certType);//证件类型
        param.put("certNo", certNo); //证件号；

        String et_email_valueStr = et_email_value.getText().toString();

        if (!TextUtils.isEmpty(et_email_valueStr)) {
            param.put("email", et_email_valueStr); //邮箱
        }


        if (data != null) {
            data.setEngSurname(engName1);
            data.setEngName(engName2);
            data.setCertType(certType);
            data.setCertNo(certNo);
            data.setSex(sex);
            data.setMobile(phone);
            data.setEmail(et_email_valueStr);

            param.put("status", data.getStatus());
        }
        //非身份证；
        if (this.certType != 1) {
            param.put("birthday", birthday);
            param.put("sex", sex);
            param.put("countryId", countryId);
        }

        if (this.certType == 2 || this.certType == 3 || this.certType == 4 || this.certType == 6) {

            String validityDateStr = tvValidityDate.getText().toString().trim();

            if (!isEmpty(validityDateStr)) {
                param.put("certInvalidDate", validityDateStr);
            }

        }

        if (!isEmpty(phone)) {
            param.put("mobile", phone);// 电话；
        }
        return param;
    }

    /**
     * 格式验证；
     *
     * @return
     */
    private boolean isInputEmpty() {
        // 证件号；
        String id = et_identification_id.getText().toString().trim();

        //身份证和军人证；
        if (certType == 1 || certType == 5) {

            String engName1 = etXingming.getText().toString().trim();
            if (isEmpty(engName1)) {
                toastCustom(R.string.setting_zh_name_empty);
                return false;
            }
            if (!RegexUtil.validChinese(engName1)) {
                toastCustom(R.string.setting_input_zh_name);
                return false;
            }

        } else {
            String engName1 = etEngNameValue.getText().toString().trim();
            if (isEmpty(engName1)) {
                toastCustom(R.string.setting_en_name_empty);
                return false;
            }
            if (!RegexUtil.isEngChacters(engName1)) {
                toastCustom(R.string.setting_input_en_name1);
                return false;
            }
            String engName2 = etEngNameValue2.getText().toString().trim();
            if (isEmpty(engName2)) {
                toastCustom(R.string.setting_en_name_empty2);
                return false;
            }
            if (!RegexUtil.isEngChacters(engName2)) {
                toastCustom(R.string.setting_input_en_name2);
                return false;
            }
        }

        /*------------------证件格式验证---------------*/
        if (certType == 1 && !RegexUtil.validIdNum(id)) {//身份证
            return false;
        } else if (certType == 2 && !RegexUtil.validPassPort(id)) { //护照
            toastCustom(R.string.setting_cetificate_format_error);
            return false;
        } else if (certType == 3 && !RegexUtil.validTaiwanese(id)) { // 台胞证；
            toastCustom(R.string.setting_cetificate_format_error);
            return false;
        } else if (certType == 4 && !RegexUtil.validGangAo(id)) { // 回乡证；
            toastCustom(R.string.setting_cetificate_format_error);
            return false;
        } else if (certType == 5 && !RegexUtil.validOfficer(id)) { // 军人证；
            toastCustom(R.string.setting_cetificate_format_error);
            return false;
        } else if (certType == 6 && !RegexUtil.validGangAo(id)) { // 港澳通行证；
            toastCustom(R.string.setting_cetificate_format_error);
            return false;
        }

        //非身份证和军人证；
        if (certType != 1 && certType != 5) {
            if (isEmpty(tv_birthday.getText().toString().trim())) {
                toastCustom(R.string.setting_birthday_empty);
                return false;
            }
            if (isEmpty(countryId)) {
                toastCustom(R.string.setting_country_empty);
                return false;
            }
        }

        if (certType == 2 || certType == 3 || certType == 4 || certType == 6) {

            String validityDateStr = tvValidityDate.getText().toString().trim();


            if (isEmpty(validityDateStr)) {
                toastCustom(R.string.setting_validity_date);
                return false;
            }

        }

        String phone = etMobilePhone.getText().toString().trim();
        // 手机号格式；
        if (!isEmpty(phone) && !RegexUtil.validPhoneNum(phone)) {
            return false;
        }

        String emilStr = et_email_value.getText().toString();
        if (!TextUtils.isEmpty(emilStr)) {

            if (!ValidatorUtil.isEmail(emilStr)) {

                toastCustom(R.string.setting_email_form);

                return false;
            }
        }

        return true;
    }

    /**
     * 证件对话框点击返回；
     * 如果是身份证  隐藏相应的项目；
     *
     * @param certificate
     */
    public void setCertificate(String certificate) {
        if (!isEmpty(certificate)) {
            tv_passport_label.setText(certificate);
            // 清空；
            et_identification_id.setText("");
        }

        tvHidePart.setVisibility(certType == 1 || certType == 5 ? View.GONE : View.VISIBLE);

        if (certType == 1 || certType == 5) {

            etEngNameValue.setKeyListener(TextKeyListener.getInstance());

            etEngNameValue2.setKeyListener(TextKeyListener.getInstance());

            tvCerTypeMsg.setVisibility(View.GONE);

            trReming.setVisibility(View.VISIBLE);

            tbMing.setVisibility(View.GONE);

            tbXing.setVisibility(View.GONE);

        } else {


            etEngNameValue.setKeyListener(new CardDigitsKeyListener(this));

            etEngNameValue2.setKeyListener(new CardDigitsKeyListener(this));

            tvCerTypeMsg.setVisibility(View.VISIBLE);

            trReming.setVisibility(View.GONE);

            tbMing.setVisibility(View.VISIBLE);

            tbXing.setVisibility(View.VISIBLE);
        }


        if (certType == 2 || certType == 3 || certType == 4 || certType == 6) {

            llValidityDate.setVisibility(View.VISIBLE);
            tvValidityDate.setVisibility(View.VISIBLE);
            lineValidityDate.setVisibility(View.VISIBLE);

        } else {

            llValidityDate.setVisibility(View.GONE);
            tvValidityDate.setVisibility(View.GONE);
            lineValidityDate.setVisibility(View.GONE);

        }


    }

    /**
     * 更改性别区域的状态；
     *
     * @param flag true:男选中；false：女选中；
     */
    private void changeSexSelection(boolean flag) {
        tvSexBoy.setSelected(flag);
        tvSexGirl.setSelected(!flag);

        if (flag) {

            tvSexBoy.setBackgroundResource(R.drawable.shap_corner_blue);

            tvSexGirl.setBackgroundResource(R.drawable.shap_corner_gray);
        } else {

            tvSexGirl.setBackgroundResource(R.drawable.shap_corner_blue);

            tvSexBoy.setBackgroundResource(R.drawable.shap_corner_gray);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1000) {

            if (resultCode == COUNTRYINTERNATION_RESULTCODE) {

                if (data != null) {

                    NationalCountryBean countryBean = (NationalCountryBean) data.getSerializableExtra("selectCountryData");


                    countryId = countryBean.getCityId() + "";
                    tv_country_id.setText(countryBean.getCityName());
                }

            }

        }

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQ_CODE_GET_CONTACT://联系人信息
                Connector connector = new Connector();
                ContactUtil.getConnector(this, data, connector);
                if (isEmpty(connector.mobile)) {
                    ToastUtil.i(PassengerEditActivity.this, getString(R.string.jfb_get_phone_number_fail));
                } else {
                    etMobilePhone.setText(connector.mobile);
                }
                break;
        }


    }

    @Override
    public void onViewCallBackSucess(Object data) {
        closePDialog();
        exeSave();
    }

    @Override
    public void onViewCallBackFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        closePDialog();
        //删除操作；
        if (curOperation == 0) {
            setResult(Activity.RESULT_OK, getIntent().putExtra("id", data.getId()));
        }
        finish();
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        closePDialog();
        toastCustom(o + "");
    }
}
