package com.yzb.card.king.ui.discount.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.discount.presenter.ActivityOfBankPresenter;
import com.yzb.card.king.ui.discount.presenter.BankOfActivityPresenter;
import com.yzb.card.king.ui.discount.presenter.impl.ActivityOfBankPresenterImpl;
import com.yzb.card.king.ui.discount.presenter.impl.BankOfActivityPresenterImpl;
import com.yzb.card.king.ui.discount.view.StoreListView;
import com.yzb.card.king.ui.credit.activity.CreditMoreFilterActivity;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.BankYhBean;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.ImageUtils;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 美食详情-优惠详情Fragment；
 */
public class MsDetail_YhxqFragment extends BaseFragment implements StoreListView, BaseViewLayerInterface
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String id = ""; //门店id；
    private String mParam2;
    private SpecHeightGridView bank_gv;
    private LinearLayout panel_yh;
    private BankAdapter adapter;
    private List<BankBean> bankBeans;
    private LayoutInflater inflater;
    private String customerId;
    private TextView tv_yhlist;
    private TextView tv_careList;//注意事项；
    private BankOfActivityPresenter bankActivityPresenter;
    private ActivityOfBankPresenter activityOfBankPresenter;

    public int selectBankPos = 0;//选中的bankbean；

    public static MsDetail_YhxqFragment newInstance(String param1, String param2)
    {
        MsDetail_YhxqFragment fragment = new MsDetail_YhxqFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            id = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.ms_yhxq_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        bankActivityPresenter = new BankOfActivityPresenterImpl(this);
        activityOfBankPresenter = new ActivityOfBankPresenterImpl(this);

        UserBean userBean = UserManager.getInstance().getUserBean();
        customerId = userBean == null ? "" : userBean.getAmountAccount();
        inflater = LayoutInflater.from(getActivity());

        panel_yh = (LinearLayout) view.findViewById(R.id.panel_yh);
        bank_gv = (SpecHeightGridView) view.findViewById(R.id.bank_gv);
        assignYhPart(view);

        tv_yhlist = (TextView) view.findViewById(R.id.tv_yhlist);
        tv_careList = (TextView) view.findViewById(R.id.tv_careList);

        adapter = new BankAdapter();
        bank_gv.setAdapter(adapter);
        refreshData();
    }

    /**
     * 刷新数据；
     */
    public void refreshData()
    {
        if (bankBeans == null || bankBeans.size() == 0)
        {
            getBankActivitys();
        } else
        {
            if (selectBankPos >= 0 && selectBankPos < bankBeans.size())
            {
                View viewItem = adapter.getView(selectBankPos, null, null);
                if (viewItem != null)
                {
                    //默认第一项；
                    viewItem.performClick();
                }
            }
        }
    }

    private ImageView ivXian;
    private TextView tvJryyh;
    private TextView tvXdsd;
    private TextView tvXdrq;
    private View panel_xdsd, panel_xdrq;

    private void assignYhPart(View view)
    {
        ivXian = (ImageView) view.findViewById(R.id.iv_xian);
        ivXian.setVisibility(View.GONE);
        tvJryyh = (TextView) view.findViewById(R.id.tv_jryyh);
        tvJryyh.setVisibility(View.GONE);
        tvXdsd = (TextView) view.findViewById(R.id.tv_xdsd);
        tvXdrq = (TextView) view.findViewById(R.id.tv_xdrq);
        panel_xdsd = view.findViewById(R.id.panel_xdsd);
        panel_xdsd.setVisibility(View.GONE);
        panel_xdrq = view.findViewById(R.id.panel_xdrq);
        panel_xdrq.setVisibility(View.GONE);
    }



    /**
     * 优惠列表
     *
     * @param bandId 银行卡id；
     */
    private void getYhList(final long bandId)
    {

        Map<String, Object> params = new HashMap<>();
        params.put("bankId", bandId);
        params.put("storeId", id);
        LogUtil.i("银行优惠活动提交的参数；==" + params);
        activityOfBankPresenter.loadData(params);
    }

    /**
     * 银行优惠活动；
     *
     * @param bankYhBeans
     */
    private void initBankYhActivity(List<BankYhBean> bankYhBeans)
    {
        if (bankYhBeans == null)
        {
            return;
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < bankYhBeans.size(); i++)
        {
            sb.append(bankYhBeans.get(i).detail);
        }
        tv_yhlist.setText(Html.fromHtml(sb.toString()));
    }

    /**
     * 限定时段；
     *
     * @param bankYhBeans
     */
    private void refreshXdsd(JSONObject xdsdJson, List<BankYhBean> bankYhBeans)
    {
        if (bankYhBeans == null || bankYhBeans.size() == 0)
        {
            tvJryyh.setVisibility(View.GONE);
        } else
        {
            tvJryyh.setVisibility(View.VISIBLE);
        }
        if (xdsdJson == null)
        {
            return;
        }

        String startTime = xdsdJson.getString("startTime");
        String endTime = xdsdJson.getString("endTime");

        if (!TextUtils.isEmpty(startTime))
        {
            startTime = DateUtil.long2String(Long.parseLong(startTime), DateUtil.DATE_FORMAT_DATE2);
        } else
        {
            startTime = "";
        }
        if (!TextUtils.isEmpty(endTime))
        {
            endTime = DateUtil.long2String(Long.parseLong(endTime), DateUtil.DATE_FORMAT_DATE2);
        } else
        {
            endTime = "";
        }
        tvXdsd.setText(startTime + "~" + endTime);

        //都为空；没有限制；
        if (TextUtils.isEmpty(startTime) && TextUtils.isEmpty(endTime))
        {
            ivXian.setVisibility(View.GONE);
            panel_xdsd.setVisibility(View.GONE);
            panel_xdrq.setVisibility(View.GONE);
        } else
        {
            ivXian.setVisibility(View.VISIBLE);
            panel_xdsd.setVisibility(View.VISIBLE);
            panel_xdrq.setVisibility(View.VISIBLE);
            //格式：2016/01/01~2016/05/31
            StringBuilder sb = new StringBuilder("");

            //优惠限定日期 说明：格式为2-2-21:25:00-21:27:00,2-1-09:28:00-09:28:00
            /*
               "以""-"横杠分割"
                第一个数值为时间类型：1、不限 2、工作日 3、周末 4、节假日
                第二个数值为每月日期
                第三个数值为开始时段
                第四个数值为结束时段
             */
            String monthDay = xdsdJson.getString("monthDay");
            if (!TextUtils.isEmpty(monthDay))
            {
                String[] arrays = monthDay.split("-");
                if (arrays != null && arrays.length == 4)
                {
                    //特殊字符的位置；
                    String timeType = arrays[0];
                    String everyMonthDay = arrays[1];
                    String startTimeModule = arrays[2];
                    String endTimeModule = arrays[3];

                    if ("1".equals(timeType))
                    {
                        sb.append("不限");
                    } else if ("2".equals(timeType))
                    {
                        sb.append("工作日");
                    } else if ("3".equals(timeType))
                    {
                        sb.append("周末");
                    } else if ("4".equals(timeType))
                    {
                        sb.append("节假日");
                    }
                    sb.append("每月" + everyMonthDay + "日" + startTimeModule + "~" + endTimeModule);
                }
            }
            tvXdrq.setText(sb.toString());
        }
    }

    @Override
    public void callSuccessViewLogic(Object data, int type)
    {
        if (data != null)
        {
            Map<String, String> result = (Map<String, String>) data;
            List<BankYhBean> bankYhBeans = JSON.parseArray(result.get("data"), BankYhBean.class);
            JSONObject xdsdJson = JSON.parseObject(result.get("info"));
            //限定时段
            refreshXdsd(xdsdJson, bankYhBeans);
            //优惠列表；
            initBankYhActivity(bankYhBeans);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }

    private class BankAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return bankBeans == null ? 0 : bankBeans.size();
        }

        @Override
        public BankBean getItem(int position)
        {
            return bankBeans == null ? null : bankBeans.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.item_yhk, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final BankBean bankBean = getItem(position);

            if (bankBean.bankId >= 0)
            {
                LogUtil.i("bankId大于0");
                //viewHolder.ivyhk.setBackgroundResource(ImageUtils.getBankImage(bankBean.bankId));
                viewHolder.ivyhk.setImageBitmap(AppUtils.getImageFromAssets(MsDetail_YhxqFragment.this.getContext(), ImageUtils.getBankName(bankBean.bankId)));
            } else if (bankBean.bankId == -2)
            {
                // 线上办卡；
                LogUtil.i("bankId==-2");
                viewHolder.ivyhk.setBackgroundResource(R.mipmap.logo_yh_xsbk);
            }

            viewHolder.tvname.setText(bankBean.bankName);
            viewHolder.tvname.setSelected(bankBean.isSelected);

            int bgResId = bankBean.isSelected ? R.drawable.round_shape2 : R.drawable.round_shape1;

            viewHolder.root.setBackgroundResource(bgResId);

            viewHolder.root.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    selectBankPos = position;

                    // 点击线上办卡；
                    if (bankBean.bankId == -2)
                    {
                        BankBean bank = null;
                        for (int i = 0; i < bankBeans.size(); i++)
                        {
                            if (bankBeans.get(i).isSelected)
                            {
                                bank = bankBeans.get(i);
                                break;
                            }
                        }
                        LogUtil.i("之前选中的bankId==" + bank.bankId);
                        Intent intent = new Intent(getActivity(), CreditMoreFilterActivity.class);
                        intent.putExtra("bank", new Bank(bank.bankId, bank.bankName));
                        startActivity(intent);
                    } else
                    {
                        // 先更新选中状态；
                        for (int i = 0; i < bankBeans.size(); i++)
                        {
                            if (i == position)
                            {
                                bankBean.isSelected = true;
                            } else
                            {
                                bankBeans.get(i).isSelected = false;
                            }
                        }

                        // 点击银行卡；
                        //是否已有该银行卡（1、有 2、没有）

                        if ("1".equals(bankBean.isCard))
                        {
                            removePecBank(-2);
                        } else if ("2".equals(bankBean.isCard))
                        {
                            addPecBank(new BankBean(-2L, "线上办卡"));
                        }
                        notifyDataSetChanged();

                        //加载相应银行的优惠信息；
                        getYhList(bankBean.bankId);
                    }
                }
            });

            return viewHolder.root;
        }

        public void removePecBank(long bankId)
        {
            if (bankBeans == null)
            {
                return;
            }
            for (int i = 0; i < bankBeans.size(); i++)
            {
                //找到；
                if (bankBeans.get(i).bankId == bankId)
                {
                    bankBeans.remove(i);
                }
            }
        }

        public void addPecBank(BankBean bankBean)
        {
            removePecBank(bankBean.bankId);
            bankBeans.add(bankBean);
        }

        public class ViewHolder
        {
            public final ImageView ivyhk;
            public final TextView tvname;
            public final View root;

            public ViewHolder(View root)
            {
                ivyhk = (ImageView) root.findViewById(R.id.iv_yhk);
                tvname = (TextView) root.findViewById(R.id.tv_name);
                this.root = root;
            }
        }
    }

    @Override
    public void onLoadListDataSucess(boolean event_tag, Object data)
    {
        if (data != null)
        {
            bankBeans = (List<BankBean>) data;
            if (bankBeans != null && bankBeans.size() > 0)
            {
                View view = adapter.getView(0, null, null);
                if (view != null)
                {
                    //默认第一项；
                    view.performClick();
                }
            }
        }
    }

    @Override
    public void onLoadListDataFail(String failMsg)
    {
        LogUtil.i("门店活动银行加载失败=" + failMsg);
    }

    /**
     * 获取银行优惠活动;
     */
    private void getBankActivitys()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", id);
        params.put("customerId", customerId);
        LogUtil.i("优惠活动银行提交参数:" + params);
        bankActivityPresenter.loadData(params);
    }
}
