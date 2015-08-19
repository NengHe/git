package com.hexin.core.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.hexin.icp.util.EncryptUtil;


public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        boolean flag = false;
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken; 
        String originalPwd = String.valueOf(token.getPassword());
        String encryptPwd = EncryptUtil.encrypt(originalPwd);
        
        Object tokenCredentials = encryptPwd;  
        Object accountCredentials = getCredentials(info);  
        
        //将密码加密后与系统保存的密码进行校验
        flag = equals(tokenCredentials, accountCredentials);
        
        return  flag;
    }

    
    
}
