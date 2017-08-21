package com.whz.ribao.entity.result;

import com.whz.ribao.entity.Base;

public class Result extends Base {
	/** 返回信息码*/
	private String code = "000000";
	/** 返回信息内容*/
	private String msg = "操作成功";
    /** 返回是否成功  **/
    private Boolean success = true;

	public Result() {
	}

	public Result(ExceptionMsg msg){
        exceptionMsg(msg);
	}

	public void exceptionMsg(ExceptionMsg msg){
        this.code =msg.getCode();
        this.msg =msg.getMsg();
        if (!ExceptionMsg.SUCCESS.getCode().equals(this.code))
            success = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
