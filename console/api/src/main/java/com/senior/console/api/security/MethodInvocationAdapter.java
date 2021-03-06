/*
 * @(#) MethodInvocationAdapter.java 2019-11-12
 *
 * Copyright 2020 senior. All rights reserved.
 */

package com.senior.console.api.security;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

/**
 * copy of {@link org.springframework.security.access.intercept.aspectj.MethodInvocationAdapter}
 * 
 * @author senior
 */
public final class MethodInvocationAdapter implements MethodInvocation {
    private final ProceedingJoinPoint jp;
    private final Method method;
    private final Object target;

    MethodInvocationAdapter(JoinPoint jp) {
        this.jp = (ProceedingJoinPoint) jp;
        if (jp.getTarget() != null) {
            target = jp.getTarget();
        } else {
            // SEC-1295: target may be null if an ITD is in use
            target = jp.getSignature().getDeclaringType();
        }
        String targetMethodName = jp.getStaticPart().getSignature().getName();
        Class<?>[] types = ((CodeSignature) jp.getStaticPart().getSignature())
                .getParameterTypes();
        Class<?> declaringType = jp.getStaticPart().getSignature().getDeclaringType();

        method = findMethod(targetMethodName, declaringType, types);

        if (method == null) {
            throw new IllegalArgumentException(
                    "Could not obtain target method from JoinPoint: '" + jp + "'");
        }
    }

    private Method findMethod(String name, Class<?> declaringType, Class<?>[] params) {
        Method method = null;

        try {
            method = declaringType.getMethod(name, params);
        } catch (NoSuchMethodException ignored) {
        }

        if (method == null) {
            try {
                method = declaringType.getDeclaredMethod(name, params);
            } catch (NoSuchMethodException ignored) {
            }
        }

        return method;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return jp.getArgs();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public Object proceed() throws Throwable {
        return jp.proceed();
    }
}
