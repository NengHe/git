function payHistoryManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;选择查询属性:</td>');
	c.push('						<td>');
	c.push('						<input type="select" id="select_by" name="colqueryby" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('							<input id="query_by" type="text" placeholder="请输入要查询的内容" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="querypayhistory_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_payhistory"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_payhistoryManage', tabTitle, c.join(""));

	queryBtn('#querypayhistory_search');
	
	$('#select_by').combobox({
		width : 180,
		panelHeight : 90,
		valueField : 'value',
		textField : 'text',
		value : "colUserActId",
		data : [ {
			"value" : "colUserActId",
			"text" : "活动ID"
		}, {
			"value" : "colAppUserId",
			"text" : "成员ID"
		}, {
			"value" : "colPayNum",
			"text" : "支付流水号"
		}, {
			"value" : "colPayAccount",
			"text" : "支付账号"
		}]
	});

	$('#querypayhistory_search').click(function() {
		var colqueryby = $('#select_by').combobox('getValue');
		var colQueryBy = $('#query_by').val();
		$('#select_payhistory').datagrid('load', {
			colQueryBy : colQueryBy,
			colqueryby : colqueryby
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getPaymentToolBar(permission);
	$('#select_payhistory').datagrid({
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
		url : root + '/rs/pay/queryPayHistory',
		idField : 'payHistoryId',
		columns : [ [ {
			field : 'colPaymentType',
			title : '支付方式',
			formatter : showPayFormatter
		}, {
			field : 'colUserActId',
			title : '活动ID'
		}, {
			field : 'colAppUserId',
			title : '成员ID'
		}, {
			field : 'colPayNum',
			title : '支付流水号'
		}, {
			field : 'colPayAccount',
			title : '支付账号'
		}, {
			field : 'colGetAccount',
			title : '收款账号'
		}, {
			field : 'colMoney',
			title : '支付金额（元）'
		}, {
			field : 'colTime',
			title : '支付时间',
			formatter : longToDateFormatter,
			sortable : true
		}, {
			field : 'colPayType',
			title : '支付类型',
			formatter : showPayTypeFormatter
		}, {
			field : 'colState',
			title : '支付状态',
			formatter : showPayStateFormatter
		}, {
			field : 'colStateTime',
			title : '状态发生时间',
			formatter : longToDateFormatter,
			sortable : true
		} ] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询支付流水失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

	var p = $('#select_payhistory').datagrid('getPager');
	$(p).pagination({
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}
function showPayFormatter(value,row){
	var payTypeName = row.colPayTypeName;
	return payTypeName;
}
