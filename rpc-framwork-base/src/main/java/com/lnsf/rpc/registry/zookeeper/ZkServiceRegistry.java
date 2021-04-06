package com.lnsf.rpc.registry.zookeeper;
import com.lnsf.rpc.registry.ServiceRegistry;
import com.lnsf.rpc.registry.zookeeper.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

/**
 * @program: rpc-framwork
 * @description: 服务注册实现类
 * @author: money
 * @create: 2021-03-22 09:23
 **/
@Slf4j
public class ZkServiceRegistry implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        //获取注册服务名称
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        //获取zookeeper服务
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        //根据服务注册名称创建持久节点
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
