function roleManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;角色名称:</td>');
	c.push('						<td>');
	c.push('							<input id="role_name" type="text" placeholder="请输入要查询的角色名称" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="queryrole_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_role"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_roleManage', tabTitle, c.join(""));

	queryBtn('#queryrole_search');
	
	$('#queryrole_search').click(function() {
		var roleName = $('#role_name').val();
		$('#select_role').datagrid('load', {
			roleName : roleName
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getRoleToolBar(permission);
	$('#select_role').datagrid( {
		iconCls : 'icon-save',
		fit : true,
		nowrap : true,
		striped : true,
		autoRowHeight : true,
		fitColumns : false,
		striped : true,
		singleSelect : true,
//		rownumbers : true,
//		pagination : true,
//		pageSize : paramconfig.page.size,
//		pageList : paramconfig.page.list,
		url : root + '/rs/role/queryRoles',
		idField : 'roleId',
		columns : [ [ {
			field : 'roleName',
			title : '角色名称',
			width : 150
		}, {
			field : 'roleType',
			title : '角色类型',
			width : 150,
			align : 'center',
			formatter : roleTypeFormatter
		}, {
			field : 'roleDesc',
			title : '角色描述',
			width : 200
		}, {
			field : 'parentName',
			title : '父角色',
			width : 200
		}, {
			field : 'parentId',
			hidden : true
		} ] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询角色失败！");
		},
		onLoadSuccess : function(data) {
		},
		onDblClickCell : function(rowIndex, field, value) {
			allocatePermission();
		}
	});

//	var p = $('#select_role').datagrid('getPager');
//	$(p).pagination( {
//		beforePageText : '第',
//		afterPageText : '页    共 {pages} 页',
//		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//	});

}

/**
 * 添加角色
 */
function createRole() {
	var title = '请您创建角色';
	c = [];
	c.push('<div id="create_role">');
	c.push('	<div>');
	c.push('		<form id="createroleform" method="post">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>角色类型:</td>');
	c.push('					<td><input type="text" id="create_role_type" style="width:180px"  name="roleType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>父角色:</td>');
	c.push('					<td>')
	c.push('						<input id="create_role_parent_id" name="parentId" type="text" style="display:none"/>');
	c.push('						<input id="create_role_parent_input" name="parentName" type="text" readonly=true style="width:180px" onclick="showZTree(\'create_role_parent_input\',\'create_role_parent_Ztree_div\',\'create_role\');"/>');
	c.push('						<div id="create_role_parent_Ztree_div" style="position:absolute;display:none;">')
	c.push('							<ul class="ztree" id="create_role_parent_ztree" style="width:172px"/>')
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>角色名称:</td>');
	c.push('					<td><input type="text" id="create_role_name" style="width:180px"  name="roleName"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>角色描述:</td>');
	c.push('					<td><input type="text" id="create_role_describe" style="width:180px"  name="roleDesc" ></input></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_role_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_role_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_role').dialog('destroy');
	$('#main_roleManage').append(c.join(""));
	$('#create_role').dialog( {
		title : title,
		closed : false,
		width : 280,
		modal : true
	});

	//加载角色类型数据
	$('#create_role_type').combobox( {
		required : true,
		missingMessage : '管理员类型不能为空',
		width : 180,
		valueField : 'colDictVal',   
		textField : 'colDictText',
		url : root + '/rs/common/queryDict4select?colDictClass=3',
		onSelect : function(record){
			var roleType = record.colDictVal;	//使管理员类型跟角色类型保持一直
			
			// 角色树加载
			var queryRoleTreeUrl = root + "/rs/common/getRoleTree4select?roleType="+roleType;
			var setting = defaultZTreeSetting("#create_role_parent_ztree", queryRoleTreeUrl,
					"#create_role_parent_input", "#create_role_parent_Ztree_div", "#create_role_parent_id");
			initZTree4InputWithSetting("#create_role_parent_ztree", setting);
			
		}
	});
	
	$('#create_role_name').validatebox( {
		required : true,
		missingMessage : '角色名称不能为空'
	});
	
	submit_clear('#create_role_submit', '#create_role_clear');
	$('#create_role_submit').click(function() {
		$('#createroleform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/role/insertRole',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_role').dialog('destroy');
					$('#select_role').datagrid('reload');
					showmessage('创建角色成功');
				} else {
					$.messager.alert('错误', '创建角色失败', 'error');
				}
			}
		});
	});
	$('#create_role_clear').click(function() {
		$("#createroleform").form('clear'); 
	});
}

/**
 * 删除角色
 */
function removeRole() {
    var row = $('#select_role').datagrid('getSelected');
    if(row){
       	$.messager.confirm('确认', '您确定要删除该角色吗', function(flag) {
           	if (flag) {
		         $.ajax({
		        	 type: 'POST',
					 url: root + '/rs/role/deleteRole',
					 data: {"roleId":row.roleId},
					 dataType: 'json',
					 success: function(data){
						if(data.code == '1'){
							$('#select_role').datagrid('reload');
							showmessage("删除角色成功！");
						} else if(data.message=='inuse'){
							$('#select_role').datagrid('reload');
							$.messager.alert("提示", "该角色关联其他用户，删除角色失败！",'error');
						} else {
							$('#select_role').datagrid('reload');
							$.messager.alert("提示", "删除角色失败！",'error');
						}
					}
				});
           	}
       });
    }else{
   		$.messager.alert('提示', "请选中您要删除的记录！");
   		return ;
    }
}

/**
 * 编辑角色
 * 
 * @return
 */
function updateRole() {
	var row = $('#select_role').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要编辑的记录！");
   		return ;
	}
	var title = '请您编辑角色';
	c = [];
	c.push('<div id="update_role">');
	c.push('	<div>');
	c.push('		<form id="updateroleform" method="post">');
	c.push('			<input type="hidden" name="roleId" />');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>角色类型:</td>');
	c.push('					<td><input type="text" id="update_role_type" style="width:180px"  name="roleType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>父角色:</td>');
	c.push('					<td>')
	c.push('						<input id="update_role_parent_id" name="parentId" type="text" style="display:none"/>');
	c.push('						<input id="update_role_parent_input" name="parentName" type="text" readonly=true style="width:180px" onclick="showZTree(\'update_role_parent_input\',\'update_role_parent_Ztree_div\',\'update_role\');"/>');
	c.push('						<div id="update_role_parent_Ztree_div" style="position:absolute;display:none;">')
	c.push('							<ul class="ztree" id="update_role_parent_ztree" style="width:172px"/>')
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>角色名称:</td>');
	c.push('					<td>');
	c.push('						<input type="text" id="update_role_name" style="width:180px"  name="roleName"  validType="nul"></input>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>角色描述:</td>');
	c.push('					<td><input type="text" id="update_role_describe" style="width:180px"  name="roleDesc" ></input></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_role_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_role_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_role').dialog('destroy');
	$('#main_roleManage').append(c.join(""));
	$('#update_role').dialog( {
		title : title,
		closed : false,
		width : 280,
		modal : true
	});

	//加载角色类型数据
	$('#update_role_type').combobox( {
		required : true,
		missingMessage : '管理员类型不能为空',
		width : 180,
		valueField : 'colDictVal',   
		textField : 'colDictText',
		url : root + '/rs/common/queryDict4select?colDictClass=3',
		onSelect : function(record){
			var roleType = record.colDictVal;	//使管理员类型跟角色类型保持一直
			
			// 父角色树加载
			var queryRoleTreeUrl = root + "/rs/common/getRoleTree4select?roleType="+roleType;
			var setting = updateRoleZTreeSetting(row, "#update_role_parent_ztree", queryRoleTreeUrl,
					"#update_role_parent_input", "#update_role_parent_Ztree_div","#update_role_parent_id");
			initZTree4InputWithSetting("#update_role_parent_ztree", setting, 
					"#update_role_parent_input", "#update_role_parent_id");
			
		}
	});
	
	$('#update_role_name').validatebox( {
		required : true,
		missingMessage : '角色名称不能为空'
	});
	submit_clear('#update_role_submit', '#update_role_clear');
	
	$('#update_role_submit').click(function() {
		$('#updateroleform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/role/updateRole',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_role').dialog('destroy');
					$('#select_role').datagrid('reload');
					showmessage('更新角色成功');
				} else {
					$.messager.alert('错误', '更新角色失败', 'error');
				}
			}
		});
	});
	
	$('#update_role_clear').click(function() {
		$('#updateroleform').form('load',{
			roleId:row.roleId,
			parentId:row.parentId,
			parentName:row.parentName,
			roleName:row.roleName,
			roleDesc:row.roleDesc
	    }); 
	});
	
	loadRoleFormData(row);
}

function loadRoleFormData(row){
	$('#updateroleform').form('load',{
		roleId:row.roleId,
//		parentId:row.parentId,
//		parentName:row.parentName,
		roleName:row.roleName,
		roleDesc:row.roleDesc
    }); 
	
	//手动设置角色类型
	setTimeout(function(){
		$('#update_role_type').combobox('select',row.roleType);
	},200);

	//手动设置角色
	setTimeout(function(){
		var treeObj = $.fn.zTree.getZTreeObj("update_role_parent_ztree");
		var node = treeObj.getNodeByParam("id", row.parentId);

		if(node != null){
			treeObj.checkNode(node,true,true,true);
		}
	},1000);
	
}

function allocatePermission() {
	var row = $('#select_role').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中需要操作的记录！");
   		return ;
	}
	var roleId = row.roleId;
	var roleName = row.roleName;
	var title = '请您为【'+roleName+'】分配权限';
	c = [];
	c.push('<div id="allocate_permission">');
	c.push('	<form id="alloPermForm" method="get">');
	c.push('		<input id="perm_role_id" name="roleId" value="'+roleId+'" type="input" hidden >');
	c.push('		<input id="allocate_permission_permOpIdStr" name="operationIds" type="hidden" >');
	c.push('		<div id="allocate_permission_tree">');
	c.push('		</div>');
	c.push('	</form>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="allocate_permission_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="allocate_permission_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#allocate_permission').dialog('destroy');
	$('#main_roleManage').append(c.join(""));
	$('#allocate_permission').dialog( {
		title : title,
		closed : false,
		width : 600,
		modal : true,
		top : 143
	});

	$('#allocate_permission_tree').treegrid({   
	    url : root + '/rs/role/queryPermissionTrees',   
//	    data : testData.permission.resource_tree,   
	    queryParams:{roleId:roleId},
	    idField:'resourceId',   
	    treeField:'label',
	    checkOnSelect:true,
	    columns:[[   
	        {title:'资源',field:'label',width:200},   
	        {title:'权限',field:'operationList',formatter:formatPermission}
//	        {title:'权限',field:'permision',width:340}
	    ]]   
	});  
	
	submit_clear('#allocate_permission_submit', '#allocate_permission_clear');
	
	$('#allocate_permission_submit').click(function() {
		$('#alloPermForm').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/role/allocatePermission',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code >= 1) {
					$('#allocate_permission_tree').datagrid('reload');
					showmessage('权限分配成功');
				} else {
					$.messager.alert('错误', '权限分配失败', 'error');
				}
			}
		});
	});
	$('#allocate_permission_clear').click(function() {
		
	});
}

/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getRoleToolBar(permission) {
	var toolBars = [];
	toolBars[0] = {
		text : "创建角色",
		iconCls : "icon-add",
		handler : createRole
	};
	toolBars[1] = {
		text : "删除角色",
		iconCls : "icon-remove",
		handler : removeRole
	};
	toolBars[2] = {
		text : "编辑角色",
		iconCls : "icon-edit",
		handler : updateRole
	};
	toolBars[3] = {
		text : "分配权限",
		iconCls : "icon-save",
		handler : allocatePermission
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

//格式化权限列显示
function formatPermission(val,row){
	if(val == null){
		return "";
	}
	
	var result = "";
	var resOpIDStr = $("#allocate_permission_permOpIdStr").val();
	var resOpIDArr = [];
	var checked = "";
	var operationIdStrObj = $("#allocate_permission_permOpIdStr");
	
	if(resOpIDStr != null && resOpIDStr.trim().length > 0){
		resOpIDArr = resOpIDStr.split(",");
	}
	
	$.each(val,function(index,item){
		var resourceOperationID = item.resourceOperationID;
		var operationName = item.operationName;
		var isOperationEnabled = item.isOperationEnabled;
		var isPermitted = item.disabled;
		var disabled = "";
		var checked = "";
		
		if(isOperationEnabled == 'yes'){
			checked = "checked";
		}
		if(isPermitted == 'true'){
			disabled = "disabled";
		}else{
			disabled = '';
		}
		if(isOperationEnabled == 'yes' && isPermitted == 'false'){
			resOpIDArr.push(resourceOperationID);
		}

		var checkTagStr = "<input type='checkbox' onClick='onCheckPermission(this,"+resourceOperationID+")' "+checked+" "+disabled+" />" + operationName + "&nbsp;&nbsp;";
		result += checkTagStr;
	});
	
	//注入所有有权限的resourceOperationID
	operationIdStrObj.val(resOpIDArr.join(","));
	
	return result;
}

//点击资源属性复选框事件
function onCheckPermission(checkObj,resourceOperationID){
	var resOpIDStr = $("#allocate_permission_permOpIdStr").val();
	var checked = checkObj.checked;
	var resOpIDArr = [];
	var flag = false;
	
	if(resOpIDStr != null && resOpIDStr.trim().length > 0){
		resOpIDArr = resOpIDStr.split(",");
		
		$.each(resOpIDArr,function(index,item){
			if(item == resourceOperationID){
				flag = true;
			}
		});
		
		if(checked && !flag){
			
			resOpIDArr.push(resourceOperationID);
		}else if(!checked && flag){
			
			resOpIDArr.pop(resourceOperationID);
		}
		
		resOpIDStr = resOpIDArr.join(",");
		
	}else{
		if(checked){
			resOpIDStr = resourceOperationID;
		}
	}
	
//	alert(resOpIDStr);
	$("#allocate_permission_permOpIdStr").val(resOpIDStr);
}


function roleTypeFormatter(value){
	if(value == '1'){
		return '机构';
	}else if(value == '2'){
		return '系统';
	}
}

