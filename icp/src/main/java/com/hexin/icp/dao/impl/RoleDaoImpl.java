package com.hexin.icp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.dao.RoleDao;
import com.hexin.icp.util.IcpObjectUtil;

@Repository
public class RoleDaoImpl extends BaseDaoSupport implements RoleDao {

	private final String DELETE_ROLE_ACCESS_BY_ROLE_ID = "delete from t_role_access where col_role_id=?";

	private final String INSERT_ROLE_ACCESS = "insert into t_role_access(col_role_id,col_access_id,col_access_type) values(?,?,?)";

	private final String FIND_PARENT_RESOURCEIDS_BY_CHILDRENIDS = "select GROUP_CONCAT(col_resource_parent_id) from t_resource where col_resource_parent_id<>0 and resource_id in(:placeHolder)";

	private final String FIND_RESOURCEIDS_BY_OPERATIONIDS = "select GROUP_CONCAT(col_resource_id) from t_resource_operation where col_resource_operation_id in(:placeHolder)";

	private final String QUERY_ROLE_ACCESS_RES_OP_LIST = "SELECT DISTINCT cur_a.resource_id,cur_a.col_resource_parent_id,cur_a.col_resouce_label,"
			+ " 	cur_a.col_resource_operation_id AS resourceOperationID,cur_a.col_operation_label AS operationName,tar_a.disabled,"
			+ "		IF(cur_a.col_resource_operation_id IS NOT NULL,IF(tar_a.col_resource_operation_id IS NOT NULL,'yes','no') ,'') AS isOperationEnabled"
			+ "	FROM("
			+ " 	SELECT DISTINCT resource_id,col_resource_parent_id,col_resource_name,col_resouce_label,col_resource_operation_id,col_operation_name,col_operation_label"
			+ " 	FROM v_role_access_resource_operation "
			+ "		WHERE col_role_id IN(:placeHolder)"
			+ "		ORDER BY col_resource_sort,col_operation_sort"
			+ "	) cur_a"
			+ "	LEFT JOIN (	"
			+ "		SELECT tar_all.*,"
			+ "			IF(tar_all.col_resource_operation_id IS NOT NULL,IF(tar_parent.col_access_id IS NOT NULL,'false','true') ,'') AS disabled"
			+ "		FROM ("
			+ "			SELECT resource_id,col_resource_parent_id,col_resource_name,col_resouce_label,col_resource_operation_id,col_operation_name,col_operation_label"
			+ "			FROM v_role_access_resource_operation "
			+ "			WHERE col_role_id IN(:placeHolder)"
			+ "		) tar_all"
			+ "		LEFT JOIN ("
			+ "			SELECT col_access_id FROM t_role_access WHERE col_access_type=2 AND col_role_id=?"
			+ "		) tar_parent ON tar_parent.col_access_id=tar_all.col_resource_operation_id"
			+ "	) tar_a ON tar_a.resource_id=cur_a.resource_id AND tar_a.col_resource_operation_id=cur_a.col_resource_operation_id";

	private final String FIND_RESOURCES_BY_ROLE_IDS = "SELECT resource_id,CONCAT(col_resource_name,':',GROUP_CONCAT(col_operation_name)) AS permission  "
			+ " FROM v_role_access_resource_operation  "
			+ " WHERE col_role_id IN(:placeHolder) " + " GROUP BY resource_id ";

	private final String GET_CHILDREN_ROLE_IDS_BY_PARENT_IDS = "SELECT GROUP_CONCAT(role_id) FROM t_role WHERE del_flag='0' and parent_id IN (:placeHolder)  ";

	@Override
	public List<Role> queryRoleList(String roleName) {
		String sql = "SELECT r.role_id AS roleId,r.parent_id AS parentId,pr.role_name AS parentName,r.role_name AS roleName,r.role_desc AS roleDesc,r.role_type as roleType ";
		sql += " FROM t_role r ";
		sql += " LEFT JOIN t_role pr ON pr.role_id=r.parent_id ";
		sql += " where r.del_flag='0' ";

		if (!StringUtils.isEmpty(roleName)) {
			sql += " and r.role_name like '%" + roleName + "%'";
		}

		List<Role> list = super.findList(sql, Role.class);

		return list;
	}

	@Override
	public List<ZTreeDTO> queryRoleTree() {
		String sql = "SELECT r.role_id AS id,ifnull(r.parent_id,0) AS pId,r.role_name AS name FROM t_role r where r.del_flag='0' ";

		List<ZTreeDTO> list = super.findList(sql, ZTreeDTO.class);

		return list;
	}

	@Override
	public int insertRole(Integer parentId, String roleType, String roleName,
			String roleDesc) {
		String sql = "insert into t_role(parent_id,role_type,role_name,role_desc,create_time) values(?,?,?,?,now()) ";

		int affectedRows = super.insert(sql, parentId, roleType, roleName,
				roleDesc);

		return affectedRows;
	}

	@Override
	public List<AdminUser> queryRelateUser(Integer roleId) {
		String sql = "SELECT col_admin_user_id FROM t_admin_user_role WHERE col_admin_user_id=? ";

		List<AdminUser> list = super.findList(sql, AdminUser.class, roleId);

		return list;
	}

	@Override
	public int deleteRole(Integer roleId) {
		String sql = "delete from t_role where role_id=? ";

		int affectedRows = super.delete(sql, roleId);

		return affectedRows;
	}

	@Override
	public void deleteRolePermission(Integer roleId) {
		String sql = "delete from t_permission where role_id=? ";

		super.delete(sql, roleId);
	}

	@Override
	public int updateRole(Integer roleId, Integer parentId, String roleType,
			String roleName, String roleDesc) {
		String sql = "update t_role set parent_id=?,role_type=?,role_name=?,role_desc=? where role_id=? ";

		int affectedRows = super.update(sql, parentId, roleType, roleName,
				roleDesc, roleId);

		return affectedRows;
	}

	@Override
	public List<Resource> queryPermissionList(Integer roleId) {
		List<Resource> result = null;
		String sql = null;

		sql = "SELECT DISTINCT r.resource_id,r.col_resource_parent_id,r.col_resouce_label,r.col_resource_name,GROUP_CONCAT(p.permission) AS permission,r.col_resource_attr_desc ";
		sql += " FROM t_permission p ";
		sql += " LEFT JOIN t_resource r ON r.resource_id=p.resource_id ";
		sql += " WHERE r.col_del_flag='0' and p.role_id=? ";
		sql += " GROUP BY resource_id ";
		sql += " order BY r.col_resource_sort ";

		result = super.findList(sql, Resource.class, roleId);

		return result;
	}

	@Override
	public String getChildrenRoleIdsByParentIds(String parentRoleIdStr) {
		String result = null;
		String sql = "";

		// 替换sql语句中的:placeHolder成对应的值
		sql = IcpObjectUtil.replaceAllPlaceHolder(
				GET_CHILDREN_ROLE_IDS_BY_PARENT_IDS, parentRoleIdStr);

		result = super.queryForObject(sql, String.class);

		return result;
	}

	@Override
	public List<Resource> findResourcesByRoleIds(String roleIds) {
		List<Resource> result = null;
		String sql = null;

		sql = IcpObjectUtil.replaceAllPlaceHolder(FIND_RESOURCES_BY_ROLE_IDS,
				roleIds);

		result = super.findList(sql, Resource.class);

		return result;
	}

	@Override
	public List<Resource> queryRoleAccessResOpList(String curChildrenRoleIds,
			String targetChildrenRoleIds, Integer targetRoleId) {
		List<Resource> result = null;
		String sql = null;

		sql = IcpObjectUtil.replacePlaceHolder(QUERY_ROLE_ACCESS_RES_OP_LIST,
				curChildrenRoleIds, targetChildrenRoleIds);

		result = super.findList(sql, Resource.class, targetRoleId);

		return result;
	}

	@Override
	public String findResourceIdsByOperationIds(String operationIds) {
		String result = null;
		String sql = "";

		// 替换sql语句中的:placeHolder成对应的值
		sql = IcpObjectUtil.replaceAllPlaceHolder(
				FIND_RESOURCEIDS_BY_OPERATIONIDS, operationIds);

		result = super.queryForObject(sql, String.class);

		return result;
	}

	@Override
	public String findParentResourceIdsByChildrenIds(String childrenResourceIds) {
		String result = null;
		String sql = "";

		// 替换sql语句中的:placeHolder成对应的值
		sql = IcpObjectUtil.replaceAllPlaceHolder(
				FIND_PARENT_RESOURCEIDS_BY_CHILDRENIDS, childrenResourceIds);

		result = super.queryForObject(sql, String.class);

		return result;
	}

	@Override
	public int batchInsertRoleAccesses(List<Object[]> batchParams) {
		int affectedRows = 0;

		affectedRows = super.batchUpdate(INSERT_ROLE_ACCESS, batchParams);

		return affectedRows;
	}

	@Override
	public int deleteRoleAccessByRoleId(Integer roleId) {
		int affectedRows = 0;

		affectedRows = super.delete(DELETE_ROLE_ACCESS_BY_ROLE_ID, roleId);

		return affectedRows;
	}
}
