package com.lnsf.rpc.annotation;

import java.lang.annotation.*;

/**
 * @program: rpc-framwork
 * @description: RPC引用注释，自动连接服务实现类
 * @author: money
 * @create: 2021-03-25 11:16
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";

}
