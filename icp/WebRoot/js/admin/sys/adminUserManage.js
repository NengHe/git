function adminUserManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_admin_user"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_adminUserManage', tabTitle, c.join(""));

	// 为datagrid添加工具条
	var _toolbar = getAdminUserToolBar(permission);
	$('#select_admin_user').datagrid( {
		iconCls : 'icon-save',
		fit : true,
		nowrap : true,
		striped : true,
		autoRowHeight : true,
		fitColumns : true,
		striped : true,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : paramconfig.page.size,
		pageList : paramconfig.page.list,
		url : root + '/rs/adminUser/queryAdminUsers',
		columns : [ [ {
			field : 'colAdminUsername',
			title : '用户名'
		},{
			field : 'colAdminUserType',
			title : '管理员类型',
			align : 'center',
			formatter : adminUserTypeFormatter
		},{
			field : 'roleName',
			title : '角色'
		},{
			field : 'colOrgName',
			title : '机构名称'
		},{
			field : 'colDelFlag',
			title : '状态',
			formatter : showDelFlagFormatter
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询管理员失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

	var p = $('#select_admin_user').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}


/**
 * 创建管理员
 */
function createAdminUser(){
	var title = '请您创建管理员';
	c = [];
	c.push('<div id="create_admin_user">');
	c.push('	<div>');
	c.push('		<form id="createadminuserform" method="post" enctype="multipart/form-data">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>用户名称:</td>');
	c.push('					<td ><input type="text" id="create_admin_user_name" style="width:95%" name="colAdminUsername" validType="nul" ></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>设置初始密码:</td>');
	c.push('					<td ><input type="password" id="create_admin_user_password" style="width:95%" name="colAdminUserPassword" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>管理员类型:</td>');
	c.push('					<td ><input type="text" id="create_admin_user_type" style="width:95%" name="colAdminUserType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>角色名称:</td>');
	c.push('					<td>')
	c.push('						<input id="create_admin_user_roleid" name="roleId" type="text" style="display:none"/>');
	c.push('						<input id="create_admin_user_role_input" name="parentName" type="text" readonly=true style="width:180px" onclick="showZTree(\'create_admin_user_role_input\',\'create_admin_user_role_Ztree_div\',\'create_admin_user\');"/>');
	c.push('						<div id="create_admin_user_role_Ztree_div" style="position:absolute;z-index:99;display:none;">')
	c.push('							<ul class="ztree" id="create_admin_user_role_ztree" style="width:172px"/>')
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr style="display:none" class="colorgidtr">');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>机构名称:</td>');
	c.push('					<td>');
	c.push('						<input id="create_admin_user_colOrgId" name="colOrgId" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1">&ensp;状态:</td>');
	c.push('					<td ><input type="text" id="create_admin_user_flag" style="width:95%"  name="colDelFlag"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_admin_user_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_admin_user_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_admin_user').dialog('destroy');
	$('#main_adminUserManage').append(c.join(""));
	$('#create_admin_user').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});
	
	$('#create_admin_user_name').validatebox( {
		required : true,
		missingMessage : '管理员名称不能为空'
	});

	$('#create_admin_user_password').validatebox( {
		required : true,
		missingMessage : '管理员密码不能为空'
	});
	
	$('#create_admin_user_type').combobox( {
		required : true,
		missingMessage : '管理员类型不能为空',
		width : 180,
		valueField : 'colDictVal',   
		textField : 'colDictText',
		url : root + '/rs/common/queryDict4select?colDictClass=2',
		onSelect : function(record){
			var userType = record.colDictVal;	//使管理员类型跟角色类型保持一直
			// 角色树加载
			var queryRoleTreeUrl = root + "/rs/common/getRoleTree4select?roleType="+userType;
			var setting = adminUserZTreeSetting("#create_admin_user_role_ztree", queryRoleTreeUrl,
					"#create_admin_user_role_input", "#create_admin_user_role_Ztree_div", "#create_admin_user_roleid");
			initZTree4InputWithSetting("#create_admin_user_role_ztree", setting, 
					"#create_admin_user_role_input", "#create_admin_user_roleid");
			
			//机构类型时，显示机构标签
			if(userType == 1){
				//机构类型时,显示机构下拉框
				showOrgCombobox();
			}else{
				//系统类型时，隐藏机构下拉框
				hideOrgCombobox();
			}
		}
	});
	
	$('#create_admin_user_role_input').validatebox( {
		required : true,
		missingMessage : '角色名称不能为空'
	});
	
	$('#create_admin_user_flag').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		value:"0",
		data:[{
			"value":"0",
			"text":"启用"
		},{
			"value":"1",
			"text":"停用"
		}]
	});

	submit_clear('#create_admin_user_submit', '#create_admin_user_clear');
	$('#create_admin_user_submit').click(function() {
		$('#createadminuserform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				
				//加密
				var pwd = $("#create_admin_user_password").val();
				pwd = hex_md5(pwd);
				$("#create_admin_user_password").val(pwd);
				
				return true;
			},
			url : root + '/rs/adminUser/createAdminUser',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_admin_user').dialog('destroy');
					$('#select_admin_user').datagrid('reload');
					showmessage('创建管理员');
				} else {
					$.messager.alert('错误', '创建管理员失败', 'error');
				}
			}
		});
	});
	$('#create_admin_user_clear').click(function() {
		$("#createadminuserform").form('clear'); 
	});
}




function removeAdminUser(){
	var row = $('#select_admin_user').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/adminUser/removeAdminUser',
				 data: {"adminUserId":row.adminUserId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_admin_user');
						showmessage("删除成功！");
					} else {
						$('#select_admin_user').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

function updateAdminUser(){
	var row = $("#select_admin_user").datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您编辑管理员';
	c = [];
	c.push('<div id="update_admin_user">');
	c.push('	<div>');
	c.push('		<form id="updateadminuserform" method="post" enctype="multipart/form-data">');
	c.push('			<table>');
	c.push(' 			<input type="hidden" name="adminUserId">');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>用户名称:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_admin_user_name" style="width:95%" name="colAdminUsername" validType="nul" ></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>设置新密码:</td>');
	c.push('					<td colspan="2"><input type="password" id="update_admin_user_password" style="width:95%" name="colAdminUserPassword" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>管理员类型:</td>');
	c.push('					<td ><input type="text" id="update_admin_user_type" style="width:95%" name="colAdminUserType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>角色名称:</td>');
	c.push('					<td>')
	c.push('						<input id="update_admin_user_roleid" name="roleId" type="text" style="display:none"/>');
	c.push('						<input id="update_admin_user_role_input" name="roleName" type="text" readonly=true style="width:180px" onclick="showZTree(\'update_admin_user_role_input\',\'update_admin_user_role_Ztree_div\',\'update_admin_user\');"/>');
	c.push('						<div id="update_admin_user_role_Ztree_div" style="position:absolute;z-index:99;display:none;">')
	c.push('							<ul class="ztree" id="update_admin_user_role_ztree" style="width:172px"/>')
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr style="display:none" class="colorgidtr">');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>机构名称:</td>');
	c.push('					<td>');
	c.push('						<input id="update_admin_user_colOrgId" name="colOrgId" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1">&ensp;状态:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_admin_user_flag" style="width:95%"  name="colDelFlag"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_admin_user_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_admin_user_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_admin_user').dialog('destroy');
	$('#main_adminUserManage').append(c.join(""));
	$('#update_admin_user').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#update_admin_user_name').validatebox( {
		required : true,
		missingMessage : '管理员名称不能为空'
	});

	$('#update_admin_user_password').validatebox( {
		required : true,
		missingMessage : '管理员密码不能为空'
	});
	
	$('#update_admin_user_type').combobox( {
		required : true,
		missingMessage : '管理员类型不能为空',
		width : 180,
		valueField : 'colDictVal',   
		textField : 'colDictText',
		url : root + '/rs/common/queryDict4select?colDictClass=2',
		onSelect : function(record){
			var userType = record.colDictVal;	//使管理员类型跟角色类型保持一直
			// 角色树加载
			var queryRoleTreeUrl = root + "/rs/common/getRoleTree4select?roleType="+userType;
			var setting = adminUserZTreeSetting("#update_admin_user_role_ztree", queryRoleTreeUrl,
					"#update_admin_user_role_input", "#update_admin_user_role_Ztree_div", "#update_admin_user_roleid");
			initZTree4InputWithSetting("#update_admin_user_role_ztree", setting,
					"#update_admin_user_role_input", "#update_admin_user_roleid");
			
			//机构类型时，显示机构标签
			if(userType == 1){
				//机构类型时,显示机构下拉框
				showOrgCombobox();
			}else{
				//系统类型时，隐藏机构下拉框
				hideOrgCombobox();
			}
		}
	});
	
	//加载机构列表数据
	$('#update_admin_user_colOrgId').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:150
	});
	
	$('#update_admin_user_role_input').validatebox( {
		required : true,
		missingMessage : '角色名称不能为空'
	});
	
	$('#update_admin_user_flag').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		value:"0",
		data:[{
			"value":"0",
			"text":"启用"
		},{
			"value":"1",
			"text":"停用"
		}]
	});
	
	
	submit_clear('#update_admin_user_submit', '#update_admin_user_clear');
	$('#update_admin_user_submit').click(function() {
		$('#updateadminuserform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				
				//加密
				var pwd = $("#update_admin_user_password").val();
				pwd = hex_md5(pwd);
				$("#update_admin_user_password").val(pwd);
				
				return true;
			},
			url :root + '/rs/adminUser/updateAdminUser',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_admin_user').dialog('destroy');
					$('#select_admin_user').datagrid('reload');
					showmessage('编辑类别成功');
				} else {
					$.messager.alert('错误', '编辑管理员失败', 'error');
				}
			}
		});
	});
	
	$('#update_admin_user_clear').click(function() {
		
		$("#updateadminuserform").form('clear'); 
		
		loadAdminUserFormData(row);
	});
	
	//加载表单数据
	loadAdminUserFormData(row);
}



/**
 * 加载管理员表单数据
 * @param row
 * @returns
 */
function loadAdminUserFormData(row){
	$("#updateadminuserform").form('load',{
		adminUserId : row.adminUserId,
		colAdminUsername : row.colAdminUsername,
		colAdminUserPassword : row.colAdminUserPassword,
//		colAdminUserType : row.colAdminUserType,
//		colOrgId : row.colOrgId,
//		colOrgName : row.colOrgName,
		roleId : row.roleId,
		roleName : row.roleName,
		colDelFlag : row.colDelFlag
	});
	
	//手动设置管理员类型
	setTimeout(function(){
		$('#update_admin_user_type').combobox('select',row.colAdminUserType);
	},200);
	
	//手动设置角色
	setTimeout(function(){
		var treeObj = $.fn.zTree.getZTreeObj("update_admin_user_role_ztree");
		var node = treeObj.getNodeByParam("id", row.roleId);

		if(node != null){
			treeObj.checkNode(node,true,true,true);
		}
	},1000);
	
	//手动设置角色
	setTimeout(function(){
		$('#update_admin_user_colOrgId').combobox('select',row.colOrgId);
	},1000);
}
/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getAdminUserToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建管理员",
		iconCls : "icon-add",
		handler : createAdminUser
	};
	toolBars[1] = {
		text : "删除管理员",
		iconCls : "icon-remove",
		handler : removeAdminUser
	};
	toolBars[2] = {
		text : "编辑管理员",
		iconCls : "icon-edit",
		handler : updateAdminUser
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

function adminUserTypeFormatter(value){
	if(value == '1'){
		return '机构';
	}else if(value == '2'){
		return '系统';
	}
}
