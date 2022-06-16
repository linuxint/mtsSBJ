package com.devkbil.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "검색 : SearchVO", description = "SearchVO")
@XmlRootElement(name = "SearchVO")
@XmlType(propOrder = {"searchKeyword", "searchType", "searchTypeArr", "searchExt1", "userno"})
@Getter
@Setter
public class SearchVO extends PageVO {
    
    @ApiModelProperty(value = "검색 키워드")
    private String searchKeyword = "";         // 검색 키워드
    @ApiModelProperty(value = "검색 필드: 제목, 내용")
    private String searchType = "";            // 검색 필드: 제목, 내용
    @ApiModelProperty(value = "검색 필드를 배열로 변환")
    private String[] searchTypeArr;            // 검색 필드를 배열로 변환
    @ApiModelProperty(value = "검색 확장 필드")
    private String searchExt1 = "";            // 검색 확장 필드
    @ApiModelProperty(value = "사용자")
    private String userno;
    
    public String[] getSearchTypeArr() {
        return searchType.split(",");
    }
}
 