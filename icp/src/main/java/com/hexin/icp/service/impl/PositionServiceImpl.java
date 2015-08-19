package com.hexin.icp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Position;
import com.hexin.icp.dao.PositionDao;
import com.hexin.icp.service.PositionService;

@Service
@Transactional(value = "transactionManager")
public class PositionServiceImpl implements PositionService {

	private static final Logger logger = LoggerFactory
			.getLogger(PositionServiceImpl.class);

	@Autowired
	private PositionDao dao;

	@Override
	public Page<Position> queryPositions(PageCondition pageCondition, String adminUserType, Integer orgId,
			String colPosName) {

		Page<Position> result = null;

		if ("1".equals(adminUserType)) {
			// 机构管理用户只返回本机构
			result = dao.queryPositionsByOrgId(pageCondition, orgId, colPosName);
		} else if ("2".equals(adminUserType)) {
			// 系统管理用户返回所有的机构
			result = dao.queryAllPositions(pageCondition, colPosName);
		}

		return result;
	}

	@Override
	public int createPosition(Integer colPosOrgId, String colPosName,
			Integer colPosShowDetailInner, Integer colPosShowDetailOutter) {

		int affectedRows = 0;

		affectedRows = dao.createPosition(colPosOrgId, colPosName,
				colPosShowDetailInner, colPosShowDetailOutter);

		return affectedRows;
	}

	@Override
	public int updatePosition(Integer positionId, Integer colPosOrgId,
			String colPosName, Integer colPosShowDetailInner,
			Integer colPosShowDetailOutter) {

		int affectedRows = 0;

		affectedRows = dao.updatePosition(positionId, colPosOrgId, colPosName,
				colPosShowDetailInner, colPosShowDetailOutter);

		return affectedRows;
	}

	@Override
	public int removePosition(Integer positionId) {

		int affectedRows = 0;

		affectedRows = dao.removePosition(positionId);

		return affectedRows;
	}

}
