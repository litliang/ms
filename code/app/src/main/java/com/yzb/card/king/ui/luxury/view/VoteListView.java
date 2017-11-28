package com.yzb.card.king.ui.luxury.view;

/**
 * 功能：评价列表
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public interface VoteListView
{
    /**
     * @param event_tag 下拉或上拉；
     * @param data
     */
    void onLoadVoteListSucess(boolean event_tag, Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadVoteListFail(String failMsg);
}
