//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.esb.util;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.*;

public class XmlParser {
    public XmlParser() {
    }

    public static Document createEmptyXmlFile(String xmlPath) {
        if (xmlPath != null && !xmlPath.equals("")) {
            Document document = DocumentHelper.createDocument();
            OutputFormat format = OutputFormat.createPrettyPrint();

            try {
                XMLWriter output = new XMLWriter(new FileWriter(xmlPath), format);
                output.write(document);
                output.close();
                return document;
            } catch (IOException var5) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Document getDocument4XmlPath(String xmlPath) {
        if (xmlPath != null && !xmlPath.equals("")) {
            File file = new File(xmlPath);
            if (!file.exists()) {
                return createEmptyXmlFile(xmlPath);
            } else {
                SAXReader reader = new SAXReader();
                Document document = null;

                try {
                    document = reader.read(xmlPath);
                } catch (DocumentException var5) {
                    var5.printStackTrace();
                }

                return document;
            }
        } else {
            return null;
        }
    }

    public static Document getDocument4XmlStr(String xmlStr) {
        SAXReader reader = new SAXReader();
        Document document = null;

        try {
            InputStream is = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
            document = reader.read(is);
        } catch (DocumentException var4) {
            var4.printStackTrace();
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        return document;
    }

    public static Element getRootNode(Document document) {
        if (document == null) {
            return null;
        } else {
            Element root = document.getRootElement();
            return root;
        }
    }

    public static Element getRootNode(String xmlPath) {
        if (xmlPath != null && !xmlPath.trim().equals("")) {
            Document document = getDocument4XmlPath(xmlPath);
            return document == null ? null : getRootNode(document);
        } else {
            return null;
        }
    }

    public static Iterator<Element> getIterator(Element parent) {
        if (parent == null) {
            return null;
        } else {
            Iterator<Element> iterator = parent.elementIterator();
            return iterator;
        }
    }

    public static List<Element> getChildElements(Element parent, String childName) {
        childName = childName.trim();
        if (parent == null) {
            return null;
        } else {
            childName = childName + "//";
            List<Element> childElements = parent.selectNodes(childName);
            return childElements;
        }
    }

    public static List<Element> getChildList(Element node) {
        if (node == null) {
            return null;
        } else {
            Iterator<Element> itr = getIterator(node);
            if (itr == null) {
                return null;
            } else {
                ArrayList childList = new ArrayList();

                while(itr.hasNext()) {
                    Element kidElement = (Element)itr.next();
                    if (kidElement != null) {
                        childList.add(kidElement);
                    }
                }

                return childList;
            }
        }
    }

    public static Node getSingleNode(Element parent, String nodeNodeName) {
        nodeNodeName = nodeNodeName.trim();
        String xpath = "//";
        if (parent == null) {
            return null;
        } else if (nodeNodeName != null && !nodeNodeName.equals("")) {
            xpath = xpath + nodeNodeName;
            Node kid = parent.selectSingleNode(xpath);
            return kid;
        } else {
            return null;
        }
    }

    public static Element getChild(Element parent, String childName) {
        childName = childName.trim();
        if (parent == null) {
            return null;
        } else if (childName != null && !childName.equals("")) {
            Element e = null;
            Iterator it = getIterator(parent);

            while(it != null && it.hasNext()) {
                Element k = (Element)it.next();
                if (k != null && k.getName().equalsIgnoreCase(childName)) {
                    e = k;
                    break;
                }
            }

            return e;
        } else {
            return null;
        }
    }

    public static boolean hasChild(Element e) {
        return e == null ? false : e.hasContent();
    }

    public static Iterator<Attribute> getAttrIterator(Element e) {
        if (e == null) {
            return null;
        } else {
            Iterator<Attribute> attrIterator = e.attributeIterator();
            return attrIterator;
        }
    }

    public static List<Attribute> getAttributeList(Element e) {
        if (e == null) {
            return null;
        } else {
            List<Attribute> attributeList = new ArrayList();
            Iterator<Attribute> atrIterator = getAttrIterator(e);
            if (atrIterator == null) {
                return null;
            } else {
                while(atrIterator.hasNext()) {
                    Attribute attribute = (Attribute)atrIterator.next();
                    attributeList.add(attribute);
                }

                return attributeList;
            }
        }
    }

    public static Attribute getAttribute(Element element, String attrName) {
        attrName = attrName.trim();
        if (element == null) {
            return null;
        } else if (attrName != null && !attrName.equals("")) {
            Attribute attribute = element.attribute(attrName);
            return attribute;
        } else {
            return null;
        }
    }

    public static String attrValue(Element e, String attrName) {
        attrName = attrName.trim();
        if (e == null) {
            return null;
        } else {
            return attrName != null && !attrName.equals("") ? e.attributeValue(attrName) : null;
        }
    }

    public static Map<String, String> getNodeAttrMap(Element e) {
        Map<String, String> attrMap = new HashMap();
        if (e == null) {
            return null;
        } else {
            List<Attribute> attributes = getAttributeList(e);
            if (attributes == null) {
                return null;
            } else {
                Iterator var4 = attributes.iterator();

                while(var4.hasNext()) {
                    Attribute attribute = (Attribute)var4.next();
                    String attrValueString = attrValue(e, attribute.getName());
                    attrMap.put(attribute.getName(), attrValueString);
                }

                return attrMap;
            }
        }
    }

    public static Map<String, String> getSingleNodeText(Element e) {
        Map<String, String> map = new HashMap();
        if (e == null) {
            return null;
        } else {
            List<Element> kids = getChildList(e);
            Iterator var4 = kids.iterator();

            while(var4.hasNext()) {
                Element e2 = (Element)var4.next();
                if (e2.getTextTrim() != null) {
                    map.put(e2.getName(), e2.getTextTrim());
                }
            }

            return map;
        }
    }

    public static Map<String, String> getSingleNodeText(String xmlFilePath) {
        xmlFilePath = xmlFilePath.trim();
        if (xmlFilePath != null && !xmlFilePath.equals("")) {
            Element rootElement = getRootNode(xmlFilePath);
            return rootElement != null && hasChild(rootElement) ? getSingleNodeText(rootElement) : null;
        } else {
            return null;
        }
    }

    public static <T> T getNameNode(String xmlFilePath, String tagName, Flag flag) {
        xmlFilePath = xmlFilePath.trim();
        tagName = tagName.trim();
        if (xmlFilePath != null && tagName != null && !xmlFilePath.equals("") && !tagName.equals("")) {
            Element rootElement = getRootNode(xmlFilePath);
            if (rootElement == null) {
                return null;
            } else {
                List<Element> tagElementList = getNameElement(rootElement, tagName);
                if (tagElementList == null) {
                    return null;
                } else {
                    switch(flag) {
                    case more:
                        return (T) tagElementList.get(0);
                    default:
                        return (T) tagElementList;
                    }
                }
            }
        } else {
            return null;
        }
    }

    public static Map<Integer, Object> getNameNodeAllKidsAttributeMap(Element parent) {
        Map<Integer, Object> allAttrMap = new HashMap();
        if (parent == null) {
            return null;
        } else {
            List<Element> childlElements = getChildList(parent);
            if (childlElements == null) {
                return null;
            } else {
                for(int i = 0; i < childlElements.size(); ++i) {
                    Element childElement = (Element)childlElements.get(i);
                    Map<String, String> attrMap = getNodeAttrMap(childElement);
                    allAttrMap.put(i, attrMap);
                }

                return allAttrMap;
            }
        }
    }

    public static <T> T getNameNodeAllAttributeMap(String xmlFilePath, String nodeName, Flag flag) {
        nodeName = nodeName.trim();
        Map<String, String> allAttrMap = null;
        Map<Integer, Map<String, String>> mostKidsAllAttriMap = new HashMap();
        if (xmlFilePath != null && nodeName != null && !xmlFilePath.equals("") && !nodeName.equals("")) {
            switch(flag) {
            case one:
                Element nameNode = (Element)getNameNode(xmlFilePath, nodeName, Flag.one);
                allAttrMap = getNodeAttrMap(nameNode);
                return (T) allAttrMap;
            case more:
                List<Element> nameKidsElements = (List)getNameNode(xmlFilePath, nodeName, Flag.more);

                for(int i = 0; i < nameKidsElements.size(); ++i) {
                    Element kid = (Element)nameKidsElements.get(i);
                    allAttrMap = getNodeAttrMap(kid);
                    mostKidsAllAttriMap.put(i, allAttrMap);
                }

                return (T) mostKidsAllAttriMap;
            default:
                return null;
            }
        } else {
            return null;
        }
    }

    public static List<Element> ransack(Element element, List<Element> allkidsList) {
        if (element == null) {
            return null;
        } else {
            if (hasChild(element)) {
                List<Element> kids = getChildList(element);
                Iterator var4 = kids.iterator();

                while(var4.hasNext()) {
                    Element e = (Element)var4.next();
                    allkidsList.add(e);
                    ransack(e, allkidsList);
                }
            }

            return allkidsList;
        }
    }

    public static List<Element> getNameElement(Element element, String nodeName) {
        nodeName = nodeName.trim();
        List<Element> kidsElements = new ArrayList();
        if (element == null) {
            return null;
        } else if (nodeName != null && !nodeName.equals("")) {
            List<Element> allKids = ransack(element, new ArrayList());
            if (allKids == null) {
                return null;
            } else {
                for(int i = 0; i < allKids.size(); ++i) {
                    Element kid = (Element)allKids.get(i);
                    if (nodeName.equals(kid.getName())) {
                        kidsElements.add(kid);
                    }
                }

                return kidsElements;
            }
        } else {
            return null;
        }
    }

    public static int validateSingle(Element element) {
        int j = 1;
        if (element == null) {
            return j;
        } else {
            Element parent = element.getParent();
            List<Element> kids = getChildList(parent);
            Iterator var5 = kids.iterator();

            while(var5.hasNext()) {
                Element kid = (Element)var5.next();
                if (element.equals(kid)) {
                    ++j;
                }
            }

            return j;
        }
    }

    public static enum Flag {
        one,
        more;

        private Flag() {
        }
    }
}
