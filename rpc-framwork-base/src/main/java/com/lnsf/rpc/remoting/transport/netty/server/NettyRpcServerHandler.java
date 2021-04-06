package com.lnsf.rpc.remoting.transport.netty.server;

import com.lnsf.rpc.enums.CompressTypeEnum;
import com.lnsf.rpc.enums.RpcResponseCodeEnum;
import com.lnsf.rpc.enums.SerializationTypeEnum;
import com.lnsf.rpc.factory.SingletonFactory;
import com.lnsf.rpc.remoting.constants.RpcConstants;
import com.lnsf.rpc.remoting.dto.RpcMessage;
import com.lnsf.rpc.remoting.dto.RpcRequest;
import com.lnsf.rpc.remoting.dto.RpcResponse;
import com.lnsf.rpc.remoting.handler.RpcRequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**

 * @author shuang.kou
 * @createTime 2020年05月25日 20:44:00
 */
/**
 * 定制服务器的ChannelHandler以处理客户端发送的数据。
 * <p>
 * 如果继承自 SimpleChannelInboundHandler 的话就不要考虑 ByteBuf 的释放 ，
 * {@link SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf ，避免可能导致的内存泄露问题。详见《Netty进阶之路 跟着案例学 Netty》
 *
 * @author: money
 * @time: 2021/4/5 16:17
 */
@Slf4j
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcRequestHandler rpcRequestHandler;

    public NettyRpcServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof RpcMessage) {
                log.info("server receive msg: [{}] ", msg);
                byte messageType = ((RpcMessage) msg).getMessageType();
                RpcMessage rpcMessage = new RpcMessage();
                //序列化
                rpcMessage.setCodec(SerializationTypeEnum.PROTOSTUFF.getCode());
                //压缩数据
                rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                //心跳请求
                if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
                    //心跳返回
                    rpcMessage.setMessageType(RpcConstants.HEARTBEAT_RESPONSE_TYPE);
                    rpcMessage.setData(RpcConstants.PONG);
                } else {
                    RpcRequest rpcRequest = (RpcRequest) ((RpcMessage) msg).getData();
                    // 执行目标方法(客户端需要执行的方法)并返回方法结果
                    Object result = rpcRequestHandler.handle(rpcRequest);
                    log.info(String.format("服务器得到的结果: %s", result.toString()));
                    //返回执行状态码
                    rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                    //channel如是激活状态，且处于可写入状态
                    if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                        RpcResponse<Object> rpcResponse = RpcResponse.success(result, rpcRequest.getRequestId());
                        rpcMessage.setData(rpcResponse);
                    } else {
                        RpcResponse<Object> rpcResponse = RpcResponse.fail(RpcResponseCodeEnum.FAIL);
                        rpcMessage.setData(rpcResponse);
                        log.error("现在不可写入, 消息丢失");
                    }
                }
                ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally {
            //确保释放ByteBuf，否则可能存在内存泄漏
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("检查到空闲连接，关闭连接");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("服务器捕获异常");
        cause.printStackTrace();
        ctx.close();
    }
}
