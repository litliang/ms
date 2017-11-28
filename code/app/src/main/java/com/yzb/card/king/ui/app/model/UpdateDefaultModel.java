package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.ui.app.base.BaseNoRefreshModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：更新默认发票抬头
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class UpdateDefaultModel extends BaseModelImpl implements BaseNoRefreshModel
{
    private String id; //信息id，用户成功后的更新处理；

    public UpdateDefaultModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final Map<String, Object> paramMap)
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
