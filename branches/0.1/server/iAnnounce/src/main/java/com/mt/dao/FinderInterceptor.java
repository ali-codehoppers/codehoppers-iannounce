package com.mt.dao;

import com.mt.idao.IFinderExecutor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

public class FinderInterceptor implements IntroductionInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        IFinderExecutor fe = (IFinderExecutor) invocation.getThis();
        String methodName = invocation.getMethod().getName();
        if (methodName.startsWith("find")) {
            return fe.executeFinder(invocation.getMethod(), invocation.getArguments());
        } else {
            return invocation.proceed();
        }
    }

    public boolean implementsInterface(Class intf) {
        return intf.isInterface() && IFinderExecutor.class.isAssignableFrom(intf);
    }
}
