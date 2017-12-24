package app.auto.runner.func;

import android.view.View;

import app.auto.runner.base.ActivityStack;
import app.auto.runner.base.RRes;
import app.auto.runner.base.action.ParamType;
import app.auto.runner.base.framework.Init;
import app.auto.runner.base.task.StringFormatTask;

/**
 * Created by admin on 2017/9/3.
 */
@ParamType(type = "ActivitySimpleName simplename;R.id.xxx id", desc = "点击界面指定视图组件")
public class Clickactivityid extends StringFormatTask {


    @Override
    public Object run(View view, String... params) {
        try {
            Class clz = Class.forName(Init.bigContext.getPackageName()+".activity."+params[0].toString());
            ActivityStack.getInstance().findActivityByClass(clz).findViewById(RRes.get("R.id."+params[1].toString()).getAndroidValue()).performClick();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
