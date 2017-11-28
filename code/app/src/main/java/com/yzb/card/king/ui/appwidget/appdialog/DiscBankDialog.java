package com.yzb.card.king.ui.appwidget.appdialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.BankInfo;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/11 14:51
 */
public class DiscBankDialog extends PopupWindow
{
    private Context context;
    List<BankInfo> istList;

    public DiscBankDialog(Context context, List<BankInfo> istList)
    {
        super(context);
        this.context = context;
        this.istList = istList;
        init();
    }

    private void init()
    {
        View view = UiUtils.inflate(R.layout.filter_dialog);
        initView(view);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        setOutsideTouchable(true);


        setHeight(CommonUtil.getScreenHeight(context) * 2 / 5);
    }

    private void initView(View view)
    {
        ListView listView = (ListView) view.findViewById(R.id.list);
        ListAdapter adapter = new AbsBaseListAdapter<BankInfo>(istList)
        {

            @Override
            protected Holder getHolder(int position)
            {
                return new DialogHolder();
            }
        };
        listView.setAdapter(adapter);
    }

    class DialogHolder extends Holder<BankInfo>
    {
        private ImageView ivIcon;
        private TextView tvName;

        @Override
        public View initView()
        {
            View view = UiUtils.inflate(R.layout.dialog_discount_bank);
            ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
            tvName = (TextView) view.findViewById(R.id.tvName);
            return view;
        }

        @Override
        public void refreshView(BankInfo data)
        {
            x.image().bind(ivIcon, ServiceDispatcher.getImageUrl(data.getBankLogoCode()));
            tvName.setText(data.getBankName());
        }
    }
}
