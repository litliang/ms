package com.yzb.card.king.ui.transport.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.transport.activity.PriceEvaluateActivity;
import com.yzb.card.king.ui.transport.bean.SpecialCar;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.StringUtils;

/**
 * 接送机基本信息
 */
public class ShuttleOrderBaseInfoFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;

    private SpecialCar busTypeBean = null;

    private TextView carTypeName;
    private TextView personNumber;
    private TextView luggageNumber;

    private TextView startPlace;
    private TextView endPlace;
    private TextView startDate;

    private TextView feeDetail;
    private LinearLayout llDetail;

    public static ShuttleOrderBaseInfoFragment newInstance(String param1, String param2) {
        ShuttleOrderBaseInfoFragment fragment = new ShuttleOrderBaseInfoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shuttle_order_base_info, container, false);

        initShuttleView();

        initShuttleData();

        return view;
    }

    public void initShuttleView() {
        carTypeName = (TextView) view.findViewById(R.id.carTypeName);
        personNumber = (TextView) view.findViewById(R.id.personNumber);
        luggageNumber = (TextView) view.findViewById(R.id.luggageNumber);

        startPlace = (TextView) view.findViewById(R.id.startPlace);
        endPlace = (TextView) view.findViewById(R.id.endPlace);
        startDate = (TextView) view.findViewById(R.id.startDate);

        feeDetail = (TextView) view.findViewById(R.id.feeDetail);

        llDetail = (LinearLayout) view.findViewById(R.id.llDetail);
    }

    public void initShuttleData() {
        busTypeBean = JSON.parseObject(mParam2, SpecialCar.class);

        carTypeName.setText(busTypeBean.carTypeName);
        personNumber.setText(getActivity().getResources().getString(R.string.shuttle_x) + busTypeBean.personNumber);
        luggageNumber.setText(getActivity().getResources().getString(R.string.shuttle_x) + busTypeBean.luggageNumber);

        startPlace.setText(busTypeBean.startPlace);
        endPlace.setText(busTypeBean.endPlace);
        String startDateStr = DateUtil.date2String(busTypeBean.startDate, DateUtil.DATE_FORMAT_DATE_TIME2);
        if(!StringUtils.isEmpty(startDateStr)) {
            startDate.setText(startDateStr.substring(0, 12) + "周"+DateUtil.getWeek(startDateStr) + " " + startDateStr.substring(12, 17));
        } else {
            startDate.setText("");
        }

        String feeDetailStr = getActivity().getResources().getString(R.string.train_rmb) + busTypeBean.startingPrice + "起＋" + getActivity().getResources().getString(R.string.train_rmb)
                + busTypeBean.perMinutePrice + "/分钟＋" + getActivity().getResources().getString(R.string.train_rmb) + busTypeBean.perKilometerPrice + "/公里";

        feeDetail.setText(feeDetailStr);

        llDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llDetail:
                startActivity(new Intent(getActivity(), PriceEvaluateActivity.class));
                break;
        }
    }
}
