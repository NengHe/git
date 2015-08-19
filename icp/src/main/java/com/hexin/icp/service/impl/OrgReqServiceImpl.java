package com.hexin.icp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexin.core.constant.Constants;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.app.exceptions.ICPIMException;
import com.hexin.icp.bean.Group;
import com.hexin.icp.bean.OrgMember;
import com.hexin.icp.bean.OrgReq;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.dao.OrgMemberDao;
import com.hexin.icp.dao.OrgReqDao;
import com.hexin.icp.service.OrgReqService;
import com.hexin.icp.util.ImUtil;

@Service
@Transactional(value = "transactionManager")
public class OrgReqServiceImpl implements OrgReqService {

	@Autowired
	private OrgReqDao dao;
	@Autowired
	private OrgMemberDao orgMemberDao;

	@Override
	public Page<OrgReq> queryOrgReqs(PageCondition pageCondition, Integer adminUserId, String adminUserType, Integer orgId,
			String colUserName) {

		Page<OrgReq> result = null;

		if ("1".equals(adminUserType)) {
			// 机构管理用户只返回本机构的入会申请
			result = dao.findOrgReqsByOrgId(pageCondition, orgId, colUserName);
		} else if ("2".equals(adminUserType)) {
			// 系统管理用户返回所有的机构的入会申请
			result = dao.findAllOrgReqs(pageCondition, colUserName);
		}

		return result;
	}

	@Override
	public ReturnMessage auditPass(Integer adminUserId, Integer orgId, Integer orgJoinReqId) throws ICPIMException {

		ReturnMessage rm = new ReturnMessage();

		OrgReq orgRq = null;
		OrgMember orgMember = null;
		Integer appUserId = null;
		Integer reqOrgId = null;
		String colReqAuthStatus = null;
		Group group = null;
		Integer groupId = null;
		int affectedReqRows;
		int affectedOrgMemberRows;
		int affectedGroupMemberRows;

		// 根据orgJoinReqId查询t_org_join_req记录
		orgRq = dao.getOrgReqById(orgJoinReqId);
		if (orgRq != null) {
			appUserId = orgRq.getColReqUserId();
			reqOrgId = orgRq.getColOrgId();
		}

		orgMember = orgMemberDao.getOrgMembersByOrgIdAndUserId(reqOrgId, appUserId);

		// 验证该用户尚未加入该机构
		if (orgMember != null) {
			rm.setCode(Constants.ORG_REQ_VERI_ALREADY_JOINED);
			return rm;
		}

		// 更新t_org_join_req记录col_req_auth_status=1（已通过）
		colReqAuthStatus = "1";
		affectedReqRows = dao.updateOrgReqAuthStatus(orgJoinReqId, colReqAuthStatus);

		// 添加t_org_members记录
		affectedOrgMemberRows = orgMemberDao.insetOrgMembers(reqOrgId, appUserId, null, null, null);

		// 根据机构id查询对应的groupId
		group = orgMemberDao.getGroupIdByOrgId(orgId);
		if (group != null) {
			groupId = group.getGroupId();

			// 添加机构聊天群成员管来呢记录
			affectedGroupMemberRows = orgMemberDao.insertOrgGroupMember(groupId, appUserId);

			// 将当前成员加入到IM聊天群当中
			ImUtil.joinToGroup(group, appUserId);
		}

		return rm;
	}

	@Override
	public ReturnMessage auditReject(Integer adminUserId, Integer orgId, Integer orgJoinReqId) {

		ReturnMessage rm = new ReturnMessage();

		OrgReq orgRq = null;
		OrgMember orgMember = null;
		Integer appUserId = null;
		Integer reqOrgId = null;
		String colReqAuthStatus = null;
		int affectedReqRows;

		// 根据orgJoinReqId查询t_org_join_req记录
		orgRq = dao.getOrgReqById(orgJoinReqId);
		if (orgRq != null) {
			appUserId = orgRq.getColReqUserId();
			reqOrgId = orgRq.getColOrgId();
		}

		orgMember = orgMemberDao.getOrgMembersByOrgIdAndUserId(reqOrgId, appUserId);

		// 验证该用户尚未加入该机构
		if (orgMember != null) {
			rm.setCode(Constants.ORG_REQ_VERI_ALREADY_JOINED);
			return rm;
		}

		// 更新t_org_join_req记录col_req_auth_status=2（已拒绝）
		colReqAuthStatus = "2";
		affectedReqRows = dao.updateOrgReqAuthStatus(orgJoinReqId, colReqAuthStatus);

		return rm;
	}

	@Override
	public int removeOrgReq(Integer adminUserId, Integer orgId, Integer orgJoinReqId) {

		int affectedReqRows;

		// 更新t_org_join_req记录col_req_auth_status=2（已拒绝）
		affectedReqRows = dao.deleteOrgReqby(orgJoinReqId);

		return affectedReqRows;
	}
}
