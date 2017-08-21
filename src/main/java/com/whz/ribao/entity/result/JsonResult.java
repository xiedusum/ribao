package com.whz.ribao.entity.result;

import org.apache.commons.lang3.StringUtils;

/**
 * 封装json结果集
 */
public class JsonResult extends Result {

    private Object obj;

    public JsonResult() {
    }

    public JsonResult(Object obj) {
        setObj(obj);
    }

    public JsonResult(ExceptionMsg msg) {
        super(msg);
    }

    public JsonResult(Object obj, ExceptionMsg msg) {
        super(msg);
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;

        if ((obj == null && StringUtils.isEmpty(getCode())) ||
                (obj instanceof String && StringUtils.isEmpty(String.valueOf(obj)))) {

            exceptionMsg(ExceptionMsg.FAILED);
        }
    }

}
