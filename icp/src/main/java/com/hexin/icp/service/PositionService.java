package com.hexin.icp.service;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Position;

public interface PositionService {

	/**
	 * 查询职位
	 * 
	 * @param pageCondition
	 * @param adminUserType
	 * @param orgId
	 * @param colOrgName
	 * @return
	 */
	public Page<Position> queryPositions(PageCondition pageCondition, String adminUserType, Integer orgId, String colPosName);

	/**
	 * 创建机构职位
	 * 
	 * @param colPosOrgId
	 * @param colPosName
	 * @param colPosShowDetailInner
	 * @param colPosShowDetailOutter
	 * @return
	 */
	public int createPosition(Integer colPosOrgId, String colPosName,
			Integer colPosShowDetailInner, Integer colPosShowDetailOutter);

	/**
	 * 更新机构职位
	 * 
	 * @param positionId
	 * @param colPosOrgId
	 * @param colPosName
	 * @param colPosShowDetailInner
	 * @param colPosShowDetailOutter
	 * @return
	 */
	public int updatePosition(Integer positionId, Integer colPosOrgId,
			String colPosName, Integer colPosShowDetailInner,
			Integer colPosShowDetailOutter);

	/**
	 * 删除机构职位
	 * 
	 * @param positionId
	 * @return
	 */
	public int removePosition(Integer positionId);

}
