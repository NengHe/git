function orgReqManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;姓名:</td>');
	c.push('						<td>');
	c.push('							<input id="org_req_name" type="text" placeholder="请输入要查询的用户姓名" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="queryorg_req_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_org_req"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_orgReqManage', tabTitle, c.join(""));

	queryBtn('#queryorg_req_search');
	
	$('#queryorg_req_search').click(function() {
		var colOrgName = $('#org_req_name').val();
		$('#select_org_req').datagrid('load', {
			colOrgName : colOrgName
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getOrgReqToolBar(permission);
	$('#select_org_req').datagrid( {
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
		url : root + '/rs/orgReq/queryOrgReqs',
		idField : 'orgJoinReqId',
		columns : [ [ {
			field : 'colReqAuthStatus',
			title : '审核状态',
			formatter : showAuditFormatter,
			align : 'center'
		}, {
			field : 'colUserName',
			title : '用户姓名'
		}, {
			field : 'colOrgCode',
			title : '申请机构代码'
		}, {
			field : 'colOrgName',
			title : '申请机构名称'
		}, {
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
			field : 'colCreateTime',
			title : '申请时间',
			formatter : longToDateFormatter
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询入会申请失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

	var p = $('#select_org_req').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}

/**
 * 审核通过
 */
function auditPass(){
	var row = $('#select_org_req').datagrid('getSelected');
	var authStstus;
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	authStatus = row.colReqAuthStatus;
	if(authStatus != '0'){
		$.messager.alert('提示', '请选择状态为<img src="images/tip.png">待审核的记录！');
		return ;
	}
	
    $.ajax({
    	 type: 'POST',
		 url: root + '/rs/orgReq/auditPass',
		 data: {"orgJoinReqId":row.orgJoinReqId},
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				reloadDatagrid('#select_org_req');
				showmessage("操作成功！");
			} else if(data.code == '-2'){
				$('#select_org_req').datagrid('reload');
				$.messager.alert("提示","用户已加入该机构，请勿重复处理申请操作！",'error');
			} else {
				$('#select_org_req').datagrid('reload');
				$.messager.alert("提示", "操作失败！",'error');
			}
		}
	});
}

/**
 * 审核不通过
 */
function auditReject(){
	var row = $('#select_org_req').datagrid('getSelected');
	var authStstus;
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	authStatus = row.colReqAuthStatus;
	if(authStatus != '0'){
		$.messager.alert('提示', '请选择状态为<img src="images/tip.png">待审核的记录！');
		return ;
	}
	
    $.ajax({
    	 type: 'POST',
		 url: root + '/rs/orgReq/auditReject',
		 data: {"orgJoinReqId":row.orgJoinReqId},
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				reloadDatagrid('#select_org_req');
				showmessage("操作成功！");
			} else if(data.code == '-2'){
				$('#select_org_req').datagrid('reload');
				$.messager.alert("提示","用户已加入该机构，请勿重复处理申请操作！",'error');
			} else {
				$('#select_org_req').datagrid('reload');
				$.messager.alert("提示", "操作失败！",'error');
			}
		}
	});
}

/**
 * 删除申请
 */
function removeOrgReq(){
	var row = $('#select_org_req').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/orgReq/removeOrgReq',
				 data: {"orgJoinReqId":row.orgJoinReqId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_org_req');
						showmessage("删除成功！");
					} else {
						$('#select_org_req').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}


/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getOrgReqToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "通过申请",
		iconCls : "icon-ok",
		handler : auditPass
	};
	toolBars[1] = {
		text : "拒绝申请",
		iconCls : "icon-no",
		handler : auditReject
	};
	toolBars[2] = {
		text : "删除申请",
		iconCls : "icon-remove",
		handler : removeOrgReq
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

