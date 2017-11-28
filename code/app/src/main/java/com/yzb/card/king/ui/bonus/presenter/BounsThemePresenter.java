package com.yzb.card.king.ui.bonus.presenter;

import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.bonus.model.BounsThemeModel;
import com.yzb.card.king.ui.bonus.view.BounsThemeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：发红包首页；
 *
 * @author:gengqiyun
 * @date: 2016/12/12
 */
public class BounsThemePresenter implements BaseMultiLoadListener
{
    private BounsThemeModel model;

    private BounsThemeView view;

    public BounsThemePresenter(BounsThemeView view)
    {
        this.view = view;
        model = new BounsThemeModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    public void setInterfaceParameters(String pageStart){
        Map<String, Object> args = new HashMap<>();
        args.put("sort", "2"); //1、随机、2最新（创建时间倒序）
        args.put("pageStart", pageStart);
        args.put("pageSize", AppConstant.MAX_PAGE_NUM);
        model.loadData(true, args);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetBounsThemeSuc(event_tag, (List<BounsThemeBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetBounsThemeFail(msg);
    }
}
