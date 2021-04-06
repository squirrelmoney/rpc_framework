package com.lnsf.rpc.extension;

import java.lang.annotation.*;

@Documented//@Documented的作用是对自定义注解进行标注，如果使用@Documented标注了，在生成javadoc的时候就会把@Documented注解给显示出来。
@Retention(RetentionPolicy.RUNTIME)//注解保留在程序运行期间，此时可以通过反射获得定义在某个类上的所有注解
@Target(ElementType.TYPE) //只允许用在类的字段上
public @interface SPI {
}
