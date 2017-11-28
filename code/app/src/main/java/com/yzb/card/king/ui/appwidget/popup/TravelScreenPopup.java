package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.FilterCollection;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.travel.SearchBarInfo;
import com.yzb.card.king.ui.travel.activity.TravelHeightSeachActivity;
import com.yzb.card.king.ui.travel.activity.TravelLineListActivity;
import com.yzb.card.king.ui.travel.adapter.TravelPicesAdapter;
import com.yzb.card.king.ui.travel.bean.TravelScreenBean;
import com.yzb.card.king.ui.travel.holder.TravelListHolder;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：旅游筛选
 * 作  者：Li JianQiang
 * 日  期：2016/11/21
 * 描  述：
 */
public class TravelScreenPopup extends PopupWindow {

    private View mRoot;
    private ListView listView;
    private List<FilterCollection> listData;
    private List<FilterCollection> temp = new ArrayList<>();
    private AbsBaseListAdapter<FilterCollection> listAdapter;
    private SeekBar seekDays;
    private SeekBar seekPrices;
    private GridView gridPrice;
    private GridView gridDays;
    private TravelPicesAdapter priceAdapter;
    private TravelPicesAdapter dayAdapter;
    private TextView personbudget_unlimited; //单人预算
    private TextView traveldays_unlimited;  //出行天数
    private LinearLayout goBack;
    private RelativeLayout rlReSet, rlComplete;
    private Activity activity;
   private int budgetChose = 1; //单人预算滑动标识
    private int daysCountChose = 1; //出行天数滑动标识
    private int choseData = 1;

    private TravelScreenBean screenBean;

    public TravelScreenPopup(Activity activity, TravelScreenBean scr)
    {
        this.activity = activity;
        this.screenBean = scr;
        initView();
        initData();
        initClick();
        init();
    }

    private void initClick()
    {
        ClickListener click = new ClickListener();
        rlReSet.setOnClickListener(click);
        rlComplete.setOnClickListener(click);
        goBack.setOnClickListener(click);
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.travel_filter_popuwindow);
        seekDays = (SeekBar) mRoot.findViewById(R.id.personbudget_seekbar_day);
        seekPrices = (SeekBar) mRoot.findViewById(R.id.personbudget_seekbar);
        gridPrice = (GridView) mRoot.findViewById(R.id.gridView_prices);
        gridDays = (GridView) mRoot.findViewById(R.id.gridView_day);
        goBack = (LinearLayout) mRoot.findViewById(R.id.panel_back);
        rlReSet = (RelativeLayout) mRoot.findViewById(R.id.rl_reset_chose);
        rlComplete = (RelativeLayout) mRoot.findViewById(R.id.rl_seach_chose);
        personbudget_unlimited = (TextView) mRoot.findViewById(R.id.personbudget_unlimited);
        traveldays_unlimited = (TextView) mRoot.findViewById(R.id.traveldays_unlimited);
        priceAdapter = new TravelPicesAdapter(activity, SearchBarInfo.priceName);
        dayAdapter = new TravelPicesAdapter(activity, SearchBarInfo.daysName);
        gridPrice.setAdapter(priceAdapter);
        gridDays.setAdapter(dayAdapter);
        listView = (ListView) mRoot.findViewById(R.id.listView);
        seekBarInfo();
    }

    private int   priceProgressValue ;

    private  int travelDaysProgressValue;

    public void setPriceProgressValue(int priceProgressValue,int travelDaysProgressValue)
    {

        this.priceProgressValue = priceProgressValue;

        this.travelDaysProgressValue = travelDaysProgressValue;

        proHandler.sendEmptyMessageDelayed(0,500);

    }

    private Handler proHandler = new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            seekPrices.setProgress(priceProgressValue);

            seekDays.setProgress(travelDaysProgressValue);


        }
    };

    /**
     * 改变滚动条信息
     */
    private void seekBarInfo()
    {
        //单人预算
        seekPrices.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if (i >=  SearchBarInfo.prices[1]) {
                    budgetChose = 2;
                }

                SearchBarInfo.calBudgetPrice(i,seekBar,screenBean,personbudget_unlimited,priceAdapter);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        gridPrice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                if(position > 0){
                    budgetChose = 2;
                }

                SearchBarInfo.clickCalBudgetPrice(position,seekPrices,screenBean,personbudget_unlimited,priceAdapter);
            }
        });
        //出行天数
        seekDays.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {

                if (i >= SearchBarInfo.prices[1]) {
                    daysCountChose = 2;
                }

                SearchBarInfo.calTravelDays(i,seekBar,screenBean,traveldays_unlimited,dayAdapter);

            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        gridDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position >0){
                    daysCountChose = 2;
                }
                SearchBarInfo.clickCalTravelDays(position,seekDays,screenBean,traveldays_unlimited,dayAdapter);
            }
        });
    }




    private void initData()
    {
        setListAdapter();
        loadListData(listData);

    }

    protected void loadListData(List<FilterCollection> listData)
    {
        SimpleRequest simpleRequest = new SimpleRequest(getServiceName());
        simpleRequest.setParam(getParam());
        simpleRequest.sendRequest(new FilterCallBack(listData));

    }

    protected void loadListData2(List<FilterCollection> listData)
    {
        SimpleRequest request = new SimpleRequest(getServiceName());
        request.setParam(getParam2());
        request.sendRequest(new FilterCallBack2(listData));
    }

    @NonNull
    protected String getServiceName()
    {
        return "QueryTravelBaseInfo";
    }




    class FilterCallBack2 extends HttpCallBackImpl {
        private List<FilterCollection> listData;

        public FilterCallBack2(List<FilterCollection> listData)
        {
            super();
            this.listData = listData;
        }

        @Override
        public void onSuccess(Object o)
        {
            List<Map> travelChild = JSONArray.parseArray(String.valueOf(o), Map.class);
            List<FilterType> filterTypes = new ArrayList<>();
            for (int i = 0; i < travelChild.size(); i++) {
                FilterType filterType = new FilterType();
                filterType.setChildType(travelChild.get(i).get("objType") + "");
                filterType.setName(travelChild.get(i).get("objName") + "");
                filterType.setCode(travelChild.get(i).get("objId") + "");
                filterType.setType("3");
                filterTypes.add(filterType);
            }
            FilterCollection fcl = new FilterCollection();
            fcl.setType("3");
            fcl.setName("特色项目");
            fcl.setChildList(filterTypes);
            addList(fcl);
//            initList(orgList);
        }


        @Override
        public void onFailed(Object o)
        {
            initList(null);
        }
    }

    class FilterCallBack extends HttpCallBackImpl {
        private List<FilterCollection> listData;

        public FilterCallBack(List<FilterCollection> listData)
        {
            super();
            this.listData = listData;
        }

        @Override
        public void onSuccess(Object o)
        {
            List<Map> travelChild = JSONArray.parseArray(String.valueOf(o), Map.class);
            List<FilterType> filterTypes = new ArrayList<>();
            for (int i = 0; i < travelChild.size(); i++) {
                FilterType filterType = new FilterType();
                filterType.setChildType(travelChild.get(i).get("objType") + "");
                filterType.setName(travelChild.get(i).get("objName") + "");
                filterType.setCode(travelChild.get(i).get("objId") + "");
                filterType.setType("2");
                filterTypes.add(filterType);
            }
            FilterCollection fcl = new FilterCollection();
            fcl.setType("2");
            fcl.setName("供应商");
            fcl.setChildList(filterTypes);
            addList(fcl);
//            loadListData2(listData);
//            initList(orgList);
        }


        @Override
        public void onFailed(Object o)
        {
            initList(null);
        }
    }

    private void addList(FilterCollection filterCollection)
    {
        List<FilterCollection> fcll = new ArrayList<>();
        fcll.add(filterCollection);
        listData.addAll(fcll);
//        addFirstItem();
        reset();
    }


    private void initList(List<FilterCollection> list)
    {
        listData.clear();
        if (list != null && list.size() > 0) {
            listData.addAll(list);
        }
//        addFirstItem();
        reset();
    }

    protected Map<String, Object> getParam()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("type", 2);
        return param;
    }

    protected Map<String, Object> getParam2()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("type", 3);
        return param;
    }

    private void updateTemp()
    {
        copyList(listData, temp);
    }

    private void copyList(List<FilterCollection> orgList, List<FilterCollection> aimList)
    {
        aimList.clear();
        for (int i = 0; i < orgList.size(); i++) {
            aimList.add(orgList.get(i).copy());
        }
    }

    private void setListAdapter()
    {
        listData = new ArrayList<>();
        listAdapter = new ListAdapter(listData);
        listView.setAdapter(listAdapter);
    }

    class ListAdapter extends AbsBaseListAdapter<FilterCollection> {

        public ListAdapter(List<FilterCollection> list)
        {
            super(list);
        }

        @Override
        public int getViewTypeCount()
        {
            return super.getViewTypeCount() + 2;
        }

        @Override
        public int getItemViewType(int position)
        {
            FilterCollection item = getList().get(position);
            if ("评分".equals(item.getName())) {
                return 1;
            } else if ("早餐/房型".equals(item.getName())) {
                return 2;
            } else if ("public".equals(item.getType())) {
                return 3;
            } else {
                return 0;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return super.getView(position, convertView, parent);
        }

        @Override
        protected Holder getHolder(int position)
        {
            TravelListHolder listHolder = new TravelListHolder(R.layout.travel_filter_pop_list_item);
            listHolder.setMaxLine(getMaxLine());

            if (1 == getItemViewType(position)) {
                listHolder.setGridParam(4, UiUtils.dp2px(15));
            } else if (2 == getItemViewType(position)) {
                listHolder.setGridParam(4, UiUtils.dp2px(15));
            } else if (3 == getItemViewType(position)) {
                listHolder.hideTextView();
            } else {

            }
            return listHolder;
        }
    }

    /**
     * 每种类型显示最多行数
     *
     * @return
     */
    protected int getMaxLine()
    {
        return 2;
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
        this.setFocusable(false);
        this.setTouchable(true);
        setOutsideTouchable(true);
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            switch (v.getId()) {
                case R.id.panel_back:
                    dismiss();
                    back();
                    break;
                case R.id.rl_seach_chose:
                    confirm(v);
                    break;
                case R.id.rl_reset_chose:
                    reset();
                    break;
            }
        }

    }

    /**
     * 重置
     */
    protected void reset()
    {
        resetList();
        notifyDataChanged();
        seekPrices.setProgress(0);
        seekDays.setProgress(0);
        screenBean.setDaysCount(0 + "");
        screenBean.setDaysCountMax(Integer.MAX_VALUE + "");
        screenBean.setSinglBudget(0 + "");
        screenBean.setSinglBudgetMax(Integer.MAX_VALUE + "");
        daysCountChose = 1; //单人预算恢复默认
        budgetChose = 1;    //出行天数恢复默认

    }

    private void notifyDataChanged()
    {
        listAdapter.notifyDataSetChanged();
    }

    private void resetList()
    {
        for (int i = 0; i < listData.size(); i++) {
            List<FilterType> list = listData.get(i).getChildList();
            for (int j = 0; j < list.size(); j++) {
                FilterType type = list.get(j);
                if (j == 0) {
                    type.setSelected(true);
                } else {
                    type.setSelected(false);
                }
            }
        }
    }

    /**
     * 确定
     */
    private void confirm(View view)
    {
//        updateTemp();
        List<FilterType> list = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {
            FilterCollection collection = listData.get(i);
            for (int j = 0; j < collection.getChildList().size(); j++) {
                FilterType type = collection.getChildList().get(j);
                if (type.isSelected() && !"不限".equals(type.getName())) {
                    list.add(type);
                    choseData = 2;
                } else if ("不限".equals(type.getName()) && type.isSelected()) {
                    choseData = 1;
                }
            }
        }
        String agentIds = "";
        String lables = "";
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getType().equals("2")) {//供应商
                for (int j = 0; j < listData.get(i).getChildList().size(); j++) {
                    FilterType type = listData.get(i).getChildList().get(j);
                    if (type.isSelected() && !"不限".equals(type.getName())) {
                        agentIds += type.getCode() + ",";
                        choseData = 2;
                    }
                }
            } else if (listData.get(i).getType().equals("3")) {
                for (int j = 0; j < listData.get(i).getChildList().size(); j++) {
                    FilterType type = listData.get(i).getChildList().get(j);
                    if (type.isSelected() && !"不限".equals(type.getName())) {
                        lables += type.getCode() + ",";
                        choseData = 2;
                    }
                }
            }
        }

        if ("".equals(agentIds)) {
            screenBean.setAgentIds("");
        } else {
            screenBean.setAgentIds(agentIds.substring(0, agentIds.lastIndexOf(",")));
        }

        if ("".equals(lables)) {
            screenBean.setLabelIds("");
        } else {

            screenBean.setLabelIds(lables.substring(0, lables.lastIndexOf(",")));
        }

        LogUtil.i("dayscount " + daysCountChose + " ww  " + budgetChose + " q  " + choseData);
        if (daysCountChose == 2 || budgetChose == 2 || choseData == 2) {
            screenBean.setSelect(true);
        } else if (daysCountChose == 1 && budgetChose == 1 && choseData == 1) {
            screenBean.setSelect(false);
        }

        LogUtil.i("qwer " + JSON.toJSONString(screenBean));
        setOnClick.getDataInfo(screenBean);
        setDate(list);
        dismiss();
    }

    public void setDate(List<FilterType> list)
    {
        screenBean.setFilterTypes(list);
        TravelLineListActivity.setTravelScreenBean(screenBean);
        screenBean = new TravelScreenBean();
    }

    private List<FilterType> subList(List<FilterType> orgList, int start, int end)
    {
        List<FilterType> list = new ArrayList<>();
        if (orgList != null && orgList.size() > 0) {
            for (int i = start; i < end; i++) {
                if (orgList.get(i).isSelected() && !"不限".equals(orgList.get(i).getName())) {
                    list.add(orgList.get(i));
                }
            }
        }
        return list;
    }

    public interface ConfigInfo {
        void getDataInfo(TravelScreenBean screenBean);
    }

    public ConfigInfo setOnClick;

    public void setOnInfoClick(ConfigInfo setOnClick)
    {
        this.setOnClick = setOnClick;
    }


    private void back()
    {
        listAdapter.notifyDataSetChanged();
    }
}
