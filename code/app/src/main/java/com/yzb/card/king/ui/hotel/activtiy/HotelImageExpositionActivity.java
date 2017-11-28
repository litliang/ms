package com.yzb.card.king.ui.hotel.activtiy;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yzb.card.king.R;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.hotel.adapter.HotelImagesHolderAdapter;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类  名：酒店图片展览
 * 作  者：Li Yubing
 * 日  期：2017/8/24
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_image_exposition)
public class HotelImageExpositionActivity extends BaseActivity {

    private RecyclerView wvLvData;

    private HotelImagesHolderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView()
    {

        if (getIntent().hasExtra("imageTitleName")) {

            setTitleNmae(getIntent().getStringExtra("imageTitleName"));

        } else {

            setTitleNmae("图片");
        }
        if (getIntent().hasExtra("photoUrls")) {

            final String photoUrls = getIntent().getStringExtra("photoUrls");

            wvLvData = (RecyclerView) findViewById(R.id.wvLvData);

            mAdapter = new HotelImagesHolderAdapter(this);

            wvLvData.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(this, 5)));

            wvLvData.setLayoutManager(new GridLayoutManager(this, 2));

            wvLvData.setAdapter(mAdapter);

            wvLvData.post(new Runnable() {
                @Override
                public void run()
                {
                    /**
                     * 请求该酒店的房间信息
                     */
                    List<String> list = new ArrayList<String>();

                    int index = photoUrls.indexOf(",");

                    if (index == -1) {

                        list.add(photoUrls);

                    } else {
                        String[] photoUrlsArray = photoUrls.split(",");

                        list = Arrays.asList(photoUrlsArray);

                    }

                    mAdapter.clear();

                    mAdapter.addAll( list);
                }
            });

        }
    }
}
