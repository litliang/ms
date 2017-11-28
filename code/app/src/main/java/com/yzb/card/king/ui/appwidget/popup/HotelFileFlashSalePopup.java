package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.ui.appwidget.popup.adapter.CatalogueTypeAdapter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/15
 * 描  述：
 */
public class HotelFileFlashSalePopup implements View.OnClickListener {

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private List<CatalogueTypeBean> monthBeanList = new ArrayList<CatalogueTypeBean>();

    private RecyclerView wvLvData;

    private CatalogueTypeAdapter hotelBrandTypeAdapter;

    private HotelFileFlashSalePopup.ViewDataCallBack callBack;

    private RangeSeekBar rangSbPrice;

    private TextView tvMinValue, tvMaxValue;

    private int currentBarMix = 100;

    private int currentBarMax = 1001;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public HotelFileFlashSalePopup(Activity activity, int defineHeight)
    {

        this.activity = activity;

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_TOP);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_hotel_filtrate_popup, null);


        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(activity, defineHeight));


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
            @Override
            public void onClickListenerDismiss()
            {
                if (callBack != null) {
                    callBack.onConfirm(calSelectoredList(), currentBarMix, currentBarMax);
                }

            }
        });

        view.findViewById(R.id.tvClear).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);


        tvMinValue = (TextView) view.findViewById(R.id.tvMinValue);

        tvMaxValue = (TextView) view.findViewById(R.id.tvMaxValue);

        rangSbPrice = (RangeSeekBar) view.findViewById(R.id.rangSbPrice);

        rangSbPrice.setNotifyWhileDragging(true);

        rangSbPrice.setRangeValues(currentBarMix, currentBarMax);

        rangSbPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue)
            {

                int mixNumber = bar.getSelectedMinValue().intValue();

                int maxNumber = bar.getSelectedMaxValue().intValue();

                setTextViewMaxMinValue(mixNumber, maxNumber);

                currentBarMix = mixNumber;

                currentBarMax = maxNumber;

            }
        });


        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        wvLvData.setLayoutManager(new GridLayoutManager(activity, 1));

        String[] strArray = new String[]{"有效期限", "适用门店"};

        for (int i = 0; i < strArray.length; i++) {

            CatalogueTypeBean bean = new CatalogueTypeBean();

            bean.setMutualList(true);

            bean.setTypeName(strArray[i]);

            List<SubItemBean> brandBeanList = new ArrayList<>();

            if (i == 0) {

                int max = 5;

                int[] monthArray = Utils.cellectionMonth(max);

                for (int a : monthArray) {

                    SubItemBean brandBean = new SubItemBean();

                    brandBean.setLocalDataCode(3);

                    brandBean.setFilterName(AppUtils.ToCH(a)+"月");

                    brandBean.setFilterId(a + "");

                    brandBeanList.add(brandBean);
                }


            } else if (i == 1) {

                String[] applyNameArray = activity.getResources().getStringArray(R.array.hotel_apply_name_array);

                int[] applyValueIntArray = activity.getResources().getIntArray(R.array.hotel_apply_value_array);

                int length = applyNameArray.length;

                for (int j = 0; j < length; j++) {

                    SubItemBean brandBean = new SubItemBean();

                    brandBean.setFilterName(applyNameArray[j]);

                    brandBean.setFilterId(applyValueIntArray[j] + "");

                    brandBean.setLocalDataCode(4);

                    brandBeanList.add(brandBean);
                }

            }

            bean.setChildList(brandBeanList);

            monthBeanList.add(bean);
        }


        hotelBrandTypeAdapter = new CatalogueTypeAdapter(activity, monthBeanList, true, 3);


        wvLvData.setAdapter(hotelBrandTypeAdapter);


    }

    private void setTextViewMaxMinValue(int mixNumber, int maxNumber)
    {

        tvMinValue.setText("¥" + mixNumber);

        if (maxNumber == 1001) {

            tvMaxValue.setText("¥1000+");

            if (maxNumber == mixNumber) {
                tvMinValue.setText("¥100");
                mixNumber = 100;
            }

        } else {

            tvMaxValue.setText("¥" + maxNumber);

        }
    }

    /**
     * 重置星級價格數據
     */
    public void reSetPpData()
    {
        currentBarMix = 100;

        currentBarMax = 1001;

        setTextViewMaxMinValue(currentBarMix, currentBarMax);

        rangSbPrice.setRangeValues(currentBarMix, currentBarMax);

        rangSbPrice.resetSelectedValues();


    }

    public void showPP(View rootView)
    {

        baseBottomFullPP.showBottomAsView(rootView);
    }

    /**
     * 计算出选择内容
     */
    public List<SubItemBean> calSelectoredList()
    {

        List<SubItemBean> selectorList = new ArrayList<SubItemBean>();

        for (CatalogueTypeBean brandTypeBean : monthBeanList) {

            List<SubItemBean> list = brandTypeBean.getChildList();

            for (SubItemBean brandBean : list) {

                boolean flag = brandBean.isDefault();

                if (flag) {

                    selectorList.add(brandBean);
                }
            }


        }

        return selectorList;
    }

    /**
     * 设置初始化数据
     */
    public void reInitializeData()
    {

        for (CatalogueTypeBean brandTypeBean : monthBeanList) {


            List<SubItemBean> list = brandTypeBean.getChildList();

            for (SubItemBean brandBean : list) {

                boolean flag = brandBean.isDefault();

                if (flag) {

                    brandBean.setDefault(false);
                }
            }


        }

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvClear://清理

                reInitializeData();

                reSetPpData();

                hotelBrandTypeAdapter.reSetView(false);

                break;
            case R.id.tvConfirm://确定

                if (callBack != null) {
                    callBack.onConfirm(calSelectoredList(), currentBarMix, currentBarMax);
                }

                baseBottomFullPP.dismiss();

                break;
            default:
                break;

        }
    }

    public void setCallBack(HotelFileFlashSalePopup.ViewDataCallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface ViewDataCallBack {

        void onConfirm(List<SubItemBean> selectedBean, int minValue, int maxValue);
    }

}


