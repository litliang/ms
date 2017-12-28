package com.yzb.card.king.util.crashreport;

import java.util.HashMap;
import java.util.Map;

import com.yzb.card.king.util.crashreport.MapAdapter.AdaptInfo.*;

/***
 * mapadapter 样式部署类
 * @author Administrator
 *
 */
public class StyleBox {

	public Map<Integer, Style> handlers = new HashMap<Integer, Style>();
	public Map<Integer, MapAdapter.AdaptInfo.Style> selectedStyle = new HashMap<Integer, Style>();

	public StyleBox(Style lwListViewHandler) {
		// TODO Auto-generated constructor stub
		addStyle(lwListViewHandler);

	}

	public void addStyle(Style lwListViewHandler) {
		// TODO Auto-generated constructor stub
		if (lwListViewHandler.selectedItem) {
			selectedStyle.put(lwListViewHandler.styleitem, lwListViewHandler);
			return;
		}
		handlers.put(lwListViewHandler.styleitem, lwListViewHandler);
	}

}
