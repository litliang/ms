package com.yzb.card.king.ui.film.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

/**
 * 输入手机号
 */
public class InputMobileActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout headerLayout;

    private EditText mobile;
    private Button confirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mobile);

        // 设置标题
        setHeader(R.mipmap.icon_back_white, "输入手机号");

        // 初始化
        init();

        // 返回转账
        switchContentLeft(AppConstant.RES_BACK);

    }

    /**
     * 初始化
     */
    public void init() {

        headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        headerLayout.setBackgroundColor(getResources().getColor(R.color.titleRed));

        Intent intent = getIntent();

        String mobileStr = intent.getStringExtra("mobile");

        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        mobile = (EditText) findViewById(R.id.mobile);
        mobile.setText(mobileStr);
        ((TextView) findViewById(R.id.readContacts)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.confirm:

                String mobileStr = mobile.getText().toString();

                mobileStr = mobileStr.replaceAll("\\s*", "");

                if(!ValidatorUtil.isMobile(mobileStr)){
                    ToastUtil.i(InputMobileActivity.this, "手机号格式错误");
                    break;
                }
                Intent intent = new Intent();
                intent.putExtra("mobile", mobileStr);
                setResult(0, intent);
                finish();
                break;

            case R.id.readContacts:// 读取联系人
                startActivityForResult(new Intent(
                        Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String username = "";
        String usernumber = "";

        if (resultCode == Activity.RESULT_OK) {
            //ContentProvider展示数据类似一个单个数据库表
            //ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
            ContentResolver reContentResolverol = getContentResolver();
            //URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
            Uri contactData = data.getData();
            //查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            //获得DATA表中的名字
            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //条件为联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mobile.setText(usernumber);

            }
        }
    }

}
