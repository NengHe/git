package com.hexin.icp.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.OrgReq;
import com.hexin.icp.dao.OrgReqDao;

@Repository
public class OrgReqDaoImpl extends BaseDaoSupport implements OrgReqDao {

	@Override
	public Page<OrgReq> findAllOrgReqs(PageCondition pageCondition, String colUserName) {

		String sql = "SELECT ojr.*,o.col_org_code,o.col_org_name,au.col_user_name,au.col_user_company,au.col_user_email,au.col_user_mobile,au.col_user_address ";
		sql += " FROM t_org_join_req ojr";
		sql += " LEFT JOIN t_org o ON o.org_id=ojr.col_org_id";
		sql += " LEFT JOIN t_app_user au ON au.user_id=ojr.col_req_user_id";
		sql += " where 1=1";

		if (StringUtils.isNotEmpty(colUserName)) {
			sql += " and o.col_org_name like '%" + colUserName + "%'";
		}

		Page<OrgReq> result = super.findPage(sql, OrgReq.class, pageCondition);

		return result;
	}

	@Override
	public Page<OrgReq> findOrgReqsByOrgId(PageCondition pageCondition, Integer orgId, String colUserName) {

		String sql = "SELECT ojr.*,o.col_org_code,o.col_org_name,au.col_user_name,au.col_user_company,au.col_user_email,au.col_user_mobile,au.col_user_address ";
		sql += " FROM t_org_join_req ojr";
		sql += " LEFT JOIN t_org o ON o.org_id=ojr.col_org_id";
		sql += " LEFT JOIN t_app_user au ON au.user_id=ojr.col_req_user_id";
		sql += " where ojr.col_org_id=?";

		if (StringUtils.isNotEmpty(colUserName)) {
			sql += " and o.col_org_name like '%" + colUserName + "%'";
		}

		Page<OrgReq> result = super.findPage(sql, OrgReq.class, pageCondition, orgId);

		return result;
	}

	@Override
	public OrgReq getOrgReqById(Integer orgJoinReqId) {

		OrgReq result = null;
		String sql = null;

		sql = "select * from t_org_join_req where org_join_req_id=?";

		result = super.findUnique(sql, OrgReq.class, orgJoinReqId);

		return result;
	}

	@Override
	public int updateOrgReqAuthStatus(Integer orgJoinReqId,
			String colReqAuthStatus) {

		int affectedRows = 0;
		String sql = null;

		sql = "update t_org_join_req set col_req_auth_status=? where org_join_req_id=?";

		affectedRows = super.insert(sql, colReqAuthStatus, orgJoinReqId);

		return affectedRows;
	}

	@Override
	public int deleteOrgReqby(Integer orgJoinReqId) {

		int affectedRows = 0;
		String sql = null;

		sql = "delete from  t_org_join_req where org_join_req_id=?";

		affectedRows = super.delete(sql, orgJoinReqId);

		return affectedRows;
	}

}
