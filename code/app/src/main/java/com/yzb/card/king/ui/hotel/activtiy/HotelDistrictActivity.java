package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.adapter.DistrictAdapterDataCallBack;
import com.yzb.card.king.ui.hotel.adapter.DistrictOneAdapter;
import com.yzb.card.king.ui.hotel.adapter.DistrictThreeAdapter;
import com.yzb.card.king.ui.hotel.adapter.DistrictTwoAdapter;
import com.yzb.card.king.ui.hotel.persenter.FilterListPersenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 类  名：酒店位置区域
 * 作  者：Li Yubing
 * 日  期：2017/7/22
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_district)
public class HotelDistrictActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    @ViewInject(R.id.rvOne)
    private RecyclerView rvOne;

    @ViewInject(R.id.rvTwo)
    private RecyclerView rvTwo;

    @ViewInject(R.id.rvThree)
    private RecyclerView rvThree;

    private DistrictOneAdapter oneAdapter;

    private DistrictTwoAdapter twoAdapter;

    private DistrictThreeAdapter threeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitleNmae(R.string.hotel_postion_district);

        initView();

        rvThree.setVisibility(View.GONE);

        initRequest();
    }

    /**
     * 初始观察者请求
     */
    private void initRequest()
    {

        FilterListPersenter persenter = new FilterListPersenter(this);

        persenter.sendDistrictPositionRequest(cityId);

        showPDialog("正在请求数据……");
    }


    private void initView()
    {

        findViewById(R.id.tvClear).setOnClickListener(this);

        findViewById(R.id.tvConfirm).setOnClickListener(this);

        rvOne.setLayoutManager(new GridLayoutManager(this, 1));

        oneAdapter = new DistrictOneAdapter(this);//,

        oneAdapter.setDataCallBack(districtAdapterDataCallBack);

        rvOne.setAdapter(oneAdapter);


        rvTwo.setLayoutManager(new GridLayoutManager(this, 1));

        twoAdapter = new DistrictTwoAdapter(this);

        twoAdapter.setDataCallBack(districtAdapterDataCallBack);

        rvTwo.setAdapter(twoAdapter);


        rvThree.setLayoutManager(new GridLayoutManager(this, 1));

        threeAdapter = new DistrictThreeAdapter(this);

        rvThree.setAdapter(threeAdapter);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvClear://清空

                if (oneAdapter.getItemCount() > 0) {
                    oneAdapter.setCurrentIndex(0);

                    oneAdapter.notifyDataSetChanged();

                    oneAdapter.callTwoChildrenData();

                }


                break;
            case R.id.tvConfirm://确定

                if (twoAdapter.getItemCount() > 0) {

                    boolean threefFlag = twoAdapter.isIfThirdColumnFlag();

                    Intent intent = new Intent(this, HotelProductListActivity.class);

                    if (threefFlag) {//获取第三列 数据

                        SubItemBean.ChildSubItemBean transmityBead = threeAdapter.getCurrentObject();

                        intent.putExtra("transmitData", transmityBead);

                    } else {//获取第二列 数据

                        SubItemBean transmityBead = twoAdapter.getCurrentObject();

                        intent.putExtra("transmitData", transmityBead);
                    }

                    setResult(1003, intent);

                    finish();

                }

                break;
            default:
                break;
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

        dimissPdialog();

        if (type == -1) {

            List<CatalogueTypeBean> catalogueTypeBeanList = JSONArray.parseArray(o + "", CatalogueTypeBean.class);

            oneAdapter.addNewDataList(catalogueTypeBeanList);

            loadOtherColumnDataView();

        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        dimissPdialog();
    }

    private DistrictAdapterDataCallBack districtAdapterDataCallBack = new DistrictAdapterDataCallBack() {
        @Override
        public void onClickItemData(Object o)
        {

            if (o instanceof SubItemBean) {

                SubItemBean bean = (SubItemBean) o;

                List<SubItemBean.ChildSubItemBean> chidList = bean.getChildList();

                if (chidList != null && chidList.size() > 0) {

                    threeAdapter.addNewChildList(chidList);

                } else {

                    threeAdapter.clearDataList();

                }

            } else if (o instanceof CatalogueTypeBean) {

                loadOtherColumnDataView();
            }

        }
    };

    /**
     * 重新加載第二列和第三列視圖和數據
     */
    private void loadOtherColumnDataView()
    {

        List<SubItemBean> childList = oneAdapter.getCurrentObjectChildList();

        if (childList != null && childList.size() > 0) {

            twoAdapter.addNewChildList(childList);

            List<SubItemBean.ChildSubItemBean> threeList = twoAdapter.getCurrentObjectChildList();

            if (threeList != null && threeList.size() > 0) {

                rvThree.setVisibility(View.VISIBLE);

                threeAdapter.addNewChildList(threeList);

            } else {
                threeAdapter.clearDataList();
                rvThree.setVisibility(View.GONE);
            }

        }
    }

}
