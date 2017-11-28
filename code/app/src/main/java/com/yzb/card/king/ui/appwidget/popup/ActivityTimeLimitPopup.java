package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.MonthBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/19
 * 描  述：活动期限
 */
public class ActivityTimeLimitPopup {

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private List<MonthBean> monthBeanList = new ArrayList<>();

    private WholeRecyclerView wvLvData;

    private ActivityTimeLimitPopup.MyPPadapter ppAdapter;

    private ActivityTimeLimitPopup.BottomDataListCallBack callBack;

    private int selectIndex;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public ActivityTimeLimitPopup(Activity activity, int defineHeight)
    {

        this.activity = activity;

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_TOP);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_activity_month_time_limit, null);

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

//        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
//            @Override
//            public void onClickListenerDismiss()
//            {
//                if (callBack != null) {
//
//
//
//                    callBack.onClickItemDataBack(null, -1);
//                }
//            }
//        });


        wvLvData = (WholeRecyclerView) view.findViewById(R.id.wvLvData);

        wvLvData.addItemDecoration(new DecorationUtil( CommonUtil.dip2px(activity,7)));

        wvLvData.setLayoutManager(new GridLayoutManager(activity, 4));

        ppAdapter = new ActivityTimeLimitPopup.MyPPadapter();

        MonthBean bean = new MonthBean();
        bean.setMonthStr("不限");
        bean.setMonthNumber(0);
        monthBeanList.add(bean);

        int max = 6;
        int[] monthArray = Utils.cellectionMonth(max);

        for( int monthT : monthArray){
            MonthBean beanT = new MonthBean();
            beanT.setMonthStr(monthT+"月");
            beanT.setMonthNumber(monthT);
            monthBeanList.add(beanT);
        }



        wvLvData.setAdapter(ppAdapter);


    }

    public void setCallBack(ActivityTimeLimitPopup.BottomDataListCallBack callBack)
    {
        this.callBack = callBack;
    }


    public void showPP(View rootView)
    {

        baseBottomFullPP.showBottomAsView(rootView);
    }

    public void setSelectIndex(int selectIndex)
    {
        this.selectIndex = selectIndex;
    }


    private class MyPPadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {

            View view = LayoutInflater.from(activity).inflate(R.layout.view_activity_month_item,null);


            CurrentViewHolder  viewHolder = new CurrentViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {

            if( holder instanceof CurrentViewHolder ){

                CurrentViewHolder holderTemp = (CurrentViewHolder) holder;

                holderTemp.initData(position);

            }

        }

        @Override
        public int getItemCount()
        {
            return monthBeanList.size();
        }

        class  CurrentViewHolder extends RecyclerView.ViewHolder{

            private  TextView tvTabName;

            private View itemView;

            public CurrentViewHolder(View itemView)
            {
                super(itemView);

                this.itemView = itemView;

                tvTabName = (TextView) itemView.findViewById(R.id.tvTabName);

            }

            public void initData(int position)
            {
                MonthBean bean =  monthBeanList.get(position);

                tvTabName.setText(bean.getMonthStr());

                if(selectIndex == position){

                    tvTabName.setEnabled(true);

                }else{

                    tvTabName.setEnabled(false);
                }

                itemView.setTag(position);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int index = (int) v.getTag();

                        MonthBean beanTemp =  monthBeanList.get(index);

                        selectIndex = index;

                        notifyDataSetChanged();

                        baseBottomFullPP.dismiss();

                        if (callBack != null) {

                            callBack.onClickItemDataBack(beanTemp, index);
                        }

                    }
                });
            }
        }
    }


    public interface BottomDataListCallBack {

        void onClickItemDataBack(MonthBean selectedBean, int selectIndex);
    }
}
