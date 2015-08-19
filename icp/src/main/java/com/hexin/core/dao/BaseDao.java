/*
 * Copyright 2011-2015 10jqka.com.cn All right reserved. This software is the confidential and proprietary information
 * of 10jqka.com.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with 10jqka.com.cn.
 */
package com.hexin.core.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.News;

/**
 * 类BaseDao.java的实现描述：基础方法DAO接口
 * 
 * @author Administrator 2012-12-3 下午6:16:15
 */
public interface BaseDao {

	/**
	 * 执行ddl语句
	 * 
	 * @param sql
	 *            sql语句
	 */
	public void execute(String sql);

	/**
	 * 插入数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            条件参数
	 */
	public int insert(String sql, Object... args);

	/**
	 * 删除数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            条件参数
	 */
	public int delete(String sql, Object... args);

	/**
	 * 更新数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            条件参数
	 */
	public int update(String sql, Object... args);

	/**
	 * 批量更新操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param batchUpdateParams
	 *            批量更新参数
	 * @return
	 */
	public int batchUpdate(String sql, List<Object[]> batchUpdateParams);

	/**
	 * 查询唯一记录
	 * 
	 * @param sql
	 *            sql语句
	 * @param dtoClass
	 *            映射的类
	 * @param args
	 *            条件参数
	 * @return 对应映射类实体
	 */
	public <T> T findUnique(String sql, Class<T> dtoClass, Object... args);

	/**
	 * 查询一个列值
	 * 
	 * @param sql
	 *            select name from t_user
	 * @param objectClass
	 *            非实体对象的class
	 * @param args
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> objectClass,
			Object... args);

	/**
	 * 查询一个实体几个列的值
	 * 
	 * @param sql
	 *            select name,password from t_user
	 * @param args
	 * @return
	 */
	public Map<String, Object> queryMap(String sql, Object... args);

	/**
	 * 查询一个实体几个列值的集合
	 * 
	 * @param sql
	 *            select name,password from t_user
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> queryListMap(String sql, Object... args);

	/**
	 * 获取指定条件数据条数
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Integer getTotalNum(String sql, Object... args);

	/**
	 * 查询记录集
	 * 
	 * @param sql
	 *            sql语句
	 * @param dtoClass
	 *            映射的类
	 * @param args
	 *            条件参数
	 * @return 对应包含映射类实体的记录集
	 */
	public <T> List<T> findList(String sql, Class<T> dtoClass, Object... args);

	/**
	 * 获取JdbcTemplate
	 * 
	 * @return JdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate();

	/**
	 * 查询记录集
	 * 
	 * @param jdbcTemplate
	 *            jdbc操作模板
	 * @param sql
	 *            sql语句
	 * @param dtoClass
	 *            映射的类
	 * @param args
	 *            条件参数
	 * @return 对应包含映射类实体的记录集
	 */
	public <T> List<T> findList(JdbcTemplate jdbcTemplate, String sql,
			Class<T> dtoClass, Object... args);

	/**
	 * 插入记录
	 * 
	 * @param sql
	 * @param args
	 * @return 主键
	 */
	public Integer insertReturnPK(String sql, Map<String, ?> args);

	public <T> List<T> findListWithBlob(String sql, Class<T> dtoClass,
			Object... args) throws SQLException, InstantiationException,
			IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchFieldException, IOException;

	/**
	 * 分页查询
	 * 
	 * @param jdbcTemplate
	 * @param sql
	 * @param currentPage
	 * @param pageSize
	 * @param dtoClass
	 * @param args
	 * @return
	 */
	public <T> Page<T> findPage(String sql, Class<T> dtoClass,
			PageCondition pageCondition, Object... args);

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param class1
	 * @param pageCondition
	 * @param args
	 * @return
	 * @throws IOException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public <T> Page<T> findPageWithBlob(String sql, Class<T> dtoClass,
			PageCondition pageCondition, Object... args) throws SecurityException, IllegalArgumentException, SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException, IOException;

}
