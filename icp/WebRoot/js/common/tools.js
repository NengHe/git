
/**
 * 打开剪裁图片窗口
 * @param parentDialogId 对应父对话框id
 * @param targetInputId 目标显示路径input标签id
 * @param fixedCropBoxWidth 固定剪裁框宽度
 * @param fixedCropBoxHeight 固定剪裁框高度
 */
function openImgCropWin(parentDialogId, targetInputId, fixedCropBoxWidth, fixedCropBoxHeight){
	var title = '剪裁图片';
	var iframeSrc = root + '/plugin/cropper/main.html?parentDialogId=' + parentDialogId 
		+ '&targetInputId=' + targetInputId	+'&fixedCropBoxWidth=' + fixedCropBoxWidth + '&fixedCropBoxHeight=' + fixedCropBoxHeight;
	
	c = [];
	c.push('<div id="cropper_img_win" style="overflow: hidden;">');
	c.push('	<iframe id="imgIframe" src="'+iframeSrc+'" scrolling="no" frameborder="0" width="900px" height="518"></iframe>');
	c.push('</div>');

	$('#cropper_img_win').dialog('destroy');
	$('#'+parentDialogId).append(c.join(""));
	$('#cropper_img_win').dialog( {
		title : title,
		closed : false,
		width : 890,
		height: 542,
		modal : true
	});
	
}

/**
 * 验证机构代码唯一
 * @param value
 * @param orgCode
 * @returns {Boolean}
 */
function checkUniqueOrgCode(value, orgId){
	var flag = false;
	
	var params = {
			'orgCode' : value,
			'orgId' : orgId
	};
	
	$.ajax({
		type : "post",
		async : false,
		url : root + '/rs/common/checkUniqueOrgCode',
		data : params,
		dataType : "json",
		success : function (json) {
			if(json != null && json.code == '1'){
				flag = json.result;
			}
		}   
	});
	
	return flag;
}



/**
 * 验证手机号码唯一
 * @param value
 * @param appUserId
 * @returns {Boolean}
 */
function checkUniquePhone(value, appUserId){
	var flag = false;
	
	var params = {
		'phone' : value,
		'appUserId' : appUserId
	};
	
	$.ajax({
		type : "post",
        async : false,
        url : root + '/rs/common/checkUniquePhone4AppUer',
        data : params,
        dataType : "json",
        success : function (json) {
        	if(json != null && json.code == '1'){
        		flag = json.result;
        	}
        }   
	});
	
	return flag;
}

/**
 * 索引类型格式化
 * @param value
 * @param row
 * @returns {String}
 */
function labelClassFormatter(value,row){
	if (value == '1') {
		return "新闻索引";
	} else if (value == '2'){
		return "活动索引";
	}
}

/**
 * 初始选中列表第一行
 * @param datagridId
 */
function initSelectFirstRow(datagridId){
	var datagrid;
	var rows;
	
	datagrid = $(datagridId);
	rows = datagrid.datagrid('getRows');
	
	if(rows != null && rows.length > 0){
		datagrid.datagrid('selectRow', 0);	//初始选中第一条
	}
}

/**
 * 审核状态formatter
 * @param value
 * @param row
 */
function showAuditFormatter(value,row){
	var result = '';
	
	if(value == '0'){
		result = "申请待审";
//		result = '<img src="images/tip.png" />';
	}else if(value == '1'){
		result = "审核通过";
//		result = '<img src="images/ok.png" />';
	}else if(value == '2'){
		result = "报名取消";
//		result = '<img src="images/no.png" />';
	}else if(value == '3'){
		result = "取消待审";
	}
	
	return result;
}

/**
 * 创建图片上传面板
 */
function createUploadWin() {
	var url = "http://smfactorytest.10jqka.com.cn/icpFiles/00/00/rBB-gVWOFKeij7O4AACgTSAoOcA314_big.jpg";
	var imgTag = '<img src="http://smfactorytest.10jqka.com.cn/icpFiles/00/00/rBB-gVWOFKeij7O4AACgTSAoOcA314_big.jpg" style="width:60px;height:80px;" />';

	var curEditor = tinymce.activeEditor;
	if (curEditor != null) {
		curEditor.setContent(cnt);
	}
}

function reloadDatagrid(id) {
	$(id).datagrid('unselectAll');
	$(id).datagrid('reload');
}

function setDateTimeBox(id, showNowFlag, required) {
	$(id).datetimebox({
		formatter : function(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			var h = date.getHours();
			var min = date.getMinutes();
			var sec = date.getSeconds();
			return y + '-' + m + '-' + d + ' ' + h + ':' + min + ':' + sec;
		},
		required : required,
		parser : function(s) {
			var date = Date.parse(s);
			if (!isNaN(date)) {
				return new Date(date);
			} else {
				return new Date();
			}
		},
		editable : false
	});
	if (showNowFlag) {
		$(id).datetimebox('setValue', new Date().formatDate(app.dateTimeStyle));
	}
}

function setDateTimeValue(id, time) {
	$(id).datetimebox('setValue', new Date(time).formatDate(app.dateTimeStyle));
}

/**
 * 预览图片
 * 
 * @param inputId
 * @param imgId
 */
function previewImage(inputId, imgId) {
	var inputFileTag = document.getElementById(inputId);
	var previewTag = document.getElementById(imgId);

	if (window.FileReader) {
		var oFReader = new window.FileReader();
		var rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;

		oFReader.onload = function(oFREvent) {
			previewTag.src = oFREvent.target.result;
		};

		var aFiles = inputFileTag.files;
		if (aFiles.length === 0) {
			return;
		}
		if (!rFilter.test(aFiles[0].type)) {
			alert("You must select a valid image file!");
			return;
		}

		oFReader.readAsDataURL(aFiles[0]);
	}

	if (navigator.appName === "Microsoft Internet Explorer") {
		var result = inputFileTag.value;
		previewTag.src = result;
		// document.getElementById("create_news_title_img").filters
		// .item("DXImageTransform.Microsoft.AlphaImageLoader").src = ;

		// }
	}
}

/**
 * 图片路径格式化成图片标签
 * 
 * @param value
 */
function titleImgFormatter(value) {
	if (value == null) {
		return '';
	}

	var result = '<img src="' + value + '" style="width:28px;height:28px;" />';

	return result;
}

/**
 * long转日期字符串
 * 
 * @param value
 * @returns
 */
function longToDateFormatter(value) {
	var date;
	var result;
	
	if(value == null){
		return '';
	}
	
	date = new Date(value);
//	result = date.toLocaleString();
//	result = result.replace(/年|月/g, "-");
//	result = result.replace(/日/g, " ");
//	result = result.substring(0, 17);
	
	result = date.formatDate(app.dateTimeStyle);

	return result;
}

/**
 * datagrid顯示長文本字段格式化
 * 
 * @param value
 * @param row
 */
function showLongTextFormatter(value, row) {
	if (value == null) {
		return '';
	}

	var result1;
	var result2;
	var result;

	result1 = value.replace(/\s/g, '');		//替换空格、换行、tab缩进等空白符
	result1 = result1.replace(/<p>/gi, '');		//替换段落标签
	result1 = result1.replace(/<\/p>/gi, '/n');

	var regex2 = /<img(\S)*(\/>)/gi;
	// 将所有的<img .../>替换成[图片](忽略大小写)
	result2 = result1.replace(regex2, '[图片]');

	// 字符串场地大于17时，省略显示
	if (result2 != null && result2.length > 17) {
		result = result2.substr(0, 18);
		result += "...";
	} else {
		result = result2;
	}

	return result;
}

/**
 * 初始化富文本编辑器
 * 
 * @param id
 */
function initEditor(id,readonly) {
	var curEditor = tinymce.get(id);
	if (curEditor != null) {
		curEditor.destroy();
	}

	tinymce
			.init({
				selector : 'textarea#' + id,
				convert_urls : false,
				readonly : readonly,
				language : 'zh_CN',
				menubar : false,
				plugins : "image textcolor preview code emoticons fullscreen imageupload",
				toolbar : "bold forecolor backcolor fontselect fontsizeselect styleselect code preview fullscreen imageupload ",
				imageupload_url : root + '/rs/common/uploadImg',
				// image_list:[
				// {title: 'img1', value:
				// 'http://smfactorytest.10jqka.com.cn/icpFiles/00/00/rBB-gVWOFKeij7O4AACgTSAoOcA314_big.jpg'}
				// ],
//				image_list : function(callback) {
//					alert('lalallala');
//					var imgList = [ {
//						title : 'img1',
//						value : 'http://smfactorytest.10jqka.com.cn/icpFiles/00/00/rBB-gVWOFKeij7O4AACgTSAoOcA314_big.jpg'
//					} ];
//					callback(imgList);
//				},
				font_formats : "Times New Roman=times new roman,times;"
						+ "Arial=arial,helvetica,sans-serif;"
						+ "Arial Black=arial black,avant garde;"
						+ "Georgia=georgia,palatino;" + "Helvetica=helvetica;"
						+ "Symbol=symbol;"
			});

}

/**
 * 设置编辑器内容
 * 
 * @param cnt
 */
function setEditorContent(id, cnt) {
	var curEditor = tinymce.get(id);
	
	if (curEditor != null && cnt != null && cnt.length > 0) {
		var regex = /\/n$/;
		var endsWithFlag = regex.test(cnt);
		if (endsWithFlag) {
			// 截取掉最后一个/n字符串 因为jdbc从数据库查询出来的字符串，如果是以</p>结尾的，会自动追加/n
			cnt = cnt.substr(0, cnt.length - 2);
		}
		curEditor.setContent(cnt);
	}
}

/**
 * 格式化0，1=》是，否
 * 格式化1,2
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function showYesFormatter(value, row, index) {
	if (value == '1') {
		return "是";
	} else {
		return "否";
	}
}
function showDelFlagFormatter(value, row, index) {
	if (value == '1') {
		return "冻结";
	} else {
		return "正常";
	}
}
//function showDictValueFormatter(value,row,Dict){
//	if(value == '1'){
//	return "券商";
//	}else {
//		return "银行";
//	}
//}
function showReceiveMsgFlagFormatter(value,row,AppUser){
	if(value == '1'){
		return "拒绝";
	}else{
		return "接收";
	}
}
function showFriendInviteFormatter(value,row,AppUser){
	if(value == '1'){
		return "拒绝";
	}else{
		return "接收";
	}
}
function showDelFlagFormatter(value,row,AppUser){
	if(value == '0'){
		return "正常";
	}else{
		return "冻结";
	}
}
function showPayTypeFormatter(value,row,index){
	if(value == '1'){
		return "付款";
	}else{
		return "退款";
	}
}
function showPayStateFormatter(value,row,index){
	if(value == '1'){
		return "成功";
	}else if (value == '2'){
		return "失败";
	}else{
		return "进行中";
	}
}
/**
 * 为input初初始化下拉框式的zTrre树
 * 
 * @param treeId
 *            树id
 * @param inputId
 *            输入框id
 * @param divId
 *            树容器id
 * @param hiddenInputId
 *            inputId对应隐藏输入框Id 用于记录对应记录的id
 */
function initZTree4InputWithSetting(treeId, setting,inputId,hiddenInputId) {
	//将inputId,hiddenInputId的值置空
	resetValue(inputId,hiddenInputId);
	
	// ztree加载
	$.fn.zTree.init($(treeId), setting);
}

/**
 * 置空对应标签的值
 * @param inputId
 * @param hiddenInputId
 */
function resetValue(inputId,hiddenInputId){
	var inputObj = $(inputId);
	var hiddenInputObj = $(hiddenInputId);
	
	if(inputObj != null){
		inputObj.val('');
	}
	if(hiddenInputObj != null){
		hiddenInputObj.val('');
	}
}


/**
 * ztree设置
 * 
 * @param url
 * @return
 */
function defaultZTreeSetting(treeId, url, inputId, divId, hiddenInputId) {
	var options = {
		async : {
			enable : true,
			contentType : "application/json",
			url : url
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		check : {
			enable : true,
			checkStyle : "checkbox",
			chkboxType : {
				'Y' : '',
				"N" : ''
			},
			nocheckInherit : false
		},
		view : {
			selectedMulti : false
		},
		callback : {
			onCheck : function(event, treeId, treeNode, clickFlag) {
				if (treeNode.checked) {
					$(hiddenInputId).val(treeNode.id);
					$(inputId).val(treeNode.name);
					$(divId).slideUp('fast');
				} else {
					$(hiddenInputId).val('');
					$(inputId).val('');
					$(divId).slideUp('fast');
				}
			},
			beforeCheck : function(treeId, treeNode) {
				var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
				var checkNodes = zTreeObj.getCheckedNodes(true);
				var flag = false;
				$.each(checkNodes, function(i, item) {
					if (treeNode == item) {
						flag = true;
					}
				});
				if (flag) {
					zTreeObj.cancelSelectedNode(treeNode);
				} else {
					zTreeObj.checkAllNodes(false);
				}
				return true;
			}
		}
	}

	return options;
}


function adminUserZTreeSetting(treeId, url, inputId, divId, hiddenInputId) {
	var options = {
		async : {
			enable : true,
			contentType : "application/json",
			url : url
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		check : {
			enable : true,
			checkStyle : "checkbox",
			chkboxType : {
				'Y' : '',
				"N" : ''
			},
			nocheckInherit : false
		},
		view : {
			selectedMulti : false
		},
		callback : {
			onCheck : function(event, treeId, treeNode, clickFlag) {
				if (treeNode.checked) {
					
					//设置隐式input标签的值
					$(hiddenInputId).val(treeNode.id);
					//设置显式input标签的值
					$(inputId).val(treeNode.name);
					//隐藏ztree
					$(divId).slideUp('fast');
					
					//当当前选中管理员为机构管理员时，显示机构标签
					if(treeNode.type == 1){
						
						//机构类型时,显示机构下拉框
						showOrgCombobox();
					}else{
						
						//系统类型时，隐藏机构下拉框
						hideOrgCombobox();
					}
					
					
				} else {
					
					//隐式input值置空
					$(hiddenInputId).val('');
					//显式input值置空
					$(inputId).val('');
					//隐藏ztree
					$(divId).slideUp('fast');
				}
			},
			beforeCheck : function(treeId, treeNode) {
				var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
				var checkNodes = zTreeObj.getCheckedNodes(true);
				var flag = false;
				
				$.each(checkNodes, function(i, item) {
					if (treeNode == item) {
						flag = true;
					}
				});
				
				if (flag) {
					zTreeObj.cancelSelectedNode(treeNode);
				} else {
					zTreeObj.checkAllNodes(false);
				}
				
				return true;
			}
		}
	}

	return options;
}

/**
 * 显示机构下拉框
 */
function showOrgCombobox(){
	$(".colorgidtr").show();
	//加载机构列表数据
	$('#create_admin_user_colOrgId').combobox({   
	    url:root + '/rs/common/queryOrgs4select',  
	    valueField:'orgId',   
	    textField:'colOrgName',
	    width:150
	});
}

/**
 * 隐藏机构下拉框
 */
function hideOrgCombobox(){
	//系统管理员时，机构信息置为空
	$(".colorgidtr").hide();
	$("#update_admin_user_colOrgId").combobox('setValue','');
}

/**
 * ztree设置
 * 
 * @param url
 * @return
 */
function updateRoleZTreeSetting(row, treeId, url, inputId, divId, hiddenInputId) {
	var options = {
		async : {
			enable : true,
			contentType : "application/json",
			url : url
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		check : {
			enable : true,
			checkStyle : "checkbox",
			chkboxType : {
				'Y' : '',
				"N" : ''
			},
			nocheckInherit : false
		},
		view : {
			selectedMulti : false
		},
		callback : {
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				var node = treeObj.getNodeByParam("id", row.parentId, null);
				if (node != null) {
					node.checked = true;
					treeObj.updateNode(node);
				}
			},
			onCheck : function(event, treeId, treeNode, clickFlag) {
				if (treeNode.checked) {
					$(hiddenInputId).val(treeNode.id);
					$(inputId).val(treeNode.name);
					$(divId).slideUp('fast');
				} else {
					$(hiddenInputId).val('');
					$(inputId).val('');
					$(divId).slideUp('fast');
				}
			},
			beforeCheck : function(treeId, treeNode) {
				var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
				var checkNodes = zTreeObj.getCheckedNodes(true);
				var flag = false;
				$.each(checkNodes, function(i, item) {
					if (treeNode == item) {
						flag = true;
					}
				});
				if (flag) {
					zTreeObj.cancelSelectedNode(treeNode);
				} else {
					zTreeObj.checkAllNodes(false);
				}
				return true;
			}
		}
	}

	return options;
}

/**
 * 显示ztree
 * 
 * @param inputIdName
 *            输入框id名称
 * @param treeDivIdName
 *            产品类型树容器id名称
 * @param panelIdName
 *            面板id名称
 */
function showZTree(inputIdName, treeDivIdName, panelIdName) {
	var inputOffset = $("#" + inputIdName).offsetParent();

	$("#" + treeDivIdName).css({
		top : inputOffset.top + "px",
		background : "#F0F8FF"
	}).slideDown("fast");
	$("#" + panelIdName).bind("mousedown", {
		inputIdName : inputIdName,
		treeDivIdName : treeDivIdName,
		panelIdName : panelIdName
	}, onBodyDownHideZTree);
	$("#" + panelIdName).bind("mousewheel", {
		inputIdName : inputIdName,
		treeDivIdName : treeDivIdName,
		panelIdName : panelIdName
	}, onBodyDownHideZTree);
}
/**
 * 隐藏ztree事件
 * 
 * @param event
 * @return
 */
function onBodyDownHideZTree(event) {
	var inputIdName = event.data.inputIdName;
	var treeDivIdName = event.data.treeDivIdName;
	var panelIdName = event.data.panelIdName;

	// 当鼠标或滑轮坐标不在产品树显示层范围内时隐藏产品类型树
	if (!(event.target.id == inputIdName || event.target.id == treeDivIdName || $(
			event.target).parents("#" + treeDivIdName).length > 0)) {
		hideZTree(treeDivIdName, panelIdName);
	}
}
/**
 * 隐藏树
 * 
 * @param treeDivIdName
 *            树容器id名称
 * @param panelIdName
 *            面板id名称
 */
function hideZTree(treeDivIdName, panelIdName) {
	$("#" + treeDivIdName).fadeOut("fast");
	$("#" + panelIdName).unbind("mousedown", onBodyDownHideZTree);
	$("#" + panelIdName).unbind("mousewheel", onBodyDownHideZTree);
}

/**
 * 根据权限控制toolbar
 * 
 * @param permission
 * @param toolBars
 *            必须遵循**增删改查顺序
 * @return
 */
// function getPermToolBars(permission, toolBars){
// var permToolBars = [];
// $.each(toolBars,function(index,item){
// if(item != null && permission.toString().charAt(index) == '1'){
// permToolBars.push(item);
// }
// });
//	
// return permToolBars;
// }
function getPermToolBars(permission, toolBars) {
	var permToolBars = [];

	var permArr = [];
	if (permission != null && permission.length > 0) {
		permArr = permission.split(",");
	}
	$.each(toolBars, function(index, item) {
		var handlerName = item.handler.name;
		$.each(permArr, function(i, perm) {
			if (handlerName == perm) {
				permToolBars.push(item);
			}
		});
	});
	return permToolBars;
}


/**
 * 更新标签页
 */
function updateTab(tabId, tabTitle, cnt) {
	var tab = $('#tabs').tabs('getTab', tabTitle);
	$('#tabs').tabs('update', {
		tab : tab,
		options : {
			id : tabId,
			content : cnt
		}
	});
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
		var subtitle = $(this).children(".tabs-closable").text();

		// $('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}

// 绑定右键菜单事件
function tabCloseEven() {
	$('#mm').menu({
		onClick : function(item) {
			closeTab(item.id);
		}
	});
	return false;
}

function closeTab(action) {
	var alltabs = $('#tabs').tabs('tabs');
	var currentTab = $('#tabs').tabs('getSelected');
	var allTabtitle = [];
	$.each(alltabs, function(i, n) {
		allTabtitle.push($(n).panel('options').title);
	})

	switch (action) {
	case "tabupdate":
		var iframe = $(currentTab.panel('options').content);
		// var src = iframe.attr('src');
		var divId = iframe.attr("id");
		var menuid = divId.substring(5, divId.length);
		var node = {
			menuid : menuid
		};
		$('#tabs').tabs('update', {
			tab : currentTab,
			options : {
				// content : createFrame(src)
				content : createBlankDiv(node)
			}
		});
		dispatch(node);
		break;
	case "close":
		var currtab_title = currentTab.panel('options').title;
		if (currtab_title != onlyOpenTitle) {
			$('#tabs').tabs('close', currtab_title);
		}
		break;
	case "closeall":
		$.each(allTabtitle, function(i, n) {
			if (n != onlyOpenTitle) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeother":
		var currtab_title = currentTab.panel('options').title;
		$.each(allTabtitle, function(i, n) {
			if (n != currtab_title && n != onlyOpenTitle) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeright":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

		if (tabIndex == alltabs.length - 1) {
			// alert('1!!');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i > tabIndex) {
				if (n != onlyOpenTitle) {
					$('#tabs').tabs('close', n);
				}
			}
		});
		break;
	case "closeleft":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
		if (tabIndex == 1) {
			// alert('2');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i < tabIndex) {
				if (n != onlyOpenTitle) {
					$('#tabs').tabs('close', n);
				}
			}
		});
		break;
	case "exit":
		$('#closeMenu').menu('hide');
		break;
	}
}

/**
 * 根据资源抽取方法名
 * 
 * @param resource
 *            "user:1001"格式
 * @return
 */
function getMethodName(resourceName) {
	// var arr = resource.split(":");
	// if(arr.length > 0){
	// return arr[0]+"Manage";
	// }
	var result = resourceName + "Manage";
	return result;
}

/**
 * 根据资源抽取权限值
 * 
 * @param resource
 *            "user:1001"格式
 * @return
 */
function getPermission(resource) {
	var arr = resource.split(":");
	if (arr.length > 1) {
		return arr[1];
	}
}

function queryBtn(qb, qc) {
	$(qb).linkbutton({
		iconCls : 'icon-search'
	});
	$(qc).linkbutton({
		iconCls : 'icon-cut'
	});
}

/**
 * 右下角窗口提示
 * 
 * @param msg
 *            提示消息
 * @param title
 *            窗口主题
 */
function showmessage(msg, title) {
	title = title == undefined ? '提示' : title;
	$.messager.show({
		title : title,
		msg : msg,
		showType : 'show',
		width : 300,
		height : 150
	});
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

function submit_clear(submit, clear) {
	$(submit).linkbutton({
		iconCls : 'icon-ok'
	});
	$(clear).linkbutton({
		iconCls : 'icon-cancel'
	});
}

/**
 * @author zhouhuachang
 * 
 * @requires jQuery
 * 
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		try {
			$.messager.progress('close');
			$.messager.alert('错误', XMLHttpRequest.responseText);
		} catch (e) {
			alert(XMLHttpRequest.responseText);
		}
	}
});

/**
 * 增加formatString功能
 * 
 * @author zhouhuachang
 * 
 * @example $.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
$.formatString = function(str) {
	for (var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @author 孙宇
 * 
 * @returns list
 */
$.stringToList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for (var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

/**
 * Create a cookie with the given key and value and other optional parameters.
 * 
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * @example $.cookie('the_cookie', 'the_value', { expires: 7, path: '/', domain:
 *          'jquery.com', secure: true });
 * @desc Create a cookie with all available options.
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * @example $.cookie('the_cookie', null);
 * @desc Delete a cookie by passing null as value. Keep in mind that you have to
 *       use the same path and domain used when the cookie was set.
 * 
 * @param String
 *            key The key of the cookie.
 * @param String
 *            value The value of the cookie.
 * @param Object
 *            options An object literal containing key/value pairs to provide
 *            optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date
 *         from now on in days or a Date object. If a negative value is
 *         specified (e.g. a date in the past), the cookie will be deleted. If
 *         set to null or omitted, the cookie will be a session cookie and will
 *         not be retained when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default:
 *         path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie
 *         (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be
 *         set and the cookie transmission will require a secure protocol (like
 *         HTTPS).
 * @type undefined
 * 
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 * 
 * Get the value of a cookie with the given key.
 * 
 * @example $.cookie('the_cookie');
 * @desc Get the value of a cookie.
 * 
 * @param String
 *            key The key of the cookie.
 * @return The value of the cookie.
 * @type String
 * 
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
$.cookie = function(key, value, options) {
	if (arguments.length > 1 && (value === null || typeof value !== "object")) {
		options = $.extend({}, options);
		if (value === null) {
			options.expires = -1;
		}
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		return (document.cookie = [
				encodeURIComponent(key),
				'=',
				options.raw ? String(value) : encodeURIComponent(String(value)),
				options.expires ? '; expires=' + options.expires.toUTCString()
						: '', options.path ? '; path=' + options.path : '',
				options.domain ? '; domain=' + options.domain : '',
				options.secure ? '; secure' : '' ].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key)
			+ '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

// num表示要四舍五入的数,v表示要保留的小数位数。
function decimal(num, v) {
	var vv = Math.pow(10, v);
	return Math.round(num * vv) / vv;
}

//搜索框自动完成
//$('#idname').autocombobox(params);
$.fn.extend({
	autocombobox:function(params){
		var that=this;
		this.localdata = null;
		this.tmpValue = '';
		var opts = $.extend({},params,{
			mode:'remote',
			hasDownArrow:false,
			loader: function(param, success,error){
				var result = [];
				if(param.q != undefined && param.q.length > 0) {
					result = filter(param.q);
				}
				success(result);
			}
		});
	
		if(opts.url != undefined) {
			$.getJSON(opts.url, function(json){
				that.localdata = json;
				$(that).combobox(opts);
			})
		} else {
			if(opts.data != undefined) {
				that.localdata = opts.data
			}
			$(that).combobox(opts);
		}
		
		function filter(q){
			if(opts.multiple == true && q.indexOf(',') != -1){
				q = q.substr(q.lastIndexOf(',') + 1)
				q = $.trim(q);
			}
			var re = new RegExp(q,'i');
			var result = [];
			$.each(that.localdata,function(index,item){
				if(re.test(item[opts.textField])) {
					result.push(item);
				}
			});					
			return result;
		}
	}
});
