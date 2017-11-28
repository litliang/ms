package com.yzb.card.king.ui.credit.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.common.LifeStageDetailBean;
import com.yzb.card.king.bean.common.PaymethodAndBankPreStageBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.AppPayBankLifeStagePopup;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.credit.adapter.AppBankPaymentAdapter;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import java.util.List;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：支付方式
 * 作  者：Li Yubing
 * 日  期：2017/8/11
 * 描  述：
 */
public class AppBankPaymentMethodHolder extends BaseViewHolder<PaymethodAndBankPreStageBean> implements View.OnClickListener {//

    private Context context;

    private TextView tvBankName;

    private TextView tvBankNoMsg;

    private ImageView ivBankImage;

    private ImageView ivSelected;

    private ImageView ivLifeStages;

    private TextView tvYouhui;

    private LinearLayout llRight, llOne, llBankLifeStage;

    private TextView tvLifeStageMsg;

    private LinearLayout tvOtherPayMethod;

    private AppBankPaymentAdapter.AdapterDataCallBack adapterDataCallBack;

    private AppPayBankLifeStagePopup channelTypePopup;

    private Handler uiHandler;

    private int selectedItemIndex = 0;//用户选择触发的付款方式编号

    public AppBankPaymentMethodHolder(ViewGroup parent, AppBankPaymentAdapter.AdapterDataCallBack adapterDataCallBack, Handler uiHandler) {
        super(parent, R.layout.view_item_bank_payment_method);

        context = parent.getContext();

        this.adapterDataCallBack = adapterDataCallBack;

        this.uiHandler = uiHandler;

    }

    @Override
    public void setData(PaymethodAndBankPreStageBean data) {

        llRight.setTag(data);

        llOne.setTag(data);

        tvBankNoMsg.setVisibility(View.VISIBLE);

        String accountType = data.getAccountType();

        if ("1".equals(accountType)) {//钱包

            tvBankName.setText(data.getTypeName());

            tvYouhui.setText(data.getPayMsg());

            tvYouhui.setTextColor(Color.parseColor("#999999"));

            String bankLogo = data.getBankLogo();

            if (!TextUtils.isEmpty(bankLogo)) {

                Glide.with(context).load(ServiceDispatcher.getImageUrl(bankLogo)).into(ivBankImage);

            } else {

                ivBankImage.setImageResource(data.getAccountLogo());
            }
            ivSelected.setBackgroundResource(R.drawable.selector_bank_pay_method_red_right);

            ivSelected.setEnabled(data.isSelected());

            tvYouhui.setVisibility(View.VISIBLE);

            ivLifeStages.setVisibility(View.INVISIBLE);

            tvOtherPayMethod.setVisibility(View.GONE);

            tvBankNoMsg.setVisibility(View.GONE);

        } else if ("2".equals(accountType)) {//信用卡
            //选择的分期信息
            LifeStageDetailBean.StageBean selectedBean = data.getSelectedBean();

            if (selectedBean != null) {

                llBankLifeStage.setVisibility(View.VISIBLE);

                String stageStr = selectedBean.getStage().replace("期", "");

                int stageNumber = Integer.parseInt(stageStr);
                /**
                 * 每期费用
                 */
                double avgMone = data.getPaymentMoney() / stageNumber;

                tvLifeStageMsg.setText("分期方式：" + Utils.subZeroAndDot(avgMone + "") + "元×" + selectedBean.getStage());

            } else {

                llBankLifeStage.setVisibility(View.GONE);

            }

            List<LifeStageDetailBean.StageBean> list = data.getStageList();

            if (list != null && list.size() > 0) {

                ivLifeStages.setVisibility(View.VISIBLE);

            } else {

                ivLifeStages.setVisibility(View.GONE);

            }

            BankActivityInfoBean infoBean = data.getBankActInfo();

            if (infoBean != null) {

                tvYouhui.setVisibility(View.VISIBLE);

                tvYouhui.setTextColor(Color.parseColor("#972c1f"));

                int actCls = infoBean.getActCls();

                if (actCls == 1) {//满减

                    tvYouhui.setText("满" + Utils.subZeroAndDot(infoBean.getFuuAmount() + "") + "减" + Utils.subZeroAndDot(infoBean.getDisContent() + ""));

                } else if (actCls == 2) {//折扣

                    int discontent = infoBean.getDisContent();

                    String disStr = Utils.handNumberToString(Float.parseFloat("" + discontent));

                    tvYouhui.setText("打" + disStr + "折");

                } else if (actCls == 3) {//随机立减

                    tvYouhui.setText("随机立减");
                }

            } else {

                tvYouhui.setText("");
            }

            tvBankName.setText(data.getBankName());

            tvBankNoMsg.setText(data.getTypeName() + " (" + data.getSortNo() + ")");

            String bankLogo = data.getBankLogo();

            if (!TextUtils.isEmpty(bankLogo)) {

                Glide.with(context).load(ServiceDispatcher.getImageUrl(bankLogo)).into(ivBankImage);
            }

            ivSelected.setBackgroundResource(R.drawable.selector_bank_pay_method_red_right);

            ivSelected.setEnabled(data.isSelected());

            tvOtherPayMethod.setVisibility(View.GONE);

        } else if ("3".equals(accountType)) {//储蓄卡

            ivLifeStages.setVisibility(View.GONE);

            tvYouhui.setText("");

            tvBankName.setText(data.getBankName());

            tvBankNoMsg.setText(data.getTypeName() + " (" + data.getSortNo() + ")");

            String bankLogo = data.getBankLogo();

            if (!TextUtils.isEmpty(bankLogo)) {

                Glide.with(context).load(ServiceDispatcher.getImageUrl(bankLogo)).into(ivBankImage);
            }

            ivSelected.setBackgroundResource(R.drawable.selector_bank_pay_method_red_right);

            ivSelected.setEnabled(data.isSelected());

            tvOtherPayMethod.setVisibility(View.GONE);

        } else if ("4".equals(accountType)) {//礼品卡余额

            tvBankName.setText(data.getTypeName());

            tvYouhui.setText(data.getPayMsg());

            tvYouhui.setTextColor(Color.parseColor("#999999"));

            ivBankImage.setImageResource(data.getAccountLogo());

            ivSelected.setBackgroundResource(R.drawable.selector_bank_pay_method_red_right);

            ivSelected.setEnabled(data.isSelected());

            tvYouhui.setVisibility(View.VISIBLE);

            ivLifeStages.setVisibility(View.INVISIBLE);

            tvOtherPayMethod.setVisibility(View.GONE);

            tvBankNoMsg.setVisibility(View.GONE);

        } else if ("5".equals(accountType)) {//红包余额

            tvBankName.setText(data.getTypeName());

            tvYouhui.setText(data.getPayMsg());

            tvYouhui.setTextColor(Color.parseColor("#999999"));

            ivBankImage.setImageResource(data.getAccountLogo());

            ivSelected.setBackgroundResource(R.drawable.selector_bank_pay_method_red_right);

            ivSelected.setEnabled(data.isSelected());

            tvYouhui.setVisibility(View.VISIBLE);

            ivLifeStages.setVisibility(View.INVISIBLE);

            tvOtherPayMethod.setVisibility(View.GONE);

            tvBankNoMsg.setVisibility(View.GONE);

        } else if ("9999".equals(accountType) || "9998".equals(accountType) || "9997".equals(accountType)) {//微信支付、信用支付、支付宝

            tvBankName.setText(data.getTypeName());

            tvBankNoMsg.setText(data.getPayMsg());

            ivBankImage.setImageResource(data.getAccountLogo());

            ivSelected.setBackgroundResource(R.drawable.selector_bank_pay_method_red_right);

            ivSelected.setEnabled(data.isSelected());

            tvYouhui.setVisibility(View.INVISIBLE);

            ivLifeStages.setVisibility(View.INVISIBLE);

            tvOtherPayMethod.setVisibility(View.GONE);

        } else if ("10000".equals(accountType)) {//添加新卡

            tvBankName.setText(data.getTypeName());

            tvBankNoMsg.setText(data.getPayMsg());

            tvBankNoMsg.setVisibility(View.GONE);

            ivBankImage.setImageResource(data.getAccountLogo());

            ivSelected.setBackgroundResource(R.mipmap.icon_add_bank_info);

            tvYouhui.setVisibility(View.INVISIBLE);

            ivLifeStages.setVisibility(View.INVISIBLE);

            tvOtherPayMethod.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();

        tvBankName = findViewById(R.id.tvBankName);

        tvBankNoMsg = findViewById(R.id.tvBankNoMsg);

        ivBankImage = findViewById(R.id.ivBankImage);

        ivSelected = findViewById(R.id.ivSelected);

        ivLifeStages = findViewById(R.id.ivLifeStages);

        tvYouhui = findViewById(R.id.tvYouhui);

        llRight = findViewById(R.id.llRight);

        llRight.setOnClickListener(this);

        llOne = findViewById(R.id.llOne);

        llOne.setOnClickListener(this);

        tvOtherPayMethod = findViewById(R.id.tvOtherPayMethod);

        llBankLifeStage = findViewById(R.id.llBankLifeStage);

        tvLifeStageMsg = findViewById(R.id.tvLifeStageMsg);

        tvOtherPayMethod.setOnClickListener(this);

        tvOtherPayMethod.setTag(false);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.llRight:

                Object object1 = v.getTag();

                if (object1 == null) {

                    return;
                }

                PaymethodAndBankPreStageBean payMethodBean = (PaymethodAndBankPreStageBean) object1;

                //选择支付方式，通知主页面更新数据，刷新页面选择状态
                if (!"10000".equals(payMethodBean.getAccountType())) {

                    int objectIndex = getAdapterPosition();

                    uiHandler.sendEmptyMessage(objectIndex);

                }
                //红包支付、礼品卡支付、钱包支付、添加新支付、微信支付、信用支付
                if ("5".equals(payMethodBean.getAccountType()) ||"4".equals(payMethodBean.getAccountType()) ||"1".equals(payMethodBean.getAccountType()) || "10000".equals(payMethodBean.getAccountType()) || "9999".equals(payMethodBean.getAccountType()) || "9998".equals(payMethodBean.getAccountType())) {

                    if (adapterDataCallBack != null) {

                        adapterDataCallBack.selectedPayMethod(payMethodBean);
                    }

                } else {

                    String cardType = payMethodBean.getCardType();
                    /**
                     * 检测信息是否有信用卡和分期信息
                     */
                    if ("2".equals(cardType)) {

                        if (payMethodBean.getStageList() != null && payMethodBean.getStageList().size() > 0) {

                            selectedItemIndex = getAdapterPosition();

                            Activity activity = GlobalApp.getInstance().getPublicActivity();

                            if (channelTypePopup == null) {

                                channelTypePopup = new AppPayBankLifeStagePopup(activity, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                                channelTypePopup.setCallBack(dataCallBackChannelType);

                            }

                            channelTypePopup.setPayMethod(payMethodBean);

                            View rootView = activity.getWindow().getDecorView();

                            channelTypePopup.showBottomByViewPP(rootView);


                        } else {

                            if (adapterDataCallBack != null) {

                                adapterDataCallBack.selectedPayMethod(payMethodBean);
                            }
                        }

                    } else if ("1".equals(cardType)) {//储蓄卡

                        if (adapterDataCallBack != null) {

                            adapterDataCallBack.selectedPayMethod(payMethodBean);
                        }
                    }
                }

                break;

            case R.id.llOne:

                Object object = v.getTag();

                if (object == null) {

                    return;
                }

                PaymethodAndBankPreStageBean payMethodBean1 = (PaymethodAndBankPreStageBean) object;

                if ("10000".equals(payMethodBean1.getAccountType())) {//添加信用卡

                    if (adapterDataCallBack != null) {

                        adapterDataCallBack.selectedPayMethod(payMethodBean1);
                    }
                }

                break;

            case R.id.tvOtherPayMethod://展开其它支付方式


                if (adapterDataCallBack != null) {

                    adapterDataCallBack.hideOrShowOtherPayData(true);
                }


                break;
            default:
                break;
        }
    }

    private AppPayBankLifeStagePopup.BottomDataListCallBack dataCallBackChannelType = new AppPayBankLifeStagePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(PaymethodAndBankPreStageBean payMethod, int selectIndex) {

            if (selectIndex != -1) {

                channelTypePopup.getBaseBottomFullPP().dismiss();
            }

            if (adapterDataCallBack != null) {

                adapterDataCallBack.selectedPayMethod(payMethod);

                Message message = uiHandler.obtainMessage();

                message.what = -1;

                message.arg1 = selectIndex;//选择分期对象编号

                message.arg2 = selectedItemIndex;//选择支付方式编号

                uiHandler.sendMessage(message);

            }
        }
    };
}
