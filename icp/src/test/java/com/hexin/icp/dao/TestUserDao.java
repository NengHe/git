package com.hexin.icp.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hexin.icp.bean.AdminUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AdminUserDao dao;

	@Test
	public void jdbcTemplateTest() {
		jdbcTemplate.getDataSource();
	}

	@Test
	public void findWhereUserIdEqualsTest() {
		AdminUser user = dao.getAdminUserByUsername("root");
		System.out.println(user);
	}
}