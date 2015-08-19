function actCommentsManage(activityId) {
	var title;
	var queryParams;
	var c;
	var _toolbar;
	
	title = '活动参与列表';
	c = [];
	c.push('<div  id="act_comments_manage" >');
	c.push('	<div id="select_act_comments"></div>');
	c.push('</div>');

	$('#act_comments_manage').dialog('destroy');
	$('#main_activityManage').append(c.join(""));
	$('#act_comments_manage').dialog( {
		title : title,
		closed : false,
		width : 800,
		height: 600,
		modal : true
	});

	// 为datagrid添加工具条
	_toolbar = getActcommentsToolBar();
	
	queryParams = {
		activityId:activityId
	};
	
	$('#select_act_comments').datagrid( {
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
		url : root + '/rs/activity/queryActComments',
		queryParams: queryParams,
		idField : 'id',
		columns : [ [ {
			field : 'colUserName',
			title : '用户姓名'
		}, {
			field : 'colCurReplyUserName',
			title : '回复人'
		}, {
			field : 'content',
			title : '内容',
			formatter : showLongTextFormatter
		}, {
			field : 'createTime',
			title : '时间',
			align : 'center',
			formatter : longToDateFormatter
		}, {
			field : 'remark',
			title : '备注'
		}, {
			field : 'showStatus',
			title : '显示状态',
			align : 'center',
			formatter : showStatusFormatter
		}, {
			field : 'replyStatus',
			title : '回复状态',
			align : 'center',
			formatter : replyStatusFormatter
		}] ],
		toolbar : _toolbar,
		onLoadError : function() {
			$.messager.alert("提示", "查询评论列表失败！");
		},
		onLoadSuccess : function(data) {
		},
		onDblClickRow : function(){
			dbClickShowCommentReply();
		}
	});

//	var p = $('#select_act_comments').datagrid('getPager');
//	$(p).pagination( {
//		beforePageText : '第',
//		afterPageText : '页    共 {pages} 页',
//		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//	});

}

/**
 * 双击显示评论或回复内容，回复内容时可编辑
 */
function dbClickShowCommentReply(){
	var datagridObj;
	var row;
	var type;
	
	datagridObj = $("#select_act_comments");
	row = datagridObj.datagrid('getSelected');
	
	type = row.type;
	if(type == 1){
		showComment(row);
	}else if(type == 2){
		updateCommentReply(row);
	}
	
}
/**
 * 显示评论内容详情
 * @param row
 */
function showComment(row){
	var c;
	var title;
	var row;
	var remark;
	
	remark = row.remark;
	title = '查看'+remark;
	
	c = [];
	c.push('<div id="show_act_comment_dialog">');
	c.push('	<textarea id="show_act_comment_content" name="colReplyContent" style="width:482px;height:114px;" ></textarea>');
	c.push('</div>');

	$('#show_act_comment_dialog').dialog('destroy');
	$('#act_comments_manage').append(c.join(""));
	$('#show_act_comment_dialog').dialog( {
		title : title,
		closed : false,
		width : 496,
		height: 150,
		modal : true
	});
	
//	initEditor("show_act_comment_content",1);
	
//	setTimeout(function(){
//		setEditorContent("show_act_comment_content",row.content);
//	},200);
	
	$("#show_act_comment_content").val(row.content);
}

/**
 * 更新评论回复
 */
function updateCommentReply(row){
	var c;
	var title;
	var row;
	var remark;
	
	row = $("#select_act_comments").datagrid('getSelected');
	remark = row.remark;
	title = remark;
	
	c = [];
	c.push('<div id="update_act_comment_reply_dialog">');
	c.push('	<div>');
	c.push('		<form id="updateactreplyform" method="post">');
	c.push('			<input hidden name="replyId" />');
	c.push('			<textarea id="udpate_act_comment_reply_content" name="colReplyContent" style="width:481px;height:97px;" ></textarea>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:0px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_act_comment_reply_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_act_comment_reply_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_act_comment_reply_dialog').dialog('destroy');
	$('#act_comments_manage').append(c.join(""));
	$('#update_act_comment_reply_dialog').dialog( {
		title : title,
		closed : false,
		width : 496,
		height: 200,
		modal : true
	});
	
	submit_clear('#update_act_comment_reply_submit', '#update_act_comment_reply_clear');
	$('#update_act_comment_reply_submit').click(function() {
		$('#updateactreplyform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/activity/updateActCommentReply',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_act_comment_reply_dialog').dialog('destroy');
					$('#select_act_comments').datagrid('reload');
					showmessage('回复成功');
				} else {
					$.messager.alert('错误', '回复失败', 'error');
				}
			}
		});
	});
	$('#update_act_comment_reply_clear').click(function() {
		$("#updateactreplyform").form('clear'); 
		loadUpdateReplyFormData(row);
	});
	
//	initEditor("udpate_act_comment_reply_content");
	
	loadUpdateReplyFormData(row);
}

//加载回复表单数据
function loadUpdateReplyFormData(row){
	$("#updateactreplyform").form('load',{
		replyId : row.id,
		colReplyContent : row.content
	}); 
	
//	setTimeout(function(){
//		setEditorContent("udpate_act_comment_reply_content",row.content);
//	},100);
}

/**
 * 回复评论
 */
function replyComment(){
	var datagridObj;
	var row;
	var type;
	var replyStatus;
	
	datagridObj = $("#select_act_comments");
	row = datagridObj.datagrid('getSelected');
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	type = row.type;
	if(type == 2){
//		$.messager.alert('提示', "请选中您要回复的评论记录！");
		return ;
	}
	
	replyStatus = row.replyStatus;
	if(replyStatus == 1){
		return ;
	}
	
	createCommentReply(row);
	
}

/**
 * 创建回复面板
 */
function createCommentReply(row){
	var c;
	var title;
	var row;
	var remark;
	
	row = $("#select_act_comments").datagrid('getSelected');
	remark = row.remark;
	title = remark;
	
	c = [];
	c.push('<div id="create_act_comment_reply_dialog">');
	c.push('	<div>');
	c.push('		<form id="createactreplyform" method="post">');
	c.push('			<input hidden name="colActCommentId" />');
	c.push('			<textarea id="create_act_comment_reply_content" name="colReplyContent"  style="width:481px;height:97px;"  ></textarea>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:0px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_act_comment_reply_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_act_comment_reply_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_act_comment_reply_dialog').dialog('destroy');
	$('#act_comments_manage').append(c.join(""));
	$('#create_act_comment_reply_dialog').dialog( {
		title : title,
		closed : false,
		width : 496,
		height: 200,
		modal : true
	});
	
	submit_clear('#create_act_comment_reply_submit', '#create_act_comment_reply_clear');
	$('#create_act_comment_reply_submit').click(function() {
		$('#createactreplyform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/activity/createActCommentReply',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_act_comment_reply_dialog').dialog('destroy');
					$('#select_act_comments').datagrid('reload');
					showmessage('回复成功');
				} else {
					$.messager.alert('错误', '回复失败', 'error');
				}
			}
		});
	});
	$('#create_act_comment_reply_clear').click(function() {
		$("#createactreplyform").form('clear'); 
	});
	
//	initEditor("create_act_comment_reply_content");
	
	loadCreateReplyFormData(row);
	
}

//加载回复表单数据
function loadCreateReplyFormData(row){
	$("#createactreplyform").form('load',{
		colActCommentId : row.id
	}); 
}

/**
 * 显示评论
 */
function allowToShow(){
	var row = $('#select_act_comments').datagrid('getSelected');
	var type;
	var colCommentShowStatus;
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	type = row.type;
	if(type != 1){
		$.messager.alert('提示', "请选中您要操作的评论记录！");
		return ;
	}
	
	colCommentShowStatus = row.showStatus;
	if(colCommentShowStatus == '1'){
		return ;
	}else{
		colCommentShowStatus = '1';
	}
	
    $.ajax({
    	 type: 'POST',
    	 url: root + '/rs/activity/updateActCommentShowStatus',
		 data: {
			 "colActCommentId":row.id,
			 "colCommentShowStatus":colCommentShowStatus
		 },
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				reloadDatagrid('#select_act_comments');
				showmessage("操作成功！");
			} else if(data.code == '-2'){
				$('#select_act_comments').datagrid('reload');
				$.messager.alert("提示","用户已加入该活动，请勿重复处理申请操作！",'error');
			} else {
				$('#select_act_comments').datagrid('reload');
				$.messager.alert("提示", "操作失败！",'error');
			}
		}
	});
}

/**
 * 屏蔽评论
 */
function disallowToShow(){
	var row = $('#select_act_comments').datagrid('getSelected');
	var type;
	var colCommentShowStatus;
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	type = row.type;
	if(type != 1){
		$.messager.alert('提示', "请选中您要操作的评论记录！");
		return ;
	}
	
	colCommentShowStatus = row.showStatus;
	if(colCommentShowStatus == '2'){
		return ;
	}else{
		colCommentShowStatus = '2';
	}
	
    $.ajax({
    	 type: 'POST',
		 url: root + '/rs/activity/updateActCommentShowStatus',
		 data: {
			 "colActCommentId":row.id,
			 "colCommentShowStatus":colCommentShowStatus
		 },
		 dataType: 'json',
		 success: function(data){
			if(data.code == '1'){
				reloadDatagrid('#select_act_comments');
				showmessage("操作成功！");
			} else if(data.code == '-2'){
				$('#select_act_comments').datagrid('reload');
				$.messager.alert("提示","用户已加入该活动，请勿重复处理申请操作！",'error');
			} else {
				$('#select_act_comments').datagrid('reload');
				$.messager.alert("提示", "操作失败！",'error');
			}
		}
	});
}


/**
 * 根据权限加载toolbar
 * 
 * @return
 */
function getActcommentsToolBar() {
	
	var toolBars = [];
	
	toolBars[0] = {
		text : "回复",
		iconCls : "icon-edit",
		handler : replyComment
	};
	toolBars[1] = {
			text : "显示",
			iconCls : "icon-ok",
			handler : allowToShow
	};
	toolBars[2] = {
		text : "屏蔽",
		iconCls : "icon-no",
		handler : disallowToShow
	};

	return toolBars;
}


function showStatusFormatter(value,row){
	var result;
	
	if(value == '1'){
		result = '<img src="images/ok.png" />';
	}else if(value == '2'){
		result = '<img src="images/no.png" />';
	}
	
	return result;
}

function replyStatusFormatter(value,row){
	var result;
	
	if(value == '1'){
		result = "已回复";
	}else if(value == '0'){
		result = "待回复";
	}
	
	return result;
}

