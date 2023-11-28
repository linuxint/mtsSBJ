package com.devkbil.mtssbj.common.util;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.devkbil.mtssbj.common.exception.SysException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class NewStringUtil {
    /**
     * @param s
     * @return
     */
    public static String rtrim(String s) {
        char[] val = s.toCharArray();
        int st = 0;
        int len = s.length();
        while (st < len && val[len - 1] <= ' ') {
            len--;
        }
        return s.substring(0, len);
    }

    /**
     * @param s
     * @return
     */
    public static String ltrim(String s) {
        char[] val = s.toCharArray();
        int st = 0;
        int len = s.length();
        while (st < len && val[st] <= ' ') {
            st++;
        }
        return s.substring(st, len);
    }

    public static String cutStr(String str, int length, String tail) {
        return cutStr(str, length, '+', tail);
    }

    public static String cutStr(String str, int length, char type, String tail) {
        if (isEmpty(str)) {
            return "";
        }

        byte[] bytes = str.getBytes();
        int counter = 0;

        if (length >= bytes.length) {
            return str;
        }

        for (int i = length - 1; i >= 0; i--) {
            if ((bytes[i] & 0x80) == 0) {
                continue;
            }
            counter++;
        }
        String f_str = null;
        switch (type) {
            case ',':
            case '-':
            default:
                f_str = new String(bytes, 0, length - counter % 2);
                break;
            case '+':
                f_str = new String(bytes, 0, length + counter % 2);
                break;//占쌩곤옙
        }

        return f_str + tail;
    }

    public static String null2blank(String str) {
        return str == null ? "" : str;
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) || (str.trim().equals(""));
    }

    public static String addZero(String str, int length) {
        String temp = "";
        for (int i = str.length(); i < length; i++) {
            temp += "0";
        }
        temp += str;
        return temp;
    }

    public static String toCamel(String ubStr) {
        if (ubStr == null) {
            return null;
        }
        if (ubStr.equals(ubStr.toUpperCase())) {
            ubStr = ubStr.toLowerCase();
        }

        StringTokenizer tokenizer = new StringTokenizer(ubStr, "_");
        StringBuffer result = new StringBuffer();
        while (tokenizer.hasMoreTokens()) {
            result.append(toHeadUpperCase(tokenizer.nextToken()));
        }
        return result.toString();
    }

    public static String toHeadUpperCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String lpad(String arg, int len, String padStr, String encType) {
        boolean isConversion = false;
        String subArg = "";
        if (arg != null) {
            subArg = arg.trim();
        } else {
            subArg = null2blank(arg);
        }
        String ascTxt = subArg;

        if (len <= ascTxt.length()) {
            return arg;
        }

        byte[] b = ascTxt.getBytes();
        int loopTo = len - b.length;

        for (int i = 0; i < loopTo; i++) {
            ascTxt = padStr + ascTxt;
        }
        if (ascTxt.length() == len) {
            for (int inx = 0; inx < len; inx++) {
                if (Character.UnicodeBlock.of(ascTxt.charAt(inx)) != Character.UnicodeBlock.LATIN_1_SUPPLEMENT) {
                    continue;
                }
                isConversion = !isConversion;
            }

            if ((Character.UnicodeBlock.of(ascTxt.charAt(len - 1)) == Character.UnicodeBlock.LATIN_1_SUPPLEMENT) && (isConversion)) {
                ascTxt = ascTxt.substring(0, len - 1) + padStr;
            }
        }

        return ascTxt;
    }

    public static String lpad(String str, int len, String addStr) {

        String result = str;

        int templen = len - result.length();

        for (int i = 0; i < templen; i++) {
            result = addStr + result;
        }

        return result;

    }

    /* dw占쏙옙占싹삼옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙("|") 占쌩곤옙占쌩다곤옙 占쏙옙占쏙옙占쏙옙청占쏙옙占쏙옙 pad 占쏙옙占쏙옙占싹곤옙 return +"|" */
    public static String lpadGbn(String arg, int len, String padStr, String encType) {
        String subArg = "";
        if (arg != null) {
            subArg = arg.trim();
        } else {
            subArg = null2blank(arg);
        }
        String ascTxt = subArg;

        return ascTxt + "|";
    }

    public static String lpadByUTF8(String utfTxt, int len, String padStr) {
        if (len <= utfTxt.length()) {
            return utfTxt;
        }
        String tmpStr = utfTxt;
        int loopTo = len - tmpStr.length();

        for (int i = 0; i < loopTo; i++) {
            tmpStr = padStr + tmpStr;
        }
        return tmpStr;
    }

    public static String rpad(String arg, int len, String padStr) {
        int loopTo = 0;
        String subArg = "";
        boolean isConversion = false;

        if (arg != null) {
            subArg = arg.trim();
        } else {
            subArg = null2blank(arg);
        }
        String ascTxt = subArg;

        if (len <= ascTxt.length()) {
            return arg;
        }

        loopTo = len - ascTxt.length();
        for (int inx = 0; inx < loopTo; inx++) {
            ascTxt = ascTxt + padStr;
        }
        if (ascTxt.length() == len) {
            for (int inx = 0; inx < len; inx++) {
                if (Character.UnicodeBlock.of(ascTxt.charAt(inx)) != Character.UnicodeBlock.LATIN_1_SUPPLEMENT) {
                    continue;
                }
                isConversion = !isConversion;
            }

            if ((Character.UnicodeBlock.of(ascTxt.charAt(len - 1)) == Character.UnicodeBlock.LATIN_1_SUPPLEMENT) && (isConversion)) {
                ascTxt = ascTxt.substring(0, len - 1) + padStr;
            }
        }

        return ascTxt;
    }

    /* dw占쏙옙占싹삼옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙("|") 占쌩곤옙占쌩다곤옙 占쏙옙占쏙옙占쏙옙청占쏙옙占쏙옙 pad 占쏙옙占쏙옙占싹곤옙 return +"|" */
    public static String rpadGbn(String arg, int len, String padStr) {
        String subArg = "";

        if (arg != null) {
            subArg = arg.trim();
        } else {
            subArg = null2blank(arg);
        }
        String ascTxt = subArg;

        return ascTxt + "|";
    }

    public static String rpadByUTF8(String utfTxt, int len, String padStr) {
        String tmpTxt = null2blank(utfTxt);
        if (len <= tmpTxt.length()) {
            return tmpTxt;
        }
        int loopTo = len - tmpTxt.length();

        for (int i = 0; i < loopTo; i++) {
            tmpTxt = tmpTxt + padStr;
        }
        return tmpTxt;
    }

    public static String rightPadWith(String source, int totLen, char pad) {
        String rtnString = "";
        try {
            String padStr = "";
            byte[] byteSrc = source.getBytes();
            int byteLen = totLen - byteSrc.length;
            if (byteLen < 0) {
                throw new Exception("totLen占쏙옙 占쌉뤄옙 占쏙옙트占쏙옙占쏙옙占쏙옙 占쌜쏙옙占싹댐옙!!");
            }
            for (int i = 0; i < byteLen; i++) {
                padStr = padStr + pad;
            }
            rtnString = source + padStr;
        } catch (Exception e) {
            return null;
        }
        return rtnString;
    }

    public static String rightPadWith(String source, int totLen, String pad) {
        String rtnString = "";
        try {
            String padStr = "";
            byte[] byteSrc = source.getBytes();
            int byteLen = totLen - byteSrc.length;
            if (byteLen < 0) {
                throw new Exception("totLen占쏙옙 占쌉뤄옙 占쏙옙트占쏙옙占쏙옙占쏙옙 占쌜쏙옙占싹댐옙!!");
            }
            for (int i = 0; i < byteLen; i++) {
                padStr = padStr + pad;
            }
            rtnString = source + padStr;
        } catch (Exception e) {
            return null;
        }

        return rtnString;
    }

    public static String[] split(String srcTxt, String delim) {
        List<String> list = toArrayList(srcTxt, delim);
        if (list != null) {
            String[] strings = new String[list.size()];
            for (int i = 0; i < strings.length; i++) {
                strings[i] = list.get(i);
            }

            return strings;
        } else {
            // SecurityPrism
            // return null;
            String[] stringsNull = null;
            return stringsNull;
        }
    }

    public static ArrayList<String> toArrayList(String srcTxt, String delim) {
        ArrayList<String> list = new ArrayList<String>();
        if (isEmpty(srcTxt)) {
            return list;
        }

        StringTokenizer st = new StringTokenizer(srcTxt, delim);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

    public static ArrayList<String> splitByLimit(String arg, int len, String encType) {
        String subArg = "";
        if (arg != null) {
            subArg = arg.trim();
        }
        String ascTxt = subArg;
        ArrayList<String> list = new ArrayList<String>();

        if (ascTxt.length() <= len) {
            list.add(arg);
            return list;
        }

        do {
            String tmpTxt = ascTxt.substring(0, len);
            ascTxt = ascTxt.substring(len);
            list.add(tmpTxt);
        }
        while (ascTxt.length() > len);

        list.add(ascTxt);

        return list;
    }

    public static String replace(String source, String pattern, String replace) {
        if (isEmpty(source)) {
            return "";
        }
        if (replace == null) {
            replace = "";
        }

        int i = 0;
        int j = 0;
        int k = pattern.length();

        StringBuilder buf = new StringBuilder();
        String result = source;
        while ((j = source.indexOf(pattern, i)) >= 0) {
            if (buf == null) {
                buf = new StringBuilder(source.length() * 2);
            }
            buf.append(source, i, j);
            buf.append(replace);

            i = j + k;
        }

        if (i != 0) {
            buf.append(source.substring(i));
            result = buf.toString();
        }
        return result;
    }

    // 占쌘어에 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙聘占� 占쏙옙占�
    public static String escapeDelim(String src) {
        if (src == null) {
            return "";
        }
        return replace(replace(replace(src, "\\^", "^^"), "\\|", "^\\\\"), "`", "^~");
    }

    public static String readerToString(Reader rd) throws IOException {
        if (rd == null) {
            return null;
        }

        StringBuffer resultSb = new StringBuffer();
        try {
            char[] buf = new char[1024];
            int readcnt = 0;

            while ((readcnt = rd.read(buf, 0, 1024)) != -1) {
                resultSb.append(buf, 0, readcnt);
                if (readcnt < 1024) {
                    break;
                }
            }
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (Exception localException) {
                }
            }
        }
        return resultSb.toString();
    }

    public static String urlEncode(String str, String charset) {
        try {
            return URLEncoder.encode(str, charset);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

    public static String urlDecode(String str, String charset) {
        try {
            return URLDecoder.decode(str, charset);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

    public static HashMap<String, String> pairMapByDelim(String str, String splitDelim, String pairDelim) {
        ArrayList<String> list = toArrayList(str, splitDelim);
        int len = list.size();
        HashMap<String, String> map = new HashMap<String, String>(len);

        for (int i = 0; i < len; i++) {
            String tmp = list.get(i);
            int pt = tmp.indexOf("=");
            if (pt <= 0) {
                continue;
            }
            map.put(tmp.substring(0, pt), tmp.substring(pt + 1));
        }

        return map;
    }

    public static boolean isNull(String str) {
        return isEmpty(str);
    }

    public static boolean isNullNotTrim(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean getBoolean(String value, boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return (value.equalsIgnoreCase("true")) || (value.equalsIgnoreCase("on")) || (value.equalsIgnoreCase("y")) || (value.equalsIgnoreCase("t"))
                || (value.equalsIgnoreCase("yes"));
    }

    public static Object escapeXml(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("'", "&apos;");
        return str;
    }

    public static Object escapeHtml(String str) {
        str = str.replaceAll("&amp;", "&");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");
        str = str.replaceAll("&quot;", "\"");
        str = str.replaceAll("&apos;", "'");
        str = str.replaceAll("&#39;", "'");
        return str;
    }

    public static Object escapeHtmlMod(String str) {
        str = str.replaceAll("lt;", "<");
        str = str.replaceAll("gt;", ">");
        str = str.replaceAll("quot;", "\"");
        str = str.replaceAll("apos;", "'");
        str = str.replaceAll("#39;", "'");
        return str;
    }

    /**
     * 占쏙옙트占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쌘몌옙 占쏙옙占� 占쏙옙占쏙옙
     *
     * @param str
     * @param src
     * @param des
     * @return
     * @throws SysException
     */
    public static String replaceAll(String str, String src, String des) throws SysException {
        StringBuffer sb = new StringBuffer(str.length());
        int startIdx = 0;
        int oldIdx = 0;
        for (; ; ) {
            startIdx = str.indexOf(src, startIdx);
            if (startIdx == -1) {
                sb.append(str.substring(oldIdx));
                break;
            }
            sb.append(str, oldIdx, startIdx);
            sb.append(des);

            startIdx += src.length();
            oldIdx = startIdx;
        }
        return sb.toString();
    }

    /**
     * 占쏙옙트占쏙옙占쏙옙 XSS 占쏙옙占쏙옙 占쏙옙占싶몌옙
     *
     * @param value
     * @return
     */
    public static String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
            value = value.replaceAll("", "");
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // HTML transformation characters
            value = org.springframework.web.util.HtmlUtils.htmlEscape(value);
            // SQL injection characters
            value = StringEscapeUtils.escapeSql(value);

            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
            //value = value.replaceAll("script", "");

            value = ((value.indexOf("script") > -1) ? "" : value.replaceAll("script", ""));

            //			// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            //			// avoid encoded attacks.
            //			// value = ESAPI.encoder().canonicalize(value);
            //
            //			// HTML transformation characters
            //			value = StringEscapeUtils.escapeHtml(value);
            //			// SQL injection characters
            //			value = StringEscapeUtils.escapeSql(value);
            //
            //			// Avoid anything between script tags
            //			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //			// Avoid anything in a src='...' type of expression
            //			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			// Remove any lonesome </script> tag
            //			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			// Remove any lonesome <script ...> tag
            //			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			// Avoid eval(...) expressions
            //			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			// Avoid expression(...) expressions
            //			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			// Avoid javascript:... expressions
            //			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			// Avoid vbscript:... expressions
            //			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			// Avoid onload= expressions
            //			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //			value = scriptPattern.matcher(value).replaceAll("");
            //
            //			value = value.replaceAll("eval\\((.*)\\)", "");
            //			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            //			// value = value.replaceAll("script", "");
            //
            //			// HTML transformation characters
            //  		  	value = org.springframework.web.util.HtmlUtils.htmlEscape(value);
            //  		  	// SQL injection characters
            //  		  	value = StringEscapeUtils.escapeSql(value);
            //
            //  		  	value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            //  		  	value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            //  		  	value = value.replaceAll("'", "&#39;");
            //  		  	value = value.replaceAll("eval\\((.*)\\)", "");
            //  		  	value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            //  		  	//value = value.replaceAll("script", "");
            //
            //			value = ((value.indexOf("script") > -1) ? "" : value.replaceAll("script", ""));

        }
        return value;
    }

    public static String maskedString(String param, String pattern) {
        if (param == null) {
            return param;
        }
        int size = param.length();
        String p = pattern;
        int pLength = p.replaceAll("-", "").length();
        if (size != pLength) {
            return param;
        }
        int patternLength = pattern.length();
        String result = "";
        int hipenCnt = 0;
        for (int i = 0; i < patternLength; i++) {
            if ("-".equals(pattern.substring(i, i + 1))) {
                result += "-";
                hipenCnt++;
                continue;
            } else if ("*".equals(pattern.substring(i, i + 1))) {
                result += "*";
            } else {
                result += param.substring(i - hipenCnt, i + 1 - hipenCnt);
            }
        }
        return result;
    }

    /**
     * 占쏙옙占쏙옙 IP
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String result = request.getHeader("WL-Proxy-Client-IP");
        if (result == null || "".equals(result.trim())) {
            result = request.getRemoteAddr();
        }
        return result;
    }

    /**
     * [占쏙옙占쏙옙占�] 占쏙옙羞� UserAgent 占쏙옙占쏙옙 占쏙옙占쏙옙
     *
     * @param request
     * @return String
     */
    public static String getUserAgentDevice(HttpServletRequest request) throws ServletException {
        String PhoneCheck = "";
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && !"".equals(userAgent)) {
            int chk1 = userAgent.toLowerCase().indexOf("iphone");
            int chk2 = userAgent.toLowerCase().indexOf("android");
            int chk3 = userAgent.toLowerCase().indexOf("ipad");
            if (chk1 > -1 || chk3 > -1) {
                PhoneCheck = "IPhone";
            } else if (chk2 > -1) {
                PhoneCheck = "Android";
            } else {
                PhoneCheck = "Others";
            }
        } else {
            PhoneCheck = "Others";
        }
        return PhoneCheck;
    }

    /**
     * [占쏙옙占쏙옙占�] 占쏙옙羞� UserAgent 占쏙옙占쏙옙 占쏙옙占쏙옙
     *
     * @param userAgent
     * @return String
     */
    public static String getUserAgentDevice(String userAgent) throws ServletException {
        String PhoneCheck = "";
        if (userAgent == null || "".equals(userAgent)) {
            PhoneCheck = "Others";
        } else {
            int chk1 = userAgent.toLowerCase().indexOf("iphone");
            int chk2 = userAgent.toLowerCase().indexOf("android");
            int chk3 = userAgent.toLowerCase().indexOf("ipad");
            if (chk1 > -1 || chk3 > -1) {
                PhoneCheck = "IPhone";
            } else if (chk2 > -1) {
                PhoneCheck = "Android";
            } else {
                PhoneCheck = "Others";
            }
        }
        return PhoneCheck;
    }

    /**
     * Request占쏙옙 UserAgent 占쏙옙占쏙옙占쏙옙 userAgent 占실댐옙
     *
     * @param userAgent
     * @return String
     */
    public static String getUserAgent(String userAgent) {
        String retVal = "";
        String PhoneCheck = "";

        if (userAgent != null && !"".equals(userAgent)) {
            int chk1 = userAgent.toLowerCase().indexOf("iphone");
            int chk2 = userAgent.toLowerCase().indexOf("android");
            int chk3 = userAgent.toLowerCase().indexOf("ipad");
            if (chk1 > -1) {
                PhoneCheck = "IPhone";
            } else if (chk2 > -1) {
                PhoneCheck = "Android";
            } else if (chk3 > -1) {
                PhoneCheck = "IPad";
            } else {
                PhoneCheck = "Others";
            }
        } else {
            PhoneCheck = "Others";
        }

        if (!"Others".equals(PhoneCheck)) {
            retVal = "MOBILE";
        } else {
            retVal = "PC";
        }
        return retVal;
    }

    /**
     * 占쌍민뱄옙호占쏙옙 占쏙옙占쏙옙占쏙옙 8占쌘몌옙(YYYYMMDD)
     *
     * @param juminNo
     * @return String
     */
    public static String getStrBirthday(String juminNo) {
        String bird = "";
        if (juminNo == null || "".equals(juminNo) || juminNo.length() < 6) {
            bird = "";
        } else {
            String str1 = juminNo.substring(0, 6);
            String str2 = juminNo.substring(6, 7);
            if ("1".equals(str2) || "2".equals(str2) || "3".equals(str2) || "4".equals(str2)) {
                if ("1".equals(str2) || "2".equals(str2)) {
                    bird = "19" + str1;
                } else {// if( "3".equals(str2) || "4".equals(str2) )
                    bird = "20" + str1;
                }
            } else if ("5".equals(str2) || "6".equals(str2) || "7".equals(str2) || "8".equals(str2)) {
                // 5, 6, 7, 8 : 占쌤깍옙占쏙옙
                // 5, 6 : 1900占쏙옙占�
                // 7, 8 : 2000占쏙옙占�
                if ("5".equals(str2) || "6".equals(str2)) {
                    bird = "19" + str1;
                } else {
                    bird = "20" + str1;
                }
            }
        }

        return bird;
    }

    /**
     * 占쌍민뱄옙호占쏙옙 占쏙옙占쏙옙占쌘듸옙 占쏙옙占싹깍옙
     *
     * @param juminNo
     * @return String
     */
    public static String getSexDc(String juminNo) {
        String sexDc = "";
        if (juminNo == null || "".equals(juminNo) || juminNo.length() < 6) {
            sexDc = "";
        } else {
            int gender = juminNo.charAt(6) - '0';
            if (gender % 2 == 0) {
                sexDc = "2";
            } else {
                sexDc = "1";
            }
        }

        return sexDc;
    }

    /**
     * 占쌍민뱄옙호占쏙옙 占쏙옙占쏙옙占쏙옙 / 占쌤깍옙占쏙옙 占쏙옙占쏙옙占쌘듸옙 占쏙옙占싹깍옙
     *
     * @param juminNo
     * @return String
     */
    public static String getLocalandForeignDc(String juminNo) {
        String domFrnDc = "";
        String str2 = juminNo.substring(6, 7);
        if ("1".equals(str2) || "2".equals(str2) || "3".equals(str2) || "4".equals(str2)) {
            domFrnDc = "1";
        } else if ("5".equals(str2) || "6".equals(str2) || "7".equals(str2) || "8".equals(str2)) {
            domFrnDc = "2";
        }

        return domFrnDc;
    }

    /**
     * 화占쏙옙占싫� 占쏙옙占쏙옙占쏙옙占쏙옙
     *
     * @param :
     * @占쏙옙 占쏙옙 :
     * @return
     */
    public static String getWebMenu(DataMap dataMap) {
        String paReqMcUrl = dataMap.getRequestURI();

        String chkPaReqkMcUrl = dataMap.getRequestURI();

        try {
            chkPaReqkMcUrl = chkPaReqkMcUrl.substring(chkPaReqkMcUrl.lastIndexOf("/") + 1);
            paReqMcUrl = NewStringUtil.NVL(paReqMcUrl, chkPaReqkMcUrl.substring(chkPaReqkMcUrl.length() > 16 ? 16 : chkPaReqkMcUrl.length()));
        } catch (Exception e) {
            e.printStackTrace();
            paReqMcUrl = chkPaReqkMcUrl;
        }

        return paReqMcUrl;
    }

    public static String NVL(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    /**
     * Object占쏙옙 null占쏙옙 占싣니몌옙 String占쏙옙占쏙옙 占쏙옙환, null占싱몌옙 占쏙옙占쏙옙占쏙옙占쌘뤄옙 占쏙옙환
     * @param obj
     * @param deflt
     * @return
     */
    public static String NVL(Object obj, String deflt) {
        String retVal = "";
        if (obj != null) {    // Object占쏙옙 null占쏙옙 占싣니몌옙 String占쏙옙占쏙옙 占쏙옙환
            retVal = obj.toString().trim();
        }
        if ("null".equalsIgnoreCase(retVal)) {
            retVal = "";
        }
        if ("".equals(retVal)) { // String占쏙옙占쏙옙 deflt(Default) 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
            retVal = deflt.trim();
        }
        return retVal;
    }

    public static String NVL(String obj, String deflt) {
        if (obj != null) {
            return obj;
        }
        return deflt;
    }

    public static int NVL(Object obj, int deflt) {
        if (obj != null) {
            try {
                return Integer.parseInt(obj.toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return deflt;
    }

    public static double NVL(Object obj, double deflt) {
        if (obj != null) {
            try {
                return Double.parseDouble(obj.toString());
            } catch (NumberFormatException e) {
                return 0.0D;
            }
        }
        return deflt;
    }

    public static Object NVL(String obj1, Object obj2) {
        return isNull(obj1) ? obj2 : obj1;
    }

    public static String[] toArray(String str, String del) {
        if (str == null) {
            // SecurityPrism
            // return null;
            String[] strNull = null;
            return strNull;
        }
        StringTokenizer st = new StringTokenizer(str, del);
        String[] names = new String[st.countTokens()];
        for (int i = 0; i < names.length; i++) {
            names[i] = st.nextToken().trim();
        }
        return names;
    }

    public static String formatDate(String format, String str) {
        if (str == null) {
            return "";
        }
        if (format == null) {
            return str;
        }
        java.util.Date dd = null;
        try {
            dd = java.sql.Date.valueOf(str);
        } catch (IllegalArgumentException e) {
            try {
                dd = Timestamp.valueOf(str);
            } catch (IllegalArgumentException localIllegalArgumentException1) {
            }
        }
        return DateFormatUtils.format(dd, format);
    }

	
	/*	占싱삼옙占� 占쌀쏙옙
	 * public static String getMoneyString(int currencyCode, long money)
	{
		ISO4217 iso4217  = ISO4217.getInstance();
		
		String cCode = iso4217.getCodeForCurrency(String.valueOf(currencyCode));
		
		if("".equals(cCode))
			throw new IllegalArgumentException("Currency with numeric code "  + currencyCode + " not found");
		
		String cExp  = iso4217.getExponentForCurrency(String.valueOf(currencyCode));
		
		if("-1".equals(cExp))
			throw new IllegalArgumentException("Currency with numeric code "  + currencyCode + " not found");

		String strMoney = String.valueOf(money);
		int exp = Integer.valueOf(cExp);
		
		StringBuffer moneyString = new StringBuffer();
		
		int money_len = strMoney.length();
		int maj = money_len - exp;
		
		if(maj >= 0)
		{
		    int ii = 0;
			
			while(maj > 0)
			{
				moneyString.append(String.valueOf(strMoney.charAt(ii++)));
				maj--;
				if(maj > 0 && maj % 3 == 0 ) moneyString.append(",");
			}
			if(exp > 0) moneyString.append(".");
			while(exp > 0)
			{
				moneyString.append(String.valueOf(strMoney.charAt(ii++)));
				exp--;
			}
		}
		else
		{
			int ii = 0;
			moneyString.append(".");
			while(maj < 0) 
			{
				moneyString.append("0");
				maj++;
			}
			
			while(ii < strMoney.length())
			{
				moneyString.append(strMoney.charAt(ii++));
			}
		}
		
		return  cCode + " " + moneyString.toString();
	
			
		Set<Currency> currencies = Currency.getAvailableCurrencies();
		for (Currency currency : currencies) {
			if (currency.getNumericCode() == currencyCode) {
				for (Locale locale : NumberFormat.getAvailableLocales()) {
					NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
					int code = nf.getCurrency().getNumericCode();
					if (currencyCode == code) {
						return currency.getCurrencyCode() + " " + nf.format(money * Math.pow(10, -1*currency.getDefaultFractionDigits()));
					}
				}
			}
		}
		throw new IllegalArgumentException("Currency with numeric code "  + currencyCode + " not found");
	
	}*/

    /**
     * 占쏙옙占쏙옙占쌘듸옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙타占쏙옙占쏙옙.
     *
     * @return 占쏙옙占쌘띰옙占� <code>true</code>占쏙옙 占쏙옙占쏙옙占싹곤옙 占싣니띰옙占� <code>false</code>占쏙옙 占쏙옙占쏙옙占싼댐옙. 占쏙옙占쌘울옙占쏙옙 <code>null</code>占싱몌옙 <code>false</code>占쏙옙
     *         占쏙옙占쏙옙占싼댐옙
     */
    public static boolean isNumeric(String str) {
        if (null == str || str.length() == 0) {
            return false;
        }
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 占쏙옙짜 占쏙옙환(DB占싯삼옙 占쏙옙占쏙옙 占쏙옙짜占쏙옙占쏙옙占쏙옙 占쏙옙占쌘몌옙 占쏙옙占쏙옙)
     *
     * @param str
     * @return Object
     */
    public static String getOnlyNumber(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    public static String maskedStr(String param, String patten) {

        String res = "";
        String[] regex = new String[2];

        if (isNull(param) || isNull(patten)) {
            return param;
        }

        if ("cardNo".equalsIgnoreCase(patten)) {
            regex[0] = "(\\d{4})(\\d*)(\\d{4})";
            regex[1] = "$1********$3";
        } else if ("phoneNo".equalsIgnoreCase(patten)) {
            regex[0] = "(\\d{2,3})(\\d{1,2})(\\d{2})(\\d{2})(\\d{2})";
            regex[1] = "$1-$2**-$4**";
        } else if ("cstNm".equalsIgnoreCase(patten)) {
            regex[0] = "(.{1})(.*)(.{1})";
            regex[1] = "$1**$3";
        } else if ("cstId".equalsIgnoreCase(patten)) {
            regex[0] = "(?<=.{3}).";
            regex[1] = "*";
        } else if ("rno".equalsIgnoreCase(patten)) {
            // rno == 10占쌘몌옙 -> 占쏙옙占쏙옙薇占싫�
            if (param.length() == 10) {
                return param;
            }

            param = param.replaceAll("-", "").replaceAll("(?<=.{7}).", "*");
            regex[0] = "(\\d{6})({7})";
            regex[1] = "$1-$2";
        } else if ("email".equalsIgnoreCase(patten)) {
            String[] paramArr = param.split("@");
            param = paramArr[0].replaceAll("(?<=.{2}).", "*") + "@" + paramArr[1];
            regex[0] = "(.*)@(.*)";
            regex[1] = "$1@$2";
        } else {
            return param;
        }

        res = param.replaceAll("-", "").replaceAll(regex[0], regex[1]);

        return res;
    }

    public static String nl2br(String str) {
        String strRtn = "";

        strRtn = str.replaceAll("\r\n", "\n");
        strRtn = strRtn.replaceAll("\r", "<br>");
        strRtn = strRtn.replaceAll("\n", "<br>");

        return strRtn;
    }

    public static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;

        } catch (NumberFormatException e) {
            // logger.error(e.getMessage(), e);
            return false;
        }

    }

    public static char toHexNibble(int b) {

        if (b >= 0 && b <= 9) {
            return (char)(b + '0');
        }

        if (b >= 0x0a && b <= 0x0f) {
            return (char)(b + 'A' - 10);
        }

        return '0';

    }

    public static String toHex(int b) {

        char[] c = new char[2];

        c[0] = toHexNibble((b >> 4) & 0x0f);

        c[1] = toHexNibble(b & 0x0f);

        return new String(c);

    }

    public static String getString(byte[] data) {

        String result = "";

        for (int i = 0; i < data.length; i++) {

            result = result + toHex(data[i]);

        }

        return result;

    }

    public static String leftPadZero(int number, int length) {

        return lpad("" + number, length, "0");

    }

    public byte getHexNibble(char c) {

        if (c >= '0' && c <= '9') {
            return (byte)(c - '0');
        }

        if (c >= 'A' && c <= 'F') {
            return (byte)(c - 'A' + 10);
        }

        return 0;
    }

    public byte getHex(String str) {

        str = str.trim();

        if (str.length() == 0) {
            str = "00";
        } else if (str.length() == 1) {
            str = "0" + str;
        }

        str = str.toUpperCase();

        return (byte)(getHexNibble(str.charAt(0)) * 16 + getHexNibble(str.charAt(1)));
    }

    public byte[] getBytes(String data) {

        String[] str = data.split(",");

        byte[] result = new byte[str.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = getHex(str[i]);
        }

        return result;

    }
}
