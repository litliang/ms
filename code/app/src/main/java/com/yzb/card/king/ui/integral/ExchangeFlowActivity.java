package com.yzb.card.king.ui.integral;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;
import com.yzb.card.king.sys.AppConstant;
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
import com.yzb.card.king.util.ValidatorUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 类  名：兑流量
 * 作  者：Li Yubing
 * 日  期：2016/6/20
 * 描  述：
 */
@ContentView(R.layout.activity_exchange_flow)
public class ExchangeFlowActivity extends BaseActivity implements BaseViewLayerInterface {

    //控件
    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.rgLogin)
    private RadioGroup rgLogin;

    @ViewInject(R.id.etPhonenum)
    private EditText etPhonenum;

    @ViewInject(R.id.ivPhotoBook)
    private ImageView ivPhotoBook;

    @ViewInject(R.id.tvExchangeName)
    private TextView tvExchangeName;

    @ViewInject(R.id.tvScale)
    private TextView tvScale;

    @ViewInject(R.id.rlArrow)
    private RelativeLayout rlArrow;

    @ViewInject(R.id.llIntegral)
    private View llIntegral;

    @ViewInject(R.id.tvExchangeMoney)
    private TextView tvExchangeMoney;

    @ViewInject(R.id.tvMoneyNumber)
    private EditText tvMoneyNumber;

    @ViewInject(R.id.tvIntegralNum)
    private EditText tvIntegralNum;

    @ViewInject(R.id.slideShowView1)
    private SlideShow1ItemView slideShowView1;

    @ViewInject(R.id.svParent)
    private ScrollView svParent;

    //数据
    private float slide1_whrate = 432 / 1080.0f;

    private WholeListView wvll;

    private ExchangeDtailAdapter adapter;

    private IntegralExchangePopup allCustomerPopup;

    private ExchangeIntegralBean currentExchangeIntegralBean;

    private int type = 7;

    private int totalMoney = 0;

    private long needAllIntegral = 0;

    private boolean firstFlag = true;

    private IntegralExchangePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new IntegralExchangePresenter(this);
        initView();
        slideShowView1.setParam(AppConstant.flow_type_parentid,selectedCity.cityId,"1009");
    }

    private void initView() {

        titleName.setText(R.string.integral_dui_liu_liang);

        tvIntegralNum.setFocusable(false);

        etPhonenum.setFocusable(false);

        etHandler.sendEmptyMessageDelayed(0, 1000);

        wvll = (WholeListView) findViewById(R.id.wvAll);

        adapter = new ExchangeDtailAdapter(this, myHandler);

        wvll.setAdapter(adapter);

        rgLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                currentExchangeIntegralBean = null;

                totalMoney = 0;

                tvMoneyNumber.setText("");

                tvIntegralNum.setText("");

                needAllIntegral = 0;

                tvExchangeMoney.setText(totalMoney + "元");

                etPhonenum.setText("");

                tvExchangeName.setText(R.string.integral_select_integral_bili);

                tvScale.setText("");
                adapter.clear();
                if (checkedId == R.id.rbChinaMobile) {//中国移动


                    type = 7;
                } else if (checkedId == R.id.rbLinkMobile) {//联通

                    type = 8;
                } else if (checkedId == R.id.rbChinaEl) {//电信
                    type = 9;

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

                                ToastUtil.i(ExchangeFlowActivity.this, getString(R.string.integral_no_integer));

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
                                    if (!TextUtils.isEmpty(newStr)) {
                                        tvIntegralNum.setText(newStr);

                                        tvIntegralNum.setSelection(newStr.length());
                                        long newStrLong = Long.parseLong(newStr);

                                        String moneyStr = String.valueOf(newStrLong * Long.parseLong(currentExchangeIntegralBean
                                                .getExchangeResult()) / Long.parseLong(currentExchangeIntegralBean.getPoint()));

                                        tvMoneyNumber.setText(moneyStr);

                                        DialogUtil.getInstance().showSingleButtonDialog(ExchangeFlowActivity.this, getString(R.string.dialog_title), getString(R.string.dialog_msg_platform_integral_num) + personageIntegralNum, getString(R.string.dialog_button_know));

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

                AppUtils.hideSoftWindow(ExchangeFlowActivity.this,tvIntegralNum);
                return false;
            }
        });
    }

    private Handler etHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            etPhonenum.setFocusable(true);
            etPhonenum.setFocusableInTouchMode(true);
            etPhonenum.requestFocus();

            tvIntegralNum.setFocusable(true);
            tvIntegralNum.setFocusableInTouchMode(true);
            tvIntegralNum.requestFocus();//etPhonenum

        }
    };

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            exchangeMoneyAction();
        }
    };

    public void exchangeNormAction(View v) {

        presenter.goIntoExchangeNorm(AppConstant.h5_category_integral_exchange_norm,getString(R.string.intergral_exchange_zhunze),this);

    }

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.rlArrow:

                    allCustomerPopup = new IntegralExchangePopup(llIntegral, ExchangeFlowActivity.this, new
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

                ToastUtil.i(ExchangeFlowActivity.this, getString(R.string.integral_input_integral_num));

                return;

            }
            if (TextUtils.isEmpty(tvMoneyNumberStr)) {

                ToastUtil.i(ExchangeFlowActivity.this, getString(R.string.integral_input_money_num));

                return;

            }

            long integralNum = Long.parseLong(tvMoneyNumberStr);
            if (integralNum <= 0) {

                ToastUtil.i(ExchangeFlowActivity.this, R.string.integral_exchange_must_one);

                return;
            }

            if (integralNum > currentExchangeIntegralBean.getPlatformIntegralNum()) {

                DialogUtil.getInstance().showSingleButtonDialog(ExchangeFlowActivity.this, getString(R.string.dialog_title), getString(R.string.dialog_msg_platform_integral_num) + currentExchangeIntegralBean.getPlatformIntegralNum(), getString(R.string.dialog_button_know));


                return;
            }

            currentExchangeIntegralBean.setPoint(tvIntegralNumStr);

            currentExchangeIntegralBean.setExchangeResult(tvMoneyNumberStr);


            if ("0".equals(currentExchangeIntegralBean.getBankId())) {//平台积分

                int pointNumber = Integer.parseInt(currentExchangeIntegralBean.getPoint());

                needAllIntegral = needAllIntegral + pointNumber;


                long accountIntegralNum = UserManager.getInstance().getIntegrationNumeber();

                if (accountIntegralNum < needAllIntegral) {

                    ToastUtil.i(ExchangeFlowActivity.this, getString(R.string.integral_user_lack_integral) + ",目前只有"
                            + accountIntegralNum + "积分。");

                    needAllIntegral = needAllIntegral - pointNumber;
                } else {

                    boolean flag = adapter.addOneNewData(currentExchangeIntegralBean);

                    if (flag) {
                        //计算兑换现金
                        exchangeMoneyAction();
                    } else {
                        ToastUtil.i(ExchangeFlowActivity.this, getString(R.string.integral_added_this_ruled));
                    }
                }

            } else {

                boolean flag = adapter.addOneNewData(currentExchangeIntegralBean);

                if (flag) {
                    //计算兑换现金
                    exchangeMoneyAction();
                } else {
                    ToastUtil.i(ExchangeFlowActivity.this, getString(R.string.integral_added_this_ruled));
                }
            }

        } else {

            ToastUtil.i(ExchangeFlowActivity.this, R.string.integral_please_select_exchange_detail);
        }

    }

    /**
     * 提交事件
     *
     * @param v
     */
    public void submitAction(View v) {

        String phoneNum = etPhonenum.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNum)) {

            ToastUtil.i(this, getString(R.string.integral_input_phone_number));

            return;

        }
        if (!ValidatorUtil.isMobile(phoneNum)) {

            ToastUtil.i(this, getString(R.string.toast_chech_your_phone_number));

            return;

        }

        if (adapter.getCount() > 0) {

            if (needAllIntegral > 0) {

                ProgressDialogUtil.getInstance().showProgressDialog(ExchangeFlowActivity.this, false);

                presenter.sendPayPointRequest(needAllIntegral, getString(R.string.integral_dui_liu_liang), this);

            } else {

                ProgressDialogUtil.getInstance().showProgressDialog(ExchangeFlowActivity.this, false);
                sendRequest();
            }

        } else {

            ToastUtil.i(this, getString(R.string.intergral_exchange_mingxi_1));

        }

    }

    private void sendRequest() {

        List<ExchangeIntegralBean> adapterDataList = adapter.getTemp();

        String planId = allCustomerPopup.getPlanId();

        String phoneNum = etPhonenum.getText().toString().trim();

        presenter.sendIntegralExchangePhoneRequest(this,type, adapterDataList, planId, phoneNum);

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

    /**
     * 联系人
     * @param view
     */
    public void ivPhotoBookAction(View view) {

        AppUtils.callContact(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        switch (requestCode) {
            case AppUtils.PICK_CONTACT:
                if (resultCode != Activity.RESULT_OK) return;
                String[] arry = AppUtils.analyContactData(this, data);
                if (arry.length == 2) {
                    String linkMan = arry[0];
                    String linkManPhone = arry[1];

                    if (linkManPhone != null) {

                        etPhonenum.setText(linkManPhone);
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (o != null) {

            if (o instanceof String) {

                String s = (String) o;

                if (s.equals(IIntegralExchange.SPECIAL_CODE)) {

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
