package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

/**
 * 机票代理商；
 * Created by dev on 2016/5/10.
 */
public class TicketAgentListAdapter extends BaseRecyAdapter<FlightDetailBean.TicketPriceInfoBean>
{
    private View.OnClickListener itemListener;
    public static final int OP_TYPE_ADVANCE = 0; //预定；
    public static final int OP_TYPE_DISCOUNT = 1; //查看打折情况；
    public static final int OP_TYPE_COUPON = 2; //优惠券；
    public static final int OP_TYPE_PACKET = 4; //红包；
    public static final int OP_TYPE_CREDIT_COUPON = 3; //刷指定信用卡享受优惠；
    public static final int OP_TYPE_DETAIL = 5; //刷指定信用卡享受优惠；

    public TicketAgentListAdapter(Context context)
    {
        super(context);
    }

    public void setItemListener(View.OnClickListener itemClickListener)
    {
        this.itemListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.item_list_ticket_agent, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            FlightDetailBean.TicketPriceInfoBean data = mList.get(position);

            viewHolder.tvCompanyName.setText(data.getAgent());
            //默认显示成人票价；
            SpannableString ss = new SpannableString("¥" + Utils.subZeroAndDot((data.getFareAdult() + data.getFueltaxAdult()) + ""));
            ss.setSpan(new AbsoluteSizeSpan(19, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tvPrice.setText(ss);

            StringBuffer childrenPriceSb = new StringBuffer();

            float a = data.getFareBab()+data.getFueltaxChd();

            if(a != 0f  && childNum != 0){

                childrenPriceSb.append("儿童¥").append(Utils.subZeroAndDot(a+"")).append(" ");
            }

            float b = data.getFareBab()+data.getFueltaxBab();

            if(b != 0f && babyNum!=0){

                childrenPriceSb.append("婴儿¥").append(Utils.subZeroAndDot(b+""));
            }

            viewHolder.tvChildrenPrice.setText(childrenPriceSb.toString());

            //代理商logo，
            CommonUtil.downloadImageForView(ServiceDispatcher.getImageUrl(data.getAgentLogo()), viewHolder.ivFlightLogo, 15);

            //余票数；
            if (data.getAcbIninfo().contains("A"))
            {
                viewHolder.tvRestNum.setText("余票充足");
            } else
            {
                viewHolder.tvRestNum.setText(context.getString(R.string.ticket_rest_ticket, Integer.parseInt(data.getAcbIninfo())));
            }

            viewHolder.tvDiscount.setText(data.getQabinName()+" 退改签详情");

            viewHolder.llDiscount.setOnClickListener(new ClickListenerImpl(OP_TYPE_DISCOUNT, position));


            FavInfoBean pre = data.getDisMap();

            if (pre != null)
            {
                viewHolder.ll_package_line.setVisibility(View.VISIBLE);

                int i = 0;

                if ("1".equals(pre.getBankStatus())) {

                    viewHolder.tvBankFav.setVisibility(View.VISIBLE);

                } else {
                    i = i+1;
                    viewHolder.tvBankFav.setVisibility(View.GONE);
                }

                if ("1".equals(pre.getTicketStatus())) {

                    viewHolder.tvQuan.setVisibility(View.VISIBLE);


                } else {
                    i = i+1;

                    viewHolder.tvQuan.setVisibility(View.GONE);
                }
                if ("1".equals(pre.getMinusStatus())) {

                    viewHolder.tvJian.setVisibility(View.VISIBLE);


                } else {

                    i = i+1;
                    viewHolder.tvJian.setVisibility(View.GONE);
                }

                LogUtil.e(""+i);
                if(i == 3){

                    viewHolder.ll_package_line.setVisibility(View.GONE);

                }

                if ("1".equals(pre.getLeftStatus())) {

                    viewHolder.ivLifeStages.setVisibility(View.VISIBLE);
                } else {

                    viewHolder.ivLifeStages.setVisibility(View.GONE);
                }


            }else {

                viewHolder.ll_package_line.setVisibility(View.GONE);
            }
            //预定；此处可能是改签
            viewHolder.llBook.setOnClickListener(new ClickListenerImpl(OP_TYPE_ADVANCE, position));

            viewHolder.rlOne.setOnClickListener(new ClickListenerImpl(OP_TYPE_DETAIL, position));

        }
    }

    private int childNum = 0;

    private int babyNum = 0;

    public void setChildrenPrice(int childNum, int babyNum)
    {

        this.babyNum = babyNum;

        this.childNum = childNum;
    }

    class ClickListenerImpl implements View.OnClickListener
    {
        public int opType; //操作类型；
        public int position; //点击位置；

        public ClickListenerImpl(int opType, int position)
        {
            this.opType = opType;
            this.position = position;
        }

        @Override
        public void onClick(View v)
        {
            //设置tag；
            v.setTag(R.id.ticket_agent_tag_op_type, opType);
            v.setTag(R.id.ticket_agent_tag_position, position);
            if (itemListener != null)
            {
                itemListener.onClick(v);
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvPrice; //价格；
        private ImageView ivFlightLogo;

        private View llDiscount; //折扣；
        private TextView tvDiscount;  //折扣内容；
        private View llBook;   //预定；
        private TextView tvRestNum;  //余票数；

        private TextView tvCompanyName;//代理商名称

        private TextView tvChildrenPrice;

        private LinearLayout ll_package_line;

        private  TextView tvBankFav,tvQuan,tvJian;

        private  ImageView ivLifeStages;

        private View llOne;

        private RelativeLayout rlOne;

        public View root;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            root = itemView;
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            ivFlightLogo = (ImageView) itemView.findViewById(R.id.iv_flight_logo);

            llDiscount = itemView.findViewById(R.id.ll_discount);
            tvDiscount = (TextView) itemView.findViewById(R.id.tv_discount);
            llBook = itemView.findViewById(R.id.ll_book);
            tvRestNum = (TextView) itemView.findViewById(R.id.tv_rest_num);

            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);//tvChildrenPrice

            tvChildrenPrice = (TextView) itemView.findViewById(R.id.tvChildrenPrice);

            ll_package_line = (LinearLayout) itemView.findViewById(R.id.ll_package_line);

            tvBankFav = (TextView) itemView.findViewById(R.id.tvBankFav);

            tvQuan = (TextView) itemView.findViewById(R.id.tvQuan);

            tvJian = (TextView) itemView.findViewById(R.id.tvJian);

            ivLifeStages = (ImageView) itemView.findViewById(R.id.ivLifeStages);

            llOne = itemView.findViewById(R.id.llOne);

            rlOne = (RelativeLayout) itemView.findViewById(R.id.rlOne);

        }
    }

}
