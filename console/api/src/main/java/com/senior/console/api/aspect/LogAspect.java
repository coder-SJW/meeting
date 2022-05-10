
package com.senior.console.api.aspect;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.senior.console.api.security.LoginInfoService;
import com.senior.domain.bo.request.SysLogCreateBoRequest;
import com.senior.service.component.SysLogAppender;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private SysLogAppender syslogAppender;
    @Resource
    private LoginInfoService loginInfoService;

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.senior.console.api.controller.*Controller.*(..))")
    public void pointCut() {

    }

    /**
     * 前置操作
     *
     * @param point 切入点
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        List<String> ignores = Lists.newArrayList("register", "upload");
        StringBuffer url = request.getRequestURL();
        List<String> list = ignores.stream().filter(o -> url.toString().contains(o)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(list)) {
            return proceed;
        }
        String ip = request.getRemoteAddr();
        String typeName = point.getSignature().getDeclaringTypeName();
        String signatureName = point.getSignature().getName();
        String method = typeName + " -> " + signatureName;
        String httpMethod = request.getMethod();
        Object[] args = point.getArgs();
        String params = "";
        Map<String, String[]> parameterMap = request.getParameterMap();
        if ("get".equalsIgnoreCase(httpMethod)) {
            params = JSON.toJSONString(parameterMap);
        } else {
            Object object = args[0];
            params = JSON.toJSONString(object, SerializerFeature.NotWriteDefaultValue);
        }
        long startTime = System.currentTimeMillis();
        long cost = System.currentTimeMillis() - startTime;

        SysLogCreateBoRequest boRequest = new SysLogCreateBoRequest();
        boRequest.setPassport(loginInfoService.getLoginAccount().getPassport());
        boRequest.setUrl(url.toString());
        boRequest.setMethod(method);
        boRequest.setParams(params);
        boRequest.setIp(ip);
        boRequest.setCost(cost);
        boRequest.setCreateTime(startTime);
        // 异步插入日志，不要影响接口响应时间
        syslogAppender.append(boRequest);
        return proceed;
    }

}
