package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/5 18:17
 */
public class IdentifySuccess
{
    private View view;
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvId;

    public IdentifySuccess()
    {
        initView();
        initData();
    }

    private void initData()
    {
        UserBean userBean = UserManager.getInstance().getUserBean();
        x.image().bind(ivPhoto, userBean.getPic());
        tvName.setText(userBean.getAuthenticationInfo().getRealName());
        tvId.setText(UiUtils.getString(R.string.user_id
                , RegexUtil.hideIdMidNum(userBean.getAuthenticationInfo().getCertNo())));
    }

    private void initView()
    {
        view = UiUtils.inflate(R.layout.holder_identitfy_success);
        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvId = (TextView) view.findViewById(R.id.tvId);
    }

    public View getView()
    {
        return view;
    }
}
