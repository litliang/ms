package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.popup.adapter.TicketDiscountContentAdapter;
import com.yzb.card.king.ui.appwidget.popup.adapter.TicketDiscountTypeAdapter;
import com.yzb.card.king.ui.hotel.adapter.DistrictAdapterDataCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/21
 * 描  述：
 */
public class TicketDiscountActivityPopup implements View.OnClickListener{

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private BottomDataListCallBack callBack;

    private RecyclerView rvOne;

    private RecyclerView rvTwo;

    private TicketDiscountTypeAdapter ticketDiscountTypeAdapter;

    private TicketDiscountContentAdapter ticketDiscountContentAdapter;


    public BaseFullPP getBaseBottomFullPP()
    {
        return baseBottomFullPP;
    }


    public void setCallBack(BottomDataListCallBack callBack)
    {
        this.callBack = callBack;
    }

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public TicketDiscountActivityPopup(Activity activity, int defineHeight,BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

        int h = (int) activity.getResources().getDimension(R.dimen.tab_bottom_h);

        int deH = bean.getScreenHeight() - h - bean.getStatusBarHeight();

        baseBottomFullPP = new BaseFullPP(activity, postion, deH);

        initPpView(defineHeight);

    }


    private void initPpView(int defineHeight)
    {

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_bottom_discount_activity_ticket, null);

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

        view.findViewById(R.id.tvCancel).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        initContentView(view);

    }

    private void initContentView(View view)
    {
        rvOne = (RecyclerView) view.findViewById(R.id.rvOne);

        rvTwo = (RecyclerView) view.findViewById(R.id.rvTwo);

        List<CatalogueTypeBean>  oneList = new ArrayList<CatalogueTypeBean>();

        CatalogueTypeBean oneBean = new CatalogueTypeBean();

        oneBean.setTypeName("促销优惠");

        oneList.add(oneBean);

        CatalogueTypeBean twoBean = new CatalogueTypeBean();

        twoBean.setTypeName("银行活动");

        oneList.add(twoBean);

        CatalogueTypeBean threeBean = new CatalogueTypeBean();

        threeBean.setTypeName("生活分期");

        oneList.add(threeBean);

        rvOne.setLayoutManager(new GridLayoutManager(activity, 1));

        ticketDiscountTypeAdapter = new TicketDiscountTypeAdapter(activity);

        ticketDiscountTypeAdapter.setDataCallBack(districtAdapterDataCallBack);

        rvOne.setAdapter(ticketDiscountTypeAdapter);

        ticketDiscountTypeAdapter.addNewDataList(oneList);

        rvTwo.setLayoutManager(new GridLayoutManager(activity, 1));

        ticketDiscountContentAdapter = new TicketDiscountContentAdapter(activity);

        rvTwo.setAdapter(ticketDiscountContentAdapter);

        ticketDiscountContentAdapter.setDataCallBack(districtAdapterDataCallBack);

         List<SubItemBean> list = new ArrayList<SubItemBean>();

        SubItemBean subItemBeanOne = new SubItemBean();
        subItemBeanOne.setFilterName("代金券");
        list.add(subItemBeanOne);

        SubItemBean subItemBeanTwo = new SubItemBean();
        subItemBeanTwo.setFilterName("立减");
        list.add(subItemBeanTwo);

        ticketDiscountContentAdapter.addNewChildList(list);

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

                break;
            default:
                break;

        }
    }

    private DistrictAdapterDataCallBack districtAdapterDataCallBack = new DistrictAdapterDataCallBack() {
        @Override
        public void onClickItemData(Object o) {

            if(o instanceof  SubItemBean){

                SubItemBean bean = (SubItemBean) o;


            }else if( o instanceof CatalogueTypeBean){

                CatalogueTypeBean bean = (CatalogueTypeBean) o;

                String typeName = bean.getTypeName();

                List<SubItemBean> list = new ArrayList<SubItemBean>();

                if("促销优惠".equals(typeName)){

                    SubItemBean subItemBeanOne = new SubItemBean();
                    subItemBeanOne.setFilterName("代金券");
                    list.add(subItemBeanOne);

                    SubItemBean subItemBeanTwo = new SubItemBean();
                    subItemBeanTwo.setFilterName("立减");
                    list.add(subItemBeanTwo);

                }else   if("银行活动".equals(typeName)){

                    SubItemBean subItemBeanOne = new SubItemBean();
                    subItemBeanOne.setFilterName("交通银行");
                    list.add(subItemBeanOne);

                    SubItemBean subItemBeanTwo = new SubItemBean();
                    subItemBeanTwo.setFilterName("浦发银行");
                    list.add(subItemBeanTwo);

                    SubItemBean subItemBeanThree = new SubItemBean();
                    subItemBeanThree.setFilterName("中国银行");
                    list.add(subItemBeanThree);

                    SubItemBean subItemBeanFor = new SubItemBean();
                    subItemBeanFor.setFilterName("农业银行");
                    list.add(subItemBeanFor);

                }else   if("生活分期".equals(typeName)){

                    SubItemBean subItemBeanOne = new SubItemBean();
                    subItemBeanOne.setFilterName("交通银行");
                    list.add(subItemBeanOne);

                    SubItemBean subItemBeanTwo = new SubItemBean();
                    subItemBeanTwo.setFilterName("浦发银行");
                    list.add(subItemBeanTwo);

                    SubItemBean subItemBeanThree = new SubItemBean();
                    subItemBeanThree.setFilterName("中国银行");
                    list.add(subItemBeanThree);

                    SubItemBean subItemBeanFor = new SubItemBean();
                    subItemBeanFor.setFilterName("农业银行");
                    list.add(subItemBeanFor);
                }


                ticketDiscountContentAdapter.addNewChildList(list);
            }

        }
    };

    public interface BottomDataListCallBack {

        void onClickItemDataBack(String name, int nameValue, int selectIndex);
    }

}

