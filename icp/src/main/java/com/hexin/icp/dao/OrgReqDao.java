package com.hexin.icp.dao;

import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.OrgReq;

public interface OrgReqDao {

	/**
	 * 查询所有机构的入会申请
	 * 
	 * @param pageCondition
	 * @param colUserName
	 * @return
	 */
	public Page<OrgReq> findAllOrgReqs(PageCondition pageCondition, String colUserName);

	/**
	 * 根据机构查询入会申请
	 * 
	 * @param pageCondition
	 * @param colUserName
	 * @return
	 */
	public Page<OrgReq> findOrgReqsByOrgId(PageCondition pageCondition, Integer orgId, String colUserName);

	/**
	 * 查询入会申请记录
	 * 
	 * @param orgJoinReqId
	 * @return
	 */
	public OrgReq getOrgReqById(Integer orgJoinReqId);

	/**
	 * 更新t_org_join_req记录审核状态
	 * 
	 * @param orgJoinReqId
	 * @param colReqAuthStatus
	 * @return
	 */
	public int updateOrgReqAuthStatus(Integer orgJoinReqId,
			String colReqAuthStatus);

	/**
	 * 删除入会申请记录
	 * 
	 * @param orgJoinReqId
	 * @return
	 */
	public int deleteOrgReqby(Integer orgJoinReqId);

}
