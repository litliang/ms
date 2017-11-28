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
import com.yzb.card.king.bean.hotel.BedTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/19
 * 描  述：床型
 */
public class BedTypeSelectedPopup implements View.OnClickListener {

    private List<BedTypeBean> bedTypeBeenList = new ArrayList<>();

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private ListView wvLvData;

    private BedTypeSelectedPopup.CurrentPpAdapter ppAdapter;

    private BedTypeSelectedPopup.DataListCallBack callBack;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public BedTypeSelectedPopup(Activity activity, int defineHeight)
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
                if(callBack != null){
                    callBack.onConfirm(getSelectoredDataList());
                }

            }
        });

        wvLvData = (ListView) view.findViewById(R.id.wvLvData);

        String[] nameArray = activity.getResources().getStringArray(R.array.bed_type_name_array);

        int[] nameValueArray = activity.getResources().getIntArray(R.array.bed_type_value_array);

        int length = nameArray.length;

        for (int i = 0; i < length; i++) {

            BedTypeBean bedTypeBean = new BedTypeBean();

            bedTypeBean.setBedTypeName(nameArray[i]);

            bedTypeBean.setBedTypeValue(nameValueArray[i]);

            bedTypeBeenList.add(bedTypeBean);
        }


        ppAdapter = new BedTypeSelectedPopup.CurrentPpAdapter();

        wvLvData.setAdapter(ppAdapter);

        wvLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


            }
        });
    }

    public void setCallBack(BedTypeSelectedPopup.DataListCallBack callBack)
    {
        this.callBack = callBack;
    }

    private void clearSelectorData()
    {

        for (BedTypeBean bedTypeBean : bedTypeBeenList) {

            bedTypeBean.setSelectedFlag(false);
        }

        ppAdapter.notifyDataSetChanged();
    }

    private List<BedTypeBean> getSelectoredDataList()
    {

        List<BedTypeBean> list = new ArrayList<>();

        for (BedTypeBean bedTypeBean : bedTypeBeenList) {

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

                baseBottomFullPP.dismiss();

                if (callBack != null) {
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
            BedTypeSelectedPopup.CurrentPpAdapter.ViewHold viewHold;

            if (convertView == null) {

                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_name_bed_type, null);

                viewHold = new BedTypeSelectedPopup.CurrentPpAdapter.ViewHold(convertView);

                convertView.setTag(viewHold);
            } else {

                viewHold = (BedTypeSelectedPopup.CurrentPpAdapter.ViewHold) convertView.getTag();
            }

            viewHold.llParent.setTag(position);

            BedTypeBean bedTypeBean = (BedTypeBean) getItem(position);

            viewHold.tvItemName.setText(bedTypeBean.getBedTypeName());

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

                        BedTypeBean bedTypeBean = (BedTypeBean) getItem(index);

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

        void onConfirm(List<BedTypeBean> selectoredListData);
    }
}
