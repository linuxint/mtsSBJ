package com.devkbil.mtssbj.board;

import com.devkbil.mtssbj.common.PagingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "게시글 검색 : BoardSearchVO")
@XmlRootElement(name = "BoardSearchVO")
@XmlType(propOrder = {"bgno", "searchKeyword", "searchType", "searchTypeArr", "searchExt1"})
@Getter
@Setter
public class BoardSearchVO extends PagingVO {

    @Schema(description = "게시판 그룹")
    private String bgno;                       // 게시판 그룹

    @Schema(description = "검색 키워드")
    private String searchKeyword = "";         // 검색 키워드

    @Schema(description = "검색 필드: 제목, 내용")
    private String searchType = "";            // 검색 필드: 제목, 내용

    @Schema(description = "검색 필드를 배열로 변환")
    private String[] searchTypeArr;            // 검색 필드를 배열로 변환

    @Schema(description = "검색 확장 필드")
    private String searchExt1 = "";            // 검색 확장 필드

    public String[] getSearchTypeArr() {
        return searchType.split(",");
    }
}
