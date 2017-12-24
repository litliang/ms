package app.auto.runner.base.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;


import com.aliang.auto.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import app.auto.runner.base.MapViewConf;
import app.auto.runner.base.RRes;
import app.auto.runner.base.framework.Init;
import dalvik.system.DexFile;

public class ViewInflater extends LayoutInflater {
    Context newContext;
    private boolean scale;

    public static final String packageName = "app.auto.runner.base.ui.";
    public static final Map<String, String> uiNames = new TreeMap<String, String>();

    static {
        uiNames.put("WebView", "android.webkit.");
        uiNames.put("CircleImageView", "android.support.v4.widget.");
        uiNames.put("ImageView", app.auto.runner.base.widget.ImageView.class.getPackage().getName() + ".");
//        uiNames.put("RoundImageView", RoundImageView.class.getPackage().getName() + ".");
        uiNames.put("WheelView", "top.smartsport.www.wheelview.");


//        uiNames.put("IjkPlayerView", "com.dl7.player.media.");
    }

    Set<String> layoutlog = new TreeSet<String>();

    @Override
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {

        View view = super.onCreateView(parent, name, attrs);
        if (parent != null && parent.getTag() != null) {
            if (!layoutlog.contains(parent.toString())) {
                layoutlog.add(parent.toString());
                parent.setOnClickListener(new ClickAction());
                fakeDataAdapterView(attrs, parent);
            }

        }
        return view;
    }

    static {
        scan();
    }

    public static void scan() {
        String path;
        try {
            path = Init.bigContext.getPackageManager().getApplicationInfo(
                    Init.bigContext.getPackageName(), 0).sourceDir;

            DexFile dexfile = new DexFile(path);
            Enumeration entries = dexfile.entries();
            while (entries.hasMoreElements()) {
                String name = (String) entries.nextElement();
                if (name.startsWith(packageName)) {

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ViewInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        this.newContext = newContext;
        this.scale = true;
        // TODO Auto-generated constructor stub

    }

    public static ViewInflater from(Context newContext) {
        return new ViewInflater(newContext);
    }

    public ViewInflater(Context newContext) {
        this(newContext, true);
    }

    public ViewInflater(Context newContext, boolean scale) {
        this(LayoutInflater.from(newContext), newContext);
        this.scale = scale;

    }

    @Override
    public LayoutInflater cloneInContext(Context arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public static ViewInflater fromAty(Context ctx) {
        return new ViewInflater(ctx, true);
    }

    public static ViewInflater fromAty(Context ctx, boolean scale) {
        return new ViewInflater(ctx, scale);
    }

    @Override
    protected View onCreateView(String name, AttributeSet attrs)
            throws ClassNotFoundException {
        // TODO Auto-generated method stub
        View view = null;
        try {

            if (uiNames.containsKey(name)) {
                try {
                    view = (View) Class.forName(uiNames.get(name) + name)
                            .getConstructor(Context.class, AttributeSet.class)
                            .newInstance(newContext, attrs);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (name.equals("View") || name.contains(".")) {
                view = super.onCreateView(name, attrs);
            } else {
                view = super.createView(name, "android.widget.", attrs);
            }
            attributeset_from_view.put(view, attrs);
            if (view.getTag() != null) {
                if (view instanceof AdapterView) {
                    ((AdapterView) view).setOnItemClickListener(new ItemClickAction());
                } else {
                    view.setOnClickListener(new ClickAction());
                }
            }
            AutoUtils.autoSize(view);
            addTagAction(attrs, view);
            fakeDataAdapterView(attrs, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }

    public View cloneView(View view) {
        try {
            return view.getClass().getConstructor(Context.class, AttributeSet.class).newInstance(newContext, getAttributeSet_From_View().get(view));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;


    }

    public Map<View, AttributeSet> getAttributeSet_From_View() {
        return attributeset_from_view;
    }

    Map<View, AttributeSet> attributeset_from_view = new HashMap<View, AttributeSet>();

    private void addTagAction(AttributeSet attrs, View view) {
        try {

            final String tag = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "tag");
            if (!tag.toLowerCase().contains("onclick")) {
                view.setOnClickListener(new View.OnClickListener() {

                    public Actions actions;

                    @Override
                    public void onClick(View v) {
                        if (actions == null) {
                            actions = Actions.withView(v).parse(tag);
                        }
                        actions.action();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fakeDataAdapterView(AttributeSet attrs, View view) {
        try {
            if (view instanceof AdapterView) {
                String tag = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "tag");

                if (tag != null) {
                    List list = new ArrayList();
                    tag = tag.replaceAll(" ", "");
                    if (tag.contains("fake:")) {
                        int fake = tag.indexOf("fake:");
                        fake += 5;
                        int fakeend = -1;
                        if (tag.contains(";")) {
                            fakeend = tag.indexOf(";", fake);

                        } else {
                            fakeend = tag.length();
                        }
                        String content = tag.substring(fake, fakeend);
                        content = content.replaceAll(" ", "");
                        content = content.replaceAll("\\[\\[", "[").replaceAll("\\]\\]", "]");
                        String[] contentAry = content.replaceAll("\\],", "]").split("\\]");
                        for (String s : contentAry) {
                            if (s.equals("")) {
                                continue;
                            }
                            List alist = new ArrayList();
                            s = s.replaceAll("\\[", "");
                            String[] dataary = s.split(",");
                            for (String d : dataary) {
                                alist.add(d);
                            }
                            list.add(alist);
                        }

                    }
                    int layoutid;
                    if (tag.contains("layout:")) {
                        int layout = tag.indexOf("layout:");
                        layout += 7;
                        int layoutend = -1;

                        if (tag.substring(layout).contains(";")) {
                            layoutend = tag.indexOf(";", layout);

                        } else {
                            layoutend = tag.length();
                        }
                        String content = tag.substring(layout, layoutend);
                        content = content.replaceAll(" ", "");
                        layoutid = RRes.get("R.layout." + content).getAndroidValue();
                    } else {
                        layoutid = R.layout.auto_string_item;
                    }
                    MapViewConf.with(newContext).conf(MapViewConf.with(newContext).source(layoutid)).source(list, view).toView();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
