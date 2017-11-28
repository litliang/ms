package com.yzb.card.king.ui.app.adapter;

import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.app.holder.IdentificationsListHolder;

import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 19:41
 * 描述：
 */
public class IdentificationsListAdapterAbs extends AbsBaseListAdapter<CertType> {

    private final int selectItemType;

    public IdentificationsListAdapterAbs(List<CertType> list, int selectItemType) {
        super(list);
        this.selectItemType=selectItemType;
    }

    @Override
    protected Holder getHolder(int position) {
        return new IdentificationsListHolder(selectItemType);
    }
}
