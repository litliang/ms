package com.yzb.card.king.util.imageselect;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 图片选择器；
 * created by gengqiyun on 2016.7.12
 */
public class GalleryActivity extends BaseActivity implements ListImageDirPopupWindow.OnImageDirSelected, View.OnClickListener {

    public static final String SELECT_IMAGES = "SELECT_IMAGES";
    public static final int MAX_IMAGE_NUMBER = AppConstant.MAX_IMAGE_NUMBER;
    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;

    public static int curImageNumber = 0; // 当前图片的数量；来源于上个页面；

    public static boolean onlyOne = false;//只允许选择一张照片,此时 curImageNumber = 8；

    private GridView mGirdView;
    private ImageSelectAdapter mAdapter;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFolder> mImageFloders = new ArrayList<>();

    private RelativeLayout mBottomLy;

    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;
    private int mScreenHeight;
    private TextView tvPicNumber;
    private ListImageDirPopupWindow mListImageDirPopupWindow;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mScreenHeight = CommonUtil.getScreenHeight(this);
        initView();
        getImages();
    }

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            ToastUtil.i(this, "没有图片");
            return;
        }

        mImgs = Arrays.asList(mImgDir.list());
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new ImageSelectAdapter(getApplicationContext(), mImgs,
                R.layout.image_selector_grid_item, mImgDir.getAbsolutePath());

        mAdapter.setImgSelectCallBack(new ImageSelectAdapter.IImgSelectCallBack() {
            @Override
            public void callBack() {


                if(onlyOne){
                    tvPicNumber.setText(R.string.image_send_str);

                }else{

                    tvPicNumber.setText(getString(R.string.send_pic_number, "" +
                            (curImageNumber + mAdapter.getmSelectedImage().size())));

                }
            }
        });
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.image_selector_list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    /**
     * 初始化View
     */
    private void initView() {

        findViewById(R.id.headerLeft).setOnClickListener(this);
        tvPicNumber = (TextView) findViewById(R.id.tv_pic_number);
        tvPicNumber.setOnClickListener(this);

        if(onlyOne){
            tvPicNumber.setText(R.string.image_send_str);

        }else{

            tvPicNumber.setText(getString(R.string.send_pic_number, "" + curImageNumber));

        }

        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);

        mImageCount = (TextView) findViewById(R.id.id_total_count);

        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
        mBottomLy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_bottom_ly: //为底部的布局设置点击事件，弹出popupWindow
                mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.3f;
                getWindow().setAttributes(lp);
                break;
            case R.id.tv_pic_number:
                if (mAdapter != null) {
                    LinkedList<String> selectedImages = mAdapter.getmSelectedImage();
                    Intent intent = new Intent();
                    intent.putExtra(SELECT_IMAGES, selectedImages);
                    setResult(0x002, intent);

                    finish();
                }
                break;
            case R.id.headerLeft:
                finish();
                break;
        }
    }

    /**
     * 底部对话框内容点击的回调；
     *
     * @param floder
     */
    @Override
    public void selected(ImageFolder floder) {

        mImgDir = new File(floder.getDir());
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new ImageSelectAdapter(getApplicationContext(), mImgs, R.layout.image_selector_grid_item,
                mImgDir.getAbsolutePath());
        mAdapter.setImgSelectCallBack(new ImageSelectAdapter.IImgSelectCallBack() {
            @Override
            public void callBack() {


                if(onlyOne){
                    tvPicNumber.setText(R.string.image_send_str);

                }else{

                    tvPicNumber.setText(getString(R.string.send_pic_number, "" +
                            (curImageNumber + mAdapter.getmSelectedImage().size())));

                }
            }
        });
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(floder.getCount() + "张");
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();
        // 还原选中的值；

        if(onlyOne){
            tvPicNumber.setText(R.string.image_send_str);

        }else{

            tvPicNumber.setText(getString(R.string.send_pic_number, "" + curImageNumber));

        }
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = GalleryActivity.this.getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?"
                                + " or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png", "image/jpg"},

                        MediaStore.Images.Media.DATE_MODIFIED);

                LogUtil.e("TAG", mCursor.getCount() + "");
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    LogUtil.e("TAG", path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFolder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFolder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

}
