package com.hexin.icp.dao;

import java.util.List;

import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.ResourceTree;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.ZTreeDTO;

public interface RoleDao {

	/**
	 * 查询到所有的角色列表
	 * 
	 * @param roleName
	 * @return
	 */
	public List<Role> queryRoleList(String roleName);

	/**
	 * 查询角色树列表
	 */
	public List<ZTreeDTO> queryRoleTree();

	/**
	 * 插入角色
	 * 
	 * @param parentId
	 * @param roleName
	 * @param roleDesc
	 * @return
	 */
	public int insertRole(Integer parentId, String roleType, String roleName,
			String roleDesc);

	/**
	 * 查询关联用户
	 * 
	 * @param roleId
	 * @return
	 */
	public List<AdminUser> queryRelateUser(Integer roleId);

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRole(Integer roleId);

	/**
	 * 删除角色权限关联记录
	 * 
	 * @param roleId
	 */
	public void deleteRolePermission(Integer roleId);

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

	/**
	 * 查询资源权限列表
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Resource> queryPermissionList(Integer roleId);

	/**
	 * 根据父角色id查询子角色id
	 * @param parentRoleIdStr
	 * @return
	 */
	public String getChildrenRoleIdsByParentIds(String parentRoleIdStr);

	/**
	 * 根据角色id查询关联权限
	 * 
	 * @param roleIdStr
	 * @return
	 */
	public List<Resource> findResourcesByRoleIds(String roleIds);

	/**
	 * 根据当前角色id、目标角色id（用于回显）查询资源树
	 * @param curChildrenRoleIds
	 * @param targetChildrenRoleIds
	 * @param targetRoleId
	 * @return
	 */
	public List<Resource> queryRoleAccessResOpList(String curChildrenRoleIds,
			String targetChildrenRoleIds, Integer targetRoleId);

	/**
	 * 根据operationIds查询到对应的资源id
	 * @param operationIds
	 * @return
	 */
	public String findResourceIdsByOperationIds(String operationIds);

	/**
	 * 根据子资源id查询父资源id(不包括0)
	 * @param childrenResourceIds
	 * @return
	 */
	public String findParentResourceIdsByChildrenIds(String childrenResourceIds);

	/**
	 * 批量添加t_role_access
	 * @param batchParams
	 * @return
	 */
	public int batchInsertRoleAccesses(List<Object[]> batchParams);

	/**
	 * 根据角色id删除权限关联记录
	 * @param roleId
	 * @return
	 */
	public int deleteRoleAccessByRoleId(Integer roleId);

}
