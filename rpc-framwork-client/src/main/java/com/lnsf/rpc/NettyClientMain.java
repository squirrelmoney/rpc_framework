package com.lnsf.rpc;

import com.esotericsoftware.kryo.io.Output;
import com.lnsf.rpc.annotation.RpcScan;
import com.lnsf.rpc.service.V0.Simple;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
@RpcScan(basePackage = {"com.lnsf.rpc"})
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException, IOException {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        int threadNum = 10;
        FileOutputStream fo = new FileOutputStream("E:/temp.txt");
        Output output = new Output(fo);
        ObjectOutputStream so = new ObjectOutputStream(output);
        ExecutorService pool = Executors. newCachedThreadPool();
        List<Long> temp = new ArrayList<>();
        List<Simple> SimpleList =new ArrayList<>();
        Simple simple = new Simple();
        for (int i = 0; i < 100000; i++) {
            Map<String, Integer> map = new HashMap<String, Integer>(2);
            map.put("zhang0", i);
            map.put("zhang1", i);
            simple.setName("zhang" + i);
            simple.setAge(i+1);
            simple.setMap(map);
            SimpleList.add(simple);
        }
            for (int i = 0; i < threadNum; i++) {
                MultiThread thread = new MultiThread();
                thread.setHelloController(helloController);
                thread.setTemp(temp);
                thread.setSimpleList(SimpleList);
                thread.setTime(i);
                pool.execute(thread);
                System.out.println(thread.getName()+" "+i);
            }
        so.writeObject(temp.toString());
        so.flush();
        so.close();
    }
}


class MultiThread extends Thread {
    private HelloController helloController;

    private List<Long> temp;

    private  List<Simple> SimpleList;

    private int time ;

    public void setSimpleList(List<Simple> simpleList) {
        SimpleList = simpleList;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void setTemp(List<Long> temp) {
        this.temp = temp;
    }


    volatile boolean bol = false;
    @Override
    public void run() {

            try {
                if (!bol ) {
                    System.out.println("发起请求");
                    this.helloController.testTime2(temp,SimpleList);
                    System.out.println("数据返回");
                    this.temp.add(1l);
                    bol = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}