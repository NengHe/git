package com.hexin.icp.dao;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Position;

public interface PositionDao {

	/**
	 * 根据机构查询职位
	 * 
	 * @param orgId
	 * @param colOrgName
	 * @return
	 */
	public Page<Position> queryPositionsByOrgId(PageCondition pageCondition, Integer orgId, String colOrgName);

	/**
	 * 查询所有职位
	 * 
	 * @param colPosName
	 * @return
	 */
	public Page<Position> queryAllPositions(PageCondition pageCondition, String colPosName);

	/**
	 * 创建职位
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
