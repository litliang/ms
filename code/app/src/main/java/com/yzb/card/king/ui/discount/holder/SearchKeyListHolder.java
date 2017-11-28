package com.yzb.card.king.ui.discount.holder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.bean.History;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/8/18 14:13
 * 描述：
 */
public class SearchKeyListHolder extends Holder<History> implements AdapterView.OnItemClickListener {
    private ListView listView;
    private KeyListAdapterAbs adapter;
    private List<History> dataList = new ArrayList<>();
    private OnItemClickListener listener;
//    private GetKeyListTask getKeyListTask;

    public void setListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_search_key_list);
        listView = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void refreshView(History data)
    {
        adapter = new KeyListAdapterAbs(dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
//        if(getKeyListTask!=null) {
////            getKeyListTask.cancel(true);
//            getKeyListTask = null;
//        }
//        getKeyListTask = new GetKeyListTask(data);
//        getKeyListTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        History history = dataList.get(position);
        if (listener != null)
            listener.onItemClick(history);
    }


    public interface OnItemClickListener {
        void onItemClick(History history);
    }

    class KeyListAdapterAbs extends AbsBaseListAdapter<History> {

        public KeyListAdapterAbs(List<History> list)
        {
            super(list);
        }

        @Override
        protected Holder getHolder(int position)
        {
            return new KeyListItemHolder();
        }
    }

    class KeyListItemHolder extends Holder<History> {

        private TextView textView;

        @Override
        public View initView()
        {
            View view = UiUtils.inflate(R.layout.item_search_key_list);
            textView = (TextView) view.findViewById(R.id.textView);
            return view;
        }

        @Override
        public void refreshView(History data)
        {
            textView.setText(data.key);
        }
    }

//    class GetKeyListTask extends CommentTask{
//        History history ;
//        public GetKeyListTask(History history) {
//            super("");
//            this.history = history;
//        }
//
//        @Override
//        protected void parseJson(String data) {
//            List<History> list = JSON.parseArray(data,History.class);
//            dataList.clear();
//            dataList.addAll(list);
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected void setParam(Map<String, String> param) {
//            param.put("key",history.key);
//            param.put("parentType",history.parentType);
//            param.put("storeId",history.storeId);
//        }
//
//        @Override
//        protected void onFail(String code, String error) {
//            dataList.clear();
//            for(int i=0;i<10;i++){
//                dataList.add(new History(history.key+i,i+"",(10-i)+""));
//            }
//            adapter.notifyDataSetChanged();
//        }
//    }
}
