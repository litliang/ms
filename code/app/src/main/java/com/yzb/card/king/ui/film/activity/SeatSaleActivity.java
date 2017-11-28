package com.yzb.card.king.ui.film.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.seatsale.OnSeatClickListener;
import com.yzb.card.king.ui.discount.seatsale.model.Seat;
import com.yzb.card.king.ui.discount.seatsale.model.SeatInfo;
import com.yzb.card.king.ui.discount.seatsale.view.SSThumView;
import com.yzb.card.king.ui.discount.seatsale.view.SSView;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;

public class SeatSaleActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout headerLayout;

    private static final int ROW = 10;
    private static final int EACH_ROW_COUNT = 16;
    private SSView mSSView;
    private SSThumView mSSThumView;
    private ArrayList<SeatInfo> list_seatInfos = new ArrayList<SeatInfo>();
    private ArrayList<ArrayList<Integer>> list_seat_conditions = new ArrayList<ArrayList<Integer>>();

    private String filmId;
    private String hallId;

    private TextView filmName;
    private TextView filmDate;
    private TextView filmTime;
    private TextView filmLanguages;
    private TextView filmEffect;
    private TextView filmPrice;
    private TextView screen;

    private ImageView editView;
    private Button payBtn;
    private TextView mobileText;

    private int count = 0;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_seat);
        switchContentLeft(AppConstant.RES_BACK);
        init();
    }


    private void init() {
        Bundle bundle = getIntent().getBundleExtra(AppConstant.INTENT_BUNDLE);
        filmId = bundle.getString("filmId");
        hallId = bundle.getString("hallId");
        String hallName = bundle.getString("hallName");
        String date = bundle.getString("date");
        String bgnTime = bundle.getString("bgnTime");
        price = bundle.getString("price");
        String storeName = bundle.getString("storeName");
        String name = bundle.getString("filmName");
        String languages = bundle.getString("filmLanguages");
        String effect = bundle.getString("filmEffect");

        // 设置标题
        setHeader(R.mipmap.icon_back_white, storeName);

        headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        headerLayout.setBackgroundColor(getResources().getColor(R.color.titleRed));

        filmDate = (TextView) findViewById(R.id.filmDate);
        filmDate.setText(date);

        filmTime = (TextView) findViewById(R.id.filmTime);
        filmTime.setText(bgnTime);

        filmPrice = (TextView) findViewById(R.id.filmPrice);
        filmPrice.setText(price);

        screen = (TextView) findViewById(R.id.screen);
        screen.setText(hallName + "  银幕");

        filmName = (TextView) findViewById(R.id.filmName);
        filmName.setText(name);

        filmLanguages = (TextView) findViewById(R.id.filmLanguages);
        filmLanguages.setText(languages);

        filmEffect = (TextView) findViewById(R.id.filmEffect);
        filmEffect.setText(effect);

        editView = (ImageView) findViewById(R.id.editView);
        editView.setOnClickListener(this);

        payBtn = (Button) findViewById(R.id.payBtn);
        payBtn.setOnClickListener(this);

        mobileText = (TextView) findViewById(R.id.mobileText);
        mobileText.setOnClickListener(this);
       // mobileText.setText(AppConstant.mobile);

        mSSView = (SSView) this.findViewById(R.id.mSSView);
        mSSThumView = (SSThumView) this.findViewById(R.id.ss_ssthumview);
        //mSSView.setXOffset(20);
        setSeatInfo();
        mSSView.init(EACH_ROW_COUNT, ROW, list_seatInfos, list_seat_conditions, mSSThumView, 5);
        mSSView.setOnSeatClickListener(new OnSeatClickListener() {
            @Override
            public boolean b(int column_num, int row_num, boolean paramBoolean) {
                count++;
                if (StringUtils.isEmpty(price))
                    price = "0";
                int allPrice = Integer.parseInt(price) * count;
                filmPrice.setText(String.valueOf(allPrice));
                String positionMsg = getString(R.string.seat_position);
                positionMsg = String.format(positionMsg, row_num + 1,column_num - 2);

                ToastUtil.i(SeatSaleActivity.this, positionMsg);
                return false;
            }

            @Override
            public void error(String msg) {
                ToastUtil.i(SeatSaleActivity.this, msg);
            }

            @Override
            public boolean a(int column_num, int row_num, boolean paramBoolean) {
                count--;
                if (StringUtils.isEmpty(price))
                    price = "0";
                int allPrice = Integer.parseInt(price) * count;
                filmPrice.setText(String.valueOf(allPrice));
                return false;
            }

            @Override
            public void a() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String mobile = "";
        if (data != null)
            mobile = data.getStringExtra("mobile");
        if (!StringUtils.isEmpty(mobile))
            mobileText.setText(mobile);
    }

    private void setSeatInfo() {
        for (int i = 0; i < ROW; i++) {//8行
            SeatInfo mSeatInfo = new SeatInfo();
            ArrayList<Seat> mSeatList = new ArrayList<Seat>();
            ArrayList<Integer> mConditionList = new ArrayList<Integer>();
            for (int j = 0; j < EACH_ROW_COUNT; j++) {//每排20个座位
                Seat mSeat = new Seat();
                if (j < 3) {
                    mSeat.setN("Z");
                    mConditionList.add(0);
                } else {
                    mSeat.setN(String.valueOf(j - 2));
                    if (j > 10) {
                        mConditionList.add(2);
                    } else {
                        mConditionList.add(1);
                    }

                }
                mSeat.setDamagedFlg("");
                mSeat.setLoveInd("0");
                mSeatList.add(mSeat);
            }
            mSeatInfo.setDesc(String.valueOf(i + 1));
            mSeatInfo.setRow(String.valueOf(i + 1));
            mSeatInfo.setSeatList(mSeatList);
            list_seatInfos.add(mSeatInfo);
            list_seat_conditions.add(mConditionList);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payBtn:
                ToastUtil.i(SeatSaleActivity.this, "敬请期待");
                break;
            case R.id.mobileText:
                Intent intent1 = new Intent();
                intent1.putExtra("mobile", mobileText.getText().toString());
                intent1.setClass(SeatSaleActivity.this, InputMobileActivity.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.editView:
                Intent intent2 = new Intent();
                intent2.putExtra("mobile", mobileText.getText().toString());
                intent2.setClass(SeatSaleActivity.this, InputMobileActivity.class);
                startActivityForResult(intent2, 0);
                break;
        }
    }
}
