package com.yzb.card.king.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * App 的文件工具类
 */
public class FileUtils {

    public static void save(String fileName, String content) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        fos.close();
    }

    public static String read(String fileName) throws Exception {
        String result = null;
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        if (file.exists()) {

            FileInputStream sdstream = new FileInputStream(file);
            byte b[] = new byte[1024];
            int n = 0;
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            while ((n = sdstream.read(b)) != -1) {
                byteArrayOS.write(b);
            }
            byte sdContent[] = byteArrayOS.toByteArray();
            result = new String(sdContent);
        }

        return result;
    }

    public static String getCachePath(Context context) {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = context.getExternalCacheDir();
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    /**
     * 通过文件名（带扩展名） 获得完整的下载路径；
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getDownloadPathByFileName(Context context, String fileName) {
        return getCachePath(context) + fileName;
    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static void readTxtFile(String filePath){
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    System.out.println(lineTxt);
                    LogUtil.e("AAAAAA",lineTxt);
                }
                read.close();
            }else{
                LogUtil.e("找不到指定的文件");
            }
        } catch (Exception e) {
            LogUtil.e("读取文件内容出错");
            e.printStackTrace();
        }

    }
}
