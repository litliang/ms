package app.auto.runner.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 * Created by Administrator on 2017/11/22.
 */
public class Util {

    public static List<String> getClassName(Context ctx, String packageName, List<String> list, boolean b) {
        List<String> classNameList = list;
        Enumeration<String> enumeration = null;
        try {
            String name = ctx.getPackageCodePath();

            File f = new File(name);
            File[] files = f.getParentFile().listFiles();
            for (File a : files) {
                if(a.isDirectory()){
                    continue;
                }
                DexFile df = new DexFile(a);//通过DexFile查找当前的APK中可执行文件
                enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
                while (enumeration.hasMoreElements()) {//遍历
                    String className = (String) enumeration.nextElement();

                    if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                        if (!className.contains("$")) {
                            if (b) {
                                classNameList.add(className);

                            } else {
                                String pkg = className.substring(0, className.lastIndexOf("."));
                                className = className.substring(className.lastIndexOf(".") + 1);
                                if (!packageName.equals(pkg)) {

                                } else {
                                    classNameList.add(className);
                                }
                            }
                        }
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return classNameList;
    }

}
