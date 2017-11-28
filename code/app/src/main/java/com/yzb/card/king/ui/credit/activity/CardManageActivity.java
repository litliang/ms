package com.yzb.card.king.ui.credit.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.TimeSelectDialog;
import com.yzb.card.king.ui.appwidget.appdialog.PayMentDialog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.interfaces.OnSelectedListener;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class CardManageActivity extends BaseActivity implements View.OnClickListener {

    private static SlideButton slidButton, slidButton2;

    private LinearLayout  dateInfo;

    private  LinearLayout rlHuankuan,rlZhangdan;
    private Button unbundling;

    private TextView date_set, txZhangdan;

    private LinearLayout panel_back;

    private PopupWindow popupWindow;

    private ImageView img_logo;

    private String dayInfo = "";
    private static CreditCard data;
    private LinearLayout slbtn1, slbtn2;

    private TextView bankNmame, peopleName, lastNum;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        receiveIntent();
        initView();
        initListener();
        initData();
    }

    private void receiveIntent()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            data = (CreditCard) intent.getSerializableExtra("data");
        }
    }

    private void initView()
    {
        setContentView(R.layout.activity_card_manage);

        bankNmame = (TextView) findViewById(R.id.bankname);
        peopleName = (TextView) findViewById(R.id.peoplename);
        lastNum = (TextView) findViewById(R.id.lastNum);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        slidButton = (SlideButton) findViewById(R.id.slidButton);
        slidButton2 = (SlideButton) findViewById(R.id.slidButton2);
        rlHuankuan = (LinearLayout) findViewById(R.id.rl_huankuan);
        rlZhangdan = (LinearLayout) findViewById(R.id.rl_zhangdan);
        txZhangdan = (TextView) findViewById(R.id.txZhangdan);
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        slbtn1 = (LinearLayout) findViewById(R.id.slbtn1);
        slbtn2 = (LinearLayout) findViewById(R.id.slbtn2);
        dateInfo = (LinearLayout) findViewById(R.id.date_info);
        date_set = (TextView) findViewById(R.id.date_set);
        unbundling = (Button) findViewById(R.id.unbundling);
        slidButton.setEnable(false);
        slidButton2.setEnable(false);
    }


    private void initListener()
    {
        dateInfo.setOnClickListener(this);
        rlZhangdan.setOnClickListener(this);
        rlHuankuan.setOnClickListener(this);
        panel_back.setOnClickListener(this);
        unbundling.setOnClickListener(this);
        slbtn1.setOnClickListener(this);
        slbtn2.setOnClickListener(this);
    }


    private void initData()
    {
        if (data != null)
        {
            if (!TextUtils.isEmpty(data.getLogo()))
            {
                x.image().bind(img_logo, ServiceDispatcher
                        .getImageUrl(data.getLogo()));
            }
            bankNmame.setText(data.getBankName());
            peopleName.setText(data.getUserName());
            lastNum.setText("尾号" + data.getSortNo());
           // unbundling.setText(data.isPayType()?"解绑":"删除");

            unbundling.setText("解绑");
            if (data.getRemindDay() == null)
            {
                date_set.setText("每月1日");
            } else
            {
                date_set.setText("每月" + data.getRemindDay() + "日");
            }
            if (data.isAutoStatus())
            {
                slidButton2.setToggleState(SlideButton.ToggleState.open);
            } else
            {
                slidButton2.setToggleState(SlideButton.ToggleState.close);
            }
            if (data.isRemindStatus())
            {
                slidButton.setToggleState(SlideButton.ToggleState.open);
                dateInfo.setVisibility(View.VISIBLE);
            } else
            {
                slidButton.setToggleState(SlideButton.ToggleState.close);
                dateInfo.setVisibility(View.GONE);
            }
            txZhangdan.setText("每月" + data.getBillDay() + "日");

        }

    }


//    public static void setSlidBtn()
//    {
//        slidButton2.setToggleState(SlideButton.ToggleState.close);
//    }

    public static void setData(CreditCard info)
    {
        data = info;
    }


    private void updateCredit(final String remind, final String updateZd, final String isRemind, final String isZdhk)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("creditId", data.getId());
        params.put("remindDay", remind);
        params.put("remindStatus", isRemind);
        params.put("billDay", updateZd);
        params.put("autoStatus", isZdhk);
        new SimpleRequest(CardConstant.CREDIT_UPDATE, params).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                if (!updateZd.equals(""))
                {
                    View paymentLayout = getLayoutInflater().inflate(R.layout.zhangdan_success, null);
                    popupWindow = new PopupWindow(paymentLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0));
                    popupWindow.setAnimationStyle(R.style.discount_popupwindow_animation);
                    popupWindow.showAtLocation(popupWindow.getContentView(), Gravity.CENTER, 0, 0);
                    handler.sendEmptyMessageDelayed(0, 2 * 1000);
                    data.setBillDay(updateZd);
                } else if (!remind.equals(""))
                {
                    date_set.setText("每月" + remind + "日");
                    data.setRemindDay(remind);
                } else if (!isRemind.equals(""))
                {
                    if ("0".equals(isRemind))
                    {
                        data.setRemindStatus(false);
                    } else
                    {
                        data.setRemindStatus(true);
                    }

                } else if (!isZdhk.equals(""))
                {
                    if ("0".equals(isZdhk))
                    {
                        data.setAutoStatus(false);
                    } else
                    {
                        data.setAutoStatus(true);
                    }
                }
            }

            @Override
            public void onFailed(Object o)
            {

            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            popupWindow.dismiss();
            txZhangdan.setText("每月" + dayInfo + "日");
        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        if (data.isAutoStatus())
        {
            slidButton2.setToggleState(SlideButton.ToggleState.open);
        } else
        {
            slidButton2.setToggleState(SlideButton.ToggleState.close);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.panel_back:
                finish();
                break;
            case R.id.date_info:
                showTimeSelectDialog(1);
                break;
            case R.id.rl_zhangdan://账单日
                showTimeSelectDialog(2);
                break;
            case R.id.rl_huankuan:
                Intent intent = new Intent(this, RepaymentHistoryActivity.class);
                intent.putExtra("creditId", data.getId());
                startActivity(intent);
                break;
            case R.id.unbundling:  //解绑
                final PayMentDialog.Builder builder = new PayMentDialog.Builder(CardManageActivity.this);
                builder.setTitle("您是否确定解绑？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        unbundling();
                        builder.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.slbtn1:  //还款提醒
                if (slidButton.getState() == SlideButton.ToggleState.open) //打开还款提醒
                {
                    slidButton.setToggleState(SlideButton.ToggleState.close);
                    updateCredit("", "", "0", "");
                    dateInfo.setVisibility(View.GONE);
                } else
                {
                    slidButton.setToggleState(SlideButton.ToggleState.open);
                    updateCredit("", "", "1", "");
                    dateInfo.setVisibility(View.VISIBLE);

                }
                RepaymentActivity.setData(data);
                break;
            case R.id.slbtn2: //自动还款
                if (slidButton2.getState() == SlideButton.ToggleState.open) //打开还款提醒
                {
                    slidButton2.setToggleState(SlideButton.ToggleState.close);
                    updateCredit("", "", "", "0");
                } else
                {
                    slidButton2.setToggleState(SlideButton.ToggleState.open);
                    Intent i = new Intent(CardManageActivity.this, AutomaticPaymentActivity.class);
                    i.putExtra("data", data);
                    startActivity(i);
                }
                RepaymentActivity.setData(data);
                break;
        }
    }

    /**
     * 解绑
     */
    private void unbundling()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("creditId", data.getId());
        new SimpleRequest(CardConstant.CREDIT_UNBUND, param).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                Intent intentTemp = new Intent();
                setResult(Activity.RESULT_OK, intentTemp);
                finish();

            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {
                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    if (onSuccessData.get(HttpConstant.SERVER_CODE).equals("0101"))
                    {
                        ToastUtil.i(GlobalApp.getInstance().getContext(),
                                "解绑失败");
                    }

                }
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    /**
     * ToggleButton统一设置监听器；
     */
    private void setSlideButtonListener(SlideButton slideButton)
    {
        LogUtil.i("qqqq " + slideButton.getState());
        slideButton.setToggleViewUpdateListener(new SlideButton.OnToggleViewUpdateListener() {
            @Override
            public void onToggleStateChange(SlideButton view, SlideButton.ToggleState state)
            {
                switch (view.getId())
                {
                    case R.id.slidButton: //还款提醒；
                        if (slidButton.getState() == SlideButton.ToggleState.open) //打开还款提醒
                        {
                            updateCredit("", "", "1", "");
                            dateInfo.setVisibility(View.VISIBLE);
                        } else
                        {
                            updateCredit("", "", "0", "");
                            dateInfo.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.slidButton2://自动还款；
                        if (slidButton2.getState() == SlideButton.ToggleState.open) //打开还款提醒
                        {
                            Intent intent = new Intent(CardManageActivity.this, AutomaticPaymentActivity.class);
                            intent.putExtra("data", data);
                            startActivity(intent);
                        } else
                        {
                            updateCredit("", "", "", "0");
                        }
                        break;
                }
            }
        });
    }

    /**
     *
     */
    private void showTimeSelectDialog(final int code)
    {
        TimeSelectDialog dayDailog = new TimeSelectDialog();
        dayDailog.setListener(new OnSelectedListener() {
            @Override
            public void onSelected(String year, String day)
            {
                if (code == 1)
                {
                    updateCredit(day, "", "", "");
                } else
                {
                    updateCredit("", day, "", "");
                    dayInfo = day;
                }
                RepaymentActivity.setData(data);
            }
        });
        dayDailog.show(getSupportFragmentManager(), "StatementDialog");
    }


}
