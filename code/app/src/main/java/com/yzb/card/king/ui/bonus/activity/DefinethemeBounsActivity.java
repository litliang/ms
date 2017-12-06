package com.yzb.card.king.ui.bonus.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.model.UploadImageImpl;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.ui.bonus.bean.BounsThemeParam;
import com.yzb.card.king.ui.discount.fragment.SelectImgDialogFragment;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ImageUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ScaleImageActivity;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.imageselect.GalleryActivity;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/6/14
 * 描  述：
 */
@ContentView(R.layout.activity_bouns_define)
public class DefinethemeBounsActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    private static final int PHOTO_WITH_CAMERA = 201;

    private static final int PHOTO_WITH_GALLERY = 205;

    public static final String ActivityData = "activity_data";

    @ViewInject(R.id.etBoundThemeName)
    private EditText etBoundThemeName;

    @ViewInject(R.id.etBounsWishes)
    private EditText etBounsWishes;

    @ViewInject(R.id.ivBackgroundImage)
    private ImageView ivBackgroundImage;

    @ViewInject(R.id.tvRemarkNum)
    private TextView tvRemarkNum;

    private BounsThemeParam themeParam;

    private UploadImageImpl uploadImage;

    private String imageUploadCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uploadImage = new UploadImageImpl(this);

        initView();

        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.hideSoftShowCursor(this, etBoundThemeName);
        uploadImage = null;
    }

    private void initView() {
        setHeader(R.mipmap.icon_back_white, getString(R.string.define_red_envelope));

        findViewById(R.id.headerLeft).setOnClickListener(this);

        findViewById(R.id.tvPreScan).setOnClickListener(this);

        findViewById(R.id.tvOk).setOnClickListener(this);

        tvRemarkNum.setText("0/20");

        etBounsWishes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String inputString = s.toString();

                int inputStringLength = inputString.length();

                tvRemarkNum.setText(inputStringLength + "/20");

            }
        });
        //计算图片加载控件
        double scallD = 568d / 1000d;
        int actualWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith() - CommonUtil.px2dip(this, 2 * 16);
        int actualHeight = (int) (actualWith * scallD);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivBackgroundImage.getLayoutParams();
        lp.height = actualHeight;
        ivBackgroundImage.setLayoutParams(lp);

    }

    private void initData() {
        Serializable ser = getIntent().getSerializableExtra(ActivityData);
        if (ser != null) {
            themeParam = (BounsThemeParam) ser;
        }
        ivBackgroundImage.setTag(false);
        if (DefinethemeBounsActivity.bless != null) {
            etBounsWishes.setText(DefinethemeBounsActivity.bless);
        }
    }

    String previewPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_WITH_CAMERA:// 拍照返回；
                String cameraPath = ImageUtils.getCameraImgPath();
                if (CommonUtil.isEmptyCharacter(cameraPath)) {
                    toastCustom(R.string.get_img_error);
                    return;
                }
                File file = new File(cameraPath);
                if (file.exists()) {

                    //旋转的角度；
                    int degree = BitmapUtil.getBitmapDegree(cameraPath);

                    Bitmap bitmap = BitmapFactory.decodeFile(cameraPath, BitmapUtil.getBitmapOptions(cameraPath, 350, 350));

                    bitmap = BitmapUtil.turnBitmap(bitmap, degree);

                    ivBackgroundImage.setImageBitmap(bitmap);

                    ivBackgroundImage.setTag(true);

                    uploadImage.upLoadImage(bitmap);

                    previewPath = cameraPath;

                } else {
                    toastCustom(R.string.get_img_error);
                }
                break;
            case PHOTO_WITH_GALLERY:// 图库图片返回
                if (data == null) {
                    return;
                }
                Serializable obj = data.getSerializableExtra(GalleryActivity.SELECT_IMAGES);
                List<String> images = (List<String>) obj;

                LogUtil.e("ABCD", "size=" + images.size());
                if (images == null || images.size() == 0) {
                    return;
                }
                for (String itemImage : images) {

                    Bitmap newBitmap = BitmapFactory.decodeFile(itemImage, BitmapUtil.getBitmapOptions(itemImage, 350, 350));

                    ivBackgroundImage.setImageBitmap(newBitmap);

                    ivBackgroundImage.setTag(true);

                    uploadImage.upLoadImage(newBitmap);

                    previewPath = itemImage;
                }
                break;
        }
    }

    public static String bless = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvPreScan://预览
                if (previewPath == null) {
                    ToastUtil.i(v.getContext(), "背景缺失，请选择后预览");
                } else {
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(Uri.parse(previewPath), "image/*");
                    startActivity(intent);
                }
                break;
            case R.id.tvOk:

                if (checkData()) {

                    String str1 = etBoundThemeName.getText().toString().trim();

                    String str2 = etBounsWishes.getText().toString().trim();

                    if (TextUtils.isEmpty(str2)) {
                        str2 = etBounsWishes.getHint().toString();
                    }
                    Intent intent = new Intent();

                    BounsThemeBean bean = new BounsThemeBean();

                    bean.setThemeName(str1);

                    bean.setBlessWord(str2);

                    bean.setOpenImageCode(imageUploadCode);
                    bean.setCloseImageCode(imageUploadCode);
                    intent.putExtra("data", bean);

                    if (getCallingActivity().getClassName().contains(BounsCreateActivity.class.getSimpleName())) {
                        setResult(1001, intent);
                        finish();

                    } else if (getCallingActivity().getClassName().contains(BounsThemeActivity.class.getSimpleName())) {
                        startActivity(new Intent(getBaseContext(), BounsCreateActivity.class).putExtra("backThemeData", bean).putExtra("isFromCustomTheme",true));

                        finish();
                    }
                }

                break;
            default:
                break;
        }
    }

    private boolean checkData() {


        boolean flag = true;

        int str = 0;

        String str1 = etBoundThemeName.getText().toString().trim();

        String str2 = etBounsWishes.getText().toString().trim();

        boolean drawableFlag = (boolean) ivBackgroundImage.getTag();


        if (TextUtils.isEmpty(str1)) {

            flag = false;

            str = R.string.toast_set_define_name;

        }
//        else if (TextUtils.isEmpty(str2)) {
//
//            flag = false;
//
//            str = R.string.toast_set_define_dedark;
//        }
        else if (!drawableFlag) {

            flag = false;

            str = R.string.toast_set_define_imagebackground;

        }


        if (!flag) {
            ToastUtil.i(this, str);
        }

        return flag;
    }

    @Event(R.id.addBackgroundImage)
    private void addBackgroundImage(View view) {

        SelectImgDialogFragment.getInstance("", "").setCallBack(new IDialogCallBack() {
            @Override
            public void dialogCallBack(Object... obj) {
                if (obj != null && obj.length > 0) {
                    //0:拍照；1：相册选择；
                    if ("0".equals(String.valueOf(obj[0]))) {
                        capture();
                    } else {
                        fromGalley();
                    }
                }
            }
        }).show(getSupportFragmentManager(), "");

    }

    /**
     * 拍照；
     */
    private void capture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  拍照后保存图片的绝对路径
        String cameraPath = ImageUtils.setCameraImgPath(this);
        File file = new File(cameraPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, PHOTO_WITH_CAMERA);
    }

    /**
     * 图库选择；
     */
    private void fromGalley() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                !Environment.isExternalStorageRemovable()) {
            Intent intent = new Intent(this, GalleryActivity.class);
            GalleryActivity.curImageNumber = 8;
            GalleryActivity.onlyOne = true;
            startActivityForResult(intent, PHOTO_WITH_GALLERY);
        } else {
            toastCustom(R.string.sd_card_unavailable);
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        imageUploadCode = o + "";
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
    }
}
