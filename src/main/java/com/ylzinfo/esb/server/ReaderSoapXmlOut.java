//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.esb.server;

import com.ylzinfo.esb.util.StringUtil;
import com.ylzinfo.esb.util.Structs;
import org.apache.commons.collections.map.ListOrderedMap;
import sun.misc.BASE64Encoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReaderSoapXmlOut {
    public ReaderSoapXmlOut() {
    }

    public static String readerSoapXMlOut(List colset, List<Structs> structsList, String msgInfo) {
        StringBuffer reXML = new StringBuffer();
        reXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        String structs = BuildStructs.getStructsData(structsList);
        String[] structsRows = structs.split("<row");
        String[] structsColumnName = new String[structsRows.length - 1];
        String[] structsColumnType = new String[structsRows.length - 1];

        for(int i = 1; i < structsRows.length; ++i) {
            structsColumnName[i - 1] = structsRows[i].split("columnname=\"")[1].split("\"")[0];
            structsColumnType[i - 1] = structsRows[i].split("typename=\"")[1].split("\"")[0];
        }

        if (colset != null && colset.size() != 0) {
            if (colset.size() == 1) {
                reXML.append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
                reXML.append("<result showtype=\"1\"/>");
            } else if (colset.size() > 1) {
                reXML.append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
                reXML.append("<result showtype=\"2\"/>");
            }

            reXML.append("<resultset name=\"retrieve\">");

            try {
                Iterator it = colset.iterator();

                while(it.hasNext()) {
                    ListOrderedMap bean = (ListOrderedMap)it.next();
                    reXML.append("<row ");

                    for(int i = 0; i < structsColumnName.length; ++i) {
                        reXML.append(structsColumnName[i]).append("=\"");
                        if ("blob".equalsIgnoreCase(structsColumnType[i])) {
                            byte[] photograph = (byte[])bean.get(structsColumnName[i]);
                            String base64Data = null;
                            if (photograph != null) {
                                base64Data = getBase64Data(photograph);
                            }

                            reXML.append(base64Data).append("\" ");
                        } else if ("clob".equalsIgnoreCase(structsColumnType[i])) {
                            String photograph = (String)bean.get(structsColumnName[i]);
                            reXML.append(photograph == null ? "" : photograph.toString()).append("\" ");
                        } else if (bean.get(structsColumnName[i]) != null) {
                            reXML.append(bean.get(structsColumnName[i]).toString()).append("\" ");
                        } else {
                            reXML.append("\" ");
                        }

                        if (i == structsColumnName.length - 1) {
                            reXML.append("/>");
                        }
                    }
                }
            } catch (Exception var13) {
                return BuildSoapXMlError("700", var13.getMessage());
            }

            reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("请求成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
            return reXML.toString();
        } else {
            reXML.append("<soap:Fault>").append("<faultcode>").append("700.000.000").append("</faultcode>").append("<faultstring>").append(msgInfo).append("</faultstring>").append("</soap:Fault>");
            reXML.append("</soap:Body>").append("</soap:Envelope>");
            return reXML.toString();
        }
    }

    public static String readerSoapXMlOut(List colset, int totalnum, List<Structs> structsList, String msgInfo, String serviceName) {
        StringBuffer reXML = new StringBuffer();
        reXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        String structs = BuildStructs.getStructsData(structsList);
        String[] structsRows = structs.split("<row");
        String[] structsColumnName = new String[structsRows.length - 1];
        String[] structsColumnType = new String[structsRows.length - 1];

        for(int i = 1; i < structsRows.length; ++i) {
            structsColumnName[i - 1] = structsRows[i].split("columnname=\"")[1].split("\"")[0];
            structsColumnType[i - 1] = structsRows[i].split("typename=\"")[1].split("\"")[0];
        }

        if (colset != null && colset.size() != 0) {
            if (colset.size() == 1) {
                reXML.append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
                reXML.append("<result showtype=\"1\"/>");
            } else if (colset.size() > 1) {
                reXML.append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
                reXML.append("<result showtype=\"2\"/>");
            }

            reXML.append("<result serviceId=\"");
            reXML.append(serviceName);
            reXML.append("\"/>");
            if (totalnum != 0) {
                reXML.append("<result totalnum=\"").append(totalnum).append("\" />");
            }

            reXML.append("<resultset name=\"retrieve\">");

            try {
                Iterator it = colset.iterator();

                while(it.hasNext()) {
                    ListOrderedMap bean = (ListOrderedMap)it.next();
                    reXML.append("<row ");

                    for(int i = 0; i < structsColumnName.length; ++i) {
                        reXML.append(structsColumnName[i]).append("=\"");
                        if ("blob".equalsIgnoreCase(structsColumnType[i])) {
                            byte[] photograph = (byte[])bean.get(structsColumnName[i]);
                            String base64Data = null;
                            if (photograph != null) {
                                base64Data = getBase64Data(photograph);
                            }

                            reXML.append(base64Data).append("\" ");
                        } else if ("clob".equalsIgnoreCase(structsColumnType[i])) {
                            String photograph = (String)bean.get(structsColumnName[i]);
                            reXML.append(photograph == null ? "" : photograph.toString()).append("\" ");
                        } else if (bean.get(structsColumnName[i]) != null) {
                            reXML.append(bean.get(structsColumnName[i]).toString()).append("\" ");
                        } else {
                            reXML.append("\" ");
                        }

                        if (i == structsColumnName.length - 1) {
                            reXML.append("/>");
                        }
                    }
                }
            } catch (Exception var15) {
                return BuildSoapXMlError("700", var15.getMessage());
            }

            reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("请求成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
            return reXML.toString();
        } else {
            reXML.append("<soap:Fault>").append("<faultcode>").append("700.000.000").append("</faultcode>").append("<faultstring>").append(msgInfo).append("</faultstring>").append("</soap:Fault>");
            reXML.append("</soap:Body>").append("</soap:Envelope>");
            return reXML.toString();
        }
    }

    public static String readerSoapXMlOut(List colset, String cpage, int rows, int totalnum, List<Structs> structsList, String msgInfo, String serviceName) {
        StringBuffer reXML = new StringBuffer();
        reXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        if (colset != null && colset.size() != 0) {
            if (colset.size() > 20000) {
                reXML.append("<soap:Fault>").append("<faultcode>").append("700.000.000").append("</faultcode>").append("<faultstring>").append("查询结果超过20000条").append("</faultstring>").append("</soap:Fault>");
                reXML.append("</soap:Body>").append("</soap:Envelope>");
                return reXML.toString();
            } else {
                reXML.append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
                String[] structsColumnName = (String[])null;
                String[] structsColumnType = (String[])null;
                int i;
                if (structsList.size() != 0) {
                    String structs = BuildStructs.getStructsData(structsList);
                    String[] structsRows = structs.split("<row");
                    structsColumnName = new String[structsRows.length - 1];
                    structsColumnType = new String[structsRows.length - 1];

                    for(i = 1; i < structsRows.length; ++i) {
                        structsColumnName[i - 1] = structsRows[i].split("columnname=\"")[1].split("\"")[0];
                        structsColumnType[i - 1] = structsRows[i].split("typename=\"")[1].split("\"")[0];
                    }
                }

                if (cpage != null) {
                    reXML.append("<result cpage=\"").append(cpage).append("\" />");
                    int pages = totalnum % rows == 0 ? totalnum / rows : totalnum / rows + 1;
                    reXML.append("<result pages=\"").append(pages).append("\" />");
                    reXML.append("<result rowCount=\"").append(totalnum).append("\" />");
                } else if (totalnum != 0) {
                    reXML.append("<result totalnum=\"").append(totalnum).append("\" />");
                }

                reXML.append("<resultset name=\"retrieve\">");

                try {
                    if (structsList.size() != 0) {
                        Iterator it = colset.iterator();

                        while(it.hasNext()) {
                            ListOrderedMap bean = (ListOrderedMap)it.next();
                            reXML.append("<row ");

                            for(i = 0; i < structsColumnName.length; ++i) {
                                reXML.append(structsColumnName[i]).append("=\"");
                                if ("blob".equalsIgnoreCase(structsColumnType[i])) {
                                    byte[] photograph = (byte[])bean.get(structsColumnName[i]);
                                    String base64Data = null;
                                    if (photograph != null) {
                                        base64Data = getBase64Data(photograph);
                                    }

                                    reXML.append(base64Data).append("\" ");
                                } else if ("clob".equalsIgnoreCase(structsColumnType[i])) {
                                    String photograph = (String)bean.get(structsColumnName[i]);
                                    reXML.append(photograph == null ? "" : photograph.toString()).append("\" ");
                                } else if (bean.get(structsColumnName[i]) != null) {
                                    reXML.append(StringUtil.encode(bean.get(structsColumnName[i]).toString())).append("\" ");
                                } else {
                                    reXML.append("\" ");
                                }

                                if (i == structsColumnName.length - 1) {
                                    reXML.append("/>");
                                }
                            }
                        }
                    } else {
                        Iterator it = colset.iterator();

                        while(it.hasNext()) {
                            ListOrderedMap bean = (ListOrderedMap)it.next();
                            reXML.append("<row ");
                            Set set = bean.entrySet();
                            Iterator setit = set.iterator();

                            while(setit.hasNext()) {
                                String[] setvalue = setit.next().toString().split("=");
                                String name = setvalue[0];
                                reXML.append(name.toLowerCase()).append("=\"").append(StringUtil.encode(setvalue[1])).append("\" ");
                            }

                            reXML.append("/>");
                        }
                    }
                } catch (Exception var16) {
                    return BuildSoapXMlError("700", var16.getMessage());
                }

                reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("请求成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
                return reXML.toString();
            }
        } else {
            reXML.append("<soap:Fault>").append("<faultcode>").append("700.000.000").append("</faultcode>").append("<faultstring>").append(msgInfo).append("</faultstring>").append("</soap:Fault>");
            reXML.append("</soap:Body>").append("</soap:Envelope>");
            return reXML.toString();
        }
    }

    public static String readerSoapXMlOut(List colset, int totalnum, List<Structs> structsList, String msgInfo) {
        StringBuffer reXML = new StringBuffer();
        reXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        String structs = BuildStructs.getStructsData(structsList);
        String[] structsRows = structs.split("<row");
        String[] structsColumnName = new String[structsRows.length - 1];
        String[] structsColumnType = new String[structsRows.length - 1];

        for(int i = 1; i < structsRows.length; ++i) {
            structsColumnName[i - 1] = structsRows[i].split("columnname=\"")[1].split("\"")[0];
            structsColumnType[i - 1] = structsRows[i].split("typename=\"")[1].split("\"")[0];
        }

        if (totalnum != 0) {
            reXML.append("<result totalnum=\"").append(totalnum).append("\" />");
        }

        if (colset != null && colset.size() != 0) {
            if (colset.size() == 1) {
                reXML.append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
                reXML.append("<result showtype=\"1\"/>");
            } else if (colset.size() > 1) {
                reXML.append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
                reXML.append("<result showtype=\"2\"/>");
            }

            reXML.append("<resultset name=\"retrieve\">");

            try {
                Iterator it = colset.iterator();

                while(it.hasNext()) {
                    ListOrderedMap bean = (ListOrderedMap)it.next();
                    reXML.append("<row ");

                    for(int i = 0; i < structsColumnName.length; ++i) {
                        reXML.append(structsColumnName[i]).append("=\"");
                        if ("blob".equalsIgnoreCase(structsColumnType[i])) {
                            byte[] photograph = (byte[])bean.get(structsColumnName[i]);
                            String base64Data = null;
                            if (photograph != null) {
                                base64Data = getBase64Data(photograph);
                            }

                            reXML.append(base64Data).append("\" ");
                        } else if ("clob".equalsIgnoreCase(structsColumnType[i])) {
                            String photograph = (String)bean.get(structsColumnName[i]);
                            reXML.append(photograph == null ? "" : photograph.toString()).append("\" ");
                        } else if (bean.get(structsColumnName[i]) != null) {
                            reXML.append(bean.get(structsColumnName[i]).toString()).append("\" ");
                        } else {
                            reXML.append("\" ");
                        }

                        if (i == structsColumnName.length - 1) {
                            reXML.append("/>");
                        }
                    }
                }
            } catch (Exception var14) {
                return BuildSoapXMlError("700", var14.getMessage());
            }

            reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("请求成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
            return reXML.toString();
        } else {
            reXML.append("<soap:Fault>").append("<faultcode>").append("700.000.000").append("</faultcode>").append("<faultstring>").append(msgInfo).append("</faultstring>").append("</soap:Fault>");
            reXML.append("</soap:Body>").append("</soap:Envelope>");
            return reXML.toString();
        }
    }

    public static String readerSoapForExcel(List colset, int totalnum, List<Structs> structsList, String msgInfo) {
        new StringBuffer();
        String structs = BuildStructs.getStructsData(structsList);
        String[] structsRows = structs.split("<row");
        String[] structslabelName = new String[structsRows.length - 1];
        String[] structsColumnName = new String[structsRows.length - 1];

        int i;
        for(i = 1; i < structsRows.length; ++i) {
            structslabelName[i - 1] = structsRows[i].split("label=\"")[1].split("\"")[0];
            structsColumnName[i - 1] = structsRows[i].split("columnname=\"")[1].split("\"")[0];
        }

        i = structsColumnName.length;
        StringBuffer stringbuffer = new StringBuffer("<table cellspacing=\"0\" cellpadding=\"5\" rules=\"all\" border=\"1\"><thead> ");

        for(int k = 0; k < i; ++k) {
            stringbuffer.append("<td align=\"center\">").append(structslabelName[k]).append("</td>");
        }

        stringbuffer.append("</thead><tbody>");
        if (colset != null) {
            Iterator iterator = colset.iterator();

            while(iterator.hasNext()) {
                ListOrderedMap obj2 = (ListOrderedMap)iterator.next();
                stringbuffer.append("<tr>");

                for(int l = 0; l < i; ++l) {
                    Object obj1 = null;
                    obj1 = obj2.get(structsColumnName[l]);
                    if (obj1 == null) {
                        obj1 = "";
                    }

                    stringbuffer.append(formatData(obj1));
                }

                stringbuffer.append("</tr>");
            }
        }

        stringbuffer.append("</tbody></table>");
        return stringbuffer.toString();
    }

    public static String BuildSoapXMlError(String errorCode, String errorMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        reXML.append("<soap:Fault>").append("<faultcode>").append(errorCode + ".000.000").append("</faultcode>").append("<faultstring>").append(errorMsg).append("</faultstring>").append("</soap:Fault>");
        reXML.append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String BuildSoapXMlError(int errorCode, String errorMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>");
        reXML.append("<soap:Fault>").append("<faultcode>").append(errorCode + ".000.000").append("</faultcode>").append("<faultstring>").append(errorMsg).append("</faultstring>").append("</soap:Fault>");
        reXML.append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    /** @deprecated */
    public static String BuildSoapXMlInfo(String infoMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">");
        reXML.append("<result information=\"").append(infoMsg).append("\"/>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("操作成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String BuildSoapXMlInfo_molss(String infoMsg) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.molss.gov.cn/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.molss.gov.cn/\">");
        reXML.append("<result information=\"").append(infoMsg).append("\"/>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("操作成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    public static String BuildSoapXMl(List successRow, List failRow, String[] keySet) {
        StringBuffer reXML = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reXML.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">").append("<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">").append("</out:system></soap:Header>").append("<soap:Body>").append("<out:business xmlns:out=\"http://www.ylzinfo.com/\">").append("<resultset name=\"retrieve\">");
        buildRowInfo(successRow, failRow, keySet, reXML);
        reXML.append("</resultset>").append("</out:business>").append("<soap:Fault>").append("<faultcode>").append("000.000.000").append("</faultcode>").append("<faultstring>").append("操作成功").append("</faultstring>").append("</soap:Fault>").append("</soap:Body>").append("</soap:Envelope>");
        return reXML.toString();
    }

    private static void buildRowInfo(List successRow, List failRow, String[] keySet, StringBuffer sbXML) {
        HashMap map;
        Iterator it;
        String key;
        int var7;
        int var8;
        String[] var9;
        if (successRow != null) {
            map = null;
            it = successRow.iterator();

            while(it.hasNext()) {
                map = (HashMap)it.next();
                sbXML.append("<row isSuccessful=\"true\" ");
                var9 = keySet;
                var8 = keySet.length;

                for(var7 = 0; var7 < var8; ++var7) {
                    key = var9[var7];
                    if (map.containsKey("produceExeResult")) {
                        sbXML.append("produceExeResult=\"").append((String)map.get("produceExeResult"));
                        sbXML.append("\"  ");
                    }

                    sbXML.append(key).append("=\"").append((String)map.get(key));
                    sbXML.append("\"  ");
                }

                sbXML.append("/>");
            }
        }

        if (failRow != null) {
            map = null;

            for(it = failRow.iterator(); it.hasNext(); sbXML.append("/>")) {
                map = (HashMap)it.next();
                sbXML.append("<row isSuccessful=\"false\" ");
                if (map.containsKey("failReason")) {
                    sbXML.append("failReason=\"");
                    var9 = keySet;
                    var8 = keySet.length;

                    for(var7 = 0; var7 < var8; ++var7) {
                        key = var9[var7];
                        sbXML.append(key).append("=").append((String)map.get(key));
                    }

                    sbXML.append((String)map.get("failReason"));
                    sbXML.append("\"  ");
                }
            }
        }

    }

    private static Object formatData(Object obj) {
        obj = obj != null ? obj : " ";
        if (obj.getClass().getName().equals("java.sql.Timestamp")) {
            Timestamp timestamp = (Timestamp)obj;
            obj = new Date(timestamp.getTime());
        }
        if (isNumeric(obj)) {
            obj = "<td style=\"vnd.ms-excel.numberformat:@\" align=\"center\">" + obj + "</td>";
        } else {
            obj = "<td align=\"center\">" + obj + "</td>";
        }

        return obj;
    }

    private static boolean isNumeric(Object obj) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (obj == null) {
            return false;
        } else {
            Matcher isNum = pattern.matcher(obj.toString());
            if (!isNum.matches()) {
                return false;
            } else {
                return obj.toString().length() > 6;
            }
        }
    }

    public static String getBase64Data(byte[] buff) {
        BASE64Encoder encoder = new BASE64Encoder();
        String data = encoder.encode(buff);
        return data;
    }
}
