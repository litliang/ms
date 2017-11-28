package com.yzb.card.king.ui.discount.fragment.pay;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.CustCouponBean;
import com.yzb.card.king.ui.app.interfaces.DiscountListener;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.ticket.presenter.CustomYhqListPresenter;
import com.yzb.card.king.ui.ticket.view.CustomYhqListView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特惠付款-->优惠券列表
 *
 * @author gengqiyun
 * @date 2016.8.29
 */
public class CustCouponDialogFragment extends BaseDialogFragment implements CustomYhqListView
{
    private static CustCouponDialogFragment dialogFragment;
    private YhqDialogAdapter adapter = null;
    private List<CustCouponBean> mUsedCouponList; //已选择的列表；
    private List<CustCouponBean> mCouponList; //总优惠券列表；
    private ItemListener clickListener;
    public static final int OP_ADD_COUPON = 1; //添加优惠券；
    public static final int OP_DEL_COUPON = 0; //删除优惠券；
    private String shopIds; // 商家的id,多个用英文逗号分割；
    private String goodsId; // 商品的id,多个用英文逗号分割；
    private String goodsCode;
    private CustomYhqListPresenter yhqListPresenter;
    private String industryId; //行业id；
    private float mTotalAmount; //订单总金额；
    private int shopCouponNum = 0; //商家优惠券的数量；
    private int platformCouponNum = 0;//平台优惠券的数量；

    public static CustCouponDialogFragment getInstance()
    {
        if (dialogFragment == null)
        {
            dialogFragment = new CustCouponDialogFragment();
        }
        return dialogFragment;
    }

    public CustCouponDialogFragment setListener(ItemListener clickListener)
    {
        this.clickListener = clickListener;
        return this;
    }

    /**
     * @param couponList 用户选择的优惠券列表；
     */
    public CustCouponDialogFragment setUsedCouponList(List<CustCouponBean> couponList)
    {
        this.mUsedCouponList = couponList;
        setCouponNumbers();
        return this;
    }

    /**
     * 刷新平台优惠券和商家优惠券的数量；
     */
    private void setCouponNumbers()
    {
        if (mUsedCouponList != null)
        {
            int shopCouponNumEmp = 0; //商家优惠券的数量；
            int platformCouponNumEmp = 0;//平台优惠券的数量；
            String type;
            for (int i = 0; i < mUsedCouponList.size(); i++)
            {
                type = mUsedCouponList.get(i).getPlatformType();
                //2平台活动；3商家活动
                if ((DiscountListener.platform_type_platform + "").equals(type))
                {
                    platformCouponNumEmp++;
                } else if ((DiscountListener.platform_type_shop + "").equals(type))
                {
                    shopCouponNumEmp++;
                }
            }
            this.shopCouponNum = shopCouponNumEmp;
            this.platformCouponNum = platformCouponNumEmp;
        }
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_yhq_dialog;
    }

    protected void initView(View view)
    {
        yhqListPresenter = new CustomYhqListPresenter(this);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        adapter = new YhqDialogAdapter(getActivity());
        listView.setAdapter(adapter);
        getData();
    }

    @Override
    public void onGetCustomYhqListSucess(List<CustCouponBean> accountBeans)
    {
        mCouponList = accountBeans;
        adapter.appendALL(mCouponList);
    }

    @Override
    public void onGetCustomYhqListFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    private void getData()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("industryId", industryId);//行业分类(多个使用英文逗号分割)
        params.put("shopId", shopIds);//商家id(多个使用英文逗号分割)
        params.put("goodsId", goodsId);//商品id(多个使用英文逗号分割)
        params.put("goodsCode", goodsCode);
        yhqListPresenter.loadData(params);
    }

    @Override
    protected int getDialogHeight()
    {
        return CommonUtil.getScreenHeight(getActivity()) * 2 / 5;
    }

    /**
     * 设置商家的id;
     *
     * @param shopIds 商家的id
     * @return
     */
    public CustCouponDialogFragment setShopId(String shopIds)
    {
        this.shopIds = shopIds;
        return this;
    }

    public CustCouponDialogFragment setIndustryId(String industryId)
    {
        this.industryId = industryId;
        return this;
    }

    public CustCouponDialogFragment setOrderAmount(float mTotalAmount)
    {
        this.mTotalAmount = mTotalAmount;
        LogUtil.i("订单总金额==" + mTotalAmount);
        return this;
    }

    public CustCouponDialogFragment setGoodsId(String goodsId)
    {
        this.goodsId = goodsId;
        return this;
    }

    public CustCouponDialogFragment setGoodsCode(String goodsCode)
    {
        this.goodsCode = goodsCode;
        return this;
    }

    /**
     * 适配器；
     */
    private class YhqDialogAdapter extends BaseListAdapter<CustCouponBean>
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
                convertView = mInflater.inflate(R.layout.listview_item_yhq, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final CustCouponBean yhqBean = getItem(position);
            viewHolder.tv.setText("¥" + Utils.subZeroAndDot(yhqBean.getCutAmount() + ""));

            viewHolder.tvmm.setText(yhqBean.getGrantCondition() == 1 ? "无门槛使用" :
                    "每满" + Utils.subZeroAndDot(yhqBean.getFullAmount() + "") + "元减" + Utils.subZeroAndDot(yhqBean.getCutAmount() + "") + "元");

            if (!isEmpty(yhqBean.getStartTime()) && !isEmpty(yhqBean.getEndTime()))
            {
                viewHolder.tv_syrq.setVisibility(View.VISIBLE);
                viewHolder.tv_syrq.setText("使用期限 " + yhqBean.getStartTime() + "~" + yhqBean.getEndTime());
            } else
            {
                viewHolder.tv_syrq.setVisibility(View.GONE);
            }

            //判断是否已使用；
            final boolean hasUse = hasUsed(yhqBean);
            //选中置灰；
            viewHolder.btnLjlq.setSelected(hasUse);
            viewHolder.btnLjlq.setClickable(true);
            viewHolder.btnLjlq.setTextColor(hasUse ? getResources().getColor(R.color.text_color_1) :
                    getResources().getColor(R.color.color_status));
            viewHolder.btnLjlq.setText(hasUse ? "取消使用" : "点击使用");
            //使用；
            viewHolder.btnLjlq.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    boolean exist = hasExist(yhqBean.getActId());
                    //已存在，则取消；
                    if (exist && clickListener != null)
                    {
                        clickListener.getCouponInfo(yhqBean, OP_DEL_COUPON);
                        dismiss();
                        return;
                    }

                    //订单金额比较；
                    if (mTotalAmount - usedDiscount() < yhqBean.getFullAmount())
                    {
                        toastCustom("订单总金额不符合优惠券使用条件");
                        return;
                    }
                    if (usedDiscount() + yhqBean.getCutAmount() > mTotalAmount)
                    {
                        toastCustom("优惠券总金额不能大于订单金额");
                        return;
                    }
                    //每个商家只能使用一张优惠券；否则不可用；
                    String errorMsg = judgeCanUseWhenAdd(yhqBean);
                    if (!isEmpty(errorMsg))
                    {
                        toastCustom(errorMsg);
                        return;
                    }
                    //执行添加；
                    clickListener.getCouponInfo(yhqBean, hasUse ? OP_DEL_COUPON : OP_ADD_COUPON);
                    dismiss();
                }
            });
            return viewHolder.root;
        }

        /**
         * 已经使用的优惠券的金额；
         *
         * @return
         */
        private float usedDiscount()
        {
            float sum = 0;
            if (mUsedCouponList != null)
            {
                for (int i = 0; i < mUsedCouponList.size(); i++)
                {
                    sum += mUsedCouponList.get(i).getCutAmount();
                }
            }
            return sum;
        }

        /**
         * 已使用的优惠券列表是否存在此优惠券；
         *
         * @param actId
         * @return
         */
        private boolean hasExist(String actId)
        {
            if (mUsedCouponList != null)
            {
                for (int i = 0; i < mUsedCouponList.size(); i++)
                {
                    if (!TextUtils.isEmpty(actId) && actId.equals(mUsedCouponList.get(i).getActId()))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 判断使用数量（平台商家各自最多一张），和共用情况；
         *
         * @param yhqBean 要使用的优惠券;
         * @return
         */
        private String judgeCanUseWhenAdd(CustCouponBean yhqBean)
        {
            //活动平台（1平台商家活动；2平台活动；3商家活动；4银行活动）直接判断 2，3即可；
            String platformType = yhqBean.getPlatformType();

            //平台优惠券；
            if ((DiscountListener.platform_type_platform + "").equals(platformType)
                    && getUsedYhqNum(DiscountListener.platform_type_platform) >= 1)
            {
                return "平台优惠券最多使用一张";
            } else if ((DiscountListener.platform_type_shop + "").equals(platformType) &&
                    getUsedYhqNum(DiscountListener.platform_type_shop) >= 1)
            {
                return "商家优惠券最多使用一张";
            }
            return "";
        }

        /**
         * 获取已使用的商家或平台 优惠券的数量；
         *
         * @param type 2平台活动；3商家活动
         * @return
         */
        public int getUsedYhqNum(int type)
        {
            int sum = 0;
            if (mUsedCouponList != null)
            {
                CustCouponBean yhqBean;
                for (int i = 0; i < mUsedCouponList.size(); i++)
                {
                    yhqBean = mUsedCouponList.get(i);
                    if (DiscountListener.platform_type_platform == type && "2".equals(yhqBean.getPlatformType()))
                    {
                        sum++;
                    } else if (DiscountListener.platform_type_platform == type && "3".equals(yhqBean.getPlatformType()))
                    {
                        sum++;
                    }
                }
            }
            return sum;
        }

        /**
         * 判断此优惠券是否已经使用；
         *
         * @param yhqBean
         * @return true:已经使用；
         */
        private boolean hasUsed(CustCouponBean yhqBean)
        {
            String actId = yhqBean.getActId();
            if (!TextUtils.isEmpty(actId) && mUsedCouponList != null)
            {
                for (int i = 0; i < mUsedCouponList.size(); i++)
                {
                    if (actId.equals(mUsedCouponList.get(i).getActId()))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        public class ViewHolder
        {
            public TextView tv;
            public TextView tvmm;
            public TextView tv_syrq;
            public TextView btnLjlq;
            public View root;

            public ViewHolder(View root)
            {
                this.root = root;
                tv = (TextView) root.findViewById(R.id.tv);
                tvmm = (TextView) root.findViewById(R.id.tv_mm);
                tv_syrq = (TextView) root.findViewById(R.id.tv_syrq);
                btnLjlq = (TextView) root.findViewById(R.id.tv_ljlq);
            }
        }
    }

    /**
     * 优惠券操作监听；
     */
    public interface ItemListener
    {
        /**
         * @param yhqBean 回调的优惠券信息；
         * @param opType  操作类型；OP_ADD_COUPON:使用；OP_DEL_COUPON:取消使用；
         */
        void getCouponInfo(CustCouponBean yhqBean, int opType);
    }
}
