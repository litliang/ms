package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yzb.card.king.R;


/**
 * <pre>
 * 文件名：	MyProgressDialog.java
 * 作　者：	lijianqiang
 * 描　述：	自定义dialog
 *
 * </pre>
 */
public class SimpleProgressDialog extends Dialog {

	private boolean needCancle = false;

	private TextView textView;

	/**
	 * @param context
	 * @param needCancle
	 */
	public SimpleProgressDialog(Context context, boolean needCancle) {
		super(context, R.style.simple_dialog);
		getWindow().setContentView(R.layout.layout_simpleprogressdialog);

		textView = (TextView) findViewById(R.id.progress_msg);
		textView.setVisibility(View.GONE);

		setDialogBG();

		this.needCancle = needCancle;
	}

	private void setDialogBG() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		// 模糊度
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().setAttributes(lp);
		// 透明度，
		lp.alpha = 1.0f;
		// 黑暗度为
		lp.dimAmount = 0.0f;
	}

	public void setPercent(String percent) {
		textView.setText(percent);
	}

	/**
	 * 设置提示文本
	 *
	 * @param text
	 */
	public void setText(String text) {
		if (textView != null) {
			textView.setText(text);
		}
	}

	/**
	 * @param context
	 * @param needCancle
	 */
	public SimpleProgressDialog(String msg, Context context, boolean needCancle) {
		super(context, R.style.simple_dialog);
		getWindow().setContentView(R.layout.layout_simpleprogressdialog);

		this.needCancle = needCancle;

		textView = (TextView) findViewById(R.id.progress_msg);
		textView.setText(msg);

		setDialogBG();
	}

	/**
	 * @see Dialog#onKeyDown(int, KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_SEARCH) {
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (needCancle) {
				// if (HttpObservable.getInstance().countObservers() > 0) {
				// HttpObservable.getInstance().noyifyHttp(
				// FusionCode.MSG_REQUEST_CANCLE);
				// }
				dismiss();
			} else {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
