//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.esb.client;

import com.ylzinfo.esb.bas.EsbException;
import com.ylzinfo.esb.bas.EsbResponse;
import com.ylzinfo.esb.bas.MessageEnvelop;
import com.ylzinfo.esb.bas.ServiceEncryptUtil;
import com.ylzinfo.esb.util.GZipUtil;
import com.ylzinfo.esb.util.HttpClientUtil;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class XMLRequest {
    private String svid;
    private boolean isGzip;
    private String[] param;
    private String[] paramValue;
    private String router;
    private String esbUrl;
    private String[] esbUserPwd;
    private ArrayList paramList;
    private List rowInfo;
    public static String FDCJM = "101";
    public static String DCJM = "001";
    protected String encryptKey;
    private String encryptType = "DCJM";
    private final String ls_key = "ylzesbService";
    private String ver;
    private String sys;
    private String org;
    private String reserve1;
    private String reserve2;

    public XMLRequest() {
    }

    public String getVer() {
        return this.ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSys() {
        return this.sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getReserve1() {
        return this.reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return this.reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getSvid() {
        return this.svid;
    }

    public void setSvid(String svid) {
        this.svid = svid;
    }

    public String[] getParam() {
        return this.param;
    }

    public void setParam(String[] param) {
        this.param = param;
    }

    public String[] getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String[] paramValue) {
        this.paramValue = paramValue;
    }

    public String getEsbUrl() {
        return this.esbUrl;
    }

    public void setEsbUrl(String esbUrl) {
        this.esbUrl = esbUrl;
    }

    public String[] getEsbUserPwd() {
        return this.esbUserPwd;
    }

    public void setEsbUserPwd(String[] esbUserPwd) {
        this.esbUserPwd = esbUserPwd;
    }

    public boolean isGzip() {
        return this.isGzip;
    }

    public void setGzip(boolean isGzip) {
        this.isGzip = isGzip;
    }

    public ArrayList getParamList() {
        return this.paramList;
    }

    public void setParamList(ArrayList paramList) {
        this.paramList = paramList;
    }

    public List getRowInfo() {
        return this.rowInfo;
    }

    public void setRowInfo(List rowInfo) {
        this.rowInfo = rowInfo;
    }

    public String getRouter() {
        return this.router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public String getEncryptKey() {
        return this.encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public EsbResponse postXmlRequest() {
        EsbResponse esbResponse = new EsbResponse();

        MessageEnvelop msgEnvelop;
        try {
            this.checkRequest();
            Object soapMsg = this.genRequestMessage(this.esbUserPwd[0], this.esbUserPwd[1]);
            List list = ServiceEncryptUtil.getSoapHeadAndBody(soapMsg.toString());
            String xmlhead = list.get(0).toString();
            List listHead = ServiceEncryptUtil.getSoapHeadParam(xmlhead);
            soapMsg = listHead.get(0) + ServiceEncryptUtil.encryptByAes(listHead.get(1).toString(), "ylzesbService") + listHead.get(2) + ServiceEncryptUtil.encryptByAes(list.get(1).toString(), "ylzesbService") + list.get(2).toString();
            String responseData = this.sendSoapMessage(soapMsg.toString());
            esbResponse.setResponseData(responseData);
        } catch (EsbException var8) {
            var8.printStackTrace();
            msgEnvelop = new MessageEnvelop();
            esbResponse.setResponseData(msgEnvelop.createMsgFault(var8.getMessage(), var8.getErrorCode()));
            var8.printStackTrace();
        } catch (Exception var9) {
            var9.printStackTrace();
            msgEnvelop = new MessageEnvelop();
            esbResponse.setResponseData(msgEnvelop.createMsgFault("发送SOAP信息失败,原因如下:" + var9.getMessage() + "访问被拒绝!"));
            var9.printStackTrace();
        }

        return esbResponse;
    }

    public String getPostXmlRequest() {
        try {
            this.checkRequest();
            Object soapMsg = this.genRequestMessage(this.esbUserPwd[0], this.esbUserPwd[1]);
            List list = ServiceEncryptUtil.getSoapHeadAndBody(soapMsg.toString());
            String xmlhead = list.get(0).toString();
            List listHead = ServiceEncryptUtil.getSoapHeadParam(xmlhead);
            soapMsg = listHead.get(0) + ServiceEncryptUtil.encryptByAes(listHead.get(1).toString(), "ylzesbService") + listHead.get(2) + ServiceEncryptUtil.encryptByAes(list.get(1).toString(), "ylzesbService") + list.get(2).toString();
            String esbResponse = soapMsg.toString();
            return esbResponse;
        } catch (Exception var6) {
            var6.printStackTrace();
            var6.printStackTrace();
            return null;
        }
    }

    public String getPostMsgRequest() {
        try {
            this.checkRequest();
            Object soapMsg = this.genRequestMessage(this.esbUserPwd[0], this.esbUserPwd[1]);
            String esbResponse = soapMsg.toString();
            return esbResponse;
        } catch (Exception var3) {
            var3.printStackTrace();
            var3.printStackTrace();
            return null;
        }
    }

    public EsbResponse postXmlRequest(String ls_key) {
        EsbResponse esbResponse = new EsbResponse();

        MessageEnvelop msgEnvelop;
        try {
            this.checkRequest();
            Object soapMsg = this.genRequestMessage(this.esbUserPwd[0], this.esbUserPwd[1]);
            List list = ServiceEncryptUtil.getSoapHeadAndBody(soapMsg.toString());
            soapMsg = list.get(0) + ServiceEncryptUtil.encryptByAes(list.get(1).toString(), ls_key) + list.get(2).toString();
            String responseData = this.sendSoapMessage(soapMsg);
            if (responseData.toLowerCase().indexOf("<soap:body>") < 0) {
                if (this.encryptType.equals(FDCJM)) {
                    ls_key = ClientKeyHelper.getEncryptFromSoap(responseData, this);
                }

                list = ServiceEncryptUtil.getSoapHeadAndBody(responseData);
                responseData = list.get(0) + ServiceEncryptUtil.decryptByAes(list.get(1).toString(), ls_key) + list.get(2).toString();
            }

            esbResponse.setResponseData(responseData);
        } catch (EsbException var7) {
            var7.printStackTrace();
            msgEnvelop = new MessageEnvelop();
            esbResponse.setResponseData(msgEnvelop.createMsgFault(var7.getMessage(), var7.getErrorCode()));
            var7.printStackTrace();
        } catch (Exception var8) {
            var8.printStackTrace();
            msgEnvelop = new MessageEnvelop();
            esbResponse.setResponseData(msgEnvelop.createMsgFault("发送SOAP信息失败,原因如下:" + var8.getMessage() + "访问被拒绝!"));
            var8.printStackTrace();
        }

        return esbResponse;
    }

    protected void checkRequest() throws EsbException {
        if (this.svid == null) {
            throw new EsbException(500, "HttpRequest 服务ID不能为空");
        } else if (this.ver == null) {
            throw new EsbException(500, "服务版本号不能为空");
        } else if (this.sys == null) {
            throw new EsbException(500, "系统名称(或编号)不能为空");
        } else if (this.param != null & this.paramValue != null && this.param.length != this.paramValue.length) {
            throw new EsbException(500, "HttpRequest 参数名、参数值没有匹配.");
        } else if (this.esbUrl == null) {
            throw new EsbException(500, "HttpRequest ESB服务器URL不能为空.");
        } else if (this.esbUserPwd == null) {
            throw new EsbException(500, "HttpRequest ESB访问用户、密码不能为空.");
        }
    }

    public Object genRequestMessage(String alias, String password) throws EsbException {
        if (this.esbUserPwd != null) {
            alias = this.esbUserPwd[0];
            password = this.esbUserPwd[1];
        }

        StringBuffer sbXML = new StringBuffer();
        sbXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><soap:Header><in:system xmlns:in=\"http://www.ylzinfo.com/\">");
        sbXML.append("<para ver=\"");
        sbXML.append(this.ver);
        sbXML.append("\"/>");
        sbXML.append("<para sys=\"");
        sbXML.append(this.sys);
        sbXML.append("\"/>");
        sbXML.append("<para usr=\"");
        sbXML.append(alias);
        sbXML.append("\"/><para pwd=\"");
        sbXML.append(password);
        sbXML.append("\"/><para sid=\"");
        sbXML.append(this.svid);
        if (this.encryptKey != null) {
            sbXML.append("\"/><para encryptkey=\"");
            sbXML.append(this.encryptKey);
        }

        sbXML.append("\"/>");
        if (this.router != null) {
            sbXML.append("<para router=\"");
            sbXML.append(this.router);
            sbXML.append("\"/>");
        }

        if (this.org != null) {
            sbXML.append("<para org=\"");
            sbXML.append(this.org);
            sbXML.append("\"/>");
        }

        if (this.reserve1 != null) {
            sbXML.append("<para reserve1=\"");
            sbXML.append(this.reserve1);
            sbXML.append("\"/>");
        }

        if (this.reserve2 != null) {
            sbXML.append("<para reserve2=\"");
            sbXML.append(this.reserve2);
            sbXML.append("\"/>");
        }

        sbXML.append("</in:system></soap:Header>");
        sbXML.append("<soap:Body><in:business xmlns:in=\"http://www.ylzinfo.com/\">");
        if (this.param != null) {
            this.getParamValue(this.param, this.paramValue, sbXML);
        }

        if (this.paramList != null) {
            this.buildParamList(this.paramList, sbXML);
        }

        if (this.rowInfo != null) {
            this.buildRowInfo(this.rowInfo, sbXML);
        }

        sbXML.append("</in:business></soap:Body></soap:Envelope>");
        return sbXML.toString();
    }

    private void appendParamList(ArrayList<ArrayList> paramList, StringBuffer sbXML) {
        String key = null;
        Iterator localIterator1 = paramList.iterator();

        while(localIterator1.hasNext()) {
            ArrayList paramArr = (ArrayList)localIterator1.next();
            key = (String)paramArr.get(0);
            sbXML.append("<paralist name=\"").append(key).append("\">");

            for(int i = 1; i < paramArr.size(); ++i) {
                HashMap map = (HashMap)paramArr.get(i);
                sbXML.append("<row ");

                for(Iterator keySet = map.keySet().iterator(); keySet.hasNext(); sbXML.append("\" ")) {
                    key = (String)keySet.next();
                    sbXML.append(key).append("=\"");
                    if (map.get(key) != null) {
                        sbXML.append((String)map.get(key));
                    }
                }

                sbXML.append("/>");
            }

            sbXML.append("</paralist>");
        }

    }

    private void buildParamList(ArrayList<ArrayList> paramList, StringBuffer sbXML) {
        String key = null;
        Iterator localIterator1 = paramList.iterator();

        while(true) {
            while(localIterator1.hasNext()) {
                Object obj = localIterator1.next();
                if (obj instanceof String) {
                    key = (String)obj;
                    sbXML.append("<paralist name=\"").append(key).append("\">");
                } else {
                    ArrayList paramArr = (ArrayList)obj;

                    for(int i = 0; i < paramArr.size(); ++i) {
                        HashMap map = (HashMap)paramArr.get(i);
                        sbXML.append("<row ");

                        for(Iterator keySet = map.keySet().iterator(); keySet.hasNext(); sbXML.append("\" ")) {
                            key = (String)keySet.next();
                            sbXML.append(key).append("=\"");
                            if (map.get(key) != null) {
                                sbXML.append((String)map.get(key));
                            }
                        }

                        sbXML.append("/>");
                    }

                    sbXML.append("</paralist>");
                }
            }

            return;
        }
    }

    private void buildRowInfo(List rowInfo, StringBuffer sbXML) {
        if (rowInfo != null) {
            HashMap map = null;
            String key = null;
            Iterator it = rowInfo.iterator();

            while(it.hasNext()) {
                map = (HashMap)it.next();
                sbXML.append("<row ");

                for(Iterator keySet = map.keySet().iterator(); keySet.hasNext(); sbXML.append("\"  ")) {
                    key = (String)keySet.next();
                    sbXML.append(key).append("=\"");
                    if (map.get(key) != null) {
                        sbXML.append(map.get(key));
                    }
                }

                sbXML.append("/>");
            }
        }

    }

    private void getParamValue(String[] param, String[] paramValue, StringBuffer pvXml) {
        if (param != null && paramValue != null) {
            for(int i = 0; i < param.length; ++i) {
                if (param[i] != null && paramValue[i] != null) {
                    pvXml.append("<para ").append(param[i]).append("=\"");
                    this.setFilter(paramValue[i], pvXml);
                    pvXml.append("\"/>");
                }
            }
        }

    }

    private void setFilter(String value, StringBuffer sb) {
        for(int i = 0; i < value.length(); ++i) {
            switch(value.charAt(i)) {
            case '"':
                sb.append("&quot;");
                break;
            case '&':
                sb.append("&amp;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            default:
                sb.append(value.charAt(i));
            }
        }

    }

    private String sendSoapMessage(Object soapMsg) throws Exception {
        PostMethod method = null;
        HttpClient httpClient = null;
        URL targetURL = new URL(this.esbUrl);
        int port = targetURL.getPort();
        String protocol = targetURL.getProtocol();
        if (port == -1) {
            if ("http".equals(protocol)) {
                port = 80;
            } else if ("https".equals(protocol)) {
                port = 443;
            }
        }

        String var14;
        try {
            method = new PostMethod();
            byte[] content = soapMsg.toString().getBytes("UTF-8");
            if (this.isGzip) {
                method.setRequestHeader("Accept-Encoding", "gzip");
                ByteArrayInputStream bis = new ByteArrayInputStream(content);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                GZIPOutputStream gos = new GZIPOutputStream(bos);

                for(int c = bis.read(); c != -1; c = bis.read()) {
                    gos.write(c);
                }

                gos.flush();
                gos.close();
                content = bos.toByteArray();
            }

            RequestEntity reqEntity = new ByteArrayRequestEntity(content, "text/xml; charset=UTF-8");
            method.setRequestEntity(reqEntity);
            method.setRequestHeader("Connection", "close");
            method.setPath(targetURL.getPath());
            method.setQueryString(targetURL.getQuery());
            method.setContentChunked(true);
            HostConfiguration config = new HostConfiguration();
            config.setHost(targetURL.getHost(), port, targetURL.getProtocol());
            httpClient = HttpClientUtil.getHttpClient();
            httpClient.executeMethod(config, method, (HttpState)null);
            if (method.getResponseHeader("Content-Encoding") == null) {
                var14 = method.getResponseBodyAsString();
                return var14;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZipUtil.decompress(method.getResponseBodyAsStream(), baos);
            baos.flush();
            baos.close();
            var14 = baos.toString("UTF-8");
        } finally {
            if (method != null) {
                method.releaseConnection();
            }

        }

        return var14;
    }

    public static void main(String[] args) {
        List rowInfo = new ArrayList();
        List rowInfo1 = new ArrayList();
        ArrayList paralist = new ArrayList();
        Map<String, String> map1 = new HashMap();
        map1.put("a", "qqq");
        map1.put("b", "sss");
        Map<String, String> map2 = new HashMap();
        map2.put("a", "004");
        map2.put("b", "fdsfds");
        rowInfo.add(map1);
        rowInfo.add(map2);
        rowInfo1.add(map1);
        rowInfo1.add(map2);
        paralist.add("list111");
        paralist.add(rowInfo);
        paralist.add("list2");
        paralist.add(rowInfo1);
        StringBuffer sb = new StringBuffer();
        XMLRequest xmlRequst = new XMLRequest();
        xmlRequst.buildParamList(paralist, sb);
        System.out.println(">>>" + sb.toString());
    }
}
