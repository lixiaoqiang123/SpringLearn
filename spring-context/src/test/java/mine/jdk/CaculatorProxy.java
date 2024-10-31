package mine.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CaculatorProxy {

    public static Caculator getProxy(final Caculator caculator){
        ClassLoader classLoader = caculator.getClass().getClassLoader();
        Class<?>[] interfaces = caculator.getClass().getInterfaces();
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                try {
                    result = method.invoke(caculator,args);
                }catch (Exception e){

                }
                return result;
            }
        };
        Object proxy = Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
        return (Caculator) proxy;
    }
}
