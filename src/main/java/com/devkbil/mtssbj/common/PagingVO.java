package com.devkbil.mtssbj.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "페이지수 : PageVO", description = "페이지수")
@XmlRootElement(name = "PageVO")
@XmlType(propOrder = {"displayRowCount", "rowStart", "rowEnd", "totPage", "totRow", "page", "pageStart", "pageEnd"})
@Getter
@Setter
public class PagingVO {
    
    @ApiModelProperty(value = "출력할 데이터 개수")
    private Integer displayRowCount = 10;           // 출력할 데이터 개수
    @ApiModelProperty(value = "시작행번호")
    private Integer rowStart;                       // 시작행번호
    @ApiModelProperty(value = "종료행 번호")
    private Integer rowEnd;                         // 종료행 번호
    @ApiModelProperty(value = "전체 페이수")
    private Integer totPage;                        // 전체 페이수
    @ApiModelProperty(value = "전체 데이터 수")
    private Integer totRow = 0;                     // 전체 데이터 수
    @ApiModelProperty(value = "현재 페이지")
    private Integer page;                           // 현재 페이지
    @ApiModelProperty(value = "시작페이지")
    private Integer pageStart;                      // 시작페이지
    @ApiModelProperty(value = "종료페이지")
    private Integer pageEnd;                        // 종료페이지
    
    /**
     * 현재 페이지 번호.
     */
    public Integer getPage() {
        if(page == null || page == 0) {
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
        
        if(total % displayRowCount > 0) {
            totPage++;
        }
        
        pageStart = (page - (page - 1) % 10);
        pageEnd = pageStart + 9;
        if(pageEnd > totPage) {
            pageEnd = totPage;
        }
        
        rowStart = ((page - 1) * displayRowCount) + 1;
        rowEnd = rowStart + displayRowCount - 1;
    }
    
}


