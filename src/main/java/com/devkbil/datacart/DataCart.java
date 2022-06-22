package com.devkbil.datacart;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Getter
@Setter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DataCart implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer dataCartId; // 카트 seq
    private String inspReqId; // 열람 요청 아이디
    private String catgMstDataId; // 카타로그 마스터 데이터 아이디
    private String catgMstDataNm; // 카타로그 마스터 데이터 명
    private String categoryDesc; // 카테고리
    private String[] categoryDescArr; // 카테고리배열
    private String keywordNm; // 연관키워드
    private String[] keywordNmArr; // 연관키워드배열
    private String chargDeptCd; // 부서코드
    private String chargDeptNm; // 부서
    private String dataStwdId; // 데이터스튜어드아이디
    private String dataStwdNm; // 데이터스튜어드명
    private String regDt; // 데이터등록일
    private String viewCount; // 조회수
    
    public String[] getCategoryDescArr() {
        return categoryDesc.split(",");
    }
    
    public String[] getKeywordNmArr() {
        return keywordNm.split(",");
    }
}