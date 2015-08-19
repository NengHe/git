package com.hexin.icp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hexin.core.constant.Constants;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.Operation;
import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.ResourceTree;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.dao.AdminUserDao;
import com.hexin.icp.dao.RoleDao;
import com.hexin.icp.service.RoleService;
import com.hexin.icp.util.IcpObjectUtil;

@Service
@Transactional(value = "transactionManager")
public class RoleServiceImpl implements RoleService {
	private static final Logger logger = LoggerFactory
			.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao dao;
	@Autowired
	private AdminUserDao adminUserDao;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Role> queryRoleList(String roleName) {
		List<Role> result = null;

		result = dao.queryRoleList(roleName);

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ZTreeDTO> getRoleTree() {
		List<ZTreeDTO> list = dao.queryRoleTree();
		return list;
	}

	@Override
	public int insertRole(Integer parentId, String roleType, String roleName,
			String roleDesc) {
		int affectedRows = dao.insertRole(parentId, roleType, roleName,
				roleDesc);
		return affectedRows;
	}

	@Override
	public ReturnMessage deleteRole(Integer roleId) {
		ReturnMessage rm = new ReturnMessage();

		// 查询该角色是否被用户关联
		List<AdminUser> list = dao.queryRelateUser(roleId);
		if (!CollectionUtils.isEmpty(list)) {
			rm.setCode(Constants.FAILED);
			rm.setMessage(Constants.ROLE_IN_USE);
			return rm;
		}

		// 删除角色权限记录
		dao.deleteRolePermission(roleId);

		// 删除角色
		int affectedRows = dao.deleteRole(roleId);
		rm.setCode(affectedRows + "");

		return rm;
	}

	@Override
	public int updateRole(Integer roleId, Integer parentId, String roleType,
			String roleName, String roleDesc) {
		int affectedRows = dao.updateRole(roleId, parentId, roleType, roleName,
				roleDesc);
		return affectedRows;
	}

	// @Override
	// public List<ResourceTree> queryPermissionTrees(Integer roleId) {
	// List<ResourceTree> newList = null;
	// List<Resource> list = null;
	//
	// // 重新解析结果集
	// list = dao.queryPermissionList(roleId);
	//
	// // 重新解析结果集
	// newList = format4QueryPermissionTrees(list);
	//
	// return newList;
	// }

	// private List<ResourceTree> format4QueryPermissionTrees(List<Resource>
	// list) {
	// if (CollectionUtils.isEmpty(list)) {
	// return null;
	// }
	//
	// Map<Integer, Boolean> map = null;
	// List<ResourceTree> result = null;
	//
	// result = new ArrayList<ResourceTree>();
	// for (Resource dto : list) {
	// Integer resourceId = dto.getResourceId();
	// Integer parentId = dto.getColResourceParentId();
	//
	// if (parentId == null || parentId.equals(0)) {
	// String label = dto.getColResouceLabel();
	// String attrDesc = dto.getColResourceAttrDesc();
	// String permission = dto.getPermission();
	//
	// ResourceTree treeDto = new ResourceTree();
	// treeDto.setResourceId(resourceId);
	// treeDto.setLabel(label);
	// treeDto.setAttrDesc(attrDesc);
	// treeDto.setPermission(permission);
	//
	// treeDto.setChildren(new ArrayList<ResourceTree>());
	// List<ResourceTree> children = treeDto.getChildren();
	// map = new HashMap<Integer, Boolean>();
	// iteFindChildren(resourceId, children, list, map);
	//
	// result.add(treeDto);
	// }
	//
	// }
	//
	// return result;
	// }
	//
	// /**
	// * 遍历加载子资源
	// *
	// * @param parentId
	// * @param parent
	// * @param list
	// */
	// private void iteFindChildren(Integer parentId, List<ResourceTree> parent,
	// List<Resource> list, Map<Integer, Boolean> map) {
	//
	// for (Resource dto : list) {
	// Integer resourceId = dto.getResourceId();
	// Integer dtoParentId = dto.getColResourceParentId();
	// Boolean flag = map.get(resourceId);
	//
	// boolean notAdded = BooleanUtils.isNotTrue(flag);
	// boolean equal = parentId.equals(dtoParentId);
	// if (notAdded && equal) {
	// String label = dto.getColResouceLabel();
	// String attrDesc = dto.getColResourceAttrDesc();
	// String permission = dto.getPermission();
	//
	// ResourceTree treeDto = new ResourceTree();
	// treeDto.setResourceId(resourceId);
	// treeDto.setLabel(label);
	// treeDto.setAttrDesc(attrDesc);
	// treeDto.setPermission(permission);
	//
	// treeDto.setChildren(new ArrayList<ResourceTree>());
	// List<ResourceTree> subChildren = treeDto.getChildren();
	// iteFindChildren(resourceId, subChildren, list, map);
	//
	// parent.add(treeDto);
	// map.put(resourceId, true);
	// }
	// }
	//
	// }

	@Override
	public List<ResourceTree> queryPermissionTrees(Integer curAdminUserId,
			Integer targetRoleId) throws IllegalArgumentException,
			SecurityException, IllegalAccessException, NoSuchFieldException {
		List<ResourceTree> result = null;
		List<Resource> list = null;
		List<Role> curRoleList = null;
		String curChildrenRoleIds = null;
		String targetChildrenRoleIds = null;
		String curRoleIdStr = null;

		curRoleList = adminUserDao.findRolesByAdminUserId(curAdminUserId);
		curRoleIdStr = IcpObjectUtil.listToInStr(curRoleList, Role.class,
				"roleId");

		// 根据roleIdStr查询对应所有的子角色，以自动包含子角色的权限
		curChildrenRoleIds = getAllChildrenRoleIds(curRoleIdStr);

		// 根据roleIdStr查询对应所有的子角色，以自动包含子角色的权限
		targetChildrenRoleIds = getAllChildrenRoleIds(targetRoleId.toString());

		// 根据当前角色id、目标角色id（用于回显）查询资源树
		list = dao.queryRoleAccessResOpList(curChildrenRoleIds,
				targetChildrenRoleIds, targetRoleId);

		result = format4QueryPermissionTrees(list);

		return result;
	}

	private List<ResourceTree> format4QueryPermissionTrees(List<Resource> list) {
		List<ResourceTree> result = null;
		List<Resource> newList = null;

		// 格式化成资源、操作一对多
		newList = format4Operation(list);

		// 格式化资源、子资源一对多
		result = format4ChildrenResource(newList);

		return result;
	}

	/**
	 * 资源、子资源一对多
	 * 
	 * @param newList
	 * @return
	 */
	private List<ResourceTree> format4ChildrenResource(List<Resource> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		;
		List<ResourceTree> result = new ArrayList<ResourceTree>();

		for (Resource dto : list) {
			Integer resourceId = dto.getResourceId();
			Integer parentId = dto.getColResourceParentId();

			if (parentId == null || parentId.equals(0)) {
				String label = dto.getColResouceLabel();
				List<Operation> operationList = dto.getOperationList();

				ResourceTree treeDto = new ResourceTree();
				treeDto.setResourceId(resourceId);
				treeDto.setLabel(label);
				treeDto.setOperationList(operationList);

				List<ResourceTree> children = new ArrayList<ResourceTree>();
				treeDto.setChildren(children);
				iteFindChildren(resourceId, children, list, map);

				result.add(treeDto);
			}

		}

		return result;
	}

	/**
	 * 遍历加载子资源
	 * 
	 * @param parentId
	 * @param parent
	 * @param list
	 */
	private void iteFindChildren(Integer parentId, List<ResourceTree> parent,
			List<Resource> list, Map<Integer, Boolean> map) {

		for (Resource dto : list) {
			Integer resourceId = dto.getResourceId();
			Integer dtoParentId = dto.getColResourceParentId();
			Boolean flag = map.get(resourceId);

			boolean notAdded = BooleanUtils.isNotTrue(flag);
			boolean equal = parentId.equals(dtoParentId);

			if (notAdded && equal) {
				String label = dto.getColResouceLabel();
				List<Operation> operationList = dto.getOperationList();

				ResourceTree treeDto = new ResourceTree();
				treeDto.setResourceId(resourceId);
				treeDto.setLabel(label);
				treeDto.setOperationList(operationList);

				List<ResourceTree> children = new ArrayList<ResourceTree>();
				map = new HashMap<Integer, Boolean>();
				treeDto.setChildren(children);
				iteFindChildren(resourceId, children, list, map);

				parent.add(treeDto);
				map.put(resourceId, true);
			}
		}

	}

	/**
	 * 资源、操作一对多
	 * 
	 * @param list
	 * @return
	 */
	private List<Resource> format4Operation(List<Resource> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		List<Resource> result = new ArrayList<Resource>();

		for (Resource dto : list) {
			Integer resourceId = dto.getResourceId();
			List<Operation> operationList = new ArrayList<Operation>();
			Boolean flag = map.get(resourceId);

			if (BooleanUtils.isTrue(flag)) { // 防止重复
				continue;
			}

			Integer colResourceParentId = dto.getColResourceParentId();
			String colResouceLabel = dto.getColResouceLabel();

			Resource resourceDto = new Resource();
			resourceDto.setResourceId(resourceId);
			resourceDto.setColResourceParentId(colResourceParentId);
			resourceDto.setColResouceLabel(colResouceLabel);
			resourceDto.setOperationList(operationList);

			for (Resource innerDto : list) {
				Integer innerResourceId = innerDto.getResourceId();
				Integer resourceOperationID = innerDto.getResourceOperationID();
				String disabled = innerDto.getDisabled();

				if (resourceId.equals(innerResourceId)
						&& resourceOperationID != null) {
					String operationName = innerDto.getOperationName();
					String isOperationEnabled = innerDto
							.getIsOperationEnabled();

					Operation operation = new Operation();
					operation.setResourceOperationID(resourceOperationID);
					operation.setOperationName(operationName);
					operation.setIsOperationEnabled(isOperationEnabled);
					operation.setDisabled(disabled);

					operationList.add(operation);
				}
			}

			result.add(resourceDto);
			// 标识已添加
			map.put(resourceId, true);
		}

		return result;
	}

	/**
	 * 根据父角色（多个以逗号隔开）获取所有的子角色id(包含parentRoleIdStr自身)
	 * 
	 * @param parentRoleIdStr
	 * @return
	 */
	private String getAllChildrenRoleIds(String parentRoleIdStr) {
		String roleIdStr = null;
		StringBuilder builder = new StringBuilder();

		// 迭代查询当前parentRoleIdStr对应的子角色。直到查询到的子角色为空为止
		iterateQueryChildrenRoleIds(parentRoleIdStr, builder);

		// 去掉最后一个逗号
		if (builder.length() > 0) {
			builder.setLength(builder.length() - 1);
		}

		// 去重
		roleIdStr = removeDuplicateIds(builder);

		return roleIdStr;
	}

	/**
	 * 去掉builder中重复的id
	 * 
	 * @param builder
	 * @return
	 */
	private String removeDuplicateIds(StringBuilder idsBuilder) {
		String result = "";
		String tmpStr = null;
		String[] tmpArr = null;
		Map<String, Boolean> map = new HashMap<String, Boolean>();

		if (idsBuilder == null || idsBuilder.length() <= 0) {
			return null;
		}

		tmpStr = idsBuilder.toString();
		tmpArr = tmpStr.split(",");

		if (tmpArr == null || tmpArr.length <= 0) {
			return null;
		}

		for (String str : tmpArr) {
			Boolean didExists = map.get(str);

			if (BooleanUtils.isTrue(didExists)) {
				continue;
			}

			result += str + ",";
			map.put(str, true);
		}

		// 去掉最后的逗号
		if (StringUtils.isNotEmpty(result)) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	/**
	 * 迭代查询当前parentRoleIdStr对应的子角色。直到查询到的子角色为空为止
	 * 
	 * @param parentRoleIdStr
	 * @param builder
	 */
	private void iterateQueryChildrenRoleIds(String parentRoleIdStr,
			StringBuilder builder) {
		String nextParentIdStr = null;

		if (StringUtils.isEmpty(parentRoleIdStr)) {
			return;
		}

		builder.append(parentRoleIdStr + ","); // 包含当前父角色

		nextParentIdStr = dao.getChildrenRoleIdsByParentIds(parentRoleIdStr);

		if (StringUtils.isNotEmpty(nextParentIdStr)) {

			iterateQueryChildrenRoleIds(nextParentIdStr, builder);
		}

	}

	@Override
	public int allocatePermission(Integer roleId, String operationIds) {
		int affectedRows = 0;
		int affectedDelRows = 0;
		List<Object[]> batchParams = new ArrayList<Object[]>();
		String[] permissionIdArr = null;
		String[] resourceIdArr = null;
		String resourceIds = null;
		String parentResourceIds = null;

		// 1、根据operationIds查询到对应的资源id
		resourceIds = dao.findResourceIdsByOperationIds(operationIds);

		// 2、根据资源id查询所有的父资源（包括自身）
		parentResourceIds = findAllParentResourceIds(resourceIds);

		// 预备关联t_resource记录
		if (StringUtils.isNotEmpty(parentResourceIds)) {
			resourceIdArr = parentResourceIds.split(",");

			for (String resourceId : resourceIdArr) {
				Integer colAccessId = Integer.parseInt(resourceId);
				String colAccessType = "1";

				batchParams.add(new Object[] { roleId, colAccessId,
						colAccessType });
			}
		} else {
			return -1;
		}

		// 预备关联t_resource_operation记录
		if (StringUtils.isNotEmpty(operationIds)) {
			permissionIdArr = operationIds.split(",");

			for (String permissionId : permissionIdArr) {
				Integer colAccessId = Integer.parseInt(permissionId);
				String colAccessType = "2";

				batchParams.add(new Object[] { roleId, colAccessId,
						colAccessType });
			}
		}
		
		// 3、根据角色id删除原有权限关联记录
		affectedDelRows = dao.deleteRoleAccessByRoleId(roleId);
		
		// 4、批量添加角色权限关联记录
		affectedRows = dao.batchInsertRoleAccesses(batchParams);

		return affectedRows;
	}

	/**
	 * 根据资源id查询所有的父资源（包括自身）
	 * 
	 * @param resourceIds
	 * @return
	 */
	private String findAllParentResourceIds(String parentResourceIds) {
		String result = null;
		StringBuilder builder = new StringBuilder();

		// 迭代查询当前parentResourceIds对应的父资源。直到角色的父角色为0或空为止
		iterateQueryParentResourceIds(parentResourceIds, builder);

		// 去掉最后一个逗号
		if (builder.length() > 0) {
			builder.setLength(builder.length() - 1);
		}

		// 去重
		result = removeDuplicateIds(builder);

		return result;
	}

	/**
	 * 迭代查询当前childrenResourceIds对应的父资源。直到角色的父角色为0或空为止
	 * 
	 * @param parentResourceIds
	 * @param builder
	 */
	private void iterateQueryParentResourceIds(String childrenResourceIds,
			StringBuilder builder) {
		String nextChildrenResourceIds = null;

		if (StringUtils.isEmpty(childrenResourceIds)) {
			return;
		}

		builder.append(childrenResourceIds + ","); // 包含当前子资源

		nextChildrenResourceIds = dao
				.findParentResourceIdsByChildrenIds(childrenResourceIds);

		if (StringUtils.isNotEmpty(nextChildrenResourceIds)) {

			iterateQueryParentResourceIds(nextChildrenResourceIds, builder);
		}
	}
}
