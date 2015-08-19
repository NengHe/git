package com.hexin.icp.service;

import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.ZTreeDTO;

public interface AdminUserService {

	/**
	 * 查询用户角色、权限
	 * 
	 * @param userName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public AdminUser getUser4Auth(Integer adminUserId)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException;

	/**
	 * 根据登陆用户获取用户
	 * 
	 * @param username
	 * @return
	 */
	public AdminUser getUser4Login(String username);

	/**
	 * 根据用户id查询菜单树
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public List<ZTreeDTO> getMenuByAdminUserId(Integer adminUserId)
			throws IllegalArgumentException, IllegalAccessException,
			SecurityException, NoSuchFieldException;

	/**
	 * 查询管理员列表
	 * 
	 * @param pageCondition
	 * 
	 * @param type
	 * @param orgId
	 * @return
	 */
	public Page<AdminUser> queryAdminUsers(PageCondition pageCondition, String type, Integer orgId);

	/**
	 * 创建管理员用户
	 * 
	 * @param colAdminUsername
	 * @param colAdminUserPassword
	 * @param colOrgId
	 * @param roleId
	 * @param createBy
	 * @param colDelFlag
	 * @return
	 */
	public int insertAdminUser(String colAdminUsername,
			String colAdminUserPassword, Integer colAdminUserType,
			Integer colOrgId, Integer roleId, Integer createBy,
			Integer colDelFlag);

	/**
	 * 更新管理用户记录
	 * 
	 * @param adminUserId
	 * @param colAdminUsername
	 * @param colAdminUserPassword
	 * @param colAdminUserType
	 * @param colOrgId
	 * @param roleId
	 * @param updateBy
	 * @param colDelFlag
	 * @return
	 */
	public int udpateAdminUser(Integer adminUserId, String colAdminUsername,
			String colAdminUserPassword, Integer colAdminUserType,
			Integer colOrgId, Integer roleId, Integer updateBy,
			Integer colDelFlag);

	/**
	 * 删除管理用户
	 * 
	 * @param adminUserId
	 * @return
	 */
	public int removeAdminUser(Integer adminUserId);

	// /**
	// * 根据登录用户查询资源树
	// *
	// * @return
	// * @throws NoSuchFieldException
	// * @throws IllegalAccessException
	// * @throws SecurityException
	// * @throws IllegalArgumentException
	// */
	// public List<ZTreeDTO> getResourceTree() throws IllegalArgumentException,
	// SecurityException, IllegalAccessException, NoSuchFieldException;

	// /**
	// * 查询用户列表
	// */
	// public List<User> queryUsers(String username);
	//
	// /**
	// * 插入用户
	// *
	// * @param username
	// * @param orgId
	// * @param positionId
	// * @param showRequest
	// * @param showDetailInner
	// * @param showDetailOutter
	// * @param personName
	// * @param personMobile
	// * @param personEmail
	// * @param personAddress
	// * @param personCompany
	// * @return
	// */
	// public int insertUser(String username, String orgId, String positionId,
	// String showRequest, String showDetailInner,
	// String showDetailOutter, String personName, String personMobile,
	// String personEmail, String personAddress, String personCompany,
	// String personJob, Integer createBy);
	//
	// /**
	// * 更新用户信息学
	// *
	// * @param userId
	// * @param username
	// * @param orgId
	// * @param positionId
	// * @param showRequest
	// * @param showDetailInner
	// * @param showDetailOutter
	// * @param personName
	// * @param personMobile
	// * @param personEmail
	// * @param personAddress
	// * @param personCompany
	// * @param personJob
	// * @param updateBy
	// * @return
	// */
	// public int updateUser(Integer userId, String username, String orgId,
	// String positionId, String showRequest, String showDetailInner,
	// String showDetailOutter, Integer personId, String personName,
	// String personMobile, String personEmail, String personAddress,
	// String personCompany, String personJob, Integer updateBy);
	//
	// /**
	// * 删除用户（软删除）
	// * @param userId
	// * @return
	// */
	// public int deleteUser(Integer userId);

}
