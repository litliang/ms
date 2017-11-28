package com.yzb.card.king.ui.integral;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.appwidget.appdialog.IntegralInputDialogFragment;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.integral.fragment.AddRosterDialogFragment;
import com.yzb.card.king.ui.integral.presenter.IntegralSharePresenter;
import com.yzb.card.king.ui.integral.view.IntegralShareView;
import com.yzb.card.king.ui.manage.IntegralDataManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分分享；
 *
 * @author gengqiyun
 * @date 2016.6.7
 */
public class IntegrationShareActivity extends BaseActivity implements View.OnClickListener,IntegralShareView
{
    private TextView tvTitle;
    private WholeListView listview;

    private AddRosterDialogFragment dialogFragment;

    private float topImgWhRate = 208 / 542f;
    private NameListAdapter adapter;
    private List<IntegralShareLinkman> nameList;
    private ScrollView scrollView;
    private IntegralSharePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integration_share);
        if(presenter == null)
        {
            presenter = new IntegralSharePresenter(this);
        }
        assignViews();

        initData();
    }

    private void initData() {

        ProgressDialogUtil.getInstance().showProgressDialog(this, false);
        presenter.loadContacts("1");
    }

    private void assignViews() {
        nameList = new ArrayList<IntegralShareLinkman>();


       // findViewById(R.id.ll_top).getLayoutParams().height = (int) (CommonUtil.getScreenWidth(this) * topImgWhRate);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.scrollTo(0, 0);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        listview = (WholeListView) findViewById(R.id.listview);
        listview.setFocusable(false);
        adapter = new NameListAdapter(IntegrationShareActivity.this);
        listview.setAdapter(adapter);
        findViewById(R.id.rl_add_name).setOnClickListener(this);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        UserBean userBean = UserManager.getInstance().getUserBean();


        if (userBean != null && userBean.getNickName() != null) {

            tv_title.setText(getString(R.string.integral_welcome_exchange_user)
                    .replace("###", UserManager.getInstance().getUserBean().getNickName()));

        } else {
            tv_title.setText(getString(R.string.integral_welcome_exchange_user));
        }
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_add_name: // 添加名单；
                Intent it = new Intent(IntegrationShareActivity.this, IntegralAddRosterActivity.class);
                startActivityForResult(it, 1000);
                break;
        }
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void onContactsLoadSuc(List<IntegralShareLinkman> o)
    {
        adapter.addNewData(o);
        IntegralDataManager.getInstance().setContactsBeenList(o);
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    @Override
    public void onContactLoadFailed(Object o)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    private class NameListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public NameListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return nameList == null ? 0 : nameList.size();
        }

        @Override
        public Object getItem(int position) {
            return nameList == null ? null : nameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_list_integration_share_add_name, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            IntegralShareLinkman temp = (IntegralShareLinkman) getItem(position);
            viewHolder.tvname.setText(temp.getNickName());
            viewHolder.tvclickgive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //ToastUtil.i(IntegrationShareActivity.this,"-----"+position);
                    exeGive(getItem(position));
                }
            });
            return viewHolder.root;
        }

        public void addNewData(List<IntegralShareLinkman> contactsBeenList) {

            nameList.clear();

            nameList.addAll(contactsBeenList);

            notifyDataSetChanged();
        }

        public class ViewHolder {
            public final TextView tvname;
            public final TextView tvclickgive;
            public final View root;

            public ViewHolder(View root) {
                tvname = (TextView) root.findViewById(R.id.tv_name);
                tvclickgive = (TextView) root.findViewById(R.id.tv_click_give);
                this.root = root;
            }
        }
    }

    /**
     * 赠送；
     *
     * @param item
     */
    private void exeGive(Object item) {

        IntegralShareLinkman integralShareLinkman = (IntegralShareLinkman) item;
        IntegralInputDialogFragment dialogFragment = IntegralInputDialogFragment.getInstance();
        dialogFragment.setIntegralShareLinkman(integralShareLinkman, UserManager.getInstance().getIntegrationNumeber());
        dialogFragment.show(getSupportFragmentManager(), "GiveIntegralDialogFragment");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1000:
                IntegralShareLinkman temp = (IntegralShareLinkman) data.getSerializableExtra("data");
                nameList.add(temp);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
