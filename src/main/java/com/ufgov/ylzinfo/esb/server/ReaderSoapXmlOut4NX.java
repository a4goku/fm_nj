//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.esb.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReaderSoapXmlOut4NX {
    private static Log log = LogFactory.getLog(ReaderSoapXmlOut4NX.class);
    private static final String responeOKStr = "成功";

    public ReaderSoapXmlOut4NX() {
    }

    public static String buildSoapXMl4Error(int errorCode, String errorMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        reXML.append("<soap:Fault>").append("<faultcode>").append(errorCode).append(".000.000").append("</faultcode>").append("<faultstring>").append(encode(errorMsg)).append("</faultstring>").append("</soap:Fault>");
        reXML.append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    /** @deprecated */
    public static String buildSoapXMl4Info(String infoMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">").append("<resultset name=\"retrieve\" information=\"").append(infoMsg).append("\" >").append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    /** @deprecated */
    public static String buildSoapXMl4Success(String infoMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(infoMsg).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String buildSoapXMl4Single(Object object) throws Exception {
        return buildSoapXMl4Single(object, "成功");
    }

    public static String buildSoapXMl4Single(Object object, String msg) throws Exception {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        buildResultInfo(object, reXML);
        reXML.append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode(msg)).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String buildSoapXMl4Single(Map<String, String> map) throws Exception {
        return buildSoapXMl4Single(map, "成功");
    }

    public static String buildSoapXMl4Single(Map<String, String> map, String msg) throws Exception {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        buildResultInfo(map, reXML);
        reXML.append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode(msg)).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String buildSoapXMl4List(Object object, List list) throws Exception {
        return buildSoapXMl4List(object, list, "成功");
    }

    public static String buildSoapXMl4List(Object object, List list, String msg) throws Exception {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        buildResultInfo(object, reXML);
        reXML.append("<resultset name=\"retrieve\">");
        buildRowInfo(list, reXML);
        reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode(msg)).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String buildSoapXMl4List(Map<String, String> map, List list) throws Exception {
        return buildSoapXMl4List(map, list, "成功");
    }

    public static String buildSoapXMl4List(Map<String, String> map, List list, String msg) throws Exception {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        buildResultInfo(map, reXML);
        reXML.append("<resultset name=\"retrieve\">");
        buildRowInfo(list, reXML);
        reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode(msg)).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String buildSoapXMl4Map(Object object, Map<String, List> map) throws Exception {
        return buildSoapXMl4Map(object, map, "成功");
    }

    public static String buildSoapXMl4Map(Object object, Map<String, List> map, String msg) throws Exception {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        buildResultInfo(object, reXML);
        reXML.append("<resultset name=\"retrieve\">");
        Set<String> sets = map.keySet();
        Iterator var6 = sets.iterator();

        while(var6.hasNext()) {
            String key = (String)var6.next();
            List list = (List)map.get(key);
            reXML.append("<resultset name=\"").append(key).append("\">");
            buildRowInfo(list, reXML);
            reXML.append("</resultset>");
        }

        reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode(msg)).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String buildSoapXMl4Map(Map<String, String> reusltMap, Map<String, List> resultSetMap) throws Exception {
        return buildSoapXMl4Map(reusltMap, resultSetMap, "成功");
    }

    public static String buildSoapXMl4Map(Map<String, String> reusltMap, Map<String, List> resultSetMap, String msg) throws Exception {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        buildResultInfo(reusltMap, reXML);
        Set<String> sets = resultSetMap.keySet();
        Iterator var6 = sets.iterator();

        while(var6.hasNext()) {
            String key = (String)var6.next();
            List list = (List)resultSetMap.get(key);
            reXML.append("<resultset name=\"").append(key).append("\">");
            buildRowInfo(list, reXML);
            reXML.append("</resultset>");
        }

        reXML.append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append(encode(msg)).append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    private static void buildRowInfo(List list, StringBuffer reXML) throws Exception {
        if (list != null) {
            Iterator localIterator = list.iterator();

            while(true) {
                Object obj;
                do {
                    if (!localIterator.hasNext()) {
                        return;
                    }

                    obj = localIterator.next();
                } while(obj == null);

                reXML.append("<row ");
                Field[] fieldlist = obj.getClass().getDeclaredFields();
                Field[] var8 = fieldlist;
                int var7 = fieldlist.length;

                for(int var6 = 0; var6 < var7; ++var6) {
                    Field field = var8[var6];
                    String sf = field.getName();
                    field.setAccessible(true);
                    Object value = field.get(obj) == null ? "" : field.get(obj);
                    value = encode(value.toString());
                    reXML.append(sf).append("=\"").append(value).append("\" ");
                }

                reXML.append("/>");
            }
        }
    }

    private static void buildResultInfo(Object obj, StringBuffer reXML) throws Exception {
        if (obj != null) {
            Field[] fieldlist = obj.getClass().getDeclaredFields();
            Field[] var6 = fieldlist;
            int var5 = fieldlist.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field field = var6[var4];
                String sf = field.getName();
                field.setAccessible(true);
                Object value = field.get(obj) == null ? "" : field.get(obj);
                value = encode(value.toString());
                reXML.append("<result ").append(sf).append("=\"").append(value).append("\"/>");
            }
        }

    }

    private static void buildResultInfo(Map<String, String> map, StringBuffer reXML) {
        if (map != null) {
            Set<String> keys = map.keySet();
            Iterator var4 = keys.iterator();

            while(var4.hasNext()) {
                String name = (String)var4.next();
                String value = (String)map.get(name);
                value = encode(value.toString());
                reXML.append("<result ").append(name).append("=\"").append(value).append("\"/>");
            }
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
