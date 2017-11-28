package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.travel.bean.TravelSaleListBean;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：特卖会适配器
 * 作  者：Li JianQiang
 * 日  期：2016/11/14
 * 描  述：
 */
public class SaleAdapter extends RecyclerView.Adapter {

	private Context context;
	private LayoutInflater inflater;

	public List<TravelSaleListBean> saleListBeen;

	public SaleAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.saleListBeen = new ArrayList<>();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(inflater.inflate(R.layout.travel_sale_item, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ViewHolder) {
			ViewHolder vh = (ViewHolder) holder;
			if (onItemClickListener != null) {
				holder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						onItemClickListener.setOnItemClickListener(position);
					}
				});
			}
			TravelSaleListBean listBean = saleListBeen.get(position);
			if (!TextUtils.isEmpty(listBean.getGoodsImageUrl())) {
				if (!TextUtils.isEmpty(listBean.getGoodsImageUrl())) {
					x.image().bind(vh.img, listBean.getGoodsImageUrl());

				} else {
					x.image().bind(vh.img, ServiceDispatcher
							.getImageUrl(listBean.getGoodsImageUrl()));
				}
			}

			String travelName = listBean.getGoodsName();
			if (TextUtils.isEmpty(travelName)) {
				vh.tmhContent.setText("");
			} else {
				vh.tmhContent.setText(travelName);
			}


			vh.tmhPrice.setText(listBean.getPrice().substring(0, listBean.getPrice().indexOf(".")));

			vh.tvYuanjia.setText(listBean.getMarketPrice()+"起");

			if (listBean.getInventoryQuantity() > 10) {
				vh.tmhXw.setVisibility(View.GONE);
			} else {
				vh.tmhXw.setVisibility(View.VISIBLE);
				vh.tmhXw.setText("仅剩" + listBean.getInventoryQuantity() + "席");
			}
		}
	}


	@Override
	public int getItemCount() {
		return saleListBeen.size();
	}


	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView img;
		TextView tmhContent;
		TextView tmhXw;
		TextView tmhPrice;
		TextView tvYuanjia;

		public ViewHolder(View itemView) {
			super(itemView);
			img = (ImageView) itemView.findViewById(R.id.tmhImg);
			tmhContent = (TextView) itemView.findViewById(R.id.thmContent);
			tmhXw = (TextView) itemView.findViewById(R.id.thmxw);
			tmhPrice = (TextView) itemView.findViewById(R.id.tmhPrice);
			tvYuanjia = (TextView) itemView.findViewById(R.id.tvYuanjia);
			tvYuanjia.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		}
	}

	public void appendData(List<TravelSaleListBean> dataList) {
		this.saleListBeen.clear();
		this.saleListBeen.addAll(dataList);
		notifyDataSetChanged();
	}

	public void addNewData(List<TravelSaleListBean> dataList) {
		this.saleListBeen.addAll(dataList);
		LogUtil.i("OrderBean==" + saleListBeen.size());
		notifyDataSetChanged();
	}

	public OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public interface OnItemClickListener {
		void setOnItemClickListener(int postion);
	}
}
