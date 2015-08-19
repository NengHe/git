package com.hexin.icp.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexin.core.constant.Constants;
import com.hexin.icp.bean.ReturnMessage;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 用户登陆
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public Object login(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {

		ReturnMessage rm = new ReturnMessage();

		Subject curUser = SecurityUtils.getSubject();

		if (!curUser.isAuthenticated()) {
			try {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				curUser.login(token);
			} catch (AuthenticationException e) {
				logger.debug(e.getMessage(), e);
				rm.setCode(Constants.FAILED);
				rm.setMessage(Constants.LOGIN_FAILED);
			}
		} else {
			rm.setMessage(Constants.RE_LOGIN);
		}

		return rm;
	}

	/**
	 * 系统登出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public Object loginOut() {

		ReturnMessage rm = new ReturnMessage();

		try {
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			rm.setCode(Constants.FAILED);
			rm.setMessage(Constants.SYS_ERROR);
		}

		return rm;
	}
}
