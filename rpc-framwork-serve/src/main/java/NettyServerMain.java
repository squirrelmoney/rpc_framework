import com.lnsf.rpc.annotation.RpcScan;
import com.lnsf.rpc.remoting.transport.netty.server.NettyRpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description: 服务器:通过@RpcService标注的自动注册服务
 * @author: money
 * @time: 2021/4/5 17:17
 */
@RpcScan(basePackage = {"com.lnsf.rpc"})
public class NettyServerMain {
    public static void main(String[] args) {
        // 通过注释注册服务
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");
//        //手动注册服务
//        HelloService helloService2 = new HelloServiceImpl2();
//        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
//                .group("test2").version("version2").build();
//        nettyRpcServer.registerService(helloService2, rpcServiceProperties);
        nettyRpcServer.start();
    }
}
