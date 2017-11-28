package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.gift.activity.GiftCardBillDetailActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.QueryCustCouponBean;
import com.yzb.card.king.ui.my.bean.SelectAccountInfoBean;
import com.yzb.card.king.ui.my.presenter.BillDetailPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.photoutils.BitmapUtil;
import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 个人账单
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：账单明细
 */

public class MyBillActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    private LinearLayout card;
    private LinearLayout crash;
    private LinearLayout red_bag;
    private LinearLayout jifen;
    private TextView title_card;
    private TextView title_red;
    private TextView title_crash;
    private TextView title_discount;
    private TextView title_pingtai;
    private BillDetailPresenter present;
    private ImageView userImg;
    private LinearLayout storejifeng;
    private LinearLayout bankjifeng;
    private ImageOptions imageOptions;
    private TextView nameTxt;
    private UserBean userBean;
    private String amountAccount;
    private SelectAccountInfoBean infoBean;
    private List<QueryCustCouponBean> listBean;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);
        initView();
        present = new BillDetailPresenter(this);
        initOptions();
        initData();
        initData2();

    }

    private void initData() {
        Map<String, Object> param = new HashMap<>();
        param.put("amountAccount", amountAccount);
        param.put("balanceStatus", 1);//查询钱包余额 1:0
        param.put("giftcardStatus", 1);//查询礼品卡余额
        param.put("personBonusStatus", 1);//查询个人红包余额
        param.put("platformPointsStauts", 1);//查询平台积分
        //param.put("shopPointsStauts", amountAccount);//商家积分
        present.getBillDetailTotalData(param, CardConstant.SelectAccountInfo, 1);//个人账单明细
    }

    private void initData2() {
        Map<String, Object> param = new HashMap<>();
        param.put("actStatus", 3);//1未领取、2领取、3未使用、4使用、5过期
        param.put("sort", 1);
        param.put("pageStart", 0);
        param.put("pageSize", 100000);
        present.getBillDetailTotalData(param, CardConstant.card_app_querycustcoupon, 2);//个人账单明细
    }

    private void initOptions() {
        imageOptions = new ImageOptions.Builder()
                //  .setSize(org.xutils.common.util.DensityUtil.dip2px(30), org.xutils.common.util.DensityUtil.dip2px(30))
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(360))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                .setUseMemCache(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.icon_nav_user)
                .setFailureDrawableId(R.mipmap.icon_nav_user)
                .setCircular(false)
                .build();
        userBean = UserManager.getInstance().getUserBean();
        x.image().bind(userImg, userBean.getPic(),
                imageOptions, new CustomBitmapLoadCallBack());
        nameTxt.setText(userBean.getNickName() + "   " + userBean.getAccount());
        amountAccount = userBean.getAmountAccount() +"";
    }

    private void initView() {

        title_card = (TextView) findViewById(R.id.bill_mytitle_card);
        title_red = (TextView) findViewById(R.id.bill_mytitle_redbag);
        title_crash = (TextView) findViewById(R.id.bill_mytitle_carshmonry);
        title_discount = (TextView) findViewById(R.id.bill_mytitle_discount);
        title_pingtai = (TextView) findViewById(R.id.bill_mytitle_pingtai);
        userImg = (ImageView) findViewById(R.id.bill_username_img);
        nameTxt = (TextView)findViewById(R.id.bill_username_txt);

        card = (LinearLayout) findViewById(R.id.bill_card_llayout);
        crash = (LinearLayout) findViewById(R.id.bill_crashmoney_llayout);
        red_bag = (LinearLayout) findViewById(R.id.bill_redbag_llayout);
        jifen = (LinearLayout) findViewById(R.id.bill_jifeng_llayout);
        storejifeng = (LinearLayout) findViewById(R.id.bill_storejifeng_llayout);
        bankjifeng = (LinearLayout) findViewById(R.id.bill_bankjifeng_llayout);

        findViewById(R.id.bill_search_bank).setOnClickListener(this);
        findViewById(R.id.bill_search_store).setOnClickListener(this);

        card.setOnClickListener(this);
        crash.setOnClickListener(this);
        red_bag.setOnClickListener(this);
        jifen.setOnClickListener(this);
        storejifeng.setOnClickListener(this);
        bankjifeng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bill_card_llayout://礼品卡收支明细
                Intent intent = new Intent(this, GiftCardBillDetailActivity.class);
                intent.putExtra("billDetail", "礼品卡明细");
                startActivity(intent);
                break;

            case R.id.bill_crashmoney_llayout://现金收支明细
                Intent intent2 = new Intent(this, CrashDetailActivity.class);
                startActivity(intent2);
                break;

            case R.id.bill_redbag_llayout://红包收支明细
                Intent intent1 = new Intent(this, GiftCardBillDetailActivity.class);
                intent1.putExtra("billDetail", "红包明细");
                startActivity(intent1);
                break;

            case R.id.bill_jifeng_llayout://平台积分明细
                startActivity(new Intent(this, PingtaiJfDetailActivity.class));
                break;
            case R.id.bill_storejifeng_llayout://商家积分明细
                startActivity(new Intent(this, StoreJfDetailActivity2.class));

                break;
            case R.id.bill_bankjifeng_llayout://银行积分明细
                ToastUtil.i(MyBillActivity.this,"敬请期待");
                break;
            case R.id.bill_search_bank://银行积分查询
                //startActivity(new Intent(this,GiftCardStoreActivity.class));
                ToastUtil.i(MyBillActivity.this,"敬请期待");
                break;
            case R.id.bill_search_store://商家积分查询
                startActivity(new Intent(this, StoreJfSearchActivity.class));
                break;

        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (null != o && type == 1) {
            //得到服务器数据  bean还没写
            infoBean = JSON.parseObject(String.valueOf(o), SelectAccountInfoBean.class);
            if (infoBean != null) {
                Log.d("gl","infoBean=======" + infoBean);
                title_card.setText("" + infoBean.getGiftcardBalance());
                title_crash.setText("" + infoBean.getBalance());

                title_red.setText("" + infoBean.getPersonBonusBalance());
                title_pingtai.setText("" + infoBean.getPlatformPoints());
            }
        }

        if (null != o && type==2) {
            listBean = JSON.parseArray(String.valueOf(o),QueryCustCouponBean.class);
            size = listBean.size();
            Log.d("gl","=====size====" + size);
            title_discount.setText(size + "张可用");
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == 1) {
            Toast.makeText(this, "加载数据失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 图片加载进度
     */
    public class CustomBitmapLoadCallBack implements Callback.ProgressCallback<Drawable>
    {

        public CustomBitmapLoadCallBack()
        {
            //  this.holder = holder;
        }

        @Override
        public void onWaiting()
        {

            //this.holder.imgPb.setProgress(0);
            LogUtil.e("-------onWaiting-------");
        }

        @Override
        public void onStarted()
        {

            LogUtil.e("----onStarted----------");
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading)
        {
            //this.holder.imgPb.setProgress((int) (current * 100 / total));

            LogUtil.e("onLoading-->total=" + total + "  current=" + current + "  isDownloading=" + isDownloading);
        }

        @Override
        public void onSuccess(Drawable result)
        {

            //this.holder.imgPb.setProgress(100);
            LogUtil.e("-----onSuccess---------");

            Bitmap bitmap = BitmapUtil.drawableToBitmap(result);
            int w = org.xutils.common.util.DensityUtil.dip2px(90);
            Bitmap bit = BitmapUtil.createFramedPhoto(w, w, bitmap, w / 2);
            userImg.setImageBitmap(bit);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback)
        {
            LogUtil.e("-------onError-------");
        }

        @Override
        public void onCancelled(CancelledException cex)
        {
            LogUtil.e("------onCancelled--------");
        }

        @Override
        public void onFinished()
        {
            LogUtil.e("--------onFinished------");
        }
    }
}
