package com.hexin.icp.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexin.core.constant.Constants;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.OrgReq;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.service.OrgReqService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/orgReq")
public class OrgReqController {

	private static final Logger logger = LoggerFactory
			.getLogger(OrgReqController.class);

	@Autowired
	private OrgReqService service;

	/**
	 * 删除入会申请
	 * 
	 * @return
	 */
	@RequiresPermissions("orgReq:remove")
	@RequestMapping(value = "removeOrgReq")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object removeOrgReq(
			@RequestParam(value = "orgJoinReqId", required = true) Integer orgJoinReqId) {

		ReturnMessage rm = new ReturnMessage();
		int code;

		try {
			AdminUser curUser = WebUtil.getLoginUser();
			Integer adminUserId = curUser.getAdminUserId();
			Integer orgId = curUser.getColOrgId();

			code = service.removeOrgReq(adminUserId, orgId, orgJoinReqId);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 审核不通过
	 * 
	 * @return
	 */
	@RequiresPermissions("orgReq:auditReject")
	@RequestMapping(value = "auditReject")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object auditReject(
			@RequestParam(value = "orgJoinReqId", required = true) Integer orgJoinReqId) {

		ReturnMessage rm = new ReturnMessage();

		try {
			AdminUser curUser = WebUtil.getLoginUser();
			Integer adminUserId = curUser.getAdminUserId();
			Integer orgId = curUser.getColOrgId();

			rm = service.auditReject(adminUserId, orgId, orgJoinReqId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 审核通过
	 * 
	 * @return
	 */
	@RequiresPermissions("orgReq:auditPass")
	@RequestMapping(value = "auditPass")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object auditPass(
			@RequestParam(value = "orgJoinReqId", required = true) Integer orgJoinReqId) {

		ReturnMessage rm = new ReturnMessage();

		try {
			AdminUser curUser = WebUtil.getLoginUser();
			Integer adminUserId = curUser.getAdminUserId();
			Integer orgId = curUser.getColOrgId();

			rm = service.auditPass(adminUserId, orgId, orgJoinReqId);

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
	@RequiresPermissions("orgReq:query")
	@RequestMapping(value = "queryOrgReqs")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object queryOrgReqs(
			PageCondition pageCondition,
			@RequestParam(value = "colUserName", required = false) String colUserName) {

		Page<OrgReq> page = null;

		try {
			AdminUser curUser = WebUtil.getLoginUser();
			Integer adminUserId = curUser.getAdminUserId();
			String adminUserType = curUser.getColAdminUserType();
			Integer orgId = curUser.getColOrgId();

			page = service.queryOrgReqs(pageCondition, adminUserId, adminUserType, orgId,
					colUserName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return page;
		}
	}

}
