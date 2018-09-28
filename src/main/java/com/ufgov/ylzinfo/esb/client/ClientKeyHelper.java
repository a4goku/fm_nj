//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.esb.client;

import com.ylzinfo.esb.bas.ServiceEncryptUtil;

import java.util.List;
import java.util.Map;

public class ClientKeyHelper {
    public ClientKeyHelper() {
    }

    public static String initKey(XMLRequest req) throws Exception {
        Map map = ServiceEncryptUtil.getKeyMessageFromXmlStr(req.getSvid());
        Object ls_algorithm = map.get("algorithm");
        if (ls_algorithm == null) {
            throw new Exception("没有服务(" + req.getSvid() + ")相关的加密配置");
        } else {
            String ls_type = ls_algorithm.toString();
            req.setEncryptType(ls_type);
            if (ls_type.equals(XMLRequest.FDCJM)) {
                List list = ServiceEncryptUtil.generateKey("AES");
                String ls_key = list.get(0).toString();
                String ls_temp = ServiceEncryptUtil.encryptByRSA(ls_key, map.get("publickeyserver").toString());
                req.setEncryptKey(ls_temp);
                return ls_key;
            } else if (ls_type.equals(XMLRequest.DCJM)) {
                String ls_key = map.get("encryptkey").toString();
                return ls_key;
            } else {
                return null;
            }
        }
    }

    public static String getEncryptFromSoap(String ls_soapxml, XMLRequest req) throws Exception {
        List list = ServiceEncryptUtil.getSoapHeadAndBody(ls_soapxml);
        String ls_head = list.get(0).toString();
        String ls_keyWord = "encryptkey=\"";
        int ii_begin = ls_head.indexOf(ls_keyWord) + ls_keyWord.length();
        int ii_end = ls_head.indexOf("\"", ii_begin);
        String ls_source = ls_head.substring(ii_begin, ii_end);
        Map map = ServiceEncryptUtil.getKeyMessageFromXmlStr(req.getSvid());
        String ls_decryptKey = ServiceEncryptUtil.decryptByRSA(ls_source, map.get("privatekeyclient").toString());
        return ls_decryptKey;
    }
}
