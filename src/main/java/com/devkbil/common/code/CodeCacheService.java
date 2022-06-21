package com.devkbil.common.code;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CodeCacheService")
public class CodeCacheService { //extends EgovAbstractServiceImpl
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CodeCacheDAO CodeCacheDAO;

    private static List<Map> codeGroup = new ArrayList<Map>();
    private static List<Map> code = new ArrayList<Map>();

    /**
     * 공통코드 메모리 등록
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void resetCodeList() throws Exception {

        if (codeGroup.isEmpty()) {
            synchronized (codeGroup) {
                if (codeGroup.isEmpty()) {

                    List<Map> MapList;
                    // 코드 그룹
                    MapList = (ArrayList<Map>)CodeCacheDAO.selectListCodeGroup();	// codecd,  codenm

                    codeGroup.clear();
                    codeGroup.addAll(MapList);

                    // 상세코드
                    MapList = (ArrayList<Map>)CodeCacheDAO.selectListCode();	// pcodecd, codecd, codenm
                    code.clear();
                    code.addAll(MapList);

                }
            }
        }
    }

    public static void clear() throws Exception {
        codeGroup.clear();
        code.clear();
    }

    /**
     * @param codecd 코드그룹 ID
     * @return String 그룹코드명
     */
    public static String getCodeGroupNm(String codecd) throws Exception {
        String returnVal = "";
        Iterator<Map> iterator = codeGroup.iterator();
        while (iterator.hasNext()) {
            Map Map = (Map) iterator.next();
            if(codecd.equals((String)Map.get("codecd"))) {
                returnVal = (String)Map.get("codenm");
                break;
            }
        }
        return returnVal;
    }

    /**
     * @param pcodecd 코드그룹 ID
     * @param detailCode code 코드
     * @return String 상세코드명
     */
    public static String getCodeNm(String pcodecd, String detailCode) throws Exception {
        String returnVal = "";
        Iterator<Map> iterator = code.iterator();
        while (iterator.hasNext()) {
            Map Map = (Map) iterator.next();
            if(pcodecd.equals((String)Map.get("pcodecd")) && detailCode.equals((String)Map.get("codecd")) ) {
                returnVal = (String)Map.get("codenm");
                break;
            }
        }
        return returnVal;
    }

    /**
     * @param pcodecd 코드그룹 ID
     * @return List<Map> 코드그룹에 속한 상세 코드 List
     */
    public static List<Map> getCode(String pcodecd) throws Exception {

        List<Map> returnVal = new ArrayList<Map>();

        Iterator<Map> iterator = code.iterator();
        while (iterator.hasNext()) {
            Map Map = (Map) iterator.next();
            if(pcodecd.equals((String)Map.get("pcodecd"))) {
                returnVal.add(Map);
            }
        }
        return returnVal;
    }

    /**
     * @param
     * @return List<Map> 상세 코드 List
     */
    public static List<Map> getCode() throws Exception {
        return code;
    }
}