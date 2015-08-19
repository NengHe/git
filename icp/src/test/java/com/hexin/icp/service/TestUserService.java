package com.hexin.icp.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestParam;

import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.ZTreeDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserService {

	@Autowired
	private AdminUserService service;
	
	@Test
	public void deleteUSerTest() {
		try {
			Integer userId = 2;
			
			int affectedRows = service.deleteAppUser(userId);
			System.out.println(affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateUSerTest() {
		try {
			Integer userId = 2;
			String username = "userUpdate1";
			String orgId = "1,2";
			String positionId = "1,2";
			String showRequest = "0";
			String showDetailInner = "0";
			String showDetailOutter = "0";
			Integer personId = 8;
			String personName = "张三更新";
			String personMobile = "12345688";
			String personEmail = "update1dw@hh.com";
			String personAddress = "更新浙江清廉洞";
			String personCompany = "更新大唐公司";
			String personJob = "更新经理";
			Integer updateBy = 2;

			int code = service.updateAppUser(userId, username, orgId,
					positionId, showRequest, showDetailInner, showDetailOutter,
					personId, personName, personMobile, personEmail,
					personAddress, personCompany, personJob, updateBy);
			System.out.println(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void insertUSerTest() {
		try {
			String username = "user1";
			String orgId = "1";
			String positionId = "2";
			String showRequest = "1";
			String showDetailInner = "1";
			String showDetailOutter = "0";
			String personName = "张三";
			String personMobile = "123456";
			String personEmail = "1dw@hh.com";
			String personAddress = "浙江清廉洞";
			String personCompany = "大唐公司";
			String personJob = "经理";
			Integer createBy = 1;

			String req = "http://localhost:8080/icp/rs/user/insertUser?username=user2&orgId=1&positionId=&showRequest=1&showDetailInner=1&showDetailOutter=0&personName=姓名&personMobile=13378892403&personEmail=邮箱&personAddress=地址&personCompany=公司&personJob=职位";
			Integer pk = service.insertMember(username, orgId, positionId,
					showRequest, showDetailInner, showDetailOutter, personName,
					personMobile, personEmail, personAddress, personCompany,
					personJob, createBy);
			System.out.println(pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryUsersTest() {

		try {
			List<AdminUser> list = service.queryAppUsers(null);
			for (AdminUser dto : list) {
				System.out.println(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getResourceTreeTest() {
		try {
			List<ZTreeDTO> treeList = service.getResourceTree();
			for (ZTreeDTO dto : treeList) {
				System.out.println(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
