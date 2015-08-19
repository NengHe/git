package com.hexin.icp.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hexin.icp.bean.AppUser;
import com.hexin.icp.bean.Dict;
import com.hexin.icp.bean.Img;
import com.hexin.icp.bean.Index;
import com.hexin.icp.bean.Org;
import com.hexin.icp.bean.PaymentTypeDTO;
import com.hexin.icp.bean.Position;
import com.hexin.icp.bean.ZTreeDTO;
import com.hexin.icp.dao.CommonDao;
import com.hexin.icp.service.CommonService;
import com.hexin.icp.util.FileUtil;

@Service
@Transactional(value = "transactionManager")
public class CommonServiceImpl implements CommonService {
	private static final Logger logger = LoggerFactory
			.getLogger(CommonServiceImpl.class);

	@Autowired
	private CommonDao dao;

	@Override
	public List<Position> queryPositions4select(Integer orgId) {
		List<Position> list = dao.findPositions4select(orgId);

		return list;
	}

	@Override
	public List<Org> queryOrgs4select(Integer adminUserId,
			String adminUserType, Integer orgId) {
		List<Org> list = null;

		if ("1".equals(adminUserType)) {
			list = new ArrayList<Org>();
			// 机构管理用户只返回本机构
			Org result = dao.getOrgByOrgId4select(orgId);
			list.add(result);
		} else if ("2".equals(adminUserType)) {
			// 系统管理用户返回所有的机构
			list = dao.findAllOrgs4select();
		}

		return list;
	}

	@Override
	public List<Index> queryIndex4select(String colLabelClass) {

		List<Index> list = dao.queryIndex4select(colLabelClass);

		return list;
	}

	@Override
	public Img uploadImg(MultipartFile imgFile) throws FileNotFoundException,
			NoSuchAlgorithmException, IOException, MyException {
		Img img = null;

		// 上传标题原图压缩图片到服务器
		String originalUploadUrl = FileUtil.uploadFile(imgFile);

		if (StringUtils.isNotEmpty(originalUploadUrl)) {
			img = new Img();
			img.setColImgPath(originalUploadUrl);
		}

		return img;
	}

	@Override
	public List<Index> queryIndexs(String colLabelClass) {

		List<Index> list = dao.queryIndex(colLabelClass);

		return list;
	}

	@Override
	public int createIndex(String colLabelClass, String colLabelText,
			String colDelFlag) {
		int affectedRows;

		affectedRows = dao.createIndex(colLabelClass, colLabelText, colDelFlag);

		return affectedRows;
	}

	@Override
	public int removeIndex(Integer dictId) {
		int affectedRows;

		affectedRows = dao.removeIndex(dictId);

		return affectedRows;
	}

	@Override
	public int udpateIndex(Integer dictId, String colLabelClass,
			String colLabelText, String colDelFlag) {
		int affectedRows;

		affectedRows = dao.udpateIndex(dictId, colLabelClass, colLabelText,
				colDelFlag);

		return affectedRows;
	}

	@Override
	public List<ZTreeDTO> getRoleTree4select(String roleType) {
		List<ZTreeDTO> result;

		result = dao.getRoleTree4select(roleType);

		return result;
	}

	



	@Override
	public boolean checkUniqueOrgCode(String orgCode, Integer orgId) {
		boolean flag = false;
		List<Org> orglist;
		Integer resultOrgId = null;

		orglist = dao.queryOrgs(orgCode);

		if (CollectionUtils.isEmpty(orglist)) {
			flag = true;
		} else if (orglist != null && orglist.size() == 1) {
			resultOrgId = orglist.get(0).getOrgId();

			if (orgId != null && orgId.equals(resultOrgId)) { // 如果结果集中该机构的id跟orgId相等（更新机构时）
				flag = true;
			}
		}

		return flag;
	}

	@Override
	public List<Dict> queryDict4select(String colDictClass, String colDictVal) {
		List<Dict> result = null;

		result = dao.queryDict4select(colDictClass, colDictVal);

		return result;
	}
	
	@Override
	public List<Dict> queryDicts(String colDictClass) {

		List<Dict> list = dao.queryDicts(colDictClass);

		return list;
	}

	@Override
	public int createDict(String colDictClass, String colDictText, String colDictVal) {
		int affectedRows;

		affectedRows = dao.createDict(colDictClass, colDictText,colDictVal);

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
	
	@Override
	public boolean checkUniquePhone4AppUser(String phone, Integer appUserId) {
		boolean flag = false;
		List<AppUser> appUserlist;
		Integer userId = null;

		appUserlist = dao.queryAppUser(phone);

		if (CollectionUtils.isEmpty(appUserlist)) {
			flag = true;
		} else if (appUserlist != null && appUserlist.size() == 1) {
			userId = appUserlist.get(0).getUserId();

			if (appUserId != null && appUserId.equals(userId)) { // 如果使用当前手机号的用户id跟appUserId相等（更新用户时）
				flag = true;
			}
		}

		return flag;
	}
	
	@Override
	public List<AppUser> queryAppUsers4auto() {
		List<AppUser> appUserlist;

		appUserlist = dao.queryAppUsers4auto();

		return appUserlist;
	}

	

	@Override
	public List<AppUser> queryAppUser(String userLoginId) {
		List<AppUser> list = dao.queryAppUser(userLoginId);
		return list;
	}

	@Override
	public int createAppUser( String colUserLoginId,
			String colUserName, String colUserTel, String colUserMobile,
			String colUserEmail, String colUserAddress, String colUserJob,
			String colUserIndustry, String colUserCompany,
			Integer colReceiveMsgFlag, Integer colFriendInvite,
			String userLoginDeviceMac, Integer colDelFlag,
			String colUserOthers) {
		int affectedRows;
		affectedRows = dao.createAppUser(colUserLoginId,colUserName,colUserTel,colUserMobile,colUserEmail,colUserAddress,colUserJob,colUserIndustry,colUserCompany,colReceiveMsgFlag,colFriendInvite,userLoginDeviceMac,colDelFlag,colUserOthers);
		return affectedRows;
	}

	@Override
	public int udpateAppUser( Integer userId,String colUserLoginId,
			String colUserName, String colUserTel, String colUserMobile,
			String colUserEmail, String colUserAddress, String colUserJob,
			String colUserIndustry, String colUserCompany,
			Integer colReceiveMsgFlag, Integer colFriendInvite,
			String userLoginDeviceMac,Integer colDelFlag,
			String colUserOthers) {
		int affectedRows;
		affectedRows = dao.updateAppUser(userId,colUserLoginId,colUserName,colUserTel,colUserMobile,colUserEmail,colUserAddress,colUserJob,colUserIndustry,colUserCompany,colReceiveMsgFlag,colFriendInvite,userLoginDeviceMac,colDelFlag,colUserOthers);
		return affectedRows;
	}

	@Override
	public int removeAppUser(Integer userId) {
		int affectedRows;
		affectedRows = dao.removeAppUser(userId);
		return affectedRows;
	}

	@Override
	public Integer getDictMaxVal(String colDictClass) {
		Integer colDictVal = 0;
		String tmpVal = null;
		
		tmpVal = dao.getDictMaxVal(colDictClass);
		
		if(StringUtils.isNotEmpty(tmpVal)){
			colDictVal = Integer.parseInt(tmpVal);
		}
		
		colDictVal += 1;
		
		return colDictVal;
	}

    @Override
    public List<PaymentTypeDTO> queryPaymentByPaymentType4select(Integer colPaymentType) {
        List<PaymentTypeDTO> result = null;
        
        result = dao.queryPaymentByPaymentType4select(colPaymentType);
        
        return result;
    }

	
}