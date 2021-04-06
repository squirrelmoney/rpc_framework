package com.lnsf.rpc.registry.zookeeper;

import com.lnsf.rpc.enums.RpcConfigEnum;
import com.lnsf.rpc.enums.RpcErrorMessageEnum;
import com.lnsf.rpc.exception.RpcException;
import com.lnsf.rpc.extension.ExtensionLoader;
import com.lnsf.rpc.loadbalance.LoadBalance;
import com.lnsf.rpc.registry.ServiceDiscovery;
import com.lnsf.rpc.registry.zookeeper.util.CuratorUtils;
import com.lnsf.rpc.utils.PropertiesFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @program: rpc-framwork
 * @description: 服务发现
 * @author: money
 * @create: 2021-03-25 11:37
 **/
@Slf4j
public class ZkServiceDiscovery implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscovery() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).
        getExtension(PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue()).
                getProperty(RpcConfigEnum.RPC_LOADBALANCE.getPropertyValue()));
    }

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) throws Exception {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList == null || serviceUrlList.size() == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcServiceName);
        log.info("成功找到服务地址:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
