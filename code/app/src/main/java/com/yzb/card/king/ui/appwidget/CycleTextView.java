package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.bean.TextCycleItem;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/27 10:48
 * 描述：
 */
public class CycleTextView extends FrameLayout {
    private TextView tvTitle;
    private TextSwitcher textSwitcher;
    private View llMore;

    private OnArrowClickListener listener;
    private List<TextCycleItem> list = new ArrayList<>();
    private int currentPosition = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            currentPosition++;
            int position = currentPosition % list.size();
            toNext(position);
        }
    };


    public CycleTextView(Context context)
    {
        super(context);
        init();
    }

    public CycleTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();

    }

    public CycleTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init()
    {
        View view = View.inflate(getContext(), R.layout.view_text_cycle, this);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        textSwitcher = (TextSwitcher) view.findViewById(R.id.textSwitcher);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView()
            {
                TextView textView = new TextView(getContext());
                textView.setTextColor(getResources().getColor(R.color.red_d00011));
                textView.setMaxLines(1);
                textView.setTextSize(16);
                return textView;
            }
        });


        textSwitcher.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.translate_discount_recommend_textswitcher_anim_in));
        textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.translate_discount_recommend_textswitcher_anim_out));

        llMore = view.findViewById(R.id.llMore);
        llMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (list.size() > 0)
                {
                    TextCycleItem item = list.get(currentPosition % list.size());
                    if (listener != null)
                        listener.onClick(item);
                }
            }
        });
    }

    public void setTitle(String title)
    {
        tvTitle.setText(title);
    }

    public void setList(List<TextCycleItem> list)
    {
        this.list = list;
        toNext(currentPosition);
    }

    private void toNext(int position)
    {
        if (list.size() > 0)
        {
            if (list.get(position).getThemeName().length() > 16)
            {
                textSwitcher.setText(list.get(position).getThemeName().substring(0, 15) + "...");
            } else
            {
                textSwitcher.setText(list.get(position).getThemeName());
            }


            mHandler.sendEmptyMessageDelayed(0, 4000);
        }
    }

    public void setOnArrowClickListener(OnArrowClickListener listener)
    {
        this.listener = listener;
    }

    public interface OnArrowClickListener {
        void onClick(TextCycleItem item);
    }
}
