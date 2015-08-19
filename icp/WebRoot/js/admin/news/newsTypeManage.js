function newsTypeManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_news_type"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_newsTypeManage', tabTitle, c.join(""));

	

	// 为datagrid添加工具条
	var _toolbar = getNewsTypeToolBar(permission);
	$('#select_news_type').datagrid( {
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
		url : root + '/rs/common/queryIndexs?colLabelClass=1',
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
			$.messager.alert("提示", "查询新闻类别失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

//	var p = $('#select_news_type').datagrid('getPager');
//	$(p).pagination( {
//		beforePageText : '第',
//		afterPageText : '页    共 {pages} 页',
//		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//	});

}


/**
 * 创建新闻
 */
function createNewsType(){
	var title = '请您创建新闻类别';
	c = [];
	c.push('<div id="create_news_type">');
	c.push('	<div>');
	c.push('		<form id="createnewstypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="colLabelClass" value="1">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>名称:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_news_type_text" style="width:95%"  name="colLabelText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px">状态:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_news_type_flag" style="width:95%"  name="colDelFlag"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_news_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_news_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_news_type').dialog('destroy');
	$('#main_newsTypeManage').append(c.join(""));
	$('#create_news_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#create_news_type_text').validatebox( {
		required : true,
		missingMessage : '新闻类别名称不能为空'
	});
	
	$('#create_news_type_flag').combobox({   
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
	
	submit_clear('#create_news_type_submit', '#create_news_type_clear');
	$('#create_news_type_submit').click(function() {
		$('#createnewstypeform').form('submit', {
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
					$('#create_news_type').dialog('destroy');
					$('#select_news_type').datagrid('reload');
					showmessage('创建新闻类别');
				} else {
					$.messager.alert('错误', '创建新闻类别失败', 'error');
				}
			}
		});
	});
	$('#create_news_type_clear').click(function() {
		$("#createnewstypeform").form('clear'); 
	});
}


function removeNewsType(){
	var row = $('#select_news_type').datagrid('getSelected');
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
						reloadDatagrid('#select_news_type');
						showmessage("删除成功！");
					} else {
						$('#select_news_type').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

function updateNewsType(){
	var row = $("#select_news_type").datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您编辑新闻类别';
	c = [];
	c.push('<div id="update_news_type">');
	c.push('	<div>');
	c.push('		<form id="updatenewstypeform" method="post" enctype="multipart/form-data">');
	c.push(' 			<input type="hidden" name="dictId">');
	c.push(' 			<input type="hidden" name="colLabelClass">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px"><font color="#dd0000">*</font>名称:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_news_type_text" style="width:95%"  name="colLabelText"  validType="nul"></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td style="padding-left:180px">状态:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_news_type_flag" style="width:95%"  name="colDelFlag"></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_news_type_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_news_type_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_news_type').dialog('destroy');
	$('#main_newsTypeManage').append(c.join(""));
	$('#update_news_type').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#update_news_type_text').validatebox( {
		required : true,
		missingMessage : '新闻类别名称不能为空'
	});
	
	$('#update_news_type_flag').combobox({   
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
	
	submit_clear('#update_news_type_submit', '#update_news_type_clear');
	$('#update_news_type_submit').click(function() {
		$('#updatenewstypeform').form('submit', {
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
					$('#update_news_type').dialog('destroy');
					$('#select_news_type').datagrid('reload');
					showmessage('编辑类别成功');
				} else {
					$.messager.alert('错误', '编辑新闻类别失败', 'error');
				}
			}
		});
	});
	$('#update_news_type_clear').click(function() {
		$("#updatenewstypeform").form('clear'); 
		
		loadNewsTypeFormData(row);
	});
	
	//加载表单数据
	loadNewsTypeFormData(row);
}



/**
 * 加载新闻表单数据
 * @param row
 * @returns
 */
function loadNewsTypeFormData(row){
	$("#updatenewstypeform").form('load',{
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
function getNewsTypeToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建新闻类别",
		iconCls : "icon-add",
		handler : createNewsType
	};
	toolBars[1] = {
		text : "删除新闻类别",
		iconCls : "icon-remove",
		handler : removeNewsType
	};
	toolBars[2] = {
		text : "编辑新闻类别",
		iconCls : "icon-edit",
		handler : updateNewsType
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}
