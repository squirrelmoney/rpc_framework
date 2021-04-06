package com.lnsf.rpc.registry;

import com.lnsf.rpc.extension.SPI;

import java.net.InetSocketAddress;

/**
 * @program: rpc-framwork
 * @description: 服务发现
 * @author: money
 * @create: 2021-03-25 11:36
 **/
@SPI
public interface ServiceDiscovery {
    /**
     * 根据名称查找服务
     *
     * @param rpcServiceName rpc service name
     * @return service address
     */
    InetSocketAddress lookupService(String rpcServiceName) throws Exception;
}
