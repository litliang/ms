package com.yzb.card.king.ui.discount.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.SlideShow5ItemView;
import com.yzb.card.king.ui.base.BaseCityChangeFragment;
import com.yzb.card.king.ui.discount.adapter.FilmMainAdapter2;
import com.yzb.card.king.ui.discount.bean.FilmBean;
import com.yzb.card.king.ui.discount.bean.LbtBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.film.activity.FilmCinemaDetailActivity;
import com.yzb.card.king.ui.film.activity.FilmListActivity;
import com.yzb.card.king.ui.film.presenter.MovieMainPersenter;
import com.yzb.card.king.ui.film.view.MovieMainView;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.ui.luxury.activity.ImgClickCallBack;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电影主页Fragment；
 */
public class FilmMainFragment extends BaseCityChangeFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener
        , AdapterItemClickCallBack, MovieMainView
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View headerView;
    private FilmMainAdapter2 adapter;
    private SwipeRefreshLayout swipeRefresh;

    private ListView listview;
    private SlideShow1ItemView slideShowView1;
    private SlideShow5ItemView slideShowView2;
    private float slide1_whrate = 432 / 1080.0f;
    private LinearLayout panel_more;
    private String typeId = "7";
    private LinearLayout panel_yp;
    private LinearLayout panel_yy;
    private LinearLayout panel_bd;
    private ChannelMainActivity activity;
    private String customerId;

    private MovieMainPersenter persenter;

    public FilmMainFragment()
    {
    }

    public static FilmMainFragment newInstance(String param1, String param2)
    {
        FilmMainFragment fragment = new FilmMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_film_main, container, false);
        init(view);
        activity = (ChannelMainActivity) getActivity();
        getData(true);
        return view;
    }

    private void init(View view)
    {

        UserBean userBean = UserManager.getInstance().getUserBean();
        customerId = userBean == null ? "" : userBean.getAmountAccount();

        if (view == null) return;

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        listview = (ListView) view.findViewById(R.id.listview);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.film_main_list_header, null);
        findViewFromHeader();

        listview.addHeaderView(headerView);
        listview.setDividerHeight(0);
        adapter = new FilmMainAdapter2(getActivity());
        adapter.setAdapterItemClickCallBack(this);
        listview.setAdapter(adapter);
    }

    private void getData(final boolean loadDataRefresh)
    {
        persenter = new MovieMainPersenter(this);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                initShow1ItemView();
                getLbt2();
                getTjyy(loadDataRefresh);
            }

        }, 100);
    }


    @Override
    public void onCityChange(Location city)
    {
        super.onCityChange(city);
        getData(true);
    }

    private void initShow1ItemView()
    {
        slideShowView1.setParam(typeId, cityId,typeId);
        slideShowView1.setOnDataLoadFinishListener(new SlideShow1ItemView.OnDataLoadFinishListener()
        {
            @Override
            public void onDataLoadFinish()
            {
                swipeRefresh.setRefreshing(false);
            }
        });

        slideShowView1.setImgClickCallBack(new ImgClickCallBack()
        {

            @Override
            public void imgClickcallBack(Object... objects)
            {
                if (objects != null && objects.length == 1 && objects[0] instanceof LbtBean)
                {
                    LbtBean lbtBean = (LbtBean) objects[0];
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataBean", lbtBean);
                    readyGoWithBundle(getActivity(), WebViewClientActivity.class, bundle);
                }
            }
        });
    }

    /**
     * ms_type表;
     */
    private void getLbt2()
    {

        Map<String, Object> map = new HashMap<>();
        map.put("typeId", typeId);
        persenter.sendMovieGuideRequest(CardConstant.card_app_querymovieguide, map);
    }

    /**
     * 获取推荐影院列表；
     *
     * @param loadDataRefresh
     */
    private void getTjyy(final boolean loadDataRefresh)
    {
        final Location city = GlobalApp.getSelectedCity();

        Map<String, Object> param = new HashMap<>();
        param.put("cityId", cityId);
        param.put("typeId", typeId);
        param.put("lat", city.latitude + "");
        param.put("lng", city.longitude + "");
        param.put("customerId", customerId);

        persenter.sendRecommendedtheaterRequest(param, CardConstant.card_app_storelist);
    }

    @Override
    public void onRefresh()
    {
        getData(true);
    }

    /**
     * init header中的view；
     */
    private void findViewFromHeader()
    {
        slideShowView1 = (SlideShow1ItemView) headerView.findViewById(R.id.slideShowView1);
        slideShow1ItemViewSetings();
        slideShowView2 = (SlideShow5ItemView) headerView.findViewById(R.id.slideShowView2);

        LinearLayout panel_dots2 = (LinearLayout) headerView.findViewById(R.id.panel_dots2);
        slideShowView2.setPanelDots(panel_dots2);

        panel_more = (LinearLayout) headerView.findViewById(R.id.panel_more);
        panel_more.setOnClickListener(this);

        panel_yp = (LinearLayout) headerView.findViewById(R.id.panel_yp);
        panel_yp.setOnClickListener(this);

        panel_yy = (LinearLayout) headerView.findViewById(R.id.panel_yy);
        panel_yy.setOnClickListener(this);

        panel_bd = (LinearLayout) headerView.findViewById(R.id.panel_bd);
        panel_bd.setOnClickListener(this);
    }

    /**
     * 顶部轮播设置；
     */
    private void slideShow1ItemViewSetings()
    {
        if (slideShowView1 == null) return;
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        ViewUtil.set(slideShowView1,
                screenWith,
                (int) (screenWith * slide1_whrate + 0.5));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panel_yp:
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("typeParentId", typeId);
//                bundle1.putString("typeId", "37");
//                bundle1.putString("typeName", "影片");
//                readyGoWithBundle(getActivity(), FilmMoreActivity.class, bundle1);
                break;

            case R.id.panel_yy:
//                Bundle bundle2 = new Bundle();
////             // 传递大分类id；
//                bundle2.putString("typeParentId", typeId);
//                bundle2.putString("typeId", "38");
//                bundle2.putString("typeName", "影院");
//                readyGoWithBundle(getActivity(), FilmMoreActivity.class, bundle2);
                break;
            // 榜单
            case R.id.panel_bd:
                readyGo(getActivity(), FilmListActivity.class);
                break;

            case R.id.panel_more://更多；
//                Bundle bundle4 = new Bundle();
//                // 传递大分类id；
//                bundle4.putString("typeParentId", typeId);
//                bundle4.putString("typeId", "38");
//                bundle4.putString("typeName", "影院");
//                readyGoWithBundle(getActivity(), FilmMoreActivity.class, bundle4);
                break;
        }
    }

    /**
     * 推荐影院item点击回调；
     *
     * @param args
     */
    @Override
    public void itemClickCallBack(Object... args)
    {

        if (args == null || args.length == 0 || (int) args[0] < 0)
        {
            return;
        }
        int position = (int) args[0];
        Bundle bundle = new Bundle();
        //影院的id；
        bundle.putString("id", adapter.getItem(position).id);
        readyGoWithBundle(getActivity(), FilmCinemaDetailActivity.class, bundle);
    }

    @Override
    public void onMovieGuideSuccess(List<FilmBean> FilmBeans)
    {
        List<FilmBean> beans = FilmBeans;
        if (beans != null && beans.size() > 0)
        {
            for (FilmBean item : beans)
            {
                item.imageCode = ServiceDispatcher.url_image + "getImg/" + item.imageCode + "/0";
            }
            slideShowView2.setDataList(beans);
        }

    }

    @Override
    public void onRecommendedTheaterSuccess(List<StoreBean> shopBeans)
    {
        List<StoreBean> shopBeans1 = shopBeans;
        if (shopBeans1 != null && shopBeans1.size() > 0)
        {
            for (StoreBean item : shopBeans1)
            {
                if (!TextUtils.isEmpty(item.storePhoto))
                {
                    item.storePhoto = ServiceDispatcher.getImageUrl(item.storePhoto);
                }
            }
        }
        adapter.clear();
        adapter.appendDataList(shopBeans1);

    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        Toast.makeText(this.getContext(), o+"", Toast.LENGTH_SHORT).show();
    }
}
