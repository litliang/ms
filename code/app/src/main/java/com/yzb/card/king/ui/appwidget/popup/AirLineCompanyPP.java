package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.WholeListView;

/**
 * 类  名：航空公司
 * 作  者：Li Yubing
 * 日  期：2017/9/20
 * 描  述：
 */
public class AirLineCompanyPP implements View.OnClickListener{

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private String[] nameArray = new String[]{};

    private int[] nameValueArray = new int[]{};

    private WholeListView wvLvData;

    private CurrentPpAdapter ppAdapter;

    private int noCheckColor;

    private int checkedColor;

    private BottomDataListCallBack callBack;


    public BaseFullPP getBaseBottomFullPP()
    {
        return baseBottomFullPP;
    }

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public AirLineCompanyPP(Activity activity, int defineHeight,BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        noCheckColor = activity.getResources().getColor(R.color.text_color_33);

        checkedColor = activity.getResources().getColor(R.color.color_416b90);

        AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

        int h = (int) activity.getResources().getDimension(R.dimen.tab_bottom_h);

        int deH = bean.getScreenHeight() - h - bean.getStatusBarHeight();

        baseBottomFullPP = new BaseFullPP(activity, postion, deH);

        initPpView(defineHeight);


    }

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public AirLineCompanyPP(Activity activity, int defineHeight)
    {

        this.activity = activity;

        noCheckColor = activity.getResources().getColor(R.color.text_color_33);

        checkedColor = activity.getResources().getColor(R.color.color_416b90);

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_BOTTOM);


        initPpView(defineHeight);
    }

    private void initPpView(int defineHeight)
    {

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_bottom_air_line_company_pp, null);

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

        view.findViewById(R.id.tvCancel).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

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


    public void setSelectIndex(int selectIndex)
    {

        ppAdapter.setSelectedIndex(selectIndex);
    }

    public void showPP(View rootView)
    {

        baseBottomFullPP.show(rootView);
    }

    public void showToByView(View view){

            AppBaseDataBean bean =GlobalApp.getInstance().getAppBaseDataBean();

        int  statusBarHeight = bean.getStatusBarHeight();

        baseBottomFullPP.showAtLocation(view, Gravity.TOP, 0,
                statusBarHeight);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.tvCancel:
                baseBottomFullPP.dismiss();

                break;

            case R.id.tvConfirm:

                baseBottomFullPP.dismiss();

                if (callBack != null) {

                    int position = ppAdapter.getSelectIndex();

                    int value = -1;

                    if (nameValueArray != null) {

                        int length = nameValueArray.length;
                        if (length > 0) {

                            value = nameValueArray[position];
                        }
                    }

                    callBack.onClickItemDataBack(nameArray[position], value, position);
                }

                break;
            default:
                break;

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
            CurrentPpAdapter.ViewHold viewHold;

            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_name_bottom_air_company, null);

                viewHold = new CurrentPpAdapter.ViewHold(convertView);


                convertView.setTag(viewHold);
            } else {

                viewHold = (CurrentPpAdapter.ViewHold) convertView.getTag();
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
