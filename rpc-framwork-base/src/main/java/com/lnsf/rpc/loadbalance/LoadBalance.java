package com.lnsf.rpc.loadbalance;

import com.lnsf.rpc.extension.SPI;

import java.util.List;

/**
 * @program: rpc-framwork
 * @description: 配置负载均衡策略接口
 * @author: money
 * @create: 2021-03-25 11:50
 **/
@SPI
public interface LoadBalance {
    /**
     * 从现有服务地址列表中选择一个
     *
     * @param serviceAddresses Service address list
     * @return target service address
     */
    String selectServiceAddress(List<String> serviceAddresses, String rpcServiceName) throws Exception;



}
