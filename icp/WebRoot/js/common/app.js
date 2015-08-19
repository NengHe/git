$(function() {
	app = (function() {

		return {
			dateStyle : 'yyyy-MM-dd',
			dateStyle2 : 'yyyy-M-d',
			dateTimeStyle : 'yyyy-MM-dd hh:mm:ss'
		};
	})();
});

Date.prototype.formatDate = function(format) // author: meizz
{
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

//扩展easyui表单的验证  
$.extend($.fn.validatebox.defaults.rules, {  
    mobile: {	//移动手机号码格式验证  
        validator: function (value) {  
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            var valid = false;
            
            //验证手机号码格式
            valid = reg.test(value);
            
            return valid;  
        },  
        message: '输入手机号码格式不准确.'  
    },
    uniqueMobile: {		//验证appUser手机号码唯一
        validator: function (value, param) {  
            var valid = false;
            var appUserId = '';

            if(param != null){
            	appUserId = param[0];
            }
            
            //验证手机号码唯一
            valid = checkUniquePhone(value, appUserId);
            
            return valid;  
        },  
        message: '该手机号码已被注册.'  
    },
    uniqueOrgCode: {		//验证机构代码唯一
    	validator: function (value, param) {  
    		var valid = false;
    		var orgId = '';
    		
    		if(param != null){
    			orgId = param[0];
            }
    		
    		//验证机构代码唯一
    		valid = checkUniqueOrgCode(value, orgId);
    		
    		return valid;  
    	},  
    	message: '该机构代码已被使用.'  
    }
});


