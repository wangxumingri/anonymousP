package dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:
 */
public class JdkProxy {

    private Object targetClass;

    public Object newProxy(Object targetClass) {
        this.targetClass = targetClass;
        /**
         *  newProxyInstance（...）参数：
         *      ClassLoader loader      ：目标类的类加载器
         *      Class<?>[] interfaces   ：目标类实现的接口
         *      InvocationHandler h)    ：InvocationHandler接口,增强的逻辑。可使用匿名内部类，或创建一个实例
         */
        return Proxy.newProxyInstance(targetClass.getClass().getClassLoader(), targetClass.getClass().getInterfaces(), new InvocationHandler() {
            /**
             * 参数：
             *      object:目标类
             *      method:执行的方法
             *      args :方法的入参
             * */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                System.out.println("前置");
                if ("save".equalsIgnoreCase(method.getName())){
                    /**不能使用第一个参数：会报错*/
                   result =  method.invoke(targetClass, args);
                }else if ("print".equalsIgnoreCase(method.getName())){
                    if (args[0].getClass().equals(String.class)){
                       result = method.invoke(targetClass,args[0].toString()+"plus");
                    }
                }else {
                    System.out.println("query方法权限不足");
                }
                System.out.println("后置");
                /**返回的是方法执行结果*/
                return result;
            }
        });
    }
}
