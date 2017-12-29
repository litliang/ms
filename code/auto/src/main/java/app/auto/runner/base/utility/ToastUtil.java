package app.auto.runner.base.utility;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

//import com.umeng.analytics.MobclickAgent;

import java.util.Date;

import app.auto.runner.base.intf.MapBuilder;
import app.auto.runner.base.intf.json.JsonToMapUtils;

/**
 * Created by Administrator on 2017/11/11.
 */
public class ToastUtil {
    public static boolean toastenable = false;
    Activity act;

    public ToastUtil(Activity act) {
        this.act = act;
    }

    public void showToast(Object toast) {
        showToast(toast, "");
    }

    public void showToast(Object toast, String tag) {
        if (!toastenable) return;
        String show = "";
        if (toast instanceof String) {
            show = toast.toString();
        } else {
            show = JsonToMapUtils.entityToJson(toast);
        }
        Toast.makeText(act, show, Toast.LENGTH_SHORT).show();
        Log.d(act.getClass().getSimpleName(), show);
        if (tag.equals("umeng")) {
//            MobclickAgent.onEvent(act, show);
        }
    }
}
