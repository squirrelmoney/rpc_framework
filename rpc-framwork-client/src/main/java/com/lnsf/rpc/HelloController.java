package com.lnsf.rpc;

import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.service.Hello;
import com.lnsf.rpc.service.HelloService;
import com.lnsf.rpc.service.V0.Simple;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


/**
 * @author smile2coder
 */
@Component
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;


    public void test(int time) throws InterruptedException {
        String hello = this.helloService.hello2(new Hello(String.valueOf(time), "222"));
        //如需使用 assert 断言，需要在 VM options 添加参数：-ea
        assert "Hello description is 222".equals(hello);
        for (int i = 0; i < 10; i++) {
            System.out.println(helloService.hello(new Hello("111", "222")));
        }
    }

    public void testTime() {
        List<Simple> simples = this.helloService.testData();
        System.out.println(simples.size());
    }
    public void testTime2(List<Long> temp,List<Simple> SimpleList) throws InterruptedException, IOException {
        List<Simple> simples = this.helloService.testData2(SimpleList);
        System.out.println(simples.size()+"test2");
    }
}
