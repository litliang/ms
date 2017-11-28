package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.SearchDefaultBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.SearchDefaultItemView;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 功能：搜索默认列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/27
 */
public class SearchDefaultAdapter extends BaseListAdapter<SearchDefaultBean>
{
    private static int ITEM_MAX_SIZE = 8;//每项最大数量；

    public SearchDefaultAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_search_default, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SearchDefaultBean bean = getItem(position);
        CommonUtil.downloadImageForView(ServiceDispatcher.getImageUrl(bean.getPhotoCode()), viewHolder.ivpic, 25);

        viewHolder.title.setText(bean.getName());
        //数量超过8个显示伸展图标；

        //根据伸展状态填充不同数量的数据；
        List<SearchDefaultBean.Child> gvList = bean.getChildList();
        int itemCount = gvList.size();
        boolean hasMore = itemCount > ITEM_MAX_SIZE ? true : false;

        viewHolder.ivnavigation.setVisibility(hasMore ? View.VISIBLE : View.INVISIBLE);
        // 展开；
        if (bean.isExpand())
        {
            viewHolder.ivnavigation.setSelected(true);
            viewHolder.childGridView.setData(gvList);
        } else
        {
            viewHolder.ivnavigation.setSelected(false);
            viewHolder.childGridView.setData(hasMore ? gvList.subList(0, 8) : gvList);
        }
        if (hasMore)
        {
            //导航点击；
            viewHolder.panel_navigation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    bean.setExpand(!bean.isExpand());
                    notifyDataSetChanged();
                }
            });
        }
        viewHolder.childGridView.setItemListener(callBack);
        return viewHolder.root;
    }

    public class ViewHolder
    {
        @ViewInject(R.id.iv_pic)
        public ImageView ivpic;

        @ViewInject(R.id.title)
        public TextView title;

        @ViewInject(R.id.iv_navigation)
        public ImageView ivnavigation;

        @ViewInject(R.id.childGridView)
        public SearchDefaultItemView childGridView;
        public View root;
        @ViewInject(R.id.panel_navigation)
        public View panel_navigation;

        public ViewHolder(View root)
        {
            this.root = root;
            x.view().inject(this, root);
        }
    }

    private ICallBack callBack;

    public void setCallBack(ICallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface ICallBack
    {
        void callBack(SearchDefaultBean.Child child);
    }

}
