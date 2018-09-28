package com.ufgov.sssfm.socket.utils;

import com.ufgov.sssfm.socket.dao.BaseDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtil {
    /*
     * 获取公共域报文
     */
    public Document getPensionData(String typeCode, String typeName)
            throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("pensiondata");
        document.appendChild(root);
        Element version = document.createElement("version");
        version.setTextContent("v1.0");
        root.appendChild(version);
        Element TypeName = document.createElement("typename");
        TypeName.setTextContent(typeName);
        Element DataSetType = document.createElement("datasettype");
        root.appendChild(DataSetType);
        DataSetType.appendChild(TypeName);
        Element TypeCode = document.createElement("typecode");
        TypeCode.setTextContent(typeCode);
        DataSetType.appendChild(TypeCode);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat df1 = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        Element SendDate = document.createElement("senddate");
        SendDate.setTextContent(df.format(date));
        root.appendChild(SendDate);
        Element ReceiveCode = document.createElement("receivecode");
        ReceiveCode.setTextContent("v1.0");
        root.appendChild(ReceiveCode);
        Element SendTime = document.createElement("sendtime");
        SendTime.setTextContent(df1.format(date));
        root.appendChild(SendTime);
        Element SendCode = document.createElement("sendcode");
        SendCode.setTextContent("v1.0");
        root.appendChild(SendCode);
        return document;
    }

    /*
     * 获取反馈报文
     */
    public Document getReturnXML(String xml, String appcode, String appmsg)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Document doc = null;
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        NodeList e = doc.getElementsByTagName("receivecode");
        String receivecode = e.item(0).getTextContent();
        e = doc.getElementsByTagName("sendcode");
        String sendcode = e.item(0).getTextContent();
        e = doc.getElementsByTagName("typecode");
        String typecode = e.item(0).getTextContent();
        e = doc.getElementsByTagName("typename");
        String typename = e.item(0).getTextContent();
        e = doc.getElementsByTagName("appseriono");
        String appseriono = e.item(0).getTextContent();

        Element root = document.createElement("pensiondata");
        document.appendChild(root);
        Element version = document.createElement("version");
        version.setTextContent("v1.0");
        root.appendChild(version);
        Element AppCode = document.createElement("appcode");
        AppCode.setTextContent(appcode);
        root.appendChild(AppCode);
        Element AppMsg = document.createElement("appmsg");
        AppMsg.setTextContent(appmsg);
        root.appendChild(AppMsg);
        Element TypeName = document.createElement("typename");
        TypeName.setTextContent(typename);
        Element DataSetType = document.createElement("datasettype");
        root.appendChild(DataSetType);
        DataSetType.appendChild(TypeName);
        Element TypeCode = document.createElement("typecode");
        TypeCode.setTextContent(typecode);
        DataSetType.appendChild(TypeCode);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat df1 = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        Element SendDate = document.createElement("senddate");
        SendDate.setTextContent(df.format(date));
        root.appendChild(SendDate);
        Element ReceiveCode = document.createElement("receivecode");
        ReceiveCode.setTextContent(receivecode);
        root.appendChild(ReceiveCode);
        Element SendTime = document.createElement("sendtime");
        SendTime.setTextContent(df1.format(date));
        root.appendChild(SendTime);
        Element SendCode = document.createElement("sendcode");
        SendCode.setTextContent(sendcode);
        root.appendChild(SendCode);
        Element PensionInfo = document.createElement("PensionInfo");
        root.appendChild(PensionInfo);
        Element appserionoele = document.createElement("appseriono");
        appserionoele.setTextContent(appseriono);
        PensionInfo.appendChild(appserionoele);
        return document;
    }

    /*
     * 划款结果信息
     */
    public Document getContrRecord(List<Map<String, String>> lists,
                                   Map<String, String> map, Document document) {
        Element root = document.getDocumentElement();
        Element total = document.createElement("PensionInfo");
        root.appendChild(total);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssms");
        Date date = new Date();
        Element appseriono = document.createElement("appseriono");
        appseriono.setTextContent(df.format(date) + map.get("appseriono"));
        total.appendChild(appseriono);
        Element total_code = document.createElement("total_code");
        total_code.setTextContent(map.get("total_code"));
        total.appendChild(total_code);
        Element areamanagecode = document.createElement("areamanagecode");
        areamanagecode.setTextContent(map.get("areamanagecode"));
        total.appendChild(areamanagecode);
        Element areamanagename = document.createElement("areamanagename");
        areamanagename.setTextContent(map.get("areamanagename"));
        total.appendChild(areamanagename);
        Element type = document.createElement("type");
        type.setTextContent(map.get("type"));
        total.appendChild(type);
        Element transfer_mon_date = document.createElement("transfer_mon_date");
        transfer_mon_date.setTextContent(map.get("transfer_mon_date"));
        total.appendChild(transfer_mon_date);
        Element contr_total = document.createElement("contr_total");
        contr_total.setTextContent(map.get("contr_total"));
        total.appendChild(contr_total);
        Element memo = document.createElement("memo");
        memo.setTextContent(map.get("memo"));
        total.appendChild(memo);
        Element rows = document.createElement("rows");
        total.appendChild(rows);

        for (Map<String, String> map2 : lists) {
            Element corp = document.createElement("row");
            rows.appendChild(corp);
            Element total_code1 = document.createElement("total_code");
            total_code1.setTextContent(map2.get("total_code"));
            corp.appendChild(total_code1);
            Element contr_serial_no = document.createElement("contr_serial_no");
            contr_serial_no.setTextContent(map2.get("contr_serial_no"));
            corp.appendChild(contr_serial_no);
            Element corp_jgb_unique_code = document
                    .createElement("corp_jgb_unique_code");
            corp_jgb_unique_code.setTextContent(map2
                    .get("corp_jgb_unique_code"));
            corp.appendChild(corp_jgb_unique_code);
            Element corp_name = document.createElement("corp_name");
            corp_name.setTextContent(map2.get("corp_name"));
            corp.appendChild(corp_name);
            Element contr_balance = document.createElement("contr_balance");
            contr_balance.setTextContent(map2.get("contr_balance"));
            corp.appendChild(contr_balance);
            Element memo1 = document.createElement("memo");
            memo1.setTextContent(map2.get("memo"));
            corp.appendChild(memo1);
        }
        return document;
    }

    /*
     * 到账信息
     */
    public Document getTotalContrRecord(Map<String, String> map,
                                        Document document) {
        Element root = document.getDocumentElement();
        Element total = document.createElement("PensionInfo");
        root.appendChild(total);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssms");
        Date date = new Date();
        Element appseriono = document.createElement("appseriono");
        appseriono.setTextContent(df.format(date) + map.get("appseriono"));
        total.appendChild(appseriono);
        Element trancode = document.createElement("trancode");
        trancode.setTextContent(map.get("trancode"));
        total.appendChild(trancode);
        Element total_code = document.createElement("total_serial_no");
        total_code.setTextContent(map.get("total_serial_no"));
        total.appendChild(total_code);
        Element areamanagecode = document.createElement("areamanagecode");
        areamanagecode.setTextContent(map.get("areamanagecode"));
        total.appendChild(areamanagecode);
        Element areamanagename = document.createElement("areamanagename");
        areamanagename.setTextContent(map.get("areamanagename"));
        total.appendChild(areamanagename);
        Element type = document.createElement("type");
        type.setTextContent(map.get("type"));
        total.appendChild(type);
        Element transfer_mon_date = document.createElement("transfer_mon_date");
        transfer_mon_date.setTextContent(map.get("transfer_mon_date"));
        total.appendChild(transfer_mon_date);
        Element total_date = document.createElement("total_date");
        total_date.setTextContent(map.get("total_date"));
        total.appendChild(total_date);
        Element contr_state = document.createElement("contr_state");
        contr_state.setTextContent(map.get("contr_state"));
        total.appendChild(contr_state);
        Element contr_total = document.createElement("contr_total");
        contr_total.setTextContent(map.get("contr_total"));
        total.appendChild(contr_total);
        Element actual_cash = document.createElement("actual_cash");
        actual_cash.setTextContent(map.get("actual_cash"));
        total.appendChild(actual_cash);
        Element memo = document.createElement("memo");
        memo.setTextContent(map.get("memo"));
        total.appendChild(memo);
        return document;
    }

    /*
     * 缴费汇总定长包
     */
    public String analysisTotalContrXml(byte[] bytes)
            throws UnsupportedEncodingException {
        BaseDAO dao = new BaseDAO();
        String trancode = getStr(bytes, 0, 4);
        if (trancode != null && trancode.equals("2100")) {
            return "0000";
        }
        String total_serial_no = getStr(bytes, 4, 20);
        String transfer_mon_date = getStr(bytes, 174, 8);
        String contr_state = getStr(bytes, 204, 2);
        String actual_cash = getStr(bytes, 206, 14);
        String result = dao.updateContr(total_serial_no, transfer_mon_date,
                actual_cash, contr_state);
        return result;
    }

    public static String getStr(byte[] bytes, int start, int len) {
        byte[] temp = new byte[len];
        for (int i = 0; i < len; i++) {
            temp[i] = bytes[start + i];
        }
        return new String(temp);
    }

    /*
     * 缴费汇总定长包
     */
    public String getTotalContrRecordToYH(Map<String, String> map)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(fillField(map.get("trancode"), "char", 4));
        sb.append(fillField(map.get("total_serial_no"), "char", 20));
        sb.append(fillField(map.get("areamanagecode"), "char", 20));
        sb.append(fillField(map.get("areamanagename"), "char", 128));
        sb.append(fillField(map.get("type"), "char", 2));
        sb.append(fillField(map.get("transfer_mon_date"), "char", 8));
        sb.append(fillField(map.get("total_date"), "char", 8));
        sb.append(fillField(map.get("contr_total"), "decimal", 14));
        sb.append(fillField(map.get("contr_state"), "char", 2));
        sb.append(fillField(map.get("actual_cash"), "decimal", 14));
        sb.append(fillField(map.get("memo"), "char", 128));
        return sb.toString();
    }

    public static String fillField(String value, String dataType, int len)
            throws UnsupportedEncodingException {
        int fileLen = 0;
        if (value != null) {
            fileLen = len - value.getBytes("gbk").length;
        } else {
            fileLen = len;
        }
        StringBuilder fillValue = new StringBuilder();
        if (fileLen < 0) {
            value = value.substring(0, len);
        }
        if ("char".equalsIgnoreCase(dataType)) {
            if (value != null) {
                fillValue.append(value);
            }
            for (int i = 0; i < fileLen; i++) {
                fillValue.append(" ");
            }
        } else if ("int".equalsIgnoreCase(dataType)
                || "decimal".equalsIgnoreCase(dataType)) {
            for (int i = 0; i < fileLen; i++) {
                fillValue.append(" ");
            }
            fillValue.append(value);
        } else if ("date".equalsIgnoreCase(dataType)) {
            if (value == null || value.trim().equals("")) {
                value = "19000101";
            }
            fillValue.append(value);
        }
        return fillValue.toString();
    }

    /*
     * 资金划款指令
     */
    public Document getInstructRecord(Map<String, String> map, Document document) {
        Element root = document.getDocumentElement();
        Element total = document.createElement("PensionInfo");
        root.appendChild(total);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssms");
        Date date = new Date();
        Element appseriono = document.createElement("appseriono");
        appseriono.setTextContent(df.format(date) + map.get("appseriono"));
        total.appendChild(appseriono);
        Element instruct_code = document.createElement("instruct_code");
        instruct_code.setTextContent(map.get("instruct_code"));
        total.appendChild(instruct_code);
        Element instruct_date = document.createElement("instruct_date");
        instruct_date.setTextContent(map.get("instruct_date"));
        total.appendChild(instruct_date);
        Element paybankaccname = document.createElement("paybankaccname");
        paybankaccname.setTextContent(map.get("paybankaccname"));
        total.appendChild(paybankaccname);
        Element paybankaccno = document.createElement("paybankaccno");
        paybankaccno.setTextContent(map.get("paybankaccno"));
        total.appendChild(paybankaccno);
        Element paybank = document.createElement("paybank");
        paybank.setTextContent(map.get("paybank"));
        total.appendChild(paybank);
        Element gatheraccname = document.createElement("gatheraccname");
        gatheraccname.setTextContent(map.get("gatheraccname"));
        total.appendChild(gatheraccname);
        Element gatherbankno = document.createElement("gatherbankno");
        gatherbankno.setTextContent(map.get("gatherbankno"));
        total.appendChild(gatherbankno);
        Element gatherbank = document.createElement("gatherbank");
        gatherbank.setTextContent(map.get("gatherbank"));
        total.appendChild(gatherbank);
        Element contrbalance = document.createElement("contrbalance");
        contrbalance.setTextContent(map.get("contrbalance"));
        total.appendChild(contrbalance);
        Element memo = document.createElement("memo");
        memo.setTextContent(map.get("memo"));
        total.appendChild(memo);
        return document;
    }

    /*
     * 缴费汇总单
     */
    public String analysisTotalContrXml(String xml) {
        BaseDAO dao = new BaseDAO();
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            dao.insertLog(xml, "报文解析错误," + e.getMessage(), "", "", "");
        }
        NodeList e = doc.getElementsByTagName("total_serial_no");
        String total_serial_no = e.item(0).getTextContent();
        e = doc.getElementsByTagName("areamanagecode");
        String areamanagecode = e.item(0).getTextContent();
        e = doc.getElementsByTagName("areamanagename");
        String areamanagename = e.item(0).getTextContent();
        e = doc.getElementsByTagName("trancode");
        String trancode = e.item(0).getTextContent();
        e = doc.getElementsByTagName("type");
        String type = e.item(0).getTextContent();
        e = doc.getElementsByTagName("transfer_mon_date");
        String transfer_mon_date = e.item(0).getTextContent();
        e = doc.getElementsByTagName("total_date");
        String total_date = e.item(0).getTextContent();
        e = doc.getElementsByTagName("contr_state");
        String contr_state = e.item(0).getTextContent();
        e = doc.getElementsByTagName("contr_total");
        String contr_total = e.item(0).getTextContent();
        e = doc.getElementsByTagName("actual_cash");
        String actual_cash = e.item(0).getTextContent();
        e = doc.getElementsByTagName("memo");
        String memo = e.item(0).getTextContent();
        e = doc.getElementsByTagName("typecode");
        String typecode = e.item(0).getTextContent();
        String result = dao.insertTotalContr(total_serial_no, areamanagecode,
                areamanagename, type, transfer_mon_date, total_date,
                contr_state, contr_total, actual_cash, memo, trancode);
        dao.insertLog(xml, result, typecode, "", total_serial_no);
        return result;
    }


    /**
     * 解析到账标识
     *
     * @param xml
     * @return
     */
    public String analysisTypeCode(String xml) {
        BaseDAO dao = new BaseDAO();
        Document doc = null;
        String typecode = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            NodeList e = doc.getElementsByTagName("typecode");
            typecode = e.item(0).getTextContent();
        } catch (Exception e) {
            dao.insertLog(xml, "报文解析错误," + e.getMessage(), "", "", "");
        }
        return typecode;
    }

    public String analysisFileName(String xml) {
        BaseDAO dao = new BaseDAO();
        Document doc = null;
        String filename = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            NodeList e = doc.getElementsByTagName("filename");
            filename = e.item(0).getTextContent();
        } catch (Exception e) {
            dao.insertLog(xml, "报文解析错误," + e.getMessage(), "", "", "");
        }
        return filename;
    }

    /*
     * 资金划款指令
     */
    public String analysisInstructXml(String xml) {
        BaseDAO dao = new BaseDAO();
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            dao.insertLog(xml, "报文解析错误," + e.getMessage(), "", "", "");
        }
        NodeList e = doc.getElementsByTagName("instruct_code");
        String instruct_code = e.item(0).getTextContent();
        e = doc.getElementsByTagName("instruct_date");
        String instruct_date = e.item(0).getTextContent();
        e = doc.getElementsByTagName("paybankaccname");
        String paybankaccname = e.item(0).getTextContent();
        e = doc.getElementsByTagName("paybankaccno");
        String paybankaccno = e.item(0).getTextContent();
        e = doc.getElementsByTagName("paybank");
        String paybank = e.item(0).getTextContent();
        e = doc.getElementsByTagName("gatheraccname");
        String gatheraccname = e.item(0).getTextContent();
        e = doc.getElementsByTagName("gatherbankno");
        String gatherbankno = e.item(0).getTextContent();
        e = doc.getElementsByTagName("gatherbank");
        String gatherbank = e.item(0).getTextContent();
        e = doc.getElementsByTagName("contrbalance");
        String contrbalance = e.item(0).getTextContent();
        e = doc.getElementsByTagName("memo");
        String memo = e.item(0).getTextContent();
        e = doc.getElementsByTagName("typecode");
        String typecode = e.item(0).getTextContent();
        String result = dao.insertInstruct(instruct_code, instruct_date,
                paybankaccname, paybankaccno, paybank, gatheraccname,
                gatherbankno, gatherbank, contrbalance, memo);
        dao.insertLog(xml, result, typecode, "", instruct_code);
        return result;
    }

    public Map<String, String> analysisgetPensionData(String xml)
            throws SAXException, IOException, ParserConfigurationException {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        NodeList e = doc.getElementsByTagName("appcode");
        String appcode = e.item(0).getTextContent();
        map.put(appcode, appcode);
        e = doc.getElementsByTagName("appmsg");
        String appmsg = e.item(0).getTextContent();
        map.put(appmsg, appmsg);
        return map;
    }
}
