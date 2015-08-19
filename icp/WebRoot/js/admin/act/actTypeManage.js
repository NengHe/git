function actTypeManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_act_type"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_actTypeManage', tabTitle, c.join(""));

	

	// 为datagrid添加工具条
	var _toolbar = getActTypeToolBar(permission);
	$('#select_act_type').datagrid( {
		iconCls : 'icon-save',
		fit : true,
		nowrap : true,
		striped : true,
		autoRowHeight : true,
		fitColumns : true,
		striped : true,
		singleSelect : true,
//		rownumbers : true,
//		pagination : true,
//		pageSize : paramconfig.page.size,
//		pageList : paramconfig.page.list,
		url : root + '/rs/common/queryIndexs?colLabelClass=2',
		idField : 'dictId',
		columns : [ [ {
			field : 'colLabelClass',
			title : '类型',
			formatter : labelClassFormatter
		},{
			field : 'colLabelText',
			title : '名称',
			width : 50
		},{
			field : 'colDelFlag',
			title : '状态',
			width : 50,
			formatter : showDelFlagFormatter
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询活动类别失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

//	var p = $('#select_act_type').datagrid('getPager');
//	$(p).pagination( {
//		beforePageText : '第',
//		afterPageText : '页    共 {pages} 页',
//		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//	});

}


/**
 * 创建活动
 */
function createActType(){
	var title = '请您创建活动类别';
	c = [];
	c.push('<div id="create_act_type">');
	c.push('	<div>');
	c.push('		<form id="createacttypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="colLabelClass" value="2">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>名称:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_act_type_text" style="width:95%"  name="colLabelText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px">状态:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_act_type_flag" style="width:95%"  name="colDelFlag"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_act_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_act_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_act_type').dialog('destroy');
	$('#main_actTypeManage').append(c.join(""));
	$('#create_act_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#create_act_type_text').validatebox( {
		required : true,
		missingMessage : '活动类别名称不能为空'
	});
	
	$('#create_act_type_flag').combobox({   
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
	
	submit_clear('#create_act_type_submit', '#create_act_type_clear');
	$('#create_act_type_submit').click(function() {
		$('#createacttypeform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/common/createIndex',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_act_type').dialog('destroy');
					$('#select_act_type').datagrid('reload');
					showmessage('创建活动类别');
				} else {
					$.messager.alert('错误', '创建活动类别失败', 'error');
				}
			}
		});
	});
	$('#create_act_type_clear').click(function() {
		$("#createacttypeform").form('clear'); 
	});
}


function removeActType(){
	var row = $('#select_act_type').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/common/removeIndex',
				 data: {"dictId":row.dictId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_act_type');
						showmessage("删除成功！");
					} else {
						$('#select_act_type').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

function updateActType(){
	var row = $("#select_act_type").datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您编辑活动类别';
	c = [];
	c.push('<div id="update_act_type">');
	c.push('	<div>');
	c.push('		<form id="updateacttypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="dictId">');
	c.push(' 			<input type="hidden" name="colLabelClass">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>名称:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_act_type_text" style="width:95%"  name="colLabelText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px">状态:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_act_type_flag" style="width:95%"  name="colDelFlag"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_act_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_act_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_act_type').dialog('destroy');
	$('#main_actTypeManage').append(c.join(""));
	$('#update_act_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#update_act_type_text').validatebox( {
		required : true,
		missingMessage : '活动类别名称不能为空'
	});
	
	$('#update_act_type_flag').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		data:[{
			"value":"0",
			"text":"启用"
		},{
			"value":"1",
			"text":"停用"
		}]
	});
	
	submit_clear('#update_act_type_submit', '#update_act_type_clear');
	$('#update_act_type_submit').click(function() {
		$('#updateacttypeform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url :root + '/rs/common/updateIndex',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_act_type').dialog('destroy');
					$('#select_act_type').datagrid('reload');
					showmessage('编辑类别成功');
				} else {
					$.messager.alert('错误', '编辑活动类别失败', 'error');
				}
			}
		});
	});
	$('#update_act_type_clear').click(function() {
		$("#updateacttypeform").form('clear'); 
		
		loadActTypeFormData(row);
	});
	
	//加载表单数据
	loadActTypeFormData(row);
}



/**
 * 加载活动表单数据
 * @param row
 * @returns
 */
function loadActTypeFormData(row){
	$("#updateacttypeform").form('load',{
		dictId : row.dictId,
		colLabelClass : row.colLabelClass,
		colLabelText : row.colLabelText,
		colDelFlag : row.colDelFlag
	}); 
	
}
/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getActTypeToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建活动类别",
		iconCls : "icon-add",
		handler : createActType
	};
	toolBars[1] = {
		text : "删除活动类别",
		iconCls : "icon-remove",
		handler : removeActType
	};
	toolBars[2] = {
		text : "编辑活动类别",
		iconCls : "icon-edit",
		handler : updateActType
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}
