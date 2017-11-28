package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.bean.DiscountInfo;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.DateUtil;

import org.xutils.x;

import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/7 18:53
 * 描述：
 */
public class DiscountInfoAdapter extends BaseAdapter {
    private Context context;
    private List<DiscountInfo> list;

    public DiscountInfoAdapter(Context context, List<DiscountInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_discount_info, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        final DiscountInfo discountInfo = list.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();

        if (!TextUtils.isEmpty(discountInfo.imageCode)) {

            x.image().bind(holder.ivCard,
                    ServiceDispatcher.getImageUrl(discountInfo.imageCode) );
        }
        holder.tvCardName.setText(discountInfo.activityName);
        holder.tvBankName.setText(discountInfo.bankName);
        holder.tvNum.setText(String.valueOf(discountInfo.praiseCount));
        holder.tvStartTime.setText(DateUtil.date2String(discountInfo.startDate, "yyyy/MM/dd"));
        holder.tvEndTime.setText(DateUtil.date2String(discountInfo.endDate, "yyyy/MM/dd"));
        holder.tvLabels.setText(discountInfo.activityType);
        holder.tvLocation.setText(discountInfo.activityCityName);
        holder.llZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    new PrizeTask(discountInfo).execute();
            }
        });
        return convertView;
    }

//    class PrizeTask extends CommentTask {
//        private DiscountInfo discountInfo;
//
//        public PrizeTask(DiscountInfo discountInfo) {
//            super("ActivityPraise");
//            this.discountInfo = discountInfo;
//        }
//
//        @Override
//        protected void parseJson(String data) {
//            discountInfo.praiseStatus = !discountInfo.praiseStatus;
//
//            if (discountInfo.praiseStatus) {
//                discountInfo.praiseCount++;
//                Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
//            } else {
//                discountInfo.praiseCount--;
//                Toast.makeText(context, "取消赞", Toast.LENGTH_SHORT).show();
//            }
//            notifyDataSetChanged();
//        }
//
//        @Override
//        protected void setParam(Map<String, String> param) {
//            param.put("activityType", "1");//1银行活动，2积分活动
//            param.put("activityId", discountInfo.activityId);
//            param.put("status", discountInfo.praiseStatus?"0":"1");
//        }
//    }

    class ViewHolder {
        public ImageView ivCard, ivZan;
        public TextView tvCardName, tvBankName, tvNum, tvStartTime, tvEndTime, tvLabels, tvLocation;
        public LinearLayout llZan;

        public ViewHolder(View view) {
            ivZan = (ImageView) view.findViewById(R.id.ivZan);
            ivCard = (ImageView) view.findViewById(R.id.ivCard);
            tvCardName = (TextView) view.findViewById(R.id.tvCardName);
            tvBankName = (TextView) view.findViewById(R.id.tvBankName);
            tvNum = (TextView) view.findViewById(R.id.tvNum);
            tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
            tvLabels = (TextView) view.findViewById(R.id.tvLabels);
            tvLocation = (TextView) view.findViewById(R.id.tvLocation);
            llZan = (LinearLayout) view.findViewById(R.id.llZan);
        }
    }
}
