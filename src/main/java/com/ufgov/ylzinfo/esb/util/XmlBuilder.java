//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.esb.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class XmlBuilder {
    private List<?> list;
    private Map<String, ?> map;
    private StringBuilder result = null;
    private List<String> includes = null;
    private List<String> excludes = null;

    public XmlBuilder() {
    }

    public XmlBuilder(List<?> list) {
        this.list = list;
    }

    public XmlBuilder(Map<String, ?> map) {
        this.map = map;
    }

    public XmlBuilder(List<?> list, String[] includes, String[] excludes) {
        this.list = list;
        this.includes = includes != null && includes.length != 0 ? Arrays.asList(includes) : null;
        this.excludes = excludes != null && excludes.length != 0 ? Arrays.asList(excludes) : null;
    }

    public List<?> getList() {
        return this.list;
    }

    public XmlBuilder setList(List<?> list) {
        this.list = list;
        return this;
    }

    public XmlBuilder setIncludes(String... incFieldName) {
        this.includes = incFieldName != null && incFieldName.length != 0 ? Arrays.asList(incFieldName) : null;
        return this;
    }

    public XmlBuilder setExcludes(String... excFieldName) {
        this.excludes = excFieldName != null && excFieldName.length != 0 ? Arrays.asList(excFieldName) : null;
        return this;
    }

    public Field[] getFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return fields;
    }

    public StringBuilder xmlBuild4List() throws Exception {
        this.checkValidityList();
        this.result = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        this.result.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        this.xmlSubBuild(this.list);
        this.result.append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode("查询成功")).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return this.result;
    }

    public StringBuilder xmlBuild4Map() throws Exception {
        this.checkValidityMap();
        this.result = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        this.result.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        this.xmlSubBuild(this.map);
        this.result.append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode("查询成功")).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return this.result;
    }

    private void xmlSubBuild(List<?> list) {
        if (list != null && list.size() >= 1) {
            Class<?> clazz = list.get(0).getClass();
            Object curObj = null;
            int listLength = list.size();
            String simpleName = clazz.getSimpleName();
            this.result.append("<resultset name=\"" + simpleName.toLowerCase() + "\">");

            for(int i = 0; i < listLength; ++i) {
                curObj = list.get(i);
                this.xmlSubSubBuild(curObj, list);
            }

            this.result.append("</resultset>");
        }
    }

    private void xmlSubBuild(Map<String, ?> dataMap) {
        Set keyset = dataMap.keySet();
        Iterator var4 = keyset.iterator();

        while(var4.hasNext()) {
            Object obj = var4.next();
            String key = (String)obj;
            Object dataObj = dataMap.get(key);
            if (dataObj instanceof List) {
                List list = (List)dataObj;
                this.list = list;
                this.xmlSubBuild(list);
            } else {
                this.result.append("<" + key + ">").append(dataObj.toString()).append("</" + key + ">");
            }
        }

    }

    private void xmlSubSubBuild(Object curObj, List<?> list) {
        String fieldName = "";
        String methodName = "";
        Method method = null;
        Object value = "";
        Class<?> clazz = curObj.getClass();
        Field[] fields = this.getFields(clazz);
        int fieldsLength = fields.length;
        String simpleName = clazz.getSimpleName();
        this.result.append("<row>");

        for(int j = 0; j < fieldsLength; ++j) {
            fieldName = fields[j].getName();
            if (list != this.list || (this.includes == null || this.excludes != null || this.includes.contains(fieldName)) && (this.includes != null || this.excludes == null || !this.excludes.contains(fieldName)) && (this.includes == null || this.excludes == null || this.includes.contains(fieldName) || !this.excludes.contains(fieldName))) {
                methodName = this.getGetterMethodNameByFieldName(fields[j]);

                try {
                    method = clazz.getDeclaredMethod(methodName);
                    method.setAccessible(true);
                    value = method.invoke(curObj);
                    if (fields[j].getType() == List.class) {
                        List<?> subList = (List)value;
                        this.xmlSubBuild(subList);
                    } else if (fields[j].getType() != Set.class) {
                        if (fields[j].getType().getClassLoader() != null) {
                            this.xmlSubSubBuild(value, (List)null);
                        } else {
                            if (value == null) {
                                value = "";
                            } else {
                                value = value.toString();
                            }

                            this.result.append("<" + fieldName + ">").append(value).append("</" + fieldName + ">");
                        }
                    } else {
                        Set<?> subSet = (Set)value;
                        Iterator<?> iter = subSet.iterator();
                        ArrayList subList = new ArrayList();

                        while(iter.hasNext()) {
                            subList.add(iter.next());
                        }

                        this.xmlSubBuild((List)subList);
                    }
                } catch (Exception var15) {
                    var15.printStackTrace();
                }
            }
        }

        this.result.append("</row>");
    }

    private String getGetterMethodNameByFieldName(Field field) {
        String methodName = null;
        String fieldName = field.getName();
        if (field.getType() != Boolean.TYPE && field.getType() != Boolean.class) {
            methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        } else if (fieldName.matches("^is[A-Z].*")) {
            methodName = fieldName;
        } else {
            methodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }

        return methodName;
    }

    private void checkValidityList() throws Exception {
        if (this.list == null) {
            throw new Exception("请保证传入的List不为null");
        } else {
            int size = this.list.size();
            if (this.list.size() == 0) {
                throw new Exception("请保证传入的List长度不为0");
            } else {
                for(int i = 1; i < size; ++i) {
                    if (this.list.get(0).getClass() != this.list.get(i).getClass()) {
                        throw new Exception("请保证传入的List每项都是同一个类型");
                    }
                }

            }
        }
    }

    private void checkValidityMap() throws Exception {
        if (this.map == null) {
            throw new Exception("请保证传入的Map不为null");
        } else if (this.map.size() == 0) {
            throw new Exception("请保证传入的Map长度不为0");
        }
    }

    private static String encode(String text) {
        StringBuffer n = new StringBuffer();

        for(int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            switch(c) {
            case '"':
                n.append("&quot;");
                break;
            case '&':
                n.append("&amp;");
                break;
            case '\'':
                n.append("&apos;");
                break;
            case '<':
                n.append("&lt;");
                break;
            case '>':
                n.append("&gt;");
                break;
            default:
                n.append(c);
            }
        }

        return new String(n);
    }
}
