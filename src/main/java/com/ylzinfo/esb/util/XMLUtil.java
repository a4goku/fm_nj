//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.esb.util;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.util.EmptyIterator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class XMLUtil {
    public static final String para = "para";
    public static final String paralist = "paralist";
    //private static final Logger logger = Logger.getLogger(XMLUtil.class);

    public XMLUtil() {
    }

    public static List parseBatchData(OMElement element) {
        try {
            List allData = new ArrayList();
            Iterator iterator = element.getChildElements();

            while(iterator != null && iterator.hasNext()) {
                OMElement om = (OMElement)iterator.next();
                Iterator<?> rowData = om.getChildElements();
                OMAttribute attribute = null;
                OMElement data = null;
                HashMap<String, String> rowMap = new HashMap();
                if (om.getAllAttributes() instanceof EmptyIterator) {
                    List paraList = new ArrayList();
                    paraList.add("para");

                    while(rowData != null && rowData.hasNext()) {
                        data = (OMElement)rowData.next();
                        rowMap.put(data.getLocalName(), data.getText().trim());
                        paraList.add(rowMap);
                    }

                    allData.add(paraList);
                    new HashMap();
                } else {
                    Iterator it = om.getAllAttributes();
                    ArrayList paraListList = new ArrayList();

                    while(it.hasNext()) {
                        attribute = (OMAttribute)it.next();
                        paraListList.add(attribute.getAttributeValue());
                    }

                    while(rowData != null && rowData.hasNext()) {
                        data = (OMElement)rowData.next();
                        Iterator dataAtrIt = data.getAllAttributes();

                        while(dataAtrIt.hasNext()) {
                            attribute = (OMAttribute)dataAtrIt.next();
                            rowMap.put(attribute.getLocalName(), attribute.getAttributeValue());
                        }

                        if (rowMap.size() > 0) {
                            paraListList.add(rowMap);
                            rowMap = new HashMap();
                        }
                    }

                    allData.add(paraListList);
                }
            }

            return allData;
        } catch (Exception var11) {
            List lt = new ArrayList();
            lt.add(var11.getMessage());
            return lt;
        }
    }

    public static List parseData(OMElement element) {
        try {
            List<HashMap<Object, Object>> allData = new ArrayList();
            Iterator iterator = element.getChildElements();

            while(iterator != null && iterator.hasNext()) {
                OMElement om = (OMElement)iterator.next();
                Iterator<?> rowData = om.getChildElements();
                OMAttribute attribute = null;
                OMElement data = null;
                HashMap rowMap = new HashMap();

                while(rowData != null && rowData.hasNext()) {
                    data = (OMElement)rowData.next();
                    Iterator it = data.getAllAttributes();
                    if (it instanceof EmptyIterator) {
                        rowMap.put(data.getLocalName(), StringUtil.deal(data.getText().trim()));
                    } else {
                        while(it.hasNext()) {
                            attribute = (OMAttribute)it.next();
                            rowMap.put(attribute.getLocalName(), StringUtil.deal(attribute.getAttributeValue()));
                        }

                        if (rowMap.size() > 0) {
                            allData.add(rowMap);
                            rowMap = new HashMap();
                        }
                    }
                }

                if (rowMap.size() > 0) {
                    allData.add(rowMap);
                }
            }

            return allData;
        } catch (Exception var9) {
            List lt = new ArrayList();
            lt.add(var9.getMessage());
            return lt;
        }
    }

    public static List parseData(String xmlStr) {
        try {
            Document doc = DocumentHelper.parseText(xmlStr);
            Element root = doc.getRootElement();
            List<Element> rows = root.elements();
            List<Element> bodyData = ((Element)rows.get(1)).elements();
            List<HashMap<Object, Object>> allData = new ArrayList();
            Iterator var7 = bodyData.iterator();

            while(var7.hasNext()) {
                Element bodyChild = (Element)var7.next();
                List<Element> business = bodyChild.elements();
                Iterator var10 = business.iterator();

                while(var10.hasNext()) {
                    Element e = (Element)var10.next();
                    HashMap<Object, Object> rowMap = new HashMap();
                    Iterator retrieveRow = e.attributeIterator();

                    while(retrieveRow.hasNext()) {
                        Attribute roweValueElement = (Attribute)retrieveRow.next();
                        rowMap.put(roweValueElement.getName(), roweValueElement.getValue());
                    }

                    allData.add(rowMap);
                }
            }

            return allData;
        } catch (Exception var14) {
            List lt = new ArrayList();
            lt.add(var14.getMessage());
            return lt;
        }
    }

    public static Map parseList4ParaList(List datalist) {
        Map returnMap = new HashMap();
        Map<String, ArrayList> paralistMap = new HashMap();
        ArrayList<Map> paramList = new ArrayList();
        ArrayList<Map> paramArray = new ArrayList();
        Iterator it = datalist.iterator();

        while(true) {
            while(true) {
                ArrayList temp;
                do {
                    do {
                        if (!it.hasNext()) {
                            returnMap.put("para", paramArray);
                            returnMap.put("paralist", paralistMap);
                            return returnMap;
                        }

                        temp = (ArrayList)it.next();
                    } while(temp == null);
                } while(temp.size() < 1);

                String paratype = (String)temp.get(0);
                if ("para".equals(paratype)) {
                    Map map = (Map)temp.get(1);
                    paramArray.add(map);
                } else {
                    for(int i = 1; i < temp.size(); ++i) {
                        Map map = (Map)temp.get(i);
                        paramList.add(map);
                    }

                    paralistMap.put(paratype, paramList);
                }
            }
        }
    }

    public static Map<String, String> getPara4BatchData(List list) {
        Map<String, String> paraMap = new HashMap();
        Map<String, String> tempMap = null;
        Map map = parseList4ParaList(list);
        ArrayList arrayList = (ArrayList)map.get("para");
        if (arrayList != null && arrayList.size() > 0) {
            Iterator var6 = arrayList.iterator();

            while(true) {
                do {
                    do {
                        if (!var6.hasNext()) {
                            return paraMap;
                        }

                        Object obj = var6.next();
                        tempMap = (Map)obj;
                    } while(tempMap == null);
                } while(tempMap.size() <= 0);

                Set set = tempMap.keySet();
                Iterator it = set.iterator();

                while(it.hasNext()) {
                    String key = (String)it.next();
                    paraMap.put(key, (String)tempMap.get(key));
                }
            }
        } else {
            return paraMap;
        }
    }

    public static Map<String, ArrayList> getParalist4BatchData(List list) {
        Map map = parseList4ParaList(list);
        Map<String, ArrayList> paralistMap = (Map)map.get("paralist");
        return paralistMap;
    }

    public static String formatBeforeTrans(String data) {
        if (data == null | "".equals(data)) {
            return "";
        } else {
            data = data.replaceAll("\\&", "&amp;");
            data = data.replaceAll("\\<", "&lt;");
            data = data.replaceAll("\\>", "&gt;");
            data = data.replaceAll("\\\"", "&quot;");
            data = data.replaceAll("\\'", "&apos;");
            data = data.replaceAll("\\\n", "STEAF_NEWLINE");
            return data;
        }
    }

    public static String formatAfterTrans(String data) {
        if (data == null | "".equals(data)) {
            return "";
        } else {
            data = data.replaceAll("&amp;", "&");
            data = data.replaceAll("&lt;", "<");
            data = data.replaceAll("&gt;", ">");
            data = data.replaceAll("&quot;", "\"");
            data = data.replaceAll("&apos;", "'");
            data = data.replaceAll("STEAF_NEWLINE", "\n");
            return data;
        }
    }

    public static String formatBeforeTransfor(String data) {
        if (data == null | "".equals(data)) {
            return "";
        } else {
            data = data.replaceAll("\\&", "___am_p;");
            data = data.replaceAll("\\<", "___l_t;");
            data = data.replaceAll("\\>", "___g_t;");
            data = data.replaceAll("\\\"", "___quo_t;");
            data = data.replaceAll("\\'", "___apo_s;");
            data = data.replaceAll("\\\n", "STEAF_NEWLIN_E");
            return data;
        }
    }

    public static String formatAfterTransfor(String data) {
        if (data == null | "".equals(data)) {
            return "";
        } else {
            data = data.replaceAll("___am_p;", "&");
            data = data.replaceAll("___l_t;", "<");
            data = data.replaceAll("___g_t;", ">");
            data = data.replaceAll("___quo_t;", "\"");
            data = data.replaceAll("___apo_s;", "'");
            data = data.replaceAll("STEAF_NEWLIN_E", "\n");
            return data;
        }
    }

    public static boolean isSuccess(String soapXml) {
        int outBusinessBegin = soapXml.indexOf("<faultcode>");
        int outBusinessEnd = soapXml.lastIndexOf("</faultcode>");
        String resCode = "";
        if (outBusinessBegin < outBusinessEnd) {
            resCode = soapXml.substring(outBusinessBegin + "<faultcode>".length(), outBusinessEnd);
        }

        return "000.000.000".equals(resCode.trim());
    }
}
