package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.FilterCollection;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.appwidget.HotelFilterView;
import com.yzb.card.king.ui.hotel.holder.ListHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：酒店筛选页
 * 作者：殷曙光
 * 日期：2016/10/25 17:01
 */
public class HotelFilterPop extends PopupWindow
{
    public static String cityId;

    private View mRoot;
    private ListView listView;
    private TextView tvConfirm;
    private TextView tvReset;
    private TextView tvCancel;
    private List<FilterCollection> listData;
    private List<FilterCollection> temp = new ArrayList<>();
    private AbsBaseListAdapter<FilterCollection> listAdapter;
    private View headerLeft;
    private ImageView headerLeftImage;
    private TextView headerTitle;
    private List<FilterType> listBreakfast;
    private List<FilterType> listPoint;
    private List<FilterCollection> orgList;
    private ArrayList<FilterType> listPublic;

    public HotelFilterPop()
    {
        initView();
        initListener();
        initData();
        init();
    }


    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.hotel_filter_pop);

        headerLeft = mRoot.findViewById(R.id.headerLeft);
        headerLeftImage = (ImageView) mRoot.findViewById(R.id.headerLeftImage);
        headerTitle = (TextView) mRoot.findViewById(R.id.headerTitle);

        listView = (ListView) mRoot.findViewById(R.id.listView);
        tvConfirm = (TextView) mRoot.findViewById(R.id.tvConfirm);
        tvReset = (TextView) mRoot.findViewById(R.id.tvReset);
        tvCancel = (TextView) mRoot.findViewById(R.id.tvCancel);
    }

    private void initListener()
    {
        ClickListener listener = new ClickListener();
        headerLeft.setOnClickListener(listener);
        tvConfirm.setOnClickListener(listener);
        tvReset.setOnClickListener(listener);
        tvCancel.setOnClickListener(listener);
    }

    protected void addFirstItem()
    {
        for (int i = 0; i < listData.size(); i++)
        {
            listData.get(i).getChildList().add(0, new FilterType("不限", "0", "0"));
        }
    }

    private void initData()
    {
        setHeader();
        setListAdapter();
        loadListData(listData);
    }

    private void setHeader()
    {
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerLeftImage.setVisibility(View.VISIBLE);
        headerTitle.setText(getTitle());
    }

    @NonNull
    protected String getTitle()
    {
        return "筛选";
    }

    protected void loadListData(List<FilterCollection> listData)
    {
        SimpleRequest simpleRequest = new SimpleRequest(getServiceName());
        simpleRequest.setParam(getParam());
        simpleRequest.sendRequest(new FilterCallBack(listData));
    }

    @NonNull
    protected String getServiceName()
    {
        return "ScreenQuery";
    }

    class FilterCallBack extends HttpCallBackImpl
    {
        private List<FilterCollection> listData;

        public FilterCallBack(List<FilterCollection> listData)
        {
            super();
            this.listData = listData;
        }

        @Override
        public void onSuccess(Object o)
        {
            orgList = JSONArray.parseArray(String.valueOf(o), FilterCollection.class);
            initList(orgList);

        }

        private void initList(List<FilterCollection> list)
        {
            listData.clear();
            if (list != null && list.size() > 0)
            {
                listData.addAll(list);
            }
            addFirstItem();
            addOtherData(listData);
            reset();
        }

        @Override
        public void onFailed(Object o)
        {
            initList(null);
        }
    }

    protected void addOtherData(List<FilterCollection> listData)
    {
        addPublic(listData);
        addEvaluatePoint(listData);
        addBreakfastBed(listData);
    }

    protected Map<String, Object> getParam()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("industryId", 8);
        param.put("arrCityId",cityId);
        return param;
    }

    private void updateTemp()
    {
        copyList(listData, temp);
    }

    private void copyList(List<FilterCollection> orgList, List<FilterCollection> aimList)
    {
        aimList.clear();
        for (int i = 0; i < orgList.size(); i++)
        {
            aimList.add(orgList.get(i).copy());
        }
    }

    private void addBreakfastBed(List<FilterCollection> listData)
    {
        FilterCollection breakBedCollection = new FilterCollection();
        breakBedCollection.setName("早餐/房型");
        listBreakfast = new ArrayList<>();
        listBreakfast.add(new FilterType("不限", "breakfast", "0"));
        listBreakfast.add(new FilterType("含早", "breakfast", "1"));
        listBreakfast.add(new FilterType("单份早餐", "breakfast", "2"));
        listBreakfast.add(new FilterType("双份早餐", "breakfast", "3"));
        listBreakfast.add(new FilterType("不限", "bed", "0"));
        listBreakfast.add(new FilterType("大床", "bed", "1"));
        listBreakfast.add(new FilterType("双床", "bed", "2"));
        listBreakfast.add(new FilterType("家庭房", "bed", "3"));
        breakBedCollection.setChildList(listBreakfast);
        listData.add(breakBedCollection);
    }

    private void addEvaluatePoint(List<FilterCollection> listData)
    {
        FilterCollection collection = new FilterCollection();
        collection.setName("评分");
        listPoint = new ArrayList<>();
        listPoint.add(new FilterType("不限", "point", "0"));
        listPoint.add(new FilterType("9以上", "point", "9"));
        listPoint.add(new FilterType("8以上", "point", "8"));
        listPoint.add(new FilterType("7以上", "point", "7"));
        collection.setChildList(listPoint);
        listData.add(collection);
    }

    private void addPublic(List<FilterCollection> listData)
    {
        FilterCollection collection = new FilterCollection();
        collection.setType("public");
        listPublic = new ArrayList<>();
        listPublic.add(new FilterType("大床房", "public","bed", "1"));
        listPublic.add(new FilterType("含早", "public","breakfast", "1"));
        listPublic.add(new FilterType("免费取消", "public","cancel", "1"));
        collection.setChildList(listPublic);
        listData.add(0, collection);
    }

    private void setListAdapter()
    {
        listData = new ArrayList<>();
        listAdapter = new ListAdapter(listData);
        listView.setAdapter(listAdapter);
    }

    class ListAdapter extends AbsBaseListAdapter<FilterCollection>
    {

        public ListAdapter(List<FilterCollection> list)
        {
            super(list);
        }

        @Override
        public int getViewTypeCount()
        {
            return super.getViewTypeCount() + 3;
        }

        @Override
        public int getItemViewType(int position)
        {
            FilterCollection item = getList().get(position);
            if ("评分".equals(item.getName()))
            {
                return 1;
            } else if ("早餐/房型".equals(item.getName()))
            {
                return 2;
            } else if ("public".equals(item.getType()))
            {
                return 3;
            } else
            {
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
            ListHolder listHolder = new ListHolder(R.layout.hotel_filter_pop_list_item);
            listHolder.setMaxLine(getMaxLine());

            if (1 == getItemViewType(position))
            {
                listHolder.setGridParam(4, UiUtils.dp2px(10));
            } else if (2 == getItemViewType(position))
            {
                listHolder.setGridParam(4, UiUtils.dp2px(10));
            } else if (3 == getItemViewType(position))
            {
                listHolder.hideTextView();
            } else
            {

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
        this.setTouchable(true);
        this.setFocusable(true);
        setOutsideTouchable(true);
    }

    class ClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.headerLeft:
                    dismiss();
                    back();
                    break;
                case R.id.tvConfirm:
                    confirm();
                    break;
                case R.id.tvReset:
                    reset();
                    break;
                case R.id.tvCancel:
                    dismiss();
                    back();
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
    }

    private void notifyDataChanged()
    {
        listAdapter.notifyDataSetChanged();
    }

    private void resetList()
    {
        for (int i = 0; i < listData.size(); i++)
        {
            List<FilterType> list = listData.get(i).getChildList();
            for (int j = 0; j < list.size(); j++)
            {
                FilterType type = list.get(j);
                if (j == 0)
                {
                    type.setSelected(true);
                } else
                {
                    type.setSelected(false);
                }
            }
        }
        resetPublicList();
        //早餐房型第二排第一个重置
        resetBedList();
    }

    private void resetPublicList()
    {
        if (listPublic != null)
        {
            listPublic.get(0).setSelected(false);
        }
    }

    protected void resetBedList()
    {
        listData.get(listData.size() - 1).getChildList().get(4).setSelected(true);
    }

    /**
     * 确定
     */
    private void confirm()
    {
//        updateTemp();
        List<FilterType> list = new ArrayList<>();
        if (orgList != null)
        {
            for (int i = 0; i < orgList.size(); i++)
            {
                FilterCollection collection = orgList.get(i);
                for (int j = 0; j < collection.getChildList().size(); j++)
                {
                    FilterType type = collection.getChildList().get(j);
                    if (type.isSelected() && !"不限".equals(type.getName()))
                    {
                        list.add(type);
                    }
                }
            }
        }
        setDate(list);
        dismiss();
    }

    protected void setDate(List<FilterType> list)
    {
        HotelFilterView.data.setList(listPublic,list, getPointList(),
                addPublicItem(getBreakfastList(), "breakfast"), addPublicItem(getBedList(), "bed"));

    }

    private List<FilterType> getPointList()
    {
        return listPoint;
    }

    private List<FilterType> getBedList()
    {

        return subList(listBreakfast, listBreakfast.size() / 2, listBreakfast.size());
    }

    private List<FilterType> getBreakfastList()
    {

        return subList(listBreakfast, 0, listBreakfast.size() / 2);
    }

    private List<FilterType> subList(List<FilterType> orgList, int start, int end)
    {
        List<FilterType> list = new ArrayList<>();
        if (orgList != null && orgList.size() > 0)
        {
            for (int i = start; i < end; i++)
            {
                if (orgList.get(i).isSelected() && !"不限".equals(orgList.get(i).getName()))
                {
                    list.add(orgList.get(i));
                }
            }
        }
        return list;
    }

    private List<FilterType> addPublicItem(List<FilterType> list, String type)
    {
        if (listPublic != null)
        {
            for (int i = 0; i < listPublic.size(); i++)
            {
                FilterType item = listPublic.get(i);
                if (item.isSelected() && item.getChildType().equals(type))
                {
                    if (!hasSameCode(list, item))
                    {
                        list.add(item);
                    }
                }
            }
        }

        return list;
    }

    private boolean hasSameCode(List<FilterType> list, FilterType item)
    {
        for (int j = 0; j < list.size(); j++)
        {
            if (list.get(j).getCode().equals(item.getCode()))
            {
                return true;
            }
        }
        return false;
    }

    private void back()
    {
//        copyList(temp,listData);
        listAdapter.notifyDataSetChanged();
    }
}
