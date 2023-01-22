package com.devkbil.mtssbj.mail;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "메일 : ImportMailVO", description = "메일")
@XmlRootElement(name = "ImportMailVO")
@XmlType(propOrder = {"from", "to", "cc", "bcc", "file", "subject", "conents", "entrydate"})
@Getter
@Setter
public class ImportMailVO {

    @ApiModelProperty(value = "발신인")
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
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
