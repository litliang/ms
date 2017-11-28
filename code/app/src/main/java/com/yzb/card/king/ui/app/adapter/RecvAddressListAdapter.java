package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.presenter.SettingUpdatePresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuView;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能：收货地址；
 *
 * @author:gengqiyun
 * @date: 2016/6/21
 */
public class RecvAddressListAdapter extends ItemMenuAdapter implements BaseViewLayerInterface
{
    private Context context;
    private LayoutInflater inflater;
    private List<GoodsAddressBean> dataList;
    public static final int DEL_SUC_EVENT = 0x003;
    public static final int ITEM_CLICK_EVENT = 0x001;
    public static final int UPDATE_GOOD_ADDRESS_EVENT = 0x002;
    private SettingUpdatePresenter updatePresenter;
    private Handler dataHandler;

    public RecvAddressListAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        updatePresenter = new SettingUpdatePresenter(this);
    }

    public void appendDataList(List<GoodsAddressBean> jdmsBeans)
    {
        if (jdmsBeans == null || jdmsBeans.size() == 0) return;
        if (this.dataList == null)
        {
            this.dataList = new ArrayList<>();
        }
        this.dataList.addAll(jdmsBeans);
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

    public List<GoodsAddressBean> getDataList()
    {
        return dataList;
    }

    @Override
    public int getCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public GoodsAddressBean getItem(int position)
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
                Message message =dataHandler.obtainMessage(DEL_SUC_EVENT);

                message.arg1 = getCount();

                dataHandler.sendMessage(message);
            }
        }
    }

    @Override
    public ItemMenuView getItemView(final int position, ItemMenuView convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = (ItemMenuView) inflater.inflate(R.layout.list_item_recv_address, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final GoodsAddressBean data = getItem(position);
        viewHolder.tvname.setText(TextUtils.isEmpty(data.contacts) ? "" : data.contacts);
        viewHolder.tvphone.setText(TextUtils.isEmpty(data.phone) ? "" : data.phone);

        StringBuilder sb = new StringBuilder();
        sb.append(!isEmpty(data.getProvinceName()) ? data.getProvinceName() : "");
        sb.append(!isEmpty(data.getCityName()) ? data.getCityName() : "");
        sb.append(!isEmpty(data.districtName) ? data.districtName : "");
        sb.append(!isEmpty(data.getAddress()) ? data.getAddress() : "");

        viewHolder.tvaddress.setText(sb.toString());

        //默认收货地址；
        viewHolder.checkboxset.setSelected(data.isDefault);

        //设置默认收货地址
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.llSetDefault.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null && !finalViewHolder.checkboxset.isSelected())
                {
                    Message msg = dataHandler.obtainMessage(UPDATE_GOOD_ADDRESS_EVENT);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });

        //删除；
        final ItemMenuView finalConvertView = convertView;
        viewHolder.llUnbind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendDelData(position, finalConvertView);

            }
        });

        //item点击；
        viewHolder.llContent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null)
                {
                    Message msg = dataHandler.obtainMessage(ITEM_CLICK_EVENT);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });

        return viewHolder.root;
    }

    private int intentDelPosition = -1; //要删除的bean的下标；
    private ItemMenuView finalConvertView;////要删除的item；


    private void sendDelData(final int position, final ItemMenuView finalConvertView)
    {
        final GoodsAddressBean data = getItem(position);
        this.intentDelPosition = position;
        this.finalConvertView = finalConvertView;

        Map<String, Object> params = new HashMap<>();
        params.put("addressId", data.getAddressId());
        params.put("status", "0");
        params.put("serviceName", CardConstant.setting_customeraddressupdate);
        updatePresenter.loadData(params);
    }

    /**
     * 更新默认item;
     *
     * @param index
     */
    public void updateDefaultById(int index)
    {
            GoodsAddressBean gab;
            for (int i = 0; i < dataList.size(); i++)
            {
                gab = dataList.get(i);

                if(index==i){

                    gab.setStatus("2");
                }else {
                    gab.setStatus("1");
                }

            }
            notifyDataSetChanged();
    }

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }

    /**
     * 刪除item；
     *
     * @param id
     */
    public void deleteById(String id)
    {
        if (!TextUtils.isEmpty(id))
        {
            for (int i = 0; i < dataList.size(); i++)
            {
                if (id.equals(dataList.get(i).getAddressId()))
                {
                    dataList.remove(i);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ToastUtil.i(context, context.getString(R.string.app_delete_success));
        if (dataList != null && dataList.size() > 0)
        {
            finalConvertView.hideMenu();
            deleteItem(intentDelPosition, finalConvertView);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ToastUtil.i(context, context.getString(R.string.app_delete_error));
    }

    public class ViewHolder
    {
        public final ImageView tvDelete;
        public final LinearLayout llUnbind;
        public final TextView tvname;
        public final TextView tvphone;
        public final TextView tvaddress;
        public final ImageView checkboxset;
        public final TextView tvsetdefaultaddress;
        public final View llContent;
        public final ItemMenuView root;
        public final View llSetDefault;

        public ViewHolder(ItemMenuView root)
        {
            tvDelete = (ImageView) root.findViewById(R.id.tvDelete);
            llUnbind = (LinearLayout) root.findViewById(R.id.llUnbind);
            tvname = (TextView) root.findViewById(R.id.tv_name);
            tvphone = (TextView) root.findViewById(R.id.tv_phone);
            tvaddress = (TextView) root.findViewById(R.id.tv_address);
            checkboxset = (ImageView) root.findViewById(R.id.checkbox_set);
            tvsetdefaultaddress = (TextView) root.findViewById(R.id.tv_set_default_address);
            llSetDefault = root.findViewById(R.id.llSetDefault);
            llContent = root.findViewById(R.id.llContent);
            this.root = root;
        }
    }

}
