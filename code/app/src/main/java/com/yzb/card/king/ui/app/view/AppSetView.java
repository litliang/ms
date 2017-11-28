package com.yzb.card.king.ui.app.view;

import com.yzb.card.king.ui.app.bean.NoticeMenu;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 17:25
 */
public interface AppSetView
{
    void noticeLoadSuccess(NoticeMenu o);

    void noticeLoadFail(Object o);
}
