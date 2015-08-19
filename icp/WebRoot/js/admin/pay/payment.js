function paymentManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;支付方式:</td>');
	c.push('						<td>');
	c.push('							<input id="payment_account" type="text" placeholder="请输入要查询的支付账号" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="querypayment_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_payment"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_paymentManage', tabTitle, c.join(""));

	queryBtn('#querypayment_search');

	$('#querypayment_search').click(function() {
		var colPayAccount = $('#payment_account').val();
		$('#select_payment').datagrid('load', {
			colPayAccount : colPayAccount
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getPaymentToolBar(permission);
	$('#select_payment').datagrid({
		iconCls : 'icon-save',
		fit : true,
		nowrap : true,
		striped : true,
		autoRowHeight : true,
		fitColumns : false,
		striped : true,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : paramconfig.page.size,
		pageList : paramconfig.page.list,
		url : root + '/rs/pay/queryPayments',
		idField : 'payId',
		columns : [ [ {
			field : 'colPaymentType',
			title : '支付方式',
			sortable : true,
			formatter : showPayFormatter
		}, {
			field : 'colPayAccount',
			title : '账户',
			sortable : true
		}, {
			field : 'colPayPsw',
			title : '安全校验码',
			sortable : true
		} , {
			field : 'colCode',
			title : '序列号',
			sortable : true
		} ] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询支付方式失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

	var p = $('#select_payment').datagrid('getPager');
	$(p).pagination({
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}

/**
 * 创建支付方式
 */
function createPayment() {
	var title = '请您创建支付方式';
	c = [];
	c.push('<div id="create_payment">');
	c.push('	<div>');
	c.push('		<form id="createpaymentform" method="post" enctype="multipart/form-data">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>支付方式:</td>');
	c.push('					<td ><input type="text" id="create_payment_id" style="width:180px"  name="colPaymentType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>支付账号:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_payment_account" style="width:180px"  name="colPayAccount" validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>支付密码:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_payment_password" style="width:180px"  name="colPayPsw" validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000"></font>账号代码:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_code" style="width:180px"  name="colCode" validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_payment_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_payment_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_payment').dialog('destroy');
	$('#main_paymentManage').append(c.join(""));
	$('#create_payment').dialog({
		title : title,
		closed : false,
		width : 610,
		modal : true
	});

	$('#create_payment_id').combobox({
		required : true,
		missingMessage : '支付方式不能为空',
		valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=7'
	});
	$('#create_payment_account').validatebox({
		required : true,
		missingMessage : '支付账号不能为空'
	});
	$('#create_payment_password').validatebox({
		required : true,
		missingMessage : '安全验证码不能为空'
	});

	/*$('#create_payment_id').combobox({
		width : 180,
		panelHeight : 40,
		valueField : 'value',
		textField : 'text',
		value : "点击选择支付方式",
		data : [ {
			"value" : "1",
			"text" : "支付宝"
		}, {
			"value" : "2",
			"text" : "微信支付"
		} ]
	});*/

	submit_clear('#create_payment_submit', '#create_payment_clear');
	$('#create_payment_submit').click(function() {
		$('#createpaymentform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				;
				$.messager.progress({
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/pay/createPayment',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("(" + data + ")");
				if (result.code == 1) {
					$('#create_payment').dialog('destroy');
					$('#select_payment').datagrid('reload');
					showmessage('创建支付方式成功');
				} else {
					$.messager.alert('错误', '创建支付方式失败', 'error');
				}
			}
		});
	});
	$('#create_payment_clear').click(function() {
		$("#createpaymentform").form('clear');
	});
}

/**
 * 删除支付方式
 */

function removePayment() {
	var row = $('#select_payment').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', "请选中您要操作的记录！");
		return;
	}
	if (row.colPaymentId == '3') {
		$.messager.alert('提示', "这条记录你不能删除！");
		return;
	} else {
		$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
			if (flag) {
				$.ajax({
					type : 'POST',
					url : root + '/rs/pay/removePayment',
					data : {
						"payId" : row.payId
					},
					dataType : 'json',
					success : function(data) {
						if (data.code == '1') {
							reloadDatagrid('#select_payment');
							showmessage("删除成功！");
						} else {
							$('#select_admin_user').datagrid('reload');
							$.messager.alert("提示", "删除失败！", 'error');
						}
					}
				});
			}
		});
	}
}

/**
 * 修改支付方式
 */
function updatePayment() {

	var row = $('#select_payment').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', "请选中您要操作的记录！");
		return;
	}
	if (row.colPaymentId == '3') {
		$.messager.alert('提示', "这条记录你不修改！");
		return;
	} else {

		var title = '请您修改支付方式';
		c = [];
		c.push('<div id="update_payment">');
		c.push('	<div>');
		c.push('		<form id="updatepaymentform" method="post" enctype="multipart/form-data">');
		c.push(' 			<input type="hidden" name="payId">');
		c.push('			<table>');
		c.push('				<tr>');
		c.push('					<td class="ltl1"><font color="#dd0000">*</font>支付方式:</td>');
		c.push('					<td ><input type="text" id="update_payment_id" style="width:180px"  name="colPaymentType" /></td>');
		c.push('				</tr>');
		c.push('				<tr>');
		c.push('					<td class="ltl1"><font color="#dd0000">*</font>支付账号:</td>');
		c.push('					<td colspan="2"><input type="text" id="update_payment_account" style="width:180px"  name="colPayAccount" ></input></td>');
		c.push('				</tr>');
		c.push('				<tr>');
		c.push('					<td class="ltl1"><font color="#dd0000">*</font>支付密码:</td>');
		c.push('					<td colspan="2"><input type="text" id="update_payment_password" style="width:180px"  name="colPayPsw" ></input></td>');
		c.push('				</tr>');
		c.push('				<tr>');
		c.push('					<td class="ltl1"><font color="#dd0000"></font>账号代码:</td>');
		c.push('					<td colspan="2"><input type="text" id="update_code" style="width:180px"  name="colCode" ></input></td>');
		c.push('				</tr>');
		c.push('			</table>');
		c.push('		</form> ');
		c.push('	</div>');
		c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
		c.push('		<center>');
		c.push('			<a href="#" id="update_payment_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
		c.push('			<a href="#" id="update_payment_clear"><strong>重置</strong>&nbsp;</a>');
		c.push('		</center>');
		c.push('	</div>');
		c.push('</div>');

		$('#update_payment').dialog('destroy');
		$('#main_paymentManage').append(c.join(""));
		$('#update_payment').dialog({
			title : title,
			closed : false,
			width : 610,
			modal : true
		});

		$('#update_payment_id').combobox({
			required : true,
			missingMessage : '支付方式不能为空',
			valueField:'colDictVal',   
		    textField:'colDictText',
		    url : root + '/rs/common/queryDict4select?colDictClass=7'
		});
		$('#update_payment_account').validatebox({
			required : true,
			missingMessage : '支付账号不能为空'
		});
		$('#update_payment_password').validatebox({
			required : true,
			missingMessage : '安全验证码不能为空'
		});

/*		$('#update_payment_id').combobox({
			width : 180,
			panelHeight : 40,
			valueField : 'value',
			textField : 'text',
			value : "点击选择支付方式",
			data : [ {
				"value" : "1",
				"text" : "支付宝"
			}, {
				"value" : "2",
				"text" : "微信支付"
			} ]
		});*/

		submit_clear('#update_payment_submit', '#update_payment_clear');
		$('#update_payment_submit').click(function() {
			$('#updatepaymentform').form('submit', {
				onSubmit : function() {
					if (!$(this).form('validate')) {
						return false;
					}
					;
					$.messager.progress({
						text : '正在提交...'
					});
					return true;
				},
				url : root + '/rs/pay/updatePayment',
				success : function(data) {
					$.messager.progress('close');
					var result = eval("(" + data + ")");
					if (result.code == 1) {
						$('#update_payment').dialog('destroy');
						$('#select_payment').datagrid('reload');
						showmessage('更新支付方式成功');
					} else {
						$.messager.alert('错误', '更新支付方式失败', 'error');
					}
				}
			});
		});
		$('#update_payment_clear').click(function() {
			$("#updatepaymentform").form('clear');
			$("#updatepaymentform").form('load',row);
		});

		// 加载表单数据
		loadpaymentFormData(row);
	}
}

/**
 * 加载表单数据
 * 
 * @param row
 */

function loadpaymentFormData(row) {
	$("#updatepaymentform").form('load', {
		payId : row.payId,
		colPaymentType : row.colPaymentType,
		colPayAccount : row.colPayAccount,
		colPayPsw : row.colPayPsw,
		colCode : row.colCode
	});
}
/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getPaymentToolBar(permission) {

	var toolBars = [];
	toolBars[0] = {
		text : "创建支付方式",
		iconCls : "icon-add",
		handler : createPayment
	};
	toolBars[1] = {
		text : "删除支付方式",
		iconCls : "icon-remove",
		handler : removePayment
	};
	toolBars[2] = {
		text : "编辑支付方式",
		iconCls : "icon-edit",
		handler : updatePayment
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}
function showPayFormatter(value,row){
	var payTypeName = row.colPayTypeName;
	return payTypeName;
}
