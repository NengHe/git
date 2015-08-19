package com.hexin.icp.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.core.constant.Constants;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.service.OrgService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/org")
public class OrgController {

	private static final Logger logger = LoggerFactory
			.getLogger(OrgController.class);

	@Autowired
	private OrgService service;

	/**
	 * 创建机构
	 * 
	 * @return
	 */
	@RequiresPermissions("org:create")
	@RequestMapping(value = "createOrg")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object createOrg(
			@RequestParam(value = "colOrgType", required = true) String colOrgType,
			@RequestParam(value = "colOrgCode", required = true) String colOrgCode,
			@RequestParam(value = "colOrgName", required = true) String colOrgName,
			@RequestParam(value = "orgImg", required = false) MultipartFile orgImg,
			@RequestParam(value = "colOrgNote", required = false) String colOrgNote,
			@RequestParam(value = "colOrgIntro", required = false) String colOrgIntro,
			@RequestParam(value = "colOrgTel", required = false) String colOrgTel,
			@RequestParam(value = "colOrgFax", required = false) String colOrgFax,
			@RequestParam(value = "colOrgAddress", required = false) String colOrgAddress,
			@RequestParam(value = "colOrgLatitude", required = false) String colOrgLatitude,
			@RequestParam(value = "colOrgLongitude", required = false) String colOrgLongitude,
			@RequestParam(value = "colOrgShowMemberFlag", required = false) Integer colOrgShowMemberFlag) {

		ReturnMessage rm = new ReturnMessage();
		AdminUser curUser = WebUtil.getLoginUser();
		Integer adminUserId = curUser.getAdminUserId();

		try {
			int code = service.createOrg(colOrgType, colOrgCode, colOrgName,
					orgImg, colOrgNote, colOrgIntro, colOrgTel, colOrgFax,
					colOrgAddress, colOrgLatitude, colOrgLongitude,
					colOrgShowMemberFlag, adminUserId);
			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}

	}

	/**
	 * 更新机构
	 * 
	 * @return
	 */
	@RequiresPermissions("org:update")
	@RequestMapping(value = "updateOrg")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object updateOrg(
			@RequestParam(value = "orgId", required = true) Integer orgId,
			@RequestParam(value = "colOrgType", required = true) String colOrgType,
			@RequestParam(value = "colOrgCode", required = true) String colOrgCode,
			@RequestParam(value = "colOrgName", required = true) String colOrgName,
			@RequestParam(value = "imgId", required = false) Integer imgId,
			@RequestParam(value = "orgImg", required = false) MultipartFile orgImg,
			@RequestParam(value = "colOrgNote", required = false) String colOrgNote,
			@RequestParam(value = "colOrgIntro", required = false) String colOrgIntro,
			@RequestParam(value = "colOrgTel", required = false) String colOrgTel,
			@RequestParam(value = "colOrgFax", required = false) String colOrgFax,
			@RequestParam(value = "colOrgAddress", required = false) String colOrgAddress,
			@RequestParam(value = "colOrgLatitude", required = false) String colOrgLatitude,
			@RequestParam(value = "colOrgLongitude", required = false) String colOrgLongitude,
			@RequestParam(value = "colOrgShowMemberFlag", required = false) Integer colOrgShowMemberFlag) {

		ReturnMessage rm = new ReturnMessage();

		try {
			int code = service.updateOrg(orgId, colOrgType, colOrgCode,
					colOrgName, imgId, orgImg, colOrgNote, colOrgIntro,
					colOrgTel, colOrgFax, colOrgAddress, colOrgLatitude,
					colOrgLongitude, colOrgShowMemberFlag);
			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 查询机构列表
	 * 
	 * @return
	 */
	@RequiresPermissions("org:query")
	@RequestMapping(value = "queryOrgs")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object queryOrgs(
			PageCondition pageCondition,
			@RequestParam(value = "colOrgName", required = false) String colOrgName) {

		// List<Org> list = null;
		Page<Org> page = null;

		try {
			AdminUser curUser = WebUtil.getLoginUser();
			Integer adminUserId = curUser.getAdminUserId();
			String adminUserType = curUser.getColAdminUserType();
			Integer orgId = curUser.getColOrgId();

			page = service.queryOrgs(pageCondition, adminUserId, adminUserType,
					orgId, colOrgName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return page;
		}
	}

}
