package com.devkbil.mtssbj.search;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "통합검색 : FullTextSearchVO")
@XmlRootElement(name = "FullTextSearchVO")
@XmlType(propOrder = {"searchRange", "searchTerm", "searchTerm1", "searchTerm2", "userno"})
@Getter
@Setter
public class FullTextSearchVO extends SearchVO {

    @Schema(description = "검색 대상 필드 - 작성자, 제목, 내용등")
    private String searchRange = "";           // 검색 대상 필드 - 작성자, 제목, 내용등
    @Schema(description = "기간 조회 사용여부")
    private String searchTerm = "";            // 기간 조회 사용여부
    @Schema(description = "시작일자")
    private String searchTerm1 = "";           // 시작일자
    @Schema(description = "종료일자")
    private String searchTerm2 = "";           // 종료일자
    @Schema(description = "사용자 권한 테스트용")
    private String userno = "";                 // 사용자 권한 테스트용

}
