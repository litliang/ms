package com.yzb.card.king.ui.app.holder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.activity.ValidFinishActivity;
import com.yzb.card.king.ui.app.activity.ValidIdentificationActivity;
import com.yzb.card.king.ui.app.bean.UploadImage;
import com.yzb.card.king.ui.app.manager.ValidManager;
import com.yzb.card.king.ui.app.popup.IdentificationsPopup;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.controller.SelectPicController;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 18:08
 * 描述：
 */
public class ValidIdentificationHolder extends Holder implements OnItemClickListener<CertType>
{
    private final ValidIdentificationActivity activity;
    private View content;
    private LinearLayout llIdentification;
    private EditText etCardNum;
    private EditText etName;
    private IdentificationsPopup identificationsPopup;
    private TextView tvCardName;
    private View btNext;
    private View ivArrow;
    private ImageView ivBack;
    private ImageView ivFront;
    private SelectPicController controller;
    private ImageView imageView;
    private CertType certType;
    private Bitmap[] bitmaps = new Bitmap[2];
    private TextView tvPicName;
    private LinearLayout llImage2;
    private List<String> codeArray;

    public ValidIdentificationHolder(ValidIdentificationActivity activity)
    {
        this.activity = activity;
        initData();
    }

    @Override
    public View initView()
    {
        content = UiUtils.inflate(R.layout.settings_verify_identification_content);
        llIdentification = (LinearLayout) content.findViewById(R.id.llIdentification);
        etCardNum = (EditText) content.findViewById(R.id.etCardNum);
        etName = (EditText) content.findViewById(R.id.etName);
        tvCardName = (TextView) content.findViewById(R.id.tvCardName);
        ivArrow = content.findViewById(R.id.ivArrow);
        ivFront = (ImageView) content.findViewById(R.id.ivFront);
        ivBack = (ImageView) content.findViewById(R.id.ivBack);
        btNext = content.findViewById(R.id.btNext);
        tvPicName = (TextView) content.findViewById(R.id.tvPicName);
        llImage2 =  (LinearLayout) content.findViewById(R.id.llImage2);
        initListener();
        return content;
    }

    private void initData()
    {
        ivFront.setTag(0);
        ivBack.setTag(1);

        controller = new SelectPicController(activity);
        controller.setOnGetPicListener(new SelectPicController.OnGetPicListener()
        {
            @Override
            public void onGetPic(Bitmap bitmap)
            {
                bitmaps[(int) imageView.getTag()] = bitmap;
                imageView.setImageDrawable(BitmapUtil.resizeImage(bitmap, imageView.getWidth()
                        , imageView.getHeight()));

             //   imageView.setImageBitmap(bitmap);
            }
        });

        identificationsPopup = new IdentificationsPopup(ValidIdentificationHolder.this);
        certType = identificationsPopup.getDefaultValue();
    }


    private void initListener()
    {
        btNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (hasValid())
                {
                    uploadImage();
                }
            }
        });

        llIdentification.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                identificationsPopup.setWidth(llIdentification.getWidth());
                identificationsPopup.showAsDropDown(v, 0, 5);
            }
        });

        ivFront.setOnClickListener(new ChoosePic());
        ivBack.setOnClickListener(new ChoosePic());
    }

    private boolean hasValid()
    {
        return validNum() && validName();
    }

    private boolean validName()
    {
        if (TextUtils.isEmpty(etName.getText().toString()))
        {
            UiUtils.shortToast("持卡人姓名不能为空");
            return false;
        } else
        {
            return true;
        }
    }

    private boolean validNum()
    {
        if (TextUtils.isEmpty(etCardNum.getText().toString()))
        {
            UiUtils.shortToast("证件号码不能为空");
            return false;
        } else
        {
            return true;
        }
    }

    private void uploadImage()
    {
        List<UploadImage> images = new ArrayList<>();
        for (int i = 0; i < bitmaps.length; i++)
        {
            if(bitmaps[i]!=null)
            images.add(new UploadImage(bitmaps[i]));
        }
        if(isIDCard(certType) && images.size()<2){
            UiUtils.shortToast("请上传证件照");
            return;
        }
        if(!isIDCard(certType) && images.size()<1){
            UiUtils.shortToast("请上传证件照");
            return;
        }

        ValidManager.getValidManager().setImages(images);


        WaitingDialog.create(activity, "正在上传用户信息");
        getValidManager().setCallBack(new BaseCallBack<List<String>>()
        {
            @Override
            public void onSuccess(List<String> data)
            {
                codeArray = data;
                if(certType!=null&&"1".equals(certType.getType())){
                    validID();
                }else {
                    apply();
                }
            }

            @Override
            public void onFail(Error error)
            {
                WaitingDialog.close();
                LogUtil.e(error.getError());
            }
        });
        getValidManager().startUpload();
    }

    private void apply() {
        SimpleRequest request = new SimpleRequest("ApplyAuthentication");
        Map<String, Object> data = new HashMap<>();

        data.put("name", etName.getText().toString());
        data.put("certType", certType.getType());
        data.put("certNo", etCardNum.getText().toString());
        data.put("idcardCodes", getPicCode());
        request.setParam(data);
        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
                WaitingDialog.close();
                UserManager.getInstance().getUserBean().setAuthenticationStatus("3");
                activity.startActivity(new Intent(activity, ValidFinishActivity.class));
            }

            @Override
            public void onFail(Error error)
            {
                WaitingDialog.close();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void validID()
    {
        SimpleRequest request = new SimpleRequest(CardConstant.setting_authentication);
        Map<String, Object> data = new HashMap<>();

        data.put("name", etName.getText().toString());
        data.put("certNo", etCardNum.getText().toString());
        data.put("certCodes", getPicCode());
        request.setParam(data);
        request.sendRequestNew(new BaseCallBack()
        {
            @Override
            public void onSuccess(Object data)
            {
               apply();
            }

            @Override
            public void onFail(Error error)
            {
                WaitingDialog.close();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    @NonNull
    private String getPicCode() {
        StringBuilder sb = new StringBuilder();
        if(codeArray!=null&& codeArray.size()>0){

            for (int i=0 ;i<codeArray.size();i++){
                sb.append(codeArray.get(i));
                if(i!=codeArray.size()-1)sb.append(",");
            }
        }
        return sb.toString();
    }


    private ValidManager getValidManager()
    {
        return ValidManager.getValidManager();
    }

    @Override
    public void refreshView(Object data)
    {

    }

    @Override
    public void onItemClick(CertType data)
    {
        setCertType(data);
        llImage2.setVisibility(isIDCard(data) ?View.VISIBLE:View.GONE);
        tvPicName.setText(isIDCard(data)?"上传正面":"上传证件照片");
    }

    private boolean isIDCard(CertType data) {
        return "1".equals(data.getType());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        controller.onActivityResult(requestCode, resultCode, data);
    }

    public void setCertType(CertType certType)
    {
        this.certType = certType;
        tvCardName.setText(certType.getName());
    }

    class ChoosePic implements View.OnClickListener
    {
        @Override
        public void onClick(final View v)
        {
            imageView = (ImageView) v;
            controller.getPicFromGallery();
        }
    }
}
