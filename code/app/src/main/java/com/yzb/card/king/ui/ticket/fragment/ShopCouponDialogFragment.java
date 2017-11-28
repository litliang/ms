package com.yzb.card.king.ui.ticket.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.yzb.card.king.ui.ticket.view.ReceiveYhqView;
import com.yzb.card.king.ui.ticket.view.DiscountView;
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
 * 商家优惠券对话框；
 */
public class ShopCouponDialogFragment extends BaseDialogFragment implements DiscountView, ReceiveYhqView
{
    private static ShopCouponDialogFragment dialogFragment;
    private YhqDialogAdapter adapter = null;
    private List<GoodActivityBean> yhqBeans;
    private ShopCouponPresenter discountPresenter;
    private ReceiveYhqPresenter receiveYhqPresenter;
    private HbDialogParam hbDialogParam;

    public static ShopCouponDialogFragment getInstance(String arg1, String arg2)
    {
        if (dialogFragment == null)
        {
            dialogFragment = new ShopCouponDialogFragment();
        }
        return dialogFragment;
    }

    public ShopCouponDialogFragment setData(HbDialogParam bean)
    {
        this.hbDialogParam = bean;
        return this;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_ticket_yhq_dialog;
    }

    @Override
    protected void initView(View view)
    {
        discountPresenter = new ShopCouponPresenter(this);
        receiveYhqPresenter = new ReceiveYhqPresenter(this);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter = new YhqDialogAdapter(getActivity());
        listView.setAdapter(adapter);
        getData();
    }

    @Override
    protected int getDialogHeight()
    {
        return CommonUtil.getScreenHeight(getActivity()) * 2 / 5;
    }

    /**
     * 获取优惠券列表；
     */
    private void getData()
    {
        if (hbDialogParam != null)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("platformType", "" + hbDialogParam.getPlatformType());//活动平台（1平台商家活动；2平台活动；3商家活动；4银行活动）
            params.put("shopIds", hbDialogParam.getShopIds());//商家id
            params.put("serviceName", CardConstant.APP_CANRECEIVECOUPON_LIST);
            discountPresenter.loadData(params);
        }
    }

    @Override
    public void onGetDiscountFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    @Override
    public void onGetDiscountSucess(String req_flag, Object data)
    {
        List<ShopGoodsActivityBean> sbs = (List<ShopGoodsActivityBean>) data;
        if (sbs != null && sbs.size() > 0)
        {
            yhqBeans = filterYhq(sbs.get(0).getGoodActivityBeans());
            if (yhqBeans.size() == 0)
            {
                toastCustom("无信息");
            } else
            {
                adapter.clearAll();
                adapter.appendALL(yhqBeans);
            }
        }
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
        for (int i = 0; i < data.size(); i++)
        {
            //未领取；
            if ("0".equals(data.get(i).getRecieveStatus()))
            {
                dataTemp.add(data.get(i));
            }
        }
        return dataTemp;
    }

    private void deleSpecificYhq(String actId)
    {
        if (!TextUtils.isEmpty(actId))
        {
            List<GoodActivityBean> gbList = adapter.getData();
            for (int i = 0; i < gbList.size(); i++)
            {
                if (actId.equals(gbList.get(i).getActId()))
                {
                    //设置为已领取；
                    adapter.removeBean(gbList.get(i));
                    break;
                }
            }
        }
    }

    @Override
    public void onReceiveYhqSucess(String id)
    {
        toastCustom(R.string.ticket_receive_suc);
        deleSpecificYhq(id);
    }

    @Override
    public void onReceiveYhqFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    /**
     * 优惠券适配器；
     */
    private class YhqDialogAdapter extends BaseListAdapter<GoodActivityBean>
    {
        public YhqDialogAdapter(Context context)
        {
            super(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.listview_item_yhq_new, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final GoodActivityBean yhqBean = getItem(position);

            viewHolder.tvAmount.setText(Utils.subZeroAndDot(yhqBean.getCutAmount() + ""));

            viewHolder.tvFullCut.setText(yhqBean.getGrantCondition() == 1 ? "无门槛使用" :
                    "每满" + Utils.subZeroAndDot(yhqBean.getFullAmount() + "") + "元减" + Utils.subZeroAndDot(yhqBean.getCutAmount() + "") + "元");

            //使用期限为空时不显示；
            if (!TextUtils.isEmpty(yhqBean.getStartTime()) && !TextUtils.isEmpty(yhqBean.getEndTime()))
            {
                viewHolder.tvDate.setVisibility(View.VISIBLE);
                viewHolder.tvDate.setText("使用期限 " + yhqBean.getStartTime() + "~" + yhqBean.getEndTime());
            } else
            {
                viewHolder.tvDate.setVisibility(View.INVISIBLE);
            }
            viewHolder.tvGet.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    recvCoupon(yhqBean.getActId());
                }
            });
            return viewHolder.root;
        }

        /**
         * 领取优惠券；
         *
         * @param actId
         */
        private void recvCoupon(String actId)
        {
            if (!isLogin())
            {
                startActivity(new Intent(getContext(), LoginActivity.class));
                return;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("actId", actId);
            params.put("orderId", actId);
            params.put("serviceName", CardConstant.card_app_receivecoupon);
            receiveYhqPresenter.loadData(params);
        }

        public class ViewHolder
        {
            public final TextView tvAmount;
            public final TextView tvFullCut;
            public final TextView tvDate;
            public final TextView tvGet;
            public final View root;

            public ViewHolder(View root)
            {
                this.root = root;
                tvAmount = (TextView) root.findViewById(R.id.tvAmount);
                tvFullCut = (TextView) root.findViewById(R.id.tvFullCut);
                tvDate = (TextView) root.findViewById(R.id.tvDate);
                tvGet = (TextView) root.findViewById(R.id.tvGet);
            }
        }
    }
}
