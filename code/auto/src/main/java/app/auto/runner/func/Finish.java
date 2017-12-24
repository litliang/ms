package app.auto.runner.func;

import android.app.Activity;
import android.view.View;

import app.auto.runner.base.action.ParamType;
import app.auto.runner.base.task.StringFormatTask;

/**
 * Created by admin on 2017/9/3.
 */
@ParamType(type = "", desc = "关闭窗口")
public class Finish extends StringFormatTask {


    @Override
    public Object run(View view, String... params) {
        ((Activity) view.getContext()).finish();
        return null;
    }
}
