package com.yzb.card.king.ui.gift.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.gift.adapter.GiftcardUseDetailAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardRecOrSendBean;
import com.yzb.card.king.ui.gift.presenter.GiftCardRecOrSendPresent;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 礼品卡领取明细
 * 作 者： gaolei
 * 日 期：2016年12月27日
 * 描 述：礼品卡领取明细
 */
public class GiftCardUseDetailActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface
{

    private ImageView ivXykBg;
    private TextView tvContent;
    private RecyclerView bounsList;
    private LinearLayout all;
    private LinearLayout received;
    private LinearLayout notReceive;
    private TextView allCount;
    private TextView receivedCount;
    private TextView notReceiveCount;
    private List<GiftCardRecOrSendBean> allList = new ArrayList<>();
    private List<GiftCardRecOrSendBean> receiveList = new ArrayList<>();
    private List<GiftCardRecOrSendBean> notReceiveList = new ArrayList<>();
    private TextView all_txt;
    private TextView receive_txt;
    private TextView norec_txt;
    private GiftcardUseDetailAdapter adapter;
    private GiftCardRecOrSendPresent present;

    private String orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_use_detail);
        present = new GiftCardRecOrSendPresent(this);
        recvIntentData();
        initView();
        initData();
        getNoRecGiftCard();
        initViews("0");
        setWordColor("0");

    }

    private void recvIntentData()
    {

        String orderId = getIntent().getStringExtra("orderId");
        if (!isEmpty(orderId))
        {
            this.orderId = orderId;
        } else
        {
            //此时通过领取礼品卡H5页面过来；
            Uri uri = getIntent().getData();
            if (uri != null)
            {
                this.orderId = uri.getQueryParameter("orderId");
            }
        }
        LogUtil.i("接收到的orderId=" + orderId);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //如果遇到进入页面第一次不加载数据的话  把下面的三句话放在onCreat中
        getNoRecGiftCard();
        initViews("0");
        setWordColor("0");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    private void initData()
    {

        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);// 订单Id要从别的activity中传入
        present.getListData(param, CardConstant.GIFTCARD_RECORSEND, 1);


        if (null != allList && allList.size() > 0)
        {

            //全部
            allCount.setText("(" + allList.size() + ")");
            //已领取
            receivedCount.setText("(" + receiveList.size() + ")");
            //未领取
            notReceiveCount.setText("(" + notReceiveList.size() + ")");

            //content image 加载文字和主题图片
            GiftCardRecOrSendBean bean = allList.get(0);

            String cardName = bean.getCardName();

            String blessWord = bean.getBlessWord();

            if (null != blessWord)
            {
                tvContent.setText(cardName+"  "+blessWord);//获取图片下面的文字祝福语
            }
            x.image().bind(ivXykBg, ServiceDispatcher.getImageUrl(allList.get(0).getCardImage()));//获取背景图片

            adapter = new GiftcardUseDetailAdapter(this, allList);
            bounsList.setAdapter(adapter);
            getNoRecGiftCard();
            adapter.notifyDataSetChanged();

        }

    }

    private void initView()
    {

        bounsList = (RecyclerView) findViewById(R.id.giftcard_use_detail_img);
        all = (LinearLayout) findViewById(R.id.ll_all);
        all.setOnClickListener(this);
        received = (LinearLayout) findViewById(R.id.ll_received);
        received.setOnClickListener(this);
        notReceive = (LinearLayout) findViewById(R.id.ll_notReceive);
        notReceive.setOnClickListener(this);

        allCount = (TextView) findViewById(R.id.allCount);
        all_txt = (TextView) findViewById(R.id.allCount_txt);
        receivedCount = (TextView) findViewById(R.id.receivedCount);
        receive_txt = (TextView) findViewById(R.id.receivedCount_txt);
        notReceiveCount = (TextView) findViewById(R.id.notReceiveCount);
        norec_txt = (TextView) findViewById(R.id.notReceiveCount_txt);
        ivXykBg = (ImageView) findViewById(R.id.iv_bgxyk);
        tvContent = (TextView) findViewById(R.id.tv_content);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        bounsList.setLayoutManager(manager);
    }

    private void initViews(String type)
    {

        //全部
        allCount.setText("(" + allList.size() + ")");
        //已领取
        receivedCount.setText("(" + receiveList.size() + ")");
        //未领取
        notReceiveCount.setText("(" + notReceiveList.size() + ")");

        if (allList.size() != 0)
        {
            //content image 加载文字和主题图片


            GiftCardRecOrSendBean bean = allList.get(0);

            String cardName = bean.getCardName();

            String blessWord = bean.getBlessWord();

            if (null != blessWord)
            {
                tvContent.setText(cardName+"  "+blessWord);//获取图片下面的文字祝福语
            }
            x.image().bind(ivXykBg, ServiceDispatcher.getImageUrl(allList.get(0).getCardImage()));//获取背景图片
        }

        if (type.equals("0"))
        {
            adapter = new GiftcardUseDetailAdapter(this, allList);
            bounsList.setAdapter(adapter);
            getNoRecGiftCard();

        } else if (type.equals("1"))
        {
            adapter = new GiftcardUseDetailAdapter(this, receiveList);
            bounsList.setAdapter(adapter);
            getNoRecGiftCard();
        } else if (type.equals("2"))
        {
            adapter = new GiftcardUseDetailAdapter(this, notReceiveList);
            bounsList.setAdapter(adapter);
            getNoRecGiftCard();
        } else
        {
            Toast.makeText(this, "没有您要查询的信息", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view)
    {
        getNoRecGiftCard();
        switch (view.getId())
        {
            case R.id.ll_all:
                initViews("0");
                setWordColor("0");
                break;

            case R.id.ll_received:
                initViews("1");
                setWordColor("1");
                break;

            case R.id.ll_notReceive:
                initViews("2");
                setWordColor("2");
                break;
        }
    }

    public void setWordColor(String type)
    {
        if (type.equals("0"))
        {
            all_txt.setTextColor(getResources().getColor(R.color.red_ff1036));
            allCount.setTextColor(getResources().getColor(R.color.red_ff1036));
            receivedCount.setTextColor(getResources().getColor(R.color.wordBlack2));
            receive_txt.setTextColor(getResources().getColor(R.color.wordBlack2));
            notReceiveCount.setTextColor(getResources().getColor(R.color.wordBlack2));
            norec_txt.setTextColor(getResources().getColor(R.color.wordBlack2));
        } else if (type.equals("1"))
        {
            receivedCount.setTextColor(getResources().getColor(R.color.red_ff1036));
            receive_txt.setTextColor(getResources().getColor(R.color.red_ff1036));
            all_txt.setTextColor(getResources().getColor(R.color.wordBlack2));
            allCount.setTextColor(getResources().getColor(R.color.wordBlack2));
            notReceiveCount.setTextColor(getResources().getColor(R.color.wordBlack2));
            norec_txt.setTextColor(getResources().getColor(R.color.wordBlack2));
        } else if (type.equals("2"))
        {
            notReceiveCount.setTextColor(getResources().getColor(R.color.red_ff1036));
            norec_txt.setTextColor(getResources().getColor(R.color.red_ff1036));
            all_txt.setTextColor(getResources().getColor(R.color.wordBlack2));
            allCount.setTextColor(getResources().getColor(R.color.wordBlack2));
            receivedCount.setTextColor(getResources().getColor(R.color.wordBlack2));
            receive_txt.setTextColor(getResources().getColor(R.color.wordBlack2));
        }
    }

    public void backAction(View view)
    {
        finish();
    }



    public void getNoRecGiftCard()
    {

        if (null != allList && allList.size() > 0)
        {
            receiveList.clear();
            notReceiveList.clear();
            for (int i = 0; i < allList.size(); i++)
            {
//                if(null != allList.get(i).getProductDesc() ){
//                    tvContent.setText(allList.get(i).getProductDesc());//获取图片下面的文字祝福语
//                }
                //x.image().bind(ivXykBg, ServiceDispatcher.getImageUrl(allList.get(i).getCardImage()));//获取背景图片 有问题 为什么这么设计
                if (allList.get(i).getReceiveTime() != null && !TextUtils.isEmpty(allList.get(i).getReceiveTime()))
                {
                    receiveList.add(allList.get(i));
                } else
                {
                    notReceiveList.add(allList.get(i));
                }
            }

            GiftCardRecOrSendBean bean = allList.get(0);

            String cardName = bean.getCardName();

            String blessWord = bean.getBlessWord();

            if (null != blessWord)
            {
                tvContent.setText(cardName+"  "+blessWord);//获取图片下面的文字祝福语
            }
            x.image().bind(ivXykBg, ServiceDispatcher.getImageUrl(allList.get(0).getCardImage()));//获取背景图片
        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1)
        {
            List<GiftCardRecOrSendBean> beanList = JSON.parseArray(String.valueOf(o), GiftCardRecOrSendBean.class);

            for (int i = 0; i < beanList.size(); i++)
            {//可以用来判断是否领用
            }
            allList.addAll(beanList);
        }

        adapter = new GiftcardUseDetailAdapter(this, allList);
        bounsList.setAdapter(adapter);
        getNoRecGiftCard();
        adapter.notifyDataSetChanged();
        //全部
        allCount.setText("(" + allList.size() + ")");
        //已领取
        receivedCount.setText("(" + receiveList.size() + ")");
        //未领取
        notReceiveCount.setText("(" + notReceiveList.size() + ")");
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        Toast.makeText(this, "服务器没有返回数据", Toast.LENGTH_SHORT).show();
    }
}
