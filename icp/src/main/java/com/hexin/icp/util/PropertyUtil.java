package com.hexin.icp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	private static Properties props = new Properties();

	/**
	 * 通过属性名提取属性值.
	 * 
	 * @param propertyName
	 *            属性名.
	 */
	public static String getCommonProperty(String propertyName) {
		props = loadProperties("common.properties");
		return props.getProperty(propertyName);
	}

	/**
	 * 载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的载入. 文件路径使用Spring Resource格式,
	 * 文件编码使用UTF-8.
	 * 
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... resourcesPaths) {

		for (String location : resourcesPaths) {
			InputStream in = null;
			try {
				logger.debug("Loading properties file from:" + location);

				in = PropertyUtil.class.getClassLoader().getResourceAsStream(
						location);
				props.load(in);
			} catch (IOException ex) {
				logger.error("Could not load properties from classpath:"
						+ location + ": " + ex.getMessage());
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return props;
	}

}
