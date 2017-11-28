package com.yzb.card.king.ui.credit.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.bean.AdjustCard;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/7 14:27
 * 描述：
 */
public class AdjustLimitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Activity context;
    private List<AdjustCard> list;
    private LayoutInflater inflater;

    public AdjustLimitAdapter(Activity context, List<AdjustCard> list)
    {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.item_adjust_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData(list.get(position));
        }
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView ivPic;
        private TextView tvSearch;
        private TextView tvManage;
        private AdjustCard data;
        private String msg = "1";
        private String phone = "2";

        public MyViewHolder(View itemView)
        {
            super(itemView);
            initView(itemView);
            initListener();
        }

        private void initListener()
        {
            tvSearch.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if (msg.equals(data.getMsgStatus()))
                    {
                        sendMessage(data.getMsgQueryAddress(), data.getMsgLimitQuery());
                    } else if (phone.equals(data.getMsgStatus()))
                    {
                        call(data.getPhoneLimitQuery());
                    } else
                    {
                        UiUtils.shortToast("缺少额度查询状态码");
                    }
                }

            });

            tvManage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (msg.equals(data.getAdjustStatus()))
                    {
                        sendMessage(data.getMsgAdjustAddress(), data.getMsgLimitAdjust());
                    } else if (phone.equals(data.getAdjustStatus()))
                    {
                        call(data.getPhoneLimitAdjust());
                    } else
                    {
                        UiUtils.shortToast("缺少额度管理状态码");
                    }
                }
            });
        }


        private void initView(View itemView)
        {
            ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
            tvSearch = (TextView) itemView.findViewById(R.id.tvSearch);
            tvManage = (TextView) itemView.findViewById(R.id.tvManage);
        }

        public void setData(AdjustCard data)
        {
            this.data = data;
            x.image().bind(ivPic, ServiceDispatcher.getImageUrl(data.getCardPhoto()));

        }

        private void sendMessage(String number, String message)
        {
            Uri uri = Uri.parse("smsto:" + number);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            sendIntent.putExtra("sms_body", message);
            UiUtils.startActivity(sendIntent);
        }

        private void call(String tel)
        {
            if (TextUtils.isEmpty(tel))
            {
                UiUtils.shortToast("电话号码不能为空");
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (context.checkSelfPermission(Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + tel));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivityForResult(callIntent, AppConstant.REQ_PHONE);
                }
            } else
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tel));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivityForResult(callIntent, AppConstant.REQ_PHONE);
            }
        }
    }

    public interface OnItemClickListener
    {
        void setOnItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener onItemClickListener;
}
