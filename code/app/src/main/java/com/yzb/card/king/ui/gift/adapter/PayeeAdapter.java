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
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/6/19
 * 描  述：常用收款人适配
 */
public class PayeeAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;

    private List<FavorPayee> list = new ArrayList<FavorPayee>();

    private  PayeeAdapterOnChecked checkedList;

    public PayeeAdapter(Context mContext, PayeeAdapterOnChecked checkedList)
    {

        inflater = LayoutInflater.from(mContext);

        this.checkedList = checkedList;
    }

    public void addNewDataList( List<FavorPayee> list){

        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<FavorPayee> getCheckList()
    {
        List<FavorPayee> temp = new ArrayList<FavorPayee>();

        for(FavorPayee payee: list){

            if(payee.isChecked()){
                temp.add(payee);
            }
        }

        return temp;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        PayeeAdapter.MyViewHolder myViewHolder = new PayeeAdapter.MyViewHolder(inflater.inflate(R.layout.view_contact_payee, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if( holder instanceof  MyViewHolder){

            MyViewHolder holder1 = (MyViewHolder) holder;

            holder1.initData(position);
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements  CompoundButton.OnCheckedChangeListener{

        private ImageView ivImage;

        private TextView tvName;

        private  TextView tvAccount;

        private ToggleButton ivChecked;

        private    FavorPayee payee;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

            tvName = (TextView) itemView.findViewById(R.id.tvName);

            tvAccount = (TextView) itemView.findViewById(R.id.tvAccount);

            ivChecked = (ToggleButton) itemView.findViewById(R.id.ivChecked);

        }

        public void initData(int position)
        {

             payee = list.get(position);

            tvName.setText(payee.getCreditName());

            tvAccount.setText(payee.getTradeAccount());

            ivChecked.setChecked(payee.isChecked());

            ivChecked.setOnCheckedChangeListener(this);

            if (!TextUtils.isEmpty(payee.getPhotoUrl()))
            {
                x.image().bind(ivImage, ServiceDispatcher
                        .getImageUrl(payee.getPhotoUrl()));

            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {

            payee.setChecked(isChecked);

            checkedList.checkedChanged();
        }
    }

   public  interface  PayeeAdapterOnChecked{

        void checkedChanged();

    }

}

