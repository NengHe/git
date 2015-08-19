package com.hexin.icp.dao;

import java.util.List;

import com.hexin.icp.bean.AppUser;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.Index;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.PaymentTypeDTO;
import com.hexin.icp.bean.Position;
import com.hexin.icp.bean.ZTreeDTO;

public interface CommonDao {

	/**
	 * 查询职位
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Position> findPositions4select(Integer orgId);

	/**
	 * 根据机构id获取机构
	 * 
	 * @param orgId
	 * @return
	 */
	public Org getOrgByOrgId4select(Integer orgId);

	/**
	 * 查询所有机构列表
	 * 
	 * @return
	 */
	public List<Org> findAllOrgs4select();

	/**
	 * 查询索引列表
	 * 
	 * @param colLabelClass
	 * @return
	 */
	public List<Index> queryIndex4select(String colLabelClass);

	/**
	 * 根据labelClass查询索引列表
	 * 
	 * @param colLabelClass
	 * @return
	 */
	public List<Index> queryIndex(String colLabelClass);

	/**
	 * 创建索引记录
	 * 
	 * @param colLabelClass
	 * @param colLabelKey
	 * @param colLabelText
	 * @param colDelFlag
	 * @return
	 */
	public int createIndex(String colLabelClass, String colLabelText,
			String colDelFlag);

	/**
	 * 删除索引记录
	 * 
	 * @param dictId
	 * @return
	 */
	public int removeIndex(Integer dictId);

	/**
	 * 更新索引记录
	 * 
	 * @param dictId
	 * @param colLabelClass
	 * @param colLabelKey
	 * @param colLabelText
	 * @param colDelFlag
	 * @return
	 */
	public int udpateIndex(Integer dictId, String colLabelClass,
			String colLabelText, String colDelFlag);

	/**
	 * 查询角色下拉框数据
	 * 
	 */
	public List<ZTreeDTO> getRoleTree4select(String roleType);

	/**
	 * 查询机构列表
	 * @param orgCode
	 * @return
	 */
	public List<Org> queryOrgs(String orgCode);

	/**
	 * 查询t_dict记录
	 * @param colDictClass
	 * @param colDictVal 
	 * @return
	 */
	public List<Dict> queryDict4select(String colDictClass, String colDictVal);
	/**
	 * 查询机构类型
	 * @param colDictClass
	 * @return
	 */
	public List<Dict> queryDicts(String colDictClass);
	/**
	 * 创建机构类型
	 * @param colDictClass
	 * @param colDictText
	 * @param colDictVal
	 * @return
	 */
	 public int createDict(String colDictClass,  String colDictText, String colDictVal);
	/**
	 * 删除机构类型
	 * @param DictId
	 * @return
	 */
	public int removeDict(Integer DictId);
	/**
	 * 更新机构类型
	 * @param DictId
	 * @param colDictClass
	 * @param colDictText
	 * @param colDictValue
	 * @return
	 */
	public int udpateDict(Integer DictId, String colDictClass,String colDictText, String colDictVal);
	/**
	 * 查询前台APP用户
	 * @param phone
	 * @return
	 */
	public List<AppUser> queryAppUser(String phone);

	/**
	 * 查询前台用户列表
	 * @return
	 */
	public List<AppUser> queryAppUsers4auto();
	
		
	/**
	 * 删除前台APP用户
	 * @param userId
	 * @return
	 */
	public int removeAppUser(Integer userId);
	/**
	 * 更新前台APP用户
	 * @param userId
	 * @param colUserLoginId
	 * @param colUserName
	 * @param colUserTel
	 * @param colUserMobile
	 * @param colUserEmail
	 * @param colUserAddress
	 * @param colUserJob
	 * @param colUserIndustry
	 * @param colUserCompany
	 * @param colReceiveMsgFlag
	 * @param colFriendInvite
	 * @param userLoginDeviceMac
	 * @param colDelFlag
	 * @param colUserOthers
	 * @return
	 */
	public int updateAppUser(Integer userId, String colUserLoginId,
			String colUserName, String colUserTel, String colUserMobile,
			String colUserEmail, String colUserAddress, String colUserJob,
			String colUserIndustry, String colUserCompany,
			Integer colReceiveMsgFlag, Integer colFriendInvite,
			String userLoginDeviceMac,Integer colDelFlag,String colUserOthers
			);
	/**
	 * 创建前台APP用户
	 * @param colUserLoginId
	 * @param colUserName
	 * @param colUserTel
	 * @param colUserMobile
	 * @param colUserEmail
	 * @param colUserAddress
	 * @param colUserJob
	 * @param colUserIndustry
	 * @param colUserCompany
	 * @param colReceiveMsgFlag
	 * @param colFriendInvite
	 * @param userLoginDeviceMac
	 * @param colDelFlag
	 * @param colUserOthers
	 * @return
	 */
	public int createAppUser( String colUserLoginId,
			String colUserName, String colUserTel, String colUserMobile,
			String colUserEmail, String colUserAddress, String colUserJob,
			String colUserIndustry, String colUserCompany,
			Integer colReceiveMsgFlag, Integer colFriendInvite,
			String userLoginDeviceMac,  Integer colDelFlag,
			String colUserOthers);
	
	/**
	 * 查询t_dict列表对应colDictClass类型的记录中，最大的col_dict_val
	 * @param colDictClass
	 * @return
	 */
	public String getDictMaxVal(String colDictClass);
	
	/**
	 * 根据支付类型查询支付方式
	 * @param colPaymentType
	 * @return
	 */
    public List<PaymentTypeDTO> queryPaymentByPaymentType4select(Integer colPaymentType);
}
