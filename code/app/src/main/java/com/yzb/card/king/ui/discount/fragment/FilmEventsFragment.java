package com.yzb.card.king.ui.discount.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.ui.discount.adapter.FilmEventsAdapter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 影片场次Fragment；
 */
public class FilmEventsFragment extends BaseFragment implements AdapterItemClickCallBack {

    private static final String ARG_STORE_ID = "storeId";
    private static final String ARG_FILM_ID = "filmId";
    private static final String ARG_FILM_NAME = "filmName";
    private static final String ARG_SELECT_DATE = "selectDate";
    private static final String ARG_DATE = "date";
    private static final String ARG_STORE_NAME = "storeName";

    private String storeId; //门店id；
    private String filmId; //影片id
    private String selectDate; //选择日期
    private String date;// 日期
    private ListView listView;
    private TextView salesAll;
    private FilmEventsAdapter adapter;
    private INotifyCallBack callBack;
    private List<Map> data;

    public void setCallBack(INotifyCallBack callBack) {
        this.callBack = callBack;
    }

    public interface INotifyCallBack {
        void notifyCount(ListView listView);
    }

    public FilmEventsFragment() {
    }

    public static FilmEventsFragment newInstance(String storeId, String filmId, String selectDate, String date, String storeName, String filmName) {
        FilmEventsFragment fragment = new FilmEventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STORE_ID, storeId);
        args.putString(ARG_FILM_ID, filmId);
        args.putString(ARG_SELECT_DATE, selectDate);
        args.putString(ARG_DATE, date);
        args.putString(ARG_STORE_NAME, storeName);
        args.putString(ARG_FILM_NAME, filmName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        if (adapter != null && adapter.getCount() > 0) {
            data = adapter.getDataList();
        }
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getString(ARG_STORE_ID);
            filmId = getArguments().getString(ARG_FILM_ID);
            selectDate = getArguments().getString(ARG_SELECT_DATE);
            date = getArguments().getString(ARG_DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film_events, container, false);
        init(view);
        return view;
    }

    /**
     * 初始化；
     *
     * @param view
     */
    private void init(View view) {
        if (view == null) return;

        salesAll = (TextView) view.findViewById(R.id.salesAll);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new FilmEventsAdapter(getActivity(), getArguments());
        listView.setAdapter(adapter);

        String date2 = DateUtil.date2String(new Date(), DateUtil.DATE_FORMAT_DATE);

        System.out.println("date:" + selectDate + "date2:" + date2);

        if (!StringUtils.isEmpty(selectDate) && date2.equals(selectDate)) {
            salesAll.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            queryFilmEvents();
        }


    }

    @Override
    public void itemClickCallBack(Object... args) {


    }

    /**
     * 查询场次列表
     */
    public void queryFilmEvents() {


    }


}
