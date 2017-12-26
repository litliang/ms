package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.bean.hotel.HotelLevelBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/19
 * 描  述：酒店 的價格 星級
 */
public class HotelStarPricePopup implements View.OnClickListener{

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private WholeRecyclerView wvLvData;

    private RangeSeekBar rangSbPrice;

    private HotelStarPricePopup.MyPPadapter ppAdapter;

    private List<HotelLevelBean> monthBeanList = new ArrayList<HotelLevelBean>();

    private HotelStarPricePopup.ViewDataListCallBack callBack;

    private TextView tvMinValue, tvMaxValue;

    private  int currentBarMix = 0;

    private int currentBarMax =1001;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public HotelStarPricePopup(Activity activity, int defineHeight, BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

        int h = (int) activity.getResources().getDimension(R.dimen.tab_bottom_h);

        int deH = bean.getScreenHeight() - h - bean.getStatusBarHeight();

        baseBottomFullPP = new BaseFullPP(activity, postion, deH);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_hotel_start_price_selector_pp, null);

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);


        initView(view);

    }
    /**
     * @param activity
     */
    public HotelStarPricePopup(Activity activity , BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

        baseBottomFullPP = new BaseFullPP(activity, postion);


        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_hotel_start_price_selector_pp, null);


        baseBottomFullPP.addChildView(view);


        initView(view);

    }

    private void initView(View view){

//        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
//            @Override
//            public void onClickListenerDismiss()
//            {
//                if(callBack != null){
//
//
//                    callBack.onConfirm(ppAdapter.getSelectorDataList(),currentBarMix,currentBarMax);
//                }
//
//            }
//        });


        tvMinValue = (TextView) view.findViewById(R.id.tvMinValue);

        tvMaxValue = (TextView) view.findViewById(R.id.tvMaxValue);

        rangSbPrice = (RangeSeekBar) view.findViewById(R.id.rangSbPrice);

        rangSbPrice.setNotifyWhileDragging(true);

        rangSbPrice.setRangeValues(currentBarMix, currentBarMax);

        wvLvData = (WholeRecyclerView) view.findViewById(R.id.wvLvData);

        wvLvData.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(activity, 7)));

        wvLvData.setLayoutManager(new GridLayoutManager(activity, 3));

        ppAdapter = new HotelStarPricePopup.MyPPadapter();

        String[] strArray = activity.getResources().getStringArray(R.array.hotel_start_name_array);

        String[] valueArray =  activity.getResources().getStringArray(R.array.hotel_start_name_value_array);

        int size = strArray.length;

        for(int a = 0 ; a < size ; a++){
            HotelLevelBean beanTemp = new HotelLevelBean();

            beanTemp.setStartName(strArray[a]);

            beanTemp.setStartValue(valueArray[a]);

            beanTemp.setLpIndex(a);

            monthBeanList.add(beanTemp);
        }

        wvLvData.setAdapter(ppAdapter);

        rangSbPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue)
            {

                int mixNumber = bar.getSelectedMinValue().intValue();

                int maxNumber = bar.getSelectedMaxValue().intValue();

                setTextViewMaxMinValue(mixNumber,maxNumber);

                currentBarMix = mixNumber;

                currentBarMax = maxNumber;

            }
        });

        view.findViewById(R.id.tvClear).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

    }


    private void setTextViewMaxMinValue(int mixNumber, int maxNumber){

        tvMinValue.setText("¥" + mixNumber);

        if (maxNumber == 1001) {

            tvMaxValue.setText("¥1000+");

            if(maxNumber == mixNumber){
                tvMinValue.setText("¥0");
                mixNumber = 0;
            }

        } else {

            tvMaxValue.setText("¥" + maxNumber);

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.tvClear://清理

                reSetPpData();
               // baseBottomFullPP.dismiss();
                break;
            case R.id.tvConfirm://确定

                baseBottomFullPP.dismiss();

                 if(callBack != null){

                     selectedList = ppAdapter.getSelectorDataList();

                     selectedCurrentBarMix = currentBarMix;

                     selectedCurrentBarMax = currentBarMax;

                     callBack.onConfirm(selectedList,currentBarMix,currentBarMax);
                 }


                break;
            default:
                break;

        }
    }

    /**
     * 重置星級價格數據
     */
    public void reSetPpData()
    {
        currentBarMix = 0;

        currentBarMax = 1001;

        setTextViewMaxMinValue(currentBarMix,currentBarMax);

        rangSbPrice.setRangeValues(currentBarMix, currentBarMax);

        rangSbPrice.resetSelectedValues();

        ppAdapter.setClearData();//清理星级
    }

    private  List<HotelLevelBean> selectedList;

    private  int selectedCurrentBarMix = 0;

    private int selectedCurrentBarMax =1001;

    public void showSelectedData() {

        currentBarMix = 0;

        currentBarMax = 1001;

        setTextViewMaxMinValue(selectedCurrentBarMix,selectedCurrentBarMax);

        rangSbPrice.setRangeValues(currentBarMix, currentBarMax);

        rangSbPrice.setSelectedMinValue(selectedCurrentBarMix);

        rangSbPrice.setSelectedMaxValue(selectedCurrentBarMax);

        if(selectedList != null && selectedList.size() > 0){

            ppAdapter.setClearData();//清理星级

            for(HotelLevelBean beanTotal : selectedList){

                for (HotelLevelBean bean :monthBeanList){

                    if(beanTotal.ifSame(bean)){

                        bean.setDeafault(true);
                        break;
                    }

                }
            }
            ppAdapter.notifyDataSetChanged();

        }

    }

    private class MyPPadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private boolean selectZero = false;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {

            View view = LayoutInflater.from(activity).inflate(R.layout.common_top_tab, null);


            HotelStarPricePopup.MyPPadapter.CurrentViewHolder viewHolder = new HotelStarPricePopup.MyPPadapter.CurrentViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {

            if (holder instanceof HotelStarPricePopup.MyPPadapter.CurrentViewHolder) {

                HotelStarPricePopup.MyPPadapter.CurrentViewHolder holderTemp = (HotelStarPricePopup.MyPPadapter.CurrentViewHolder) holder;

                holderTemp.initData(position);

            }

        }

        @Override
        public int getItemCount()
        {
            return monthBeanList.size();
        }

        public void setClearData()
        {

            selectZero = false;

            for (HotelLevelBean bean :monthBeanList){

                bean.setDeafault(false);
            }

            notifyDataSetChanged();

        }

        public List<HotelLevelBean> getSelectorDataList()
        {
            List<HotelLevelBean> selectorDataList = new ArrayList<HotelLevelBean>();

            for (HotelLevelBean bean :monthBeanList){

                if(bean.isDeafault()){

                    selectorDataList.add(bean);
                }

            }
            return selectorDataList;
        }

        class CurrentViewHolder extends RecyclerView.ViewHolder {

            private TextView tvTabName;

            private View itemView;

            private ImageView ivRight;


            public CurrentViewHolder(View itemView)
            {
                super(itemView);

                this.itemView = itemView;

                tvTabName = (TextView) itemView.findViewById(R.id.tvTabName);

                ivRight = (ImageView) itemView.findViewById(R.id.ivRight);

            }

            public void initData(int position)
            {
                HotelLevelBean bean = monthBeanList.get(position);

                tvTabName.setText(bean.getStartName());

                tvTabName.setEnabled(bean.isDeafault());

                if (bean.isDeafault()) {

                    ivRight.setVisibility(View.VISIBLE);

                    if("0".equals(bean.getStartValue())){

                        selectZero = true;
                    }

                } else {
                    ivRight.setVisibility(View.INVISIBLE);
                }

                itemView.setTag(position);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        int index = (int) v.getTag();

                        HotelLevelBean beanTemp = monthBeanList.get(index);

                        boolean flag = !beanTemp.isDeafault();

                        beanTemp.setDeafault(flag);

                        if (index == 0) {//不限

                            selectZero = true;

                            for (int i = 0; i < getItemCount(); i++) {

                                HotelLevelBean itemListBean = monthBeanList.get(i);

                                if (i != index) {

                                    itemListBean.setDeafault(false);
                                }

                            }

                            notifyDataSetChanged();

                        } else {

                            //对0号位置的数据进行处理
                            if (selectZero) {

                                selectZero = false;

                                HotelLevelBean zeroBean = monthBeanList.get(0);

                                zeroBean.setDeafault(false);

                                notifyDataSetChanged();
                            } else {

                                if (flag) {

                                    ivRight.setVisibility(View.VISIBLE);
                                } else {
                                    ivRight.setVisibility(View.INVISIBLE);
                                }
                                tvTabName.setEnabled(flag);
                            }


                        }

                    }
                });
            }
        }
    }

    public interface ViewDataListCallBack {

        void onConfirm(List<HotelLevelBean> selectedList,int minValue, int maxValue);
    }

    public void showPP(View rootView)
    {

        baseBottomFullPP.showTopByView(rootView);

    }

    public void showFull(View view){

        baseBottomFullPP.show(view);
    }


    public void showBottomByViewPP(View rootView)
    {

        baseBottomFullPP.showAsDropDown(rootView);

    }

    public void setViewDataCallBack(ViewDataListCallBack callBack){

        this.callBack = callBack;
    }



}
