package dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:cglib动态代理
 */
public class CgilbProxy implements MethodInterceptor {
    private Object targetClass;

    public Object getCglibProxy(Object targetClass) {
        this.targetClass = targetClass;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass.getClass());
        enhancer.setCallback(this);

        return enhancer.create();
    }

    /**
     * 参数:
     *  代理类
     *  方法
     *  参数
     *  方法代理
     *
     * */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("前置");
        Object result = null;
        result = methodProxy.invoke(targetClass, objects);
        System.out.println("后置");
        return result;
    }
}
