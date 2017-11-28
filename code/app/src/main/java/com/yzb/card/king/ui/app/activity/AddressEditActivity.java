package com.yzb.card.king.ui.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.presenter.SettingUpdatePresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.appwidget.picks.CountryCityWheelView;
import com.yzb.card.king.ui.appwidget.picks.SelectCountryPick;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author gengqiyun
 * @date 2016.6.21
 * 新增/修改地址；
 */
public class AddressEditActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface
{
    private GoodsAddressBean data;
    private EditText etRecvPerson;
    public static final String ARG_DATA_BEAN = "ARG_DATA_BEAN";
    private EditText etPhone;
    private TextView tvProvince;
    private EditText etDetailAddress;

    private SettingUpdatePresenter settingUpdatePresenter;
    private TextView tvDelete;
    private int curOperation = -1; //0：删除操作；1：保存操作；
    private SelectCountryPick countryPick;
    private String regionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        settingUpdatePresenter = new SettingUpdatePresenter(this);
        recvIntentData();
        assignViews();
    }

    private void recvIntentData()
    {
        Serializable obj = getIntent().getSerializableExtra(ARG_DATA_BEAN);
        if (obj != null && obj instanceof GoodsAddressBean)
        {
            data = (GoodsAddressBean) obj;
            cityId = data.cityId;
        }
    }

    private void assignViews()
    {
        String title = data == null ? getString(R.string.setting_add_goods_address) : getString(R.string.setting_modify_goods_address);
        setHeader(R.mipmap.icon_arrow_back_black, title);

        findViewById(R.id.headerLeft).setOnClickListener(this);
        tvDelete = (TextView) findViewById(R.id.tvDelete);
        tvDelete.setOnClickListener(this);

        //新增无删除；
        tvDelete.setVisibility(data == null ? View.GONE : View.VISIBLE);

        findViewById(R.id.tvSave).setOnClickListener(this);

        etRecvPerson = (EditText) findViewById(R.id.et_recv_person);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvProvince = (TextView) findViewById(R.id.tv_province);
        tvProvince.setOnClickListener(this);

        etDetailAddress = (EditText) findViewById(R.id.etDetailAddress);
        findViewById(R.id.ll_locate).setOnClickListener(this);
        initViewContent();
    }

    private void initViewContent()
    {
        if (data != null)
        {
            etRecvPerson.setText(isEmpty(data.contacts) ? "" : data.contacts);
            etPhone.setText(isEmpty(data.phone) ? "" : data.phone);

            //省市区；
            StringBuilder sb = new StringBuilder(data.provinceName);
            if (!isEmpty(data.cityName))
            {
                sb.append(data.cityName);
            }
            if (!isEmpty(data.districtName))
            {
                sb.append(data.districtName);
            }
            tvProvince.setText(sb.toString());

            etDetailAddress.setText(isEmpty(data.address) ? "" : data.address);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvDelete:
                curOperation = 0;
                exeDelete();
                break;
            case R.id.tvSave:
                if (isInputRight())
                {
                    curOperation = 1;
                    exeSave();
                }
                break;
            case R.id.tv_province: // 省市；
                showCountryPickDialog();
                break;
            case R.id.ll_locate: // 定位；

                Location location = GlobalApp.getPositionedCity();

                StringBuilder sb = new StringBuilder(location.province);

                if (!isEmpty(location.cityName))
                {
                    sb.append(location.cityName);
                }
                if (!isEmpty(location.getDistrict()))
                {
                    sb.append(location.getDistrict());
                }
                String cityId = location.cityId;
                String provinceId = location.provinceId;
                //定位成功；
                if (!isEmpty(provinceId) && !isEmpty(cityId))
                {
                    tvProvince.setText(sb.toString());

                    if (!isEmpty(location.getDistrict()))
                    {
                        findRegionIdByParentId(cityId, location.getDistrict());
                    }
                }

                break;
        }
    }

    /**
     * 通过区域name查询区域id；
     *
     * @param cityId
     * @param district
     */
    private void findRegionIdByParentId(String cityId, String district)
    {
        NationalCountryBean beanList = new NationalCountryPresenter().selectOneDataByNameFromDb(district);

        if(beanList != null){
            regionId = beanList.getCityId() + "";
        }

    }

    /**
     * 执行删除；
     */
    private void exeDelete()
    {
        showPDialog(R.string.setting_committing);
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", data.getAddressId());
        params.put("districtId",data.getDistrictId());
        params.put("status", "0");
        params.put("serviceName", CardConstant.setting_customeraddressupdate);
        settingUpdatePresenter.loadData(params);
    }

    /**
     * 国籍选择；
     */
    private void showCountryPickDialog()
    {
        CommonUtil.hideKeyboard(etPhone, this);
        if (countryPick == null)
        {
            countryPick = new SelectCountryPick(this, CountryCityWheelView.Type.CHINA);
            countryPick.setCancelable(true);
            countryPick.setListener(new SelectCountryPick.SelectedData()
            {
                @Override
                public void getSelectedData(List<NationalCountryBean> data)
                {
                    if (data != null && data.size() == 4)
                    {
                        NationalCountryBean provinceBean = data.get(1);
                        NationalCountryBean cityBean = data.get(2);
                        NationalCountryBean regionBean = data.get(3);

                        StringBuilder sb = new StringBuilder();
                        sb.append(isEmpty(provinceBean.getCityName()) ? "" : provinceBean.getCityName());
                        sb.append(isEmpty(cityBean.getCityName()) ? "" : cityBean.getCityName());
                        sb.append(isEmpty(regionBean.getCityName()) ? "" : regionBean.getCityName());

                        tvProvince.setText(sb.toString());

                        cityId = cityBean.getCityId() + "";

                        regionId = regionBean.getCityId() + "";
                    }
                }
            });
        }
        countryPick.show();
    }



    /**
     * 收货地址创建或修改
     */
    private void exeSave()
    {

        if(TextUtils.isEmpty(regionId)){

            ToastUtil.i(this,"请手动选择省市区");

            return;
        }

        showPDialog(R.string.setting_committing);
        final String recvPerson = etRecvPerson.getText().toString().trim();
        final String phone = etPhone.getText().toString().trim();
        final String detailAddress = etDetailAddress.getText().toString().trim();
        //final String postalCode = etPostalCode.getText().toString().trim();

        Map<String, Object> param = new HashMap<>();
        param.put("contacts", recvPerson);
        param.put("phone", phone);
        param.put("districtId", regionId);
        param.put("address", detailAddress);
        // param.put("postCode", postalCode);
        //修改；
        if (data != null)
        {
            param.put("addressId", data.getAddressId());
        }

        param.put("serviceName", data != null ? CardConstant.setting_customeraddressupdate :
                CardConstant.setting_customeraddresscreate);
        settingUpdatePresenter.loadData(param);
    }

    /**
     * 入参验证；
     */
    private boolean isInputRight()
    {
        String etContent0 = etRecvPerson.getText().toString().trim();
        if (isEmpty(etContent0))
        {
            ToastUtil.i(this, getString(R.string.setting_recv_goods_not_empty));
            return false;
        }
        String etPhoneStr = etPhone.getText().toString().trim();
        if (!RegexUtil.validPhoneNum(etPhoneStr))
        {
            return false;
        }
        String etContent2 = tvProvince.getText().toString().trim();
        if (isEmpty(etContent2))
        {
            ToastUtil.i(this, getString(R.string.setting_province_not_empty));
            return false;
        }
        String etContent3 = etDetailAddress.getText().toString().trim();
        if (isEmpty(etContent3))
        {
            ToastUtil.i(this, getString(R.string.setting_detail_address_not_empty));
            return false;
        }

        return true;
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom(R.string.app_op_info_success);
        //删除操作；
        if (curOperation == 0)
        {
            setResult(Activity.RESULT_OK, getIntent().putExtra("id", this.data.getAddressId()));
        }
        finish();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom(o+"");
    }
}

