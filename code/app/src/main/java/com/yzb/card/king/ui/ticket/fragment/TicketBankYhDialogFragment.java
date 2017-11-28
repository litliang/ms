package com.yzb.card.king.ui.ticket.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.interfaces.DiscountListener;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.ticket.presenter.DiscountPresenter;
import com.yzb.card.king.ui.ticket.view.DiscountView;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016/10/13.
 * 机票代理商银行优惠对话框；
 */
public class TicketBankYhDialogFragment extends BaseDialogFragment implements DiscountView
{
    private static TicketBankYhDialogFragment dialogFragment;
    private List<GoodActivityBean> bankInfos;
    private DiscountPresenter discountPresenter;
    private ListView listView;
    private String goodsIds;
    private String shopIds;

    public static TicketBankYhDialogFragment getInstance(String arg1, String arg2)
    {
        if (dialogFragment == null)
        {
            dialogFragment = new TicketBankYhDialogFragment();
        }
        return dialogFragment;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.filter_dialog;
    }

    @Override
    protected void initView(View view)
    {
        discountPresenter = new DiscountPresenter(this);
        listView = (ListView) view.findViewById(R.id.list);
        getData();
    }

    @Override
    protected int getDialogHeight()
    {
        return CommonUtil.getScreenHeight(getActivity()) * 2 / 5;
    }

    /**
     * 获取银行优惠列表；
     */
    private void getData()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("platformType", "" + DiscountListener.platform_type_bank);//活动平台（1平台商家活动；2平台活动；3商家活动；4银行活动）
        params.put("activityItem", "" + DiscountListener.type_bank_all);//活动项（当活动平台为123时，0所有；1优惠券；2红包；3折扣；4满减；5积分；6促销；当活动平台为银行时，0所有；1银行积分；2银行折扣；）
        params.put("industryIds", AppConstant.ticket_id);//行业分类(多个使用英文逗号分割)
        params.put("shopIds", shopIds);//商家id(多个使用英文逗号分割)  此处为代理商的id
        params.put("goodsIds", goodsIds);//商品id(多个使用英文逗号分割) 此处为航班的id；
        params.put("flag", "" + DiscountListener.req_platform_type_bank + "" + DiscountListener.req_type_bank_all);
        discountPresenter.loadData(params);
    }

    @Override
    public void onGetDiscountSucess(String req_flag, Object data)
    {
        if (data != null)
        {
            List<ShopGoodsActivityBean> sbs = (List<ShopGoodsActivityBean>) data;
            if (sbs != null && sbs.size() > 0)
            {
                this.bankInfos = sbs.get(0).getGoodActivityBeans();
            }
        }
        BankYhDialogAdapter adapter = new BankYhDialogAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void onGetDiscountFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    /**
     * 设置数据；
     *
     * @return
     */
    public TicketBankYhDialogFragment setData(String shopIds, String goodsIds)
    {
        this.shopIds = shopIds;
        this.goodsIds = goodsIds;
        return this;
    }

    /**
     * 银行优惠适配器；
     */
    private class BankYhDialogAdapter extends BaseAdapter
    {
        private LayoutInflater inflater;

        public BankYhDialogAdapter()
        {
            inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount()
        {
            return bankInfos == null ? 0 : bankInfos.size();
        }

        @Override
        public GoodActivityBean getItem(int position)
        {
            return bankInfos == null ? null : bankInfos.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.dialog_discount_bank, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final GoodActivityBean bankInfo = getItem(position);
            x.image().bind(viewHolder.ivIcon, ServiceDispatcher.getImageUrl(bankInfo.getBankLogoCode()));
            viewHolder.tvName.setText(bankInfo.getBankName());
            viewHolder.tvBankCoupon.setText(bankInfo.getDetail());
            return viewHolder.root;
        }

        public class ViewHolder
        {
            public ImageView ivIcon; // 银行图片；
            public TextView tvName; //银行名称；
            public TextView tvBankCoupon; // 银行折扣；
            public View root;

            public ViewHolder(View root)
            {
                this.root = root;
                ivIcon = (ImageView) root.findViewById(R.id.ivIcon);
                tvName = (TextView) root.findViewById(R.id.tvName);
                tvBankCoupon = (TextView) root.findViewById(R.id.tv_bank_coupon);
            }
        }
    }
}
