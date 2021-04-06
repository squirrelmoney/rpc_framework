package com.lnsf.rpc;

import com.lnsf.rpc.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: rpc-framwork
 * @description:
 * @author: money
 * @create: 2021-03-25 11:26
 **/
@RpcScan(basePackage = {"com.lnsf.rpc"})
public class SocketClientMain {
    public static void main(String[] args) throws InterruptedException {
        //
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        long l = System.currentTimeMillis();
        helloController.testTime();
        System.out.println("Socket调用100万条数据所需时间："+(System.currentTimeMillis()-l));
    }
}
