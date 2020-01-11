package io.jopen.apply.plugin.demo.init;

import io.jopen.springboot.plugin.init.Init;

/**
 * @author maxuefeng
 * @since 2020/1/9
 */
@Init(initialization = Init.InitMode.STATIC_METHOD,value = "init")
public class InitDemo {

    public static void init() {
        System.err.println("初始化工作");
    }
}
