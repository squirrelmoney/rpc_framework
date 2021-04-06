package com.lnsf.rpc.annotation;


import com.lnsf.rpc.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * @program: rpc-framwork
 * @description: 扫描定制注释
 * @author: money
 * @create: 2021-03-25 11:16
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface RpcScan {

    String[] basePackage();

}
