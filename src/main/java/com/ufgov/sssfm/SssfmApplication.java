package com.ufgov.sssfm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SssfmApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SssfmApplication.class, args);
        SpringApplication.run(SssfmApplication.class, args);
        //新建线程类
        //SocketThread socketThread = new SocketThread(null);
        //启动线程
        //socketThread.start();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SssfmApplication.class);
    }
}
