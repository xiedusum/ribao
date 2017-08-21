package com.whz.ribao.core;

import com.whz.ribao.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/8/13.
 */
public class SessionContext {

    public static UserInfo getUserInfo(){
        SimplePrincipalCollection simplePrincipalCollection =(SimplePrincipalCollection) SecurityUtils.getSecurityManager().getSession(new WebSessionKey(getSession().getId(), getRequest(), getResponse()))
                .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        return (UserInfo) simplePrincipalCollection.getPrimaryPrincipal();
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static HttpSession getSession(){
        return getRequest().getSession();
    }
}
