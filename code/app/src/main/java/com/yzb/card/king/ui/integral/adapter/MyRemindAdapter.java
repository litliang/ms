package com.yzb.card.king.ui.integral.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuView;
import com.yzb.card.king.ui.integral.presenter.MyRemindPresenter;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public class MyRemindAdapter extends ItemMenuAdapter {

    public List<RemindBean> remindBeans;

    private LayoutInflater inflater;

    private Context context;

    private MyRemindPresenter presenter;

    public MyRemindAdapter(Context context, List<RemindBean> remindBeans, MyRemindPresenter presenter)
    {
        this.remindBeans = remindBeans;
        this.presenter = presenter;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    /**
     * 设置信用卡卡号
     *
     * @param content
     * @param length
     * @param tv1
     * @param tv2
     * @param tv3
     * @param tv4
     */
    private void getSubString(String content, int length, TextView tv1, TextView tv2, TextView tv3, TextView tv4)
    {
        int[] maps = new int[]{R.mipmap.num_zero0, R.mipmap.num_one1, R.mipmap.num_two2, R.mipmap.num_three3,
                R.mipmap.num_four4, R.mipmap.num_five5, R.mipmap.num_six6, R.mipmap.num_seven7, R.mipmap.num_eight8,
                R.mipmap.num_nine9};
        if (!content.substring(length - 4, length - 3).equals(" "))
        {
            tv1.setBackgroundResource(maps[Integer.valueOf(content.substring(length - 4, length - 3))]);
        }
        if (!content.substring(length - 3, length - 2).equals(" "))
        {
            tv2.setBackgroundResource(maps[Integer.valueOf(content.substring(length - 3, length - 2))]);
        }
        if (!content.substring(length - 2, length - 1).equals(" "))
        {
            tv3.setBackgroundResource(maps[Integer.valueOf(content.substring(length - 2, length - 1))]);
        }
        if (!content.substring(length - 1, length).equals(" "))
        {
            tv4.setBackgroundResource(maps[Integer.valueOf(content.substring(length - 1, length))]);
        }
    }

    @Override
    public void onDeleItem(int pos)
    {

    }

    @Override
    public ItemMenuView getItemView(final int position, ItemMenuView convertView, ViewGroup parent)
    {
        MyViewHolder myViewHolder;
        if (convertView == null)
        {
            convertView = (ItemMenuView) LayoutInflater.from(context).inflate
                    (R.layout.view_adapter_item_remind, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.imgRemind=(ImageView)convertView.findViewById(R.id.remind_list_image);
            myViewHolder.txtContent=(TextView)convertView.findViewById(R.id.remind_context);
            myViewHolder.txtTitle=(TextView)convertView.findViewById(R.id.remind_title);
            myViewHolder.btnNext=(Button)convertView.findViewById(R.id.remind_button2);
            myViewHolder.imgxxx=(ImageView)convertView.findViewById(R.id.iv_x);
            myViewHolder.tvCardNumbernbsp=(TextView)convertView.findViewById(R.id.tvCardNumbernbsp);
            myViewHolder.tvCardNumber1=(TextView)convertView.findViewById(R.id.tvCardNumber1);
            myViewHolder.tvCardNumber2=(TextView)convertView.findViewById(R.id.tvCardNumber2);
            myViewHolder.tvCardNumber3=(TextView)convertView.findViewById(R.id.tvCardNumber3);
            myViewHolder.tvCardNumber4=(TextView)convertView.findViewById(R.id.tvCardNumber4);
            myViewHolder.delete= (ImageView) convertView.findViewById(R.id.message_detail_delete_img);
            convertView.setTag(myViewHolder);
        } else
        {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        final RemindBean rb = remindBeans.get(position);
        myViewHolder.txtTitle.setText(rb.getTitle());
        myViewHolder.txtContent.setText(rb.getContent());
        String a = ServiceDispatcher.getImageUrl(rb.getPhoto());
        x.image().bind(myViewHolder.imgRemind, a);

        int fullNolength = rb.getCreditNo().length();

        if (rb.getRemindStatus().equals("1"))
        {
            if (rb.getRemindType().equals("1"))
            {
                //设置我的提醒里面的信用卡显示卡号
                myViewHolder.imgxxx.setBackgroundResource(R.mipmap.xxxx);
                getSubString(rb.getCreditNo(), fullNolength, myViewHolder.tvCardNumber1,
                        myViewHolder.tvCardNumber2, myViewHolder.tvCardNumber3, myViewHolder.tvCardNumber4);
                myViewHolder.btnNext.setBackgroundResource(R.drawable.bg_round_corner_grayre);
                myViewHolder.btnNext.setTextColor(context.getResources().getColor(R.color.color_b7b7b7));
                myViewHolder.btnNext.setText("已还款");
                myViewHolder.btnNext.setClickable(false);

            } else
            {
                //下次提醒我
                myViewHolder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        ToastUtil.i(context, "下次提醒我");
                    }
                });
            }
        } else
        {
            if (rb.getRemindType().equals("1"))
            {
                myViewHolder.imgxxx.setBackgroundResource(R.mipmap.xxxx);
                getSubString(rb.getCreditNo(), fullNolength, myViewHolder.tvCardNumber1,
                        myViewHolder.tvCardNumber2, myViewHolder.tvCardNumber3, myViewHolder.tvCardNumber4);
                myViewHolder.btnNext.setText("去还款");
                myViewHolder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        presenter.getCardInfo(rb.getDetailId());

                    }
                });
            }

        }
        final ItemMenuView itemMenuView = convertView;
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                itemMenuView.hideMenu();
                deleteItem(position, itemMenuView);
                presenter.deleteRemind(rb);
            }
        });
        return convertView;
    }

    @Override
    public int getCount()
    {
        return remindBeans.size();
    }

    class MyViewHolder {
        TextView txtTitle;
        TextView txtContent;
        ImageView imgRemind;
        Button btnNext;
        ImageView imgxxx;
        TextView tvCardNumbernbsp;
        TextView tvCardNumber1;
        TextView tvCardNumber2;
        TextView tvCardNumber3;
        TextView tvCardNumber4;
        ImageView delete;


    }
}
