package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.bean.RightBean;
import com.yzb.card.king.ui.app.presenter.PayMethodsPresenter;
import com.yzb.card.king.ui.app.presenter.UpdatePayOrderPresenter;
import com.yzb.card.king.ui.app.presenter.UpdateUserInfoPresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.app.view.PayMethodsView;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.credit.popup.PasswordDialog;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.pop.SmallAmoutPop;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ViewUtil;
import com.yzb.wallet.logic.comm.FreePayLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.22
 * 支付设置；
 */
public class PaySettingActivity extends BaseActivity implements View.OnClickListener, PayMethodsView,
        AppBaseView, BaseViewLayerInterface
{
    private static final String PAY_STATE_AUTO = "0"; // 支付状态：自动  “1”：手动；
    private static final String PAY_STATE_USER = "1";
    private SlideButton slideButtonAutoHk;
    private DragSortListView listview;
    private ArrayAdapter<String> adapter;
    private List<String> dataList; //付款方式名称列表；
    private List<PayMethod> payMethods;
    private UserBean userBean; //当前登录用户；
    private PayMethodsPresenter payMethodsPresenter; //获取付款方式；
    private UpdatePayOrderPresenter updatePayOrderPresenter; //更新支付顺序；
    private UpdateUserInfoPresenter userInfoPresenter;
    private View llAmount;
    private TextView tvAmount;
    private SlideButton sbNoPass;
    private View llNoPass;
    private View llAuto;
    private PasswordDialog passwordDialog;
    private boolean pwdChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_setting);

        payMethodsPresenter = new PayMethodsPresenter(this, this);
        updatePayOrderPresenter = new UpdatePayOrderPresenter(this, this);
        userInfoPresenter = new UpdateUserInfoPresenter(this);
        assignViews();
        getPayMethodList();
    }

    public void assignViews()
    {
        userBean = UserManager.getInstance().getUserBean();
        setHeader();
        initPayOrderView();
        initAmountView();
    }

    private void initPayOrderView()
    {
        llAuto = findViewById(R.id.llAuto);
        findViewById(R.id.llSlideButton).setOnClickListener(this);
        findViewById(R.id.scrollView).scrollTo(0, 0);
        //说明；
        TextView tv_notice = (TextView) findViewById(R.id.tv_notice);
        tv_notice.setOnClickListener(this);
        String htmlText1 = "<p><font color='#7A7A7A'>" + getString(R.string.setting_surport_version) + "</font>";
        String htmlText2 = "<font color='#3D6D99'>" + getString(R.string.setting_more_declare) + "</font></p>";
        tv_notice.setText(Html.fromHtml(htmlText1 + htmlText2));

        slideButtonAutoHk = (SlideButton) findViewById(R.id.slide_button_auto_hk);
        slideButtonAutoHk.setBgIconResId(R.mipmap.bg_setting_blue);
        slideButtonAutoHk.setEnable(false);
        //0：自动；1：手动
        if (userBean != null) slideButtonAutoHk.setToggleState(PAY_STATE_USER.equals(
                userBean.getPaymentStatus()) ? SlideButton.ToggleState.close
                : SlideButton.ToggleState.open);

        dragSortListViewInit();
        showOrHideDeductionView(!PAY_STATE_AUTO.equals(userBean.getPaymentStatus()));
    }

    private void setHeader()
    {
        setTitleNmae(getString(R.string.setting_pay_settting));
    }

    private void initAmountView()
    {
        llAmount = findViewById(R.id.llAmount);

        tvAmount = (TextView) findViewById(R.id.tvAmount);
        sbNoPass = (SlideButton) findViewById(R.id.sbNoPass);
        sbNoPass.setBgIconResId(R.mipmap.bg_setting_blue);
        llNoPass = findViewById(R.id.llNoPass);

        tvAmount.setOnClickListener(new View.OnClickListener()
        {

            private SmallAmoutPop smallAmountPop;

            @Override
            public void onClick(final View v)
            {
                if (pwdChecked)
                {
                    showAmountPop(v);
                } else
                {
                    showPassword(new PasswordDialog.OnPwdCheckListener()
                    {
                        @Override
                        public void onPwdRight()
                        {
                            pwdChecked = true;
                            passwordDialog.dismiss();
                            showAmountPop(v);
                        }

                        @Override
                        public void onPwdWrong()
                        {

                        }
                    });
                }
            }

            private void showAmountPop(View v)
            {
                if (smallAmountPop == null)
                {
                    smallAmountPop = new SmallAmoutPop();
                    smallAmountPop.setWidth(tvAmount.getWidth());
                    smallAmountPop.setListener(new OnItemClickListener<String>()
                    {
                        @Override
                        public void onItemClick(String data)
                        {
                            tvAmount.setText(data);
                            setSmallAmount();
                            smallAmountPop.dismiss();
                        }
                    });
                }
                smallAmountPop.showAsDropDown(v, 0, 0);
            }
        });
        sbNoPass.setEnable(false);
        llNoPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (pwdChecked)
                {
                    amountChange();
                } else
                {
                    showPassword(new PasswordDialog.OnPwdCheckListener()
                    {
                        @Override
                        public void onPwdRight()
                        {
                            pwdChecked = true;
                            passwordDialog.dismiss();
                            amountChange();
                        }

                        @Override
                        public void onPwdWrong()
                        {
                            passwordDialog.dismiss();
                        }
                    });
                }
            }

            private void amountChange()
            {
                setSmallAmountState(sbNoPass.getState() == SlideButton.ToggleState.open ?
                        SlideButton.ToggleState.close : SlideButton.ToggleState.open);
                setSmallAmount();
            }
        });

        querySmallAmount();
    }

    private void showPassword(PasswordDialog.OnPwdCheckListener onPwdCheckListener)
    {
        if (passwordDialog == null)
        {
            passwordDialog = new PasswordDialog();
            passwordDialog.setListener(onPwdCheckListener);
        }
        passwordDialog.show(getSupportFragmentManager(), "Password");
    }

    private void querySmallAmount()
    {
        FreePayLogic payHandle = new FreePayLogic(PaySettingActivity.this);

        payHandle.validate(UserManager.getInstance().getUserBean().getAccount());

        // 操作结果
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                String freePay = resultMap.get("freePay");
                String freeAmount = resultMap.get("freeAmount");
                setSmallAmountState("1".equals(freePay) ? SlideButton.ToggleState.open :
                        SlideButton.ToggleState.close);
                tvAmount.setText("0.0".equals(freeAmount) ? "" : freeAmount);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
            }
        });
    }

    private void setSmallAmount()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("freePay", sbNoPass.getState() == SlideButton.ToggleState.open ? "1" : "0");
        params.put("freeAmount", tvAmount.getText().toString());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);

        // 初始化
        FreePayLogic payHandle = new FreePayLogic(PaySettingActivity.this);
        payHandle.set(params);
        // 操作结果
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                UiUtils.shortToast(ERROR_MSG);
            }
        });
    }

    private void setSmallAmountState(SlideButton.ToggleState state)
    {
        sbNoPass.setToggleState(state);
        if (state == SlideButton.ToggleState.close)
        {
            llAmount.setVisibility(View.GONE);
        } else
        {
            llAmount.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 可拖动的ListView的初始化；
     */
    private void dragSortListViewInit()
    {
        listview = (DragSortListView) findViewById(R.id.listview);
        listview.setDropListener(onDrop);
        // 拖动控制器；
        DragSortController mController = buildController(listview);
        listview.setFloatViewManager(mController);
        listview.setOnTouchListener(mController);
        listview.setFocusable(false);

        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item_pay_method, R.id.tv_pay_method_name, dataList);
        listview.setAdapter(adapter);
    }

    /**
     * 排序结束的监听；
     */
    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener()
    {
        @Override
        public void drop(int from, int to)
        {
            String item = adapter.getItem(from);

            adapter.notifyDataSetChanged();
            adapter.remove(item);
            adapter.insert(item, to);
            if (payMethods != null && payMethods.size() > 0)
            {
                // 交换数据源的位置；
                Collections.swap(payMethods, from, to);
                updatePayOrder();
            }
        }
    };

    /**
     * 改变支付顺序；
     */
    private void updatePayOrder()
    {
        if (userBean == null || payMethods == null || payMethods.size() == 0)
        {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        params.put("mobile", userBean.getAccount());
        params.put("data", JSON.toJSONString(getRightBean(payMethods)));

        updatePayOrderPresenter.loadData(params);
    }

    private List<RightBean> getRightBean(List<PayMethod> payMethods)
    {
        List<RightBean> rightBeen = new ArrayList<>();
        for (int i = 0; i < payMethods.size(); i++)
        {
            rightBeen.add(new RightBean(payMethods.get(i)));
        }
        return rightBeen;
    }


    /**
     * 更改是否自动支付；
     * isAuto 0：自动，1：手动；
     */
    public void updateAutoPayOrder(int isAuto)
    {
        if (userBean == null)
        {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("paymentStatus", isAuto + "");
        userInfoPresenter.update(params);
    }

    @Override
    public void onGetPayMethodListSucess(String flag, List<PayMethod> data)
    {
        if (data != null)
        {
            payMethods = data;
            refreshAdapter();
        }
    }

    @Override
    public void onGetPayMethodListFail(String failMsg)
    {
        // toastCustom(failMsg);
    }

    /**
     * 获取支付列表数据；
     */
    private void getPayMethodList()
    {
        if (userBean != null)
        {
            //0：自动；1：手动
            Map<String, String> params = new HashMap<>();
            params.put("mobile", userBean.getAccount());
            params.put("merchantNo", WalletConstant.MERCHANT_NO);
            params.put("sign", AppConstant.sign);
            params.put("paymethodStatus", userBean.getPaymentStatus());//用来标志是有序还是无序；0:默认；1：有序；
            LogUtil.e("payMethodListS参数："+JSON.toJSONString(params));
            payMethodsPresenter.loadData(params);
        }
    }

    /**
     * 刷新扣款顺序数据；
     */
    private void refreshAdapter()
    {
        if (payMethods != null && payMethods.size() > 0)
        {
            dataList.clear();
            for (PayMethod item : payMethods)
            {
                dataList.add(getName(item));
            }
            adapter.notifyDataSetChanged();
        }
        ViewUtil.setListViewHeight(listview);
    }

    @NonNull
    private String getName(PayMethod item)
    {
        if (TextUtils.isEmpty(item.getSortNo()) && TextUtils.isEmpty(item.getBankName()))
            return item.getTypeName();
        return UiUtils.getString(R.string.card_bank_and_tail, item.getBankName()
                + item.getTypeName(), item.getSortNo());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_notice: // 更多说明；
                Bundle bundle = new Bundle();
                bundle.putString("category", AppConstant.h5_category_paysetting);
                bundle.putString("titleName", getString(R.string.text_more_introduce));
                readyGoWithBundle(this, WebViewClientActivity.class, bundle);
                break;
            case R.id.llSlideButton:
                updateAutoPayOrder(slideButtonAutoHk.getState() == SlideButton.ToggleState.open ? 1 : 0);
                break;
        }
    }

    /**
     * 初始化DragSortController；
     *
     * @param dslv
     * @return
     */
    public DragSortController buildController(DragSortListView dslv)
    {
        DragSortController controller = new DragSortController(dslv);
        controller.setDragHandleId(R.id.drag_handle);
        controller.setRemoveEnabled(false);
        controller.setSortEnabled(true);
        controller.setDragInitMode(DragSortController.ON_DOWN);
        return controller;
    }

    /**
     * 显示或隐藏扣款顺序部分的view；
     * isVisible  true:设置可见；false：设置隐藏；
     */
    public void showOrHideDeductionView(boolean isVisible)
    {
        llAuto.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onViewCallBackSucess(Object data)
    {

    }

    @Override
    public void onViewCallBackFail(String failMsg)
    {

    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        //  true:打开；false：关闭；
        slideButtonAutoHk.setToggleState(slideButtonAutoHk.getState() == SlideButton.ToggleState.open
                ? SlideButton.ToggleState.close : SlideButton.ToggleState.open);
        int state = slideButtonAutoHk.getState() == SlideButton.ToggleState.open ? 0 : 1;
        userBean.setPaymentStatus(state + "");
        showOrHideDeductionView(state == 1);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        UiUtils.shortToast("更新失败");
    }
}
