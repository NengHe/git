package com.hexin.icp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.PayHistory;
import com.hexin.icp.bean.Payment;
import com.hexin.icp.dao.PayDao;
import com.hexin.icp.service.PayService;
import com.hexin.icp.util.EncryptUtil;

@Service
@Transactional(value = "transactionManager")
public class PayServiceImpl implements PayService {
	private static final Logger logger = LoggerFactory
			.getLogger(PayServiceImpl.class);

	@Autowired
	private PayDao dao;

	@Override
	public Page<Payment> queryPayments(PageCondition pageCondition,
			String colPayAccount) {

		Page<Payment> page = dao.queryPayments(pageCondition, colPayAccount);
		// 用for循环取出对象
		for (int i = 1; i < page.getRows().size(); i++) {
			// 取出StudentInfo对象中的参数
			List<Payment> payment = page.getRows();
			String base64Psw = payment.get(i).getColPayPsw();
			payment.get(i).setColPayPsw(EncryptUtil.getFromBase64(base64Psw));
		}
		return page;
	}

	@Override
	public int createPayment(Integer colPaymentType, String colPayAccount,
			String colPayPsw, String colCode) {

		int affectedRows;
		String originalPwd = String.valueOf(colPayPsw);
		String encryptPwd = EncryptUtil.getBase64(originalPwd);
		affectedRows = dao.createPayment(colPaymentType, colPayAccount,
				encryptPwd, colCode);
		return affectedRows;
	}

	@Override
	public int udpatePayment(Integer payId, Integer colPaymentType,
			String colPayAccount, String colCode, String colPayPsw) {
		int affectedRows;
		String originalPwd = String.valueOf(colPayPsw);
		String encryptPwd = EncryptUtil.getBase64(originalPwd);
		affectedRows = dao.updatePayment(payId, colPaymentType, colPayAccount,
				colCode, encryptPwd);
		return affectedRows;
	}

	@Override
	public int removePayment(Integer payId) {
		int affectedRows;
		affectedRows = dao.removePayment(payId);
		return affectedRows;
	}

	@Override
	public Page<PayHistory> queryPayHistory(PageCondition pageCondition,
			String colqueryby, String colQueryBy) {
		Page<PayHistory> list = dao.queryPayHistory(pageCondition, colqueryby,
				colQueryBy);
		return list;
	}

	@Override
	public List<Dict> queryDicts(String colDictClass) {

		List<Dict> list = dao.queryDicts(colDictClass);

		return list;
	}

	@Override
	public int createDict(String colDictClass, String colDictText,
			String colDictVal) {
		int affectedRows;

		affectedRows = dao.createDict(colDictClass, colDictText, colDictVal);

		return affectedRows;
	}

	@Override
	public int removeDict(Integer dictId) {
		int affectedRows;

		affectedRows = dao.removeDict(dictId);

		return affectedRows;
	}

	@Override
	public int udpateDict(Integer dictId, String colDictClass,
			String colDictText, String colDictVal) {
		int affectedRows;

		affectedRows = dao.udpateDict(dictId, colDictClass, colDictText,
				colDictVal);

		return affectedRows;
	}

}
