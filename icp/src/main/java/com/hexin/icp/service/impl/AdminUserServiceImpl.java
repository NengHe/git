package com.hexin.icp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.dao.AdminUserDao;
import com.hexin.icp.dao.OrgDao;
import com.hexin.icp.dao.RoleDao;
import com.hexin.icp.service.AdminUserService;
import com.hexin.icp.util.EncryptUtil;
import com.hexin.icp.util.IcpObjectUtil;
import com.myhexin.core.collections.CollectionUtils;

@Service
@Transactional(value = "transactionManager")
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserDao dao;
	@Autowired
	private OrgDao orgDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public AdminUser getUser4Auth(Integer adminUserId) throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException {

		AdminUser user = null;
		List<Role> roleList = null;
		String roleIdStr = null;
		String childrenRoleIds = null;
		List<Resource> permList = null;

		user = dao.getAdminUserByUserId(adminUserId);
		roleList = dao.findRolesByAdminUserId(user.getAdminUserId());

		if (!CollectionUtils.isEmpty(roleList)) {
			// 设置用户角色
			user.setRoleList(roleList);
			roleIdStr = IcpObjectUtil.listToInStr(roleList, Role.class, "roleId");

			// 根据roleIdStr查询对应所有的子角色，以自动包含子角色的权限
			childrenRoleIds = getAllChildrenRoleIds(roleIdStr);

			// permList = dao.findResourcesByRoleIdStr(roleIdStr);
			permList = roleDao.findResourcesByRoleIds(childrenRoleIds);

			// 去掉permission中重复的属性
			// format4findResourcesByRoleIdStr(permList);

			// 设置用户权限
			user.setResourceList(permList);
		}

		return user;
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
	private void iterateQueryChildrenRoleIds(String parentRoleIdStr, StringBuilder builder) {

		String nextParentIdStr = null;

		if (StringUtils.isEmpty(parentRoleIdStr)) {
			return;
		}

		builder.append(parentRoleIdStr + ","); // 包含当前父角色

		nextParentIdStr = roleDao.getChildrenRoleIdsByParentIds(parentRoleIdStr);

		if (StringUtils.isNotEmpty(nextParentIdStr)) {

			iterateQueryChildrenRoleIds(nextParentIdStr, builder);
		}

	}

	// private void format4findResourcesByRoleIdStr(List<Resource> list) {
	// if (CollectionUtils.isEmpty(list)) {
	// return;
	// }
	//
	// for (Resource dto : list) {
	// String permission = dto.getPermission();
	// Object[] permissionArr = IcpObjectUtil.strToArr(permission, ",",
	// String.class);
	// if (permissionArr == null || permissionArr.length <= 0) {
	// continue;
	// }
	//
	// StringBuilder builder = new StringBuilder();
	// Map<String, Boolean> map = new HashMap<String, Boolean>();
	// for (int i1 = 0; i1 < permissionArr.length; i1++) {
	// String str = (String) permissionArr[i1];
	//
	// if (BooleanUtils.isTrue(map.get(str))
	// || StringUtils.isEmpty(str)) {
	// continue;
	// }
	//
	// builder.append(str + ",");
	// map.put(str, true);
	// }
	// // 剪掉最后一个逗号
	// if (builder.length() > 1) {
	// builder.deleteCharAt(builder.length() - 1);
	// }
	//
	// // 去掉permission中重复的属性
	// String resourceName = dto.getColResourceName();
	// String newPermission = resourceName;
	//
	// newPermission += ":" + builder.toString(); //
	// 组装成resource:attr的格式，便于controller层权限拦截
	//
	// dto.setPermission(newPermission);
	// }
	//
	// }

	@Override
	public AdminUser getUser4Login(String username) {

		return dao.getAdminUserByUsername(username);
	}

	@Override
	public List<ZTreeDTO> getMenuByAdminUserId(Integer adminUserId) throws IllegalArgumentException,
			IllegalAccessException, SecurityException,
			NoSuchFieldException {

		List<ZTreeDTO> list = null;
		String childrenRoleIds = null;

		List<Role> roleList = dao.findRolesByAdminUserId(adminUserId);
		if (!CollectionUtils.isEmpty(roleList)) {
			String roleIdStr = IcpObjectUtil.listToInStr(roleList, Role.class, "roleId");

			// 根据roleIdStr查询对应所有的子角色，以自动包含子角色的权限
			childrenRoleIds = getAllChildrenRoleIds(roleIdStr);

			list = dao.findMenuResourcesByRoleIds(childrenRoleIds);

		}

		return list;
	}

	// private void format4getMenuByAdminUserId(List<ZTreeDTO> list) {
	// if (CollectionUtils.isEmpty(list)) {
	// return;
	// }
	//
	// for (ZTreeDTO dto : list) {
	// String permission = dto.getPermission();
	// Object[] permissionArr = IcpObjectUtil.strToArr(permission, ",",
	// String.class);
	// if (permissionArr == null || permissionArr.length <= 0) {
	// return;
	// }
	//
	// StringBuilder builder = new StringBuilder();
	// Map<String, Boolean> map = new HashMap<String, Boolean>();
	// for (int i1 = 0; i1 < permissionArr.length; i1++) {
	// String str = (String) permissionArr[i1];
	//
	// if (BooleanUtils.isTrue(map.get(str))
	// || StringUtils.isEmpty(str)) {
	// continue;
	// }
	//
	// builder.append(str + ",");
	// map.put(str, true);
	// }
	// // 剪掉最后一个逗号
	// if (builder.length() > 1) {
	// builder.deleteCharAt(builder.length() - 1);
	// }
	//
	// // 去掉permission中重复的属性
	// String newPermission = builder.toString();
	// dto.setPermission(newPermission);
	// }
	// }

	@Override
	public Page<AdminUser> queryAdminUsers(PageCondition pageCondition, String type, Integer orgId) {

		Page<AdminUser> result = null;

		if ("1".equals(type)) {
			// 机构管理用户返回所有的管理用户
			result = dao.queryAdminUsersByOrgId(pageCondition, orgId);
		} else if ("2".equals(type)) {
			// 系统管理用户返回所有的管理用户
			result = dao.queryAllAdminUsers(pageCondition);
		}

		return result;
	}

	@Override
	public int insertAdminUser(String colAdminUsername, String colAdminUserPassword, Integer colAdminUserType,
			Integer colOrgId, Integer roleId, Integer createBy, Integer colDelFlag) {

		Integer adminUserId = null;
		int affectedRows = 0;

		// 密码加密
		String originalPwd = String.valueOf(colAdminUserPassword);
		String encryptPwd = EncryptUtil.encrypt(originalPwd);

		// 插入t_admin_user记录
		adminUserId = dao.insertAdminUser(colAdminUsername, encryptPwd, colAdminUserType, colOrgId, createBy,
				colDelFlag);

		// 插入t_admin_user_role关联记录
		affectedRows = dao.insertAdminUserRole(adminUserId, roleId);

		return 1;
	}

	@Override
	public int udpateAdminUser(Integer adminUserId, String colAdminUsername, String colAdminUserPassword,
			Integer colAdminUserType, Integer colOrgId, Integer roleId, Integer updateBy,
			Integer colDelFlag) {

		int affectedUserRows = 0;
		int affectedRelDelRows = 0;
		int affectedRelRows = 0;

		// 密码加密
		String originalPwd = String.valueOf(colAdminUserPassword);
		String encryptPwd = EncryptUtil.encrypt(originalPwd);

		// 插入t_admin_user记录
		affectedUserRows = dao.updateAdminUser(adminUserId, colAdminUsername, encryptPwd, colAdminUserType,
				colOrgId, updateBy, colDelFlag);

		// 根据用户id删除原始角色关联记录
		affectedRelDelRows = dao.deleteAdminUserRoleByAdminUserId(adminUserId);

		// 批量插入t_admin_user_role关联记录
		affectedRelRows = dao.insertAdminUserRole(adminUserId, roleId);

		return 1;
	}

	@Override
	public int removeAdminUser(Integer adminUserId) {

		int affectedUserRows = 0;
		int affectedRelRows = 0;
		int affectedMemRelRows = 0;

		// 插入t_admin_user记录
		affectedUserRows = dao.deleteAdminUser(adminUserId);

		// 根据用户id删除原始角色关联记录
		affectedRelRows = dao.deleteAdminUserRoleByAdminUserId(adminUserId);

		return 1;
	}

	// @Override
	// public List<ZTreeDTO> getResourceTree() throws IllegalArgumentException,
	// SecurityException, IllegalAccessException, NoSuchFieldException {
	// AdminUser curUser = WebUtil.getLoginUser();
	// List<Role> roleList = dao.findRolesByAdminUserId(curUser
	// .getAdminUserId());
	// String roleIdStr = IcpObjectUtil.listToInStr(roleList, Role.class,
	// "roleId");
	//
	// List<ZTreeDTO> resourceList = dao
	// .findPermissionTreeByRoleIdStr(roleIdStr);
	// return resourceList;
	// }
}
