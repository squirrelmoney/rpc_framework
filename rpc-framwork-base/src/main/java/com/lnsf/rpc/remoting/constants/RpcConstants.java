package com.lnsf.rpc.remoting.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description: 网络传输模块共用的常量
 * @author: money
 * @time: 2021/4/5 7:08
 */
public class RpcConstants {
    /**
     * 神奇数字。验证RpcMessage
     */
    public static final byte[] MAGIC_NUMBER = {(byte) 'g', (byte) 'r', (byte) 'p', (byte) 'c'};
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    //版本信息
    public static final byte VERSION = 1;
    public static final byte TOTAL_LENGTH = 16;
    public static final byte REQUEST_TYPE = 1;
    public static final byte RESPONSE_TYPE = 2;
    //心跳请求类型
    public static final byte HEARTBEAT_REQUEST_TYPE = 3;
    //心跳返回类型
    public static final byte HEARTBEAT_RESPONSE_TYPE = 4;
    public static final int HEAD_LENGTH = 16;
    public static final String PING = "Heartbeat";
    public static final String PONG = "Heartbeating";
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;

}
