package com.hexin.icp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.icp.bean.AppUser;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.Img;
import com.hexin.icp.bean.Index;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.PaymentTypeDTO;
import com.hexin.icp.bean.Position;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.bean.ZTreeDTO;

public interface CommonService {

	/**
	 * 查询职位
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Position> queryPositions4select(Integer orgId);

	/**
	 * 查询机构列表
	 * 
	 * @param adminUserId
	 * @param adminUserType
	 * @param orgId
	 * @return
	 */
	public List<Org> queryOrgs4select(Integer adminUserId,
			String adminUserType, Integer orgId);

	/**
	 * 查询索引列表
	 * 
	 * @param colLabelClass
	 * @return
	 */
	public List<Index> queryIndex4select(String colLabelClass);

	/**
	 * 上传图片文件
	 * 
	 * @param imgFile
	 * @return
	 * @throws MyException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 */
	public Img uploadImg(MultipartFile imgFile) throws FileNotFoundException,
			NoSuchAlgorithmException, IOException, MyException;

	/**
	 * 根据labelClass查询索引列表
	 * 
	 * @param colLabelClass
	 * @return
	 */
	public List<Index> queryIndexs(String colLabelClass);

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
	 * 更新索引记录
	 * 
	 * @param dictId
	 * @return
	 */
	public int removeIndex(Integer dictId);

	/**
	 * 删除索引记录
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
	 * 查询角色下拉框
	 * @param roleType 
	 * 
	 */
	public List<ZTreeDTO> getRoleTree4select(String roleType);

	/**
	 * 验证机构代码唯一
	 * @param orgCode
	 * @param orgId
	 * @return
	 */
	public boolean checkUniqueOrgCode(String orgCode, Integer orgId);

	/**
	 * 查询t_dict列表
	 * @param colDictClass
	 * @param colDictVal 
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
	public int createDict(String colDictClass, String colDictText,String colDictVal);
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
	 * 验证机构成员手机号码唯一
	 * @param phone
	 * @param appUserId 
	 * @return
	 */
	public boolean checkUniquePhone4AppUser(String phone, Integer appUserId);

	/**
	 * 查询前台用户列表
	 * 
	 * @return
	 */
	public List<AppUser> queryAppUsers4auto();


	/**
	 * 查询前台APP用户
	 * @param phone
	 * @return
	 */
	public List<AppUser> queryAppUser(String phone);
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
	public int createAppUser(  String colUserLoginId,
			String colUserName, String colUserTel, String colUserMobile,
			String colUserEmail, String colUserAddress, String colUserJob,
			String colUserIndustry, String colUserCompany,
			Integer colReceiveMsgFlag, Integer colFriendInvite,
			String userLoginDeviceMac, Integer colDelFlag,
			String colUserOthers);
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
	public int udpateAppUser( Integer userId,String colUserLoginId,
			String colUserName, String colUserTel, String colUserMobile,
			String colUserEmail, String colUserAddress, String colUserJob,
			String colUserIndustry, String colUserCompany,
			Integer colReceiveMsgFlag, Integer colFriendInvite,
			String userLoginDeviceMac, Integer colDelFlag, String colUserOthers);
	/**
	 * 删除前台APP用户
	 * @param userId
	 * @return
	 */
	public int removeAppUser(Integer userId);

	/**
	 * 查询t_dict列表对应colDictClass类型的记录中，最大的col_dict_val
	 * @param colDictClass
	 * @return
	 */
	public Integer getDictMaxVal(String colDictClass);

	/**
	 * 查询支付方式
	 * @param colPaymentType
	 * @return
	 */
    public List<PaymentTypeDTO> queryPaymentByPaymentType4select(Integer colPaymentType);

	
}
