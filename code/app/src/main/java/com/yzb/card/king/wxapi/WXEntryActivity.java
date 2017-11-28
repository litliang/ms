package com.yzb.card.king.wxapi;


import android.content.Intent;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * Created by gqy on 16/4/13.
 * 微信分享相关；
 */
public class WXEntryActivity extends WXCallbackActivity {
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}
}
