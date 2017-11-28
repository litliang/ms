package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/19
 * 描  述：生活分期数
 */
public class LifeStagePopup implements View.OnClickListener {

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private String[] nameArray = new String[]{};

    private int[] nameValueArray = new int[]{};


    private ListView wvLvData;

    private LifeStagePopup.CurrentPpAdapter ppAdapter;

    private int noCheckColor;

    private int checkedColor;

    private LifeStagePopup.BottomDataListCallBack callBack;


    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public LifeStagePopup(Activity activity, int defineHeight)
    {

        this.activity = activity;

        noCheckColor = activity.getResources().getColor(R.color.text_color_33);

        checkedColor = activity.getResources().getColor(R.color.color_416b90);

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_TOP);

        View view = null;


        view = LayoutInflater.from(this.activity).inflate(R.layout.view_use_bank_selector_pp, null);

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
                onSelectedItemObjectAndDismiss(ppAdapter.getSelectIndex());
            }
        });


        wvLvData = (ListView) view.findViewById(R.id.wvLvData);


        ppAdapter = new LifeStagePopup.CurrentPpAdapter();

        nameArray = activity.getResources().getStringArray(R.array.life_stage_num_name_array);

        nameValueArray = activity.getResources().getIntArray(R.array.life_stage_num_value_array);

        wvLvData.setAdapter(ppAdapter);

        wvLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                ppAdapter.setSelectedIndex(position);

            }
        });
    }

    public void setCallBack(LifeStagePopup.BottomDataListCallBack callBack)
    {
        this.callBack = callBack;
    }


    public void setSelectIndex(int selectIndex)
    {

        ppAdapter.setSelectedIndex(selectIndex);
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

                ppAdapter.setSelectedIndex(-1);

                break;
            case R.id.tvConfirm://确定
                onSelectedItemObjectAndDismiss(ppAdapter.getSelectIndex());
                break;
            default:
                break;

        }
    }

    /**
     * @param position 当callBank传递的数据 null,-1,-1时，则说明清空了
     */
    private void onSelectedItemObjectAndDismiss(int position)
    {

        baseBottomFullPP.dismiss();

        if (callBack != null) {

            String name = null;

            int value = -1;

            if (position == -1) {

            } else {

                int length = nameValueArray.length;

                if (length > 0) {

                    value = nameValueArray[position];
                }

                name = nameArray[position];
            }


            callBack.onClickItemDataBack(name, value, position);
        }

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
            LifeStagePopup.CurrentPpAdapter.ViewHold viewHold;

            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_name_bottom, null);

                viewHold = new LifeStagePopup.CurrentPpAdapter.ViewHold(convertView);


                convertView.setTag(viewHold);
            } else {

                viewHold = (LifeStagePopup.CurrentPpAdapter.ViewHold) convertView.getTag();
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

        public int getSelectIndex()
        {

            return selectIndex;
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

