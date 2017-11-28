package com.yzb.card.king.ui.hotel.fragment;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelImageBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.hotel.activtiy.HotelPicActivity;
import com.yzb.card.king.ui.hotel.adapter.HotelImagesHolderAdapter;
import com.yzb.card.king.ui.other.activity.FullScreenImgActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 2016/5/18.
 */
public class PicFragment extends Fragment {

    private int currentType = 0;

    private View rootView;

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.hotel_detail_all, container, false);
        initView();
        initData();
        return rootView;
    }

    private void initView()
    {
        listView = (ListView) rootView.findViewById(R.id.listView);

    }

    private void initData()
    {

        listView.setAdapter(new DetailAdapter());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        currentType = getArguments().getInt("type");

    }

    public static PicFragment newInstance(int type)
    {

        Bundle args = new Bundle();
        args.putInt("type", type);
        PicFragment fragment = new PicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    class DetailAdapter extends BaseAdapter {


        private List<HotelImageBean> listDataAll;

        private HotelPicActivity activity;

        private Matrix matrix = null;


        public DetailAdapter()
        {
            activity = (HotelPicActivity) getActivity();
            listDataAll = activity.listAll;
            matrix = new Matrix();
            matrix.postScale(2, 2);
        }

        @Override
        public int getCount()
        {
            if (currentType == 0) {

                int size = listDataAll.size();

                return size;

            } else {

                return 1;

            }
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.hotel_detial_item_all, null);
                convertView.setTag(new ViewHolderAll(convertView));
            }

            List<HotelImageBean.ImageBean> listData;

            ViewHolderAll holderAll = (ViewHolderAll) convertView.getTag();

            if (currentType == 0) {

                holderAll.llTitle.setVisibility(View.VISIBLE);

                holderAll.title_.setText(activity.tabNames.get(position + 1));

                listData = listDataAll.get(position).getPhotosList();

            } else {
                holderAll.llTitle.setVisibility(View.INVISIBLE);

                listData = listDataAll.get(currentType - 1).getPhotosList();
            }

            if (listData != null) {

                holderAll.initData(listData);
            }

            return convertView;
        }


        class ViewHolderAll {

            public TextView title_;

            public RecyclerView wvLvData;

            public LinearLayout llTitle;

            private HotelImagesHolderAdapter mAdapter;

            public ViewHolderAll(View parent)
            {
                title_ = (TextView) parent.findViewById(R.id.tv_title);

                wvLvData = (RecyclerView) parent.findViewById(R.id.wvLvData);

                llTitle = (LinearLayout) parent.findViewById(R.id.llTitle);

                mAdapter = new HotelImagesHolderAdapter(getContext(), hander);

                wvLvData.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(getContext(), 1)));

                wvLvData.setLayoutManager(new GridLayoutManager(getContext(), 3));

                wvLvData.setAdapter(mAdapter);

            }

            public void initData(List<HotelImageBean.ImageBean> imageUrl)
            {

                List<String> imageUrlStrList = new ArrayList<>();

                for (HotelImageBean.ImageBean photoUrl : imageUrl) {

                    imageUrlStrList.add(photoUrl.getPhotoUrl());

                }

                if (currentType == 0) {

                    mAdapter.setNewDataByNumber(imageUrlStrList, 6);

                    mAdapter.setImageUrl(imageUrl);

                } else {

                    mAdapter.clear();

                    mAdapter.addAll(imageUrlStrList);
                }

            }


            private Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg)
                {
                    super.handleMessage(msg);

                    int what = msg.what;


                    if (what == -1) {//展开

                        mAdapter.spreadAllData();

                        return;
                    }


                    List<String> list = null;

                    if (currentType == 0) {

                        list = new ArrayList<>();

                        List<HotelImageBean.ImageBean> tempList = new ArrayList<HotelImageBean.ImageBean>();


                        //统计酒店的所有图片
                        for (HotelImageBean hotelImageBean : listDataAll) {

                            List<HotelImageBean.ImageBean> imageBeanList = hotelImageBean.getPhotosList();

                            tempList.addAll(imageBeanList);

                        }

                        //查询目标图片的编号
                        long totalImageId = mAdapter.getImageUrl().get(what).getPhotoId();

                        int sizeTmep = tempList.size();

                        if (sizeTmep > 0) {

                            for (int i = 0; i < sizeTmep; i++) {

                                HotelImageBean.ImageBean imageBean = tempList.get(i);

                                list.add(imageBean.getPhotoUrl());

                                if (totalImageId == imageBean.getPhotoId()) {
                                    what = i;

                                }

                            }
                        }

                    } else {

                        list = mAdapter.getData();

                    }

                    String[] url = (String[]) list.toArray(new String[list.size()]);

                    Intent intent = new Intent(getContext(), FullScreenImgActivity.class);
                    intent.putExtra("currentPage", what);
                    intent.putExtra("imgUrls", url);
                    startActivity(intent);

                }
            };
        }


    }
}
