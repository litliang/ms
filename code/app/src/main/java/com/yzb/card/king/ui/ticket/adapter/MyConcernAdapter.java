package com.yzb.card.king.ui.ticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.PlaneStarEndBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.ticket.holder.BaseNullHolder;
import com.yzb.card.king.util.Utils;

import org.xutils.x;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/28
 * 描  述：
 */
public class MyConcernAdapter extends RecyclerView.Adapter {

    private List<PlaneStarEndBean> planeStarEndBeen;
    private LayoutInflater inflater;
    private Context context;
    private RecyclerView recyclerView;

    public MyConcernAdapter(Context context, List<PlaneStarEndBean> planeStarEndBeen)
    {
        this.planeStarEndBeen = planeStarEndBeen;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (planeStarEndBeen.size() == 0 || planeStarEndBeen == null)
        {
            return AppConstant.TYPE_NULL;
        } else
        {
            return AppConstant.TYPE_NORMLL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == AppConstant.TYPE_NULL)
        {
            return new BaseNullHolder(inflater.inflate(R.layout.base_null_data_item, parent, false));
        } else
        {
            return new MyViewHodel(inflater.inflate(R.layout.plane_myconcern_item, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHodel)
        {
            final MyViewHodel myViewHolder = (MyViewHodel) holder;
            if (onitemClickListener != null)
            {
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int position = myViewHolder.getLayoutPosition();
                        onitemClickListener.onItemClickListener(view, position);
                    }
                });
            }

            PlaneStarEndBean flightDynamicsBean = planeStarEndBeen.get(position);

            myViewHolder.hbName.setText(flightDynamicsBean.getAirline());

            myViewHolder.starTime.setText(flightDynamicsBean.getDepTime());
            myViewHolder.endTime.setText(flightDynamicsBean.getArrTime());


            myViewHolder.starJc.setText(flightDynamicsBean.getDepCity());

            myViewHolder.endJc.setText(flightDynamicsBean.getArrCity());

            x.image().bind(myViewHolder.hbLog, ServiceDispatcher.getImageUrl(flightDynamicsBean.getShopLogo()));
            myViewHolder.tv_flightNumber.setText("- "+flightDynamicsBean.getFlightNum()+" -");
          //  LogUtil.e("AAAAAA",flightDynamicsBean.getFlightNum()+"-------"+flightDynamicsBean.getAexpected()+"---"+flightDynamicsBean.getDexpected());
          myViewHolder.seTime.setText(Utils.getHourSpace(flightDynamicsBean.getDepTime(),flightDynamicsBean.getArrTime(),3));


        }
    }

    @Override
    public int getItemCount()
    {
        return planeStarEndBeen == null || planeStarEndBeen.size() == 0 ? 1 : planeStarEndBeen.size();
    }


    public class MyViewHodel extends RecyclerView.ViewHolder {
        public final ImageView hbLog;
        public final TextView hbName;
        public final TextView starTime;
        public final TextView endTime;
        public final TextView starJc;
        public final TextView endJc;
        public TextView seTime;
        public  TextView tv_flightNumber;

        public MyViewHodel(View itemView)
        {
            super(itemView);
            hbLog = (ImageView) itemView.findViewById(R.id.hbLog);
            hbName = (TextView) itemView.findViewById(R.id.hb_name);
            starTime = (TextView) itemView.findViewById(R.id.star_time);
            endTime = (TextView) itemView.findViewById(R.id.end_time);
            starJc = (TextView) itemView.findViewById(R.id.star_jc);
            endJc = (TextView) itemView.findViewById(R.id.end_jc);
            seTime = (TextView) itemView.findViewById(R.id.seTime);
            tv_flightNumber = (TextView) itemView.findViewById(R.id.tv_flightNumber);

        }
    }

    //添加点击事件
    public interface OnitemClickListener {
        void onItemClickListener(View view, int position);
    }

    private OnitemClickListener onitemClickListener;

    public void setOnitemClickListener(OnitemClickListener onitemClickListener)
    {
        this.onitemClickListener = onitemClickListener;
    }
}
