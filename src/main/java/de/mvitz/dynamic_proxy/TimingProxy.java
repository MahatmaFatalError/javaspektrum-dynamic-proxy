package de.mvitz.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TimingProxy implements InvocationHandler {

    private final Object subject;

    private TimingProxy(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        try {
            return method.invoke(subject, args);
        } finally {
            long duration = System.nanoTime() - start;
            System.out.println(method.getName() + " took: " + duration + "ns");
        }
    }

    public static <T> T time(T instance) {
        return (T) Proxy.newProxyInstance(
                instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(),
                new TimingProxy(instance)
        );
    }
}
