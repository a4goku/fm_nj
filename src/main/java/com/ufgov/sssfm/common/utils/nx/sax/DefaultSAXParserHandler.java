package com.ufgov.sssfm.common.utils.nx.sax;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author firmboy
 * @create 2018-06-29 下午2:39
 **/
public class DefaultSAXParserHandler extends DefaultHandler {

    String value = null;

    ArrayList<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
    public ArrayList<Map<String,Object>> getMaps() {
        return maps;
    }


    /**
     * 用来标识解析开始
     */
    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.startDocument();
        System.out.println("SAX解析开始");
    }

    /**
     * 用来标识解析结束
     */
    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.endDocument();
        System.out.println("SAX解析结束");
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        value = new String(ch, start, length);
    }
}
