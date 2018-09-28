package com.ufgov.sssfm.common.utils.nx;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author firmboy
 * @create 2018-06-26 下午3:21
 **/
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    public static Map<String,Object> getBeans(Class clazz){
        return context.getBeansWithAnnotation(Service.class);
    }


}
