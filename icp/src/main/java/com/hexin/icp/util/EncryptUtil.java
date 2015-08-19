package com.hexin.icp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.Sha384Hash;

import com.myhexin.core.utils.MD5HashUtil;

public class EncryptUtil {

    protected static Log logger = LogFactory.getLog(EncryptUtil.class);

    public static String encrypt(String data) {
        if (data == null || data.trim().length() <= 0) {
            return null;
        }

        String sha384Hex = new Sha384Hash(data).toBase64();

        return sha384Hex;
    }

    @SuppressWarnings("finally")
    public static String encryptWithMD5(String str) {
        String result = null;
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();

            for (int i = 0; i < byteArray.length; i++) {
                int tmp = 0xFF & byteArray[i];

                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(tmp));
                } else {
                    md5StrBuff.append(Integer.toHexString(tmp));
                }
            }

            result = md5StrBuff.toString();

        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return result;
        }

    }
    
    // base64加密  
    @SuppressWarnings("restriction")
	public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // base64解密  
    @SuppressWarnings("restriction")
	public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    public static void main(String[] args) {
        System.out.println(encrypt("1"));
        System.out.println(encrypt("c4ca4238a0b923820dcc509a6f75849b"));
        System.out.println(encryptWithMD5("1"));
        System.out.println(encryptWithMD5("123456"));
        System.out.println(encryptWithMD5("c4ca4238a0b923820dcc509a6f75849b"));
    }

}
