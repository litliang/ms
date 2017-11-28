package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.SelectAppVersionModel;

import java.util.Map;

/**
 * 作者：刘锐 on 2017/3/2 14:51
 * 邮箱：liurui@payvel.com
 */

public class SelectAppVersionModelImpl extends BaseModelImpl implements SelectAppVersionModel {
	private String applicationType; //应用类型

	public SelectAppVersionModelImpl(BaseMultiLoadListener listener) {
		super(listener);
	}

	@Override
	protected void afterSuccess(String data) {
		if (loadListener != null) {
			loadListener.onListenSuccess(true, data);
		}
	}

	@Override
	public void loadData(Map<String, Object> paramMap) {

		this.serviceName = String.valueOf(paramMap.get("serviceName"));
		this.applicationType = String.valueOf(paramMap.get("applicationType"));
		this.paramMap = paramMap;
		this.paramMap.remove("serviceName");
		sendRequest();
	}
}
