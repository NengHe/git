function orgMemberManage(tabTitle, permission) {

	c = [];
	c.push('<div class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;成员登陆id:</td>');
	c.push('						<td>');
	c.push('							<input id="org_member_col_user_mobile_search" type="text" placeholder="请输入手机号查询" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="query_org_member_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_org_member"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_org_member_manage', tabTitle, c.join(""));

	queryBtn('#query_org_member_search');
	
	$('#query_org_member_search').click(function() {
		var colUserMobile = $('#org_member_col_user_mobile_search').val();
		$('#select_org_member').datagrid('load', {
			colUserMobile : colUserMobile
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getOrgMemberToolBar(permission);
	$('#select_org_member').datagrid( {
		iconCls : 'icon-save',
		fit : true,
		nowrap : true,
		striped : true,
		autoRowHeight : true,
		fitColumns : false,
		striped : true,
		singleSelect : true,
		rownumbers : true,
//		pagination : true,
//		pageSize : paramconfig.page.size,
//		pageList : paramconfig.page.list,
		url : root + '/rs/orgMember/queryOrgMembers',
		idField : 'orgMemInternalId',
		columns : [ [ 
//		              {
//			field : 'colOrgCode',
//			title : '机构代码'
//		},  {
//			field : 'colOrgName',
//			title : '机构名称'
//		},{
//			field : 'colPosName',
//			title : '机构职务'
//		},
		{
			field : 'colUserName',
			title : '姓名'
		},
//		{
//			field : 'orgPositions',
//			title : '机构',
//			formatter : orgPossFormatter
//		},
		{
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
//		onClickCell : function(rowIndex, field, value){
//			$(this).datagrid('selectRow', rowIndex);
//		},
//		onLoadSuccess : function(data) {
//			//初始选中第一行，防止表格初始加载时，点击【查看机构列表】没有选中的行
//			initSelectFirstRow('#select_org_member');
//		},
		onDblClickRow : function(rowIndex, field, value) {
			updateOrgMember();
		}
	});

//	var p = $('#select_org_member').datagrid('getPager');
//	$(p).pagination( { 
//		beforePageText : '第',
//		afterPageText : '页    共 {pages} 页',
//		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//	});

}



/**
 * 创建机构成员
 */
function createOrgMember() {
	var title = '请您创建成员';
	c = [];
	c.push('<div id="create_org_member">');
	c.push('	<div>');
	c.push('		<form id="create_org_member_form" method="post">');
	c.push('			<table class="dialogtable">');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>姓名:</td>');
	c.push('					<td><input type="text" id="create_org_member_user_name" style="width:180px"  name="colUserName"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>手机:</td>');
	c.push('					<td><input type="text" id="create_org_member_user_mobile" style="width:180px"  name="colUserMobile"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构名称:</td>');
	c.push('					<td>');
	c.push('						<select id="create_org_member_org" name="orgId" class="easyui-combobox" required="true" >');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构职务:</td>');
	c.push('					<td>');
	c.push('						<select id="create_org_member_pos" name="positionId" class="easyui-combobox"  required="true" >');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td colspan="2">');
	c.push('						<div id="create_org_member_new_org_pos_div">');
	c.push('						</div>');
	c.push('					<td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td></td>');
	c.push('					<td><a href="#" id="create_org_member_new_org_pos_btn">新增机构及职务</a></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;公司:</td>');
	c.push('					<td><input type="text" id="create_org_member_user_company" name="colUserCompany" style="width:180px"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;行业:</td>');
	c.push('					<td><input type="text" id="create_org_member_user_industry" name="colUserIndustry" style="width:180px" ></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;公司职位:</td>');
	c.push('					<td><input type="text" id="create_org_member_user_job" name="colUserJob" style="width:180px" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;邮箱:</td>');
	c.push('					<td><input type="text" id="create_member_personEmail" name="colUserEmail" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;地址:</td>');
	c.push('					<td><input type="text" id="create_member_personAddress" name="colUserAddress" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受非好友消息:</td>');
	c.push('					<td>');
	c.push('						<select id="create_org_member_receive_msg" name="colReceiveMsgFlag" class="easyui-combobox"   validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受好友申请:</td>');
	c.push('					<td>');
	c.push('						<input id="create_org_member_friend_invite" name="colFriendInvite" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="create_org_member_show_detail_inner" name="colShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="create_org_member_show_detail_outter" name="colShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_org_member_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_org_member_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');


	$('#create_org_member').dialog('destroy');
	$('#main_org_member_manage').append(c.join(""));
	$('#create_org_member').dialog( {
		title : title,
		closed : false,
		width : 480,
		height : 580,
		modal : true
	});

	$('#create_org_member_user_name').validatebox( {
		required : true,
		missingMessage : '姓名不能为空'
	});
	
	$('#create_org_member_user_mobile').validatebox( {
		required : true,
		validType : ['mobile','uniqueMobile'],
		missingMessage : '请输入11位手机号'
	});
	
	$('#create_member_personEmail').validatebox( {
		validType : ['email']
	});
	
	//加载机构列表数据
	var orgList = null;
	$.ajax({  
		url:root + '/rs/org/queryOrgs',  
        type: "GET",  
        dataType: "json",  
        cache: true,  
        success: function (result) {  
        	orgList = result;
        }
    });  
	
	//加载机构列表数据
	$('#create_org_member_org').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:150,
	    onSelect:function(record){
	    	var orgId = record.orgId;
	    	//加载机构职位列表数据
	    	$('#create_org_member_pos').combobox({   
	    	    url:root + '/rs/common/queryPositionsByOrgId4select?orgId='+orgId,   
	    	    valueField:'positionId',   
	    	    textField:'colPosName',
	    	    width:150
	    	});  
	    }
	});  
	
	$('#create_org_member_pos').combobox({   
	    width:150
	});  
	
	$('#create_org_member_receive_msg').combobox({   
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
	
	$('#create_org_member_friend_invite').combobox({   
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

	$('#create_org_member_show_detail_inner').combobox({   
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

	$('#create_org_member_show_detail_outter').combobox({   
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
	
	submit_clear('#create_org_member_submit', '#create_org_member_clear');
	$('#create_org_member_submit').click(function() {
		$('#create_org_member_form').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/orgMember/createOrgMember',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_org_member').dialog('destroy');
					$('#select_org_member').datagrid('reload');
					showmessage('创建成员成功');
				} else {
					$.messager.alert('错误', '创建成员失败', 'error');
				}
			}
		});
	});
	$('#create_org_member_clear').click(function() {
		$("#creatememberform").form('clear'); 
		$("#create_org_member_new_org_pos_div").remove();
	});
	
	$('#create_org_member_new_org_pos_btn').linkbutton();
	
	$("#create_org_member_new_org_pos_btn").click(function () {
		//动态创建机构、职位标签
		createNewPosSection('create_org_member_new_org_pos_div','create_org_member_new_org_pos_class','create_org_member');
	});

}

/**
 * 编辑角色
 * 
 * @return
 */
function updateOrgMember() {
	var row = $('#select_org_member').datagrid('getSelected');
    if(row == null){
	   $.messager.alert('提示', '您选中您要编辑的记录');
    }
	
	var title = '请您编辑成员';
	c = [];
	c.push('<div id="update_org_member">');
	c.push('	<div>');
	c.push('		<form id="update_org_member_form" method="post">');
	c.push('			<input type="text" hidden name="userId" >');
//	c.push('			<input type="text" hidden name="orgMemInternalId" >');
	c.push('			<table class="dialogtable">');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>姓名:</td>');
	c.push('					<td><input type="text" id="update_org_member_user_name" style="width:180px"  name="colUserName"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>手机:</td>');
	c.push('					<td><input type="text" id="update_org_member_user_mobile" style="width:180px"  name="colUserMobile"  validType="nul"></td>');
	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>机构名称:</td>');
//	c.push('					<td>');
//	c.push('						<select id="update_org_member_org" name="orgId" class="easyui-combobox" required="true" >');
//	c.push('						</select>');
//	c.push('					</td>');
//	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>机构职务:</td>');
//	c.push('					<td>');
//	c.push('						<select id="update_org_member_pos" name="positionId" class="easyui-combobox"  required="true" >');
//	c.push('						</select>');
//	c.push('					</td>');
//	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td colspan="2">');
	c.push('						<div id="update_org_member_new_org_pos_div">');
	c.push('						</div>');
	c.push('					<td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td></td>');
	c.push('					<td><a href="#" id="update_org_member_new_org_pos_btn">新增机构及职务</a></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>公司:</td>');
	c.push('					<td><input type="text" id="update_org_member_user_company" name="colUserCompany" style="width:180px"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;行业:</td>');
	c.push('					<td><input type="text" id="update_org_member_user_industry" name="colUserIndustry" style="width:180px" ></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;公司职位:</td>');
	c.push('					<td><input type="text" id="update_org_member_user_job" name="colUserJob" style="width:180px" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>邮箱:</td>');
	c.push('					<td><input type="text" id="update_member_personEmail" name="colUserEmail" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>地址:</td>');
	c.push('					<td><input type="text" id="update_member_personAddress" name="colUserAddress" style="width:180px" validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受非好友消息:</td>');
	c.push('					<td>');
	c.push('						<select id="update_org_member_receive_msg" name="colReceiveMsgFlag" class="easyui-combobox"   validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否接受好友申请:</td>');
	c.push('					<td>');
	c.push('						<input id="update_org_member_friend_invite" name="colFriendInvite" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="update_org_member_show_detail_inner" name="colShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="update_org_member_show_detail_outter" name="colShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_org_member_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_org_member_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');


	$('#update_org_member').dialog('destroy');
	$('#main_org_member_manage').append(c.join(""));
	$('#update_org_member').dialog( {
		title : title,
		closed : false,
		width : 480,
		height : 580,
		modal : true
	});

	$('#update_org_member_user_name').validatebox( {
		required : true,
		missingMessage : '姓名不能为空'
	});
	
	$('#update_org_member_user_mobile').validatebox( {
		required : true,
		validType : ['mobile','uniqueMobile['+row.userId+']'],
		missingMessage : '请输入11位手机号'
	});

	$('#update_member_personEmail').validatebox( {
		validType : ['email']
	});
	
	//加载机构列表数据
	var orgList = null;
	$.ajax({  
		url:root + '/rs/org/queryOrgs',  
        type: "GET",  
        dataType: "json",  
        cache: true,  
        success: function (result) {  
        	orgList = result;
        }
    });  
	
	//加载机构列表数据
//	$('#update_org_member_org').combobox({   
//	    url:root + '/rs/common/queryOrgs4select',  
//	    valueField:'orgId',   
//	    textField:'colOrgName',
//	    width:150,
//	    onSelect:function(record){
//	    	var orgId = record.orgId;
//	    	$('#update_org_member_pos').combobox('setValue','');
//	    	//加载机构职位列表数据
//	    	$('#update_org_member_pos').combobox('reload',root + '/rs/common/queryPositionsByOrgId4select?orgId='+orgId);  
//	    }
//	}); 
//	
//	$('#update_org_member_pos').combobox({   
//	    url:root + '/rs/common/queryPositionsByOrgId4select?orgId='+row.orgId,   
//	    valueField:'positionId',   
//	    textField:'colPosName',
//	    width:150
//	});  
//	
//	$('#update_org_member_pos').combobox({   
//	    width:150
//	});  
	
	$('#update_org_member_receive_msg').combobox({   
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
	
	$('#update_org_member_friend_invite').combobox({   
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

	$('#update_org_member_show_detail_inner').combobox({   
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

	$('#update_org_member_show_detail_outter').combobox({   
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
	
	submit_clear('#update_org_member_submit', '#update_org_member_clear');
	$('#update_org_member_submit').click(function() {
		$('#update_org_member_form').form('submit', {
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
					$('#update_org_member').dialog('destroy');
					$('#select_org_member').datagrid('reload');
					showmessage('修改成员成功');
				} else {
					$.messager.alert('错误', '修改成员失败', 'error');
				}
			}
		});
	});
	
	$('#update_org_member_new_org_pos_btn').linkbutton();
	$("#update_org_member_new_org_pos_btn").click(function () {
		//动态创建机构、职位标签
		createNewPosSection('update_org_member_new_org_pos_div','update_org_member_new_org_pos_class','update_org_member');
	});
	
	$('#update_org_member_clear').click(function() {
		$("#creatememberform").form('clear'); 
		//加载表单数据
		loadOrgMemberFormData(row);
	});
	
	//加载表单数据
	loadOrgMemberFormData(row);
}


/**
 * 添加机构成员(已有前台用户，关联机构)
 */
function addOrgMember() {
	var title = '请您添加成员';
	c = [];
	c.push('<div id="add_org_member">');
	c.push('	<div>');
	c.push('		<form id="add_org_member_form" method="post">');
	c.push('			<table class="dialogtable">');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>关联用户:</td>');
	c.push('					<td><input type="text" id="add_org_member_relate_user_id" style="width:180px" name="userId" ></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构名称:</td>');
	c.push('					<td>');
	c.push('						<select id="add_org_member_org" name="orgId" class="easyui-combobox" required="true" >');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构职务:</td>');
	c.push('					<td>');
	c.push('						<select id="add_org_member_pos" name="positionId" class="easyui-combobox"  required="true" >');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td colspan="2">');
	c.push('						<div id="add_org_member_new_org_pos_div">');
	c.push('						</div>');
	c.push('					<td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td></td>');
	c.push('					<td><a href="#" id="add_org_member_new_org_pos_btn">新增机构及职务</a></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="add_org_member_show_detail_inner" name="colShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="add_org_member_show_detail_outter" name="colShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="add_org_member_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="add_org_member_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');


	$('#add_org_member').dialog('destroy');
	$('#main_org_member_manage').append(c.join(""));
	$('#add_org_member').dialog( {
		title : title,
		closed : false,
		width : 480,
		height : 350,
		modal : true
	});

	$('#add_org_member_relate_user_id').autocombobox({
		valueField:'userId',
		textField:'colUserMobile',
		url:root + '/rs/common/queryAppUsers4auto',
//		data : testData.org.appUsers,
		multiple:false,
		onChange: function(newValue,oldValue) {
			this.tmpValue = newValue[0];
		},
		required : true,
		missingMessage : '关联用户不能为空'
	});
	
	//加载机构列表数据
	var orgList = null;
	$.ajax({  
		url:root + '/rs/org/queryOrgs',  
        type: "GET",  
        dataType: "json",  
        cache: true,  
        success: function (result) {  
        	orgList = result;
        }
    });  
	
	//加载机构列表数据
	$('#add_org_member_org').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:150,
	    onSelect:function(record){
	    	var orgId = record.orgId;
	    	//加载机构职位列表数据
	    	$('#add_org_member_pos').combobox({   
	    	    url:root + '/rs/common/queryPositionsByOrgId4select?orgId='+orgId,   
	    	    valueField:'positionId',   
	    	    textField:'colPosName',
	    	    width:150
	    	});  
	    }
	});  
	
	$('#add_org_member_pos').combobox({   
	    width:150
	});  
	
	$('#add_org_member_receive_msg').combobox({   
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

	$('#add_org_member_show_detail_inner').combobox({   
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

	$('#add_org_member_show_detail_outter').combobox({   
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
	
	submit_clear('#add_org_member_submit', '#add_org_member_clear');
	$('#add_org_member_submit').click(function() {
		$('#add_org_member_form').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/orgMember/addOrgMember',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#add_org_member').dialog('destroy');
					$('#select_org_member').datagrid('reload');
					showmessage('创建成员成功');
				} else {
					$.messager.alert('错误', '创建成员失败', 'error');
				}
			}
		});
	});
	$('#add_org_member_clear').click(function() {
		$("#addmemberform").form('clear'); 
		$("#add_org_member_new_org_pos_div").remove();
	});
	
	$('#add_org_member_new_org_pos_btn').linkbutton();
	
	$("#add_org_member_new_org_pos_btn").click(function () {
		//动态创建机构、职位标签
		createNewPosSection('add_org_member_new_org_pos_div','add_org_member_new_org_pos_class','add_org_member');
	});

}


function loadOrgMemberFormData(row){

	$('#update_org_member_form').form('load',{
		userId:row.userId,
//		orgMemInternalId:row.orgMemInternalId,
		colUserName:row.colUserName,
		colUserMobile:row.colUserMobile,
		colUserEmail:row.colUserEmail,
		colUserAddress:row.colUserAddress,
		colUserCompany:row.colUserCompany,
		colUserIndustry:row.colUserIndustry,
		colUserJob:row.colUserJob,
		colReceiveMsgFlag:row.colReceiveMsgFlag,
		colFriendInvite:row.colFriendInvite,
//		orgId:row.orgId,
//		positionId:row.positionId,
		colShowDetailInner:row.colShowDetailInner,
		colShowDetailOutter:row.colShowDetailOutter
	});
	
//	$('#update_org_member_pos').combobox('reload',root + '/rs/common/queryPositionsByOrgId4select?orgId='+row.orgId);
	//动态加载机构职务标签
	loadOrgMemberSection(row,'update_org_member_new_org_pos_div','update_org_member_new_org_pos_btn','update_org_member_new_org_pos_class','update_org_member');
}

/**
 * 动态加载机构职务标签
 */
function loadOrgMemberSection(row,divId,btnId,jobListClass,opType){
	$("."+jobListClass).remove();
	
	//刷新机构职位label名称index
	refreshCount(jobListClass);
	
	var orgPoss = row.orgPositions;
	if(orgPoss != null){
		$.each(orgPoss,function(i,item){
			iOrgId = item.orgId;
			iPositionId = item.positionId;
			//激活update_org_member_new_org_pos点击时事件
			$("#"+btnId).click();
			//赋值
			var count = $("."+jobListClass).size();
			var orgTagIdName = opType + "_new_org_member_org_" + count;
			var posTagIdName = opType + "_new_org_member_pos_" + count;
			
			var orgObject = $('#'+orgTagIdName);
			var posObject = $('#'+posTagIdName);
			
			orgObject.combobox('setValue',iOrgId);
			
			var positionData;
			$.ajax({
				type: "post",
	            async: false,
	            url:root + '/rs/common/queryPositionsByOrgId4select?orgId='+iOrgId,   
	            dataType: "json",
	            contentType: "application/json;charset=utf-8",
	            success: function (json) {
	            	positionData = json;
	            }   
			});
			posObject.combobox({   
	    	    data : positionData,
	    	    valueField:'positionId',   
	    	    textField:'colPosName',
	    	    width:150,
	    	    onLoadSuccess:function(data){
	    	    	posObject.combobox('setValue',iPositionId);
	    	    }
	    	});
		});
	}
}

/**
 * 创建新的机构、职位标签
 */
function createNewPosSection(divId,jobListClass,opType){
	var count = $("."+jobListClass).size();
	var orgTagIdName = opType + "_new_org_member_org_" + (count + 1);
	var posTagIdName = opType + "_new_org_member_pos_" + (count + 1);
	
	$("#"+divId).append("" +
		"<div class='"+jobListClass+"'>" +
			"<span class='joblistclose'><img src='images/icon_close.png'></span>" +
			"<div class='jobrow'>" +
				"<span class='joblisttitle'>机构名称" + (count + 1) + "：</span>" +
				"<select id='"+orgTagIdName+"' name='orgId' required='true'>" +
				"</select>" +
			"</div>" +
			"<div class='jobrow'>" +
				"<span class='joblisttitle'>机构职务" + (count + 1) + "：</span>" +
				"<select id='"+posTagIdName+"' name='positionId' required='true'>" +
				"</select>" +
			"</div>" +
		"</div>");
	
	//加载机构数据
	$("#"+orgTagIdName).combobox({   
		url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:150,
	    onSelect:function(record){
	    	var orgId = record.orgId;
	    	//加载机构职位列表数据
	    	$("#"+posTagIdName).combobox({   
	    	    url:root + '/rs/common/queryPositionsByOrgId4select?orgId='+orgId,   
	    	    valueField:'positionId',   
	    	    textField:'colPosName',
	    	    width:150
	    	});  
	    }
	});  
	//初始化职位标签
	$("#"+posTagIdName).combobox({   
	    width:150
	});

	$(document).delegate(".joblistclose", "click", function () {
		$(this).parent("."+jobListClass).remove();
		refreshCount(jobListClass);
	});
}

/**
 * 刷新机构职位label名称index
 * @param jobListClass
 */
function refreshCount(jobListClass) {

	$("."+jobListClass).each(function (i) {

		var self = $(this);
		self.find(".joblisttitle:eq(0)").text("机构名称" + (i + 1) + "：");
		self.find(".joblisttitle:eq(1)").text("机构职务" + (i + 1) + "：");
	});
}

/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getOrgMemberToolBar(permission) {
	var toolBars = [];
	toolBars[0] = {
		text : "创建成员",
		iconCls : "icon-add",
		handler : createOrgMember
	};
	toolBars[1] = {
		text : "编辑成员",
		iconCls : "icon-edit",
		handler : updateOrgMember
	};
	toolBars[2] = {
			text : "添加成员",
			iconCls : "icon-save",
			handler : addOrgMember
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	
	var showActParticipantsBar = {
		text : "查看机构职务",
		iconCls : "icon-search",
		handler : showOrgPoss	
	};
	
	permToolBars.push(showActParticipantsBar);
	
	return permToolBars;
}

/**
 * 成员所属机构、职位formatter
 * @param value
 * @param row
 */
function orgPossFormatter(value,row){
	
	var result = '<a onclick="showOrgPoss()" style="color:blue">查看</a>';
	
	return result;
}

/**
 * 显示成员机构职位列表
 * @param orgPositions
 */
function showOrgPoss(){
	var c;
	var title='查看机构职务列表';
	var row;
	var orgPositions;
	
	row = $('#select_org_member').datagrid('getSelected');
	if(row != null){
		orgPositions = row.orgPositions;
	}
	
	c = [];
	c.push('<div id="select_org_member_show_org_poss_dialog" >');
	c.push('	<div id="select_org_member_show_org_poss"></div>');
	c.push('</div>');

	$('#select_org_member_show_org_poss_dialog').dialog('destroy');
	$('#main_org_member_manage').append(c.join(""));
	$('#select_org_member_show_org_poss_dialog').dialog( {
		title : title,
		closed : false,
		width : 400,
		height: 500,
		modal : true
	});

	$('#select_org_member_show_org_poss').datagrid( {
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
		data : orgPositions,
		idField : 'orgId',
		columns : [ [ {
			field : 'colOrgCode',
			title : '机构代码'
		}, {
			field : 'colOrgName',
			title : '机构名称'
		}, {
			field : 'colPosName',
			title : '职务'
		}] ]
	});

	var p = $('#select_org_member_show_org_poss').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

