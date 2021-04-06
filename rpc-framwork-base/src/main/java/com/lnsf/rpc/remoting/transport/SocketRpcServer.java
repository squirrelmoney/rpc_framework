package com.lnsf.rpc.remoting.transport;
import com.lnsf.rpc.provider.ServiceProvider;
import com.lnsf.rpc.utils.concurrent.threadpool.ThreadPoolFactoryUtils;

import java.util.concurrent.ExecutorService;

/**
 * @program: rpc-framwork
 * @description:
 * @author: money
 * @create: 2021-03-19 13:59
 **/

public class SocketRpcServer {
    public static final int PORT = 9998;
    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;


    public SocketRpcServer(ExecutorService threadPool, ServiceProvider serviceProvider) {
        threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
//        serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
        this.threadPool = threadPool;
        this.serviceProvider = serviceProvider;
    }
}
