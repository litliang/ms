package com.yzb.card.king.ui.app.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.my.CouponNearbyBean;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.adapter.LbtGvAdapter;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.other.task.CustomerChannelListTask;
import com.yzb.card.king.ui.travel.adapter.CouponFiltLAdapter;
import com.yzb.card.king.ui.travel.adapter.CouponFiltRAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.SharePrefUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：从底部弹出的pop；
 *
 * @author:gengqiyun
 * @date: 2017/1/11
 */

public class CommonBottomPop extends PopupWindow implements View.OnClickListener {
    public static final int WHAT_SINGLE = 0x006;
    public static final int WHAT_NEAR = 0x007;
    public static final int WHAT_CATEGORY = 0x008;
    private ListView mLvLeft;
    private ListView mLvRight;
    private GridView mGvCategory;
    private ListView mLvSingle;
    private Context context;

    public final static int TYPE_TWO = 1;
    public final static int TYPE_CATEGORY = 2;
    public final static int TYPE_ONE = 3;

    private List<SubItemBean> oneList;//有1列的数据；

    private List<CatalogueTypeBean> nearbyList;

    private CouponFiltLAdapter leftAdapter;
    private CouponFiltRAdapter rightAdapter;
    private View panelLvRight;
    private LbtGvAdapter channelAdapter;
    private Handler dataHandler;

    private TextView tvLabel;
    private Drawable divider, transDivider;
    private String selectItemId; //选中的页面下标；
    private List<ChildTypeBean> childTypeBeans;

    public CommonBottomPop(Context context)
    {
        this.context = context;
        divider = context.getResources().getDrawable(R.drawable.listview_divider);
        transDivider = new ColorDrawable(Color.TRANSPARENT);
        initView();
    }

    private void initView()
    {
        View v = LayoutInflater.from(context).inflate(R.layout.pop_common_bottom, null);
        setContentView(v);

        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        setOutsideTouchable(true);
        setAnimationStyle(R.style.popupwindow_animation_style);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        oneList = new ArrayList<>();
        nearbyList = new ArrayList<>();

        tvLabel = (TextView) v.findViewById(R.id.tvLabel);
        mLvLeft = (ListView) v.findViewById(R.id.lvLeft);
        mLvRight = (ListView) v.findViewById(R.id.lvRight);
        panelLvRight = v.findViewById(R.id.panelLvRight);

        mGvCategory = (GridView) v.findViewById(R.id.gvCategory);
        mLvSingle = (ListView) v.findViewById(R.id.lvSingle);
        mLvSingle.setDivider(divider);

        v.findViewById(R.id.filterBlack).setOnClickListener(this);
    }

    private void sendMsg(int what, Object obj)
    {
        if (dataHandler != null) {
            Message msg = dataHandler.obtainMessage(what);
            msg.obj = obj;
            dataHandler.sendMessage(msg);
        }
    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y)
    {
        super.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 2列时初始化左侧的；
     */
    private void initLeftView()
    {
        leftAdapter = new CouponFiltLAdapter(context);

        mLvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                parentIndex = position;

                childrenIndex = -1;

                CatalogueTypeBean catalogueTypeBean = nearbyList.get(position);

                oneList = catalogueTypeBean.getChildList();

                leftAdapter.setSelectedEntity(catalogueTypeBean);

                tvLabel.setText(catalogueTypeBean.getTypeName());

                initRightView();
            }
        });
        mLvLeft.setDivider(transDivider);

        mLvLeft.setAdapter(leftAdapter);
    }

    /**
     * 2列时初始化右侧的；
     */
    private void initRightView()
    {

        rightAdapter = new CouponFiltRAdapter(context);

        mLvRight.setDivider(transDivider);

        mLvRight.setAdapter(rightAdapter);

        mLvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                childrenIndex = position;

                dismiss();


                if (dataHandler != null) {
                    Message msg = dataHandler.obtainMessage(WHAT_NEAR);
                    msg.obj = oneList.get(position);
                    dataHandler.sendMessage(msg);
                }
            }
        });

        for(SubItemBean tem: oneList){

            tem.setDefault(false);
        }

        if(childrenIndex != -1){
            oneList.get(childrenIndex).setDefault(true);
        }


        rightAdapter.clearAll();

        rightAdapter.appendALL(oneList);
    }

    /**
     * 1列时数据初始化；
     */
    private void initSingleView()
    {
        rightAdapter = new CouponFiltRAdapter(context);
        mLvSingle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                dismiss();
                sendMsg(WHAT_SINGLE, oneList.get(position));
            }
        });
        mLvSingle.setAdapter(rightAdapter);
        rightAdapter.clearAll();
        rightAdapter.appendALL(oneList);


    }

    /**
     * 设置类型；
     */
    public void setType(int pageType)
    {
        refreshView(pageType);
    }

    private void refreshView(int pageType)
    {
        switch (pageType) {
            case TYPE_ONE:
                mLvLeft.setVisibility(View.GONE);
                panelLvRight.setVisibility(View.GONE);
                mGvCategory.setVisibility(View.GONE);
                mLvSingle.setVisibility(View.VISIBLE);
                break;
            case TYPE_CATEGORY:
                mLvLeft.setVisibility(View.GONE);
                panelLvRight.setVisibility(View.GONE);
                mGvCategory.setVisibility(View.VISIBLE);
                mLvSingle.setVisibility(View.GONE);
                break;
            case TYPE_TWO:
                mLvLeft.setVisibility(View.VISIBLE);
                panelLvRight.setVisibility(View.VISIBLE);
                mGvCategory.setVisibility(View.GONE);
                mLvSingle.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        dismiss();
    }

    public void setOneData(List<SubItemBean> oneList)
    {
        this.oneList = oneList;
        initSingleView();
    }

    private int parentIndex = 0;

    private int childrenIndex = 0;

    /**
     * 初始化two；
     */
    public void initTwoData()
    {
        initLeftView();

        leftAdapter.clearAll();
        leftAdapter.appendALL(nearbyList);
        leftAdapter.setSelectedEntity(nearbyList.get(parentIndex));

        oneList = nearbyList.get(parentIndex).getChildList();

        initRightView();
    }

    /**
     * 获取频道列表；
     */
    private void getUserChannel()
    {
        CustomerChannelListTask task = new CustomerChannelListTask(new CustomerChannelListTask.IChannelListCallBack() {
            @Override
            public void callBack(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
            {
                if (displayChannelList != null) {
                    displayChannelList.addAll(hideChannelList);

                    String localJson = SharePrefUtil.getValueFromSp(GlobalApp.getInstance(), AppConstant.sp_childtypelist_home, "[]");
                    List<ChildTypeBean> localLists = JSON.parseArray(localJson, ChildTypeBean.class);
                    childTypeBeans = CommonUtil.filterList(localLists, displayChannelList);

                    childTypeBeans.add(new ChildTypeBean("0000", "全部"));
                    initCategoryView();
                }
            }
        });
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("parentId", AppConstant.discount_type_parentid);
        param.put("category", AppConstant.discount_channel_category);
        task.setParamData(param);
        task.sendRequest(null);
    }

    private void initCategoryView()
    {
        if (childTypeBeans != null && childTypeBeans.size() > 0) {
            channelAdapter = new LbtGvAdapter(childTypeBeans);
            channelAdapter.setCallBack(new LbtGvAdapter.IGvItemClickCallBack() {
                @Override
                public void callBack(ChildTypeBean typeBean)
                {
                    dismiss();
                    sendMsg(WHAT_CATEGORY, typeBean);
                }
            });
            mGvCategory.setAdapter(channelAdapter);
        }
    }

    public void initCategoryData()
    {
        if (childTypeBeans != null && childTypeBeans.size() > 0) {
            initCategoryView();
        } else {
            getUserChannel();
        }
    }

    public void setDataHandler(Handler handler)
    {
        this.dataHandler = handler;
    }

    public void setNearbyBean(List<CatalogueTypeBean> catalogueTypeBeanList)
    {

        this.nearbyList = catalogueTypeBeanList;
    }

    public List<CatalogueTypeBean> getNearbyList()
    {
        return nearbyList;
    }


    /**
     * 设置选中的页面下标；
     */
    public void setSelectItemId(String selectItemId)
    {
        this.selectItemId = selectItemId;
    }
}
