//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.analysis.domain;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import java.util.HashMap;

@XStreamConverter(RowConverter.class)
public class Row extends HashMap<String, String> {
    private static final long serialVersionUID = 5619951409573339302L;

    public Row() {
    }
}
