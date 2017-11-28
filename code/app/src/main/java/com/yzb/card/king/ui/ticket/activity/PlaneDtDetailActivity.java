package com.yzb.card.king.ui.ticket.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FlightDynamicsBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.ticket.model.IPlaneDtDetail;
import com.yzb.card.king.ui.ticket.presenter.PlaneDtDetailPresenter;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.SharedPreferencesUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 航班动态列表信息
 */
public class PlaneDtDetailActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    private Bundle bundle = null;

    private TextView bt, starCityDetail, endCityDetail, sjgoTime, sjdTime, jhgoTime, jhdTime,
            goJc, dJc, tq_tbStar, qfhzl_tx, djk_tx, zjgt_tx, tq_tbend, states, ddhzl_tx, xlzp_tx;

    private LinearLayout ivBack, jtLl;

    private ImageView sharePlane;

    private ImageView collectPlane;

    private final static String STATUS_COLLECT = "1";   //收藏

    private final static String STATUS_NOTCOLLECT = "0";  //未收藏

    private String flag = "-1";

    private FlightDynamicsBean flightDynamicsBean = null;

    private PlaneDtDetailPresenter presenter;

    private List<FlightDynamicsBean> list = new ArrayList<>();

    private String hbhName;

    private String hbTime;

    public static final int SENDMESSAGE = 1;

    private String info="";

    private TextView textView15,tvYjDepTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_dt_detail);
        initView();
        getIntentInfo();
        getDate();
    }

    private void initView()
    {
        bt = (TextView) findViewById(R.id.bt);
        starCityDetail = (TextView) findViewById(R.id.star_city_detail);
        endCityDetail = (TextView) findViewById(R.id.end_city_detail);
        sjgoTime = (TextView) findViewById(R.id.sjgo_time);
        sjdTime = (TextView) findViewById(R.id.sjd_time);
        jhgoTime = (TextView) findViewById(R.id.jhgo_time);
        jhdTime = (TextView) findViewById(R.id.jhd_time);
        goJc = (TextView) findViewById(R.id.go_jc);
        dJc = (TextView) findViewById(R.id.end_jc);
        tq_tbStar = (TextView) findViewById(R.id.tq_tb);
        tq_tbend = (TextView) findViewById(R.id.tq_tb1);
        states = (TextView) findViewById(R.id.states);
        qfhzl_tx = (TextView) findViewById(R.id.qfhzl_tx);
        zjgt_tx = (TextView) findViewById(R.id.zjgt_tx);
        djk_tx = (TextView) findViewById(R.id.djk_tx);
        ddhzl_tx = (TextView) findViewById(R.id.ddhzl_tx);
        xlzp_tx = (TextView) findViewById(R.id.xlzp_tx);
        textView15 = (TextView) findViewById(R.id.textView15);

        ivBack = (LinearLayout) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        sharePlane = (ImageView) findViewById(R.id.share_plane);
        sharePlane.setOnClickListener(this);
        collectPlane = (ImageView) findViewById(R.id.collect_plane);
        collectPlane.setOnClickListener(this);
        tvYjDepTime = (TextView) findViewById(R.id.tvYjDepTime);

        presenter = new PlaneDtDetailPresenter(this);

        info = (String) SharedPreferencesUtils.getParam(this, "list", "[]", "collect");

    }

    /**
     * 获取历史纪录中是否有该航班
     */
    private void initHistory()
    {
        list = JSON.parseArray(info, FlightDynamicsBean.class);

        for (int i = 0; i < list.size(); i++)
        {
            if (flightDynamicsBean.getFlight_number().equals(list.get(i).getFlight_number()) && flightDynamicsBean.getTimeseres().equals(list.get(i).getTimeseres()))
            {
                flag = STATUS_COLLECT;

                updateScPanelState(flag);
            }
        }
    }

    private void getIntentInfo()
    {
        bundle = getIntent().getExtras();
        if (bundle != null)
        {
            hbhName = bundle.getString("hbInfo");
            hbTime = bundle.getString("hbTime");
        }

    }

    private void getDate()
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(getString(R.string.list_footer_loading),
                PlaneDtDetailActivity.this, true);
        Map<String, Object> param = new HashMap<>();
        param.put("name", hbhName);
        param.put("date", hbTime);
        presenter.getInfo(param);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back:  //返回
                finish();
                break;
            case R.id.share_plane:  //分享
                if (flightDynamicsBean == null)
                {
                    return;
                }
                ShareDialogFragment sdf2 = ShareDialogFragment.getInstance("", "");
                sdf2.setUrl(ServiceDispatcher.URL_DYNAMIC_FLIGHT_DETAILS+flightDynamicsBean.getFlightId());
                sdf2.show(getSupportFragmentManager(), "ShareDialogFragment");
                break;
            case R.id.collect_plane:  //收藏
                if (flightDynamicsBean == null)
                {
                    return;
                }
                if (STATUS_COLLECT.equals(flag))
                {
                    toastCustom(getString(R.string.qxcoll));
                    flag = "0";
                    updateScPanelState(flag);
                    removeHistory(flightDynamicsBean);
                } else
                {
                    toastCustom(getString(R.string.collsucc));
                    flag = "1";
                    updateScPanelState(flag);
                    addLocal();
                }
                break;
        }
    }

    /**
     * 添加历史记录
     */
    private void addLocal()
    {
        FlightDynamicsBean flightDynamics = flightDynamicsBean;

        list.add(0, flightDynamics);
        saveHistory();
        ArrayList<FlightDynamicsBean> fdb = (ArrayList<FlightDynamicsBean>) list;
        Intent intent = new Intent("com.planeMyConcernFragmet");
        intent.putExtra("notice", 2);
        Bundle b = new Bundle();
        b.putSerializable("list", fdb);
        intent.putExtra("bundle", b);
        sendBroadcast(intent);
    }

    /**
     * 将历史记录保存到本地
     */
    protected void saveHistory()
    {
        SharedPreferencesUtils.setParam(PlaneDtDetailActivity.this, "list",
                JSON.toJSONString(list), "collect");
    }

    /**
     * 取消关注
     *
     * @param f
     */
    public void removeHistory(FlightDynamicsBean f)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (f.getFlight_number().equals(list.get(i).getFlight_number()))
            {
                list.remove(i);
                Intent intent = new Intent("com.planeMyConcernFragmet");
                intent.putExtra("notice", 1);
                intent.putExtra("listId", i);
                sendBroadcast(intent);
            }
        }
        SharedPreferencesUtils.setParam(PlaneDtDetailActivity.this, "list",
                JSON.toJSONString(list), "collect");


    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    /**
     * 设置详情信息
     */
    private void planeDetailInfo()
    {
        if (flightDynamicsBean != null)
        {
            bt.setText(flightDynamicsBean.getFlight_number().toUpperCase());
            flag = flightDynamicsBean.getState();
            starCityDetail.setText(flightDynamicsBean.getDepcity());
            endCityDetail.setText(flightDynamicsBean.getArrcity());
            goJc.setText(flightDynamicsBean.getDepairport());
            dJc.setText(flightDynamicsBean.getArrairport());

            states.setText(flightDynamicsBean.getState());
            if (flightDynamicsBean.getSjdeptime() != null && flightDynamicsBean.getSjdeptime().length() > 11)
            {
                sjgoTime.setText(flightDynamicsBean.getSjdeptime().substring(11, flightDynamicsBean.getSjdeptime().length()));

            }
            if (flightDynamicsBean.getSjarrtime() != null && flightDynamicsBean.getSjarrtime().length() > 11)
            {
                sjdTime.setText(flightDynamicsBean.getSjarrtime().substring(11, flightDynamicsBean.getSjarrtime().length()));
            }
            tq_tbStar.setText(flightDynamicsBean.getDep_weather());
            tq_tbend.setText(flightDynamicsBean.getArr_weather());
            jhgoTime.setText(getString(R.string.ticket_jh_time) + flightDynamicsBean.getJhdeptime());
            jhdTime.setText(getString(R.string.ticket_jh_time1) + flightDynamicsBean.getJharrtime());
            qfhzl_tx.setText(flightDynamicsBean.getDep_terminal());
            zjgt_tx.setText(flightDynamicsBean.getZjgt());
            djk_tx.setText(flightDynamicsBean.getDjk());
            ddhzl_tx.setText(flightDynamicsBean.getArr_terminal());
            xlzp_tx.setText(flightDynamicsBean.getXlzp());

            String yjArrTime = flightDynamicsBean.getTimeseres();
            if(!TextUtils.isEmpty(yjArrTime)) {
                long yjArrTimeL = Utils.toTimestamp(yjArrTime, 4);
                SpannableString spannableString15 = new SpannableString("预计:" + Utils.toData(yjArrTimeL, 12));
                spannableString15.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView15.setText(spannableString15);
            }

            String yjArrTime1 = flightDynamicsBean.getYjtimeseres();
            if(!TextUtils.isEmpty(yjArrTime1)) {
                long yjArrTimeL1 = Utils.toTimestamp(yjArrTime1, 4);
                SpannableString spannableString16 = new SpannableString("预计:" + Utils.toData(yjArrTimeL1, 12));
                spannableString16.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvYjDepTime.setText(spannableString16);
            }
        }
    }


    /**
     * 更新收藏view状态；
     *
     * @param flag 1:已收藏；0：未收藏；
     */
    private void updateScPanelState(String flag)
    {
        if ("1".equals(flag))
        {
            collectPlane.setSelected(true);
            collectPlane.setBackgroundResource(R.mipmap.icon_detail_xk_full);
        } else
        {
            collectPlane.setBackgroundResource(R.mipmap.focus_succ);
            collectPlane.setSelected(false);

        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == IPlaneDtDetail.SELECT_INFO)
        {
            flightDynamicsBean = (FlightDynamicsBean) o;
            planeDetailInfo();
            initHistory();
            ProgressDialogUtil.getInstance().closeProgressDialog();
        }else
        if (type == IPlaneDtDetail.SELECT_INFO)
        {
            ProgressDialogUtil.getInstance().closeProgressDialog();
            if (o != null && o instanceof Map)
            {

                Map<String, String> onSuccessData = (Map<String, String>) o;

                if (onSuccessData.get(HttpConstant.SERVER_ERROR).equals("Error Start or End!"))
                {
                    ToastUtil.i(GlobalApp.getInstance().getContext(),
                            "无机票信息");
                } else if (onSuccessData.get(HttpConstant.SERVER_CODE).equals("9999"))
                {
                    ToastUtil.i(this, "无机票信息");
                } else
                {
                    ToastUtil.i(GlobalApp.getInstance().getContext(),
                            onSuccessData.get(HttpConstant.SERVER_ERROR));
                }
            }
            if (o == null)
            {
                //   ToastUtil.i(this, "请求超时");
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
