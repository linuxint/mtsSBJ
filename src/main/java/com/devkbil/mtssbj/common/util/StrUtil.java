package com.dyinfo.common.string;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 Created by KJB on 2016-04-25. */
public class StrUtil {
    /**
     기본 패팅 0
     */
    private static char DEFAULT_PAD_CHAR = '0';
    
    /**
     공백 문자열
     */
    private static String EMPTY_STR = "";
    
    /**
     null 문자열
     */
    private static String NULL_STR = "null";
    
    //////////////////////////////////////////////////// boolean ///////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////// format  ///////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////// boolean ///////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////// boolean ///////////////////////////////////////////////////////////////
    
    /**
     str의 times만큼 반복리턴
     @param str
     @param times
     
     @return
     */
    public static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
    
    public static String[] splite(String st, String key) {
        if(st == null) return new String[0];
        
        StringTokenizer token      = new StringTokenizer(st, key);
        int             tokenCount = token.countTokens();
        String[]        arr        = new String[tokenCount];
        for(int i = 0; i < tokenCount; i++) {
            arr[i] = token.nextToken();
        }
        return arr;
    }
    
    public static String[] spliteFirst(String st, String key) {
        if(st == null) return new String[0];
        int nPos;
        nPos = st.indexOf(key);
        String[] arr = new String[2];
        arr[0] = st.substring(0,nPos);
        arr[1] = st.substring(nPos+1, st.length());
        return arr;
    }
    
    public static boolean isZero(String str) {
        boolean retValue = false;
        if(isNULL(str)) {
            retValue = true;
        }
        if(str.indexOf(".") >= 0) {
            // double
            retValue = Double.parseDouble(str)==0.0 ? true : false;
        } else {
            // int
            retValue = Integer.parseInt(str) == 0 ? true : false;
        }
        return retValue;
    }
    
    /**
     Null Check (String)
     @param str 입력 param of String
     @return true/false of boolean
     */
    public static boolean isNULL(String str) {
        if(str == null || str.trim().equals(EMPTY_STR) || str.trim().equalsIgnoreCase(NULL_STR)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     Null Check (String Array)
     @param str 입력 param of String Array
     @return true/false of boolean
     */
    public static boolean isNULL(String[] str) {
        boolean retValue;
        try {
            if(str.length==0) {
                retValue = false;
            } else {
                retValue = isNULL(str.toString());
            }
        }catch(Exception e) {
            retValue = false;
        }
        return retValue;
    }
    
    /**
     NULL replace string
     @param str chkeck value
     @param NVLString null replace value
     @return input data or replace value
     */
    public static String NVL(String str, String NVLString) {
        String retValue = "";
        try {
            if(isNULL(str)) {
                retValue = NVLString;
            } else {
                retValue = str;
            }
        } catch(Exception e) {
        } finally {
        }
        return retValue;
    }
    
    /**
     값이 없는 경우 대체한 문자열 값을 출력하도록 한다
     @param source 입력 문자열
     @return 처리 결과
     @since 2011.12.29
     */
    public static String NVL(String source) {
        return NVL(source, EMPTY_STR);
    }
    
    
    //2012.01.21 moidified by Badboy
    public static String formatData(String str, int len, String sType) {
        
        StringBuffer strRtn = new StringBuffer(len);
        char strInit = ' ';
        int strlen;
        int addcnt;
        try {
            if(isNULL(str)) { strlen = 0;
            } else {
                strlen = str.length();
                strRtn.append(str);
            }
            addcnt = len - strlen;
    
            if("BLANK".equals(sType)) {  strInit = ' ';
            } else if("ZERO".equals(sType)) { strInit = '0'; }
            
            for(int i = 0; i < addcnt; i++) {
                strRtn.append(strInit);
            }
        }catch(Exception e) {
        }finally {
        }
        return strRtn.toString();
    
    }
    
    public static String toEncode(String str, String asEncode, String toEncode) {
        String retValue;
        if(str==null) { return null; }
        try {
            retValue =  new String(str.getBytes(asEncode), toEncode);
        } catch(UnsupportedEncodingException e) {
            retValue = str;
        }
        return retValue;
    }
    
    /**
     인코딩변경(KSC5601 -> UTF-8)
     @param str KSC5601문자열
     @return UTF-8문자열
     */
    public static String toUtf8(String str) {
        return toEncode(str, "KSC5601", "UTF-8");
    }
    
    /**
     인코딩변경(8859_1 -> KSC5601)
     @param str
     @return
     */
    public static String toKsc(String str) {
        return toEncode(str, "8859_1", "KSC5601");
    }
    
    /**
     인코딩변경(EUC-KR -> kSC5601)
     @param str
     @return
     */
    public static String eucToKsc(String str) {
        return toEncode(str, "EUC-KR", "KSC5601");
    }
    
    /**
     인코딩변경(8859_1 -> EUC-KR)
     @param str
     @return
     */
    public static String toEucKr(String str) {
        return toEncode(str, "8859_1", "EUC-KR");
    }
    
    /**
     인코딩변경(KSC5601 -> 8859_1)
     @param str
     @return
     */
    public static String encode(String str) {
        return toEncode(str, "KSC5601", "8859_1");
    }
    
    /**
     인코딩변경(KSC5601 -> UTF-8)
     @param str
     @return
     */
    public static String encode2(String str) {
        return toEncode(str, "KSC5601", "UTF-8");
    }
    
    /**
     인코딩변경(UTF-8 -> EUC-KR)
     @param str
     @return
     */
    public static String encode3(String str) {
        return toEncode(str, "UTF-8", "EUC-KR");
    }
    
    /**
     문자열의 바이트 단위 절단 리턴
     @param b 입력문자열
     @param startIdx 시작문자열
     @param length 절단길이
     @return 문자열 절단 결과
     */
    public static String getByteString(char[] b, int startIdx, int length) {
        if(b == null || length == 0) return null;
        String temp = new String(b, startIdx, length);
        //System.out.println("temp==="+temp);
        return new String(b, startIdx, length);
    }
    
    public static String getString(char[] b) {
        if(b == null) return null;
        return new String(b);
    }
    
    public static char[] getNChar(char[] b, int startIdx, int length) {
        if(b == null || length == 0) return null;
        char[] tmpData = new char[length];
        int    i       = 0;
        for(int nCnt = startIdx; nCnt < (startIdx + length); nCnt++) {
            tmpData[i] = b[nCnt];
            i++;
        }
        //System.out.println("STR sr " + tmpData);
        return tmpData;
    }
    
    public static String getNString(char[] b, int startIdx, int length) {
        if(b == null || length == 0) return null;
        return new String(getNChar(b, startIdx, length));
    }
    
    public static char[] getContextField(char[] b, int startIdx, int length, String flow) {
        if(b == null || length == 0) return null;
        String temp = substringb(new String(b), startIdx, length);
        if("R".equals(flow)) {
            temp = padRightSpace(temp, length);
        } else {
            if("L".equals(flow)) {
                temp = padLeftSpace(temp, length);
            }
        }
        return String2Char(temp);
    }
    
    /**
     인코딩 문자열길이 리턴
     @param str 문자열
     @param enc 인코딩 - euc-kr, UTF-8
     
     @return 길이
     */
    public static int getStrLen(String str, String enc) {
        int    nLen = 0;
        byte[] b    = null;
        
        if(str != null) {
            try {
                b = str.getBytes(enc);
                nLen = b.length;
            } catch(Exception e) {
            }
        }
        
        return nLen;
    }
    
    /**
     인코딩 문자열길이 리턴
     @param str 문자열
     
     @return 길이
     */
    public static int getStrLen(String str) {
        int    nLen = 0;
        byte[] b    = null;
        
        if(str != null) {
            try {
                b = str.getBytes();
                nLen = b.length;
            } catch(Exception e) {
            }
        }
        
        return nLen;
    }
    
    /**
     @param str
     @param len
     
     @return
     */
    public static String paddingBlank(String str, int len) {
        String strRtn = "";
        
        int strlen;
        
        try {
            if(str == null || str == "") {
                strlen = 0;
            } else {
                strlen = str.length();
            }
            
            strRtn = NVL(str);
            int addcnt = len - strlen;
            
            for(int i = 0; i < addcnt; i++)
                strRtn += " ";
        } catch(Exception e) {
        }
        
        return strRtn;
    }
    
    public static String padLeftSpace(String str, int len) {
        return String.format("%1$" + len + "s", str);
    }
    
    public static String padRightSpace(String str, int len) {
        return String.format("%1$-" + len + "s", str);
    }
    
    public static String padLeftZeros(String str, int len) {
        return padLeftSpace(str, len).replace(' ', '0');
    }
    
    public static String padRightZeros(String str, int len) {
        return padRightSpace(str, len).replace(' ', '0');
    }
    
    public static String paddingBlankB(String str, int len) {
        String strRtn = "";
        
        int strlen;
        
        try {
            if(isNULL(str)) {
                strlen = 0;
            } else {
                byte[] b = str.getBytes("euc-kr");
                strlen = b.length;
            }
            
            strRtn = NVL(str);
            int addcnt = len - strlen;
            
            for(int i = 0; i < addcnt; i++)
                strRtn += " ";
        } catch(Exception e) {
        }
        
        return strRtn;
    }
        
    /**
     현재 시간을 지정된 포맷으로 리턴한다.
     @param sFormat ex)[yyyyMMddHHmmss]=>20041115130911
     
     @return String 지정되 시간
     */
    public static String getDateFormatString(String sFormat) {
        if(sFormat == null || sFormat.equals("")) return "";
        
        SimpleDateFormat sdf1 = new SimpleDateFormat(sFormat, Locale.KOREA);
        String           s1   = sdf1.format(new Date());
        
        return s1;
    }
    
    public static char[] CharPadZero(char[] indata, int len) {
        return StrUtil.String2Char(StrUtil.paddingZero(StrUtil.Char2String(indata), len));
    }
    
    public static char[] StringPadZero(String indata, int len) {
        return StrUtil.String2Char(StrUtil.paddingZero(indata, len));
    }
    
    public static char[] IntegerPadZero(int indata, int len) {
        return StrUtil.StringPadZero(Integer2String(indata), len);
    }
    
    public static char[] StringPadBlank(String indata, int len) {
        return StrUtil.String2Char(StrUtil.paddingBlank(indata, len));
    }
    
    /**
     주어진 길이보다 길이가 작은 문자열을 앞에 0을 붙여 패딩한다 <BR>
     @param str 문자열
     @param len 길이
     
     @return 앞에 '0'으로 패딩된 문자열을 리턴한다. 단, 주어진 길이보다 크거나 같으면 원본문자열을 그대로 리턴한다
     */
    
    public static String paddingZero(String str, int len) {
        int    strLen = str.length();
        int    cab    = 0;
        String tmp    = "";
        if(strLen >= len) {
            return str;
        } else {
            cab = len - strLen;
        }
        
        for(int ii = 0; ii < cab; ii++) {
            tmp = "0" + tmp;
        }
        
        return tmp + str;
    }
    
    /**
     메시지포맷 적용
     @param Form MessageFormat
     @param String1 입력변수
     
     @return 포멧적용문자열
     */
    public static String msgForm(String Form, String[] String1) {
        MessageFormat msgFormat = new MessageFormat(Form);
        return msgFormat.format(String1);
    }
    
    /**
     메시지포맷 적용
     @param strKey KeyString
     @param strValue ValueString
     
     @return 포멧적용문자열
     */
    public static String msgForm(String strKey, String strValue) {
        return MessageFormat.format("%s : %s", strKey, strValue);
    }
    
    /**
     문자열길이 만큼 널("\0")문자를 추가한 스트링을 리턴합니다.
     @param poi 기준문자열길이
     @param bstr 체크문자열
     
     @return
     */
    public static String concatNull(int poi, String bstr) {
        bstr = bstr.trim();
        String tempstr = "";
        if(bstr.toCharArray().length < poi) {
            for(int i = 0; i < (poi - bstr.toCharArray().length); i++) {
                tempstr += "\0";
            }
        }
        return bstr + tempstr;
    }
    
    public static String[] splite2(String st, String key, int size) {
        if(st == null) return new String[0];
        
        String[] arr = new String[size];
        
        for(int i = 0; i < size; i++) {
            int nIndex = st.indexOf(key);
            if(nIndex == 0) {
                arr[i] = "";
                st = st.substring(1);
            } else {
                if(nIndex < 0) {
                    arr[i] = st;
                } else {
                    arr[i] = st.substring(0, nIndex);
                    st = st.substring(nIndex + 1);
                }
            }
        }
        
        return arr;
    }
    
    /**
     날짜문자열(yyyymmdd)에 날짜를 더한(혹은 뺀) 일자를 구함 <br>
     @param str yyyyMMdd 형식의 날짜 문자열
     @param days 더할, 혹은 뺄 날수
     
     @return yyyyMMdd 형식의 8자리 날짜
     
     @throws ParseException return 날짜문자열 형식이 잘못되었을 경우
     */
    public static String addDays(String str, int days) throws ParseException {
    
        SimpleDateFormat fmt  = new SimpleDateFormat("yyyyMMdd");
        Date             date = fmt.parse(str);
        date.setTime(date.getTime() + (long) days * 1000L * 60L * 60L * 24L);
        return fmt.format(date);
    }

    /**
     Removes white space from left ends of the string argument.
     @param s 원본문자열
     
     @return a left trimed string, with white space removed from the front.
     */
    public static String ltrim(String s) {
        if(s == null) return "";
        for(int i = 0; i < s.length(); i++)
            if(" \t\n\r\f".indexOf(s.charAt(i)) == - 1) return s.substring(i);
        
        return "";
    }
    
    /**
     Removes white space from left ends of the string argument.
     @param s 원본문자열
     
     @return a right trimed string, with white space removed from the end.
     */
    public static String rtrim(String s) {
        if(s == null || s.equals("")) return "";
        for(int i = s.length() - 1; i >= 0; i--)
            if(" \t\n\r\f".indexOf(s.charAt(i)) == - 1) return s.substring(0, i);
        
        return "";
    }
    
    /**
     원본문자열(str)에서 캐릭터(ch)를 찾아 제거한다 <BR>
     @param str 입력문자열
     @param ch 제거할 문자
     
     @return 변환된 문자열을 리턴한다
     */
    
    public static String removeChar(String str, char ch) {
        return removeChar(str, String.valueOf(ch));
    }
    
    /**
     원본문자열(str)에서 문자열(ch)을 찾아 제거한다 <BR>
     @param str 입력문자열
     @param ch 제거할 문자열
     
     @return 변환된 문자열을 리턴한다
     */
    
    public static String removeChar(String str, String ch) {
        StringBuffer    buff  = new StringBuffer();
        StringTokenizer token = new StringTokenizer(str, ch);
        while(token.hasMoreTokens()) {
            buff.append(token.nextToken());
        }
        
        return buff.toString();
    }
    
    //////////////////////////////////////////////////// conv     ///////////////////////////////////////////////////////////////
    
    
    /**
     문자열을 문자배열로 변환
     @param inString
     
     @return
     */
    public static char[] String2Char(String inString) {
        if(inString == null) return null;
        char[] charArray = inString.toCharArray();
        return charArray;
    }
    
    /**
     문자배열을 문자열로 변환
     @param inCArray
     
     @return
     */
    public static String Char2String(char[] inCArray) {
        if(inCArray == null) return null;
        return new String(inCArray);
    }
    
    /**
     문자열을 숫자로 변환
     @param inValue 입력문자열
     
     @return 숫자
     */
    public static int String2Integer(String inValue) {
        if(inValue == null || inValue.length() < 1) return 0;
        int nRet = Integer.parseInt(inValue);
        return nRet;
    }
    
    /**
     문자배열을 숫자로 변환
     @param inValue
     
     @return 숫자
     */
    public static int Char2Integer(char[] inValue) {
        if(inValue == null) return 0;
        int nRet = Integer.parseInt(Char2String(inValue));
        return nRet;
    }
    
    /**
     숫자를 문자열로 변환
     @param inValue
     
     @return
     */
    public static String Integer2String(int inValue) {
        return Integer.toString(inValue);
    }
    
    public static String Double2String(double inValue) {
        return Double.toString(inValue);
    }
    public static Double String2Double(String inValue) {
        return Double.parseDouble(NVL(inValue,"0"));
    }
    public static Double Char2Double(char[] inValue) {
        Double retValue = 0.0;
        String sTemp;
        try {
            if(inValue.length <= 0 ) {
                return 0.0;
            }
            sTemp = new String(inValue);
            retValue = Double.parseDouble(sTemp);
        } catch(Exception e) {
        }
        return retValue;
    }

    /**
     숫자를 문자배열로 변환
     @param inValue
     
     @return
     */
    public static char[] Integer2Char(int inValue) {
        return String2Char(Integer2String(inValue));
    }
    
    public static boolean arrayFind(String[] strArray, String strSearch) {
        final List<String> list = new ArrayList<String>();
        boolean retValue = true;
        try {
            Collections.addAll(list, strArray);
            retValue = list.contains(strSearch);
        } catch(Exception e) {
            retValue = false;
        }
        return retValue;
    }
    
    /**
     parseTrnInfo
     <p>
     TB_TRM_INTF_HIS 테이블의 SRM_DAT_TXT(keos거래데이터) 를 Parsing하여 특정항목 값을 추출한다.
     SRM_DAT_TXT형식 : 항목명=값^항목명=값..... (항목 구분자 = '^')
     @param srcStr String
     @param findStr String
     
     @return findStr의 값
     
     @throws
     */
    public static String parseTrnInfo(String srcStr, String findStr) {
        String str = "";
        int startIndex;
        int endIndex;
        
        if(srcStr == null || "".equals(srcStr)) {
            return str;
        }
        startIndex = srcStr.indexOf(findStr);
        if(startIndex < 0) {
            return str;
        }
        
        startIndex = startIndex + findStr.length() + 1;
        endIndex = srcStr.indexOf("^", startIndex);
        
        // 수표이면... 2015.06.12 Badboy
        if("bchk_inf".equals(findStr)) {
            endIndex = srcStr.indexOf("^", endIndex + 1);
        }
        
        if(endIndex >= 0) {
            str = srcStr.substring(startIndex, endIndex);
        } else {
            str = srcStr.substring(startIndex);
        }
        
        return str;
    }
    
    public static int length2(String str){
        int en = 0;
        int ko = 0;
        int etc = 0;
        char[] val = str.toCharArray();
        for(int i=0; i<val.length; i++) {
            if(val[i] >= 'A' && val[i] <= 'Z'){
                en++;
            }else if(val[i] >= '\uAC00' && val[i] <= '\uD7A3'){
                ko++;
                ko++;
            }else{
                etc++;
            }
        }
        return en + ko + etc;
    }
    
    public static String substringb(String strData, int iStartPos, int iByteLength) {
        byte[] bytTemp = null;
        int iRealStart = 0;
        int iRealEnd = 0;
        int iLength = 0;
        int iChar = 0;
        
        try {
            bytTemp = strData.getBytes("MS949");
            iLength = bytTemp.length;
            
            for(int i=0; i<iLength; i++){
                iChar = (int)bytTemp[i];
            }
            
            for(int i=0; i<iLength; i++){
                if(iStartPos <= i){
                    break;
                }
                iChar = (int)bytTemp[i];
                if(iChar > 127 || iChar<0) {
                    iRealStart++;
                    i++;
                }else{
                    iRealStart++;
                }
            }
            
            iRealEnd = iRealStart;
            int iEndLength = iRealStart + iByteLength;
            for(int j=iRealStart; j<iEndLength; j++){
                iChar = (int)bytTemp[j+(iStartPos-iRealStart)];
                if(iChar > 127 || iChar<0) {
                    iRealEnd++;
                    j++;
                }else{
                    iRealEnd++;
                }
            }
            
        }catch(Exception e){
            //logger.info("[subString] Error :: "+e.getMessage());
        }
        
        return strData.substring(iRealStart, iRealEnd);
    }
}
