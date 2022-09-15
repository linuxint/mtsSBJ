package com.devkbil.mtssbj.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "첨부파일 : FileVO", description = "첨부파일")
@XmlRootElement(name = "첨부파일")
@XmlType(propOrder = {"fileno", "parentPK", "filename", "realname", "filesize"})
@Getter
@Setter
public class FileVO {
    @ApiModelProperty(value = "파일번호")
    private Integer fileno;
    @ApiModelProperty(value = "파일 상위키 (게시판,프로젝트)")
    private String parentPK;
    @ApiModelProperty(value = "서버 파일명")
    private String filename;
    @ApiModelProperty(value = "업로드 파일명")
    private String realname;
    @ApiModelProperty(value = "파일사이즈")
    private long filesize;
    
    @ApiModelProperty(value = "파일fullpath")
    private String uri;
    @ApiModelProperty(value = "파일경로")
    private String filepath;
    @ApiModelProperty(value = "파일드라이브")
    private String fileroot;
    
    /**
     * 파일 크기를 정형화하기.
     */
    public String size2String() {
        Integer unit = 1024;
        if(filesize < unit) {
            return String.format("(%d B)", filesize);
        }
        int exp = (int) (Math.log(filesize) / Math.log(unit));
        
        return String.format("(%.0f %s)", filesize / Math.pow(unit, exp), "KMGTPE".charAt(exp - 1));
    }
    
}
