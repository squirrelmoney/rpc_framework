package com.lnsf.rpc.compress;
import com.lnsf.rpc.extension.SPI;


/**
 * @program: rpc-framwork
 * @description: 当服务器关闭时，执行相关服务关闭操作，例如注销所有服务
 * @author: money
 * @create: 2021-03-25 10:08
 **/
@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
