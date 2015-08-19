package com.hexin.icp.dao;

import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.ZTreeDTO;

public interface AdminUserDao {

	/**
	 * Returns one row from the t_user table that match the criteria
	 * 'adminUsername = :username'.
	 */
	public AdminUser getAdminUserByUsername(String adminUsername);

	/**
	 * Returns one row from the t_user table that match the criteria
	 * 'adminUserId = :username'.
	 */
	public AdminUser getAdminUserByUserId(Integer adminUserId);

	/**
	 * 根据用户id查询关联角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<Role> findRolesByAdminUserId(int adminUserId);

	/**
	 * 根据角色id查询关联权限
	 * 
	 * @param roleIdStr
	 * @return
	 */
	public List<Resource> findResourcesByRoleIdStr(String roleIdStr);

	/**
	 * 根据角色id查询关联权限树
	 * 
	 * @param roleIdStr
	 * @return
	 */
	public List<ZTreeDTO> findPermissionTreeByRoleIdStr(String roleIdStr);

	/**
	 * 查询菜单资源
	 * 
	 * @param roleIdStr
	 * @return
	 */
	public List<ZTreeDTO> findMenuResourcesByRoleIdStr(String roleIdStr);

	/**
	 * 查询管理员列表
	 * 
	 * @param pageCondition
	 * 
	 * @return
	 */
	public Page<AdminUser> queryAllAdminUsers(PageCondition pageCondition);

	/**
	 * 根据机构id查询管理员用户列表
	 * 
	 * @param pageCondition
	 * 
	 * @param orgId
	 * @return
	 */
	public Page<AdminUser> queryAdminUsersByOrgId(PageCondition pageCondition, Integer orgId);

	/**
	 * 插入管理用户记录
	 * 
	 * @param colAdminUsername
	 * @param colAdminUserPassword
	 * @param colAdminUserType
	 * @param colOrgId
	 * @param createBy
	 * @param colDelFlag
	 * @return
	 */
	public Integer insertAdminUser(String colAdminUsername,
			String colAdminUserPassword, Integer colAdminUserType,
			Integer colOrgId, Integer createBy, Integer colDelFlag);

	/**
	 * 插入管理用户角色关联记录
	 * 
	 * @param adminUserId
	 * @param roleId
	 * @return
	 */
	public int insertAdminUserRole(Integer adminUserId, Integer roleId);

	/**
	 * 更新管理用户记录
	 * 
	 * @param adminUserId
	 * @param colAdminUsername
	 * @param colAdminUserPassword
	 * @param colAdminUserType
	 * @param colOrgId
	 * @param updateBy
	 * @param colDelFlag
	 * @return
	 */
	public int updateAdminUser(Integer adminUserId, String colAdminUsername,
			String colAdminUserPassword, Integer colAdminUserType,
			Integer colOrgId, Integer updateBy, Integer colDelFlag);

	/**
	 * 删除管理用户角色关联记录
	 * 
	 * @param adminUserId
	 * @return
	 */
	public int deleteAdminUserRoleByAdminUserId(Integer adminUserId);

	/**
	 * 删除管理用户
	 * 
	 * @param adminUserId
	 * @return
	 */
	public int deleteAdminUser(Integer adminUserId);

	/**
	 * 根据角色id获取所有菜单资源
	 * 
	 * @param childrenRoleIds
	 * @return
	 */
	public List<ZTreeDTO> findMenuResourcesByRoleIds(String roleIds);

}
