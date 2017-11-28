package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.QrUtil;

/**
 * 类  名：微信公众号
 * 作  者：Li Yubing
 * 日  期：2016/7/8
 * 描  述：
 */
public class WeiXinBarcodeDialog extends Dialog {

    private ImageView ivBarcode;

    /**
     * @param context
     */
    public WeiXinBarcodeDialog(Context context) {
        super(context, R.style.simple_dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_weixin_barcode);

        ivBarcode = (ImageView) findViewById(R.id.ivBarcode);

        UserBean userInfo = UserManager.getInstance().getUserBean();

        String userInfoStr = JSONObject.toJSONString(userInfo);

        ivBarcode.setImageBitmap(QrUtil.createQrImage(userInfoStr, 800, 800));

    }
}
