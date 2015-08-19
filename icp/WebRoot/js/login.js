if (window.location != window.top.location){//解决超时登陆内嵌iframe问题
	window.top.location.href=window.location.href;
} 
$(document).ready(function() {	
	
	$('#username4Inner').focus();
	
	$('#username4Inner').bind('keydown', function(event) {
		if (event.keyCode == 13) {
			$('#password4Inner').focus();
		}
	});
	$('#password4Inner').bind('keydown', function(event) {
		if (event.keyCode == 13) {
			$('#loginSubmit').click();
		}
	});
	
});

function doLogin(form) {	
	$(form).form('submit', {
		url: root + '/rs/login',
		onSubmit : function () {
			var $username = $('#username4Inner');
			if ($.trim($username.val()) == '') {
				$.messager.alert('提示', '用户名为空','info');
				$username.focus();
				return false;
			}
			
			var $password = $('#password4Inner');
			if ($.trim($password.val()) == '') {
				$.messager.alert('提示', '密码为空','info');
				$password.focus();
				return false;
			}
			
			//加密
			var pwd = $password.val();
			pwd = hex_md5(pwd);
			$password.val(pwd);
			
			return true;
			
		},
		success : function (data) {
			var data = eval('(' + data + ')');
			if (data.code != '1') {
				$.messager.alert('系统提示','用户名或密码错误！','error');
			} else {
				window.location.href = root + '/index.html';
			}
		}
	});
}

