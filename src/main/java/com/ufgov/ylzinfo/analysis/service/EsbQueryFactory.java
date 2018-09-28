//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.analysis.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.ylzinfo.analysis.domain.BusinessDomain;
import com.ylzinfo.analysis.domain.FaultDomain;
import com.ylzinfo.esb.bas.EsbException;
import com.ylzinfo.esb.util.StringUtil;
import com.ylzinfo.esb.util.XmlParser;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

public class EsbQueryFactory {
//    private static final Logger LOGGER = Logger.getLogger(EsbQueryFactory.class);
    public static final String CONNECTION_ERROR = "通讯失败!请联系技术支持!";

    public EsbQueryFactory() {
    }

    public <T extends EsbService> T getServiceResult(String soapXmlResult, Class<T> cls) throws EsbException {
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("-_", "_")));
        List<String> result = this.processResult(soapXmlResult);
        BusinessDomain businessDomain = new BusinessDomain();
        if ("000.000.000".equals(result.get(0))) {
            String xmlData = extractBusinessData(soapXmlResult);
            if (StringUtil.hasText(xmlData)) {
                xstream.processAnnotations(BusinessDomain.class);
                businessDomain = (BusinessDomain)xstream.fromXML(xmlData);
            }

            businessDomain.setIsOK("yes");
        } else {
            businessDomain.setIsOK("no");
        }

        businessDomain.setFaultCode((String)result.get(0));
        businessDomain.setFaultString((String)result.get(1));
        return this.constructServiceAndReturn(cls, businessDomain);
    }

    public QueryComplexListService getServiceResult4Complex(String complexXml) throws EsbException {
        QueryComplexListService service = new QueryComplexListService();
        Document doc = XmlParser.getDocument4XmlStr(complexXml);
        Element rootElement = doc.getRootElement();
        Element bodyElement = null;
        List<Element> elementlist = XmlParser.getChildList(rootElement);
        Iterator var8 = elementlist.iterator();

        while(var8.hasNext()) {
            Element e = (Element)var8.next();
            if ("body".equalsIgnoreCase(e.getName())) {
                bodyElement = e;
            }
        }

        List<Element> bodylist = XmlParser.getChildList(bodyElement);
        Iterator var9 = bodylist.iterator();

        while(true) {
            while(var9.hasNext()) {
                Element e = (Element)var9.next();
                if ("business".equalsIgnoreCase(e.getName())) {
                    Map<String, String> results = new HashMap();
                    Map<String, List> mdatasMap = new HashMap();
                    List<Element> businesslist = XmlParser.getChildList(e);
                    Iterator var14 = businesslist.iterator();

                    while(var14.hasNext()) {
                        Element element = (Element)var14.next();
                        String name = element.getName();
                        if ("cpage".equalsIgnoreCase(name)) {
                            service.currentPage = element.getText();
                        } else if ("pages".equalsIgnoreCase(name)) {
                            service.totalPages = element.getText();
                        } else if ("rowCount".equalsIgnoreCase(name)) {
                            service.totalCounts = element.getText();
                        } else if ("resultset".equalsIgnoreCase(name)) {
                            String resultsetName = XmlParser.attrValue(element, "name");
                            mdatasMap.put(resultsetName, constructBusinessAndReturn(element));
                        } else {
                            results.put(name, element.getText());
                        }
                    }

                    service.resultMap = results;
                    service.setMdatasMap(mdatasMap);
                } else if ("fault".equalsIgnoreCase(e.getName())) {
                    List<Element> faultlist = XmlParser.getChildList(e);
                    Iterator var12 = faultlist.iterator();

                    while(var12.hasNext()) {
                        Element element = (Element)var12.next();
                        String name = element.getName();
                        if ("faultcode".equalsIgnoreCase(name)) {
                            service.faultCode = element.getText();
                        } else if ("faultstring".equalsIgnoreCase(name)) {
                            service.faultString = element.getText();
                        }
                    }

                    if ("000.000.000".equals(service.faultCode)) {
                        service.isOK = true;
                    } else {
                        service.isOK = false;
                    }
                }
            }

            return service;
        }
    }

    private <T extends EsbService> T getServiceWithRequest(String soapXmlResult, Class<T> cls) throws EsbException {
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("-_", "_")));
        List<String> result = this.processExceptions(xstream, soapXmlResult);
        BusinessDomain businessDomain = null;
        FaultDomain faultDomain = null;
        if ("true".equals(result.get(0))) {
            if (StringUtil.hasText((String)result.get(1))) {
                xstream.processAnnotations(BusinessDomain.class);
                businessDomain = (BusinessDomain)xstream.fromXML((String)result.get(1));
            } else {
                businessDomain = new BusinessDomain();
            }

            return this.constructServiceAndReturn(cls, businessDomain);
        } else if ("false".equals(result.get(0))) {
            if (StringUtil.hasText((String)result.get(1))) {
                xstream.processAnnotations(FaultDomain.class);
                faultDomain = (FaultDomain)xstream.fromXML((String)result.get(1));
            } else {
                faultDomain = new FaultDomain();
            }

            return this.constructServiceAndReturn(cls, faultDomain);
        } else {
            return null;
        }
    }

    private List<String> processExceptions(XStream xstream, String soapXmlResult) throws EsbException {
        List<String> resultList = new ArrayList(2);
        if (soapXmlResult != null && StringUtil.hasText(soapXmlResult)) {
            String xmlData = extractBusinessData(soapXmlResult);
            if (StringUtil.hasText(xmlData) && xmlData.indexOf("</soap:Fault>") <= 0) {
                resultList.add(0, "true");
            } else {
                String faultString = extractFaultData(soapXmlResult);
                if (!StringUtil.hasText(faultString)) {
                    throw new EsbException("通讯失败!请联系技术支持!");
                }

                xmlData = extractSoapFault(soapXmlResult);
                resultList.add(0, "false");
            }

            resultList.add(1, xmlData);
            return resultList;
        } else {
            throw new EsbException("通讯失败!请联系技术支持!");
        }
    }

    private List<String> processResult(String soapXmlResult) throws EsbException {
        List<String> resultList = new ArrayList(2);
        if (soapXmlResult != null && StringUtil.hasText(soapXmlResult)) {
            String faultCode = extractFaultCode(soapXmlResult);
            String faultString = extractFaultData(soapXmlResult);
            resultList.add(0, faultCode);
            resultList.add(1, faultString);
            return resultList;
        } else {
            throw new EsbException("通讯失败!请联系技术支持!");
        }
    }

    private List<String> processData(XStream xstream, String soapXmlResult) throws EsbException {
        List<String> resultList = new ArrayList(2);
        if (soapXmlResult != null && StringUtil.hasText(soapXmlResult)) {
            String xmlData = extractBusinessData(soapXmlResult);
            if (StringUtil.hasText(xmlData) && xmlData.indexOf("</soap:Fault>") <= 0) {
                resultList.add(0, "true");
            } else {
                xmlData = extractSoapFault(soapXmlResult);
                if (!StringUtil.hasText(xmlData)) {
                    throw new EsbException("通讯失败!请联系技术支持!");
                }

                resultList.add(0, "false");
            }

            resultList.add(1, xmlData);
            return resultList;
        } else {
            throw new EsbException("通讯失败!请联系技术支持!");
        }
    }

    private <T extends EsbService> T constructServiceAndReturn(Class<T> cls, BusinessDomain businessDomain) throws EsbException {
        try {
            T service = (T)cls.newInstance();
            service.doInit(businessDomain);
            return service;
        } catch (InstantiationException var4) {
//            LOGGER.error(var4, var4);
        } catch (IllegalAccessException var5) {
//            LOGGER.error(var5, var5);
        }

        throw new EsbException("InstantiationException:class[" + cls + "]");
    }

    private <T extends EsbService> T constructServiceAndReturn(Class<T> cls, FaultDomain faultDomain) throws EsbException {
        try {
            T service = (T)cls.newInstance();
            service.doInit(faultDomain);
            return service;
        } catch (InstantiationException var4) {
//            LOGGER.error(var4, var4);
        } catch (IllegalAccessException var5) {
//            LOGGER.error(var5, var5);
        }

        throw new EsbException("InstantiationException:class[" + cls + "]");
    }

    private static String extractBusinessData(String responseMsg) {
        int outBusinessBegin = responseMsg.indexOf("<out:business");
        int outBusinessEnd = responseMsg.lastIndexOf("</out:business>");
        return outBusinessBegin < outBusinessEnd ? responseMsg.substring(outBusinessBegin, outBusinessEnd + "</out:business>".length()) : "";
    }

    private static String extractFaultData(String responseMsg) {
        int outBusinessBegin = responseMsg.indexOf("<faultstring>");
        int outBusinessEnd = responseMsg.lastIndexOf("</faultstring>");
        return outBusinessBegin < outBusinessEnd ? responseMsg.substring(outBusinessBegin + "<faultstring>".length(), outBusinessEnd) : "";
    }

    private static String extractFaultCode(String responseMsg) {
        int outBusinessBegin = responseMsg.indexOf("<faultcode>");
        int outBusinessEnd = responseMsg.lastIndexOf("</faultcode>");
        return outBusinessBegin < outBusinessEnd ? responseMsg.substring(outBusinessBegin + "<faultcode>".length(), outBusinessEnd) : "";
    }

    private static String extractSoapFault(String responseMsg) {
        int outBusinessBegin = responseMsg.indexOf("<soap:Fault>");
        int outBusinessEnd = responseMsg.lastIndexOf("</soap:Fault>");
        return outBusinessBegin < outBusinessEnd ? responseMsg.substring(outBusinessBegin, outBusinessEnd + "</soap:Fault>".length()) : "";
    }

    private static List constructBusinessAndReturn(Element resultsetElement) {
        List list = new ArrayList();
        List<Element> rowlist = XmlParser.getChildList(resultsetElement);
        Iterator var4 = rowlist.iterator();

        while(var4.hasNext()) {
            Element rowelement = (Element)var4.next();
            Map rowmap = new HashMap();
            List<Element> elementlist = XmlParser.getChildList(rowelement);
            Iterator var8 = elementlist.iterator();

            while(var8.hasNext()) {
                Element element = (Element)var8.next();
                String elementName = element.getName();
                if ("resultset".equalsIgnoreCase(elementName)) {
                    List templist = constructBusinessAndReturn(element);
                    rowmap.put(elementName, templist);
                } else {
                    rowmap.put(elementName, element.getText());
                }
            }

            list.add(rowmap);
        }

        return list;
    }
}
