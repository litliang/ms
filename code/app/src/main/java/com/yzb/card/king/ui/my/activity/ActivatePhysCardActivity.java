package com.yzb.card.king.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.presenter.ActivatePhysCardPresenter;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;


/**
 * 功能：礼品卡--激活实体卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/15
 */
@ContentView(R.layout.activity_activate_physcard)
public class ActivatePhysCardActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface
{
    @ViewInject(R.id.etGiftCardNo)
    private EditText etGiftCardNo;
    @ViewInject(R.id.etGiftCardPwd)
    private EditText etGiftCardPwd;
    private ActivatePhysCardPresenter activeCardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        activeCardPresenter = new ActivatePhysCardPresenter(this);
        initView();
    }

    private void initView()
    {
        setTitleNmae("激活实体卡");

        findViewById(R.id.panelHowActive).setOnClickListener(this);
        findViewById(R.id.tvNowActive).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
//            case R.id.headerLeft: //左侧点击
//                finish();
//                break;
//            case R.id.headerRight: //扫描卡点击
//                startScan();
//                break;
            case R.id.panelHowActive: //如何激活
                Bundle bundle = new Bundle();
                bundle.putString(WebViewClientActivity.CATEGORY, AppConstant.h5_giftcard_active);
                bundle.putString(WebViewClientActivity.TITLE_NAME, "如何激活");
                readyGoWithBundle(this, WebViewClientActivity.class, bundle);
                break;
            case R.id.tvNowActive: //立即激活；
                if (isInputRight())
                {
                    exeActive();
                }
                break;
        }
    }

    private void startScan()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSaoYiSao", true);
        bundle.putString(AppConstant.PAYTYPE, AppConstant.NONE);
        Intent intent = new Intent(this, MipcaActivityCapture.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) return;
        if (data != null)
        {
            //礼品卡信息格式： BIU7265391734:NVU3749J
            String scanResult = data.getStringExtra("scanResult");
            if (!isEmpty(scanResult) && scanResult.contains(":"))
            {
                String[] info = scanResult.split(":");
                etGiftCardNo.setText(info[0]);
                etGiftCardPwd.setText(info[1]);
            }
        }
    }

    /**
     * 立即激活
     */
    private void exeActive()
    {
        showPDialog(R.string.loading);
        activeCardPresenter.setInterfaceParameters(etGiftCardNo.getText().toString().trim(),etGiftCardPwd.getText().toString().trim());
    }

    private boolean isInputRight()
    {
        if (isEmpty(etGiftCardNo.getText().toString().trim()))
        {
            toastCustom("请输入礼品卡号");
            return false;
        }
        if (isEmpty(etGiftCardPwd.getText().toString().trim()))
        {
            toastCustom("请输入礼品卡密码");
            return false;
        }
        return true;
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom("激活成功");
        finish();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom(o+"");
    }
}
