package com.yzb.card.king.ui.discount.presenter;

import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.model.IScpMain;
import com.yzb.card.king.ui.discount.model.ScpMainDaoImpl;
import com.yzb.card.king.ui.travel.view.TravelMainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/20
 * 描  述：
 */
public class ScpMainPresenter implements DataCallBack {
    private IScpMain scpMain;

    private TravelMainView view;

    public ScpMainPresenter(TravelMainView view)
    {
        this.view = view;
        this.scpMain = new ScpMainDaoImpl(this);
    }

    /**
     * 获取商户列表
     *
     * @param param
     */
    public void getTjsh(Map<String, Object> param)
    {
        scpMain.getTjsh(param);
    }

    /**
     * 个人频道
     *
     * @param parentId
     * @param category
     */
    public void getUserChannel(String parentId, String category)
    {
        scpMain.getUserChannel(parentId, category);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IScpMain.SHANGHULIST_CODE)
        {
            view.callSuccessViewLogic(o, IScpMain.SHANGHULIST_CODE);
        } else if (type == IScpMain.GERENPD_CODE)
        {
            //解析出隐藏的和显示的数据
            List<ChildTypeBean> displayBeans = new ArrayList<>();
            List<ChildTypeBean> hideBeans = new ArrayList<>();

            List<ChildTypeBean> beans = (List<ChildTypeBean>) o;
            if (beans != null && beans.size() > 0)
            {
                for (ChildTypeBean item : beans)
                {
                    item.typeImage = ServiceDispatcher.getImageUrl(item.typeImage);
                    //显示；
                    if ("1".equals(item.status))
                    {
                        displayBeans.add(item);
                    } else
                    {
                        hideBeans.add(item);
                    }
                }
            }

            if (view != null)
            {
                view.transmitChannelData(displayBeans, hideBeans);
            }
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        view.callFailedViewLogic(null,-1);
    }
}
