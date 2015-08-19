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
import com.hexin.icp.bean.Position;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.service.PositionService;
import com.hexin.icp.util.WebUtil;

@Controller
@RequestMapping("/position")
public class PositionController {

	private static final Logger logger = LoggerFactory
			.getLogger(PositionController.class);

	@Autowired
	private PositionService service;

	/**
	 * 根据机构查询职位
	 * 
	 * @param orgId
	 *            机构id
	 * @return
	 */
	@RequiresPermissions("position:query")
	@RequestMapping(value = "queryPositions")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object queryPositions(
			PageCondition pageCondition,
			@RequestParam(value = "colPosName", required = false) String colPosName) {

		Page<Position> result = null;
		AdminUser curUser = null;
		String adminUserType = null;
		Integer orgId = null;

		try {
			curUser = WebUtil.getLoginUser();
			adminUserType = curUser.getColAdminUserType();
			orgId = curUser.getColOrgId();

			result = service.queryPositions(pageCondition, adminUserType, orgId, colPosName);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return result;
		}
	}

	/**
	 * 创建机构职位
	 * 
	 * @param orgId
	 *            机构id
	 * @return
	 */
	@RequiresPermissions("position:create")
	@RequestMapping(value = "createPosition")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object createPosition(
			@RequestParam(value = "colPosOrgId", required = true) Integer colPosOrgId,
			@RequestParam(value = "colPosName", required = true) String colPosName,
			@RequestParam(value = "colPosShowDetailInner", required = true) Integer colPosShowDetailInner,
			@RequestParam(value = "colPosShowDetailOutter", required = true) Integer colPosShowDetailOutter) {

		ReturnMessage rm = new ReturnMessage();
		int code = 0;

		try {

			code = service.createPosition(colPosOrgId, colPosName,
					colPosShowDetailInner, colPosShowDetailOutter);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 更新机构职位
	 * 
	 * @param orgId
	 *            机构id
	 * @return
	 */
	@RequiresPermissions("position:update")
	@RequestMapping(value = "updatePosition")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object updatePosition(
			@RequestParam(value = "positionId", required = true) Integer positionId,
			@RequestParam(value = "colPosOrgId", required = true) Integer colPosOrgId,
			@RequestParam(value = "colPosName", required = true) String colPosName,
			@RequestParam(value = "colPosShowDetailInner", required = true) Integer colPosShowDetailInner,
			@RequestParam(value = "colPosShowDetailOutter", required = true) Integer colPosShowDetailOutter) {

		ReturnMessage rm = new ReturnMessage();
		int code = 0;

		try {

			code = service.updatePosition(positionId, colPosOrgId, colPosName,
					colPosShowDetailInner, colPosShowDetailOutter);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 删除机构职位
	 * 
	 * @param orgId
	 *            机构id
	 * @return
	 */
	@RequiresPermissions("position:remove")
	@RequestMapping(value = "removePosition")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object removePosition(
			@RequestParam(value = "positionId", required = true) Integer positionId) {

		ReturnMessage rm = new ReturnMessage();
		int code = 0;

		try {

			code = service.removePosition(positionId);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

}
