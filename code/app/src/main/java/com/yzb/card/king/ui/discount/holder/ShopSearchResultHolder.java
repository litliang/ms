package com.yzb.card.king.ui.discount.holder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.task.OnLoadFinisListener;
import com.yzb.card.king.ui.discount.adapter.GoodsAdapterAbs;
import com.yzb.card.king.ui.discount.adapter.TypeAdapterAbs;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.bean.GoodsType;
import com.yzb.card.king.ui.discount.bean.History;
import com.yzb.card.king.ui.discount.bean.Position;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/8/24 14:40
 * 描述：
 */
public class ShopSearchResultHolder extends Holder<History> implements OnLoadFinisListener {
    private ListView lvType;
    private FrameLayout flOrder;
    private ListView lvGoods;
  //  private BaseTask baseTask;
    private OrderDetailHolder orderDetailHolder;
    private List<GoodsType> typeList = new ArrayList<>();
    private TypeAdapterAbs typeAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    private GoodsAdapterAbs goodsAdapter;
    private Position selectPosition;
    private String goodsTypeId;

    public ShopSearchResultHolder(OrderDetailHolder orderDetailHolder) {
        super();
        this.orderDetailHolder = orderDetailHolder;
        initData();
    }

    private void initData() {
        typeAdapter = new TypeAdapterAbs(typeList,selectPosition);
        goodsAdapter = new GoodsAdapterAbs(goodsList,orderDetailHolder);
        lvType.setAdapter(typeAdapter);
        lvGoods.setAdapter(goodsAdapter);
        lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition.position = position;
                typeAdapter.notifyDataSetChanged();
                selectPosition.position = position;
                typeAdapter.notifyDataSetChanged();
//                goodsTypeId = typeList.get(position).goodsTypeId;
//                GetGoodsTask getGoodsTask = new GetGoodsTask();
//                getGoodsTask.execute();
            }
        });

    }

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.holder_shop_search_result);
        lvType = (ListView) view.findViewById(R.id.lvType);
        lvGoods = (ListView) view.findViewById(R.id.lvGoods);

        return view;
    }

    @Override
    public void refreshView(History data) {
//        if(baseTask!=null){
//            baseTask.cancel(true);
//            baseTask = null;
//        }
//        baseTask = new BaseTask();
//        baseTask.setServiceName("");
//        Map<String,String> map = new HashMap<>();
//        map.put("storeId",data.storeId);
//        map.put("key",data.key);
//        baseTask.setParam(map);
//        baseTask.setListener(this);
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void onFail(String code, String error) {

    }
}
