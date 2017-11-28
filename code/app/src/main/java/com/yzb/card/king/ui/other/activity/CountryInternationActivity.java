package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.SideBar;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.ui.other.adapter.CityListAdapter;
import com.yzb.card.king.ui.other.adapter.DividerDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 类  名：选择国际地区
 * 作  者：Li Yubing
 * 日  期：2017/10/14
 * 描  述：
 */
@ContentView(R.layout.activity_country_internation)
public class CountryInternationActivity extends BaseActivity {

    public static final int COUNTRYINTERNATION_RESULTCODE = 5002;

    private RecyclerView recyclerView;

    @ViewInject(R.id.sidrbar)
    private SideBar sidrbar;

    @ViewInject(R.id.llSearch)
    private EditText etSearch;

    @ViewInject(R.id.llCity)
    private LinearLayout llCity;

    private CityListWithHeadersAdapter adapter;

    private HashMap<String, Integer> letters = new HashMap<>();


    private NationalCountryPresenter nationCountryPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();
        initData();

        llCity.setVisibility(View.VISIBLE);
    }

    private void initView()
    {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        sidrbar.setTextColor(getResources().getColor(R.color.color_436a8e));

        sidrbar.setOnLetterTouchListener(new SideBar.OnLetterTouchListener() {
            @Override
            public void onLetterTouch(String letter, int position)
            {
                if (letters.containsKey(letter)) {
                    recyclerView.scrollToPosition(letters.get(letter));
                }
            }

            @Override
            public void onActionUp()
            {
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

    }

    private void initData()
    {
        setTitleNmae("国籍（地区）");

        nationCountryPresenter = new NationalCountryPresenter();

        selectDomestic();

    }

    private void loadCityToView(List<NationalCountryBean> civilDataList)
    {

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

        //收集索引首次出现的位置
        int sizeList = sortLettersList.size();

        for (int i = 0; i < sizeList; i++) {

            NationalCountryBean bean = sortLettersList.get(i);

            String firsSpellStr = bean.getFirstSpell();

            String letter = String.valueOf(firsSpellStr.charAt(0)).toUpperCase();

            //如果没有这个key则加入并把位置也加入
            if (!letters.containsKey(letter)) {

                letters.put(letter, i);

            }
        }

        //不自定义则默认26个字母
        sidrbar.setShowString(customLetters);

        adapter.clear();

        adapter.addAll(sortLettersList);

    }


    /**
     * 选择国籍
     */
    private void selectDomestic()
    {

        //选择国籍
        List<NationalCountryBean> civilDataList = nationCountryPresenter.selectCountryData();

        loadCityToView(civilDataList);
    }


    private class CityListWithHeadersAdapter extends CityListAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_city_children_item, parent, false);

            return new CityListWithHeadersAdapter.CityNameViewHold(view);

        }

        @Override
        public int getItemViewType(int position)
        {
            NationalCountryBean bean = getItem(position);

            if (bean.get_id() < 0) {

                return bean.get_id();

            } else {
                return super.getItemViewType(position);
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {

            if (holder instanceof CityListWithHeadersAdapter.CityNameViewHold) {//城市名

                TextView textView = (TextView) holder.itemView;

                NationalCountryBean bean = getItem(position);

                textView.setTag(bean);

                textView.setText(bean.getCityName());

            }

        }

        @Override
        public long getHeaderId(int position)
        {
            NationalCountryBean bean = getItem(position);

            return bean.getFirstSpell().charAt(0);

        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent)
        {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_city_parent_item, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            TextView textView = (TextView) holder.itemView;
            textView.setText(getItem(position).getFirstLetter());
        }


        class CityNameViewHold extends RecyclerView.ViewHolder {

            public CityNameViewHold(View itemView)
            {
                super(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Object o = v.getTag();

                        if (o != null) {

                            NationalCountryBean bean = (NationalCountryBean) v.getTag();

                            bundleDataFinsh(bean);

                        }

                    }
                });
            }
        }


    }

    /**
     * 绑定数据
     *
     * @param bean
     */
    public void bundleDataFinsh(NationalCountryBean bean)
    {

        Intent  intent = new Intent();

        intent.putExtra("selectCountryData",bean);

        setResult(COUNTRYINTERNATION_RESULTCODE,intent);
        finish();

    }


}
