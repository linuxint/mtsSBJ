package com.devkbil.mtssbj.datacart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Getter
@Setter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ApiModel(value = "데이터 장바구니 : Data Cart", description = "데이터 장바구니")
@XmlRootElement(name = "DataCart")
@XmlType(propOrder = {"dataCartId", "inspReqId", "catgMstDataId", "catgMstDataNm", "categoryDesc", "categoryDescArr", "keywordNm", "keywordNmArr", "chargDeptCd", "chargDeptNm", "dataStwdId", "dataStwdNm", "regDt", "viewCount"})
public class DataCart implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "카트 seq")
    private Integer dataCartId; // 카트 seq
    @ApiModelProperty(value = "열람 요청 아이디")
    private String inspReqId; // 열람 요청 아이디
    @ApiModelProperty(value = "카타로그 마스터 데이터 아이디")
    private String catgMstDataId; // 카타로그 마스터 데이터 아이디
    @ApiModelProperty(value = "카타로그 마스터 데이터 명")
    private String catgMstDataNm; // 카타로그 마스터 데이터 명
    @ApiModelProperty(value = "카테고리")
    private String categoryDesc; // 카테고리
    @ApiModelProperty(value = "카테고리배열")
    private String[] categoryDescArr; // 카테고리배열
    @ApiModelProperty(value = "연관키워드")
    private String keywordNm; // 연관키워드
    @ApiModelProperty(value = "연관키워드배열")
    private String[] keywordNmArr; // 연관키워드배열
    @ApiModelProperty(value = "부서코드")
    private String chargDeptCd; // 부서코드
    @ApiModelProperty(value = "부서명")
    private String chargDeptNm; // 부서명
    @ApiModelProperty(value = "데이터관리자아이디")
    private String dataStwdId; // 데이터관리자아이디
    @ApiModelProperty(value = "데이터관리자명")
    private String dataStwdNm; // 데이터관리자명
    @ApiModelProperty(value = "데이터등록일")
    private String regDt; // 데이터등록일
    @ApiModelProperty(value = "조회수")
    private String viewCount; // 조회수
    
    public String[] getCategoryDescArr() {
        return categoryDesc.split(",");
    }
    
    public String[] getKeywordNmArr() {
        return keywordNm.split(",");
    }
}