package com.lnsf.rpc.remoting.dto;


import lombok.*;

/**
 * @description:
 * @author: money
 * @time: 2021/3/14 7:08
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {

    //rpc消息类型
    private byte messageType;
    //序列化类型
    private byte codec;
    //压缩类型
    private byte compress;
    //请求id
    private int requestId;
    //请求数据
    private Object data;

}
