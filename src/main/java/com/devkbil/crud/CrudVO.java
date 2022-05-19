package com.devkbil.crud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "트리구조 : TreeVO", description = "TreeVO")
@XmlRootElement(name = "TreeVO")
@XmlType(propOrder = {"crno", "crtitle", "userno", "crmemo", "crdate", "usernm"})
@Getter
@Setter
@ToString
public class CrudVO {
    
    @ApiModelProperty(value = "번호")
    private String crno;        // 번호
    @ApiModelProperty(value = "제목")
    private String crtitle;    // 제목
    @ApiModelProperty(value = "작성자")
    private String userno;        // 작성자
    @ApiModelProperty(value = "내용")
    private String crmemo;        // 내용
    @ApiModelProperty(value = "작성일자")
    private String crdate;        // 작성일자
    @ApiModelProperty(value = "작성자 이름")
    private String usernm;        // 작성자 이름
    
}
