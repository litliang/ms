package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.presenter.SettingUpdatePresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuView;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能：发票抬头；
 *
 * @author:gengqiyun
 * @date: 2016/6/21
 */
public class DebitRiseListAdapter extends ItemMenuAdapter implements BaseViewLayerInterface
{
    public static final int DEL_SUC_EVENT = 0x003;

    private Context context;

    private LayoutInflater inflater;

    private List<DebitRiseBean> dataList;

    public static final int ITEM_CLICK_EVENT = 0x001;

    public static final int UPDATE_DEBIT_RISE_EVENT = 0x002;

    private SettingUpdatePresenter settingUpdatePresenter;

    private Handler dataHandler;

    public DebitRiseListAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        settingUpdatePresenter = new SettingUpdatePresenter(this);
    }

    public void appendDataList(List<DebitRiseBean> dataList)
    {
        if (dataList == null || dataList.size() == 0)
            return;

        if (this.dataList == null)
        {
            this.dataList = new ArrayList<>();
        }
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clear()
    {
        if (dataList != null)
        {
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    public List<DebitRiseBean> getDataList()
    {
        return dataList;
    }

    @Override
    public int getCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public DebitRiseBean getItem(int position)
    {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public void onDeleItem(int pos)
    {
        if (dataList != null)
        {
            dataList.remove(pos);
            notifyDataSetChanged();
            if (dataHandler != null && getCount() == 0)
            {
                Message msg = dataHandler.obtainMessage(DEL_SUC_EVENT);
                msg.arg1 = getCount();
                dataHandler.sendMessage(msg);
            }
        }
    }

    @Override
    public ItemMenuView getItemView(final int position, ItemMenuView convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = (ItemMenuView) inflater.inflate(R.layout.list_item_debit_rise, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DebitRiseBean data = getItem(position);

        long type = data.getType();

        if( type == 2){
            viewHolder.line.setVisibility(View.INVISIBLE);
            viewHolder.tvname.setText(data.getContent());
            viewHolder.tvPhone.setText("类型：个人");
        }else{
            viewHolder.line.setVisibility(View.VISIBLE);
            viewHolder.tvname.setText(TextUtils.isEmpty(data.content) ? "" : data.content);
            viewHolder.tvPhone.setText(data.getCompanyAddr());
        }


        //默认发票抬头；
        viewHolder.checkboxset.setSelected(data.isDefault());

        // 更新默认发票抬头；
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ll_set_default.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null && !finalViewHolder.checkboxset.isSelected())
                {
                    Message msg = dataHandler.obtainMessage(UPDATE_DEBIT_RISE_EVENT);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });
        // 删除；
        final ItemMenuView finalConvertView = convertView;
        viewHolder.llUnbind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendDelData(position, finalConvertView);
            }
        });
        viewHolder.ll_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Message msg = dataHandler.obtainMessage(ITEM_CLICK_EVENT);
                msg.arg1 = position;
                dataHandler.sendMessage(msg);
            }
        });
        return viewHolder.root;
    }

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }


    private int intentDelPosition = -1; //要删除的bean的下标；
    private ItemMenuView finalConvertView;////要删除的item；

    /**
     * 删除发票抬头
     *
     * @param position 点击的下标；
     */
    private void sendDelData(final int position, final ItemMenuView finalConvertView)
    {
        final DebitRiseBean data = getItem(position);
        this.intentDelPosition = position;
        this.finalConvertView = finalConvertView;

        Map<String, Object> params = new HashMap<>();
        params.put("invoiceId", data.getInvoiceId() + ""); //发票抬头id；
        params.put("status", "0");//状态（0停用1启用）
        params.put("serviceName", CardConstant.setting_invoiceupdate);
        settingUpdatePresenter.loadData(params);
    }

    public void updateDefaultById(int index )
    {

            DebitRiseBean drb;
            for (int i = 0; i < dataList.size(); i++)
            {
                drb = dataList.get(i);
                if(index==i){
                    drb.setStatus("2");
                }else {
                    drb.setStatus("1");
                }
            }
            notifyDataSetChanged();

    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (dataList != null && dataList.size() > 0 && intentDelPosition >= 0)
        {
            finalConvertView.hideMenu();
            deleteItem(intentDelPosition, finalConvertView);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ToastUtil.i(context,o+"");
    }

    public class ViewHolder
    {
        public final TextView tvname;

        public final ImageView checkboxset;
        public final TextView tvsetdefaultaddress;
        public final TextView tvPhone;
        public final View line;

        public final ItemMenuView root;
        public final View ll_content;
        public final View llUnbind;
        private final View ll_set_default;

        public ViewHolder(ItemMenuView root)
        {
            tvname = (TextView) root.findViewById(R.id.tv_name);
            tvPhone = (TextView) root.findViewById(R.id.tvPhone);
            line =  root.findViewById(R.id.line);
            checkboxset = (ImageView) root.findViewById(R.id.checkbox_set);
            tvsetdefaultaddress = (TextView) root.findViewById(R.id.tv_set_default_address);
            ll_set_default = root.findViewById(R.id.ll_set_default);
            ll_content = root.findViewById(R.id.ll_content);
            llUnbind = root.findViewById(R.id.llUnbind);
            this.root = root;
        }
    }
}
