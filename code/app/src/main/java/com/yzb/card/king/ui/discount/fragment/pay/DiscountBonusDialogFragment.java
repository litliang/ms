package com.yzb.card.king.ui.discount.fragment.pay;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.discount.bean.AccountBalanceBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.ticket.presenter.AccountBalancePresenter;
import com.yzb.card.king.ui.ticket.view.AccountBalanceView;
import com.yzb.card.king.util.RegexUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 红包金额输入
 *
 * @author gengqiyun
 * @date 2016.8.29
 */
public class DiscountBonusDialogFragment extends BaseDialogFragment implements View.OnClickListener, AccountBalanceView
{
    private static DiscountBonusDialogFragment dialog;
    private double mBonusBalance; //红包账户余额；
    private double mUseAmount; //已选择的金额；

    private TextView tvAllBonusAmount;
    private EditText etBonusAmount;
    private KeyBoardListener keyBoardListener;// 键盘监听
    private View tvSubmit;
    private View tvCancel;
    private double mOrderAmount; //订单总金额；
    private AccountBalancePresenter balancePresenter;
    private AccountBalanceBean accountBeans; //红包账户信息；
    private String shopId;

    /**
     * 已使用金额；
     *
     * @param useAmount
     */
    public DiscountBonusDialogFragment setUseAmount(float useAmount)
    {
        this.mUseAmount = useAmount;
        return this;
    }

    public DiscountBonusDialogFragment setOrderAmount(float mTotalAmount)
    {
        this.mOrderAmount = mTotalAmount;
        return this;
    }

    public DiscountBonusDialogFragment setShopId(String shopId)
    {
        this.shopId = shopId;
        return this;
    }

    /**
     * 键盘监听；
     *
     * @param keyBoardListener
     */
    public DiscountBonusDialogFragment setKeyBoardListener(KeyBoardListener keyBoardListener)
    {
        this.keyBoardListener = keyBoardListener;
        return this;
    }

    public static DiscountBonusDialogFragment getInstance()
    {
        if (dialog == null)
        {
            dialog = new DiscountBonusDialogFragment();
        }
        return dialog;
    }

    /**
     * 获取红包余额；
     */
    private void getData()
    {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("accountType", AppConstant.ACCOUNT_TYPE_BONUS);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", AppConstant.sign);
        balancePresenter.loadData(params);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvSubmit: //确定；
                if (isInputRight() && keyBoardListener != null)
                {
                    String useAmount = etBonusAmount.getText().toString().trim();
                    //使用金额；
                    double doubleUseAmount = Double.parseDouble(useAmount);

                    double shopBonusBalance = accountBeans.getShopBonusBalance();
                    double personBonusBalance = accountBeans.getPersonBonusBalance();
                    double platformBonusBalance = accountBeans.getPlatformBonusBalance();

                    double restAmount;

                    double shopBouns = doubleUseAmount >= shopBonusBalance ? shopBonusBalance : doubleUseAmount;
                    double pesonBouns = 0;
                    double platformBouns = 0;

                    restAmount = doubleUseAmount - shopBouns;
                    if (restAmount > 0)
                    {
                        pesonBouns = restAmount >= personBonusBalance ? personBonusBalance : restAmount;
                    }

                    restAmount = doubleUseAmount - shopBouns - pesonBouns;
                    //可以继续抵扣平台红包；
                    if (restAmount > 0)
                    {
                        platformBouns = restAmount >= platformBonusBalance ? platformBonusBalance : restAmount;
                    }

                    keyBoardListener.getBonusAmount(shopBouns, pesonBouns, platformBouns);
                    dismiss();
                }
                break;
            case R.id.tvCancel: //取消；
                dismiss();
                break;
        }
    }

    private boolean isInputRight()
    {
        String amount = etBonusAmount.getText().toString().trim();
        if (isEmpty(amount))
        {
            toastCustom("请输入红包金额");
            return false;
        }

        double amountFloat = Double.parseDouble(amount);
        if (amountFloat < 0)
        {
            toastCustom("红包金额不能小于0");
            return false;
        }
        if (amountFloat > mOrderAmount)
        {
            toastCustom("红包金额不能大于订单金额");
            return false;
        }
        return true;
    }

    private List<View> keybordViews = new ArrayList<>();
    private int[] keyIds = {R.id.key00, R.id.key01, R.id.key02, R.id.key03,
            R.id.key04, R.id.key05, R.id.key06, R.id.key07,
            R.id.key08, R.id.key09, R.id.keydel, R.id.keypoint};
    private String[] keyTags = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "", "."};


    /**
     * 键盘view初始化；
     *
     * @param view
     */
    private void keyBoardInit(View view)
    {
        View keyView;
        for (int i = 0; i < keyIds.length; i++)
        {
            keyView = view.findViewById(keyIds[i]);
            keyView.setOnClickListener(keyboardClickListener);
            keyView.setTag(keyTags[i]);
            keybordViews.add(keyView);
        }
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_pay_bonus_input;
    }

    @Override
    protected void initView(View view)
    {
        balancePresenter = new AccountBalancePresenter(getActivity(), this);

        tvAllBonusAmount = (TextView) view.findViewById(R.id.tvAllBonusAmount);
        etBonusAmount = (EditText) view.findViewById(R.id.etBonusAmount);
        etBonusAmount.addTextChangedListener(bonusAmountInputListener);
        tvSubmit = view.findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        keyBoardInit(view);
        getData();
    }

    /**
     * 金额变化监听；
     */
    private MyTextWatcher bonusAmountInputListener = new MyTextWatcher()
    {
        @Override
        public void afterTextChange(String text)
        {
            String newText = text;

            //判断合法；
            if (!isEmpty(newText) && !RegexUtil.verifyJeFormat2(newText))
            {
                etBonusAmount.setText(newText.substring(0, newText.length() - 1));
            }
            newText = etBonusAmount.getText().toString().trim();
            //当超过用户红包总额时，自动变显示为用户红包总额
            if (!isEmpty(newText) && Float.parseFloat(newText) > mBonusBalance)
            {
                etBonusAmount.setText(mBonusBalance + "");
            }

            //此处可输入金额不可超过用户订单总额，当超过用户订单总额时，自动变显示为用户订单总额  -----------------------
            if (newText.length() > 0)
            {
                tvSubmit.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.GONE);
            } else
            {
                tvSubmit.setVisibility(View.GONE);
                tvCancel.setVisibility(View.VISIBLE);
            }
            etBonusAmount.setSelection(etBonusAmount.getText().length());
        }
    };

    private View.OnClickListener keyboardClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            String oldInputContent = etBonusAmount.getText().toString().trim();
            switch (v.getId())
            {
                case R.id.keydel://删除最新一个
                    if (oldInputContent.length() > 0)
                    {
                        etBonusAmount.setText(oldInputContent.substring(0, oldInputContent.length() - 1));
                    }
                    break;
                default://输入
                    String newInput = oldInputContent + String.valueOf(v.getTag());
                    etBonusAmount.setText(newInput);
                    break;
            }
        }
    };

    @Override
    public void onGetAccountBalanceSucess(AccountBalanceBean balanceBean)
    {
        this.accountBeans = balanceBean;



        if (balanceBean != null)
        {
            mBonusBalance = accountBeans.getPersonBonusBalance() + accountBeans.getPlatformBonusBalance() +
                    accountBeans.getShopBonusBalance();

            NumberFormat bf = NumberFormat.getInstance();
            bf.setGroupingUsed(false);
            tvAllBonusAmount.setText(bf.format(mBonusBalance));
        }
    }

    @Override
    public void onGetAccountBalanceFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    /**
     * 键盘监听器
     */
    public interface KeyBoardListener
    {
        /**
         * shopBalance 平台红包余额；
         * personBalance 个人红包余额；
         * platformBalance 商家红包余额；
         */
        void getBonusAmount(double shopBalance, double personBalance, double platformBalance);
    }
}