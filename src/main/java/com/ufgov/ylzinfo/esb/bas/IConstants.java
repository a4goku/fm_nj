//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.esb.bas;

public class IConstants {
    public static String ENCRYPT_KEY = "2009200920102010";
    public static final String SIGN_PASSWORD = "signPassword";
    public static final String USER_CRT_FILE = "userCrtFile";
    public static final String SECURITY_POLICY_SIGN = "000";
    public static final String SECURITY_POLICY_ENCRYPT = "001";
    public static final int serverStopError = 100;
    public static final int esbReturnError = 200;
    public static final int esbConnTimeoutError = 300;
    public static final int configFileError = 400;
    public static final int esbRequestError = 500;
    public static final String ENVELOP_END = "</soap:Envelope>";
    public static final String BODY_END = "</soap:Body>";
    public static final String BODY_START = "<soap:Body>";
    public static final String BUSINESS_START = "<out:business xmlns:out=\"http://www.ylzinfo.com/\">";
    public static final String BUSINESS_END = "</out:business>";
    public static final String HEAD_END = "</out:system></soap:Header>";
    public static final String HEAD_START = "<soap:Header><out:system xmlns:out=\"http://www.ylzinfo.com/\">";
    public static final String ENVELOP_START = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">";
    public static final String XML_PI = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
    public static final String XML_PI_UTF_8 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    public static final String Single_Row = "<result showtype=\"1\" />";
    public static final String Show_Type_Result = "</result>";
    public static final String Multilateral_Row = "<result showtype=\"2\" />";
    public static final String Structs_START = "<resultset name=\"structs\">";
    public static final String Structs_END = "</resultset>";
    public static final String Retrieve_START = "<resultset name=\"retrieve\">";
    public static final String Retrieve_END = "</resultset>";
    public static final String Message_START = "<resultset name=\"retrieve\" information=\"";
    public static final String Message_R = "\" >";
    public static final String Message_End = "</resultset>";
    public static final String Single = "1";
    public static final String Multilateral = "2";
    public static final String FAULT_START = "<soap:Fault>";
    public static final String FAULT_END = "</soap:Fault>";
    public static final String FAULT_CODE_START = "<faultcode>";
    public static final String FAULT_CODE_END = "</faultcode>";
    public static final String FAULT_STR_START = "<faultstring>";
    public static final String FAULT_STR_END = "</faultstring>";
    public static final String ERROR_MSG_START = "<error msg=\"";
    public static final String ERROR_MSG_END = "\" />";

    public IConstants() {
    }
}
