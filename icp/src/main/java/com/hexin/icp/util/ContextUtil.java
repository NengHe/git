package com.hexin.icp.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ContextUtil {
	
	public static Object getBean(String key){
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
		System.out.println(ac);
		Object value = ac.getBean(key);
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(getBean("dataSource"));
	}
}
