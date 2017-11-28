package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.WholeListView;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/13
 * 描  述：加载有列表形式的数据
 */
public class BottomDataListPP {

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private String[] nameArray = new String[]{};

    private int[] nameValueArray = new int[]{};

    private TextView tvTitleName;

    private WholeListView wvLvData;

    private CurrentPpAdapter ppAdapter;

    private int noCheckColor;

    private int checkedColor;

    private BottomDataListCallBack callBack;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public BottomDataListPP(Activity activity, int defineHeight)
    {

        this.activity = activity;

        noCheckColor = activity.getResources().getColor(R.color.text_color_33);

        checkedColor = activity.getResources().getColor(R.color.color_416b90);

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_BOTTOM);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_bottom_title_listview, null);


        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);


        tvTitleName = (TextView) view.findViewById(R.id.tvTitleName);


        initListViewData(view);
    }


    /**
     * 酒店订单的保留时间
     *
     * @param activity
     * @param defineHeight 自定义子视图的高度
     * @param flag
     */
    public BottomDataListPP(Activity activity, int defineHeight, boolean flag)
    {

        this.activity = activity;

        noCheckColor = activity.getResources().getColor(R.color.text_color_33);

        checkedColor = activity.getResources().getColor(R.color.text_color_33);

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_BOTTOM);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_bottom_title_two_listview, null);


        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);


        initListViewData(view);
    }


    private void initListViewData(View view)
    {

        wvLvData = (WholeListView) view.findViewById(R.id.wvLvData);

        ppAdapter = new CurrentPpAdapter();

        wvLvData.setAdapter(ppAdapter);

        wvLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {


                ppAdapter.setSelectedIndex(position);

                baseBottomFullPP.dismiss();

                if (callBack != null) {

                    int value = -1;

                    if (nameValueArray != null) {

                        int length = nameValueArray.length;
                        if (length > 0) {

                            value = nameValueArray[position];
                        }
                    }

                    callBack.onClickItemDataBack(nameArray[position], value, position);
                }
            }
        });

    }

    public void setCallBack(BottomDataListCallBack callBack)
    {
        this.callBack = callBack;
    }

    /**
     * @param nameArray
     * @param nameValueArray
     */
    public void setLogicData(String[] nameArray, int[] nameValueArray)
    {

        this.nameArray = nameArray;

        this.nameValueArray = nameValueArray;

        ppAdapter.notifyDataSetChanged();
    }


    public void setTitleName(int strId)
    {

        tvTitleName.setText(strId);

    }

    public void setGone(){

        tvTitleName.setVisibility(View.GONE);
    }

    public void setSelectIndex(int selectIndex)
    {

        ppAdapter.setSelectedIndex(selectIndex);
    }

    public void showPP(View rootView)
    {

        baseBottomFullPP.show(rootView);
    }


    private class CurrentPpAdapter extends BaseAdapter {

        private int selectIndex = 0;

        @Override
        public int getCount()
        {
            return nameArray.length;
        }

        @Override
        public Object getItem(int position)
        {
            return nameArray[position];
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHold viewHold;

            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_name_bottom, null);

                viewHold = new ViewHold(convertView);


                convertView.setTag(viewHold);
            } else {

                viewHold = (ViewHold) convertView.getTag();
            }

            String nameStr = nameArray[position];

            viewHold.tvItemName.setText(nameStr);

            if (selectIndex == position) {

                viewHold.ivChecked.setVisibility(View.VISIBLE);

                viewHold.tvItemName.setTextColor(checkedColor);

            } else {

                viewHold.ivChecked.setVisibility(View.INVISIBLE);

                viewHold.tvItemName.setTextColor(noCheckColor);
            }

            if (position + 1 == getCount()) {

                viewHold.vLine.setVisibility(View.INVISIBLE);

            } else {
                viewHold.vLine.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        public void setSelectedIndex(int selectedIndex)
        {
            this.selectIndex = selectedIndex;
            notifyDataSetChanged();
        }


        class ViewHold {

            ImageView ivChecked;

            TextView tvItemName;

            View vLine;

            public ViewHold(View convertView)
            {

                ivChecked = (ImageView) convertView.findViewById(R.id.ivChecked);

                tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);

                vLine = convertView.findViewById(R.id.vLine);
            }
        }

    }

    public interface BottomDataListCallBack {

        void onClickItemDataBack(String name, int nameValue, int selectIndex);
    }

}
