package app.auto.runner;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import app.auto.runner.base.utility.ToastUtil;

/**
 * Created by Administrator on 2017/11/3.
 */
public class ActivityBase extends Activity {
    ToastUtil toastUtil = new ToastUtil(this);
    public void showToast(String string){
        toastUtil.showToast(string, "");
    }

    public String getTextString(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }

    public TextView getTextView(int id) {
        return (TextView) findViewById(id);
    }
    public EditText getEditText(int id) {
        return (EditText) findViewById(id);
    }

    public RadioButton getRadioButton(int id) {
        return (RadioButton) findViewById(id);
    }
    public ImageView getImageView(int id) {
        return (ImageView) findViewById(id);
    }

    public View getView(int id) {
        return findViewById(id);
    }


}
