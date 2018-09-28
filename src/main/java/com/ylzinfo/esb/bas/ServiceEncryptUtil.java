//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.esb.bas;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ServiceEncryptUtil {
    private static char[] HEXCHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public ServiceEncryptUtil() {
    }

    public static String encryptByAes(String ls_source, String ls_key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        ls_source = URLEncoder.encode(ls_source.toString(), "UTF-8");
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(ls_key.getBytes());
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, key);
        byte[] result = cipher.doFinal(ls_source.getBytes());
        String ls_result = new String(Base64.encodeBase64(result));
        return ls_result;
    }

    public static String decryptByAes(String ls_source, String ls_key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(ls_key.getBytes());
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, key);
        byte[] result = cipher.doFinal(Base64.decodeBase64(ls_source.getBytes()));
        String ls_result = URLDecoder.decode(new String(result), "UTF-8");
        return ls_result;
    }

    public static String encryptByRSA(String ls_source, String ls_key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey)getKeyObjFromKeyStr(ls_key, 1);
        cipher.init(1, publicKey);
        byte[] results = cipher.doFinal(ls_source.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(results), "UTF-8");
    }

    public static String decryptByRSA(String ls_source, String ls_key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey)getKeyObjFromKeyStr(ls_key, 0);
        cipher.init(2, privateKey);
        byte[] results = cipher.doFinal(Base64.decodeBase64(ls_source.getBytes()));
        return new String(results);
    }

    public static Key getKeyObjFromKeyStr(String ls_keyStr, int type) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        if (type == 0) {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(toBytes(ls_keyStr));
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            return privateKey;
        } else {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(toBytes(ls_keyStr));
            PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
            return publicKey;
        }
    }

    public static final byte[] toBytes(String s) {
        byte[] bytes = new byte[s.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

    public static List getSoapHeadAndBody(String ls_soapxml) {
        List returnList = new ArrayList();
        int ii_begin = ls_soapxml.toLowerCase().indexOf("<soap:body>");
        int ii_end;
        if (ii_begin > 0) {
            ii_end = ls_soapxml.toLowerCase().indexOf("</soap:body>") + "</soap:body>".length();
            returnList.add(ls_soapxml.substring(0, ii_begin));
            returnList.add(ls_soapxml.substring(ii_begin, ii_end));
            returnList.add(ls_soapxml.substring(ii_end, ls_soapxml.length()));
        } else {
            ii_begin = ls_soapxml.toLowerCase().indexOf("</soap:header>") + "</soap:header>".length();
            ii_end = ls_soapxml.toLowerCase().indexOf("</soap:envelope>");
            returnList.add(ls_soapxml.substring(0, ii_begin));
            returnList.add(ls_soapxml.substring(ii_begin, ii_end));
            returnList.add(ls_soapxml.substring(ii_end, ls_soapxml.length()));
        }

        return returnList;
    }

    public static List getSoapHeadParam(String ls_soapxml) {
        List returnList = new ArrayList();
        int ii_begin = ls_soapxml.toLowerCase().indexOf("<soap:header>") + "<soap:header>".length();
        if (ii_begin > 0) {
            int heard_end = ls_soapxml.toLowerCase().indexOf("</soap:header>");
            int ii_end = ls_soapxml.toLowerCase().indexOf("</soap:header>") + "</soap:header>".length();
            returnList.add(ls_soapxml.substring(0, ii_begin));
            returnList.add(ls_soapxml.substring(ii_begin, heard_end));
            returnList.add(ls_soapxml.substring(heard_end, ii_end));
        }

        return returnList;
    }

    public static List generateKey(String ls_type) throws NoSuchAlgorithmException {
        List listReturn = new ArrayList();
        if (ls_type.equals("RSA")) {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PublicKey pubkey = keyPair.getPublic();
            PrivateKey prikey = keyPair.getPrivate();
            listReturn.add(toHexString(pubkey.getEncoded()));
            listReturn.add(toHexString(prikey.getEncoded()));
        }

        if (ls_type.equals("AES")) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            listReturn.add(Base64.encodeBase64(secretKey.getEncoded()));
        }

        return listReturn;
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);

        for(int i = 0; i < b.length; ++i) {
            sb.append(HEXCHAR[(b[i] & 240) >>> 4]);
            sb.append(HEXCHAR[b[i] & 15]);
        }

        return sb.toString();
    }

    public static String createXmlStrFromKeyMessage(List list) {
        StringBuffer result = new StringBuffer();
        result.append("<keys>");

        for(int i = 0; i < list.size(); ++i) {
            result.append("<akey serviceid=\"");
            Map aKey = (Map)list.get(i);
            result.append(aKey.get("serviceid"));
            result.append("\">");
            result.append("<algorithm>");
            result.append(aKey.get("algorithm"));
            result.append("</algorithm>");
            result.append("<encryptkey>");
            result.append(aKey.get("encryptkey"));
            result.append("</encryptkey>");
            result.append("<publickeyserver>");
            result.append(aKey.get("publickeyserver"));
            result.append("</publickeyserver>");
            result.append("<privatekeyclient>");
            result.append(aKey.get("privatekeyclient"));
            result.append("</privatekeyclient>");
            result.append("</akey>");
        }

        result.append("</keys>");
        return result.toString();
    }

    public static Map getKeyMessageFromXmlStr(String ls_serviceid) throws ParserConfigurationException, SAXException, IOException {
        TreeMap returnMap = new TreeMap();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document doc = dbBuilder.parse(ServiceEncryptUtil.class.getResourceAsStream("/keyStore.xml"));
        NodeList nList = doc.getElementsByTagName("akey");

        for(int i = 0; i < nList.getLength(); ++i) {
            Element node = (Element)nList.item(i);
            if (node.getAttribute("serviceid").equals(ls_serviceid)) {
                returnMap.put("algorithm", node.getElementsByTagName("algorithm").item(0).getFirstChild().getNodeValue());
                returnMap.put("encryptkey", node.getElementsByTagName("encryptkey").item(0).getFirstChild().getNodeValue());
                returnMap.put("publickeyserver", node.getElementsByTagName("publickeyserver").item(0).getFirstChild().getNodeValue());
                returnMap.put("privatekeyclient", node.getElementsByTagName("privatekeyclient").item(0).getFirstChild().getNodeValue());
                return returnMap;
            }
        }

        return returnMap;
    }

    public static String getServiceEncryptSoap(String ls_soap, String clientPublicKeyStr) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        List listTemp = generateKey("AES");
        String ls_key = listTemp.get(0).toString();
        String ls_encryptKey = encryptByRSA(ls_key, clientPublicKeyStr);
        List list = getSoapHeadAndBody(ls_soap);
        String ls_encryptBody = encryptByAes(list.get(1).toString(), ls_key);
        String ls_head = list.get(0).toString();
        int ii_begin = ls_soap.indexOf("</out:system>");
        if (ii_begin < 0) {
            ii_begin = ls_soap.indexOf("</soap:Header>");
        }

        StringBuffer ls_resultBuf = new StringBuffer();
        ls_resultBuf.append(ls_head.substring(0, ii_begin));
        ls_resultBuf.append("<para encryptkey=\"");
        ls_resultBuf.append(ls_encryptKey);
        ls_resultBuf.append("\"/>");
        ls_resultBuf.append(ls_head.substring(ii_begin, ls_head.length()));
        ls_resultBuf.append(ls_encryptBody);
        ls_resultBuf.append(list.get(2).toString());
        return ls_resultBuf.toString();
    }

    public static void main(String[] args) {
    }
}
