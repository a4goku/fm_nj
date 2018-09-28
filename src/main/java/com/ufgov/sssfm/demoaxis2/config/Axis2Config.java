package com.ufgov.sssfm.demoaxis2.config;

import org.apache.axis2.extensions.spring.receivers.ApplicationContextHolder;
import org.apache.axis2.transport.http.AxisServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author firmboy
 * @create 2018-06-06 下午7:25
 **/
@Configuration
public class Axis2Config {

    @Bean
    public ServletRegistrationBean axisServlet(SimpleServletConfig config){
        AxisServlet axisServlet = new AxisServlet();
        try {
            axisServlet.init(config);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new ServletRegistrationBean(axisServlet, "/services/*");
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder(){
        return new ApplicationContextHolder();
    }

    @Bean
    public SimpleServletConfig simpleServletConfig(ServletContext servletContext){
        String path = "";
        try {
            path = ResourceUtils.getURL("classpath:").getPath();
            if(path.startsWith("file")){
                //这是用jar文件或者war文件直接启动的，要从当前jar包的根目录获取配置文件
                int index = path.indexOf("!");
                path = path.substring(0,index);
                index = path.lastIndexOf("/");
                path = path.substring(5,index);
            }else{
                //这个就是普通部署了，
            }
            File file = new File(path + "/services");
            if(file.exists()){
                System.out.println("");
            }
            System.out.println("path="+path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SimpleServletConfig servletConfig = new SimpleServletConfig(servletContext);
        servletConfig.setInitParameter("axis2.repository.path",path);
        return servletConfig;
    }

}
