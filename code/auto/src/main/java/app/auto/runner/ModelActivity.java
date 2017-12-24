package app.auto.runner;

import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import app.auto.runner.base.RRes;
import app.auto.runner.base.action.Actions;
import app.auto.runner.base.action.ViewInflater;

/**
 * Created by Administrator on 2017/10/26.
 */
public class ModelActivity extends ActivityBase {

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("layout");

        setContentView(new ViewInflater(this).inflate(RRes.get("R.layout." + name).getAndroidValue(),null));

        loadAct();
        init();

    }

    private void init() {
    }

    //解析动作
    //解析 onclick onlongclick
    public void loadAct() {
        try {
            InputStream is = getAssets().open(name + ".act");

            InputStreamReader rder = new InputStreamReader(is, "utf-8");
            Properties p = new Properties();
            p.load(rder);
            Set treeSet = new TreeSet(p.keySet());
            for (Object k : treeSet) {
                String key = k.toString();
                final String value = p.getProperty(k.toString());

                final View view = getView(RRes.get("R.id." + key).getAndroidValue());
                if (view == null) {
                    continue;
                }
                if (key.toLowerCase().contains(".onclick")) {
                    view.setOnClickListener(new View.OnClickListener() {

                        public Actions actions;

                        @Override
                        public void onClick(View v) {
                            if (actions == null) {
                                actions = Actions.withView(v).parse(value);
                            }
                            actions.action();
                        }
                    });
                } else if (key.toLowerCase().contains(".onlongclick")) {
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        public Actions actions;

                        @Override
                        public boolean onLongClick(View v) {
                            if (actions == null) {
                                actions = Actions.withView(v).parse(value);
                            }
                            actions.action();
                            return false;
                        }


                    });
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
