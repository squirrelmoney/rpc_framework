package com.lnsf.rpc.utils;

/**
 * @description:
 * @author: money
 * @time: 2021/4/5 7:10
 */
public class RuntimeUtil {
    /**
     * 获取CPU的核心数
     * @return cpu的核心数
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}
