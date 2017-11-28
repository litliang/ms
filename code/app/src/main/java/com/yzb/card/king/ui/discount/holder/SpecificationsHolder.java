package com.yzb.card.king.ui.discount.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.bean.Specification;
import com.yzb.card.king.util.UiUtils;

import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/15 14:34
 * 描述：
 */
public class SpecificationsHolder extends Holder<Goods> {
    private GridView gvSize;
    private SpecificationsAdapterAbs adapter;
    private Goods goods;

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.grid_view);
        gvSize = (GridView) view.findViewById(R.id.gvSize);
        return view;
    }

    @Override
    public void refreshView(Goods data) {
        this.goods = data;
        adapter = new SpecificationsAdapterAbs(goods.specList);
        gvSize.setAdapter(adapter);
    }

    class SpecificationsAdapterAbs extends AbsBaseListAdapter<Specification> {
        List<Specification> list;

        public SpecificationsAdapterAbs(List<Specification> list) {
            super(list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            Specification specification = list.get(position);
            SpecificationsItemHolder specificationsItemHolder = (SpecificationsItemHolder) view.getTag();
            specificationsItemHolder.setTextViewEnable(goods.specification.specId.equals(specification.specId));
            return view;
        }

        @Override
        protected Holder getHolder(int position) {
            SpecificationsItemHolder specificationsItemHolder = new SpecificationsItemHolder();
            specificationsItemHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View textView = v.findViewById(R.id.textView);
                    goods.specification = (Specification) textView.getTag();
                    goods.goodsPrice = goods.specification.specPrice;
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onSpecificationSelected(goods.specification);
                    }
                }
            });
            return specificationsItemHolder;
        }
    }

    private OnSpecificationSelectedListener listener;

    public void setOnSpecificationSelectedListener(OnSpecificationSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnSpecificationSelectedListener {
        void onSpecificationSelected(Specification specification);
    }
}
