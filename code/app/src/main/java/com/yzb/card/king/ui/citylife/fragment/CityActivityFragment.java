package com.yzb.card.king.ui.citylife.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.citylife.activity.CityLifeFilterListActivity;
import com.yzb.card.king.ui.discount.fragment.BaseFragment;
import com.yzb.card.king.util.CommonUtil;

/**
 * Created by gengqiyun on 2016/6/16.
 * 城市生活；
 */
public class CityActivityFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final float imgWhRate = 979 / 540f;
    private SwipeRefreshLayout swipeRefresh;
    private ListView listview;
    private View headerView;

    public static CityActivityFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        CityActivityFragment fragment = new CityActivityFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cityactivity_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (view != null) {
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            ViewGroup.LayoutParams vl = iv.getLayoutParams();
            vl.width = CommonUtil.getScreenWidth(getActivity());
            vl.height = (int) (CommonUtil.getScreenWidth(getActivity()) * imgWhRate);
            iv.setLayoutParams(vl);

          //  iv.setBackgroundResource(R.drawable.citylife_main_img);
            iv.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                readyGo(getActivity(), CityLifeFilterListActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {

    }

}
