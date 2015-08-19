package com.hexin.icp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.app.exceptions.ICPIMException;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.ZTreeDTO;

public interface OrgService {

	/**
	 * 查询机构列表
	 * 
	 * @param pageCondition
	 * 
	 * @param colOrgName
	 * 
	 * @param userId
	 * @return
	 */
	public Page<Org> queryOrgs(PageCondition pageCondition,
			Integer adminUserId, String adminUserType, Integer orgId,
			String colOrgName);

	/**
	 * 创建机构
	 * 
	 * @param colOrgCode
	 * @param colOrgName
	 * @param orgImg
	 * @param colOrgNote
	 * @param colOrgIntro
	 * @param colOrgTel
	 * @param colOrgFax
	 * @param colOrgAddress
	 * @param colOrgLatitude
	 * @param colOrgLongitude
	 * @param colOrgShowMemberFlag
	 * @param adminUserId 
	 * @return
	 * @throws MyException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 * @throws ICPIMException 
	 */
	public int createOrg(String colOrgType, String colOrgCode,
			String colOrgName, MultipartFile orgImg, String colOrgNote,
			String colOrgIntro, String colOrgTel, String colOrgFax,
			String colOrgAddress, String colOrgLatitude,
			String colOrgLongitude, Integer colOrgShowMemberFlag,
			Integer adminUserId)
			throws FileNotFoundException, NoSuchAlgorithmException,
			IOException, MyException, ICPIMException;

	/**
	 * 更新机构
	 * 
	 * @param colOrgId
	 * @param colOrgCode
	 * @param colOrgName
	 * @param orgImg
	 * @param colOrgNote
	 * @param colOrgIntro
	 * @param colOrgTel
	 * @param colOrgFax
	 * @param colOrgAddress
	 * @param colOrgLatitude
	 * @param colOrgLongitude
	 * @param colOrgShowMemberFlag
	 * @return
	 * @throws MyException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 */
	public int updateOrg(Integer orgId, String colOrgType, String colOrgCode,
			String colOrgName, Integer imgId, MultipartFile orgImg,
			String colOrgNote, String colOrgIntro, String colOrgTel,
			String colOrgFax, String colOrgAddress, String colOrgLatitude,
			String colOrgLongitude, Integer colOrgShowMemberFlag)
			throws FileNotFoundException, NoSuchAlgorithmException,
			IOException, MyException;

}
