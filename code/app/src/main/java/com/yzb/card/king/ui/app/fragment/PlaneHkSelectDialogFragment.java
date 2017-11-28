package com.yzb.card.king.ui.app.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dev.mylibrary.OnWheelChangedListener;
import com.example.dev.mylibrary.WheelView;
import com.example.dev.mylibrary.adapter.ArrayWheelAdapter;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：在线选座的航空公司选择器
 * 作  者：Li JianQiang
 * 日  期：2016/10/8
 * 描  述：
 */
public class PlaneHkSelectDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private static PlaneHkSelectDialogFragment dialogFragment;

    private WheelView wheelPlane;
    private String[] planes;
    private static String plane;
    private static int index;

    public static PlaneHkSelectDialogFragment getInstance(String arg1)
    {


        synchronized (PlaneHkSelectDialogFragment.class)
        {
            if (dialogFragment == null)
            {
                dialogFragment = new PlaneHkSelectDialogFragment();
            }
        }
        plane = arg1;
        return dialogFragment;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_plane_hk_select_dialog;
    }

    protected void initView(View view)
    {
        if (view != null)
        {
            view.findViewById(R.id.tv_cancel).setOnClickListener(this);
            view.findViewById(R.id.tv_ok).setOnClickListener(this);
            initWheelView(view);
        }
    }

    private void initWheelView(View view)
    {
        wheelPlane = (WheelView) view.findViewById(R.id.cr);
        wheelPlane.setVisibleItems(5);

        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue)
            {

            }
        };


        planes = new String[]{"东方航空", "海南航空", "东方航空", "北京航空", "中国航空"};
        wheelPlane.setViewAdapter(new PlaneArrayAdapter(getActivity(), planes, 0));
        for (int i =0;i<planes.length;i++){
            if(plane.equals(planes[i])){
                index=i;
            }
        }
        wheelPlane.setCurrentItem(index);
        wheelPlane.addChangingListener(listener);


    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ok:
                if (callBack != null)
                {
                    Map<String, String> data = new HashMap<>();
                    data.put("cr", planes[wheelPlane.getCurrentItem()]);
                    callBack.dialogCallBack(data);
                }
                dismiss();
                break;
        }
    }

    //    /**
//     * 航空选择适配器；
//     */
    class PlaneArrayAdapter extends ArrayWheelAdapter<String> {
        int currentItem;
        //        // Index of item to be highlighted
        int currentValue;

        //
//        /**
//         * Constructor
//         */
        public PlaneArrayAdapter(Context context, String[] items, int current)
        {
            super(context, items);
            this.currentValue = current;
            setTextSize(16);
        }

        @Override
        protected void configureTextView(TextView view)
        {
            super.configureTextView(view);

            //选中部分的颜色；
            if (currentItem == currentValue)
            {
                view.setTextColor(Color.parseColor("#4E4F51"));
            } else
            { //非选中部分的颜色；
                view.setTextColor(Color.parseColor("#3D4245"));
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent)
        {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }

    private IDialogCallBack callBack;

    public PlaneHkSelectDialogFragment setCallBack(IDialogCallBack callBack)
    {
        this.callBack = callBack;
        return this;
    }
}
