package com.devkbil.mail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@ApiModel(value = "코드 : TreeVO", description = "ClassCodeVO")
@XmlRootElement(name = "ClassCodeVO")
@XmlType(propOrder = {"from", "to", "cc", "bcc", "file", "subject", "conents", "entrydate"})
@Getter
@Setter
public class ImportMailVO {
    
    @ApiModelProperty(value = "발신인")
    private String from; // 발신인
    @ApiModelProperty(value = "수신인")
    private ArrayList<String> to = new ArrayList<String>(); // 수신인
    @ApiModelProperty(value = "참조자")
    private ArrayList<String> cc = new ArrayList<String>(); // 참조자
    @ApiModelProperty(value = "숨은참조자")
    private ArrayList<String> bcc = new ArrayList<String>(); // 숨은참조자
    @ApiModelProperty(value = "첨부파일")
    private ArrayList<String> file = new ArrayList<String>(); // 첨부파일
    
    @ApiModelProperty(value = "제목")
    private String subject; // 제목
    @ApiModelProperty(value = "내용")
    private String conents; // 내용
    @ApiModelProperty(value = "작성일")
    private String entrydate; // 작성일
    
}