package com.lnsf.rpc.serviceImpl;

import com.lnsf.rpc.annotation.RpcService;
import com.lnsf.rpc.service.Hello;
import com.lnsf.rpc.service.HelloService;
import com.lnsf.rpc.service.V0.Simple;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:52:00
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
public class HelloServiceImpl2 implements HelloService {

    static {
        System.out.println("HelloServiceImpl2被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl2收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl2返回: {}.", result);
        return result;
    }
    @Override
    public String hello2(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
    @Override
    public List<Simple> testData() {
        List<Simple> SimpleList =new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            Map<String, Integer> map = new HashMap<String, Integer>(2);
            map.put("zhang0", i);
            map.put("zhang1", i);
            SimpleList.add(new Simple("zhang" + i, (i + 1), map));
        }
        return  SimpleList;
    }

    @Override
    public List<Simple> testData2() {
        return null;
    }
}
