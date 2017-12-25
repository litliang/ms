package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.hotel.FilterCollection;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.TicketFilterView;
import com.yzb.card.king.ui.appwidget.popup.adapter.CatalogueTypeAdapter;
import com.yzb.card.king.ui.hotel.holder.ListHolder;
import com.yzb.card.king.ui.manage.FilterManager;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/19
 */

public class TicketFilterPopup extends PopupWindow
{
    private View mRoot;
    private View tvConfirm;
    private TextView headerTitle;
    private ImageView headerLeftImage;
    private View headerLeft;
   private List<CatalogueTypeBean> orgList;

    private RecyclerView wvLvData;

    private CatalogueTypeAdapter hotelBrandTypeAdapter;

    public TicketFilterPopup()
    {
        initView();
        initListener();
        initData();
        init();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.pop_ticket_filter);

        headerLeft = mRoot.findViewById(R.id.llTemp);
        headerLeftImage = (ImageView) mRoot.findViewById(R.id.headerLeftImage);
        headerTitle = (TextView) mRoot.findViewById(R.id.tvTitle);
        tvConfirm =  mRoot.findViewById(R.id.tvConfirm);

        wvLvData = (RecyclerView) mRoot.findViewById(R.id.wvLvData);

        wvLvData.setLayoutManager(new GridLayoutManager(GlobalApp.getInstance().getContext(), 1));

        List<CatalogueTypeBean> monthBeanList = new ArrayList<CatalogueTypeBean>();

        hotelBrandTypeAdapter = new CatalogueTypeAdapter(GlobalApp.getInstance().getPublicActivity(), monthBeanList,true,3);

        wvLvData.setAdapter(hotelBrandTypeAdapter);


    }

    private void initListener()
    {
        ClickListener listener = new ClickListener();
        headerLeft.setOnClickListener(listener);
        tvConfirm.setOnClickListener(listener);
    }

    class ClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.llTemp:
                    dismiss();
                    break;
                case R.id.tvConfirm:
                    confirm();
                    break;



            }
        }

    }

    private void confirm()
    {
        List<FilterType>  selectorList =  calSelectoredList();


        TicketFilterView.filterData.setFilterTypes(selectorList);
        dismiss();
    }

    /**
     * 计算出选择内容
     */
    public  List<FilterType> calSelectoredList(){

        List<FilterType> selectorList = new ArrayList<FilterType>();

        for (CatalogueTypeBean brandTypeBean :orgList){

            List<SubItemBean>  list =   brandTypeBean.getChildList();

            for(SubItemBean brandBean :list){

                boolean flag = brandBean.isDefault();

                if(flag){

                    FilterType filterType = new FilterType();
                    filterType.setBaseCabinCode(brandBean.getFilterId());
                    filterType.setType(brandBean.getChildTypeCode());

                    filterType.setCode(brandBean.getFilterId());

                    selectorList.add(filterType);
                }
            }

        }

        return selectorList ;
    }


    private void initData()
    {
        setHeader();
        loadData();
    }

    private void setHeader()
    {
        headerLeftImage.setVisibility(View.VISIBLE);
        headerTitle.setText("筛选");
    }



    private void loadData()
    {
        SimpleRequest simpleRequest = new SimpleRequest(getServiceName());
        simpleRequest.setParam(getParam());
        simpleRequest.sendRequest(new FilterCallBack());
    }

    private String getServiceName()
    {
        return CardConstant.HOTEL_FILTER_LIST;
    }

    private Map<String, Object> getParam()
    {
        Flight flight = FilterManager.getInstance().getCurrentFlight();
        Map<String, Object> param = new HashMap<>();
        param.put("industryId", AppConstant.ticket_id);
        param.put("industryFun", 1);
        param.put("funType", 2);
        param.put("addrId", flight.getEndCity().getCityId());//目的城市id  3690
        param.put("desAddrId", flight.getStartCity().getCityId());//出发城市id 52315
        return param;
    }

    private void init()
    {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    class FilterCallBack extends HttpCallBackImpl
    {

        public FilterCallBack()
        {
            super();
        }

        @Override
        public void onSuccess(Object o)
        {
            orgList = JSONArray.parseArray(String.valueOf(o), CatalogueTypeBean.class);


            hotelBrandTypeAdapter.addNewData(orgList);
        }


        @Override
        public void onFailed(Object o)
        {

        }
    }






}
