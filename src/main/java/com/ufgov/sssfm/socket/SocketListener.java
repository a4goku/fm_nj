package com.ufgov.sssfm.socket;

import org.springframework.context.ApplicationListener;

public class SocketListener implements ApplicationListener<SocketStart> {
    @Override
    public void onApplicationEvent(SocketStart event){
        //event.print();
    }
}
