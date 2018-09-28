package com.ufgov.sssfm.common.utils.nx;

import com.ufgov.sssfm.common.utils.nx.annotation.ChildernAnnotation;
import com.ufgov.sssfm.common.utils.nx.sax.DefaultSAXParserHandler;
import com.ufgov.sssfm.common.utils.nx.sax.ParserHandlerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author firmboy
 * @create 2018-06-27 上午9:25
 **/
public class NxXmlUtil {


    /**
     *
     * @param path
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String path, Class<T> tClass,String handlerType){
        Map<String,Object> map = null;
        try {
            map = parseXml(new File(path),handlerType);
        } catch (IllegalAccessException e) {
            //解析出错
            e.printStackTrace();
        }
        return parse(map,tClass);
    }

    /** @Author xucy
     * @Description xml 解析成bean
     * @Date 9:58 2018/8/10
     * @Param  content  传过来的xml内容  tClass 要生成的bean handlerType xml转换格式 strOrPath 判断是文件xml还是字符串中xml true代表字符串 false代表文件
     * @return
     **/
    public static <T> Map  xmlToBeanList(String content, Class<T> tClass,String handlerType,boolean strOrPath){
        Map mapResult=new HashMap();
    	ArrayList<Map<String, Object>> maps = null;
    	List<T> returnList=new ArrayList<T>();
        try {
            maps = parseXmlList(content,handlerType,strOrPath);
        } catch (Exception e) {
            System.out.println("解析OSS服务器上业务要素文件失败");
            //解析出错
            mapResult.put("errorMsg","解析OSS服务器上业务要素文件失败");
            return mapResult;
        }
        for(int i=0;i<maps.size();i++){
            Object obj=null;
            try{
                //解析报文赋值给实体Bean时报错
                obj=parse(maps.get(i),tClass);
            }catch (Exception e){
                System.out.println("解析报文赋值给实体Bean时报错");
                mapResult.put("errorMsg",e);
                return mapResult;
            }
        	returnList.add((T)obj);
        }
        mapResult.put("returnList",returnList);
        return mapResult;
    }
    
    /**
     *
     * @param map
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T parse(Map map,Class<T> tClass) {
        Constructor c = null;
        Object o = null;
        Constructor<?>[] constructors = tClass.getConstructors();
        for (Constructor constructor:constructors){
            Class[] parameterTypes = constructor.getParameterTypes();
            if(parameterTypes.length==0){
                //空的构造函数
                c = constructor;
            }
        }
        if(c == null){
            throw new IllegalStateException("类中没有空构造函数");
        }
        try {
            o = c.newInstance(new Object[]{});
        } catch (InstantiationException | IllegalAccessException |InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException("Constructor new实例的时候出错");
        }
        //将map中的属性赋值到对象中区
        Field[] subFields = tClass.getDeclaredFields();
        Class<? super T> superclass = tClass.getSuperclass();
        Field[] superFields = superclass.getDeclaredFields();


        Field[] fields = new Field[subFields.length+superFields.length];
        int i = 0;
        for (Field field:subFields){
            fields[i++] = field;
        }
        for (Field field:superFields){
            fields[i++] = field;
        }
        for (Field field:fields){
            Class type = field.getType();
            field.setAccessible(true);
            if (type.isAssignableFrom(List.class)){
                //说明是数组，需要重新调用
                Type genericType = field.getGenericType();
                if(genericType instanceof ParameterizedType){
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                    ArrayList results = new ArrayList();
                    if(genericClazz.isAnnotationPresent(ChildernAnnotation.class)){
                    	List<Map<String,Object>> list = (List<Map<String,Object>>)map.get("Children");
                        for (Map<String,Object> detail:list){
                            Object result = parse(detail, genericClazz);
                            results.add(result);
                        }
                        try {
                            field.set(o,results);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            throw new IllegalStateException("往实例bean中赋值的时候报错");
                        }
                    }else{
                    	List<Map<String,Object>> list = (List<Map<String,Object>>)map.get("Details");
                        for (Map<String,Object> detail:list){
                            Object result = parse(detail, genericClazz);
                            results.add(result);
                        }
                    }
                    try {
                        field.set(o,results);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("往实例bean中赋值的时候报错");
                    }
                }
            }else if(type.isAssignableFrom(Date.class)){
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    if(map.get(field.getName()).toString().length()>8&&(!map.get(field.getName()).toString().contains("\n"))){
                    	Date date = format.parse(map.get(field.getName()).toString().substring(0,8));
                        field.set(o,date);
                    }else{
                    	field.set(o,null);
                    }
                    
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("往实例bean中赋值的时候报错");
                }catch (ParseException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("往实例bean中赋值的时候报错");
                }
            }else if(type.isAssignableFrom(BigDecimal.class)){
                try {
                	
                   field.set(o,new BigDecimal(map.get(field.getName()).toString()));
                    
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("往实例bean中赋值的时候报错");
                }
            }else {
                try {
                    field.set(o,map.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("往实例bean中赋值的时候报错");
                }
            }
        }

        return (T)o;
    }



    public static Map<String,Object> parseXml(File file,String handlerType) throws IllegalAccessException {
        ArrayList<Map<String, Object>> maps = null;
        if(!file.exists()){
            throw new IllegalAccessException("文件访问不到");
        }
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            ParserHandlerFactory parserHandlerFactory = new ParserHandlerFactory();
            DefaultSAXParserHandler handler = parserHandlerFactory.getHandler(handlerType);
            parser.parse(file.getAbsolutePath(), handler);
            maps = handler.getMaps();
        } catch (ParserConfigurationException | SAXException  |IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return maps.get(0);
    }
    
    //返回包含多个map的List
    public static ArrayList<Map<String, Object>>  parseXmlList(String  content,String handlerType,Boolean strOrPath) throws Exception {
        ArrayList<Map<String, Object>> maps = null;
        if(strOrPath){
            SAXParserFactory factory = SAXParserFactory.newInstance();
            try {
                SAXParser parser = factory.newSAXParser();
                ParserHandlerFactory parserHandlerFactory = new ParserHandlerFactory();
                DefaultSAXParserHandler handler = parserHandlerFactory.getHandler(handlerType);
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(content.getBytes("GBK"));
                parser.parse(tInputStringStream, handler);
                maps = handler.getMaps();
            } catch (ParserConfigurationException | SAXException  |IOException e) {
                e.printStackTrace();
                throw e;
            }
        }else{

            File file=new File(content);
            if(!file.exists()){
                throw new IllegalAccessException("文件访问不到");
            }
            SAXParserFactory factory = SAXParserFactory.newInstance();
            try {
                SAXParser parser = factory.newSAXParser();
                ParserHandlerFactory parserHandlerFactory = new ParserHandlerFactory();
                DefaultSAXParserHandler handler = parserHandlerFactory.getHandler(handlerType);
                parser.parse(file.getAbsolutePath(), handler);
                maps = handler.getMaps();
            } catch (ParserConfigurationException | SAXException  |IOException e) {
                e.printStackTrace();
                throw e;
            }
        }

        return maps;
    }

}
