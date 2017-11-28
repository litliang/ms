package com.yzb.card.king.ui.transport.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseFragmentActivity;
import com.yzb.card.king.ui.transport.fragment.SwitchFragmentCallBack;
import com.yzb.card.king.ui.transport.fragment.WriteOrderFragment;
import com.yzb.card.king.ui.transport.fragment.ShopSelectFragment;

/**
 * 自驾租车业务流
 * Created by tarena on 2016/5/26.
 */
public class SelfDriveLogicActivity extends BaseFragmentActivity implements SwitchFragmentCallBack
{

    public static String CLASS_NAME;

    private String address = "";
    /**
     * 常量
     */
    public static final int SELF_DRIVE_FLAG = 1;//自驾租车

    public static final int DAIL_YRENT_FLAG = 2;//日租包车

    /**
     * 控件
     */
    private TextView titleName;

    private TextView tvStepShopSelectTwo, tvStepShopSelectTwoText, tvStepShopSelectThree, tvStepShopSelectThreeText,
            tvStepShopSelectFour, tvStepShopSelectFourText;

    private ImageView ivStepShopSelectTwo, ivStepShopSelectThree;

    /**
     * 当前量
     */
    private int currentFragmentIndex = 2;

    private int currentLogicFlag = SELF_DRIVE_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selfdrive_logic);

        CLASS_NAME = SelfDriveLogicActivity.this.getClass().getName();

        registerBoradcastReceiver();

        currentFragmentIndex = getIntent().getIntExtra("stepIndex", 2);

        if (getIntent().hasExtra("address"))
        {
            address = getIntent().getStringExtra("address");
        }
        if (getIntent().hasExtra("logic_flag"))
        {
            currentLogicFlag = getIntent().getIntExtra("logic_flag", SELF_DRIVE_FLAG);
        }


        initView();
    }

    /**
     * 初始化当前页面
     */
    private void initView()
    {

        titleName = (TextView) findViewById(R.id.titleName);

        titleName.setText(R.string.nearby_shop_str);

        tvStepShopSelectTwo = (TextView) findViewById(R.id.tvStepShopSelectTwo);

        tvStepShopSelectTwoText = (TextView) findViewById(R.id.tvStepShopSelectTwoText);

        tvStepShopSelectThree = (TextView) findViewById(R.id.tvStepShopSelectThree);

        tvStepShopSelectThreeText = (TextView) findViewById(R.id.tvStepShopSelectThreeText);

        tvStepShopSelectFour = (TextView) findViewById(R.id.tvStepShopSelectFour);

        tvStepShopSelectFourText = (TextView) findViewById(R.id.tvStepShopSelectFourText);

        ivStepShopSelectTwo = (ImageView) findViewById(R.id.ivStepShopSelectTwo);

        ivStepShopSelectThree = (ImageView) findViewById(R.id.ivStepShopSelectThree);

        ShopSelectFragment fragment1 = new ShopSelectFragment();

        fragment1.setCallBack(this);

        FragmentManager fm = getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.flSelfDive, fragment1);

        transaction.commit();

        switchFunctionFragment(currentFragmentIndex);
    }

    @Override
    public void transmitFragmentIndex(int index)
    {

        currentFragmentIndex = index;
        switchFunctionFragment(currentFragmentIndex);

    }

    /**
     * 切换功能碎片
     *
     * @param fragmentIndex
     */
    private void switchFunctionFragment(int fragmentIndex)
    {


        //流程步骤提示
        stepFlowImage(fragmentIndex);
        //自驾租车步骤业务流
        switch (fragmentIndex)
        {

            case 2://附近门店
                titleName.setText(R.string.nearby_shop_str);

                ShopSelectFragment shopFragment = new ShopSelectFragment();

                Bundle shopBundle = new Bundle();

                shopBundle.putInt("logic_flag", currentLogicFlag);
                shopBundle.putString("address", address);
                shopFragment.setArguments(shopBundle);

                shopFragment.setCallBack(this);

                FragmentManager fm = getFragmentManager();

                FragmentTransaction transaction = fm.beginTransaction();

                transaction.replace(R.id.flSelfDive, shopFragment);

                transaction.commit();
                break;
            case 3://车辆选择
//                BusSelectFragment fragment2 = new BusSelectFragment();
//
//                fragment2.setCallBack(this);
//
//                FragmentManager fm2 = getFragmentManager();
//
//                FragmentTransaction transaction2 = fm2.beginTransaction();
//
//                transaction2.replace(R.id.flSelfDive, fragment2);
//
//                transaction2.commit();

                Log.i("LCZ", " 自驾租车 ============" + currentLogicFlag);

                Intent intent = new Intent(SelfDriveLogicActivity.this, SelectMotorcycleTypeActivity.class);

                intent.putExtra("logic_flag", currentLogicFlag);

                startActivity(intent);

                break;
            case 4://填写订单
                WriteOrderFragment fragment3 = new WriteOrderFragment();

                Bundle writeBundl = new Bundle();

                writeBundl.putInt("logic_flag", currentLogicFlag);

                fragment3.setArguments(writeBundl);

                fragment3.setCallBack(this);

                FragmentManager fm3 = getFragmentManager();

                FragmentTransaction transaction3 = fm3.beginTransaction();

                transaction3.replace(R.id.flSelfDive, fragment3);

                transaction3.commit();

                titleName.setText(R.string.write_order_str);
                break;
            case 5://关闭当前界面
                Log.i("LCZ", "Case   5 关闭当前页面");
                finish();
                break;
            default:
                break;
        }

    }


    /**
     * 步骤流程图示
     *
     * @param fragmentIndex
     */
    private void stepFlowImage(int fragmentIndex)
    {

        switch (fragmentIndex)
        {
            case 2:

                ivStepShopSelectTwo.setBackgroundResource(R.mipmap.icon_step_grey);

                tvStepShopSelectThree.setBackgroundResource(R.drawable.icon_round_number_grey);
                tvStepShopSelectThreeText.setTextColor(getResources().getColor(R.color.gray_aaaaaa));
                ivStepShopSelectThree.setBackgroundResource(R.mipmap.icon_step_grey);

                tvStepShopSelectFour.setBackgroundResource(R.drawable.icon_round_number_grey);
                tvStepShopSelectFourText.setTextColor(getResources().getColor(R.color.gray_aaaaaa));

                break;
//            case 3:
//
//
//                tvStepShopSelectThree.setBackgroundResource(R.mipmap.icon_round_number_red);
//                tvStepShopSelectThreeText.setTextColor(getResources().getColor(R.color.red_d84043));
//                ivStepShopSelectThree.setBackgroundResource(R.mipmap.icon_step_grey);
//
//                tvStepShopSelectFour.setBackgroundResource(R.mipmap.icon_round_number_grey);
//                tvStepShopSelectFourText.setTextColor(getResources().getColor(R.color.gray_aaaaaa));
//                break;

            case 4:

                tvStepShopSelectThree.setBackgroundResource(R.drawable.icon_round_number_red);
                tvStepShopSelectThreeText.setTextColor(getResources().getColor(R.color.red_d84043));
                ivStepShopSelectThree.setBackgroundResource(R.mipmap.icon_step_grey);

                tvStepShopSelectFour.setBackgroundResource(R.drawable.icon_round_number_red);
                tvStepShopSelectFourText.setTextColor(getResources().getColor(R.color.red_d84043));
                break;
            default:
                break;
        }
    }

    /**
     * 返回按钮重定义
     *
     * @param view
     */
    public void backAction(View view)
    {
        backKeyLogic();
    }

    /**
     * 重写onKeyDown方法可以拦截系统默认的处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            backKeyLogic();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 返回按钮的业务事件
     */
    private void backKeyLogic()
    {
//        if(currentFragmentIndex==2){
//
//            finish();
//
//        }else{
//            currentFragmentIndex --;
//            switchFunctionFragment(currentFragmentIndex);
//        }


        finish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unRegisterBoradcastReceiver();
    }

    /**
     * 注册广播接收器
     */
    public void registerBoradcastReceiver()
    {


        IntentFilter myIntentFilter = new IntentFilter();

        myIntentFilter.addAction(SelfDriveLogicActivity.CLASS_NAME);
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    /**
     * 取消注册广播
     */
    public void unRegisterBoradcastReceiver()
    {

        if (mBroadcastReceiver != null)
        {

            unregisterReceiver(mBroadcastReceiver);
        }

    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            String action = intent.getAction();

            if (action.equals(SelfDriveLogicActivity.CLASS_NAME))
            {

                boolean isFinish = intent.getBooleanExtra("hasFinish", false);

                if (isFinish)
                {
                    finish();
                }
            }
        }
    };
}
