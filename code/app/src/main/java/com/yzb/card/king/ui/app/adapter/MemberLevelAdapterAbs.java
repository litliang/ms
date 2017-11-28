package com.yzb.card.king.ui.app.adapter;

import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.app.bean.LevelGridItem;
import com.yzb.card.king.ui.app.holder.MemberLevelGridHolder;

import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 9:41
 * 描述：
 */
public class MemberLevelAdapterAbs extends AbsBaseListAdapter<LevelGridItem> {

    public MemberLevelAdapterAbs(List<LevelGridItem> list) {
        super(list);
    }

    @Override
    protected Holder getHolder(int position) {
        return new MemberLevelGridHolder();
    }
}
