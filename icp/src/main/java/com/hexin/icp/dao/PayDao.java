package com.hexin.icp.dao;

import java.util.List;

import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.PayHistory;
import com.hexin.icp.bean.Payment;

public interface PayDao {
	/**
	 * 查询t_payment表
	 * 
	 * @param colPayAccount
	 * @return
	 */
	public Page<Payment> queryPayments(PageCondition pageCondition,
			String colPayAccount);

	/**
	 * 创建支付方式
	 * 
	 * @param colPaymentId
	 * @param colPayAccount
	 * @param colPayPsw
	 * @return
	 */
	public int createPayment(Integer colPaymentType, String colPayAccount,
			String encryptPwd, String colCode);

	/**
	 * 编辑支付方式
	 * 
	 * @param colPaymentId
	 * @param colPayAccount
	 * @param colPayPsw
	 * @return
	 */
	public int updatePayment(Integer payId, Integer colPaymentType,
			String colPayAccount, String colCode, String encryptPwd);

	/**
	 * 删除支付方式
	 * 
	 * @param payId
	 * @return
	 */
	public int removePayment(Integer payId);

	/**
	 * 查询支付流水
	 * 
	 * @param colqueryby
	 * @param colQueryBy
	 * @return
	 */
	public Page<PayHistory> queryPayHistory(PageCondition pageCondition,
			String colqueryby, String colQueryBy);

	/**
	 * 查询机构类型
	 * 
	 * @param colDictClass
	 * @return
	 */
	public List<Dict> queryDicts(String colDictClass);

	/**
	 * 创建机构类型
	 * 
	 * @param colDictClass
	 * @param colDictText
	 * @param colDictVal
	 * @return
	 */
	public int createDict(String colDictClass, String colDictText,
			String colDictVal);

	/**
	 * 删除机构类型
	 * 
	 * @param DictId
	 * @return
	 */
	public int removeDict(Integer DictId);

	/**
	 * 更新机构类型
	 * 
	 * @param DictId
	 * @param colDictClass
	 * @param colDictText
	 * @param colDictValue
	 * @return
	 */
	public int udpateDict(Integer DictId, String colDictClass,
			String colDictText, String colDictVal);

}
