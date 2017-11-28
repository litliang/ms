package com.yzb.card.king.ui.ticket.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.BouncQueryBean;
import com.yzb.card.king.bean.ticket.ReasonForChangeBean;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.popup.GetPicPopup;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.order.OrderManageActivity;
import com.yzb.card.king.ui.order.ReasonForChangeActivity;
import com.yzb.card.king.ui.order.adapter.RefundTicketAdapter;
import com.yzb.card.king.ui.other.controller.SelectPicController;
import com.yzb.card.king.ui.ticket.adapter.FilterBaseOnClickListener;
import com.yzb.card.king.ui.ticket.presenter.BouncQueryPresenter;
import com.yzb.card.king.ui.ticket.presenter.BouncRefundPrensenter;
import com.yzb.card.king.ui.ticket.presenter.TicketOrderDetailPresenter;
import com.yzb.card.king.ui.ticket.view.BouncQueryView;
import com.yzb.card.king.ui.ticket.view.BouncRefundView;
import com.yzb.card.king.ui.ticket.view.TicketOrderDetailView;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机票申请退票
 */
@ContentView(R.layout.activity_refund_ticket)
public class RefundTicketActivity extends BaseActivity implements FilterBaseOnClickListener, TicketOrderDetailView,
        BouncQueryView, BouncRefundView
{
    public static final int ORDER = 1;
    public static final int GUESTER = 2;
    private static final int REQ_CODE_GET_CONTACT = 0x006;// 添加手机联系人
    private static final int REQ_GET_REFUND_REASON = 0x005;// 获取退款原因；
    @ViewInject(R.id.recyclerTicket)
    private WholeRecyclerView mTicketRecyclerView;
    @ViewInject(R.id.yxtp_tx)
    private TextView yxtp_tx;
    @ViewInject(R.id.tv_rason)
    private TextView tvReason;
    @ViewInject(R.id.wallet_checkbox)
    private ImageView wallet;
    @ViewInject(R.id.mobile)
    private EditText mobile;
    @ViewInject(R.id.etName)
    private EditText etName;
    @ViewInject(R.id.panelReason)
    private View panelReason;
    @ViewInject(R.id.panelUploadImg)
    private View panelUploadImg;
    @ViewInject(R.id.etReason)
    private EditText etReason;
    @ViewInject(R.id.ivUploadImg)
    private ImageView ivUploadImg;
    @ViewInject(R.id.tvUpload)
    private TextView tvUpload;
    @ViewInject(R.id.original_checkbox) //原路退回；
    private ImageView ivOriginal;
    private String orderId;  //订单id；
    private String payMethod; //付款方式（1钱包余额；2信用卡；3储蓄卡；）
    private List<TicketOrderDetBean.OrderInfoBean> mTicketList = new ArrayList<>();
    private RefundTicketAdapter adapter;
    private TicketOrderDetailPresenter ticketOrderDetPresenter;
    private TicketOrderDetBean orderDetailBean; //详情；
    private BouncQueryPresenter bouncQueryPresenter; //退票查询；
    private BouncRefundPrensenter bouncRefundPresenter; //退票确认；
    private ReasonForChangeBean reasonForChangeBean;//退款原因；

    private SelectPicController picController;
    private Bitmap mBitmap;
    private Connector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        receiveIntent();
        initView();
        initPicController();
        ticketOrderDetPresenter = new TicketOrderDetailPresenter(this);
        bouncQueryPresenter = new BouncQueryPresenter(this);
        bouncRefundPresenter = new BouncRefundPrensenter(this);
        initData();
    }

    private void initPicController()
    {
        picController = new SelectPicController(this);
        picController.setOnGetPicListener(new SelectPicController.OnGetPicListener()
        {
            @Override
            public void onGetPic(Bitmap bitmap)
            {
                mBitmap = bitmap;
                tvUpload.setVisibility(View.GONE);
                ivUploadImg.setVisibility(View.VISIBLE);
                ivUploadImg.setImageDrawable(new BitmapDrawable(bitmap));
            }
        });
    }

    /**
     * 接收从订单列表传过来的值
     */
    private void receiveIntent()
    {
        orderId = getIntent().getStringExtra("orderId");
        payMethod = getIntent().getStringExtra("payMethod");
        LogUtil.i("orderId=" + orderId + ",payMethod=" + payMethod);
    }

    private void initView()
    {
        adapter = new RefundTicketAdapter(mTicketList, this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mTicketRecyclerView.setLayoutManager(lm);
        mTicketRecyclerView.setAdapter(adapter);

        if (AppConstant.refund_type_wallet.equals(payMethod))
        {
            wallet.setSelected(true);
        } else if (AppConstant.refund_type_card1.equals(payMethod) || AppConstant.refund_type_card0.equals(payMethod))
        {
            ivOriginal.setSelected(true);
        }

        SpannableString sb = new SpannableString(getString(R.string.refund_content));
        sb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_3a6e98)),
                sb.length() - 4, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        yxtp_tx.setText(sb);

        panelUploadImg.setVisibility(View.GONE);
        panelReason.setVisibility(View.GONE);
    }

    /**
     * 获取数据；
     */
    private void initData()
    {
        showNoCancelPDialog(R.string.loading);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        ticketOrderDetPresenter.loadData(true, params);
    }

    @Override
    public void onGetOrderDetailSucess(TicketOrderDetBean orderDetailBean)
    {
        closePDialog();
        this.orderDetailBean = orderDetailBean;
        if (orderDetailBean != null)
        {
            adapter.clearAll();
            adapter.setFlightType(orderDetailBean.getFlightType());
            adapter.appendALL(orderDetailBean.getOrderInfo());
        }
    }

    @Override
    public void onGetOrderDetailFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 机票查询；
     */
    private void queryTicketPrice()
    {
        showNoCancelPDialog(R.string.loading);
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("orderNoList", getOrderNoList()); //订单号
        bouncQueryPresenter.loadData(true, argsMap);
    }

    /**
     * 获取订单号列表；
     */
    private List<Map<String, Object>> getOrderNoList()
    {
        List<Map<String, Object>> mList = new ArrayList<>();
        List<TicketOrderDetBean.OrderInfoBean> orders = adapter.getSelectTicket();
        // 添加往返 组合套餐；
        if (adapter.isCombineProducts() && orders.size() == 2)
        {
            addRoundTicket(mList, orders.get(0));
        } else
        {
            addTicket(mList, orders);
        }
        return mList;
    }

    /**
     * 添加往返票()；
     */
    private void addRoundTicket(List<Map<String, Object>> mList, TicketOrderDetBean.OrderInfoBean order)
    {
        if (order != null)
        {
            Map<String, Object> args = new HashMap<>();
            args.put("orderNo", order.getOrderNo());
            args.put("userList", getUserList(order.getTicketsList())); //证件信息
            args.put("applyType", reasonForChangeBean.getApplyType()); //退票原因类型；

            args.put("reason", panelReason.getVisibility() == View.VISIBLE ?
                    etReason.getText().toString().trim() : reasonForChangeBean.getContent()); //退票说明
            args.put("appName", etName.getText().toString().trim());
            args.put("appContact", mobile.getText().toString().trim());
            mList.add(args);
        }
    }

    /**
     * 添加单程，往返非组合套餐，或多程票；
     */
    private void addTicket(List<Map<String, Object>> mList, List<TicketOrderDetBean.OrderInfoBean> orders)
    {
        if (mList != null && orders != null)
        {
            for (int i = 0; i < orders.size(); i++)
            {
                addRoundTicket(mList, orders.get(i));
            }
        }
    }

    /**
     * 获取证件信息
     *
     * @param ticketsList
     */
    private List<Map<String, String>> getUserList(List<TicketOrderDetBean.TicketsListBean> ticketsList)
    {
        List<Map<String, String>> args = new ArrayList<>();
        if (ticketsList != null)
        {
            for (int i = 0; i < ticketsList.size(); i++)
            {
                //添加选中的；
                if (ticketsList.get(i).isSelect())
                {
                    Map<String, String> map = new HashMap<>();
                    map.put("paxIdType", ticketsList.get(i).getIdType());
                    map.put("paxIdNo", ticketsList.get(i).getGuestIDCard());
                    args.add(map);
                }
            }
        }
        return args;
    }

    @Override
    public void onBouncQueryFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    public void onBouncQuerySucess(final BouncQueryBean bouncQueryBean)
    {
        closePDialog();
        if (bouncQueryBean != null)
        {
            StringBuilder sb = new StringBuilder();

            float refundAmount = 0;
            float refundFee = 0;
            refundAmount += bouncQueryBean.getRefundAmount();
            refundFee += bouncQueryBean.getRefundFee();

            if(refundAmount == 0f && refundFee==0f){

                sb.append(bouncQueryBean.getMessage());

            }else{
                sb.append("退票金额:" + Utils.subZeroAndDot(refundAmount + "") + "元\n");
                sb.append("退票手续费:" + Utils.subZeroAndDot(refundFee + "") + "元");
            }

            new AlertDialog.Builder(this).
                    setTitle(R.string.dialog_title).
                    setMessage(sb.toString()).setNegativeButton(R.string.cancel, null).
                    setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            exeRefund(bouncQueryBean);
                        }
                    }).show();
        }
    }

    @Override
    public void onBouncRefundSucess()
    {
        //刷新订单页数据；
        Intent intent = new Intent(OrderManageActivity.ORDERMANAGER_ACTION);
        sendBroadcast(intent);

        closePDialog();
        toastCustom("退票成功");
        finish();
    }

    @Override
    public void onBouncRefundFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 退票；
     *
     * @param bouncQueryBean
     */

    private void exeRefund(BouncQueryBean bouncQueryBean)
    {
        showNoCancelPDialog(R.string.loading);
        Map<String, Object> argsMap = new HashMap<>();

        argsMap.put("reason", panelReason.getVisibility() == View.VISIBLE ?
                etReason.getText().toString().trim() : reasonForChangeBean.getContent()); //退票说明

        argsMap.put("appName", etName.getText().toString().trim());
        argsMap.put("appContact", mobile.getText().toString().trim());
        argsMap.put("refundList", bouncQueryBean.getRefundList()); //退票信息
        argsMap.put("refundAmount", bouncQueryBean.getRefundAmount() + ""); //退票金额（退还给用户的金额）
        argsMap.put("refundFee", bouncQueryBean.getRefundFee() + ""); //退票手续费（收取用户的手续费）
        argsMap.put("orderId", orderId);
        argsMap.put("applyType", reasonForChangeBean.getApplyType()); //退票原因类型；

        if ("286".equals(reasonForChangeBean.getApplyType()))
        {
            argsMap.put("attachments", getAttachMap()); //退票的附件信息；
        }


        LogUtil.i("退票入参：" + argsMap);
        bouncRefundPresenter.loadData(true, argsMap);
    }


    /**
     * 获取退票的附件信息；支持多个，目前暂传1个；
     */
    private List<Map<String, Object>> getAttachMap()
    {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> attachMap = new HashMap<>();
        attachMap.put("id", 1);
        attachMap.put("name", "因病证明");
        attachMap.put("content", BitmapUtil.getBase64(mBitmap).getBytes());
        list.add(attachMap);
        return list;
    }

    @Event({R.id.back, R.id.wallet, R.id.original, R.id.btn_ok, R.id.add, R.id.btn_refund, R.id.llUploadImg})
    private void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.add:
                ContactUtil.startContactsActivity(this, REQ_CODE_GET_CONTACT);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.wallet:  //钱包
                wallet.setSelected(true);
                ivOriginal.setSelected(false);
                break;
            case R.id.original:  //原路返回
                wallet.setSelected(false);
                ivOriginal.setSelected(true);
                break;
            case R.id.btn_ok: //提交申请
                if (isInputRight())
                {
                    queryTicketPrice();
                }
                break;
            case R.id.btn_refund:  //退票原因
                Intent reason = new Intent(this, ReasonForChangeActivity.class);
                reason.putExtra("reasonType", "1");
                reason.putExtra("reason", tvReason.getText().toString());
                startActivityForResult(reason, REQ_GET_REFUND_REASON);
                break;
            case R.id.llUploadImg: //上传图片；
                getLocalImg();
                break;
        }
    }

    private GetPicPopup getPicPopup;

    private void getLocalImg()
    {
        if (getPicPopup == null)
        {
            getPicPopup = new GetPicPopup(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    switch (v.getId())
                    {
                        case R.id.tvCamera:
                            picController.getPicFormCamera();
                            break;
                        case R.id.tvAlbum:
                            picController.getPicFromGallery();
                            break;
                        case R.id.tvCancel:
                            break;
                    }
                    getPicPopup.dismiss();
                }
            });
        }
        getPicPopup.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }

    /**
     * 内容是否正确；
     */
    private boolean isInputRight()
    {
        if (orderDetailBean == null)
        {
            toastCustom("详情加载失败，请稍后重试");
        }

        if (!adapter.hasAvailablePassengers())
        {
            toastCustom(R.string.ticket_no_available_passenger);
            return false;
        }

        //是否选择退票人；
        List<TicketOrderDetBean.TicketsListBean> tickets = adapter.getSelectPassengers();

        if (tickets == null || tickets.size() == 0)
        {
            toastCustom(R.string.ticket_chose_plane);
            return false;
        }

        if (!wallet.isSelected() && !ivOriginal.isSelected())
        {
            toastCustom("请选择退款方式");
            return false;
        }

        if (reasonForChangeBean == null)
        {
            toastCustom(R.string.ticke_chose_yuanyin);
            return false;
        }

        //其它退票原因，需手动输入退票原因；
        if (reasonForChangeBean.getId() == 8)
        {
            //输入退票原因；
            if (isEmpty(etReason.getText().toString().trim()))
            {
                toastCustom(R.string.ticke_input_yuanyin);
                return false;
            }
        } else if (reasonForChangeBean.getId() == 6)
        {
            //原因为其它时，显示退票原因输入框；
            if (mBitmap == null)
            {
                toastCustom(R.string.ticke_upload_img);
                return false;
            }
        }
        if (isEmpty(etName.getText().toString().trim()))
        {
            toastCustom(R.string.hint_name);
            return false;
        }
        if (!RegexUtil.validPhoneNum(mobile.getText().toString().trim()))
        {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        picController.onActivityResult(requestCode, resultCode, data);

        if (data == null || resultCode != Activity.RESULT_OK) return;

        switch (requestCode)
        {
            case REQ_GET_REFUND_REASON:
                Serializable ser = data.getSerializableExtra("reasonBean");
                if (ser != null)
                {
                    reasonForChangeBean = (ReasonForChangeBean) ser;
                    tvReason.setText(reasonForChangeBean.getContent());

                    //原因为其它时，显示退票原因输入框；
                    if (reasonForChangeBean.getId() == 6)
                    {
                        panelUploadImg.setVisibility(View.VISIBLE);
                        panelReason.setVisibility(View.GONE);
                    } else if (reasonForChangeBean.getId() == 8)
                    {
                        panelUploadImg.setVisibility(View.GONE);
                        panelReason.setVisibility(View.VISIBLE);
                    } else
                    {
                        panelUploadImg.setVisibility(View.GONE);
                        panelReason.setVisibility(View.GONE);
                    }
                }
                break;
            case REQ_CODE_GET_CONTACT://联系人信息
                Connector connector = new Connector();
                ContactUtil.getConnector(this, data, connector);
                etName.setText(connector.nickName);
                mobile.setText(connector.mobile);
                break;
        }
    }

    @Override
    public void onClick(int postition, int type)
    {
        switch (type)
        {
            case RefundTicketActivity.GUESTER://乘客；
                break;
            case RefundTicketActivity.ORDER:// 机票
                mTicketRecyclerView.getAdapter().notifyItemChanged(postition);
                break;
        }
    }
}
