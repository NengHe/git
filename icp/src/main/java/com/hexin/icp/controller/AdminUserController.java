package com.hexin.icp.controller;

import java.util.List;

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
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.service.AdminUserService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/adminUser")
public class AdminUserController {

	private static final Logger logger = LoggerFactory
			.getLogger(AdminUserController.class);

	@Autowired
	private AdminUserService service;

	/**
	 * 查询管理用户列表
	 */
	@RequiresPermissions("adminUser:query")
	@RequestMapping(value = "queryAdminUsers")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object queryUser(PageCondition pageCondition) {

		Page<AdminUser> result = null;
		AdminUser curUser;
		String type;
		Integer orgId;

		try {
			curUser = WebUtil.getLoginUser();
			type = curUser.getColAdminUserType();
			orgId = curUser.getColOrgId();

			result = service.queryAdminUsers(pageCondition, type, orgId);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return result;
		}
	}

	/**
	 * 添加管理用户
	 */
	@RequiresPermissions("adminUser:create")
	@RequestMapping(value = "createAdminUser")
	@ResponseBody
	@SuppressWarnings("finally")
	public ReturnMessage createAdminUser(
			@RequestParam(value = "colAdminUsername", required = true) String colAdminUsername,
			@RequestParam(value = "colAdminUserPassword", required = true) String colAdminUserPassword,
			@RequestParam(value = "colAdminUserType", required = true) Integer colAdminUserType,
			@RequestParam(value = "colOrgId", required = false) Integer colOrgId,
			@RequestParam(value = "roleId", required = true) Integer roleId,
			@RequestParam(value = "colDelFlag", required = true) Integer colDelFlag) {

		ReturnMessage rm = new ReturnMessage();
		AdminUser curUser;
		Integer createBy;
		int code;

		try {
			curUser = WebUtil.getLoginUser();
			createBy = curUser.getAdminUserId();

			code = service.insertAdminUser(colAdminUsername,
					colAdminUserPassword, colAdminUserType, colOrgId, roleId,
					createBy, colDelFlag);

			rm.setCode(code + "");
		} catch (Exception e) {
			rm.setCode(Constants.FAILED);
			logger.error(e.getMessage(), e);
		} finally {
			return rm;
		}
	}

	/**
	 * 更新管理用户
	 */
	@RequiresPermissions("adminUser:update")
	@RequestMapping(value = "updateAdminUser")
	@ResponseBody
	@SuppressWarnings("finally")
	public ReturnMessage updateAdminUser(
			@RequestParam(value = "adminUserId", required = true) Integer adminUserId,
			@RequestParam(value = "colAdminUsername", required = true) String colAdminUsername,
			@RequestParam(value = "colAdminUserPassword", required = true) String colAdminUserPassword,
			@RequestParam(value = "colAdminUserType", required = true) Integer colAdminUserType,
			@RequestParam(value = "colOrgId", required = false) Integer colOrgId,
			@RequestParam(value = "roleId", required = true) Integer roleId,
			@RequestParam(value = "colDelFlag", required = true) Integer colDelFlag) {

		ReturnMessage rm = new ReturnMessage();
		AdminUser curUser;
		Integer updateBy;
		int code;

		try {
			curUser = WebUtil.getLoginUser();
			updateBy = curUser.getAdminUserId();

			code = service.udpateAdminUser(adminUserId, colAdminUsername,
					colAdminUserPassword, colAdminUserType, colOrgId, roleId,
					updateBy, colDelFlag);

			rm.setCode(code + "");
		} catch (Exception e) {
			rm.setCode(Constants.FAILED);
			logger.error(e.getMessage(), e);
		} finally {
			return rm;
		}
	}

	/**
	 * 删除管理用户
	 */
	@RequiresPermissions("adminUser:remove")
	@RequestMapping(value = "removeAdminUser")
	@ResponseBody
	@SuppressWarnings("finally")
	public ReturnMessage removeAdminUser(
			@RequestParam(value = "adminUserId", required = true) Integer adminUserId) {

		ReturnMessage rm = new ReturnMessage();
		int code;

		try {

			code = service.removeAdminUser(adminUserId);

			rm.setCode(code + "");
		} catch (Exception e) {
			rm.setCode(Constants.FAILED);
			logger.error(e.getMessage(), e);
		} finally {
			return rm;
		}
	}

	// /**
	// * 删除用户（软删除）
	// *
	// * @see权限说明：必须拥有user资源的增加权限
	// */
	// @RequiresPermissions("user:^[0-1]{1,}1[0-1]{2}$")
	// @RequestMapping(value = "deleteUser")
	// @ResponseBody
	// @SuppressWarnings("finally")
	// public ReturnMessage deleteUser(
	// @RequestParam(value = "userId", required = true) Integer userId) {
	// ReturnMessage rm = new ReturnMessage();
	//
	// try {
	// int affetedRows = service.deleteUser(userId);
	// rm.setCode(affetedRows + "");
	// } catch (Exception e) {
	// rm.setCode(Constants.FAILED);
	// logger.error(e.getMessage(), e);
	// } finally {
	// return rm;
	// }
	// }

	// /**
	// * 修改用户
	// *
	// * @see权限说明：必须拥有user资源的增加权限
	// */
	// @RequiresPermissions("user:^[0-1]{2,}1[0-1]{1}$")
	// @RequestMapping(value = "updateUser")
	// @ResponseBody
	// @SuppressWarnings("finally")
	// public ReturnMessage updateUser(
	// @RequestParam(value = "userId", required = true) Integer userId,
	// @RequestParam(value = "username", required = true) String username,
	// @RequestParam(value = "orgId", required = true) String orgId,
	// @RequestParam(value = "positionId", required = false) String positionId,
	// @RequestParam(value = "showRequest", required = false) String
	// showRequest,
	// @RequestParam(value = "showDetailInner", required = false) String
	// showDetailInner,
	// @RequestParam(value = "showDetailOutter", required = false) String
	// showDetailOutter,
	// @RequestParam(value = "personId", required = true) Integer personId,
	// @RequestParam(value = "personName", required = true) String personName,
	// @RequestParam(value = "personMobile", required = true) String
	// personMobile,
	// @RequestParam(value = "personEmail", required = true) String personEmail,
	// @RequestParam(value = "personAddress", required = true) String
	// personAddress,
	// @RequestParam(value = "personCompany", required = true) String
	// personCompany,
	// @RequestParam(value = "personCompany", required = true) String personJob)
	// {
	// ReturnMessage rm = new ReturnMessage();
	//
	// try {
	// User curUser = WebUtil.getLoginUser();
	// Integer updateBy = curUser.getUserId();
	// int code = service.updateUser(userId, username, orgId, positionId,
	// showRequest, showDetailInner, showDetailOutter, personId,
	// personName, personMobile, personEmail, personAddress,
	// personCompany, personJob, updateBy);
	// rm.setCode(code + "");
	// } catch (Exception e) {
	// rm.setCode(Constants.FAILED);
	// logger.error(e.getMessage(), e);
	// } finally {
	// return rm;
	// }
	// }

	/**
	 * 查询当前登录用户
	 * 
	 * @param user_id
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "getLoginUser")
	@ResponseBody
	@SuppressWarnings("finally")
	public ReturnMessage getLoginUser() {

		ReturnMessage rm = new ReturnMessage();

		try {
			AdminUser curUser = WebUtil.getLoginUser();
			// AdminUser list =
			// service.getUser4Auth(curUser.getAdminUserName());
			rm.setResult(curUser);
		} catch (Exception e) {
			rm.setCode(Constants.FAILED);
			logger.error(e.getMessage(), e);
		} finally {
			return rm;
		}
	}

	/**
	 * 查询当前登录用户
	 * 
	 * @param user_id
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "getMenu")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object getMenu() {

		List<ZTreeDTO> list = null;
		try {
			AdminUser curUser = WebUtil.getLoginUser();
			list = service.getMenuByAdminUserId(curUser.getAdminUserId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return list;
		}
	}
}
