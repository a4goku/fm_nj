package com.ufgov.sssfm.demoaxis2.web.impl;

import com.ufgov.sssfm.demoaxis2.web.AxisHelloWorld;
import org.apache.axiom.om.OMElement;
import org.springframework.stereotype.Service;

/**
 * @author firmboy
 * @create 2018-06-06 下午11:36
 **/
@Service("helloWorldService")
public class AxisHelloWorldImpl implements AxisHelloWorld {

    @Override
    public String getMessage(OMElement message) {
        return "-----------Axis Server-----------"+message;
    }
}
