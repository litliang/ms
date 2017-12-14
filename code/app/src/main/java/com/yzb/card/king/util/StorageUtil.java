package com.yzb.card.king.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Administrator on 2017/11/3.
 */
public class StorageUtil {
    private static Context ctx;
    public static String sys_var_path;
    public static String user_var_path;
    public static String exp_path;
    public static String testpath;
    private static String layoutpath;
    private static String pathid = "";

    public static void init(Context ctx) {
        StorageUtil.ctx = ctx;
//        pathid = "/" + ctx.getPackageName().replaceAll(".", "_");
//        layoutpath = Environment.getExternalStorageDirectory().getAbsolutePath() + pathid +
////                "/" + ctx.getPackageName().replaceAll(".", "_") +
//                "/appconf_layout";
        testpath = Environment.getExternalStorageDirectory().getAbsolutePath() + pathid +
//                pathid +
                "/appconf_test";
//        intfpath = Environment.getExternalStorageDirectory().getAbsolutePath() + pathid +
////                pathid +
//                "/appconf_intfs";
//        intf_instpath = Environment.getExternalStorageDirectory().getAbsolutePath() + pathid +
////                pathid +
//                "/appconf_intfs_inst_path";
//        sys_var_path = Environment.getExternalStorageDirectory().getAbsolutePath() + pathid +
////                pathid +
//                "/sys_var_path";
//        user_var_path = Environment.getExternalStorageDirectory().getAbsolutePath() + pathid +
////                pathid +
//                "/user_var_path";
//        exp_path = Environment.getExternalStorageDirectory().getAbsolutePath() + pathid +
////                pathid +
//                "/exp_path";
//        createFile(layoutpath);
        createFile(testpath);
//        createFile(intfpath);
//        createFile(intf_instpath);
//        createFile(sys_var_path);
//        createFile(user_var_path);
    }

    private static void createFile(String intf_instpath) {
        new File(intf_instpath).getParentFile().mkdirs();
        File file = new File(intf_instpath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String intfpath;

    public static String intf_instpath;

    public String path;

    public StorageUtil(String path) {
        this.path = path;
    }

    public static String getLayoutPath(String path) {
        return layoutpath + "/" + path;
    }

    public void rmKey(String key) {

        Properties p = new Properties();
        try {
            p.load(new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.remove(key);
        try {
            p.store(new FileOutputStream(new File(path)), "add " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addKeyV(String key, String v) {

        Properties p = new Properties();
        try {
            p.load(new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.put(key, v);
        try {
            p.store(new FileOutputStream(new File(path)), "add " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasKey( String key) {

        Properties p = new Properties();
        try {
            p.load(new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean has = p.containsKey(key);
        try {
            p.store(new FileOutputStream(new File(path)), "add " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return has;
    }

    public String getKeyedV(String key) {
        if(key==null){
            return null;
        }

        Properties p = new Properties();
        try {
            p.load(new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result;
        if (p.containsKey(key)) {
            result = p.getProperty(key);
        } else {
            result = "117";
        }
        try {
            p.store(new FileOutputStream(new File(path)), "add " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}