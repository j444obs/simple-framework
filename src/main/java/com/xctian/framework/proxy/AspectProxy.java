package com.xctian.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 模板模式，切面代理抽象类
 *
 * @author xctian
 * @date 2020/1/28
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    public void end() {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void begin() {
    }
}
