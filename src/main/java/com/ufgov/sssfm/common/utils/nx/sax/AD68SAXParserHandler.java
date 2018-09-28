package com.ufgov.sssfm.common.utils.nx.sax;

import com.ufgov.sssfm.common.utils.nx.annotation.SAXHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author firmboy
 * @create 2018-06-27 上午10:45
 **/
@SAXHandler(model = "ad68")
public class AD68SAXParserHandler extends DefaultSAXParserHandler {


    Map<String,Object> map = null;
    List<Map<String,Object>> details = null;
    Map<String,Object> detail = null;

    int mapIndex = 0;


    /**
     * 解析xml元素
     */
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //调用DefaultHandler类的startElement方法
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("Main")) {
            mapIndex++;
            map = new LinkedHashMap<String, Object>();
        }else if(qName.equals("Details")){
            details = new ArrayList<Map<String,Object>>();
        }else if(qName.equals("Detail")){
            detail = new LinkedHashMap<String, Object>();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //调用DefaultHandler类的endElement方法
        super.endElement(uri, localName, qName);
        //判断是否针对一本书已经遍历结束
        if (qName.equals("Main")) {
            maps.add(map);
            map = new LinkedHashMap<String, Object>();
            //map = null;
            //System.out.println("======================结束遍历某一本书的内容=================");
        }else if (qName.equals("Details")){
            map.put("Details",details);
            details = null;
        }else if (qName.equals("Detail")){
            details.add(detail);
            detail = null;
        }else{
            if(detail!=null) {
                detail.put(qName, value);
            }else {
                map.put(qName, value);
            }
        }
    }


}
