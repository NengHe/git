function payTypeManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_pay_type"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_payTypeManage', tabTitle, c.join(""));

	

	// 为datagrid添加工具条
	var _toolbar = getpayTypeToolBar(permission);
	$('#select_pay_type').datagrid( {
		iconCls : 'icon-save',
		fit : true,
		nowrap : true,
		striped : true,
		autoRowHeight : true,
		fitColumns : true,
		striped : true,
		singleSelect : true,
		url : root + '/rs/pay/queryPayType?colDictClass=7',
		idField : 'dictId',
		columns : [ [ 
		{
			field : 'colDictText',
			title : '支付类型名称',
			
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询支付类型失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

}


/**
 * 创建机构
 */
function createPayType(){
	var title = '请您创建机构类别';
	c = [];
	c.push('<div id="create_pay_type">');
	c.push('	<div>');
	c.push('		<form id="createpaytypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="colDictClass" value="7">');
	c.push('			<input type="text" id="create_pay_type_val" readonly hidden name="colDictVal" />');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>支付类型名称:</td>');
	c.push('					<td><input type="text" id="create_pay_type_text" style="width:180px"  name="colDictText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_pay_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_pay_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_pay_type').dialog('destroy');
	$('#main_payTypeManage').append(c.join(""));
	$('#create_pay_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#create_pay_type_text').validatebox( {
		required : true,
		missingMessage : '支付类型名称不能为空'
	});
		
	submit_clear('#create_pay_type_submit', '#create_pay_type_clear');
	$('#create_pay_type_submit').click(function() {
		$('#createpaytypeform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/pay/createPayType',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_pay_type').dialog('destroy');
					$('#select_pay_type').datagrid('reload');
					showmessage('创建支付类型');
				} else {
					$.messager.alert('错误', '创建支付类型失败', 'error');
				}
			}
		});
	});
	$('#create_pay_type_clear').click(function() {
		$("#createpaytypeform").form('clear'); 
	});
	
	 $.ajax({
    	 type: 'POST',
    	 url: root + '/rs/common/getDictMaxVal',
		 data: {
			 "colDictClass" : 7
		 },
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				var typeVal = data.result;
				$('#create_pay_type_val').val(typeVal);
			} 
		}
	});
	
}


function removePayType(){
	var row = $('#select_pay_type').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/pay/removePayType',
				 data: {"dictId":row.dictId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_pay_type');
						showmessage("删除成功！");
					} else {
						$('#select_pay_type').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

function updatePayType(){
	var row = $("#select_pay_type").datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您编辑支付类型';
	c = [];
	c.push('<div id="update_pay_type">');
	c.push('	<div>');
	c.push('		<form id="updatepaytypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="dictId">');
	c.push(' 			<input type="hidden" name="colDictClass">');
	c.push(' 			<input type="hidden" name="colDictVal">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>机构类型名称:</td>');
	c.push('					<td><input type="text" id="update_pay_type_text" style="width:180px"  name="colDictText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_pay_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_pay_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_pay_type').dialog('destroy');
	$('#main_payTypeManage').append(c.join(""));
	$('#update_pay_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#update_pay_type_text').validatebox( {
		required : true,
		missingMessage : '支付类型名称不能为空'
	});
	

	submit_clear('#update_pay_type_submit', '#update_pay_type_clear');
	$('#update_pay_type_submit').click(function() {
		$('#updatepaytypeform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url :root + '/rs/pay/updatePayType',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_pay_type').dialog('destroy');
					$('#select_pay_type').datagrid('reload');
					showmessage('编辑类别成功');
				} else {
					$.messager.alert('错误', '编辑支付类别失败', 'error');
				}
			}
		});
	});
	$('#update_pay_type_clear').click(function() {
		$("#updatepaytypeform").form('clear'); 
		
		loadpayTypeFormData(row);
	});

	//加载表单数据
	loadpayTypeFormData(row);
}



/**
 * 加载机构表单数据
 * @param row
 * @returns
 */
function loadpayTypeFormData(row){
	$("#updatepaytypeform").form('load',{
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
function getpayTypeToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建支付类型",
		iconCls : "icon-add",
		handler : createPayType
	};
	toolBars[1] = {
		text : "删除支付类型",
		iconCls : "icon-remove",
		handler : removePayType
	};
	toolBars[2] = {
		text : "编辑支付类型",
		iconCls : "icon-edit",
		handler : updatePayType
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
	
}
