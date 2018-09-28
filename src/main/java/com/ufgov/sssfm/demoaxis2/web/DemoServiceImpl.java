package com.ufgov.sssfm.demoaxis2.web;

import org.springframework.stereotype.Component;

/**
 * @author firmboy
 * @create 2018-06-06 下午7:36
 **/
@Component("springWebService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String demo(String str) {
        return "hello world";
    }
}
