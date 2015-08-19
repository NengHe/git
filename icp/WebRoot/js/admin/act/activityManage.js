function activityManage(tabTitle, permission) {

	c = [];
	c.push('<div  class="easyui-layout" data-options="fit:true">');
	c.push('	<div region="north" border="false" >');
	c.push('		<div style="padding:5px;height:38px">');
	c.push('			<div class="scrm-search">');
	c.push('				<table>');
	c.push('					<tr>');
	c.push('						<td> &nbsp;活动标题:</td>');
	c.push('						<td>');
	c.push('							<input id="search_activity_title" type="text" placeholder="请输入要查询的活动标题" />');
	c.push('						</td>');
	c.push('						<td>');
	c.push('							<a id="queryactivity_search" href="#" ><strong>查询</strong>&nbsp;</a>');
	c.push('						</td>');
	c.push('					</tr>');
	c.push('				</table>');
	c.push('			</div>');
	c.push('		</div>');
	c.push('	</div>');
	c.push('	<div region="center" border="false" style="padding:5px;">');
	c.push('		<div id="select_activity"></div>');
	c.push('	</div>');
	c.push('</div>');

	// 更新该子标签页
	updateTab('main_activityManage', tabTitle, c.join(""));

	queryBtn('#queryactivity_search');
	
	$('#queryactivity_search').click(function() {
		var colActivityTitle = $('#search_activity_title').val();
		$('#select_activity').datagrid('load', {
			colActivityTitle : colActivityTitle
		});
	});

	// 为datagrid添加工具条
	var _toolbar = getActivityToolBar(permission);
	$('#select_activity').datagrid( {
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
		url : root + '/rs/activity/queryActivities',
		idField : 'activityId',
		columns : [ [ {
			field : 'colOrgCode',
			title : '机构代码'
		},{
			field : 'colOrgName',
			title : '机构名称'
		},{
			field : 'colActivityTitle',
			title : '活动标题'
		},{
			field : 'colActivityTitleImgUrl',
			title : '标题图片',
			formatter : titleImgFormatter,
			align : 'center'
		},{
			field : 'colActivityBrief',
			title : '简讯',
			formatter : showLongTextFormatter
		},{
			field : 'colActivityAuditableFlag',
			title : '是否需要审核',
			formatter : showYesFormatter,
			align : 'center'
		},{
			field : 'colActivityNeedPayFlag',
			title : '是否需要付款',
			formatter : showYesFormatter,
			align : 'center'
		},{
			field : 'colActivityStatus',
			title : '活动状态',
			formatter : activityStatusFormatter,
			align : 'center'
		},{
			field : 'colActivityEnrollDeadline',
			title : '报名截止时间',
			formatter : longToDateFormatter,
			align : 'center'
		},{
			field : 'colActivityStartTime',
			title : '开始时间',
			formatter : longToDateFormatter,
			align : 'center'
		},{
			field : 'colActivityEndTime',
			title : '结束时间',
			formatter : longToDateFormatter,
			align : 'center'
		},{
			field : 'colActivityOrgnizer',
			title : '主办方'
		},{
			field : 'colActivityPlace',
			title : '地点'
		},{
			field : 'colActivityContent',
			title : '活动内容',
			formatter : showLongTextFormatter
		},{
			field : 'colActivityType',
			title : '面向类型',
			formatter : activityTypeFormatter,
			align : 'center'
		},{
			field : 'colActivityIndexLabel',
			title : '活动类型'
		}, 
//		{
//			field : '#',
//			title : '参与列表',
//			formatter:showParticipatorsFormatter
//		}, 
		{
			field : 'colActivityCost',
			title : '费用'
		}, {
			field : 'colActivitySharableFlag',
			title : '是否可分享',
			formatter : showYesFormatter,
			align : 'center'
		}, 
//		{
//			field : '#',
//			title : '评论',
//			formatter:showCommentsFormatter
//		}, 
//		{
//			field : 'colDelFlag',
//			title : '状态',
//			formatter : showDelFlagFormatter,
//			align : 'center'
//		},
		{
			field : 'colActivityBriefImgUrl',
			title : '简介图片url'
		},{
			field : 'colActivityContentDetailUrl',
			title : '详情页面url'
		}] ],
		toolbar : _toolbar,
		onLoadError : function(data) {
			$.messager.alert("提示", "查询活动失败！");
		},
		onLoadSuccess : function(data) {
			//初始选中第一行，防止表格初始加载时，点击【查看参与列表】或【查看评论列表】时没有选中的行
			initSelectFirstRow('#select_activity');
		}
	});

	var p = $('#select_activity').datagrid('getPager');
	$(p).pagination( {
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

}


/**
 * 查看参与者列表
 * @param value
 * @param row
 */
function showParticipatorsFormatter(value,row){
	
	var result = '<a onclick="showActParticipants()" style="color:blue">查看</a>';
	
	return result;
}

/**
 * 查看评论列表
 * @param value
 * @param row
 */
function showCommentsFormatter(value,row){
	
	var result = '<a onclick="showComments()" style="color:blue">查看</a>';
	
	return result;
}

/**
 * 创建活动
 */
function createActivity(){
	var title = '请您创建活动';
	c = [];
	c.push('<div id="create_activity">');
	c.push('	<div>');
	c.push('		<form id="createactivityform" method="post" enctype="multipart/form-data">');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>标题:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="create_activity_title" style="width:95%"  name="colActivityTitle"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>标题图片:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
	c.push('						<div>');
	c.push('							<input id="create_activity_title_img_btn" type="button" value="编辑图片" />');
	c.push('							<input type="text" id="create_activity_title_img_path" style="width:81%;" name="colActivityTitleImgUrl" />');
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>简讯:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="create_activity_brief" style="width:95%;height:80px;" name="colActivityBrief"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>简讯图片:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
	c.push('						<div>');
	c.push('							<input id="create_activity_brief_img_btn" type="button" value="编辑图片" />');
	c.push('							<input type="text" id="create_activity_brief_img_path" style="width:81%;" name="colActivityBriefImgUrl" />');
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;需要审核:</td>');
	c.push('					<td><input type="text" id="create_activity_auditable_flag" style="width:180px;"  name="colActivityAuditableFlag" /></td>');
	c.push('					<td>&nbsp;&nbsp;需要付费:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="create_activity_need_pay_flag" style="width:180px;" name="colActivityNeedPayFlag" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>报名截止:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="create_activity_enroll_deadline" style="width:180px;"  name="colActivityEnrollDeadline" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>开始时间:</td>');
	c.push('					<td><input type="text" id="create_activity_start_time" style="width:180px;"  name="colActivityStartTime" /></td>');
	c.push('					<td><font color="#dd0000">*</font>结束时间:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="create_activity_end_time" style="width:180px;"  name="colActivityEndTime" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>面向类型:</td>');
	c.push('					<td><input type="text" id="create_activity_type" style="width:180px;"  name="colActivityType" /></td>');
	c.push('					<td>&nbsp;&nbsp;发布机构:</td>');
	c.push('					<td style="height:36px;">');
	c.push('						<input id="create_activity_org_id" name="colActivityIssueOrgId" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;主办方:</td>');
	c.push('					<td><input type="text" id="create_activity_orgnizer" style="width:180px;"  name="colActivityOrgnizer" /></td>');
	c.push('					<td>&nbsp;&nbsp;花费:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="create_activity_cost" style="width:180px;"  name="colActivityCost" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;活动类型:</td>');
	c.push('					<td><input type="text" id="create_activity_index_id" style="width:180px;"  name="colActivityIndexId" /></td>');
	c.push('					<td>&nbsp;&nbsp;支持分享:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="create_activity_sharable" style="width:180px;" name="colActivitySharableFlag" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;地点:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
	c.push('						<input type="text" id="create_activity_amap_addr" name="colActivityPlace" style="width:415px" />');
	c.push('						<a id="create_activity_show_amap_btn"><img src="images/amap.png" style="width:22px;height:22px;margin-bottom:-5px;" /></a>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;经度:</td>');
	c.push('					<td>');
	c.push('						<input type="text" id="create_activity_amap_addr_lng" name="colActivityPlaceLatitude" style="width:180" />');
	c.push('					</td>');
	c.push('					<td>&nbsp;&nbsp;纬度:</td>');
	c.push('					<td style="height:36px;">');
	c.push('						<input type="text" id="create_activity_amap_addr_lat" name="colActivityPlaceLongitude" style="width:180;" />');
	c.push('					</td>');
	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>背景:</td>');
//	c.push('					<td colspan="2"><textarea id="create_activity_background" name="colActivityBackground" style="width:466px;height:75px;" ></textarea></td>');
//	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>议程:</td>');
//	c.push('					<td colspan="2"><textarea id="create_activity_agenda" name="colActivityAgenda" style="width:466px;height:75px;" ></textarea></td>');
//	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>嘉宾:</td>');
//	c.push('					<td colspan="2"><textarea id="create_activity_guest" name="colActivityGuest" style="width:466px;height:75px;" ></textarea></td>');
//	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>内容:</td>');
	c.push('					<td colspan="3"><textarea id="create_activity_content" name="colActivityContent" ></textarea></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:0px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="create_activity_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="create_activity_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#create_activity').dialog('destroy');
	$('#main_activityManage').append(c.join(""));
	$('#create_activity').dialog( {
		title : title,
		closed : false,
		width : 580,
		height:600,
		modal : true
	});

	$('#create_activity_title').validatebox( {
		required : true,
		missingMessage : '标题不能为空'
	});
	
	$('#create_activity_title_img_btn').bind('click',function(){
		openImgCropWin('create_activity', 'create_activity_title_img_path', 140, 105);
	});
	
	$('#create_activity_brief').validatebox( {
		required : true,
		missingMessage : '简讯不能为空'
	});
	
	$('#create_activity_brief_img_btn').bind('click',function(){
		openImgCropWin('create_activity', 'create_activity_brief_img_path', 640, 265);
	});
	
	$('#create_activity_type').combobox({  
	    width:180,
	    panelHeight:40,
	    required: true, 
	    valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=4',
	    onSelect:function(record){
	    	var activityType = record.colDictVal;
	    	
	    	if(activityType == 1){		//如果面向类型为机构
	    		//控制发布机构必填
	    		$('#create_activity_org_id').combobox({   
	    		    required: true,   
	    		    validType: 'null'  
	    		}); 
	    	}else{
	    		$('#create_activity_org_id').combobox('setValue','');
	    		$('#create_activity_org_id').combobox({   
	    		    required: false
	    		});
	    	}
	    }
	});

	//加载机构列表数据
	$('#create_activity_org_id').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:180
	}); 
	
	setDateTimeBox('#create_activity_enroll_deadline',true,false);
	setDateTimeBox('#create_activity_start_time',true,false);
	setDateTimeBox('#create_activity_end_time',true,false);
	
	$('#create_activity_index_id').combobox({   
	    width:180,
	    valueField:'dictId',   
	    textField:'colLabelText',
	    required:true,
	    url : root + '/rs/common/queryIndexs4select?colLabelClass=2',
	});
	$('#create_activity_auditable_flag').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		required:true,
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});
	$('#create_activity_need_pay_flag').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		required:true,
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});
	$('#create_activity_sharable').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		required:true,
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});
	
	$("#create_activity_show_amap_btn").bind("click", function(){
		showAMap("#create_activity","#create_activity_amap_addr","#create_activity_amap_addr_lng","#create_activity_amap_addr_lat");
	});
	
	
	//初始化活动简介标签 kindeditor
//	initEditor("create_activity_background");
//	initEditor("create_activity_agenda");
//	initEditor("create_activity_guest");
	initEditor("create_activity_content");
	
	submit_clear('#create_activity_submit', '#create_activity_clear');
	$('#create_activity_submit').click(function() {
		$('#createactivityform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/activity/createActivity',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#create_activity').dialog('destroy');
					$('#select_activity').datagrid('reload');
					showmessage('创建活动成功');
				} else {
					$.messager.alert('错误', '创建活动失败', 'error');
				}
			}
		});
	});
	$('#create_activity_clear').click(function() {
		$("#createactivityform").form('clear'); 
	});
}


function removeActivity(){
	var row = $('#select_activity').datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
   	$.messager.confirm('确认', '您确定要删除该记录吗', function(flag) {
       	if (flag) {
	         $.ajax({
	        	 type: 'POST',
				 url: root + '/rs/activity/removeActivity',
				 data: {"activityId":row.activityId},
				 dataType: 'json',
				 success: function(data){
					if(data.code == '1'){
						reloadDatagrid('#select_activity');
						showmessage("删除成功！");
					} else {
						$('#select_activity').datagrid('reload');
						$.messager.alert("提示", "删除失败！",'error');
					}
				}
			});
       	}
   });
}

function updateActivity(){
	var row = $("#select_activity").datagrid('getSelected');
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	var title = '请您编辑活动';
	c = [];
	c.push('<div id="update_activity">');
	c.push('	<div>');
	c.push('		<form id="updateactivityform" method="post" enctype="multipart/form-data">');
	c.push('			<input type="text" hidden name="activityId" />');
	c.push('			<input type="text" hidden name="actTitleImgId" />');
	c.push('			<table>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>标题:</td>');
	c.push('					<td colspan="3"><input type="text" id="update_activity_title" style="width:95%"  name="colActivityTitle"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>标题图片:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
	c.push('						<div>');
	c.push('							<input id="update_activity_title_img_btn" type="button" value="编辑图片" />');
	c.push('							<input type="text" id="update_activity_title_img_path" style="width:81%;" name="colActivityTitleImgUrl" />');
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>简讯:</td>');
	c.push('					<td colspan="3"><input type="text" id="update_activity_brief" style="width:95%;height:80px;"  name="colActivityBrief"  validType="nul"></input></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>简讯图片:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
	c.push('						<div>');
	c.push('							<input id="update_activity_brief_img_btn" type="button" value="编辑图片" />');
	c.push('							<input type="text" id="update_activity_brief_img_path" style="width:81%;" name="colActivityBriefImgUrl" />');
	c.push('						</div>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;需要审核:</td>');
	c.push('					<td><input type="text" id="update_activity_auditable_flag" style="width:180px;"  name="colActivityAuditableFlag" /></td>');
	c.push('					<td>&nbsp;&nbsp;需要付费:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_activity_need_pay_flag" style="width:180px;" name="colActivityNeedPayFlag" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>报名截止:</td>');
	c.push('					<td colspan="3" style="height:36px;"><input type="text" id="update_activity_enroll_deadline" style="width:180px;"  name="colActivityEnrollDeadline" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>开始时间:</td>');
	c.push('					<td><input type="text" id="update_activity_start_time" style="width:180px;"  name="colActivityStartTime" /></td>');
	c.push('					<td><font color="#dd0000">*</font>结束时间:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_activity_end_time" style="width:180px;"  name="colActivityEndTime" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;面向类型:</td>');
	c.push('					<td><input type="text" id="update_activity_type" style="width:180px;"  name="colActivityType" /></td>');
	c.push('					<td>&nbsp;&nbsp;发布机构:</td>');
	c.push('					<td style="height:36px;">');
	c.push('						<input id="update_activity_org_id" name="colActivityIssueOrgId" />');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;主办方:</td>');
	c.push('					<td><input type="text" id="update_activity_orgnizer" style="width:180px;"  name="colActivityOrgnizer" /></td>');
	c.push('					<td>&nbsp;&nbsp;花费:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_activity_cost" style="width:180px;"  name="colActivityCost" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;类型:</td>');
	c.push('					<td><input type="text" id="update_activity_index_id" style="width:180px;"  name="colActivityIndexId" /></td>');
	c.push('					<td>&nbsp;&nbsp;支持分享:</td>');
	c.push('					<td style="height:36px;"><input type="text" id="update_activity_sharable" style="width:180px;" name="colActivitySharableFlag" /></td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;地点:</td>');
	c.push('					<td colspan="3" style="height:36px;">');
	c.push('						<input type="text" id="update_activity_amap_addr" name="colActivityPlace" style="width:415px" />');
	c.push('						<a id="update_activity_show_amap_btn" ><img src="images/amap.png" style="width:22px;height:22px;margin-bottom:-5px;" /></a>');
	c.push('					</td>');
	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td>&nbsp;&nbsp;经度:</td>');
	c.push('					<td>');
	c.push('						<input type="text" id="update_activity_amap_addr_lng" name="colActivityPlaceLatitude" style="width:180" />');
	c.push('					</td>');
	c.push('					<td>&nbsp;&nbsp;纬度:</td>');
	c.push('					<td style="height:36px;">');
	c.push('						<input type="text" id="update_activity_amap_addr_lat" name="colActivityPlaceLongitude" style="width:180;" />');
	c.push('					</td>');
	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>背景:</td>');
//	c.push('					<td colspan="2"><textarea id="update_activity_background" name="colActivityBackground" style="width:466px;height:75px;" ></textarea></td>');
//	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>议程:</td>');
//	c.push('					<td colspan="2"><textarea id="update_activity_agenda" name="colActivityAgenda" style="width:466px;height:75px;" ></textarea></td>');
//	c.push('				</tr>');
//	c.push('				<tr>');
//	c.push('					<td><font color="#dd0000">*</font>嘉宾:</td>');
//	c.push('					<td colspan="2"><textarea id="update_activity_guest" name="colActivityGuest" style="width:466px;height:75px;" ></textarea></td>');
//	c.push('				</tr>');
	c.push('				<tr>');
	c.push('					<td><font color="#dd0000">*</font>内容:</td>');
	c.push('					<td colspan="3" style="height:36px;"><textarea id="update_activity_content" name="colActivityContent" ></textarea></td>');
	c.push('				</tr>');
	c.push('			</table>');
	c.push('		</form> ');
	c.push('	</div>');
	c.push('	<div style="text-align:left;padding:0px 20px 20px 20px">');
	c.push('		<center>');
	c.push('			<a href="#" id="update_activity_submit"><strong>提交</strong>&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	c.push('			<a href="#" id="update_activity_clear"><strong>重置</strong>&nbsp;</a>');
	c.push('		</center>');
	c.push('	</div>');
	c.push('</div>');

	$('#update_activity').dialog('destroy');
	$('#main_activityManage').append(c.join(""));
	$('#update_activity').dialog( {
		title : title,
		closed : false,
		width : 580,
		height:600,
		modal : true
	});

	$('#update_activity_title').validatebox( {
		required : true,
		missingMessage : '标题不能为空'
	});

	$('#update_activity_title_img_btn').bind('click',function(){
		openImgCropWin('update_activity', 'update_activity_title_img_path', 140, 105);
	});
	
	$('#update_activity_brief').validatebox( {
		required : true,
		missingMessage : '简讯不能为空'
	});
	
	$('#update_activity_brief_img_btn').bind('click',function(){
		openImgCropWin('update_activity', 'update_activity_brief_img_path', 640, 265);
	});
	
	$('#update_activity_auditable_flag').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		required:true,
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});
	
	$('#update_activity_need_pay_flag').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		required:true,
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});
	
	$('#update_activity_type').combobox({   
	    width:180,
	    panelHeight:40,
	    required: true, 
	    valueField:'colDictVal',   
	    textField:'colDictText',
	    url : root + '/rs/common/queryDict4select?colDictClass=4',
	    onSelect:function(record){
	    	var activityType = record.colDictVal;
	    	
	    	if(activityType == 1){		//如果面向类型为机构
	    		//控制发布机构必填
	    		$('#update_activity_org_id').combobox({   
	    		    required: true,   
	    		    validType: 'null'  
	    		}); 
	    	}else{
	    		$('#update_activity_org_id').combobox('setValue','');
	    		$('#update_activity_org_id').combobox({   
	    		    required: false
	    		});
	    	}
	    }
	});
	
	//加载机构列表数据
	$('#update_activity_org_id').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:180
	}); 
	
	setDateTimeBox('#update_activity_enroll_deadline',false,false);
	setDateTimeBox('#update_activity_start_time',false,false);
	setDateTimeBox('#update_activity_end_time',false,false);
	
	$('#update_activity_index_id').combobox({   
	    width:180,
	    valueField:'dictId',   
	    textField:'colLabelText',
	    required:true,
	    url : root + '/rs/common/queryIndexs4select?colLabelClass=2',
	});
	$('#update_activity_sharable').combobox({   
		width:180,
		panelHeight:40,
		valueField:'value',   
		textField:'text',
		required:true,
		value:"1",
		data:[{
			"value":"1",
			"text":"是"
		},{
			"value":"0",
			"text":"否"
		}]
	});
	
	$("#update_activity_show_amap_btn").bind("click", function(){
		showAMap("#update_activity","#update_activity_amap_addr","#update_activity_amap_addr_lng","#update_activity_amap_addr_lat");
	});
	
	//初始化活动简介标签 kindeditor
//	initEditor("update_activity_background");
//	initEditor("update_activity_agenda");
//	initEditor("update_activity_guest");
	initEditor("update_activity_content");
	
	submit_clear('#update_activity_submit', '#update_activity_clear');
	$('#update_activity_submit').click(function() {
		$('#updateactivityform').form('submit', {
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
				$.messager.progress( {
					text : '正在提交...'
				});
				return true;
			},
			url : root + '/rs/activity/updateActivity',
			success : function(data) {
				$.messager.progress('close');
				var result = eval("("+data+")");
				if (result.code == 1) {
					$('#update_activity').dialog('destroy');
					$('#select_activity').datagrid('reload');
					showmessage('更新活动成功');
				} else {
					$.messager.alert('错误', '更新活动失败', 'error');
				}
			}
		});
	});
	$('#update_activity_clear').click(function() {
		$("#updateactivityform").form('clear'); 
		loadActivityFormData(row);
	});
	
	//加载表单数据
	loadActivityFormData(row);
}

//查看活动参与者列表
function showActParticipants(){
	var datagridObj;
	var row;
	var activityId;
	
	datagridObj = $("#select_activity");
	row = datagridObj.datagrid('getSelected');
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	activityId = row.activityId;
	actParticipantsManage(activityId);
}

//查看活动参与者列表
function showActComments(){
	var datagridObj;
	var row;
	var activityId;
	
	datagridObj = $("#select_activity");
	row = datagridObj.datagrid('getSelected');
	
	if(row == null){
		$.messager.alert('提示', "请选中您要操作的记录！");
		return ;
	}
	
	activityId = row.activityId;
	
	actCommentsManage(activityId);
}

/**
 * 加载活动表单数据
 * @param row
 * @returns
 */
function loadActivityFormData(row){
	$("#updateactivityform").form('load',{
		activityId : row.activityId,
//		actTitleImgId : row.actTitleImgId,
//		colActivityType : row.colActivityType,
		colActivityOrgnizer : row.colActivityOrgnizer,
		colActivityTitle : row.colActivityTitle,
		colActivityTitleImgUrl : row.colActivityTitleImgUrl,
		colActivityBrief : row.colActivityBrief,
		colActivityBriefImgUrl : row.colActivityBriefImgUrl,
		colActivityAuditableFlag : row.colActivityAuditableFlag,
		colActivityNeedPayFlag : row.colActivityNeedPayFlag,
		colActivityPlace : row.colActivityPlace,
		colActivityPlaceLatitude : row.colActivityPlaceLatitude,
		colActivityPlaceLongitude : row.colActivityPlaceLongitude,
		colActivityCost : row.colActivityCost,
		colActivitySharableFlag : row.colActivitySharableFlag,
		colActivityIndexId : row.colActivityIndexId
//		colActivityBackground : row.colActivityBackground,
//		colActivityAgenda : row.colActivityAgenda,
//		colActivityGuest : row.colActivityGuest
	}); 
	
	setTimeout(function(){
		$('#update_activity_type').combobox('select',row.colActivityType);
	},200);
	setTimeout(function(){
		$('#update_activity_org_id').combobox('select',row.colActivityIssueOrgId);
	},200);
	
	setDateTimeValue('#update_activity_enroll_deadline',row.colActivityEnrollDeadline);
	setDateTimeValue('#update_activity_start_time',row.colActivityStartTime);
	setDateTimeValue('#update_activity_end_time',row.colActivityEndTime);
	
	setTimeout(function(){
//		setEditorContent("update_activity_background",row.colActivityBackground);
//		setEditorContent("update_activity_agenda",row.colActivityAgenda);
//		setEditorContent("update_activity_guest",row.colActivityGuest);
		setEditorContent("update_activity_content",row.colActivityContent);
	},100);
	
//	if(row.actTitleImgPath != null){
//		document.getElementById("update_activity_title_img_preview").src = row.actTitleImgPath;
//	}
}
/**
 * 根据权限加载toolbar
 * 
 * @param permission
 * @return
 */
function getActivityToolBar(permission) {
	
	var toolBars = [];
	toolBars[0] = {
		text : "创建活动",
		iconCls : "icon-add",
		handler : createActivity
	};
	toolBars[1] = {
		text : "删除活动",
		iconCls : "icon-remove",
		handler : removeActivity
	};
	toolBars[2] = {
		text : "编辑活动",
		iconCls : "icon-edit",
		handler : updateActivity
	};
	toolBars[3] = {
			text : "查看参与列表",
			iconCls : "icon-edit",
			handler : showActParticipants
	};
	toolBars[4] = {
			text : "查看评论列表",
			iconCls : "icon-edit",
			handler : showActComments
	};

	var permToolBars = getPermToolBars(permission, toolBars);
	return permToolBars;
}

function activityTypeFormatter(value){
	var result = '';
	
	if(value == '1'){
		return '组织成员';
	}else if(value == '2'){
		return '所有人';
	}
	
	return result;
}

function activityStatusFormatter(value){
	var result = '';
	
	if(value == '1'){
		result = '接受报名';
	}else if(value == '2'){
		result = '报名结束';
	}else if(value == '3'){
		result = '进行中';
	}else if(value == '2'){
		result = '结束';
	}
	
	return result;
}
