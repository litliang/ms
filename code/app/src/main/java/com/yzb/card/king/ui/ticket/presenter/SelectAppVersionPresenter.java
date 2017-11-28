package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.SelectAppVersionModel;
import com.yzb.card.king.ui.ticket.view.SelectAppVersionView;

import java.util.Map;

/**
 * 作者：刘锐 on 2017/3/2 14:42
 * 邮箱：liurui@payvel.com
 * 版本更新
 */

public class SelectAppVersionPresenter implements BaseMultiLoadListener {

	private SelectAppVersionModel model;
	private SelectAppVersionView view;

	public SelectAppVersionPresenter(SelectAppVersionView view) {
		this.view = view;
		model = new SelectAppVersionModelImpl(this);
	}

	public void loadData(Map<String, Object> paramMap) {
		model.loadData(paramMap);
	}

	@Override
	public void onListenSuccess(boolean event_tag, Object data) {
		view.onSelectAppVersionSucess(String.valueOf(data));
	}

	@Override
	public void onListenError(String msg) {
		view.onSelectAppVersionFail(msg);
	}
}
