package com.hexin.icp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InjectValueUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(InjectValueUtil.class);
	
	public static final String DEFAULT_BOLOB_CHAR_SET = "utf8";

	public static <T> void setFieldValue(T obj, String filedName,
			Object fieldValue) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		if (obj == null) {
			return;

		}

		Class clz = obj.getClass();
		Field field = null;
		try {
			field = clz.getDeclaredField(filedName);
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (java.lang.NoSuchFieldException e) {
			if(logger.isDebugEnabled()){
				logger.debug(e.getMessage(), e);
			}
			return ;
		}
	}

	public static <T> void setFieldValue(T obj, String filedName,
			Blob fieldValue) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException, SQLException, IOException {
		if (obj == null) {
			return;

		}

		Class clz = obj.getClass();
		Field field = clz.getDeclaredField(filedName);
		field.setAccessible(true);

		byte[] returnBytes = fieldValue.getBytes(1, (int) fieldValue.length());
		String value = new String(returnBytes,DEFAULT_BOLOB_CHAR_SET);
		
		field.set(obj, value);
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			String cnt = new String(line.getBytes(),"utf-8");
			sb.append(line + "/n");
		}

		is.close();

		String result = sb.toString();
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new Date(1435556847000l));
		System.out.println(IcpObjectUtil.underlineToCamel("HELLO_WORLD"));
		
		try {
			String a = "d2f23鑷不宸�imgsrc=";
			String b;
			b = new String(a.getBytes("utf8"),"gbk");
			System.out.println(b);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
