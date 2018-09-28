package com.ufgov.sssfm.demoaxis2.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author firmboy
 * @create 2018-06-07 下午5:11
 **/
public class SimpleServletConfig implements ServletConfig {

    private final ConcurrentHashMap<String,String> params = new ConcurrentHashMap<String,String>();

    private final ServletContext servletContext;

    public SimpleServletConfig(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public String getServletName() {
        return "axisServlet";
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String getInitParameter(String s) {
        return params.get(s);
    }

    public void setInitParameter(String key ,String value){
        params.put(key,value);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return new Enumeration() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public Object nextElement() {
                return null;
            }
        };
    }
}
