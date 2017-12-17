package com.yzb.card.king.ui.appwidget.popup.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.HotelBrandPopup;
import com.yzb.card.king.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/21
 * 描  述：
 */
public class CatalogueTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CatalogueTypeBean> monthBeanList = new ArrayList<CatalogueTypeBean>();

    private Activity activity;

    private  boolean flag = true;

    private boolean spreadFlag = false;//全部展开

    private int rowNum = 4;

    public CatalogueTypeAdapter(Activity activity, List<CatalogueTypeBean> monthBeanList ){

        this.monthBeanList = monthBeanList;

        this.activity = activity;

    }

    public CatalogueTypeAdapter(Activity activity, List<CatalogueTypeBean> monthBeanList,boolean spreadFlag,int rowNum  ){

        this.monthBeanList = monthBeanList;

        this.activity = activity;

        this.spreadFlag = spreadFlag;

        this.rowNum = rowNum;

    }

    public void addNewData(List<CatalogueTypeBean> monthBeanList ){

        this.monthBeanList.clear();

        this.monthBeanList.addAll(monthBeanList);

        notifyDataSetChanged();
    }

    public  void reSetView(boolean flag){

        this.flag = flag;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_type_brand_item, null);

        CatalogueTypeAdapter.HotelBrandTypeHolder hotelBrandTypeHolder = new CatalogueTypeAdapter.HotelBrandTypeHolder(view);

        return hotelBrandTypeHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof CatalogueTypeAdapter.HotelBrandTypeHolder) {

            CatalogueTypeAdapter.HotelBrandTypeHolder hotelBrandTypeHolder = (CatalogueTypeAdapter.HotelBrandTypeHolder) holder;

            hotelBrandTypeHolder.initData(position);

        }

    }

    @Override
    public int getItemCount()
    {
        return monthBeanList.size();
    }


    private   SubItemadapter.ItemDataCallBack itemDataCallBack;


    public void setItemDataCallBack(SubItemadapter.ItemDataCallBack itemDataCallBack)
    {
        this.itemDataCallBack = itemDataCallBack;
    }


    class HotelBrandTypeHolder extends RecyclerView.ViewHolder {

        private TextView tvBrandTypeName;

        private WholeRecyclerView wvBrand;

        private ImageView ivLine;

        private ImageButton tvSpread;

        private LinearLayout llArrowGray;

        private   SubItemadapter myPPadapter;

        public HotelBrandTypeHolder(View itemView)
        {
            super(itemView);

            tvBrandTypeName = (TextView) itemView.findViewById(R.id.tvBrandTypeName);

            wvBrand = (WholeRecyclerView) itemView.findViewById(R.id.wvBrand);

            tvSpread = (ImageButton) itemView.findViewById(R.id.tvSpread);

            ivLine = (ImageView) itemView.findViewById(R.id.ivLine);

            llArrowGray = (LinearLayout) itemView.findViewById(R.id.llArrowGray);
        }

        public void initData(int position)
        {

            CatalogueTypeBean brandTypeBean = monthBeanList.get(position);

            tvBrandTypeName.setText(brandTypeBean.getTypeName());

            List<SubItemBean> adapterList = brandTypeBean.getChildList();

            int size = adapterList.size();

            if(size > 4 && !spreadFlag){

                llArrowGray.setTag(true);

                tvSpread.setVisibility(View.VISIBLE);

                llArrowGray.setOnClickListener(spreadListener);

                tvSpread.setImageResource(R.mipmap.icon_arrow_down_gray_item);

            }else{

                tvSpread.setVisibility(View.INVISIBLE);

            }


            if(spreadFlag){

                myPPadapter = new SubItemadapter(activity,adapterList,spreadFlag);

            }else{

                myPPadapter = new SubItemadapter(activity,adapterList);

            }

            myPPadapter.setMutualList(brandTypeBean.isMutualList());

            RecyclerView.LayoutManager layoutManager = wvBrand.getLayoutManager();

            if(flag){

                if(layoutManager==null){

                  wvBrand.addItemDecoration(new DecorationUtil( CommonUtil.dip2px(activity,7)));

                }
            }

            if(layoutManager==null ){

                wvBrand.setLayoutManager(new GridLayoutManager(activity, rowNum));

            }

            wvBrand.setAdapter(myPPadapter);

            if(itemDataCallBack!= null){

                myPPadapter.setItemDataCallBack(itemDataCallBack);

            }

            if(getItemCount() == position+1){

                ivLine.setVisibility(View.INVISIBLE);
            }else {

                ivLine.setVisibility(View.VISIBLE);
            }
        }

        private  View.OnClickListener spreadListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                boolean flag = (boolean) v.getTag();

                if(flag){//展开

                    myPPadapter.spreadData(flag);

                    tvSpread.setImageResource(R.mipmap.icon_arrow_up_gray_item);
                }else {//收缩

                    myPPadapter.shrinkData(flag);

                    tvSpread.setImageResource(R.mipmap.icon_arrow_down_gray_item);
                }

                v.setTag(!flag);
            }
        };

    }



}
