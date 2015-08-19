package com.hexin.icp.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.hexin.core.constant.Constants;
import com.myhexin.core.utils.ObjectUtils;

public class IcpObjectUtil {
	protected static Log logger = LogFactory.getLog(IcpObjectUtil.class);

	/**
	 * 根据regex将sourceStr转成对应clazz类型的数组
	 * 
	 * @param sourceStr
	 * @param regex
	 * @param clazz
	 * @return
	 */
	public static <T> Object[] strToArr(String sourceStr, String regex,
			Class<T> clazz) {
		if (StringUtils.isEmpty(sourceStr) || clazz == null) {
			return null;
		}

		String[] arr = sourceStr.split(regex);
		if (ObjectUtils.isEmpty(arr)) {
			return null;
		}
		if (clazz.getName().equals("java.lang.String")) {
			return arr;
		}

		Object[] result = new Object[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (clazz.getName().equals("java.lang.Integer")) {
				result[i] = Integer.parseInt(arr[i]);
			}
		}

		return result;
	}

	/**
	 * 抽取列表中某个字段值成sql查询中的in字段
	 * 
	 * @param <T>
	 * @param list
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static <T> String listToInStr(List<T> list, Class<T> clazz,
			String fieldName) throws IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchFieldException {
		if (list == null || list.size() <= 0) {
			return "";
		}

		String result = "";

		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		for (Object obj : list) {
			Object valueObject = field.get(obj);
//			if (valueObject.getClass().equals("valueObject")) {
//				result += "'" + valueObject + "',";
//			} else {
				result += valueObject + ",";
//			}
		}
		if (result.length() > 1) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	public static String strToJson(String str) throws Exception {
		String result = null;
		JSONObject jsonObj = null;

		jsonObj = new JSONObject(str);

		result = jsonObj.getString("addRole");

		return result;

	}

	public static void main(String[] args) {

		Date date = new Date();
		System.out.println(date);
		
		long datetime = date.getTime();
		System.out.println(datetime);
		
	    Date newDate = new Date(datetime);
	    System.out.println(newDate);
		
//		String sql = "ferf (:placeHolder) dcew(:placeHolder)  ";
//		String result = replacePlaceHolder(sql,"2,3","hh");
//		System.out.println(result);
		
//		String camel = underlineToCamel("hh_hjcds_w");
//		String underline = camelToUnderline("asBsdfsLde");
//		
//		System.out.println(camel);
//		System.out.println(underline);

		// try {
		// String result =
		// strToJson("{'addRole':'增加角色','removeRole':'删除角色','updateRole':'更新角色','queryRole':'查看角色','allocatePermission':'分配权限'}");
		// System.out.println(result);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// List<User> list = new ArrayList<User>();
		// User user1 = new User();
		// user1.setUserId(1);
		// user1.setUsername("zs");
		// User user2 = new User();
		// user2.setUserId(2);
		// user2.setUsername("ls");
		// list.add(user1);
		// list.add(user2);
		// try {
		// String result = listToInStr(list, User.class, "userId");
		// System.out.println(result);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// Object[] arr = strToArr("2,234", ",", Integer.class);
		// for (Object obj : arr)
		// System.out.println(obj);

		// System.out.println(BooleanUtils.isNotTrue(false));
		// System.out.println(StringUtils.equals(null,""));
		// String a = "dewsd";
		// String b = "dewsd";
		// Map<String,Boolean> map = new HashMap<String,Boolean>();
		// map.put(a, true);
		// System.out.println(BooleanUtils.isTrue(map.get(b)));
		// StringBuilder builder = new StringBuilder();
		// builder.append("dwedr,");
		// builder.deleteCharAt(builder.length() - 1);
		// System.out.println(builder.toString());

		// try {
		// String result = formatDateStr("2015-07-", "yyyy-MM-dd HH:mm:ss",
		// "yyyy-MM-dd");
		// System.out.println(result);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * 按照给定格式 格式化时间字符串
	 * 
	 * @param title
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static String formatDateStr(String dateStr, String fromFormate,
			String toFormat) throws ParseException {
		Date date;
		String result = "";

		if (StringUtils.isEmpty(dateStr)) {
			return result;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(fromFormate);
		date = sdf.parse(dateStr);

		sdf = new SimpleDateFormat(toFormat);
		result = sdf.format(date);

		return result;
	}

	/**
	 * 将sql语句中包含":placeHolder"字符串的全部替换成toReplaceStr的值
	 * 
	 * @param sql
	 * @param toReplaceStr
	 * @return
	 */
	public static String replaceAllPlaceHolder(String sql, String toReplaceStr) {
		String regexStr = ":placeHolder";
		Pattern patt = null;
		Matcher matcher = null;

		patt = Pattern.compile(regexStr);
		matcher = patt.matcher(sql);

		return matcher.replaceAll(toReplaceStr);
	}
	
	/**
	 * 按照顺序将sql语句中包含":placeHolder"字符串的替换成toReplaceStr的值
	 * 
	 * @param sql
	 * @param toReplaceStr
	 * @return
	 */
	public static String replacePlaceHolder(String sql, String... toReplaceStrs) {
		if(toReplaceStrs == null || toReplaceStrs.length <= 0){
			return sql;
		}
		
		String result = null;
		String regexStr = ":placeHolder";
		Pattern patt = null;
		Matcher matcher = null;
		
		patt = Pattern.compile(regexStr);
		result = sql;
		
		for (String arg : toReplaceStrs) {
			
			matcher = patt.matcher(result);
			
			result = matcher.replaceFirst(arg);
		}
		
		return result;
	}

	/**
	 * 将下划线方式命名的字符串转换为驼峰式
	 * 例如：HELLO_WORLD->helloWorld
	 * 
	 * @param name
	 *            转换前的下划线方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String underlineToCamel(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
	 * 例如：HELLO_WORLD->helloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(Constants.UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
