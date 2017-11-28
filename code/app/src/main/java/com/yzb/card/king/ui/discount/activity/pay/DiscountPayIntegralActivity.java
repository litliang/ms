package com.yzb.card.king.ui.discount.activity.pay;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.discount.bean.DiscountIntegralBean;
import com.yzb.card.king.ui.discount.bean.DiscountIntegralDetailBean;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.ticket.presenter.BankIntegralPresenter;
import com.yzb.card.king.ui.ticket.view.BankIntegralView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.Utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特惠付款-->使用积分
 *
 * @author xingyan；
 *         第一次修改  gengqiyun  2016.8.19；
 *         <p/>
 *         accountType=4:平台积分;accountType=5:商家积分;
 */
public class DiscountPayIntegralActivity extends BaseActivity implements View.OnClickListener, BankIntegralView
{
    public static final String INTEGRA_LDETAI_LIST = "INTEGRA_LDETAI_LIST";
    public static final String USER_INTEGRAL_REUSLT = "USER_INTEGRAL_REUSLT";
    private ScrollView scrollView;

    private EditText etExchangeMoney; //积分兑换金额；
    private EditText etExchangeIntegral;//使用积分数量；

    private GridLayout integralGridLayout; //银行积分列表；
    private List<DiscountIntegralBean> mIntegralList; //银行积分列表；

    private GridLayout integralDetailGridLayout; //积分明细；
    private ArrayList<DiscountIntegralDetailBean> mIntegralDetailList; //用户已经兑换的积分明细列表；来自于上个页面；

    private TextView tvTotalAmount; //积分总额；

    private int mIntegralRate = 0;  // 选中的积分兑换比例；
    private LayoutInflater inflater;
    private String oldMoneyInputText = "";
    private BankIntegralPresenter integralPresenter; //银行积分列表；
    private String shopIds; //商家id；

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_pay_integral);
        integralPresenter = new BankIntegralPresenter(this);
        initView();
        recvIntentData();
    }

    private void recvIntentData()
    {
        Serializable obj = getIntent().getSerializableExtra(INTEGRA_LDETAI_LIST);
        if (obj instanceof List)
        {
            mIntegralDetailList = (ArrayList<DiscountIntegralDetailBean>) obj;
            refreshIntegralDetailGridData();
        }
        shopIds = getIntent().getStringExtra("shopIds");
    }

    private void initView()
    {
        setHeader(R.mipmap.icon_back_white, getString(R.string.discount_payment_use_jf), R.mipmap.icon_add_giftcard);
        findViewById(R.id.headerLeft).setOnClickListener(this);
        // 添加信用卡
        findViewById(R.id.headerRight).setOnClickListener(this);

        mIntegralList = new ArrayList<>();
        mIntegralDetailList = new ArrayList<>();

        inflater = getLayoutInflater();

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 20);
        // 兑换金额
        etExchangeMoney = (EditText) findViewById(R.id.etExchangeMoney);
        // 输入积分
        etExchangeIntegral = (EditText) findViewById(R.id.etExchangeIntegral);

        etExchangeMoney.setOnFocusChangeListener(etExchangeMoneyFocusListener);
        etExchangeIntegral.setOnFocusChangeListener(etExchangeIntegralFocusListener);

        etExchangeMoney.requestFocus();
        integralGridLayout = (GridLayout) findViewById(R.id.integralGridLayout);
        integralDetailGridLayout = (GridLayout) findViewById(R.id.integralDetailGridLayout);

        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        updateTotalAmount(0);
        findViewById(R.id.tvUseIntegral).setOnClickListener(this);
        findViewById(R.id.rlInstruction).setOnClickListener(this);
        findViewById(R.id.tv_ok).setOnClickListener(this);
    }

    /**
     * 更新合计总金额；
     *
     * @param amount
     */
    private void updateTotalAmount(float amount)
    {
        SpannableString ss = new SpannableString("合计" + Utils.subZeroAndDot(amount + "") + "元");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#bf9543")), 2, ss.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTotalAmount.setText(ss);
    }

    @Override
    public void onGetBankIntegralSucess(List<DiscountIntegralBean> discountIntegralBeans)
    {
        closePDialog();
        this.mIntegralList = discountIntegralBeans;
        initIntegralGridData();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getIntegralList();
    }

    @Override
    public void onGetBankIntegralFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 查询积分列表
     */
    public void getIntegralList()
    {
        showPDialog(R.string.loading);
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shopIds);
        integralPresenter.loadData(params);
    }

    //金额的监听；
    private MyTextWatcher exChangeMoneyWatcher = new MyTextWatcher()
    {
        @Override
        public void afterTextChange(String s)
        {
            String amount = s.trim();
            if (s.equals(oldMoneyInputText))
            {
                return;
            }
            //超过小数点后2位；
            if (RegexUtil.verifyJeFormat(amount))
            {
                amount = amount.substring(0, amount.indexOf(".") + 3);
                etExchangeMoney.setText(amount);
                etExchangeMoney.setSelection(etExchangeMoney.getText().toString().trim().length());
            }
            //计算积分；
            calcIntegral(amount);
        }
    };

    private String oldIntegralInputText = ""; //积分输入框中上次的内容；用于防止setText方法出现卡死的现象；
    //积分变动的监听；
    private MyTextWatcher exIntegralWatcher = new MyTextWatcher()
    {

        @Override
        public void afterTextChange(String s)
        {
            //内容有变化；
            if (!s.equals(oldIntegralInputText))
            {
                //计算金额；
                calcMoney(s);
                etExchangeIntegral.setSelection(etExchangeIntegral.getText().toString().trim().length());
            }
        }
    };

    /**
     * 计算积分
     *
     * @param money 格式化的金额；
     */
    private void calcIntegral(String money)
    {
        if (TextUtils.isEmpty(money))
        {
            oldIntegralInputText = "";
            etExchangeIntegral.setText("");
            return;
        }
        if (mIntegralRate <= 0)
        {
            etExchangeMoney.setText("");
            toastCustom("请选择账户类型");
            return;
        }
        //计算积分
        BigDecimal moneyDecimal = new BigDecimal(money);
        //乘法；
        BigDecimal integralDecimal = moneyDecimal.multiply(new BigDecimal(mIntegralRate)).setScale(0, BigDecimal.ROUND_HALF_EVEN);

        //初始化oldIntegralInputText，防止出现卡死现象；
        oldIntegralInputText = integralDecimal.intValue() + "";
        etExchangeIntegral.setText(oldIntegralInputText);
    }

    /**
     * 计算金额
     *
     * @param integral 积分；
     */
    private void calcMoney(String integral)
    {
        if (TextUtils.isEmpty(integral))
        {
            etExchangeMoney.setText("");
            return;
        }
        if (mIntegralRate <= 0)
        {
            etExchangeIntegral.setText("");
            toastCustom("请选择账户类型");
            return;
        }
        LogUtil.i("mIntegralRate==" + mIntegralRate);
        LogUtil.i("integral==" + integral);

        // 金额计算
        BigDecimal integralDecimal = new BigDecimal(integral);
        BigDecimal moneyDecimal = integralDecimal.divide(new BigDecimal(mIntegralRate)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        oldMoneyInputText = moneyDecimal.floatValue() + "";
        etExchangeMoney.setText(oldMoneyInputText);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvUseIntegral: //使用积分；
                String inputIntegral = etExchangeIntegral.getText().toString().trim();//积分输入框；
                String inputMoney = etExchangeMoney.getText().toString().trim();//金额输入框；

                if (TextUtils.isEmpty(inputIntegral) && TextUtils.isEmpty(inputMoney))
                {
                    toastCustom("请输入积分或金额");
                    return;
                }
                if (Float.parseFloat(inputIntegral) <= 0f)
                {
                    toastCustom("使用积分不能为0");
                    return;
                }
                //选中的账户类型；
                DiscountIntegralBean selectData = getSelectedIntegralBean();
                if (selectData == null)
                {
                    toastCustom("请选择账户类型");
                    return;
                }
                float totalIntegralAmount = selectData.getIntegralBalance(); //总积分余额；
                float inputMoneyAmount = Float.parseFloat(inputMoney);//输入的金额；
                float inputIntegralAmount = Float.parseFloat(inputIntegral);//输入的积分；


                DiscountIntegralDetailBean detailBean;
                //平台积分，商家积分，特定银行积分；
                if (AppConstant.INTEGRAL_ACCOUNT_PLATFORM.equals(selectData.getAccountType()) ||
                        AppConstant.INTEGRAL_ACCOUNT_SHOP.equals(selectData.getAccountType()))
                {
                    DiscountIntegralDetailBean detailBeanEmp = getIntegralByAccountType(selectData.getAccountType());
                    detailBean = detailBeanEmp != null ? detailBeanEmp : null;
                } else
                {
                    DiscountIntegralDetailBean detailBeanEmp = getIntegralDataBeanById(selectData.getBankId());
                    detailBean = detailBeanEmp != null ? detailBeanEmp : null;
                }

                //存在明细记录；
                if (detailBean != null)
                {
                    float detailIntegralAmount = detailBean.getExchangeIntegralNum();//明细中已兑换的积分数量；
                    float detailMoneyAmount = detailBean.getExchangeMoneyNum();//明细中已兑换的金额；
                    if (detailIntegralAmount + inputIntegralAmount > totalIntegralAmount)
                    {
                        toastCustom("积分余额不足");
                        return;
                    } else
                    {
                        detailBean.setExchangeIntegralNum(detailIntegralAmount + inputIntegralAmount);
                        detailBean.setExchangeMoneyNum(detailMoneyAmount + inputMoneyAmount);
                    }
                } else
                { //不存在明细记录；
                    if (inputIntegralAmount > totalIntegralAmount)
                    {
                        toastCustom("积分余额不足");
                        return;
                    }
                    DiscountIntegralDetailBean detailBeanEmp2 = new DiscountIntegralDetailBean();
                    detailBeanEmp2.setBankId(selectData.getBankId());
                    detailBeanEmp2.setExchangeIntegralNum(inputIntegralAmount);
                    detailBeanEmp2.setExchangeMoneyNum(inputMoneyAmount);
                    detailBeanEmp2.setBankName(selectData.getBankName());
                    detailBeanEmp2.setAccountType(selectData.getAccountType());
                    mIntegralDetailList.add(detailBeanEmp2);
                }
                refreshIntegralDetailGridData();
                break;
            case R.id.tv_ok: //确认；
                Intent intent = new Intent();
                intent.putExtra(USER_INTEGRAL_REUSLT, mIntegralDetailList);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case R.id.headerRight:// 添加卡
                readyGo(this, AddCanPayCardActivity.class);
                break;
            case R.id.rlInstruction:// 帮助
                Bundle bundle1 = new Bundle();
                bundle1.putString("titleName", "帮助");
                bundle1.putString("category", AppConstant.h5_category_integral_help);
                readyGoWithBundle(this, WebViewClientActivity.class, bundle1);
                break;
        }
    }

    /**
     * 获取选中的积分bean;
     */
    private DiscountIntegralBean getSelectedIntegralBean()
    {
        if (mIntegralList != null)
        {
            for (int i = 0; i < mIntegralList.size(); i++)
            {
                if (mIntegralList.get(i).isSelected())
                {
                    return mIntegralList.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 获取明细列表中已经兑换过的特定id的bean;
     *
     * @param bankId 银行卡id；
     */
    private DiscountIntegralDetailBean getIntegralDataBeanById(int bankId)
    {
        if (mIntegralDetailList == null)
        {
            return null;
        }

        DiscountIntegralDetailBean integralDetailBean;
        for (int i = 0; i < mIntegralDetailList.size(); i++)
        {
            integralDetailBean = mIntegralDetailList.get(i);
            //平台积分，商家积分，特定银行积分；
            if (integralDetailBean.getBankId() == bankId)
            {
                return integralDetailBean;
            }
        }
        return null;
    }

    /**
     * 获取明细列表中已经兑换过的特定accountType的bean;
     *
     * @param accountType 账户类型；
     */
    private DiscountIntegralDetailBean getIntegralByAccountType(String accountType)
    {
        if (mIntegralDetailList == null || TextUtils.isEmpty(accountType))
        {
            return null;
        }

        DiscountIntegralDetailBean integralDetailBean;
        for (int i = 0; i < mIntegralDetailList.size(); i++)
        {
            integralDetailBean = mIntegralDetailList.get(i);
            //平台积分，商家积分，特定银行积分；
            if (accountType.equals(integralDetailBean.getAccountType()))
            {
                return integralDetailBean;
            }
        }
        return null;
    }

    public void initIntegralGridData()
    {
        if (mIntegralList == null || mIntegralList.size() == 0)
        {
            return;
        }
        integralGridLayout.removeAllViews();
        View gvItemView;
        for (int i = 0; i < mIntegralList.size(); i++)
        {
            gvItemView = inflater.inflate(R.layout.item_gl_integral_new, null);
            initIntegralItemViewData(gvItemView, mIntegralList.get(i), i);
            integralGridLayout.addView(gvItemView);
        }
    }

    /**
     * 初始化积分列表view的数据；
     *
     * @param gvItemView
     * @param integralBean
     * @param position
     */
    private void initIntegralItemViewData(View gvItemView, final DiscountIntegralBean integralBean, final int position)
    {
        if (gvItemView == null || integralBean == null)
        {
            return;
        }
        final ImageView ivBankLogo = (ImageView) gvItemView.findViewById(R.id.ivBankLogo);
        CommonUtil.downloadImageForView(ServiceDispatcher.getImageUrl(integralBean.getBankLogo()), ivBankLogo, 23);

        TextView tvBankName = (TextView) gvItemView.findViewById(R.id.tvBankName);
        tvBankName.setText(integralBean.getBankName());
        TextView tvIntegralBalance = (TextView) gvItemView.findViewById(R.id.tvIntegralBalance);

        tvIntegralBalance.setText(Utils.subZeroAndDot(integralBean.getIntegralBalance() + ""));

        TextView tvIntegralRate = (TextView) gvItemView.findViewById(R.id.tvIntegralRate);
        tvIntegralRate.setText(Utils.subZeroAndDot(integralBean.getIntegralRate() + ""));
        //积分item单击；
        gvItemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntegralRate = mIntegralList.get(position).getIntegralRate();
                refreshIntegralGrid(position);
                etExchangeMoney.setText("");
                etExchangeIntegral.setText("");
            }
        });
    }

    /**
     * 刷新积分列表View的选中状态；
     *
     * @param position 选中的下标；
     */
    public void refreshIntegralGrid(int position)
    {
        DiscountIntegralBean integralBean;
        View toggleView;
        for (int i = 0; i < mIntegralList.size(); i++)
        {
            integralBean = mIntegralList.get(i);
            toggleView = integralGridLayout.getChildAt(i).findViewById(R.id.iv_toggle);
            integralBean.setIsSelected(i == position ? true : false);
            toggleView.setSelected(i == position ? true : false);
        }
    }

    /**
     * 刷新积分明细中view数据；
     */
    public void refreshIntegralDetailGridData()
    {
        if (mIntegralDetailList == null || mIntegralDetailList.size() == 0)
        {
            return;
        }
        integralDetailGridLayout.removeAllViews();

        View view;
        TextView tvBankName;
        TextView tvBankAmount;
        DiscountIntegralDetailBean detailBean;
        float totalMoneyAmount = 0.0f; //明细总金额；

        for (int i = 0; i < mIntegralDetailList.size(); i++)
        {
            detailBean = mIntegralDetailList.get(i);
            totalMoneyAmount += detailBean.getExchangeMoneyNum();

            view = inflater.inflate(R.layout.item_list_discount_pay_integral_detail, null);
            tvBankName = (TextView) view.findViewById(R.id.tvBankName);
            tvBankName.setText(detailBean.getBankName());

            tvBankAmount = (TextView) view.findViewById(R.id.tvBankAmount);
            tvBankAmount.setText(Utils.subZeroAndDot(detailBean.getExchangeMoneyNum() + "") + "元");

            final DiscountIntegralDetailBean finalDetailBean = detailBean;
            final View finalView = view;
            view.findViewById(R.id.ivCutNumber).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //移除数据和view；
                    integralDetailGridLayout.removeView(finalView);
                    mIntegralDetailList.remove(finalDetailBean);
                    updateTotalAmount(calculateTotalDetailMoney());
                }
            });
            integralDetailGridLayout.addView(view);
        }
        updateTotalAmount(totalMoneyAmount);
    }

    /**
     * 计算明细总金额；
     */
    private float calculateTotalDetailMoney()
    {
        if (mIntegralDetailList == null || mIntegralDetailList.size() == 0)
        {
            return 0.0f;
        }
        float totalMoneyAmount = 0.0f;

        //计算总金额；
        for (int i = 0; i < mIntegralDetailList.size(); i++)
        {
            totalMoneyAmount += mIntegralDetailList.get(i).getExchangeMoneyNum();
        }
        return totalMoneyAmount;
    }

    private View.OnFocusChangeListener etExchangeMoneyFocusListener = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (hasFocus)
            {
                etExchangeMoney.addTextChangedListener(exChangeMoneyWatcher);
                etExchangeIntegral.removeTextChangedListener(exIntegralWatcher);
            }
        }
    };
    private View.OnFocusChangeListener etExchangeIntegralFocusListener = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (hasFocus)
            {
                etExchangeIntegral.addTextChangedListener(exIntegralWatcher);
                etExchangeMoney.removeTextChangedListener(exChangeMoneyWatcher);
            }
        }
    };

}
