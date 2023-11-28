package com.devkbil.mtssbj.common.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.devkbil.mtssbj.common.Context;
import com.devkbil.mtssbj.common.exception.ParamException;
import com.devkbil.mtssbj.common.exception.SysException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class DataMap extends HashMap implements Serializable {
    private Context context;

    public DataMap() {
        super(500);
    }

    public DataMap(int initialCapacity) {
        super(initialCapacity);
    }

    public DataMap(Map map) {
        super(map);
    }

    public Object get(String name) {
        Object obj = super.get(name);
        return obj;
    }

    public void put(String name, int value) {
        if (!super.containsKey(name)) {
            put(name, Integer.valueOf(value));
        } else {
            //new BizException("Duplicate");
            put(name, Integer.valueOf(value));
        }
    }

    public void put(String name, long value) {
        if (!super.containsKey(name)) {
            put(name, Long.valueOf(value));
        } else {
            //new BizException("Duplicate");
            put(name, Long.valueOf(value));
        }
    }

    public void put(String name, float value) {
        if (!super.containsKey(name)) {
            put(name, Float.valueOf(value));
        } else {
            //new BizException("Duplicate");
            put(name, Float.valueOf(value));
        }
    }

    public void put(String name, double value) {
        if (!super.containsKey(name)) {
            put(name, Double.valueOf(value));
        } else {
            //new BizException("Duplicate");
            put(name, Double.valueOf(value));
        }
    }

    public void put(String name, boolean value) {
        if (!super.containsKey(name)) {
            put(name, Boolean.valueOf(value));
        } else {
            //new BizException("Duplicate");
            put(name, Boolean.valueOf(value));
        }
    }

    public void put(String name, String value) {
        if (!super.containsKey(name)) {
            super.put(name, value);
        } else {
            //new BizException("Duplicate");
            super.put(name, value);
        }
    }

    public String getString(String paramName) {
        Object obj = super.get(paramName);
        if (obj == null) {
            return null;
        }
        if (((obj instanceof Collection)) || ((obj instanceof Object[]))) {
            throw new SysException("The object registered in " + paramName + " is not a String convertible object." + obj.getClass().getName());
        }
        return obj.toString();
    }

    public int getInt(String paramName) throws NumberFormatException {
        String value = getString(paramName);
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public long getLong(String paramName) throws NumberFormatException {
        String value = getString(paramName);
        if (value == null) {
            return 0L;
        }
        return Long.parseLong(value);
    }

    public boolean getBoolean(String paramName) {
        String value = getString(paramName);
        if (value == null) {
            return false;
        }
        return NewStringUtil.getBoolean(value, false);
    }

    public String getParameter(String paramName) throws ParamException {
        String str = getString(paramName);
        if (NewStringUtil.isNullNotTrim(str)) {
            throw new ParamException(paramName);
        }
        return str;
    }

    public String getParameter(String paramName, String defaultValue) {
        String str = getString(paramName);
        if (NewStringUtil.isNullNotTrim(str)) {
            return defaultValue;
        }
        return str;
    }

    public String[] getParameterValues(String paramName) {
        String[] strArr = getStringArray(paramName);
        return strArr;
    }

    public int getIntParameter(String paramName) throws ParamException {
        String str = getParameter(paramName);

        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new SysException("It is not a number format -" + str);
        }
        return i;
    }

    public int getIntParameter(String paramName, int defaultValue) throws ParamException {
        String str = getParameter(paramName, "");
        if (NewStringUtil.isNull(str)) {
            return defaultValue;
        }
        return getIntParameter(paramName);
    }

    public Object getObjectParameter(String paramName) throws ParamException {
        Object obj = get(paramName);
        if (obj == null) {
            throw new ParamException(paramName);
        }
        return obj;
    }

    public Object getObjectParameter(String paramName, Object defaultObj) {
        Object obj = get(paramName);
        if (obj == null) {
            return defaultObj;
        }
        return obj;
    }

    public double getDoubleParameter(String paramName) throws ParamException {
        String str = getParameter(paramName);

        double i = 0.0D;
        try {
            i = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new SysException("It is not a number format -" + str);
        }
        return i;
    }

    public double getDoubleParameter(String paramName, double defaultValue) throws ParamException {
        String str = getParameter(paramName, "");
        if (NewStringUtil.isNull(str)) {
            return defaultValue;
        }

        double i = 0.0D;
        try {
            i = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new SysException("It is not a number format -" + str);
        }
        return i;
    }

    public String[] getStringArray(String paramName) {
        Object obj = super.get(paramName + "Array");
        String param = null;
        String[] params = null;
        if ((obj instanceof String[])) {
            return (String[])obj;
        }
        if ((obj instanceof Collection)) {
            return null;
        }
        if ((obj instanceof Object[])) {
            Object[] objArray = new Object[0];
            String[] array = new String[objArray.length];
            for (int i = 0; i < objArray.length; i++) {
                if (objArray[i].toString() != null) {
                    array[i] = objArray[i].toString();
                } else {
                    array[i] = "";
                }
            }
            return array;
        }
        if (obj != null) {
            String[] array = new String[1];
            array[0] = obj.toString();
            return array;
        }
        param = getParameter(paramName, null);
        if (param == null) {
            log.debug("There is no object registered as " + paramName + " in Map.");
            return null;
        }
        params = new String[1];
        params[0] = param;
        return params;
    }
    /*public String[] getStringArray(String paramName)
    {
        Object obj = super.get(paramName + "Array");
        String param = null;
        String[] params = (String[]) null;
        if ((obj instanceof String[])) {
            return (String[]) obj;
        }
        if ((obj instanceof Collection)) {
            return null;
        }
        if ((obj instanceof Object[])) {
            Object[] objArray = (Object[]) obj;
            String[] array = new String[objArray.length];
            for (int i = 0; i < objArray.length; i++) {
                try {
                    array[i] = objArray[i].toString();
                }
                catch (NullPointerException e) {
                    array[i] = "";
                }
            }
            return array;
        }
        if (obj != null) {
            String[] array = new String[1];
            array[0] = obj.toString();
            return array;
        }
        param = getParameter(paramName, null);
        if (param == null) {
            LogManager.getLogger("fw").debug("There is no object registered as " + paramName + " in Map.");
            return null;
        }
        params = new String[1];
        params[0] = param;
        return params;
    }*/

    public Object clone() {
        DataMap dataMap = new DataMap();
        dataMap.putAll((Map)super.clone());

        dataMap.setContext(getContext());
        return dataMap;
    }

    public void putAll(Map m) {
        putAll(m, true);
    }

    public void putAll(Map m, boolean isContextCopy) {
        // 18.06.07 CWS 수정 - Object가 null이 아닐때만 putAll 실행.
        if (m != null) {
            super.putAll(m);
            if ((isContextCopy) && ((m instanceof DataMap))) {
                DataMap map = null;
                if (map.getContext() != null) {
                    setContext(map.getContext());
                }
            }
        }
    }

    public void setContext(Context context) {
        if (context != null) {
            this.context = context;
        }
    }

    public String toString() {
        if (isEmpty()) {
            return "DataMap is empty =========================";
        }
        StringBuffer buf = new StringBuffer(2000);
        Set keySet = super.keySet();
        Iterator i = keySet.iterator();
        String key = null;
        Object item = null;
        while (i.hasNext()) {
            try {
                key = i.next().toString();
                if ((!"q".equals(key)) && (!"p".equals(key))) {
                    item = get(key);
                    if (item == null) {
                        buf.append(key + "=null\n");
                    } else if ((item instanceof String)) {
                        if ((item == null) || (((String)item).length() == 0)) {
                            item = "";
                        } else if (isMaskNeeded(key)) {
                            item = "****";
                        }
                        buf.append(key + "=[" + item + "]\n");
                    } else if (((item instanceof Integer)) || ((item instanceof Long)) || ((item instanceof Double)) || ((item instanceof Float))
                            || ((item instanceof Boolean))) {
                        buf.append(key + "=[" + item + "]\n");
                    } else if ((item instanceof String[])) {
                        String[] data = new String[0];
                        buf.append(key + "=[");
                        for (int j = 0; j < data.length; j++) {
                            if (isMaskNeeded(key)) {
                                buf.append("****");
                            } else {
                                buf.append(data[j]);
                            }
                            if (j < data.length - 1) {
                                buf.append(",");
                            }
                        }
                        buf.append("] Array Size:" + data.length + " \n");
                    } else {
                        buf.append(key + "=[" + item + "] ClassName:" + item.getClass().getName() + "\n");
                    }
                }
            } catch (Exception localException) {
            }
        }
        buf.append("end of DataMap info ============================\n");
        return buf.toString();
    }

    public String getDataMapInfo() {
        return toString();
    }

    public String getRequestURI() {
        return (String)get("srnNo");
    }

    public void setRequestURI(String uri) {
        put("srnNo", uri);
    }

    public String toLog() {
        if (isEmpty()) {
            return "No information stored in the DataMap";
        }
        StringBuffer buf = new StringBuffer(2000);
        Set keySet = super.keySet();
        Iterator i = keySet.iterator();
        String key = null;
        Object item = null;
        while (i.hasNext()) {
            key = (String)i.next();
            item = get(key);
            if (item == null) {
                buf.append(key + "=null" + "<BR/>");
            } else if ((item instanceof String)) {
                if (isMaskNeeded(key)) {
                    item = "****";
                }
                item = NewStringUtil.escapeXml(item.toString());

                buf.append(key + "=[" + item + "]<BR>");
            } else if ((item instanceof String[])) {
                String[] data = new String[0];
                buf.append(key + "=[");
                for (int j = 0; j < data.length; j++) {
                    if (isMaskNeeded(key)) {
                        buf.append("****");
                    } else {
                        buf.append(data[j]);
                    }
                    if (j < data.length - 1) {
                        buf.append(",");
                    }
                }
                buf.append("] Array Size:" + data.length + " <BR/>");
            } else {
                buf.append(key + "=[" + item + "] ClassName:" + item.getClass().getName() + "<BR/>");
            }
        }
        return buf.toString();
    }

    private boolean isMaskNeeded(String key) {
        // TODO PAN
        // 2017.12.06 : change --------------------- start
        //if (key.toUpperCase().indexOf("CDNO") != -1) {
        //    return true;
        //}
        // 2017.12.06 : change --------------------- end

        if (key.toUpperCase().indexOf("CARD_NO") != -1) {
            return true;
        }
        if (key.toUpperCase().indexOf("PASSWORD") != -1) {
            return true;
        }
        if (key.toUpperCase().indexOf("PWD") != -1) {
            return true;
        }
        if (key.equals("RNO")) {
            return true;
        }
        if (key.equals("RRNO")) {
            return true;
        }
        return key.equals("CARD_PASSWORD");
    }

    public String getUserId() {
        return (String)get("USER_ID");
    }

    public void setUserId(String userId) {
        put("USER_ID", userId);
    }

    public String getUserName() {
        return (String)get("USER_NAME");
    }

    public void setUserName(String userName) {
        put("USER_NAME", userName);
    }

    public String getContextName() {
        return getString("_CONTEXT_NAME");
    }

    public void setContextName(String contextName) {
        put("_CONTEXT_NAME", contextName);
    }

    /*
    public void putDataSet(String name, DataSet ds)
    {
        put("_BeginLoop_" + name, ds);
    }
    */
}
