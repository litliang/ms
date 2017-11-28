package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayout;
import android.text.InputFilter;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yzb.card.king.R;
import com.yzb.card.king.service.UploadImgIntentService;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.ImageInfoBean;
import com.yzb.card.king.ui.discount.fragment.SelectImgDialogFragment;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.ui.other.presenter.CommonReviewPresenter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ImageUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.imageselect.GalleryActivity;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 我要评价；
 * created by gengqiyun on 2016.7.5
 */
public class WypjActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    private static final int PHOTO_WITH_CAMERA = 201;
    private static final int PHOTO_WITH_GALLERY = 205;

    private StarBar starBarTemp;
    private EditText etOptions;
    private GridLayout gridLayout;
    private View iv_anonymous; //匿名评价复选框；

    private List<ImageInfoBean> bitmapList;

    private View panel_xzp; // 秀照片；

    private View panel_addfile; //添加文件；

    private static final int image_out_width = 350;
    private static final int image_out_height = 350;
    private static final int MAX_UPLOAD_IMG_MOUNT = 9; // 可上传图片的最大张数；


    private  CommonReviewPresenter commonReviewPresenter;
    //参数
    private  int industryId;
    private long shopId;
    private long storeId;
    private long goodsIdTwo;
    private long goodsIdThree;
    private String goodsName;
    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wypj);
        setTitleNmae("我要点评");

        commonReviewPresenter = new CommonReviewPresenter(this);

        initData();

        assignViews();
    }

    private void initData()
    {

         industryId = getIntent().getIntExtra("industryId",0);

         shopId = getIntent().getLongExtra("shopId",0);

         storeId = getIntent().getLongExtra("storeId",0);

         goodsIdTwo = getIntent().getLongExtra("goodsIdTwo",-1);

         goodsIdThree = getIntent().getLongExtra("goodsIdThree",-1);

         goodsName = getIntent().getStringExtra("goodsName");

        orderId = getIntent().getLongExtra("orderId",0);
    }

    private void assignViews()
    {
        bitmapList = new ArrayList<>();
        starBarTemp = (StarBar) findViewById(R.id.starBarTemp);

        starBarTemp.isHalfStart(true);

        starBarTemp.setIntegerMark(true);

        starBarTemp.setStarMark(1f);

        starBarTemp.setOnStarChangeListener(new StarBar.OnStarChangeListener() {
            @Override
            public void onStarChange(float mark)
            {

                int rate = (int) mark;
                if (mark >= 0 && mark < 0.5) {
                    starBarTemp.setStarMark(0.5f);
                }

            }
        });


        etOptions = (EditText) findViewById(R.id.et_options);
        // 最大140个字符；
        etOptions.setFilters(new InputFilter[]{new InputFilter.LengthFilter(140)});
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        panel_xzp = findViewById(R.id.ivAddImage);
        panel_xzp.setId((int) System.currentTimeMillis() + 1000);
        initXtpViewWh(panel_xzp);

        panel_xzp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // 打开图片系统；
                goPickImgeActivity();
            }
        });

        panel_addfile = getLayoutInflater().inflate(R.layout.gl_addfile, null);
        panel_addfile.setId((int) System.currentTimeMillis() + 1000);

        //预添加增添图片的ImageView
        addViewToGl(panel_addfile, "", false);

        //匿名评价View；
        iv_anonymous = findViewById(R.id.iv_anonymous);
        iv_anonymous.setSelected(false);

        //匿名评价外部view；
        findViewById(R.id.anonymous).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
    }

    /**
     * 添加图片到GridLayout：
     *
     * @param view
     * @param imgBase64
     * @param hasImage
     */
    private void addViewToGl(View view, String imgBase64, boolean hasImage)
    {

        if (gridLayout != null && view != null) {
            initGlItemViewWh(view);
            if (gridLayout.getChildCount() >= 1) {
                //添加到倒数第二位；
                gridLayout.addView(view, gridLayout.getChildCount() - 1);
                bitmapList.add(new ImageInfoBean(view.getId(), imgBase64, hasImage));
            } else {
                // 此处添加默认的秀图片；
                gridLayout.addView(view);
                bitmapList.add(new ImageInfoBean(view.getId(), "", false));
            }
        }

        // 秀照片或添加图片按钮；
        if (view.getId() == panel_addfile.getId()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // 打开图片系统；
                    goPickImgeActivity();
                }
            });
        }

        // 判断是否隐藏秀照片部分；
        // 无有效图片时，显示秀照片；
        // 否则，隐藏秀照片，显示添加照片部分；
        if (gridLayout != null && gridLayout.getChildCount() >= 2) {
            panel_xzp.setVisibility(View.GONE);
            panel_addfile.setVisibility(View.VISIBLE);
        } else {
            // 显示秀照片；
            panel_xzp.setVisibility(View.VISIBLE);
            panel_addfile.setVisibility(View.GONE);
        }
    }

    /**
     * 跳转至选择图片界面；
     */
    private void goPickImgeActivity()
    {
        // 最多九张；
        if (gridLayout.getChildCount() >= MAX_UPLOAD_IMG_MOUNT + 1) {
            toastCustom(R.string.upload_max_picnum_notice);
            return;
        }
        SelectImgDialogFragment.getInstance("", "").setCallBack(new IDialogCallBack() {
            @Override
            public void dialogCallBack(Object... obj)
            {
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
     * 图库选择；
     */
    private void fromGalley()
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                !Environment.isExternalStorageRemovable()) {
            Intent intent = new Intent(this, GalleryActivity.class);
            GalleryActivity.curImageNumber = gridLayout.getChildCount() - 1;
            startActivityForResult(intent, PHOTO_WITH_GALLERY);
        } else {
            toastCustom(R.string.sd_card_unavailable);
        }
    }

    /**
     * 拍照；
     */
    private void capture()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  拍照后保存图片的绝对路径
        String cameraPath = ImageUtils.setCameraImgPath(this);
        File file = new File(cameraPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, PHOTO_WITH_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case PHOTO_WITH_CAMERA:// 拍照返回；
                String cameraPath = ImageUtils.getCameraImgPath();
                if (CommonUtil.isEmptyCharacter(cameraPath)) {
                    toastCustom(R.string.get_img_error);
                    return;
                }
                File file = new File(cameraPath);
                if (file.exists()) {
                    final View glItem = getLayoutInflater().inflate(R.layout.gl_image_item, null);
                    glItem.setId((int) System.currentTimeMillis() + 1000);
                    ImageView iv = (ImageView) glItem.findViewById(R.id.iv);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    //旋转的角度；
                    int degree = BitmapUtil.getBitmapDegree(cameraPath);

                    glItem.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            delViewFromGl(glItem.getId());
                        }
                    });
                    Bitmap bitmap = BitmapFactory.decodeFile(cameraPath, BitmapUtil.getBitmapOptions(cameraPath, image_out_width, image_out_height));
                    bitmap = BitmapUtil.turnBitmap(bitmap, degree);
                    iv.setImageBitmap(bitmap);
                    String base64Photo = Base64.encodeToString(BitmapUtil.getBitmapByte(bitmap), Base64.DEFAULT);
//                    bitmap.recycle();
                    addViewToGl(glItem, base64Photo, true);
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
                if (images == null || images.size() == 0) {
                    return;
                }
                LogUtil.i("接收到图片张数：===" + images.size());
                for (String itemImage : images) {
                    handleRecvImage(itemImage);
                }
                break;
        }
    }

    /**
     * 处理返回的图片；
     *
     * @param imagePath
     */
    public void handleRecvImage(String imagePath)
    {
        if (isEmpty(imagePath)) {
            return;
        }

        Bitmap newBitmap = BitmapFactory.decodeFile(imagePath, BitmapUtil.getBitmapOptions(imagePath, image_out_width, image_out_height));
        String base64Photo = Base64.encodeToString(BitmapUtil.getBitmapByte(newBitmap), Base64.DEFAULT);

        final View glItem = getLayoutInflater().inflate(R.layout.gl_image_item, null);
        glItem.setId((int) System.currentTimeMillis() + 1000);
        ImageView iv = (ImageView) glItem.findViewById(R.id.iv);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageBitmap(newBitmap);

        glItem.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                delViewFromGl(glItem.getId());
            }
        });
        addViewToGl(glItem, base64Photo, true);
    }

    /**
     * 删除图片；
     *
     * @param id
     */
    private void delViewFromGl(int id)
    {
        if (gridLayout != null) {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                if (gridLayout.getChildAt(i).getId() == id) {
                    // 删除view；
                    gridLayout.removeView(gridLayout.getChildAt(i));
                    bitmapList.remove(i);
                    break;
                }
            }

            if (gridLayout != null && gridLayout.getChildCount() >= 2) {
                panel_xzp.setVisibility(View.GONE);
                panel_addfile.setVisibility(View.VISIBLE);
            } else {
                panel_xzp.setVisibility(View.VISIBLE);
                panel_addfile.setVisibility(View.GONE);
            }
        }
    }

    /**
     * init GridLayout中item的尺寸；
     *
     * @param view
     */
    private void initGlItemViewWh(View view)
    {
        if (view != null) {
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            int size = (int) ((CommonUtil.getScreenWidth(this) - CommonUtil.dip2px(this, 50)) / 3.0f + 0.5f);
            int margin = CommonUtil.dip2px(this, 5);
            layoutParams.width = size;
            layoutParams.height = size;
            layoutParams.leftMargin = margin;
            layoutParams.rightMargin = margin;
            layoutParams.topMargin = margin;
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 初始化秀图片的尺寸；
     */
    private void initXtpViewWh(View view)
    {
        if (view != null) {
            int size = (int) ((CommonUtil.getScreenWidth(this) - CommonUtil.dip2px(this, 50)) / 3.0f + 0.5f);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
            int margin = CommonUtil.dip2px(this, 5);
            layoutParams.leftMargin = margin;
            layoutParams.topMargin = margin;
            view.setLayoutParams(layoutParams);
        }
    }


    private boolean isInvilid()
    {
        if (starBarTemp.getStarMark() == 0.0f) {
            toastCustom(R.string.no_mark);
            return false;
        }
        if (isEmpty(etOptions.getText().toString().trim())) {
            toastCustom(R.string.input_comment_content);
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.anonymous: //匿名；
                iv_anonymous.setSelected(!iv_anonymous.isSelected());
                break;
            case R.id.tv_commit:
                if (!isInvilid()) {
                    return;
                }
                // 有图片先处理图片,否则，直接提交；(bitmapList中默认有一张图片，size>=1)
                if (bitmapList == null || bitmapList.size() <= 1) {
                    exeCommit("");
                } else {
                    //图片的Base64格式，
                    List<String> imgArray = new ArrayList<>();
                    // 要提交的图片的code码数组；
                    List<String> codeArray = new ArrayList<>();

                    for (int i = 0; i < bitmapList.size(); i++) {
                        try {
                            if (bitmapList.get(i).isHasImage()) {
                                imgArray.add(bitmapList.get(i).getImgStr());
                                //生成图片的code码；
                                codeArray.add(ImageUtils.getCode("" + i));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtil.i("图片上传失败");
                            return;
                        }
                    }
                    //有要上传的图片；
                    if (imgArray.size() > 0) {
                        UploadImgIntentService.uploadFile(getApplicationContext(), imgArray, codeArray);
                        // 组合image code列表；
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < codeArray.size(); i++) {
                            if (i == codeArray.size() - 1) {
                                sb.append(codeArray.get(i));
                            } else {
                                sb.append(codeArray.get(i) + ",");
                            }
                        }
                       exeCommit(sb.toString());
                    }
                }
                break;
        }
    }

    /**
     * 提交；
     *
     * @param picCodes
     */
    private void exeCommit(final String picCodes)
    {
        showPDialog(R.string.setting_committing);
        // 1星2分；
         int rating = (int) (starBarTemp.getStarMark());

        String comment = etOptions.getText().toString().trim();

        if (!isEmpty(comment)) {
            try {
                comment = URLEncoder.encode(comment, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String anonymous = iv_anonymous.isSelected() ? "1" : "0";

        commonReviewPresenter.sendCreateReviewRequest(industryId,shopId,storeId,goodsIdTwo,goodsIdThree,goodsName,rating,comment,anonymous,picCodes,orderId);
    }


    @Override
    public void callSuccessViewLogic(Object data, int type)
    {

        dimissPdialog();

        finish();
        ToastUtil.i(this,"成功");
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        dimissPdialog();

        ToastUtil.i(this,"失败");
    }
}
