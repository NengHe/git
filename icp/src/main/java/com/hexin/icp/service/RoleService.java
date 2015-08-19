package com.hexin.icp.service;

import java.util.List;

import com.hexin.icp.bean.ResourceTree;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.ZTreeDTO;

public interface RoleService {

	/**
	 * 查询角色列表
	 * 
	 * @param roleName
	 * @return
	 */
	public List<Role> queryRoleList(String roleName);

	/**
	 * 查询角色树
	 */
	public List<ZTreeDTO> getRoleTree();

	/**
	 * 角色树
	 */
	public int insertRole(Integer parentId, String roleType, String roleName,
			String roleDesc);

	/**
	 * 删除角色
	 */
	public ReturnMessage deleteRole(Integer roleId);

	/**
	 * 更新角色
	 * 
	 * @param roleId
	 * @param parentId
	 * @param roleName
	 * @param roleDesc
	 * @return
	 */
	public int updateRole(Integer roleId, Integer parentId, String roleType,
			String roleName, String roleDesc);

//	/**
//	 * 根据角色id查询资源权限树
//	 * 
//	 * @param roleId
//	 * @return
//	 */
//	public List<ResourceTree> queryPermissionTrees(Integer roleId);

	/**
	 * 根据当期登陆用户与目标角色id查询资源权限树
	 * 
	 * @param roleId
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public List<ResourceTree> queryPermissionTrees(Integer curAdminUserId,
			Integer targetRoleId) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException;

	/**
	 * 分配权限
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	public int allocatePermission(Integer roleId, String operationIds);

}
