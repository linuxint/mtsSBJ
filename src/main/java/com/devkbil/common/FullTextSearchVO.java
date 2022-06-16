package com.devkbil.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "통합검색 : FullTextSearchVO", description = "FullTextSearchVO")
@XmlRootElement(name = "FullTextSearchVO")
@XmlType(propOrder = {"searchRange", "searchTerm", "searchTerm1", "searchTerm2", "userno"})
@Getter
@Setter
public class FullTextSearchVO extends SearchVO {
    
    @ApiModelProperty(value = "검색 대상 필드 - 작성자, 제목, 내용등")
    private String searchRange = "";           // 검색 대상 필드 - 작성자, 제목, 내용등
    @ApiModelProperty(value = "기간 조회 사용여부")
    private String searchTerm = "";            // 기간 조회 사용여부
    @ApiModelProperty(value = "시작일자")
    private String searchTerm1 = "";           // 시작일자
    @ApiModelProperty(value = "종료일자")
    private String searchTerm2 = "";           // 종료일자
    @ApiModelProperty(value = "사용자 권한 테스트용")
    private String userno = "";                 // 사용자 권한 테스트용
    
}
 