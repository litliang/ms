package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.model.DebitRiseListModel;
import com.yzb.card.king.ui.app.model.SettingUpdateModel;
import com.yzb.card.king.ui.app.view.DebitRiseListView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.List;
import java.util.Map;

/**
 * 功能：发票抬头列表
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class DebitRiseListPresenter implements  BaseMultiLoadListener
{
    public static final int  INVOICELIST_CODE = CardConstant.setting_invoicelist.hashCode();

    public static final int  INVOICECREATE_CODE = CardConstant.setting_invoicecreate.hashCode();

    private DebitRiseListModel model;

    private SettingUpdateModel modelUpdate;

    private BaseViewLayerInterface view;

    private int currentCode ;

    public DebitRiseListPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new DebitRiseListModel(this);

        modelUpdate = new SettingUpdateModel(this);
    }

    /**
     * 请求发票抬头信息
     * @param event_flag
     * @param paramMap
     */
    public void loadData(boolean event_flag, Map<String, Object> paramMap)
    {
        currentCode = INVOICELIST_CODE;

        model.loadData(event_flag, paramMap);
    }

    /**
     * 添加或者修改
     * @param paramMap
     */
    public void addOrUpdateRiseData(Map<String, Object> paramMap){

        currentCode = INVOICECREATE_CODE;

        modelUpdate.loadData(paramMap);
    }




    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {

        view.callSuccessViewLogic(data,currentCode);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,currentCode);
    }

    public DebitRiseBean getDefaultDebitRise(Object data)
    {
        if (data != null)
        {
            List<DebitRiseBean> addressBeans = (List<DebitRiseBean>) data;
            for (int i = 0; i < addressBeans.size(); i++)
            {
                if (addressBeans.get(i).isDefault())
                {
                    return addressBeans.get(i);
                }
            }
        }
        return null;
    }
}
