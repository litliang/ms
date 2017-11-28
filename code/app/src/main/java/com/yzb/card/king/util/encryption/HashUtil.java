package com.yzb.card.king.util.encryption;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Hash加密解密工具类
 */
public class HashUtil {

	public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * 对文件进行加密
	 * @param fileName
	 * @param hashType
	 * @return
	 * @throws Exception
	 */
	public static String getHash(String fileName, String hashType)
			throws Exception {
		InputStream fis;
		fis = new FileInputStream(fileName);
		byte[] buffer = new byte[1024];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return toHexString(md5.digest());
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	/**
	 * 获取加密数据
	 * @param source
	 * @return
	 */
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 获取加密数据
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		byte[] source = str.getBytes();
		return getMD5(source);
	}

	public static String getX(int id) {
		String md5 = HashUtil.getMD5(Integer.toString(id).getBytes());
		return md5.substring(0, 1);
	}

	/**
	 * DES加密操作
	 * DES加密和解密过程中，密钥长度都必须是8的倍数
	 * @param datasource
	 * @param password
	 * @return
	 */
	public static byte[] desCrypto(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES解密操作
	 * DES加密和解密过程中，密钥长度都必须是8的倍数
	 * @param src
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}
	
	
	/**
     * DES算法密钥
     */
    private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128, -65 }; 
    /**
     * 数据加密，算法（DES）
     *
     * @param data
     *            要进行加密的数据
     * @return 加密后的数据
     */
    public static String encryptBasedDes(String data) {
        String encryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            // 加密，并把字节数组编码成字符串
            encryptedData = Base64.encodeToString(cipher.doFinal(data.getBytes()), Base64.DEFAULT);
        } catch (Exception e) {
//            log.error("加密错误，错误信息：", e);
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }
    
    /**
     * 数据解密，算法（DES）
     *
     * @param cryptData
     *            加密数据
     * @return 解密后的数据
     */
    /*public static String decryptBasedDes(String cryptData) {
        String decryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串解码为字节数组，并解密
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
        } catch (Exception e) {
//            log.error("解密错误，错误信息：", e);
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }*/
    
    
    /**
	 * 获取随机码:(生成code)
	 *  
	 * @author BHL
	 * @return
	 */
    public static String genRandomCode(int length,String authCodeType){
		
		String val = "";     
        try{
		    Random random = new Random();     
		    for(int i = 0; i < length; i++){ 
		    	if(authCodeType.equals("str")){
		    		 int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
			         val += (char) (choice + random.nextInt(26));     
		    	}else if(authCodeType.equals("int")){
		    		val += String.valueOf(random.nextInt(10));
		    	}else if(authCodeType.equals("mix")){
		    		String charOrNum = random.nextInt(2) % 2 == 0 ? "str" : "int"; // 输出字母还是数字     
		 	        if("str".equalsIgnoreCase(charOrNum)){     
		 	            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
		 	            val += (char) (choice + random.nextInt(26));     
		 	        }else if("int".equalsIgnoreCase(charOrNum)){     
		 	            val += String.valueOf(random.nextInt(10));     
		 	        } 
		    	} 
		    }   
        }catch(Exception e){
        	val = null;
        }
	    
        if(null != val){
        	val = val.toLowerCase();
        }
	             
	    return val;
	}

    /**
   	 * 数字获取随机码:(生成code)
   	 *  
   	 * @author BHL
   	 * @return
   	 */
       public static String genRandomCode(int length){
   		
   		String val = "";     
           try{
   		    Random random = new Random();     
   		    for(int i = 0; i < length; i++){ 
   		    		val += String.valueOf(random.nextInt(10));
   		    }   
           }catch(Exception e){
           	val = null;
           }
   	    
           if(null != val){
           	val = val.toLowerCase();
           }
   	             
   	    return val;
   	}


}