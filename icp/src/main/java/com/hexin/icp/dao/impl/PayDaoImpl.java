package com.hexin.icp.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hexin.core.dao.BaseDaoSupport;
import com.hexin.core.page.Page;
import com.hexin.core.page.PageCondition;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.PayHistory;
import com.hexin.icp.bean.Payment;
import com.hexin.icp.dao.PayDao;

@Repository
public class PayDaoImpl extends BaseDaoSupport implements PayDao {
	@Override
	public Page<Payment> queryPayments(PageCondition pageCondition,
			String colPayAccount) {
		Page<Payment> result = null;
		String sql = null;

		sql = "select p.*,d.col_dict_text as colPayTypeName";
		sql += " from t_payment p";
		sql += " left join t_dict d on d.col_dict_class='7' and d.col_dict_val=p.col_payment_type";
		sql += " where 1=1";

		if (StringUtils.isNotEmpty(colPayAccount)) {
			sql += " and p.col_pay_account like '%" + colPayAccount + "%'";
		}

		result = super.findPage(sql, Payment.class, pageCondition);

		return result;
	}

	@Override
	public int createPayment(Integer colPaymentType, String colPayAccount,
			String encryptPwd, String colCode) {
		int affectedRows;
		String sql;
		sql = "insert into t_payment (col_payment_type,col_pay_account,col_pay_psw,col_code) values (?,?,?,?)";
		affectedRows = super.insert(sql, colPaymentType, colPayAccount,
				encryptPwd, colCode);
		return affectedRows;
	}

	@Override
	public int updatePayment(Integer payId, Integer colPaymentType,
			String colPayAccount, String colCode, String encryptPwd) {
		int affectedRows;
		String sql;
		sql = "update t_payment set col_payment_type=?,col_pay_account=?,col_pay_psw=?,col_code=? where pay_id=?";
		affectedRows = super.update(sql, colPaymentType, colPayAccount,
				encryptPwd, colCode, payId);
		return affectedRows;
	}

	@Override
	public int removePayment(Integer payId) {
		int affectedRows;
		String sql;
		sql = "delete from t_payment where pay_id=?";
		affectedRows = super.insert(sql, payId);
		return affectedRows;
	}

	@Override
	public Page<PayHistory> queryPayHistory(PageCondition pageCondition,
			String colqueryby, String colQueryBy) {
		Page<PayHistory> result = null;
		String sql = null;
		String a = "colUserActId";
		String b = "colAppUserId";
		String c = "colPayNum";
		String d = "colPayAccount";

		sql = "select p.*,d.col_dict_text as colPayTypeName";
		sql += " from t_pay_history p";
		sql += " left join t_dict d on d.col_dict_class='7' and d.col_dict_val=p.col_payment_type";
		sql += " where 1=1";

		if (StringUtils.isNotEmpty(colQueryBy)) {
			if (StringUtils.equals(colqueryby, a)) {
				sql += " and p.col_user_act_id like '%" + colQueryBy + "%'";
			} else if (StringUtils.equals(colqueryby, b)) {
				sql += " and p.col_app_user_id like '%" + colQueryBy + "%'";
			} else if (StringUtils.equals(colqueryby, c)) {
				sql += " and p.col_pay_num like '%" + colQueryBy + "%'";
			} else if (StringUtils.equals(colqueryby, d)) {
				sql += " and p.col_pay_account like '%" + colQueryBy + "%'";
			}

		}

		result = super.findPage(sql, PayHistory.class, pageCondition);

		return result;
	}

	@Override
	public List<Dict> queryDicts(String colDictClass) {

		String sql = "select * from t_dict where col_dict_class=?";

		List<Dict> list = super.findList(sql, Dict.class, colDictClass);

		return list;
	}

	@Override
	public int createDict(String colDictClass, String colDictText,
			String colDictVal) {
		int affectedRows;
		String sql;

		sql = "insert into t_dict(col_dict_class,col_dict_key,col_dict_text,col_dict_val,col_dict_create_time) values(?,'payType',?,?,now())";

		affectedRows = super.insert(sql, colDictClass, colDictText, colDictVal);

		return affectedRows;
	}

	@Override
	public int removeDict(Integer dictId) {
		int affectedRows;
		String sql;

		sql = "delete from t_dict where dict_id=?";

		affectedRows = super.delete(sql, dictId);

		return affectedRows;
	}

	@Override
	public int udpateDict(Integer DictId, String colDictClass,
			String colDictText, String colDictVal) {
		int affectedRows;
		String sql;

		sql = "update t_dict set col_dict_text=?,col_dict_val=? where dict_id=? and col_dict_class=?";

		affectedRows = super.insert(sql, colDictText, colDictVal, DictId,
				colDictClass);

		return affectedRows;
	}
}
