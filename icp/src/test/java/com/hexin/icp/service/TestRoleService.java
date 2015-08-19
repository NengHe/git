package com.hexin.icp.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.ZTreeDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestRoleService {

	@Autowired
	private RoleService service;

	@Test
	public void queryRoleListTest() {
		try {
			List<Role> list = service.queryRoleList(null);
			for (Role dto : list) {
				System.out.println(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getRoleTreeTest() {
		try {
			List<ZTreeDTO> treeList = service.getRoleTree();
			for (ZTreeDTO dto : treeList) {
				System.out.println(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void insertRoleTest() {
		try {
			int parentId = 1;
			String roleName = "测试";
			String roleDesc = "测试人员专用，他用者死";
			int affectedRows = service.insertRole(parentId, roleName, roleDesc);
			System.out.println(affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
