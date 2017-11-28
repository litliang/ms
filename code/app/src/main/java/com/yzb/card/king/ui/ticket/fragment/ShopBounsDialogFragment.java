package com.yzb.card.king.ui.ticket.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HbDialogParam;
import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.ticket.presenter.ReceiveYhqPresenter;
import com.yzb.card.king.ui.ticket.presenter.ShopCouponPresenter;
import com.yzb.card.king.ui.ticket.view.DiscountView;
import com.yzb.card.king.ui.ticket.view.ReceiveYhqView;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016/10/12.
 * 商家红包列表对话框；
 */
public class ShopBounsDialogFragment extends BaseDialogFragment implements ReceiveYhqView, DiscountView, View.OnClickListener
{
    private static ShopBounsDialogFragment dialogFragment;
    private HbDialogAdapter adapter = null;
    private List<GoodActivityBean> hbBeans;
    private ShopCouponPresenter discountPresenter;
    private HbDialogParam bean;
    private ReceiveYhqPresenter receiveHbPresenter;

    public static ShopBounsDialogFragment getInstance(String arg1, String arg2)
    {
        if (dialogFragment == null)
        {
            dialogFragment = new ShopBounsDialogFragment();
        }
        return dialogFragment;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_hb_dialog;
    }

    @Override
    protected int getDialogHeight()
    {
        return CommonUtil.getScreenHeight(getActivity()) / 2;
    }

    protected void initView(View view)
    {
        discountPresenter = new ShopCouponPresenter(this);
        receiveHbPresenter = new ReceiveYhqPresenter(this);
        ListView listView = (ListView) view.findViewById(R.id.listView);

        view.findViewById(R.id.ivClose).setOnClickListener(this);
        adapter = new HbDialogAdapter();
        listView.setAdapter(adapter);
        getData();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void getData()
    {
        if (bean == null)
        {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("platformType", "" + bean.getPlatformType());
        params.put("shopIds", bean.getShopIds());
        params.put("serviceName", CardConstant.card_canreceivebonuslist);
        discountPresenter.loadData(params);
    }

    @Override
    public void onGetDiscountSucess(String req_flag, Object data)
    {
        List<ShopGoodsActivityBean> sbs = (List<ShopGoodsActivityBean>) data;
        if (sbs != null && sbs.size() > 0)
        {
            this.hbBeans = filterYhq(sbs.get(0).getGoodActivityBeans());
            if (hbBeans.size() == 0)
            {
                toastCustom("无信息");
            } else
            {
                adapter.clearAll();
                adapter.appendALL(hbBeans);
            }
        }
    }

    @Override
    public void onGetDiscountFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    public ShopBounsDialogFragment setData(HbDialogParam bean)
    {
        this.bean = bean;
        return this;
    }

    /**
     * 过滤已经领取的优惠券；
     *
     * @param data 包括已领取和未领取的优惠券；
     * @return
     */
    private List<GoodActivityBean> filterYhq(List<GoodActivityBean> data)
    {
        List<GoodActivityBean> dataTemp = new ArrayList<>();
        if (data != null)
        {
            for (int i = 0; i < data.size(); i++)
            {
                //未领取；
                if ("0".equals(data.get(i).getRecieveStatus()))
                {
                    dataTemp.add(data.get(i));
                }
            }
        }
        return dataTemp;
    }

    @Override
    public void onReceiveYhqSucess(String orderId)
    {
        toastCustom(R.string.ticket_receive_suc);
        adapter.deleteItemById(orderId);
    }

    @Override
    public void onReceiveYhqFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    /**
     * 领取红包；
     *
     * @param orderId 红包id；
     */
    private void recvPacket(String orderId)
    {
        if (!isLogin())
        {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("account", getUserBean().getAccount()); //红包id
        params.put("orderId", orderId); //红包id
        params.put("serviceName", CardConstant.card_app_receivebonus);
        receiveHbPresenter.loadData(params);
    }

    @Override
    public void onClick(View v)
    {
        dismiss();
    }

    private class HbDialogAdapter extends BaseListAdapter<GoodActivityBean>
    {

        public HbDialogAdapter()
        {
            super(getActivity());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.listview_item_shop_hb, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final GoodActivityBean hbBean = getItem(position);
            viewHolder.tvAmount.setText(Utils.subZeroAndDot(hbBean.getAmount() + "") + "元");

            //2平台活动；3商家活动
            viewHolder.tvCouponType.setText("2".equals(hbBean.getPlatformType()) ? "平台红包" : "商家红包");

            //使用条件；
            viewHolder.tvUseIntro.setText(hbBean.getShopLimit());

            viewHolder.tvUse.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    recvPacket(hbBean.getOrderId());
                }
            });
            return viewHolder.root;
        }

        /**
         * 删除item；
         *
         * @param orderId
         */
        public void deleteItemById(String orderId)
        {
            if (!TextUtils.isEmpty(orderId))
            {
                for (int i = 0; i < mList.size(); i++)
                {
                    if (orderId.equals(mList.get(i).getOrderId()))
                    {
                        mList.remove(i);
                        notifyDataSetChanged();
                        break;
                    }
                }
            }
        }

        public class ViewHolder
        {
            public final TextView tvAmount; //红包金额；
            public final TextView tvUseIntro; //使用条件；
            public final TextView tvsyqx; //使用期限；
            public final TextView tvCouponType; //立即领取；
            public final TextView tvUse; //红包类型；
            public final View root;

            public ViewHolder(View root)
            {
                tvAmount = (TextView) root.findViewById(R.id.tvAmount);
                tvUse = (TextView) root.findViewById(R.id.tvUse);
                tvCouponType = (TextView) root.findViewById(R.id.tvCouponType);
                tvUseIntro = (TextView) root.findViewById(R.id.tvUseIntro);
                tvsyqx = (TextView) root.findViewById(R.id.tv_syqx);
                this.root = root;
            }
        }
    }
}
