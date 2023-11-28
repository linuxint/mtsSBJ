package com.devkbil.mtssbj.search;

import com.devkbil.mtssbj.common.PagingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "검색 : SearchVO")
@XmlRootElement(name = "SearchVO")
@XmlType(propOrder = {"searchKeyword", "searchType", "searchTypeArr", "searchExt1", "userno"})
@Getter
@Setter
public class SearchVO extends PagingVO {

    @Schema(description = "검색 키워드")
    private String searchKeyword = "";         // 검색 키워드
    @Schema(description = "검색 필드: 제목, 내용")
    private String searchType = "";            // 검색 필드: 제목, 내용
    @Schema(description = "검색 필드를 배열로 변환")
    private String[] searchTypeArr;            // 검색 필드를 배열로 변환
    @Schema(description = "검색 확장 필드")
    private String searchExt1 = "";            // 검색 확장 필드
    @Schema(description = "사용자")
    private String userno;

    public String[] getSearchTypeArr() {
        return searchType.split(",");
    }
}
