package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.PaymethodAndBankPreStageBean;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.CardInfo;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;
import com.yzb.wallet.logic.bank.bankListLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/18
 * 描  述：
 */
public class BankSelectPopup implements View.OnClickListener, BaseViewLayerInterface {

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private String[] nameArray = new String[]{};

    private int[] nameValueArray = new int[]{};

    private TextView tvTitleName;

    private ListView wvLvData;

    private BankSelectPopup.CurrentPpAdapter ppAdapter;

    private int noCheckColor;

    private int checkedColor;

    private BankSelectPopup.BottomDataListCallBack callBack;

    private List<PaymethodAndBankPreStageBean> totalList;

    private boolean tyleFlag = false;//目前只有两个银行样式  true; 具备清理和确定功能

    private String myBankIds;//我的银行id

    private String allBankIds;//所有银行id

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public BankSelectPopup(Activity activity, int defineHeight, boolean falg)
    {
        this.tyleFlag = falg;

        this.activity = activity;

        noCheckColor = activity.getResources().getColor(R.color.text_color_33);

        checkedColor = activity.getResources().getColor(R.color.color_416b90);

        baseBottomFullPP = new BaseFullPP(activity, BaseFullPP.ViewPostion.VIEW_TOP);

        View view = null;

        if (tyleFlag) {

            view = LayoutInflater.from(this.activity).inflate(R.layout.view_use_bank_selector_pp, null);

            view.findViewById(R.id.tvClear).setOnClickListener(this);

            view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        } else {

            view = LayoutInflater.from(this.activity).inflate(R.layout.view_bottom_title_listview, null);

        }

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);

            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
            @Override
            public void onClickListenerDismiss()
            {
                onSelectedItemObjectAndDismiss(ppAdapter.getSelectIndex());
            }
        });


        if (tyleFlag) {

        } else {

            tvTitleName = (TextView) view.findViewById(R.id.tvTitleName);

            tvTitleName.setVisibility(View.GONE);

            view.findViewById(R.id.tvLine).setVisibility(View.GONE);

        }

        wvLvData = (ListView) view.findViewById(R.id.wvLvData);

        ppAdapter = new BankSelectPopup.CurrentPpAdapter();

        nameArray = activity.getResources().getStringArray(R.array.user_bank_info_name_array);

        if (!UserManager.getInstance().isLogin()) {

            nameArray = Utils.changeToStringArrayRemoveTotalStr(nameArray,nameArray[1]);
        }

        wvLvData.setAdapter(ppAdapter);

        wvLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                ppAdapter.setSelectedIndex(position);

                if (tyleFlag) {


                } else {

                    onSelectedItemObjectAndDismiss(position);
                }

            }
        });

        //发送合作银行信息
        GetCouponPersenter getCouponPersenter = new GetCouponPersenter(this);

        getCouponPersenter.sendQueryCoopertiveBankActionRequest(0, Integer.MAX_VALUE);

        if (UserManager.getInstance().isLogin()) {
            //发送我的绑卡信息
            loadCardNum();
        }

    }

    public String getMyBankIds()
    {
        return myBankIds;
    }

    public String getAllBankIds()
    {
        return allBankIds;
    }

    private void loadCardNum()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        bankListLogic payHandle = new bankListLogic(activity);
        payHandle.getData(params);
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {

                List<CardInfo> cardInfoList = JSON.parseArray(resultMap.get("data"), CardInfo.class);

                for (CardInfo cardInfo : cardInfoList) {

                    LogUtil.e("" + cardInfo.getBankName() + "---------" + cardInfo.getArchivesPhoto());
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                UiUtils.shortToast(ERROR_MSG);
            }
        });
    }


    public void setCallBack(BankSelectPopup.BottomDataListCallBack callBack)
    {
        this.callBack = callBack;
    }

    public void setTitleName(int strId)
    {

        if (tvTitleName != null) {

            tvTitleName.setText(strId);
        }

    }

    public void setSelectIndex(int selectIndex)
    {

        ppAdapter.setSelectedIndex(selectIndex);
    }

    public void showPP(View rootView)
    {
        baseBottomFullPP.showBottomAsView(rootView);
    }

    public List<PaymethodAndBankPreStageBean> getTotalList()
    {
        return totalList;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvClear://清理

                ppAdapter.setSelectedIndex(-1);

                break;
            case R.id.tvConfirm://确定
                onSelectedItemObjectAndDismiss(ppAdapter.getSelectIndex());
                break;
            default:
                break;

        }
    }

    /**
     * @param position 当callBank传递的数据 null,-1,-1时，则说明清空了
     */
    private void onSelectedItemObjectAndDismiss(int position)
    {
        baseBottomFullPP.dismiss();

        if (callBack != null) {

            String name = null;

            int value = -1;

            if (position == -1) {

            } else {

                int length = nameValueArray.length;

                if (length > 0) {

                    value = nameValueArray[position];
                }

                name = nameArray[position];
            }
            callBack.onClickItemDataBack(name, value, position);
        }

    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == GetCouponPersenter.QUERYCOOPERATIVEBANK_CODE) {//合作银行列表

            totalList = JSONArray.parseArray(o + "", PaymethodAndBankPreStageBean.class);

            int length = nameArray.length;

            int size = totalList.size();

            String[] totalInt = new String[length + size];

            int i = 0;
            for (; i < length; i++) {

                totalInt[i] = nameArray[i];

            }

            StringBuffer allBankSb = new StringBuffer();
            //
            for (int j = 0; j < size; j++) {

                PaymethodAndBankPreStageBean bankPreStageBean = totalList.get(j);

                totalInt[i + j] = bankPreStageBean.getBankName();

                allBankSb.append(bankPreStageBean.getBankId());

                if (j == size - 1) {

                } else {
                    allBankSb.append(",");
                }

            }
            allBankIds = allBankSb.toString();

            nameArray = totalInt;

            ppAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }


    private class CurrentPpAdapter extends BaseAdapter {

        private int selectIndex = 0;

        @Override
        public int getCount()
        {
            return nameArray.length;
        }

        @Override
        public Object getItem(int position)
        {
            return nameArray[position];
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            BankSelectPopup.CurrentPpAdapter.ViewHold viewHold;

            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_name_bottom, null);

                viewHold = new BankSelectPopup.CurrentPpAdapter.ViewHold(convertView);


                convertView.setTag(viewHold);
            } else {

                viewHold = (BankSelectPopup.CurrentPpAdapter.ViewHold) convertView.getTag();
            }

            String nameStr = nameArray[position];

            viewHold.tvItemName.setText(nameStr);

            if (selectIndex == position) {

                viewHold.ivChecked.setVisibility(View.VISIBLE);

                viewHold.tvItemName.setTextColor(checkedColor);

            } else {

                viewHold.ivChecked.setVisibility(View.INVISIBLE);

                viewHold.tvItemName.setTextColor(noCheckColor);
            }

            if (position + 1 == getCount()) {

                viewHold.vLine.setVisibility(View.INVISIBLE);

            } else {
                viewHold.vLine.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        public void setSelectedIndex(int selectedIndex)
        {
            this.selectIndex = selectedIndex;
            notifyDataSetChanged();
        }

        public int getSelectIndex()
        {

            return selectIndex;
        }


        class ViewHold {

            ImageView ivChecked;

            TextView tvItemName;

            View vLine;

            public ViewHold(View convertView)
            {
                ivChecked = (ImageView) convertView.findViewById(R.id.ivChecked);

                tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);

                vLine = convertView.findViewById(R.id.vLine);
            }
        }
    }

    public interface BottomDataListCallBack {

        void onClickItemDataBack(String name, int nameValue, int selectIndex);
    }
}
