package com.yzb.wallet.sys;

import android.os.Build;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

//import org.directwebremoting.json.JsonUtil;


/**
 * @author XiaoLe.Hu
 */
public class HttpClientUtil {

    /**
     * 以JSON格式提交请求,将响应的JSON串转化为指定的对象并返回
     *
     * @param url
     * @param map
     * @param valueType
     * @return
     */
    public static <T> T postJsonBody(String url, Object map, Class<T> valueType) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try {
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json;charset=UTF-8");
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);//连接时间
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);//数据传输时间

            String reqbody = JSON.toJSONString(map);

            //System.out.println(reqbody);
            post.setEntity(new StringEntity(reqbody, "UTF-8"));
            HttpResponse response = httpclient.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                throw new Exception("网络请求应答状态错误：" + statusCode);
            }

            String respbody = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (respbody == null || respbody.equals(""))
                return null;
            return JSON.parseObject(respbody, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream invokeGet(String url) {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet get = new HttpGet(url);

        try {
            HttpResponse response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T requestByPost(String urlpath, Object map, Class<T> valueType) throws IOException {
        String str = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }

        URL url = new URL(urlpath);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setConnectTimeout(300000);
        conn.setReadTimeout(300000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
        //需要设置 gzip的请求头 才可以获取Content-Encoding响应码
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.connect();
        String urlEncodedRequestStr = URLEncoder.encode(JSON.toJSONString(map), "utf-8");
        //String requestStr = "jsonStr=" + urlEncodedRequestStr;
        String requestStr = JSON.toJSONString(map);
        conn.getOutputStream().write(requestStr.getBytes("utf-8"));
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        //获取所有响应头字段
        /*Map<String, List<String>> map2 = conn.getHeaderFields();
        if (null != map2) {
            for (String key : map2.keySet()) {
                System.out.println(key + "--->" + map2.get(key));
            }
        }*/

        String content_encode = conn.getContentEncoding();
        System.out.println("content_encode:" + content_encode);
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode:" + responseCode);
        if (responseCode != 200) {
            try {
                throw new Exception("网络请求应答状态错误：" + responseCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //如果是gzip的压缩流 进行解压缩处理
        if (null != content_encode && !"".equals(content_encode) && content_encode.equals("gzip")) {
            GZIPInputStream in = new GZIPInputStream(conn.getInputStream());
            if (in == null) {
                return null;
            }
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, len);
            }
            in.close();
            arrayOutputStream.close();
            conn.disconnect();
            str = new String(arrayOutputStream.toByteArray(), "utf-8");
        } else {
            //正常流处理
            InputStream in = conn.getInputStream();
            if (in == null) {
                return null;
            }
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, len);
            }
            in.close();
            arrayOutputStream.close();
            conn.disconnect();
            str = new String(arrayOutputStream.toByteArray(), "utf-8");
        }
        return JSON.parseObject(str, valueType);
    }

}
