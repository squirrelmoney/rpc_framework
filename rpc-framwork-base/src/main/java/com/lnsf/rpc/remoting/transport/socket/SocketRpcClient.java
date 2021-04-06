package com.lnsf.rpc.remoting.transport.socket;

import com.lnsf.rpc.entity.RpcServiceProperties;
import com.lnsf.rpc.enums.RpcConfigEnum;
import com.lnsf.rpc.exception.RpcException;
import com.lnsf.rpc.extension.ExtensionLoader;
import com.lnsf.rpc.registry.ServiceDiscovery;
import com.lnsf.rpc.remoting.dto.RpcRequest;
import com.lnsf.rpc.remoting.transport.RpcRequestTransport;
import com.lnsf.rpc.utils.PropertiesFileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @program: rpc-framwork
 * @description: 基于 Socket 传输 RpcRequest
 * @author: money
 * @create: 2021-03-25 11:33
 **/
@AllArgsConstructor
@Slf4j
public class SocketRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).
                getExtension(PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue()).
                        getProperty(RpcConfigEnum.RPC_REGISTAER_CENTER.getPropertyValue()));
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) throws Exception {
        // 通过rpcRequest构建rpc服务名称
        String rpcServiceName = RpcServiceProperties.builder().serviceName(rpcRequest.getInterfaceName())
                .group(rpcRequest.getGroup()).version(rpcRequest.getVersion()).build().toRpcServiceName();
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcServiceName);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress,1500);
            
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 通过输出流将数据发送到服务器
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //从输入流读取RpcResponse
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}
