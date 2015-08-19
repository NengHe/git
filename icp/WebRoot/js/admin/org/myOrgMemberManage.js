function myOrgMemberManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;成员登陆id:</td>');
	c.push('						<td>');
	c.push('							<input id="my_org_member_col_user_mobile_search" type="text" placeholder="请输入手机号查询" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="query_my_org_member_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_my_org_member"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_my_org_member_manage', tabTitle, c.join(""));

	queryBtn('#query_my_org_member_search');
	
	$('#query_my_org_member_search').click(function() {
		var colUserMobile = $('#my_org_member_col_user_mobile_search').val();
		$('#select_my_org_member').datagrid('load', {
			colUserMobile : colUserMobile
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getMyOrgMemberToolBar(permission);
	$('#select_my_org_member').datagrid( {
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
		url : root + '/rs/orgMember/queryMyOrgMembers',
		idField : 'orgMemInternalId',
		columns : [ [ {
			field : 'colOrgCode',
			title : '机构代码'
		},  {
			field : 'colOrgName',
			title : '机构名称'
		},{
			field : 'colPosName',
			title : '机构职务'
		},{
			field : 'colUserName',
			title : '姓名'
		},{
			field : 'colUserCompany',
			title : '公司'
		},{
			field : 'colUserIndustry',
			title : '行业'
		},{
			field : 'colUserJob',
			title : '公司职位'
		},{
			field : 'colUserMobile',
			title : '手机'
		},{
			field : 'colUserEmail',
			title : '邮箱'
		},{
			field : 'colUserAddress',
			title : '地址'
		},{
			field : 'colReceiveMsgFlag',
			title : '是否接受非好友消息',
			formatter:showYesFormatter
		},{
			field : 'colFriendInvite',
			title : '是否接受好友申请',
			formatter:showYesFormatter
		},{
			field : 'colShowDetailInner',
			title : '是否显示详细信息（内部）',
			formatter:showYesFormatter
		},{
			field : 'colShowDetailOutter',
			title : '是否显示详细信息（外部）',
			formatter:showYesFormatter
		},{
			field : 'colOrgUserStatus',
			title : '状态',
			width : 80,
			formatter:showDelFlagFormatter
		}]],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询成员失败！");
		},
		onLoadSuccess : function(data) {
		},
		onDblClickCell : function(rowIndex, field, value) {
			allocatePermission();
		}
	});

	var p = $('#select_my_org_member').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}

/**
 * 添加机构成员
 */
function createMyOrgMember() {
	var title = '请您创建成员';
	c = [];
	c.push('<div id="create_my_org_member">');
	c.push('	<div>');
	c.push('		<form id="create_my_org_member_form" method="post">');
	c.push('			<table class="dialogtable">');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>姓名:</td>');
	c.push('					<td><input type="text" id="create_my_org_member_user_name" style="width:180px"  name="colUserName"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>手机:</td>');
	c.push('					<td><input type="text" id="create_my_org_member_user_mobile" style="width:180px"  name="colUserMobile"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构职务:</td>');
	c.push('					<td>');
	c.push('						<select id="create_my_org_member_pos" name="positionId" class="easyui-combobox"  required="true" >');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>公司:</td>');
	c.push('					<td><input type="text" id="create_my_org_member_user_company" name="colUserCompany" style="width:180px"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;行业:</td>');
	c.push('					<td><input type="text" id="create_my_org_member_user_industry" name="colUserIndustry" style="width:180px" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;公司职位:</td>');
	c.push('					<td><input type="text" id="create_my_org_member_user_job" name="colUserJob" style="width:180px" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>邮箱:</td>');
	c.push('					<td><input type="text" id="create_my_member_personEmail" name="colUserEmail" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>地址:</td>');
	c.push('					<td><input type="text" id="create_my_member_personAddress" name="colUserAddress" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受非好友消息:</td>');
	c.push('					<td>');
	c.push('						<select id="create_my_org_member_receive_msg" name="colReceiveMsgFlag" class="easyui-combobox"   validType="nul">');
	c.push('						</select>');
	c.push('					</td>');0
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受好友申请:</td>');
	c.push('					<td>');
	c.push('						<input id="create_my_org_member_friend_invite" name="colFriendInvite" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="create_my_org_member_show_detail_inner" name="colShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="create_my_org_member_show_detail_outter" name="colShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_my_org_member_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_my_org_member_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');


	$('#create_my_org_member').dialog('destroy');
	$('#main_my_org_member_manage').append(c.join(""));
	$('#create_my_org_member').dialog( {
		title : title,
		closed : false,
		width : 480,
		modal : true
	});

	$('#create_my_org_member_user_name').validatebox( {
		required : true,
		missingMessage : '姓名不能为空'
	});
	
	$('#create_my_org_member_user_mobile').validatebox( {
		required : true,
		validType : ['mobile','uniqueMobile'],
		missingMessage : '请输入11位手机号'
	});

	$('#create_my_member_personEmail').validatebox( {
		validType : ['email']
	});
	
	//加载机构职位列表数据
	$('#create_my_org_member_pos').combobox({   
	    url:root + '/rs/common/queryPositionsByOrgId4select',   
	    valueField:'positionId',   
	    textField:'colPosName',
	    width:150
	});   
	
	$('#create_my_org_member_receive_msg').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});
	
	$('#create_my_org_member_friend_invite').combobox({   
		width:150,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});

	$('#create_my_org_member_show_detail_inner').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});

	$('#create_my_org_member_show_detail_outter').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});
	
	submit_clear('#create_my_org_member_submit', '#create_my_org_member_clear');
	$('#create_my_org_member_submit').click(function() {
		$('#create_my_org_member_form').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/orgMember/createMyOrgMember',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_my_org_member').dialog('destroy');
					$('#select_my_org_member').datagrid('reload');
					showmessage('创建成员成功');
				} else {
					$.messager.alert('错误', '创建成员失败', 'error');
				}
			}
		});
	});
	$('#create_my_org_member_clear').click(function() {
		$("#creatememberform").form('clear'); 
	});
	
}


/**
 * 编辑角色
 * 
 * @return
 */
function updateMyOrgMember() {
	var row = $('#select_my_org_member').datagrid('getSelected');
    if(row == null){
	   $.messager.alert('提示', '您选中您要编辑的记录');
    }
	
	var title = '请您编辑成员';
	c = [];
	c.push('<div id="update_my_org_member">');
	c.push('	<div>');
	c.push('		<form id="update_my_org_member_form" method="post">');
	c.push('			<input type="text" hidden name="orgId" >');
	c.push('			<input type="text" hidden name="userId" >');
	c.push('			<input type="text" hidden name="orgMemInternalId" >');
	c.push('			<table class="dialogtable">');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>姓名:</td>');
	c.push('					<td><input type="text" id="update_my_org_member_user_name" style="width:180px"  name="colUserName"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>手机:</td>');
	c.push('					<td><input type="text" id="update_my_org_member_user_mobile" style="width:180px"  name="colUserMobile"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构职务:</td>');
	c.push('					<td>');
	c.push('						<select id="update_my_org_member_pos" name="positionId" class="easyui-combobox"  required="true" >');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>公司:</td>');
	c.push('					<td><input type="text" id="update_my_org_member_user_company" name="colUserCompany" style="width:180px"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;行业:</td>');
	c.push('					<td><input type="text" id="update_my_org_member_user_industry" name="colUserIndustry" style="width:180px" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;公司职位:</td>');
	c.push('					<td><input type="text" id="update_my_org_member_user_job" name="colUserJob" style="width:180px" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>邮箱:</td>');
	c.push('					<td><input type="text" id="update_my_member_personEmail" name="colUserEmail" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>地址:</td>');
	c.push('					<td><input type="text" id="update_my_member_personAddress" name="colUserAddress" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受非好友消息:</td>');
	c.push('					<td>');
	c.push('						<select id="update_my_org_member_receive_msg" name="colReceiveMsgFlag" class="easyui-combobox"   validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受好友申请:</td>');
	c.push('					<td>');
	c.push('						<input id="update_my_org_member_friend_invite" name="colFriendInvite" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="update_my_org_member_show_detail_inner" name="colShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="update_my_org_member_show_detail_outter" name="colShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_my_org_member_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_my_org_member_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');


	$('#update_my_org_member').dialog('destroy');
	$('#main_my_org_member_manage').append(c.join(""));
	$('#update_my_org_member').dialog( {
		title : title,
		closed : false,
		width : 480,
		modal : true
	});

	$('#update_my_org_member_user_name').validatebox( {
		required : true,
		missingMessage : '姓名不能为空'
	});
	
	$('#update_my_org_member_user_mobile').validatebox( {
		required : true,
		validType : ['mobile','uniqueMobile['+row.userId+']'],
		missingMessage : '请输入11位手机号'
	});
	
	$('#update_my_member_personEmail').validatebox( {
		validType : ['email']
	});
	
	$('#update_my_org_member_pos').combobox({   
	    url:root + '/rs/common/queryPositionsByOrgId4select',   
	    valueField:'positionId',   
	    textField:'colPosName',
	    width:150
	});  
	
	$('#update_my_org_member_receive_msg').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});
	
	$('#update_my_org_member_friend_invite').combobox({   
		width:150,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});

	$('#update_my_org_member_show_detail_inner').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});

	$('#update_my_org_member_show_detail_outter').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});
	
	submit_clear('#update_my_org_member_submit', '#update_my_org_member_clear');
	$('#update_my_org_member_submit').click(function() {
		$('#update_my_org_member_form').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/orgMember/updateOrgMember',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_my_org_member').dialog('destroy');
					$('#select_my_org_member').datagrid('reload');
					showmessage('修改成员成功');
				} else {
					$.messager.alert('错误', '修改成员失败', 'error');
				}
			}
		});
	});
	
	$('#update_my_org_member_clear').click(function() {
		$("#creatememberform").form('clear'); 
		//加载表单数据
		loadMyOrgMemberFormData(row);
	});
	
	//加载表单数据
	loadMyOrgMemberFormData(row);
}

/**
 * 添加机构成员(已有前台用户，关联机构)
 */
function addMyOrgMember() {
	var title = '请您添加成员';
	c = [];
	c.push('<div id="add_my_org_member">');
	c.push('	<div>');
	c.push('		<form id="add_my_org_member_form" method="post">');
	c.push('			<table class="dialogtable">');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>关联用户:</td>');
	c.push('					<td><input type="text" id="add_my_org_member_relate_user_id" style="width:180px" name="userId" ></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构职务:</td>');
	c.push('					<td>');
	c.push('						<input id="add_my_org_member_pos" name="positionId" / >');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="add_my_org_member_show_detail_inner" name="colShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="add_my_org_member_show_detail_outter" name="colShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="add_my_org_member_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="add_my_org_member_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#add_my_org_member').dialog('destroy');
	$('#main_my_org_member_manage').append(c.join(""));
	$('#add_my_org_member').dialog( {
		title : title,
		closed : false,
		width : 480,
		height : 300,
		modal : true
	});

	$('#add_my_org_member_relate_user_id').autocombobox({
		valueField : 'userId',
		textField : 'colUserMobile',
		url : root + '/rs/common/queryAppUsers4auto',
		multiple : false,
		onChange : function(newValue,oldValue) {
			this.tmpValue = newValue[0];
		},
		required : true,
		missingMessage : '关联用户不能为空'
	});
	
	//加载机构职位列表数据
	$('#add_my_org_member_pos').combobox({   
	    url : root + '/rs/common/queryPositionsByOrgId4select',   
	    valueField : 'positionId',   
	    textField : 'colPosName',
	    width : 150,
	    required : true,
	    missingMessage : '职位不能为空'
	});  
	
	$('#add_my_org_member_receive_msg').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});

	$('#add_my_org_member_show_detail_inner').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});

	$('#add_my_org_member_show_detail_outter').combobox({   
	    width:150,
	    panelHeight:40,
	    valueField:'value',   
	    textField:'text',
	    value:"1",
	    data:[{
	    	"value":"1",
	    	"text":"是"
	    },{
	    	"value":"0",
	    	"text":"否"
	    }]
	});
	
	submit_clear('#add_my_org_member_submit', '#add_my_org_member_clear');
	$('#add_my_org_member_submit').click(function() {
		$('#add_my_org_member_form').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/orgMember/addMyOrgMember',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#add_my_org_member').dialog('destroy');
					$('#select_my_org_member').datagrid('reload');
					showmessage('创建成员成功');
				} else {
					$.messager.alert('错误', '创建成员失败', 'error');
				}
			}
		});
	});
	$('#add_my_org_member_clear').click(function() {
		$("#addmemberform").form('clear'); 
		$("#add_my_org_member_new_my_org_pos_div").remove();
	});
	
	$('#add_my_org_member_new_my_org_pos_btn').linkbutton();
	
	$("#add_my_org_member_new_my_org_pos_btn").click(function () {
		//动态创建机构、职位标签
		createNewPosSection('add_my_org_member_new_my_org_pos_div','add_my_org_member_new_my_org_pos_class','add_my_org_member');
	});

}

function loadMyOrgMemberFormData(row){

	$('#update_my_org_member_form').form('load',{
		userId:row.userId,
		orgMemInternalId:row.orgMemInternalId,
		colUserName:row.colUserName,
		colUserMobile:row.colUserMobile,
		colUserEmail:row.colUserEmail,
		colUserAddress:row.colUserAddress,
		colUserCompany:row.colUserCompany,
		colUserIndustry:row.colUserIndustry,
		colUserJob:row.colUserJob,
		colReceiveMsgFlag:row.colReceiveMsgFlag,
		colFriendInvite:row.colFriendInvite,
		orgId:row.orgId,
		positionId:row.positionId,
		colShowDetailInner:row.colShowDetailInner,
		colShowDetailOutter:row.colShowDetailOutter
	});
	
}

/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getMyOrgMemberToolBar(permission) {
	var toolBars = [];
	toolBars[0] = {
		text : "创建成员",
		iconCls : "icon-add",
		handler : createMyOrgMember
	};
	toolBars[1] = {
		text : "编辑成员",
		iconCls : "icon-edit",
		handler : updateMyOrgMember
	};
	toolBars[2] = {
		text : "添加成员",
		iconCls : "icon-save",
		handler : addMyOrgMember
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

