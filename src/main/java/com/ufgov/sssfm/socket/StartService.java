package com.ufgov.sssfm.socket;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class StartService implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("++++++++++++++++++++++++写" + i + "个循环求关注++++++++++++++++++++++++");
        }
        //新建线程类
        SocketThread socketThread = new SocketThread(null);
        //启动线程
        socketThread.start();
    }


}
