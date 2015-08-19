/**
 * 常用变量配置
 */
$(function() {
	root = "/icp";
	paramconfig = (function() {
		return {
			toolips : {// toolip提示长途
				showlength : 10
			},
			page : {// datagrid页数大小
				size_10 : 10,
				size_5 : 5,
				size_15 : 15,
				size_20 : 20
			},
			pagelist : {// datagrid页数大小
				list : [ 5, 10, 15, 20 ]
			}
		};
	})();
});

