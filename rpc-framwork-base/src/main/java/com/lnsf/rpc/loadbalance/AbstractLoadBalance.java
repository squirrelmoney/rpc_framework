package com.lnsf.rpc.loadbalance;

import com.lnsf.rpc.registry.zookeeper.util.CuratorUtils;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @program: rpc-framwork
 * @description: 负载均衡策略的抽象类
 *
 * @author: money
 * @create: 2021-03-25 11:51
 **/
public abstract class AbstractLoadBalance implements LoadBalance {
    /**
     * 根据服务器数量选择负载均衡策略
     * select 方法的逻辑比较简单，首先会检测 地址 集合的合法性，
     * 然后再检测 serviceAddresses 集合元素数量。如果只包含一个 serviceAddresses，直接返回该 serviceAddresses 即可。
     * 如果包含多个 serviceAddresses，此时需要通过负载均衡算法选择一个 serviceAddresses。具体的负载均衡算法由子类实现，
     * @param serviceAddresses Service address list
     * @param rpcServiceName
     * @return
     */
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, String rpcServiceName) throws Exception {
        if (serviceAddresses == null || serviceAddresses.size() == 0) {
            return null;
        }
        // 如果 服务列表中仅有一个服务 ，直接返回即可，无需进行负载均衡
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses, rpcServiceName);
    }

    protected abstract String doSelect(List<String> serviceAddresses, String rpcServiceName) throws Exception;

    protected int getWeight(String serviceAddresses, String rpcServiceName) throws Exception {
        long weight = CuratorUtils.getWeight(rpcServiceName, serviceAddresses);
        if (weight > 0) {
            // 获取服务提供者启动时间戳
            long timestamp = ManagementFactory.getRuntimeMXBean().getStartTime();
            if (timestamp > 0L) {
                // 计算服务提供者运行时长
                int uptime = (int) (System.currentTimeMillis() - timestamp);
                // 获取服务预热时间，默认为10分钟
                int warmup = 10*60*1000;
                // 如果服务运行时间小于预热时间，则重新计算服务权重，即降权
                if (uptime > 0 && uptime < warmup) {
                    // 重新计算服务权重
                    weight = calculateWarmupWeight(uptime, warmup, (int) weight);
                }
            }
        }
        return (int)weight;
    }

    static int calculateWarmupWeight(int uptime, int warmup, int weight) {
        // 计算权重，下面代码逻辑上形似于 (uptime / warmup) * weight。
        // 随着服务运行时间 uptime 增大，权重计算值 ww 会慢慢接近配置值 weight
        int ww = (int) ((float) uptime / ((float) warmup / (float) weight));
        return ww < 1 ? 1 : (ww > weight ? weight : ww);
    }
}
