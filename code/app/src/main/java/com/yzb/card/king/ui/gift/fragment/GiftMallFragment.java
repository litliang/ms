package com.yzb.card.king.ui.gift.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FragmentMessageEvent;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.gift.activity.BuyMindECardActivity;
import com.yzb.card.king.ui.gift.activity.BuyMindPhysCardActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardGiveMindActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardHomeActivity;
import com.yzb.card.king.ui.gift.adapter.ShopGiftCardAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardProductBean;
import com.yzb.card.king.ui.gift.presenter.GiftCardStorePresenter;
import com.yzb.card.king.ui.gift.presenter.GiftCardStoreProductPresenter;
import com.yzb.card.king.ui.gift.view.GiftCardProductView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 类  名：礼品卡商城碎片
 * 作  者：Li Yubing
 * 日  期：2017/1/10
 * 描  述：
 */
public class GiftMallFragment extends BaseFragment implements View.OnClickListener, BaseViewLayerInterface, ShopGiftCardAdapter.OnNotItemClickListener, GiftCardProductView
{
    private View view;
    private GiftCardStorePresenter presenter;
    private GiftCardStoreProductPresenter productPresenter;
    private List<GiftCardProductBean> blessList;
    private List<GiftCardProductBean> listProdtct;
    private ShopGiftCardAdapter adapter;
    private RecyclerView recycler;
    private String cityId;
    private ViewFlipper flipper;
    private String[] strings;


    private SlideShow1ItemView slideShowView1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        cityId = GlobalApp.getSelectedCity().cityId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_giftcard_store, container, false);
        presenter = new GiftCardStorePresenter(this);
        productPresenter = new GiftCardStoreProductPresenter(this);
        initView();
        initData();
        initProductData();
        initStuffImg();
        return view;
    }

    private void initStuffImg()
    {

        slideShowView1.setParam(AppConstant.GIFTCARD_SHOP_HOMEPAGE,cityId,"1");
    }

    private void initData()
    {
      //发送礼品卡商城的心意卡滚动信息请求
        presenter.sendGiftDataRequest();
    }

    private void initProductData()
    {

        productPresenter.setGiftGoodsReqeust();
    }


    private void initView()
    {
        flipper = (ViewFlipper) view.findViewById(R.id.giftcard_store_viewFlipper);

        view.findViewById(R.id.panelPhysicalCard).setOnClickListener(this);
        view.findViewById(R.id.panelWishCard).setOnClickListener(this);
        recycler = (RecyclerView) view.findViewById(R.id.giftcard_shop_recyclerview);

        view.findViewById(R.id.iv_back).setOnClickListener(this);

        slideShowView1 = (SlideShow1ItemView) view.findViewById(R.id.slideShowView1);
    }

    private void initViewFlipper()
    {

        if (strings != null)
        {
            for (int i = 0; i < strings.length; i++)
            {
                flipper.addView(getTextVeiw(blessList.get(i).getBlessWord(), blessList.get(i).getProductId() + "", blessList.get(i).getImageCode() + ""));
            }
            flipper.getCurrentView().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });
        }
    }

    private void initAdapter()
    {
        adapter = new ShopGiftCardAdapter(getActivity(), listProdtct);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new DecorationUtil(5));
        recycler.setAdapter(adapter);
        adapter.setOnNotItemClickListener(this);

    }


    @Override
    public void onStop()
    {
        super.onStop();
        GlobalApp.backFlag = false;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelPhysicalCard: //心意实体卡；
                readyGo(getActivity(), BuyMindPhysCardActivity.class);
                break;
            case R.id.panelWishCard: //心意e卡；
                readyGo(getActivity(), BuyMindECardActivity.class);
                break;
            case R.id.iv_back:
                if (GlobalApp.backFlag)
                {
                    startActivity(new Intent(getContext(), GiftCardHomeActivity.class));
                    GlobalApp.backFlag = false;
                    //首先通知更新我的碎片，再次开启礼品卡页
                    FragmentMessageEvent event = new FragmentMessageEvent();

                    event.setFragmentIndex(3);//此处与appFactory里面的getHomeTabFragmentList方法排序一直

                    EventBus.getDefault().post(event);

                } else
                {

                    FragmentMessageEvent event = new FragmentMessageEvent();

                    event.setFragmentIndex(2);//此处与appFactory里面的getHomeTabFragmentList方法排序一直

                    EventBus.getDefault().post(event);
                }
                break;
        }
    }

    private TextView getTextVeiw(final String data, final String productId, final String imageCode)
    {
        TextView textView = new TextView(getContext());
        textView.setSingleLine(true);
        textView.setText(data);
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), GiftCardGiveMindActivity.class);
                intent.putExtra("productId", productId);
                intent.putExtra("blessWord", data);
                intent.putExtra("imageCode", imageCode);

                startActivity(intent);
            }
        });
        return textView;
    }

    //item点击事件
    @Override
    public void OnNotItemClick(RecyclerView parent, View view, int position, List<GiftCardProductBean> data)
    {
        Intent intent = new Intent(getActivity(), GiftCardGiveMindActivity.class);
        intent.putExtra("productId", data.get(position).getProductId() + "");
        intent.putExtra("blessWord", data.get(position).getBlessWord());
        intent.putExtra("imageCode", data.get(position).getImageCode());
        startActivity(intent);
    }

    @Override
    public void onLoadProductSuccess(Object o, int type)
    {
        if (type == 1)
        {
            listProdtct = JSON.parseArray(String.valueOf(o), GiftCardProductBean.class);
            initAdapter();
        }
    }

    @Override
    public void onLoadProductFail(Object o, int type)
    {
        if (type == 1)
        {
            toastCustom("无返回数据");
        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1)
        {
            blessList = JSON.parseArray(String.valueOf(o), GiftCardProductBean.class);
            int size = blessList.size();
            for (int i = 0; i < size; i++)
            {
                strings = new String[size];
                strings[i] = blessList.get(i).getBlessWord();
            }
            initViewFlipper();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == 1 && getActivity() != null)
        {
            Toast.makeText(getActivity(), "无返回数据", Toast.LENGTH_SHORT).show();
        }
    }
}
