package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.presenter.ActivatePhysCardPresenter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;

import java.util.HashMap;
import java.util.Map;


/**
 * @author gengqiyun
 * @date 2016.8.23
 * 礼品卡充值对话框；
 */
public class GiftCardDialogFragment extends BaseDialogFragment implements View.OnClickListener, BaseViewLayerInterface
{
    private static GiftCardDialogFragment dialogFragment;
    private EditText etCardNumber; //卡号；
    private EditText etPwd; //密码；
    private View llProgress;
    private IGiftCardPayCallBack cardPayCallBack; //礼品卡添加结果回调；
    private ActivatePhysCardPresenter giftCardPayPresenter;
    private static final int MY_SCAN_REQUEST_CODE = 0x0001;

    public GiftCardDialogFragment setCardPayCallBack(IGiftCardPayCallBack cardPayCallBack)
    {
        this.cardPayCallBack = cardPayCallBack;
        return this;
    }

    public static GiftCardDialogFragment getInstance()
    {
        if (dialogFragment == null)
        {
            dialogFragment = new GiftCardDialogFragment();
        }
        return dialogFragment;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_pay_gift_card;
    }

    protected void initView(View view)
    {
        giftCardPayPresenter = new ActivatePhysCardPresenter(this);

        etCardNumber = (EditText) view.findViewById(R.id.et_card_number);
        view.findViewById(R.id.iv_scan).setOnClickListener(this);
        etPwd = (EditText) view.findViewById(R.id.et_pwd);
        llProgress = view.findViewById(R.id.llProgress);
        llProgress.setVisibility(View.INVISIBLE);
        view.findViewById(R.id.tv_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_scan:
                scanCard();
                break;
            case R.id.tv_ok:
                if (isInputRight())
                {
                    giftCardRecharge();
                }
                break;
        }
    }

    /**
     * 礼品卡扫描；
     */
    private void scanCard()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSaoYiSao", true);
        bundle.putString(AppConstant.PAYTYPE, AppConstant.NONE);
        Intent intent = new Intent(getActivity(), MipcaActivityCapture.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivityForResult(intent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) return;
        if (data != null)
        {
            //礼品卡信息格式： BIU7265391734:NVU3749J
            String scanResult = data.getStringExtra("scanResult");
            if (!isEmpty(scanResult) && scanResult.contains(":"))
            {
                String[] info = scanResult.split(":");
                etCardNumber.setText(info[0]);
                etPwd.setText(info[1]);
            }
        }
    }

    /**
     * 礼品卡充值；
     */
    private void giftCardRecharge()
    {
        showProgress();
        String inputAmount = etCardNumber.getText().toString().trim();
        String inputPwd = etPwd.getText().toString().trim();
        giftCardPayPresenter.setInterfaceParameters(inputAmount,inputPwd);
    }

    private boolean isInputRight()
    {
        String inputAmount = etCardNumber.getText().toString().trim();
        if (isEmpty(inputAmount))
        {
            toastCustom("卡号不能为空");
            return false;
        }
        String inputPwd = etPwd.getText().toString().trim();
        if (isEmpty(inputPwd))
        {
            toastCustom("密码不能为空");
            return false;
        }
        return true;
    }



    public void showProgress()
    {
        llProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgress()
    {
        llProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        hideProgress();
        toastCustom(R.string.discount_payment_pay_suc);
        if (cardPayCallBack != null)
        {
            cardPayCallBack.addAcardSuccess();
        }
        dismiss();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        hideProgress();
        toastCustom(o+"");
    }

    /**
     * 充值回调；
     */
    public interface IGiftCardPayCallBack
    {
        /**
         * 添加卡成功；
         */
        void addAcardSuccess();
    }
}
