package com.yzb.card.king.ui.bonus.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.adapter.RedBagDetailAdapter;
import com.yzb.card.king.ui.bonus.bean.RedBagRecOrSendBean;
import com.yzb.card.king.ui.bonus.presenter.RedBagRecOrSendPresenter;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 红包明细详情
 * 作 者： gaolei
 * 日 期：2016年12月26日
 * 描 述：红包明细详情
 */
public class RedBagDetailActivity extends BaseActivity implements RedBagDetailAdapter.OnMyItemClickListener, BaseViewLayerInterface
{

    private RecyclerView recycler;

    private RedBagDetailAdapter adapter;

    private RedBagRecOrSendPresenter presenter;

    private RedBagRecOrSendBean listBean;

    private List<RedBagRecOrSendBean.ReceiveListBean> receiveList;

    private ImageView userImg;

    private TextView userName;

    private TextView hopeWord;

    private TextView allMessage;

    private TextView recAcount;

    private ImageView backPhoto;

    private TextView titleName;

    private String themeName;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_bag_detail);
        presenter = new RedBagRecOrSendPresenter(this);
        themeName = getIntent().getExtras().getString("themeName");
        initPresent();
        initView();
    }

    private void initData(boolean statusFlag)
    {

        if (receiveList != null)
        {
            adapter = new RedBagDetailAdapter(this, receiveList);
            adapter.setStatuFlag(statusFlag);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(manager);
            recycler.addItemDecoration(new DecorationUtil(2));
            recycler.setAdapter(adapter);
            adapter.setOnMyItemClickListener(this);
        }

    }

    private void initView()
    {
        recycler = (RecyclerView) findViewById(R.id.red_bag_detail_recyclerview);
        userImg = (ImageView) findViewById(R.id.redbag_detail_usercicle_img);
        backPhoto = (ImageView) findViewById(R.id.thumbImg);
        userName = (TextView) findViewById(R.id.redbag_detail_user_txt);
        hopeWord = (TextView) findViewById(R.id.redbag_detail_dreamword_txt);
        allMessage = (TextView) findViewById(R.id.redbag_detail_allmessage_txt);
        recAcount = (TextView) findViewById(R.id.redbag_detail_totalmoney_txt);
        titleName = (TextView) findViewById(R.id.red_bag_titleName);
        titleName.setText(themeName);

    }

    private void initPresent()
    {
        Map<String, Object> param = new HashMap<>();
        long orderId = getIntent().getExtras().getInt("RecordId");//实际中根据RecordId来请求
        param.put("orderId", orderId);
        presenter.getRedBagDetailTotalData(param, CardConstant.REC_OR_SEND_DETAIL, 1);
    }

    @Override
    public void OnMyItemClick(RecyclerView parent, View view, int position, List<RedBagRecOrSendBean.ReceiveListBean> data)
    {
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1)
        {
            listBean = JSON.parseObject(String.valueOf(o), RedBagRecOrSendBean.class);
            userName.setText(listBean.getIssuePerson() + "");
            x.image().bind(userImg, ServiceDispatcher.getImageUrl(listBean.getIssuePersonPhoto()),
                    XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(30), ImageView.ScaleType.FIT_CENTER));//头像图片
            x.image().bind(backPhoto, ServiceDispatcher.getImageUrl(listBean.getOpenImageCode() + ""));//获取背景图片
            hopeWord.setText(listBean.getBlessWord() + "");
            String orderAcount = listBean.getOrderAmount();//订单金额
            int totalQuantity = listBean.getTotalQuantity();//发放数量
            int receiveQuantity = listBean.getReceiveQuantity();//领取数量
            StringBuilder builder = new StringBuilder();
            builder.append(totalQuantity + "个红包共").append(orderAcount + "元");

            recAcount.setText(listBean.getReceiveAmount() + "元");
            allMessage.setText(builder.toString());
            receiveList = listBean.getReceiveList();

            for (int i = 0; i < receiveList.size(); i++)
            {
                if (receiveList.get(i).getReceivePerson().equals(listBean.getIssuePerson()))
                {
                    recAcount.setText(receiveList.get(i).getReceiveAmount() + "");
                    break;
                }
            }

            boolean orderStatus = (OrderBean.ORDER_STATUS_COMPLETE + "").equals(listBean.getOrderStatus());
            boolean flag = totalQuantity == receiveQuantity || !orderStatus;
            initData(flag);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == 1)
        {
            Toast.makeText(this, "无信息", Toast.LENGTH_SHORT).show();
        }
    }
}
