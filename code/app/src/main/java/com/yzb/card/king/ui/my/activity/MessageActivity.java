package com.yzb.card.king.ui.my.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.integral.MyRemindActivity;
import com.yzb.card.king.ui.my.bean.MessageTypeBean;
import com.yzb.card.king.ui.my.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 消息模块的主函数
 * 作 者： gaolei
 * 日 期：2016年12月8日
 * 描 述：跳转具体通知消息的主函数
 */

public class MessageActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    private RelativeLayout msgActivityLayout;
    private RelativeLayout msgOrderLayout;
    private RelativeLayout msgSystemLayout;
    private TextView activityNumTxt;
    private TextView orderNumTxt;
    private TextView sysNumTxt;
    private List<Map<String, Object>> data;
    private MessageNumReceiver receiver = new MessageNumReceiver();
    private MessagePresenter presenter;
    private MessageTypeBean listBean;
    private TextView actTxt;
    private TextView actTime;
    private TextView ordTxt;
    private TextView ordTime;
    private TextView sysTxt;
    private TextView sysTime;

    private FrameLayout rlThree,rlOne,rlTwo;
    private TextView tvThree,tvOne,tvTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        presenter = new MessagePresenter(this);
        initMainPresenter();
        initView();
        initResgin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initMainPresenter();
    }

    private void initStatusPresenter() {
        Map<String, Object> param = new HashMap<>();
        param.put("newsId", "");
        param.put("sturt", "");//0：删除，2：已阅读
        param.put("materialId", "");//推送记录id
        presenter.getMessageData(param, "", 3);
    }

    private void initDetailPresenter() {
        Map<String, Object> param = new HashMap<>();
        param.put("taskType", "H");//H:活动通知、S:系统通知  D:订单通知、U:个人通知
        param.put("taskSturt", "1,2");//0：删除，1：未阅读 2：已阅读 ，）支持多个查询，中间用英文逗号分隔，如查询已阅读和未阅读为1,2
        presenter.getMessageData(param, "", 2);
    }

    private void initMainPresenter() {
        Map<String, Object> param = new HashMap<>();
        //param.put("", "");
        presenter.getMessageData(param, CardConstant.QueryNewsList, 1);
    }

    private void intData() {
        data = GlobalApp.getInstance().jpushData;
        if (data != null && data.size() > 0) {
            activityNumTxt.setVisibility(View.VISIBLE);
            activityNumTxt.setText(data.size() + "");
        } else {
            activityNumTxt.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        msgActivityLayout = (RelativeLayout) findViewById(R.id.message_activity_layout);
        msgOrderLayout = (RelativeLayout) findViewById(R.id.message_order_layout);
        msgSystemLayout = (RelativeLayout) findViewById(R.id.message_system_layout);

        activityNumTxt = (TextView) findViewById(R.id.message_activity_numberTxt);
        orderNumTxt = (TextView) findViewById(R.id.message_order_numberTxt);
        sysNumTxt = (TextView) findViewById(R.id.message_system_numberTxt);

        actTxt = (TextView) findViewById(R.id.message_activity_content_txt);
        actTime = (TextView) findViewById(R.id.message_activity_time_txt);
        ordTxt = (TextView) findViewById(R.id.message_order_content_txt);
        ordTime = (TextView) findViewById(R.id.message_order_time_txt);
        sysTxt = (TextView) findViewById(R.id.message_system_content_txt);
        sysTime = (TextView) findViewById(R.id.message_system_time_txt);

        msgActivityLayout.setOnClickListener(this);
        msgOrderLayout.setOnClickListener(this);
        msgSystemLayout.setOnClickListener(this);

        rlThree = (FrameLayout)findViewById(R.id.rlThree);
        tvThree = (TextView) findViewById(R.id.tvThree);

        rlOne = (FrameLayout)findViewById(R.id.rlOne);
        tvOne = (TextView) findViewById(R.id.tvOne);

        rlTwo = (FrameLayout)findViewById(R.id.rlTwo);
        tvTwo = (TextView) findViewById(R.id.tvTwo);

        findViewById(R.id.rlTx).setOnClickListener(this);
    }

    public void backAction(View view) {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlTx:

                readyGo(this, MyRemindActivity.class);

                break;
            case R.id.message_activity_layout:
                Intent intent = new Intent(this, MessageDetailActivity.class);
                intent.putExtra("activityMsg", "活动通知");
                startActivity(intent);
                break;
            case R.id.message_order_layout:
                Intent intent2 = new Intent(this, MessageDetailActivity.class);
                intent2.putExtra("orderMsg", "订单通知");
                startActivity(intent2);
                break;
            case R.id.message_system_layout:
                Intent intent3 = new Intent(this, MessageDetailActivity.class);
                intent3.putExtra("systemMsg", "系统消息");
                startActivity(intent3);
                break;
        }
    }

    private void initResgin() {
        //发送广播刷新页面
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.message.number");
        this.registerReceiver(receiver, filter);
    }


    private void setMessageView(int messageNum,FrameLayout frameLayout,TextView tv,TextView tvAdd){
        if (messageNum == 0) {
            frameLayout.setVisibility(View.INVISIBLE);
        } else {
            frameLayout.setVisibility(View.VISIBLE);

            if(messageNum>100){
                tv.setText("99");
                tvAdd.setVisibility(View.VISIBLE);

            }else{
                tvAdd.setVisibility(View.INVISIBLE);
                tv.setText(messageNum + "");
            }

        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1) {
            listBean = JSON.parseObject(String.valueOf(o), MessageTypeBean.class);
            if (listBean != null) {
                int len = listBean.getNewsMapList().size();
                for (int i = 0; i < len; i++) {
                    if (listBean.getNewsMapList().get(i).getTaskType().equals("H")) {//活动通知
                        actTxt.setText(listBean.getNewsMapList().get(i).getNewsCommon() + "");
                        String tradeTime = listBean.getNewsMapList().get(i).getOptime();
                        String[] split = tradeTime.split(" ");
                        String time = split[1];
                        String[] split2 = time.split(":");
                        actTime.setText(split[0] + "  " + split2[0] + ":" + split2[1]);
                        int one = listBean.getNewsMapList().get(i).getNewsNmber();

                        setMessageView(one,rlOne,activityNumTxt,tvOne);
                    }
                    if (listBean.getNewsMapList().get(i).getTaskType().equals("S")) {//系统通知
                        sysTxt.setText(listBean.getNewsMapList().get(i).getNewsCommon() + "");
                        String tradeTime = listBean.getNewsMapList().get(i).getOptime();
                        String[] split = tradeTime.split(" ");
                        String time = split[1];
                        String[] split2 = time.split(":");
                        sysTime.setText(split[0] + "  " + split2[0] + ":" + split2[1]);
                        int three = listBean.getNewsMapList().get(i).getNewsNmber();
                        setMessageView(three,rlThree,sysNumTxt,tvThree);

                    }
                    if (listBean.getNewsMapList().get(i).getTaskType().equals("D")) {//订单通知
                        ordTxt.setText(listBean.getNewsMapList().get(i).getNewsCommon() + "");
                        String tradeTime = listBean.getNewsMapList().get(i).getOptime();
                        String[] split = tradeTime.split(" ");
                        String time = split[1];
                        String[] split2 = time.split(":");
                        ordTime.setText(split[0] + "  " + split2[0] + ":" + split2[1]);

                        int one = listBean.getNewsMapList().get(i).getNewsNmber();
                        setMessageView(one,rlTwo,orderNumTxt,tvTwo);
                    }
                }
            } else {
                //Toast.makeText(this, "无信息", Toast.LENGTH_SHORT).show();
            }
        }

        if (type == 2) {

        }

        if (type == 3) {

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == 1) {
            Toast.makeText(this, "三大消息类型", Toast.LENGTH_SHORT).show();
        }
    }

    public class MessageNumReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals("com.message.number")) {
//                Log.d("gl", "================================");
//                List<Map<String, Object>> list = new ArrayList<>();
//                list.addAll(data);
//                int i = intent.getExtras().getInt("position");
//                if (list.size() - i == 0) {
//                    activityNumTxt.setVisibility(View.INVISIBLE);
//                }
//                if (data.size() > i){
//                    if (list.size() - i == 0) {
//                        activityNumTxt.setVisibility(View.INVISIBLE);
//                    } else {
//                        activityNumTxt.setText(list.size() - i + "");
//                    }
//                } else {
//                    activityNumTxt.setText(data.size()+"");
//                    if (data.size()==0){
//                        activityNumTxt.setVisibility(View.INVISIBLE);
//                    }
//                }
//            }
        }

        public MessageNumReceiver() {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}
