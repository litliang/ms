package com.yzb.card.king.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.FastPaymentOrderBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.user.AuthenticationInfoBean;
import com.yzb.card.king.jpush.bean.JpushLogicBean;
import com.yzb.card.king.jpush.bean.JpushNumBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.activity.IDAuthenticationActivity;
import com.yzb.card.king.ui.credit.activity.CreditOnlineCardActivity;
import com.yzb.card.king.ui.credit.activity.RepaymentActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.integral.MyRemindActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.bonus.activity.BounsListActivity;
import com.yzb.card.king.ui.my.activity.CashSuccActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardRecvECardActivity;
import com.yzb.card.king.ui.my.activity.MessageDetailActivity;
import com.yzb.card.king.ui.bonus.activity.RedBagDetailSendActivity;
import com.yzb.card.king.ui.my.activity.TransRecordActivity;
import com.yzb.card.king.ui.my.bean.AuthorBean;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.presenter.JpushPresenter;
import com.yzb.card.king.ui.my.view.JpushView;
import com.yzb.card.king.ui.order.HotelOrderDetailActivity;
import com.yzb.card.king.ui.order.PlaneTicketsDetailActivity;
import com.yzb.card.king.ui.order.TourismTicketsDetailActivity;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.yzb.card.king.util.UiUtils.startActivity;

/**
 *
 * 类 名： 推送消息的接受器
 * 作 者： gaolei
 * 日 期：2016年12月6日
 * 描 述：接受推送通知的
 */

public class JPushMsgReceiver extends BroadcastReceiver implements JpushView {

    private static final int NID = 0x1;//发送消息的ID

    private String newsType;

    private String key;

    private JpushPresenter presenter;

    private JpushNumBean jpushNumBean;

    private String targetActivity;

    private JpushLogicBean activityData;

    private FastPaymentOrderBean fastPaymentOrderBean;

    private String activityData2;

    private Context context1;

    private String title;

    private String msg;

    private CreditCard creditCard;

    private AuthorBean authorBean;

    private Payee payee1;

    private PayMethod payMethod;

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        presenter = new JpushPresenter(this);
        this.context1 = context;
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            //在用户注册之后才会有Id  eg:18071adc0302efbd21a
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);

            Map<String, String> map = new HashMap<>();
            if (!TextUtils.isEmpty(regId)) {
                map.put("regId", regId);
                GlobalApp.regIdList.add(map);
            }

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.i( "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            initPresenter(null, null);
            String contentMessage = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String title = intent.getStringExtra(JPushInterface.EXTRA_TITLE);
            LogUtil.e("contentMessage: " + contentMessage + "\n" +
                    "extra: " + extra + "\n" + "title: " + title);

                if (null != contentMessage && title.equals("8888")) {
                    fastPaymentOrderBean = JSON.parseObject(contentMessage, FastPaymentOrderBean.class);
                    //接收嘛尚付和牵手付转账信息
                    if(GlobalApp.activityStr != null){

                        fastPaymentOrderBean.setMarkActivity(GlobalApp.activityStr);

                        if("mfQr".equals(GlobalApp.activityStr)){//码尚付

                            EventBus.getDefault().post(fastPaymentOrderBean);

                        }else  if("qsNfcLinked".equals(GlobalApp.activityStr)){//牵手付

                            EventBus.getDefault().post(fastPaymentOrderBean);
                        }
                    }
                    sendNotifiction(context, "Hi Life", "转账成功", newsType, key);
                }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            title = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            msg = intent.getExtras().getString(JPushInterface.EXTRA_ALERT);
            initPresenter(null, null);
        }
        else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.i( "[MyReceiver] 用户点击打开了通知");

            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            JSONObject jsonObject;
            try {
                if (null != extra) {
                    jsonObject = new JSONObject(extra);
                    newsType = jsonObject.getString("newsType");
                    key = jsonObject.getString("key");
                    LogUtil.i("newType: " + newsType + "\n" + "key: " + key);
                    initPresenter(newsType, key);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtil.i( "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.i( "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtil.i( "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }



    //发送一个自定义的通知
    public void sendNotifiction(Context context, String title, String msg, String type, String newkey) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //设置相关属性
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置小图标
        if (TextUtils.isEmpty(title)) {

            builder.setContentTitle("Hi Life");

        } else {
            builder.setContentTitle(title);//标题内容
        }
        builder.setContentText(msg);//设置内容
        builder.setAutoCancel(true);//自动取消  build.setOnging(true);//常驻通知
        builder.setDefaults(Notification.DEFAULT_ALL);//设置声音 震动 呼吸灯
        //builder.setNumber(7);//设置发送多少条
        builder.setTicker("您有一条新消息");//发送通知那一栏显示的东西  没下拉时的消息
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent();
        //定义一个意图。当点击通知时要打开一个界面   第二个参数为请求编码
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //设置通知的事件
        builder.setContentIntent(pi);

        //获取系统的通知管理器,然后发送
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(NID, notification);
        //initPresenter(type, newkey);

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    LogUtil.i( "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.i( "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    private void initPresenter(String newsType, String key) {

        Map<String, Object> param = new HashMap<>();

        param.put("newsType", newsType);//如果不添则是所有的tag
        param.put("key", key);//如果不添则是所有的tag

        presenter.getJpushData(param, CardConstant.GetActivityCommon, 1);
    }

    /**
     * 极光推送的标签设置
     */
    @Override
    public void onLoadJpushSuccess(Object o, int type)
    {
        if (type == 1)
        {
            jpushNumBean = JSON.parseObject(String.valueOf(o), JpushNumBean.class);
            targetActivity = jpushNumBean.getTargetActivity();
            activityData2 = jpushNumBean.getActivityData();

            if (targetActivity.equals("103")) {//身份驗證成功
                authorBean = JSON.parseObject(String.valueOf(activityData2), AuthorBean.class);


            }else if (targetActivity.equals("105")) {
                creditCard = JSON.parseObject(String.valueOf(activityData2),CreditCard.class);

            }else if (targetActivity.equals("106")) {
                payee1 = JSON.parseObject(String.valueOf(activityData2),Payee.class);

            }else if (targetActivity.equals("107")) {

                payMethod = JSON.parseObject(String.valueOf(activityData2),PayMethod.class);

            }
            else {
                activityData = JSON.parseObject(activityData2, JpushLogicBean.class);
             String     activityDataOrder = activityData.getOrderId();
            }

                startTargetActivity(context1);

        }
    }

    @Override
    public void onLoadJpushFail(Object o, int type) {
        if (type == 1) {

        }
    }

    public void startTargetActivity (Context context) {
        //sendSelfLoginRequest();
        if (targetActivity == null && TextUtils.isEmpty(targetActivity)) {
                            Intent intentt3 = new Intent(context, MessageDetailActivity.class);
                            intentt3.putExtra("systemMsg", "系统消息");
                            intentt3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentt3);
        } else {
            if (targetActivity.equals("2001")) {//机票订单详情接口缺少总订单状态、订单金额
                Intent i = new Intent(context, PlaneTicketsDetailActivity.class);
                i.putExtra(PlaneTicketsDetailActivity.ORDER_BEAN, activityData.getOrderId() +"");
                startActivity(i);
            } else
             if (targetActivity.equals("6002")) {//旅游产品id
                Intent i = new Intent(context, TravelProductDetailActivity.class);
                i.putExtra("id", activityData.getProductId());
                startActivity(i);
            }  else
             if (targetActivity.equals("6003")) {//旅游订单页面
                 Intent i = new Intent(context, TourismTicketsDetailActivity.class);
                 OrderBean orderBean = new OrderBean();
                 orderBean.setOrderId(activityData.getOrderId());
                 i.putExtra("orderBean", orderBean);
                 startActivity(i);
             } else
             if (targetActivity.equals("9001")) {//领取红包页面
                Intent i = new Intent(context, BounsListActivity.class);
                i.putExtra("id", activityData.getOrderId());//现在没有用到
                startActivity(i);
            } else
             if (targetActivity.equals("8001")) {//礼品卡领取页面
                Intent i = new Intent(context, GiftCardRecvECardActivity.class);
//                i.putExtra("id", activityData.getOrderId());//现在没有用到
                i.putExtra("id", activityData.getOrderId());//后面修改的
                startActivity(i);
            } else
             if (targetActivity.equals("7002")) {//酒店订单详情页面
                Intent i = new Intent(context, HotelOrderDetailActivity.class);
                i.putExtra("id", activityData.getOrderId() );
                 i.putExtra("hotelType", activityData.getHotelType());
                startActivity(i);
            } else
             if (targetActivity.equals("2002")) {//特价机票  暂时不处理
//                Intent i = new Intent(context, SingleListActivity.class);
//                i.putExtra("activityData", activityData.getProductId());
//                startActivity(i);
            } else
             if (targetActivity.equals("101")) {//在线办卡页面
                Intent i = new Intent(context, CreditOnlineCardActivity.class);
               // i.putExtra("activityData", activityData.getOrderId());
                startActivity(i);
            } else
             if (targetActivity.equals("102")) {//我的提醒页面
                Intent i = new Intent(context, MyRemindActivity.class);
                //i.putExtra("activityData", activityData.getOrderId());
                startActivity(i);
            } else
             if (targetActivity.equals("100")) {//h5页面

                 Bundle bundle = new Bundle();
                 bundle.putString("url",activityData.getUrl() );
                 UiUtils.readyGoWithBundle(WebViewClientActivity.class, bundle);
            } else
             if (targetActivity.equals("7001")) {//酒店详情页面
//                 Intent intent = new Intent(context, HotelDetailActivity.class);
//                 intent.putExtra("id", activityData.getProductId());
//                 startActivity(intent);
            } else
             if (targetActivity.equals("6666")){//订单详情页面(转账、)
                 Intent intent = new Intent(context, MessageDetailActivity.class);
                 intent.putExtra("orderMsg", "订单通知");
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             } else
             if (targetActivity.equals("8888")) {//转账订单数据页面
//                 Toast.makeText(context, "转账订单数据 title:" + title + "\n转账订单数据 msg:" + msg, Toast.LENGTH_SHORT).show();
             } else
             if (targetActivity.equals("9002")) {//红包订单详情页面
                 Intent intent = new Intent(context, RedBagDetailSendActivity.class);
                 intent.putExtra("orderId", activityData.getOrderId());
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);

             } else
             if (targetActivity.equals("2222")) {//未领取的礼品卡列表页面
                 Intent intent = new Intent(context, GiftCardRecvECardActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }else
             if (targetActivity.equals("1111")) {//未领取的红包列表页面
                 Intent intent = new Intent(context, BounsListActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }else
             if (targetActivity.equals("103")) {//个人身份认证页面-成功 解析修改 1
                 //sendSelfLoginRequest();
                 Intent intent = new Intent(context, IDAuthenticationActivity.class);
                 UserManager.getInstance().getUserBean().setAuthenticationStatus("1");
                 AuthenticationInfoBean author = new AuthenticationInfoBean();
                 author.setCertNo(authorBean.getCertNo());
                 author.setRealName(authorBean.getRealName());
                 UserManager.getInstance().getUserBean().setAuthenticationInfo(author);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//IdentifySuccess
                 startActivity(intent);
             }else
             if (targetActivity.equals("104")) {//个人身份认证页面-失败  解析修改 2
                 Intent intent = new Intent(context, IDAuthenticationActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }else
             if (targetActivity.equals("105")) {//信用卡还款提醒  还款成功、失败都跳这个页面
                 Intent intent = new Intent(context, RepaymentActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 intent.putExtra("data", creditCard);
                 startActivity(intent);
             }else
             if (targetActivity.equals("106")) {//某人轉賬記錄
                 Intent intent = new Intent(context, TransRecordActivity.class);
                 intent.putExtra("payee", payee1);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }else
             if (targetActivity.equals("107")) {//提现結果
                 Intent intent = new Intent(context, CashSuccActivity.class);
                 intent.putExtra("amount", payMethod.getAmount());
                 intent.putExtra("payMethod", payMethod);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }
        }
    }

}
