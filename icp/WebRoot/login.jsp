<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>同花顺中台系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" type="text/css" href="css/easyui-themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/loginStyle.css">
	
	<script type="text/javascript" src="js/library/jquery.min.js"></script>
	<script type="text/javascript" src="js/library/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/common/common.js"></script>
    <script type="text/javascript" src="js/common/encrypt.js"></script>
    <script type="text/javascript" src="js/login.js"></script>
    
  </head>
  
  <body class="body_bg" >
		<noscript>
			<div
				style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
				<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
			</div>
		</noscript>
		<div class="login_win">
			<div class="login_input">
				<form id="loginForm" name="loginForm" method="post">
					
					<div id="div_1">
						<span class="input_1"><input type="text" id="username4Inner" name="username" placeholder="请输入用户名" /></span>
						<span class="input_1"><input type="password" id="password4Inner" name="password" placeholder="请输入密码"/></span>
					</div>
					<div >
						<a href="javascript:void(0);"  id="loginSubmit"  class="login_button"  onclick="doLogin('#loginForm')"></a>
					</div>
				</form>
			</div>
			
		</div>
	</body>
</html>
