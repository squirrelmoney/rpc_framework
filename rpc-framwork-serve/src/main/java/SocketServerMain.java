import com.lnsf.rpc.annotation.RpcScan;
import com.lnsf.rpc.remoting.transport.socket.SocketRpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: rpc-framwork
 * @description: 服务提供 启动类
 * @author: money
 * @create: 2021-03-25 10:33
 **/
@RpcScan(basePackage = {"com.lnsf.rpc"})
public class SocketServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SocketServerMain.class);
        SocketRpcServer socketServer = (SocketRpcServer) applicationContext.getBean("socketRpcServer");
//        HelloService helloService = new HelloServiceImpl();
//        SocketRpcServer socketRpcServer = new SocketRpcServer();
//        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
//                .group("rpcServer").version("version1").build();
//        socketRpcServer.registerService(helloService, rpcServiceProperties);
        socketServer.start();
    }
}
