package com.lnsf.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address"),
    RPC_TRANSPORT("rpc.transport"),
    RPC_SERIALIZER("rpc.serializer"),
    RPC_LOADBALANCE("rpc.loadbalance"),
    RPC_COMPRESS("rpc.compress"),
    RPC_REGISTAER_CENTER("rpc.registerCenter");
    private final String propertyValue;

}
