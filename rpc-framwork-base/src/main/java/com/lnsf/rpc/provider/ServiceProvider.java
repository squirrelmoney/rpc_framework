package com.lnsf.rpc.provider;

import com.lnsf.rpc.entity.RpcServiceProperties;

/**
 * @program: rpc-framwork
 * @description: 存储和提供服务对象
 * @author: money
 * @create: 2021-03-19 14:11
 **/
public interface ServiceProvider {

    /**
     * @DeSC 添加服务
     * @param service              服务对象
     * @param serviceClass         由服务实例对象实现的接口类
     * @param rpcServiceProperties 服务信息
     */
    void addService(Object service, Class<?> serviceClass, RpcServiceProperties rpcServiceProperties);

    /**
     * @DESC 获取服务
     * @param rpcServiceProperties 服务信息
     * @return service object
     */
    Object getService(RpcServiceProperties rpcServiceProperties);

    /**
     * @DESC 发布服务
     * @param service              服务对象
     * @param rpcServiceProperties 服务信息
     */
    void publishService(Object service, RpcServiceProperties rpcServiceProperties);

    /**
     * @param service 服务对象
     */
    void publishService(Object service);
}
