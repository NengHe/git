/*
 * Copyright 2011-2015 10jqka.com.cn All right reserved. This software is the confidential and proprietary information
 * of 10jqka.com.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with 10jqka.com.cn.
 */
package com.hexin.core.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.hexin.core.constant.Constants;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.core.page.PageIndex;
import com.hexin.icp.bean.News;
import com.hexin.icp.util.ContextUtil;
import com.hexin.icp.util.IcpObjectUtil;
import com.hexin.icp.util.InjectValueUtil;

/**
 * 类BaseDaoSupport.java的实现描述：基础方法DAO接口实现
 * 
 * @author Administrator 2012-12-3 下午7:22:42
 */
public class BaseDaoSupport implements BaseDao {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insert(String sql, Object... args) {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		int affectedRows = jdbcTemplate.update(sql, args);

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return affectedRows;
	}

	@Override
	public int delete(String sql, Object... args) {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		int affectedRows = jdbcTemplate.update(sql, args);

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return affectedRows;
	}

	@Override
	public int update(String sql, Object... args) {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		int affectedRows = jdbcTemplate.update(sql, args);

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return affectedRows;
	}

	@Override
	public int batchUpdate(String sql, List<Object[]> batchUpdateParams) {

		int affectedRows = 0;
		int[] affectedRowArr = null;
		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, batchUpdateParams);

		affectedRowArr = jdbcTemplate.batchUpdate(sql, batchUpdateParams);

		// 计算总更新行数
		for (int ar : affectedRowArr) {
			affectedRows += ar;
		}

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return affectedRows;
	}

	private void debugSql(String sql, List<Object[]> batchUpdateParams) {

		if (logger.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("sql: ");
			buffer.append(sql);
			buffer.append(", args: ");
			for (Object[] args : batchUpdateParams) {
				buffer.append("(");
				for (Object arg : args) {
					buffer.append(arg);
					buffer.append(",");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				buffer.append("), ");
			}

			logger.debug(buffer.toString());
		}
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> objectClass,
			Object... args) {

		T result = null;
		try {
			result = jdbcTemplate.queryForObject(sql, args, objectClass);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			logger.debug("The result set is null");
		}
		return result;
	}

	@Override
	public <T> T findUnique(String sql, Class<T> dtoClass, Object... args) {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(dtoClass);

		List<T> list = jdbcTemplate.query(sql, rowMapper, args);
		if (list.isEmpty()) {
			return null;
		}

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return list.get(0);
	}

	@Override
	public <T> List<T> findList(String sql, Class<T> dtoClass, Object... args) {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(dtoClass);

		List<T> result = jdbcTemplate.query(sql, rowMapper, args);

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return result;
	}

	private void debugSql(String sql, Object[] args) {

		if (logger.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("sql: ");
			buffer.append(sql);
			buffer.append(", args: ");
			for (Object arg : args) {
				buffer.append(arg);
				buffer.append(",");
			}
			buffer.deleteCharAt(buffer.length() - 1);

			logger.debug(buffer.toString());
		}
	}

	private void debugSql(String sql, Map<String, ?> args) {

		if (logger.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("sql: ");
			buffer.append(sql);
			buffer.append(", args: ");
			for (Map.Entry<String, ?> entry : args.entrySet()) {
				buffer.append(entry.getValue());
				buffer.append(",");
			}
			buffer.deleteCharAt(buffer.length() - 1);

			logger.debug(buffer.toString());
		}
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {

		return jdbcTemplate;
	}

	@Override
	public <T> List<T> findList(JdbcTemplate jdbcTemplate, String sql,
			Class<T> dtoClass, Object... args) {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(dtoClass);

		List<T> result = jdbcTemplate.query(sql, rowMapper, args);

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return result;
	}

	@Override
	public Map<String, Object> queryMap(String sql, Object... args) {

		return jdbcTemplate.queryForMap(sql, args);
	}

	@Override
	public List<Map<String, Object>> queryListMap(String sql, Object... args) {

		return jdbcTemplate.queryForList(sql, args);
	}

	@Override
	public Integer getTotalNum(String sql, Object... args) {

		return jdbcTemplate.queryForInt(sql, args);
	}

	@Override
	public void execute(String sql) {

		jdbcTemplate.execute(sql);

	}

	@Override
	public Integer insertReturnPK(String sql, Map<String, ?> args) {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		DataSource dataSource = (DataSource) ContextUtil.getBean("dataSource");

		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		SqlParameterSource ps = new MapSqlParameterSource(args);
		KeyHolder holder = new GeneratedKeyHolder();

		namedJdbcTemplate.update(sql, ps, holder);
		Integer pk = holder.getKey().intValue();

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return pk;
	}

	@Override
	public <T> List<T> findListWithBlob(String sql, Class<T> dtoClass,
			Object... args) throws SQLException, InstantiationException,
			IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchFieldException, IOException {

		long startTime = System.currentTimeMillis();
		long endTime;
		long durTime;

		debugSql(sql, args);

		PreparedStatement ps = jdbcTemplate.getDataSource().getConnection()
				.prepareStatement(sql);

		setPreparedStatementParameter(ps, args);

		List<T> list = new ArrayList<T>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

			T obj = dtoClass.newInstance();
			for (int i = 1; i <= colCount; i++) {
				String colName = rsmd.getColumnLabel(i); // 包括别名
				String colTypeName = rsmd.getColumnTypeName(i);
				String beanFiledName = IcpObjectUtil.underlineToCamel(colName);

				if ("blob".equalsIgnoreCase(colTypeName)) {
					InjectValueUtil.setFieldValue(obj, beanFiledName,
							rs.getBlob(i));
				} else {
					InjectValueUtil.setFieldValue(obj, beanFiledName,
							rs.getObject(i));
				}
			}

			list.add(obj);
		}

		endTime = System.currentTimeMillis();
		durTime = endTime - startTime;
		logger.debug("This jdbc operation costs time: " + durTime);

		return list;
	}

	private void setPreparedStatementParameter(PreparedStatement ps,
			Object[] args) throws SQLException {

		if (args == null || args.length <= 0) {
			return;
		}

		for (int i = 0; i < args.length; i++) {
			String clzName = args[i].getClass().getName();
			int psIndex = i + 1;
			
			if (clzName.equals("java.lang.Integer")) {
				ps.setInt(psIndex, (Integer) args[i]);
			} else if (clzName.equals("java.lang.Double")) {
				ps.setDouble(psIndex, (Double) args[i]);
			} else if (clzName.equals("java.lang.Float")) {
				ps.setDouble(psIndex, (Float) args[i]);
			} else if (clzName.equals("java.lang.Long")) {
				ps.setLong(psIndex, (Long) args[i]);
			} else {
				ps.setString(psIndex, (String) args[i]);
			}
		}

	}

	@Override
	public <T> Page<T> findPage(String sql, Class<T> dtoClass,
			PageCondition pageCondition, Object... args) {

		return findMySqlPage(jdbcTemplate, sql, dtoClass, pageCondition, args);
	}

	@Override
	public <T> Page<T> findPageWithBlob(String sql, Class<T> dtoClass,
			PageCondition pageCondition, Object... args) throws SecurityException, IllegalArgumentException, SQLException,
			InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {

		return findMySqlPageWithBlob(jdbcTemplate, sql, dtoClass, pageCondition, args);
	}

	public <T> Page<T> findMySqlPageWithBlob(JdbcTemplate jdbcTemplate, String sql,
			Class<T> dtoClass, PageCondition pageCondition, Object... args) throws SecurityException,
			IllegalArgumentException, SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException,
			IOException {

		Page<T> page = new Page<T>();
		StringBuffer countSqlBuf = new StringBuffer();
		int currentPage = 1;
		int pageSize = 10;
		String camelSortBy = "";
		String underlineSortBy = "";
		String orderBy = "";
		long total;
		long totalPage;
		List<T> resultList = null;

		// 兼容distinct
		countSqlBuf.append("select count(*) from (");
		countSqlBuf.append(StringUtils.substringBeforeLast(sql, "order "));
		countSqlBuf.append(") tmp_table");
		debugSql(countSqlBuf.toString(), args);

		// 查询总记录数目
		total = jdbcTemplate.queryForObject(countSqlBuf.toString(), Long.class,
				args);
		page.setTotal(total);

		StringBuffer pageSqlBuf = new StringBuffer();
		pageSqlBuf.append("select * from (");
		pageSqlBuf.append(sql);
		pageSqlBuf.append(") t ");

		if (pageCondition != null) {
			currentPage = pageCondition.getPage();
			pageSize = pageCondition.getRows();
			camelSortBy = pageCondition.getSort();
			orderBy = pageCondition.getOrder();

			// 将排序字段转换成下划线分割式
			underlineSortBy = IcpObjectUtil.camelToUnderline(camelSortBy);
		}

		if (StringUtils.isNotEmpty(underlineSortBy)
				&& StringUtils.isNotEmpty(orderBy)) {
			pageSqlBuf.append(" order by ");
			pageSqlBuf.append(underlineSortBy).append(" ").append(orderBy)
					.append(" ");
		}
		pageSqlBuf.append(" limit ");
		pageSqlBuf.append((currentPage - 1) * pageSize);
		pageSqlBuf.append(" ,");
		pageSqlBuf.append(pageSize);
		pageSqlBuf.append(" ");

		debugSql(pageSqlBuf.toString(), args);

		resultList = findListWithBlob(pageSqlBuf.toString(), dtoClass, args);

		long mod = total % pageSize;
		if (mod == 0) {
			totalPage = total / pageSize;
		} else {
			totalPage = total / pageSize + 1;
		}

		page.setRows(resultList);
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotalPage(totalPage);
		page.setPageIndex(PageIndex.getPageIndex(Constants.PAGE_RANGE,
				pageSize, totalPage));

		return page;
	}

	public <T> Page<T> findMySqlPage(JdbcTemplate jdbcTemplate, String sql,
			Class<T> dtoClass, PageCondition pageCondition, Object... args) {

		Page<T> page = new Page<T>();
		StringBuffer countSqlBuf = new StringBuffer();
		int currentPage = 1;
		int pageSize = 10;
		String camelSortBy = "";
		String underlineSortBy = "";
		String orderBy = "";
		long total;
		long totalPage;
		List<T> resultList = null;

		// 兼容distinct
		countSqlBuf.append("select count(*) from (");
		countSqlBuf.append(StringUtils.substringBeforeLast(sql, "order "));
		countSqlBuf.append(") tmp_table");
		debugSql(countSqlBuf.toString(), args);

		// 查询总记录数目
		total = jdbcTemplate.queryForObject(countSqlBuf.toString(), Long.class,
				args);
		page.setTotal(total);

		StringBuffer pageSqlBuf = new StringBuffer();
		pageSqlBuf.append("select * from (");
		pageSqlBuf.append(sql);
		pageSqlBuf.append(") t ");

		if (pageCondition != null) {
			currentPage = pageCondition.getPage();
			pageSize = pageCondition.getRows();
			camelSortBy = pageCondition.getSort();
			orderBy = pageCondition.getOrder();

			// 将排序字段转换成下划线分割式
			underlineSortBy = IcpObjectUtil.camelToUnderline(camelSortBy);
		}

		if (StringUtils.isNotEmpty(underlineSortBy)
				&& StringUtils.isNotEmpty(orderBy)) {
			pageSqlBuf.append(" order by ");
			pageSqlBuf.append(underlineSortBy).append(" ").append(orderBy)
					.append(" ");
		}
		pageSqlBuf.append(" limit ");
		pageSqlBuf.append((currentPage - 1) * pageSize);
		pageSqlBuf.append(" ,");
		pageSqlBuf.append(pageSize);
		pageSqlBuf.append(" ");

		debugSql(pageSqlBuf.toString(), args);

		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(dtoClass);
		resultList = jdbcTemplate.query(pageSqlBuf.toString(), rowMapper, args);

		long mod = total % pageSize;
		if (mod == 0) {
			totalPage = total / pageSize;
		} else {
			totalPage = total / pageSize + 1;
		}

		page.setRows(resultList);
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotalPage(totalPage);
		page.setPageIndex(PageIndex.getPageIndex(Constants.PAGE_RANGE,
				pageSize, totalPage));

		return page;
	}

}
