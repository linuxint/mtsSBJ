package com.dyinfo.common.string;

import java.util.List;
/**
 Created by KBIL on 2016-08-05. */
public class StrConv {
    
    public String listToString(List rList, char delimiter) {
        StringBuffer retValue = new StringBuffer(1000);
        try {
            for(int nCnt = 0; nCnt < rList.size(); nCnt++) {
                if(nCnt == 0) {
                    retValue.append((String) rList.get(nCnt));
                } else {
                    retValue.append(delimiter);
                    retValue.append(rList.get(nCnt));
                }
            }
        } catch(Exception e) {
        } finally {
        }
        return retValue.toString();
    }
    
}
