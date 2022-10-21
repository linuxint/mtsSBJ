package com.dyinfo.common.string;

import java.util.Arrays;
import java.util.HashMap;
/**
 Created by KBIL on 2016-08-05. */
public class StrSearch {
    
    /**
     데이터검색(String Array in Data)
     @param stringArray StringArray
     @param stringData StringData
     @return 존재여부(true/false)
     */
    public static boolean searchData(String[] stringArray, String stringData) {
        boolean retValue = false;
        try {
            if(StrUtil.isNULL(stringData)) {
                return false;
            }
            if(stringArray.length == 0) {
                return false;
            }
            retValue = Arrays.asList(stringArray).contains(stringData);
            
        } catch(Exception e) {
            retValue = false;
        } finally {
        }
        return retValue;
    }
    
    /**
     HashMap 데이터 검색
     @param Dat hashmap source
     @param key search key
     @return value of key
     */
    public static String getMapData(HashMap<String, String> Dat, String key) {
        String retValue = "";
        try {
            if(Dat.size() <= 0) {
                retValue = "";
            } else if(Dat.containsKey(key)  && !Dat.containsValue(key)) {
                retValue = Dat.get(key).trim();
            }
        } catch(Exception e) {
            retValue = "";
            //logger.error("getMapData "+e.getMessage() + " " + key);
        }
        return retValue;
    }
    
    /**
     HashMap 데이터 검색후 없을때 기본값 세팅
     @param Dat hashmap source
     @param key search key
     @param defData default value
     @return value of key
     */
    public static String getMapData(HashMap<String, String> Dat, String key, String defData) {
        String retValue;
        try {
            retValue = getMapData(Dat, key).trim();
            if(StrUtil.isNULL(retValue)) {
                retValue = defData;
            }
        }catch(Exception e) {
            retValue = "";
        }
        return retValue;
    }
    
}
