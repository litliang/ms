package app.auto.runner.func;

import android.view.View;
import android.widget.Toast;

import app.auto.runner.base.action.ParamType;
import app.auto.runner.base.task.StringFormatTask;

/**
 * Created by admin on 2017/9/3.
 */



@ParamType(type = "信息内容 content", desc = "屏幕弹出吐司信息")
public class Print extends StringFormatTask {

    @Override
    public Object run(View view, String... params) {
        Toast.makeText(view.getContext(),(""+(params.length>0?params[0].toString():"")),Toast.LENGTH_LONG).show();
        return null;
    }
}
