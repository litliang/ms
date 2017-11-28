package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.activity.AddressManageActivity;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.appwidget.popup.IntegralExchangePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.integral.adapter.ExchangeDtailAdapter;
import com.yzb.card.king.ui.integral.model.IIntegralExchange;
import com.yzb.card.king.ui.integral.presenter.IntegralExchangePresenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DialogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 类  名：兑油卡
 * 作  者：Li Yubing
 * 日  期：2016/6/20
 * 描  述：
 */
@ContentView(R.layout.activity_exchange_youka)
public class ExchangeYouka extends BaseActivity implements BaseViewLayerInterface {

    //控件
    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.includeYouka)
    private View includeYouka;

    @ViewInject(R.id.rgLogin)
    private RadioGroup rgLogin;

    @ViewInject(R.id.tvExchangeName)
    private TextView tvExchangeName;

    @ViewInject(R.id.etYourYoukaNo)
    private EditText etYourYoukaNo;

    @ViewInject(R.id.tvScale)
    private TextView tvScale;

    @ViewInject(R.id.tvExchangeMoney)
    private TextView tvExchangeMoney;

    @ViewInject(R.id.llIntegral)
    private View llIntegral;

    @ViewInject(R.id.rlArrow)
    private RelativeLayout rlArrow;

    @ViewInject(R.id.tvMoneyNumber)
    private TextView tvMoneyNumber;

    @ViewInject(R.id.tvIntegralNum)
    private EditText tvIntegralNum;

    @ViewInject(R.id.slideShowView1)
    private SlideShow1ItemView slideShowView1;

    @ViewInject(R.id.svParent)
    private ScrollView svParent;

    @ViewInject(R.id.tvPeopleName)
    private TextView tvPeopleName;//接收人名

    @ViewInject(R.id.tvPhone)
    private TextView tvPhone;//接收人号码

    @ViewInject(R.id.tvAddress)
    private TextView tvAddress;//接收人地址

    //数据
    private float slide1_whrate = 432 / 1080.0f;


    private boolean firstFlag = true;

    //控件
    private WholeListView wvll;

    private ExchangeDtailAdapter adapter;

    private IntegralExchangePopup allCustomerPopup;

    private ExchangeIntegralBean currentExchangeIntegralBean;

    private int type = 2;

    private int totalMoney = 0;

    private long needAllIntegral = 0;

    private int status = 0;

    //数据
    private GoodsAddressBean goodsAddressBean;

    private IntegralExchangePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new IntegralExchangePresenter(this);
        initView();
        slideShowView1.setParam(AppConstant.youka_type_parentid,selectedCity.cityId,"1007");
         //发送
        presenter.sendFaultLinkManInfo();
    }
    private void initView() {

        titleName.setText(R.string.integral_dui_you_ka);

        etYourYoukaNo.setFocusable(false);

        tvIntegralNum.setFocusable(false);

        etHandler.sendEmptyMessageDelayed(0, 1000);

        SlideButton slideButtonNoNf = (SlideButton) findViewById(R.id.slide_button_no_nf);
        slideButtonNoNf.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener() {
            @Override
            public void onToggleStateChange(SlideButton.ToggleState state) {

                if (state == SlideButton.ToggleState.open) {
                    includeYouka.setVisibility(View.VISIBLE);

                    status = 1;

                } else {
                    includeYouka.setVisibility(View.GONE);
                    status = 0;
                }
            }
        });

        wvll = (WholeListView) findViewById(R.id.wvAll);

        adapter = new ExchangeDtailAdapter(this, myHandler);

        wvll.setAdapter(adapter);


        rgLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                currentExchangeIntegralBean = null;

                totalMoney = 0;

                needAllIntegral = 0;

                tvExchangeMoney.setText(totalMoney + "元");

                tvMoneyNumber.setText("");

                tvIntegralNum.setText("");
                tvExchangeName.setText(R.string.integral_select_integral_bili);
                tvScale.setText("");
                etYourYoukaNo.setText("");
                adapter.clear();

                if (checkedId == R.id.rbShihua) {//中石化
                    type = 2;
                } else if (checkedId == R.id.rbShiyou) {//中石油
                    type = 3;
                }

            }
        });

        rlArrow.setOnClickListener(myClickListener);


        tvIntegralNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (currentExchangeIntegralBean != null) {

                    String integralStr = tvIntegralNum.getText().toString().trim();


                    if (!TextUtils.isEmpty(integralStr)) {

                        String firstStr = integralStr.substring(0, 1);

                        if ("0".equals(firstStr)) {

                            if (firstFlag) {
                                firstFlag = false;
                                ToastUtil.i(ExchangeYouka.this, getString(R.string.integral_no_integer));
                            }

                        } else {
                            //计算当前输入的金额，不可超过本人持有的积分总额，否则不可输入
                            long integralInt = Long.parseLong(integralStr);

                            long personageIntegralNum = currentExchangeIntegralBean.getPlatformIntegralNum();//个人平台持有积分

                            if (integralInt <= personageIntegralNum) {//输入的积分持有数不大于个人平台积分持有数，正常处理

                                String moneyStr = String.valueOf(integralInt * Long.parseLong(currentExchangeIntegralBean
                                        .getExchangeResult()) / Long.parseLong(currentExchangeIntegralBean.getPoint()));

                                tvMoneyNumber.setText(moneyStr);
                                tvIntegralNum.setSelection(integralStr.length());

                            } else {//反之，不可输入

                                int strLength = integralStr.length();

                                if (strLength > 0) {

                                    String newStr = integralStr.substring(0, strLength - 1);
                                    if(!TextUtils.isEmpty(newStr)) {
                                        tvIntegralNum.setText(newStr);
                                        tvIntegralNum.setSelection(newStr.length());
                                        long newStrLong = Long.parseLong(newStr);

                                        String moneyStr = String.valueOf(newStrLong * Long.parseLong(currentExchangeIntegralBean
                                                .getExchangeResult()) / Long.parseLong(currentExchangeIntegralBean.getPoint()));

                                        tvMoneyNumber.setText(moneyStr);

                                        DialogUtil.getInstance().showSingleButtonDialog(ExchangeYouka.this, getString(R.string.dialog_title), getString(R.string.dialog_msg_platform_integral_num) + personageIntegralNum, getString(R.string.dialog_button_know));

                                    }
                                }

                            }

                        }
                    } else {

                        firstFlag = true;
                    }
                }
            }
        });

        svParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                AppUtils.hideSoftWindow(ExchangeYouka.this, tvIntegralNum);
                return false;
            }
        });
    }

    private Handler etHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            tvIntegralNum.setFocusable(true);
            tvIntegralNum.setFocusableInTouchMode(true);
            tvIntegralNum.requestFocus();

            etYourYoukaNo.setFocusable(true);
            etYourYoukaNo.setFocusableInTouchMode(true);
            etYourYoukaNo.requestFocus();

        }
    };

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            exchangeMoneyAction();
        }
    };

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.rlArrow:

                    allCustomerPopup = new IntegralExchangePopup(llIntegral, ExchangeYouka.this, new
                            IntegralExchangePopup.PriorityListener() {

                                @Override
                                public void refreshPriorityUI(int index) {

                                    currentExchangeIntegralBean = allCustomerPopup.getSelectOjbect(index);

                                    if (currentExchangeIntegralBean != null) {

                                        //检查是否是当Hi Life平台的兑换规则
                                        if ("0".equals(currentExchangeIntegralBean.getBankId())) {//平台数据

                                            long accountIntegralNum = UserManager.getInstance().getIntegrationNumeber();


                                            currentExchangeIntegralBean.setPlatformIntegralNum(accountIntegralNum);

                                        }

                                        tvExchangeName.setText(currentExchangeIntegralBean.getRuleName());

                                        tvScale.setText(currentExchangeIntegralBean.getPoint() + ":" +
                                                currentExchangeIntegralBean.getExchangeResult());

                                        tvMoneyNumber.setText(currentExchangeIntegralBean.getExchangeResult());

                                        tvIntegralNum.setText(currentExchangeIntegralBean.getPoint());

                                    }

                                }
                            });

                    allCustomerPopup.create(type, null);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 添加计划
     *
     * @param view
     */
    public void addNewPlan(View view) {

        if (currentExchangeIntegralBean != null) {

            String tvMoneyNumberStr = tvMoneyNumber.getText().toString().trim();

            String tvIntegralNumStr = tvIntegralNum.getText().toString().trim();


            if (TextUtils.isEmpty(tvIntegralNumStr)) {

                ToastUtil.i(ExchangeYouka.this, getString(R.string.integral_input_integral_num));

                return;

            }
            if (TextUtils.isEmpty(tvMoneyNumberStr)) {

                ToastUtil.i(ExchangeYouka.this, getString(R.string.integral_input_money_num));

                return;

            }

            long integralNum = Long.parseLong(tvMoneyNumberStr);
            if (integralNum <= 0) {

                ToastUtil.i(ExchangeYouka.this, R.string.integral_exchange_must_one);

                return;
            }

            if (integralNum > currentExchangeIntegralBean.getPlatformIntegralNum()) {

                DialogUtil.getInstance().showSingleButtonDialog(ExchangeYouka.this, getString(R.string.dialog_title),
                        getString(R.string.dialog_msg_platform_integral_num) +
                                currentExchangeIntegralBean.getPlatformIntegralNum(), getString(R.string.dialog_button_know));

                return;
            }

            currentExchangeIntegralBean.setPoint(tvIntegralNumStr);

            currentExchangeIntegralBean.setExchangeResult(tvMoneyNumberStr);

            if ("0".equals(currentExchangeIntegralBean.getBankId())) {//平台积分

                int pointNumber = Integer.parseInt(currentExchangeIntegralBean.getPoint());

                needAllIntegral = needAllIntegral + pointNumber;


                long accountIntegralNum = UserManager.getInstance().getIntegrationNumeber();

                if (accountIntegralNum < needAllIntegral) {

                    ToastUtil.i(ExchangeYouka.this, getString(R.string.integral_user_lack_integral) + ",目前只有" +
                            accountIntegralNum + "积分。");

                    needAllIntegral = needAllIntegral - pointNumber;
                } else {

                    boolean flag = adapter.addOneNewData(currentExchangeIntegralBean);

                    if (flag) {
                        //计算兑换现金
                        exchangeMoneyAction();
                    } else {
                        ToastUtil.i(ExchangeYouka.this, getString(R.string.integral_added_this_ruled));
                    }

                }

            } else {

                boolean flag = adapter.addOneNewData(currentExchangeIntegralBean);

                if (flag) {//计算兑换现金
                    exchangeMoneyAction();
                } else {

                    ToastUtil.i(ExchangeYouka.this, getString(R.string.integral_added_this_ruled));
                }
            }
        } else {

            ToastUtil.i(ExchangeYouka.this, R.string.integral_please_select_exchange_detail);
        }

    }

    /**
     * 提交事件
     *
     * @param v
     */
    public void submitAction(View v) {

        if (status == 0) {

            String youkaNumber = etYourYoukaNo.getText().toString().trim();

            if (TextUtils.isEmpty(youkaNumber)) {

                ToastUtil.i(ExchangeYouka.this, getString(R.string.hint_integral_input_your_youka_number));

                return;
            }

        } else if( status == 1){//需要送货地址

            if(goodsAddressBean == null){

                ToastUtil.i(ExchangeYouka.this,getString(R.string.toast_setting_address));

                return;
            }

        }
        if (adapter.getCount() > 0) {

            if (needAllIntegral > 0) {

                ProgressDialogUtil.getInstance().showProgressDialog(ExchangeYouka.this, false);

                presenter.sendPayPointRequest(needAllIntegral, getString(R.string.integral_dui_you_ka),this);

            } else {

                ProgressDialogUtil.getInstance().showProgressDialog(ExchangeYouka.this, false);
                sendRequest();
            }

        } else {

            ToastUtil.i(this, getString(R.string.intergral_exchange_mingxi_1));

        }
    }

    /**
     * 发送请求
     *
     */
    private void sendRequest() {

        String youkaNumber = etYourYoukaNo.getText().toString().trim();

        List<ExchangeIntegralBean> adapterDataList = adapter.getTemp();

        String planId = allCustomerPopup.getPlanId();

        presenter.sendIntegralExchagneYoukaRequest(this,type,adapterDataList, planId, status + "",
                goodsAddressBean == null?"": goodsAddressBean.getAddressId(), 12 + "", youkaNumber);

    }

    /**
     * 根据兑换细则兑换现金
     */
    private void exchangeMoneyAction() {
        //tvExchangeMoney
        List<ExchangeIntegralBean> adapterDataList = adapter.getTemp();

        totalMoney = 0;

        needAllIntegral = 0;
        for (ExchangeIntegralBean bean : adapterDataList) {

            int point = Integer.parseInt(bean.getPoint());

            int result = Integer.parseInt(bean.getExchangeResult());

            totalMoney = totalMoney + result;

            if ("0".equals(bean.getBankId())) {

                needAllIntegral = needAllIntegral + point;
            }
        }

        tvExchangeMoney.setText(totalMoney + "元");

    }

    public void ivPhotoBookAction(View view) {

        AppUtils.callContact(this);
    }

    public void exchangeNormAction(View v) {

        presenter.goIntoExchangeNorm( AppConstant.h5_category_integral_exchange_norm,getString(R.string.intergral_exchange_zhunze),this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (allCustomerPopup != null) {
            allCustomerPopup.dismiss();

        }

        return super.onTouchEvent(event);

    }

    @Event(R.id.ibModfy)
    private void ibModfy(View v) {

        Intent intentAddress = new Intent(this, AddressManageActivity.class);

        intentAddress.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);

        startActivityForResult(intentAddress, 1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 1000:

                if (resultCode == 1001) {//抬头信息


                } else if (resultCode == 1002) {//送货地址

                    GoodsAddressBean temp = (GoodsAddressBean) data.getSerializableExtra("addressBeanTemp");

                    callSuccessViewLogic(temp,1);

                }

                break;
            default:
                break;

        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if( o != null){

            if( o instanceof  GoodsAddressBean){

                GoodsAddressBean temp = (GoodsAddressBean) o;

                goodsAddressBean = temp;

                tvPeopleName.setText(temp.contacts);

                tvPhone.setText(temp.phone);

                tvAddress.setText(temp.provinceName + " " + temp.cityName + " " + temp.address);
            }else if( o instanceof String){

                String s = (String) o;

                if(s .equals(IIntegralExchange.SPECIAL_CODE)){

                    sendRequest();
                }
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
