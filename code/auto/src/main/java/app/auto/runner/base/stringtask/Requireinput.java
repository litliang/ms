package app.auto.runner.base.stringtask;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import app.auto.runner.base.task.StringFormatTask;
import app.auto.runner.func.Print;

/**
 * Created by admin on 2017/9/3.
 */

public class Requireinput extends StringFormatTask {


    public StringFormatTask with(Activity act) {
        return super.with(act);
    }

    @Override
    public Object run(View view, String... params) {
        String txt = ((TextView) view).getText().toString();
        if(txt==null||txt.trim().equals("")){
            new Print().run(view,params[0]);
        }
        return null;
    }

}
