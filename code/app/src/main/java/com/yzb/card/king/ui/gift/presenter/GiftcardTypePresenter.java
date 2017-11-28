package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.ui.gift.bean.GiftcardTypeBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.gift.modle.GiftcardTypeModel;
import com.yzb.card.king.ui.gift.view.GiftcardTypeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：查询礼品卡分类列表
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class GiftcardTypePresenter implements BaseMultiLoadListener
{
    private GiftcardTypeModel model;
    private GiftcardTypeView view;

    public GiftcardTypePresenter(GiftcardTypeView view)
    {
        this.view = view;
        model = new GiftcardTypeModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    /**
     * 设置此接口参数信息
     * @param event_tag
     * @param typeId  分类id  非必填
     * @param typeName  分类名称  非必填
     */
    public void setInterfaceParameters(boolean event_tag,String typeId,String typeName){
        Map<String, Object> args = new HashMap<>();
        args.put("typeId",typeId);
        args.put("typeName",typeName);
        model.loadData(event_tag, args);
    }


    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetGiftcardTypeSuc((List<GiftcardTypeBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetGiftcardTypeFail(msg);
    }
}
