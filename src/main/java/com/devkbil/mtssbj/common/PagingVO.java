package com.devkbil.mtssbj.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "페이지수 : PageVO")
@XmlRootElement(name = "PageVO")
@XmlType(propOrder = {"displayRowCount", "rowStart", "rowEnd", "totPage", "totRow", "page", "pageStart", "pageEnd"})
@Getter
@Setter
public class PagingVO {

    @Schema(description = "출력할 데이터 개수")
    private Integer displayRowCount = 10;           // 출력할 데이터 개수
    @Schema(description = "시작행번호")
    private Integer rowStart;                       // 시작행번호
    @Schema(description = "종료행 번호")
    private Integer rowEnd;                         // 종료행 번호
    @Schema(description = "전체 페이수")
    private Integer totPage;                        // 전체 페이수
    @Schema(description = "전체 데이터 수")
    private Integer totRow = 0;                     // 전체 데이터 수
    @Schema(description = "현재 페이지")
    private Integer page;                           // 현재 페이지
    @Schema(description = "시작페이지")
    private Integer pageStart;                      // 시작페이지
    @Schema(description = "종료페이지")
    private Integer pageEnd;                        // 종료페이지

    /**
     * 현재 페이지 번호.
     */
    public Integer getPage() {
        if (page == null || page == 0) {
            page = 1;
        }

        return page;
    }

    /**
     * 전체 데이터 개수(total)를 이용하여 페이지수 계산.
     */
    public void pageCalculate(Integer total) {
        getPage();
        totRow = total;
        totPage = total / displayRowCount;

        if (total % displayRowCount > 0) {
            totPage++;
        }

        pageStart = (page - (page - 1) % 10);
        pageEnd = pageStart + 9;
        if (pageEnd > totPage) {
            pageEnd = totPage;
        }

        rowStart = ((page - 1) * displayRowCount) + 1;
        rowEnd = rowStart + displayRowCount - 1;
    }

}


