function orgManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;机构名称:</td>');
	c.push('						<td>');
	c.push('							<input id="org_name" type="text" placeholder="请输入要查询的机构名称" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="queryorg_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_org"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_orgManage', tabTitle, c.join(""));

	queryBtn('#queryorg_search');
	
	$('#queryorg_search').click(function() {
		var colOrgName = $('#org_name').val();
		$('#select_org').datagrid('load', {
			colOrgName : colOrgName
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getOrgToolBar(permission);
	$('#select_org').datagrid( {
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
		url : root + '/rs/org/queryOrgs',
		idField : 'orgId',
		columns : [ [ {
			field : 'colOrgCode',
			title : '机构代码',
			sortable : true
		}, {
			field : 'colOrgName',
			title : '机构名称',
			sortable : true
		}, {
			field : 'colOrgType',
			title : '机构类型',
			align : 'center',
			formatter : orgTypeFormatter,
			sortable : true
		}, {
			field : 'colImgCompressPath',
			title : '机构头像',
			formatter : titleImgFormatter
		}, {
			field : 'colOrgTel',
			title : '电话'
		}, {
			field : 'colOrgFax',
			title : '传真'
		}, {
			field : 'colOrgAddress',
			title : '地址'
		}, {
			field : 'colOrgNote',
			title : '领导寄语'
		}, {
			field : 'colOrgIntro',
			title : '简介',
			formatter:showLongTextFormatter
		}, {
			field : 'colOrgShowMemberFlag',
			title : '显示成员列表',
			formatter : showYesFormatter,
			align : 'center'
		}, {
			field : 'colDelFlag',
			title : '状态',
			formatter : showDelFlagFormatter,
			align : 'center'
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询机构失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

	var p = $('#select_org').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}

/**
 * 创建机构
 */
function createOrg(){
	var title = '请您创建机构';
	c = [];
	c.push('<div id="create_org">');
	c.push('	<div>');
	c.push('		<form id="createorgform" method="post" enctype="multipart/form-data">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构类型:</td>');
	c.push('					<td ><input type="text" id="create_org_type" style="width:180px"  name="colOrgType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构头像:</td>');
	c.push('					<td>');
	c.push('						<input type="file" id="create_org_img" style="width:180px;" name="orgImg" />');
	c.push('					</td>');
	c.push('					<td rowspan="6">');
	c.push('						<img id="create_org_img_preview" style="width:145px;height:145px;" src="images/preview.png" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构代码:</td>');
	c.push('					<td ><input type="text" id="create_org_code" style="width:180px"  name="colOrgCode"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构名称:</td>');
	c.push('					<td ><input type="text" id="create_org_name" style="width:180px"  name="colOrgName"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;电话:</td>');
	c.push('					<td><input type="text" id="create_org_tel" style="width:180px"  name="colOrgTel" ></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;传真:</td>');
	c.push('					<td><input type="text" id="create_org_fax" style="width:180px"  name="colOrgFax" ></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;显示成员列表:</td>');
	c.push('					<td><select type="text" id="create_org_show_member_flag" style="width:180px"  name="colOrgShowMemberFlag" ></select></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;会长寄语:</td>');
	c.push('					<td colspan="2"><input type="text" id="create_org_note" style="width:90%"  name="colOrgNote" ></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;机构地址:</td>');
	c.push('					<td colspan="2">');
	c.push('						<input type="text" id="create_org_amap_addr" name="colOrgAddress" style="width:90%" />');
	c.push('						<a id="create_org_show_amap_btn"><img src="images/amap.png" style="width:22px;height:22px;margin-bottom:-5px;" /></a>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;经度:</td>');
	c.push('					<td colspan="2">');
	c.push('						<input type="text" id="create_org_amap_addr_lng" name="colOrgLongitude" style="width:180" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;纬度:</td>');
	c.push('					<td colspan="2">');
	c.push('						<input type="text" id="create_org_amap_addr_lat" name="colOrgLatitude" style="width:180;" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;机构简介:</td>');
//	c.push('					<td colspan="2"><textarea id="create_org_intro" name="colOrgIntro" ></textarea></td>');
	c.push('					<td colspan="2"><textarea id="create_org_intro" name="colOrgIntro" style="width:380px;height:80px;" ></textarea></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_org_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_org_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_org').dialog('destroy');
	
	$('#main_orgManage').append(c.join(""));
	
	$('#create_org').dialog( {
		title : title,
		closed : false,
		width : 505,
		modal : true
	});

	$('#create_org_type').combobox( {
		required : true,
		missingMessage : '机构类型不能为空',
		valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=1'
	});
	
	$('#create_org_code').validatebox( {
		required : true,
		validType : ['uniqueOrgCode'],
		missingMessage : '机构代碼不能为空'
	});
	
	$('#create_org_name').validatebox( {
		required : true,
		missingMessage : '机构名称不能为空'
	});
	
	$('#create_org_show_member_flag').combobox({   
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
	
	//绑定地图显示事件
	$("#create_org_show_amap_btn").bind("click ", function(){
		showAMap("#create_org","#create_org_amap_addr","#create_org_amap_addr_lng","#create_org_amap_addr_lat");
	});
	
	//初始化机构简介标签 kindeditor
//	initEditor("create_org_intro");
	
	submit_clear('#create_org_submit', '#create_org_clear');
	$('#create_org_submit').click(function() {
		$('#createorgform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/org/createOrg',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_org').dialog('destroy');
					$('#select_org').datagrid('reload');
					showmessage('创建机构成功');
				} else {
					$.messager.alert('错误', '创建机构失败', 'error');
				}
			}
		});
	});
	$('#create_org_clear').click(function() {
		$("#createorgform").form('clear'); 
	});
	
	$("#create_org_img").bind("change", function(){
		previewImage("create_org_img","create_org_img_preview");
	});
}

/**
 * 修改机构
 */
function updateOrg(){
	
	var row = $('#select_org').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您修改机构';
	c = [];
	c.push('<div id="update_org">');
	c.push('	<div>');
	c.push('		<form id="updateorgform" method="post" enctype="multipart/form-data">');
	c.push('			<input type="text" hidden name="orgId" />');
	c.push('			<input type="text" hidden name="imgId" />');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构类型:</td>');
	c.push('					<td ><input type="text" id="update_org_type" style="width:180px"  name="colOrgType" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构头像:</td>');
	c.push('					<td>');
	c.push('						<input type="file" id="update_org_img" style="width:180px;" name="orgImg" />');
	c.push('					</td>');
	c.push('					<td rowspan="6">');
	c.push('						<img id="update_org_img_preview"  style="width:145px;height:145px;" src="images/preview.png" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构代码:</td>');
	c.push('					<td><input type="text" id="update_org_code" style="width:180px"  name="colOrgCode"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>机构名称:</td>');
	c.push('					<td><input type="text" id="update_org_name" style="width:180px"  name="colOrgName"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;电话:</td>');
	c.push('					<td><input type="text" id="update_org_tel" style="width:180px"  name="colOrgTel" ></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;传真:</td>');
	c.push('					<td><input type="text" id="update_org_fax" style="width:180px"  name="colOrgFax" ></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;显示成员列表:</td>');
	c.push('					<td><select type="text" id="update_org_show_member_flag" style="width:180px"  name="colOrgShowMemberFlag" ></select></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;会长寄语:</td>');
	c.push('					<td colspan="2"><input type="text" id="update_org_note" style="width:90%"  name="colOrgNote" ></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;机构地址:</td>');
	c.push('					<td colspan="2">');
	c.push('						<input type="text" id="update_org_amap_addr" name="colOrgAddress" style="width:90%" />');
	c.push('						<a id="update_org_show_amap_btn"><img src="images/amap.png" style="width:22px;height:22px;margin-bottom:-5px;" /></a>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;经度:</td>');
	c.push('					<td colspan="2">');
	c.push('						<input type="text" id="update_org_amap_addr_lng" name="colOrgLongitude" style="width:180" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;纬度:</td>');
	c.push('					<td colspan="2">');
	c.push('						<input type="text" id="update_org_amap_addr_lat" name="colOrgLatitude" style="width:180;" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;机构简介:</td>');
//	c.push('					<td colspan="2"><textarea id="update_org_intro" name="colOrgIntro" ></textarea></td>');
	c.push('					<td colspan="2"><textarea id="update_org_intro" name="colOrgIntro" style="width:380px;height:80px;"></textarea></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_org_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_org_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_org').dialog('destroy');
	$('#main_orgManage').append(c.join(""));
	$('#update_org').dialog( {
		title : title,
		closed : false,
		width : 505,
		modal : true
	});


	$('#update_org_type').combobox( {
		required : true,
		missingMessage : '机构类型不能为空',
		valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=1'
	});
	
	$('#update_org_code').validatebox( {
		required : true,
		validType : ['uniqueOrgCode['+row.orgId+']'],
		missingMessage : '机构代碼不能为空'
	});
	
	$('#update_org_name').validatebox( {
		required : true,
		missingMessage : '机构名称不能为空'
	});
	
	$('#update_org_show_member_flag').combobox({   
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
	
	submit_clear('#update_org_submit', '#update_org_clear');
	$('#update_org_submit').click(function() {
		$('#updateorgform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/org/updateOrg',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_org').dialog('destroy');
					$('#select_org').datagrid('reload');
					showmessage('修改机构成功');
				} else {
					$.messager.alert('错误', '修改机构失败', 'error');
				}
			}
		});
	});
	
	$('#update_org_clear').click(function() {
		$("#updateorgform").form('clear'); 
		//加载表格数据
		$("#updateorgform").form('load',row);
		//设置编辑器内容
//		setEditorContent("update_org_intro",row.colOrgIntro);
	});
	
	//绑定地图显示事件
	$("#update_org_show_amap_btn").bind("click ", function(){
		showAMap("#update_org","#update_org_amap_addr","#update_org_amap_addr_lng","#update_org_amap_addr_lat");
	});

	$("#update_org_img").bind("change", function(){
		previewImage("update_org_img","update_org_img_preview");
	});
	
	//初始化机构简介标签 kindeditor
//	initEditor("update_org_intro");
	
	//加载表单数据
	loadOrgFormData(row);
}

/**
 * 加载表单数据
 * @param row
 */
function loadOrgFormData(row){
	//加载表格数据
	$("#updateorgform").form('load',row);
	
	if(row.colImgPath != null){
		document.getElementById("update_org_img_preview").src = row.colImgPath;
	}
	
	//设置编辑器内容
//	setTimeout(function(){
//		setEditorContent("update_org_intro",row.colOrgIntro);
//	},100);
}

/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getOrgToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建机构",
		iconCls : "icon-add",
		handler : createOrg
	};
	toolBars[1] = {
		text : "编辑机构",
		iconCls : "icon-edit",
		handler : updateOrg
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

function orgTypeFormatter(value,row){
//	var result = '';
	
//	if(value == '1'){
//		result = '券商';
//	}else if(value == '2'){
//		result = '银行';
//	}
	
//	return result;
	
	var typeName = row.colOrgTypeName;
	
	return typeName;
}

