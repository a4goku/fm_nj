//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.esb.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class StringUtil {
    private static final char[] AMP_ENCODE = "&amp;".toCharArray();
    private static final int DUMP_HEX_CHAR_COUNT = 75;
    private static final char[] GT_ENCODE = "&gt;".toCharArray();
    private static final char[] LT_ENCODE = "&lt;".toCharArray();
    private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
    private static final char[] base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static int[] base64Codes = new int[256];
    private static MessageDigest digest = null;
    private static final int fillchar = 61;
    private static int[] hexCharCodes = new int[256];
    private static final char[] lowerHexChar = "0123456789abcdef".toCharArray();
    private static SimpleDateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static Random randGen = new Random();
    private static final char[] upcaseHexChar = "0123456789ABCDEF".toCharArray();
    private static final char[] zeroArray = "0000000000000000".toCharArray();

    static {
        int i;
        for(i = 0; i < 256; ++i) {
            byte tmp128_127 = -1;
            base64Codes[i] = tmp128_127;
            hexCharCodes[i] = tmp128_127;
        }

        for(i = 0; i < base64Chars.length; ++i) {
            base64Codes[base64Chars[i]] = (byte)i;
        }

        for(i = 0; i < upcaseHexChar.length; ++i) {
            hexCharCodes[upcaseHexChar[i]] = (byte)i;
        }

        for(i = 0; i < lowerHexChar.length; ++i) {
            hexCharCodes[lowerHexChar[i]] = (byte)i;
        }

    }

    public StringUtil() {
    }

    public static int compareString(String s, String s2, String encoding) {
        if (s2 == null) {
            return s != null ? 1 : 0;
        } else if (s == null) {
            return -1;
        } else {
            try {
                byte[] v1 = s.getBytes(encoding);
                byte[] v2 = s2.getBytes(encoding);
                int i = v1.length;
                int j = v2.length;
                int n = Math.min(i, j);
                int k = 0;

                for(int lim = n; k < lim; ++k) {
                    int c1 = v1[k] & 255;
                    int c2 = v2[k] & 255;
                    if (c1 != c2) {
                        return c1 - c2;
                    }
                }

                return i - j;
            } catch (Exception var12) {
                return 0;
            }
        }
    }

    public static byte[] decodeBase64(String data, int offset) {
        int len = data.length();
        byte[] result = new byte[len * 3 / 4];
        int pos = 0;
        if (offset >= len) {
            return null;
        } else {
            for(int i = offset; i < len; ++i) {
                int c = base64Codes[data.charAt(i)];
                if (c != -1) {
                    ++i;
                    int c1 = base64Codes[data.charAt(i)];
                    c = c << 2 | c1 >> 4 & 3;
                    result[pos++] = (byte)c;
                    ++i;
                    if (i < len) {
                        c = data.charAt(i);
                        if ('=' == c) {
                            break;
                        }

                        c = base64Codes[data.charAt(i)];
                        c1 = c1 << 4 & 240 | c >> 2 & 15;
                        result[pos++] = (byte)c1;
                    }

                    ++i;
                    if (i < len) {
                        c1 = data.charAt(i);
                        if ('=' == c1) {
                            break;
                        }

                        c1 = base64Codes[data.charAt(i)];
                        c = c << 6 & 192 | c1;
                        result[pos++] = (byte)c;
                    }
                }
            }

            if (result.length != pos) {
                byte[] result2 = new byte[pos];
                System.arraycopy(result, 0, result2, 0, pos);
                result = result2;
            }

            return result;
        }
    }

    public static byte[] decodeBase64(String data) {
        return decodeBase64(data, 0);
    }

    public static final byte[] decodeHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        int byteCount = 0;
        int length = hex.length();

        for(int i = 0; i < length; i += 2) {
            byte newByte = 0;
            newByte = (byte)(newByte | hexCharCodes[hex.charAt(i)]);
            newByte = (byte)(newByte << 4);
            newByte = (byte)(newByte | hexCharCodes[hex.charAt(i + 1)]);
            bytes[byteCount] = newByte;
            ++byteCount;
        }

        return bytes;
    }

    public static String decodeUrlString(String str) {
        String strret = null;
        if (str == null) {
            return str;
        } else {
            try {
                strret = URLDecoder.decode(str, "GBK");
                return strret;
            } catch (Exception var3) {
                var3.printStackTrace(System.err);
                return null;
            }
        }
    }

    public static String encode(String text) {
        StringBuffer n = new StringBuffer();

        for(int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            switch(c) {
            case '"':
                n.append("&quot;");
                break;
            case '&':
                n.append("&amp;");
                break;
            case '\'':
                n.append("&apos;");
                break;
            case '<':
                n.append("&lt;");
                break;
            case '>':
                n.append("&gt;");
                break;
            default:
                n.append(c);
            }
        }

        return new String(n);
    }

    public static final String dumpHex(byte[] bytes) {
        int linecount = (bytes.length + 15) / 16;
        char[] buf = new char[linecount * 75];
        byte[] bs = new byte[16];
        int bytepos = 0;

        for(int i = 0; i < linecount; ++i) {
            int addr = i * 16;
            int bufpos = i * 75;
            buf[bufpos++] = upcaseHexChar[addr >> 12 & 15];
            buf[bufpos++] = upcaseHexChar[addr >> 8 & 15];
            buf[bufpos++] = upcaseHexChar[addr >> 4 & 15];
            buf[bufpos++] = upcaseHexChar[addr & 15];
            buf[bufpos++] = ' ';
            buf[bufpos++] = ' ';

            int endLine;
            for(int j = 0; j < 16; ++bytepos) {
                if (bytepos < bytes.length) {
                    endLine = bytes[bytepos] & 255;
                    bs[j] = bytes[bytepos];
                    buf[bufpos++] = upcaseHexChar[endLine >> 4];
                    buf[bufpos++] = upcaseHexChar[endLine & 15];
                    if (j == 7) {
                        buf[bufpos++] = '-';
                    } else {
                        buf[bufpos++] = ' ';
                    }
                } else {
                    buf[bufpos++] = ' ';
                    buf[bufpos++] = ' ';
                    buf[bufpos++] = ' ';
                    bs[j] = 32;
                }

                ++j;
            }

            buf[bufpos++] = ' ';
            buf[bufpos++] = ' ';
            char[] chs = (new String(bs)).toCharArray();

            for(endLine = 0; endLine < chs.length; ++endLine) {
                char ch = chs[endLine];
                if (ch > 0 && ch < ' ') {
                    buf[bufpos++] = '.';
                } else {
                    buf[bufpos++] = ch;
                }
            }

            for(endLine = (i + 1) * 75 - 2; bufpos < endLine; ++bufpos) {
                buf[bufpos] = ' ';
            }

            buf[bufpos++] = '\r';
            buf[bufpos++] = '\n';
        }

        return new String(buf);
    }

    public static String encodeBase64(byte[] data) {
        return encodeBase64(data, false);
    }

    public static String encodeBase64(byte[] data, boolean lineBreak) {
        int len = data.length;
        char[] buf = new char[(len / 3 + 1) * 4 + len / 57 + 1];
        int pos = 0;

        for(int i = 0; i < len; ++i) {
            int c = data[i] >> 2 & 63;
            buf[pos++] = base64Chars[c];
            c = data[i] << 4 & 63;
            ++i;
            if (i < len) {
                c |= data[i] >> 4 & 15;
            }

            buf[pos++] = base64Chars[c];
            if (i < len) {
                c = data[i] << 2 & 63;
                ++i;
                if (i < len) {
                    c |= data[i] >> 6 & 3;
                }

                buf[pos++] = base64Chars[c];
            } else {
                ++i;
                buf[pos++] = '=';
            }

            if (i < len) {
                c = data[i] & 63;
                buf[pos++] = base64Chars[c];
            } else {
                buf[pos++] = '=';
            }

            if (lineBreak && i % 57 == 56) {
                buf[pos++] = '\n';
            }
        }

        return new String(buf, 0, pos);
    }

    public static String encodeBase64(String data) {
        return encodeBase64(data.getBytes());
    }

    public static final String encodeHex(byte[] bytes, boolean isUpper) {
        char[] hexChar;
        if (isUpper) {
            hexChar = upcaseHexChar;
        } else {
            hexChar = lowerHexChar;
        }

        char[] buf = new char[bytes.length * 2];

        for(int i = 0; i < bytes.length; ++i) {
            int code = bytes[i] & 255;
            buf[2 * i] = hexChar[code >> 4];
            buf[2 * i + 1] = hexChar[code & 15];
        }

        return new String(buf);
    }

    public static final String encodeHex(byte[] bytes) {
        return encodeHex(bytes, true);
    }

    public static String encodeUrlString(String str) {
        String strret = null;
        if (str == null) {
            return str;
        } else {
            try {
                strret = URLEncoder.encode(str, "GBK");
                return strret;
            } catch (Exception var3) {
                var3.printStackTrace(System.err);
                return null;
            }
        }
    }

    public static synchronized String formatChineseString(String text) {
        if (text == null) {
            return text;
        } else {
            String ret = replace(text, "０", "0");
            ret = replace(ret, "１", "1");
            ret = replace(ret, "２", "2");
            ret = replace(ret, "３", "3");
            ret = replace(ret, "４", "4");
            ret = replace(ret, "５", "5");
            ret = replace(ret, "６", "6");
            ret = replace(ret, "７", "7");
            ret = replace(ret, "８", "8");
            ret = replace(ret, "９", "9");
            ret = replace(ret, "＃", "#");
            return ret;
        }
    }

    public static String genEmptyString(int length) {
        char[] cs = new char[length];

        for(int i = 0; i < length; ++i) {
            cs[i] = ' ';
        }

        return new String(cs);
    }

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    public static String getCurrTimeISO8601(Date date) {
        if (date == null) {
            date = new Date();
        }

        String dateStr = m_ISO8601Local.format(date);
        return dateStr.substring(0, 22) + ":" + dateStr.substring(22);
    }

    public static int getStrIndex(String s, String[] args) {
        int length = args.length;

        for(int i = 0; i < length; ++i) {
            if (args[i].equals(s)) {
                return i;
            }
        }

        return -1;
    }

    public static String join(Iterator iterator, String separator) {
        StringBuffer buf = new StringBuffer();

        while(iterator.hasNext()) {
            buf.append(iterator.next());
            if (iterator.hasNext()) {
                buf.append(separator);
            }
        }

        return buf.toString();
    }

    public static String join(Object[] list, String separator) {
        return join(Arrays.asList(list).iterator(), separator);
    }

    public static boolean nullOrBlank(String param) {
        return param == null || param.trim().equals("");
    }

    public static boolean parseBoolean(String param, boolean value) {
        if (nullOrBlank(param)) {
            return value;
        } else {
            switch(param.charAt(0)) {
            case '0':
            case 'F':
            case 'N':
            case 'f':
            case 'n':
                return false;
            case '1':
            case 'T':
            case 'Y':
            case 't':
            case 'y':
                return true;
            default:
                return value;
            }
        }
    }

    public static boolean parseBoolean(String param) {
        return parseBoolean(param, false);
    }

    public static double parseDouble(String param, double defValue) {
        double d = defValue;

        try {
            d = Double.parseDouble(param);
        } catch (Exception var6) {
            ;
        }

        return d;
    }

    public static float parseFloat(String param, float defValue) {
        float f = defValue;

        try {
            f = Float.parseFloat(param);
        } catch (Exception var4) {
            ;
        }

        return f;
    }

    public static int parseInt(String param, int defValue) {
        int i = defValue;

        try {
            i = Integer.parseInt(param);
        } catch (Exception var4) {
            ;
        }

        return i;
    }

    public static long parseLong(String param, long defValue) {
        long l = defValue;

        try {
            l = Long.parseLong(param);
        } catch (Exception var6) {
            ;
        }

        return l;
    }

    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        } else {
            char[] randBuffer = new char[length];

            for(int i = 0; i < randBuffer.length; ++i) {
                randBuffer[i] = numbersAndLetters[randGen.nextInt(numbersAndLetters.length)];
            }

            return new String(randBuffer);
        }
    }

    public static final String replace(String line, String oldString, String newString, boolean ignoreCase) {
        if (line != null && oldString != null && newString != null) {
            String lcLine = line;
            String lcOldString = oldString;
            if (ignoreCase) {
                lcLine = line.toLowerCase();
                lcOldString = oldString.toLowerCase();
            }

            int i = 0;
            if ((i = lcLine.indexOf(lcOldString, i)) < 0) {
                return line;
            } else {
                char[] line2 = line.toCharArray();
                char[] newString2 = newString.toCharArray();
                int oLength = oldString.length();
                StringBuffer buf = new StringBuffer(line2.length);
                buf.append(line2, 0, i).append(newString2);
                i += oLength;

                int j;
                for(j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                    buf.append(line2, j, i - j).append(newString2);
                    i += oLength;
                }

                buf.append(line2, j, line2.length - j);
                return buf.toString();
            }
        } else {
            return null;
        }
    }

    public static final String replace(String line, String oldString, String newString) {
        return replace(line, oldString, newString, false);
    }

    public static final String replaceIgnoreCase(String line, String oldString, String newString) {
        return replace(line, oldString, newString, true);
    }

    static boolean strEquals(String s1, String s2) {
        if (s1 != null && s2 != null) {
            return s1.equals(s2);
        } else {
            return s1 == s2;
        }
    }

    public static String toChinese(String strvalue) {
        return toEncoding(strvalue, "ISO-8859-1", "GBK");
    }

    public static String toEncoding(String strvalue, String fromEncoding, String toEncoding) {
        try {
            if (strvalue == null) {
                return null;
            } else {
                strvalue = new String(strvalue.getBytes(fromEncoding), toEncoding);
                return strvalue;
            }
        } catch (Exception var4) {
            return null;
        }
    }

    public static String toLatin(String strvalue) {
        return toEncoding(strvalue, "GBK", "ISO-8859-1");
    }

    public static boolean toBoolean(String strvalue) {
        return "1".equals(strvalue);
    }

    private static String toUnicodeEscapeString(String str) {
        StringBuffer buf = new StringBuffer();
        int len = str.length();

        for(int i = 0; i < len; ++i) {
            char ch = str.charAt(i);
            switch(ch) {
            case '\t':
                buf.append("\\t");
                break;
            case '\n':
                buf.append("\\n");
                break;
            case '\r':
                buf.append("\\r");
                break;
            case '\\':
                buf.append("\\\\");
                break;
            default:
                if (ch >= ' ' && ch <= 127) {
                    buf.append(ch);
                } else {
                    buf.append('\\');
                    buf.append('u');
                    buf.append(upcaseHexChar[ch >> 12 & 15]);
                    buf.append(upcaseHexChar[ch >> 8 & 15]);
                    buf.append(upcaseHexChar[ch >> 4 & 15]);
                    buf.append(upcaseHexChar[ch >> 0 & 15]);
                }
            }
        }

        return buf.toString();
    }

    public static String StringReplace(String source, String from, String to) {
        for(int i = source.indexOf(from); i > 0; i = source.indexOf(from)) {
            source = source.substring(0, i).concat(to).concat(source.substring(i + from.length()));
        }

        return source;
    }

    public static String deal(String val) {
        val = StringReplace(val, "\"", "’");
        val = StringReplace(val, ">", "’");
        val = StringReplace(val, "<", "’");
        return val;
    }

    public static String transform(String obj) {
        if (obj == null || obj.equals("null")) {
            obj = " ";
        }

        return obj;
    }

    public static String transFormOut(String parm) {
        if (parm.contains("'")) {
            parm = parm.replaceAll("'", "\\\\'");
        }

        return parm;
    }

    public static boolean hasText(String str) {
        return str != null;
    }
}
