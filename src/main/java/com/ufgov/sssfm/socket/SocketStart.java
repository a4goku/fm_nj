package com.ufgov.sssfm.socket;

import org.springframework.context.ApplicationEvent;


public class SocketStart extends ApplicationEvent {
    public SocketStart(Object object){
        super(object);
    }

    public void print(){
        System.out.println("++++++++启动一个监听++++++++++++");
    }
}
