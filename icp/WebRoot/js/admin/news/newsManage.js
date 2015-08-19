function newsManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;新闻标题:</td>');
	c.push('						<td>');
	c.push('							<input id="search_news_title" type="text" placeholder="请输入要查询的新闻标题" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="querynews_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_news"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_newsManage', tabTitle, c.join(""));

	queryBtn('#querynews_search');
	
	$('#querynews_search').click(function() {
		var colNewsTitle = $('#search_news_title').val();
		$('#select_news').datagrid('load', {
			colNewsTitle : colNewsTitle
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getNewsToolBar(permission);
	$('#select_news').datagrid( {
		iconCls : 'icon-save',
		fit : true,
		nowrap : true,
		striped : true,
		autoRowHeight : true,
		fitColumns : true,
		striped : true,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : paramconfig.page.size,
		pageList : paramconfig.page.list,
		url : root + '/rs/news/queryNews',
		idField : 'newsId',
		columns : [ [ {
			field : 'colOrgCode',
			title : '机构代码'
		},{
			field : 'colOrgName',
			title : '机构名称'
		},{
			field : 'colNewsTitle',
			title : '新闻标题'
		},{
			field : 'colNewsTitleImgUrl',
			title : '标题图片',
			formatter : titleImgFormatter,
			align : 'center'
		},{
			field : 'colNewsBrief',
			title : '简讯',
			formatter : showLongTextFormatter
		},{
			field : 'colNewsIssueTime',
			title : '时间',
			formatter : longToDateFormatter,
			align : 'center'
		}, {
			field : 'colNewsSource',
			title : '新闻来源'
		}, {
			field : 'colNewsContent',
			title : '新闻内容',
			formatter : showLongTextFormatter
		}, {
			field : 'colNewsOrientedType',
			title : '面向类型',
			formatter : newsOrientedTypeFormatter,
			align : 'center'
		}, {
			field : 'colNewsTypeName',
			title : '新闻类型'
		}, {
			field : 'colSharableFlag',
			title : '是否可分享',
			formatter : showYesFormatter,
			align : 'center'
		}, {
			field : 'colDelFlag',
			title : '状态',
			formatter : showDelFlagFormatter,
			align : 'center'
		},{
			field : 'colNewsContentDetailUrl',
			title : '详情页面url'
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询新闻失败！");
		},
		onLoadSuccess : function(data) {
		}
	});

	var p = $('#select_news').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}


/**
 * 创建新闻
 */
function createNews(){
	var title = '请您创建新闻';
	c = [];
	c.push('<div id="create_news">');
	c.push('	<div>');
	c.push('		<form id="createnewsform" method="post" enctype="multipart/form-data">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td ><font color="#dd0000">*</font>标题:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="create_news_title" style="width:95%"  name="colNewsTitle"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>简讯:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="create_news_brief" style="width:95%;height:80px;"  name="colNewsBrief"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>标题图片:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
//	c.push('						<input type="file" id="create_news_title_img_path" style="width:180px" name="newsTitleImg" />');
	c.push('						<div>');
	c.push('							<input id="create_news_title_img_btn" type="button" value="编辑图片" />');
	c.push('							<input type="text" id="create_news_title_img_path" style="width:81%;" name="colNewsTitleImgUrl" />');
	c.push('						</div>');
	c.push('					</td style="height:36px;">');
//	c.push('					<td rowspan="7">');
//	c.push('						<img id="create_news_title_img" style="width:200px;height:200px;" src="images/preview.png" />');
//	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>时间:</td>');
	c.push('					<td><input type="text" id="create_news_issue_time" style="width:180px;"  name="colNewsIssueTime" /></td>');
	c.push('					<td>&nbsp;&nbsp;来源:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="create_news_source" style="width:180px;"  name="colNewsSource" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>面向类型:</td>');
	c.push('					<td><input type="text" id="create_news_oriented_type" style="width:180px;"  name="colNewsOrientedType" /></td>');
	c.push('					<td>&nbsp;&nbsp;发布机构:</td>');
	c.push('					<td>');
	c.push('						<input id="create_news_org_id" name="colNewsIssuerId" />');
	c.push('					</td style="height:36px;">');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>新闻类型:</td>');
	c.push('					<td><input type="text" id="create_news_type" style="width:180px;"  name="colNewsTypeId" /></td>');
	c.push('					<td><font color="#dd0000">*</font>支持分享:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="create_news_sharable" style="width:95%"  name="colSharableFlag" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>内容:</td>');
	c.push('					<td colspan="3" style="height:36px;"><textarea id="create_news_content" name="colNewsContent" ></textarea></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_news_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_news_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_news').dialog('destroy');
	$('#main_newsManage').append(c.join(""));
	$('#create_news').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#create_news_title').validatebox( {
		required : true,
		missingMessage : '标题不能为空'
	});
	
	$('#create_news_title_img_btn').bind('click',function(){
		openImgCropWin('create_news', 'create_news_title_img_path', 140, 105);
	});
	
	$('#create_news_brief').validatebox( {
		required : true,
		missingMessage : '简讯不能为空'
	});
	
	setDateTimeBox('#create_news_issue_time',true,false);
	
	$('#create_news_oriented_type').combobox({   
		width : 180,
		required:true,
		valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=6',
		onSelect:function(record){
			var orientedType = record.colDictVal;
			
			if(orientedType == 1){		//如果面向类型为机构
				//控制发布机构必填
				$('#create_news_org_id').combobox({   
					required: true,   
					validType: 'null'  
				}); 
			}else{
				$('#create_news_org_id').combobox('setValue','');
				$('#create_news_org_id').combobox({   
					required: false
				});
			}
		}
	});
	
	$('#create_news_type').combobox({   
	    width:180,
	    valueField:'dictId',   
	    textField:'colLabelText',
	    required:true,
	    url : root + '/rs/common/queryIndexs4select?colLabelClass=1',
//	    onSelect:function(record){
//	    	var newsTypeName = record.colLabelText;
//	    	
//	    	if(newsTypeName == '组织'){		//如果面向类型为机构
//	    		//控制发布机构必填
//	    		$('#create_news_org_id').combobox({   
//	    		    required: true,   
//	    		    validType: 'null'  
//	    		}); 
//	    	}else{
//	    		$('#create_news_org_id').combobox('setValue','');
//	    		$('#create_news_org_id').combobox({   
//	    		    required: false
//	    		});
//	    	}
//	    }
	});
	
	//加载机构列表数据
	$('#create_news_org_id').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:180
	}); 
	
	$('#create_news_sharable').combobox({   
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
	
//	$("#create_news_title_img_path").bind("change ", function(){
//		previewImage("create_news_title_img_path","create_news_title_img");
//	});
	
	//初始化新闻简介标签 kindeditor
	initEditor("create_news_content");
	
	submit_clear('#create_news_submit', '#create_news_clear');
	$('#create_news_submit').click(function() {
		$('#createnewsform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/news/createNews',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_news').dialog('destroy');
					$('#select_news').datagrid('reload');
					showmessage('创建新闻成功');
				} else {
					$.messager.alert('错误', '创建新闻失败', 'error');
				}
			}
		});
	});
	$('#create_news_clear').click(function() {
		$("#createnewsform").form('clear'); 
	});
}


function removeNews(){
	var row = $('#select_news').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/news/removeNews',
				 data: {"newsId":row.newsId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_news');
						showmessage("删除成功！");
					} else {
						$('#select_news').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

function updateNews(){
	var row = $("#select_news").datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您编辑新闻';
	c = [];
	c.push('<div id="update_news">');
	c.push('	<div>');
	c.push('		<form id="updatenewsform" method="post" enctype="multipart/form-data">');
	c.push('			<input type="text" hidden name="newsId" />');
	c.push('			<input type="text" hidden name="newsTitleImgId" />');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>标题:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="update_news_title" style="width:95%"  name="colNewsTitle"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>简讯:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="update_news_brief" style="width:95%;height:80px;"  name="colNewsBrief"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>标题图片:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
	c.push('						<div>');
	c.push('							<input id="update_news_title_img_btn" type="button" value="编辑图片" />');
	c.push('							<input type="text" id="update_news_title_img_path" style="width:81%;" name="colNewsTitleImgUrl" />');
	c.push('						</div>');
	c.push('					</td style="height:36px;">');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>时间:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_news_issue_time" style="width:180px;" name="colNewsIssueTime" /></td>');
	c.push('					<td>&nbsp;&nbsp;来源:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_news_source" style="width:180px;"  name="colNewsSource" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>面向类型:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_news_oriented_type" style="width:180px;"  name="colNewsOrientedType" /></td>');
	c.push('					<td>&nbsp;&nbsp;发布机构:</td>');
	c.push('					<td style="height:36px;">');
	c.push('						<input id="update_news_org_id" name="colNewsIssuerId" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>新闻类型:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_news_type" style="width:180px;"  name="colNewsTypeId" /></td>');
	c.push('					<td><font color="#dd0000">*</font>支持分享:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_news_sharable" style="width:95%"  name="colSharableFlag" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>内容:</td>');
	c.push('					<td colspan="3" style="height:36px;"><textarea id="update_news_content" name="colNewsContent" ></textarea></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:20px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_news_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_news_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_news').dialog('destroy');
	$('#main_newsManage').append(c.join(""));
	$('#update_news').dialog( {
		title : title,
		closed : false,
		width : 580,
		modal : true
	});

	$('#update_news_title').validatebox( {
		required : true,
		missingMessage : '标题不能为空'
	});

	$('#update_news_title_img_btn').bind('click',function(){
		openImgCropWin('update_news', 'update_news_title_img_path', 140, 105);
	});
	
	$('#update_news_brief').validatebox( {
		required : true,
		missingMessage : '简讯不能为空'
	});
	
	setDateTimeBox('#update_news_issue_time',false,false);
	

	$('#update_news_oriented_type').combobox({   
		width : 180,
		required:true,
		valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=6',
		onSelect:function(record){
			var orientedType = record.colDictVal;
			
			if(orientedType == 1){		//如果面向类型为机构
				//控制发布机构必填
				$('#create_news_org_id').combobox({   
					required: true,   
					validType: 'null'  
				}); 
			}else{
				$('#create_news_org_id').combobox('setValue','');
				$('#create_news_org_id').combobox({   
					required: false
				});
			}
		}
	});
	
	$('#update_news_type').combobox({   
	    width:180,
	    valueField:'dictId',   
	    textField:'colLabelText',
	    required:true,
	    url : root + '/rs/common/queryIndexs4select?colLabelClass=1',
//	    onSelect:function(record){
//	    	var newsTypeName = record.colLabelText;
//	    	
//	    	if(newsTypeName == '组织'){		//如果面向类型为机构
//	    		//控制发布机构必填
//	    		$('#update_news_org_id').combobox({   
//	    		    required: true,   
//	    		    validType: 'null'  
//	    		}); 
//	    	}else{
//	    		$('#update_news_org_id').combobox('setValue','');
//	    		$('#update_news_org_id').combobox({   
//	    		    required: false
//	    		});
//	    	}
//	    }
	});
	
	//加载机构列表数据
	$('#update_news_org_id').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:180
	}); 
	
	$('#update_news_sharable').combobox({   
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
	
	$("#update_news_title_img_path").bind("change ", function(){
		previewImage("update_news_title_img_path","update_news_title_img");
	});
	
	//初始化新闻简介标签 kindeditor
	initEditor("update_news_content");
	
	submit_clear('#update_news_submit', '#update_news_clear');
	$('#update_news_submit').click(function() {
		$('#updatenewsform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/news/updateNews',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_news').dialog('destroy');
					$('#select_news').datagrid('reload');
					showmessage('编辑新闻成功');
				} else {
					$.messager.alert('错误', '编辑新闻失败', 'error');
				}
			}
		});
	});
	$('#update_news_clear').click(function() {
		$("#updatenewsform").form('clear'); 
		
		loadNewsFormData(row);
	});
	
	//加载表单数据
	loadNewsFormData(row);
}



/**
 * 加载新闻表单数据
 * @param row
 * @returns
 */
function loadNewsFormData(row){
	$("#updatenewsform").form('load',{
		newsId : row.newsId,
		newsTitleImgId : row.newsTitleImgId,
		colNewsTitle : row.colNewsTitle,
		colNewsBrief : row.colNewsBrief,
		colNewsSource : row.colNewsSource,
		colNewsTypeId : row.colNewsTypeId,
//		colNewsTypeId : row.colNewsTypeId,
		colSharableFlag : row.colSharableFlag
	}); 
	
	setTimeout(function(){
		$('#update_news_oriented_type').combobox('select',row.colNewsOrientedType);
//		$('#update_news_type').combobox('select',row.colNewsTypeId);
	},200);
	setTimeout(function(){
		$('#update_news_org_id').combobox('select',row.colNewsIssuerId);
	},200);
	
	setDateTimeValue('#update_news_issue_time',row.colNewsIssueTime);
	
	setTimeout(function(){
		setEditorContent("update_news_content",row.colNewsContent);
	},100);
	
	if(row.newsTitleImgCompressPath != null){
		document.getElementById("update_news_title_img").src = row.newsTitleImgPath;
	}
}
/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getNewsToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建新闻",
		iconCls : "icon-add",
		handler : createNews
	};
	toolBars[1] = {
		text : "删除新闻",
		iconCls : "icon-remove",
		handler : removeNews
	};
	toolBars[2] = {
		text : "编辑新闻",
		iconCls : "icon-edit",
		handler : updateNews
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

function newsOrientedTypeFormatter(value){
	if(value == '1'){
		return '组织成员';
	}else if(value == '2'){
		return '所有人';
	}
}
