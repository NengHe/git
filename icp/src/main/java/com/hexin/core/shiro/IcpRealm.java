package com.hexin.core.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.hexin.core.constant.Constants;
import com.hexin.icp.bean.Resource;
import com.hexin.icp.bean.Role;
import com.hexin.icp.bean.AdminUser;
import com.hexin.icp.service.AdminUserService;
import com.hexin.icp.util.WebUtil;

/**
 * @Company:浙江核新同花顺网络信息股份有限公司
 * @ClassName: IcpRealm
 * @Description: apache shiro认证&授权realm
 */

public class IcpRealm extends AuthorizingRealm {
	protected static Log logger = LogFactory.getLog(IcpRealm.class);

	@Autowired
	private AdminUserService adminUserService;

	private static final int FIRSTVALUE = 0;

	private static final int SECONDVALUE = 1;

	private static final String CONCATECHAR = ":";

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) {
		SimpleAuthenticationInfo info = null;
		UsernamePasswordToken token = null;
		AdminUser user = null;

		token = (UsernamePasswordToken) authenticationToken;
		user = adminUserService.getUser4Login(token.getUsername());

		if (user != null) {

			this.setSessionUser(user);
			info = new SimpleAuthenticationInfo(user,
					user.getColAdminUserPassword(), user.getColAdminUsername());

			// 主动加载权限
			doGetAuthorizationInfo(info.getPrincipals());
		}

		return info;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {

		SimpleAuthorizationInfo info = null;
		AdminUser user = null;
		AdminUser userAuth = null;

		try {
			user = WebUtil.getLoginUser();
			if (user == null) {
				return null;
			}

			userAuth = adminUserService.getUser4Auth(user.getAdminUserId());
			
			if (userAuth != null) {
				info = new SimpleAuthorizationInfo();
				
				for (Role role : userAuth.getRoleList()) {
					String rolName = role.getRoleName();
					
					// 用户角色集合
					info.addRole(rolName);
				}
				
				for (Resource resource : userAuth.getResourceList()) {
					// 用户权限集合
					String resourceName = resource.getPermission();
					
					if (StringUtils.isNotEmpty(resourceName)) {
						info.addStringPermission(resourceName);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return info;
	}

	/**
	 * Override the superclass is permitted method ,which is the
	 * PermissionAnnotationHandler invoked
	 */

	// @Override
	// public boolean isPermitted(PrincipalCollection principals, String
	// permission) {
	// System.out.println("Original Permission:"+permission);
	// Permission p = getPermissionResolver().resolvePermission(permission);
	// return isPermitted(principals, p,permission);
	// }
	//
	//
	// public boolean isPermitted(PrincipalCollection principals,
	// Permission permission,String permissionText) {
	// // TODO Auto-generated method stub
	// //check if the permission got from the anonotation ia a reluar expression
	// , if it is , then check it with the user got from the AuthorizationInfo ,
	// //if the user from the info comfirms with the rule of the permission ,
	// then update the permission's user with the got user.
	// AuthorizationInfo info = getAuthorizationInfo(principals);
	// if(info == null){ //登陆过期
	// return false;
	// }
	//
	// Set<String> stringPermissions=(Set<String>)info.getStringPermissions();
	// permission=updateAnnotationPermissionValueIfNeeded(stringPermissions,
	// permissionText, permission);
	// return super.isPermitted(principals, permission);
	// }
	// private Map<String,String> getThePermissionValue(String
	// permissionVlaue,Map<String,String> permissionKeyValueMap ){
	// if(!StringUtils.isEmpty(permissionVlaue)){
	// String [] perValues=permissionVlaue.split(IcpRealm.CONCATECHAR);
	// permissionKeyValueMap.put(perValues[IcpRealm.FIRSTVALUE],
	// perValues[IcpRealm.SECONDVALUE]);
	// }
	// return permissionKeyValueMap;
	// }
	// private Permission updateAnnotationPermissionValueIfNeeded(Set<String>
	// stringPermissions,String annotationPermissionText,Permission
	// originalPermission){
	//
	// Map<String,String> permissionKeyValueMap=new HashMap<String, String>();
	// for(String singlePermissionText:stringPermissions){
	// permissionKeyValueMap=getThePermissionValue(singlePermissionText,permissionKeyValueMap);
	// }
	// String []
	// permissionSplitedValue=annotationPermissionText.split(IcpRealm.CONCATECHAR);
	// String permissonResources=permissionSplitedValue[IcpRealm.FIRSTVALUE];
	// String permissionTextPermis=permissionSplitedValue[IcpRealm.SECONDVALUE];
	// boolean permissionCheckMatch=false;
	// String updateAnnotationPermissionText="";
	// for (String permissionKey:permissionKeyValueMap.keySet()){
	// if(permissionKey.trim().equals(permissonResources)&&permissionCheckMatch==false){
	// permissionCheckMatch=regualrCheckPermissionValue(permissionTextPermis,permissionKeyValueMap.get(permissionKey));
	// if(permissionCheckMatch==true){
	// updateAnnotationPermissionText=permissionKey.concat(IcpRealm.CONCATECHAR).concat(permissionKeyValueMap.get(permissionKey));
	// System.out.println("Updated Permission Text:"+updateAnnotationPermissionText);
	// originalPermission=
	// getPermissionResolver().resolvePermission(updateAnnotationPermissionText);
	// }
	// }
	// }
	// return originalPermission;
	// }
	//
	// private boolean regualrCheckPermissionValue(String
	// annotationPermissionValue,String configuredPermissionValue){
	// return
	// Pattern.matches(annotationPermissionValue,(CharSequence)configuredPermissionValue);
	//
	// }
	/**
	 * 设置session用户
	 * 
	 * @param user
	 *            登陆用户
	 */
	private void setSessionUser(AdminUser user) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute(Constants.SESSION_USER_KEY, user);
		logger.debug("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
	}

}
