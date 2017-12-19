package com.yzb.wallet.util;

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

import java.io.IOException;
import java.io.InputStream;

//import org.directwebremoting.json.JsonUtil;


/**
 *
 * @author XiaoLe.Hu
 *
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
		     httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,2000);//数据传输时间

			String reqbody = JSON.toJSONString(map);

			//System.out.println(reqbody);
			post.setEntity(new StringEntity(reqbody, "UTF-8"));
			HttpResponse response = httpclient.execute(post);

			int statusCode = response.getStatusLine().getStatusCode();

			if(statusCode != 200) {
				throw new Exception("网络请求应答状态错误：" + statusCode);
			}

			String respbody = EntityUtils.toString(response.getEntity(), "UTF-8");
			if(respbody == null || respbody.equals(""))
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

}
