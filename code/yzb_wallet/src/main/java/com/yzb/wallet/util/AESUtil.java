package com.yzb.wallet.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class AESUtil {
    private static final String ALGORITHM_OPTIONS = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM_KEY = "AES";
    private static final String ALGORITHM_ENCODING = "UTF-8";

    private AESUtil() {
    }

    public static String encrypt(String plainText, String key) {
        String keyValue = null;
        try {

            System.out.println("原文(" + plainText.length() + "):" + plainText);

            SecretKey sk = new SecretKeySpec(HexUtil.toByteArray(key), ALGORITHM_KEY);

            byte[] encodedKey = sk.getEncoded();
            keyValue = HexUtil.toHexString(encodedKey);

            System.out.println("秘钥(" + keyValue.length() + "):" + keyValue);

            Cipher cipher = Cipher.getInstance(ALGORITHM_OPTIONS);
            cipher.init(Cipher.ENCRYPT_MODE, sk);
            keyValue = HexUtil.toHexString(cipher.doFinal(plainText.getBytes(ALGORITHM_ENCODING)));

            System.out.println("密文(" + keyValue.length() + "):" + keyValue);
        } catch (Exception e) {
            keyValue = null;
        }
        return keyValue;
    }

    public static void main(String[] args) {

    }
}