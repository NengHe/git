package com.hexin.icp.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Position;
import com.hexin.icp.dao.PositionDao;

@Repository
public class PositionDaoImpl extends BaseDaoSupport implements PositionDao {

	@Override
	public Page<Position> queryPositionsByOrgId(PageCondition pageCondition, Integer orgId, String colPosName) {

		String sql = "select p.*,o.col_org_code,o.col_org_name  ";
		sql += " from t_position p ";
		sql += " left join t_org o on o.org_id=p.col_pos_org_id ";
		sql += " where p.col_pos_org_id=? ";

		if (StringUtils.isNotEmpty(colPosName)) {
			sql += " and p.col_pos_name like '%" + colPosName + "%'";
		}

		Page<Position> result = super.findPage(sql, Position.class, pageCondition, orgId);

		return result;
	}

	@Override
	public Page<Position> queryAllPositions(PageCondition pageCondition, String colPosName) {

		String sql = "select p.*,o.col_org_code,o.col_org_name  ";
		sql += " from t_position p ";
		sql += " left join t_org o on o.org_id=p.col_pos_org_id ";
		sql += " where 1=1 ";

		if (StringUtils.isNotEmpty(colPosName)) {
			sql += " and p.col_pos_name like '%" + colPosName + "%'";
		}

		Page<Position> result = super.findPage(sql, Position.class, pageCondition);

		return result;
	}

	@Override
	public int createPosition(Integer colPosOrgId, String colPosName,
			Integer colPosShowDetailInner, Integer colPosShowDetailOutter) {

		int affectedRows = 0;
		String sql = null;

		sql = "insert into t_position(col_pos_org_id,col_pos_name,col_pos_show_detail_inner,col_pos_show_detail_outter,create_time) values(?,?,?,?,now())";

		affectedRows = super.insert(sql, colPosOrgId, colPosName,
				colPosShowDetailInner, colPosShowDetailOutter);

		return affectedRows;
	}

	@Override
	public int updatePosition(Integer positionId, Integer colPosOrgId,
			String colPosName, Integer colPosShowDetailInner,
			Integer colPosShowDetailOutter) {

		int affectedRows = 0;
		String sql = null;

		sql = "update t_position set col_pos_org_id=?,col_pos_name=?,col_pos_show_detail_inner=?,col_pos_show_detail_outter=? where position_id=?";

		affectedRows = super.update(sql, colPosOrgId, colPosName,
				colPosShowDetailInner, colPosShowDetailOutter, positionId);

		return affectedRows;
	}

	@Override
	public int removePosition(Integer positionId) {

		int affectedRows = 0;
		String sql = null;

		sql = "delete from t_position where position_id=?";

		affectedRows = super.delete(sql, positionId);

		return affectedRows;
	}

}
