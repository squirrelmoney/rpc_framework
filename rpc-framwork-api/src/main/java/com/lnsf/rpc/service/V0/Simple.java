package com.lnsf.rpc.service.V0;

import java.io.Serializable;
import java.util.Map;

/**
 * @program: testSerializable
 * @description:
 * @author: money
 * @create: 2021-03-17 10:14
 **/

public class Simple implements Serializable {
    private static final long serialVersionUID = -4914434736682797743L;
    private String name;
    private int age;
    private Map<String,Integer> map;
    public Simple(String s, int i){

    }
    public Simple(String name, int age, Map<String,Integer> map){
        this.name = name;
        this.age = age;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public Simple() {
    }

    @Override
    public String toString() {
        return "Simple{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", map=" + map +
                '}';
    }
}
