package com.farmacloud.server.shiro.guice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.shiro.authz.aop.RoleAnnotationMethodInterceptor;

public class ShiroMethodInterceptor implements MethodInterceptor
{
    private RoleAnnotationMethodInterceptor roleAnnotationMethodInterceptor = new RoleAnnotationMethodInterceptor();
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return roleAnnotationMethodInterceptor.invoke(new ShiroMethodInvocation(methodInvocation));
    }
 
    private static class ShiroMethodInvocation implements org.apache.shiro.aop.MethodInvocation 
    {
        private MethodInvocation methodInvocation;
 
        public ShiroMethodInvocation(MethodInvocation methodInvocation) {
            this.methodInvocation = methodInvocation;
        }

		@Override
		public Object[] getArguments() {
			return methodInvocation.getArguments();
		}

		@Override
		public Method getMethod() {
			return methodInvocation.getMethod();
		}

		@Override
		public Object getThis() {
			return methodInvocation;
		}

		@Override
		public Object proceed() throws Throwable {
			return methodInvocation.proceed();
		}
    }
}
