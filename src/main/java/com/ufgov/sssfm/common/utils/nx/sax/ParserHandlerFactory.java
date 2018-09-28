package com.ufgov.sssfm.common.utils.nx.sax;

import com.ufgov.sssfm.common.utils.nx.SpringUtil;
import com.ufgov.sssfm.common.utils.nx.annotation.SAXHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author firmboy
 * @create 2018-06-29 上午9:05
 * 通过实现简单工厂，来判断
 **/
@Component
public class ParserHandlerFactory {


    public ParserHandlerFactory(){
        //init();
    }

    public void init(){
        System.out.println("方法执行");
        Map<String, Object> beans = SpringUtil.getBeans(Service.class);
        Set<Map.Entry<String, Object>> entries = beans.entrySet();
        for(Map.Entry<String, Object> entry:entries){
            if(entry.getValue().getClass().isAnnotationPresent(SAXHandler.class)){
                System.out.println(entry.getKey()+"="+entry.getValue());
            }
            System.out.println(entry.getValue());
        }
    }

    public DefaultSAXParserHandler getHandler(String handlerType){
        Map<String, Object> beans = SpringUtil.getBeans(Service.class);
        Set<Map.Entry<String, Object>> entries = beans.entrySet();
        for(Map.Entry<String, Object> entry:entries){
            if(entry.getValue().getClass().isAnnotationPresent(SAXHandler.class)){
                SAXHandler annotation = entry.getValue().getClass().getAnnotation(SAXHandler.class);
                String model = annotation.model();
                if(model.equals(handlerType)){
                    try {
                        return  (DefaultSAXParserHandler)entry.getValue().getClass().newInstance();
                    } catch (InstantiationException |IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
