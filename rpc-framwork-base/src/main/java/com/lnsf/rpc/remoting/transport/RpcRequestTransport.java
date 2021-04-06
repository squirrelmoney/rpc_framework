package com.lnsf.rpc.remoting.transport;

import com.lnsf.rpc.extension.SPI;
import com.lnsf.rpc.remoting.dto.RpcRequest;

/**
 * @program: rpc-framwork
 * @description: 发送请求
 * @author: money
 * @create: 2021-03-25 11:31
 **/
@SPI
public interface RpcRequestTransport {
    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest) throws Exception;
}
