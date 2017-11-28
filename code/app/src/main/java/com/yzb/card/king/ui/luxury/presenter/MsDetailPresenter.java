package com.yzb.card.king.ui.luxury.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.luxury.model.IMsDetail;
import com.yzb.card.king.ui.luxury.model.MsDetailDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/20
 * 描  述：
 */
public class MsDetailPresenter implements DataCallBack {
    private IMsDetail msDetail;

    private BaseViewLayerInterface view;

    public MsDetailPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.msDetail = new MsDetailDaoImpl(this);
    }

    /**
     * 获取商家信息
     *
     * @param param
     */
    public void getData(Map<String, Object> param)
    {
        msDetail.getData(param);
    }


    /**
     * 收藏
     *
     * @param param
     */
    public void collect(Map<String, Object> param)
    {
        msDetail.collect(param);
    }

    /**
     * 查询是否收藏
     *
     * @param param
     */
    public void isCollect(Map<String, Object> param)
    {
        msDetail.isCollect(param);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IMsDetail.GETDATA_CODE)//获取信息成功回调
        {
            view.callSuccessViewLogic(o, IMsDetail.GETDATA_CODE);
        } else if (type == IMsDetail.COLLECT_CODE)  //收藏成功回调
        {
            view.callSuccessViewLogic(o, IMsDetail.COLLECT_CODE);
        } else if (type == IMsDetail.ISCOLLECT_CODE)    //查询是否收藏成功回调
        {
            view.callSuccessViewLogic(o, IMsDetail.ISCOLLECT_CODE);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == IMsDetail.GETDATA_CODE) //获取信息失败回调
        {
            view.callFailedViewLogic(o, IMsDetail.GETDATA_CODE);
        } else if (type == IMsDetail.COLLECT_CODE)  //收藏失败回调
        {
            view.callFailedViewLogic(o, IMsDetail.COLLECT_CODE);
        } else if (type == IMsDetail.ISCOLLECT_CODE)  //查询手法收藏失败回调
        {
            view.callFailedViewLogic(o, IMsDetail.ISCOLLECT_CODE);
        }
    }
}
