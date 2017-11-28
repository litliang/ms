package com.yzb.card.king.ui.discount.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.MsDetailActivity;
import com.yzb.card.king.ui.discount.bean.StoreBean;


/**
 * 美食详情-商户信息Fragment；
 */
public class MsDetail_ShxxFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Activity activity;
    private TextView tv_shop_info;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    public MsDetail_ShxxFragment() {
    }

    public static MsDetail_ShxxFragment newInstance(String param1, String param2) {
        MsDetail_ShxxFragment fragment = new MsDetail_ShxxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ms_shxx_fragment_new, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        if (view == null) return;
        //商户信息；
        tv_shop_info = (TextView) view.findViewById(R.id.tv_shop_info);
        initViewContent();
    }

    public void initViewContent() {
        String htmlContent = getData();
        if (tv_shop_info != null) {
            tv_shop_info.setText(Html.fromHtml(TextUtils.isEmpty(htmlContent) ? "<p></p>" : htmlContent));
        }
    }

    private String getData() {
        if (activity != null) {
            if (activity instanceof MsDetailActivity) {
                StoreBean storeBean = ((MsDetailActivity) activity).storeBean;
                if (storeBean != null) {
                    return storeBean.shopIntro;
                }
            }
        }
        return null;
    }

}
