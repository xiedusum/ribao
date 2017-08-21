package com.whz.ribao.web;

import com.whz.ribao.entity.result.ExceptionMsg;
import com.whz.ribao.entity.result.JsonResult;
import com.whz.ribao.entity.result.PageResult;
import com.whz.ribao.entity.result.Result;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BaseController {

    protected Logger logger = Logger.getLogger(this.getClass());
    
    protected Result result(ExceptionMsg msg){
    	return new Result(msg);
    }
    protected Result result(){
    	return new Result();
    }
    
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected JsonResult jsonResult(){
        return new JsonResult();
    }

    protected JsonResult jsonResult(Object obj){
        return new JsonResult(obj);
    }

    protected JsonResult jsonResult(ExceptionMsg msg){
        return new JsonResult(msg);
    }

    protected JsonResult jsonResult(Object obj, ExceptionMsg msg){
        return new JsonResult(obj, msg);
    }

    protected PageResult pageResult(List rows, Integer page, Integer pageSize, Long total){
        return new PageResult(rows, page, pageSize, total);
    }

}
