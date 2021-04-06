package com.lnsf.rpc.remoting.transport.socket;

import com.lnsf.rpc.config.CustomShutdownHook;
import com.lnsf.rpc.entity.RpcServiceProperties;
import com.lnsf.rpc.factory.SingletonFactory;
import com.lnsf.rpc.provider.ServiceProvider;
import com.lnsf.rpc.provider.ServiceProviderImpl;
import com.lnsf.rpc.utils.concurrent.threadpool.ThreadPoolFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @program: rpc-framwork
 * @description:
 * @author: money
 * @create: 2021-03-19 13:59
 **/
@Slf4j
@Component
public class SocketRpcServer {
    public static final int PORT = 10000;
    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;


    public SocketRpcServer() {
       //创建特定业务线的线程池
        this.threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        //创建单一对象，避免频繁的创建、摧毁
        this.serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }

    /**
     * @param service
     */
    public void registerService(Object service) {
        serviceProvider.publishService(service);
    }

    /**
     * 服务注册，把服务注册到zookeeper
     * @param service
     * @param rpcServiceProperties
     */
    public void registerService(Object service, RpcServiceProperties rpcServiceProperties) {
        serviceProvider.publishService(service, rpcServiceProperties);
    }

    /**
     * BIO模式，同步阻塞IO，socket一旦监听到请求，线程会被阻塞，直到返回结果
     * 开启通信->监听请求->处理请求
     */
    public void start() {
        try (ServerSocket server = new ServerSocket()) {
            //ip
            String host = InetAddress.getLocalHost().getHostAddress();
            //绑定 IP：port
            server.bind(new InetSocketAddress(host, PORT));
            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }
}
