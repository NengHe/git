package com.hexin.icp.dao;

import org.springframework.web.multipart.MultipartFile;

import com.hexin.icp.bean.Img;

public interface ImgDao {

	/**
	 * 创建图片记录
	 * @param orgImgUrl
	 * @param compressImgUrl
	 * @return
	 */
	public Integer createImg(String originalImgUrl, String compressImgUrl);

	/**
	 * 创建图片关联记录
	 * @param imgId
	 * @param newsId
	 * @param colRelType
	 * @return
	 */
	public int createImgRel(Integer imgId, Integer relId, String colRelType);

	/**
	 * 查询图片记录
	 * @param newsTitleImgId
	 * @return
	 */
	public Img getImgByImgId(Integer newsTitleImgId);

	/**
	 * 更新图片记录
	 * @param newsTitleImgId
	 * @param originalImgUrl
	 * @param compressImgUrl
	 * @return
	 */
	public int updateImg(Integer imgId, String originalImgUrl,
			String compressImgUrl);

}
