//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.esb.bas;

public class MessageEnvelop {
    public MessageEnvelop() {
    }

    public String createMsgFault(String errorMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        reXML.append("<soap:Fault>").append("<faultcode>").append("500").append("</faultcode>").append("<faultstring>").append("<error msg=\"").append(errorMsg).append("\" />").append("</faultstring>").append("</soap:Fault>");
        reXML.append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public String createMsgFault(String errorMsg, int errorCode) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        reXML.append("<soap:Fault>").append("<faultcode>").append("500-").append(errorCode).append("</faultcode>").append("<faultstring>").append("<error msg=\"").append(errorMsg).append("\" />").append("</faultstring>").append("</soap:Fault>");
        reXML.append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }
}
