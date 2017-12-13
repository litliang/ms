package com.yzb.card.king.ui.discount.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.ActivityCouponParam;
import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.common.UseShopCouponNumBean;
import com.yzb.card.king.bean.my.AccountInfoBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.appdialog.UsedRedPacketDialogFragment;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.my.presenter.AccountInfoPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：特惠付款(新)
 * 作  者：Li Yubing
 * 日  期：2017/8/11
 * 描  述：
 */
@ContentView(R.layout.activity_app_preferent_payment)
public class AppPreferentialPaymentActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    @ViewInject(R.id.tvTicketNum)
    private TextView tvTicketNum;

    @ViewInject(R.id.tvRedTicketNum)
    private TextView tvRedTicketNum;

    @ViewInject(R.id.tvOneMsg)
    private TextView tvOneMsg;

    @ViewInject(R.id.tvTwoMsg)
    private TextView tvTwoMsg;

    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;
    //代金券
    @ViewInject(R.id.llDaijinQuan)
    private LinearLayout llDaijinQuan;
    //优惠券
    @ViewInject(R.id.llRedDaijinQuan)
    private LinearLayout llRedDaijinQuan;

    @ViewInject(R.id.tvConponeOneMsg)
    private TextView tvConponeOneMsg;

    @ViewInject(R.id.tvConponeTwoMsg)
    private TextView tvConponeTwoMsg;

    private TextView tvTotalAmount, tvGoodsName, tvPrice;

    private String goodName;

    private PayOrderBean bean;
    /**
     * 用戶持有的代金券数量
     */
    private int ticketNumber = 0;
    /**
     * 用戶持有的优惠券数量
     */
    private int redRicketNumber = 0;

    private BaseCouponBean baseCouponBean = null;

    private ActivityCouponParam activityCouponParam;

    private GetCouponPersenter persenter;

    private GoldTicketParam goldTicketParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    private void initView() {
        setTitleNmae("特惠付款");

        tvConponeOneMsg.setVisibility(View.GONE);

        tvConponeTwoMsg.setVisibility(View.GONE);

        findViewById(R.id.tv_commit).setOnClickListener(this);

        llDaijinQuan.setOnClickListener(this);

        llRedDaijinQuan.setOnClickListener(this);

        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);

        tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);

        tvPrice = (TextView) findViewById(R.id.tvPrice);
    }

    private void initData() {
        persenter = new GetCouponPersenter(this);

        if (getIntent().hasExtra("goodName") && getIntent().hasExtra("orderData")) {

            goodName = getIntent().getStringExtra("goodName");

            bean = (PayOrderBean) getIntent().getSerializableExtra("orderData");

            tvPrice.setText(Utils.subZeroAndDot(bean.getOrderAmount() + ""));

            tvGoodsName.setText(goodName);

            activityCouponParam = new ActivityCouponParam(bean.getOrderId(), bean.getOrderAmount());

            calPayMoney();
        }
        /**
         * 查询用户持有的代金券信息
         */
        if (getIntent().hasExtra("goldTicketParam")) {

            goldTicketParam = (GoldTicketParam) getIntent().getSerializableExtra("goldTicketParam");

            persenter.sendActivityDeductionInfoRequest(bean.getOrderId(), goldTicketParam.getIndustryId(), goldTicketParam.getShopId(), goldTicketParam.getStoreId(), goldTicketParam.getGoodsId());

            llDaijinQuan.setTag(goldTicketParam);

            llRedDaijinQuan.setTag(goldTicketParam);
        }

    }

    /**
     * 计算实际付款总金额
     */
    private void calPayMoney() {
        double total = bean.getOrderAmount();
        //计算代金券或优惠券抵扣金额
        double daijinMoney = 0;

        if (baseCouponBean != null) {

            daijinMoney = calDeductionMoneyByTicket();

        }

        //可抵扣的红包和抵扣券总额
        double totalDikouMoeny = daijinMoney;

        if (totalDikouMoeny <= total) {

            tvTotalAmount.setText(Utils.subZeroAndDot((total - totalDikouMoeny) + ""));

            activityCouponParam.setOrderAmount(totalDikouMoeny);

            tv_commit.setBackgroundResource(R.color.color_980100);

            tv_commit.setOnClickListener(this);

        } else {

            ToastUtil.i(this, "抵扣总金额不能大于订单金额");

            tv_commit.setBackgroundResource(R.color.color_999999);

            tv_commit.setOnClickListener(null);

        }

    }

    /**
     * 计算代金券/优惠券可抵扣的金额
     */
    private double calDeductionMoneyByTicket() {

        double dMoney = Double.parseDouble(tvPrice.getText().toString());

        if (baseCouponBean == null) {

            return 0;
        }

        int fullAmount = baseCouponBean.getFullAmount();

        if (dMoney >= fullAmount || fullAmount == 0) {

            //计算优惠券可抵消多少钱
            String couponType = baseCouponBean.getCouponType();

            if ("1".equals(couponType)) {//满减

                int a = baseCouponBean.getCutAmount();

                return a;

            } else if ("2".equals(couponType)) {

                double a = Double.parseDouble("0." + baseCouponBean.getCutAmount());

                return a * dMoney;

            } else {

                return baseCouponBean.getCutValue();
            }

        } else {

            return 0;
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_commit://付款方式

                if (bean != null && goldTicketParam != null) {

                    Intent intent = new Intent(AppPreferentialPaymentActivity.this, AppPaymentMethodActivity.class);

                    intent.putExtra("orderData", bean);

                    intent.putExtra("goodName", goodName);

                    double paymentMoney = Double.parseDouble(tvTotalAmount.getText().toString());

                    intent.putExtra("paymentMoney", paymentMoney);

                    intent.putExtra("goldTicketParam", goldTicketParam);

                    intent.putExtra("activityCouponParam", activityCouponParam);

                    startActivityForResult(intent, 1000);
                }

                break;
            case R.id.llDaijinQuan://代金券

                if (v.getTag() != null) {

                    GoldTicketParam goldTicketParam = (GoldTicketParam) v.getTag();

                    Intent intent = new Intent(AppPreferentialPaymentActivity.this, PayMoneySelectTicketActivity.class);

                    intent.putExtra("goldTicketParam", goldTicketParam);

                    if (selectedCouponCode == 7778) {

                        intent.putExtra("selectActId", selectActId);

                    } else if (selectedCouponCode == 7779) {

                        intent.putExtra("selectActId", -1);
                    }

                    intent.putExtra("titleName", "选择代金券");

                    intent.putExtra("issuePlatform", 1);

                    double orderMoney = bean.getOrderAmount();

                    intent.putExtra("orderMoney", orderMoney);

                    startActivityForResult(intent, 1000);
                }

                break;

            case R.id.llRedDaijinQuan://优惠券

                if (v.getTag() != null) {

                    GoldTicketParam goldTicketParam = (GoldTicketParam) v.getTag();

                    Intent intent = new Intent(AppPreferentialPaymentActivity.this, PayMoneySelectTicketActivity.class);

                    intent.putExtra("goldTicketParam", goldTicketParam);


                    if (selectedCouponCode == 7778) {

                        intent.putExtra("selectActId", -1);

                    } else if (selectedCouponCode == 7779) {

                        intent.putExtra("selectActId", selectActId);
                    }

                    intent.putExtra("titleName", "选择优惠券");

                    intent.putExtra("issuePlatform", 2);

                    double orderMoney = bean.getOrderAmount();

                    intent.putExtra("orderMoney", orderMoney);

                    startActivityForResult(intent, 1000);

                }
                break;

            default:
                break;
        }
    }

    /**
     * 选择的代金券/优惠券id
     */
    private long selectActId = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            if (resultCode == 7777) {

                finish();

            } else if (resultCode == 7778 || resultCode == 7779) {//代金券或优惠券


                if (data != null) {

                    baseCouponBean = (BaseCouponBean) data.getSerializableExtra("baseCouponBean");
                    //代金券id
                    activityCouponParam.setCouponId(baseCouponBean.getActId());

                    activityCouponParam.setCouponItemId(baseCouponBean.getItemId());

                    double tickeMoeny = calDeductionMoneyByTicket();

                    activityCouponParam.setCouponAmount(tickeMoeny);

                    checkYouhuiData(activityCouponParam);

                    selectedCouponCode = resultCode;

                    if (resultCode == 7778) {

                        tvRedTicketNum.setText(redRicketNumber + "");

                        tvTwoMsg.setVisibility(View.VISIBLE);

                        tvConponeTwoMsg.setVisibility(View.GONE);

                    } else if (resultCode == 7779) {


                        tvTicketNum.setText(ticketNumber + "");

                        tvOneMsg.setVisibility(View.VISIBLE);

                        tvConponeOneMsg.setVisibility(View.GONE);
                    }
                } else {

                    if (resultCode == selectedCouponCode) {//选择的券和当前的券类型一样则替换


                        baseCouponBean = null;

                        selectActId = -1;

                        //代金券id
                        activityCouponParam.setCouponId(selectActId);

                        activityCouponParam.setCouponItemId(-1);

                        activityCouponParam.setCouponAmount(0);

                        activityCouponParam.setOrderAmount(0);

                        calPayMoney();
                    }


                    if (resultCode == 7778) {


                        tvTicketNum.setText(ticketNumber + "");

                        tvOneMsg.setVisibility(View.VISIBLE);

                        tvConponeOneMsg.setVisibility(View.GONE);

                    } else if (resultCode == 7779) {

                        tvRedTicketNum.setText(redRicketNumber + "");

                        tvTwoMsg.setVisibility(View.VISIBLE);

                        tvConponeTwoMsg.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    //记录选择券的code码
    private int selectedCouponCode = 0;

    /**
     * 检查 代金券、优惠券数据
     *
     * @param activityCouponParam
     */
    private void checkYouhuiData(ActivityCouponParam activityCouponParam) {

        double couponAmout = 0;

        if (activityCouponParam != null) {

            couponAmout = activityCouponParam.getCouponAmount();
        }

        //去除优惠金额后的实付金额
        double paymentMoney = bean.getOrderAmount() - couponAmout;

        this.activityCouponParam.setOrderAmount(paymentMoney);

        showPDialog("正在请求数据……");

        persenter.sendActivityDeductionCheckRequest(this.activityCouponParam);

    }


    @Override
    public void callSuccessViewLogic(Object o, int type) {

        if (GetCouponPersenter.ACTIVITYDEDUCTIONINFO_CODE == type) {

            UseShopCouponNumBean baseCouponBeans = JSON.parseObject(o + "", UseShopCouponNumBean.class);

            ticketNumber = baseCouponBeans.getCashCouponQuantity();

            tvTicketNum.setText(ticketNumber + "");

            redRicketNumber = baseCouponBeans.getCouponQuantity();

            tvRedTicketNum.setText(redRicketNumber + "");

        } else if (GetCouponPersenter.ACTIVITYDEDUCTIONCHECK_CODE == type) {

            dimissPdialog();

            if (baseCouponBean != null) {

                selectActId = baseCouponBean.getActId();
            }

            //抵扣的代金券金額
            double tickeMoeny = calDeductionMoneyByTicket();

            if (selectedCouponCode == 7778) {//代金券

                if (tickeMoeny != 0) {

                    tvTicketNum.setText("¥" + Utils.subZeroAndDot(tickeMoeny + ""));

                    tvOneMsg.setVisibility(View.GONE);

                    tvConponeOneMsg.setVisibility(View.VISIBLE);

                    tvConponeOneMsg.setText(baseCouponBean.getActName());

                    tvConponeTwoMsg.setVisibility(View.GONE);

                } else {

                    tvTicketNum.setText(ticketNumber + "");

                    tvOneMsg.setVisibility(View.VISIBLE);

                    tvConponeTwoMsg.setVisibility(View.GONE);

                    tvConponeOneMsg.setVisibility(View.GONE);


                }

            } else if (selectedCouponCode == 7779) {//优惠券


                if (tickeMoeny != 0) {

                    tvRedTicketNum.setText("¥" + Utils.subZeroAndDot(tickeMoeny + ""));

                    tvTwoMsg.setVisibility(View.GONE);


                    tvConponeTwoMsg.setVisibility(View.VISIBLE);

                    tvConponeTwoMsg.setText(baseCouponBean.getActName());

                    tvConponeOneMsg.setVisibility(View.GONE);

                } else {

                    tvRedTicketNum.setText(ticketNumber + "");

                    tvTwoMsg.setVisibility(View.VISIBLE);

                    tvConponeTwoMsg.setVisibility(View.GONE);

                    tvConponeOneMsg.setVisibility(View.GONE);
                }
            }

            activityCouponParam.setBonusAmount(0);

            activityCouponParam.setGiftcardAmount(0);

            calPayMoney();

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        dimissPdialog();

        if (GetCouponPersenter.ACTIVITYDEDUCTIONINFO_CODE == type) {

            tvTicketNum.setText(ticketNumber + "");

            tvRedTicketNum.setText(redRicketNumber + "");

        } else if (GetCouponPersenter.ACTIVITYDEDUCTIONCHECK_CODE == type) {

            tv_commit.setBackgroundResource(R.color.color_999999);

            tv_commit.setOnClickListener(null);

            if (!TextUtils.isEmpty(o + "")) {

                Error error = JSONObject.parseObject(JSON.toJSONString(o), Error.class);

                ToastUtil.i(GlobalApp.getInstance().getContext(), error.getError());
            }

        }
    }
}
