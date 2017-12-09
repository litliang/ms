package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.gift.bean.FavorPayee;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/6/19
 * 描  述：常用收款人适配
 */
public class PayeeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private LayoutInflater inflater;

    private List<FavorPayee> list = new ArrayList<FavorPayee>();

    private PayeeAdapterOnChecked checkedList;

    public PayeeAdapter(Context mContext, PayeeAdapterOnChecked checkedList) {

        inflater = LayoutInflater.from(mContext);

        this.checkedList = checkedList;
    }

    public boolean del = false;

    public void setDel(boolean del) {
        this.del = del;
    }

    public List<FavorPayee> getList() {
        return list;
    }

    public void addNewDataList(List<FavorPayee> list) {

//        this.list.clear();
        for (Object o : list) {
            addNewData((FavorPayee) o);
        }
        notifyDataSetChanged();
    }

    public void addNewData(FavorPayee list) {
        addNewData(list,true);
    }
    public void addNewData(FavorPayee list,boolean changed) {

//        this.list.clear();
        int hasindex =
                Collections.binarySearch(this.list, list, new Comparator<FavorPayee>() {
                    @Override
                    public int compare(FavorPayee lhs, FavorPayee rhs) {
                        return lhs.getTradeAccount().compareTo(rhs.getTradeAccount());
                    }
                });
        if (hasindex <= -1) {
            list.setChecked(true);
            this.list.add(list);

        }
        if(changed)
        notifyDataSetChanged();
    }


    public List<FavorPayee> getCheckList() {
        List<FavorPayee> temp = new ArrayList<FavorPayee>();

        for (FavorPayee payee : list) {

            if (payee.isChecked()) {
                temp.add(payee);
            }
        }

        return temp;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PayeeAdapter.MyViewHolder myViewHolder = new PayeeAdapter.MyViewHolder(inflater.inflate(R.layout.view_contact_payee, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder) {

            MyViewHolder holder1 = (MyViewHolder) holder;

            holder1.initData(position);
            holder1.rm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        private ImageView rm;

        private ImageView ivImage;

        private TextView tvName;

        private TextView tvAccount;

        private ToggleButton ivChecked;

        private FavorPayee payee;

        public MyViewHolder(View itemView) {
            super(itemView);
            rm = (ImageView) itemView.findViewById(R.id.rm);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

            tvName = (TextView) itemView.findViewById(R.id.tvName);

            tvAccount = (TextView) itemView.findViewById(R.id.tvAccount);

            ivChecked = (ToggleButton) itemView.findViewById(R.id.ivChecked);

        }

        public void initData(int position) {

            payee = list.get(position);

            tvName.setText(payee.getCreditName());

            tvAccount.setText(payee.getTradeAccount());

            ivChecked.setChecked(payee.isChecked());

            ivChecked.setOnCheckedChangeListener(this);

            if (!TextUtils.isEmpty(payee.getPhotoUrl())) {
                x.image().bind(ivImage, ServiceDispatcher
                        .getImageUrl(payee.getPhotoUrl()));

            }
            if (del) {
                ivChecked.setVisibility(View.GONE);
                rm.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            payee.setChecked(isChecked);

            checkedList.checkedChanged();
        }
    }

    public interface PayeeAdapterOnChecked {

        void checkedChanged();

    }

}

