package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.gift.bean.FavorPayee;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/6/19
 * 描  述：添加联系人的适配器
 */
public class AddContactAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;

    private List<FavorPayee> list = new ArrayList<FavorPayee>();

    private Context mContext;

    private CurrentClickListener listener;




    public AddContactAdapter(Context mContext, CurrentClickListener listener)
    {
        this.mContext = mContext;

        this.listener = listener;

        inflater = LayoutInflater.from(mContext);
    }

    public List<FavorPayee> getList()
    {
        return list;
    }

    public  void addOneNewData(FavorPayee newFavorPayee){

        boolean flag = true;
        //检查是否有重复数据
        for(FavorPayee temp:list){

            if(temp.getTradeAccount().equals(newFavorPayee.getTradeAccount())){

                flag = false;

                ToastUtil.i(mContext,"已添加过此账户");

                break;
            }
        }

        if(flag){

            list.add(newFavorPayee);

            notifyDataSetChanged();
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        MyViewHolder myViewHolder = new MyViewHolder(inflater.inflate(R.layout.view_add_contact, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if( holder instanceof MyViewHolder ){

            MyViewHolder holderTemp = (MyViewHolder) holder;

            holderTemp.initData(position);

        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName;

        private TextView tvAccount;

        private TextView tvDelete;

        private ImageView ivImage;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAccount = (TextView) itemView.findViewById(R.id.tvAccount);
            tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }

        public void initData(int position){

            FavorPayee  payee = list.get(position);

            tvName.setText(payee.getCreditName());

            tvAccount.setText(payee.getTradeAccount());

            tvDelete.setTag(position);

            tvDelete.setOnClickListener(this);

            if (!TextUtils.isEmpty(payee.getPhotoUrl()))
            {

                x.image().bind(ivImage, ServiceDispatcher
                        .getImageUrl(payee.getPhotoUrl()));

            }

        }


        @Override
        public void onClick(View v)
        {
            switch (v.getId()){
                case R.id.tvDelete://删除

                    int index = (int) v.getTag();

                    list.remove(index);

                    notifyDataSetChanged();

                    listener.delAction();
                    break;
                default:
                    break;
            }
        }
    }

    public interface  CurrentClickListener{

        void delAction();

    }

}
