package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.ui.app.base.BaseNoRefreshModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：发票抬头更新（删除或修改）
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class SettingUpdateModel extends BaseModelImpl implements BaseNoRefreshModel
{
    private String id;

    public SettingUpdateModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = String.valueOf(paramMap.get("serviceName"));
        this.id = String.valueOf(paramMap.get("id"));
        paramMap.remove("serviceName");
        paramMap.remove("id");
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        LogUtil.i("更新成功=" + data);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, id);
        }
    }
}
