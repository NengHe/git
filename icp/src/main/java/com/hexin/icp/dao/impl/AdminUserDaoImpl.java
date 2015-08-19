package com.hexin.icp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.dao.AdminUserDao;
import com.hexin.icp.util.IcpObjectUtil;

@Repository
public class AdminUserDaoImpl extends BaseDaoSupport implements AdminUserDao {

	private final String FIND_MENU_RESOURCES_BY_ROLE_IDS = "SELECT id,pId,resourceName,NAME,GROUP_CONCAT(col_res_op_name) AS permission  "
			+ " FROM ("
			+ "		SELECT distinct resource_id AS id,col_resource_parent_id AS pId,col_resource_name AS resourceName,col_resouce_label AS NAME,col_res_op_name,col_resource_sort"
			+ " 	FROM v_role_access_resource_operation  "
			+ " 	WHERE col_role_id IN(:placeHolder) "
			+ " 	ORDER BY col_resource_sort "
			+ " ) t"
			+ " GROUP BY id"
			+ " ORDER BY col_resource_sort";

	@Override
	public AdminUser getAdminUserByUsername(String adminUsername) {

		String sql = "SELECT au.admin_user_id,au.col_admin_username,au.col_admin_user_password,au.col_org_id,au.col_admin_user_type FROM t_admin_user au WHERE au.col_del_flag='0' AND au.col_admin_username = ? ";

		AdminUser user = super.findUnique(sql, AdminUser.class, adminUsername);

		return user;
	}

	@Override
	public List<Resource> findResourcesByRoleIdStr(String roleIdStr) {

		List<Resource> result = null;
		String sql = null;
		String replaceSql = null;

		sql = "SELECT DISTINCT r.resource_id,r.col_resource_parent_id,GROUP_CONCAT(p.permission) AS permission,r.col_resource_name ";
		sql += " FROM t_permission p ";
		sql += " LEFT JOIN t_resource r ON r.resource_id=p.resource_id ";
		sql += " WHERE r.col_del_flag='0' AND p.role_id IN (:placeHolder) ";
		sql += " GROUP BY resource_id ";

		replaceSql = IcpObjectUtil.replaceAllPlaceHolder(sql, roleIdStr);

		result = super.findList(replaceSql, Resource.class);

		return result;
	}

	@Override
	public List<Role> findRolesByAdminUserId(int userId) {

		String sql = "SELECT r.role_id,r.role_name ";
		sql += "  FROM t_admin_user_role ur   ";
		sql += "  LEFT JOIN t_role r ON r.role_id=ur.col_admin_role_id ";
		sql += "  WHERE ur.col_admin_user_id=? ";

		List<Role> list = super.findList(sql, Role.class, userId);

		return list;
	}

	@Override
	public List<ZTreeDTO> findPermissionTreeByRoleIdStr(String roleIdStr) {

		String sql = "SELECT id,pId,CONCAT(NAME,':',IFNULL(permission,'')) AS FILE,label AS NAME ";
		sql += " FROM ( ";
		sql += " 	SELECT DISTINCT r.resource_id AS id,r.parent_id AS pId,r.name,IFNULL(p.permission,'') AS permission,r.label,r.sort ";
		sql += " 	FROM t_permission p ";
		sql += " 	LEFT JOIN t_resource r ON r.resource_id=p.resource_id ";
		sql += " 	WHERE r.del_flag='0' AND p.role_id IN (?) ";
		sql += " 	ORDER BY permission DESC ";
		sql += " ) t ";
		sql += " GROUP BY id ";
		sql += " ORDER BY sort ";

		List<ZTreeDTO> list = super.findList(sql, ZTreeDTO.class, roleIdStr);

		return list;
	}

	@Override
	public AdminUser getAdminUserByUserId(Integer adminUserId) {

		String sql = "SELECT au.admin_user_id,au.col_admin_username,au.col_admin_user_password,au.col_org_id,au.col_admin_user_type FROM t_admin_user au WHERE au.col_del_flag='0' AND au.admin_user_id = ? ";

		AdminUser user = super.findUnique(sql, AdminUser.class, adminUserId);

		return user;
	}

	@Override
	public List<ZTreeDTO> findMenuResourcesByRoleIdStr(String roleIdStr) {

		String sql = "SELECT DISTINCT r.resource_id as id,r.col_resource_parent_id as pId,r.col_resouce_label as name,r.col_resource_name as resourceName,GROUP_CONCAT(p.permission) AS permission ";
		sql += " FROM t_permission p ";
		sql += " LEFT JOIN t_resource r ON r.resource_id=p.resource_id ";
		sql += " WHERE r.col_del_flag='0' AND p.role_id IN (?) ";
		sql += " GROUP BY r.resource_id ";
		sql += " order BY r.col_resource_sort ";

		List<ZTreeDTO> list = super.findList(sql, ZTreeDTO.class, roleIdStr);

		return list;
	}

	@Override
	public Page<AdminUser> queryAllAdminUsers(PageCondition pageCondition) {

		Page<AdminUser> result = null;
		String sql;

		sql = "SELECT au.*,o.col_org_code,o.col_org_name,r.role_id,r.role_name";
		sql += " FROM t_admin_user au ";
		sql += " LEFT JOIN t_org o ON o.org_id=au.col_org_id ";
		sql += " LEFT JOIN t_admin_user_role aur ON aur.col_admin_user_id=au.admin_user_id ";
		sql += " LEFT JOIN t_role r ON r.role_id=aur.col_admin_role_id ";

		result = super.findPage(sql, AdminUser.class, pageCondition);

		return result;
	}

	@Override
	public Page<AdminUser> queryAdminUsersByOrgId(PageCondition pageCondition, Integer orgId) {

		Page<AdminUser> result = null;
		String sql;

		sql = "SELECT au.*,o.col_org_code,o.col_org_name,r.role_id,r.role_name";
		sql += " FROM t_admin_user au ";
		sql += " LEFT JOIN t_org o ON o.org_id=au.col_org_id ";
		sql += " LEFT JOIN t_admin_user_role aur ON aur.col_admin_user_id=au.admin_user_id ";
		sql += " LEFT JOIN t_role r ON r.role_id=aur.col_admin_role_id ";
		sql += " where au.col_org_id=?";

		result = super.findPage(sql, AdminUser.class, pageCondition, orgId);

		return result;
	}

	@Override
	public Integer insertAdminUser(String colAdminUsername,
			String colAdminUserPassword, Integer colAdminUserType,
			Integer colOrgId, Integer createBy, Integer colDelFlag) {

		Integer adminUserId = null;
		String sql;

		sql = "insert into t_admin_user(col_admin_username,col_admin_user_password,col_admin_user_type,col_org_id,create_by,col_del_flag,create_time) "
				+ "values(:col_admin_username,:col_admin_user_password,:col_admin_user_type,:col_org_id,:create_by,:col_del_flag,now())";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("col_admin_username", colAdminUsername);
		params.put("col_admin_user_password", colAdminUserPassword);
		params.put("col_admin_user_type", colAdminUserType);
		params.put("col_org_id", colOrgId);
		params.put("create_by", createBy);
		params.put("col_del_flag", colDelFlag);

		adminUserId = super.insertReturnPK(sql, params);

		return adminUserId;
	}

	@Override
	public int insertAdminUserRole(Integer adminUserId, Integer roleId) {

		int affectedRows = 0;
		String sql;

		sql = "insert into t_admin_user_role(col_admin_user_id,col_admin_role_id,create_time) values(?,?,now())";

		affectedRows = super.insert(sql, adminUserId, roleId);

		return affectedRows;
	}

	@Override
	public int updateAdminUser(Integer adminUserId, String colAdminUsername,
			String colAdminUserPassword, Integer colAdminUserType,
			Integer colOrgId, Integer updateBy, Integer colDelFlag) {

		int affectedRows = 0;
		String sql;

		sql = "update t_admin_user set col_admin_username=?,col_admin_user_password=?,col_admin_user_type=?"
				+ ",col_org_id=?,last_update_by=?,col_del_flag=?,last_update_time=now() where admin_user_id=?";

		affectedRows = super.update(sql, colAdminUsername,
				colAdminUserPassword, colAdminUserType, colOrgId, updateBy,
				colDelFlag, adminUserId);

		return affectedRows;
	}

	@Override
	public int deleteAdminUserRoleByAdminUserId(Integer adminUserId) {

		int affectedRows = 0;
		String sql;

		sql = "delete from t_admin_user_role where col_admin_user_id=?";

		affectedRows = super.delete(sql, adminUserId);

		return affectedRows;
	}

	@Override
	public int deleteAdminUser(Integer adminUserId) {

		int affectedRows = 0;
		String sql;

		sql = "delete from t_admin_user where admin_user_id=?";

		affectedRows = super.delete(sql, adminUserId);

		return affectedRows;
	}

	@Override
	public List<ZTreeDTO> findMenuResourcesByRoleIds(String roleIds) {

		List<ZTreeDTO> result = null;
		String sql = null;

		sql = IcpObjectUtil.replaceAllPlaceHolder(FIND_MENU_RESOURCES_BY_ROLE_IDS,
				roleIds);

		result = super.findList(sql, ZTreeDTO.class);

		return result;
	}

}
