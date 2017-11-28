package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.NoRollGridView;
import com.yzb.card.king.ui.appwidget.SideBar;
import com.yzb.card.king.ui.appwidget.popup.presenter.QueryCitysPresenter;
import com.yzb.card.king.ui.appwidget.popup.presenter.impl.QueryCityPresenterImpl;
import com.yzb.card.king.ui.appwidget.popup.view.QueryCityView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.other.bean.IPlace;
import com.yzb.card.king.ui.other.bean.Place;
import com.yzb.card.king.ui.other.listeners.SimpleWatcher;
import com.yzb.card.king.ui.ticket.activity.search.CharacterParser;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SharedPreferencesUtils;
import com.yzb.card.king.util.UiUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 国内国外地点搜索页；
 */
public class SelectPlaceActivity extends BaseActivity implements QueryCityView, View.OnClickListener {
    public static final String CITY_ID = "cityId";

    public static final String SIGN = "sign"; //页面标记

    private final int HISTORY_MAX_SIZE = 10;

    private String currentRegion = AppConstant.select_domestic_region_id;

    private LinearLayout ll_tab;

    private ExpandableListView expandableListView;

    private LinearLayout select_abroad;

    private LinearLayout select_domestic;

    private LinearLayout ll_history;

    private TextView select_domestic_textView;

    private TextView select_domestic_textView1;

    private TextView select_abroad_textView;

    private TextView select_abroad_textView1;

    private EditText filter_edit;

    private Map<String, List<IPlace>> cities = new HashMap<>();

    private List<City> localCityList = null;

    private MyBaseAdapter expandAdapter;

    private QueryCitysPresenter queryCitysPresenter;

    private SideBar sideBar;

    private View top;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;

        super.onCreate(savedInstanceState);

        queryCitysPresenter = getPresenter();

        initView();

        initListener();

        initData();
    }

    public QueryCitysPresenter getPresenter()
    {
        return new QueryCityPresenterImpl(this);
    }

    /**
     * 初始化监听事件
     */
    private void initListener()
    {
        //根据输入框输入值的改变来过滤搜索
        filter_edit.addTextChangedListener(new SimpleWatcher() {

            private CharacterParser parser;

            @Override
            protected void init()
            {
                parser = CharacterParser.getInstance();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                StringBuilder sb = new StringBuilder();
                //将字符串转成拼音
                for (int i = 0; i < s.length(); i++) {
                    String string = s.subSequence(i, i + 1).toString();
                    if (RegexUtil.validChinese(string)) {
                        string = parser.convert(string);
                    }
                    sb.append(string);
                }
                queryCity(currentRegion, sb.toString());
            }

        });

        sideBar.setOnLetterTouchListener(new SideBar.OnLetterTouchListener() {
            @Override
            public void onLetterTouch(String letter, int position)
            {
                if (expandAdapter != null)
                    expandableListView.setSelectedGroup(position);
            }

            @Override
            public void onActionUp()
            {
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                return true;//屏蔽点击收缩事件
            }

        });
        select_domestic.setOnClickListener(this);

        select_abroad.setOnClickListener(this);
    }


    private void queryCity(String currentRegion, String key)
    {
        queryCitysPresenter.queryCity(currentRegion, key);
    }

    @Override
    public void onBackPressed()
    {
        CitySelectManager.getInstance().clearData();
        super.onBackPressed();
    }

    public void goBack(View view)
    {
        CitySelectManager.getInstance().clearData();
        setResult(AppConstant.RES_HOME_PAGE);
        finish();
    }

    private void initView()
    {
        setContentView(R.layout.activity_select_place);

        top = findViewById(R.id.llTop);

        ll_history = (LinearLayout) findViewById(R.id.ll_history);

        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        filter_edit = (EditText) findViewById(R.id.filter_edit);

        select_domestic_textView = (TextView) findViewById(R.id.select_domestic_textView);

        select_domestic_textView1 = (TextView) findViewById(R.id.select_domestic_textView1);

        select_abroad_textView = (TextView) findViewById(R.id.select_abroad_textView);

        select_abroad_textView1 = (TextView) findViewById(R.id.select_abroad_textView1);

        select_domestic = (LinearLayout) findViewById(R.id.select_domestic);

        select_abroad = (LinearLayout) findViewById(R.id.select_abroad);

        sideBar = ((SideBar) findViewById(R.id.sidrbar));

        hideHistoryAndTab();
    }

    private void hideHistoryAndTab()
    {
        if (hideTop()) {
            top.setVisibility(View.GONE);
        }
    }

    protected boolean hideTop()
    {
        return false;
    }


    private void initData()
    {
        String JSONString = (String) SharedPreferencesUtils.getParam(this, "localCityList", "[]"
                , Integer.toString(GlobalVariable.industryId));

        localCityList = JSON.parseArray(JSONString, City.class);

        initHistory();

        queryAllCity(AppConstant.select_domestic_region_id);
    }

    /**
     * 初始化listView数据
     */
    private void initList()
    {
        String[] tempLetters = getResources().getStringArray(R.array.letters);

        Map<String, List<IPlace>> lettersDivider = new HashMap<>();

        List<String> letters = new ArrayList<>();

        for (int j = 0; j < tempLetters.length; j++) {
            String key = tempLetters[j];

            List<IPlace> list;

            if (j == 0) {
                list = new ArrayList<>();

                list.add(getPositionedPlace());

            } else if (j == 1) {
                list = cities.get(getString(R.string.hot_info));

            } else {
                list = cities.get(key.toLowerCase());
            }

            if (list != null && list.size() > 0) {
                lettersDivider.put(key, list);

                letters.add(key);
            }
        }

        sideBar.setShowString(letters);

        if (expandAdapter == null) {

            expandAdapter = new MyBaseAdapter(letters, lettersDivider);

            expandableListView.setAdapter(expandAdapter);

        } else {

            expandAdapter.addNewMoreData(letters, lettersDivider);

        }
    }

    private void initList(Map<String, List<IPlace>> places)
    {
        //打印数据
        Iterator<Map.Entry<String, List<IPlace>>> it = places.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, List<IPlace>> entry = it.next();

            String keyStr = entry.getKey();

            List<IPlace> valueList = entry.getValue();

            if(valueList != null) {
                int size = valueList.size();


                for (int a = 0; a < size; a++) {

                    IPlace iPlace = valueList.get(a);


                }
            }


        }


        cities.clear();

        if (places != null) {
            for (String key : places.keySet()) {
                cities.put(key.toLowerCase(), places.get(key));
            }
        }
        initList();
    }

    protected IPlace getPositionedPlace()
    {
        return new City(positionedCity.cityId, positionedCity.cityName);
    }

    /**
     * 初始化历史栏
     */
    protected void initHistory()
    {
        if (localCityList != null && localCityList.size() > 0) {
            for (int i = 0; i < localCityList.size(); i++) {
                IPlace city = localCityList.get(i);

                View itemView = getHistoryItemView(city);

                ll_history.addView(itemView);
            }
        }
    }

    /**
     * 选择城市后触发的事件
     */
    private class OnCitySelectListener implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            Place city = (Place) v.getTag();

            if (!TextUtils.isEmpty(city.getPlaceName())) {

                queryCitysPresenter.sumHotCity(GlobalVariable.industryId, city.getPlaceId());

                setManagerData(city);

                addHistory(city);

                saveLastCity(city);

                Intent intent = new Intent();

                intent.putExtra("city", (Serializable) city);

                setResult(RESULT_OK, intent);

                finish();
            }else{

                //检查选择的城市，如果定位城市数据不存在，则说明没有定位到数据
                if(TextUtils.isEmpty(city.getPlaceName()) ){

                    showNoCancelPDialog(R.string.is_located);
                    //重新开启定位功能
                    GlobalApp.getInstance().setOnCityChangeListeners(onCityChangeListener);

                    GlobalApp.getInstance().toReposition();

                }

            }
        }
    }

    private GlobalApp.OnCityChangeListener onCityChangeListener = new GlobalApp.OnCityChangeListener() {
        @Override
        public void onCityChange(Location city)
        {
            initData();

            GlobalApp.getInstance().removeListener(onCityChangeListener);

            closePDialog();
        }
    };

    private void saveLastCity(Place city)
    {
        SharePrefUtil.saveToSp(this, "lastCity", JSON.toJSONString(city));
    }

    /**
     * 填充一个历史条目
     *
     * @param city
     * @return
     */
    private View getHistoryItemView(IPlace city)
    {
        LinearLayout history_item = (LinearLayout) UiUtils.inflate(R.layout.history_item);

        TextView tv = (TextView) history_item.findViewById(R.id.textView);

        tv.setText(city.getPlaceName());

        history_item.setTag(city);

        history_item.setOnClickListener(new OnCitySelectListener());

        return history_item;
    }

    private void setManagerData(IPlace city)
    {
        CitySelectManager.getInstance().setPlace(city);
    }

    /**
     * 添加历史记录
     *
     * @param place
     */
    protected void addHistory(IPlace place)
    {
        City city = (City) place;

        for (int i = 0; i < localCityList.size(); i++) {
            if (city.getPlaceId().equals(localCityList.get(i).getPlaceId())) {
                localCityList.remove(i);
            }
        }

        localCityList.add(0, city);

        keepHistorySize();
    }

    /**
     * 保证历史记录不超过最大数
     */
    private void keepHistorySize()
    {
        if (localCityList.size() > HISTORY_MAX_SIZE) {
            localCityList.remove(localCityList.size() - 1);
            keepHistorySize();
        }
    }

    /**
     * 将历史记录保存到本地
     */
    protected void saveHistory()
    {
        SharedPreferencesUtils.setParam(SelectPlaceActivity.this, "localCityList",
                JSON.toJSONString(localCityList), Integer.toString(GlobalVariable.industryId));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        saveHistory();
    }

    public void innerCity(View v)
    {
        refreshTab(v);
    }

    public void foreignCity(View v)
    {
        refreshTab(v);
    }

    private void refreshTab(View v)
    {
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            if (i == 1) continue;

            LinearLayout ll = (LinearLayout) ll_tab.getChildAt(i);

            TextView child0 = (TextView) ll.getChildAt(0);

            TextView child1 = (TextView) ll.getChildAt(1);

            child0.setEnabled(v == ll);

            child1.setEnabled(!child1.isEnabled());
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.select_domestic:
                selectDomestic();
                break;
            case R.id.select_abroad:
                selectAbroad();
                break;
        }
    }

    /**
     * 选择国内
     */
    private void selectDomestic()
    {
        select_domestic_textView.setTextColor(getResources().getColor(R.color.ticket_red));

        select_domestic_textView1.setBackgroundColor(getResources().getColor(R.color.ticket_red));

        select_abroad_textView.setTextColor(getResources().getColor(R.color.ticket_gray1));

        select_abroad_textView1.setBackgroundColor(getResources().getColor(R.color.ticket_gray1));

        currentRegion = AppConstant.select_domestic_region_id;

        queryAllCity(AppConstant.select_domestic_region_id);
    }

    /**
     * 选择国外
     */
    private void selectAbroad()
    {
        select_abroad_textView.setTextColor(getResources().getColor(R.color.ticket_red));

        select_abroad_textView1.setBackgroundColor(getResources().getColor(R.color.ticket_red));

        select_domestic_textView.setTextColor(getResources().getColor(R.color.ticket_gray1));

        select_domestic_textView1.setBackgroundColor(getResources().getColor(R.color.ticket_gray1));

        currentRegion = AppConstant.select_abroad_region_id;

        queryAllCity(AppConstant.select_abroad_region_id);
    }


    class MyBaseAdapter extends BaseExpandableListAdapter {

        private List<String> letters;

        private Map<String, List<IPlace>> lettersDivider = new HashMap<>();

        public MyBaseAdapter(List<String> letters, Map<String, List<IPlace>> lettersDivider)
        {
            this.letters = letters;

            this.lettersDivider = lettersDivider;
        }

        public void addNewMoreData(List<String> letters, Map<String, List<IPlace>> lettersDivider)
        {

            this.letters.clear();

            this.letters.addAll(letters);

            this.lettersDivider.clear();

            this.lettersDivider.putAll(lettersDivider);

            notifyDataSetChanged();
        }

        @Override
        public int getGroupCount()
        {
            return letters == null ? 0 : letters.size();
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            String letter = letters.get(groupPosition);

            List<IPlace> list = lettersDivider.get(letter);

            return list == null ? 0 : 1;
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return letters.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return lettersDivider.get(letters.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        @Override
        public boolean hasStableIds()
        {
            return false;
        }

        @Override
        public int getChildType(int groupPosition, int childPosition)
        {
            String letter = letters.get(groupPosition);

            if (AppConstant.TICKET_LOCATION.equals(letter)) {

                return 0;

            } else if (AppConstant.HOT.equals(letter)) {

                return 1;

            } else {

                return 2;
            }
        }

        @Override
        public int getChildTypeCount()
        {
            return 3;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        {
            if (!isExpanded) {
                expandableListView.expandGroup(groupPosition);//展开组
            }
            if (convertView == null) {

                convertView = View.inflate(SelectPlaceActivity.this, R.layout.adapter_group_view, null);

                convertView.setTag(new GroupViewHolder(convertView));
            }

            GroupViewHolder holder = (GroupViewHolder) convertView.getTag();

            holder.tViewGroupName.setText(letters.get(groupPosition));

            return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent)
        {

            int currentType = getChildType(groupPosition, childPosition);

            List<IPlace> cityList = lettersDivider.get(letters.get(groupPosition));

            String zimu = letters.get(groupPosition);

            IPlace city = cityList.get(childPosition);

            if (currentType == 0) {//定位数据

                convertView = getPositionView(convertView, city);

            } else {

                convertView = getCityView(cityList,zimu);

            }
            return convertView;
        }

        private View getCityView(List<IPlace> cities, final String zimu)
        {
            View convertView = View.inflate(SelectPlaceActivity.this, R.layout.adapter_child_view, null);

            NoRollGridView gridView = (NoRollGridView) convertView.findViewById(R.id.gridView);

            ListAdapter adapter = new AbsBaseListAdapter<IPlace>(cities) {
                @Override
                protected Holder getHolder(int position)
                {
                    return new GridHolder(zimu);
                }
            };

            gridView.setAdapter(adapter);

            return convertView;
        }

        private View getPositionView(View convertView, IPlace city)
        {
            if (convertView == null) {

                convertView = UiUtils.inflate(R.layout.adapter_position_view);

                convertView.setTag(new PositionViewHolder(convertView));
            }

            PositionViewHolder holder = (PositionViewHolder) convertView.getTag();

            holder.tv_position.setText(TextUtils.isEmpty(city.getPlaceName()) ? getString(R.string.locate_failure_reposition)
                    : city.getPlaceName());

            holder.tv_position.setTag(city);


            holder.tv_position.setOnClickListener(new OnCitySelectListener());

            return convertView;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        class PositionViewHolder {
            TextView tv_position;

            public PositionViewHolder(View parent)
            {
                tv_position = (TextView) parent.findViewById(R.id.tv);
            }
        }

        class GroupViewHolder {

            TextView tViewGroupName;

            public GroupViewHolder(View parent)
            {
                tViewGroupName = (TextView) parent.findViewById(R.id.tView_group_name);
            }

        }

        class GridHolder extends Holder<IPlace> {

            private TextView textView;

            private String z;

            public GridHolder(String z){

                this.z = z;
            }

            @Override
            public View initView()
            {
                View view = UiUtils.inflate(R.layout.city_grid_item);

                textView = (TextView) view.findViewById(R.id.textView);

                textView.setOnClickListener(new OnCitySelectListener());

                return view;
            }

            @Override
            public void refreshView(IPlace data)
            {
                textView.setText(data.getPlaceName());
                textView.setTag(data);

            }
        }
    }

    private void queryAllCity(String type)
    {
        queryCitysPresenter.queryCity(type, "");
    }

    @Override
    public void onGetCitysSucess(Object data)
    {
        cities.clear();

        initList((Map<String, List<IPlace>>) data);

    }

    @Override
    public void onGetCitysFail(String failMsg)
    {
        toastCustom(R.string.app_load_data_error);

        initList(null);
    }

    @Override
    public void onQueryFail()
    {
        UiUtils.shortToast("查询失败");
    }

    @Override
    public void onQuerySuccess(Map<String, List<IPlace>> places)
    {
        initList(places);
    }


}
