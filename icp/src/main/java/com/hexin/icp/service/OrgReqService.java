package com.hexin.icp.service;

import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.app.exceptions.ICPIMException;
import com.hexin.icp.bean.OrgReq;
import com.hexin.icp.bean.ReturnMessage;

public interface OrgReqService {

	/**
	 * 查询申请入会列表
	 * @param pageCondition 
	 * @param adminUserId
	 * @param adminUserType
	 * @param orgId
	 * @param colUserName
	 * @return
	 */
	public Page<OrgReq> queryOrgReqs(PageCondition pageCondition, Integer adminUserId, String adminUserType,
			Integer orgId, String colUserName);

	/**
	 * 通过入会申请
	 * @param adminUserId
	 * @param orgId
	 * @param orgJoinReqId
	 * @return
	 * @throws ICPIMException 
	 */
	public ReturnMessage auditPass(Integer adminUserId, Integer orgId,
			Integer orgJoinReqId) throws ICPIMException;

	/**
	 * 审核不通过
	 * @param adminUserId
	 * @param orgId
	 * @param orgJoinReqId
	 * @return
	 */
	public ReturnMessage auditReject(Integer adminUserId, Integer orgId,
			Integer orgJoinReqId);

	/**
	 * 删除入会申请
	 * @param adminUserId
	 * @param orgId
	 * @param orgJoinReqId
	 * @return
	 */
	public int removeOrgReq(Integer adminUserId, Integer orgId,
			Integer orgJoinReqId);

}
