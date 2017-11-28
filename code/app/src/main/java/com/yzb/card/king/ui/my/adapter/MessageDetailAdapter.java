package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.user.AuthenticationInfoBean;
import com.yzb.card.king.jpush.bean.JpushLogicBean;
import com.yzb.card.king.jpush.bean.JpushNumBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.activity.IDAuthenticationActivity;
import com.yzb.card.king.ui.appwidget.appdialog.MessageDelDialog;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuView;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.CreditOnlineCardActivity;
import com.yzb.card.king.ui.credit.activity.RepaymentActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.integral.MyRemindActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.bonus.activity.BounsListActivity;
import com.yzb.card.king.ui.my.activity.CashSuccActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardRecvECardActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardUseDetailActivity;
import com.yzb.card.king.ui.my.activity.MessageDetailActivity;
import com.yzb.card.king.ui.bonus.activity.RedBagDetailSendActivity;
import com.yzb.card.king.ui.my.activity.TransRecordActivity;
import com.yzb.card.king.ui.my.bean.AuthorBean;
import com.yzb.card.king.ui.my.bean.MessageDetailBean;
import com.yzb.card.king.ui.my.bean.MessageStatusBean;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.presenter.JpushPresenter;
import com.yzb.card.king.ui.my.presenter.MessagePresenter;
import com.yzb.card.king.ui.my.view.JpushView;
import com.yzb.card.king.ui.order.HotelOrderDetailActivity;
import com.yzb.card.king.ui.order.PlaneTicketsDetailActivity;
import com.yzb.card.king.ui.order.TourismTicketsDetailActivity;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzb.card.king.util.UiUtils.startActivity;

/**
 * 类 名： 活动通知的适配器
 * 作 者： gaolei
 * 日 期：2016年12月8日
 * 描 述：活动通知适配
 */
public class MessageDetailAdapter extends ItemMenuAdapter implements BaseViewLayerInterface, JpushView {

    private Context context;

    private List<MessageDetailBean.SubNewsMapListBean> list;

    private View.OnClickListener onClickListener;

    private static int flag = 1;

    private MessagePresenter presenter;

    private MessageStatusBean bean;

    private JpushPresenter presenter2;

    private JpushNumBean jpushNumBean;

    private String targetActivity;

    private String activityData2;

    private AuthorBean authorBean;

    private CreditCard creditCard;

    private JpushLogicBean activityData;

    private String activityDataOrder;

    private Payee payee1;

    private PayMethod payMethod;

    public MessageDetailAdapter(Context context, List<MessageDetailBean.SubNewsMapListBean> list, View.OnClickListener onClickListener) {

        this.context = context;

        this.list = list;

        this.onClickListener = onClickListener;

        presenter = new MessagePresenter(this);

        presenter2 = new JpushPresenter(this);

    }

    @Override
    public void onDeleItem(int pos) {

        list.remove(pos);
        Intent intent = new Intent("com.gaolei.test");
        intent.putExtra("sendmessage", 1);
        intent.putExtra("delone", pos);
        context.sendBroadcast(intent);
        notifyDataSetChanged();

        //通知服务器删除数据
        Map<String, Object> param = new HashMap<>();
        param.put("newsId", list.get(pos).getNewsId());
        param.put("sturt", "0");//0：删除，2：已阅读
        param.put("materialId", list.get(pos).getMaterialId());//推送记录id
        presenter.getMessageData(param, CardConstant.UpdateSubNewsSturt, 3);

    }

    @Override
    public ItemMenuView getItemView(final int position, ItemMenuView convertView, ViewGroup parent) {

        final MessageDetailViewHodler vh;
        if (convertView == null) {
            convertView = (ItemMenuView) LayoutInflater.from(context).inflate
                    (R.layout.item_message_activity_detail, parent, false);
            vh = new MessageDetailViewHodler();
            convertView.setTag(vh);
        } else {
            vh = (MessageDetailViewHodler) convertView.getTag();
        }

        vh.dateTxt = (TextView) convertView.findViewById(R.id.message_detail_date_txt);
        vh.titleTxt = (TextView) convertView.findViewById(R.id.message_detail_title_txt);
        vh.descTxt = (TextView) convertView.findViewById(R.id.message_detail_desc_txt);
        vh.clickTxt = (TextView) convertView.findViewById(R.id.message_detail_click_txt);
        vh.delImg = (ImageView) convertView.findViewById(R.id.message_detail_delete_img);
        vh.newTxt = (TextView) convertView.findViewById(R.id.message_detail_new_txt);
        vh.layout = (LinearLayout) convertView.findViewById(R.id.message_detail_layout);


        String tradeTime = list.get(position).getOptime();
        String[] split = tradeTime.split(" ");
        String time = split[1];
        String[] split2 = time.split(":");
        vh.dateTxt.setText(split[0]+" "+split2[0] + ":" + split2[1]);
        vh.titleTxt.setText("" + list.get(position).getNewsTitle());
        vh.descTxt.setText("" + list.get(position).getNewsCommon());
        if (list.get(position).getTaskSturt().equals("1")) {
            vh.newTxt.setVisibility(View.VISIBLE);
        } else {
            vh.newTxt.setVisibility(View.INVISIBLE);
        }


        final ItemMenuView itemMenuView = convertView;
        vh.clickTxt.setTag(position);
        vh.clickTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
                vh.newTxt.setVisibility(View.GONE);


                initPresenter(list.get(position).getTaskType(), list.get(position).getKey());
                Log.d("gl","type" + list.get(position).getTaskType() + "\n" +"key" +list.get(position).getKey());

//                Intent intent = new Intent(context, MessageWebViewActivity.class);
//                intent.putExtra("data","http://www.baidu.com");
//                context.startActivity(intent);
                //这里调用服务器请求,传入已读的状态,下次再点击开始的时候从服务器得到数据,然后判断是否读取,然后更新未读数量

                Map<String, Object> param = new HashMap<>();
                param.put("newsId", list.get(position).getNewsId());
                param.put("sturt", "2");//0：删除，2：已阅读
                param.put("materialId", list.get(position).getMaterialId());//推送记录id
                presenter.getMessageData(param, CardConstant.UpdateSubNewsSturt, 1);

                sendCusBroadCast(context, flag);
                flag++;
            }
        });

        //点击删除的弹出dialog
        vh.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MessageDelDialog delDialog = new MessageDelDialog(context);

                delDialog.setYesOnclickListener(new MessageDelDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick(String str) {
                        itemMenuView.hideMenu();
                        deleteItem(position, itemMenuView);
                        delDialog.dismiss();
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });

                delDialog.setNoOnclickListener(new MessageDelDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick(String dtr) {
                        itemMenuView.hideMenu();
                        delDialog.dismiss();
                        Toast.makeText(context, "您取消了操作", Toast.LENGTH_SHORT).show();
                    }
                });
                delDialog.show();
            }
        });

        //总体布局的监听
        vh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public void deleteItem(int pos, ItemMenuView view) {
        super.deleteItem(pos, view);
    }

    @Override
    public void onLoadJpushSuccess(Object o, int type) {
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
                activityDataOrder = activityData.getOrderId();
            }

            startTargetActivity(context);

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
                i.putExtra("id", activityData.getCodeNo());//后面修改的
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
//                Intent intent = new Intent(context, HotelDetailActivity.class);
//                intent.putExtra("id", activityData.getProductId());
//                startActivity(intent);
            } else
            if (targetActivity.equals("6666")){//订单详情页面(转账、)
                Intent i = new Intent(context, GiftCardUseDetailActivity.class);
                i.putExtra("orderId", activityData.getOrderId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
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
                intent.putExtra("amount", payMethod.getAccount());
                intent.putExtra("payMethod", payMethod);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    private void initPresenter(String newsType, String key) {

        Map<String, Object> param = new HashMap<>();

        param.put("newsType", newsType);
        param.put("key", key);

        presenter2.getJpushData(param, CardConstant.GetActivityCommon, 1);
    }


    @Override
    public void onLoadJpushFail(Object o, int type) {

    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 3) {
            bean = JSON.parseObject(String.valueOf(o), MessageStatusBean.class);
            bean.getSturt();
            //Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        }

        if (type == 1) {
//            Toast.makeText(context, "已阅", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }

    public static class MessageDetailViewHodler {
        private TextView dateTxt;
        private TextView titleTxt;
        private TextView descTxt;
        private TextView clickTxt;
        private ImageView delImg;
        private TextView newTxt;
        private LinearLayout layout;
    }

    //发送广播刷新页面
    private void sendCusBroadCast(Context context, int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.setAction("com.message.number");
        context.sendBroadcast(intent);
    }
}
