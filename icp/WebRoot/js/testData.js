var testData = {
	"permission":{
		"role_list" : [{"role_id":1,"role_name":"admin","role_desc":"管理员","parent_name":"超级管理员"},
			{"role_id":2,"role_name":"admin2","role_desc":"管理员2","parent_name":"超级管理员"}],
			
		"role_tree_data" : [{"id":1,"name":"管理员","pId":""},
			{"id":2,"name":"管理员2","pId":"1"}],
			
		"role_add" : {"code":"1","message":"提示的错误信息","result":"返回的结果集"},
		
		"resource_tree" : [{
			"resourceId" : 1,
			"label" : "活动管理",
			"operationList" : [{
				"resourceOperationID" : 1,
				"operationName" : "增",
				"isOperationEnabled" : "yes"
			},{
				"resourceOperationID" : 2,
				"operationName" : "删",
				"isOperationEnabled" : "yes"
			},{
				"resourceOperationID" : 3,
				"operationName" : "改",
				"isOperationEnabled" : "no"
			},{
				"resourceOperationID" : 4,
				"operationName" : "查",
				"isOperationEnabled" : "yes"
			}],
			"children" : [{
				"resourceId" : 2,
				"label" : "活动类型",
				"operationList" : [{
					"resourceOperationID" : 5,
					"operationName" : "增",
					"isOperationEnabled" : "yes"
				},{
					"resourceOperationID" : 6,
					"operationName" : "删",
					"isOperationEnabled" : "yes"
				},{
					"resourceOperationID" : 7,
					"operationName" : "改",
					"isOperationEnabled" : "no"
				},{
					"resourceOperationID" : 8,
					"operationName" : "查",
					"isOperationEnabled" : "yes"
				}]
			}]
		},{
			"resourceId" : 3,
			"label" : "新闻管理",
			"operationList" : [{
				"resourceOperationID" : 9,
				"operationName" : "增",
				"isOperationEnabled" : "yes"
			},{
				"resourceOperationID" : 10,
				"operationName" : "删",
				"isOperationEnabled" : "yes"
			},{
				"resourceOperationID" : 11,
				"operationName" : "改",
				"isOperationEnabled" : "no"
			},{
				"resourceOperationID" : 12,
				"operationName" : "查",
				"isOperationEnabled" : "yes"
			}],
			"children" : [{
				"resourceId" : 4,
				"label" : "新闻类型",
				"operationList" : [{
					"resourceOperationID" : 13,
					"operationName" : "增",
					"isOperationEnabled" : "yes"
				},{
					"resourceOperationID" : 14,
					"operationName" : "删",
					"isOperationEnabled" : "no"
				}]
			}]
		}
		],
	},
	
	"user":{
		"user" : [
			{"userId":1,"username":"root","showRequest":"1","showDetailInner":"0","showDetailOutter":"0","personcompany":"浙江浙商投资有限公司","personMobile":"18699524122","personName":"Bin","personEmail":"dsf@nn.com","personAddress":"防卫厅人情味儿","personCompany":"没有公司","orgCode":"0001","orgName":"Bin不要玩啦，起来干活的俱乐部","posName":"哈哈哈哈,荣格","posState":"1"}
		],
			
	},
	
	"org":{
		"appUsers" : [
			{"userId":1,"colUserMobile":"1"},
			{"userId":2,"colUserMobile":"12"},
			{"userId":3,"colUserMobile":"123"},
			{"userId":4,"colUserMobile":"133"},
			{"userId":5,"colUserMobile":"124"}
		],
			
	},

}