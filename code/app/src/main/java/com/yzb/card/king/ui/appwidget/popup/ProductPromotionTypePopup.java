package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.PromotionTypeBean;
import com.yzb.card.king.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/20
 * 描  述：优惠促销类型
 */
public class ProductPromotionTypePopup implements View.OnClickListener {

    private List<PromotionTypeBean> bedTypeBeenList = new ArrayList<PromotionTypeBean>();

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private ListView wvLvData;

    private ProductPromotionTypePopup.CurrentPpAdapter ppAdapter;

    private ProductPromotionTypePopup.DataListCallBack callBack;

    private  boolean ifTodayShuaiFang = false;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public ProductPromotionTypePopup(Activity activity, int defineHeight)
    {

        this.activity = activity;

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_TOP);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_use_bank_selector_pp, null);

        view.findViewById(R.id.tvClear).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
            @Override
            public void onClickListenerDismiss()
            {
                if(callBack!=null){

                    callBack.onConfirm(getSelectoredDataList());
                }
            }
        });

        wvLvData = (ListView) view.findViewById(R.id.wvLvData);

        String[] nameArray = activity.getResources().getStringArray(R.array.promotion_type_name_array);

        int[] nameValueArray = activity.getResources().getIntArray(R.array.promotion_type_value_array);

        int length = nameArray.length;

        for (int i = 0; i < length; i++) {

            PromotionTypeBean bedTypeBean = new PromotionTypeBean();

            bedTypeBean.setTypeName(nameArray[i]);

            bedTypeBean.setTypeValue(nameValueArray[i]);

            bedTypeBeenList.add(bedTypeBean);
        }
        bedTypeBeenList.remove(0);

        ppAdapter = new ProductPromotionTypePopup.CurrentPpAdapter();

        wvLvData.setAdapter(ppAdapter);

        wvLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


            }
        });
    }

    /**
     * 今日甩房数据，由入店日期和今天日期确定
     *
     * @param startDate
     */
    public void setTypeDataByDate(Date startDate)
    {

        Date nowDate = new Date();

        String startDateStr = DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE3);

        String nowDateStr = DateUtil.date2String(nowDate, DateUtil.DATE_FORMAT_DATE3);

        String[] nameArray = activity.getResources().getStringArray(R.array.promotion_type_name_array);

        int[] nameValueArray = activity.getResources().getIntArray(R.array.promotion_type_value_array);

        if(startDateStr.equals(nowDateStr) && !ifTodayShuaiFang){//如果入住日期是当前日期，则需要今日甩房

            PromotionTypeBean bedTypeBean = new PromotionTypeBean();

            bedTypeBean.setTypeName(nameArray[0]);

            bedTypeBean.setTypeValue(nameValueArray[0]);

            bedTypeBeenList.add(0,bedTypeBean);

            ifTodayShuaiFang = true;

        }else if(!startDateStr.equals(nowDateStr) && ifTodayShuaiFang){

            ifTodayShuaiFang = false;

            bedTypeBeenList.remove(0);
        }

        ppAdapter.notifyDataSetChanged();

    }

    public void setCallBack(ProductPromotionTypePopup.DataListCallBack callBack)
    {
        this.callBack = callBack;
    }

    private void clearSelectorData()
    {

        for (PromotionTypeBean bedTypeBean : bedTypeBeenList) {

            bedTypeBean.setSelectedFlag(false);
        }

        ppAdapter.notifyDataSetChanged();
    }

    private List<PromotionTypeBean> getSelectoredDataList()
    {

        List<PromotionTypeBean> list = new ArrayList<>();

        for (PromotionTypeBean bedTypeBean : bedTypeBeenList) {

            boolean flag = bedTypeBean.isSelectedFlag();

            if (flag) {

                list.add(bedTypeBean);
            }

        }

        return list;
    }

    public void showPP(View rootView)
    {

        baseBottomFullPP.showBottomAsView(rootView);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvClear://清理

                clearSelectorData();

                break;
            case R.id.tvConfirm://确定

                if (callBack != null) {

                    baseBottomFullPP.dismiss();

                    callBack.onConfirm(getSelectoredDataList());
                }

                break;
            default:
                break;

        }
    }


    private class CurrentPpAdapter extends BaseAdapter {


        @Override
        public int getCount()
        {
            return bedTypeBeenList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return bedTypeBeenList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ProductPromotionTypePopup.CurrentPpAdapter.ViewHold viewHold;

            if (convertView == null) {

                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_name_bed_type, null);

                viewHold = new ProductPromotionTypePopup.CurrentPpAdapter.ViewHold(convertView);

                convertView.setTag(viewHold);
            } else {

                viewHold = (ProductPromotionTypePopup.CurrentPpAdapter.ViewHold) convertView.getTag();
            }

            viewHold.llParent.setTag(position);

            PromotionTypeBean bedTypeBean = (PromotionTypeBean) getItem(position);

            viewHold.tvItemName.setText(bedTypeBean.getTypeName());

            boolean flag = bedTypeBean.isSelectedFlag();

            viewHold.ivChecked.setEnabled(flag);

            viewHold.tvItemName.setEnabled(flag);

            if (position + 1 == getCount()) {

                viewHold.vLine.setVisibility(View.INVISIBLE);

            } else {
                viewHold.vLine.setVisibility(View.VISIBLE);
            }

            return convertView;
        }


        class ViewHold {

            ImageView ivChecked;

            TextView tvItemName;

            LinearLayout llParent;

            View vLine;

            public ViewHold(View convertView)
            {

                ivChecked = (ImageView) convertView.findViewById(R.id.ivChecked);

                tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);

                llParent = (LinearLayout) convertView.findViewById(R.id.llParent);

                llParent.setTag(false);

                vLine = convertView.findViewById(R.id.vLine);

                llParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int index = (int) v.getTag();

                        PromotionTypeBean bedTypeBean = (PromotionTypeBean) getItem(index);

                        boolean flag = !bedTypeBean.isSelectedFlag();

                        ivChecked.setEnabled(flag);

                        tvItemName.setEnabled(flag);

                        bedTypeBean.setSelectedFlag(flag);
                    }
                });
            }
        }

    }

    public interface DataListCallBack {

        void onConfirm(List<PromotionTypeBean> selectoredListData);
    }
}

