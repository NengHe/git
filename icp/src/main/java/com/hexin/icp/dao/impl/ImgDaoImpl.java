package com.hexin.icp.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.icp.bean.Img;
import com.hexin.icp.dao.ImgDao;

@Repository
public class ImgDaoImpl extends BaseDaoSupport implements ImgDao {

	@Override
	public Integer createImg(String originalImgUrl, String compressImgUrl) {
		String sql = "insert into t_img(col_img_path,col_img_compress_path,col_create_time) values(:col_img_path,:col_img_compress_path,now())";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("col_img_path", originalImgUrl);
		params.put("col_img_compress_path", compressImgUrl);

		Integer imgId = super.insertReturnPK(sql, params);

		return imgId;
	}

	@Override
	public int createImgRel(Integer imgId, Integer relId, String colRelType) {
		String sql = "insert into t_img_rel(col_img_id,col_rel_id,col_rel_type,create_time) values(?,?,?,now())";

		int affectedRows = super.insert(sql, imgId, relId, colRelType);

		return affectedRows;
	}

	@Override
	public Img getImgByImgId(Integer imgId) {
		String sql = "select * from t_img where img_id=?";

		Img img = super.findUnique(sql, Img.class, imgId);

		return img;
	}

	@Override
	public int updateImg(Integer imgId, String originalImgUrl,
			String compressImgUrl) {
		String sql = "update t_img set col_img_path=?,col_img_compress_path=? where img_id=?";

		int affctedRwos = super.update(sql, originalImgUrl, compressImgUrl,
				imgId);

		return affctedRwos;
	}
}
