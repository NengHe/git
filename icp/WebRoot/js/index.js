window.onload = function() {
	$('#loading-mask').fadeOut();// 去除Loading
}

$.ajaxSetup({"complete":myfunc}); 

function myfunc(XMLHttpRequest, textStatus, errorThrown) {
	// alert(XMLHttpRequest.status);
	if(XMLHttpRequest.status == 303) {
		window.location = "login.jsp";
	} else if(XMLHttpRequest.status == 408) {
		window.location = "login.jsp";
	}
 }
$.ajaxSetup({cache:false}); 

var onlyOpenTitle = "欢迎使用";// 不允许关闭的标签的标题
var user = null;
var tree = null;

$(function() {
	
	initLeftMenu();
	tabClose();
	tabCloseEven();
	registerLoginout();
	showLoginUser();
	
});

function showLoginUser() {
	$.ajax( {
		async: false,
		type: 'GET',
		url: root + '/rs/adminUser/getLoginUser',
		dataType: 'json',
		success: function(data) {
			$('#userSpan').text(data.result.colAdminUsername);
			user = data.result;
		}
	});
}

function registerLoginout() {
	$("#loginOut").click(function() {
		$.messager.confirm("确认", "确定退出系统吗?", function(flag) {
			if (flag) {
				$.ajax( {
					type : 'GET',
					url : root + '/rs/logout',
					dataType : 'json',
					success : function(data) {
						if ('fail' == data.result) {
							$.messager.alert('错误', '注销失败');
						} else {
							window.location.href = 'login.jsp';
						}
					}
				});
			}
		});
	});
}

// 初始化左侧
function initLeftMenu() {
	// ztree加载
	$.fn.zTree.init($("#indexleftMenuTree"),leftMenuTreeSetting(root+'/rs/adminUser/getMenu'));
}
/**
 * 主页左边树树形设置
 * 
 * @param url
 *            请求节点数据url（return json）
 * @param treeId
 *            ztree id
 */
function leftMenuTreeSetting(url){
	var zTreeOnClick=function(event, treeId, treeNode){
		zTreeOnClickForLeftMenu(event, treeId, treeNode);
	}
	
	var options={
		async:{
			enable:true,
			contentType:"application/json",
			url:url
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view:{
			dblClickExpand:false
		},
		callback: {
			onClick: zTreeOnClick
		}
	}
	
	return options;
}

function zTreeOnClickForLeftMenu(event, treeId, treeNode) {
    if(null!=treeNode.resourceName){// 有resourceName的节点才能打开tab
    	addTab( treeNode );
    }
};

//添加tab
function addTab(node) {
	var title = node.name;
	var methodName = getMethodName(node.resourceName);

	if (!$('#tabs').tabs('exists', node.name)) {
		$('#tabs').tabs('add', {
			title : title,
			content : createBlankDiv(methodName),
			closable : true
		});
		dispatch(node);
	} else {
		$('#tabs').tabs('select', title);
		$('#mm-tabupdate').click();
	}
	
	tabClose();
}

// 建空div容器
function createBlankDiv(methodName) {
	queryAndGrid = [];
	queryAndGrid.push('<div id="main_' + methodName + '">');
	queryAndGrid.push("</div>");
	return queryAndGrid.join("");
}

// 按resource渲染不同组件
function dispatch(node) {
	var methodName = getMethodName(node.resourceName);
//	var permission = getPermission(resource);
	var title = node.name;
	var permission = node.permission;
	if(permission != null && permission != ''){
		var method = "this." + methodName + "('" + title + "','" + permission + "')";
		//根据方法名调用对应的js文件
		eval(method);
	}
}

/**
 * used for select menu
 * 
 * @param value
 *            节点名称
 */
//function doSearchIndexLeftMenuNode(value){
//	var treeObj = $.fn.zTree.getZTreeObj("indexleftMenuTree");
//	var node=treeObj.getNodeByParam("name", value, null);;
//	treeObj.selectNode(node);
//}


//function createRightPanel(subtitle,resource){
//	var methodName = getMethodName(resource);
//	var permission = getPermission(resource);
//	if(permission != null && permission != ''){
//		var method = "this." + methodName + "('" + subtitle + "'," + permission + ")";
//		//根据方法名调用对应的js文件
//		eval(method);
//	}
//}
