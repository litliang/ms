package com.yzb.card.king.ui.other.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.other.presenter.CommonReviewPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * 用户评价列表；
 *
 * @author gengqiyun
 * @date 2016.7.30
 */
public class PjActivity extends BaseActivity implements BaseViewLayerInterface {

    private RefreshRecyclerView mRecyclerView;

    private TagFlowLayout mFlowLayout;

    private TagAdapter tagAdapter;

    private CommonReviewAdapter commonReviewAdapter;

    private String[] mVals;

    private int industryId;//行业

    private long storeId; // 门店id

    private long goodsIdThree = -1;

    private long goodsIdTwo = -1;

    private int defaultIndex = 0;//0全部、1好评、2中评、3差评、4有图

    private int page = 0;

    private CommonReviewPresenter commonReviewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pj);

        commonReviewPresenter = new CommonReviewPresenter(this);

        recvIntentData();

        initReView();

    }

    private void initReView()
    {

        commonReviewAdapter = new CommonReviewAdapter(this);
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(commonReviewAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction()
            {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction()
            {
                getData(false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run()
            {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });
    }


    public void getData(final boolean isRefresh)
    {

        if (isRefresh) {
            page = 0;

        } else {
            page = commonReviewAdapter.getItemCount();

        }
        commonReviewPresenter.sendReviewInfoListRequest(industryId, storeId, goodsIdTwo, goodsIdThree, defaultIndex + "", page);
    }


    private void recvIntentData()
    {
        industryId = getIntent().getIntExtra("industryId", 0);

        storeId = getIntent().getLongExtra("storeId", 0);

        if (getIntent().hasExtra("pjTitle")) {

            setTitleNmae(getIntent().getStringExtra("pjTitle"));

        }
        //酒店评论信息
        if (getIntent().hasExtra("vote") && getIntent().hasExtra("voteDesc")) {

            TextView tvHotelVote = (TextView) findViewById(R.id.tvHotelVote);

            TextView tvHoteVoteMessage = (TextView) findViewById(R.id.tvHoteVoteMessage);

            TextView tvHotelVoteMessageOne = (TextView) findViewById(R.id.tvHotelVoteMessageOne);

            float vote = getIntent().getFloatExtra("vote", 1);

            String voteDesc = getIntent().getStringExtra("voteDesc");

            tvHotelVote.setText(vote + "");

            HoteUtil.hotelVoteMessage(vote, tvHoteVoteMessage);

            tvHotelVoteMessageOne.setText(voteDesc);

        }
    }

    private void assignViews()
    {
        final LayoutInflater mInflater = LayoutInflater.from(this);

        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);

        mFlowLayout.setAdapter(tagAdapter = new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView itemView = (TextView) mInflater.inflate(R.layout.view_tab_text_blue_c,
                        mFlowLayout, false);

                itemView.setText(s);

                return itemView;
            }
        });

        mFlowLayout.setMaxSelectCount(1);

        tagAdapter.setSelectedList(defaultIndex);

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {

                if(position == defaultIndex){

                    defaultIndex = 0;

                    tagAdapter.setSelectedList(defaultIndex);

                }else {
                    defaultIndex = position;
                }

                mRecyclerView.showSwipeRefresh();
                getData(true);

                return true;
            }
        });

        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet)
            {
                //  getActivity().setTitle("choose:" + selectPosSet.toString());
            }
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        mRecyclerView.showSwipeRefresh();
        getVoteCount();
        getData(true);


    }


    /**
     * 获取门店评论次数
     */
    private void getVoteCount()
    {
        commonReviewPresenter.sendReviewCountRequest(industryId, storeId, goodsIdTwo, goodsIdThree);
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == CommonReviewPresenter.VOTE_COUNT_CODE) {

            mVals = getResources().getStringArray(R.array.pj_tab_titles);

            JSONObject jsonObject = JSONObject.parseObject(o + "");

            if (jsonObject != null) {

                int totalCount = jsonObject.getIntValue("totalCount");

                int googCount = jsonObject.getIntValue("goodCount");

                int middleCount = jsonObject.getIntValue("middleCount");

                int badCount = jsonObject.getIntValue("badCount");

                int photoCount = jsonObject.getIntValue("photoCount");

                mVals[1] = mVals[1] + " " + googCount;

                mVals[2] = mVals[2] + " " + middleCount;

                mVals[3] = mVals[3] + " " + badCount;

                mVals[4] = mVals[4] + " " + photoCount;

                assignViews();
            }

        } else if (type == CommonReviewPresenter.VOTE_LIST_CODE) {

            List<PjBean> list = JSONArray.parseArray(o + "", PjBean.class);

            if (page == 0) {

                commonReviewAdapter.clear();

                commonReviewAdapter.addAll(list);

                mRecyclerView.dismissSwipeRefresh();

                mRecyclerView.getRecyclerView().scrollToPosition(0);

            } else {
                commonReviewAdapter.addAll(list);

            }

            int size = list.size();

            if (size < AppConstant.MAX_PAGE_NUM) {

                mRecyclerView.showNoMore();
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

        if (page == 0) {

            mRecyclerView.dismissSwipeRefresh();

            Error error = JSONObject.parseObject(JSON.toJSONString(o),Error.class);

            if(HttpConstant.NOINFO.equals(error.getCode())){
                commonReviewAdapter.clear();
            }

        }
    }
}
