package com.yzb.card.king.ui.integral;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 类  名：兑现金
 * 作  者：Li Yubing
 * 日  期：2016/6/20
 * 描  述：
 */
@ContentView(R.layout.activity_exchange_money)
public class ExchangeMoneyActivity extends BaseActivity implements BaseViewLayerInterface {
    //控件
    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.llIntegral)
    private View llIntegral;

    @ViewInject(R.id.tvExchangeMoney)
    private TextView tvExchangeMoney;

    @ViewInject(R.id.rlArrow)
    private RelativeLayout rlArrow;

    @ViewInject(R.id.tvExchangeName)
    private TextView tvExchangeName;

    @ViewInject(R.id.tvScale)
    private TextView tvScale;

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

    //控件
    private WholeListView wvll;

    private IntegralExchangePopup allCustomerPopup;

    private ExchangeDtailAdapter adapter;

    private ExchangeIntegralBean currentExchangeIntegralBean = null;

    private int totalMoney = 0;

    private int needAllIntegral = 0;

    private boolean firstFlag = true;

    private IntegralExchangePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new IntegralExchangePresenter(this);
        initView();

        initData();
    }

    private void initData() {

        slideShowView1.setParam(AppConstant.money_type_parentid,selectedCity.cityId,"1010");
    }


    private void initView() {
        titleName.setText(R.string.integral_dui_xian_jin);

        tvIntegralNum.setFocusable(false);

        etHandler.sendEmptyMessageDelayed(0, 1000);
        wvll = (WholeListView) findViewById(R.id.wvAll);

        svParent.scrollTo(0, 0);

        adapter = new ExchangeDtailAdapter(this, myHandler);

        wvll.setAdapter(adapter);

        rlArrow.setOnClickListener(myClickListener);

        tvExchangeMoney.setText(totalMoney + "元");

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

                                ToastUtil.i(ExchangeMoneyActivity.this, getString(R.string.integral_no_integer));
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

                                        DialogUtil.getInstance().showSingleButtonDialog(ExchangeMoneyActivity.this, getString(R.string.dialog_title), getString(R.string.dialog_msg_platform_integral_num) + personageIntegralNum, getString(R.string.dialog_button_know));

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

                AppUtils.hideSoftWindow(ExchangeMoneyActivity.this, tvIntegralNum);
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
        }
    };
    /**
     * 刷新页面举报
     */
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            exchangeMoneyAction();
        }
    };
    /**
     * 监听事件
     */
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.rlArrow:


                    allCustomerPopup = new IntegralExchangePopup(llIntegral, ExchangeMoneyActivity.this, new
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

                    allCustomerPopup.create(10, null);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 添加新规划
     *
     * @param view
     */
    public void addNewPlan(View view) {

        if (currentExchangeIntegralBean != null) {

            String tvMoneyNumberStr = tvMoneyNumber.getText().toString().trim();

            String tvIntegralNumStr = tvIntegralNum.getText().toString().trim();


            if (TextUtils.isEmpty(tvIntegralNumStr)) {

                ToastUtil.i(ExchangeMoneyActivity.this, getString(R.string.integral_input_integral_num));

                return;

            }
            if (TextUtils.isEmpty(tvMoneyNumberStr)) {

                ToastUtil.i(ExchangeMoneyActivity.this, getString(R.string.integral_input_money_num));

                return;

            }
            long integralNum = Long.parseLong(tvMoneyNumberStr);
            if (integralNum <= 0) {

                ToastUtil.i(ExchangeMoneyActivity.this, R.string.integral_exchange_must_one);

                return;
            }

            if (integralNum > currentExchangeIntegralBean.getPlatformIntegralNum()) {

                DialogUtil.getInstance().showSingleButtonDialog(ExchangeMoneyActivity.this, getString(R.string.dialog_title), getString(R.string.dialog_msg_platform_integral_num) + currentExchangeIntegralBean.getPlatformIntegralNum(), getString(R.string.dialog_button_know));

                return;
            }
            currentExchangeIntegralBean.setPoint(tvIntegralNumStr);

            currentExchangeIntegralBean.setExchangeResult(tvMoneyNumberStr);


            if ("0".equals(currentExchangeIntegralBean.getBankId())) {//平台积分

                int pointNumber = Integer.parseInt(currentExchangeIntegralBean.getPoint());

                needAllIntegral = needAllIntegral + pointNumber;


                long accountIntegralNum = UserManager.getInstance().getIntegrationNumeber();

                if (accountIntegralNum < needAllIntegral) {

                    ToastUtil.i(ExchangeMoneyActivity.this, getString(R.string.integral_user_lack_integral) + ",目前只有"
                            + accountIntegralNum + "积分。");

                    needAllIntegral = needAllIntegral - pointNumber;
                } else {

                    boolean flag = adapter.addOneNewData(currentExchangeIntegralBean);

                    if (flag) {
                        //计算兑换现金
                        exchangeMoneyAction();
                    } else {
                        ToastUtil.i(ExchangeMoneyActivity.this, getString(R.string.integral_added_this_ruled));
                    }
                }
            } else {

                boolean flag = adapter.addOneNewData(currentExchangeIntegralBean);
                if (flag) {
                    //计算兑换现金
                    exchangeMoneyAction();
                } else {
                    ToastUtil.i(ExchangeMoneyActivity.this, getString(R.string.integral_added_this_ruled));
                }
            }
        } else {
            ToastUtil.i(ExchangeMoneyActivity.this, R.string.integral_please_select_exchange_detail);
        }
    }
    /**
     * 提交事件
     *
     * @param v
     */

    public void submitAction(View v) {

        if (adapter.getCount() > 0) {

            if (needAllIntegral > 0) {
                ProgressDialogUtil.getInstance().showProgressDialog(ExchangeMoneyActivity.this, false);

                presenter.sendPayPointRequest(needAllIntegral, getString(R.string.integral_dui_xian_jin), this);

            } else {

                ProgressDialogUtil.getInstance().showProgressDialog(ExchangeMoneyActivity.this, false);
                sendRequest();
            }


        } else {

            ToastUtil.i(this, getString(R.string.intergral_exchange_mingxi_1));

        }

    }

    /**
     * 发送请求
     */
    public void sendRequest() {

        List<ExchangeIntegralBean> adapterDataList = adapter.getTemp();

        String planId = allCustomerPopup.getPlanId();

        presenter.sendIntegralExchangeCashRequest(10,adapterDataList,planId,this);

    }

    public void exchangeNormAction(View v) {

        presenter.goIntoExchangeNorm(AppConstant.h5_category_integral_exchange_norm, getString(R.string.intergral_exchange_zhunze), this);

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
