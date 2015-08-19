package com.hexin.icp.bean;

import com.hexin.core.constant.Constants;

/**
 * 返回前台通用信息格式 默认:code=1，表示调用接口正确，无异常
 * 
 * @author lzs 2014-5-13 下午02:10:31
 */
public class ReturnMessage {
	
    private String code = Constants.SUCCESSED; // 通常1:成功;0:失败
    private String message;
    private Object result;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }

}
