package com.yzb.card.king.ui.discount.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gengqiyun on 2016/4/20.
 * 分享对话框；
 */
public class ShareDialogFragment extends BaseDialogFragment implements View.OnClickListener,CommandView {
    private static ShareDialogFragment dialogFragment;
    private LayoutInflater inflater;
    private String title = "Hi life";
    private String content = "Hi life";
    private String image = "";
    private String url = "";

    public static final int WEIXIN_INDEX = 1;
    private Activity activity;

    static SHARE_MEDIA[] platfomlist = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE};//, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT
    private Bitmap bitmap;

    private BaseActivity baseActivity;

    public void setContent(BaseActivity baseActivity){

        this.baseActivity = baseActivity;
    }


    public ShareDialogFragment setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
        return this;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    public ShareDialogFragment setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public ShareDialogFragment setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public ShareDialogFragment setImage(String image)
    {
        this.image = image;
        return this;
    }

    public ShareDialogFragment setContent(String content)
    {
        this.content = content;
        return this;
    }

    public static ShareDialogFragment getInstance(String arg1, String arg2)
    {

        synchronized (ShareDialogFragment.class) {
            if (dialogFragment == null) {
                dialogFragment = new ShareDialogFragment();
            }
        }
        return dialogFragment;
    }

    /**
     * 发送获取分享内容
     * @param codeType
     * @param hotelId
     * @param industryId
     */
    public  void initShareContent(String codeType,long hotelId,String industryId){

        CommandPresenter   commandPresenter = new CommandPresenter(this);
        Map<String, Object> args = new HashMap<>();
        args.put("codeType",codeType);
        args.put("code", hotelId); //编码
        args.put("industryId",industryId);
        args.put("activityData", JSON.toJSONString(getActivityMap(hotelId)));
        commandPresenter.loadData(args);
    }


    private Map<String, Object> getActivityMap(long hotelId) {
        Map<String, Object> param = new HashMap<>();
        param.put("hotelId", hotelId);
        return param;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_share_dialog;
    }

    protected String[] share_platforms_packages;

    protected void initView(View view)
    {
        share_platforms_packages = getResources().getStringArray(R.array.share_platforms_packages);
        inflater = LayoutInflater.from(getActivity());
        GridView gv = (GridView) view.findViewById(R.id.gv);
        gv.setAdapter(new ShareGridAdapter());
        view.findViewById(R.id.ivClose).setOnClickListener(this);
    }

    private ImageView iv;
    private TextView tv;

    @Override
    public void onClick(View v)
    {
        dismiss();
    }

    @Override
    public void onGetCommandSuc(String commandAndUrl)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        url = CommonUtil.getGiftcardShareUrl(commandAndUrl);

        show(baseActivity.getSupportFragmentManager(), "");

    }

    @Override
    public void onGetCommandFail(String failMsg)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();


    }

    public class ShareGridAdapter extends BaseAdapter {
        public String[] sharePlatforms = getActivity().getResources().getStringArray(R.array.share_platforms_array);
        public int[] resIds = {
                R.mipmap.icon_weixin_new
                , R.mipmap.icon_weixin_pyq_new
                , R.mipmap.icon_qq_new
                , R.mipmap.icon_qq_q_new
                , R.mipmap.icon_email_new
                , R.mipmap.icon_sms_new
                , R.mipmap.icon_copy_new

        };
        //      , R.mipmap.icon_share_qqkj , R.mipmap.icon_share_xlwb , R.mipmap.icon_share_txwb

        @Override
        public int getCount()
        {
            return sharePlatforms.length;
        }

        @Override
        public String getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent)
        {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_share, parent, false);
                iv = (ImageView) convertView.findViewById(R.id.iv);
                tv = (TextView) convertView.findViewById(R.id.tv);
            }
            iv.setBackgroundResource(resIds[position]);
            tv.setText(sharePlatforms[position]);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    dismiss();
                    if (position < 4) {

                        exeShare(activity, position);

                    } else {

                        if (position == 4) {//邮件
                            Context context = getContext();

                            CommonUtil.sendEmail(context, "", url);

                        } else if (position == 5) {//短信

                            Context context = getContext();

                            CommonUtil.sendSmsAction(context, url);

                        } else if (position == 6) {//复制

                            if (getActivity() != null) {

                                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                                clipboard.setPrimaryClip(ClipData.newPlainText("command", url));

                                toastCustom("复制成功");

                            }
                        }

                    }
                }
            });
            return convertView;
        }
    }

    protected void exeShare(final Activity activity, int position)
    {
        if (position < 0 || position >= platfomlist.length) {
            return;
        }

        // 自定义分享对话框；
        ProgressDialog dialog = new ProgressDialog(activity, R.style.dialog_style);
        dialog.setMessage("正在分享，请稍后...");
        //使用自定义的dialog；
        Config.dialog = dialog;

        ShareAction shareAction = new ShareAction(activity).setPlatform(platfomlist[position]);

        if (!TextUtils.isEmpty(title)) {
            shareAction.withTitle(title.length() > 20 ? title.substring(0, 20) : title);
        }

        if (!TextUtils.isEmpty(content)) {
            shareAction.withText(content);
        }
        if (!TextUtils.isEmpty(url)) {
            shareAction.withTargetUrl(url);
        }

        if (!TextUtils.isEmpty(image)) {
            shareAction.withMedia(new UMImage(activity, image));
        } else if (bitmap != null) {
            shareAction.withMedia(new UMImage(activity, bitmap));
        }

        shareAction.setCallback(new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA share_media)
            {
                if (shareCallBack != null) {
                    shareCallBack.shareCallBack();
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable)
            {
                if (getActivity() != null) {
                    ToastUtil.i(activity, "分享失败");
                    if (shareCallBack != null) {
                        shareCallBack.shareCallBack();
                    }
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media)
            {
                if (getActivity() != null) {
                    ToastUtil.i(activity, "分享取消");
                    if (shareCallBack != null) {
                        shareCallBack.shareCallBack();
                    }
                }
            }
        }).share();
    }

    private IShareCallBack shareCallBack;

    public void setShareCallBack(IShareCallBack shareCallBack)
    {
        this.shareCallBack = shareCallBack;
    }

    public interface IShareCallBack {
        void shareCallBack();
    }
}
