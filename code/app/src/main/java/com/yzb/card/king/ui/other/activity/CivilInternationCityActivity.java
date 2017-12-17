package com.yzb.card.king.ui.other.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.SideBar;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.hotel.adapter.SingleConditionAdapter;
import com.yzb.card.king.ui.my.presenter.HotCityPresenter;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.ui.other.adapter.CityListAdapter;
import com.yzb.card.king.ui.other.adapter.DividerDecoration;
import com.yzb.card.king.ui.other.adapter.SearchCityByKeyAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 玉兵 on 2017/7/27.
 * <p>
 * <p>
 * 国内国际城市选择页面
 */
@ContentView(R.layout.activity_civil_internation_city)
public class CivilInternationCityActivity extends BaseActivity implements View.OnClickListener {

    public static final int CIVIINTERNATION_RESULTCODE = 5001;

    private RecyclerView recyclerView;

    @ViewInject(R.id.sidrbar)
    private SideBar sidrbar;

    @ViewInject(R.id.llSearch)
    private EditText etSearch;

    @ViewInject(R.id.llCity)
    private LinearLayout llCity;

    @ViewInject(R.id.hvSearchResult)
    private HeadFootRecyclerView hvSearchResult;

    private CityListWithHeadersAdapter adapter;

    private HashMap<String, Integer> letters = new HashMap<>();

    private TextView select_domestic_textView;

    private TextView select_domestic_textView1;

    private TextView select_abroad_textView;

    private TextView select_abroad_textView1;

    private LinearLayout select_domestic;

    private LinearLayout select_abroad;

    private NationalCountryPresenter nationCountryPresenter;

    private HotCityPresenter hotCityPresenterTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();

        llCity.setVisibility(View.VISIBLE);
        hvSearchResult.setVisibility(View.GONE);
    }

    private void initView() {

        findViewById(R.id.tvCancel).setOnClickListener(this);

        select_domestic = (LinearLayout) findViewById(R.id.select_domestic);

        select_domestic.setOnClickListener(this);

        select_abroad = (LinearLayout) findViewById(R.id.select_abroad);

        select_abroad.setOnClickListener(this);

        select_domestic_textView = (TextView) findViewById(R.id.select_domestic_textView);

        select_domestic_textView1 = (TextView) findViewById(R.id.select_domestic_textView1);

        select_abroad_textView = (TextView) findViewById(R.id.select_abroad_textView);

        select_abroad_textView1 = (TextView) findViewById(R.id.select_abroad_textView1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        sidrbar.setTextColor(getResources().getColor(R.color.color_436a8e));

        sidrbar.setOnLetterTouchListener(new SideBar.OnLetterTouchListener() {
            @Override
            public void onLetterTouch(String letter, int position) {
                if (letters.containsKey(letter)) {
                    recyclerView.scrollToPosition(letters.get(letter));
                }
            }

            @Override
            public void onActionUp() {
            }
        });

        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        adapter = new CityListWithHeadersAdapter();

        recyclerView.setAdapter(adapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        recyclerView.addItemDecoration(new DividerDecoration(this));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String keyStr = etSearch.getText().toString();

                if (!TextUtils.isEmpty(keyStr)) {

                    List<NationalCountryBean> searchTotalSearchList = nationCountryPresenter.dimSearchTotalCity(keyStr);

                    if (searchTotalSearchList != null) {

                        int size = searchTotalSearchList.size();

                        if (size > 0) {

                            if (llCity.getVisibility() == View.VISIBLE) {
                                hvSearchResult.setVisibility(View.VISIBLE);
                                llCity.setVisibility(View.GONE);
                            }
                            //加载搜索到的数据
                            initSearchResult(searchTotalSearchList);

                        } else {
                            if (llCity.getVisibility() == View.GONE) {
                                llCity.setVisibility(View.VISIBLE);
                                hvSearchResult.setVisibility(View.GONE);
                            }
                        }


                    } else {
                        if (llCity.getVisibility() == View.GONE) {
                            llCity.setVisibility(View.VISIBLE);
                            hvSearchResult.setVisibility(View.GONE);
                        }
                    }
                } else {
                    if (llCity.getVisibility() == View.GONE) {
                        llCity.setVisibility(View.VISIBLE);
                        hvSearchResult.setVisibility(View.GONE);
                    }
                }

            }
        });
    }

    private SearchCityByKeyAdapter hotelSearchResultAdapter;

    private void initSearchResult(List<NationalCountryBean> searchTotalSearchList) {

        if (hotelSearchResultAdapter == null) {

            hotelSearchResultAdapter = new SearchCityByKeyAdapter(CivilInternationCityActivity.this, searchTotalSearchList);

            hvSearchResult.setLayoutManager(new GridLayoutManager(this, 1));

            hotelSearchResultAdapter.setCityDataCallBack(dataCallBack);

            hvSearchResult.setAdapter(hotelSearchResultAdapter);

        } else {

            hotelSearchResultAdapter.addNewList(searchTotalSearchList);
        }

    }

    private SearchCityByKeyAdapter.CityDataCallBack dataCallBack = new SearchCityByKeyAdapter.CityDataCallBack() {
        @Override
        public void selectedCityItem(NationalCountryBean bean) {
            bundleDataFinsh(bean);
        }
    };

    private void initData() {

        hotCityPresenterTemp = new HotCityPresenter(null);

        nationCountryPresenter = new NationalCountryPresenter();

        selectDomestic();


    }

    private void loadCityToView(List<NationalCountryBean> civilDataList, int type) {

        letters.clear();
        //快捷导航索引
        HashMap<String, Integer> lettersTemp = new HashMap<>();

        char[] letterCharArray = new char[0];

        int position = 0;
        //收集字母
        for (NationalCountryBean bean : civilDataList) {

            String letter = String.valueOf(bean.getFirstSpell().charAt(0));

            //如果没有这个key则加入并把位置也加入
            if (!lettersTemp.containsKey(letter)) {

                lettersTemp.put(letter, position);

                char[] tepletterCharArray = new char[letterCharArray.length + 1];

                System.arraycopy(letterCharArray, 0, tepletterCharArray, 0, letterCharArray.length);

                tepletterCharArray[letterCharArray.length] = bean.getFirstSpell().charAt(0);

                letterCharArray = tepletterCharArray;
            }
            position++;
        }
        lettersTemp = null;
        //字母排序
        Arrays.sort(letterCharArray);

        ArrayList<String> customLetters = new ArrayList<>();
        //分类收集不同城市信息
        List<NationalCountryBean> sortLettersList = new ArrayList<NationalCountryBean>();

        for (char a : letterCharArray) {

            customLetters.add(String.valueOf(a).toUpperCase());

            //收集数据
            for (NationalCountryBean bean : civilDataList) {

                char firstLetter = bean.getFirstSpell().charAt(0);

                if (firstLetter == a) {

                    bean.setFirstLetter(String.valueOf(firstLetter).toUpperCase());

                    sortLettersList.add(bean);
                }

            }

        }

        letterCharArray = null;
        /**
         * 添加“当前”、“历史”、“热门”
         */
        if (type == 1) {
            customLetters.add(0, "当前");
            customLetters.add(1, "历史");
            customLetters.add(2, "热门");
        } else if (type == 2) {
            customLetters.add(0, "热门");
        }

        if (type == 1) {
            NationalCountryBean headBean = new NationalCountryBean();
            headBean.set_id(-1000);
            headBean.setFirstSpell("###");
            headBean.setFirstLetter("当前");
            sortLettersList.add(0, headBean);

            NationalCountryBean headBeanOne = new NationalCountryBean();
            headBeanOne.set_id(-1001);
            headBeanOne.setFirstSpell("@@@");
            headBeanOne.setFirstLetter("历史");
            sortLettersList.add(1, headBeanOne);

            NationalCountryBean headBeanTwo = new NationalCountryBean();
            headBeanTwo.set_id(-1002);
            headBeanTwo.setFirstSpell("$$$");
            headBeanTwo.setFirstLetter("热门");
            headBeanTwo.setType(type + "");
            sortLettersList.add(2, headBeanTwo);
        } else if (type == 2) {
            NationalCountryBean headBeanTwo = new NationalCountryBean();
            headBeanTwo.set_id(-1002);
            headBeanTwo.setFirstSpell("$$$");
            headBeanTwo.setFirstLetter("热门");
            headBeanTwo.setType(type + "");
            sortLettersList.add(0, headBeanTwo);
        }

        //收集索引首次出现的位置
        int sizeList = sortLettersList.size();

        for (int i = 0; i < sizeList; i++) {

            NationalCountryBean bean = sortLettersList.get(i);

            String firsSpellStr = bean.getFirstSpell();

            if ("###".equals(firsSpellStr) || "@@@".equals(firsSpellStr) || "$$$".equals(firsSpellStr)) {

                letters.put(bean.getFirstLetter(), i);

            } else {
                String letter = String.valueOf(firsSpellStr.charAt(0)).toUpperCase();

                //如果没有这个key则加入并把位置也加入
                if (!letters.containsKey(letter)) {

                    letters.put(letter, i);

                }
            }
        }

        //不自定义则默认26个字母
        sidrbar.setShowString(customLetters);

        adapter.clear();

        adapter.addAll(sortLettersList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_domestic:
                selectDomestic();
                break;
            case R.id.select_abroad:
                selectAbroad();
                break;
            case R.id.tvCancel:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 选择国内
     */
    private void selectDomestic() {
        select_domestic_textView.setTextColor(getResources().getColor(R.color.color_436a8e));

        select_domestic_textView1.setBackgroundColor(getResources().getColor(R.color.color_436a8e));

        select_abroad_textView.setTextColor(getResources().getColor(R.color.color_0a1437));

        select_abroad_textView1.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        //国内城市
        List<NationalCountryBean> civilDataList = nationCountryPresenter.selectIfForeignCountryData(false);

        loadCityToView(civilDataList, 1);
    }


    /**
     * 选择国外
     */
    private void selectAbroad() {
        select_abroad_textView.setTextColor(getResources().getColor(R.color.color_436a8e));

        select_abroad_textView1.setBackgroundColor(getResources().getColor(R.color.color_436a8e));

        select_domestic_textView.setTextColor(getResources().getColor(R.color.color_0a1437));

        select_domestic_textView1.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        List<NationalCountryBean> internationDataList = nationCountryPresenter.selectIfForeignCountryData(true);

        loadCityToView(internationDataList, 2);
    }


    private class CityListWithHeadersAdapter extends CityListAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == -1000) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_city_head_user_place, parent, false);

                return new CityUserPlaceViewHold(view);

            } else if (viewType == -1001 || viewType == -1002) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_city_history_item, parent, false);

                return new CityHistoryViewHold(view);
            } else {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_city_children_item, parent, false);

                return new CityNameViewHold(view);
            }


        }

        @Override
        public int getItemViewType(int position) {
            NationalCountryBean bean = getItem(position);

            if (bean.get_id() < 0) {

                return bean.get_id();

            } else {
                return super.getItemViewType(position);
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof CityNameViewHold) {//城市名

                TextView textView = (TextView) holder.itemView;

                NationalCountryBean bean = getItem(position);

                textView.setTag(bean);

                textView.setText(bean.getCityName());

            } else if (holder instanceof CityHistoryViewHold) {//热门城市、历史城市

                CityHistoryViewHold viewHold = (CityHistoryViewHold) holder;

                viewHold.initData(getItem(position));

            } else if (holder instanceof CityUserPlaceViewHold) {//当前用户位置

                CityUserPlaceViewHold viewHold = (CityUserPlaceViewHold) holder;

                viewHold.initData();

            }

        }

        @Override
        public long getHeaderId(int position) {
            NationalCountryBean bean = getItem(position);

            int id = bean.get_id();

            if (id == -1000) {

                return id;

            } else {

                return bean.getFirstSpell().charAt(0);
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_city_parent_item, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(getItem(position).getFirstLetter());
        }

        class CityUserPlaceViewHold extends RecyclerView.ViewHolder {

            private TextView tvUserAddress, tvCityName;

            public CityUserPlaceViewHold(View itemView) {
                super(itemView);

                tvCityName = (TextView) itemView.findViewById(R.id.tvCityName);

                tvUserAddress = (TextView) itemView.findViewById(R.id.tvUserAddress);
            }

            public void initData() {
                //重新开启定位功能
                GlobalApp.getInstance().setOnCityChangeListeners(onCityChangeListener);

                GlobalApp.getInstance().toReposition();

            }

            private GlobalApp.OnCityChangeListener onCityChangeListener = new GlobalApp.OnCityChangeListener() {
                @Override
                public void onCityChange(Location city) {

                    GlobalApp.getInstance().removeListener(onCityChangeListener);


                    tvUserAddress.setText("当前位置：" + city.addressInfoStr());

                    tvCityName.setText("当前城市：" + city.cityName);

                    tvCityName.setTag(city);

                    tvUserAddress.setTag(city);

                    tvCityName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Location location = (Location) v.getTag();

                            hotCityPresenterTemp.sendCityStatisticsRequest(location.getCityId(), GlobalVariable.industryId + "");

                            GlobalApp.getInstance().setSelectedCity(location);

                            setResult(CIVIINTERNATION_RESULTCODE);

                            finish();

                        }
                    });

                    tvUserAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Location location = (Location) v.getTag();

                            hotCityPresenterTemp.sendCityStatisticsRequest(location.getCityId(), GlobalVariable.industryId + "");

                            GlobalApp.getInstance().setSelectedCity(location);

                            setResult(5002);

                            finish();

                        }
                    });
                }
            };
        }

        class CityNameViewHold extends RecyclerView.ViewHolder {

            public CityNameViewHold(View itemView) {
                super(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object o = v.getTag();

                        if (o != null) {

                            NationalCountryBean bean = (NationalCountryBean) v.getTag();

                            bundleDataFinsh(bean);

                        }

                    }
                });
            }
        }

        class CityHistoryViewHold extends RecyclerView.ViewHolder {

            private WholeRecyclerView wvBrand;

            private SingleConditionAdapter subItemAdapter;

            private List<NationalCountryBean> list;

            public CityHistoryViewHold(View itemView) {
                super(itemView);

                wvBrand = (WholeRecyclerView) itemView.findViewById(R.id.wvBrand);

                subItemAdapter = new SingleConditionAdapter(CivilInternationCityActivity.this);

                wvBrand.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(CivilInternationCityActivity.this, 7)));

                RecyclerView.LayoutManager layoutManager = wvBrand.getLayoutManager();

                if (layoutManager == null) {

                    wvBrand.setLayoutManager(new GridLayoutManager(CivilInternationCityActivity.this, 4));

                }

                subItemAdapter.setItemDataCallBack(new SingleConditionAdapter.ItemDataCallBack() {
                    @Override
                    public void onSelectorItem(int subItemBean) {

                        if (list != null) {

                            NationalCountryBean bean = list.get(subItemBean);

                            bundleDataFinsh(bean);
                        }
                    }
                });

                wvBrand.setAdapter(subItemAdapter);


            }

            public void initData(NationalCountryBean bean) {

                int id = bean.get_id();
                List<String> brandBeanList = new ArrayList<>();
                if (id == -1001) {//历史数据

                    //存储历史城市
                    String histCityData = SharePrefUtil.getValueFromSp(CivilInternationCityActivity.this, SharePrefUtil.USE_CITY_HISTORY_DATA, null);

                    if (histCityData != null) {

                        List<NationalCountryBean> listData = JSONArray.parseArray(histCityData, NationalCountryBean.class);

                        list = listData;

                        for (NationalCountryBean cityBean : listData) {

                            brandBeanList.add(cityBean.getCityName());
                        }
                    }

                    subItemAdapter.addNewList(brandBeanList, -1);

                } else if (id == -1002) {//热门城市数据
                    //发送数据请求
                    HotCityPresenter hotCityPresenter = new HotCityPresenter(hotCityBVI);

                    hotCityPresenter.sendHotCityRequest("1", GlobalVariable.industryId + "", bean.getType());
                }

            }

            BaseViewLayerInterface hotCityBVI = new BaseViewLayerInterface() {
                @Override
                public void callSuccessViewLogic(Object o, int type) {
                    list = JSON.parseArray(o + "", NationalCountryBean.class);


                    if (list != null && list.size() > 0) {

                        ArrayList<String> brandBeanList = new ArrayList<>();

                        for (NationalCountryBean bean : list) {

                            brandBeanList.add(bean.getCityName());
                        }

                        subItemAdapter.addNewList(brandBeanList, -1);
                    }

                }

                @Override
                public void callFailedViewLogic(Object o, int type) {

                }
            };

        }

    }

    /**
     * 绑定数据
     *
     * @param bean
     */
    public void bundleDataFinsh(NationalCountryBean bean) {

        saveToPre(bean);

        GlobalApp.getInstance().nationalContryBeanToLocation(bean);

        setResult(CIVIINTERNATION_RESULTCODE);

        finish();

        hotCityPresenterTemp.sendCityStatisticsRequest(bean.getCityId() + "", GlobalVariable.industryId + "");


    }

    private int maxNumber = 4;

    private void saveToPre(NationalCountryBean bean) {

        //存储历史城市
        String histCityData = SharePrefUtil.getValueFromSp(this, SharePrefUtil.USE_CITY_HISTORY_DATA, null);

        if (histCityData != null) {

            List<NationalCountryBean> listData = JSONArray.parseArray(histCityData, NationalCountryBean.class);

            int size = listData.size();

            if (size > 0) {

                //检查是否已经存在该城市
                boolean ifExit = true;

                for (NationalCountryBean beanTemp : listData) {

                    if (bean.getCityName().equals(beanTemp.getCityName()) && bean.getCityRuby().equals(beanTemp.getCityRuby()) && bean.getCityId() == beanTemp.getCityId()) {

                        ifExit = false;

                        break;
                    }

                }

                if (ifExit) {

                    if (size == maxNumber) {//移除最后一笔数据

                        listData.remove(maxNumber - 1);

                    }

                    listData.add(0, bean);

                    String totalStr = JSON.toJSONString(listData);

                    SharePrefUtil.saveToSp(this, SharePrefUtil.USE_CITY_HISTORY_DATA, totalStr);

                }


            } else {

                listData.add(bean);
            }

        } else {

            List<NationalCountryBean> listData = new ArrayList<NationalCountryBean>();

            listData.add(bean);

            String totalStr = JSON.toJSONString(listData);

            SharePrefUtil.saveToSp(this, SharePrefUtil.USE_CITY_HISTORY_DATA, totalStr);
        }

    }
}
