package com.yzb.card.king.ui.appwidget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.GiftcardTypeBean;
import com.yzb.card.king.ui.my.adapter.FilterOneAdapter;
import com.yzb.card.king.ui.my.model.DataUtil;
import com.yzb.card.king.ui.gift.presenter.GiftcardTypePresenter;
import com.yzb.card.king.ui.gift.view.GiftcardTypeView;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能：购买e卡筛选菜单；
 *
 * @author:gengqiyun
 * @date: 2016/12/16
 */
public class FilterECardMenuView extends BaseViewGroup implements View.OnClickListener, AdapterView.OnItemClickListener,
        GiftcardTypeView
{
    TextView tvCategory;
    ImageView ivCategoryArrow;

    TextView tvSort;
    ImageView ivSortArrow;
    ListView listView;
    LinearLayout llContentListView;
    View viewMaskBg;   //灰色背景；

    private boolean isShowing = false;
    private int filterPosition = -1;
    private int panelHeight;
    private GiftcardTypeBean selectCategory; //选中的分类；
    private GiftcardTypeBean selectSort; //选中的排序；
    private FilterOneAdapter oneAdapter;
    private GiftcardTypePresenter typePresenter;
    private List<GiftcardTypeBean> categoryList;
    private List<GiftcardTypeBean> sortList;

    public FilterECardMenuView(Context context)
    {
        super(context);
    }

    public FilterECardMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FilterECardMenuView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.view_filter_ecard;
    }

    @Override
    protected void init()
    {
        super.init();
        typePresenter = new GiftcardTypePresenter(this);
        tvCategory = (TextView) rootView.findViewById(R.id.tv_category);
        ivCategoryArrow = (ImageView) rootView.findViewById(R.id.iv_category_arrow);

        tvSort = (TextView) rootView.findViewById(R.id.tv_sort);
        ivSortArrow = (ImageView) rootView.findViewById(R.id.iv_sort_arrow);

        listView = (ListView) rootView.findViewById(R.id.listView);

        llContentListView = (LinearLayout) rootView.findViewById(R.id.ll_content_list_view);

        rootView.findViewById(R.id.ll_category).setOnClickListener(this);
        rootView.findViewById(R.id.ll_sort).setOnClickListener(this);

        viewMaskBg = rootView.findViewById(R.id.view_mask_bg);
        viewMaskBg.setOnClickListener(this);

        llContentListView.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        sortList = DataUtil.getSortData();
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);

        initMenuData();
    }

    @Override
    public void onGetGiftcardTypeSuc(List<GiftcardTypeBean> typeBeanList)
    {
        List<GiftcardTypeBean> beanList = new ArrayList<>();
        GiftcardTypeBean bean = new GiftcardTypeBean();
        bean.setTypeName("全部");
        bean.setTypeId("");
        beanList.add(bean);

        beanList.addAll(typeBeanList);
        this.categoryList = beanList;

        showFilterLayout(0);
    }

    private  int    category;//1 实体卡，2电子卡

    private int ecardType = -1;//1 心意卡，2礼品卡

    public void setCategory(int category,int ecardType){

        this.category = category;

        this.ecardType = ecardType;

    }

    @Override
    public void onGetGiftcardTypeFail(String failMsg)
    {
        toast(failMsg);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_category: //分类；
                filterPosition = 0;
                if (categoryList == null || categoryList.size() == 0)
                {
                    getCategories();
                } else
                {
                    showFilterLayout(0);
                }
                break;
            case R.id.ll_sort://排序；
                filterPosition = 1;
                showFilterLayout(1);
                break;
            case R.id.view_mask_bg:  //灰色背景；
                hide();
                break;
        }
    }

    /**
     * 获取分类菜单列表；
     */
    private void getCategories()
    {
        Map<String, Object> args = new HashMap<>();

        args.put("category",category);

        if(ecardType!=-1){
            args.put("ecardType",ecardType);
        }

        typePresenter.loadData(true, args);
    }

    // 显示筛选布局
    public void showFilterLayout(int position)
    {
        resetFilterStatus();
        switch (position)
        {
            case 0:
                setCategoryAdapter();
                break;
            case 1:
                setSortAdapter();
                break;
        }
        if (!isShowing)
        {
            show();
        }
    }

    /**
     * menu菜单item点击；
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        GiftcardTypeBean filterBean = null;
        switch (filterPosition)
        {
            case 0:
                filterBean = categoryList.get(position);
                selectCategory = filterBean;
                tvCategory.setText(selectCategory.getTypeName());
                break;
            case 1:
                filterBean = sortList.get(position);
                selectSort = filterBean;
                tvSort.setText(selectSort.getTypeName());
                break;
        }
        if (itemListener != null)
        {
            itemListener.onFilterItemClick(filterBean, filterPosition);
        }
        hide();
    }

    public FilterItemListener itemListener;

    public void setFilterItemListener(FilterItemListener itemListener)
    {
        this.itemListener = itemListener;
    }

    /**
     * item点击；
     */
    public interface FilterItemListener
    {
        void onFilterItemClick(GiftcardTypeBean filterBean, int type);
    }

    /**
     * 暂时注释；
     */
    private void initMenuData()
    {
//        if (categoryList != null && categoryList.size() > 0)
//        {
//            selectCategory = categoryList.get(0);
//            tvCategory.setText(selectCategory.getTypeName());
//        }
        if (sortList != null && sortList.size() > 0)
        {
            selectSort = sortList.get(0);
            tvSort.setText(selectSort.getTypeName());
        }
    }

    // 设置排序数据
    private void setSortAdapter()
    {
        if (sortList != null && sortList.size() > 0)
        {
            tvSort.setSelected(true);
            ivSortArrow.setImageResource(R.mipmap.icon_arrow_up_big);
            listView.setVisibility(VISIBLE);
//            if (selectSort == null)
//            {
//                //默认选中第1个；
//                selectSort = sortList.get(0);
//            }
            oneAdapter = new FilterOneAdapter(mContext, sortList);
            listView.setAdapter(oneAdapter);
//            oneAdapter.setSelectedEntity(selectSort);
            listView.setOnItemClickListener(this);
        }
    }

    /**
     * 设置分类数据
     */
    private void setCategoryAdapter()
    {
        if (categoryList != null && categoryList.size() > 0)
        {
            tvCategory.setSelected(true);
            ivCategoryArrow.setImageResource(R.mipmap.icon_arrow_up_big);
            listView.setVisibility(VISIBLE);

//            if (selectCategory == null)
//            {
//                //默认选中第1个；
//                selectCategory = filterData.getCategory().get(0);
//            }
            oneAdapter = new FilterOneAdapter(mContext, categoryList);
            listView.setAdapter(oneAdapter);
//            oneAdapter.setSelectedEntity(selectCategory);
            listView.setOnItemClickListener(this);
        }
    }

    /**
     * 是否显示
     *
     * @return
     */
    public boolean isShowing()
    {
        return isShowing;
    }

    /**
     * 动画显示
     */
    private void show()
    {
        isShowing = true;
        viewMaskBg.setVisibility(VISIBLE);
        llContentListView.setVisibility(VISIBLE);

        llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = llContentListView.getHeight();
                ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });
    }

    // 隐藏动画
    public void hide()
    {
        isShowing = false;
        resetFilterStatus();
        viewMaskBg.setVisibility(View.GONE);
        llContentListView.setVisibility(GONE);
    }

    /**
     * 复位筛选的显示状态
     */
    public void resetFilterStatus()
    {
        tvCategory.setSelected(false);
        ivCategoryArrow.setImageResource(R.mipmap.icon_arrow_down_big);

        tvSort.setSelected(false);
        ivSortArrow.setImageResource(R.mipmap.icon_arrow_down_big);
    }

    /**
     * 复位所有的状态
     */
    public void resetAllStatus()
    {
        resetFilterStatus();
        hide();
    }
}
