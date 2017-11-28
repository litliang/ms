package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.RoomBean;
import com.yzb.card.king.bean.hotel.RoomPolicy;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.discount.bean.LbtBean;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：房间详情
 * 作  者：Li Yubing
 * 日  期：2016/7/26
 * 描  述：酒店详情---房间详情
 * <p/>
 * 第一次修改：gengqiyun  2016.10.30
 */
public class RoomDetailDialogFragment extends BaseDialogFragment implements View.OnClickListener
{
    private Activity context;
    private boolean ifShowSubscribe = true;
    private RoomPolicy roomPolicy;
    private TextView tvPrice;
    private TextView tvRoomName;
    private SlideShow1ItemView show1ItemView;
    private TextView tvRoomFoor;
    private TextView tvAddBed;
    private TextView tvBedType;
    private TextView tvBedWidth;
    private TextView tvZaocan;
    private TextView tvRoomArea;
    private TextView tvSmoking;
    private TextView tvWifi;

    public RoomDetailDialogFragment setIfShowSubscribe(boolean ifShowSubscribe)
    {
        this.ifShowSubscribe = ifShowSubscribe;
        return this;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    public RoomDetailDialogFragment setRoomPolicy(RoomPolicy roomPolicy)
    {
        this.roomPolicy = roomPolicy;
        return this;
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    protected int getGravity()
    {
        return Gravity.CENTER;
    }

    @Override
    protected int getDialogWidth()
    {
        return CommonUtil.getScreenWidth(getActivity());
    }

    @Override
    protected int getDialogHeight()
    {
        return CommonUtil.getScreenHeight(getActivity());
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_room_detail;
    }

    @Override
    protected void initView(View view)
    {
        view.findViewById(R.id.ivDismissDialog).setOnClickListener(this);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        View tvSubscribe = view.findViewById(R.id.tvSubscribe);
        tvSubscribe.setVisibility(ifShowSubscribe ? View.VISIBLE : View.GONE);
        tvSubscribe.setOnClickListener(this);

        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvRoomName = (TextView) view.findViewById(R.id.tvRoomName);
        show1ItemView = (SlideShow1ItemView) view.findViewById(R.id.show1ItemView);
        tvRoomArea = (TextView) view.findViewById(R.id.tvRoomArea);
        tvRoomFoor = (TextView) view.findViewById(R.id.tvRoomFoor);
        tvZaocan = (TextView) view.findViewById(R.id.tvZaocan);
        tvBedType = (TextView) view.findViewById(R.id.tvBedType);
        tvBedWidth = (TextView) view.findViewById(R.id.tvBedWidth);
        tvAddBed = (TextView) view.findViewById(R.id.tvAddBed);
        tvSmoking = (TextView) view.findViewById(R.id.tvSmoking);
        tvWifi = (TextView) view.findViewById(R.id.tvWifi);
        initViewContent();
    }

    /**
     * 初始化view数据；
     */
    public void initViewContent()
    {
        if (roomPolicy == null)
        {
            return;
        }
        RoomBean homeInfoBean = roomPolicy.getRooms();
        tvPrice.setText("¥" + Utils.subZeroAndDot(roomPolicy.getPolicysPrice() + ""));
        show1ItemView.setImageDataList(getPhotos(roomPolicy.getRooms()));

        tvRoomName.setText(homeInfoBean.getRoomsName());
        tvRoomArea.setText("建筑面积" + homeInfoBean.getAreaDesc());
        tvRoomFoor.setText("楼层" + homeInfoBean.getFloorDesc());
        tvZaocan.setText(homeInfoBean.getBreakfastDesc());
        tvBedType.setText(homeInfoBean.getBedTypeDesc());
        tvBedWidth.setText(homeInfoBean.getBedWidth());
        tvAddBed.setText(homeInfoBean.getAddBedDesc());
        tvSmoking.setText(homeInfoBean.getSmokeDesc());
        tvWifi.setText(homeInfoBean.getWifiDesc());
    }

    /**
     * 获取图片列表；
     *
     * @param bean
     * @return
     */
    private List<LbtBean> getPhotos(RoomBean bean)
    {
        List<LbtBean> list = new ArrayList<>();
        String[] urls = bean.getPhotoUrls().split(",");
        for (int i = 0; i < urls.length; i++)
        {
            list.add(new LbtBean(urls[i]));
        }
        return list;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvSubscribe: //预约
//                Intent intent = new Intent();
//                intent.setClass(context, RoomOrderActivity.class);
//                intent.putExtra("policy", roomPolicy);
//                context.startActivityForResult(intent, HotelDetailActivity.REQ_ORDER);
//                dismiss();
                break;
            case R.id.ivDismissDialog:
                dismiss();
                break;
        }
    }

}
