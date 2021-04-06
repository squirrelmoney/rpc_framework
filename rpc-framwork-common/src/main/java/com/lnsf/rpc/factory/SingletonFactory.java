package com.lnsf.rpc.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: rpc-framwork
 * @description: 获取单例对象的工厂类
 *               单例模式只能有一个实例，减少了内存开支，特别是一个对象需要频繁的创建，销毁的类
 *               只生产一个实例，也减少了系统的开销，当一个对象的产生需要比较多的资源的时候，
 *               如配置资源等产生其它依赖的对象时候，则可以通过这种设计模式在应用启动的时候就产生这个对象，
 *               然后用于驻留在内存中，可以直接取来使用。
 * @author: money
 * @create: 2021-03-25 09:51
 **/
public final class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new HashMap<>();

    private SingletonFactory() {
    }

    public static <T> T getInstance(Class<T> c) {
        String key = c.toString();
        Object instance;
        synchronized (SingletonFactory.class) {
            instance = OBJECT_MAP.get(key);
            if (instance == null) {
                try {
                    instance = c.getDeclaredConstructor().newInstance();
                    OBJECT_MAP.put(key, instance);
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return c.cast(instance);
    }
}
