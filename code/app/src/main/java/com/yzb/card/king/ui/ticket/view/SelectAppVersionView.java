package com.yzb.card.king.ui.ticket.view;

/**
 * 作者：刘锐 on 2017/3/2 14:45
 * 邮箱：liurui@payvel.com
 * 版本更新
 */

public interface SelectAppVersionView {
	/**
	 * 获取成功；
	 */
	void onSelectAppVersionSucess(String versioninfo);

	/**
	 * 获取失败；
	 *
	 * @param failMsg 错误消息；
	 */
	void onSelectAppVersionFail(String failMsg);
}
