package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.SearchResultBean;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.Utils;


/**
 * 功能：搜索结果列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/27
 */
public class SearchListAdapter extends BaseListAdapter<SearchResultBean>
{
    private String keyWord; //关键字；
    private boolean flag; //true:关键字搜索；false：酒店搜索；

    private int type =-1;// 1:优惠券搜索

    public SearchListAdapter(Context context)
    {
        super(context);
    }

    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_search_result, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SearchResultBean bean = getItem(position);


        if(type == 1){
            viewHolder.tvtitle.setText(Utils.keyWordMatch(bean.getStoreName(), keyWord, "#be9743"));
        }else {

            if (flag)
            {
                viewHolder.tvtitle.setText(Utils.keyWordMatch(bean.getKeyword(), keyWord, "#be9743"));
            } else
            {
                viewHolder.tvtitle.setText(Utils.keyWordMatch(bean.getObjName(), keyWord, "#be9743"));
            }

        }

        viewHolder.root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (callBack != null)
                {
                    callBack.callBack(bean);
                }
            }
        });
        return viewHolder.root;
    }

    public void setKeyWord(String keyWord)
    {
        this.keyWord = keyWord;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }


    public class ViewHolder
    {
        public TextView tvtitle;
        public View root;

        public ViewHolder(View root)
        {
            tvtitle = (TextView) root.findViewById(R.id.tv_title);
            this.root = root;
        }
    }

    private ISearchCallBack callBack;

    public void setCallBack(ISearchCallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface ISearchCallBack
    {
        void callBack(SearchResultBean bean);
    }
}
