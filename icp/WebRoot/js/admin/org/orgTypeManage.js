function orgTypeManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_org_type"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_orgTypeManage', tabTitle, c.join(""));

	

	// 为datagrid添加工具条
	var _toolbar = getorgTypeToolBar(permission);
	$('#select_org_type').datagrid( {
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
		url : root + '/rs/common/queryDicts?colDictClass=1',
		idField : 'dictId',
		columns : [ [ 
//		              {
//			field : 'colDictVal',
//			title : '序列值',
//		},
		{
			field : 'colDictText',
			title : '机构类型名称',
			
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询机构类型失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

//	var p = $('#select_org_type').datagrid('getPager');
//	$(p).pagination( {
//		beforePageText : '第',
//		afterPageText : '页    共 {pages} 页',
//		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//	});

}


/**
 * 创建机构
 */
function createOrgType(){
	var title = '请您创建机构类别';
	c = [];
	c.push('<div id="create_org_type">');
	c.push('	<div>');
	c.push('		<form id="createorgtypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="colDictClass" value="1">');
	c.push('			<input type="text" id="create_org_type_val" readonly hidden name="colDictVal" />');
	c.push('			<table>');
//	c.push('				<tr>');
//	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>序列:</td>');
//	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>机构类型名称:</td>');
	c.push('					<td><input type="text" id="create_org_type_text" style="width:180px"  name="colDictText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_org_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_org_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_org_type').dialog('destroy');
	$('#main_orgTypeManage').append(c.join(""));
	$('#create_org_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#create_org_type_text').validatebox( {
		required : true,
		missingMessage : '机构类型名称不能为空'
	});
	
//	$('#create_org_type_val').numberbox( {
//		 required : true,
//		 missingMessage : '序列必须为正整数'
//	});
	
	submit_clear('#create_org_type_submit', '#create_org_type_clear');
	$('#create_org_type_submit').click(function() {
		$('#createorgtypeform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/common/createDict',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_org_type').dialog('destroy');
					$('#select_org_type').datagrid('reload');
					showmessage('创建机构类型');
				} else {
					$.messager.alert('错误', '创建机构类型失败', 'error');
				}
			}
		});
	});
	$('#create_org_type_clear').click(function() {
		$("#createorgtypeform").form('clear'); 
	});
	
	 $.ajax({
    	 type: 'POST',
    	 url: root + '/rs/common/getDictMaxVal',
		 data: {
			 "colDictClass" : 1
		 },
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				var typeVal = data.result;
				$('#create_org_type_val').val(typeVal);
			} 
		}
	});
	
}


function removeOrgType(){
	var row = $('#select_org_type').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/common/removeDict',
				 data: {"dictId":row.dictId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_org_type');
						showmessage("删除成功！");
					} else {
						$('#select_org_type').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

function updateOrgType(){
	var row = $("#select_org_type").datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您编辑机构类型';
	c = [];
	c.push('<div id="update_org_type">');
	c.push('	<div>');
	c.push('		<form id="updateorgtypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="dictId">');
	c.push(' 			<input type="hidden" name="colDictClass">');
	c.push('			<table>');
//	c.push('				<tr>');
//	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>序列值:</td>');
//	c.push('					<td><input type="text" id="update_org_type_val" readonly style="width:180px"  name="colDictVal" class="easyui-numberbox" min="1"></td>');
//	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>机构类型名称:</td>');
	c.push('					<td><input type="text" id="update_org_type_text" style="width:180px"  name="colDictText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_org_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_org_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_org_type').dialog('destroy');
	$('#main_orgTypeManage').append(c.join(""));
	$('#update_org_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#update_org_type_text').validatebox( {
		required : true,
		missingMessage : '机构类型名称不能为空'
	});
	
	$('#update_org_type_val').numberbox( {
		 required : true,
		 missingMessage : '序列必须为正整数'
	});
	
	submit_clear('#update_org_type_submit', '#update_org_type_clear');
	$('#update_org_type_submit').click(function() {
		$('#updateorgtypeform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url :root + '/rs/common/updateDict',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_org_type').dialog('destroy');
					$('#select_org_type').datagrid('reload');
					showmessage('编辑类别成功');
				} else {
					$.messager.alert('错误', '编辑机构类别失败', 'error');
				}
			}
		});
	});
	$('#update_org_type_clear').click(function() {
		$("#updateorgtypeform").form('clear'); 
		
		loadorgTypeFormData(row);
	});

	//加载表单数据
	loadorgTypeFormData(row);
}



/**
 * 加载机构表单数据
 * @param row
 * @returns
 */
function loadorgTypeFormData(row){
	$("#updateorgtypeform").form('load',{
		dictId : row.dictId,
		colDictClass : row.colDictClass,
		colDictText : row.colDictText,
		colDictVal : row.colDictVal
	}); 
	
}
/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getorgTypeToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建机构类型",
		iconCls : "icon-add",
		handler : createOrgType
	};
	toolBars[1] = {
		text : "删除机构类型",
		iconCls : "icon-remove",
		handler : removeOrgType
	};
	toolBars[2] = {
		text : "编辑机构类型",
		iconCls : "icon-edit",
		handler : updateOrgType
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
	
}
