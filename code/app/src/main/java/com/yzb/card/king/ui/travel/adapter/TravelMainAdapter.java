package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.ui.travel.bean.TravelBean;
import com.yzb.card.king.ui.travel.bean.TravelFxbBean;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ValidatorUtil;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.yzb.card.king.R.id.tv;


/**
 * Created by gengqiyun on 2016/4/12.
 * 旅游-首页 适配器；
 */
public class TravelMainAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<TravelFxbBean> storeBeans;
    private AdapterItemClickCallBack callBack;
    private ImageOptions imageOption;

    public TravelMainAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        imageOption = XImageOptionUtil.getRoundImageOption(15, ImageView.ScaleType.CENTER_CROP);
        this.storeBeans = new ArrayList<>();
    }

    public void appendDataList(List<TravelFxbBean> storeBeans)
    {
        this.storeBeans.clear();
        this.storeBeans.addAll(storeBeans);
        notifyDataSetChanged();
    }

    public void newDataList(List<TravelFxbBean> storeBeans)
    {
        this.storeBeans.addAll(storeBeans);
        notifyDataSetChanged();
    }

    public TravelFxbBean getItem(int position)
    {
        return storeBeans.get(position);
    }

    public List<TravelFxbBean> getDataList()
    {
        return this.storeBeans;
    }

    public void clear()
    {
        if (storeBeans != null)
        {
            storeBeans.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(inflater.inflate(R.layout.gv_item_travel_main, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        ViewHolder vh = (ViewHolder) holder;

        TravelFxbBean travelBean = storeBeans.get(position);
        // 显示圆形图片；
        if (!TextUtils.isEmpty(travelBean.getProductImageUrl()))
        {

                x.image().bind(vh.ivpic, ServiceDispatcher
                        .getImageUrl(travelBean.getProductImageUrl()), imageOption);
        }

        String travelName = travelBean.getProductName();
        if (TextUtils.isEmpty(travelName))
        {
            vh.tvintroduce.setText("");
        } else
        {
            // 以图片宽度为基准进行文本压缩；
//            CharSequence orginalCs = TextUtils.ellipsize(travelName, vh.tvintroduce.getPaint(),
//                    CommonUtil.dip2px(context, 142) * 3 / 2, TextUtils.TruncateAt.END);
            vh.tvintroduce.setText(travelName);
        }

        String price = travelBean.getBgnPrice() + "";

        vh.tv_price.setText(price.substring(0, price.indexOf(".")));

        vh.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (callBack != null)
                {
                    callBack.itemClickCallBack(position);
                }
            }
        });
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemCount()
    {
        return storeBeans == null ? 0 : storeBeans.size();
    }

    public void setAdapterItemClickCallBack(AdapterItemClickCallBack callBack)
    {
        this.callBack = callBack;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView ivpic;
        public final TextView tvintroduce;
        public final TextView tv_price;
        public final View root;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.root = itemView;
            ivpic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvintroduce = (TextView) itemView.findViewById(R.id.tv_introduce);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

        }

    }
}
