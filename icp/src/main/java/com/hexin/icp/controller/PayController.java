package com.hexin.icp.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
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
import com.hexin.icp.bean.AppUser;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.PayHistory;
import com.hexin.icp.bean.Payment;
import com.hexin.icp.bean.ReturnMessage;
import com.hexin.icp.service.PayService;

@Controller
@RequestMapping("/pay")
public class PayController {
	private static final Logger logger = LoggerFactory
			.getLogger(PayController.class);
	@Autowired
	private PayService service;

	/**
	 * 查询支付方式列表
	 * 
	 * @param colPayAccount
	 * @return
	 */
	@RequiresPermissions("payment:query")
	@RequestMapping(value = "queryPayments")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object queryPayments(
			PageCondition pageCondition,
			@RequestParam(value = "colPayAccount", required = false) String colPayAccount) {
		/* List<Payment> list = null; */
		Page<Payment> page = null;
		try {
			page = service.queryPayments(pageCondition, colPayAccount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return page;
		}
	}

	/**
	 * 创建支付方式
	 * 
	 * @return
	 */
	@RequiresPermissions("payment:create")
	@RequestMapping(value = "createPayment")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object createPayment(
			@RequestParam(value = "colPaymentType", required = true) Integer colPaymentType,
			@RequestParam(value = "colPayAccount", required = false) String colPayAccount,
			@RequestParam(value = "colPayPsw", required = false) String colPayPsw,
			@RequestParam(value = "colCode", required = false) String colCode) {
		ReturnMessage rm = new ReturnMessage();
		int code;

		try {

			code = service.createPayment(colPaymentType, colPayAccount,
					colPayPsw, colCode);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 编辑支付方式
	 * 
	 * @return
	 */
	@RequiresPermissions("payment:update")
	@RequestMapping(value = "updatePayment")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object udpatePayment(
			@RequestParam(value = "payId", required = true) Integer payId,
			@RequestParam(value = "colPaymentType", required = true) Integer colPaymentType,
			@RequestParam(value = "colPayAccount", required = false) String colPayAccount,
			@RequestParam(value = "colPayPsw", required = false) String colPayPsw,
			@RequestParam(value = "colCode", required = false) String colCode) {
		ReturnMessage rm = new ReturnMessage();
		int code;

		try {

			code = service.udpatePayment(payId, colPaymentType, colPayAccount,
					colCode, colPayPsw);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 删除支付方式
	 * 
	 * @return
	 */
	@RequiresPermissions("payment:remove")
	@RequestMapping(value = "removePayment")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object removePayment(
			@RequestParam(value = "payId", required = true) Integer payId) {
		ReturnMessage rm = new ReturnMessage();
		int code;

		try {

			code = service.removePayment(payId);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 查询支付流水
	 * 
	 * @param colqueryby
	 * @param colQueryBy
	 * @return
	 */
	@RequiresPermissions("payhistory:query")
	@RequestMapping(value = "queryPayHistory")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object queryPayHistory(
			PageCondition pageCondition,
			@RequestParam(value = "colqueryby", required = false) String colqueryby,
			@RequestParam(value = "colQueryBy", required = false) String colQueryBy) {
		Page<PayHistory> list = null;

		try {
			list = service.queryPayHistory(pageCondition, colqueryby,
					colQueryBy);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return list;
		}
	}

	/**
	 * 查询机构类型
	 * 
	 * @return
	 */
	@RequiresPermissions("payType:query")
	@RequestMapping(value = "queryPayType")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object queryDict(
			@RequestParam(value = "colDictClass", required = true) String colDictClass) {
		List<Dict> list = null;

		try {
			list = service.queryDicts(colDictClass);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return list;
		}
	}

	/**
	 * 创建机构类型
	 * 
	 * @return
	 */
	@RequiresPermissions("payType:create")
	@RequestMapping(value = "createPayType")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object createDict(
			@RequestParam(value = "colDictClass", required = true) String colDictClass,
			@RequestParam(value = "colDictText", required = true) String colDictText,
			@RequestParam(value = "colDictVal", required = true) String colDictVal) {
		ReturnMessage rm = new ReturnMessage();
		int code;

		try {

			code = service.createDict(colDictClass, colDictText, colDictVal);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 更新机构类型
	 * 
	 * @return
	 */
	@RequiresPermissions("payType:update")
	@RequestMapping(value = "updatePayType")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object udpateDict(
			@RequestParam(value = "dictId", required = true) Integer dictId,
			@RequestParam(value = "colDictClass", required = true) String colDictClass,
			@RequestParam(value = "colDictText", required = true) String colDictText,
			@RequestParam(value = "colDictVal", required = true) String colDictVal) {
		ReturnMessage rm = new ReturnMessage();
		int code;

		try {

			code = service.udpateDict(dictId, colDictClass, colDictText,
					colDictVal);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}

	/**
	 * 删除机构类型
	 * 
	 * @return
	 */
	@RequiresPermissions("payType:remove")
	@RequestMapping(value = "removePayType")
	@ResponseBody
	@SuppressWarnings("finally")
	public Object removeDict(
			@RequestParam(value = "dictId", required = true) Integer dictId) {
		ReturnMessage rm = new ReturnMessage();
		int code;

		try {

			code = service.removeDict(dictId);

			rm.setCode(code + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
		} finally {
			return rm;
		}
	}
}
