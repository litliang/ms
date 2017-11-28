package com.yzb.card.king.ui.discount.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.MsDetailActivity;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;


/**
 * 美食详情-商家信息Fragment；
 */
public class MsDetail_SjxxFragment extends Fragment implements View.OnClickListener
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String sjId; //商家id；
    private ImageView ivSj;
    private TextView tvName;
    private TextView tvFsnumber;
    private TextView tvDz;
    private TextView tvTel;
    private TextView tvYysj;

    private ImageOptions imageOptions;

    public MsDetail_SjxxFragment()
    {
    }

    public static MsDetail_SjxxFragment newInstance(String param1, String param2)
    {
        MsDetail_SjxxFragment fragment = new MsDetail_SjxxFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void setSjId(String sjId)
    {
        this.sjId = sjId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.ms_sjxx_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(85 / 2), ImageView.ScaleType.FIT_XY);
        ivSj = (ImageView) view.findViewById(R.id.iv_sj);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvFsnumber = (TextView) view.findViewById(R.id.tv_fsnumber);
        tvDz = (TextView) view.findViewById(R.id.tv_dz);
        tvTel = (TextView) view.findViewById(R.id.tv_tel);
        tvTel.setOnClickListener(this);
        tvYysj = (TextView) view.findViewById(R.id.tv_yysj);
        initViewContent();
    }

    /**
     * 更新商家信息中的粉丝数量；
     *
     * @param flag 0：-1， 1：+1；
     */
    public void updalteFsNumber(int flag)
    {
        Activity activity = getActivity();
        if (activity != null && activity instanceof MsDetailActivity)
        {
            MsDetailActivity context = (MsDetailActivity) activity;
            StoreBean storeBean = context.storeBean;
            if (storeBean != null)
            {
                // 粉丝数量>0才加；
                if (flag == 0 && storeBean.collectCount > 0)
                {
                    --storeBean.collectCount;
                } else if (flag == 1)
                {
                    ++storeBean.collectCount;
                }
            }
            tvFsnumber.setText(storeBean.collectCount + getResources().getString(R.string.fs));
        }
    }

    public void initViewContent()
    {
        Activity activity = getActivity();
        if (activity instanceof MsDetailActivity)
        {

            MsDetailActivity context = (MsDetailActivity) activity;
            StoreBean storeBean = context.storeBean;

            if (storeBean != null)
            {
                String imgUrl = ServiceDispatcher.getImageUrl(storeBean.storePhoto);
                x.image().bind(ivSj, imgUrl, imageOptions);
                tvName.setText(storeBean.storeName);

                tvDz.setText(storeBean.storeAddr);
                tvFsnumber.setText(TextUtils.isEmpty(storeBean.collectCount + "") ? 0 +
                        getResources().getString(R.string.fs) : storeBean.collectCount +
                        getResources().getString(R.string.fs));
                tvTel.setText(getResources().getString(R.string.tel) + storeBean.storeTel);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_tel:

                String telContent = tvTel.getText().toString();
                if (TextUtils.isEmpty(telContent) && !telContent.endsWith(":"))
                {
                    return;
                }
                String targetTel = telContent.substring(telContent.indexOf(":") + 1);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(getString(R.string.tel) + targetTel));
                //检查授权情况；
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
//                        != PackageManager.PERMISSION_GRANTED)
//                {
//                    ToastUtil.i(getActivity(), getResources().getString(R.string.notSq));
//                    return;
//                }
                startActivity(intent);
                break;
        }
    }

}
