package com.hexin.icp.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.app.exceptions.ICPIMException;
import com.hexin.icp.bean.Img;
import com.hexin.icp.bean.Org;
import com.hexin.icp.dao.ImgDao;
import com.hexin.icp.dao.OrgDao;
import com.hexin.icp.service.OrgService;
import com.hexin.icp.util.FileUtil;
import com.hexin.icp.util.ImUtil;

@Service
@Transactional(value = "transactionManager")
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao dao;
	@Autowired
	private ImgDao imgDao;

	@Override
	public Page<Org> queryOrgs(PageCondition pageCondition, Integer adminUserId, String adminUserType, Integer orgId,
			String colOrgName) {

		Page<Org> page = null;

		if ("1".equals(adminUserType)) {
			page = new Page<Org>();
			// 机构管理用户只返回本机构
			Org result = dao.getOrgByOrgId(orgId, colOrgName);
			if (result != null){
			    List<Org> list = new ArrayList<Org>();
			    list.add(result);
			    page.setRows(list);
			}
		} else if ("2".equals(adminUserType)) {
			// 系统管理用户返回所有的机构
			page = dao.findAllOrgs(pageCondition, colOrgName);
		}

		return page;
	}

	@Override
	public int createOrg(String colOrgType, String colOrgCode, String colOrgName, MultipartFile orgImg,
			String colOrgNote, String colOrgIntro, String colOrgTel, String colOrgFax,
			String colOrgAddress, String colOrgLatitude, String colOrgLongitude,
			Integer colOrgShowMemberFlag, Integer adminUserId) throws FileNotFoundException,
			NoSuchAlgorithmException, IOException,
			MyException, ICPIMException {

		Img newImg = null;
		String newImgPath = null;
		String newCompressImgPath = null;
		Integer newImgId = null;
		Integer newOrgId = null;
		Integer newOrgGroupId = null;
		String newOrgGroupName = colOrgName;
		int affectedRelRows;

		newOrgId = dao.createOrg(colOrgType, colOrgCode, colOrgName, colOrgNote, colOrgIntro, colOrgTel, colOrgFax,
				colOrgAddress, colOrgLatitude, colOrgLongitude, colOrgShowMemberFlag);

		// 上传并更新图片记录
		if (orgImg != null && !orgImg.isEmpty()) {
			// 上传头像
			newImg = FileUtil.uploadOriginalAndCompress(orgImg);

			if (newImg != null) { // 创建图片记录
				// 创建图片记录
				newImgPath = newImg.getColImgPath();
				newCompressImgPath = newImg.getColImgCompressPath();
				newImgId = imgDao.createImg(newImgPath, newCompressImgPath);

				// 创建图片关联记录
				String colRelType = "2";
				affectedRelRows = imgDao.createImgRel(newImgId, newOrgId, colRelType);
			}
		}

		// 创建机构聊天群记录
		newOrgGroupId = dao.createOrgGroup(newOrgId, newOrgGroupName, adminUserId);

		// 创建聊天群到IM
		ImUtil.createGroup(newOrgGroupId, newOrgGroupName);

		return 1;
	}

	@Override
	public int updateOrg(Integer orgId, String colOrgType, String colOrgCode, String colOrgName, Integer imgId,
			MultipartFile orgImg, String colOrgNote, String colOrgIntro, String colOrgTel,
			String colOrgFax, String colOrgAddress, String colOrgLatitude, String colOrgLongitude,
			Integer colOrgShowMemberFlag) throws FileNotFoundException, NoSuchAlgorithmException,
			IOException, MyException {

		Img oldImg = null;
		Img newImg = null;
		String oldImgPath = null;
		String oldCompressImgPath = null;
		String newImgPath = null;
		String newCompressImgPath = null;
		Integer newImgId = null;
		int affectedOrgRows;
		int affectedImgRows;
		int affectedRelRows;

		// 上传并更新图片记录
		if (orgImg != null && !orgImg.isEmpty()) {
			// 上传头像
			newImg = FileUtil.uploadOriginalAndCompress(orgImg);

			if (newImg != null && imgId != null) { // 更新图片记录
				// 更新图片记录
				newImgPath = newImg.getColImgPath();
				newCompressImgPath = newImg.getColImgCompressPath();
				affectedImgRows = imgDao.updateImg(imgId, newImgPath, newCompressImgPath);

				// 删除原图片文件
				oldImg = imgDao.getImgByImgId(imgId);
				oldImgPath = oldImg.getColImgPath();
				oldCompressImgPath = oldImg.getColImgCompressPath();
				FileUtil.deleteRemoteFile(oldImgPath, oldCompressImgPath);

			} else if (newImg != null && oldImg == null) { // 创建图片记录
				// 创建图片记录
				newImgPath = newImg.getColImgPath();
				newCompressImgPath = newImg.getColImgCompressPath();
				newImgId = imgDao.createImg(newImgPath, newCompressImgPath);

				// 创建图片关联记录
				String colRelType = "2";
				affectedRelRows = imgDao.createImgRel(newImgId, orgId, colRelType);
			}
		}

		// 更新机构记录
		affectedOrgRows = dao.updateOrg(orgId, colOrgType, colOrgCode, colOrgName, colOrgNote, colOrgIntro, colOrgTel,
				colOrgFax, colOrgAddress, colOrgLatitude, colOrgLongitude, colOrgShowMemberFlag);

		return affectedOrgRows;
	}
}
