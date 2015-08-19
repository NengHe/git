package com.hexin.icp.dao;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Org;

public interface OrgDao {

	/**
	 * 查询所有的机构
	 * 
	 * @param colOrgName
	 * @return
	 */
	public Page<Org> findAllOrgs(PageCondition pageCondition, String colOrgName);

	/**
	 * 根据机构id查询机构
	 * 
	 * @param orgId
	 * @param colOrgName
	 * @return
	 */
	public Org getOrgByOrgId(Integer orgId, String colOrgName);

	/**
	 * 根据用户id查询机构
	 * 
	 * @param userId
	 * @return
	 */
	public Org getOrgByUserId(Integer userId);

	public Integer createOrg(String colOrgType, String colOrgCode,
			String colOrgName, String colOrgNote, String colOrgIntro,
			String colOrgTel, String colOrgFax, String colOrgAddress,
			String colOrgLatitude, String colOrgLongitude,
			Integer colOrgShowMemberFlag);

	public int updateOrg(Integer orgId, String colOrgType, String colOrgCode,
			String colOrgName, String colOrgNote, String colOrgIntro,
			String colOrgTel, String colOrgFax, String colOrgAddress,
			String colOrgLatitude, String colOrgLongitude,
			Integer colOrgShowMemberFlag);

	/**
	 * 创建机构聊天群记录
	 * @param newOrgId
	 * @param colOrgName
	 * @param adminUserId
	 */
	public int createOrgGroup(Integer newOrgId, String colOrgName,
			Integer adminUserId);

}
