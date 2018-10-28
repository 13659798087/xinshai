package com.xinshai.xinshai.servlet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化servlet
 *
 * @author liuyq
 * @date 2013-05-02
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        // 启动定时获取access_token的线程
        new Thread(new TokenThread()).start();
    }


}