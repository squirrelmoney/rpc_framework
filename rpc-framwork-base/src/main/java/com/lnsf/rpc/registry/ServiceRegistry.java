package com.lnsf.rpc.registry;

import com.lnsf.rpc.extension.SPI;

import java.net.InetSocketAddress;

/**
 * @program: rpc-framwork
 * @description: 服务注册
 * @author: money
 * @create: 2021-03-22 09:15
 **/
@SPI
public interface ServiceRegistry {
    /**
     * 服务注册
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
