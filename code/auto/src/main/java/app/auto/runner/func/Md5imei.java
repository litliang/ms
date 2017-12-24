package app.auto.runner.func;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;

import app.auto.runner.base.MD5Util;
import app.auto.runner.base.action.ParamType;
import app.auto.runner.base.task.StringFormatTask;

/**
 * Created by admin on 2017/9/3.
 */
@ParamType(type = "手机全球唯一sn码 imei",desc = "获取MD5后的手机imei值")
public class Md5imei extends StringFormatTask {


    public StringFormatTask with(Activity act) {
        return super.with(act);
    }

    @Override
    public Object run(View view, String... params) {
        TelephonyManager tm = (TelephonyManager) view.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone
        return  MD5Util.encrypt(tm.getDeviceId());
    }

}
