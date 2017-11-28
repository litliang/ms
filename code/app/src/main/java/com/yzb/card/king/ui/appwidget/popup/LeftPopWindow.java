package com.yzb.card.king.ui.appwidget.popup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.citylife.activity.CityLifeFilterListActivity;
import com.yzb.card.king.ui.credit.holder.LeftPopHolder;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：
 * 作者：殷曙光
 * 日期：2016/6/4 16:11
 * 描述：
 */
public class LeftPopWindow extends PopupWindow implements View.OnClickListener
{
    private final View llContent;
    private Context context;
    private View mRoot;
    private GridView gridView;
    private List<ChildTypeBean> dataList = new ArrayList<>();
    private ChildTypeBean selectedBean;


    /**
     * 此时：信用卡模块；
     *
     * @param context
     */
    public LeftPopWindow(Context context)
    {
        super(context);

        this.context = context;
        mRoot = LayoutInflater.from(context).inflate(R.layout.popwindow_left, null);
        mRoot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        gridView = (GridView) mRoot.findViewById(R.id.gridView);
        llContent =  mRoot.findViewById(R.id.llContent);
        llContent.setOnClickListener(this);
        this.setContentView(mRoot);
        init();
        initData();

    }

    private void init()
    {
        this.setContentView(mRoot);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

    }


    private void initData()
    {
        getTypeBean();
    }

    private void getTypeBean()
    {
        // 本地json格式的子分类；
        String childTypeJson = SharePrefUtil.getValueFromSp(UiUtils.getContext(),
                AppConstant.sp_childtypelist_home, "[]");

        List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);
        dataList.clear();
        dataList.addAll(childTypeBeans);
        initGridLayout();
    }

    private void initGridLayout()
    {

        AbsBaseListAdapter adapter = new AbsBaseListAdapter<ChildTypeBean>(dataList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new LeftPopHolder();
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ChildTypeBean item = dataList.get(position);
                selectedBean = item;
                action(Integer.parseInt(item.id));
            }
        });
    }

    private void action(int id)
    {
        GlobalVariable.industryId = id;
        Intent intent = new Intent();
        switch (id)
        {
            case 1:             //美食
                setMsIntent(intent);
                break;
            case 2:             //丽人
                break;
            case 3:             //旅游
                setTravelIntent(intent);
                break;
            case 4:             //婚庆
                intent.setClass(context, ChannelMainActivity.class);
                break;
            case 5:             //奢侈品
                setLuxuryIntent(intent);
                break;
            case 6:             //机票
                setTicketIntent(intent);
                break;
            case 7:             //电影
                setMovieIntent(intent);
                break;
            case 8:             //酒店
                setHotelIntent(intent);
                break;
            case 9:             //陆上交通
                setTransportIntent(intent);
                break;
            case 10:             //银行优惠
                setBankDiscountIntent(intent);
                break;
            case 120:
                setCityLifeIntent(intent);
                break;
            case 121:
                setTaiJiIntent(intent);
                break;
            case 85:
                intent.setClass(context, ChannelMainActivity.class);
                intent.putExtra("pagetype", 0);
                break;
            default:
                intent.setClass(context, ChannelMainActivity.class);
                intent.putExtra("pagetype", 0);
                break;
        }
        context.startActivity(intent);
    }

    protected void setTaiJiIntent(Intent intent)
    {
//        intent.setClass(context, TaijiLifeFilterListActivity.class);
//        intent.putExtra("pagetype", 8);
    }

    protected void setCityLifeIntent(Intent intent)
    {
        intent.setClass(context, CityLifeFilterListActivity.class);
        intent.putExtra("pagetype", 7);
    }

    protected void setBankDiscountIntent(Intent intent)
    {
        intent.setClass(context, ChannelMainActivity.class);
        intent.putExtra("pagetype", 0);
    }

    protected void setTransportIntent(Intent intent)
    {
        intent.setClass(context, ChannelMainActivity.class);
        intent.putExtra("pagetype", 6);
    }

    protected void setHotelIntent(Intent intent)
    {
//        intent.setClass(context, HotelListActivity.class);
//        HotelParam param = new HotelParam();
//        HotelFilterView.data.setDisBankCode("1");
//        param.setDepDate(new Date());
//        param.setArrDate(new Date());
//       param.setCityId();
//        intent.putExtra("param",param);
    }

    protected void setMovieIntent(Intent intent)
    {
//        intent.setClass(context, FilmMoreActivity.class);
//        Bundle bundle7 = new Bundle();
//        bundle7.putString("typeParentId", AppConstant.film_id);
//        bundle7.putString("typeId", "37");
//        bundle7.putString("typeName", "影片");
    }

    protected void setTicketIntent(Intent intent)
    {
        intent.putExtra("pagetype", 3);
        intent.putExtra("data",selectedBean);
        intent.setClass(context, ChannelMainActivity.class);
    }

    protected void setLuxuryIntent(Intent intent)
    {
//        intent.setClass(context, MsMoreActivity.class);
//        Bundle bundle4 = new Bundle();
//        bundle4.putString("typeParentId", AppConstant.shechipin_id);
//        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle4);
    }

    protected void setTravelIntent(Intent intent)
    {
        intent.setClass(context, ChannelMainActivity.class);

        intent.putExtra("pagetype", 5);

        intent.putExtra("data", selectedBean);

//        startActivity(intent);
//        intent.setClass(context, TravelLineListActivity.class);
//        Bundle bundle3 = new Bundle();
//        bundle3.putString("typeGrandParentId", AppConstant.travel_id);
//        bundle3.putString("typeName", "旅游");
    }

    protected void setMsIntent(Intent intent)
    {
//        intent.setClass(context, MsMoreActivity.class);
//        Bundle bundle2 = new Bundle();
//        bundle2.putString("typeGrandParentId", AppConstant.meishi_id);
//        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle2);
    }

    @Override
    public void onClick(View v)
    {

    }
}
