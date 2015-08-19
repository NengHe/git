package com.hexin.icp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Org;
import com.hexin.icp.dao.OrgDao;

@Repository
public class OrgDaoImpl extends BaseDaoSupport implements OrgDao {

	private final String CREATE_ORG_GROUP = "INSERT INTO t_org_group(col_org_group_org_id,col_org_group_name,col_org_group_create_user_id) values(?,?,?)";

	@Override
	public Page<Org> findAllOrgs(PageCondition pageCondition, String colOrgName) {
		Page<Org> page = null;
		String sql = null;

		sql = "select o.*,i.col_img_path,i.img_id,i.col_img_compress_path,d.col_dict_text as colOrgTypeName ";
		sql += " from t_org o";
		sql += " left join t_img_rel ir on ir.col_rel_type=2 and ir.col_rel_id=o.org_id";
		sql += " left join t_img i on i.img_id=ir.col_img_id";
		sql += " left join t_dict d on d.col_dict_class='1' and d.col_dict_val=o.col_org_type";
		sql += " where 1=1";

		if (StringUtils.isNotEmpty(colOrgName)) {
			sql += " and o.col_org_name like '%" + colOrgName + "%'";
		}

		// List<Org> list = super.findList(sql, Org.class);
		page = super.findPage(sql, Org.class, pageCondition);

		return page;
	}

	@Override
	public Org getOrgByOrgId(Integer orgId, String colOrgName) {
		String sql = "select o.*,i.col_img_path,i.img_id,i.col_img_compress_path,d.col_dict_text as colOrgTypeName ";
		sql += " from t_org o";
		sql += " left join t_img_rel ir on ir.col_rel_type=2 and ir.col_rel_id=o.org_id";
		sql += " left join t_img i on i.img_id=ir.col_img_id";
		sql += " left join t_dict d on d.col_dict_class='1' and d.col_dict_val=o.col_org_type";
		sql += " where o.org_id=? ";

		if (StringUtils.isNotEmpty(colOrgName)) {
			sql += " and o.col_org_name like '%" + colOrgName + "%'";
		}

		Org org = super.findUnique(sql, Org.class, orgId);

		return org;
	}

	@Override
	public Org getOrgByUserId(Integer userId) {
		String sql = "SELECT o.org_id AS orgId,o.org_code AS orgCode,o.org_name AS orgName ";
		sql += " FROM t_user_position up ";
		sql += " LEFT JOIN t_position po ON po.position_id=up.position_id  ";
		sql += " LEFT JOIN t_org o ON o.org_id=po.org_id ";
		sql += " WHERE up.user_id=? ";
		sql += " LIMIT 1 ";

		Org org = super.findUnique(sql, Org.class, userId);

		return org;
	}

	@Override
	public Integer createOrg(String colOrgType, String colOrgCode,
			String colOrgName, String colOrgNote, String colOrgIntro,
			String colOrgTel, String colOrgFax, String colOrgAddress,
			String colOrgLatitude, String colOrgLongitude,
			Integer colOrgShowMemberFlag) {
		String sql = "insert into t_org(col_org_type,col_org_code, col_org_name, col_org_note,col_org_intro,col_org_tel,col_org_fax,col_org_address,col_org_latitude,col_org_longitude,col_org_show_member_flag,create_time) "
				+ "values(:col_org_type,:col_org_code, :col_org_name, :col_org_note,:col_org_intro,:col_org_tel,:col_org_fax,:col_org_address,:col_org_latitude,:col_org_longitude,:col_org_show_member_flag,now())";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("col_org_type", colOrgType);
		map.put("col_org_code", colOrgCode);
		map.put("col_org_name", colOrgName);
		map.put("col_org_note", colOrgNote);
		map.put("col_org_intro", colOrgIntro);
		map.put("col_org_tel", colOrgTel);
		map.put("col_org_fax", colOrgFax);
		map.put("col_org_address", colOrgAddress);
		map.put("col_org_latitude", colOrgLatitude);
		map.put("col_org_longitude", colOrgLongitude);
		map.put("col_org_show_member_flag", colOrgShowMemberFlag);

		Integer orgId = super.insertReturnPK(sql, map);

		return orgId;
	}

	@Override
	public int updateOrg(Integer orgId, String colOrgType, String colOrgCode,
			String colOrgName, String colOrgNote, String colOrgIntro,
			String colOrgTel, String colOrgFax, String colOrgAddress,
			String colOrgLatitude, String colOrgLongitude,
			Integer colOrgShowMemberFlag) {
		String sql = "update t_org set col_org_type=?, col_org_code=?, col_org_name=?, col_org_note=?,col_org_intro=?,col_org_tel=?,col_org_fax=?,col_org_address=?,col_org_latitude=?,col_org_longitude=?,col_org_show_member_flag=? where org_id=? ";

		int affectedRows = super.update(sql, colOrgType, colOrgCode,
				colOrgName, colOrgNote, colOrgIntro, colOrgTel, colOrgFax,
				colOrgAddress, colOrgLatitude, colOrgLongitude,
				colOrgShowMemberFlag, orgId);

		return affectedRows;
	}

	@Override
	public int createOrgGroup(Integer newOrgId, String colOrgName,
			Integer adminUserId) {
		int affectedRows = 0;

		affectedRows = super.insert(CREATE_ORG_GROUP, newOrgId, colOrgName,
				adminUserId);

		return affectedRows;

	}

}
