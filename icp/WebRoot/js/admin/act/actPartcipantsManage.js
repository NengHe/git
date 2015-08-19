function actParticipantsManage(activityId) {
	var title;
	var queryParams;
	var c;
	var _toolbar;
	
	title = '活动参与列表';
	c = [];
	c.push('<div  id="act_participants_manage" >');
	c.push('	<div id="select_act_participants"></div>');
	c.push('</div>');

	$('#act_participants_manage').dialog('destroy');
	$('#main_activityManage').append(c.join(""));
	$('#act_participants_manage').dialog( {
		title : title,
		closed : false,
		width : 800,
		height: 600,
		modal : true
	});

	// 为datagrid添加工具条
	_toolbar = getActParticipantsToolBar();
	
	queryParams = {
		activityId:activityId
	};
	
	$('#select_act_participants').datagrid( {
		iconCls : 'icon-save',
		nowrap : true,
		striped : true,
		fit:true,
		autoRowHeight : true,
		fitColumns : true,
		striped : true,
		singleSelect : true,
//		rownumbers : true,
//		pagination : true,
//		pageSize : paramconfig.page.size,
//		pageList : paramconfig.page.list,
		url : root + '/rs/activity/queryActParticipants',
		queryParams: queryParams,
		idField : 'colUserActivityId',
		columns : [ [ {
			field : 'colCreateTime',
			title : '申请时间',
			formatter : longToDateFormatter
		}, {
			field : 'colUserName',
			title : '用户姓名'
		}, 
//		{
//			field : 'colOrgCode',
//			title : '机构代码'
//		}, {
//			field : 'colOrgName',
//			title : '机构名称'
//		}, 
		{
			field : 'colUserMobile',
			title : '手机'
		}, {
			field : 'colUserEmail',
			title : '邮箱'
		}, {
			field : 'colUserAddress',
			title : '地址'
		}, {
			field : 'colUserCompany',
			title : '公司'
		}, {
			field : 'colAuthStatus',
			title : '审核状态',
			formatter : showAuditFormatter,
			align : 'center'
		}, {
			field : 'colPaymentStatus',
			title : '支付状态',
			formatter : paymentStatusFormatter
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询参与列表失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

//	var p = $('#select_act_participants').datagrid('getPager');
//	$(p).pagination( {
//		beforePageText : '第',
//		afterPageText : '页    共 {pages} 页',
//		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//	});

}

/**
 * 审核通过
 */
function auditActJoinPass(){
	var row = $('#select_act_participants').datagrid('getSelected');
	var authStstus;
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	authStatus = row.colAuthStatus;
	if(authStatus != '0'){
		$.messager.alert('提示', '请选择状态为申请审核的记录！');
		return ;
	}
	
    $.ajax({
    	 type: 'POST',
    	 url: root + '/rs/activity/auditActJoinPass',
		 data: {"colUserActivityId":row.colUserActivityId},
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				reloadDatagrid('#select_act_participants');
				showmessage("操作成功！");
			} else if(data.code == '-2'){
				$('#select_act_participants').datagrid('reload');
				$.messager.alert("提示","用户已加入该活动，请勿重复处理申请操作！",'error');
			} else {
				$('#select_act_participants').datagrid('reload');
				$.messager.alert("提示", "操作失败！",'error');
			}
		}
	});
}

/**
 * 审核不通过
 */
function auditActJoinReject(){
	var row = $('#select_act_participants').datagrid('getSelected');
	var authStstus;
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	authStatus = row.colAuthStatus;
	if(authStatus != '0'){
		$.messager.alert('提示', '请选择状态为申请待审的记录！');
		return ;
	}
	
    $.ajax({
    	 type: 'POST',
		 url: root + '/rs/activity/auditActJoinReject',
		 data: {"colUserActivityId":row.colUserActivityId},
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				reloadDatagrid('#select_act_participants');
				showmessage("操作成功！");
			} else if(data.code == '-2'){
				$('#select_act_participants').datagrid('reload');
				$.messager.alert("提示","用户已加入该活动，请勿重复处理申请操作！",'error');
			} else {
				$('#select_act_participants').datagrid('reload');
				$.messager.alert("提示", "操作失败！",'error');
			}
		}
	});
}

/**
 * 取消确认审核
 */
function auditActJoinCancel(){
	var row = $('#select_act_participants').datagrid('getSelected');
	var authStstus;
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	authStatus = row.colAuthStatus;
	if(authStatus != '3'){
		$.messager.alert('提示', '请选择状态为取消待审的记录！');
		return ;
	}
	
	$.ajax({
		type: 'POST',
		url: root + '/rs/activity/auditActJoinReject',
		data: {"colUserActivityId":row.colUserActivityId},
		dataType: 'json',
		success: function(data){
			if(data.code == '1'){
				reloadDatagrid('#select_act_participants');
				showmessage("操作成功！");
			} else if(data.code == '-2'){
				$('#select_act_participants').datagrid('reload');
				$.messager.alert("提示","用户已加入该活动，请勿重复处理申请操作！",'error');
			} else {
				$('#select_act_participants').datagrid('reload');
				$.messager.alert("提示", "操作失败！",'error');
			}
		}
	});
}

/**
 * 删除
 */
function removeActParticipants(){
	var row = $('#select_act_participants').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/activity/removeActParticipants',
				 data: {"colUserActivityId":row.colUserActivityId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_act_participants');
						showmessage("删除成功！");
					} else {
						$('#select_act_participants').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

/**
 * 收款
 */
function collection(){
	var row = $('#select_act_participants').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	var colPaymentStatus = row.colPaymentStatus;
	if(colPaymentStatus != '0'){
		$.messager.alert('提示', '请选择未付款的记录！');
		return ;
	}
	
	var title;
	var queryParams;
	var c;
	var _toolbar;
	
	title = '请选择退款支付方式';
	c = [];
	c.push('<div  id="collection_act_participants" >');
	c.push('	<div>');
	c.push('		<form id="collection_act_parti_form" method="post" >');
	c.push('			<input type="hidden" name="colUserActivityId" />');
	c.push('			<input type="hidden" name="colUserId" />');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>收款方式:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="collection_act_parti_pay_type" style="width:180px" name="colPaymentType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>收款账号:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="collection_act_parti_receiver_account" style="width:260px" name="colReceiverAccount" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>支付账号:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="collection_act_parti_pay_account" style="width:260px" name="colPayAccount" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>收款金额:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="collection_act_parti_money" style="width:260px" name="colMoney" /></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:0px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="collection_act_parti_pay_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="collection_act_parti_pay_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');
	
	$('#collection_act_participants').dialog('destroy');
	$('#act_participants_manage').append(c.join(""));
	$('#collection_act_participants').dialog( {
		title : title,
		closed : false,
		width : 360,
		modal : true
	});
	
	$('#collection_act_parti_pay_type').combobox({   
		width:180,
		required: true, 
		valueField:'colDictVal',   
		textField:'colDictText',
		url : root + '/rs/common/queryDict4select?colDictClass=7',
		onSelect:function(record){
			var paymentType = record.colDictVal;
			$('#collection_act_parti_receiver_account').combobox({
				width:260,
				required: true, 
				valueField:'colPayAccount',   
				textField:'colPayAccount',
				url : root + '/rs/common/queryPaymentByPaymentId4select?colPaymentType=' + paymentType
			});
		}
	});
	
	submit_clear('#collection_act_parti_pay_submit', '#collection_act_parti_pay_clear');
	$('#collection_act_parti_pay_submit').click(function() {
		$('#collection_act_parti_form').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/activity/collection',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#collection_act_participants').dialog('destroy');
					$('#select_act_participants').datagrid('reload');
					showmessage('退款成功');
				} else {
					$.messager.alert('错误', '退款失败', 'error');
				}
			}
		});
	});
	
	$('#collection_act_parti_pay_clear').click(function() {
		$("#collection_act_parti_form").form('clear'); 
	});
	
	//加载参数
	loadActCollectionFormData(row);
	
}

/**
 * 退款
 */
function refund(){
	var row = $('#select_act_participants').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	var colPaymentStatus = row.colPaymentStatus;
	if(colPaymentStatus != '1'){
		$.messager.alert('提示', '请选择已付款的记录！');
		return ;
	}
	
	var title;
	var queryParams;
	var c;
	var _toolbar;
	
	title = '请选择退款支付方式';
	c = [];
	c.push('<div  id="refund_act_participants" >');
	c.push('	<div>');
	c.push('		<form id="refund_act_parti_form" method="post" >');
	c.push('			<input type="hidden" name="colUserActivityId" />');
	c.push('			<input type="hidden" name="colUserId" />');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>支付方式:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="refund_act_parti_pay_type" style="width:180px" name="colPaymentType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>支付账号:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="refund_act_parti_pay_account" style="width:260px" name="colPayAccount" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>收款账号:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="refund_act_parti_receiver_account" style="width:260px" name="colReceiverAccount" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>退款金额:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="refund_act_parti_money" style="width:260px" name="colMoney" /></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:0px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="refund_act_parti_pay_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="refund_act_parti_pay_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#refund_act_participants').dialog('destroy');
	$('#act_participants_manage').append(c.join(""));
	$('#refund_act_participants').dialog( {
		title : title,
		closed : false,
		width : 360,
		modal : true
	});
	
	$('#refund_act_parti_pay_type').combobox({   
	    width:180,
	    required: true, 
	    valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=7',
	    onSelect:function(record){
	    	var paymentType = record.colDictVal;
	    	$('#refund_act_parti_pay_account').combobox({
	    		width:260,
	    	    required: true, 
	    	    valueField:'colPayAccount',   
	    	    textField:'colPayAccount',
	    	    url : root + '/rs/common/queryPaymentByPaymentId4select?colPaymentType=' + paymentType
	    	});
	    }
	});
	
	submit_clear('#refund_act_parti_pay_submit', '#refund_act_parti_pay_clear');
	$('#refund_act_parti_pay_submit').click(function() {
		$('#refund_act_parti_form').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/activity/refund',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#refund_act_participants').dialog('destroy');
					$('#select_act_participants').datagrid('reload');
					showmessage('退款成功');
				} else {
					$.messager.alert('错误', '退款失败', 'error');
				}
			}
		});
	});
	
	$('#refund_act_parti_pay_clear').click(function() {
		$("#refund_act_parti_form").form('clear'); 
	});
	
	//加载参数
	loadActRefundFormData(row);
	
}

function loadActCollectionFormData(row){
	$("#collection_act_parti_form").form('load',{
		colUserActivityId : row.colUserActivityId,
		colUserId : row.colUserId
	}); 
	
	var params = {
			'colActivityId' : row.colActivityId
	};
	//查询对应支付流水记录，为收款账户、退款金额赋值
	$.ajax({
		type : "post",
		async : false,
		url : root + '/rs/activity/getCostByActivityId',
		data : params,
		dataType : "json",
		success : function (json) {
			if(json != null && json.code != '-1'){
				var colActivityCost = json.result.colActivityCost;
				
				$('#collection_act_parti_money').val(colActivityCost);
			}
		}   
	});
	
}

function loadActRefundFormData(row){
	$("#refund_act_parti_form").form('load',{
		colUserActivityId : row.colUserActivityId,
		colUserId : row.colUserId
	}); 
	
	var params = {
		'colUserActivityId' : row.colUserActivityId,
		'colAppUserId' : row.colUserId,
		'colPayType' : 1
	};
	//查询对应支付流水记录，为收款账户、退款金额赋值
	$.ajax({
		type : "post",
		async : false,
		url : root + '/rs/activity/getPaymentHistoryByUserActivityId',
		data : params,
		dataType : "json",
		success : function (json) {
			if(json != null){
				var colReceiverAccount = json.colReceiverAccount;
				var colMoney = json.colMoney;
				
				$('#refund_act_parti_receiver_account').val(colReceiverAccount);
				$('#refund_act_parti_money').val(colMoney);
			}
		}   
	});
	
}

/**
 * 根据权限加载toolbar
 * 
 * @return
 */
function getActParticipantsToolBar() {
	
	var toolBars = [];
	
	toolBars[0] = {
		text : "通过申请",
		iconCls : "icon-ok",
		handler : auditActJoinPass
	};
	toolBars[1] = {
		text : "拒绝申请",
		iconCls : "icon-no",
		handler : auditActJoinReject
	};
	toolBars[2] = {
			text : "取消申请",
			iconCls : "icon-no",
			handler : auditActJoinCancel
	};
	toolBars[3] = {
			text : "收款",
			iconCls : "icon-edit",
			handler : collection
	};
	toolBars[4] = {
			text : "退款",
			iconCls : "icon-edit",
			handler : refund
	};
	toolBars[5] = {
		text : "删除",
		iconCls : "icon-remove",
		handler : removeActParticipants
	};

	return toolBars;
}

function paymentStatusFormatter(value){
	var result = '';
	
	if(value == 0){
		result = '未付款';
	}else if(value == 1){
		result = '已付款';
	}else if(value == 2){
		result = '已退款';
	}
	
	return result;
}
