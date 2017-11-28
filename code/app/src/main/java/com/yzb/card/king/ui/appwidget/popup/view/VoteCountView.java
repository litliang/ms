package com.yzb.card.king.ui.appwidget.popup.view;



/**
 * 功能：评论数量；
 *
 * @author:gengqiyun
 * @date: 2016/9/5
 */
public interface VoteCountView
{
    void onGetVoteCountSucess(boolean event_tag, Object data);

    void onGetVoteCountFail(String failMsg);
}
