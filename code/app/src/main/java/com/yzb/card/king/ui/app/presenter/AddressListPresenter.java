package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.app.model.AddressListModel;
import com.yzb.card.king.ui.app.view.AddressListView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：收货地址列表
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class AddressListPresenter implements BaseMultiLoadListener
{
    private AddressListModel model;
    private AddressListView view;

    public AddressListPresenter(AddressListView view)
    {
        this.view = view;
        model = new AddressListModel(this);
    }

    public void loadData(boolean event_flag, Map<String, Object> paramMap)
    {
        model.loadData(event_flag, paramMap);
    }


    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadAddressListSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadAddressListFail(msg);
    }

    public GoodsAddressBean getDefaultAddress(Object data)
    {
        if (data != null)
        {
            List<GoodsAddressBean> addressBeans = (List<GoodsAddressBean>) data;
            for (int i = 0; i < addressBeans.size(); i++)
            {
                if (addressBeans.get(i).isDefault)
                {
                    return addressBeans.get(i);
                }
            }
        }
        return null;
    }
}
