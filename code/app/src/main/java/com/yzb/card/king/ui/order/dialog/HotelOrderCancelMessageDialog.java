package com.yzb.card.king.ui.order.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.PayFormIf;
import com.yzb.card.king.bean.order.ProductBaseOrderBean;
import com.yzb.card.king.util.Utils;

/**
 * 类  名：酒店订单取消说明
 * 作  者：Li Yubing
 * 日  期：2017/9/13
 * 描  述：
 */
public class HotelOrderCancelMessageDialog extends DialogFragment implements View.OnClickListener{

    private ProductBaseOrderBean item;

    private boolean flagFunction = false;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_fragment_dialog_hotel_order_cancel_dialog, container, false);
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle bundle = getArguments();

        item = (ProductBaseOrderBean) bundle.get("dataDetail");

        if(bundle.containsKey("flagFunction")){

            flagFunction = bundle.getBoolean("flagFunction");
        }

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        LinearLayout llFunction = (LinearLayout) view.findViewById(R.id.llFunction);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setVisibility(View.GONE);

        TextView tvCancelMsg = (TextView) view.findViewById(R.id.tvCancelMsg);

        if(flagFunction){

            llFunction.setVisibility(View.GONE);
        }else {

            view.findViewById(R.id.tvConfirm).setOnClickListener(this);

            view.findViewById(R.id.tvCancel).setOnClickListener(this);

        }
        int paymentType = Integer.parseInt(item.getPaymentType());

        int cancelType = Integer.parseInt(item.getCancelType());

        if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentType) {//到店付款

            String cancelDateStr = "订单取消后不可恢复，是否取消订单";

            tvCancelMsg.setText(cancelDateStr);

        } else if (PayFormIf.PAYMENT_TYPE_ONLINE == paymentType) {//在线付

            if (PayFormIf.CANCEL_TYPE_TIMELIMIT == cancelType) {//限时取消

                StringBuffer sb = new StringBuffer();
                sb.append("限时取消");

                tvTitle.setText(sb.toString());

                tvTitle.setVisibility(View.VISIBLE);

                String orderIime = item.getOrderCreateTime();

                String orderTimeStr =  Utils.toData( Utils.toTimestamp(orderIime,1),9)+"18:00";

                String cancelDateStr = "订单确认后，"+orderTimeStr+"之前可免费取消订单，将退还全部房费；之后预订人因自身原因或者其它非法原因要求取消订单，将扣收全额房费";

                tvCancelMsg.setText(cancelDateStr);

            } else if (PayFormIf.CANCEL_TYPE_PAID == cancelType) {//付费取消

                String cancelDateStr = "订单取消后不可恢复，将扣除全部担保金额"+item.getActualAmount()+"元，是否取消订单";

                tvCancelMsg.setText(cancelDateStr);
            }

        } else if (PayFormIf.PAYMENT_TYPE_DANBAO == paymentType) {//担保付

            if (PayFormIf.CANCEL_TYPE_TIMELIMIT == cancelType) {//限时取消

                StringBuffer sb = new StringBuffer();
                sb.append("限时取消");

                tvTitle.setText(sb.toString());

                tvTitle.setVisibility(View.VISIBLE);

                String orderIime = item.getOrderCreateTime();

                String orderTimeStr =  Utils.toData( Utils.toTimestamp(orderIime,1),9)+"18:00";

                String cancelDateStr = "订单确认后，"+orderTimeStr+"之前可免费取消订单，将退还全部房费；之后预订人因自身原因或者其它非法原因要求取消订单，将扣收全额房费";

                tvCancelMsg.setText(cancelDateStr);

            } else if (PayFormIf.CANCEL_TYPE_PAID == cancelType) {//付费取消

                String cancelDateStr = "订单取消后不可恢复，将扣除全部担保金额"+item.getActualAmount()+"元，是否取消订单";

                tvCancelMsg.setText(cancelDateStr);
            }

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvConfirm:
                //检查是否登录
                dismiss();
                break;
            case R.id.tvCancel:

                dismiss();

                break;

            default:
                break;
        }
    }
}
