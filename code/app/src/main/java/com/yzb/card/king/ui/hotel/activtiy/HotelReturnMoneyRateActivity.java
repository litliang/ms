package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.order.HotelOrderDetailActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 类  名：返现进度
 * 作  者：Li Yubing
 * 日  期：2017/9/14
 * 描  述：
 */
@ContentView(R.layout.activity_return_money_rate)
public class HotelReturnMoneyRateActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.tvMsg)
    private TextView tvMsg;

    @ViewInject(R.id.tvButton)
    private TextView tvButton;

    //状态空间
    @ViewInject(R.id.ivOneYuan)
    private ImageView ivOneYuan;

    @ViewInject(R.id.ivOneLine)
    private ImageView ivOneLine;

    @ViewInject(R.id.tvOneT)
    private TextView tvOneT;

    @ViewInject(R.id.tvOneM)
    private TextView tvOneM;


    @ViewInject(R.id.ivTwoYuan)
    private ImageView ivTwoYuan;

    @ViewInject(R.id.ivTwoLine)
    private ImageView ivTwoLine;


    @ViewInject(R.id.tvTwoT)
    private TextView tvTwoT;

    @ViewInject(R.id.tvTwoM)
    private TextView tvTwoM;


    @ViewInject(R.id.ivThreeYuan)
    private ImageView ivThreeYuan;

    @ViewInject(R.id.tvThreeM)
    private TextView tvThreeM;

    @ViewInject(R.id.tvThreeT)
    private TextView tvThreeT;

    /**
     * 订单id
     */
    private long orderId;
    /**
     * 类型
     */
    private int hotelType;
    /**
     * 返现价格
     */
    private float money = 90;
    /**
     * 状态
     */
    private int orderStutas;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        orderId = getIntent().getLongExtra("orderId", 0);

        hotelType = getIntent().getIntExtra("hotelType", 0);

        money = getIntent().getFloatExtra("money", 0);

        orderStutas = getIntent().getIntExtra("orderStutas", 0);


        setTitleNmae("返现进度");

        StringBuffer sb = new StringBuffer();

        sb.append("您已离店，请于30天内在订单详情页申请返现。您的返现").append(money).append("元");

        tvMsg.setText(sb.toString());


        ivOneYuan.setBackgroundResource(R.mipmap.icon_blue_double_yuan);

        tvOneT.setTextColor(getResources().getColor(R.color.color_446b91));

        tvOneM.setTextColor(getResources().getColor(R.color.color_446b91));

        if (orderStutas == OrderBean.ORDER_STATUS_DAIFANXIAN) {//待返现

            ivOneLine.setBackgroundResource(R.mipmap.line_blue_xu);

            ivTwoLine.setBackgroundResource(R.mipmap.line_blue_xu);

            ivTwoYuan.setBackgroundResource(R.mipmap.icon_blue_double_yuan);

            tvTwoT.setTextColor(getResources().getColor(R.color.color_446b91));

            tvTwoM.setTextColor(getResources().getColor(R.color.color_446b91));

        } else if (orderStutas == OrderBean.ORDER_STATUS_YIFANXIAN) {//已返现

            ivOneLine.setBackgroundResource(R.mipmap.line_blue_xu);

            ivTwoLine.setBackgroundResource(R.mipmap.line_blue_xu);

            ivTwoYuan.setBackgroundResource(R.mipmap.icon_blue_double_yuan);

            tvTwoT.setTextColor(getResources().getColor(R.color.color_446b91));

            tvTwoM.setTextColor(getResources().getColor(R.color.color_446b91));

            ivThreeYuan.setBackgroundResource(R.mipmap.icon_blue_double_yuan);

            tvThreeT.setTextColor(getResources().getColor(R.color.color_446b91));

            tvThreeM.setTextColor(getResources().getColor(R.color.color_446b91));

        }
        tvButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        Intent intent = new Intent(this, HotelOrderDetailActivity.class);
        intent.putExtra("id", orderId);
        intent.putExtra("hotelType", hotelType);
        startActivity(intent);

        finish();

    }
}
