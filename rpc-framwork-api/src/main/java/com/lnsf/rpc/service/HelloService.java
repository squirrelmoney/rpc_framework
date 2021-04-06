package com.lnsf.rpc.service;


import com.lnsf.rpc.service.V0.Simple;

import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:03:00
 */
public interface HelloService {
    String hello(Hello hello);

    String hello2(Hello hello);

    List<Simple> testData();

    List<Simple> testData2(List<Simple> test);
}
