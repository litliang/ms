package com.yzb.card.king.ui.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.util.CommonUtil;

import java.util.List;

/**
 * @author gengqiyun
 * @date 2016.8.29
 * 证件列表对话框；
 */
public class CertificateDialogFragment extends BaseDialogFragment implements View.OnClickListener
{
    private static CertificateDialogFragment dialogFragment;
    private ListView listview;
    private List<String> dataList;

    /**
     * 注入数据；
     *
     * @param dataList
     */
    public CertificateDialogFragment setDataList(List<String> dataList)
    {
        this.dataList = dataList;
        return this;
    }

    public static CertificateDialogFragment getInstance()
    {

        synchronized (CertificateDialogFragment.class)
        {
            if (dialogFragment == null)
            {
                dialogFragment = new CertificateDialogFragment();
            }
        }
        return dialogFragment;
    }

    @Override
    protected int getGravity()
    {
        return Gravity.CENTER;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_certificate_list_dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setWindowAnimations(0);
        window.getAttributes().width = CommonUtil.getScreenWidth(getActivity()) - CommonUtil.dip2px(getContext(), 30);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    protected void initView(View view)
    {
        if (view != null)
        {
            view.findViewById(R.id.iv_close).setOnClickListener(this);
            listview = (ListView) view.findViewById(R.id.listview);

            listview.setAdapter(new CertificateAdater(getActivity()));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if (callBack != null)
                    {
                        callBack.callBack(dataList.get(position), position + 1);
                    }
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    private class CertificateAdater extends BaseAdapter
    {
        private final LayoutInflater inflater;

        public CertificateAdater(Context context)
        {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount()
        {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public String getItem(int position)
        {
            return dataList == null ? null : dataList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.list_item_certificate, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvcontent.setText(getItem(position));

            return viewHolder.root;
        }

        public class ViewHolder
        {
            public final TextView tvcontent;
            public final View root;

            public ViewHolder(View root)
            {
                tvcontent = (TextView) root.findViewById(R.id.tv_content);
                this.root = root;
            }
        }
    }

    private ICerificateCallBack callBack;

    public interface ICerificateCallBack
    {
        void callBack(String name, int certificateType);
    }

    public CertificateDialogFragment setCallBack(ICerificateCallBack callBack)
    {
        this.callBack = callBack;
        return this;
    }
}
