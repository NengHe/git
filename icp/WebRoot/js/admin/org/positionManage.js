function positionManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;职位名称:</td>');
	c.push('						<td>');
	c.push('							<input id="position_name" type="text" placeholder="请输入要查询的职位名称" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="queryposition_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_position"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_positionManage', tabTitle, c.join(""));

	queryBtn('#queryposition_search');
	
	$('#queryposition_search').click(function() {
		var colPosName = $('#position_name').val();
		$('#select_position').datagrid('load', {
			colPosName : colPosName
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getPositionToolBar(permission);
	$('#select_position').datagrid( {
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
		url : root + '/rs/position/queryPositions',
		idField : 'colPosOrgId',
		columns : [ [ {
			field : 'colOrgCode',
			title : '机构代码'
		},{
			field : 'colOrgName',
			title : '机构名称'
		},{
			field : 'colPosName',
			title : '职位名称'
		},{
			field : 'colPosShowDetailInner',
			title : '是否显示详细信息（内部）',
			formatter:showYesFormatter
		},{
			field : 'colPosShowDetailOutter',
			title : '是否显示详细信息（外部）',
			formatter:showYesFormatter
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询机构失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

	var p = $('#select_position').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}

/**
 *修改职务
 */
function createPosition(){
	var title = '请您创建职务';
	c = [];
	c.push('<div id="create_position">');
	c.push('	<div>');
	c.push('		<form id="createpositionform" method="post" enctype="multipart/form-data">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>选择机构:</td>');
	c.push('					<td>');
	c.push('						<select id="create_position_org_id" name="colPosOrgId" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>职位名称:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_position_name" style="width:180px"  name="colPosName"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="create_position_show_detail_inner" name="colPosShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="create_position_show_detail_outter" name="colPosShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_position_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_position_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_position').dialog('destroy');
	$('#main_positionManage').append(c.join(""));
	$('#create_position').dialog( {
		title : title,
		closed : false,
		width : 610,
		modal : true
	});
	
	$('#create_position_org_id').combobox({   
	    url:root + '/rs/common/queryOrgs4select',   
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:150
	});   
	
	$('#create_position_name').validatebox( {
		required : true,
		missingMessage : '机构名称不能为空'
	});
	
	$('#create_position_show_detail_inner , #create_position_show_detail_outter').combobox({   
	    width:180,
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
	
	
	submit_clear('#create_position_submit', '#create_position_clear');
	$('#create_position_submit').click(function() {
		$('#createpositionform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/position/createPosition',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_position').dialog('destroy');
					$('#select_position').datagrid('reload');
					showmessage('创建机构成功');
				} else {
					$.messager.alert('错误', '创建机构失败', 'error');
				}
			}
		});
	});
	$('#create_position_clear').click(function() {
		$("#createpositionform").form('clear'); 
	});
}

/**
 * 删除职位
 */

function removePosition(){
	var row = $('#select_position').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/position/removePosition',
				 data: {"positionId":row.positionId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_position');
						showmessage("删除成功！");
					} else {
						$('#select_admin_user').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

/**
 * 修改职位
 */
function updatePosition(){
	
	var row = $('#select_position').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您修改机构';
	c = [];
	c.push('<div id="update_position">');
	c.push('	<div>');
	c.push('		<form id="updatepositionform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="positionId">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>选择机构:</td>');
	c.push('					<td>');
	c.push('						<select id="update_position_org_id" name="colPosOrgId" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>职位名称:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_position_name" style="width:180px"  name="colPosName"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>是否显示详细信息（内部）:</td>');
	c.push('					<td>');
	c.push('						<select id="update_position_show_detail_inner" name="colPosShowDetailInner" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td class="ltl1"><font color="#dd0000">*</font>是否显示详细信息（外部）:</td>');
	c.push('					<td>');
	c.push('						<select id="update_position_show_detail_outter" name="colPosShowDetailOutter" class="easyui-combobox" validType="nul">');
	c.push('						</select>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_position_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_position_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_position').dialog('destroy');
	$('#main_positionManage').append(c.join(""));
	$('#update_position').dialog( {
		title : title,
		closed : false,
		width : 610,
		modal : true
	});

	$('#update_position_org_id').combobox({   
	    url:root + '/rs/common/queryOrgs4select',   
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:150
	});   
	
	$('#update_position_name').validatebox( {
		required : true,
		missingMessage : '机构名称不能为空'
	});
	
	$('#update_position_show_detail_inner , #update_position_show_detail_outter').combobox({   
	    width:180,
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
	
	submit_clear('#update_position_submit', '#update_position_clear');
	$('#update_position_submit').click(function() {
		$('#updatepositionform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/position/updatePosition',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_position').dialog('destroy');
					$('#select_position').datagrid('reload');
					showmessage('修改职务成功');
				} else {
					$.messager.alert('错误', '修改职务失败', 'error');
				}
			}
		});
	});
	
	$('#update_position_clear').click(function() {
		$("#updatepositionform").form('clear');
		loadpositionFormData(row);
	});
	
	//加载表单数据
	loadpositionFormData(row);
	
}

/**
 * 加载表单数据
 * @param row
 */

function loadpositionFormData(row){
	$("#updatepositionform").form('load',{
		positionId : row.positionId,
		colPosOrgId : row.colPosOrgId,
		colPosName : row.colPosName,
		colPosShowDetailInner : row.colPosShowDetailInner,
		colPosShowDetailOutter : row.colPosShowDetailOutter
	});
}
/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getPositionToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建职位",
		iconCls : "icon-add",
		handler : createPosition
	};
	toolBars[1] = {
			text : "删除职位",
			iconCls : "icon-remove",
			handler : removePosition
	};
	toolBars[2] = {
		text : "编辑职位",
		iconCls : "icon-edit",
		handler : updatePosition
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

