package app.auto.runner.func;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.view.View;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Map;

import app.auto.runner.base.DialogUtil;
import app.auto.runner.base.action.BaseTask;
import app.auto.runner.base.action.ParamType;
import app.auto.runner.base.intf.QueryBuilder;
import app.auto.runner.base.task.StringFormatTask;

/**
 * Created by Administrator on 2017/11/2.
 */

@ParamType(type = "域名地址 url;参数提交类型 type;参数数据类型 param:{名称 name,参数类型 type:enum{参数拼接 param,路径拼接 path};defaultvalue 测试值}", desc = "服务器资源访问(http)")
public class Http extends StringFormatTask {

    @Override
    public Object run(final View view, String... params) {
        String cfg = params[0].toString();
        String jsonparams = params[1].toString();
        JSONObject valueObject = null;

        Map map = (Map) app.auto.runner.base.JsonUtil.extractJsonRightValue(cfg);
        String url = map.get("url").toString();
        url = url.replaceAll("\\{", "<").replaceAll("\\}", ">").replaceAll("\\[", "<").replaceAll("\\]", ">");
        String type = map.get("type").toString();
        List urlparams = (List) map.get("type");
        final QueryBuilder queryBuilder = QueryBuilder.build(url);
        if (map.get("type").toString().toLowerCase().equals("get")) {
            queryBuilder.setType(QueryBuilder.TYPE.GET);
        } else if (map.get("type").toString().toLowerCase().equals("post")) {
            queryBuilder.setType(QueryBuilder.TYPE.POST);
        }
        for (int i = 0; i < urlparams.size(); i++) {
            Map value = (Map) urlparams.get(i);

            String paramtype = value.get("type").toString();
            String name = value.get("name").toString();
            String defaultvalue = value.get("defaultvalue").toString();
            defaultvalue = (String) BaseTask.buildExp(defaultvalue).withView(view).innerrun();
            if (paramtype.toLowerCase().contains("path")) {
                queryBuilder.setUrl(queryBuilder.getUrl().replaceAll("<" + name + ">", defaultvalue));
            } else {
                queryBuilder.add(name, defaultvalue);
            }

        }
        queryBuilder.exe(new QueryBuilder.Callback() {

            @Override
            public void onSuccess(String result) {
                dialog(url + result);

            }

            private void dialog(String result) {
                final DialogUtil.DialogInfo di = new DialogUtil.DialogInfo(view.getContext());
                di.message = SpannableStringBuilder.valueOf("" + result);
                di.aty = view.getContext();


                di.neutralButtonClickListener = new DialogUtil.DialogClicker() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {

                        super.onClick(dialog, arg1);
                        Context context = view.getContext();
                        share(context, String.valueOf(di.message));
                    }
                };
                di.neutralButtonText = "发送";
                DialogUtil.showNeutralDialog(di, true);
            }

            public void share(Context context, String extraText) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(shareIntent, "分享"));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog(url + ex.getMessage());
            }


            @Override
            public void onDebug(RequestParams rp) {
                String type = "";
                if (queryBuilder.getType() == QueryBuilder.TYPE.POST) {
                    type = "POST";
                } else if (queryBuilder.getType() == QueryBuilder.TYPE.GET) {
                    type = "GET";
                }
                url = type + "  " + (rp.toString().endsWith("?") ? rp.toString().substring(0, rp.toString().length() - 1) : rp.toString()) + "\n\n";

            }

            String url;
        });
        return null;
    }
}
